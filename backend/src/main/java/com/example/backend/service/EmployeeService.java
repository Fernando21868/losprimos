package com.example.backend.service;

import com.example.backend.config.ModelMapperConfig;
import com.example.backend.config.PasswordEncoderConfig;
import com.example.backend.dto.request.EmployeeDtoRequest;
import com.example.backend.dto.response.EmployeeDtoResponse;
import com.example.backend.dto.response.ResponseSuccessDto;
import com.example.backend.exceptions.EmployeeNotFoundException;
import com.example.backend.exceptions.UserAlreadExistException;
import com.example.backend.model.Employee;
import com.example.backend.model.Role;
import com.example.backend.model.RoleEnum;
import com.example.backend.repository.IEmployeeRepository;
import com.example.backend.repository.IRoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService implements IEmployeeService{

    private final IEmployeeRepository employeeRepository;
    private final ModelMapperConfig mapper;
    private final IRoleRepository roleRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;

    public EmployeeService(IEmployeeRepository employeeRepository, ModelMapperConfig modelMapperConfig, IRoleRepository roleRepository, PasswordEncoderConfig passwordEncoderConfig){
        this.employeeRepository = employeeRepository;
        this.mapper = modelMapperConfig;
        this.roleRepository = roleRepository;
        this.passwordEncoderConfig = passwordEncoderConfig;
    }
    @Override
    public List<EmployeeDtoResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(employee -> mapper.modelMapper().map(employee, EmployeeDtoResponse.class)).collect(Collectors.toList());
    }

    @Override
    public EmployeeDtoResponse getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            return mapper.modelMapper().map(employee.get(), EmployeeDtoResponse.class);
        }
        throw new EmployeeNotFoundException("No se encontro el empleado");
    }

    @Override
    public ResponseSuccessDto<EmployeeDtoResponse> createEmployee(EmployeeDtoRequest employeeDtoRequest) {
        if(emailExists(employeeDtoRequest.getEmail())){
            throw new UserAlreadExistException("Actualmente ya existe un empleado con el email: " + employeeDtoRequest.getEmail());
        }
        if(usernameExists(employeeDtoRequest.getUsername())){
            throw new UserAlreadExistException("Actualmente ya existe un empleado con el username: " + employeeDtoRequest.getUsername());
        }
        Employee employee = mapper.modelMapper().map(employeeDtoRequest, Employee.class);
        if(employee.getRoles()!=null) {
            // Guardar los roles primero y actualizar las referencias en el empleado
            List<Role> persistedRoles = employee.getRoles().stream()
                    .map(role -> roleRepository.save(role))
                    .collect(Collectors.toList());
            employee.setRoles(persistedRoles); // Actualizar la lista de roles en el objeto Employee
        } else {
            Role role = new Role();
            role.setRol(RoleEnum.EMPLOYEE);
            List<Role> persistedRoles = new ArrayList<>();
            persistedRoles.add(role);
            roleRepository.save(persistedRoles.get(0));
            employee.setRoles(persistedRoles);
        }
        employee.setPassword(passwordEncoderConfig.passwordEncoder().encode(employee.getPassword()));
        Employee employeePersist = employeeRepository.save(employee);
        EmployeeDtoResponse employeeDtoResponse = mapper.modelMapper().map(employeePersist, EmployeeDtoResponse.class);
        return new ResponseSuccessDto<>(employeeDtoResponse, 201, "El empleado se creó con éxito", false);
    }

    @Override
    public ResponseSuccessDto<EmployeeDtoResponse> updateEmployee(Long id, EmployeeDtoRequest employeeDtoRequest) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            Employee existingEmployee = employee.get();
            mapper.modelMapper().map(employeeDtoRequest, existingEmployee);
            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            EmployeeDtoResponse employeeDtoResponse = mapper.modelMapper().map(updatedEmployee, EmployeeDtoResponse.class);
            return new ResponseSuccessDto<>(employeeDtoResponse, 200, "El empleado fue actualizado con exito", false);
        }
        throw new EmployeeNotFoundException("No se encontro el empleado para actualizar");
    }

    @Override
    public ResponseSuccessDto<EmployeeDtoResponse> deleteEmployee(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            EmployeeDtoResponse employeeDtoResponse = mapper.modelMapper().map(employee.get(), EmployeeDtoResponse.class);
            employeeRepository.deleteById(id);
            return new ResponseSuccessDto<>(null, 204, "El empleado se elimino con exito", false);
        }
        throw new EmployeeNotFoundException("No se encontro el empleado para eliminar");
    }

    private boolean emailExists(String email){
        return employeeRepository.findByEmail(email) != null;
    }

    private boolean usernameExists(String username){
        return employeeRepository.findByUsername(username) != null;
    }
}

package com.example.backend.service;

import com.example.backend.config.EmailConfig;
import com.example.backend.config.ModelMapperConfig;
import com.example.backend.config.PasswordEncoderConfig;
import com.example.backend.dto.request.EmployeeDtoRequest;
import com.example.backend.dto.response.ClientDtoResponse;
import com.example.backend.dto.response.EmployeeDtoResponse;
import com.example.backend.dto.response.ResponseSuccessDto;
import com.example.backend.exceptions.ClientNotFoundException;
import com.example.backend.exceptions.EmployeeNotFoundException;
import com.example.backend.exceptions.UsernameAlreadExistException;
import com.example.backend.model.Employee;
import com.example.backend.model.Role;
import com.example.backend.model.RoleEnum;
import com.example.backend.repository.IEmployeeRepository;
import com.example.backend.repository.IRoleRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Service for employee - CRUD operations
@Service
public class EmployeeService implements IEmployeeService{

    // Inject dependencies
    private final IEmployeeRepository employeeRepository;
    private final ModelMapperConfig mapper;
    private final IRoleRepository roleRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final EmailConfig emailConfig;

    // Constructor for dependencies
    public EmployeeService(IEmployeeRepository employeeRepository, ModelMapperConfig modelMapperConfig, IRoleRepository roleRepository, PasswordEncoderConfig passwordEncoderConfig, EmailConfig emailConfig){
        this.employeeRepository = employeeRepository;
        this.mapper = modelMapperConfig;
        this.roleRepository = roleRepository;
        this.passwordEncoderConfig = passwordEncoderConfig;
        this.emailConfig = emailConfig;
    }

    /***
     * Service con get all the employees
     * @return List of all employees created
     */
    @Override
    public ResponseSuccessDto<List<EmployeeDtoResponse>> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDtoResponse> employeeDtoResponses = employees.stream().map(employee -> mapper.modelMapper().map(employee, EmployeeDtoResponse.class)).collect(Collectors.toList());
        return new ResponseSuccessDto<>(employeeDtoResponses, 200, "Los empleados fueron encontrados con exito", false);
    }

    /***
     * Service to find an employee by an id
     * @param id unique parameter to find the employee
     * @return the employee by the specific id
     */
    @Override
    public ResponseSuccessDto<EmployeeDtoResponse> getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            EmployeeDtoResponse employeeDtoResponse = mapper.modelMapper().map(employee.get(), EmployeeDtoResponse.class);
            return new ResponseSuccessDto<>(employeeDtoResponse, 200, "El empleado con el id: " + id + " fue encontrado", false);
        }
        throw new EmployeeNotFoundException("No se encontro el empleado con el id: " + id + " especificado");
    }

    /***
     * Service to create an employee
     * @param employeeDtoRequest data to create an employee
     * @return the employee created
     */
    @Override
    public ResponseSuccessDto<EmployeeDtoResponse> createEmployee(EmployeeDtoRequest employeeDtoRequest) {
        if(emailExists(employeeDtoRequest.getEmail())){
            throw new UsernameAlreadExistException("Actualmente ya existe un empleado con el email: " + employeeDtoRequest.getEmail());
        }
        if(usernameExists(employeeDtoRequest.getUsername())){
            throw new UsernameAlreadExistException("Actualmente ya existe un empleado con el username: " + employeeDtoRequest.getUsername());
        }
        Employee employee = mapper.modelMapper().map(employeeDtoRequest, Employee.class);
        if(employee.getRoles()!=null) {
            // Guardar los roles primero y actualizar las referencias en el empleado
            List<Role> persistedRoles = employee.getRoles().stream()
                    .map(roleRepository::save)
                    .collect(Collectors.toList());
            employee.setRoles(persistedRoles); // Actualizar la lista de roles en el objeto Employee
        } else {
            // TODO: FIJARSE SI AUTOMATICAMENTE SE PUEDEN GUARDAR LOS ROLES DEL DEBIDO A LA RELACION MANYTOMANY Y EL CASACADE
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

    /***
     * Service to update an employee by an unique id
     * @param id unique id to find an employee and updated it
     * @param employeeDtoRequest data to update the employee
     * @return the employee updated
     */
    @Override
    public ResponseSuccessDto<EmployeeDtoResponse> updateEmployee(Long id, EmployeeDtoRequest employeeDtoRequest) {
        if(emailExists(employeeDtoRequest.getEmail())){
            throw new UsernameAlreadExistException("Actualmente ya existe un empleado con el email: " + employeeDtoRequest.getEmail());
        }
        if(usernameExists(employeeDtoRequest.getUsername())){
            throw new UsernameAlreadExistException("Actualmente ya existe un empleado con el username: " + employeeDtoRequest.getUsername());
        }
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

    /***
     * Service to delete an employee by an id
     * @param id unique id to delete an employee
     * @return status
     */
    @Override
    public ResponseSuccessDto<EmployeeDtoResponse> deleteEmployee(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            employeeRepository.deleteById(id);
            return new ResponseSuccessDto<>(null, 204, "El empleado se elimino con exito", false);
        }
        throw new EmployeeNotFoundException("No se encontro el empleado para eliminar");
    }

    /***
     * Service to register an employee and send an email for verify an account
     * @param employeeDtoRequest the data to register a new employee
     * @param siteURL the URL to add to the email to verify the employee by an email
     * @return the employee being created
     * @throws UnsupportedEncodingException exception
     * @throws MessagingException exception
     */
    @Override
    public ResponseSuccessDto<EmployeeDtoResponse> registerEmployee(EmployeeDtoRequest employeeDtoRequest, String siteURL) throws UnsupportedEncodingException, MessagingException {
        if(emailExists(employeeDtoRequest.getEmail())){
            throw new UsernameAlreadExistException("Actualmente ya existe un empleado con el email: " + employeeDtoRequest.getEmail());
        }
        if(usernameExists(employeeDtoRequest.getUsername())){
            throw new UsernameAlreadExistException("Actualmente ya existe un empleado con el username: " + employeeDtoRequest.getUsername());
        }
        Employee employee = mapper.modelMapper().map(employeeDtoRequest, Employee.class);
        if(employee.getRoles()!=null) {
            // Guardar los roles primero y actualizar las referencias en el empleado
            List<Role> persistedRoles = employee.getRoles().stream()
                    .map(roleRepository::save)
                    .collect(Collectors.toList());
            employee.setRoles(persistedRoles); // Actualizar la lista de roles en el objeto Employee
        } else {
            // TODO: FIJARSE SI AUTOMATICAMENTE SE PUEDEN GUARDAR LOS ROLES DEL DEBIDO A LA RELACION MANYTOMANY Y EL CASACADE
            Role role = new Role();
            role.setRol(RoleEnum.EMPLOYEE);
            List<Role> persistedRoles = new ArrayList<>();
            persistedRoles.add(role);
            roleRepository.save(persistedRoles.get(0));
            employee.setRoles(persistedRoles);
        }
        employee.setPassword(passwordEncoderConfig.passwordEncoder().encode(employee.getPassword()));
        String randomCode = RandomString.make(64);
        employee.setVerificationCode(randomCode);
        employee.setEnabled(false);
        Employee employeePersist = employeeRepository.save(employee);
        EmployeeDtoResponse employeeDtoResponse = mapper.modelMapper().map(employeePersist, EmployeeDtoResponse.class);
        emailConfig.sendVerificationEmail(employee, siteURL);
        return new ResponseSuccessDto<>(employeeDtoResponse, 201, "El empleado se creó con éxito", false);
    }

    /***
     * Service to get an employee by the username
     * @param username unique username to find an employee
     * @return the employee found
     */
    @Override
    public EmployeeDtoResponse getEmployeeByUsername(String username) {
        Optional<Employee> employee = employeeRepository.findByUsername(username);
        if(employee.isPresent()){
            return mapper.modelMapper().map(employee.get(), EmployeeDtoResponse.class);
        }
        throw new ClientNotFoundException("No se encontro el empleado con el id especificado");
    }

    /***
     * Service to confirm an account of an employee by a token sent to his/her email
     * @param verificationCode token that was saved with a registered employee
     * @return the employee being verified
     */
    @Override
    public ResponseSuccessDto<EmployeeDtoResponse> verifyRegisteredAccount(String verificationCode) {
        Optional<Employee> employee = employeeRepository.findByVerificationCode(verificationCode);
        if(employee.isPresent()){
            Employee employeePersist = employee.get();
            employeePersist.setVerificationCode(null);
            employeePersist.setEnabled(true);
            employeeRepository.save(employeePersist);
            ClientDtoResponse clientDtoResponse = mapper.modelMapper().map(employeePersist, ClientDtoResponse.class);
            return new ResponseSuccessDto<>(clientDtoResponse, 201, "El empleado fue creado con exito", false);
        }
        throw new ClientNotFoundException("La verificacion de la cuenta no tuvo exito, el codigo no existe");
    }

    /***
     * Method to verify if an email already exists in the DB
     * @param email to search in the database
     * @return true or false
     */
    private boolean emailExists(String email){
        return employeeRepository.findByEmail(email).isPresent();
    }

    /***
     * Method to verify if a username already exists in the DB
     * @param username to search in the database
     * @return true or false
     */
    private boolean usernameExists(String username){
        return employeeRepository.findByUsername(username).isPresent();
    }
}

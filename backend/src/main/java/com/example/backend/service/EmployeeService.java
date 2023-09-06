package com.example.backend.service;

import com.example.backend.config.EmailConfig;
import com.example.backend.config.ModelMapperConfig;
import com.example.backend.config.PasswordEncoderConfig;
import com.example.backend.dto.request.EmployeeDtoRequest;
import com.example.backend.dto.response.EmployeeDtoResponse;
import com.example.backend.dto.response.ResponseSuccessDto;
import com.example.backend.exceptions.EmployeeNotFoundException;
import com.example.backend.exceptions.UsernameAlreadExistException;
import com.example.backend.model.Employee;
import com.example.backend.model.Role;
import com.example.backend.model.RoleEnum;
import com.example.backend.repository.IRoleRepository;
import com.example.backend.repository.IEmployeeRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService extends UserService<EmployeeDtoResponse, EmployeeDtoRequest, Employee, IEmployeeRepository, EmployeeNotFoundException> implements IEmployeeService{

    private final IRoleRepository roleRepository;
    public EmployeeService(IRoleRepository roleRepository, IEmployeeRepository userRepository, ModelMapperConfig mapper, PasswordEncoderConfig passwordEncoderConfig, EmailConfig emailConfig) {
        super(userRepository, mapper, passwordEncoderConfig, emailConfig);
        this.roleRepository = roleRepository;
    }

    @Override
    protected Class<EmployeeDtoResponse> getResponseClass() {
        return EmployeeDtoResponse.class;
    }

    @Override
    protected Class<Employee> getEntityClass() {
        return Employee.class;
    }

    @Override
    protected EmployeeNotFoundException createNotFoundException(String message) {
        return new EmployeeNotFoundException(message);
    }

    @Override
    public ResponseSuccessDto<EmployeeDtoResponse> register(EmployeeDtoRequest employeeDtoRequest, String siteURL) throws UnsupportedEncodingException, MessagingException {
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
        Employee employeePersist = userRepository.save(employee);
        EmployeeDtoResponse employeeDtoResponse = mapper.modelMapper().map(employeePersist, EmployeeDtoResponse.class);
        emailConfig.sendVerificationEmail(employee, siteURL);
        return new ResponseSuccessDto<>(employeeDtoResponse, 201, "El empleado se creó con éxito", false);
    }
}

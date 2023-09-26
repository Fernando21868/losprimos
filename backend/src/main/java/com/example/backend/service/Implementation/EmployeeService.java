package com.example.backend.service.Implementation;

import com.example.backend.config.EmailConfig;
import com.example.backend.config.ModelMapperConfig;
import com.example.backend.config.PasswordEncoderConfig;
import com.example.backend.dto.request.EmployeeDtoRequest;
import com.example.backend.dto.response.EmployeeDtoResponse;
import com.example.backend.dto.response.ResponseSuccessDto;
import com.example.backend.exceptions.EmailAlreadExistException;
import com.example.backend.exceptions.EmployeeNotFoundException;
import com.example.backend.exceptions.UsernameAlreadExistException;
import com.example.backend.model.Employee;
import com.example.backend.model.Role;
import com.example.backend.model.RoleEnum;
import com.example.backend.repository.IRoleRepository;
import com.example.backend.repository.IEmployeeRepository;
import com.example.backend.service.Interface.IEmployeeService;
import net.bytebuddy.utility.RandomString;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class EmployeeService<
            ResponseDTO extends EmployeeDtoResponse,
            RequestDTO extends EmployeeDtoRequest,
            Model extends Employee,
            Repository extends IEmployeeRepository<Model>,
            NotFoundException extends EmployeeNotFoundException>
        extends UserService<
            ResponseDTO,
            RequestDTO,
            Model,
            Repository,
            NotFoundException>
        implements IEmployeeService<
                    ResponseDTO, RequestDTO> {
    public EmployeeService(Repository userRepository, ModelMapperConfig mapper, IRoleRepository roleRepository, PasswordEncoderConfig passwordEncoderConfig, EmailConfig emailConfig) {
        super(userRepository, mapper, roleRepository, passwordEncoderConfig, emailConfig);
    }

    /***
     * Service implementation to register an employee of a specific type
     * @param employeeDtoRequest data to create an employee
     * @return the employee created
     */
    @Override
    public ResponseSuccessDto<ResponseDTO> register(RequestDTO employeeDtoRequest, String siteURL) throws UnsupportedEncodingException, MessagingException{
        if (emailExists(employeeDtoRequest.getEmail())) {
            throw new EmailAlreadExistException("Actualmente ya existe un empleado con el email: " + employeeDtoRequest.getEmail());
        }
        if (usernameExists(employeeDtoRequest.getUsername())) {
            throw new UsernameAlreadExistException("Actualmente ya existe un empleado con el username: " + employeeDtoRequest.getUsername());
        }
        Model employee = super.getMapper().modelMapper().map(employeeDtoRequest, super.getModel());
        if (employee.getRoles() != null) {
            // Guardar los roles primero y actualizar las referencias en el empleado
            List<Role> persistedRoles = employee.getRoles().stream()
                    .map(super.getRoleRepository()::save)
                    .collect(Collectors.toList());
            employee.setRoles(persistedRoles); // Actualizar la lista de roles en el objeto Employee
        } else {
            // TODO: FIJARSE SI AUTOMATICAMENTE SE PUEDEN GUARDAR LOS ROLES DEL DEBIDO A LA RELACION MANYTOMANY Y EL CASACADE
            Role role = new Role();
            role.setRol(RoleEnum.EMPLOYEE);
            List<Role> persistedRoles = new ArrayList<>();
            persistedRoles.add(role);
            super.getRoleRepository().save(persistedRoles.get(0));
            employee.setRoles(persistedRoles);
        }
        employee.setPassword(super.getPasswordEncoderConfig().passwordEncoder().encode(employee.getPassword()));
        String randomCode = RandomString.make(64);
        employee.setVerificationCode(randomCode);
        employee.setEnabled(false);
        Model employeePersist = super.getPersonRepository().save(employee);
        ResponseDTO employeeDtoResponse = super.getMapper().modelMapper().map(employeePersist,
                super.getResponse());
        super.getEmailConfig().sendVerificationEmail(employee, siteURL);
        return new ResponseSuccessDto<>(employeeDtoResponse, 201, "El empleado se creó con éxito", false);
    }

}

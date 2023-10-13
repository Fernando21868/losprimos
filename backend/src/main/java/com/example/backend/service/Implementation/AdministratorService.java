package com.example.backend.service.Implementation;

import com.example.backend.config.EmailConfig;
import com.example.backend.config.ModelMapperConfig;
import com.example.backend.config.PasswordEncoderConfig;
import com.example.backend.dto.request.AdministratorDTORequest;
import com.example.backend.dto.response.AdministratorDTOResponse;
import com.example.backend.exceptions.AdministratorNotFoundException;
import com.example.backend.model.Administrator;
import com.example.backend.repository.IAdministratorRepository;
import com.example.backend.repository.IRoleRepository;
import com.example.backend.service.Interface.IAdministratorService;
import org.springframework.stereotype.Service;


@Service
public class AdministratorService extends EmployeeService<AdministratorDTOResponse, AdministratorDTORequest,
        Administrator, IAdministratorRepository, AdministratorNotFoundException> implements IAdministratorService {

    public AdministratorService(IAdministratorRepository userRepository, ModelMapperConfig mapper, IRoleRepository roleRepository, PasswordEncoderConfig passwordEncoderConfig, EmailConfig emailConfig) {
        super(userRepository, mapper, roleRepository, passwordEncoderConfig, emailConfig);
    }

    @Override
    protected String getVerificationEndpointEmployee() {
        return "administrators";
    }

    @Override
    protected Class<AdministratorDTOResponse> getResponseClass() {
        return AdministratorDTOResponse.class;
    }

    @Override
    protected Class<Administrator> getEntityClass() {
        return Administrator.class;
    }

    @Override
    protected AdministratorNotFoundException notFoundException() {
        return new AdministratorNotFoundException("No se encontro un administrador con el id especificado");
    }

    @Override
    protected String getAllMessage() {
        return "Los administradores fueron encontrados con exito";
    }

}

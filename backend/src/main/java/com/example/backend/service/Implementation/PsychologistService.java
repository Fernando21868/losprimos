package com.example.backend.service.Implementation;

import com.example.backend.config.EmailConfig;
import com.example.backend.config.ModelMapperConfig;
import com.example.backend.config.PasswordEncoderConfig;
import com.example.backend.dto.request.PsychologistDTORequest;
import com.example.backend.dto.response.PsychologistDTOResponse;
import com.example.backend.exceptions.PsychologistNotFoundException;
import com.example.backend.model.Psychologist;
import com.example.backend.repository.IPsychologistRepository;
import com.example.backend.repository.IRoleRepository;
import com.example.backend.service.Interface.IPsychologistService;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Service;

@Service
public class PsychologistService extends EmployeeService<PsychologistDTOResponse, PsychologistDTORequest,
        Psychologist, IPsychologistRepository, PsychologistNotFoundException> implements IPsychologistService {

    public PsychologistService(IPsychologistRepository userRepository, ModelMapperConfig mapper, IRoleRepository roleRepository, PasswordEncoderConfig passwordEncoderConfig, EmailConfig emailConfig) {
        super(userRepository, mapper, roleRepository, passwordEncoderConfig, emailConfig);
    }

    @Override
    protected String getVerificationEndpointEmployee() {
        return "psychologists";
    }

    @Override
    protected Class<PsychologistDTOResponse> getResponseClass() {
        return PsychologistDTOResponse.class;
    }

    @Override
    protected Class<Psychologist> getEntityClass() {
        return Psychologist.class;
    }

    @Override
    protected PsychologistNotFoundException notFoundException() {
        return new PsychologistNotFoundException("No se encontro un psicologo con el id especificado");
    }

    @Override
    protected String getAllMessage() {
        return "Los psicologos fueron encontrados con exito";
    }
}

package com.example.backend.service.Implementation;

import com.example.backend.config.EmailConfig;
import com.example.backend.config.ModelMapperConfig;
import com.example.backend.config.PasswordEncoderConfig;
import com.example.backend.dto.request.NurseDTORequest;
import com.example.backend.dto.response.NurseDTOResponse;
import com.example.backend.exceptions.NurseNotFoundException;
import com.example.backend.model.Nurse;
import com.example.backend.repository.INurseRepository;
import com.example.backend.repository.IRoleRepository;
import com.example.backend.service.Interface.INurseService;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Service;

@Service
public class NurseService extends EmployeeService<NurseDTOResponse, NurseDTORequest, Nurse, INurseRepository, NurseNotFoundException> implements INurseService {

    public NurseService(INurseRepository userRepository, ModelMapperConfig mapper, IRoleRepository roleRepository, PasswordEncoderConfig passwordEncoderConfig, EmailConfig emailConfig) {
        super(userRepository, mapper, roleRepository, passwordEncoderConfig, emailConfig);
    }

    @Override
    protected Class<NurseDTOResponse> getResponseClass() {
        return NurseDTOResponse.class;
    }

    @Override
    protected Class<Nurse> getEntityClass() {
        return Nurse.class;
    }

    @Override
    protected NurseNotFoundException createNotFoundException(String message) {
        return new NurseNotFoundException(message);
    }

    @Override
    protected String getAllMessage() {
        return "Los enfermeros fueron encontrados con exito";
    }
}

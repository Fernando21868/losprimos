package com.example.backend.service.Implementation;

import com.example.backend.config.EmailConfig;
import com.example.backend.config.ModelMapperConfig;
import com.example.backend.config.PasswordEncoderConfig;
import com.example.backend.dto.request.SocialWorkerDTORequest;
import com.example.backend.dto.response.SocialWorkerDTOResponse;
import com.example.backend.exceptions.SocialWorkerNotFoundException;
import com.example.backend.model.SocialWorker;
import com.example.backend.repository.IRoleRepository;
import com.example.backend.repository.ISocialWorkerRepository;
import com.example.backend.service.Interface.ISocialWorkerService;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Service;

@Service
public class SocialWorkerService extends EmployeeService<SocialWorkerDTOResponse, SocialWorkerDTORequest,
        SocialWorker, ISocialWorkerRepository, SocialWorkerNotFoundException> implements ISocialWorkerService {

    public SocialWorkerService(ISocialWorkerRepository userRepository, ModelMapperConfig mapper, IRoleRepository roleRepository, PasswordEncoderConfig passwordEncoderConfig, EmailConfig emailConfig) {
        super(userRepository, mapper, roleRepository, passwordEncoderConfig, emailConfig);
    }

    @Override
    protected Class<SocialWorkerDTOResponse> getResponseClass() {
        return SocialWorkerDTOResponse.class;
    }

    @Override
    protected Class<SocialWorker> getEntityClass() {
        return SocialWorker.class;
    }

    @Override
    protected SocialWorkerNotFoundException createNotFoundException(String message) {
        return new SocialWorkerNotFoundException(message);
    }

    @Override
    protected String getAllMessage() {
        return "Los trabajadores sociales fueron encontrados con exito";
    }
}

package com.example.backend.service.Implementation;

import com.example.backend.config.EmailConfig;
import com.example.backend.config.ModelMapperConfig;
import com.example.backend.config.PasswordEncoderConfig;
import com.example.backend.dto.request.KinesiologistDTORequest;
import com.example.backend.dto.response.KinesiologistDTOResponse;
import com.example.backend.exceptions.KinesiologistNotFoundException;
import com.example.backend.model.Kinesiologist;
import com.example.backend.repository.IKinesiologistRepository;
import com.example.backend.repository.IRoleRepository;
import com.example.backend.service.Interface.IKinesiologistService;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Service;

@Service
public class KinesiologistService extends EmployeeService<KinesiologistDTOResponse, KinesiologistDTORequest,
        Kinesiologist, IKinesiologistRepository, KinesiologistNotFoundException> implements IKinesiologistService {

    public KinesiologistService(IKinesiologistRepository userRepository, ModelMapperConfig mapper, IRoleRepository roleRepository, PasswordEncoderConfig passwordEncoderConfig, EmailConfig emailConfig) {
        super(userRepository, mapper, roleRepository, passwordEncoderConfig, emailConfig);
    }

    @Override
    protected Class<KinesiologistDTOResponse> getResponseClass() {
        return KinesiologistDTOResponse.class;
    }

    @Override
    protected Class<Kinesiologist> getEntityClass() {
        return Kinesiologist.class;
    }

    @Override
    protected KinesiologistNotFoundException createNotFoundException(String message) {
        return new KinesiologistNotFoundException(message);
    }

    @Override
    protected String getAllMessage() {
        return "Los kinesiologos fueron encontrados con exito";
    }
}

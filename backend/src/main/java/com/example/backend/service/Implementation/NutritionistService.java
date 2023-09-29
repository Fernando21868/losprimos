package com.example.backend.service.Implementation;

import com.example.backend.config.EmailConfig;
import com.example.backend.config.ModelMapperConfig;
import com.example.backend.config.PasswordEncoderConfig;
import com.example.backend.dto.request.NutritionistDTORequest;
import com.example.backend.dto.response.NutritionistDTOResponse;
import com.example.backend.exceptions.NutritionistNotFoundException;
import com.example.backend.model.Nutritionist;
import com.example.backend.repository.INutritionistRepository;
import com.example.backend.repository.IRoleRepository;
import com.example.backend.service.Interface.INutritionistService;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Service;

@Service
public class NutritionistService extends EmployeeService<NutritionistDTOResponse, NutritionistDTORequest,
        Nutritionist, INutritionistRepository, NutritionistNotFoundException> implements INutritionistService {

    public NutritionistService(INutritionistRepository userRepository, ModelMapperConfig mapper, IRoleRepository roleRepository, PasswordEncoderConfig passwordEncoderConfig, EmailConfig emailConfig) {
        super(userRepository, mapper, roleRepository, passwordEncoderConfig, emailConfig);
    }

    @Override
    protected String getVerificationEndpointEmployee() {
        return "nutritionists";
    }

    @Override
    protected Class<NutritionistDTOResponse> getResponseClass() {
        return NutritionistDTOResponse.class;
    }

    @Override
    protected Class<Nutritionist> getEntityClass() {
        return Nutritionist.class;
    }

    @Override
    protected NutritionistNotFoundException createNotFoundException(String message) {
        return new NutritionistNotFoundException(message);
    }

    @Override
    protected String getAllMessage() {
        return "Los nutricionistas fueron encontrados con exito";
    }
}

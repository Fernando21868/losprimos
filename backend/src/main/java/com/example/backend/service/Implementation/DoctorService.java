package com.example.backend.service.Implementation;

import com.example.backend.config.EmailConfig;
import com.example.backend.config.ModelMapperConfig;
import com.example.backend.config.PasswordEncoderConfig;
import com.example.backend.dto.request.DoctorDTORequest;
import com.example.backend.dto.response.DoctorDTOResponse;
import com.example.backend.exceptions.DoctorNotFoundException;
import com.example.backend.model.Doctor;
import com.example.backend.repository.IDoctorRepository;
import com.example.backend.repository.IRoleRepository;
import com.example.backend.service.Interface.IDoctorService;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Service;

@Service
public class DoctorService extends EmployeeService<DoctorDTOResponse, DoctorDTORequest,
        Doctor, IDoctorRepository, DoctorNotFoundException>
        implements IDoctorService {

    public DoctorService(IDoctorRepository userRepository, ModelMapperConfig mapper, IRoleRepository roleRepository, PasswordEncoderConfig passwordEncoderConfig, EmailConfig emailConfig) {
        super(userRepository, mapper, roleRepository, passwordEncoderConfig, emailConfig);
    }

    @Override
    protected String getVerificationEndpointEmployee() {
        return "doctors";
    }

    @Override
    protected Class<DoctorDTOResponse> getResponseClass() {
        return DoctorDTOResponse.class;
    }

    @Override
    protected Class<Doctor> getEntityClass() {
        return Doctor.class;
    }

    @Override
    protected DoctorNotFoundException notFoundException() {
        return new DoctorNotFoundException("No se encontro un doctor con el id especificado");
    }

    @Override
    protected String getAllMessage() {
        return "Los doctores fueron encontrados con exito";
    }
}

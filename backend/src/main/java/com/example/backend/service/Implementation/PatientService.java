package com.example.backend.service.Implementation;

import com.example.backend.config.ModelMapperConfig;
import com.example.backend.dto.request.PatientDTORequest;
import com.example.backend.dto.response.PatientDTOResponse;
import com.example.backend.exceptions.PatientNotFoundException;
import com.example.backend.model.Patient;
import com.example.backend.repository.IPatientRepository;
import com.example.backend.service.Interface.IPatientService;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Service;

@Service
public class PatientService extends PersonService<PatientDTOResponse, PatientDTORequest, Patient, IPatientRepository, PatientNotFoundException> implements IPatientService {

    public PatientService(IPatientRepository userRepository, ModelMapperConfig mapper) {
        super(userRepository, mapper);
    }

    @Override
    protected Class<PatientDTOResponse> getResponseClass() {
        return PatientDTOResponse.class;
    }

    @Override
    protected Class<Patient> getEntityClass() {
        return Patient.class;
    }

    @Override
    protected PatientNotFoundException notFoundException() {
        return new PatientNotFoundException("No se encontro un paciente con el id especificado");
    }

    @Override
    protected String getAllMessage() {
        return "Los pacientes fueron encontrados con exito";
    }
}

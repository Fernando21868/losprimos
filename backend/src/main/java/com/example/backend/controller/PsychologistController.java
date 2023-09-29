package com.example.backend.controller;

import com.example.backend.dto.request.PsychologistDTORequest;
import com.example.backend.dto.response.PsychologistDTOResponse;
import com.example.backend.exceptions.PsychologistNotFoundException;
import com.example.backend.model.Psychologist;
import com.example.backend.repository.IPsychologistRepository;
import com.example.backend.service.Implementation.PsychologistService;
import com.example.backend.service.Interface.IPsychologistService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/psychologists")
public class PsychologistController extends EmployeeController<
        PsychologistDTOResponse,
        PsychologistDTORequest,
        Psychologist,
        IPsychologistRepository,
        PsychologistNotFoundException,
        IPsychologistService,
        PsychologistService>{
    public PsychologistController(PsychologistService service) {
        super(service);
    }

}

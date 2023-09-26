package com.example.backend.controller;

import com.example.backend.dto.request.NurseDTORequest;
import com.example.backend.dto.response.NurseDTOResponse;
import com.example.backend.exceptions.NurseNotFoundException;
import com.example.backend.model.Nurse;
import com.example.backend.repository.INurseRepository;
import com.example.backend.service.Implementation.NurseService;
import com.example.backend.service.Interface.INurseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/nurses")
public class NurseController extends EmployeeController<
        NurseDTOResponse,
        NurseDTORequest,
        Nurse,
        INurseRepository,
        NurseNotFoundException,
        INurseService,
        NurseService>{
    public NurseController(NurseService service) {
        super(service);
    }
}

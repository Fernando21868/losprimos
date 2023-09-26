package com.example.backend.controller;

import com.example.backend.dto.request.DoctorDTORequest;
import com.example.backend.dto.response.DoctorDTOResponse;
import com.example.backend.exceptions.DoctorNotFoundException;
import com.example.backend.model.Doctor;
import com.example.backend.repository.IDoctorRepository;
import com.example.backend.service.Implementation.DoctorService;
import com.example.backend.service.Interface.IDoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController extends EmployeeController<
        DoctorDTOResponse,
        DoctorDTORequest,
        Doctor,
        IDoctorRepository,
        DoctorNotFoundException,
        IDoctorService,
        DoctorService>{
    public DoctorController(DoctorService service) {
        super(service);
    }
}

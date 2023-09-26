package com.example.backend.controller;

import com.example.backend.dto.request.KinesiologistDTORequest;
import com.example.backend.dto.response.KinesiologistDTOResponse;
import com.example.backend.exceptions.KinesiologistNotFoundException;
import com.example.backend.model.Kinesiologist;
import com.example.backend.repository.IKinesiologistRepository;
import com.example.backend.service.Implementation.KinesiologistService;
import com.example.backend.service.Interface.IKinesiologistService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/kinesiologists")
public class KinesiologistController extends EmployeeController<
        KinesiologistDTOResponse,
        KinesiologistDTORequest,
        Kinesiologist,
        IKinesiologistRepository,
        KinesiologistNotFoundException,
        IKinesiologistService,
        KinesiologistService>{
    public KinesiologistController(KinesiologistService service) {
        super(service);
    }
}

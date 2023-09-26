package com.example.backend.controller;

import com.example.backend.dto.request.SocialWorkerDTORequest;
import com.example.backend.dto.response.SocialWorkerDTOResponse;
import com.example.backend.exceptions.SocialWorkerNotFoundException;
import com.example.backend.model.SocialWorker;
import com.example.backend.repository.ISocialWorkerRepository;
import com.example.backend.service.Implementation.SocialWorkerService;
import com.example.backend.service.Interface.ISocialWorkerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/socialWorkers")
public class SocialWorkerController extends EmployeeController<
        SocialWorkerDTOResponse,
        SocialWorkerDTORequest,
        SocialWorker,
        ISocialWorkerRepository,
        SocialWorkerNotFoundException,
        ISocialWorkerService,
        SocialWorkerService>{
    public SocialWorkerController(SocialWorkerService service) {
        super(service);
    }
}

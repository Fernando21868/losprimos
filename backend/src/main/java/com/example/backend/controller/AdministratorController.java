package com.example.backend.controller;

import com.example.backend.dto.request.AdministratorDTORequest;
import com.example.backend.dto.response.AdministratorDTOResponse;
import com.example.backend.exceptions.AdministratorNotFoundException;
import com.example.backend.model.Administrator;
import com.example.backend.repository.IAdministratorRepository;
import com.example.backend.service.Implementation.AdministratorService;
import com.example.backend.service.Interface.IAdministratorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/administrators")
public class AdministratorController extends EmployeeController<
        AdministratorDTOResponse,
        AdministratorDTORequest,
        Administrator,
        IAdministratorRepository,
        AdministratorNotFoundException,
        IAdministratorService,
        AdministratorService> {

    public AdministratorController(AdministratorService service) {
        super(service);
    }

}

package com.example.backend.controller;

import com.example.backend.dto.request.AdministratorDTORequest;
import com.example.backend.dto.request.EmployeeDtoRequest;
import com.example.backend.dto.response.AdministratorDTOResponse;
import com.example.backend.dto.response.EmployeeDtoResponse;
import com.example.backend.dto.response.ResponseSuccessDto;
import com.example.backend.exceptions.AdministratorNotFoundException;
import com.example.backend.model.Administrator;
import com.example.backend.repository.IAdministratorRepository;
import com.example.backend.service.AdministratorService;
import com.example.backend.service.EmployeeService;
import com.example.backend.service.IAdministratorService;
import com.example.backend.service.IEmployeeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

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

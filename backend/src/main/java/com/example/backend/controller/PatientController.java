package com.example.backend.controller;

import com.example.backend.dto.request.PatientDTORequest;
import com.example.backend.service.Interface.IPatientService;
import com.example.backend.service.Implementation.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    IPatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    @GetMapping
    public ResponseEntity<?> getAllClients() {
        return new ResponseEntity<>(patientService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        return new ResponseEntity<>(patientService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createClients(@Valid @RequestBody PatientDTORequest patientDTORequest) {
        return new ResponseEntity<>(patientService.create(patientDTORequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateClient(@Valid @RequestBody PatientDTORequest patientDTORequest) {
        return new ResponseEntity<>(patientService.update(patientDTORequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id){
        return new ResponseEntity<>(patientService.delete(id), HttpStatus.OK);
    }

}

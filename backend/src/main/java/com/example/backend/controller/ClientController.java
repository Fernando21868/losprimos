package com.example.backend.controller;

import com.example.backend.dto.request.ClientDtoRequest;
import com.example.backend.service.ClientService;
import com.example.backend.service.IClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    IClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<?> getAllClients() {
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        return new ResponseEntity<>(clientService.getClientById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createClients(@Valid @RequestBody ClientDtoRequest clientCreateDtoRequest) {
        return new ResponseEntity<>(clientService.createClient(clientCreateDtoRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @Valid @RequestBody ClientDtoRequest clientCreateDtoRequest) {
        return new ResponseEntity<>(clientService.updateClient(id, clientCreateDtoRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id){
        return new ResponseEntity<>(clientService.deleteClient(id), HttpStatus.NO_CONTENT);
    }
}

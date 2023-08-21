package com.example.backend.controller;

import com.example.backend.dto.request.ClientDtoRequest;
import com.example.backend.service.ClientService;
import com.example.backend.service.IClientService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    IClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @InitBinder
    public void initialBinderForTrimmingSpaces(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimEditor);
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


    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> registerClient(@Valid @RequestBody ClientDtoRequest clientDtoRequest){
        return new ResponseEntity<>(clientService.registerClient(clientDtoRequest), HttpStatus.OK);
    }

    @GetMapping("/profile/{username}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> getClientByUsername(@PathVariable String username) {
        return new ResponseEntity<>(clientService.getClientByUsername(username), HttpStatus.OK);
    }
}

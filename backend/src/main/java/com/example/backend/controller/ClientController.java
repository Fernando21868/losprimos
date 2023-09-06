package com.example.backend.controller;

import com.example.backend.dto.request.ClientDtoRequest;
import com.example.backend.dto.response.ClientDtoResponse;
import com.example.backend.dto.response.ResponseSuccessDto;
import com.example.backend.service.ClientService;
import com.example.backend.service.IClientService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    IClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    @GetMapping
    public ResponseEntity<?> getAllClients() {
        return new ResponseEntity<>(clientService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        return new ResponseEntity<>(clientService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createClients(@Valid @RequestBody ClientDtoRequest clientDtoRequest) {
        return new ResponseEntity<>(clientService.create(clientDtoRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @Valid @RequestBody ClientDtoRequest clientDtoRequest) {
        return new ResponseEntity<>(clientService.update(id, clientDtoRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id){
        return new ResponseEntity<>(clientService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@Valid @RequestBody ClientDtoRequest clientDtoRequest, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        return new ResponseEntity<>(clientService.register(clientDtoRequest, getSiteURL(request)), HttpStatus.OK);
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verifyRegisteredAccount")
    public ResponseEntity<?> verifyRegisteredAccount(@RequestParam(name = "code") String code){
        ResponseSuccessDto<ClientDtoResponse> responseSuccessDto = clientService.verifyRegisteredAccount(code);
        if(!responseSuccessDto.getError()){
            String clientConfirmationPage = "http://localhost:5173/accountVerified";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", clientConfirmationPage);
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }
        return new ResponseEntity<>(responseSuccessDto, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<?> getClientByUsername(@PathVariable String username) {
        return new ResponseEntity<>(clientService.getByUsername(username), HttpStatus.OK);
    }

}

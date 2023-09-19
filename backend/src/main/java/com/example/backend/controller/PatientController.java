package com.example.backend.controller;

import com.example.backend.dto.request.PatientDTORequest;
import com.example.backend.service.IPatientService;
import com.example.backend.service.PatientService;
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
    public ResponseEntity<?> updateClient(@PathVariable Long id, @Valid @RequestBody PatientDTORequest patientDTORequest) {
        return new ResponseEntity<>(patientService.update(id, patientDTORequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id){
        return new ResponseEntity<>(patientService.delete(id), HttpStatus.OK);
    }

    //@PostMapping("/register")
    //public ResponseEntity<?> registerClient(@Valid @RequestBody ClientDtoRequest clientDtoRequest,
                                             //HttpServletRequest request) throws UnsupportedEncodingException,
    // MessagingException {
    //    return new ResponseEntity<>(patientService.register(clientDtoRequest, getSiteURL(request)), HttpStatus.OK);
    //}
//
    //private String getSiteURL(HttpServletRequest request) {
    //    String siteURL = request.getRequestURL().toString();
    //    return siteURL.replace(request.getServletPath(), "");
    //}
//
    //@GetMapping("/verifyRegisteredAccount")
    //public ResponseEntity<?> verifyRegisteredAccount(@RequestParam(name = "code") String code){
    //    ResponseSuccessDto<ClientDtoResponse> responseSuccessDto = patientService.verifyRegisteredAccount(code);
    //    if(!responseSuccessDto.getError()){
    //        String clientConfirmationPage = "http://localhost:5173/accountVerified";
    //        HttpHeaders headers = new HttpHeaders();
    //        headers.add("Location", clientConfirmationPage);
    //        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    //    }
    //    return new ResponseEntity<>(responseSuccessDto, HttpStatus.BAD_REQUEST);
    //}
//
    //@GetMapping("/profile/{username}")
    //public ResponseEntity<?> getClientByUsername(@PathVariable String username) {
    //    return new ResponseEntity<>(patientService.getByUsername(username), HttpStatus.OK);
    //}

}

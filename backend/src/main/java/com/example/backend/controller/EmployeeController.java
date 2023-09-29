package com.example.backend.controller;

import com.example.backend.dto.request.EmployeeDtoRequest;
import com.example.backend.dto.response.EmployeeDtoResponse;
import com.example.backend.dto.response.ResponseSuccessDto;
import com.example.backend.exceptions.EmployeeNotFoundException;
import com.example.backend.model.Employee;
import com.example.backend.repository.IEmployeeRepository;
import com.example.backend.service.Implementation.EmployeeService;
import com.example.backend.service.Interface.IEmployeeService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Getter
@Setter
public abstract class EmployeeController<
        ResponseDTO extends EmployeeDtoResponse,
        RequestDTO extends EmployeeDtoRequest,
        Model extends Employee,
        Repository extends IEmployeeRepository<Model>,
        NotFoundException extends EmployeeNotFoundException,
        IService extends IEmployeeService<ResponseDTO, RequestDTO>,
        Service extends EmployeeService<ResponseDTO, RequestDTO, Model, Repository,
                NotFoundException>> {

    IService service;
    public EmployeeController(Service service) {
        this.service = (IService) service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RequestDTO employeeDtoRequest) {
        return new ResponseEntity<>(service.create(employeeDtoRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody RequestDTO employeeDtoRequest) {
        return new ResponseEntity<>(service.update(id, employeeDtoRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RequestDTO employeeDtoRequest,
                                                   HttpServletRequest request) throws UnsupportedEncodingException,
            MessagingException {
        return new ResponseEntity<>(service.register(employeeDtoRequest, getSiteURL(request)), HttpStatus.OK);
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verifyRegisteredAccount")
    public ResponseEntity<?> verifyRegisteredAccount(@RequestParam(name = "code") String code){
        ResponseSuccessDto<ResponseDTO> responseSuccessDto = service.verifyRegisteredAccount(code);
        if(!responseSuccessDto.getError()){
            String clientConfirmationPage = "http://localhost:5173/accountVerified";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", clientConfirmationPage);
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }
        return new ResponseEntity<>(responseSuccessDto, HttpStatus.BAD_REQUEST);
    }

}

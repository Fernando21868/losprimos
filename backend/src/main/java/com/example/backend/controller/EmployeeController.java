package com.example.backend.controller;

import com.example.backend.dto.request.EmployeeDtoRequest;
import com.example.backend.dto.response.EmployeeDtoResponse;
import com.example.backend.dto.response.ResponseSuccessDto;
import com.example.backend.service.EmployeeService;
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
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    IEmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDtoRequest employeeDtoRequest) {
        return new ResponseEntity<>(employeeService.create(employeeDtoRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDtoRequest employeeDtoRequest) {
        return new ResponseEntity<>(employeeService.update(id, employeeDtoRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        return new ResponseEntity<>(employeeService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> registerEmployee(@Valid @RequestBody EmployeeDtoRequest employeeDtoRequest, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        return new ResponseEntity<>(employeeService.register(employeeDtoRequest, getSiteURL(request)), HttpStatus.OK);
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verifyRegisteredAccount")
    public ResponseEntity<?> verifyRegisteredAccount(@RequestParam(name = "code") String code){
        ResponseSuccessDto<EmployeeDtoResponse> responseSuccessDto = employeeService.verifyRegisteredAccount(code);
        if(!responseSuccessDto.getError()){
            String clientConfirmationPage = "http://localhost:5173/accountVerified";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", clientConfirmationPage);
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }
        return new ResponseEntity<>(responseSuccessDto, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/profile/{username}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> getEmployeeByUsername(@PathVariable String username) {
        return new ResponseEntity<>(employeeService.getByUsername(username), HttpStatus.OK);
    }

}

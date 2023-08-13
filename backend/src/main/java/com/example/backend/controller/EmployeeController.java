package com.example.backend.controller;

import com.example.backend.dto.request.EmployeeDtoRequest;
import com.example.backend.dto.response.EmployeeDtoResponse;
import com.example.backend.service.EmployeeService;
import com.example.backend.service.IEmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    IEmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDtoRequest employeeDtoRequest) {
        return new ResponseEntity<>(employeeService.createEmployee(employeeDtoRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDtoRequest employeeDtoRequest) {
        return new ResponseEntity<>(employeeService.updateEmployee(id, employeeDtoRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        return new ResponseEntity<>(employeeService.deleteEmployee(id), HttpStatus.NO_CONTENT);
    }
}
















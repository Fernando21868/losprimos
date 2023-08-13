package com.example.backend.service;

import com.example.backend.dto.request.EmployeeDtoRequest;
import com.example.backend.dto.response.EmployeeDtoResponse;
import com.example.backend.dto.response.ResponseSuccessDto;

import java.util.List;

public interface IEmployeeService {

    List<EmployeeDtoResponse> getAllEmployees();
    EmployeeDtoResponse getEmployeeById(Long id);
    ResponseSuccessDto createEmployee(EmployeeDtoRequest employeeDtoRequest);
    ResponseSuccessDto updateEmployee(Long id, EmployeeDtoRequest employeeDtoRequest);
    ResponseSuccessDto deleteEmployee(Long id);

}

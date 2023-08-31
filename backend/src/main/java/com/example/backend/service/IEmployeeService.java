package com.example.backend.service;

import com.example.backend.dto.request.EmployeeDtoRequest;
import com.example.backend.dto.response.ClientDtoResponse;
import com.example.backend.dto.response.EmployeeDtoResponse;
import com.example.backend.dto.response.ResponseSuccessDto;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IEmployeeService {

    ResponseSuccessDto<List<EmployeeDtoResponse>> getAllEmployees();
    ResponseSuccessDto<EmployeeDtoResponse> getEmployeeById(Long id);
    ResponseSuccessDto<EmployeeDtoResponse> createEmployee(EmployeeDtoRequest employeeDtoRequest);
    ResponseSuccessDto<EmployeeDtoResponse> updateEmployee(Long id, EmployeeDtoRequest employeeDtoRequest);
    ResponseSuccessDto<EmployeeDtoResponse> deleteEmployee(Long id);
    ResponseSuccessDto<EmployeeDtoResponse> registerEmployee(EmployeeDtoRequest employeeDtoRequest, String siteURL) throws UnsupportedEncodingException, MessagingException;
    EmployeeDtoResponse getEmployeeByUsername(String username);
    ResponseSuccessDto<EmployeeDtoResponse> verifyRegisteredAccount(String verificationCode);
}

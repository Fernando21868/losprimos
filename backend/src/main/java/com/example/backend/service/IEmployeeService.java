package com.example.backend.service;

import com.example.backend.dto.request.EmployeeDtoRequest;
import com.example.backend.dto.response.EmployeeDtoResponse;
import com.example.backend.dto.response.ResponseSuccessDto;
import com.example.backend.model.Employee;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface IEmployeeService<Response extends EmployeeDtoResponse, Request extends EmployeeDtoRequest> extends IUserService<Response,
        Request>{

}

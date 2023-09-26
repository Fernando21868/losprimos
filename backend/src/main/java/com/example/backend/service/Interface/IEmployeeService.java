package com.example.backend.service.Interface;

import com.example.backend.dto.request.EmployeeDtoRequest;
import com.example.backend.dto.response.EmployeeDtoResponse;

public interface IEmployeeService<Response extends EmployeeDtoResponse, Request extends EmployeeDtoRequest> extends IUserService<Response,
        Request>{

}

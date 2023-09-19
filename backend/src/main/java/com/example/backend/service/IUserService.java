package com.example.backend.service;


import com.example.backend.dto.request.UserDtoRequest;
import com.example.backend.dto.response.ResponseSuccessDto;
import com.example.backend.dto.response.UserDtoResponse;
import com.example.backend.exceptions.EmailAlreadExistException;
import com.example.backend.exceptions.UsernameAlreadExistException;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IUserService<Response extends UserDtoResponse, Request extends UserDtoRequest> extends IPersonService<Response,
        Request>{
    Response getByUsername(String username);
    ResponseSuccessDto<Response> verifyRegisteredAccount(String verificationCode);
    ResponseSuccessDto<Response> register(Request employeeDtoRequest, String siteURL) throws UnsupportedEncodingException, MessagingException;

}

package com.example.backend.service.Interface;


import com.example.backend.dto.request.UserDtoRequest;
import com.example.backend.dto.response.ResponseSuccessDto;
import com.example.backend.dto.response.UserDtoResponse;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface IUserService<Response extends UserDtoResponse, Request extends UserDtoRequest> extends IPersonService<Response,
        Request>{
    ResponseSuccessDto<Response> verifyRegisteredAccount(String verificationCode);
    ResponseSuccessDto<Response> register(Request employeeDtoRequest, String siteURL) throws UnsupportedEncodingException, MessagingException;

}

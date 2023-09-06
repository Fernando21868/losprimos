package com.example.backend.service;


import com.example.backend.dto.response.ResponseSuccessDto;
import com.example.backend.exceptions.EmailAlreadExistException;
import com.example.backend.exceptions.UsernameAlreadExistException;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IUserService<Response, Request> {

    ResponseSuccessDto<List<Response>> getAll();
    ResponseSuccessDto<Response> getById(Long id);
    ResponseSuccessDto<Response> create(Request request);
    ResponseSuccessDto<Response> update(Long id, Request userDtoRequest);
    ResponseSuccessDto<Response> delete(Long id);
    ResponseSuccessDto<Response> register(Request request, String siteURL) throws UnsupportedEncodingException, MessagingException;
    Response getByUsername(String username);
    ResponseSuccessDto<Response> verifyRegisteredAccount(String verificationCode);

}

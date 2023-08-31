package com.example.backend.service;

import com.example.backend.dto.request.UserDtoRequest;
import com.example.backend.dto.response.ClientDtoResponse;
import com.example.backend.dto.request.ClientDtoRequest;
import com.example.backend.dto.response.ResponseSuccessDto;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IClientService {
    ResponseSuccessDto<List<ClientDtoResponse>> getAllClients();
    ResponseSuccessDto<ClientDtoResponse> getClientById(Long id);
    ResponseSuccessDto<ClientDtoResponse> createClient(ClientDtoRequest clientCreateDtoRequest);
    ResponseSuccessDto<ClientDtoResponse> updateClient(Long id, ClientDtoRequest clientCreateDtoRequest);
    ResponseSuccessDto<ClientDtoResponse> deleteClient(Long id);
    ResponseSuccessDto<ClientDtoResponse> registerClient(ClientDtoRequest clientCreateDtoRequest, String siteURL) throws UnsupportedEncodingException, MessagingException;
    ClientDtoResponse getClientByUsername(String username);
    ResponseSuccessDto<ClientDtoResponse> verifyRegisteredAccount(String verificationCode);

}

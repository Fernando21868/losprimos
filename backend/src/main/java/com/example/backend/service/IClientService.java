package com.example.backend.service;

import com.example.backend.dto.request.UserDtoRequest;
import com.example.backend.dto.response.ClientDtoResponse;
import com.example.backend.dto.request.ClientDtoRequest;
import com.example.backend.dto.response.ResponseSuccessDto;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IClientService {
    List<ClientDtoResponse> getAllClients();
    ClientDtoResponse getClientById(Long id);
    ResponseSuccessDto createClient(ClientDtoRequest clientCreateDtoRequest);
    ResponseSuccessDto updateClient(Long id, ClientDtoRequest clientCreateDtoRequest);
    ResponseSuccessDto deleteClient(Long id);
    ResponseSuccessDto registerClient(ClientDtoRequest clientCreateDtoRequest, String siteURL) throws UnsupportedEncodingException, MessagingException;
    ClientDtoResponse getClientByUsername(String username);
    ResponseSuccessDto verifyRegisteredAccount(String verificationCode);

}

package com.example.backend.service;

import com.example.backend.config.EmailConfig;
import com.example.backend.config.ModelMapperConfig;
import com.example.backend.config.PasswordEncoderConfig;
import com.example.backend.dto.request.ClientDtoRequest;
import com.example.backend.dto.response.ClientDtoResponse;
import com.example.backend.exceptions.ClientNotFoundException;
import com.example.backend.model.Client;
import com.example.backend.repository.IClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService extends UserService<ClientDtoResponse, ClientDtoRequest, Client, IClientRepository, ClientNotFoundException> implements IClientService{

    public ClientService(IClientRepository userRepository, ModelMapperConfig mapper, PasswordEncoderConfig passwordEncoderConfig, EmailConfig emailConfig) {
        super(userRepository, mapper, passwordEncoderConfig, emailConfig);
    }

    @Override
    protected Class<ClientDtoResponse> getResponseClass() {
        return ClientDtoResponse.class;
    }

    @Override
    protected Class<Client> getEntityClass() {
        return Client.class;
    }

    @Override
    protected ClientNotFoundException createNotFoundException(String message) {
        return new ClientNotFoundException(message);
    }
}

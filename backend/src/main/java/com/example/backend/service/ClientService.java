package com.example.backend.service;

import com.example.backend.config.ModelMapperConfig;
import com.example.backend.config.PasswordEncoderConfig;
import com.example.backend.dto.response.ClientDtoResponse;
import com.example.backend.dto.request.ClientDtoRequest;
import com.example.backend.dto.response.ResponseSuccessDto;
import com.example.backend.exceptions.ClientNotFoundException;
import com.example.backend.exceptions.UserAlreadExistException;
import com.example.backend.model.Client;
import com.example.backend.model.Role;
import com.example.backend.model.RoleEnum;
import com.example.backend.repository.IClientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService implements IClientService{

    private final IClientRepository clientRepository;
    private final ModelMapperConfig mapper;
    private final PasswordEncoderConfig passwordEncoderConfig;

    public ClientService(IClientRepository clientRepository, ModelMapperConfig mapper, PasswordEncoderConfig passwordEncoderConfig){
        this.clientRepository = clientRepository;
        this.mapper = mapper;
        this.passwordEncoderConfig = passwordEncoderConfig;
    }

    @Override
    public List<ClientDtoResponse> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(client -> mapper.modelMapper().map(client, ClientDtoResponse.class)).collect(Collectors.toList());
    }

    @Override
    public ClientDtoResponse getClientById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            return mapper.modelMapper().map(client.get(), ClientDtoResponse.class);
        }
        throw new ClientNotFoundException("No se encontro el cliente con el id especificado");
    }

    @Override
    public ResponseSuccessDto<ClientDtoResponse> createClient(ClientDtoRequest clientCreateDtoRequest) {
        if(emailExists(clientCreateDtoRequest.getEmail())){
            throw new UserAlreadExistException("Actualmente ya existe un cliente con el email: " + clientCreateDtoRequest.getEmail());
        }
        if(usernameExists(clientCreateDtoRequest.getUsername())){
            throw new UserAlreadExistException("Actualmente ya existe un cliente con el username: " + clientCreateDtoRequest.getUsername());
        }
        Client client = mapper.modelMapper().map(clientCreateDtoRequest, Client.class);
        Role role = new Role();
        role.setRol(RoleEnum.CLIENT);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        client.setRoles(roles);
        client.setPassword(passwordEncoderConfig.passwordEncoder().encode(client.getPassword()));
        Client clientPersist = clientRepository.save(client);
        ClientDtoResponse clientDtoResponse = mapper.modelMapper().map(clientPersist, ClientDtoResponse.class);
        return new ResponseSuccessDto<>(clientDtoResponse, 201, "El cliente fue creado con exito", false);
    }

    @Override
    public ResponseSuccessDto<ClientDtoResponse> updateClient(Long id, ClientDtoRequest clientCreateDtoRequest) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            Client clientExisting = client.get();
            mapper.modelMapper().map(clientCreateDtoRequest, clientExisting);
            Client clientUpdated = clientRepository.save(clientExisting);
            ClientDtoResponse clientDtoResponse = mapper.modelMapper().map(clientUpdated, ClientDtoResponse.class);
            return new ResponseSuccessDto<>(clientDtoResponse, 200, "El cliente fue actulizado con exito", false);
        }
        throw  new ClientNotFoundException("No se encontro el cliente para ser actualizado");
    }

    @Override
    public ResponseSuccessDto<ClientDtoResponse> deleteClient(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            clientRepository.deleteById(id);
            ClientDtoResponse clientDtoResponse = mapper.modelMapper().map(client.get(), ClientDtoResponse.class);
            return new ResponseSuccessDto<>(clientDtoResponse, 204, "El cliente fue eliminado con exito", false);
        }
        throw  new ClientNotFoundException("No se encontro el cliente para ser eliminado");

    }

    private boolean emailExists(String email){
        return clientRepository.findByEmail(email) != null;
    }

    private boolean usernameExists(String username){
        return clientRepository.findByUsername(username) != null;
    }
}

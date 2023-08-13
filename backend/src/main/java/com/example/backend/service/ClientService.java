package com.example.backend.service;

import com.example.backend.config.ModelMapperConfig;
import com.example.backend.dto.response.ClientDtoResponse;
import com.example.backend.dto.request.ClientDtoRequest;
import com.example.backend.dto.response.ResponseSuccessDto;
import com.example.backend.exceptions.ClientNotFoundException;
import com.example.backend.model.Client;
import com.example.backend.repository.IClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService implements IClientService{

    private final IClientRepository clientRepository;

    private final ModelMapperConfig mapper;

    public ClientService(IClientRepository clientRepository, ModelMapperConfig mapper){
        this.clientRepository = clientRepository;
        this.mapper = mapper;
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
        Client client = mapper.modelMapper().map(clientCreateDtoRequest, Client.class);
        Client clientPersist = clientRepository.save(client);
        ClientDtoResponse clientDtoResponse = mapper.modelMapper().map(clientPersist, ClientDtoResponse.class);
        return new ResponseSuccessDto<>(201, "El cliente fue creado con exito", clientDtoResponse);
    }

    @Override
    public ResponseSuccessDto<ClientDtoResponse> updateClient(Long id, ClientDtoRequest clientCreateDtoRequest) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            Client clientExisting = client.get();
            mapper.modelMapper().map(clientCreateDtoRequest, clientExisting);
            Client clientUpdated = clientRepository.save(clientExisting);
            ClientDtoResponse clientDtoResponse = mapper.modelMapper().map(clientUpdated, ClientDtoResponse.class);
            return new ResponseSuccessDto<>(200, "El cliente fue actulizado con exito", clientDtoResponse);
        }
        throw  new ClientNotFoundException("No se encontro el cliente para ser actualizado");
    }

    @Override
    public ResponseSuccessDto<ClientDtoResponse> deleteClient(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            clientRepository.deleteById(id);
            ClientDtoResponse clientDtoResponse = mapper.modelMapper().map(client.get(), ClientDtoResponse.class);
            return new ResponseSuccessDto<>(204, "El cliente fue eliminado con exito", clientDtoResponse);
        }
        throw  new ClientNotFoundException("No se encontro el cliente para ser eliminado");

    }
}

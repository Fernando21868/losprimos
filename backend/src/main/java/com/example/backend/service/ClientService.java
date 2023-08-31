package com.example.backend.service;

import com.example.backend.config.EmailConfig;
import com.example.backend.config.ModelMapperConfig;
import com.example.backend.config.PasswordEncoderConfig;
import com.example.backend.dto.response.ClientDtoResponse;
import com.example.backend.dto.request.ClientDtoRequest;
import com.example.backend.dto.response.ResponseSuccessDto;
import com.example.backend.exceptions.ClientNotFoundException;
import com.example.backend.exceptions.UsernameAlreadExistException;
import com.example.backend.model.Client;
import com.example.backend.model.Role;
import com.example.backend.model.RoleEnum;
import com.example.backend.repository.IClientRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Service for clients - CRUD operations
@Service
public class ClientService implements IClientService{

    // Inject dependencies
    private final IClientRepository clientRepository;
    private final ModelMapperConfig mapper;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final EmailConfig emailConfig;


    // Constructor for dependencies
    public ClientService(IClientRepository clientRepository, ModelMapperConfig mapper, PasswordEncoderConfig passwordEncoderConfig, EmailConfig emailConfig){
        this.clientRepository = clientRepository;
        this.mapper = mapper;
        this.passwordEncoderConfig = passwordEncoderConfig;
        this.emailConfig = emailConfig;
    }

    /***
     * Service con get all the clients
     * @return List of all clients created
     */
    @Override
    public ResponseSuccessDto<List<ClientDtoResponse>> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        List<ClientDtoResponse> clientDtoResponses = clients.stream().map(client -> mapper.modelMapper().map(client, ClientDtoResponse.class)).collect(Collectors.toList());
        return new ResponseSuccessDto<>(clientDtoResponses, 200, "Los clientes fueron encontrados con exito", false);
    }

    /***
     * Service to find a client by an id
     * @param id unique parameter to find the client
     * @return the client by the specific id
     */
    @Override
    public ResponseSuccessDto<ClientDtoResponse> getClientById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            ClientDtoResponse clientDtoResponse = mapper.modelMapper().map(client.get(), ClientDtoResponse.class);
            return new ResponseSuccessDto<>(clientDtoResponse, 200, "El cliente con el id: " + id + " fue encontrado", false);
        }
        throw new ClientNotFoundException("No se encontro el cliente con el id: " + id + " especificado");
    }

    /***
     * Service to create a client
     * @param clientDtoRequest data to create a client
     * @return the client created
     */
    @Override
    public ResponseSuccessDto<ClientDtoResponse> createClient(ClientDtoRequest clientDtoRequest) {
        if(emailExists(clientDtoRequest.getEmail())){
            throw new UsernameAlreadExistException("Actualmente ya existe un cliente con el email: " + clientDtoRequest.getEmail());
        }
        if(usernameExists(clientDtoRequest.getUsername())){
            throw new UsernameAlreadExistException("Actualmente ya existe un cliente con el usuario: " + clientDtoRequest.getUsername());
        }
        Client client = mapper.modelMapper().map(clientDtoRequest, Client.class);
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

    /***
     * Service to update a client by an unique id
     * @param id unique id to find a client and updated it
     * @param clientDtoRequest data to update the client
     * @return the client updated
     */
    @Override
    public ResponseSuccessDto<ClientDtoResponse> updateClient(Long id, ClientDtoRequest clientDtoRequest) {
        if (emailExists(clientDtoRequest.getEmail())) {
            throw new UsernameAlreadExistException("Actualmente ya existe un cliente con el email: " + clientDtoRequest.getEmail());
        }
        if (usernameExists(clientDtoRequest.getUsername())) {
            throw new UsernameAlreadExistException("Actualmente ya existe un cliente con el usuario: " + clientDtoRequest.getUsername());
        }
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            Client clientExisting = client.get();
            mapper.modelMapper().map(clientDtoRequest, clientExisting);
            Client clientUpdated = clientRepository.save(clientExisting);
            ClientDtoResponse clientDtoResponse = mapper.modelMapper().map(clientUpdated, ClientDtoResponse.class);
            return new ResponseSuccessDto<>(clientDtoResponse, 200, "El cliente fue actulizado con exito", false);
        }
        throw  new ClientNotFoundException("No se encontro el cliente para ser actualizado");
    }


    /***
     * Service to delete a client by an id
     * @param id unique id to delete a client
     * @return status
     */
    @Override
    public ResponseSuccessDto<ClientDtoResponse> deleteClient(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            clientRepository.deleteById(id);
            return new ResponseSuccessDto<>(null, 204, "El cliente fue eliminado con exito", false);
        }
        throw  new ClientNotFoundException("No se encontro el cliente para ser eliminado");
    }

    /***
     * Service to register a client and send an email for verify an account
     * @param clientDtoRequest the data to register a new client
     * @param siteURL the URL to add to the email to verify the client by an email
     * @return the client being created
     * @throws UnsupportedEncodingException exception
     * @throws MessagingException exception
     */
    @Override
    public ResponseSuccessDto<ClientDtoResponse> registerClient(ClientDtoRequest clientDtoRequest, String siteURL) throws UnsupportedEncodingException, MessagingException {
        if (emailExists(clientDtoRequest.getEmail())) {
            throw new UsernameAlreadExistException("Actualmente ya existe un cliente con el email: " + clientDtoRequest.getEmail());
        }
        if (usernameExists(clientDtoRequest.getUsername())) {
            throw new UsernameAlreadExistException("Actualmente ya existe un cliente con el usuario: " + clientDtoRequest.getUsername());
        }
        Client client = mapper.modelMapper().map(clientDtoRequest, Client.class);
        Role role = new Role();
        role.setRol(RoleEnum.CLIENT);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        client.setRoles(roles);
        client.setPassword(passwordEncoderConfig.passwordEncoder().encode(client.getPassword()));
        String randomCode = RandomString.make(64);
        client.setVerificationCode(randomCode);
        client.setEnabled(false);
        Client clientPersist = clientRepository.save(client);
        ClientDtoResponse clientDtoResponse = mapper.modelMapper().map(clientPersist, ClientDtoResponse.class);
        emailConfig.sendVerificationEmail(client, siteURL);
        return new ResponseSuccessDto<>(clientDtoResponse, 201, "El cliente fue creado con exito", false);
    }

    // TODO: DE AQUI EN ADELANTE VER SI SE PUEDE REFACTORIZAR

    /***
     * Service to get a client by the username
     * @param username unique username to find a client
     * @return the client found
     */
    @Override
    public ClientDtoResponse getClientByUsername(String username) {
        Optional<Client> client = clientRepository.findByUsername(username);
        if(client.isPresent()){
            return mapper.modelMapper().map(client.get(), ClientDtoResponse.class);
        }
        throw new ClientNotFoundException("No se encontro el cliente con el id especificado");
    }

    /***
     * Service to confirm an account of a client by a token sent to his/her email
     * @param verificationCode token that was saved with a registered client
     * @return the client being verified
     */
    @Override
    public ResponseSuccessDto<ClientDtoResponse> verifyRegisteredAccount(String verificationCode) {
        Optional<Client> client = clientRepository.findByVerificationCode(verificationCode);
        if(client.isPresent()){
            Client clientPersist = client.get();
            clientPersist.setVerificationCode(null);
            clientPersist.setEnabled(true);
            clientRepository.save(clientPersist);
            ClientDtoResponse clientDtoResponse = mapper.modelMapper().map(clientPersist, ClientDtoResponse.class);
            return new ResponseSuccessDto<>(clientDtoResponse, 201, "El cliente fue creado con exito", false);
        }
        throw new ClientNotFoundException("La verificacion de la cuenta no tuvo exito, el codigo no existe");
    }

    /***
     * Method to verify if an email already exists in the DB
     * @param email to search in the database
     * @return true or false
     */
    private boolean emailExists(String email){
        return clientRepository.findByEmail(email).isPresent();
    }

    /***
     * Method to verify if a username already exists in the DB
     * @param username to search in the database
     * @return true or false
     */
    private boolean usernameExists(String username){
        return clientRepository.findByUsername(username).isPresent();
    }
}

package com.example.backend.service;

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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService implements IClientService{

    // Inject dependencies
    private final IClientRepository clientRepository;
    private final ModelMapperConfig mapper;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final JavaMailSender mailSender;

    // Constructor for dependencies
    public ClientService(IClientRepository clientRepository, ModelMapperConfig mapper, PasswordEncoderConfig passwordEncoderConfig, JavaMailSender mailSender){
        this.clientRepository = clientRepository;
        this.mapper = mapper;
        this.passwordEncoderConfig = passwordEncoderConfig;
        this.mailSender = mailSender;
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
     * @param clientCreateDtoRequest data to create a client
     * @return the client created
     */
    @Override
    public ResponseSuccessDto<ClientDtoResponse> createClient(ClientDtoRequest clientCreateDtoRequest) {
        if(emailExists(clientCreateDtoRequest.getEmail())){
            throw new UsernameAlreadExistException("Actualmente ya existe un cliente con el email: " + clientCreateDtoRequest.getEmail());
        }
        if(usernameExists(clientCreateDtoRequest.getUsername())){
            throw new UsernameAlreadExistException("Actualmente ya existe un cliente con el usuario: " + clientCreateDtoRequest.getUsername());
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

    /***
     * Service to update a client by an unique id
     * @param id unique id to find a client and updated it
     * @param clientCreateDtoRequest data to update the client
     * @return the client updated
     */
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
            mapper.modelMapper().map(client.get(), ClientDtoResponse.class);
            return new ResponseSuccessDto<>(null, 204, "El cliente fue eliminado con exito", false);
        }
        throw  new ClientNotFoundException("No se encontro el cliente para ser eliminado");
    }

    /***
     * Service to register a client and send an email for verify an account
     * @param clientCreateDtoRequest the data to register a new client
     * @param siteURL the URL to add to the email to verify the client by an email
     * @return the client being created
     * @throws UnsupportedEncodingException exception
     * @throws MessagingException exception
     */
    @Override
    public ResponseSuccessDto<ClientDtoResponse> registerClient(ClientDtoRequest clientCreateDtoRequest, String siteURL) throws UnsupportedEncodingException, MessagingException {
        if(emailExists(clientCreateDtoRequest.getEmail())){
            throw new UsernameAlreadExistException("Actualmente ya existe un cliente con el email: " + clientCreateDtoRequest.getEmail());
        }
        if(usernameExists(clientCreateDtoRequest.getUsername())){
            throw new UsernameAlreadExistException("Actualmente ya existe un cliente con el usuario: " + clientCreateDtoRequest.getUsername());
        }
        Client client = mapper.modelMapper().map(clientCreateDtoRequest, Client.class);
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
        sendVerificationEmail(client, siteURL);
        return new ResponseSuccessDto<>(clientDtoResponse, 201, "El cliente fue creado con exito", false);
    }

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
     * Method to send an email and a link to verify the registered account of a client
     * @param client data of a client that was registered
     * @param siteURL URL to create a link to send to the email of a client
     * @throws MessagingException exception
     * @throws UnsupportedEncodingException exception
     */
    private void sendVerificationEmail(Client client, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = client.getEmail();
        String fromAddress = "elderarias2015@gmail.com";
        String senderName = "losprimos";
        String subject = "Por favor, verifiquese para terminar con la registracion.";
        String content = "Querido [[name]],<br>"
                + "Haga click en el enlace de abajo para verificar su cuenta:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Muchas gracias,<br>"
                + "losprimos.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", client.getFirstName());
        String verifyURL = siteURL + "/api/v1/clients/verifyRegisteredAccount?code=" + client.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

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

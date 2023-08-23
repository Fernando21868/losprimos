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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final IClientRepository clientRepository;
    private final ModelMapperConfig mapper;
    private final PasswordEncoderConfig passwordEncoderConfig;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

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
            throw new UsernameAlreadExistException("Actualmente ya existe un cliente con el email: " + clientCreateDtoRequest.getEmail());
        }
        if(usernameExists(clientCreateDtoRequest.getUsername())){
            throw new UsernameAlreadExistException("Actualmente ya existe un cliente con el username: " + clientCreateDtoRequest.getUsername());
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

    @Override
    public ResponseSuccessDto<ClientDtoResponse> registerClient(ClientDtoRequest clientCreateDtoRequest, String siteURL) throws UnsupportedEncodingException, MessagingException {
        if(emailExists(clientCreateDtoRequest.getEmail())){
            throw new UsernameAlreadExistException("Actualmente ya existe un cliente con el email: " + clientCreateDtoRequest.getEmail());
        }
        if(usernameExists(clientCreateDtoRequest.getUsername())){
            throw new UsernameAlreadExistException("Actualmente ya existe un cliente con el username: " + clientCreateDtoRequest.getUsername());
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

    @Override
    public ClientDtoResponse getClientByUsername(String username) {
        Optional<Client> client = clientRepository.findByUsername(username);
        if(client.isPresent()){
            return mapper.modelMapper().map(client.get(), ClientDtoResponse.class);
        }
        throw new ClientNotFoundException("No se encontro el cliente con el id especificado");
    }

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

    private void sendVerificationEmail(Client user, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
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

        content = content.replace("[[name]]", user.getFirstName());
        String verifyURL = siteURL + "/api/v1/clients/verifyRegisteredAccount?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }

    private boolean emailExists(String email){
        return clientRepository.findByEmail(email) != null;
    }

    private boolean usernameExists(String username){
        return clientRepository.findByUsername(username).isPresent();
    }
}

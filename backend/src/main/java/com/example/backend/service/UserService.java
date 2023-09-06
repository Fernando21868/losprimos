package com.example.backend.service;

import com.example.backend.config.EmailConfig;
import com.example.backend.config.ModelMapperConfig;
import com.example.backend.config.PasswordEncoderConfig;
import com.example.backend.dto.request.UserDtoRequest;
import com.example.backend.dto.response.ResponseSuccessDto;
import com.example.backend.dto.response.UserDtoResponse;
import com.example.backend.exceptions.*;
import com.example.backend.model.Role;
import com.example.backend.model.RoleEnum;
import com.example.backend.model.User;
import com.example.backend.repository.IUserRepository;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.Conditions;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Service for clients - CRUD operations
public abstract class UserService<Response extends UserDtoResponse, Request extends UserDtoRequest, Entity extends User, Repository extends IUserRepository<Entity>, NotFoundException extends UserNotFoundException> implements IUserService<Response, Request> {

    // Inject dependencies
    // TODO: VER SI QUEDAN COMO PROTECTED O PRIVATE
    protected final Repository userRepository;
    protected final ModelMapperConfig mapper;
    protected final PasswordEncoderConfig passwordEncoderConfig;
    protected final EmailConfig emailConfig;
    private final Class<Response> response;
    private final Class<Entity> entity;



    // Constructor for dependencies
    public UserService(Repository userRepository, ModelMapperConfig mapper, PasswordEncoderConfig passwordEncoderConfig, EmailConfig emailConfig) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoderConfig = passwordEncoderConfig;
        this.emailConfig = emailConfig;
        this.response = getResponseClass();
        this.entity = getEntityClass();
    }

    /***
     * Abstracts methods to obtain the type of class of the entities
     * @return nothing
     */
    protected abstract Class<Response> getResponseClass();
    protected abstract Class<Entity> getEntityClass();
    protected abstract NotFoundException createNotFoundException(String message);

    /***
     * Service con get all the users of a specific type
     * @return List of all users created of a specific type
     */
    @Override
    public ResponseSuccessDto<List<Response>> getAll() {
        List<Entity> users = userRepository.findAll();
        List<Response> userDtoResponses = users.stream().map(user -> mapper.modelMapper().map(user, response)).collect(Collectors.toList());
        return new ResponseSuccessDto<>(userDtoResponses, 200, "Los usuarios fueron encontrados con exito", false);
    }

    /***
     * Service to find a user of a specific type by an id
     * @param id unique parameter to find the a user
     * @return the user by the specific id
     */
    @Override
    public ResponseSuccessDto<Response> getById(Long id) {
        Optional<Entity> user = userRepository.findById(id);
        if(user.isPresent()){
            Response userDtoResponse = mapper.modelMapper().map(user.get(), response);
            return new ResponseSuccessDto<>(userDtoResponse, 200, "El usuario con el id: " + id + " fue encontrado", false);
        }
        throw createNotFoundException("No se encontró al cliente con el ID: " + id);
    }

    /***
     * Service to create a user of a specific type
     * @param userDtoRequest data to create a client
     * @return the client created
     */
    @Override
    public ResponseSuccessDto<Response> create(Request userDtoRequest) {
        if(emailExists(userDtoRequest.getEmail())){
            throw new EmailAlreadExistException("Actualmente ya existe un cliente con el email: " + userDtoRequest.getEmail());
        }
        if(usernameExists(userDtoRequest.getUsername())){
            throw new UsernameAlreadExistException("Actualmente ya existe un cliente con el usuario: " + userDtoRequest.getUsername());
        }
        Entity user = mapper.modelMapper().map(userDtoRequest, entity);
        Role role = new Role();
        role.setRol(RoleEnum.CLIENT);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoderConfig.passwordEncoder().encode(user.getPassword()));
        Entity userPersist = userRepository.save(user);
        Response userDtoResponse = mapper.modelMapper().map(userPersist, response);
        return new ResponseSuccessDto<>(userDtoResponse, 201, "El usuario fue creado con exito", false);
    }

    /***
     * Service to register a user of a specific type
     * @param userDtoRequest data to create a client
     * @return the client created
     */
    @Override
    public ResponseSuccessDto<Response> register(Request userDtoRequest, String siteURL) throws UnsupportedEncodingException, MessagingException{
        if(emailExists(userDtoRequest.getEmail())){
            throw new EmailAlreadExistException("Actualmente ya existe un cliente con el email: " + userDtoRequest.getEmail());
        }
        if(usernameExists(userDtoRequest.getUsername())){
            throw new UsernameAlreadExistException("Actualmente ya existe un cliente con el usuario: " + userDtoRequest.getUsername());
        }
        Entity user = mapper.modelMapper().map(userDtoRequest, entity);
        Role role = new Role();
        role.setRol(RoleEnum.CLIENT);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoderConfig.passwordEncoder().encode(user.getPassword()));
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);
        Entity userPersist = userRepository.save(user);
        Response userDtoResponse = mapper.modelMapper().map(userPersist, response);
        emailConfig.sendVerificationEmail(user, siteURL);
        return new ResponseSuccessDto<>(userDtoResponse, 201, "El usuario fue creado con exito", false);
    }

    /***
     * Service to update a user of a specific type by a unique id
     * @param id unique id to find a user and updated it
     * @param userDtoRequest data to update the user
     * @return the user updated
     */
    @Override
    public ResponseSuccessDto<Response> update(Long id, Request userDtoRequest) {
        if (emailExists(userDtoRequest.getEmail())) {
            throw new EmailAlreadExistException("Actualmente ya existe un usuario con el email: " + userDtoRequest.getEmail());
        }
        if (usernameExists(userDtoRequest.getUsername())) {
            throw new UsernameAlreadExistException("Actualmente ya existe un usuario con el usuario: " + userDtoRequest.getUsername());
        }
        Optional<Entity> user = userRepository.findById(id);
        if(user.isPresent()){
            Entity userExisting = user.get();
            mapper.modelMapper().getConfiguration().setPropertyCondition(Conditions.isNotNull());
            mapper.modelMapper().map(userDtoRequest, userExisting);
            Entity userUpdated = userRepository.save(userExisting);
            Response userDtoResponse = mapper.modelMapper().map(userUpdated, response);
            return new ResponseSuccessDto<>(userDtoResponse, 200, "El usuario fue actulizado con exito", false);
        }
        throw createNotFoundException("No se encontro el usuario para ser actualizado");
    }

    /***
     * Service to delete a user of a specific type by an id
     * @param id unique id to delete a user
     * @return status
     */
    @Override
    public ResponseSuccessDto<Response> delete(Long id) {
        Optional<Entity> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.deleteById(id);
            return new ResponseSuccessDto<>(null, 204, "El usuario fue eliminado con exito", false);
        }
        throw createNotFoundException("No se encontro el usuario para ser eliminado");
    }

    /***
     * Service to get a user of a specific type by the username
     * @param username unique username to find a user
     * @return the user found
     */
    @Override
    public Response getByUsername(String username) {
        Optional<Entity> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return mapper.modelMapper().map(user.get(), response);
        }
        throw createNotFoundException("No se encontro el usuario con el id especificado");
    }

    /***
     * Service to confirm an account of a user of a specific type by a token sent to his/her email
     * @param verificationCode token that was saved with a registered user
     * @return the user being verified
     */
    @Override
    public ResponseSuccessDto<Response> verifyRegisteredAccount(String verificationCode) {
        Optional<Entity> user = userRepository.findByVerificationCode(verificationCode);
        if(user.isPresent()){
            Entity userPersist = user.get();
            userPersist.setVerificationCode(null);
            userPersist.setEnabled(true);
            userRepository.save(userPersist);
            Response userDtoResponse = mapper.modelMapper().map(userPersist, response);
            return new ResponseSuccessDto<>(userDtoResponse, 201, "El usuario fue creado con exito", false);
        }
        throw createNotFoundException("La verificacion de la cuenta no tuvo exito, el codigo no existe");
    }

    /***
     * Method to verify if an email already exists in the DB
     * @param email to search in the database
     * @return true or false
     */
    protected boolean emailExists(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    /***
     * Method to verify if a username already exists in the DB
     * @param username to search in the database
     * @return true or false
     */
    protected boolean usernameExists(String username){
        return userRepository.findByUsername(username).isPresent();
    }

}
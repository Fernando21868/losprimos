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
import com.example.backend.model.User;;
import com.example.backend.repository.IRoleRepository;
import com.example.backend.repository.IUserRepository;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.utility.RandomString;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


// Service for users - CRUD operations
@Setter
@Getter
public abstract class UserService<
            ResponseDTO extends UserDtoResponse,
            RequestDTO extends UserDtoRequest,
            Model extends User,
            Repository extends IUserRepository<Model>,
            NotFoundException extends UserNotFoundException>
        extends PersonService<
            ResponseDTO,
            RequestDTO,
            Model,
            Repository,
            NotFoundException>
        implements IUserService<
            ResponseDTO,
            RequestDTO>{

    // Some DI
    private final IRoleRepository roleRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final EmailConfig emailConfig;

    // Constructor
    public UserService(Repository userRepository, ModelMapperConfig mapper, IRoleRepository roleRepository, PasswordEncoderConfig passwordEncoderConfig, EmailConfig emailConfig) {
        super(userRepository, mapper);
        this.roleRepository = roleRepository;
        this.passwordEncoderConfig = passwordEncoderConfig;
        this.emailConfig = emailConfig;
    }

    /***
     * Service to confirm an account of a user of a specific type by a token sent to his/her email
     * @param verificationCode token that was saved with a registered user
     * @return the user being verified
     */
    @Override
    public ResponseSuccessDto<ResponseDTO> verifyRegisteredAccount(String verificationCode) {
        Optional<Model> user = super.getPersonRepository().findByVerificationCode(verificationCode);
        if(user.isPresent()){
            Model userPersist = user.get();
            userPersist.setVerificationCode(null);
            userPersist.setEnabled(true);
            super.getPersonRepository().save(userPersist);
            ResponseDTO userDtoResponse = super.getMapper().modelMapper().map(userPersist, super.getResponse());
            return new ResponseSuccessDto<>(userDtoResponse, 201, "El usuario fue creado con exito", false);
        }
        throw createNotFoundException("La verificacion de la cuenta no tuvo exito, el codigo no existe");
    }

    /***
     * Service to get a user of a specific type by the username
     * @param username unique username to find a user
     * @return the user found
     */
    @Override
    public ResponseDTO getByUsername(String username) {
        Optional<Model> user = super.getPersonRepository().findByUsername(username);
        if(user.isPresent()){
            return super.getMapper().modelMapper().map(user.get(), super.getResponse());
        }
        throw createNotFoundException("No se encontro el usuario con el id especificado");
    }

    /***
     * Method to verify if a username already exists in the DB
     * @param username to search in the database
     * @return true or false
     */
    protected boolean usernameExists(String username){
        return super.getPersonRepository().findByUsername(username).isPresent();
    }

}

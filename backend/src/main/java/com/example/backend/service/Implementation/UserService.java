package com.example.backend.service.Implementation;

import com.example.backend.config.EmailConfig;
import com.example.backend.config.ModelMapperConfig;
import com.example.backend.config.PasswordEncoderConfig;
import com.example.backend.dto.request.UserDtoRequest;
import com.example.backend.dto.response.ResponseSuccessDto;
import com.example.backend.dto.response.UserDtoResponse;
import com.example.backend.exceptions.*;

import com.example.backend.model.User;
import com.example.backend.repository.IRoleRepository;
import com.example.backend.repository.IUserRepository;
import com.example.backend.service.Interface.IUserService;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import java.util.Optional;


/**
 * Service for users - CRUD operations
 * @param <ResponseDTO>
 * @param <RequestDTO>
 * @param <Model>
 * @param <Repository>
 * @param <NotFoundException>
 */
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
                    RequestDTO> {

    /**
     * Some di and variables
     */
    private static Logger logger = Logger.getLogger(UserService.class);
    private final IRoleRepository roleRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final EmailConfig emailConfig;

    /**
     * Constructor and dependencies
     * @param userRepository repository
     * @param mapper mapper of objects
     * @param roleRepository repository of roles
     * @param passwordEncoderConfig password injection
     * @param emailConfig email injection
     */
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
        try {
            logger.info("Service to confirm an account by a token");
            Optional<Model> user = super.getPersonRepository().findByVerificationCodeAndVerificationCodeIsNotNull(verificationCode);
            if(user.isEmpty()){
                logger.error("The token: " + verificationCode + " doesn't exist");
                throw new VerificationCodeNotFoundException("La verificacion de la cuenta no tuvo exito, el codigo no existe");
            }
            Model userPersist = user.get();
            userPersist.setVerificationCode(null);
            userPersist.setEnabled(true);
            super.getPersonRepository().save(userPersist);
            ResponseDTO userDtoResponse = super.getMapper().modelMapper().map(userPersist, super.getResponse());
            return new ResponseSuccessDto<>(userDtoResponse, HttpStatus.CREATED.value(), "El usuario fue creado con exito",
                    false);
        } catch (Exception exception) {
            logger.error("Something wrong occur while trying to verify the account");
            throw new InternalException(exception.getMessage());
        }
    }

    // TODO: IF SOME REQUIREMENT CHANGE ABOUT ADDING A NEW TYPE OF USER WE CAN CREATE IT HERE AND USE usernameExists
    //  () TO VERIFY IF THE USERNAME ALREADY EXISTS

    /***
     * Method to verify if a username already exists in the DB
     * @param username to search in the database
     * @return true or false
     */
    protected boolean usernameExists(String username){
        return super.getPersonRepository().findByUsernameAndUsernameIsNotNull(username).isPresent();
    }

}

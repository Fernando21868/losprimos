package com.example.backend.service.Implementation;

import com.example.backend.config.ModelMapperConfig;
import com.example.backend.dto.request.PersonDTORequest;
import com.example.backend.dto.response.PersonDTOResponse;
import com.example.backend.dto.response.ResponseSuccessDto;
import com.example.backend.exceptions.DniAlreadyExistException;
import com.example.backend.exceptions.EmailAlreadExistException;
import com.example.backend.exceptions.InternalException;
import com.example.backend.exceptions.PersonNotFoundException;
import com.example.backend.model.Person;
import com.example.backend.repository.IPersonRepository;
import com.example.backend.service.Interface.IPersonService;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.modelmapper.Conditions;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service of Persons
 * @param <ResponseDTO>
 * @param <RequestDTO>
 * @param <Model>
 * @param <Repository>
 * @param <NotFoundException>
 */
@Setter
@Getter
public abstract class PersonService<ResponseDTO extends PersonDTOResponse,
        RequestDTO extends PersonDTORequest,
        Model extends Person,
        Repository extends IPersonRepository<Model>,
        NotFoundException extends PersonNotFoundException>
        implements IPersonService<
        ResponseDTO,
        RequestDTO> {

    /**
     * Dependencies
     */
    // TODO: VER SI QUEDAN COMO PROTECTED O PRIVATE
    private static Logger logger = Logger.getLogger(PersonService.class);
    private final Repository personRepository;
    private final ModelMapperConfig mapper;
    private final Class<ResponseDTO> response;
    private final Class<Model> model;


    /**
     * Constructor for dependencies
     * @param personRepository repository of users
     * @param mapper mapper to use in objects
     */
    public PersonService(Repository personRepository, ModelMapperConfig mapper) {
        this.personRepository = personRepository;
        this.mapper = mapper;
        this.response = getResponseClass();
        this.model = getEntityClass();
    }

    /***
     * Abstracts methods to obtain the type of class of the entities
     * @return nothing
     */
    protected abstract Class<ResponseDTO> getResponseClass();
    protected abstract Class<Model> getEntityClass();
    protected abstract NotFoundException notFoundException();
    protected abstract String getAllMessage();

    /***
     * Service con get all the persons of a specific type
     * @return List of all persons created of a specific type
     */
    @Override
    public ResponseSuccessDto<List<ResponseDTO>> getAll() {
        logger.info("Service to find all persons or users or employees or patients");
        try {
            List<Model> persons = personRepository.findAll();
            List<ResponseDTO> personsDtoResponses = persons.stream().map(person -> mapper.modelMapper().map(person, response)).collect(Collectors.toList());
            return new ResponseSuccessDto<>(personsDtoResponses, HttpStatus.OK.value(), getAllMessage(), false);
        } catch (Exception exception) {
            logger.error("Something wrong occur while trying to find all persons or users or employees or patients");
            throw new InternalException(exception.getMessage());
        }
    }

    /***
     * Service to find a person of a specific type by an id
     * @param id unique parameter to find the a person
     * @return the person by the specific id
     */
    @Override
    public ResponseSuccessDto<ResponseDTO> getById(Long id) {
        logger.info("Service to find a person by id");
        try {
            Optional<Model> person = personRepository.findById(id);
            if(person.isEmpty()){
                logger.error("The person with the id: " + id + " doesn't exist");
                throw notFoundException();
            }
            ResponseDTO personDtoResponse = mapper.modelMapper().map(person.get(), response);
            return new ResponseSuccessDto<>(personDtoResponse, HttpStatus.OK.value(),
                    "La persona con el ID: " + id +
                    " fue " +
                    "encontrada",
                    false);
        } catch (Exception exception) {
            logger.error("Something wrong occur while trying to find a person or user or employee or patient");
            throw new InternalException(exception.getMessage());
        }
    }

    /***
     * Service to create a person of a specific type
     * @param personDtoRequest data to create a person
     * @return the person created
     */
    @Override
    public ResponseSuccessDto<ResponseDTO> create(RequestDTO personDtoRequest) {
        logger.info("Service to create a person");
        try {
            verifyExistenceOfEmail(personDtoRequest);
            verifyExistenceOfDni(personDtoRequest);
            Model person = mapper.modelMapper().map(personDtoRequest, model);
            if(person.getPersons() != null && !person.getPersons().isEmpty()){
                Set<Person> referents = person.getPersons();
                referents.forEach(singleReferent -> {
                    singleReferent.setPerson(person);
                });
            }
            Model personPersist = personRepository.save(person);
            ResponseDTO personDtoResponse = mapper.modelMapper().map(personPersist, response);
            return new ResponseSuccessDto<>(personDtoResponse, HttpStatus.CREATED.value(), "La persona fue creada con exito",
                    false);

        } catch (Exception exception) {
            logger.error("Something wrong occur while trying to create a patient");
            throw new InternalException(exception.getMessage());
        }
    }

    /**
     * Verify if an email already exists in the database
     * @param personDtoRequest data to create a person
     */
    public void verifyExistenceOfEmail(RequestDTO personDtoRequest){
        if(emailExists(personDtoRequest.getEmail())){
            logger.error("Already exist a person with the email: " + personDtoRequest.getEmail());
            throw new EmailAlreadExistException("Actualmente ya existe una persona con el email: " + personDtoRequest.getEmail());
        }
        if(personDtoRequest.getPersons() !=null && !personDtoRequest.getPersons().isEmpty()){
            for (PersonDTORequest person: personDtoRequest.getPersons()){
                if(emailExists(person.getEmail())){
                    logger.error("Already exist a person with the email: " + person.getEmail());
                    throw new EmailAlreadExistException("Actualmente ya existe una persona con el email: " + person.getEmail());
                }
            }
        }
    }

    /**
     * Verify if a dni already exists in the database
     * @param personDtoRequest data to create a person
     */
    public void verifyExistenceOfDni(RequestDTO personDtoRequest){
        if(dniExists(personDtoRequest.getDni())){
            logger.error("Already exist a person with the dni: " + personDtoRequest.getDni());
            throw new DniAlreadyExistException("Actualmente ya existe una persona con el dni: " + personDtoRequest.getDni());
        }
        if(personDtoRequest.getPersons() !=null && !personDtoRequest.getPersons().isEmpty()){
            for (PersonDTORequest person: personDtoRequest.getPersons()){
                if(dniExists(person.getDni())){
                    logger.error("Already exist a person with the dni: " + person.getDni());
                    throw new EmailAlreadExistException("Actualmente ya existe una persona con el dni: " + person.getDni());
                }
            }
        }
    }

    /***
     * Service to update a person of a specific type by a unique id
     * @param personDtoRequest data to update the person
     * @return the person updated
     */
    @Override
    public ResponseSuccessDto<ResponseDTO> update(RequestDTO personDtoRequest) {
        logger.info("Service to update a person");
        // TODO: VERIFICAR COMO SE VAN A ACTUALIZAR LOS PACIENTES Y USUARIOS O EMPLEADOS
        try {
            verifyExistenceOfEmail(personDtoRequest);
            verifyExistenceOfDni(personDtoRequest);
            Optional<Model> person = personRepository.findById(personDtoRequest.getId());
            if(person.isEmpty()){
                logger.error("The person with the id: " + personDtoRequest.getId() + " doesn't exist");
                throw notFoundException();
            }
            Model personExisting = person.get();
            mapper.modelMapper().getConfiguration().setPropertyCondition(Conditions.isNotNull());
            mapper.modelMapper().map(personDtoRequest, personExisting);
            Model personUpdated = personRepository.save(personExisting);
            ResponseDTO personDtoResponse = mapper.modelMapper().map(personUpdated, response);
            return new ResponseSuccessDto<>(personDtoResponse, HttpStatus.OK.value(), "La persona fue actulizada con exito", false);
        } catch (Exception exception) {
            logger.error("Something wrong occur while trying to update a person");
            throw new InternalException(exception.getMessage());
        }
    }

    /***
     * Service to delete a person of a specific type by an id
     * @param id unique id to delete a person
     * @return status
     */
    @Override
    public ResponseSuccessDto<ResponseDTO> delete(Long id) {
        logger.info("Service to delete a person");
        try {
            Optional<Model> person = personRepository.findById(id);
            if(person.isEmpty()){
                logger.error("The person with the id: " + id + " doesn't exist");
                throw notFoundException();
            }
            personRepository.deleteById(id);
            return new ResponseSuccessDto<>(null, HttpStatus.ACCEPTED.value(), "La persona fue eliminada con exito", false);
        } catch (Exception exception) {
            logger.error("Something wrong occur while trying to delete a person");
            throw new InternalException(exception.getMessage());
        }
    }

    /***
     * Method to verify if an email already exists in the DB
     * @param email to search in the database
     * @return true or false
     */
    protected boolean emailExists(String email){
        return personRepository.findByEmailAndEmailIsNotNull(email).isPresent();
    }

    /***
     * Method to verify if a dni already exists in the DB
     * @param dni to search in the database
     * @return true or false
     */
    protected boolean dniExists(String dni){
        return personRepository.findByDniAndDniIsNotNull(dni).isPresent();
    }

}
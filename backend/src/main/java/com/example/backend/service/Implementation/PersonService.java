package com.example.backend.service.Implementation;

import com.example.backend.config.ModelMapperConfig;
import com.example.backend.dto.request.PersonDTORequest;
import com.example.backend.dto.response.PersonDTOResponse;
import com.example.backend.dto.response.ResponseSuccessDto;
import com.example.backend.exceptions.EmailAlreadExistException;
import com.example.backend.exceptions.PersonNotFoundException;
import com.example.backend.model.Person;
import com.example.backend.repository.IPersonRepository;
import com.example.backend.service.Interface.IPersonService;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.Conditions;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // Inject dependencies
    // TODO: VER SI QUEDAN COMO PROTECTED O PRIVATE
    private final Repository personRepository;
    private final ModelMapperConfig mapper;
    private final Class<ResponseDTO> response;
    private final Class<Model> model;


    // Constructor for dependencies
    public PersonService(Repository userRepository, ModelMapperConfig mapper) {
        this.personRepository = userRepository;
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
    protected abstract NotFoundException createNotFoundException(String message);
    protected abstract String getAllMessage();

    /***
     * Service con get all the persons of a specific type
     * @return List of all persons created of a specific type
     */
    @Override
    public ResponseSuccessDto<List<ResponseDTO>> getAll() {
        List<Model> persons = personRepository.findAll();
        List<ResponseDTO> personsDtoResponses = persons.stream().map(person -> mapper.modelMapper().map(person, response)).collect(Collectors.toList());
        return new ResponseSuccessDto<>(personsDtoResponses, 200, getAllMessage(), false);
    }

    /***
     * Service to find a person of a specific type by an id
     * @param id unique parameter to find the a person
     * @return the person by the specific id
     */
    @Override
    public ResponseSuccessDto<ResponseDTO> getById(Long id) {
        Optional<Model> person = personRepository.findById(id);
        if(person.isPresent()){
            ResponseDTO personDtoResponse = mapper.modelMapper().map(person.get(), response);
            return new ResponseSuccessDto<>(personDtoResponse, 200, "La persona con el ID: " + id + " fue encontrada", false);
        }
        throw createNotFoundException("No se encontró al la persona con el ID: " + id);
    }

    /***
     * Service to create a person of a specific type
     * @param personDtoRequest data to create a person
     * @return the person created
     */
    @Override
    public ResponseSuccessDto<ResponseDTO> create(RequestDTO personDtoRequest) {
        if(emailExists(personDtoRequest.getEmail())){
            throw new EmailAlreadExistException("Actualmente ya existe una persona con el email: " + personDtoRequest.getEmail());
        }
        Model person = mapper.modelMapper().map(personDtoRequest, model);
        Model personPersist = personRepository.save(person);
        ResponseDTO personDtoResponse = mapper.modelMapper().map(personPersist, response);
        return new ResponseSuccessDto<>(personDtoResponse, 201, "La persona fue creada con exito", false);
    }

    /***
     * Service to update a person of a specific type by a unique id
     * @param id unique id to find a person and updated it
     * @param personDtoRequest data to update the person
     * @return the person updated
     */
    @Override
    public ResponseSuccessDto<ResponseDTO> update(Long id, RequestDTO personDtoRequest) {
        if (emailExists(personDtoRequest.getEmail())) {
            throw new EmailAlreadExistException("Actualmente ya existe una persona con el email: " + personDtoRequest.getEmail());
        }
        Optional<Model> person = personRepository.findById(id);
        if(person.isPresent()){
            Model personExisting = person.get();
            mapper.modelMapper().getConfiguration().setPropertyCondition(Conditions.isNotNull());
            mapper.modelMapper().map(personDtoRequest, personExisting);
            Model personUpdated = personRepository.save(personExisting);
            ResponseDTO personDtoResponse = mapper.modelMapper().map(personUpdated, response);
            return new ResponseSuccessDto<>(personDtoResponse, 200, "La persona fue actulizada con exito", false);
        }
        throw createNotFoundException("No se encontro a la persona para ser actualizada");
    }

    /***
     * Service to delete a person of a specific type by an id
     * @param id unique id to delete a person
     * @return status
     */
    @Override
    public ResponseSuccessDto<ResponseDTO> delete(Long id) {
        Optional<Model> person = personRepository.findById(id);
        if(person.isPresent()){
            personRepository.deleteById(id);
            return new ResponseSuccessDto<>(null, 204, "La persona fue eliminada con exito", false);
        }
        throw createNotFoundException("No se encontro la persona para ser eliminada");
    }

    /***
     * Method to verify if an email already exists in the DB
     * @param email to search in the database
     * @return true or false
     */
    protected boolean emailExists(String email){
        return personRepository.findByEmail(email).isPresent();
    }

}
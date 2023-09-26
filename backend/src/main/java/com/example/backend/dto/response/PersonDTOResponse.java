package com.example.backend.dto.response;

import com.example.backend.dto.request.PersonDTORequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@SuperBuilder
public class PersonDTOResponse {

    // not nullables
    private String firstName;
    private String lastName;
    private Integer dni;
    private Date birthday;
    private String nationality;
    private String age;
    private String sex;

    // nullables
    private String phoneNumber;
    private String email;
    private String socialWork;

    private AddressDtoResponse addressDtoResponse;
    private Set<PersonDTOResponse> personDTOResponses;

}

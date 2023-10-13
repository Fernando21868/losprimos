package com.example.backend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Response dto for person
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class PersonDTOResponse implements Serializable {

    /**
     * Id
     */
    private Long id;

    /**
     * Not nullables
     */
    private String firstName;
    private String lastName;
    private Integer dni;
    private Date birthday;
    private String nationality;
    private String age;
    private String sex;

    /**
     * Nullables
     */
    private String phoneNumber;
    private String email;
    private String socialWork;

    /**
     * Relations
     */
    private AddressDtoResponse address;
    private Set<PersonDTOResponse> persons;

}

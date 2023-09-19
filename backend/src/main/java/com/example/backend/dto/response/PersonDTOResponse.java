package com.example.backend.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@NoArgsConstructor
@SuperBuilder
public class PersonDTOResponse {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date birthday;
    private Integer dni;

    private AddressDtoResponse address;

}

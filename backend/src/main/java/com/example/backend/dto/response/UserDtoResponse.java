package com.example.backend.dto.response;

import com.example.backend.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
public class UserDtoResponse {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String birthday;
    private String username;
    private List<Role> roles;

    private AddressDtoResponse address;
}

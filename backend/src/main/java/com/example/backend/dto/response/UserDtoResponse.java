package com.example.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoResponse {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String birthday;
    private String address;
    private String username;

}

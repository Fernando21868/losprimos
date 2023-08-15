package com.example.backend.dto.response;

import com.example.backend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    private List<Role> roles;
}

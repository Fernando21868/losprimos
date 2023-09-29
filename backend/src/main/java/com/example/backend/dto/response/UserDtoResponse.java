package com.example.backend.dto.response;

import com.example.backend.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserDtoResponse extends PersonDTOResponse{

    private String username;
    private List<Role> roles;

}

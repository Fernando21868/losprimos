package com.example.backend.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticateDtoRequest {

    private String username;
    private String password;

}

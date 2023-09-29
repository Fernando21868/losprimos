package com.example.backend.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticateDtoResponse {

    private String username;
    private String token;

}

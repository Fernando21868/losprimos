package com.example.backend.dto.response;

import lombok.*;

import java.io.Serializable;

/**
 * Response dto for authentication user
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticateDtoResponse implements Serializable {

    /**
     * Not nullables
     */
    private String username;
    private String token;

}

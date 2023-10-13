package com.example.backend.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Request dto for authentication user
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticateDtoRequest implements Serializable {

    /**
     * Not nullables
     */
    @NotBlank(message = "Por favor ingrese un nombre de usuario")
    @Size(max = 30, message = "El nombre de usuario no debe tener mas de 30 caracteres")
    private String username;
    @NotBlank(message = "Por favor ingrese una contraseña")
    @Size(max = 30, message = "La contraseña no debe tener mas de 30 caracteres")
    private String password;

}

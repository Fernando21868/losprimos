package com.example.backend.dto.request;

import com.example.backend.annotation.PasswordValueMatch;
import com.example.backend.model.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * Request dto for user
 */
@PasswordValueMatch.List({@PasswordValueMatch(field = "password", fieldMatch = "confirmPassword", message = "Las contraseñas no coinciden")})
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserDtoRequest extends PersonDTORequest implements Serializable {

    /**
     * Id
     */
    private Long id;

    /**
     * Not nullables
     */
    @NotBlank(message = "Por favor introduzca nombre de usuario")
    @Size(max = 30, message = "El nombre de usuario no debe contener mas de 30 caracteres")
    private String username;
    @NotBlank(message = "Por favor introduzca una contraseña")
    @Size(max = 30, message = "La contraseña no debe contener mas de 30 caracteres")
    //@ValidPassword
    private String password;
    @NotBlank(message = "Por favor introduzca una contraseña")
    @Size(max = 30, message = "La contraseña no debe contener mas de 30 caracteres")
    //@ValidPassword
    private String confirmPassword;

    /**
     * Nullables
     */
    @Size(min = 1, max = 3)
    private List<Role> roles;

}

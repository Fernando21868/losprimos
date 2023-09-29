package com.example.backend.dto.request;

import com.example.backend.annotation.PasswordValueMatch;
import com.example.backend.annotation.ValidEmail;
import com.example.backend.annotation.ValidPassword;
import com.example.backend.model.Address;
import com.example.backend.model.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@PasswordValueMatch.List({@PasswordValueMatch(field = "password", fieldMatch = "confirmPassword", message = "Las contraseñas no coinciden")})
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserDtoRequest extends PersonDTORequest{


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

    @Size(min = 1, max = 3)
    private List<Role> roles;

}

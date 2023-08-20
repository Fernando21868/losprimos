package com.example.backend.dto.request;

import com.example.backend.annotation.PasswordValueMatch;
import com.example.backend.annotation.ValidEmail;
import com.example.backend.annotation.ValidPassword;
import com.example.backend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@PasswordValueMatch.List({@PasswordValueMatch(field = "password", fieldMatch = "confirmPassword", message = "Las contraseñas no coinciden")})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRequest {

    @ValidEmail(message = "Por favor introduzca un email valido")
    @NotNull(message = "Por favor introduzca un email")
    @NotBlank(message = "Por favor introduzca un email")
    private String email;

    @NotBlank(message = "Por favor introduzca nombre")
    @Size(max = 30, message = "El nombre no debe contener mas de 30 caracteres")
    private String firstName;

    @NotBlank(message = "Por favor introduzca apellido ")
    @Size(max = 30, message = "El apellido no debe contener mas de 30 caracteres")
    private String lastName;

    @NotBlank(message = "Por favor introduzca nombre de usuario")
    @Size(max = 30, message = "El nombre de usuario no debe contener mas de 30 caracteres")
    private String username;

    @NotBlank(message = "Por favor introduzca una contraseña")
    @Size(max = 30, message = "La contraseña no debe contener mas de 30 caracteres")
    @ValidPassword
    private String password;

    @NotBlank(message = "Por favor introduzca una contraseña")
    @Size(max = 30, message = "La contraseña no debe contener mas de 30 caracteres")
    @ValidPassword
    private String confirmPassword;

    @Size(max = 30, message = "El telefono no debe contener mas de 30 caracteres")
    private String phoneNumber;

    @PastOrPresent(message = "La fecha de nacimiento debe ser anterior a la actual")
    private Date birthday;

    @Size(max = 100, message = "La direccion debe contener por lo menos 30 caracteres")
    private String address;

    @Size(min = 1, max = 3)
    private List<Role> roles;

}

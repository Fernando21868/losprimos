package com.example.backend.dto.request;

import com.example.backend.model.Role;
import com.example.backend.model.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRequest {

    @Email(message = "Por favor introdusca un email valido")
    @NotBlank(message = "Por favor introduzca un email")
    private String email;

    @NotBlank(message = "Por favor introduzca nombre")
    @Size(max = 30, message = "El nombre debe contener por lo menos 30 caracteres")
    private String firstName;

    @NotBlank(message = "Por favor introduzca apellido ")
    @Size(max = 30, message = "El apellido debe contener por lo menos 30 caracteres")
    private String lastName;

    @NotBlank(message = "Por favor introduzca nombre de usuario")
    @Size(max = 30, message = "El username debe contener por lo menos 30 caracteres")
    private String username;

    @Size(max = 30, message = "El telefono debe contener por lo menos 30 caracteres")
    private String phoneNumber;

    @PastOrPresent(message = "La fecha de nacimiento debe ser anterior a la actual")
    private Date birthday;

    @Size(max = 100, message = "La direccion debe contener por lo menos 30 caracteres")
    private String address;

    private List<RoleEnum> roles;

}

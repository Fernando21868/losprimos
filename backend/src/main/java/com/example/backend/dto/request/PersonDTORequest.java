package com.example.backend.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
@SuperBuilder
public class PersonDTORequest {

    @NotNull(message = "Por favor introduzca un dni")
    @Min(value = 0, message = "Introduzca un dni valido")
    @Min(value = 100_000, message = "Introduzca un dni valido")
    private Integer dni;

    //@ValidEmail(message = "Por favor introduzca un email valido")
    @Email(message = "Por favor introduzca un email valido")
    @NotNull(message = "Por favor introduzca un email")
    @NotBlank(message = "Por favor introduzca un email")
    private String email;

    @NotBlank(message = "Por favor introduzca nombre")
    @Size(max = 30, message = "El nombre no debe contener mas de 30 caracteres")
    private String firstName;

    @NotBlank(message = "Por favor introduzca apellido ")
    @Size(max = 30, message = "El apellido no debe contener mas de 30 caracteres")
    private String lastName;

    @Size(max = 30, message = "El telefono no debe contener mas de 30 caracteres")
    private String phoneNumber;

    @PastOrPresent(message = "La fecha de nacimiento debe ser anterior a la actual")
    private Date birthday;

    private AddressDtoRequest address;

}

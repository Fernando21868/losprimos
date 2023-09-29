package com.example.backend.dto.request;

import com.example.backend.model.Patient;
import com.example.backend.model.Person;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class PersonDTORequest {

    // not nullables
    @NotBlank(message = "Por favor introduzca un nombre")
    @Size(max = 30, message = "El nombre no debe contener mas de 30 caracteres")
    private String firstName;
    @NotBlank(message = "Por favor introduzca un apellido ")
    @Size(max = 30, message = "El apellido no debe contener mas de 30 caracteres")
    private String lastName;
    // @NotBlank(message = "Por favor introduzca un dni")
    @Size(max = 9, message = "El dni no debe contener mas de 9 caracteres")
    private String dni;
    // @NotNull(message = "Por favor introduzca una fecha de nacimiento")
    @PastOrPresent(message = "La fecha de nacimiento debe ser anterior a la actual")
    private Date birthday;
    // @NotBlank(message = "Por favor introduzca una nacionalidad")
    @Size(max = 30, message = "La nacionalidad no debe contener mas de 30 caracteres")
    private String nationality;
    // @NotBlank(message = "Por favor introduzca una edad")
    @Size(max = 4, message = "La edad no debe contener mas de 4 caracteres")
    private String age;
    // @NotBlank(message = "Por favor introduzca un sexo")
    @Size(max = 10, message = "El sexo no debe contener mas de 10 caracteres")
    private String sex;

    // nullables
    @Size(max = 30, message = "El telefono no debe contener mas de 30 caracteres")
    private String phoneNumber;
    //@ValidEmail(message = "Por favor introduzca un email valido")
    @Email(message = "Por favor introduzca un email valido")
    private String email;
    @Size(max = 30, message = "La obra social no debe contener mas de 30 caracteres")
    private String socialWork;

    // relations
    private AddressDtoRequest address;
    private Set<PersonDTORequest> persons;

}

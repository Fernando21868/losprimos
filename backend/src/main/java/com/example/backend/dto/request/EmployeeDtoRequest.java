package com.example.backend.dto.request;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * Request DTO for employee
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class EmployeeDtoRequest extends UserDtoRequest implements Serializable {

    /**
     * Id
     */
    private Long id;

    /**
     * Not nullables
     */
    @NotBlank(message = "Por favor ingrese una matricula")
    @Size(max = 30, message = "La matricula no debe tener mas de 30 caracteres")
    private String enrollment;
    @NotBlank(message = "Por favor ingrese un tipo de empleado")
    @Size(max = 30, message = "El tipo de empleado no debe tener mas de 30 caracteres")
    private String type;


}

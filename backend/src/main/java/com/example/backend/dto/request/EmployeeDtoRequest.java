package com.example.backend.dto.request;

import com.example.backend.model.Role;
import com.example.backend.model.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
public class EmployeeDtoRequest extends UserDtoRequest {

    @NotBlank(message = "Por favor introduzca tipo de empleado")
    @Size(max = 30, message = "El tipo de empleado debe contener por lo menos 30 caracteres")
    private String type;

    @NotNull(message = "Por favor introduzca un dni")
    @Min(value = 0, message = "Introduzca un dni valido")
    @Min(value = 100_000, message = "Introduzca un dni valido")
    private Integer dni;

    @Future(message = "Las vacaciones deben ser posterior a la fecha actual")
    private Date vacations;



}

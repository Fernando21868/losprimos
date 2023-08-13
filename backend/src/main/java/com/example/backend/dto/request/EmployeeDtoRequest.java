package com.example.backend.dto.request;

import com.example.backend.model.Role;
import com.example.backend.model.RoleEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class EmployeeDtoRequest extends UserDtoRequest {

    @NotBlank(message = "Por favor introduzca tipo de empleado")
    @Size(max = 30, message = "El tipo de empleado debe contener por lo menos 30 caracteres")
    private String type;

    @NotBlank(message = "Por favor introduzca un permiso")
    @Size(max = 30, message = "El permiso debe contener por lo menos 30 caracteres")
    private String permission;

    @Future(message = "Las vacaciones deben ser posterior a la fecha actual")
    private Date vacations;

    public EmployeeDtoRequest(@Email(message = "Por favor introdusca un email valido") @NotBlank(message = "Por favor introduzca un email") String email, @NotBlank(message = "Por favor introduzca nombre") @Size(max = 30, message = "El nombre debe contener por lo menos 30 caracteres") String firstName, @NotBlank(message = "Por favor introduzca apellido ") @Size(max = 30, message = "El apellido debe contener por lo menos 30 caracteres") String lastName, @NotBlank(message = "Por favor introduzca nombre de usuario") @Size(max = 30, message = "El username debe contener por lo menos 30 caracteres") String username, @Size(max = 30, message = "El telefono debe contener por lo menos 30 caracteres") String phoneNumber, @PastOrPresent(message = "La fecha de nacimiento debe ser anterior a la actual") Date birthday, @Size(max = 100, message = "La direccion debe contener por lo menos 30 caracteres") String address, List<RoleEnum> roles, String type, String permission, Date vacations) {
        super(email, firstName, lastName, username, phoneNumber, birthday, address, roles);
        this.type = type;
        this.permission = permission;
        this.vacations = vacations;
    }
}

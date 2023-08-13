package com.example.backend.dto.request;

import com.example.backend.model.Role;
import com.example.backend.model.RoleEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ClientDtoRequest extends UserDtoRequest {

    public ClientDtoRequest(@Email(message = "Por favor introdusca un email valido") @NotBlank(message = "Por favor introduzca un email") String email, @NotBlank(message = "Por favor introduzca nombre") @Size(max = 30, message = "El nombre debe contener por lo menos 30 caracteres") String firstName, @NotBlank(message = "Por favor introduzca apellido ") @Size(max = 30, message = "El apellido debe contener por lo menos 30 caracteres") String lastName, @NotBlank(message = "Por favor introduzca nombre de usuario") @Size(max = 30, message = "El username debe contener por lo menos 30 caracteres") String username, @Size(max = 30, message = "El telefono debe contener por lo menos 30 caracteres") String phoneNumber, @PastOrPresent(message = "La fecha de nacimiento debe ser anterior a la actual") Date birthday, @Size(max = 100, message = "La direccion debe contener por lo menos 30 caracteres") String address, List<RoleEnum> roles) {
        super(email, firstName, lastName, username, phoneNumber, birthday, address, roles);
    }
}

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
public class ClientDtoRequest extends UserDtoRequest {

    public ClientDtoRequest(@Email(message = "Por favor introdusca un email valido") @NotBlank(message = "Por favor introduzca un email") String email, @NotBlank(message = "Por favor introduzca nombre") @Size(max = 30, message = "El nombre no debe contener mas de 30 caracteres") String firstName, @NotBlank(message = "Por favor introduzca apellido ") @Size(max = 30, message = "El apellido no debe contener mas de 30 caracteres") String lastName, @NotBlank(message = "Por favor introduzca nombre de usuario") @Size(max = 30, message = "El nombre de usuario no debe contener mas de 30 caracteres") String username, @NotBlank(message = "Por favor introduzca una contraseña") @Size(max = 30, message = "La contraseña no debe contener mas de 30 caracteres") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{4,}$", message = "La contraseña debe contener por lo menos 4 caracteres, minusculas, mayusuculas y numeros") String password, @NotBlank(message = "Por favor introduzca una contraseña") @Size(max = 30, message = "La contraseña no debe contener mas de 30 caracteres") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{4,}$", message = "La contraseña debe contener por lo menos 4 caracteres, minusculas, mayusuculas y numeros") String confirmPassword, @Size(max = 30, message = "El telefono no debe contener mas de 30 caracteres") String phoneNumber, @PastOrPresent(message = "La fecha de nacimiento debe ser anterior a la actual") Date birthday, @Size(max = 100, message = "La direccion debe contener por lo menos 30 caracteres") String address, @Size(min = 1, max = 3) List<Role> roles) {
        super(email, firstName, lastName, username, password, confirmPassword, phoneNumber, birthday, address, roles);
    }

}

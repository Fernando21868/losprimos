package com.example.backend.service.Interface;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.dto.request.UserAuthenticateDtoRequest;
import com.example.backend.dto.response.UserAuthenticateDtoResponse;

public interface ISessionService {
    /**
     * Realiza la validación del usuario y contraseña ingresado.
     * En caso de ser correcto, devuelve la cuenta con el token necesario para realizar las demás consultas.
     *
     * @param user -- user con userName y password
     * @return UserResponseDTO -- Objeto con userName y token
     * @throws UserNotFoundException -- se lanza la exception en caso de no encontrar al usuario
     */
    UserAuthenticateDtoResponse login(UserAuthenticateDtoRequest user );
}

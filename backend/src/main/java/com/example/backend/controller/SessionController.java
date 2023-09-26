package com.example.backend.controller;

import com.example.backend.dto.request.UserAuthenticateDtoRequest;
import com.example.backend.dto.response.UserAuthenticateDtoResponse;
import com.example.backend.service.Interface.ISessionService;
import com.example.backend.service.Implementation.SessionServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SessionController {
    private final ISessionService service;

    public SessionController(SessionServiceImpl sessionService) {
        this.service = sessionService;
    }

    /**
     * Realiza la validación del usuario y contraseña ingresado.
     * En caso de ser correcto, devuelve la cuenta con el token necesario para realizar las demás consultas.
     *
     * @param user - user con userName y password
     * @return UserResponseDTO
     */
    @PostMapping("/sign-in")
    public UserAuthenticateDtoResponse login(@RequestBody UserAuthenticateDtoRequest user ) {
        return service.login(user);
    }

}

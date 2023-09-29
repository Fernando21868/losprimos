package com.example.backend.controller;

import com.example.backend.dto.request.UserAuthenticateDtoRequest;
import com.example.backend.dto.response.UserAuthenticateDtoResponse;
import com.example.backend.model.User;
import com.example.backend.service.Interface.ISessionService;
import com.example.backend.service.Implementation.SessionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/profile/{username}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        return new ResponseEntity<>(service.getByUsername(username), HttpStatus.OK);
    }

}

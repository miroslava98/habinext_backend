package org.example.backend_habinext.auth.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend_habinext.auth.dto.LoginDTO;
import org.example.backend_habinext.auth.dto.RegisterDTO;
import org.example.backend_habinext.auth.entities.Usuario;
import org.example.backend_habinext.auth.service.AuthenticationService;
import org.example.backend_habinext.auth.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Usuario> register(@RequestBody @Valid RegisterDTO registerUserDto) {
        Usuario registeredUser = null;
        registeredUser = authenticationService.signup(registerUserDto);


        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginDTO loginUserDto) {
        Usuario authenticatedUser = authenticationService.login(loginUserDto);

        System.out.println(authenticatedUser.getCorreo());

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/perfil")
    public ResponseEntity<?> perfilUsuario(String correo) {
        Usuario usuario = authenticationService.perfilUsuario(correo);
        if (usuario == null) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }

        if (!usuario.isEnabled()) {
            return new ResponseEntity<>("Usuario deshabilitado", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse {
        private String token;
        private long expiresIn;

    }
}
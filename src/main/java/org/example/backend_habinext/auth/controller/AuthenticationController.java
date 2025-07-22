package org.example.backend_habinext.auth.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.coyote.Response;
import org.example.backend_habinext.auth.dto.LoginDTO;
import org.example.backend_habinext.auth.dto.RegisterDTO;
import org.example.backend_habinext.auth.entities.Usuario;
import org.example.backend_habinext.auth.repository.IRepositoryUsuario;
import org.example.backend_habinext.auth.service.AuthenticationService;
import org.example.backend_habinext.auth.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @Autowired
    private final IRepositoryUsuario repoUsuario;

    private final PasswordEncoder passwordEncoder;


    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, IRepositoryUsuario repoUsuario, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.repoUsuario = repoUsuario;
        this.passwordEncoder = passwordEncoder;
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

    @GetMapping("/usuario")
    public ResponseEntity<?> perfilUsuarioActual(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = authenticationService.perfilUsuarioActual(userDetails.getUsername());


        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PutMapping("/modificarPerfil")
    public ResponseEntity<?> modificarUsuario(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestBody ActualizarPerfil perfil) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Usuario usuario = authenticationService.perfilUsuarioActual(userDetails.getUsername());

        if (usuario.isEnabled()) {
            if (!perfil.getNombre().isBlank() || !perfil.getContrasenya().isBlank()) {
                usuario.setNombre(perfil.getNombre());
                usuario.setContrasenya(passwordEncoder.encode(perfil.getContrasenya()));
                usuario.setImagen_perfil(perfil.getImagen_perfil());
                repoUsuario.saveAndFlush(usuario);
            }


        }
        return ResponseEntity.ok(usuario);

    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse {
        private String token;
        private long expiresIn;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActualizarPerfil {
        private String imagen_perfil;
        private String nombre;
        private String contrasenya;

    }


}
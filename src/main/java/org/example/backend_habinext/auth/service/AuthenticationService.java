package org.example.backend_habinext.auth.service;


import org.example.backend_habinext.auth.dto.LoginDTO;
import org.example.backend_habinext.auth.dto.RegisterDTO;
import org.example.backend_habinext.auth.entities.Usuario;
import org.example.backend_habinext.auth.exceptions.EmailAlreadyUsedException;
import org.example.backend_habinext.auth.repository.IRepositoryUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private final IRepositoryUsuario userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            IRepositoryUsuario userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario signup(RegisterDTO input) {
        Optional<Usuario> opUsuario = userRepository.findByCorreo(input.getCorreo());
        if (opUsuario.isPresent()) {
            throw new EmailAlreadyUsedException("El correo ya está en uso");
        }
        Usuario user = new Usuario();
        user.setNombre(input.getNombre());
        user.setCorreo(input.getCorreo());
        user.setContrasenya(passwordEncoder.encode(input.getContrasenya()));


        return userRepository.save(user);
    }

    public Usuario login(LoginDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getCorreo(),
                        input.getContrasenya()
                )
        );

        return userRepository.findByCorreo(input.getCorreo())
                .orElseThrow();
    }

    public Usuario perfilUsuario(String correo) {
        Optional<Usuario> opUsuario = userRepository.findByCorreo(correo);
        Usuario usuario = null;
        if (opUsuario.isPresent()) {
            usuario = opUsuario.get();
        }

        return usuario;
    }
}
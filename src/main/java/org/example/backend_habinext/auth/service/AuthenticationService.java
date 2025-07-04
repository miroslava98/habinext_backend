package org.example.backend_habinext.auth.service;


import org.example.backend_habinext.auth.dto.LoginDTO;
import org.example.backend_habinext.auth.dto.RegisterDTO;
import org.example.backend_habinext.auth.entities.Usuario;
import org.example.backend_habinext.auth.repository.IRepositoryUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
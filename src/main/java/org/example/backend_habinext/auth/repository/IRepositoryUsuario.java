package org.example.backend_habinext.auth.repository;

import org.example.backend_habinext.auth.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRepositoryUsuario extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);
}

package com.ecommerce.auth_service.repository;

import com.ecommerce.auth_service.model.Rol;
import com.ecommerce.auth_service.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);

}

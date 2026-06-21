package com.ecommerce.auth_service.service;

import com.ecommerce.auth_service.dto.request.UsuarioRequestDTO;
import com.ecommerce.auth_service.dto.response.UsuarioResponseDTO;
import com.ecommerce.auth_service.model.Usuario;

import java.util.Optional;

public interface UsuarioService {

    Optional<Usuario> findByCorreo(String correo);
    UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto);

}

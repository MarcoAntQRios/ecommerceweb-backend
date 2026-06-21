package com.ecommerce.auth_service.service.impl;

import com.ecommerce.auth_service.dto.request.UsuarioRequestDTO;
import com.ecommerce.auth_service.dto.response.UsuarioResponseDTO;
import com.ecommerce.auth_service.exception.ConflictException;
import com.ecommerce.auth_service.mapper.UsuarioMapper;
import com.ecommerce.auth_service.model.Usuario;
import com.ecommerce.auth_service.repository.UsuarioRepository;
import com.ecommerce.auth_service.service.PasswordService;
import com.ecommerce.auth_service.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordService passwordService;
    private final UsuarioMapper usuarioMapper;
    @Override
    public Optional<Usuario> findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);    }

    @Override
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto) {

        if (usuarioRepository.findByCorreo(dto.getCorreo()).isPresent()) {
            throw new ConflictException(
                    "Ya existe un usuario registrado con el correo: " + dto.getCorreo()
            );
        }

        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setPassword(passwordService.encriptar(usuario.getPassword()));
        return usuarioMapper.toDto(usuarioRepository.save(usuario));

    }
}

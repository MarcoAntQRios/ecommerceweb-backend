package com.ecommerce.usuariotec.service.impl;

import com.ecommerce.usuariotec.dto.request.UsuarioRequestDTO;
import com.ecommerce.usuariotec.dto.request.UsuarioUpdateDTO;
import com.ecommerce.usuariotec.dto.response.UsuarioResponseDTO;
import com.ecommerce.usuariotec.exception.AccessDeniedException;
import com.ecommerce.usuariotec.exception.BadRequestException;
import com.ecommerce.usuariotec.exception.ConflictException;
import com.ecommerce.usuariotec.exception.NotFoundException;
import com.ecommerce.usuariotec.mapper.UsuarioMapper;
import com.ecommerce.usuariotec.model.Rol;
import com.ecommerce.usuariotec.model.Usuario;
import com.ecommerce.usuariotec.repository.RolRepository;
import com.ecommerce.usuariotec.repository.UsuarioRepository;
import com.ecommerce.usuariotec.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public UsuarioResponseDTO crear(UsuarioRequestDTO dto) {


        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            log.error("Ya existe un usuario con el correo: {}", dto.getCorreo());
            throw new ConflictException(
                    "Ya existe un usuario con el correo: " + dto.getCorreo()
            );
        }

        Rol rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new NotFoundException(
                        "Rol no encontrado con id: " + dto.getRolId()
                ));
        Usuario usuario = usuarioMapper.toEntity(dto, rol);
        return usuarioMapper.toDto(usuarioRepository.save(usuario));    }

    @Override
    public List<UsuarioResponseDTO> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDto)
                .toList();
    }

    @Override
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Usuario no encontrado con id: " + id
                ));
        return usuarioMapper.toDto(usuario);
    }

    @Override
    public UsuarioResponseDTO actualizar(Long id, UsuarioUpdateDTO dto) {
        String currentUserId = ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject();

        if (!currentUserId.equals(String.valueOf(id))) {
            throw new AccessDeniedException("No puedes modificar datos de otro usuario");
        }

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Usuario no encontrado con id: " + id
                ));

        Rol rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new NotFoundException(
                        "Rol no encontrado con id: " + dto.getRolId()
                ));

        usuarioMapper.updateEntity(dto, usuario, rol);

        Usuario updated = usuarioRepository.save(usuario);

        return usuarioMapper.toDto(updated);
    }
}

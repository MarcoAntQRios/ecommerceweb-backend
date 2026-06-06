package com.ecommerce.usuariotec.service.impl;

import com.ecommerce.usuariotec.dto.request.UsuarioRequestDTO;
import com.ecommerce.usuariotec.dto.response.UsuarioResponseDTO;
import com.ecommerce.usuariotec.mapper.UsuarioMapper;
import com.ecommerce.usuariotec.model.Rol;
import com.ecommerce.usuariotec.model.Usuario;
import com.ecommerce.usuariotec.repository.RolRepository;
import com.ecommerce.usuariotec.repository.UsuarioRepository;
import com.ecommerce.usuariotec.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public UsuarioResponseDTO crear(UsuarioRequestDTO dto) {
        Rol rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new RuntimeException(
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
                .orElseThrow(() -> new RuntimeException(
                        "Usuario no encontrado con id: " + id
                ));
        return usuarioMapper.toDto(usuario);
    }

    @Override
    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Usuario no encontrado con id: " + id
                ));
        Rol rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new RuntimeException(
                        "Rol no encontrado con id: " + dto.getRolId()
                ));
        usuarioMapper.updateEntity(dto, usuario, rol);
        return usuarioMapper.toDto(usuarioRepository.save(usuario));
    }
}

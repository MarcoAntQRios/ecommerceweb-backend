package com.ecommerce.usuariotec.service;

import com.ecommerce.usuariotec.dto.request.UsuarioRequestDTO;
import com.ecommerce.usuariotec.dto.response.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO crear(UsuarioRequestDTO dto);
    List<UsuarioResponseDTO> listar();
    UsuarioResponseDTO buscarPorId(Long id);
    UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO dto);
}

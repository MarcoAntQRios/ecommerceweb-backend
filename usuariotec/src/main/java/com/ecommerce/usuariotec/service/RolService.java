package com.ecommerce.usuariotec.service;

import com.ecommerce.usuariotec.dto.request.RolRequestDTO;
import com.ecommerce.usuariotec.dto.response.RolResponseDTO;

import java.util.List;

public interface RolService {
    RolResponseDTO create(RolRequestDTO dto);
    List<RolResponseDTO> listar();
    RolResponseDTO buscarPorId(Long id);
}

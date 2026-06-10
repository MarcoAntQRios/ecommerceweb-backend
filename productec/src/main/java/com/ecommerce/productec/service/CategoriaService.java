package com.ecommerce.productec.service;

import com.ecommerce.productec.dto.request.CategoriaRequestDTO;
import com.ecommerce.productec.dto.response.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaService {
    CategoriaResponseDTO crear(CategoriaRequestDTO dto);
    List<CategoriaResponseDTO> listar();
    CategoriaResponseDTO buscarPorId(Long id);
    CategoriaResponseDTO actualizar(Long id, CategoriaRequestDTO dto);
}

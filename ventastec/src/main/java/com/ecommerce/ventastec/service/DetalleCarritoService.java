package com.ecommerce.ventastec.service;

import com.ecommerce.ventastec.dto.request.DetalleCarritoRequestDTO;
import com.ecommerce.ventastec.dto.response.DetalleCarritoResponseDTO;

public interface DetalleCarritoService {
    DetalleCarritoResponseDTO agregar(Long carritoId, DetalleCarritoRequestDTO dto);
    void quitar(Long detalleId);
    void aumentar(Long detalleId);
    void disminuir(Long detalleId);
}

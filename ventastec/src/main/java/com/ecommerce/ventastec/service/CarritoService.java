package com.ecommerce.ventastec.service;

import com.ecommerce.ventastec.dto.response.CarritoResponseDTO;

public interface CarritoService {

    CarritoResponseDTO obtener(Long usuarioId);
    CarritoResponseDTO verCarrito(Long carritoId);

}

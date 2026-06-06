package com.ecommerce.ventastec.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CarritoResponseDTO {

    private Long id;
    private String usuarioNombre;
    private List<DetalleCarritoResponseDTO> detalleCarrito;

}

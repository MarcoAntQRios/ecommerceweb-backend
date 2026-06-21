package com.ecommerce.ventastec.dto.response;

import lombok.Data;

@Data
public class DetalleCarritoResponseDTO {

    private Long id;
    private String productoNombre;
    private String productoImagen;
    private Integer cantidad;
    private Double subtotal;
    private Long productoId;
}

package com.ecommerce.productec.dto.response;

import lombok.Data;

@Data
public class DetalleCarritoResponseDTO {

    private Long id;
    private String productoNombre;
    private Integer cantidad;
    private Double subtotal;
    private Long productoId;
}

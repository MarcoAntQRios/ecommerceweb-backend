package com.ecommerce.ventastec.dto.response;

import lombok.Data;

@Data
public class DetalleVentaResponseDTO {
    private Long id;
    private Long productoId;
    private String productoNombre;
    private Integer cantidad;
    private Double precio;
    private Double subtotal;

}

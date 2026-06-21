package com.ecommerce.ventastec.dto.response;

import lombok.Data;

@Data
public class DetalleComprobanteDTO {
    private String nombreProducto;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}

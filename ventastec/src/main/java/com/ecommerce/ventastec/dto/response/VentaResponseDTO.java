package com.ecommerce.ventastec.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaResponseDTO {
    private Long id;
    private String usuarioNombre;
    private LocalDateTime fecha;
    private Double total;
    private String estado;
    private String urlCheckout;
    private List<DetalleVentaResponseDTO> detalleVenta;
}

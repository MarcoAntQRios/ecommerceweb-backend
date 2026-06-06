package com.ecommerce.productec.dto.response;

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
    private List<DetalleVentaResponseDTO> detalleVenta;
}

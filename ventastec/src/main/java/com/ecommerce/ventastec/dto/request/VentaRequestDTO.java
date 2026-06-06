package com.ecommerce.ventastec.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class VentaRequestDTO {
    //private Long carritoId;
    private Long usuarioId;
    private List<DetalleVentaRequestDTO> detalles;

}

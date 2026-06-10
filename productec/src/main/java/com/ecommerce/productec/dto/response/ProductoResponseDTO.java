package com.ecommerce.productec.dto.response;

import lombok.Data;

@Data
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String imagen;
    private String tipoNombre;
    private String categoriaNombre;
}

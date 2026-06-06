package com.ecommerce.ventastec.dto.request;

import lombok.Data;

@Data
public class ProductoRequestDTO {

    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String imagen;
    private Long tipoId;

}

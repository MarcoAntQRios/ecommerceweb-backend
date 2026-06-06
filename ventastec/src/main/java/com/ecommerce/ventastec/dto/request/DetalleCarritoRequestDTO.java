package com.ecommerce.ventastec.dto.request;

import lombok.Data;

@Data
public class DetalleCarritoRequestDTO {

    private Long productoId;
    private Integer cantidad;

}

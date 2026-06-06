package com.ecommerce.productec.dto.request;

import lombok.Data;

@Data
public class DetalleCarritoRequestDTO {

    private Long productoId;
    private Integer cantidad;

}

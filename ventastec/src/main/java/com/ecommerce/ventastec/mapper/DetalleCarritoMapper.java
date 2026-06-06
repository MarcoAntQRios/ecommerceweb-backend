package com.ecommerce.ventastec.mapper;

import com.ecommerce.ventastec.dto.response.DetalleCarritoResponseDTO;
import com.ecommerce.ventastec.model.DetalleCarrito;
import org.springframework.stereotype.Component;

@Component
public class DetalleCarritoMapper {

    public DetalleCarritoResponseDTO toDto(DetalleCarrito detalle) {
        DetalleCarritoResponseDTO response = new DetalleCarritoResponseDTO();
        response.setId(detalle.getId());
        response.setCantidad(detalle.getCantidad());
        response.setSubtotal(detalle.getSubtotal());
        response.setProductoId(detalle.getProductoId());
        return response;
    }

}

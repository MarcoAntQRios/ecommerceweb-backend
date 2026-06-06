package com.ecommerce.ventastec.mapper;

import com.ecommerce.ventastec.dto.response.DetalleVentaResponseDTO;
import com.ecommerce.ventastec.model.DetalleVenta;
import org.springframework.stereotype.Component;

@Component
public class DetalleVentaMapper {

    public DetalleVentaResponseDTO toDto(DetalleVenta detalle) {
        DetalleVentaResponseDTO response = new DetalleVentaResponseDTO();
        response.setId(detalle.getId());
        response.setCantidad(detalle.getCantidad());
        response.setPrecio(detalle.getPrecio());
        response.setSubtotal(detalle.getSubtotal());
        response.setProductoId(detalle.getIdProducto());
        return response;
    }
}

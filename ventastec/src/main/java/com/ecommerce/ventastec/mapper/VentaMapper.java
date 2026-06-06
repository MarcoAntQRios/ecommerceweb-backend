package com.ecommerce.ventastec.mapper;


import com.ecommerce.ventastec.dto.response.VentaResponseDTO;
import com.ecommerce.ventastec.model.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class VentaMapper {

    @Autowired
    private  DetalleVentaMapper detalleVentaMapper;

    public VentaResponseDTO toDto(Venta venta) {
        VentaResponseDTO response = new VentaResponseDTO();
        response.setId(venta.getId());
        response.setFecha(venta.getFecha());
        response.setTotal(venta.getTotal());
        response.setEstado(venta.getEstado());
        response.setDetalleVenta(venta.getDetalleVenta()
                .stream()
                .map(detalleVentaMapper::toDto)
                .collect(Collectors.toList()));
        return response;
    }

}

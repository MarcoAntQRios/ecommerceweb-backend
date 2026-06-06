package com.ecommerce.ventastec.mapper;

import com.ecommerce.ventastec.dto.response.CarritoResponseDTO;
import com.ecommerce.ventastec.model.Carrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CarritoMapper {

    @Autowired
    private DetalleCarritoMapper detalleCarritoMapper;

    public CarritoResponseDTO toDto(Carrito carrito) {
        CarritoResponseDTO response = new CarritoResponseDTO();
        response.setId(carrito.getId());
        response.setDetalleCarrito(carrito.getDetalles()
                .stream()
                .map(detalleCarritoMapper::toDto)
                .collect(Collectors.toList()));
        return response;
    }

}

package com.ecommerce.productec.mapper;

import com.ecommerce.productec.dto.request.ProductoTipoRequestDTO;
import com.ecommerce.productec.dto.response.ProductoTipoResponseDTO;
import com.ecommerce.productec.model.ProductoTipo;
import org.springframework.stereotype.Component;

@Component
public class ProductoTipoMapper {
    public ProductoTipo toEntity(ProductoTipoRequestDTO dto){
        ProductoTipo productoTipo = new ProductoTipo();
        productoTipo.setNombre(dto.getNombre());
        return productoTipo;
    }

    public ProductoTipoResponseDTO toDto(ProductoTipo productoTipo){
        ProductoTipoResponseDTO responseDTO = new ProductoTipoResponseDTO();
        responseDTO.setId(productoTipo.getId());
        responseDTO.setNombre(productoTipo.getNombre());
        return responseDTO;
    }

    public void updateEntity(ProductoTipoRequestDTO dto, ProductoTipo tipo) {
        tipo.setNombre(dto.getNombre());
    }
}

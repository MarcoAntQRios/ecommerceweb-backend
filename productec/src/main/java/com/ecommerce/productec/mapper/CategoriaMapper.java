package com.ecommerce.productec.mapper;

import com.ecommerce.productec.dto.response.CategoriaResponseDTO;
import com.ecommerce.productec.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public CategoriaResponseDTO toDto(Categoria categoria) {
        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        return dto;
    }
}
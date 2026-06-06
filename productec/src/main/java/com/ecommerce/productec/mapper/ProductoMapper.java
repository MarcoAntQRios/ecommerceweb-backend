package com.ecommerce.productec.mapper;

import com.ecommerce.productec.dto.request.ProductoRequestDTO;
import com.ecommerce.productec.dto.response.ProductoResponseDTO;
import com.ecommerce.productec.model.Producto;
import com.ecommerce.productec.model.ProductoTipo;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public Producto toEntity(ProductoRequestDTO dto, ProductoTipo tipo){
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setImagen(dto.getImagen());
        producto.setTipo(tipo);
        return producto;
    }
    public ProductoResponseDTO toDto(Producto producto){
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setImagen(producto.getImagen());
        dto.setTipoNombre(producto.getTipo().getNombre());
        return dto;
    }
    public void updateEntity(ProductoRequestDTO dto, Producto producto, ProductoTipo tipo){
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setImagen(dto.getImagen());
        producto.setTipo(tipo);
    }

}

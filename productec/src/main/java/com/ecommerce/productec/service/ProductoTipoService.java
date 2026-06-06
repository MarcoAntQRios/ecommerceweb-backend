package com.ecommerce.productec.service;

import com.ecommerce.productec.dto.request.ProductoTipoRequestDTO;
import com.ecommerce.productec.dto.response.ProductoTipoResponseDTO;

import java.util.List;

public interface ProductoTipoService {
    ProductoTipoResponseDTO create (ProductoTipoRequestDTO dto);
    List<ProductoTipoResponseDTO> listar();
    ProductoTipoResponseDTO actualizar(Long id, ProductoTipoRequestDTO dto);

}

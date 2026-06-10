package com.ecommerce.productec.service;

import com.ecommerce.productec.dto.request.ProductoRequestDTO;
import com.ecommerce.productec.dto.response.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {
    ProductoResponseDTO create (ProductoRequestDTO dto);
    List<ProductoResponseDTO> listar();
    List<ProductoResponseDTO> listarPorTipoYCategoria(String tipoNombre, String categoriaNombre);
    ProductoResponseDTO buscarPorId(Long id);
    ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto);
    ProductoResponseDTO actualizarStock(Long id, Integer stock);
    void descontarStock(Long id, Integer cantidad);
    void eliminar(Long id);

}

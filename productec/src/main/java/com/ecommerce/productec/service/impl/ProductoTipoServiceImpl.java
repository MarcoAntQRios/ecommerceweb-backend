package com.ecommerce.productec.service.impl;

import com.ecommerce.productec.dto.request.ProductoTipoRequestDTO;
import com.ecommerce.productec.dto.response.ProductoTipoResponseDTO;
import com.ecommerce.productec.mapper.ProductoTipoMapper;
import com.ecommerce.productec.model.ProductoTipo;
import com.ecommerce.productec.repository.ProductoTipoRepository;
import com.ecommerce.productec.service.ProductoTipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ProductoTipoServiceImpl implements ProductoTipoService {

    private final ProductoTipoRepository productoTipoRepository;
    private final ProductoTipoMapper productoTipoMapper;

    @Override
    public ProductoTipoResponseDTO create(ProductoTipoRequestDTO dto) {

        ProductoTipo productoTipo = productoTipoMapper.toEntity(dto);
        ProductoTipo tipoGuardado = productoTipoRepository.save(productoTipo);

        return productoTipoMapper.toDto(tipoGuardado);
    }

    @Override
    public List<ProductoTipoResponseDTO> listar() {
        return productoTipoRepository.findAll()
                .stream()
                .map(productoTipoMapper::toDto)
                .toList();
    }

    @Override
    public ProductoTipoResponseDTO actualizar(Long id, ProductoTipoRequestDTO dto) {
        ProductoTipo tipo = productoTipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Tipo de producto no encontrado con id: " + id
                ));
        productoTipoMapper.updateEntity(dto, tipo);
        ProductoTipo tipoGuardado = productoTipoRepository.save(tipo);
        return productoTipoMapper.toDto(tipoGuardado);
    }

}

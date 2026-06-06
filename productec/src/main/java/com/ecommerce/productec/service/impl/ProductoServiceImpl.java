package com.ecommerce.productec.service.impl;

import com.ecommerce.productec.dto.request.ProductoRequestDTO;
import com.ecommerce.productec.dto.response.ProductoResponseDTO;
import com.ecommerce.productec.mapper.ProductoMapper;
import com.ecommerce.productec.model.Producto;
import com.ecommerce.productec.model.ProductoTipo;
import com.ecommerce.productec.repository.ProductoRepository;
import com.ecommerce.productec.repository.ProductoTipoRepository;
import com.ecommerce.productec.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoTipoRepository productoTipoRepository;
    @Autowired
    private ProductoMapper productoMapper;

    //toDo: feign cliente usuario admin;
    @Override
    public ProductoResponseDTO create(ProductoRequestDTO dto) {

        ProductoTipo productoTipo = productoTipoRepository.findById(dto.getTipoId())
                .orElseThrow(() -> new RuntimeException(
                        "Tipo de producto no encontrado con id: " + dto.getTipoId()
                ));
        Producto producto = productoMapper.toEntity(dto,productoTipo);

        Producto productoGuardado = productoRepository.save(producto);

        return  productoMapper.toDto(productoGuardado);
    }

    @Override
    public List<ProductoResponseDTO> listar() {
        return productoRepository.findAll()
                .stream()
                .map(productoMapper::toDto)
                .toList();
    }


    @Override
    public List<ProductoResponseDTO> listarPorTipo(String nombre) {
        return productoRepository.findByTipoNombre(nombre)
                .stream()
                .map(productoMapper::toDto)
                .toList();
    }

    @Override
    public ProductoResponseDTO buscarPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(
                        "Producto no encontrado con id: " + id
         ));
        return productoMapper.toDto(producto);

    }

    @Override
    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Producto no encontrado con id: " + id
                ));

        ProductoTipo tipo = productoTipoRepository.findById(dto.getTipoId())
                .orElseThrow(() -> new RuntimeException(
                        "Tipo de producto no encontrado con id: " + dto.getTipoId()
                ));

        productoMapper.updateEntity(dto, producto, tipo);
        return productoMapper.toDto(productoRepository.save(producto));
    }

    @Override
    public ProductoResponseDTO actualizarStock(Long id, Integer cantidad) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Producto no encontrado con id: " + id
                ));
        producto.setStock(producto.getStock() + cantidad);
        return productoMapper.toDto(productoRepository.save(producto));
    }
    @Override
    public void descontarStock(Long id, Integer cantidad) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Producto no encontrado con id: " + id
                ));

        if (producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);
    }

    @Override
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con id: " + id);
        }
        productoRepository.deleteById(id);
    }

}

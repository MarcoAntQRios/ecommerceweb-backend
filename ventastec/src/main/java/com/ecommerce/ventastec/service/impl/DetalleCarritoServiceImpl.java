package com.ecommerce.ventastec.service.impl;

import com.ecommerce.ventastec.cliente.ProductoCliente;
import com.ecommerce.ventastec.dto.request.DetalleCarritoRequestDTO;
import com.ecommerce.ventastec.dto.response.DetalleCarritoResponseDTO;
import com.ecommerce.ventastec.dto.response.ProductoResponseDTO;
import com.ecommerce.ventastec.mapper.DetalleCarritoMapper;
import com.ecommerce.ventastec.model.Carrito;
import com.ecommerce.ventastec.model.DetalleCarrito;
import com.ecommerce.ventastec.repository.CarritoRepository;
import com.ecommerce.ventastec.repository.DetalleCarritoRepository;
import com.ecommerce.ventastec.service.DetalleCarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DetalleCarritoServiceImpl implements DetalleCarritoService {

    private final DetalleCarritoRepository detalleCarritoRepository;
    private final CarritoRepository carritoRepository;
    private final ProductoCliente productoCliente;
    private final DetalleCarritoMapper detalleCarritoMapper;


    @Override
    public DetalleCarritoResponseDTO agregar(Long carritoId, DetalleCarritoRequestDTO dto) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        ProductoResponseDTO producto = productoCliente.obtener(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getStock() < dto.getCantidad()) {
            throw new RuntimeException("Stock insuficiente: " + producto.getStock());
        }

        // busca si el producto ya está en el carrito
        DetalleCarrito detalle = detalleCarritoRepository
                .findByCarritoIdAndProductoId(carritoId, dto.getProductoId())
                .orElseGet(() -> {
                    DetalleCarrito nuevo = new DetalleCarrito();
                    nuevo.setCarrito(carrito);
                    nuevo.setProductoId(dto.getProductoId());
                    nuevo.setCantidad(0);
                    nuevo.setSubtotal(0.0);
                    return nuevo;
                });

        // suma la cantidad y recalcula el subtotal
        detalle.setCantidad(detalle.getCantidad() + dto.getCantidad());
        detalle.setSubtotal(producto.getPrecio() * detalle.getCantidad());

        return detalleCarritoMapper.toDto(detalleCarritoRepository.save(detalle));
    }


    @Override
    public void quitar(Long detalleId) {
        DetalleCarrito detalle = detalleCarritoRepository.findById(detalleId)
                .orElseThrow(() -> new RuntimeException(
                        "Detalle no encontrado con id: " + detalleId
                ));

        // elimina el detalle completo sin importar la cantidad
        detalleCarritoRepository.deleteById(detalleId);
    }
    @Override
    public void aumentar(Long detalleId) {
        DetalleCarrito detalle = detalleCarritoRepository.findById(detalleId)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado con id: " + detalleId));

        if (detalle.getCantidad() >= 5) {
            throw new RuntimeException("Cantidad máxima es 5");
        }

        ProductoResponseDTO producto = productoCliente.obtener(detalle.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        detalle.setCantidad(detalle.getCantidad() + 1);
        detalle.setSubtotal(producto.getPrecio() * detalle.getCantidad());
        detalleCarritoRepository.save(detalle);
    }

    @Override
    public void disminuir(Long detalleId) {
        DetalleCarrito detalle = detalleCarritoRepository.findById(detalleId)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado con id: " + detalleId));

        if (detalle.getCantidad() <= 1) {
            throw new RuntimeException("Cantidad mínima es 1");
        }

        ProductoResponseDTO producto = productoCliente.obtener(detalle.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        detalle.setCantidad(detalle.getCantidad() - 1);
        detalle.setSubtotal(producto.getPrecio() * detalle.getCantidad());
        detalleCarritoRepository.save(detalle);
    }
}

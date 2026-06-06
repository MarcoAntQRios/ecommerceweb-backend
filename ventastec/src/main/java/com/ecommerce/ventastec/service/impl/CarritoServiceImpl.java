package com.ecommerce.ventastec.service.impl;

import com.ecommerce.ventastec.cliente.ProductoCliente;
import com.ecommerce.ventastec.cliente.UsuarioTecCliente;
import com.ecommerce.ventastec.dto.response.CarritoResponseDTO;
import com.ecommerce.ventastec.dto.response.ProductoResponseDTO;
import com.ecommerce.ventastec.dto.response.UsuarioResponseDTO;
import com.ecommerce.ventastec.mapper.CarritoMapper;
import com.ecommerce.ventastec.model.Carrito;
import com.ecommerce.ventastec.repository.CarritoRepository;
import com.ecommerce.ventastec.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService {

    private final UsuarioTecCliente usuarioTecCliente;
    private final ProductoCliente productoCliente;
    private final CarritoRepository carritoRepository;
    private final CarritoMapper carritoMapper;

    @Override
    public CarritoResponseDTO obtener(Long usuarioId) {
        UsuarioResponseDTO usuario = usuarioTecCliente.obtener(usuarioId)
                .orElseThrow(() -> new RuntimeException(
                        "Usuario no encontrado con id: " + usuarioId
                ));

        // busca si ya tiene carrito, si no lo crea
        Carrito carrito = carritoRepository.findByUsuarioId(usuarioId)
                .orElseGet(() -> {
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setUsuarioId(usuario.getId());
                    return carritoRepository.save(nuevoCarrito);
                });
        CarritoResponseDTO carritoResponseDTO = carritoMapper.toDto(carrito);

        carritoResponseDTO.setUsuarioNombre(usuario.getNombre());

        return carritoResponseDTO;
    }

    @Override
    public CarritoResponseDTO verCarrito(Long carritoId) {

        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException(
                        "Carrito no encontrado con id: " + carritoId
                ));
        UsuarioResponseDTO usuario = usuarioTecCliente.obtener(carrito.getUsuarioId()).orElse(null);
        CarritoResponseDTO carritoResponseDTO = carritoMapper.toDto(carrito);
        if (usuario != null) {
            carritoResponseDTO.setUsuarioNombre(usuario.getNombre());
        }
        //mostrar nombre del producto en el detalle
        carritoResponseDTO.getDetalleCarrito().forEach(detalle ->
                productoCliente.obtener(detalle.getProductoId())
                        .ifPresent(producto -> detalle.setProductoNombre(producto.getNombre()))
        );

        return carritoResponseDTO;
    }
}

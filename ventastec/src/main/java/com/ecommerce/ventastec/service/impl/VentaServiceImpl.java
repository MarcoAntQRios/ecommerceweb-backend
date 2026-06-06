package com.ecommerce.ventastec.service.impl;

import com.ecommerce.ventastec.cliente.ProductoCliente;
import com.ecommerce.ventastec.cliente.UsuarioTecCliente;
import com.ecommerce.ventastec.dto.request.DetalleVentaRequestDTO;
import com.ecommerce.ventastec.dto.request.VentaRequestDTO;
import com.ecommerce.ventastec.dto.response.ProductoResponseDTO;
import com.ecommerce.ventastec.dto.response.VentaResponseDTO;
import com.ecommerce.ventastec.mapper.VentaMapper;
import com.ecommerce.ventastec.model.DetalleVenta;
import com.ecommerce.ventastec.model.Venta;
import com.ecommerce.ventastec.repository.CarritoRepository;
import com.ecommerce.ventastec.repository.DetalleCarritoRepository;
import com.ecommerce.ventastec.repository.VentaRepository;
import com.ecommerce.ventastec.service.DetalleVentaService;
import com.ecommerce.ventastec.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final CarritoRepository carritoRepository;
    private final DetalleCarritoRepository detalleCarritoRepository;
    private final DetalleVentaService detalleVentaService;
    private final VentaMapper ventaMapper;
    private final ProductoCliente productoCliente;
    private final UsuarioTecCliente usuarioTecCliente;
    @Transactional
    @Override
    public VentaResponseDTO venta(VentaRequestDTO ventaRequestDTO) {


        Double total = 0.0;
        List<DetalleVenta> detalleVentas = new  ArrayList<DetalleVenta>();

        for (DetalleVentaRequestDTO detalleVenta: ventaRequestDTO.getDetalles()){

            ProductoResponseDTO producto = productoCliente.obtener(detalleVenta.getProductoId())
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            Double subtotal = producto.getPrecio()*detalleVenta.getCantidad();
            total += subtotal;
            DetalleVenta detalleVentaRegistrar = armarDetalle(detalleVenta, producto.getPrecio(),subtotal);
            detalleVentas.add(detalleVentaRegistrar);
        }

        // 4. crear la venta
        Venta venta = new Venta();
        venta.setUsuarioId(ventaRequestDTO.getUsuarioId());
        venta.setFecha(LocalDateTime.now());
        venta.setTotal(total);
        venta.setEstado("PROCESADO");
        Venta ventaGuardada = ventaRepository.save(venta);


        List<DetalleVenta> detalles = detalleVentaService.crearDetalles(ventaGuardada, detalleVentas);
        ventaGuardada.setDetalleVenta(detalles);

        //toDo: recuperar el carrito por usuario y  eliminar el carrito
        carritoRepository.findByUsuarioId(ventaRequestDTO.getUsuarioId())
                .ifPresent(carrito -> {
                    detalleCarritoRepository.deleteAll(carrito.getDetalles());
                    carritoRepository.delete(carrito);
                });
        // 7. retornar
        return ventaMapper.toDto(ventaGuardada);
    }

    public List<VentaResponseDTO> listar() {
        return ventaRepository.findAll()
                .stream()
                .map(venta -> {
                    VentaResponseDTO dto = ventaMapper.toDto(venta);

                    // Nombre del usuario
                    usuarioTecCliente.obtener(venta.getUsuarioId())
                            .ifPresent(usuario -> dto.setUsuarioNombre(usuario.getNombre() + " " + usuario.getApellido()));

                    // Nombre del producto en cada detalle
                    dto.getDetalleVenta().forEach(detalle ->
                            productoCliente.obtener(detalle.getProductoId())
                                    .ifPresent(producto -> detalle.setProductoNombre(producto.getNombre()))
                    );

                    return dto;
                })
                .toList();
    }

    @Override
    public VentaResponseDTO buscarPorId(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Venta no encontrada con id: " + id
                ));
        return ventaMapper.toDto(venta);
    }

    @Override
    public List<VentaResponseDTO> listarPorUsuario(Long usuarioId) {
        return ventaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(ventaMapper::toDto)
                .toList();    }


    private DetalleVenta armarDetalle(DetalleVentaRequestDTO detalleVentaRequestDTO,Double precio, Double subtotal){
        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setCantidad(detalleVentaRequestDTO.getCantidad());
        detalleVenta.setIdProducto(detalleVentaRequestDTO.getProductoId());
        detalleVenta.setPrecio(precio);
        detalleVenta.setSubtotal(subtotal);
        return detalleVenta;
    }

}

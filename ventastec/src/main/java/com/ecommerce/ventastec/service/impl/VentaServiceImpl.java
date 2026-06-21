package com.ecommerce.ventastec.service.impl;

import com.ecommerce.ventastec.cliente.ProductoCliente;
import com.ecommerce.ventastec.cliente.UsuarioTecCliente;
import com.ecommerce.ventastec.dto.request.DetalleVentaRequestDTO;
import com.ecommerce.ventastec.dto.request.VentaRequestDTO;
import com.ecommerce.ventastec.dto.response.DetalleComprobanteDTO;
import com.ecommerce.ventastec.dto.response.ProductoResponseDTO;
import com.ecommerce.ventastec.dto.response.VentaResponseDTO;
import com.ecommerce.ventastec.exception.BadRequestException;
import com.ecommerce.ventastec.exception.NotFoundException;
import com.ecommerce.ventastec.mapper.VentaMapper;
import com.ecommerce.ventastec.model.DetalleVenta;
import com.ecommerce.ventastec.model.Venta;
import com.ecommerce.ventastec.repository.CarritoRepository;
import com.ecommerce.ventastec.repository.DetalleCarritoRepository;
import com.ecommerce.ventastec.repository.DetalleVentaRepository;
import com.ecommerce.ventastec.repository.VentaRepository;
import com.ecommerce.ventastec.service.ComprobantePDFService;
import com.ecommerce.ventastec.service.DetalleVentaService;
import com.ecommerce.ventastec.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
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
    private final DetalleVentaRepository detalleVentaRepository;
    private final VentaMapper ventaMapper;
    private final ProductoCliente productoCliente;
    private final UsuarioTecCliente usuarioTecCliente;
    private final StripePaymentServiceImpl stripePaymentService;
    private final ComprobantePDFService comprobantePDFService;
    @Transactional
    @Override
    public VentaResponseDTO venta(VentaRequestDTO ventaRequestDTO) {


        Double total = 0.0;
        List<DetalleVenta> detalleVentas = new  ArrayList<DetalleVenta>();

        for (DetalleVentaRequestDTO detalleVenta: ventaRequestDTO.getDetalles()){

            ProductoResponseDTO producto = productoCliente.obtener(detalleVenta.getProductoId())
            .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

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
        venta.setEstado("PENDIENTE");
        Venta ventaGuardada = ventaRepository.save(venta);


        List<DetalleVenta> detalles = detalleVentaService.crearDetalles(ventaGuardada, detalleVentas);
        ventaGuardada.setDetalleVenta(detalles);

        //toDo: recuperar el carrito por usuario y  eliminar el carrito
        carritoRepository.findByUsuarioId(ventaRequestDTO.getUsuarioId())
                .ifPresent(carrito -> {
                    detalleCarritoRepository.deleteAll(carrito.getDetalles());
                    carritoRepository.delete(carrito);
                });
        Long montoTotal = convertirTotalEnCentavos(total);
        String urlPago = stripePaymentService.createCheckoutSession(montoTotal,ventaGuardada.getId(),"PEN","Venta de productos");
        VentaResponseDTO ventaResponse = ventaMapper.toDto(ventaGuardada);
        ventaResponse.setUrlCheckout(urlPago);
        return ventaResponse;
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

        VentaResponseDTO dto = ventaMapper.toDto(venta);

        usuarioTecCliente.obtener(venta.getUsuarioId())
                .ifPresent(usuario -> dto.setUsuarioNombre(usuario.getNombre() + " " + usuario.getApellido()));

        dto.getDetalleVenta().forEach(detalle ->
                productoCliente.obtener(detalle.getProductoId())
                        .ifPresent(producto -> detalle.setProductoNombre(producto.getNombre()))
        );

        return dto;
    }

    @Override
    public List<VentaResponseDTO> listarPorUsuario(Long usuarioId) {
        return ventaRepository.findByUsuarioId(usuarioId)
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
    public VentaResponseDTO procesarPago(Long ventaId) {
        Venta venta = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + ventaId));

        if ("PROCESADO".equals(venta.getEstado())) {
            // Ya fue procesado antes (doble llamada), devuelve sin volver a descontar
            return ventaMapper.toDto(venta);
        }

        // Descontar stock de cada producto SOLO al confirmar el pago
        for (DetalleVenta detalle : venta.getDetalleVenta()) {
            productoCliente.descontarStock(detalle.getIdProducto(), detalle.getCantidad());
        }

        venta.setEstado("PROCESADO");
        Venta ventaActualizada = ventaRepository.save(venta);

        return ventaMapper.toDto(ventaActualizada);
    }

    private DetalleVenta armarDetalle(DetalleVentaRequestDTO detalleVentaRequestDTO,Double precio, Double subtotal){
        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setCantidad(detalleVentaRequestDTO.getCantidad());
        detalleVenta.setIdProducto(detalleVentaRequestDTO.getProductoId());
        detalleVenta.setPrecio(precio);
        detalleVenta.setSubtotal(subtotal);
        return detalleVenta;
    }
    private Long convertirTotalEnCentavos(Double total){
        return Math.round(total*100);
    }

    @Override
    public String obtenerCheckoutUrl(Long ventaId) {
        Venta venta = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + ventaId));
        Long monto = Math.round(venta.getTotal() * 100);
        return stripePaymentService.createCheckoutSession(monto, ventaId, "PEN", "Venta de productos");
    }

    @Override
    public VentaResponseDTO cancelarPago(Long ventaId) {
        Venta venta = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + ventaId));

        if ("PROCESADO".equals(venta.getEstado())) {
            throw new BadRequestException("No se puede cancelar una venta ya procesada");
        }

        // No hay que devolver stock porque nunca se descontó
        venta.setEstado("CANCELADO");
        Venta ventaActualizada = ventaRepository.save(venta);

        return ventaMapper.toDto(ventaActualizada);
    }

    @Override
    public byte[] descargarComprobante(Long ventaId) {

        Venta venta = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new NotFoundException("Venta no encontrada con id: " + ventaId));

        if ("CANCELADO".equals(venta.getEstado())) {
            throw new BadRequestException("No se puede generar comprobante de una venta cancelada");
        }
        String nombreCliente = usuarioTecCliente.obtener(venta.getUsuarioId())
                .map(u -> u.getNombre() + " " + u.getApellido())
                .orElse("Cliente");

        // Convertir DetalleVenta → DetalleComprobanteDTO consultando nombre por Feign
        List<DetalleComprobanteDTO> detalles = detalleVentaRepository
                .findByVentaId(ventaId)
                .stream()
                .map(detalle -> {
                    String nombreProducto = productoCliente.obtener(detalle.getIdProducto())
                            .map(ProductoResponseDTO::getNombre)
                            .orElse("Producto #" + detalle.getIdProducto());

                    DetalleComprobanteDTO dto = new DetalleComprobanteDTO();
                    dto.setNombreProducto(nombreProducto);
                    dto.setCantidad(detalle.getCantidad());
                    dto.setPrecioUnitario(detalle.getPrecio());
                    dto.setSubtotal(detalle.getSubtotal());
                    return dto;
                })
                .toList();

        ByteArrayInputStream pdf = comprobantePDFService.generarComprobante(
                venta.getId(),
                nombreCliente,
                venta.getTotal(),
                venta.getEstado(),
                detalles
        );

        try {
            return pdf.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar comprobante", e);
        }
    }
}

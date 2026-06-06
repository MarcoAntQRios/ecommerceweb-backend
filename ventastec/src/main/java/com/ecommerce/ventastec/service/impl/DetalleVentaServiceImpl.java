package com.ecommerce.ventastec.service.impl;

import com.ecommerce.ventastec.cliente.ProductoCliente;
import com.ecommerce.ventastec.dto.response.ProductoResponseDTO;
import com.ecommerce.ventastec.model.DetalleCarrito;
import com.ecommerce.ventastec.model.DetalleVenta;
import com.ecommerce.ventastec.model.Venta;
import com.ecommerce.ventastec.repository.DetalleVentaRepository;
import com.ecommerce.ventastec.service.DetalleVentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalleVentaServiceImpl implements DetalleVentaService {

    private final DetalleVentaRepository detalleVentaRepository;
    private final ProductoCliente productoCliente;

    @Override
    public List<DetalleVenta> crearDetalles(Venta venta, List<DetalleVenta> detalleVentas) {
        List<DetalleVenta> detalles = new ArrayList<>();

        for (DetalleVenta detalleVenta : detalleVentas) {
            // crear detalle venta
            detalleVenta.setVenta(venta);
            detalles.add(detalleVenta);
            //descontar stock
            productoCliente.descontarStock(detalleVenta.getIdProducto(), detalleVenta.getCantidad());


        }
        System.out.println("Total detalles a guardar: " + detalles.size());
        return detalleVentaRepository.saveAll(detalles);
    }
}

package com.ecommerce.ventastec.service;

import com.ecommerce.ventastec.model.DetalleCarrito;
import com.ecommerce.ventastec.model.DetalleVenta;
import com.ecommerce.ventastec.model.Venta;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleVentaService {
    List<DetalleVenta> crearDetalles(Venta venta, List<DetalleVenta> detalleVentas);

}

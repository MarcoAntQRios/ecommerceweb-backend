package com.ecommerce.ventastec.service;

import com.ecommerce.ventastec.dto.response.DetalleComprobanteDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ComprobantePDFService {
    ByteArrayInputStream generarComprobante(
            Long ventaId,
            String cliente,
            Double total,
            String stripePaymentId,
            List<DetalleComprobanteDTO> detalles
    );
}

package com.ecommerce.ventastec.service;

import com.ecommerce.ventastec.dto.request.VentaRequestDTO;
import com.ecommerce.ventastec.dto.response.VentaResponseDTO;

import java.util.List;

public interface VentaService {
    VentaResponseDTO venta(VentaRequestDTO ventaRequestDTO);
    List<VentaResponseDTO> listar();
    VentaResponseDTO buscarPorId(Long id);
    List<VentaResponseDTO> listarPorUsuario(Long usuarioId);
    VentaResponseDTO procesarPago(Long ventaId);
    VentaResponseDTO cancelarPago(Long ventaId);
    String obtenerCheckoutUrl(Long ventaId);
    byte[] descargarComprobante(Long ventaId);
}

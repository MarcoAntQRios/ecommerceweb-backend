package com.ecommerce.ventastec.controller;

import com.ecommerce.ventastec.dto.request.VentaRequestDTO;
import com.ecommerce.ventastec.dto.response.VentaResponseDTO;
import com.ecommerce.ventastec.service.ComprobantePDFService;
import com.ecommerce.ventastec.service.StripePaymentService;
import com.ecommerce.ventastec.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;
    private final StripePaymentService stripePaymentService;
    private  final ComprobantePDFService comprobantePDFService;
    @PostMapping
    public ResponseEntity<VentaResponseDTO> venta(@RequestBody VentaRequestDTO ventaRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.venta(ventaRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> listar() {
        return ResponseEntity.ok(ventaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.buscarPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<VentaResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ventaService.listarPorUsuario(usuarioId));
    }

    @PatchMapping("/procesar-pago")
    public ResponseEntity<VentaResponseDTO> procesarPago(@RequestParam Long ventaId) {
        VentaResponseDTO venta = ventaService.procesarPago(ventaId);
        return ResponseEntity.ok(venta);
    }

    @GetMapping("/{id}/checkout-url")
    public ResponseEntity<String> obtenerCheckoutUrl(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.obtenerCheckoutUrl(id));
    }
    @PatchMapping("/cancelar-pago")
    public ResponseEntity<VentaResponseDTO> cancelarPago(@RequestParam Long ventaId) {
        VentaResponseDTO venta = ventaService.cancelarPago(ventaId);
        return ResponseEntity.ok(venta);
    }
    @GetMapping("/{id}/comprobante")
    public ResponseEntity<byte[]> descargarComprobante(
            @PathVariable Long id) {

        byte[] pdf = ventaService.descargarComprobante(id);

        return ResponseEntity.ok()
                .header(
                        "Content-Disposition",
                        "attachment; filename=comprobante-" + id + ".pdf"
                )
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}

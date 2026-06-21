package com.ecommerce.ventastec.controller;


import com.ecommerce.ventastec.service.DetalleCarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito-detalles")
@RequiredArgsConstructor

public class DetalleCarritoController {

    private final DetalleCarritoService detalleCarritoService;



    @DeleteMapping("/{productoDetalleId}")
    public ResponseEntity<Void> quitar(
            @PathVariable Long productoDetalleId) {
        detalleCarritoService.quitar(productoDetalleId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{productoDetalleId}/aumentar")
    public ResponseEntity<Void> aumentar(

            @PathVariable Long productoDetalleId) {

        detalleCarritoService.aumentar(productoDetalleId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{productoDetalleId}/disminuir")
    public ResponseEntity<Void> disminuir(
            @PathVariable Long productoDetalleId) {
        detalleCarritoService.disminuir(productoDetalleId);
        return ResponseEntity.noContent().build();
    }


}

package com.ecommerce.productec.controller;

import com.ecommerce.productec.dto.request.ProductoRequestDTO;
import com.ecommerce.productec.dto.response.ProductoResponseDTO;
import com.ecommerce.productec.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> registrarProducto(
            @RequestBody ProductoRequestDTO dto) {
        ProductoResponseDTO response = productoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        return ResponseEntity.ok(productoService.listar());
    }
    @GetMapping("/tipo/{nombre}")
    public ResponseEntity<List<ProductoResponseDTO>> listarPorTipo(
            @PathVariable String nombre) {
        return ResponseEntity.ok(productoService.listarPorTipo(nombre));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> buscarPorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarPorId(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.ok(productoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/stock")
    public ResponseEntity<ProductoResponseDTO> actualizarStock(
            @PathVariable Long id,
            @RequestParam Integer stock) {
        return ResponseEntity.ok(productoService.actualizarStock(id, stock));
    }
    @PutMapping("/{id}/descontar-stock")
    public ResponseEntity<Void> descontarStock(
            @PathVariable Long id,
            @RequestParam Integer cantidad) {
        productoService.descontarStock(id, cantidad);
        return ResponseEntity.noContent().build();
    }
}

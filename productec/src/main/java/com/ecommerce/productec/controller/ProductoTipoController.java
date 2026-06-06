package com.ecommerce.productec.controller;

import com.ecommerce.productec.dto.request.ProductoTipoRequestDTO;
import com.ecommerce.productec.dto.response.ProductoTipoResponseDTO;
import com.ecommerce.productec.service.ProductoTipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto-tipos")
@RequiredArgsConstructor
public class ProductoTipoController {
    private final ProductoTipoService productoTipoService;

    @PostMapping
    public ResponseEntity<ProductoTipoResponseDTO> registrarTipo(
            @RequestBody ProductoTipoRequestDTO dto) {
        ProductoTipoResponseDTO response = productoTipoService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductoTipoResponseDTO>> listar() {
        return ResponseEntity.ok(productoTipoService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoTipoResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody ProductoTipoRequestDTO dto) {
        return ResponseEntity.ok(productoTipoService.actualizar(id, dto));
    }
}

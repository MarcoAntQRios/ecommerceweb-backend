package com.ecommerce.usuariotec.controller;

import com.ecommerce.usuariotec.dto.request.RolRequestDTO;
import com.ecommerce.usuariotec.dto.response.RolResponseDTO;
import com.ecommerce.usuariotec.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @PostMapping
    public ResponseEntity<RolResponseDTO> crear(
            @RequestBody RolRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rolService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<RolResponseDTO>> listar() {
        return ResponseEntity.ok(rolService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(rolService.buscarPorId(id));
    }
}

package com.ecommerce.ventastec.controller;
import com.ecommerce.ventastec.dto.request.DetalleCarritoRequestDTO;
import com.ecommerce.ventastec.dto.response.CarritoResponseDTO;
import com.ecommerce.ventastec.dto.response.DetalleCarritoResponseDTO;
import com.ecommerce.ventastec.service.CarritoService;
import com.ecommerce.ventastec.service.DetalleCarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carritos")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;
    private final DetalleCarritoService detalleCarritoService;

    @PostMapping("/{carritoId}/productos")
    public ResponseEntity<DetalleCarritoResponseDTO> agregar(
            @PathVariable Long carritoId,
            @RequestBody DetalleCarritoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(detalleCarritoService.agregar(carritoId, dto));
    }
    @PostMapping("/usuarios/{usuarioId}")
    public ResponseEntity<CarritoResponseDTO> obtener(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(carritoService.obtener(usuarioId));
    }

    @GetMapping("/{carritoId}")
    public ResponseEntity<CarritoResponseDTO> verCarrito(@PathVariable Long carritoId) {
        return ResponseEntity.ok(carritoService.verCarrito(carritoId));
    }

}

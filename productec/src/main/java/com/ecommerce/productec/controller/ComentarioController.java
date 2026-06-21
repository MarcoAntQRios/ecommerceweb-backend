package com.ecommerce.productec.controller;

import com.ecommerce.productec.dto.request.ComentarioRequestDTO;
import com.ecommerce.productec.dto.response.ComentarioResponseDTO;
import com.ecommerce.productec.dto.response.ComentariosProductoResponseDTO;
import com.ecommerce.productec.service.ComentarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioService comentarioService;

    @PostMapping
    public ResponseEntity<ComentarioResponseDTO> crear(
            @RequestBody ComentarioRequestDTO dto,
            JwtAuthenticationToken authentication) {

        Jwt jwt = authentication.getToken();
        Long idUsuario = Long.valueOf(jwt.getSubject());
        String nombreUsuario = jwt.getClaimAsString("nombre");

        ComentarioResponseDTO response = comentarioService.crear(dto, idUsuario, nombreUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<ComentariosProductoResponseDTO> listarPorProducto(
            @PathVariable Long idProducto) {
        return ResponseEntity.ok(comentarioService.listarPorProducto(idProducto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id,
            JwtAuthenticationToken authentication) {

        Long idUsuario = Long.valueOf(authentication.getToken().getSubject());
        comentarioService.eliminar(id, idUsuario);
        return ResponseEntity.noContent().build();
    }
}
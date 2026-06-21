package com.ecommerce.auth_service.controller;

import com.ecommerce.auth_service.domain.LoginRequest;
import com.ecommerce.auth_service.domain.TokenResponse;
import com.ecommerce.auth_service.dto.request.UsuarioRequestDTO;
import com.ecommerce.auth_service.dto.response.UsuarioResponseDTO;
import com.ecommerce.auth_service.service.AuthService;
import com.ecommerce.auth_service.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity
                .ok()
                .body(authService.login(loginRequest));
    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioResponseDTO> registrar(
            @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(dto));
    }

}

package com.ecommerce.auth_service.service.impl;

import com.ecommerce.auth_service.domain.LoginRequest;
import com.ecommerce.auth_service.domain.TokenResponse;
import com.ecommerce.auth_service.model.Usuario;
import com.ecommerce.auth_service.service.AuthService;
import com.ecommerce.auth_service.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtEncoder jwtEncoder;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenResponse login(LoginRequest request) {
        if (!isValidUser(request)) {
            throw new BadCredentialsException("Invalid email or password");
        }

        Instant now = Instant.now();

        Usuario usuario = usuarioService.findByCorreo(request.email())
                .orElseThrow(() -> new BadCredentialsException("Usuario no existe"));
        List<String> roles = List.of(usuario.getRol().getNombre());

        String scope = roles.stream()
                .collect(Collectors.joining(" "));



        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("api-gateway")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(3600))
                .subject(String.valueOf(usuario.getId()))
                .claim("scope", scope)
                .claim("roles", roles)
                .claim("nombre", usuario.getNombre())
                .claim("correo", usuario.getCorreo())
                .build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        String token = jwtEncoder
                .encode(JwtEncoderParameters.from(header, claims))
                .getTokenValue();

        return new TokenResponse(token, "Bearer");
    }

    private boolean isValidUser(LoginRequest request) {

        Usuario usuario = usuarioService.findByCorreo(request.email())
                .orElseThrow(() -> new BadCredentialsException("Usuario no existe") );

        return passwordEncoder.matches(request.password(), usuario.getPassword());
    }
}


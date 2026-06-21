package com.ecommerce.ventastec.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class JwtConfig {

    @Bean
    public JwtDecoder jwtDecoder(JwtProperties jwtProperties) {
        byte[] secret = jwtProperties.secret().getBytes();
        SecretKeySpec secretKey = new SecretKeySpec(secret, "HmacSHA256");

        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }



}

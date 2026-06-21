package com.ecommerce.auth_service.service.impl;

import com.ecommerce.auth_service.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encriptar(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean validarPassword(String password, String passwordEnc) {
        return passwordEncoder.matches(password, passwordEnc);
    }
}

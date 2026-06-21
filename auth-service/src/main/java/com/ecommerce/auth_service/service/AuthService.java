package com.ecommerce.auth_service.service;

import com.ecommerce.auth_service.domain.LoginRequest;
import com.ecommerce.auth_service.domain.TokenResponse;

public interface AuthService {
    TokenResponse login(LoginRequest loginRequest);
}

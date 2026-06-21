package com.ecommerce.auth_service.service;

public interface PasswordService {

    String encriptar(String password);
    boolean validarPassword(String password, String passwordEnc);

}

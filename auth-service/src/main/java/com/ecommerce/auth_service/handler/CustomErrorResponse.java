package com.ecommerce.auth_service.handler;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CustomErrorResponse(LocalDateTime fecha, int status, String mensaje, String path) {



}

package com.ecommerce.productec.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CustomErrorResponse(
        LocalDateTime fecha,
        int status,
        String mensaje,
        String path
) {}
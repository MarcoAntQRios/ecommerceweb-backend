package com.ecommerce.usuariotec.exception;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Builder
public record CustomErrorResponse(
        LocalDateTime fecha,
        int status,
        String mensaje,
        String path
) {}
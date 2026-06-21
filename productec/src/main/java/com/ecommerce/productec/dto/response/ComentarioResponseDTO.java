package com.ecommerce.productec.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ComentarioResponseDTO {

    private Long id;
    private Long idProducto;
    private Long idUsuario;
    private String nombreUsuario;
    private String contenido;
    private Long idComentarioPadre;
    private LocalDateTime fechaCreacion;
    private List<ComentarioResponseDTO> respuestas; // solo se puebla en raíces, vacío en respuestas
}
package com.ecommerce.productec.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ComentariosProductoResponseDTO {

    private Long idProducto;
    private Integer totalComentarios;    // raíz + respuestas, en todos los niveles
    private List<ComentarioResponseDTO> comentarios; // comentarios raíz, cada uno con sus respuestas anidadas

}
package com.ecommerce.productec.dto.request;

import lombok.Data;

@Data
public class ComentarioRequestDTO {

    private Long idProducto;
    private String contenido;
    private Long idComentarioPadre;   // null = comentario raíz; con valor = respuesta (a cualquier nivel)

}
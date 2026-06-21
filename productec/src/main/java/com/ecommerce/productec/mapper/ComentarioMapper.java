package com.ecommerce.productec.mapper;

import com.ecommerce.productec.dto.response.ComentarioResponseDTO;
import com.ecommerce.productec.model.Comentario;
import com.ecommerce.productec.model.Producto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ComentarioMapper {

    public Comentario toEntity(String contenido, Producto producto,
                               Comentario comentarioPadre, Long idUsuario, String nombreUsuario) {
        Comentario c = new Comentario();
        c.setContenido(contenido);
        c.setProducto(producto);
        c.setComentarioPadre(comentarioPadre);
        c.setIdUsuario(idUsuario);
        c.setNombreUsuario(nombreUsuario);
        return c;
    }

    public ComentarioResponseDTO toDto(Comentario c, List<ComentarioResponseDTO> respuestas) {
        ComentarioResponseDTO dto = new ComentarioResponseDTO();
        dto.setId(c.getId());
        dto.setIdUsuario(c.getIdUsuario());
        dto.setNombreUsuario(c.getNombreUsuario());
        dto.setContenido(c.getContenido());
        dto.setFechaCreacion(c.getFechaCreacion());
        dto.setRespuestas(respuestas);
        return dto;
    }
}
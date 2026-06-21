package com.ecommerce.productec.service;

import com.ecommerce.productec.dto.request.ComentarioRequestDTO;
import com.ecommerce.productec.dto.response.ComentarioResponseDTO;
import com.ecommerce.productec.dto.response.ComentariosProductoResponseDTO;

public interface ComentarioService {

    ComentarioResponseDTO crear(ComentarioRequestDTO dto, Long idUsuario, String nombreUsuario);

    ComentariosProductoResponseDTO listarPorProducto(Long idProducto);

    void eliminar(Long idComentario, Long idUsuario);

}
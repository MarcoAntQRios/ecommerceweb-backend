package com.ecommerce.productec.service.impl;

import com.ecommerce.productec.dto.request.ComentarioRequestDTO;
import com.ecommerce.productec.dto.response.ComentarioResponseDTO;
import com.ecommerce.productec.dto.response.ComentariosProductoResponseDTO;
import com.ecommerce.productec.exception.BadRequestException;
import com.ecommerce.productec.exception.NotFoundException;
import com.ecommerce.productec.mapper.ComentarioMapper;
import com.ecommerce.productec.model.Comentario;
import com.ecommerce.productec.model.Producto;
import com.ecommerce.productec.repository.ComentarioRepository;
import com.ecommerce.productec.repository.ProductoRepository;
import com.ecommerce.productec.service.ComentarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final ProductoRepository productoRepository;
    private final ComentarioMapper comentarioMapper;

    @Override
    public ComentarioResponseDTO crear(ComentarioRequestDTO dto, Long idUsuario, String nombreUsuario) {
        Producto producto = productoRepository.findById(dto.getIdProducto())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado: " + dto.getIdProducto()));

        Comentario padre = null;
        if (dto.getIdComentarioPadre() != null) {
            padre = comentarioRepository.findById(dto.getIdComentarioPadre())
                    .orElseThrow(() -> new NotFoundException("Comentario no encontrado: " + dto.getIdComentarioPadre()));

            if (padre.getComentarioPadre() != null) {
                throw new BadRequestException("No se puede responder a una respuesta");
            }
        }

        Comentario guardado = comentarioRepository.save(
                comentarioMapper.toEntity(dto.getContenido(), producto, padre, idUsuario, nombreUsuario)
        );

        return comentarioMapper.toDto(guardado, List.of());
    }
    @Override
    public ComentariosProductoResponseDTO listarPorProducto(Long idProducto) {
        if (!productoRepository.existsById(idProducto))
            throw new NotFoundException("Producto no encontrado: " + idProducto);

        List<Comentario> raices = comentarioRepository
                .findByProductoIdAndComentarioPadreIsNullOrderByFechaCreacionDesc(idProducto);

        Map<Long, List<ComentarioResponseDTO>> respuestasPorPadre = comentarioRepository
                .findByComentarioPadreIdInOrderByFechaCreacionAsc(raices.stream().map(Comentario::getId).toList())
                .stream()
                .collect(Collectors.groupingBy(
                        c -> c.getComentarioPadre().getId(),
                        Collectors.mapping(c -> comentarioMapper.toDto(c, List.of()), Collectors.toList())
                ));

        ComentariosProductoResponseDTO response = new ComentariosProductoResponseDTO();
        response.setIdProducto(idProducto);
        response.setTotalComentarios((int) comentarioRepository.contarTodosPorProducto(idProducto));
        response.setComentarios(raices.stream()
                .map(r -> comentarioMapper.toDto(r, respuestasPorPadre.getOrDefault(r.getId(), List.of())))
                .toList());

        return response;
    }


    @Override
    public void eliminar(Long idComentario, Long idUsuario) {
        Comentario comentario = comentarioRepository.findById(idComentario)
                .orElseThrow(() -> new NotFoundException("Comentario no encontrado: " + idComentario));

        if (!comentario.getIdUsuario().equals(idUsuario))
            throw new BadRequestException("No puedes eliminar un comentario que no te pertenece");

        comentarioRepository.delete(comentario);
    }
}
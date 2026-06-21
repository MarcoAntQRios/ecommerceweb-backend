package com.ecommerce.productec.repository;

import com.ecommerce.productec.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findByProductoIdAndComentarioPadreIsNullOrderByFechaCreacionDesc(Long idProducto);

    List<Comentario> findByComentarioPadreIdInOrderByFechaCreacionAsc(List<Long> idsPadres);

    @Query("SELECT COUNT(c) FROM Comentario c WHERE c.producto.id = :idProducto")
    long contarTodosPorProducto(@Param("idProducto") Long idProducto);

}
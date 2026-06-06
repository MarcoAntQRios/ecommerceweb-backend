package com.ecommerce.ventastec.repository;

import com.ecommerce.ventastec.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
   //para verificar que un usuario no tenga ya un carrito creado
    boolean existsByUsuarioId(Long usuarioId);
    Optional<Carrito> findByUsuarioId(Long usuarioId);

}

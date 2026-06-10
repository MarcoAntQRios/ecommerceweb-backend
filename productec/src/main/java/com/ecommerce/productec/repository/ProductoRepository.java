package com.ecommerce.productec.repository;

import com.ecommerce.productec.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByTipoNombreAndCategoriaNombre(String tipoNombre, String categoriaNombre);
}

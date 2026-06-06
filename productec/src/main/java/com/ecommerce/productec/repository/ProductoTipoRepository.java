package com.ecommerce.productec.repository;

import com.ecommerce.productec.model.Producto;
import com.ecommerce.productec.model.ProductoTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoTipoRepository  extends JpaRepository<ProductoTipo, Long> {
}

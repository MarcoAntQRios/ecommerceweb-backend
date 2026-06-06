package com.ecommerce.ventastec.cliente;

import com.ecommerce.ventastec.dto.response.ProductoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "productec")
public interface ProductoCliente {


        @GetMapping("/api/productos/{id}")
        Optional<ProductoResponseDTO> obtener(@PathVariable Long id);

    @PutMapping("/api/productos/{id}/stock")
    ProductoResponseDTO actualizarStock(@PathVariable Long id, @RequestParam Integer stock);

    @PutMapping("/api/productos/{id}/descontar-stock")  // ← agregar
    void descontarStock(@PathVariable Long id, @RequestParam Integer cantidad);


}


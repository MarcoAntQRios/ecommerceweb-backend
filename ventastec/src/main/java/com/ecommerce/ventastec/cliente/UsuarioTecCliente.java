package com.ecommerce.ventastec.cliente;

import com.ecommerce.ventastec.dto.response.UsuarioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient("usuariotec")
public interface UsuarioTecCliente {

    @GetMapping("/api/usuarios/{id}")
    Optional <UsuarioResponseDTO> obtener(@PathVariable Long id);

}

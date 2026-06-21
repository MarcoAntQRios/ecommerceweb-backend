package com.ecommerce.ventastec.cliente;

import com.ecommerce.ventastec.config.FeignClientConfig;
import com.ecommerce.ventastec.dto.response.UsuarioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name="usuariotec", configuration = FeignClientConfig.class)
public interface UsuarioTecCliente {

    @GetMapping("/api/usuarios/{id}")
    Optional <UsuarioResponseDTO> obtener(@PathVariable Long id);

}

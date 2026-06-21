package com.ecommerce.usuariotec.controller;

import com.ecommerce.usuariotec.dto.request.UsuarioRequestDTO;
import com.ecommerce.usuariotec.dto.response.UsuarioResponseDTO;
import com.ecommerce.usuariotec.service.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    @Test
    public void createTest(){
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(1L);
        usuarioResponseDTO.setNombre("John");
        usuarioResponseDTO.setApellido("Doe");

        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO();
        usuarioRequestDTO.setNombre("John");
        Mockito.when(usuarioService.crear(Mockito.any(UsuarioRequestDTO.class))).thenReturn(usuarioResponseDTO);

        ResponseEntity<UsuarioResponseDTO> response= usuarioController.crear(usuarioRequestDTO);

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(usuarioRequestDTO.getNombre(), response.getBody().getNombre() );
    }

    @Test
    public void listarTest() {
        List<UsuarioResponseDTO> lista = List.of(
                new UsuarioResponseDTO(), new UsuarioResponseDTO()
        );
        Mockito.when(usuarioService.listar()).thenReturn(lista);

        ResponseEntity<List<UsuarioResponseDTO>> response = usuarioController.listar();

        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertEquals(2, response.getBody().size());
    }

    @Test
    public void buscarPorIdTest() {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(1L);
        dto.setNombre("John");

        Mockito.when(usuarioService.buscarPorId(1L)).thenReturn(dto);

        ResponseEntity<UsuarioResponseDTO> response = usuarioController.buscarPorId(1L);

        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertEquals(1L, response.getBody().getId());
    }
}

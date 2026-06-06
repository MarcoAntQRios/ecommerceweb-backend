package com.ecommerce.ventastec.dto.request;

import lombok.Data;

@Data
public class UsuarioRequestDTO {
    private String nombre;
    private String apellido;
    private String correo;
    private String password;
    private String telefono;
    private String direccion;
    private Long rolId;
}

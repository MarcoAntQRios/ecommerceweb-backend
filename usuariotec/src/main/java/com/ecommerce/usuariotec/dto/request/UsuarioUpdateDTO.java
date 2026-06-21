package com.ecommerce.usuariotec.dto.request;

import lombok.Data;

@Data
public class UsuarioUpdateDTO {
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String direccion;
    private Long rolId;
}
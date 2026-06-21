package com.ecommerce.auth_service.model;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table (name="usuarios", schema="usuariotec")
public class Usuario {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name="id_usuario")
    private Long id;

    @Column (name="nombre")
    private String nombre;

    @Column (name="apellido")
    private String apellido;

    @Column (name="correo")
    private String correo;

    @Column (name ="password")
    private String password;

    @Column (name="telefono")
    private String telefono;

    @Column (name="direccion")
    private String direccion;

    @ManyToOne
    @JoinColumn (name="id_rol")
    private Rol rol;

}

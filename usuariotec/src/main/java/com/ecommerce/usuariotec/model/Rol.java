package com.ecommerce.usuariotec.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="roles", schema="usuariotec")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id_rol")
    private Long id;

    @Column (name="nombre")
    private String nombre;

}

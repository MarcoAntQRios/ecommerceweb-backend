package com.ecommerce.ventastec.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="carrito")
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito")
    private Long id;

    @Column(name = "id_usuario")
    private Long usuarioId;

    @OneToMany(mappedBy = "carrito", fetch = FetchType.EAGER)
    private List<DetalleCarrito> detalles = new ArrayList<>();
}

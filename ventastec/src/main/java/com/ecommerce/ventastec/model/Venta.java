package com.ecommerce.ventastec.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long id;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "total")
    private Double total;

    @Column(name = "estado")
    private String estado;

    @Column(name = "id_usuario")
    private Long usuarioId;

    @OneToMany(mappedBy = "venta", fetch = FetchType.EAGER)
    private List<DetalleVenta> detalleVenta = new ArrayList<>();

}

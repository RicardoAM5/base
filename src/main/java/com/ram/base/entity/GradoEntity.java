package com.ram.base.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table (name = "grado")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradoEntity {
    @Id
    @Column(name = "id_grado")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_grado;

    @Column(unique = true)
    private String grado;

    private String descripcion;

    @OneToMany(mappedBy = "gradoEntity")
    private List<BobinaCompraEntity> bobinas;


    private boolean estatus;

}

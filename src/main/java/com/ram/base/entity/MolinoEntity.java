package com.ram.base.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table (name = "molino")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MolinoEntity {
    @Id
    @Column(name = "id_molino")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_molino;

    @Column(unique = true)
    private String molino;

    private String descripcion;

    @OneToMany(mappedBy = "molinoEntity")
    private List<BobinaCompraEntity> bobinas;


    private boolean estatus;

}

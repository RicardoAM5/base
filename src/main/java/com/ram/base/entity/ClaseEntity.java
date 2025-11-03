package com.ram.base.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table (name = "clase")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClaseEntity {
    @Id
    @Column(name = "id_clase")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_clase;

    @Column(unique = true)
    private String clase;

    @OneToMany(mappedBy = "claseEntity")
    private List<BobinaCompraEntity> bobinas;


    private boolean estatus;

}

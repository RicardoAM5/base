package com.ram.base.entity;

//Un area sirve para identificar un segmento dentro de una localidad
// Un area solo puede tener una localidad


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "area")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AreaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_area;

    @Column(unique = true)
    private String area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_localidad", nullable = false)
    private LocalidadEntity localidadEntity;


    private boolean estatus;



}

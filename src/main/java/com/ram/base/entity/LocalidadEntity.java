package com.ram.base.entity;

//Una localidad sirve para identificar la identiicacion geografica un lugar
// Una localidad puede tener una o varias areas


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "localidad")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalidadEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id_localidad;

    @Column(unique = true)
    private String localidad;
    
    @OneToMany(mappedBy = "localidadEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AreaEntity> areas;

    private boolean estatus;



}

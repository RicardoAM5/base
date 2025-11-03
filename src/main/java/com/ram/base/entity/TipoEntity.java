package com.ram.base.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table (name = "tipo")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipoEntity {

    @Id
    @Column(name = "id_tipo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tipo;

    @Column(unique = true)
    private  String tipo;


    @OneToMany(mappedBy = "tipoEntity")
    private List<BobinaCompraEntity> bobinas;


    private boolean estatus;

}

package com.ram.base.entity.procesos;

// Una maquina puede tener una o varias funciones/procesos
// Cada maquina tiene un tiempo asociado

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "maquina")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaquinaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_maquina;

    @Column(unique = true, nullable = false)
    private String nombre;

    private String descripcion;

    private boolean estatus;

    // Relación One-to-Many con ProcesoEntity
    @OneToMany(mappedBy = "maquina", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<ProcesoEntity> procesos;


}
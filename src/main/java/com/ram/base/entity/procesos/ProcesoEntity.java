package com.ram.base.entity.procesos;

// Un proceso o funcionalidad solo puede ir a una maquina + personal
// Representa las funciones/procesos que puede realizar una máquina

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "proceso")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcesoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_proceso;

    @Column(unique = true, nullable = false)
    private String nombre;

    private String descripcion;

    // Tiempo específico que toma este proceso en esta máquina
    private Double tiempoProceso;

    // Personal requerido para este proceso
    private Integer personalRequerido;

    private String nivelExperiencia;

    private boolean estatus;

    // Relación Many-to-One con MaquinaEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_maquina", nullable = false)
    private MaquinaEntity maquina;


}
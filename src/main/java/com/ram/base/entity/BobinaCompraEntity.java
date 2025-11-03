package com.ram.base.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Son las bobinas que se compran a los proveedores para su posterior uso en produccion
//Es como el catalogo de bobinas compradas
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bobina_compra")
public class BobinaCompraEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_bobina;

    private String codigoProveedor; // codigo que le da el proveedor a la bobina
    private Double ancho;    // ancho del material
    private Double gramaje;  // gramaje del material
    private String calibre; // calibre del material
    private Double peso;      // peso en kg
    private Double precio;    // precio de compra por kg
    private Double ETA;      // Timepo estimado de llegada

    @ManyToOne
    @JoinColumn(name = "id_tipo") // nombre de la columna en la tabla bobinas
    private TipoEntity tipoEntity;

    @ManyToOne
    @JoinColumn(name = "id_clase") // nombre de la columna en la tabla bobinas
    private ClaseEntity claseEntity;

    @ManyToOne
    @JoinColumn(name = "id_molino") // nombre de la columna en la tabla bobinas
    private MolinoEntity molinoEntity;

    @ManyToOne
    @JoinColumn(name = "id_grado") // nombre de la columna en la tabla bobinas
    private GradoEntity gradoEntity;

    @ManyToOne (targetEntity = ProveedorEntity.class)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private ProveedorEntity proveedor;

}


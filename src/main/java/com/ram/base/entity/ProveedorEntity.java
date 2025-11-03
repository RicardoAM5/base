package com.ram.base.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proveedor")
public class ProveedorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_proveedor;

    @Column(unique = true)
    private String nombreComercial;
    private String razonSocial;
    private String rfc;
    private String telefono;
    private String correo;
    private String pais;
    private String estado;
    private String ciudad;
    private Boolean estatus;


    @OneToMany ( targetEntity = BobinaCompraEntity.class, mappedBy = "proveedor", fetch = FetchType.LAZY)
    private List<BobinaCompraEntity> bobinasCompradas;


}


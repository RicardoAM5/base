package com.ram.base.entity.productos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proveedor")
public class ProveedorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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


 

}


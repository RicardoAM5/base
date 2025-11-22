package com.ram.base.entity.compras;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ram.base.entity.productos.BobinaCompraEntity;
import io.swagger.v3.oas.annotations.media.Schema;
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


    @OneToMany ( targetEntity = BobinaCompraEntity.class, mappedBy = "proveedor", fetch = FetchType.LAZY)
    @JsonIgnore
    @Schema(hidden = true)
    private List<BobinaCompraEntity> bobinasCompradas;


}


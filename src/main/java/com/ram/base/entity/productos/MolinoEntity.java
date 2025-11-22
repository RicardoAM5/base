package com.ram.base.entity.productos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table (name = "molino")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MolinoEntity {
    @Id
    @Column(name = "id_molino")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id_molino;

    @Column(unique = true)
    private String molino;

    private String descripcion;

    @OneToMany(mappedBy = "molinoEntity")
    @JsonIgnore
    @Schema(hidden = true)
    private List<BobinaCompraEntity> bobinas;


    private boolean estatus;

}

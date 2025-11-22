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

@Table (name = "clase")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClaseEntity {
    @Id
    @Column(name = "id_clase")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id_clase;

    @Column(unique = true)
    private String clase;

    @OneToMany(mappedBy = "claseEntity")
    @JsonIgnore
    @Schema(hidden = true)
    private List<BobinaCompraEntity> bobinas;


    private boolean estatus;

}

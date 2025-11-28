package com.ram.base.repository.productos;

import com.ram.base.entity.productos.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para Proveedores
 */
@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity, Long> {
}

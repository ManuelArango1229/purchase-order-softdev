package com.softdev.purchase_order.infrastucture.repositories;

import com.softdev.purchase_order.infrastucture.entities.OrdenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio de Spring Data para la entidad Orden.
 * Extiende JpaRepository para proporcionar operaciones CRUD.
 */
public interface SpringDataOrdenRepository extends JpaRepository<OrdenEntity, UUID> {
    /**
     * Busca una orden por su ID.
     *
     * @param id El ID de la orden a buscar.
     * @return Un Optional que contiene la orden si se encuentra, o vacío si no.
     */
    Optional<OrdenEntity> findById(UUID id);
}

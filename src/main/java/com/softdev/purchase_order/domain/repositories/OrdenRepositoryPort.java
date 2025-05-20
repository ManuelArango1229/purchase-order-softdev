package com.softdev.purchase_order.domain.repositories;

import com.softdev.purchase_order.domain.entities.Orden;
import java.util.Optional;
import java.util.UUID;

/**
 * Interfaz que define el repositorio para las órdenes.
 * Proporciona métodos para guardar y buscar órdenes por su ID.
 */
public interface OrdenRepository {
    /**
     * Guarda una orden en el repositorio.
     *
     * @param orden La orden a guardar.
     * @return La orden guardada.
     */
    Orden save(Orden orden);
    /**
     * Busca una orden por su ID.
     *
     * @param id El ID de la orden a buscar.
     * @return Un objeto Optional que contiene la orden si se encuentra, o vacío si no se encuentra.
     */
    Optional<Orden> findById(UUID id);
}

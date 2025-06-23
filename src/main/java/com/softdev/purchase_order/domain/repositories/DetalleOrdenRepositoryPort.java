package com.softdev.purchase_order.domain.repositories;

import com.softdev.purchase_order.domain.entities.DetalleOrden;
import java.util.List;

/**
 * Interfaz que define el repositorio para los detalles de las órdenes.
 * Proporciona un método para buscar detalles de una orden por su ID.
 */
public interface DetalleOrdenRepositoryPort {
    /**
     * Busca los detalles de una orden por su ID.
     *
     * @param ordenId El ID de la orden cuyos detalles se desean buscar.
     * @return Una lista de detalles de la orden.
     */
    List<DetalleOrden> findByOrdenId(String ordenId);
}

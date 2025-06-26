package com.softdev.purchase_order.infrastucture.repositories;

import com.softdev.purchase_order.infrastucture.entities.DetalleOrdenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio JPA para los detalles de las órdenes.
 * Proporciona métodos para acceder a los detalles de las órdenes en la base de datos.
 */
@Repository
public interface DetalleOrdenJpaRepository extends JpaRepository<DetalleOrdenEntity, UUID> {
    /**
     * Busca los detalles de una orden por su ID.
     *
     * @param ordenId El ID de la orden cuyos detalles se desean buscar.
     * @return Una lista de detalles de la orden.
     */
    @Query("SELECT d FROM DetalleOrdenEntity d WHERE d.orden.id = :ordenId")
    List<DetalleOrdenEntity> findByOrdenId(@Param("ordenId") UUID ordenId);


}

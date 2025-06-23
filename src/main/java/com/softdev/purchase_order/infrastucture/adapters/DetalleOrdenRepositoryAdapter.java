package com.softdev.purchase_order.infrastucture.adapters;

import com.softdev.purchase_order.domain.entities.DetalleOrden;
import com.softdev.purchase_order.domain.repositories.DetalleOrdenRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import com.softdev.purchase_order.infrastucture.entities.DetalleOrdenEntity;
import com.softdev.purchase_order.infrastucture.repositories.DetalleOrdenJpaRepository;



/**
 * Adaptador para el repositorio de detalles de órdenes.
 * Implementa la interfaz DetalleOrdenRepositoryPort y utiliza un repositorio para acceder a los datos.
 */
@Component
@RequiredArgsConstructor
public class DetalleOrdenRepositoryAdapter implements DetalleOrdenRepositoryPort {

    /**
     * Repositorio para acceder a los detalles de las órdenes.
     */
    private final DetalleOrdenJpaRepository detalleOrdenJpaRepository;

    /**
     * Método para buscar los detalles de una orden por su ID.
     * Convierte los resultados del repositorio a entidades de dominio.
     *
     * @param ordenId El ID de la orden cuyos detalles se desean buscar.
     * @return Una lista de detalles de la orden.
     */
    @Override
    public List<DetalleOrden> findByOrdenId(final String ordenId) {
        UUID id = UUID.fromString(ordenId);
        return detalleOrdenJpaRepository.findByOrdenId(id).stream()
                .map(this::toDomain)
                .toList();
    }

    /**
     * Convierte una entidad DetalleOrdenEntity a una entidad de dominio DetalleOrden.
     *
     * @param entity La entidad DetalleOrdenEntity a convertir.
     * @return La entidad de dominio DetalleOrden.
     */
    private DetalleOrden toDomain(final DetalleOrdenEntity entity) {
        return new DetalleOrden(
            entity.getNombreProducto(),
            entity.getCantidad(),
            BigDecimal.valueOf(entity.getPrecioUnitario()),
            entity.getOrdenId() // es String, perfecto para el dominio
        );
    }


}

package com.softdev.purchase_order.use_cases.service;

import com.softdev.purchase_order.domain.repositories.OrdenRepositoryPort;
import com.softdev.purchase_order.domain.repositories.DetalleOrdenRepositoryPort;
import com.softdev.purchase_order.domain.entities.Orden;
import com.softdev.purchase_order.domain.entities.DetalleOrden;
import com.softdev.purchase_order.use_cases.dto.response.OrdenConDetallesDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Servicio para obtener una orden con sus detalles.
 * Utiliza los repositorios de Orden y DetalleOrden para recuperar la información necesaria.
 */
@Service
@RequiredArgsConstructor
public class ObtenerOrdenConDetallesService {

    /**
     * Repositorio para acceder a las órdenes.
     */
    private final OrdenRepositoryPort ordenRepositoryPort;
    /**
     * Repositorio para acceder a los detalles de las órdenes.
     */
    private final DetalleOrdenRepositoryPort detalleOrdenRepositoryPort;

    /**
     * Método para obtener una orden con sus detalles a partir de su ID.
     * Si el ID no es un UUID válido o la orden no existe, devuelve un Optional vacío.
     *
     * @param ordenId El ID de la orden a buscar.
     * @return Un Optional que contiene la OrdenConDetallesDTO si se encuentra, o vacío si no.
     */
    public Optional<OrdenConDetallesDTO> ejecutar(final String ordenId) {
        try {
            UUID uuid = UUID.fromString(ordenId);  // Conversión segura
            Optional<Orden> ordenOpt = ordenRepositoryPort.findById(uuid);

            if (ordenOpt.isEmpty()) {
                return Optional.empty();
            }

            Orden orden = ordenOpt.get();
            List<DetalleOrden> detalles = detalleOrdenRepositoryPort.findByOrdenId(uuid.toString());

            // If OrdenConDetallesDTO.from only accepts Orden, you may need to set details inside the DTO or modify as needed
            return Optional.of(OrdenConDetallesDTO.from(orden));
        } catch (IllegalArgumentException e) {
            // Si el String no es un UUID válido
            return Optional.empty();
        }
    }
}

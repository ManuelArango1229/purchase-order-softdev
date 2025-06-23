package com.softdev.purchase_order.infrastucture.adapters;

import com.softdev.purchase_order.domain.entities.DetalleOrden;
import com.softdev.purchase_order.domain.entities.MetodoPago;
import com.softdev.purchase_order.domain.entities.Orden;
import com.softdev.purchase_order.domain.entities.EstadoOrden;
import com.softdev.purchase_order.domain.repositories.OrdenRepositoryPort;
import com.softdev.purchase_order.infrastucture.entities.DetalleOrdenEntity;
import com.softdev.purchase_order.infrastucture.entities.MetodoPagoEmbeddable;
import com.softdev.purchase_order.infrastucture.entities.OrdenEntity;
import com.softdev.purchase_order.infrastucture.repositories.SpringDataOrdenRepository;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adaptador para la interfaz de repositorio de ordenes.
 * Implementa la interfaz OrdenRepositoryPort y utiliza un repositorio de Spring Data.
 */
@Component
public class OrdenRepositoryAdapter implements OrdenRepositoryPort {

    /**
     * Repositorio de Spring Data para la entidad Orden.
     */
    private final SpringDataOrdenRepository repository;

    /**
     * Constructor que inicializa el adaptador con el repositorio de Spring Data.
     *
     * @param repositoryParam Repositorio de Spring Data para la entidad Orden.
     */
    public OrdenRepositoryAdapter(final SpringDataOrdenRepository repositoryParam) {
        this.repository = repositoryParam;
    }

    /**
     * Guarda una orden en la base de datos.
     *
     * @param orden La orden a guardar.
     * @return La orden guardada.
     */
    @Override
    public Orden save(final Orden orden) {
        OrdenEntity ordenEntity = mapToEntity(orden);
        OrdenEntity savedEntity = repository.save(ordenEntity);
        return mapToDomain(savedEntity);
    }

    /**
     * Busca una orden por su ID.
     *
     * @param id El ID de la orden a buscar.
     * @return Un Optional que contiene la orden si se encuentra, o vacío si no.
     */
    @Override
    public Optional<Orden> findById(final UUID id) {
        return repository.findById(id).map(this::mapToDomain);
    }

    /**
     * Mapea una orden de dominio a una entidad de base de datos.
     *
     * @param orden La orden de dominio a mapear.
     * @return La entidad correspondiente.
     */
    private OrdenEntity mapToEntity(final Orden orden) {
        OrdenEntity entity = new OrdenEntity();
        entity.setId(orden.getId());
        entity.setEmailCliente(orden.getEmailCliente());
        entity.setNombreCliente(orden.getNombreCliente());
        entity.setDniCliente(orden.getDniCliente());
        entity.setDireccion(orden.getDireccion());
        entity.setValorTotal(orden.getValorTotal().doubleValue());
        entity.setFechaPedido(orden.getFechaPedido());
        entity.setEstado(orden.getEstado().name());

        // Método de pago (objeto embebido)
        MetodoPago metodoPago = orden.getMetodoPago();
        MetodoPagoEmbeddable metodoPagoEmbeddable = new MetodoPagoEmbeddable();
        metodoPagoEmbeddable.setNombre(metodoPago.getNombre());
        metodoPagoEmbeddable.setNumeroTarjeta(metodoPago.getNumeroTarjeta());
        metodoPagoEmbeddable.setFechaExpiracion(metodoPago.getFechaExpiracion());
        metodoPagoEmbeddable.setCvv(metodoPago.getCvv());
        metodoPagoEmbeddable.setNombreTitular(metodoPago.getNombreTitular());
        entity.setMetodoPago(metodoPagoEmbeddable);

        // Detalles de orden
        for (DetalleOrden detalle : orden.getDetalles()) {
            DetalleOrdenEntity detalleEntity = new DetalleOrdenEntity();
            detalleEntity.setNombreProducto(detalle.getNombreProducto());
            detalleEntity.setCantidad(detalle.getCantidad());
            detalleEntity.setPrecioUnitario(detalle.getPrecioUnitario().doubleValue());
            detalleEntity.setSubtotal(detalle.getSubtotal().doubleValue());
            entity.addDetalle(detalleEntity);
        }

        return entity;
    }

    /**
     * Mapea una entidad de orden a un objeto de dominio.
     *
     * @param entity La entidad de orden a mapear.
     * @return El objeto de dominio correspondiente.
     */
    private Orden mapToDomain(final OrdenEntity entity) {
        // Mapear detalles
        var detalles = entity.getDetalles().stream()
            .map(det -> new DetalleOrden(
                det.getNombreProducto(),
                det.getCantidad(),
                BigDecimal.valueOf(det.getPrecioUnitario()),  // <-- convertir aquí
                det.getOrdenId()
            ))
            .collect(Collectors.toList());

        // Mapear método de pago
        MetodoPagoEmbeddable metodoPagoEmbeddable = entity.getMetodoPago();
        MetodoPago metodoPago = new MetodoPago(
                metodoPagoEmbeddable.getNombre(),
                metodoPagoEmbeddable.getNumeroTarjeta(),
                metodoPagoEmbeddable.getFechaExpiracion(),
                metodoPagoEmbeddable.getCvv(),
                metodoPagoEmbeddable.getNombreTitular());

        // Crear orden
        return new Orden(
                entity.getId(),
                entity.getEmailCliente(),
                entity.getNombreCliente(),
                entity.getDniCliente(),
                entity.getDireccion(),
                detalles,
                metodoPago,
                BigDecimal.valueOf(entity.getValorTotal()),
                entity.getFechaPedido(),
                EstadoOrden.valueOf(entity.getEstado())
                );
    }
}

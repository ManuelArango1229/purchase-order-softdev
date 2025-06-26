package com.softdev.purchase_order.infrastucture.adapters;

import com.softdev.purchase_order.domain.entities.DetalleOrden;
import com.softdev.purchase_order.infrastucture.entities.DetalleOrdenEntity;
import com.softdev.purchase_order.infrastucture.repositories.DetalleOrdenJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DetalleOrdenRepositoryAdapterTest {

    private DetalleOrdenJpaRepository repository;
    private DetalleOrdenRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = mock(DetalleOrdenJpaRepository.class);
        adapter = new DetalleOrdenRepositoryAdapter(repository);
    }

    @Test
    void testFindByOrdenId() {
        UUID ordenId = UUID.randomUUID();

        DetalleOrdenEntity entity = new DetalleOrdenEntity();
        entity.setNombreProducto("Producto A");
        entity.setCantidad(2);
        entity.setPrecioUnitario(10.5);
        entity.setSubtotal(21.0);
        entity.setId(UUID.randomUUID());

        when(repository.findByOrdenId(ordenId)).thenReturn(List.of(entity));

        List<DetalleOrden> result = adapter.findByOrdenId(ordenId.toString());

        assertEquals(1, result.size());
        assertEquals("Producto A", result.get(0).getNombreProducto());
        assertEquals(2, result.get(0).getCantidad());
        assertEquals(new BigDecimal("10.5"), result.get(0).getPrecioUnitario());

        verify(repository, times(1)).findByOrdenId(ordenId);
    }
}

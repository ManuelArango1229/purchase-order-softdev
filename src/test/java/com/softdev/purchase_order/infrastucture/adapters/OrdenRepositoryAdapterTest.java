package com.softdev.purchase_order.infrastucture.adapters;

import com.softdev.purchase_order.domain.entities.*;
import com.softdev.purchase_order.infrastucture.entities.MetodoPagoEmbeddable;
import com.softdev.purchase_order.infrastucture.entities.OrdenEntity;
import com.softdev.purchase_order.infrastucture.repositories.SpringDataOrdenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrdenRepositoryAdapterTest {

    private SpringDataOrdenRepository repository;
    private OrdenRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = mock(SpringDataOrdenRepository.class);
        adapter = new OrdenRepositoryAdapter(repository);
    }

    @Test
    void testSave() {
        Orden orden = crearOrden();

        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Orden saved = adapter.save(orden);

        assertNotNull(saved);
        assertEquals(orden.getId(), saved.getId());
        verify(repository, times(1)).save(any());
    }

@Test
void testFindById() {
    UUID id = UUID.randomUUID();
    OrdenEntity entity = new OrdenEntity();
    entity.setId(id);
    entity.setEmailCliente("cliente@test.com");
    entity.setNombreCliente("Cliente");
    entity.setDniCliente("12345678");
    entity.setDireccion("Calle Falsa 123");
    entity.setValorTotal(100.0);
    entity.setFechaPedido(LocalDateTime.now());
    entity.setEstado("CREADA");

    MetodoPagoEmbeddable metodoPago = new MetodoPagoEmbeddable();
    metodoPago.setNombre("VISA");
    metodoPago.setNumeroTarjeta("1234567812345678");
    metodoPago.setFechaExpiracion("12/30");
    metodoPago.setCvv("123");
    metodoPago.setNombreTitular("Cliente");

    entity.setMetodoPago(metodoPago);  // 👈 Imprescindible para que no falle

    when(repository.findById(id)).thenReturn(Optional.of(entity));

    Optional<Orden> result = adapter.findById(id);

    assertTrue(result.isPresent());
    assertEquals(id, result.get().getId());
    assertEquals("Cliente", result.get().getNombreCliente());
    assertEquals("VISA", result.get().getMetodoPago().getNombre());

    verify(repository, times(1)).findById(id);
}


    private Orden crearOrden() {
        return new Orden(
                UUID.randomUUID(),
                "cliente@test.com",
                "Cliente",
                "12345678",
                "Calle Falsa",
                List.of(new DetalleOrden("Producto", 2, BigDecimal.valueOf(10.0))),
                new MetodoPago("VISA", "1234567812345678", "12/30", "123", "Cliente"),
                BigDecimal.valueOf(100.0),
                LocalDateTime.now(),
                EstadoOrden.CREADA
        );
    }
}

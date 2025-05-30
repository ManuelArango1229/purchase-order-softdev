package com.softdev.purchase_order.use_cases.service;

import com.softdev.purchase_order.use_cases.dto.request.ProductoOrdenRequest;
import com.softdev.purchase_order.use_cases.dto.request.RealizarOrdenRequest;
import com.softdev.purchase_order.use_cases.dto.response.OrdenConDetallesDTO;
import com.softdev.purchase_order.use_cases.dto.response.UsuarioResponse;
import com.softdev.purchase_order.domain.entities.DetalleOrden;
import com.softdev.purchase_order.domain.entities.EstadoOrden;
import com.softdev.purchase_order.domain.entities.MetodoPago;
import com.softdev.purchase_order.domain.entities.Orden;
import com.softdev.purchase_order.domain.repositories.OrdenRepositoryPort;
import com.softdev.purchase_order.domain.repositories.ProductoServicePort;
import com.softdev.purchase_order.domain.repositories.RealizarOrdenPort;
import com.softdev.purchase_order.domain.repositories.UsuarioServicePort;
import com.softdev.purchase_order.infrastucture.messaging.OrdenPublisherService;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Servicio para realizar una orden de compra.
 * Implementa la lógica de negocio para procesar una orden y publicarla en RabbitMQ.
 */
public class RealizarOrdenService implements RealizarOrdenPort {

    /**
     * Repositorio para manejar las operaciones de orden.
     */
    private final OrdenRepositoryPort ordenRepository;
    /**
     * Servicio para manejar las operaciones de productos.
     */
    private final ProductoServicePort productoServicePort;
    /**
     * Servicio para manejar las operaciones de usuario.
     */
    private final UsuarioServicePort usuarioServicePort;
    /**
     * Servicio para publicar mensajes de órdenes a RabbitMQ.
     */
    private final OrdenPublisherService ordenPublisherService;

    /**
     * Constructor que inicializa el servicio con los repositorios necesarios.
     *
     * @param ordenRepositoryParam El repositorio de órdenes.
     * @param productoServicePortParam El servicio de productos.
     * @param usuarioServicePortParam El servicio de usuarios.
     * @param ordenPublisherServiceParam El servicio para publicar órdenes en RabbitMQ.
     */
    public RealizarOrdenService(final OrdenRepositoryPort ordenRepositoryParam,
                               final ProductoServicePort productoServicePortParam,
                               final UsuarioServicePort usuarioServicePortParam,
                               final OrdenPublisherService ordenPublisherServiceParam) {
        this.ordenRepository = ordenRepositoryParam;
        this.productoServicePort = productoServicePortParam;
        this.usuarioServicePort = usuarioServicePortParam;
        this.ordenPublisherService = ordenPublisherServiceParam;
    }

    /**
     * Realiza una orden de compra y la publica en RabbitMQ.
     *
     * @param request El objeto que contiene la información de la orden.
     * @param emailCliente El correo electrónico del cliente.
     * @return La orden creada.
     */
    @Override
    @Transactional
    public Orden realizarOrden(final RealizarOrdenRequest request, final String emailCliente) {
        // 1. Obtener información del usuario
        UsuarioResponse usuario = usuarioServicePort.obtenerUsuario(emailCliente);
        System.out.println("Direccion del cliente: " + usuario.getDireccion());

        // 2. Verificar y procesar productos
        List<DetalleOrden> detallesOrden = new ArrayList<>();
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ProductoOrdenRequest productoRequest : request.getProductos()) {
            String nombreProducto = productoRequest.getProducto();
            int cantidad = productoRequest.getCantidad();

            // Verificar que el producto existe
            if (!productoServicePort.existeProducto(nombreProducto)) {
                throw new RuntimeException("El producto '" + nombreProducto + "' no existe");
            }

            // Verificar stock disponible
            if (!productoServicePort.verificarStock(nombreProducto, cantidad)) {
                throw new RuntimeException("No hay suficiente stock para el producto '" + nombreProducto + "', cantidad solicitada: " + cantidad);
            }

            // Obtener precio del producto
            BigDecimal precioUnitario = productoServicePort.obtenerPrecio(nombreProducto);

            // Crear detalle de orden
            DetalleOrden detalle = new DetalleOrden(nombreProducto, cantidad, precioUnitario);
            detallesOrden.add(detalle);

            // Actualizar valor total
            valorTotal = valorTotal.add(detalle.getSubtotal());

            // Actualizar stock (descontar)
            productoServicePort.actualizarStock(nombreProducto, cantidad);
        }

        // 3. Crear objeto de método de pago
        MetodoPago metodoPago = new MetodoPago(
            request.getMetodoPago().getMetodoPago(),
            request.getMetodoPago().getNumeroTarjeta(),
            request.getMetodoPago().getFechaExpiracion(),
            request.getMetodoPago().getCvv(),
            request.getMetodoPago().getNombreTitular()
        );

        // 4. Crear la orden
        Orden orden = new Orden(
            UUID.randomUUID(),
            emailCliente,
            usuario.getNombre(),
            usuario.getDni(),
            usuario.getDireccion(),
            detallesOrden,
            metodoPago,
            valorTotal,
            LocalDateTime.now(),
            EstadoOrden.CREADA
        );

        // 5. Guardar la orden
        Orden ordenGuardada = ordenRepository.save(orden);

        // 6. Convertir a DTO y publicar en RabbitMQ
        try {
            OrdenConDetallesDTO ordenDTO = OrdenConDetallesDTO.from(ordenGuardada);
            ordenPublisherService.publicarOrden(ordenDTO);
        } catch (Exception e) {
            // Log del error pero no fallar la transacción
            System.err.println("Error al publicar la orden en RabbitMQ: " + e.getMessage());
            // Opcional: Podrías decidir si quieres que falle toda la transacción
            // o solo registrar el error y continuar
        }

        return ordenGuardada;
    }

    /**
     * Método adicional para reenviar una orden existente a RabbitMQ.
     *
     * @param orden La orden a reenviar.
     */
    public void reenviarOrdenARabbitMQ(final Orden orden) {
        try {
            OrdenConDetallesDTO ordenDTO = OrdenConDetallesDTO.from(orden);
            ordenPublisherService.publicarOrdenConMensaje(ordenDTO, "Reenvío de orden");
        } catch (Exception e) {
            System.err.println("Error al reenviar la orden: " + e.getMessage());
            throw new RuntimeException("Error al reenviar la orden a RabbitMQ", e);
        }
    }
}

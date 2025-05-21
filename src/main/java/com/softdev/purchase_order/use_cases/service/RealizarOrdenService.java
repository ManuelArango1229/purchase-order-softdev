package com.softdev.purchase_order.use_cases.service;

import com.softdev.purchase_order.use_cases.dto.request.ProductoOrdenRequest;
import com.softdev.purchase_order.use_cases.dto.request.RealizarOrdenRequest;
import com.softdev.purchase_order.use_cases.dto.response.UsuarioResponse;
import com.softdev.purchase_order.domain.entities.DetalleOrden;
import com.softdev.purchase_order.domain.entities.EstadoOrden;
import com.softdev.purchase_order.domain.entities.MetodoPago;
import com.softdev.purchase_order.domain.entities.Orden;
import com.softdev.purchase_order.domain.repositories.OrdenRepositoryPort;
import com.softdev.purchase_order.domain.repositories.ProductoServicePort;
import com.softdev.purchase_order.domain.repositories.RealizarOrdenPort;
import com.softdev.purchase_order.domain.repositories.UsuarioServicePort;


import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Servicio para realizar una orden de compra.
 * Implementa la lógica de negocio para procesar una orden.
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
     * Constructor que inicializa el servicio con los repositorios necesarios.
     *
     * @param ordenRepositoryParam El repositorio de órdenes.
     * @param productoServicePortParam El servicio de productos.
     * @param usuarioServicePortParam El servicio de usuarios.
     */
    public RealizarOrdenService(final OrdenRepositoryPort ordenRepositoryParam,
                               final ProductoServicePort productoServicePortParam,
                               final UsuarioServicePort usuarioServicePortParam) {
        this.ordenRepository = ordenRepositoryParam;
        this.productoServicePort = productoServicePortParam;
        this.usuarioServicePort = usuarioServicePortParam;
    }

    /**
     * Realiza una orden de compra.
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
            detallesOrden,
            metodoPago,
            valorTotal,
            LocalDateTime.now(),
            EstadoOrden.CREADA
        );

        // 5. Guardar la orden
        return ordenRepository.save(orden);
    }
}

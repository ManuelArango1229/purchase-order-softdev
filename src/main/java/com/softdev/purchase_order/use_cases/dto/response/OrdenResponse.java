package com.softdev.purchase_order.use_cases.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Clase que representa la respuesta de una orden de compra.
 */
@AllArgsConstructor
@Getter
public class OrdenResponse {
    /**
     * Mensaje de respuesta.
     */
    private String mensaje;
    /**
     * Fecha de pedido.
     */
    private String fechaPedido;
    /**
     * Identificador único de la orden.
     */
    private String idOrden;
    /**
     * Lista de detalles de la orden.
     */
    private List<DetalleOrdenResponse> detalles;
    /**
     * Valor total de la orden.
     */
    private BigDecimal valorTotal;
    /**
     * Método de pago utilizado para la orden.
     */
    private String metodoPago;
    /**
     * DNI del cliente.
     */
    private String dniCliente;
    /**
     * URL para rastrear la orden.
     */
    private String urlRastreo;

    /**
     * Constructor de la clase OrdenResponse.
     * 
     * @param idOrden      Identificador único de la orden.
     * @param detalles     Lista de detalles de la orden.
     * @param valorTotal   Valor total de la orden.
     * @param metodoPago   Método de pago utilizado para la orden.
     * @param dniCliente   DNI del cliente.
     * @param fechaPedido  Fecha y hora en que se realizó el pedido.
     */
    public OrdenResponse(UUID idOrden, List<DetalleOrdenResponse> detalles, 
                         BigDecimal valorTotal, String metodoPago, 
                         String dniCliente, LocalDateTime fechaPedido) {
        this.mensaje = "Pedido realizado satisfactoriamente";
        this.idOrden = idOrden.toString();
        this.detalles = detalles;
        this.valorTotal = valorTotal;
        this.metodoPago = metodoPago;
        this.dniCliente = dniCliente;
        this.fechaPedido = fechaPedido.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.urlRastreo = "/pedidos/rastrear/" + idOrden;
    }
    /**
     * Constructor vacío para la deserialización de JSON.
     */
    public OrdenResponse() {
    }
}
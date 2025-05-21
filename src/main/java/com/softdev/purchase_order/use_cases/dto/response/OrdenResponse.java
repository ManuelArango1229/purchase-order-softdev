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
     * @param idOrdenParam      Identificador único de la orden.
     * @param detallesParam     Lista de detalles de la orden.
     * @param valorTotalParam   Valor total de la orden.
     * @param metodoPagoParam   Método de pago utilizado para la orden.
     * @param dniClienteParam   DNI del cliente.
     * @param fechaPedidoParam  Fecha y hora en que se realizó el pedido.
     */
    public OrdenResponse(final UUID idOrdenParam, final List<DetalleOrdenResponse> detallesParam,
                         final BigDecimal valorTotalParam, final String metodoPagoParam,
                         final String dniClienteParam, final LocalDateTime fechaPedidoParam) {
        this.mensaje = "Pedido realizado satisfactoriamente";
        this.idOrden = idOrdenParam.toString();
        this.detalles = detallesParam;
        this.valorTotal = valorTotalParam;
        this.metodoPago = metodoPagoParam;
        this.dniCliente = dniClienteParam;
        this.fechaPedido = fechaPedidoParam.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.urlRastreo = "/pedidos/rastrear/" + idOrdenParam;
    }
    /**
     * Constructor vacío para la deserialización de JSON.
     */
    public OrdenResponse() {
    }
}

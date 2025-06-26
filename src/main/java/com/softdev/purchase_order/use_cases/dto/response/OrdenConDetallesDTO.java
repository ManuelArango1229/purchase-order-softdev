package com.softdev.purchase_order.use_cases.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.softdev.purchase_order.domain.entities.Orden;
import com.softdev.purchase_order.domain.entities.DetalleOrden;
import com.softdev.purchase_order.domain.entities.MetodoPago;

import lombok.Data;

/**
 * DTO que representa una orden con sus detalles, excluyendo información sensible.
 * Incluye el ID de la orden, datos del cliente, detalles de la factura y método de pago.
 */
@Data
public class OrdenConDetallesDTO {

    /**
     * Identificador único de la orden.
     */
    private String id;
    /**
     * Información del cliente, excluyendo datos sensibles.
     */
    private String emailCliente;
    /**
     * Nombre del cliente, excluyendo datos sensibles.
     */
    private String nombreCliente;
    /**
     * DNI del cliente, excluyendo datos sensibles.
     */
    private String dniCliente;
    /**
     * Dirección de entrega de la orden, excluyendo datos sensibles.
     * Este campo puede ser opcional o no estar presente en algunos casos.
     */
    private String direccion;
    /**
     * Lista de detalles de la orden, sin información sensible como el ID de la orden.
     */
    private List<DetalleFacturaDTO> detalles;
    /**
     * Método de pago utilizado para la orden, sin datos sensibles.
     */
    private MetodoPagoFacturaDTO metodoPago;
    /**
     * Valor total de la orden.
     */
    private double valorTotal;
    /**
     * Fecha y hora en que se realizó el pedido.
     */
    private String fechaPedido;

    /**
     * Método estático para crear un DTO a partir de una entidad Orden.
     * Mapea los detalles de la orden y el método de pago, excluyendo información sensible.
     *
     * @param orden La entidad Orden a mapear.
     * @return Un objeto OrdenConDetallesDTO con la información mapeada.
     */
    public static OrdenConDetallesDTO from(final Orden orden) {
        OrdenConDetallesDTO dto = new OrdenConDetallesDTO();
        dto.id = orden.getId().toString();
        dto.emailCliente = orden.getEmailCliente();
        dto.nombreCliente = orden.getNombreCliente();
        dto.dniCliente = orden.getDniCliente();
        dto.direccion = orden.getDireccion();
        System.out.println("Direccion de entrega: " + dto.direccion);
        dto.valorTotal = orden.getValorTotal().doubleValue();
        dto.fechaPedido = orden.getFechaPedido().toString();

        // Mapeo filtrado de detalles (sin ordenId)
        dto.detalles = orden.getDetalles().stream()
                .map(DetalleFacturaDTO::from)
                .collect(Collectors.toList());

        // Método de pago sin datos sensibles y con nombre obligatorio
        dto.metodoPago = MetodoPagoFacturaDTO.from(orden.getMetodoPago());

        return dto;
    }

    @Data
    public static class DetalleFacturaDTO {
        /**
         * Nombre del producto en el detalle de la orden.
         */
        private String nombreProducto;
        /**
         * Cantidad del producto en el detalle de la orden.
         */
        private int cantidad;
        /**
         * Precio unitario del producto en el detalle de la orden.
         */
        private double precioUnitario;
        /**
         * Subtotal del detalle de la orden (cantidad * precio unitario).
         */
        private double subtotal;

        /**
         * Método estático para crear un DTO de DetalleFacturaDTO a partir de un DetalleOrden.
         * Mapea los campos necesarios, excluyendo el ID de la orden.
         *
         * @param detalle El DetalleOrden a mapear.
         * @return Un objeto DetalleFacturaDTO con la información mapeada.
         */
        public static DetalleFacturaDTO from(final DetalleOrden detalle) {
            DetalleFacturaDTO d = new DetalleFacturaDTO();
            d.nombreProducto = detalle.getNombreProducto();
            d.cantidad = detalle.getCantidad();
            d.precioUnitario = detalle.getPrecioUnitario().doubleValue();
            d.subtotal = detalle.getSubtotal().doubleValue();
            return d;
        }
    }

    /**
     * DTO que representa el método de pago utilizado en la orden, excluyendo datos sensibles.
     * Incluye el nombre del método de pago, que es obligatorio.
     */
    @Data
    public static class MetodoPagoFacturaDTO {
        /**
         * Nombre del método de pago utilizado en la orden.
         * Este campo es obligatorio y se asigna un valor por defecto si es null.
         */
        private String nombre;

        /**
         * Método estático para crear un DTO de MetodoPagoFacturaDTO a partir de un MetodoPago.
         * Asigna un valor por defecto al nombre si es null.
         *
         * @param metodoPago El MetodoPago a mapear.
         * @return Un objeto MetodoPagoFacturaDTO con la información mapeada.
         */
        public static MetodoPagoFacturaDTO from(final MetodoPago metodoPago) {
            MetodoPagoFacturaDTO m = new MetodoPagoFacturaDTO();
            // Si nombre es null, asignar un valor por defecto
            m.nombre = metodoPago.getNombre() != null ? metodoPago.getNombre() : "Tarjeta de crédito";
            return m;
        }
    }
    /**
     * Método toString para representar el DTO como una cadena.
     * Incluye todos los campos relevantes, excluyendo información sensible.
     *
     * @return Una representación en cadena del objeto OrdenConDetallesDTO.
     */
    @Override
    public String toString() {
        return "OrdenConDetallesDTO{"
                + "id='" + id + '\''
                + ", emailCliente='" + emailCliente + '\''
                + ", nombreCliente='" + nombreCliente + '\''
                + ", dniCliente='" + dniCliente + '\''
                + ", direccion='" + direccion + '\''
                + ", detalles=" + detalles
                + ", metodoPago=" + metodoPago
                + ", valorTotal=" + valorTotal
                + ", fechaPedido='" + fechaPedido + '\''
                + '}';
    }
}

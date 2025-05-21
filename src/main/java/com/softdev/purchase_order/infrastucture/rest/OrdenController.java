package com.softdev.purchase_order.infrastucture.rest;

import com.softdev.purchase_order.use_cases.dto.request.RealizarOrdenRequest;
import com.softdev.purchase_order.use_cases.dto.response.DetalleOrdenResponse;
import com.softdev.purchase_order.use_cases.dto.response.OrdenResponse;
import com.softdev.purchase_order.domain.entities.Orden;
import com.softdev.purchase_order.domain.repositories.RealizarOrdenPort;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para manejar las operaciones relacionadas con las órdenes de compra.
 */
@RestController
@RequestMapping("/ordenes")
public class OrdenController {

    /**
     * Puerto para realizar operaciones de orden.
     */
    private final RealizarOrdenPort realizarOrdenPort;

    /**
     * Constructor que inicializa el controlador con el puerto de orden.
     *
     * @param realizarOrdenPortParam Puerto para realizar operaciones de orden.
     */
    public OrdenController(final RealizarOrdenPort realizarOrdenPortParam) {
        this.realizarOrdenPort = realizarOrdenPortParam;
    }

    /**
     * Endpoint para realizar una orden de compra.
     *
     * @param request El objeto que contiene la información de la orden.
     * @param token   El token de autorización del cliente.
     * @return La respuesta con la información de la orden creada.
     */
    @PostMapping("/realizarOrden")
    public ResponseEntity<OrdenResponse> realizarOrden(
            final @RequestBody RealizarOrdenRequest request,
            final @RequestHeader("Authorization") String token) {

        // Extraer el email del token (esto es un ejemplo, deberías implementar la lógica real)
        String emailCliente = extraerEmailDelToken(token);

        // Procesar la orden
        Orden orden = realizarOrdenPort.realizarOrden(request, emailCliente);

        // Mapear respuesta
        List<DetalleOrdenResponse> detallesResponse = orden.getDetalles().stream()
                .map(detalle -> new DetalleOrdenResponse(
                        detalle.getNombreProducto(),
                        detalle.getPrecioUnitario(),
                        detalle.getCantidad(),
                        detalle.getSubtotal()))
                .collect(Collectors.toList());

        OrdenResponse response = new OrdenResponse(
                orden.getId(),
                detallesResponse,
                orden.getValorTotal(),
                orden.getMetodoPago().getNombre(),
                orden.getDniCliente(),
                orden.getFechaPedido()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Método privado para extraer el email del token JWT.
     * Este es un ejemplo simplificado. En un escenario real, deberías usar una biblioteca JWT para decodificar el token.
     *
     * @param token El token JWT.
     * @return El email extraído del token.
     */
    // Este método es simplificado. En un escenario real, utilizarías un servicio de autenticación
    private String extraerEmailDelToken(final String token) {
        // Aquí deberías implementar la lógica real para extraer el email del token JWT
        // Por simplicidad, simulamos la extracción
        if (token != null && token.startsWith("Bearer ")) {
            //String jwtToken = token.substring(7);
            // En un caso real, aquí decodificarías el token JWT y extraerías el claim 'email'
            // Por ahora, simplemente retornamos un email de prueba
            return "test9@correo.com";
        }
        throw new RuntimeException("Token inválido o no proporcionado");
    }
}

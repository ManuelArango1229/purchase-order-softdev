package com.softdev.purchase_order.domain.repositories;

import com.softdev.purchase_order.domain.entities.Orden;
import com.softdev.purchase_order.use_cases.dto.request.RealizarOrdenRequest;

/**
 * Interfaz que define el caso de uso para realizar una orden.
 */
public interface RealizarOrdenPort {
    /**
     * Realiza una orden y la guarda en el repositorio.
     *
     * @param request La solicitud para realizar la orden.
     * @param emailCliente El correo electrónico del cliente que realiza la orden.
     * @return La orden realizada.
     */
    Orden realizarOrden(RealizarOrdenRequest request, String emailCliente);
}

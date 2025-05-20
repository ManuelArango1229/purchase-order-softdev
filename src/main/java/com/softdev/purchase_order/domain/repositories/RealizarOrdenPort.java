package com.softdev.purchase_order.domain.repositories;

import com.softdev.purchase_order.domain.entities.Orden;

/**
 * Interfaz que define el caso de uso para realizar una orden.
 */
public interface RealizarOrdenUseCase {
    /**
     * Realiza una orden y la guarda en el repositorio.
     *
     * @param request La solicitud para realizar la orden.
     * @param emailCliente El correo electrónico del cliente que realiza la orden.
     * @return La orden realizada.
     */
    Orden realizarOrden(RealizarOrdenRequest request, String emailCliente);
}

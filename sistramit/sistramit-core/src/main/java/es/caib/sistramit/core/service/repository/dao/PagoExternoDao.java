package es.caib.sistramit.core.service.repository.dao;

import es.caib.sistramit.core.api.model.flujo.RetornoPago;

/**
 * Interfaz de acceso a base de datos para los datos de sesión de pago externo.
 *
 * @author Indra
 *
 */
public interface PagoExternoDao {

    /**
     * Genera ticket de retorno para pago.
     *
     * @param retornoPago
     *            datos retorno pago
     * @return ticket retorno
     */
    String generarTicketPago(RetornoPago retornoPago);

    /**
     * Devuelve datos retorno pago.
     *
     * @param ticket
     *            Ticket
     * @return Datos retorno pago
     */
    RetornoPago consumirTicketPago(String ticket);

}

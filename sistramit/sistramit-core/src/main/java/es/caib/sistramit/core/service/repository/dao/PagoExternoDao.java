package es.caib.sistramit.core.service.repository.dao;

import java.util.Date;

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
	 * Consume ticket retorno pago (un solo uso).
	 *
	 * @param ticket
	 *            Ticket
	 * @return Datos retorno pago
	 */
	RetornoPago consumirTicketPago(String ticket);

	/**
	 * Obtiene datos retorno pago (debe haber sido consumido antes).
	 *
	 * @param ticket
	 *            Ticket
	 * @return Datos retorno pago
	 */
	RetornoPago obtenerTicketPago(String ticket);

	/**
	 * Borra pagos externos finalizados caducados.
	 *
	 * @param fechaLimiteInicio
	 * @param limiteBorrado
	 *
	 * @return
	 */
	public int purgarPagosExternosFinalizados(final Date fechaLimiteInicio);

	/**
	 * Borra pagos externos NO finalizados caducados.
	 *
	 * @param fechaLimiteFin
	 * @param limiteBorrado
	 *
	 * @return
	 */
	public int purgarPagosExternosNoFinalizados(final Date fechaLimiteFin);

}

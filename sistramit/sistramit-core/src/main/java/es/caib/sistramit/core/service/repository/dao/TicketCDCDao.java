package es.caib.sistramit.core.service.repository.dao;

import es.caib.sistramit.core.api.model.system.rest.externo.InfoTicketAcceso;

/**
 * Interfaz de acceso a base de datos para los datos de sesión de pago externo.
 *
 * @author Indra
 *
 */
public interface TicketCDCDao {

	/**
	 * Genera ticket de retorno para pago.
	 *
	 * @param retornoPago
	 *            datos retorno pago
	 * @return ticket retorno
	 */
	String generarTicketAcceso(InfoTicketAcceso pInfoTicketAcceso);

}

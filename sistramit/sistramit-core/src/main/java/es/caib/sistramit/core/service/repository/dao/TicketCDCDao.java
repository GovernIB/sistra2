package es.caib.sistramit.core.service.repository.dao;

import es.caib.sistramit.core.api.model.system.rest.externo.InfoTicketAcceso;

/**
 * Interfaz de acceso a base de datos para acceso desde carpeta ciudadana (CDC).
 *
 * @author Indra
 *
 */
public interface TicketCDCDao {

	/**
	 * Genera ticket para acceso desde CDC.
	 *
	 * @param pInfoTicketAcceso
	 *            datos acceso CDC
	 * @return ticket retorno
	 */
	String generarTicketAcceso(InfoTicketAcceso pInfoTicketAcceso);

	/**
	 * Obtiene ticket acceso.
	 *
	 * @param ticket
	 *            ticket
	 * @return Información ticket acceso
	 */
	InfoTicketAcceso obtieneTicketAcceso(String ticket);

	/**
	 * Consume ticket acceso.
	 * 
	 * @param ticket
	 *            Ticket
	 */
	void consumirTicketAcceso(String ticket);

}

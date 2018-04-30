package es.caib.sistramit.frontend.model;

/**
 * Información para la página de login via ticket.
 *
 * @author Indra
 *
 */
public final class LoginTicketInfo {

	/**
	 * Nombre ticket.
	 */
	private String ticketName;

	/**
	 * Valor ticket.
	 */
	private String ticketValue;

	/**
	 * Idioma.
	 */
	private String idioma;

	/**
	 * Método de acceso a ticketName.
	 *
	 * @return ticketName
	 */
	public String getTicketName() {
		return ticketName;
	}

	/**
	 * Método para establecer ticketName.
	 *
	 * @param pTicketName
	 *            ticketName a establecer
	 */
	public void setTicketName(final String pTicketName) {
		ticketName = pTicketName;
	}

	/**
	 * Método de acceso a ticketValue.
	 *
	 * @return ticketValue
	 */
	public String getTicketValue() {
		return ticketValue;
	}

	/**
	 * Método para establecer ticketValue.
	 *
	 * @param pTicketValue
	 *            ticketValue a establecer
	 */
	public void setTicketValue(final String pTicketValue) {
		ticketValue = pTicketValue;
	}

	/**
	 * Método de acceso a idioma.
	 *
	 * @return idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Método para establecer idioma.
	 *
	 * @param pIdioma
	 *            idioma a establecer
	 */
	public void setIdioma(final String pIdioma) {
		idioma = pIdioma;
	}

}

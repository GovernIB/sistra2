package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;

/**
 * Datos necesarios para retorno formulario externo.
 *
 * @author Indra
 *
 */
public class RetornoFormularioExterno implements ModelApi {

	/** Id sesion tramitacion. */
	private String idSesionTramitacion;

	/** Ticket sesion formulario. */
	private String ticket;

	/** Id paso. */
	private String idPaso;

	/** Id formuario. */
	private String idFormulario;

	/** Usuario. */
	private UsuarioAutenticadoInfo usuario;

	/**
	 * Método de acceso a idSesionTramitacion.
	 *
	 * @return idSesionTramitacion
	 */
	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	/**
	 * Método para establecer idSesionTramitacion.
	 *
	 * @param idSesionTramitacion
	 *                                idSesionTramitacion a establecer
	 */
	public void setIdSesionTramitacion(final String idSesionTramitacion) {
		this.idSesionTramitacion = idSesionTramitacion;
	}

	/**
	 * Método de acceso a idPaso.
	 *
	 * @return idPaso
	 */
	public String getIdPaso() {
		return idPaso;
	}

	/**
	 * Método para establecer idPaso.
	 *
	 * @param idPaso
	 *                   idPaso a establecer
	 */
	public void setIdPaso(final String idPaso) {
		this.idPaso = idPaso;
	}

	/**
	 * Método de acceso a idFormulario.
	 *
	 * @return idFormulario
	 */
	public String getIdFormulario() {
		return idFormulario;
	}

	/**
	 * Método para establecer idFormulario.
	 *
	 * @param idFormulario
	 *                         idFormulario a establecer
	 */
	public void setIdFormulario(final String idFormulario) {
		this.idFormulario = idFormulario;
	}

	/**
	 * Método de acceso a usuario.
	 *
	 * @return usuario
	 */
	public UsuarioAutenticadoInfo getUsuario() {
		return usuario;
	}

	/**
	 * Método para establecer usuario.
	 *
	 * @param usuario
	 *                    usuario a establecer
	 */
	public void setUsuario(final UsuarioAutenticadoInfo usuario) {
		this.usuario = usuario;
	}

	/**
	 * Método de acceso a ticket.
	 * 
	 * @return ticket
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * Método para establecer ticket.
	 * 
	 * @param ticket
	 *                   ticket a establecer
	 */
	public void setTicket(final String ticket) {
		this.ticket = ticket;
	}

}

package es.caib.sistramit.core.service.model.formulario.interno;

import es.caib.sistramit.core.service.model.formulario.DatosInicioSesionFormulario;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;

/**
 * Datos de la sesión del formulario que se mantienen en memoria (datos páginas,
 * definición formulario, etc.).
 *
 * @author Indra
 *
 */
public final class DatosSesionFormularioInterno {

	/**
	 * Id formulario.
	 */
	private String idFormulario;

	/**
	 * Ticket de sesion de formulario.
	 */
	private String ticket;

	/**
	 * Definicion del tramite.
	 */
	private DefinicionTramiteSTG definicionTramite;

	/**
	 * Datos iniciales de sesion.
	 */
	private DatosInicioSesionFormulario datosInicioSesion;

	/**
	 * Datos formulario.
	 */
	private DatosFormularioInterno datosFormulario;

	/**
	 * Debug habilitado.
	 */
	private boolean debugEnabled;

	/**
	 * Indica si el formulario se ha finalizado.
	 */
	private boolean finalizada;

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
	 *            idFormulario a establecer
	 */
	public void setIdFormulario(String idFormulario) {
		this.idFormulario = idFormulario;
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
	 *            ticket a establecer
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	/**
	 * Método de acceso a definicionTramite.
	 * 
	 * @return definicionTramite
	 */
	public DefinicionTramiteSTG getDefinicionTramite() {
		return definicionTramite;
	}

	/**
	 * Método para establecer definicionTramite.
	 * 
	 * @param definicionTramite
	 *            definicionTramite a establecer
	 */
	public void setDefinicionTramite(DefinicionTramiteSTG definicionTramite) {
		this.definicionTramite = definicionTramite;
	}

	/**
	 * Método de acceso a datosInicioSesion.
	 * 
	 * @return datosInicioSesion
	 */
	public DatosInicioSesionFormulario getDatosInicioSesion() {
		return datosInicioSesion;
	}

	/**
	 * Método para establecer datosInicioSesion.
	 * 
	 * @param datosInicioSesion
	 *            datosInicioSesion a establecer
	 */
	public void setDatosInicioSesion(DatosInicioSesionFormulario datosInicioSesion) {
		this.datosInicioSesion = datosInicioSesion;
	}

	/**
	 * Método de acceso a datosFormulario.
	 * 
	 * @return datosFormulario
	 */
	public DatosFormularioInterno getDatosFormulario() {
		return datosFormulario;
	}

	/**
	 * Método para establecer datosFormulario.
	 * 
	 * @param datosFormulario
	 *            datosFormulario a establecer
	 */
	public void setDatosFormulario(DatosFormularioInterno datosFormulario) {
		this.datosFormulario = datosFormulario;
	}

	/**
	 * Método de acceso a debugEnabled.
	 * 
	 * @return debugEnabled
	 */
	public boolean isDebugEnabled() {
		return debugEnabled;
	}

	/**
	 * Método para establecer debugEnabled.
	 * 
	 * @param debugEnabled
	 *            debugEnabled a establecer
	 */
	public void setDebugEnabled(boolean debugEnabled) {
		this.debugEnabled = debugEnabled;
	}

	/**
	 * Método de acceso a finalizada.
	 * 
	 * @return finalizada
	 */
	public boolean isFinalizada() {
		return finalizada;
	}

	/**
	 * Método para establecer finalizada.
	 * 
	 * @param finalizada
	 *            finalizada a establecer
	 */
	public void setFinalizada(boolean finalizada) {
		this.finalizada = finalizada;
	}

}

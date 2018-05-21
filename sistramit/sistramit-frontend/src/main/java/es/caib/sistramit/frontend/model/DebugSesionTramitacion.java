package es.caib.sistramit.frontend.model;

import java.io.Serializable;
import java.util.List;

import es.caib.sistramit.core.api.model.comun.LogEvento;

/**
 * Informacion debug sesion tramitación.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DebugSesionTramitacion implements Serializable {

	/**
	 * Id sesion tramitacion.
	 */
	private String idSesionTramitacion;

	/**
	 * Entorno.
	 */
	private String entorno;

	/**
	 * Id tramite.
	 */
	private String idTramite;

	/**
	 * Eventos.
	 */
	private List<LogEvento> eventos;

	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	public void setIdSesionTramitacion(final String idSesionTramitacion) {
		this.idSesionTramitacion = idSesionTramitacion;
	}

	public String getEntorno() {
		return entorno;
	}

	public void setEntorno(final String entorno) {
		this.entorno = entorno;
	}

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(final String idTramite) {
		this.idTramite = idTramite;
	}

	public List<LogEvento> getEventos() {
		return eventos;
	}

	public void setEventos(final List<LogEvento> eventos) {
		this.eventos = eventos;
	}

}

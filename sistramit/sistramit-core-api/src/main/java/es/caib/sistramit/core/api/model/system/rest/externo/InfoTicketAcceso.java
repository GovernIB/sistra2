package es.caib.sistramit.core.api.model.system.rest.externo;

import java.io.Serializable;
import java.util.Date;

import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;

/**
 * Información ticket acceso.
 *
 * @author Indra
 *
 */

@SuppressWarnings("serial")
public class InfoTicketAcceso implements Serializable {

	/** Id sesión tramitación. */
	private String idSesionTramitacion;

	/** Info usuario autenticado. */
	private UsuarioAutenticadoInfo usuarioAutenticadoInfo;

	/** Indica si ticket se ha usado. */
	private boolean usado;

	/** Indica fecha ticket. */
	private Date fecha;

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
	 *            idSesionTramitacion a establecer
	 */
	public void setIdSesionTramitacion(String idSesionTramitacion) {
		this.idSesionTramitacion = idSesionTramitacion;
	}

	/**
	 * Método de acceso a usuarioAutenticadoInfo.
	 *
	 * @return usuarioAutenticadoInfo
	 */
	public UsuarioAutenticadoInfo getUsuarioAutenticadoInfo() {
		return usuarioAutenticadoInfo;
	}

	/**
	 * Método para establecer usuarioAutenticadoInfo.
	 *
	 * @param usuarioAutenticadoInfo
	 *            usuarioAutenticadoInfo a establecer
	 */
	public void setUsuarioAutenticadoInfo(UsuarioAutenticadoInfo usuarioAutenticadoInfo) {
		this.usuarioAutenticadoInfo = usuarioAutenticadoInfo;
	}

	/**
	 * Método de acceso a usado.
	 *
	 * @return usado
	 */
	public boolean isUsado() {
		return usado;
	}

	/**
	 * Método para establecer usado.
	 *
	 * @param usado
	 *            usado a establecer
	 */
	public void setUsado(boolean usado) {
		this.usado = usado;
	}

	/**
	 * Método de acceso a fecha.
	 *
	 * @return fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Método para establecer fecha.
	 *
	 * @param fecha
	 *            fecha a establecer
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}

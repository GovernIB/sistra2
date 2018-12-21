package es.caib.sistramit.core.api.model.system.rest.externo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;

/**
 * Filtros para los eventos (RestApiExternaService)
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class InfoTicketAcceso implements Serializable {

	private String idSesionTramitacion;

	private UsuarioAutenticadoInfo usuarioAutenticadoInfo;

	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	public void setIdSesionTramitacion(final String idSesionTramitacion) {
		this.idSesionTramitacion = idSesionTramitacion;
	}

	public UsuarioAutenticadoInfo getUsuarioAutenticadoInfo() {
		return usuarioAutenticadoInfo;
	}

	public void setUsuarioAutenticadoInfo(final UsuarioAutenticadoInfo usuarioAutenticadoInfo) {
		this.usuarioAutenticadoInfo = usuarioAutenticadoInfo;
	}

}

package es.caib.sistramit.rest.api.externa.v1;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Datos para la obtención de ticket de acceso
 *
 * @author Indra
 *
 */
@ApiModel(value = "RInfoTicketAcceso", description = "Datos para la obtención de ticket de acceso")
public class RInfoTicketAcceso {

	/**
	 * Identificador de la sesión.
	 */
	@ApiModelProperty(value = "Identificador de la sesión")
	private String idSesionTramitacion;

	@ApiModelProperty(value = "Información de acceso")
	private RUsuarioAutenticadoInfo usuarioAutenticadoInfo;

	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	public void setIdSesionTramitacion(final String idSesionTramitacion) {
		this.idSesionTramitacion = idSesionTramitacion;
	}

	public RUsuarioAutenticadoInfo getUsuarioAutenticadoInfo() {
		return usuarioAutenticadoInfo;
	}

	public void setUsuarioAutenticadoInfo(final RUsuarioAutenticadoInfo usuarioAutenticadoInfo) {
		this.usuarioAutenticadoInfo = usuarioAutenticadoInfo;
	}
}

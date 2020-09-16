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

	/** Identificador de la sesión. */
	@ApiModelProperty(value = "Identificador de la sesión")
	private String idSesionTramitacion;

	/** Usuario autenticado. */
	@ApiModelProperty(value = "Información de acceso")
	private RUsuarioAutenticadoInfo usuarioAutenticadoInfo;

	/** Url callback error en caso de no poder acceder. */
	@ApiModelProperty(value = "Url callback error en caso de no poder acceder")
	private String urlCallbackError;

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

	/**
	 * Método de acceso a urlCallbackError.
	 * 
	 * @return urlCallbackError
	 */
	public String getUrlCallbackError() {
		return urlCallbackError;
	}

	/**
	 * Método para establecer urlCallbackError.
	 * 
	 * @param urlCallbackError
	 *                             urlCallbackError a establecer
	 */
	public void setUrlCallbackError(final String urlCallbackError) {
		this.urlCallbackError = urlCallbackError;
	}
}

package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * Area
 *
 * @author Indra
 *
 */
@ApiModel(value = "REnvioRemoto", description = "Envío remoto")
public class REnvioRemoto {

	/** Identificador. */
	@ApiModelProperty(value = "Identificador")
	private String identificador;

	/** Url. */
	@ApiModelProperty(value = "Url")
	private String url;

	/** Timeout. */
	@ApiModelProperty(value = "timeout")
	private String timeout;

	/** Id configuracion autenticación. */
	@ApiModelProperty(value = "Identificador configuración autenticación")
	private String identificadorConfAutenticacion;

	/** Identificador área (nulo si global). */
	@ApiModelProperty(value = "Identificador área. Será nulo si ámbito global")
	private String identificadorArea;

	/**
	 * Método de acceso a id.
	 *
	 * @return id
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Método para establecer id.
	 *
	 * @param id
	 *               id a establecer
	 */
	public void setIdentificador(final String id) {
		this.identificador = id;
	}

	/**
	 * Método de acceso a url.
	 *
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Método para establecer url.
	 *
	 * @param url
	 *                url a establecer
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * Método de acceso a timeout.
	 *
	 * @return timeout
	 */
	public String getTimeout() {
		return timeout;
	}

	/**
	 * Método para establecer timeout.
	 *
	 * @param timeout
	 *                    timeout a establecer
	 */
	public void setTimeout(final String timeout) {
		this.timeout = timeout;
	}

	/**
	 * Método de acceso a idConfiguracionAutenticacion.
	 *
	 * @return idConfiguracionAutenticacion
	 */
	public String getIdentificadorConfAutenticacion() {
		return identificadorConfAutenticacion;
	}

	/**
	 * Método para establecer idConfiguracionAutenticacion.
	 *
	 * @param idConfiguracionAutenticacion
	 *                                         idConfiguracionAutenticacion a
	 *                                         establecer
	 */
	public void setIdentificadorConfAutenticacion(final String idConfiguracionAutenticacion) {
		this.identificadorConfAutenticacion = idConfiguracionAutenticacion;
	}

	/**
	 * Método de acceso a identificadorArea.
	 * 
	 * @return identificadorArea
	 */
	public String getIdentificadorArea() {
		return identificadorArea;
	}

	/**
	 * Método para establecer identificadorArea.
	 * 
	 * @param identificadorArea
	 *                              identificadorArea a establecer
	 */
	public void setIdentificadorArea(final String identificadorArea) {
		this.identificadorArea = identificadorArea;
	}

}

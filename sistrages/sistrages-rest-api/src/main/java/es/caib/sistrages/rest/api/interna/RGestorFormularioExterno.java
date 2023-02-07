package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Plugin.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RGestorFormularioExterno", description = "Gestor formulario externo")
public class RGestorFormularioExterno {

	/** Identificador. */
	@ApiModelProperty(value = "Identificador")
	private String identificador;

	/** Identificador entidad (nulo si global). */
	@ApiModelProperty(value = "Identificador entidad (Código DIR3). Será nulo si ámbito global")
	private String identificadorEntidad;

	/** Url. */
	@ApiModelProperty(value = "Url")
	private String url;

	/** Configuracion autenticacion. */
	@ApiModelProperty(value = "Configuracion autenticacion")
	private String identificadorConfAutenticacion;

	/** Identificador área. */
	@ApiModelProperty(value = "Identificador área.")
	private String identificadorArea;

	/**
	 * Método de acceso a identificador.
	 *
	 * @return identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Método para establecer identificador.
	 *
	 * @param identificador
	 *                          identificador a establecer
	 */
	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
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
	 * Método de acceso a identificadorEntidad.
	 *
	 * @return identificadorEntidad
	 */
	public String getIdentificadorEntidad() {
		return identificadorEntidad;
	}

	/**
	 * Método para establecer identificadorEntidad.
	 *
	 * @param identificadorEntidad
	 *                                 identificadorEntidad a establecer
	 */
	public void setIdentificadorEntidad(final String identificadorEntidad) {
		this.identificadorEntidad = identificadorEntidad;
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

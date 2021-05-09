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

	/** Url. */
	@ApiModelProperty(value = "Url")
	private String url;

	/** Configuracion autenticacion. */
	@ApiModelProperty(value = "Configuracion autenticacion")
    private RConfiguracionAutenticacion configuracionAutenticacion;
	
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
	 * @return the configuracionAutenticacion
	 */
	public RConfiguracionAutenticacion getConfiguracionAutenticacion() {
		return configuracionAutenticacion;
	}

	/**
	 * @param configuracionAutenticacion the configuracionAutenticacion to set
	 */
	public void setConfiguracionAutenticacion(RConfiguracionAutenticacion configuracionAutenticacion) {
		this.configuracionAutenticacion = configuracionAutenticacion;
	}

}

package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Plantilla entidad.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPlantillaEntidad", description = "Plantilla entidad")
public class RPlantillaEntidad {

	/**
	 * Idioma.
	 */
	@ApiModelProperty(value = "Idioma")
	private String idioma;

	/**
	 * Tipo.
	 */
	@ApiModelProperty(value = "Tipo plantilla (FR: Finalización registro)")
	private String tipo;

	/**
	 * Path.
	 */
	@ApiModelProperty(value = "Path plantilla")
	private String path;

	/**
	 * Método de acceso a idioma.
	 *
	 * @return idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Método para establecer idioma.
	 *
	 * @param idioma
	 *                   idioma a establecer
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Método de acceso a tipo.
	 *
	 * @return tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipo.
	 *
	 * @param tipo
	 *                 tipo a establecer
	 */
	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Método de acceso a path.
	 *
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Método para establecer path.
	 *
	 * @param path
	 *                 path a establecer
	 */
	public void setPath(final String path) {
		this.path = path;
	}

}

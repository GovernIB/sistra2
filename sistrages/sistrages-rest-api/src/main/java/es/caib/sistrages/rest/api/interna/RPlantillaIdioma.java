package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Plantilla formulario.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPlantillaIdioma", description = "Plantilla por idioma")
public class RPlantillaIdioma {

	/** Idioma. */
	@ApiModelProperty(value = "Idioma")
	private String idioma;

	/** Clase formateador (classname). */
	@ApiModelProperty(value = "plantilla)")
	private RPlantillaFormulario plantilla;

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
	 *            idioma a establecer
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Método de acceso a plantilla.
	 * 
	 * @return plantilla
	 */
	public RPlantillaFormulario getPlantilla() {
		return plantilla;
	}

	/**
	 * Método para establecer plantilla.
	 * 
	 * @param plantilla
	 *            plantilla a establecer
	 */
	public void setPlantilla(RPlantillaFormulario plantilla) {
		this.plantilla = plantilla;
	}

}

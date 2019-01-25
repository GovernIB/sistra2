package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Propiedades campo texto normal.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPropiedadesTextoNormal", description = "Descripcion de RPropiedadesTextoNormal")
public class RPropiedadesTextoNormal {

	/** Texto normal: tamaño máximo. */
	@ApiModelProperty(value = "Texto normal: tamaño máximo")
	private int tamanyoMax;

	/** Texto normal: multilinea. */
	@ApiModelProperty(value = "Texto normal: multilinea")
	private int lineas;

	/**
	 * Método de acceso a tamanyoMax.
	 *
	 * @return tamanyoMax
	 */
	public int getTamanyoMax() {
		return tamanyoMax;
	}

	/**
	 * Método para establecer tamanyoMax.
	 *
	 * @param tamanyoMax
	 *            tamanyoMax a establecer
	 */
	public void setTamanyoMax(int tamanyoMax) {
		this.tamanyoMax = tamanyoMax;
	}

	/**
	 * Método de acceso a lineas.
	 * 
	 * @return lineas
	 */
	public int getLineas() {
		return lineas;
	}

	/**
	 * Método para establecer lineas.
	 * 
	 * @param lineas
	 *            lineas a establecer
	 */
	public void setLineas(int lineas) {
		this.lineas = lineas;
	}

}

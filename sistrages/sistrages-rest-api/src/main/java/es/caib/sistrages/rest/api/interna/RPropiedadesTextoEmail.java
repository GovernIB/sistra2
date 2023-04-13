package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Propiedades campo texto normal.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPropiedadesTextoEmail", description = "Descripcion de RPropiedadesTextoEmail")
public class RPropiedadesTextoEmail {

	/** Texto normal: tamaño máximo. */
	@ApiModelProperty(value = "Texto normal: tamaño máximo")
	private int tamanyoMax;

	@ApiModelProperty(value = "Prevenir pegar")
	private boolean prevenirPegar;

	/**
	 * @return the prevenirPegar
	 */
	public final boolean isPrevenirPegar() {
		return prevenirPegar;
	}

	/**
	 * @param prevenirPegar the prevenirPegar to set
	 */
	public final void setPrevenirPegar(boolean prevenirPegar) {
		this.prevenirPegar = prevenirPegar;
	}

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
	 * @param tamanyoMax tamanyoMax a establecer
	 */
	public void setTamanyoMax(final int tamanyoMax) {
		this.tamanyoMax = tamanyoMax;
	}

}

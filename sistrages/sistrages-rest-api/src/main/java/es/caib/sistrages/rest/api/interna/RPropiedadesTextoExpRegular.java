package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Propiedades campo texto expresión regular.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPropiedadesTextoExpRegular", description = "Descripcion de RPropiedadesTextoExpRegular")
public class RPropiedadesTextoExpRegular {

	/** Texto exp regular: expresion. */
	@ApiModelProperty(value = "Texto exp regular: expresion")
	private String expresionRegular;

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
	 * Método de acceso a expresionRegular.
	 *
	 * @return expresionRegular
	 */
	public String getExpresionRegular() {
		return expresionRegular;
	}

	/**
	 * Método para establecer expresionRegular.
	 *
	 * @param expresionRegular expresionRegular a establecer
	 */
	public void setExpresionRegular(String expresionRegular) {
		this.expresionRegular = expresionRegular;
	}
}

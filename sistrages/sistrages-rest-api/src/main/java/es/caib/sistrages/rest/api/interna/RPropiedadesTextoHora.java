package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Propiedades campo texto normal.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPropiedadesTextoHora", description = "Descripcion de RPropiedadesTextoHora")
public class RPropiedadesTextoHora {

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
}

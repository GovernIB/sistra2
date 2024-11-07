package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Propiedades campo texto telefono.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPropiedadesTextoTelefonoInternacional", description = "Descripcion de RPropiedadesTextoTelefonoInternacional")
public class RPropiedadesTextoTelefonoInternacional {

	/** Validación precisa. */
	@ApiModelProperty(value = "Validación precisa")
	private boolean validacionPrecisa;

	/** Prevenir pegar. */
	@ApiModelProperty(value = "Prevenir pegar")
	private boolean prevenirPegar;

	/** Validación precisa. */
	public boolean isValidacionPrecisa() {
		return validacionPrecisa;
	}

	/** Validación precisa. */
	public void setValidacionPrecisa(boolean validacionPrecisa) {
		this.validacionPrecisa = validacionPrecisa;
	}

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

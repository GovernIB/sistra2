package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Componente Campo Oculto.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RComponenteCampoOculto", description = "Descripcion de RComponenteCampoOculto")
public class RComponenteCampoOculto extends RComponente {
	/** Propiedades campo. */
	@ApiModelProperty(value = "Propiedades campo")
	private RPropiedadesCampo propiedadesCampo;

	/**
	 * Método de acceso a propiedadesCampo.
	 *
	 * @return propiedadesCampo
	 */
	public RPropiedadesCampo getPropiedadesCampo() {
		return propiedadesCampo;
	}

	/**
	 * Método para establecer propiedadesCampo.
	 *
	 * @param propiedadesCampo propiedadesCampo a establecer
	 */
	public void setPropiedadesCampo(final RPropiedadesCampo propiedadesCampo) {
		this.propiedadesCampo = propiedadesCampo;
	}
}

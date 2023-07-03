package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Componente lista elementos.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RComponenteListaElementos", description = "Descripcion de RComponenteListaElementos")
public class RComponenteListaElementos extends RComponente {

	/** Propiedades campo. */
	@ApiModelProperty(value = "Propiedades campo")
	private RPropiedadesCampo propiedadesCampo;

	/** Máximo num elementos. */
	@ApiModelProperty(value = "Número máximo elementos")
	private int maxElementos;

	/** Página asociada con la definición de campos de un elemento. */
	@ApiModelProperty(value = "Página asociada con la definición de campos de un elemento")
	private RPaginaFormulario paginaElemento;

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
	 * @param propiedadesCampo
	 *                             propiedadesCampo a establecer
	 */
	public void setPropiedadesCampo(final RPropiedadesCampo propiedadesCampo) {
		this.propiedadesCampo = propiedadesCampo;
	}

	/**
	 * Método de acceso a paginaElemento.
	 *
	 * @return paginaElemento
	 */
	public RPaginaFormulario getPaginaElemento() {
		return paginaElemento;
	}

	/**
	 * Método para establecer paginaElemento.
	 *
	 * @param paginaElemento
	 *                           paginaElemento a establecer
	 */
	public void setPaginaElemento(final RPaginaFormulario paginaElemento) {
		this.paginaElemento = paginaElemento;
	}

	/**
	 * Método de acceso a maxElementos.
	 * 
	 * @return maxElementos
	 */
	public int getMaxElementos() {
		return maxElementos;
	}

	/**
	 * Método para establecer maxElementos.
	 * 
	 * @param maxElementos
	 *                         maxElementos a establecer
	 */
	public void setMaxElementos(final int maxElementos) {
		this.maxElementos = maxElementos;
	}

}

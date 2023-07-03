package es.caib.sistramit.core.service.model.formulario.interno;

import java.io.Serializable;

/**
 * Datos que se mantienen en memoria para la edición de un elemento de una lista
 * de elementos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosEdicionElemento implements Serializable {

	/** Id campo lista elementos que se está editando. */
	private String idCampoListaElementos;

	/** Indice del elemento editado (nulo si nuevo). */
	private Integer indiceElemento;

	/** Página asociada al elemento. */
	private PaginaData paginaElemento;

	/**
	 * Obtiene idCampoListaElementos.
	 *
	 * @return idCampoListaElementos
	 */
	public String getIdCampoListaElementos() {
		return idCampoListaElementos;
	}

	/**
	 * Establece idCampoListaElementos.
	 *
	 * @param idCampoListaElementos
	 *                                  idCampoListaElementos
	 */
	public void setIdCampoListaElementos(final String idCampoListaElementos) {
		this.idCampoListaElementos = idCampoListaElementos;
	}

	/**
	 * Obtiene indiceElemento.
	 *
	 * @return indiceElemento
	 */
	public Integer getIndiceElemento() {
		return indiceElemento;
	}

	/**
	 * Establece indiceElemento.
	 *
	 * @param indiceElemento
	 *                           indiceElemento
	 */
	public void setIndiceElemento(final Integer indiceElemento) {
		this.indiceElemento = indiceElemento;
	}

	/**
	 * Método de acceso a paginaElemento.
	 *
	 * @return paginaElemento
	 */
	public PaginaData getPaginaElemento() {
		return paginaElemento;
	}

	/**
	 * Método para establecer paginaElemento.
	 *
	 * @param paginaElemento
	 *                           paginaElemento a establecer
	 */
	public void setPaginaElemento(final PaginaData paginaElemento) {
		this.paginaElemento = paginaElemento;
	}

}

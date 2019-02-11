package es.caib.sistramit.core.service.model.formulario.interno;

import java.io.Serializable;
import java.util.List;

import es.caib.sistramit.core.api.model.formulario.PaginaFormularioData;

/**
 * Inicialización de la página.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class InicializacionPagina implements Serializable {
	/**
	 * Datos de la página.
	 */
	private PaginaFormularioData pagina;
	/**
	 * Dependencias de los campos de la página.
	 */
	private List<DependenciaCampo> dependencias;

	/**
	 * Método de acceso a pagina.
	 *
	 * @return pagina
	 */
	public PaginaFormularioData getPagina() {
		return pagina;
	}

	/**
	 * Método para establecer pagina.
	 *
	 * @param pPagina
	 *            pagina a establecer
	 */
	public void setPagina(final PaginaFormularioData pPagina) {
		pagina = pPagina;
	}

	/**
	 * Método de acceso a dependencias.
	 *
	 * @return dependencias
	 */
	public List<DependenciaCampo> getDependencias() {
		return dependencias;
	}

	/**
	 * Método para establecer dependencias.
	 *
	 * @param pDependencias
	 *            dependencias a establecer
	 */
	public void setDependencias(final List<DependenciaCampo> pDependencias) {
		dependencias = pDependencias;
	}

}

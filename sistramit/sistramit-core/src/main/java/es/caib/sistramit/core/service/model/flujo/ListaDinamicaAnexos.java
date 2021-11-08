package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;
import java.util.List;

import es.caib.sistramit.core.api.model.flujo.Anexo;

/**
 * ListaDinamicaAnexos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ListaDinamicaAnexos implements Serializable {

	/**
	 * Anexos.
	 */
	private List<Anexo> anexos;

	/**
	 * Indica si los anexos dinámicos van antes que los anexos fijos.
	 */
	private boolean precedenciaSobreAnexosFijos;

	/**
	 * Método de acceso a anexos.
	 * 
	 * @return anexos
	 */
	public List<Anexo> getAnexos() {
		return anexos;
	}

	/**
	 * Método para establecer anexos.
	 * 
	 * @param anexos
	 *                   anexos a establecer
	 */
	public void setAnexos(final List<Anexo> anexos) {
		this.anexos = anexos;
	}

	/**
	 * Método de acceso a precedenciaSobreAnexosFijos.
	 * 
	 * @return precedenciaSobreAnexosFijos
	 */
	public boolean isPrecedenciaSobreAnexosFijos() {
		return precedenciaSobreAnexosFijos;
	}

	/**
	 * Método para establecer precedenciaSobreAnexosFijos.
	 * 
	 * @param precedenciaSobreAnexosFijos
	 *                                        precedenciaSobreAnexosFijos a
	 *                                        establecer
	 */
	public void setPrecedenciaSobreAnexosFijos(final boolean precedenciaSobreAnexosFijos) {
		this.precedenciaSobreAnexosFijos = precedenciaSobreAnexosFijos;
	}

}

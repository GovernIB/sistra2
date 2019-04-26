package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeSelector;

/**
 * Configuración de un campo del formulario de tipo selector.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public abstract class ConfiguracionCampoSelector extends ConfiguracionCampo {

	/**
	 * Constructor.
	 */
	public ConfiguracionCampoSelector() {
		super();
		setTipo(TypeCampo.SELECTOR);
	}

	/**
	 * Tipo de selector.
	 */
	private TypeSelector contenido;

	/**
	 * Método de acceso a modo.
	 *
	 * @return modo
	 */
	public final TypeSelector getContenido() {
		return contenido;
	}

	/**
	 * Método para establecer modo.
	 *
	 * @param pModo
	 *            modo a establecer
	 */
	protected final void setContenido(final TypeSelector pModo) {
		contenido = pModo;
	}

}

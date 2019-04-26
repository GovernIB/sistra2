package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeSelector;

/**
 * Configuración de un campo del formulario de tipo selector de tipo selección
 * múltiple.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ConfiguracionCampoSelectorMultiple extends ConfiguracionCampoSelector {

	/**
	 * Constructor
	 */
	public ConfiguracionCampoSelectorMultiple() {
		super();
		setContenido(TypeSelector.MULTIPLE);
	}

}

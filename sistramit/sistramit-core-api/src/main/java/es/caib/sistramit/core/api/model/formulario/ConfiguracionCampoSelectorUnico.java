package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeSelector;

/**
 * Configuración de un campo del formulario de tipo selector de tipo selección
 * única.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ConfiguracionCampoSelectorUnico extends ConfiguracionCampoSelector {

	/**
	 * Constructor
	 */
	public ConfiguracionCampoSelectorUnico() {
		super();
		setContenido(TypeSelector.UNICO);
	}

}

package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeSelector;

/**
 * Configuración de un campo del formulario de tipo selector de tipo dinámico.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ConfiguracionCampoSelectorDinamico extends ConfiguracionCampoSelector {

	/**
	 * Constructor
	 */
	public ConfiguracionCampoSelectorDinamico() {
		super();
		setContenido(TypeSelector.DINAMICO);
	}

}

package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeComponenteFormulario;

/**
 * Componente formulario de tipo campo selector.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ComponenteFormularioCampoSelector extends ComponenteFormularioCampo {

	// TODO Pendiente establecer mas atributos
	private String campoPropioSelector;

	public ComponenteFormularioCampoSelector() {
		this.setTipo(TypeComponenteFormulario.SELECTOR);
	}

	public String getCampoPropioSelector() {
		return campoPropioSelector;
	}

	public void setCampoPropioSelector(final String campoPropioSelector) {
		this.campoPropioSelector = campoPropioSelector;
	}

}

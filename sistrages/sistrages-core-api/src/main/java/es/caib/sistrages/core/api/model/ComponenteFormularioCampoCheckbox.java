package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * Componente formulario de tipo campo checkbox.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ComponenteFormularioCampoCheckbox extends ComponenteFormularioCampo {

	/** Valor checked. */
	private String valorChecked;

	/** Valor no checked. */
	private String valorNoChecked;

	public ComponenteFormularioCampoCheckbox() {
		this.setTipo(TypeObjetoFormulario.CHECKBOX);
	}

	public String getValorChecked() {
		return valorChecked;
	}

	public void setValorChecked(final String valorChecked) {
		this.valorChecked = valorChecked;
	}

	public String getValorNoChecked() {
		return valorNoChecked;
	}

	public void setValorNoChecked(final String valorNoChecked) {
		this.valorNoChecked = valorNoChecked;
	}

}

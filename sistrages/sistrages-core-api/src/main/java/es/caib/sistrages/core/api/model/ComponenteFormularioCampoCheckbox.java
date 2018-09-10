package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * La clase ComponenteFormularioCampoCheckbox.
 */

public final class ComponenteFormularioCampoCheckbox extends ComponenteFormularioCampo {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/**
	 * valor checked.
	 */
	private String valorChecked;

	/**
	 * valor no checked.
	 */
	private String valorNoChecked;

	/**
	 * Crea una nueva instancia de ComponenteFormularioCampoCheckbox.
	 */
	public ComponenteFormularioCampoCheckbox() {
		this.setTipo(TypeObjetoFormulario.CHECKBOX);
	}

	/**
	 * Obtiene el valor de valorChecked.
	 *
	 * @return el valor de valorChecked
	 */
	public String getValorChecked() {
		return valorChecked;
	}

	/**
	 * Establece el valor de valorChecked.
	 *
	 * @param valorChecked
	 *            el nuevo valor de valorChecked
	 */
	public void setValorChecked(final String valorChecked) {
		this.valorChecked = valorChecked;
	}

	/**
	 * Obtiene el valor de valorNoChecked.
	 *
	 * @return el valor de valorNoChecked
	 */
	public String getValorNoChecked() {
		return valorNoChecked;
	}

	/**
	 * Establece el valor de valorNoChecked.
	 *
	 * @param valorNoChecked
	 *            el nuevo valor de valorNoChecked
	 */
	public void setValorNoChecked(final String valorNoChecked) {
		this.valorNoChecked = valorNoChecked;
	}

}

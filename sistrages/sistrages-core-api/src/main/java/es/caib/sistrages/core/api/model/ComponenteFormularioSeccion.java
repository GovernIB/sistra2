package es.caib.sistrages.core.api.model;

/**
 * Componente formulario de tipo seccion.
 *
 * @author Indra
 *
 */

public final class ComponenteFormularioSeccion extends ComponenteFormulario {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	private String letra;

	/**
	 * Crea una nueva instancia de ComponenteFormularioSeccion.
	 */
	public ComponenteFormularioSeccion() {
		super();
	}

	/**
	 * Obtiene el valor de letra.
	 *
	 * @return el valor de letra
	 */
	public String getLetra() {
		return letra;
	}

	/**
	 * Establece el valor de letra.
	 *
	 * @param letra
	 *            el nuevo valor de letra
	 */
	public void setLetra(final String letra) {
		this.letra = letra;
	}

}

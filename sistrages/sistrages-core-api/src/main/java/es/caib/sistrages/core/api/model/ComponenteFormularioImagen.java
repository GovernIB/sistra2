package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * La clase ComponenteFormularioImagen.
 */
@SuppressWarnings("serial")
public final class ComponenteFormularioImagen extends ComponenteFormulario {

	/**
	 * fichero.
	 */
	private Fichero fichero;

	/**
	 * Crea una nueva instancia de ComponenteFormularioImagen.
	 */
	public ComponenteFormularioImagen() {
		this.setTipo(TypeObjetoFormulario.IMAGEN);
	}

	/**
	 * Obtiene el valor de fichero.
	 *
	 * @return el valor de fichero
	 */
	public Fichero getFichero() {
		return fichero;
	}

	/**
	 * Establece el valor de fichero.
	 *
	 * @param fichero
	 *            el nuevo valor de fichero
	 */
	public void setFichero(final Fichero fichero) {
		this.fichero = fichero;
	}

}

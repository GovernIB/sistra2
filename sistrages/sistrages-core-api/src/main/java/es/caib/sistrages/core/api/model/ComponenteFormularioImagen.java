package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * Componente formulario de tipo imagen.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ComponenteFormularioImagen extends ComponenteFormulario {

	/** Fichero imagen. */
	private Fichero fichero;

	public ComponenteFormularioImagen() {
		this.setTipo(TypeObjetoFormulario.IMAGEN);
	}

	public Fichero getFichero() {
		return fichero;
	}

	public void setFichero(final Fichero fichero) {
		this.fichero = fichero;
	}

}

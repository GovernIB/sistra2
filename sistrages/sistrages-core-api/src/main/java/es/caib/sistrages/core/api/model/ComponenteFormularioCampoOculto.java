package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * La clase ComponenteFormularioCampoOculto.
 */

public final class ComponenteFormularioCampoOculto extends ComponenteFormularioCampo {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/**
	 * Crea una nueva instancia de ComponenteFormularioEtiqueta.
	 */
	public ComponenteFormularioCampoOculto() {
		this.setTipo(TypeObjetoFormulario.CAMPO_OCULTO);
	}

}

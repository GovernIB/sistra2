package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * La clase ComponenteFormularioCampoCaptcha.
 */

public final class ComponenteFormularioCampoCaptcha extends ComponenteFormularioCampo {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/**
	 * Crea una nueva instancia de ComponenteFormularioCampoCaptcha.
	 */
	public ComponenteFormularioCampoCaptcha() {
		this.setTipo(TypeObjetoFormulario.CAPTCHA);
	}

}

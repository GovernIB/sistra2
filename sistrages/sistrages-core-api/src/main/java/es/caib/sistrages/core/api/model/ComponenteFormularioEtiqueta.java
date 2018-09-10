package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeEtiqueta;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * La clase ComponenteFormularioEtiqueta.
 */

public final class ComponenteFormularioEtiqueta extends ComponenteFormulario {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/**
	 * tipo etiqueta.
	 */
	private TypeEtiqueta tipoEtiqueta = TypeEtiqueta.INFO;

	/**
	 * Crea una nueva instancia de ComponenteFormularioEtiqueta.
	 */
	public ComponenteFormularioEtiqueta() {
		this.setTipo(TypeObjetoFormulario.ETIQUETA);
	}

	/**
	 * Obtiene el valor de tipoEtiqueta.
	 *
	 * @return el valor de tipoEtiqueta
	 */
	public TypeEtiqueta getTipoEtiqueta() {
		return tipoEtiqueta;
	}

	/**
	 * Establece el valor de tipoEtiqueta.
	 *
	 * @param tipoEtiqueta
	 *            el nuevo valor de tipoEtiqueta
	 */
	public void setTipoEtiqueta(final TypeEtiqueta tipoEtiqueta) {
		this.tipoEtiqueta = tipoEtiqueta;
	}

}

package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
import es.caib.sistrages.core.api.model.types.TypeEtiqueta;

/**
 * Componente formulario de tipo etiqueta.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ComponenteFormularioEtiqueta extends ComponenteFormulario {

	/** Tipo etiqueta. */
	private TypeEtiqueta tipoEtiqueta = TypeEtiqueta.INFO;

	public ComponenteFormularioEtiqueta() {
		this.setTipo(TypeObjetoFormulario.ETIQUETA);
	}

	public TypeEtiqueta getTipoEtiqueta() {
		return tipoEtiqueta;
	}

	public void setTipoEtiqueta(final TypeEtiqueta tipoEtiqueta) {
		this.tipoEtiqueta = tipoEtiqueta;
	}

}

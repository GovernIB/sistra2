package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeComponenteFormulario;

/**
 * Componente formulario de tipo campo texto.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ComponenteFormularioCampoTexto extends ComponenteFormularioCampo {

	// TODO Pendiente establecer mas atributos
	private String campoPropioTexto;

	public ComponenteFormularioCampoTexto() {
		this.setTipo(TypeComponenteFormulario.CAMPO_TEXTO);
	}

	public String getCampoPropioTexto() {
		return campoPropioTexto;
	}

	public void setCampoPropioTexto(final String campoPropioTexto) {
		this.campoPropioTexto = campoPropioTexto;
	}
}

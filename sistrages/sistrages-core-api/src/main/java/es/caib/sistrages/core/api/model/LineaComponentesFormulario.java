package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Línea componentes para una página de formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class LineaComponentesFormulario extends ModelApi {

	/** Id. */
	private Long id;

	private int orden;

	/** Componentes. */
	private List<ComponenteFormulario> componentes = new ArrayList<ComponenteFormulario>();

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public List<ComponenteFormulario> getComponentes() {
		return componentes;
	}

	public void setComponentes(final List<ComponenteFormulario> componentes) {
		this.componentes = componentes;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

}

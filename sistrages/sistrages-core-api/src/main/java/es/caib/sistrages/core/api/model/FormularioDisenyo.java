package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Diseño formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class FormularioDisenyo extends ModelApi {

	/** Id. */
	private Long id;

	/** Páginas formulario. */
	private final List<PaginaFormulario> paginas = new ArrayList<PaginaFormulario>();

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public List<PaginaFormulario> getPaginas() {
		return paginas;
	}

}

package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Página formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PaginaFormulario extends ModelApi {

	/** Id. */
	private Long id;

	private Script scriptValidacion;

	private int orden;

	private boolean paginaFinal;

	/** Líneas componentes. */
	private List<LineaComponentesFormulario> lineas = new ArrayList<>();

	/**
	 * Crea una nueva instancia de PaginaFormulario.
	 */
	public PaginaFormulario() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public List<LineaComponentesFormulario> getLineas() {
		return lineas;
	}

	public void setLineas(final List<LineaComponentesFormulario> lineas) {
		this.lineas = lineas;
	}

	public Script getScriptValidacion() {
		return scriptValidacion;
	}

	public void setScriptValidacion(final Script scriptValidacion) {
		this.scriptValidacion = scriptValidacion;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(final int orden) {
		this.orden = orden;
	}

	public boolean isPaginaFinal() {
		return paginaFinal;
	}

	public void setPaginaFinal(final boolean paginaFinal) {
		this.paginaFinal = paginaFinal;
	}

	public ComponenteFormulario getComponente(final String codigoComponente) {
		ComponenteFormulario res = null;
		if (lineas != null) {
			for (final LineaComponentesFormulario lc : lineas) {
				if (lc.getComponentes() != null) {
					for (final ComponenteFormulario cf : lc.getComponentes()) {
						if (cf.getIdComponente().equals(codigoComponente)) {
							res = cf;
							break;
						}
					}
				}
			}
		}
		return res;
	}

	public ComponenteFormulario getComponente(final Long idComponente) {
		ComponenteFormulario res = null;
		if (lineas != null) {
			for (final LineaComponentesFormulario lc : lineas) {
				if (lc.getComponentes() != null) {
					for (final ComponenteFormulario cf : lc.getComponentes()) {
						if (cf.getId().equals(idComponente)) {
							res = cf;
							break;
						}
					}
				}
			}
		}
		return res;
	}

	public LineaComponentesFormulario getLinea(final Long idLinea) {
		LineaComponentesFormulario res = null;
		if (lineas != null) {
			for (final LineaComponentesFormulario lc : lineas) {
				if (lc.getId().equals(idLinea)) {
					res = lc;
					break;
				}
			}
		}
		return res;
	}

	public LineaComponentesFormulario getLineaComponente(final Long idComponente) {
		LineaComponentesFormulario res = null;
		if (lineas != null) {
			for (final LineaComponentesFormulario lc : lineas) {
				if (lc.getComponentes() != null) {
					for (final ComponenteFormulario cf : lc.getComponentes()) {
						if (cf.getId().equals(idComponente)) {
							res = lc;
							break;
						}
					}
				}
			}
		}
		return res;
	}
}

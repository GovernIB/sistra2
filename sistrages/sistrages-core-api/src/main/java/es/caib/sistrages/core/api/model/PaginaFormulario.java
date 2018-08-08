package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase PaginaFormulario.
 */
@SuppressWarnings("serial")
public final class PaginaFormulario extends ModelApi {

	/**
	 * codigo.
	 */
	private Long codigo;

	/**
	 * script de validacion.
	 */
	private Script scriptValidacion;

	/**
	 * orden.
	 */
	private int orden;

	/**
	 * indica si es la pagina final.
	 */
	private boolean paginaFinal;

	/**
	 * lineas.
	 */
	private List<LineaComponentesFormulario> lineas = new ArrayList<>();

	/** Pagina asociada lista elementos. **/
	private boolean paginaAsociadaListaElementos;

	/**
	 * Crea una nueva instancia de PaginaFormulario.
	 */
	public PaginaFormulario() {
		super();
	}

	/**
	 * Obtiene el valor de codigo.
	 *
	 * @return el valor de codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Establece el valor de codigo.
	 *
	 * @param codigo
	 *            el nuevo valor de codigo
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * Obtiene el valor de lineas.
	 *
	 * @return el valor de lineas
	 */
	public List<LineaComponentesFormulario> getLineas() {
		return lineas;
	}

	/**
	 * Establece el valor de lineas.
	 *
	 * @param lineas
	 *            el nuevo valor de lineas
	 */
	public void setLineas(final List<LineaComponentesFormulario> lineas) {
		this.lineas = lineas;
	}

	/**
	 * Obtiene el valor de scriptValidacion.
	 *
	 * @return el valor de scriptValidacion
	 */
	public Script getScriptValidacion() {
		return scriptValidacion;
	}

	/**
	 * Establece el valor de scriptValidacion.
	 *
	 * @param scriptValidacion
	 *            el nuevo valor de scriptValidacion
	 */
	public void setScriptValidacion(final Script scriptValidacion) {
		this.scriptValidacion = scriptValidacion;
	}

	/**
	 * Obtiene el valor de orden.
	 *
	 * @return el valor de orden
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * Establece el valor de orden.
	 *
	 * @param orden
	 *            el nuevo valor de orden
	 */
	public void setOrden(final int orden) {
		this.orden = orden;
	}

	/**
	 * Verifica si es pagina final.
	 *
	 * @return true, si es pagina final
	 */
	public boolean isPaginaFinal() {
		return paginaFinal;
	}

	/**
	 * Establece el valor de paginaFinal.
	 *
	 * @param paginaFinal
	 *            el nuevo valor de paginaFinal
	 */
	public void setPaginaFinal(final boolean paginaFinal) {
		this.paginaFinal = paginaFinal;
	}

	/**
	 * Obtiene el valor de componente.
	 *
	 * @param codigoComponente
	 *            the codigo componente
	 * @return el valor de componente
	 */
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

	/**
	 * Obtiene el valor de componente.
	 *
	 * @param idComponente
	 *            the codigo componente
	 * @return el valor de componente
	 */
	public ComponenteFormulario getComponente(final Long idComponente) {
		ComponenteFormulario res = null;
		if (lineas != null) {
			for (final LineaComponentesFormulario lc : lineas) {
				if (lc.getComponentes() != null) {
					for (final ComponenteFormulario cf : lc.getComponentes()) {
						if (cf.getCodigo().equals(idComponente)) {
							res = cf;
							break;
						}
					}
				}
			}
		}
		return res;
	}

	/**
	 * Obtiene el valor de linea.
	 *
	 * @param idLinea
	 *            the codigo linea
	 * @return el valor de linea
	 */
	public LineaComponentesFormulario getLinea(final Long idLinea) {
		LineaComponentesFormulario res = null;
		if (lineas != null) {
			for (final LineaComponentesFormulario lc : lineas) {
				if (lc.getCodigo().equals(idLinea)) {
					res = lc;
					break;
				}
			}
		}
		return res;
	}

	/**
	 * Obtiene el valor de lineaComponente.
	 *
	 * @param idComponente
	 *            the codigo componente
	 * @return el valor de lineaComponente
	 */
	public LineaComponentesFormulario getLineaComponente(final Long idComponente) {
		LineaComponentesFormulario res = null;
		if (lineas != null) {
			for (final LineaComponentesFormulario lc : lineas) {
				if (lc.getComponentes() != null) {
					for (final ComponenteFormulario cf : lc.getComponentes()) {
						if (cf.getCodigo().equals(idComponente)) {
							res = lc;
							break;
						}
					}
				}
			}
		}
		return res;
	}

	/**
	 * @return the paginaAsociadaListaElementos
	 */
	public boolean isPaginaAsociadaListaElementos() {
		return paginaAsociadaListaElementos;
	}

	/**
	 * @param paginaAsociadaListaElementos
	 *            the paginaAsociadaListaElementos to set
	 */
	public void setPaginaAsociadaListaElementos(final boolean paginaAsociadaListaElementos) {
		this.paginaAsociadaListaElementos = paginaAsociadaListaElementos;
	}
}

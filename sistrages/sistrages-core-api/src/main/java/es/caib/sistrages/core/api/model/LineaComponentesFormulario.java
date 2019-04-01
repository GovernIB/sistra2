package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistrages.core.api.model.comun.ConstantesDisenyo;

/**
 * La clase LineaComponentesFormulario.
 */

public final class LineaComponentesFormulario extends ObjetoFormulario
		implements Comparable<LineaComponentesFormulario> {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/**
	 * orden.
	 */
	private int orden;

	/**
	 * componentes.
	 */
	private List<ComponenteFormulario> componentes = new ArrayList<>();

	/**
	 * Obtiene el valor de componentes.
	 *
	 * @return el valor de componentes
	 */
	public List<ComponenteFormulario> getComponentes() {
		return componentes;
	}

	/**
	 * Establece el valor de componentes.
	 *
	 * @param componentes
	 *            el nuevo valor de componentes
	 */
	public void setComponentes(final List<ComponenteFormulario> componentes) {
		this.componentes = componentes;
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
	 * Evalua si la linea está completa.
	 *
	 * @return true, si está completa
	 */
	public boolean completa() {
		return (!componentes.isEmpty() && columnasComponentes() >= ConstantesDisenyo.NUM_MAX_COMPONENTES_LINEA);
	}

	/**
	 * Devuelve el número de columnas que ocupan los componentes de una línea.
	 *
	 * @return Número de columnas
	 */
	public int columnasComponentes() {
		int ncolumnas = 0;
		if (!componentes.isEmpty()) {
			for (final ComponenteFormulario elementoFormulario : componentes) {
				switch (elementoFormulario.getTipo()) {
				case SECCION:
					ncolumnas += elementoFormulario.getNumColumnas();
					break;
				case ETIQUETA:
					ncolumnas += elementoFormulario.getNumColumnas();
					break;
				case CAMPO_TEXTO:
					ncolumnas += elementoFormulario.getNumColumnas();
					break;
				case SELECTOR:
					ncolumnas += elementoFormulario.getNumColumnas();
					break;
				case CHECKBOX:
					ncolumnas += elementoFormulario.getNumColumnas();
					break;
				default:
					break;
				}
			}
		}
		return ncolumnas;
	}

	/**
	 * Evalua si cabe el componente en la linea.
	 *
	 * @param pComponente
	 *            componente
	 * @return true, si cabe
	 */
	public boolean cabenComponentes(final ComponenteFormulario pComponente) {
		boolean res = true;
		int ncolumnas = pComponente.getNumColumnas();
		if (!componentes.isEmpty()) {
			for (final ComponenteFormulario elementoFormulario : componentes) {
				if (!elementoFormulario.getCodigo().equals(pComponente.getCodigo())) {
					switch (elementoFormulario.getTipo()) {
					case SECCION:
						ncolumnas += elementoFormulario.getNumColumnas();
						break;
					case ETIQUETA:
						ncolumnas += elementoFormulario.getNumColumnas();
						break;
					case CAMPO_TEXTO:
						ncolumnas += elementoFormulario.getNumColumnas();
						break;
					case SELECTOR:
						ncolumnas += elementoFormulario.getNumColumnas();
						break;
					case CHECKBOX:
						ncolumnas += elementoFormulario.getNumColumnas();
						break;
					default:
						break;
					}

					if (ncolumnas > ConstantesDisenyo.NUM_MAX_COMPONENTES_LINEA) {
						res = false;
						break;
					}
				}
			}
		}
		return res;
	}

	@Override
	public int compareTo(final LineaComponentesFormulario arg0) {
		return Integer.compare(this.getOrden(), arg0.getOrden());
	}

	/**
	 * Método que se encarga de añadir un componente en una posición concreta y
	 * posteriormente los reordena. Método exclusivo del dialogDisenyoFormulario.
	 *
	 * @param componente
	 * @param posicion
	 * @return
	 */
	public void addComponente(final ComponenteFormulario componente) {
		getComponentes().add(componente.getOrden() - 1, componente);

		// actualizamos orden
		for (int i = 1; i <= getComponentes().size(); i++) {
			getComponentes().get(i - 1).setOrden(i);
		}

	}
}

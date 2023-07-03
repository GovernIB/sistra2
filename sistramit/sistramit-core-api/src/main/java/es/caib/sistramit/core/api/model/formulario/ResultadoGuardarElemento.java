package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;
import java.util.List;

/**
 * Resultado de guardar elemento de lista elementos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResultadoGuardarElemento implements Serializable {

	/**
	 * Mensaje validación.
	 */
	private MensajeValidacion validacion;

	/**
	 * Valor elemento.
	 */
	private List<ValorCampo> valor;

	/**
	 * Indice elemento (si nulo, añadir al final).
	 */
	private Integer indice;

	/**
	 * Orden elemento (si nulo no cambia orden).
	 */
	private Integer orden;

	/**
	 * Obtiene validacion.
	 *
	 * @return validacion
	 */
	public MensajeValidacion getValidacion() {
		return validacion;
	}

	/**
	 * Establece validacion.
	 *
	 * @param validacion
	 *                       validacion
	 */
	public void setValidacion(final MensajeValidacion validacion) {
		this.validacion = validacion;
	}

	/**
	 * Obtiene indice.
	 *
	 * @return indice
	 */
	public Integer getIndice() {
		return indice;
	}

	/**
	 * Establece indice.
	 *
	 * @param indice
	 *                   indice
	 */
	public void setIndice(final Integer indice) {
		this.indice = indice;
	}

	/**
	 * Obtiene valor.
	 *
	 * @return valor
	 */
	public List<ValorCampo> getValor() {
		return valor;
	}

	/**
	 * Establece valor.
	 *
	 * @param valor
	 *                  valor
	 */
	public void setValor(final List<ValorCampo> valor) {
		this.valor = valor;
	}

	/**
	 * Obtiene orden.
	 *
	 * @return orden
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * Establece orden.
	 *
	 * @param orden
	 *                  orden
	 */
	public void setOrden(final Integer orden) {
		this.orden = orden;
	}

}

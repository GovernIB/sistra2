package es.caib.sistramit.core.api.model.comun;

import java.io.Serializable;

/**
 * Resultado de un proceso programado.
 *
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResultadoProcesoProgramado implements Serializable {

	/**
	 * Resultado del proceso: si ha finalizado bien o mal.
	 */
	private boolean finalizadoOk = true;

	/**
	 * Detalles de la ejecución del proceso.
	 */
	private ListaPropiedades detalles;

	/**
	 * Método de acceso a resultado.
	 *
	 * @return resultado
	 */
	public boolean isFinalizadoOk() {
		return finalizadoOk;
	}

	/**
	 * Método para establecer resultado.
	 *
	 * @param pResultado
	 *            resultado a establecer
	 */
	public void setFinalizadoOk(final boolean pResultado) {
		finalizadoOk = pResultado;
	}

	/**
	 * Método de acceso a detalles.
	 *
	 * @return detalles
	 */
	public ListaPropiedades getDetalles() {
		return detalles;
	}

	/**
	 * Método para establecer detalles.
	 *
	 * @param pDetalles
	 *            detalles a establecer
	 */
	public void setDetalles(final ListaPropiedades pDetalles) {
		detalles = pDetalles;
	}
}

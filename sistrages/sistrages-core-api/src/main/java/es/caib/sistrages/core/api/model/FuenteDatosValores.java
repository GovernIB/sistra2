package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Fuente Datos con Valores.
 *
 * @author Indra
 *
 */

public class FuenteDatosValores extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;
	/** Campos. **/
	private List<FuenteFila> filas = new ArrayList<>();

	/**
	 * @return the filas
	 */
	public List<FuenteFila> getFilas() {
		return filas;
	}

	/**
	 * @param filas
	 *            the filas to set
	 */
	public void setFilas(final List<FuenteFila> filas) {
		this.filas = filas;
	}

	/**
	 * Borra una fila.
	 *
	 * @param fuenteDatosFila
	 */
	public void removeFila(final FuenteFila fuenteDatosFila) {
		if (this.filas.contains(fuenteDatosFila)) {
			this.filas.remove(fuenteDatosFila);
		}
	}

	/**
	 * Borra una fila.
	 *
	 * @param fuenteDatosFila
	 */
	public void addFila(final FuenteFila fuenteDatosFila) {
		this.filas.add(fuenteDatosFila);
	}
}

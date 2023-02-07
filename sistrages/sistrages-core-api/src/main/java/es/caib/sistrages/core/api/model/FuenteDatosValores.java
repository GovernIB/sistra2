package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistrages.core.api.model.comun.Propiedad;

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

	@Override
	public String toString() {
        return toString("","ca");
	}

	/**
     * Método to string
     * @param tabulacion Indica el texto anterior de la linea para que haya tabulacion.
     * @return El texto
     */
     public String toString(String tabulacion, String idioma) {
           StringBuilder texto = new StringBuilder(tabulacion + "\t Datos de fuenteDatos. \n");
           if (this.getFilas() != null) {
        	   for(FuenteFila fila : getFilas()) {
        		   texto.append(tabulacion +"\t" + fila.toString(tabulacion+"\t", idioma) );
        	   }
           }
           return texto.toString();
     }
}

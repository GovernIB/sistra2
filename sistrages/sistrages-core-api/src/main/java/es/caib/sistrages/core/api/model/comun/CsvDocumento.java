package es.caib.sistrages.core.api.model.comun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Modeliza documento CSV. Empieza con fila 0.
 *
 * @author Indra
 *
 */
public class CsvDocumento {

	/** Columnas del csv. **/
	private String[] columnas;

	/** Filas de datos. **/
	private List<HashMap<String, String>> filas = new ArrayList<>();

	/**
	 * Anyade una fila.
	 *
	 * @return
	 */
	public int addFila() {
		filas.add(new HashMap<>());
		return filas.size() - 1;
	}

	/**
	 * Devuelve el num. de filas.
	 *
	 * @return
	 */
	public int getNumeroFilas() {
		return filas.size();
	}

	public void setValor(final int fila, final String columna, final String valor) throws Exception {
		checkExisteDato(fila, columna);
		final Map<String, String> valores = filas.get(fila);
		valores.put(columna, valor);
	}

	public String getValor(final int fila, final String columna) throws Exception {
		checkExisteDato(fila, columna);
		final Map<String, String> valores = filas.get(fila);
		return valores.get(columna);
	}

	/**
	 * Imprime los datos del CSV.
	 *
	 * @return
	 * @throws Exception
	 */
	public String print() throws Exception {
		final StringBuilder sb = new StringBuilder(8192);
		for (int fila = 0; fila < getNumeroFilas(); fila++) {
			sb.append("FILA: " + fila + "\n");
			for (int columna = 0; columna < getColumnas().length; columna++) {
				final String col = getColumnas()[columna];
				sb.append("	> COLUMNA: " + col + " = " + getValor(fila, col) + "\n");
			}
		}
		return sb.toString();

	}

	/**
	 * Comprueba si ya existe un dato.
	 *
	 * @param fila
	 * @param columna
	 * @throws Exception
	 */
	private void checkExisteDato(final int fila, final String columna) throws Exception {
		if (fila >= filas.size()) {
			throw new Exception("No existe fila " + fila);
		}
		boolean enc = false;
		for (int i = 0; i < columnas.length; i++) {
			if (columnas[i].equals(columna)) {
				enc = true;
				break;
			}
		}
		if (!enc) {
			throw new Exception("No existe columna " + columna);
		}
	}

	/**
	 * @return the columnas
	 */
	public String[] getColumnas() {
		return columnas;
	}

	/**
	 * @param columnas
	 *            the columnas to set
	 */
	public void setColumnas(final String[] columnas) {
		this.columnas = columnas;
	}

	/**
	 * @return the filas
	 */
	public List<HashMap<String, String>> getFilas() {
		return filas;
	}

	/**
	 * @param filas
	 *            the filas to set
	 */
	public void setFilas(final List<HashMap<String, String>> filas) {
		this.filas = filas;
	}
}

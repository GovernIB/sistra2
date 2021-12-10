package es.caib.sistra2.commons.plugins.dominio.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Modela datos de un dominio.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ValoresDominio implements Serializable {

	/**
	 * Filas de valores.
	 */
	private final List<Map<String, String>> datos = new ArrayList<>();

	/**
	 * Ficheros.
	 */
	private final Map<String, FicheroDominio> ficheros = new HashMap<>();

	/**
	 * Codigo retorno (opcional, puede indicar códugo retorno en caso de que no haya
	 * error).
	 */
	private String codigoRetorno;

	/**
	 * Indica si ha habido error.
	 */
	private boolean error;

	/**
	 * Codigo error.
	 */
	private String codigoError;

	/**
	 * Texto error.
	 */
	private String descripcionError;

	/**
	 * Añade una fila de valores.
	 *
	 * @return Numero de fila añadida
	 */
	public int addFila() {
		final Map<String, String> fila = new HashMap<>();
		datos.add(fila);
		return datos.size();
	}

	/**
	 * Establece el valor de una columna en una fila.
	 *
	 * @param numfila
	 *                    Numero de fila (empiezan desde 1)
	 * @param cod
	 *                    Nombre columna
	 * @param val
	 *                    Valor
	 */
	public void setValor(final int numfila, final String cod, final String val) {
		final Map<String, String> fila = datos.get(numfila - 1);
		fila.put(cod.toUpperCase(), val == null ? null : val);
	}

	/**
	 * Obtiene el valor de una columna en una fila. Si el valor del campo es nulo se
	 * devuelve cadena vacía.
	 *
	 * @param numfila
	 *                    Numero de fila (empiezan desde 1)
	 * @param cod
	 *                    Nombre columna
	 * @return Valor columna para la fila seleccionada
	 */
	public String getValor(final int numfila, final String cod) {
		String valor;
		final Map<String, String> fila = datos.get(numfila - 1);
		valor = fila.get(cod.toUpperCase());
		if (valor == null) {
			valor = "";
		}
		return valor;
	}

	/**
	 * Obtiene numero de filas.
	 *
	 * @return Numero de filas
	 */
	public int getNumeroFilas() {
		return datos.size();
	}

	/**
	 * Método de acceso a error.
	 *
	 * @return error
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * Método para establecer error.
	 *
	 * @param pError
	 *                   error a establecer
	 */
	public void setError(final boolean pError) {
		error = pError;
	}

	/**
	 * Método de acceso a descripcionError.
	 *
	 * @return descripcionError
	 */
	public String getDescripcionError() {
		return descripcionError;
	}

	/**
	 * Método para establecer descripcionError.
	 *
	 * @param pDescripcionError
	 *                              descripcionError a establecer
	 */
	public void setDescripcionError(final String pDescripcionError) {
		descripcionError = pDescripcionError;
	}

	/**
	 * Añade un fichero.
	 *
	 * @param id
	 *                    Id fichero
	 * @param fichero
	 *                    Fichero
	 */
	public void addFichero(final String id, final FicheroDominio fichero) {
		ficheros.put(id.toUpperCase(), fichero);
	}

	/**
	 * Obtiene un fichero.
	 *
	 * @param id
	 *               Id fichero
	 * @return fichero
	 */
	public FicheroDominio getFichero(final String id) {
		return ficheros.get(id.toUpperCase());
	}

	/**
	 * Obtiene los nombres de las columnas.
	 *
	 * @return el atributo nombre columnas
	 */
	public List<String> getNombreColumnas() {
		final List<String> nombresColumnas = new ArrayList<>();
		if (!datos.isEmpty()) {
			final Map<String, String> fila = datos.get(0);
			for (final Map.Entry<String, String> entry : fila.entrySet()) {
				nombresColumnas.add(entry.getKey());
			}
		}
		return nombresColumnas;
	}

	/**
	 * Método de acceso a datos.
	 *
	 * @return el datos
	 */
	public List<Map<String, String>> getDatos() {
		return datos;
	}

	/**
	 * Método de acceso a codigoError.
	 *
	 * @return codigoError
	 */
	public String getCodigoError() {
		return codigoError;
	}

	/**
	 * Método para establecer codigoError.
	 *
	 * @param pCodigoError
	 *                         codigoError a establecer
	 */
	public void setCodigoError(final String pCodigoError) {
		codigoError = pCodigoError;
	}

	/**
	 * @return the ficheros
	 */
	public Map<String, FicheroDominio> getFicheros() {
		return ficheros;
	}

	/**
	 * Método de acceso a codigoRetorno.
	 *
	 * @return codigoRetorno
	 */
	public String getCodigoRetorno() {
		return codigoRetorno;
	}

	/**
	 * Método para establecer codigoRetorno.
	 *
	 * @param codigoRetorno
	 *                          codigoRetorno a establecer
	 */
	public void setCodigoRetorno(final String codigoRetorno) {
		this.codigoRetorno = codigoRetorno;
	}
}

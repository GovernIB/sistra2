package es.caib.sistramit.core.service.model.script;

import java.util.List;

/**
 * Modela datos de un dominio.
 *
 * @author Indra
 *
 */
public interface ClzValoresDominioInt extends PluginClass {

	/**
	 * Obtiene el valor de una columna en una fila. Si el valor del campo es nulo se
	 * devuelve cadena vacía.
	 *
	 * @param numfila
	 *            Numero de fila (empiezan desde 1)
	 * @param cod
	 *            Nombre columna
	 * @return Valor columna para la fila seleccionada
	 */
	String getValor(final int numfila, final String cod);

	/**
	 * Obtiene numero de filas.
	 *
	 * @return Numero de filas
	 */
	int getNumeroFilas();

	/**
	 * Método de acceso a error.
	 *
	 * @return error
	 */
	boolean isError();

	/**
	 * Método de acceso a codigo error.
	 *
	 * @return descripcionError
	 */
	String getCodigoError();

	/**
	 * Método de acceso a descripcionError.
	 *
	 * @return descripcionError
	 */
	String getDescripcionError();

	/**
	 * Obtiene un fichero.
	 *
	 * @param id
	 *            Id fichero
	 * @return fichero
	 */
	ClzFicheroInt getFichero(final String id);

	/**
	 * Obtiene los nombres de las columnas.
	 *
	 * @return el atributo nombre columnas
	 */
	List<String> getNombreColumnas();

}

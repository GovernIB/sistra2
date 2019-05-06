package es.caib.sistra2.commons.plugins.dominio.rest;

import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistra2.commons.plugins.dominio.rest.api.v1.RValoresDominio;
import es.caib.sistra2.commons.plugins.dominio.rest.cxf.Columna;
import es.caib.sistra2.commons.plugins.dominio.rest.cxf.Fila;
import es.caib.sistra2.commons.plugins.dominio.rest.cxf.Filas;

/**
 * Utilidades.
 *
 * @author Indra
 *
 */
public class Utilidades {

	/** Constructor. **/
	private Utilidades() {
		// Constructor vacio
	}

	/**
	 * Convierte los datos de dominio remoto sistra2 a valoresDominio
	 *
	 * @param rvaloresDominio
	 * @return
	 */
	public static ValoresDominio getValoresDominio(final RValoresDominio rvaloresDominio) {
		ValoresDominio valoresDominio = null;
		if (rvaloresDominio != null) {
			valoresDominio = new ValoresDominio();
			valoresDominio.setDatos(rvaloresDominio.getDatos());
			valoresDominio.setCodigoError(rvaloresDominio.getCodigoError());
			valoresDominio.setError(rvaloresDominio.isError());
		}
		return valoresDominio;
	}

	/**
	 * Convierte los datos de dominio remoto sistra1 a valoresDominio
	 *
	 * @param valoresDominioSistra1
	 * @return
	 */
	public static ValoresDominio getValoresDominioSistra1(
			final es.caib.sistra2.commons.plugins.dominio.rest.cxf.ValoresDominio valoresDominioSistra1) {
		final ValoresDominio valoresDominio = new ValoresDominio();
		valoresDominio.setError(valoresDominioSistra1.isError());
		if (valoresDominioSistra1.getDescripcionError() != null) {
			valoresDominio.setDescripcionError(valoresDominioSistra1.getDescripcionError().getValue());
		}
		final Filas filas = valoresDominioSistra1.getFilas().getValue();
		if (filas != null) {
			for (final Fila fila : filas.getFila()) {
				final int numfila = valoresDominio.addFila();
				for (final Columna columna : fila.getColumna()) {
					valoresDominio.setValor(numfila, columna.getCodigo(), columna.getValor());
				}
			}
		}
		return valoresDominio;
	}
}

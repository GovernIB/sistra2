package es.caib.sistrahelp.core.api.exception;

import java.util.Date;

import es.caib.sistrahelp.core.api.model.comun.ListaPropiedades;

/**
 * Interfaz para que extiendan todas las excepciones.
 *
 * @author Indra
 *
 */
public interface ServiceException {

	/**
	 * Obtiene mensaje de la excepción.
	 *
	 * @return Mensaje excepción.
	 */
	String getMessage();

	/**
	 * Obtiene los detalles de la excepción.
	 *
	 * @return the detallesExcepcion
	 */
	ListaPropiedades getDetallesExcepcion();

	/**
	 * Obtiene fecha excepción.
	 *
	 * @return Fecha excepción
	 */
	Date getFechaExcepcion();

}

package es.caib.sistramit.core.api.exception;

import java.util.Date;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Interfaz para que extiendan todas las excepciones.
 *
 * @author Indra
 *
 */
public interface ServiceException {

	/**
	 * Indica nivel de excepción: FATAL / CONTINUABLE.
	 *
	 * @return el atributo nivel
	 */
	TypeNivelExcepcion getNivel();

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

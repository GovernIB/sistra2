package es.caib.sistramit.core.api.exception;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;

/**
 *
 * Contiene los datos de una excepcion de servicio.
 *
 */
@SuppressWarnings("serial")
public final class ServiceExceptionData implements Serializable {

	/**
	 * Detalles de la excepción.
	 */
	private ListaPropiedades detallesExcepcion;

	/**
	 * Fecha de la excepción.
	 */
	private Date fechaExcepcion = new Date();

	/**
	 * Obtiene los detalles de la excepción.
	 *
	 * @return Devuelbe detalles Excepcion
	 */
	public ListaPropiedades getDetallesExcepcion() {
		return detallesExcepcion;
	}

	/**
	 * Metodo de acceso a fechaExcepcion.
	 *
	 * @return fechaExcepcion
	 */
	public Date getFechaExcepcion() {
		return fechaExcepcion;
	}

	/**
	 * Metodo para establecer fechaExcepcion.
	 *
	 * @param pFechaExcepcion
	 *            fechaExcepcion a establecer
	 */
	public void setFechaExcepcion(final Date pFechaExcepcion) {
		fechaExcepcion = pFechaExcepcion;
	}

	/**
	 * Genera traza excepcion.
	 *
	 * @param excep
	 *            Excepcion
	 * @return Traza
	 */
	public String getTrazaError(final Throwable excep) {
		return ExceptionUtils.getStackTrace(excep);
	}

	/**
	 * Mótodo para establecer detallesExcepcion.
	 *
	 * @param pDetallesExcepcion
	 *            detallesExcepcion a establecer
	 */
	public void setDetallesExcepcion(final ListaPropiedades pDetallesExcepcion) {
		detallesExcepcion = pDetallesExcepcion;
	}

}

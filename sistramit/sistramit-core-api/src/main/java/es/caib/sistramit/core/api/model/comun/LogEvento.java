package es.caib.sistramit.core.api.model.comun;

import java.io.Serializable;
import java.util.Date;

/**
 * Log evento de auditoria.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class LogEvento implements Serializable {

	/**
	 * Identificador del usuario autenticado en la sesión.
	 */
	private String identificadorUsuario;

	/**
	 * Descripción evento.
	 */
	private String descripcion;

	/**
	 * Tipo evento.
	 */
	private String tipo;

	/**
	 * Fecha evento Log interno.
	 */
	private Date fecha;

	/**
	 * Resultado evento (depende del evento).
	 */
	private String resultado;

	/**
	 * Propiedades evento.
	 */
	private ListaPropiedades propiedades;

	/**
	 * En caso de tipo evento ERROR permite establecer el codigo de excepcion.
	 */
	private String excepcionError;

	/**
	 * En caso de tipo evento ERROR permite establecer la traza de la excepcion
	 * generada.
	 */
	private String trazaError;

	/**
	 * Método de acceso a tipoEvento de Log interno.
	 *
	 * @return tipoEvento
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipoEvento de Log interno..
	 *
	 * @param pTipoEvento
	 *            tipoEvento a establecer
	 */
	public void setTipo(final String pTipoEvento) {
		tipo = pTipoEvento;
	}

	/**
	 * Método de acceso a fecha de Log interno.
	 *
	 * @return fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Método para establecer fecha de Log interno.
	 *
	 * @param pFecha
	 *            fecha a establecer
	 */
	public void setFecha(final Date pFecha) {
		fecha = pFecha;
	}

	/**
	 * Método de acceso a descripcion de Log interno.
	 *
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Método para establecer descripcion de Log interno.
	 *
	 * @param pDescripcion
	 *            descripcion a establecer
	 */
	public void setDescripcion(final String pDescripcion) {
		descripcion = pDescripcion;
	}

	/**
	 * Método de acceso a resultado de Log interno.
	 *
	 * @return resultado
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * Método para establecer resultado de Log interno.
	 *
	 * @param pResultado
	 *            resultado a establecer
	 */
	public void setResultado(final String pResultado) {
		resultado = pResultado;
	}

	/**
	 * Método de acceso a propiedadesEvento de Log interno.
	 *
	 * @return propiedadesEvento
	 */
	public ListaPropiedades getPropiedadesEvento() {
		return propiedades;
	}

	/**
	 * Método para establecer propiedadesEvento de Log interno.
	 *
	 * @param pPropiedadesEvento
	 *            propiedadesEvento a establecer
	 */
	public void setPropiedadesEvento(final ListaPropiedades pPropiedadesEvento) {
		propiedades = pPropiedadesEvento;
	}

	/**
	 * Método de acceso a identificadorUsuario de Log interno.
	 *
	 * @return identificadorUsuario de Log interno.
	 */
	public String getIdentificadorUsuario() {
		return identificadorUsuario;
	}

	/**
	 * Método para establecer identificadorUsuario de Log interno.
	 *
	 * @param pIdentificadorUsuarioLogInterno
	 *            identificadorUsuario a establecer
	 */
	public void setIdentificadorUsuario(final String pIdentificadorUsuarioLogInterno) {
		identificadorUsuario = pIdentificadorUsuarioLogInterno;
	}

	/**
	 * Método de acceso a excepcionError de Log interno.
	 *
	 * @return excepcionError
	 */
	public String getExcepcionError() {
		return excepcionError;
	}

	/**
	 * Método para establecer excepcionError de Log interno.
	 *
	 * @param pExcepcionErrorLogInterno
	 *            excepcionError a establecer
	 */
	public void setExcepcionError(final String pExcepcionErrorLogInterno) {
		excepcionError = pExcepcionErrorLogInterno;
	}

	/**
	 * Método de acceso a trazaError de Log interno.
	 *
	 * @return trazaError
	 */
	public String getTrazaError() {
		return trazaError;
	}

	/**
	 * Método para establecer trazaError de Log interno.
	 *
	 * @param pTrazaErrorLogInterno
	 *            trazaError a establecer
	 */
	public void setTrazaError(final String pTrazaErrorLogInterno) {
		trazaError = pTrazaErrorLogInterno;
	}

}

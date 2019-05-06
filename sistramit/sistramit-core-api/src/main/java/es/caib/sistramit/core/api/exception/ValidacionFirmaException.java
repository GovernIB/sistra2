package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Error provocado al validar firma.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ValidacionFirmaException extends ServiceRollbackException {

	/*
	 * ValidacionFirmaException FATAL.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor ValidacionFirmaException.
	 *
	 * @param pMessage
	 *            Parámetro message
	 * @param pDetalles
	 *            Parámetro detalles
	 */
	public ValidacionFirmaException(final String pMessage, final ListaPropiedades pDetalles) {
		super(pMessage, pDetalles);
	}

	/**
	 * Constructor ValidacionFirmaException.
	 *
	 * @param pMessage
	 *            Parámetro message
	 * @param pCause
	 *            Parámetro cause
	 * @param pDetalles
	 *            Parámetro detalles
	 */
	public ValidacionFirmaException(final String pMessage, final Throwable pCause, final ListaPropiedades pDetalles) {
		super(pMessage, pCause, pDetalles);
	}

	/**
	 * Constructor ValidacionFirmaException.
	 *
	 * @param pMessage
	 *            Parámetro message
	 * @param pCause
	 *            Parámetro cause
	 */
	public ValidacionFirmaException(final String pMessage, final Throwable pCause) {
		super(pMessage, pCause);
	}

	/**
	 * Constructor ValidacionFirmaException.
	 *
	 * @param pMessage
	 *            Parámetro message
	 */
	public ValidacionFirmaException(final String pMessage) {
		super(pMessage);
	}

}

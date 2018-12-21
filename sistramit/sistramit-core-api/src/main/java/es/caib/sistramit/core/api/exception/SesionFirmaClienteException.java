package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Error provocado al realizar firma de cliente.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class SesionFirmaClienteException extends ServiceRollbackException {

	/*
	 * SesionFirmaClienteException ERROR.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.ERROR;
	}

	/**
	 * Constructor ParametrosEntradaIncorrectosException.
	 *
	 * @param pMessage
	 *            Parámetro message
	 * @param pDetalles
	 *            Parámetro detalles
	 */
	public SesionFirmaClienteException(final String pMessage, final ListaPropiedades pDetalles) {
		super(pMessage, pDetalles);
	}

	/**
	 * Constructor ParametrosEntradaIncorrectosException.
	 *
	 * @param pMessage
	 *            Parámetro message
	 * @param pCause
	 *            Parámetro cause
	 * @param pDetalles
	 *            Parámetro detalles
	 */
	public SesionFirmaClienteException(final String pMessage, final Throwable pCause,
			final ListaPropiedades pDetalles) {
		super(pMessage, pCause, pDetalles);
	}

	/**
	 * Constructor ParametrosEntradaIncorrectosException.
	 *
	 * @param pMessage
	 *            Parámetro message
	 * @param pCause
	 *            Parámetro cause
	 */
	public SesionFirmaClienteException(final String pMessage, final Throwable pCause) {
		super(pMessage, pCause);
	}

	/**
	 * Constructor ParametrosEntradaIncorrectosException.
	 *
	 * @param pMessage
	 *            Parámetro message
	 */
	public SesionFirmaClienteException(final String pMessage) {
		super(pMessage);
	}

}

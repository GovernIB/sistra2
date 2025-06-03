package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Error provocado al digitalizar documento.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class SesionDigitalizacionException extends ServiceRollbackException {

	/*
	 * SesionFirmaClienteException FATAL.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor ParametrosEntradaIncorrectosException.
	 *
	 * @param pMessage
	 *            Parámetro message
	 * @param pDetalles
	 *            Parámetro detalles
	 */
	public SesionDigitalizacionException(final String pMessage, final ListaPropiedades pDetalles) {
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
	public SesionDigitalizacionException(final String pMessage, final Throwable pCause,
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
	public SesionDigitalizacionException(final String pMessage, final Throwable pCause) {
		super(pMessage, pCause);
	}

	/**
	 * Constructor ParametrosEntradaIncorrectosException.
	 *
	 * @param pMessage
	 *            Parámetro message
	 */
	public SesionDigitalizacionException(final String pMessage) {
		super(pMessage);
	}

}

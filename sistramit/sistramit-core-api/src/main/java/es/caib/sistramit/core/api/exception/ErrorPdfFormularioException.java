package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que se ha producido un error al generar el pdf de
 * formulario para un formulario interno.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ErrorPdfFormularioException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor ErrorPdfFormularioException.
	 *
	 * @param pMessage
	 *            Mensaje de error.
	 * @param pOrigen
	 *            Excepcion origen.
	 */
	public ErrorPdfFormularioException(final String pMessage, final Throwable pOrigen) {
		super(pMessage, pOrigen);
	}

}

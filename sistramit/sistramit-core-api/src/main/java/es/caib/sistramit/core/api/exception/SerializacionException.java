package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que habido un error al serializar datos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class SerializacionException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		// SerializadorException FATAL.
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor SerializadorException.
	 *
	 * @param pMessage
	 *            Mensaje de error.
	 */
	public SerializacionException(final String pMessage) {
		super(pMessage);
	}

	/**
	 * Constructor SerializadorException.
	 *
	 * @param pMessage
	 *            Mensaje de error.
	 * @param pExc
	 *            Excepcion original.
	 */
	public SerializacionException(final String pMessage, final Throwable pExc) {
		super(pMessage, pExc);
	}

	/**
	 * Genera excepción ServiceRollbackException estableciendo un mensaje, la causa
	 * y los detalles.
	 *
	 * @param pMessage
	 *            Mensaje
	 * @param pCause
	 *            Causa
	 * @param pDetalles
	 *            Detalles de la excepción
	 */
	public SerializacionException(final String pMessage, final Throwable pCause, final ListaPropiedades pDetalles) {
		super(pMessage, pCause, pDetalles);
	}

}

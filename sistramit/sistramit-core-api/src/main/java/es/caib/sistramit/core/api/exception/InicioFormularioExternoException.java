package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que habido un error al iniciar formulario externo.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class InicioFormularioExternoException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		// TicketFormularioException FATAL.
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor TicketFormularioException.
	 *
	 * @param pMessage
	 *                     Mensaje de error.
	 */
	public InicioFormularioExternoException(final String pMessage) {
		super(pMessage);
	}

	/**
	 * Constructor TicketFormularioException.
	 *
	 * @param pMessage
	 *                     Mensaje de error.
	 * @param pExc
	 *                     Excepcion original.
	 */
	public InicioFormularioExternoException(final String pMessage, final Throwable pExc) {
		super(pMessage, pExc);
	}

	/**
	 * Genera excepción ServiceRollbackException estableciendo un mensaje, la causa
	 * y los detalles.
	 *
	 * @param pMessage
	 *                      Mensaje
	 * @param pCause
	 *                      Causa
	 * @param pDetalles
	 *                      Detalles de la excepción
	 */
	public InicioFormularioExternoException(final String pMessage, final Throwable pCause,
			final ListaPropiedades pDetalles) {
		super(pMessage, pCause, pDetalles);
	}

}

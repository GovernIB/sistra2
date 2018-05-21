package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que no esta permitido el acceso al trámite: control
 * plazo, validación script personalización y control acceso.
 *
 * El mensaje establecido en la excepción se mostrará al usuario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class AccesoNoPermitidoException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		// AccesoNoPermitidoException FATAL.
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor AccesoNoPermitidoException.
	 *
	 * @param pMessage
	 *            Mensaje de error.
	 */
	public AccesoNoPermitidoException(final String pMessage) {
		super(pMessage);
	}

	/**
	 * Constructor AccesoNoPermitidoException.
	 *
	 * @param message
	 *            Parámetro message
	 * @param detalles
	 *            Parámetro detalles
	 */
	public AccesoNoPermitidoException(final String message, final ListaPropiedades detalles) {
		super(message, detalles);
	}
}

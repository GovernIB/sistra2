package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción en los controles por tipo que no esta controlada.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class TipoNoControladoException extends ServiceRollbackException {

	/*
	 * TipoNoControladoException FATAL.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor TipoNoControladoException.
	 *
	 * @param pMessage
	 *            Mensaje de error.
	 */
	public TipoNoControladoException(final String pMessage) {
		super(pMessage);
	}

}

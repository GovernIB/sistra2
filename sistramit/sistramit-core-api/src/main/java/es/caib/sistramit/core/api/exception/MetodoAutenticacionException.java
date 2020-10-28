package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;

/**
 * Excepción si el método de autenticación no está permitido en el trámite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class MetodoAutenticacionException extends ServiceRollbackException {

	/*
	 * MetodoAutenticacionException FATAL.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor QaaException.
	 *
	 * @param pMessage
	 *                     Mensaje de error.
	 */
	public MetodoAutenticacionException(final TypeMetodoAutenticacion metodo) {
		super("Método de autenticación " + metodo.toString() + " no está permitido en el trámite");
	}

}

package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que la extensión del anexo no es valida.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class TamanyoMaximoAnexosAlcanzadoException extends ServiceRollbackException {

	/*
	 * ExtensionAnexoNoValidaException WARNING.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.WARNING;
	}

	/**
	 * Constructor ExtensionAnexoNoValidaException.
	 *
	 * @param tamanyo
	 *            Mensaje de error.
	 */
	public TamanyoMaximoAnexosAlcanzadoException(final String tamanyo) {
		super(tamanyo);
	}

}

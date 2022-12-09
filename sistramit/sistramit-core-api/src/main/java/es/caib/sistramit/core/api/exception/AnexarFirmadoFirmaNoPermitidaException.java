package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que se ha anexado un anexo firmado pero no se permite
 * que se anexe firmado correcta.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class AnexarFirmadoFirmaNoPermitidaException extends ServiceRollbackException {

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
	 * @param error
	 *                    Mensaje de error.
	 */
	public AnexarFirmadoFirmaNoPermitidaException(final String error) {
		super(error);
	}

}

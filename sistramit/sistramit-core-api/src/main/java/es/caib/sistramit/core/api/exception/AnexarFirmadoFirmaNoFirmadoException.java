package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que se ha anexado un anexo firmado que no tiene firma
 * correcta.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class AnexarFirmadoFirmaNoFirmadoException extends ServiceRollbackException {

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
	 *                    Mensaje de error.
	 */
	public AnexarFirmadoFirmaNoFirmadoException(final String tamanyo) {
		super(tamanyo);
	}

}

package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que se ha anexado un anexo y no se ha podido verificar si esta protegido.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class AnexarPdfNoVerificadoProtegidoException extends ServiceRollbackException {

	/*
	 * AnexarPdfProtegidoException WARNING.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.WARNING;
	}

	/**
	 * Constructor AnexarPdfProtegidoException.
	 */
	public AnexarPdfNoVerificadoProtegidoException(Throwable ex){
		super("No se ha podido verificar si PDF protegido con contraseña: " + ex.getMessage(), ex);
	}

}

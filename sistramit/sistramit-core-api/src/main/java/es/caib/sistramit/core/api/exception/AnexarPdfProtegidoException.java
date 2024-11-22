package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que se ha anexado un anexo protegido con contraseña.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class AnexarPdfProtegidoException extends ServiceRollbackException {

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
	public AnexarPdfProtegidoException(){
		super("PDF protegido con contraseña");
	}

}

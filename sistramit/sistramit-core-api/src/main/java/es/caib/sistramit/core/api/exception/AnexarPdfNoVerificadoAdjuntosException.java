package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que se ha anexado un anexo y no se ha podido verificar si esta protegido.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class AnexarPdfNoVerificadoAdjuntosException extends ServiceRollbackException {

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
	public AnexarPdfNoVerificadoAdjuntosException(Throwable ex){
		super("No se ha podido verificar si PDF tiene adjuntos: " + ex.getMessage(), ex);
	}

}

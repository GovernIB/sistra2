package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que no se ha podido verificar si es PADES.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class AnexarVerificarAdjuntosException extends ServiceRollbackException {

	/*
	 * AnexarVerificarPadesException WARNING.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.WARNING;
	}

	/**
	 * Constructor AnexarVerificarPadesException.
	 */
	public AnexarVerificarAdjuntosException(){
		super("No se puede anexar PDF que contengan adjuntos");
	}

}

package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepcion que indica que la sesión formulario se ha finalizado y no se puede
 * realizar una accion sobre él.
 */
@SuppressWarnings("serial")
public final class FormularioFinalizadoException extends ServiceRollbackException {

	/**
	 * Instancia un nuevo formulario no existe exception de
	 * FormularioFinalizadoException.
	 *
	 * @param message
	 *            Parámetro message
	 * @param detalles
	 *            Parámetro detalles
	 */
	public FormularioFinalizadoException(final String message, final ListaPropiedades detalles) {
		super(message, detalles);
	}

	/**
	 * Instancia un nuevo formulario no existe exception de
	 * FormularioFinalizadoException.
	 *
	 *
	 * @param cause
	 *            Parámetro cause
	 * @param message
	 *            Parámetro message
	 * @param detalles
	 *            Parámetro detalles
	 */
	public FormularioFinalizadoException(final String message, final Throwable cause, final ListaPropiedades detalles) {
		super(message, cause, detalles);
	}

	/**
	 * Instancia un nuevo formulario no existe exception de
	 * FormularioFinalizadoException.
	 *
	 * @param message
	 *            Parámetro message
	 */
	public FormularioFinalizadoException(final String message) {
		super(message);
	}

	/**
	 * Instancia un nuevo formulario no existe exception de
	 * FormularioFinalizadoException.
	 *
	 * @param message
	 *            Parámetro message
	 * @param cause
	 *            Parámetro cause
	 */
	public FormularioFinalizadoException(final String message, final Throwable cause) {
		super(message, cause);
	}

	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

}

package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Class FormularioNoExisteException.
 */
@SuppressWarnings("serial")
public final class FormularioNoExisteException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Instancia un nuevo formulario no existe exception de
	 * FormularioNoExisteException.
	 *
	 * @param message
	 *            Parámetro message
	 * @param detalles
	 *            Parámetro detalles
	 */
	public FormularioNoExisteException(final String message, final ListaPropiedades detalles) {
		super(message, detalles);
	}

	/**
	 * Instancia un nuevo formulario no existe exception de
	 * FormularioNoExisteException.
	 *
	 *
	 * @param cause
	 *            Parámetro cause
	 * @param message
	 *            Parámetro message
	 * @param detalles
	 *            Parámetro detalles
	 */
	public FormularioNoExisteException(final String message, final Throwable cause, final ListaPropiedades detalles) {
		super(message, cause, detalles);
	}

	/**
	 * Instancia un nuevo formulario no existe exception de
	 * FormularioNoExisteException.
	 *
	 * @param message
	 *            Parámetro message
	 */
	public FormularioNoExisteException(final String message) {
		super(message);
	}

	/**
	 * Instancia un nuevo formulario no existe exception de
	 * FormularioNoExisteException.
	 *
	 * @param message
	 *            Parámetro message
	 * @param cause
	 *            Parámetro cause
	 */
	public FormularioNoExisteException(final String message, final Throwable cause) {
		super(message, cause);
	}

}

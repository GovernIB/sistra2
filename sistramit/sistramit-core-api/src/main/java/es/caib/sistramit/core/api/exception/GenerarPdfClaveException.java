package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Excepcion lanzada al generar PDF de clave para anónimos.
 *
 */
@SuppressWarnings("serial")
public final class GenerarPdfClaveException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor GenerarPdfClaveException.
	 *
	 * @param message
	 *            Mensaje
	 * @param cause
	 *            Causa
	 */
	public GenerarPdfClaveException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor GenerarPdfClaveException.
	 *
	 * @param message
	 *            Mensaje
	 * @param xml
	 *            xml
	 * @param cause
	 *            Causa
	 */
	public GenerarPdfClaveException(final String message, final String xml, final Throwable cause) {
		super(message, cause);
		final ListaPropiedades props = new ListaPropiedades();
		if (xml != null) {
			props.addPropiedad("xml", xml);
		} else {
			props.addPropiedad("xml", "nulo");
		}
		this.setDetallesExcepcion(props);
	}

	/**
	 * Constructor GenerarPdfClaveException.
	 *
	 * @param message
	 *            Mensaje
	 */
	public GenerarPdfClaveException(final String message) {
		super(message);
	}

}

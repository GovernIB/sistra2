package es.caib.sistramit.frontend.model;

import java.io.Serializable;

import es.caib.sistramit.frontend.model.types.TypeRespuestaJSON;

/**
 * Mensaje a mostrar el usuario tras cargar el asistente. Se usa al volver de
 * formulario externo si hay error de validación.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class MensajeAsistente implements Serializable {

	/**
	 * Tipo mensaje.
	 */
	private TypeRespuestaJSON tipo;

	/**
	 * Mensaje.
	 */
	private MensajeUsuario mensaje;

	/**
	 * Método de acceso a tipo.
	 *
	 * @return tipo
	 */
	public TypeRespuestaJSON getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipo.
	 *
	 * @param pTipo
	 *            tipo a establecer
	 */
	public void setTipo(final TypeRespuestaJSON pTipo) {
		tipo = pTipo;
	}

	/**
	 * Método de acceso a mensaje.
	 *
	 * @return mensaje
	 */
	public MensajeUsuario getMensaje() {
		return mensaje;
	}

	/**
	 * Método para establecer mensaje.
	 *
	 * @param pMensaje
	 *            mensaje a establecer
	 */
	public void setMensaje(final MensajeUsuario pMensaje) {
		mensaje = pMensaje;
	}

}

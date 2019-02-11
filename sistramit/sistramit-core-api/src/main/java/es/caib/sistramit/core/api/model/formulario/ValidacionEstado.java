package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.formulario.types.TypeValidacionEstado;

@SuppressWarnings("serial")
public class ValidacionEstado implements Serializable {

	private TypeValidacionEstado estado = TypeValidacionEstado.CORRECTO;

	private MensajeAviso mensaje;

	/**
	 * Método de acceso a estado.
	 *
	 * @return estado
	 */
	public TypeValidacionEstado getEstado() {
		return estado;
	}

	/**
	 * Método para establecer estado.
	 *
	 * @param estado
	 *            estado a establecer
	 */
	public void setEstado(TypeValidacionEstado estado) {
		this.estado = estado;
	}

	/**
	 * Método de acceso a mensaje.
	 *
	 * @return mensaje
	 */
	public MensajeAviso getMensaje() {
		return mensaje;
	}

	/**
	 * Método para establecer mensaje.
	 *
	 * @param mensaje
	 *            mensaje a establecer
	 */
	public void setMensaje(MensajeAviso mensaje) {
		this.mensaje = mensaje;
	}

}

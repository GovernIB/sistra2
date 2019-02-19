package es.caib.sistramit.core.service.component.script;

import es.caib.sistramit.core.api.model.formulario.MensajeValidacion;

/**
 * Respuesta de la ejecución de un script.
 *
 * @author Indra
 *
 */
public final class RespuestaScript {

	/**
	 * Valor retornado por el script: un string o un plugin de tipo resultado (en
	 * caso de que el script retorne un valor).
	 */
	private Object resultado;

	/**
	 * En caso de que el script tenga asociada validación, se permite indicar
	 * mensaje.
	 */
	private MensajeValidacion mensajeValidacion;

	/**
	 * Método de acceso a resultado.
	 *
	 * @return resultado
	 */
	public Object getResultado() {
		return resultado;
	}

	/**
	 * Método para establecer resultado.
	 *
	 * @param resultado
	 *            resultado a establecer
	 */
	public void setResultado(Object resultado) {
		this.resultado = resultado;
	}

	/**
	 * Método de acceso a mensajeValidacion.
	 *
	 * @return mensajeValidacion
	 */
	public MensajeValidacion getMensajeValidacion() {
		return mensajeValidacion;
	}

	/**
	 * Método para establecer mensajeValidacion.
	 *
	 * @param mensajeValidacion
	 *            mensajeValidacion a establecer
	 */
	public void setMensajeValidacion(MensajeValidacion mensajeValidacion) {
		this.mensajeValidacion = mensajeValidacion;
	}

}

package es.caib.sistramit.frontend.model;

import es.caib.sistramit.frontend.model.types.TypeRespuestaJSON;

/**
 *
 * Modeliza una respuesta JSON.
 *
 */
public final class RespuestaJSON {

	/**
	 * Estado respuesta.
	 */
	private TypeRespuestaJSON estado = TypeRespuestaJSON.SUCCESS;

	/**
	 * Mensaje para usuario (opcional para SUCCESS).
	 */
	private MensajeUsuario mensaje = new MensajeUsuario("", "");

	/**
	 * Objeto con los datos de respuesta.
	 */
	private Object datos;

	/**
	 * Url de redirección tras la respuesta. Si no existe url no se redirige.
	 */
	private String url;

	/**
	 * Obtiene estado.
	 *
	 * @return estado
	 */
	public TypeRespuestaJSON getEstado() {
		return estado;
	}

	/**
	 * Establece estado.
	 *
	 * @param pestado
	 *            Estado
	 */
	public void setEstado(final TypeRespuestaJSON pestado) {
		this.estado = pestado;
	}

	/**
	 * Obtiene datos.
	 *
	 * @return datos.
	 */
	public Object getDatos() {
		return datos;
	}

	/**
	 * Establece datos.
	 *
	 * @param pdata
	 *            Datos
	 */
	public void setDatos(final Object pdata) {
		this.datos = pdata;
	}

	/**
	 * Obtiene mensaje.
	 *
	 * @return Mensaje
	 */
	public MensajeUsuario getMensaje() {
		return mensaje;
	}

	/**
	 * Establece mensaje.
	 *
	 * @param pmensaje
	 *            Mensaje
	 */
	public void setMensaje(final MensajeUsuario pmensaje) {
		this.mensaje = pmensaje;
	}

	/**
	 * Método de acceso a url.
	 *
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Método para establecer url.
	 *
	 * @param pUrl
	 *            url a establecer
	 */
	public void setUrl(final String pUrl) {
		url = pUrl;
	}
}

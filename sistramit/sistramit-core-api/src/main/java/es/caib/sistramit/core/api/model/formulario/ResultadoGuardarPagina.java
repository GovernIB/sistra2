package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Resultado de guardar la página: indica si se ha guardado bien, si no ha
 * pasado la validación y se ha generado un mensaje, etc. También indica si se
 * ha llegado al final del formulario en cuyo caso se deberá retornar al flujo
 * de tramitación invocando a guardar formulario del paso correspondiente
 * pasando el ticket de retorno.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResultadoGuardarPagina implements Serializable {

	/**
	 * Indica si se ha finalizado el formulario. En este caso se debe retornar al
	 * flujo de tramitación mediante la url indicada.
	 */
	private TypeSiNo finalizado = TypeSiNo.NO;

	/**
	 * En caso de no estar finalizado indicamos si debe recargar la pagina.
	 */
	private TypeSiNo recargar = TypeSiNo.NO;

	/**
	 * En caso de que se haya finalizado el formulario se indica la url para
	 * retornar al flujo de tramitación.
	 */
	private String url;

	/**
	 * Permite establecer un mensaje de validación.
	 */
	private MensajeValidacion validacion;

	/**
	 * Método de acceso a finalizado.
	 *
	 * @return finalizado
	 */
	public TypeSiNo getFinalizado() {
		return finalizado;
	}

	/**
	 * Método para establecer finalizado.
	 *
	 * @param finalizado
	 *                       finalizado a establecer
	 */
	public void setFinalizado(final TypeSiNo finalizado) {
		this.finalizado = finalizado;
	}

	/**
	 * Método de acceso a mensaje.
	 *
	 * @return mensaje
	 */
	public MensajeValidacion getValidacion() {
		return validacion;
	}

	/**
	 * Método para establecer mensaje.
	 *
	 * @param mensaje
	 *                    mensaje a establecer
	 */
	public void setValidacion(final MensajeValidacion mensaje) {
		this.validacion = mensaje;
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
	 * @param url
	 *                url a establecer
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * Método de acceso a recargar.
	 * 
	 * @return recargar
	 */
	public TypeSiNo getRecargar() {
		return recargar;
	}

	/**
	 * Método para establecer recargar.
	 * 
	 * @param recargar
	 *                     recargar a establecer
	 */
	public void setRecargar(final TypeSiNo recargar) {
		this.recargar = recargar;
	}

}

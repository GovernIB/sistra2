package es.caib.sistramit.core.api.model.flujo;

/**
 * Anexo que se puede anexar en formulario soporte.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class EntidadSoporteAnexo implements ModelApi {

	/** Extensiones (lista separada por comas). */
	private String extensiones;

	/** Tamaño máximo (tamaño acabado en MB o KB, p.e.: 1MB). */
	private String tamanyo;

	/**
	 * Método de acceso a extensiones.
	 *
	 * @return extensiones
	 */
	public String getExtensiones() {
		return extensiones;
	}

	/**
	 * Método para establecer extensiones.
	 *
	 * @param extensiones
	 *            extensiones a establecer
	 */
	public void setExtensiones(String extensiones) {
		this.extensiones = extensiones;
	}

	/**
	 * Método de acceso a tamanyo.
	 *
	 * @return tamanyo
	 */
	public String getTamanyo() {
		return tamanyo;
	}

	/**
	 * Método para establecer tamanyo.
	 *
	 * @param tamanyo
	 *            tamanyo a establecer
	 */
	public void setTamanyo(String tamanyo) {
		this.tamanyo = tamanyo;
	}

}
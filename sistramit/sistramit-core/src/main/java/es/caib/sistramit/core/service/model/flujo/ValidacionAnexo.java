package es.caib.sistramit.core.service.model.flujo;

/**
 * Resultado validación anexo.
 *
 * @author Indra
 *
 */
public class ValidacionAnexo {

	/** Indica si se ha anexado firmado. */
	private boolean anexadoFirmado;

	/**
	 * Método de acceso a anexadoFirmado.
	 * 
	 * @return anexadoFirmado
	 */
	public boolean isAnexadoFirmado() {
		return anexadoFirmado;
	}

	/**
	 * Método para establecer anexadoFirmado.
	 * 
	 * @param anexadoFirmado
	 *                           anexadoFirmado a establecer
	 */
	public void setAnexadoFirmado(final boolean anexadoFirmado) {
		this.anexadoFirmado = anexadoFirmado;
	}

}

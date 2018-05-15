package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Distintas acciones del paso de tramitación Anexar.
 *
 * @author Indra
 *
 */
public enum TypeAccionPasoAnexar implements TypeAccionPaso {

	// TODO Pendiente definir accciones

	/**
	 * Descargar anexo. Parámetros entrada: idAnexo, instancia Parámetros salida:
	 * nombreFichero, datosFichero (byte[])
	 */
	DESCARGAR_ANEXO(false),
	/**
	 * Anexar documento. Parámetros entrada: idAnexo, nombreFichero, datosFichero
	 * (byte[]), titulo Parámetros salida: conversionPDF (true/false)
	 */
	ANEXAR_DOCUMENTO;

	/**
	 * Indica si la acción modifica datos del paso.
	 */
	private boolean modificaPasoAnexar = true;

	/**
	 * Constructor.
	 *
	 * @param pmodificaPaso
	 *            Indica si modifica el paso.
	 */
	private TypeAccionPasoAnexar(final boolean pmodificaPaso) {
		modificaPasoAnexar = pmodificaPaso;
	}

	/**
	 * Constructor.
	 */
	private TypeAccionPasoAnexar() {
	}

	@Override
	public boolean isModificaPaso() {
		return modificaPasoAnexar;
	}

}

package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Distintas acciones del paso de tramitación Anexar.
 *
 * @author Indra
 *
 */
public enum TypeAccionPasoRegistrar implements TypeAccionPaso {

	// TODO PENDIENTE PARAMETROS ACCIONES FIRMA

	/**
	 * Descargar documento. Parámetros entrada: idDocumento, instancia. Parámetros
	 * salida: nombreFichero, datosFichero.
	 */
	DESCARGAR_DOCUMENTO(false),
	/**
	 * Iniciar firma documento.
	 */
	INICIAR_FIRMA_DOCUMENTO,
	/**
	 * Verificar firma documento.
	 */
	VERIFICAR_FIRMA_DOCUMENTO,
	/**
	 * Registrar tramite. Parámetros entrada: reintentar (TypeSiNo). Parámetros
	 * salida: resultado (TypeResultadoRegistro), numeroRegistro.
	 */
	REGISTRAR_TRAMITE;

	/**
	 * Indica si la acción modifica datos del paso.
	 */
	private boolean modificaPasoRegistrar = true;

	/**
	 * Constructor.
	 *
	 * @param pmodificaPaso
	 *            Indica si modifica el paso.
	 */
	private TypeAccionPasoRegistrar(final boolean pmodificaPaso) {
		modificaPasoRegistrar = pmodificaPaso;
	}

	/**
	 * Constructor.
	 */
	private TypeAccionPasoRegistrar() {
	}

	@Override
	public boolean isModificaPaso() {
		return modificaPasoRegistrar;
	}
}

package es.caib.sistramit.core.service.component.integracion;

import es.caib.sistramit.core.service.model.system.EnvioAviso;

/**
 * Interface EnvioComponent.
 */
public interface EnvioAvisoComponent {

	/**
	 * Procesa envíos inmediatos.
	 */
	void procesarEnviosInmediatos();

	/**
	 * Proceso reintentos.
	 */
	void procesarEnviosReintentos();

	/**
	 * Guarda envio.
	 *
	 * @param envio
	 *                  envio
	 */
	void generarEnvio(EnvioAviso envio);

}

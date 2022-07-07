package es.caib.sistramit.core.service.component.integracion;

import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistramit.core.api.model.flujo.ResultadoRegistrar;

/**
 * Interface EnvioComponent.
 */
public interface EnvioRemotoComponent {

	/**
	 * Inicia sesión envío.
	 *
	 * @param idEntidad
	 *                          id Entidad
	 * @param idEnvioRemoto
	 *                          id Envio Remoto
	 * @param debugEnabled
	 *                          Indica si se debugea
	 * @return id sesión envío
	 */
	String iniciarSesionEnvio(String idEntidad, String idEnvioRemoto, boolean debugEnabled);

	/**
	 * Realiza envío.
	 *
	 * @param idEntidad
	 *                          id Entidad
	 * @param idEnvioRemoto
	 *                          id Envio Remoto
	 * @param idSesionEnvio
	 *                          id sesion envío
	 * @param asiento
	 *                          asiento (incluido datos propios)
	 * @param debugEnabled
	 *                          Indica si se debugea
	 * @return Resultado envío
	 */
	ResultadoRegistrar realizarEnvio(String idEntidad, String idEnvioRemoto, String idSesionTramitacion,
			String idSesionEnvio, AsientoRegistral asiento, boolean debugEnabled);

	/**
	 * Verifica si ha finalizado proceso de envío.
	 *
	 * * @param idEntidad id Entidad
	 * 
	 * @param idEnvioRemoto
	 *                          id Envio Remoto
	 * @param idSesionEnvio
	 *                          id sesion envío
	 * @param debugEnabled
	 *                          Indica si se debugea
	 * @return Resultado envío
	 */
	ResultadoRegistrar reintentarEnvio(String idEntidad, String idEnvioRemoto, String idSesionEnvio,
			boolean debugEnabled);

}

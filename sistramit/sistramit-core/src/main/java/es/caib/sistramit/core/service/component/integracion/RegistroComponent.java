package es.caib.sistramit.core.service.component.integracion;

import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistra2.commons.plugins.registro.api.ResultadoJustificante;
import es.caib.sistramit.core.api.model.flujo.ResultadoRegistrar;
import es.caib.sistramit.core.api.model.flujo.types.TypeDescargaJustificante;

/**
 * Interface RegistroComponent.
 */
public interface RegistroComponent {

	/**
	 * Inicia sesión registro.
	 *
	 * @param codigoEntidad
	 *                          codigo entidad
	 * @param debugEnabled
	 *                          Indica si se debugea
	 * @return id sesión registro
	 */
	String iniciarSesionRegistro(String codigoEntidad, boolean debugEnabled);

	/**
	 * Realiza registro.
	 *
	 * @param codigoEntidad
	 *                                codigo entidad
	 * @param idSesionTramitacion
	 *                                id sesion tramitacion
	 * @param asiento
	 *                                asiento (incluido datos propios)
	 * @param debugEnabled
	 *                                Indica si se debugea
	 * @return Resultado registro
	 */
	ResultadoRegistrar registrar(String codigoEntidad, String idSesionTramitacion, String idSesionRegistro,
			AsientoRegistral asiento, boolean debugEnabled);

	/**
	 * Verifica si ha finalizado proceso de registro.
	 *
	 * @param idSesionRegistro
	 *                             id sesion registro
	 * @param debugEnabled
	 *                             Indica si se debugea
	 * @return Resultado registro
	 */
	ResultadoRegistrar reintentarRegistro(String codigoEntidad, String idSesionRegistro, boolean debugEnabled);

	/**
	 * Obtiene justificante de registro.
	 *
	 * @param codigoEntidad
	 *                           código entidad
	 * @param numeroRegistro
	 *                           número de registro
	 * @param debugEnabled
	 *                           Indica si se debugea
	 * @return justificante registro
	 */
	ResultadoJustificante obtenerJustificanteRegistro(String codigoEntidad, String numeroRegistro,
			boolean debugEnabled);

	/**
	 * Obtener libro organismo.
	 *
	 * @param codigoEntidad
	 *                            código entidad
	 * @param codigoOrganismo
	 *                            código organismo
	 * @param debugEnabled
	 *                            debug
	 * @return libro organismo
	 */
	String obtenerLibroOrganismo(String codigoEntidad, String codigoOrganismo, boolean debugEnabled);

	/**
	 * Obtenemos como se descargan los justificantes.
	 *
	 * @param codigoEntidad
	 *                          Código entidad
	 * @return como se descargan los justificantes
	 */
	TypeDescargaJustificante descargaJustificantes(String codigoEntidad);

}

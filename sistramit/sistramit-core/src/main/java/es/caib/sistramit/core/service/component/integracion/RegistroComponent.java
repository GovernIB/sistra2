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
	 * Realiza registro.
	 *
	 * @param idSesionTramitacion
	 *            id sesion tramitacion
	 * @param asiento
	 *            asiento (incluido datos propios)
	 * @param debugEnabled
	 *            Indica si se debugea
	 * @return Resultado registro
	 */
	ResultadoRegistrar registrar(String idSesionTramitacion, AsientoRegistral asiento, boolean debugEnabled);

	/**
	 * Verifica si ha finalizado proceso de registro.
	 *
	 * @param idSesionTramitacion
	 *            id sesion tramitacion
	 * @param debugEnabled
	 *            Indica si se debugea
	 * @return Resultado registro
	 */
	ResultadoRegistrar reintentarRegistro(String idSesionTramitacion, boolean debugEnabled);

	/**
	 * Obtiene justificante de registro.
	 *
	 * @param codigoEntidad
	 *            código entidad
	 * @param numeroRegistro
	 *            número de registro
	 * @param debugEnabled
	 *            Indica si se debugea
	 * @return justificante registro
	 */
	ResultadoJustificante obtenerJustificanteRegistro(String codigoEntidad, String numeroRegistro,
			boolean debugEnabled);

	/**
	 * Obtener libro organismo.
	 *
	 * @param codigoEntidad
	 *            código entidad
	 * @param codigoOrganismo
	 *            código organismo
	 * @param debugEnabled
	 *            debug
	 * @return libro organismo
	 */
	String obtenerLibroOrganismo(String codigoEntidad, String codigoOrganismo, boolean debugEnabled);

	/**
	 * Obtenemos como se descargan los justificantes.
	 *
	 * @param codigoEntidad
	 *            Código entidad
	 * @return como se descargan los justificantes
	 */
	TypeDescargaJustificante descargaJustificantes(String codigoEntidad);

}

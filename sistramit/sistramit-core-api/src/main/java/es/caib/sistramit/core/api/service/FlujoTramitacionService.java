package es.caib.sistramit.core.api.service;

import java.util.Map;

import es.caib.sistramit.core.api.model.flujo.DetalleTramite;

/**
 * Servicio flujo tramitacion.
 *
 * @author Indra
 *
 */
public interface FlujoTramitacionService {

	/**
	 * Iniciar tramite.
	 *
	 * @param idTramite
	 *            id tramite
	 * @param version
	 *            version tramite
	 * @param idioma
	 *            idioma
	 * @param idTramiteCatalogo
	 *            id procedimiento
	 * @param urlInicio
	 *            url inicio
	 * @param parametrosInicio
	 *            parametros inicio
	 * @return id sesion tramitacion
	 */
	public String iniciarTramite(final String idTramite, final int version, final String idioma,
			final String idTramiteCatalogo, final String urlInicio, final Map<String, String> parametrosInicio);

	/**
	 * Obtiene detalle trámite.
	 *
	 * @param idSesionTramitacion
	 *            id sesión tramitación
	 * @return detalle trámite
	 */
	public DetalleTramite obtenerDetalleTramite(final String idSesionTramitacion);

	// -------------------------------------------------------------------------------------------
	// - Métodos especiales invocados desde el interceptor. No pasan por interceptor
	// de auditoria.
	// -------------------------------------------------------------------------------------------
	/**
	 * Función interna que no pasa por interceptor de auditoría. Sirve para obtener
	 * detalle del flujo desde el propio interceptor (solo debe ser usada desde el
	 * interceptor).
	 */
	public DetalleTramite obtenerFlujoTramitacionInfo(final String idSesionTramitacion);

	/**
	 * Función interna que no pasa por interceptor de auditoría. Sirve para
	 * invalidar automáticamente el el flujo desde el propio interceptor (solo debe
	 * ser usada desde el interceptor).
	 */
	public void invalidarFlujoTramitacion(final String idSesionTramitacion);

}

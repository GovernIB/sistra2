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
	 * @param idProcedimiento
	 *            id procedimiento
	 * @param urlInicio
	 *            url inicio
	 * @param parametrosInicio
	 *            parametros inicio
	 * @return id sesion tramitacion
	 */
	public String iniciarTramite(final String idTramite, final int version, final String idioma,
			final String idProcedimiento, final String urlInicio, final Map<String, String> parametrosInicio);

	/**
	 * Obtiene detalle trámite.
	 * 
	 * @param idSesionTramitacion
	 *            id sesión tramitación
	 * @return detalle trámite
	 */
	public DetalleTramite obtenerDetalleTramite(final String idSesionTramitacion);

}

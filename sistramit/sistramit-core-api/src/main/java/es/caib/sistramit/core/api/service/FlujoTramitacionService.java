package es.caib.sistramit.core.api.service;

import java.util.Map;

import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;

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
	String iniciarTramite(final String idTramite, final int version, final String idioma,
			final String idTramiteCatalogo, final String urlInicio, final Map<String, String> parametrosInicio);

	 /**
     * Carga un trámite existente de persistencia.
     *
     * @param idSesionTramitacion
     *            Id sesión de tramitación
     * @return Paso actual
     */
    ResultadoIrAPaso cargarTramite(String idSesionTramitacion);

	/**
	 * Obtiene detalle trámite.
	 *
	 * @param idSesionTramitacion
	 *            id sesión tramitación
	 * @return detalle trámite
	 */
	DetalleTramite obtenerDetalleTramite(final String idSesionTramitacion);

	 /**
     * Recarga tramite que existe en una sesion de front (vuelta de formularios
     * o recuperacion errores).
     *
     * @param idSesionTramitacion
     *            Id sesión de tramitación
     * @return Paso actual
     */
    ResultadoIrAPaso recargarTramite(String idSesionTramitacion);

    /**
     * Va al paso indicado.
     *
     * @param idSesionTramitacion
     *            Id sesión de tramitación
     * @param idPaso
     *            Identificador de paso.
     * @return Identificador del paso en el que se queda el trámite.
     */
    ResultadoIrAPaso irAPaso(String idSesionTramitacion, String idPaso);

    /**
     * Realiza la acción indicada en el paso.
     *
     * @param idSesionTramitacion
     *            Id sesión de tramitación
     * @param idPaso
     *            Identificador del paso.
     * @param accionPaso
     *            Acción a realizar en el paso (depende del paso y de la
     *            acción).
     * @param parametros
     *            Parámetros del paso (depende del paso y de la acción).
     * @return Devuelve parámetros de retorno del paso (depende del paso y de la
     *         acción).
     */
    ResultadoAccionPaso accionPaso(String idSesionTramitacion, String idPaso, TypeAccionPaso accionPaso,
            ParametrosAccionPaso parametros);


    /**
     * Cancela el trámite provocando su eliminación.
     *
     * @param idSesionTramitacion
     *            Id sesión de tramitación
     */
    void cancelarTramite(String idSesionTramitacion);


	// -------------------------------------------------------------------------------------------
	// - Métodos especiales invocados desde el interceptor. No pasan por interceptor
	// de auditoria.
	// -------------------------------------------------------------------------------------------
	/**
	 * Función interna que no pasa por interceptor de auditoría. Sirve para obtener
	 * detalle del flujo desde el propio interceptor (solo debe ser usada desde el
	 * interceptor).
	 */
	DetalleTramite obtenerFlujoTramitacionInfo(final String idSesionTramitacion);

	/**
	 * Función interna que no pasa por interceptor de auditoría. Sirve para
	 * invalidar automáticamente el el flujo desde el propio interceptor (solo debe
	 * ser usada desde el interceptor).
	 */
	void invalidarFlujoTramitacion(final String idSesionTramitacion);

}

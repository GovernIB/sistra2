package es.caib.sistramit.core.api.service;

import java.util.Map;

import es.caib.sistramit.core.api.model.flujo.AnexoFichero;
import es.caib.sistramit.core.api.model.flujo.DetallePasos;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.FlujoTramitacionInfo;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;

/**
 * Servicio flujo tramitacion.
 *
 * @author Indra
 *
 */
public interface FlujoTramitacionService {

    /**
     * Genera sesón de tramitación.
     *
     * @param usuarioAutenticado
     *            usuario autenticado
     * @return id sesion tramitacion
     */
    String crearSesionTramitacion(UsuarioAutenticadoInfo usuarioAutenticado);

    /**
     * Iniciar tramite.
     *
     * @param idSesionTramitacion
     *            id Sesion Tramitacion
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
     *
     */
    void iniciarTramite(String idSesionTramitacion, final String idTramite,
            final int version, final String idioma,
            final String idTramiteCatalogo, final String urlInicio,
            final Map<String, String> parametrosInicio);

    /**
     * Carga un trámite existente de persistencia.
     *
     * @param idSesionTramitacion
     *            Id sesión de tramitación
     * @param usuarioAutenticadoInfo
     *            info usuario
     */
    void cargarTramite(String idSesionTramitacion,
            UsuarioAutenticadoInfo usuarioAutenticadoInfo);

    /**
     * Recarga tramite que existe en una sesion de front (vuelta de formularios
     * o recuperacion errores).
     *
     * @param idSesionTramitacion
     *            Id sesión de tramitación
     * @param userInfo
     *            info usuario autenticado
     */
    void recargarTramite(String idSesionTramitacion,
            UsuarioAutenticadoInfo userInfo);

    /**
     * Obtiene detalle trámite.
     *
     * @param idSesionTramitacion
     *            id sesión tramitación
     * @return detalle trámite
     */
    DetalleTramite obtenerDetalleTramite(final String idSesionTramitacion);

    /**
     * Obtiene detalle paso actual.
     *
     * @param idSesionTramitacion
     *            id sesión tramitación
     * @return detalle trámite
     */
    DetallePasos obtenerDetallePasos(final String idSesionTramitacion);

    /**
     * Va al paso indicado.
     *
     * @param idSesionTramitacion
     *            Id sesión de tramitación
     * @param idPaso
     *            Identificador de paso.
     * @return Estado pasos
     */
    ResultadoIrAPaso irAPaso(String idSesionTramitacion, String idPaso);

    /**
     * Ir a paso actual.
     *
     * @param idSesionTramitacion
     *            Id sesión de tramitación
     * @return Estado pasos
     */
    ResultadoIrAPaso irAPasoActual(String idSesionTramitacion);

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
    ResultadoAccionPaso accionPaso(String idSesionTramitacion, String idPaso,
            TypeAccionPaso accionPaso, ParametrosAccionPaso parametros);

    /**
     * Cancela el trámite provocando su eliminación.
     *
     * @param idSesionTramitacion
     *            Id sesión de tramitación
     */
    void cancelarTramite(String idSesionTramitacion);

    /**
     * Envío formulario de soporte.
     *
     * @param idSesionTramitacion
     *            id sesión tramitación
     * @param nif
     *            nif
     * @param nombre
     *            nombre
     * @param telefono
     *            telefono
     * @param email
     *            email
     * @param problemaTipo
     *            problema tipo
     * @param problemaDesc
     *            problema descripción
     * @param anexo
     *            anexo
     */
    void envioFormularioSoporte(String idSesionTramitacion, String nif,
            String nombre, String telefono, String email, String problemaTipo,
            String problemaDesc, AnexoFichero anexo);

    /** Realiza purga flujos tramitación. */
    void purgar();

    // TODO PARA BORRAR
    String simularRellenarFormulario(String idSesionTramitacion, String xml);

    // -------------------------------------------------------------------------------------------
    // - Métodos especiales invocados desde el interceptor. No pasan por
    // interceptor de auditoria.
    // -------------------------------------------------------------------------------------------
    /**
     * Función interna que no pasa por interceptor de auditoría. Sirve para
     * obtener detalle del flujo desde el propio interceptor (solo debe ser
     * usada desde el interceptor).
     *
     * @param idSesionTramitacion
     *            id sesión tramitación
     * @return detalle trámite
     */
    FlujoTramitacionInfo obtenerFlujoTramitacionInfo(
            final String idSesionTramitacion);

    /**
     * Función interna que no pasa por interceptor de auditoría. Sirve para
     * invalidar automáticamente el el flujo desde el propio interceptor (solo
     * debe ser usada desde el interceptor).
     *
     * @param idSesionTramitacion
     *            id sesión tramitación
     */
    void invalidarFlujoTramitacion(final String idSesionTramitacion);

}

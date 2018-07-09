package es.caib.sistramit.core.service.component.flujo;

import es.caib.sistramit.core.api.model.flujo.DetallePaso;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResPersonalizacionTramite;
import es.caib.sistramit.core.service.model.flujo.DatosSesionTramitacion;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;

/**
 * Modificaciones del flujo. A través de este objeto se modificarán los datos de
 * sesión.
 *
 * @author Indra
 *
 */
public interface ModificacionesFlujo {

    /**
     * Genera variables accesibles desde el paso de tramitación. Permitirán
     * acceder a datos externos al paso (propiedades del trámite, datos de otros
     * formularios, etc.).
     *
     * @param pDatosSesion
     *            Datos de sesión de tramitación.
     * @param idPaso
     *            Genera las variables accesibles para el paso. Si es nulo se
     *            generará variables a nivel de trámite, sin tener en cuenta los
     *            pasos.
     * @return Variables de flujo
     */
    VariablesFlujo generarVariablesFlujo(
            final DatosSesionTramitacion pDatosSesion, final String idPaso);

    /**
     * Actualiza estado de trámite.
     *
     * @param datosSesion
     *            Datos sesión
     */
    void actualizarEstadoTramite(final DatosSesionTramitacion datosSesion);

    /**
     * Personaliza trámite en función de los datos de personalización.
     *
     * @param pDatosSesion
     *            Datos sesión tramitación
     * @param rp
     *            Datos de personalización provenientes del script de
     *            personalización
     */
    void personalizarTramite(final DatosSesionTramitacion pDatosSesion,
            final ResPersonalizacionTramite rp);

    /**
     * Invoca accion paso.
     *
     * @param pDatosSesion
     *            Datos sesión
     * @param pIdPaso
     *            Id paso
     * @param pAccionPaso
     *            Acción paso
     * @param pParametros
     *            Parámetros acción
     * @return Respuesta paso
     */
    RespuestaAccionPaso invocarAccionPaso(
            final DatosSesionTramitacion pDatosSesion, final String pIdPaso,
            final TypeAccionPaso pAccionPaso,
            final ParametrosAccionPaso pParametros);

    /**
     * Crea el trámite en persistencia.
     *
     * @param pDatosSesion
     *            Datos sesión
     */
    void crearTramitePersistencia(final DatosSesionTramitacion pDatosSesion);

    /**
     * Añade paso de tramitación.
     *
     * @param pDatosSesion
     *            Datos sesión
     * @param idPaso
     *            Id paso
     * @param tipo
     *            Tipo paso
     */
    void addPasoTramitacion(final DatosSesionTramitacion pDatosSesion,
            final String idPaso, final TypePaso tipo);

    /**
     * Elimina último paso de tramitación.
     *
     * @param pDatosSesion
     *            Datos sesión
     */
    void removeUltimoPasoTramitacion(final DatosSesionTramitacion pDatosSesion);

    /**
     * Invalida paso de tramitación marcandolo para revisar.
     *
     * @param pDatosSesion
     *            Datos sesión
     *
     * @param idPaso
     *            Id paso
     */
    void invalidarPasoTramitacion(final DatosSesionTramitacion pDatosSesion,
            final String idPaso);

    /**
     * Establece accesiblidad del paso.
     *
     * @param pDatosSesion
     *            Datos sesión
     * @param idPaso
     *            Id paso
     * @param accesible
     *            Indica si es accesible
     */
    void establecerAccesibilidadPaso(final DatosSesionTramitacion pDatosSesion,
            final String idPaso, final boolean accesible);

    /**
     * Establece si el paso es solo lectura.
     *
     * @param pDatosSesion
     *            Datos sesión
     * @param idPaso
     *            Id paso
     * @param soloLectura
     *            Indica si es solo lectura
     */
    void establecerSoloLecturaPaso(final DatosSesionTramitacion pDatosSesion,
            final String idPaso, final boolean soloLectura);

    /**
     * Carga pasos desde persistencia.
     *
     * @param pDatosSesion
     *            Datos sesión
     */
    void cargarPasosPersistencia(final DatosSesionTramitacion pDatosSesion);

    /**
     * Registra acceso al trámite actualizando en su caso la fecha de caducidad.
     *
     * @param pDatosSesion
     *            Datos sesion
     */
    void registrarAccesoTramite(final DatosSesionTramitacion pDatosSesion);

    /**
     * Establece paso actual.
     *
     * @param pDatosSesion
     *            Datos sesión
     * @param pIdPaso
     *            Id paso
     * @return indica si se podido establecer el paso como actual
     *
     */
    boolean establecerPasoActual(final DatosSesionTramitacion pDatosSesion,
            final String pIdPaso);

    /**
     * Obtiene detalle del paso.
     *
     * @param pDatosSesion
     *            Datos sesión
     * @param pIdPaso
     *            Id paso
     * @return detalle del paso.
     */
    DetallePaso obtenerDetallePaso(final DatosSesionTramitacion pDatosSesion,
            final String pIdPaso);

    /**
     * Calcula cual es el siguiente paso al actual teniendo en cuenta el script
     * de navegación.
     *
     * @param pDatosSesion
     *            Datos sesión
     */
    void calcularSiguientePaso(final DatosSesionTramitacion pDatosSesion);

    /**
     * Ejecuta script de personalización del trámite.
     *
     * @param pDatosSesion
     *            Datos sesión
     * @return Respuesta del script
     */
    RespuestaScript ejecutarScriptPersonalizacion(
            final DatosSesionTramitacion pDatosSesion);

    /**
     * Calcula parametros iniciales tramite evaluando script parametros
     * iniciales del trámite.
     *
     * @param pDatosSesion
     *            Datos sesión
     *
     */
    void calcularParametrosInicialesTramite(
            final DatosSesionTramitacion pDatosSesion);

    /**
     * Cancela trámite.
     *
     * @param pDatosSesion
     *            Datos sesión
     */
    void cancelarTramite(final DatosSesionTramitacion pDatosSesion);

    /**
     * Calcula metodos disponibles de login.
     *
     * @param pDatosSesion
     *            Datos sesion
     */
    void calcularMetodosLogin(DatosSesionTramitacion pDatosSesion);

}

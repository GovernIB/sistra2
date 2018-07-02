package es.caib.sistramit.core.service.component.flujo.pasos;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.exception.ConfiguracionModificadaException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DetallePaso;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoAccion;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoAnexar;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoCapturar;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoDebeSaber;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoGuardar;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoInformacion;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoPagar;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoRegistrar;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoRellenar;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.literales.Literales;
import es.caib.sistramit.core.service.component.script.ScriptExec;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoReferencia;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.EstadoSubestadoPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeFaseActualizacionDatosInternos;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Implementa lógica común de un controlador de paso basado en tener cacheado en
 * memoria el detalle del paso.
 *
 * @author Indra
 *
 */
public abstract class ControladorPasoReferenciaImpl implements ControladorPaso {

    /** Dao del paso. */
    @Autowired
    private FlujoPasoDao dao;
    /** Motor de ejecución de scritps. */
    @Autowired
    private ScriptExec scriptFlujo;
    /** Literales negocio. */
    @Autowired
    private Literales literales;
    /** Acceso a configuracion modulo. */
    @Autowired
    private ConfiguracionComponent config;

    /** Log. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ControladorPasoReferenciaImpl.class);

    @Override
    public final void inicializarPaso(final DatosPaso pDatosPaso,
            final DefinicionTramiteSTG pDefinicionTramite,
            final VariablesFlujo pVariablesFlujo) {
        if (pVariablesFlujo.isDebugEnabled()) {
            debug("Inicializando paso " + pDatosPaso.getIdPaso());
        }
        if (pDatosPaso.getEstado() != TypeEstadoPaso.NO_INICIALIZADO) {
            throw new AccionPasoNoPermitidaException(
                    "No se puede inicializar el paso porque el paso no esta pendiente de inicializar");
        }
        // Recuperamos datos de persistencia (no existira ningun documento
        // todavia)
        final DatosPersistenciaPaso dpp = dao.obtenerPasoPersistencia(
                pVariablesFlujo.getIdSesionTramitacion(),
                pDatosPaso.getIdPaso());
        // Actualizamos datos internos paso (se crearan los documentos y se
        // inicializara estado paso)
        recalcularDatosInternos(pDatosPaso, dpp, pDefinicionTramite,
                pVariablesFlujo,
                TypeFaseActualizacionDatosInternos.INICIALIZAR_PASO);
        if (pVariablesFlujo.isDebugEnabled()) {
            debug("Paso inicializado: " + pDatosPaso.getIdPaso());
        }

    }

    @Override
    public final void cargarPaso(final DatosPaso pDatosPaso,
            final DefinicionTramiteSTG pDefinicionTramite,
            final VariablesFlujo pVariablesFlujo) {
        if (pVariablesFlujo.isDebugEnabled()) {
            debug("Cargando paso " + pDatosPaso.getIdPaso());
        }
        // Recuperamos datos de persistencia
        final DatosPersistenciaPaso dpp = dao.obtenerPasoPersistencia(
                pVariablesFlujo.getIdSesionTramitacion(),
                pDatosPaso.getIdPaso());

        // Si el paso esta no inicializado o para revisar no actualizamos datos
        // internos (ya se actualizaran al inicializar o revisar).
        if (dpp.getEstado() != TypeEstadoPaso.NO_INICIALIZADO
                && dpp.getEstado() != TypeEstadoPaso.REVISAR) {

            // Obtenemos estado actual
            final TypeEstadoPaso estadoActual = dpp.getEstado();

            // Actualizamos datos internos paso a partir de los datos de
            // persistencia
            recalcularDatosInternos(pDatosPaso, dpp, pDefinicionTramite,
                    pVariablesFlujo,
                    TypeFaseActualizacionDatosInternos.CARGAR_PASO);
            // Comprobamos que el estado en el que se queda el paso es
            // el mismo que estaba en persistencia.
            if (dpp.getEstado() != estadoActual) {
                // En caso contrario debe estar en el mismo estado sino implica
                // que ha habido un cambio en la definicion del tramite
                throw new ConfiguracionModificadaException(
                        "El estado actual del paso: " + dpp.getEstado().name()
                                + " no concuerda con el almacenado: "
                                + estadoActual.name());
            }
        } else {
            // Si esta no inicializado o para revisar simplemente establecemos
            // estado paso
            pDatosPaso.internalData().setEstado(dpp.getEstado());
        }
        if (pVariablesFlujo.isDebugEnabled()) {
            debug("Paso cargado: " + pDatosPaso.getIdPaso());
        }
    }

    @Override
    public final void revisarPaso(final DatosPaso pDatosPaso,
            final DefinicionTramiteSTG pDefinicionTramite,
            final VariablesFlujo pVariablesFlujo) {
        if (pVariablesFlujo.isDebugEnabled()) {
            debug("Revisar paso " + pDatosPaso.getIdPaso());
        }
        if (pDatosPaso.getEstado() != TypeEstadoPaso.REVISAR) {
            throw new AccionPasoNoPermitidaException(
                    "No se puede revisar el paso porque el paso no esta pendiente de revisar");
        }
        // Recuperamos datos de persistencia
        final DatosPersistenciaPaso dpp = dao.obtenerPasoPersistencia(
                pVariablesFlujo.getIdSesionTramitacion(),
                pDatosPaso.getIdPaso());
        // Regeneramos datos paso a partir definición y datos de persistencia
        recalcularDatosInternos(pDatosPaso, dpp, pDefinicionTramite,
                pVariablesFlujo,
                TypeFaseActualizacionDatosInternos.REVISAR_PASO);
        if (pVariablesFlujo.isDebugEnabled()) {
            debug("Paso revisado: " + pDatosPaso.getIdPaso());
        }
    }

    @Override
    public final void invalidarPaso(final DatosPaso pDatosPaso,
            final DefinicionTramiteSTG pDefinicionTramite,
            final VariablesFlujo pVariablesFlujo) {
        if (pVariablesFlujo.isDebugEnabled()) {
            debug("Invalidar paso: " + pDatosPaso.getIdPaso());
        }

        // Recuperamos datos de persistencia
        final DatosPersistenciaPaso dpp = dao.obtenerPasoPersistencia(
                pVariablesFlujo.getIdSesionTramitacion(),
                pDatosPaso.getIdPaso());
        // Actualizamos estado del paso
        actualizarEstadoPaso(pDatosPaso, dpp,
                new EstadoSubestadoPaso(TypeEstadoPaso.REVISAR, null));
        if (pVariablesFlujo.isDebugEnabled()) {
            debug("Paso invalidado: " + pDatosPaso.getIdPaso());
        }
    }

    @Override
    public final void establecerSoloLectura(final DatosPaso pDatosPaso,
            final DefinicionTramiteSTG pDefinicionTramite,
            final boolean pSoloLectura) {
        // Marca el paso para que no se puedan modificar los formularios
        pDatosPaso.internalData().setSoloLectura(pSoloLectura);
        // Lo marcamos para el front
        final DatosInternosPasoReferencia pDipa = (DatosInternosPasoReferencia) pDatosPaso
                .internalData();
        if (pSoloLectura) {
            pDipa.getDetallePaso().setSoloLectura(TypeSiNo.SI);
        } else {
            pDipa.getDetallePaso().setSoloLectura(TypeSiNo.NO);
        }
    }

    @Override
    public final RespuestaAccionPaso accionPaso(final DatosPaso pDatosPaso,
            final TypeAccionPaso pAccionPaso,
            final ParametrosAccionPaso pParametros,
            final DefinicionTramiteSTG pDefinicionTramite,
            final VariablesFlujo pVariablesFlujo) {

        // Verificamos que la accion se corresponde con el paso
        if (!verificarAccionPaso(pDatosPaso.getTipo(), pAccionPaso)) {
            throw new AccionPasoNoPermitidaException(
                    "Se esta ejecutando una accion de tipo "
                            + pAccionPaso.getClass().getSimpleName() + "."
                            + pAccionPaso.name() + " sobre paso de tipo "
                            + pDatosPaso.getTipo().name());
        }

        // Obtiene datos de persistencia
        final DatosPersistenciaPaso dpp = dao.obtenerPasoPersistencia(
                pVariablesFlujo.getIdSesionTramitacion(),
                pDatosPaso.getIdPaso());

        // Realiza accion sobre el paso
        final RespuestaEjecutarAccionPaso rp = ejecutarAccionPaso(pDatosPaso,
                dpp, pAccionPaso, pParametros, pDefinicionTramite,
                pVariablesFlujo);

        // Comprobamos si debemos recalcular los datos internos tras ejecutar la
        // acción
        if (rp.isRecalcularDatosInternos()) {
            actualizarDatosInternos(pDatosPaso, dpp, pDefinicionTramite,
                    pVariablesFlujo,
                    TypeFaseActualizacionDatosInternos.EJECUCION_ACCION_PASO);
        }

        // Actualizamos estado paso tras la accion
        final EstadoSubestadoPaso estadoPaso = evaluarEstadoPaso(pDatosPaso);
        actualizarEstadoPaso(pDatosPaso, dpp, estadoPaso);

        // Chequeo de que los datos de persistencia son los mismos que estan en
        // BBDD.
        // Este chequeo se puede deshabilitar una vez este testeada la
        // aplicacion
        if (Boolean.parseBoolean(getConfig().obtenerPropiedadConfiguracion(
                TypePropiedadConfiguracion.CHECK_PERSISTENCIA_POSTACCION))) {
            final DatosPersistenciaPaso dppNew = dao.obtenerPasoPersistencia(
                    pVariablesFlujo.getIdSesionTramitacion(),
                    pDatosPaso.getIdPaso());
            if (!dpp.equals(dppNew)) {
                throw new AccionPasoNoPermitidaException(
                        "LOS DATOS DE PERSISTENCIA SON INCONSISTENTES CON BBDD");
            }
        }

        // Devolvemos respuesta paso
        return rp.getRespuestaAccionPaso();

    }

    @Override
    public final DetallePaso detallePaso(final DatosPaso pDatosPaso,
            final DefinicionTramiteSTG pDefinicionTramite) {
        // TODO VER SI DUPLICAMOS VARIABLE XA EVITAR Q SE MODIFIQUE DE FORMA
        // EXTERNA
        if (UtilsSTG.isDebugEnabled(pDefinicionTramite)) {
            debug("Obtener detalle paso " + pDatosPaso.getIdPaso());
        }
        return ((DatosInternosPasoReferencia) pDatosPaso.internalData())
                .getDetallePaso();
    }

    @Override
    public final List<DatosDocumento> obtenerDocumentosPaso(
            final DatosPaso pDatosPaso,
            final DefinicionTramiteSTG pDefinicionTramite) {
        if (UtilsSTG.isDebugEnabled(pDefinicionTramite)) {
            debug("Obtener documentos paso " + pDatosPaso.getIdPaso());
        }
        List<DatosDocumento> res = null;

        // El paso debe estar completado para que los documentos sean
        // accesibles.
        if (pDatosPaso.getEstado() == TypeEstadoPaso.COMPLETADO) {

            // Comprobamos si es un paso que requiere acceso a persistencia para
            // recuperar datos
            final DatosPersistenciaPaso dpp = dao.obtenerPasoPersistencia(
                    pDatosPaso.getIdSesionTramitacion(),
                    pDatosPaso.getIdPaso());

            // Buscamos los documentos completados.
            res = obtenerDocumentosCompletadosPaso(pDatosPaso, dpp,
                    pDefinicionTramite);
        }

        // Devolvemos lista de documentos
        if (res == null) {
            res = new ArrayList<>();
        }
        if (UtilsSTG.isDebugEnabled(pDefinicionTramite)) {
            debug("Obtenidos " + res.size() + " documentos del paso "
                    + pDatosPaso.getIdPaso());
        }
        return res;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // FUNCIONES A IMPLEMENTAR
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Regenera datos internos a partir de la definición del trámite y de la
     * informacion de persistencia.
     *
     * @param pDatosPaso
     *            Datos paso
     * @param pDpp
     *            Datos persistencia
     * @param pDefinicionTramite
     *            Definición trámite
     * @param pVariablesFlujo
     *            Variables flujo accesibles desde el paso
     * @param pFaseEjecucion
     *            Indica en que momento se actualizan los datos internos
     *            (inicio, carga, revisar o después de una acción).
     */
    protected abstract void actualizarDatosInternos(final DatosPaso pDatosPaso,
            final DatosPersistenciaPaso pDpp,
            final DefinicionTramiteSTG pDefinicionTramite,
            final VariablesFlujo pVariablesFlujo,
            TypeFaseActualizacionDatosInternos pFaseEjecucion);

    /**
     * Evalua estado del paso.
     *
     * @param pDatosPaso
     *            Datos paso
     * @return Estado del paso
     */
    protected abstract EstadoSubestadoPaso evaluarEstadoPaso(
            final DatosPaso pDatosPaso);

    /**
     * Obtiene los documentos completado para un paso.
     *
     * @param pDatosPaso
     *            Datos paso
     * @param pDpp
     *            Datos persistencia
     * @param pDefinicionTramite
     *            Definicion trámite
     * @return Documentos completados en un paso
     */
    protected abstract List<DatosDocumento> obtenerDocumentosCompletadosPaso(
            final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
            final DefinicionTramiteSTG pDefinicionTramite);

    /**
     * Ejecuta la acción del paso.
     *
     * @param pDatosPaso
     *            Datos paso
     * @param pDpp
     *            Datos persistencia
     * @param pAccionPaso
     *            Acción paso
     * @param pParametros
     *            Parametros acción
     * @param pDefinicionTramite
     *            Definición trámite
     * @param pVariablesFlujo
     *            Variables flujo
     * @return Respuesta de la ejecución del paso
     */
    protected abstract RespuestaEjecutarAccionPaso ejecutarAccionPaso(
            final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
            final TypeAccionPaso pAccionPaso,
            final ParametrosAccionPaso pParametros,
            final DefinicionTramiteSTG pDefinicionTramite,
            final VariablesFlujo pVariablesFlujo);

    // -----------------------------------------------------------------------------------------------------------------
    // FUNCIONES UTILIDAD
    // -----------------------------------------------------------------------------------------------------------------
    /**
     * Muestra mensaje de debug.
     *
     * @param mensajeDebug
     *            mensaje de debug
     */
    protected final void debug(final String mensajeDebug) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(mensajeDebug);
        }
    }

    /**
     * Método de acceso a scriptFlujo.
     *
     * @return scriptFlujo
     */
    protected final ScriptExec getScriptFlujo() {
        return scriptFlujo;
    }

    /**
     * Método de acceso a literales.
     *
     * @return literales
     */
    protected final Literales getLiterales() {
        return literales;
    }

    /**
     * Método de acceso a dao.
     *
     * @return dao
     */
    protected final FlujoPasoDao getDao() {
        return dao;
    }

    /**
     * Método de acceso a logger.
     *
     * @return logger
     */
    protected static final Logger getLogger() {
        return LOGGER;
    }

    /**
     * Método de acceso a configuracion modulo.
     *
     * @return config
     */
    protected final ConfiguracionComponent getConfig() {
        return config;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // FUNCIONES PRIVADAS
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Actualiza estado paso.
     *
     * @param pDatosPaso
     *            Datos paso
     * @param pDpp
     *            Datos persistencia paso
     * @param pEstadoPaso
     *            Nuevo estado
     */
    private void actualizarEstadoPaso(final DatosPaso pDatosPaso,
            final DatosPersistenciaPaso pDpp,
            final EstadoSubestadoPaso pEstadoPaso) {

        // Actualizamos detalle y datos internos
        final DatosInternosPasoReferencia pDipa = (DatosInternosPasoReferencia) pDatosPaso
                .internalData();
        pDipa.getDetallePaso().setCompletado(TypeSiNo.NO);
        pDipa.setEstado(pEstadoPaso.getEstado());
        pDipa.setSubestado(pEstadoPaso.getSubestado());

        // En funcion del nuevo estado vemos si es necesario establecer datos
        // adicionales
        if (pEstadoPaso.getEstado() == TypeEstadoPaso.COMPLETADO) {
            // Si esta completado lo indicamos
            pDipa.getDetallePaso().setCompletado(TypeSiNo.SI);
        }

        // Actualizamos persistencia
        if (pDpp.getEstado() != pDipa.getEstado()) {
            pDpp.setEstado(pDipa.getEstado());
            dao.cambiarEstadoPaso(pDatosPaso.getIdSesionTramitacion(),
                    pDipa.getIdPaso(), pEstadoPaso.getEstado());
        }

    }

    /**
     * Recalcula datos internos a partir de la definición del trámite y de los
     * datos de persistencia actualizando el estado del tramite.
     *
     * @param pDatosPaso
     *            Datos paso
     * @param pDpp
     *            Datos persistencia
     * @param pDefinicionTramite
     *            Definicion tramite
     * @param pVariablesFlujo
     *            Variables flujo
     * @param pFaseEjecucion
     *            Indica en que momento se actualizan los datos internos
     *            (inicio, carga, revisar o después de una acción).
     */
    private void recalcularDatosInternos(final DatosPaso pDatosPaso,
            final DatosPersistenciaPaso pDpp,
            final DefinicionTramiteSTG pDefinicionTramite,
            final VariablesFlujo pVariablesFlujo,
            final TypeFaseActualizacionDatosInternos pFaseEjecucion) {
        // Actualizamos datos internos
        actualizarDatosInternos(pDatosPaso, pDpp, pDefinicionTramite,
                pVariablesFlujo, pFaseEjecucion);
        // Actualizamos estado del paso
        final EstadoSubestadoPaso estadoPaso = evaluarEstadoPaso(pDatosPaso);
        actualizarEstadoPaso(pDatosPaso, pDpp, estadoPaso);
    }

    /**
     * Verifica si la accion se corresponde con el paso.
     *
     * @param pTipoPaso
     *            Tipo paso
     * @param pAccionPaso
     *            Tipo accion
     * @return true si se corresponde
     */
    private boolean verificarAccionPaso(final TypePaso pTipoPaso,
            final TypeAccionPaso pAccionPaso) {
        boolean ok = false;
        switch (pTipoPaso) {
        case ACCION:
            ok = (pAccionPaso instanceof TypeAccionPasoAccion);
            break;
        case ANEXAR:
            ok = (pAccionPaso instanceof TypeAccionPasoAnexar);
            break;
        case CAPTURAR:
            ok = (pAccionPaso instanceof TypeAccionPasoCapturar);
            break;
        case DEBESABER:
            ok = (pAccionPaso instanceof TypeAccionPasoDebeSaber);
            break;
        case GUARDAR:
            ok = (pAccionPaso instanceof TypeAccionPasoGuardar);
            break;
        case INFORMACION:
            ok = (pAccionPaso instanceof TypeAccionPasoInformacion);
            break;
        case PAGAR:
            ok = (pAccionPaso instanceof TypeAccionPasoPagar);
            break;
        case REGISTRAR:
            ok = (pAccionPaso instanceof TypeAccionPasoRegistrar);
            break;
        case RELLENAR:
            ok = (pAccionPaso instanceof TypeAccionPasoRellenar);
            break;
        default:
            throw new TipoNoControladoException(
                    "Tipo de paso no controlado: " + pTipoPaso.name());
        }
        return ok;
    }

}

package es.caib.sistramit.core.service.component.flujo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.DefinicionTramiteCP;
import es.caib.sistra2.commons.plugins.email.AnexoEmail;
import es.caib.sistra2.commons.plugins.email.IEmailPlugin;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RVersionTramiteControlAcceso;
import es.caib.sistramit.core.api.exception.ErrorFormularioSoporteException;
import es.caib.sistramit.core.api.exception.FlujoInvalidoException;
import es.caib.sistramit.core.api.exception.LimiteTramitacionException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.exception.TramiteNoExisteException;
import es.caib.sistramit.core.api.model.comun.types.TypeEntorno;
import es.caib.sistramit.core.api.model.flujo.AnexoFichero;
import es.caib.sistramit.core.api.model.flujo.DatosUsuario;
import es.caib.sistramit.core.api.model.flujo.DetallePasos;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.FlujoTramitacionInfo;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoTramite;
import es.caib.sistramit.core.api.model.flujo.types.TypeFlujoTramitacion;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.system.types.TypePluginGlobal;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.integracion.CatalogoProcedimientosComponent;
import es.caib.sistramit.core.service.component.literales.Literales;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.DatosFormularioSoporte;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaTramite;
import es.caib.sistramit.core.service.model.flujo.DatosSesionTramitacion;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.repository.dao.FlujoTramiteDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsFormularioSoporte;

@Component("flujoTramitacionComponent")
@Scope(value = "prototype")
public class FlujoTramitacionComponentImpl
        implements FlujoTramitacionComponent {

    /** Log. */
    private final Logger log = LoggerFactory.getLogger(getClass());

    /** Controlador flujo tramitación. */
    @Autowired
    private ControladorFlujoTramitacion controladorFlujo;

    /** Configuracion. */
    @Autowired
    private ConfiguracionComponent configuracionComponent;

    /** CatalogoProcedimientosComponent. */
    @Autowired
    private CatalogoProcedimientosComponent catalogoProcedimientosComponent;

    /** Acceso a persistencia. */
    @Autowired
    private FlujoTramiteDao dao;

    /** Acceso a literales. */
    @Autowired
    private Literales literalesComponent;

    /**
     * Indica si el flujo es invalido. Se marcará como inválido al generarse una
     * excepción de tipo FATAL.
     */
    private boolean flujoInvalido;

    /** Datos de la sesión de tramitación. */
    private DatosSesionTramitacion datosSesion;

    /** Usuario autenticado. */
    private UsuarioAutenticadoInfo usuarioAutenticadoInfo;

    /** Id sesion tramitacion. */
    private String idSesionTramitacion;

    @Override
    public String crearSesionTramitacion(
            UsuarioAutenticadoInfo pUsuarioAutenticado) {
        // Establecemos info usuario
        usuarioAutenticadoInfo = pUsuarioAutenticado;
        // Generamos id de sesión
        idSesionTramitacion = dao.crearSesionTramitacion();
        // Retornamos id sesion
        return idSesionTramitacion;
    }

    @Override
    public void iniciarTramite(final String idTramite, final int version,
            final String idioma, final String idTramiteCatalogo,
            final String urlInicio,
            final Map<String, String> parametrosInicio) {

        // Control de si el flujo es válido
        controlFlujoInvalido();

        // Inicializa datos generales sesión recuperando info GTT, GSE y GUC.
        this.datosSesion = generarDatosSesion(idSesionTramitacion,
                TypeEstadoTramite.RELLENANDO, idTramite, version, idioma,
                idTramiteCatalogo, urlInicio, parametrosInicio,
                usuarioAutenticadoInfo, new Date(), null);

        // Realizamos operacion de iniciar
        controladorFlujo.iniciarTramite(datosSesion);

    }

    @Override
    public void cargarTramite(String pIdSesionTramitacion,
            UsuarioAutenticadoInfo pUsuarioAutenticado) {
        usuarioAutenticadoInfo = pUsuarioAutenticado;
        idSesionTramitacion = pIdSesionTramitacion;
        cargarImpl(idSesionTramitacion, false);
    }

    @Override
    public void recargarTramite(String pIdSesionTramitacion,
            UsuarioAutenticadoInfo pUsuarioAutenticado) {
        usuarioAutenticadoInfo = pUsuarioAutenticado;
        idSesionTramitacion = pIdSesionTramitacion;
        cargarImpl(idSesionTramitacion, true);
    }

    @Override
    public DetalleTramite obtenerDetalleTramite() {
        // Control de si el flujo es válido
        controlFlujoInvalido();
        // Devuelve detalle
        return controladorFlujo.detalleTramite(datosSesion);
    }

    @Override
    public DetallePasos obtenerDetallePasos() {
        // Control de si el flujo es válido
        controlFlujoInvalido();
        // Devuelve detalle
        return controladorFlujo.detallePasos(datosSesion);
    }

    @Override
    public void invalidarFlujoTramicacion() {
        flujoInvalido = true;
    }

    @Override
    public ResultadoIrAPaso irAPaso(String idPaso) {
        return controladorFlujo.irAPaso(datosSesion, idPaso);
    }

    @Override
    public ResultadoIrAPaso irAPasoActual() {
        return controladorFlujo.irAPaso(datosSesion,
                datosSesion.getDatosTramite().getIdPasoActual());
    }

    @Override
    public ResultadoAccionPaso accionPaso(String idPaso,
            TypeAccionPaso accionPaso, ParametrosAccionPaso parametros) {

        // TODO PENDIENTE

        return null;
    }

    @Override
    public void cancelarTramite() {
        // Control de si el flujo es válido
        controlFlujoInvalido();
        // Cancela tramite
        controladorFlujo.cancelarTramite(datosSesion);
        // Marcamos flujo como invalido
        invalidarFlujoTramicacion();
    }

    @Override
    public FlujoTramitacionInfo obtenerFlujoTramitacionInfo() {
        final FlujoTramitacionInfo f = new FlujoTramitacionInfo();
        f.setIdSesionTramitacion(getIdSesionTramitacion());
        f.setDebug(isDebugEnabled());
        return f;
    }

    @Override
    public void envioFormularioSoporte(String nif, String nombre,
            String telefono, String email, String problemaTipo,
            String problemaDesc, AnexoFichero anexo) {

        // Obtenemos entidad
        final RConfiguracionEntidad entidad = configuracionComponent
                .obtenerConfiguracionEntidad(datosSesion.getDefinicionTramite()
                        .getDefinicionVersion().getIdEntidad());

        // Datos form soporte
        final DatosFormularioSoporte datosFormSoporte = new DatosFormularioSoporte();
        datosFormSoporte.setNif(nif);
        datosFormSoporte.setNombre(nombre);
        datosFormSoporte.setEmail(email);
        datosFormSoporte.setTelefono(telefono);
        datosFormSoporte.setProblemaTipo(problemaTipo);
        datosFormSoporte.setProblemaDesc(problemaDesc);

        // Generamos destinatarios, asunto y mensaje
        final List<String> destinatarios = UtilsFormularioSoporte
                .obtenerDestinatariosFormularioSoporte(datosFormSoporte,
                        entidad, datosSesion);
        final String asunto = UtilsFormularioSoporte
                .obtenerAsuntoFormularioSoporte(literalesComponent,
                        datosSesion);
        final String mensaje = UtilsFormularioSoporte
                .construyeMensajeSoporteIncidencias(literalesComponent,
                        datosFormSoporte, entidad, datosSesion);

        // Anexo
        final List<AnexoEmail> anexos = new ArrayList<>();
        if (anexo != null) {
            final AnexoEmail a = new AnexoEmail();
            a.setFileName(anexo.getFileName());
            a.setContent(anexo.getFileContent());
            a.setContentType(anexo.getFileContentType());
            anexos.add(a);
        }

        // Enviamos mail
        final IEmailPlugin plgEmail = (IEmailPlugin) configuracionComponent
                .obtenerPluginGlobal(TypePluginGlobal.EMAIL);
        if (!plgEmail.envioEmail(destinatarios, asunto, mensaje, anexos)) {
            throw new ErrorFormularioSoporteException("Error enviando mail");
        }

    }

    // -------------------------------------------------------
    // FUNCIONES PRIVADAS
    // -------------------------------------------------------

    /**
     * En caso de que el flujo no sea válido genera una excepción.
     */
    private void controlFlujoInvalido() {
        if (flujoInvalido) {
            throw new FlujoInvalidoException();
        }
    }

    /**
     * Genera datos de sesion de tramitación.
     *
     * @param idSesionTramitacion
     *            id sesion tramitacion
     * @param estado
     *            estado tramite
     * @param pIdTramite
     *            id tramite
     * @param pVersion
     *            version
     * @param pIdioma
     *            idioma
     * @param pIdTramiteCP
     *            id tramite CP
     * @param pUrlInicio
     *            url inicio
     * @param pParametrosInicio
     *            parametros inicio
     * @param pUsuarioAutenticadoInfo
     *            usuario autenticado
     * @param pFechaCaducidad
     *            fecha caducidad
     * @return Datos sesion tramitacion
     */
    private DatosSesionTramitacion generarDatosSesion(
            String idSesionTramitacion, final TypeEstadoTramite estado,
            final String pIdTramite, final int pVersion, final String pIdioma,
            final String pIdTramiteCP, final String pUrlInicio,
            final Map<String, String> pParametrosInicio,
            final UsuarioAutenticadoInfo pUsuarioAutenticadoInfo,
            final Date pFechaInicio, final Date pFechaCaducidad) {

        // Obtenemos la definición del trámite del GTT (si no está el idioma
        // disponible, se coge el idioma por defecto o bien el primero
        // disponible)
        final DefinicionTramiteSTG defTramGTT = configuracionComponent
                .recuperarDefinicionTramite(pIdTramite, pVersion, pIdioma);

        // El idioma puede variar según los idiomas disponibles
        final String idiomaSesion = defTramGTT.getDefinicionVersion()
                .getIdioma();

        // Control limitacion de tramitacion
        controlLimitacionTramitacion(defTramGTT);

        // Obtenemos las propiedades del trámite en el Catalogo de
        // Procedimientos
        final DefinicionTramiteCP tramiteCP = catalogoProcedimientosComponent
                .obtenerDefinicionTramite(
                        defTramGTT.getDefinicionVersion().getIdEntidad(),
                        pIdTramiteCP, pIdioma);

        // Props tipo flujo y entorno
        final TypeFlujoTramitacion tipoFlujo = TypeFlujoTramitacion
                .fromString(defTramGTT.getDefinicionVersion().getTipoFlujo());
        if (tipoFlujo == null) {
            throw new TipoNoControladoException("Tipo de flujo no controlado: "
                    + defTramGTT.getDefinicionVersion().getTipoFlujo());
        }

        final String propEntorno = configuracionComponent
                .obtenerPropiedadConfiguracion(
                        TypePropiedadConfiguracion.ENTORNO);
        final TypeEntorno entorno = TypeEntorno.fromString(propEntorno);
        if (entorno == null) {
            throw new TipoNoControladoException(
                    "Tipo de entorno no controlado: " + propEntorno);
        }

        // Creamos sesion
        final DatosSesionTramitacion st = new DatosSesionTramitacion(
                defTramGTT);
        st.getDatosTramite().setEstadoTramite(estado);
        st.getDatosTramite().setIdSesionTramitacion(idSesionTramitacion);
        st.getDatosTramite().setIdTramite(pIdTramite);
        st.getDatosTramite().setVersionTramite(pVersion);
        st.getDatosTramite().setTipoFlujo(tipoFlujo);
        st.getDatosTramite().setTituloTramite(tramiteCP.getDescripcion());
        st.getDatosTramite().setUrlInicio(pUrlInicio);
        st.getDatosTramite().setParametrosInicio(pParametrosInicio);
        st.getDatosTramite().setDefinicionTramiteCP(tramiteCP);
        st.getDatosTramite().setIdioma(idiomaSesion);
        st.getDatosTramite().setEntorno(entorno);
        st.getDatosTramite().setNivelAutenticacion(
                pUsuarioAutenticadoInfo.getAutenticacion());
        st.getDatosTramite().setMetodoAutenticacionInicio(
                pUsuarioAutenticadoInfo.getMetodoAutenticacion());
        st.getDatosTramite().setIniciador(
                UtilsFlujo.getDatosUsuario(pUsuarioAutenticadoInfo));
        st.getDatosTramite().setVigente(tramiteCP.isVigente());
        st.getDatosTramite().setPlazoInicio(tramiteCP.getPlazoInicio());
        st.getDatosTramite().setPlazoFin(tramiteCP.getPlazoFin());
        st.getDatosTramite().setFechaCaducidad(pFechaCaducidad);
        st.getDatosTramite().setFechaInicio(pFechaInicio);

        return st;
    }

    /**
     * Control limitación
     *
     * @param defTramGTT
     *            Definición trámite
     */
    private void controlLimitacionTramitacion(DefinicionTramiteSTG defTramGTT) {

        final RVersionTramiteControlAcceso controlAcceso = defTramGTT
                .getDefinicionVersion().getControlAcceso();
        if (controlAcceso.isLimitarTramitacion()) {

            final String idTramite = defTramGTT.getDefinicionVersion()
                    .getIdentificador();
            final int version = defTramGTT.getDefinicionVersion().getVersion();
            final long limitNumero = controlAcceso
                    .getLimiteTramitacionInicios();
            final int limitIntervalo = controlAcceso
                    .getLimiteTramitacionIntervalo();

            final Long total = dao.contadorLimiteTramitacion(idTramite, version,
                    limitIntervalo, new Date());
            if (total.longValue() >= limitNumero) {
                throw new LimiteTramitacionException(idTramite, version,
                        limitNumero, limitIntervalo);
            }
        }

    }

    /**
     * Devuelve id sesion tramitacion.
     *
     * @return id sesion tramitacion
     */
    private String getIdSesionTramitacion() {
        return idSesionTramitacion;
    }

    /**
     * Devuelve si esta habilitado debug.
     *
     * @return si esta habilitado debug
     */
    private boolean isDebugEnabled() {
        boolean res = false;
        if (datosSesion != null && datosSesion.getDefinicionTramite() != null) {
            res = datosSesion.getDefinicionTramite().getDefinicionVersion()
                    .getControlAcceso().isDebug();
        }
        return res;
    }

    /**
     * Realiza carga tramite.
     *
     * @param pIdSesionTramitacion
     *            Id sesion tramitacion
     * @param recarga
     *            Indica si la carga se produce tras un error
     */
    private void cargarImpl(final String pIdSesionTramitacion,
            final boolean recarga) {
        // Control de si el flujo es válido
        controlFlujoInvalido();

        // Recuperamos datos de persistencia
        final DatosPersistenciaTramite tram = dao
                .obtenerTramitePersistencia(pIdSesionTramitacion);
        if (tram == null) {
            throw new TramiteNoExisteException(pIdSesionTramitacion);
        }

        // Obtenemos usuario iniciador y nivel auth de persistencia
        final TypeAutenticacion nivelAuthIniciador = tram.getAutenticacion();
        DatosUsuario usuarioIniciador = null;
        if (nivelAuthIniciador != TypeAutenticacion.ANONIMO) {
            usuarioIniciador = new DatosUsuario();
            usuarioIniciador.setNif(tram.getNifIniciador());
            usuarioIniciador.setNombre(tram.getNombreIniciador());
            usuarioIniciador.setApellido1(tram.getApellido1Iniciador());
            usuarioIniciador.setApellido2(tram.getApellido2Iniciador());
        }

        // Verificamos si usuario puede cargar el tramite
        UtilsFlujo.controlCargaTramite(tram, usuarioAutenticadoInfo, recarga);

        // Inicializa datos de sesión
        datosSesion = generarDatosSesion(pIdSesionTramitacion, tram.getEstado(),
                tram.getIdTramite(), tram.getVersionTramite(), tram.getIdioma(),
                tram.getIdTramiteCP(), tram.getUrlInicio(),
                tram.getParametrosInicio(), usuarioAutenticadoInfo,
                tram.getFechaInicio(), tram.getFechaCaducidad());

        // Lanzamos operación de cargar
        controladorFlujo.cargarTramite(datosSesion);

    }

}

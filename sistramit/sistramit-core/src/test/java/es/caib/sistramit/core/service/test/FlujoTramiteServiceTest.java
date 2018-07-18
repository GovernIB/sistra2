package es.caib.sistramit.core.service.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistramit.core.api.model.flujo.DetallePasos;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.api.model.security.InfoLoginTramite;
import es.caib.sistramit.core.api.model.security.SesionInfo;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.service.FlujoTramitacionService;
import es.caib.sistramit.core.api.service.SecurityService;
import es.caib.sistramit.core.service.component.integracion.SistragesComponent;
import es.caib.sistramit.core.service.component.integracion.SistragesMock;

/**
 * Testing capa de negocio de tramitación.
 *
 * @author Indra
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FlujoTramiteServiceTest extends BaseDbUnit {

    /** Flujo tramitación. */
    @Autowired
    private FlujoTramitacionService flujoTramitacionService;

    /** Servicio seguridad. */
    @Autowired
    private SecurityService securityService;

    /** Acceso STG (simulado). */
    @Autowired
    private SistragesComponent sistragesComponent;

    /** Id entidad test. */
    private final static String ID_ENTIDAD = "E1";
    /** Id tramite test. */
    private final static String ID_TRAMITE = "T1";
    /** Version tramite test. */
    private final static int VERSION_TRAMITE = 1;
    /** Id tramite CP test. */
    private final static String ID_TRAMITE_CP = "Y";
    /** Idioma test. */
    private final static String IDIOMA = "es";
    /** QAA Test. */
    private final static String QAA = "3";
    /** Url inicio. */
    private static final String URL_INICIO = "localhost:8080/sistramitfront/asistente/iniciarTramite.html?tramite="
            + ID_TRAMITE + "&version=" + VERSION_TRAMITE + "&idioma=" + IDIOMA
            + "&idTramiteCatalogo=" + ID_TRAMITE_CP;

    /** Publicación JNDI Datasource y creación de BBDD en memoria. */
    @BeforeClass
    public static void setUpClass() throws Exception {
        JndiBean.doSetup();
    }

    /** Verificación cacheo definiciones. */
    @Test
    public void test1_cache() {

        String timestamp1, timestamp2, timestamp3;

        // Configuracion global
        RConfiguracionGlobal confGlobal = null;
        confGlobal = sistragesComponent.obtenerConfiguracionGlobal();
        timestamp1 = confGlobal.getTimestamp();
        confGlobal = sistragesComponent.obtenerConfiguracionGlobal();
        timestamp2 = confGlobal.getTimestamp();
        Assert.isTrue(timestamp1.equals(timestamp2), "No coincide timestamp");
        sistragesComponent.evictConfiguracionGlobal();
        confGlobal = sistragesComponent.obtenerConfiguracionGlobal();
        timestamp3 = confGlobal.getTimestamp();
        Assert.isTrue(!timestamp2.equals(timestamp3),
                "No ha variado timestamp");

        // Configuracion entidad
        RConfiguracionEntidad confEntidad = null;
        confEntidad = sistragesComponent
                .obtenerConfiguracionEntidad(ID_ENTIDAD);
        timestamp1 = confEntidad.getTimestamp();
        confEntidad = sistragesComponent
                .obtenerConfiguracionEntidad(ID_ENTIDAD);
        timestamp2 = confEntidad.getTimestamp();
        Assert.isTrue(timestamp1.equals(timestamp2), "No coincide timestamp");
        sistragesComponent.evictConfiguracionEntidad(ID_ENTIDAD);
        confEntidad = sistragesComponent
                .obtenerConfiguracionEntidad(ID_ENTIDAD);
        timestamp3 = confEntidad.getTimestamp();
        Assert.isTrue(!timestamp2.equals(timestamp3),
                "No ha variado timestamp");

        // Avisos entidad
        RAvisosEntidad avisosEntidad = null;
        avisosEntidad = sistragesComponent.obtenerAvisosEntidad(ID_ENTIDAD);
        timestamp1 = avisosEntidad.getTimestamp();
        avisosEntidad = sistragesComponent.obtenerAvisosEntidad(ID_ENTIDAD);
        timestamp2 = avisosEntidad.getTimestamp();
        Assert.isTrue(timestamp1.equals(timestamp2), "No coincide timestamp");
        sistragesComponent.evictAvisosEntidad(ID_ENTIDAD);
        avisosEntidad = sistragesComponent.obtenerAvisosEntidad(ID_ENTIDAD);
        timestamp3 = avisosEntidad.getTimestamp();
        Assert.isTrue(!timestamp2.equals(timestamp3),
                "No ha variado timestamp");

        // Definicion tramite
        RVersionTramite defTramite = sistragesComponent
                .recuperarDefinicionTramite(ID_TRAMITE, VERSION_TRAMITE,
                        IDIOMA);
        timestamp1 = defTramite.getTimestamp();
        defTramite = sistragesComponent.recuperarDefinicionTramite(ID_TRAMITE,
                VERSION_TRAMITE, IDIOMA);
        timestamp2 = defTramite.getTimestamp();
        Assert.isTrue(timestamp1.equals(timestamp2), "No coincide timestamp");
        sistragesComponent.evictDefinicionTramite(ID_TRAMITE, VERSION_TRAMITE,
                IDIOMA);
        defTramite = sistragesComponent.recuperarDefinicionTramite(ID_TRAMITE,
                VERSION_TRAMITE, IDIOMA);
        timestamp3 = defTramite.getTimestamp();
        Assert.isTrue(!timestamp2.equals(timestamp3),
                "No ha variado timestamp");

    }

    /** Verificación login autenticado. */
    @Test
    public void test2_loginAutenticado() {
        final UsuarioAutenticadoInfo usuarioAutenticadoInfo = loginSimulado(
                TypeAutenticacion.AUTENTICADO);
        Assert.isTrue(
                usuarioAutenticadoInfo != null && usuarioAutenticadoInfo
                        .getAutenticacion() == TypeAutenticacion.AUTENTICADO,
                "No se ha autenticado correctamente");
        this.logger.info("Autenticado: " + usuarioAutenticadoInfo.getNif()
                + " - " + usuarioAutenticadoInfo.getNombre() + " "
                + usuarioAutenticadoInfo.getApellido1() + " "
                + usuarioAutenticadoInfo.getApellido2());
    }

    /** Verificación login anonimo. */
    @Test
    public void test3_loginAnonimo() {
        final UsuarioAutenticadoInfo usuarioAutenticadoInfo = loginSimulado(
                TypeAutenticacion.ANONIMO);
        Assert.isTrue(
                usuarioAutenticadoInfo != null && usuarioAutenticadoInfo
                        .getAutenticacion() == TypeAutenticacion.ANONIMO,
                "No se ha autenticado correctamente");
        this.logger.info("Autenticado anonimo");
    }

    /** Verificación carga/recarga tramite autenticado. */
    @Test
    public void test4_cargaTramiteAutenticado() {

        DetalleTramite dt = null;

        // Simula login autenticado
        final UsuarioAutenticadoInfo usuarioAutenticadoInfo = loginSimulado(
                TypeAutenticacion.AUTENTICADO);

        // Generar sesion tramitacion
        final String idSesionTramitacion = flujoTramitacionService
                .crearSesionTramitacion(usuarioAutenticadoInfo);

        // Iniciar trámite
        final Map<String, String> parametrosInicio = new HashMap<>();

        flujoTramitacionService.iniciarTramite(idSesionTramitacion, ID_TRAMITE,
                VERSION_TRAMITE, IDIOMA, ID_TRAMITE_CP, URL_INICIO,
                parametrosInicio);
        Assert.isTrue(idSesionTramitacion != null,
                "No se devuelve id sesion tramitacion");

        // Recargar trámite (dentro sesion)
        flujoTramitacionService.recargarTramite(idSesionTramitacion,
                usuarioAutenticadoInfo);
        dt = flujoTramitacionService.obtenerDetalleTramite(idSesionTramitacion);
        Assert.isTrue(idSesionTramitacion.equals(dt.getTramite().getIdSesion()),
                "No se devuelve id sesion tramitacion");

        // Cargar trámite (fuera sesion)

        flujoTramitacionService.cargarTramite(idSesionTramitacion,
                usuarioAutenticadoInfo);
        dt = flujoTramitacionService.obtenerDetalleTramite(idSesionTramitacion);
        Assert.isTrue(idSesionTramitacion.equals(dt.getTramite().getIdSesion()),
                "No se devuelve id sesion tramitacion");

    }

    /** Verificación carga/recarga tramite anonimo. */
    @Test
    public void test5_cargaTramiteAnonimo() {

        DetalleTramite dt = null;

        // Simula login anonimo
        final UsuarioAutenticadoInfo usuarioAutenticadoInfo = loginSimulado(
                TypeAutenticacion.ANONIMO);

        // Generar sesion tramitacion
        final String idSesionTramitacion = flujoTramitacionService
                .crearSesionTramitacion(usuarioAutenticadoInfo);

        // Iniciar trámite
        final Map<String, String> parametrosInicio = new HashMap<>();
        flujoTramitacionService.iniciarTramite(idSesionTramitacion, ID_TRAMITE,
                VERSION_TRAMITE, IDIOMA, ID_TRAMITE_CP, URL_INICIO,
                parametrosInicio);
        Assert.isTrue(idSesionTramitacion != null,
                "No se devuelve id sesion tramitacion");

        // Recargar trámite (dentro sesion)
        flujoTramitacionService.recargarTramite(idSesionTramitacion,
                usuarioAutenticadoInfo);
        dt = flujoTramitacionService.obtenerDetalleTramite(idSesionTramitacion);
        Assert.isTrue(idSesionTramitacion.equals(dt.getTramite().getIdSesion()),
                "No se devuelve id sesion tramitacion");

        // Cargar trámite mediante clave tramitacion (fuera sesion)
        final InfoLoginTramite infoLogin = securityService
                .obtenerInfoLoginTramiteAnonimoPersistente(idSesionTramitacion);
        Assert.isTrue(infoLogin.isLoginAnonimoAuto(),
                "No se ha establecido login anonimo automatico");

        flujoTramitacionService.cargarTramite(idSesionTramitacion,
                usuarioAutenticadoInfo);
        dt = flujoTramitacionService.obtenerDetalleTramite(idSesionTramitacion);
        Assert.isTrue(idSesionTramitacion.equals(dt.getTramite().getIdSesion()),
                "No se devuelve id sesion tramitacion");

    }

    /** Verificación flujo tramitacion: recorre pasos de tramitación. */
    @Test
    public void test6_flujoTramitacion() {

        final UsuarioAutenticadoInfo usuarioAutenticadoInfo = loginSimulado(
                TypeAutenticacion.AUTENTICADO);

        // Generar sesion tramitacion
        final String idSesionTramitacion = flujoTramitacionService
                .crearSesionTramitacion(usuarioAutenticadoInfo);

        // Iniciar trámite
        final Map<String, String> parametrosInicio = new HashMap<>();
        flujoTramitacionService.iniciarTramite(idSesionTramitacion, ID_TRAMITE,
                VERSION_TRAMITE, IDIOMA, ID_TRAMITE_CP, URL_INICIO,
                parametrosInicio);
        Assert.isTrue(idSesionTramitacion != null,
                "No se devuelve id sesion tramitacion");
        this.logger.info("Tramite iniciado: " + idSesionTramitacion);

        // Detalle tramite
        final DetalleTramite dt = flujoTramitacionService
                .obtenerDetalleTramite(idSesionTramitacion);
        Assert.isTrue(idSesionTramitacion.equals(dt.getTramite().getIdSesion()),
                "No coincide id sesion tramitacion");
        this.logger.info("Detalle Tramite: " + dt.print());

        // Detalle paso actual: Debe saber
        final DetallePasos dp = flujoTramitacionService
                .obtenerDetallePasos(idSesionTramitacion);
        Assert.isTrue(dp.getActual().getTipo() == TypePaso.DEBESABER,
                "Paso inicial no es debe saber");
        this.logger.info("Detalle paso: " + dp.print());

    }

    // -------------------------------------------------------------
    // FUNCIONES PRIVADAS
    // -------------------------------------------------------------

    private UsuarioAutenticadoInfo loginSimulado(TypeAutenticacion auth) {

        // Recuperacion info login
        securityService.obtenerInfoLoginTramite(ID_TRAMITE, VERSION_TRAMITE,
                ID_TRAMITE_CP, IDIOMA, URL_INICIO);

        // Inicio sesion hacia redireccion
        final List<TypeAutenticacion> authList = new ArrayList<>();
        authList.add(TypeAutenticacion.AUTENTICADO);
        authList.add(TypeAutenticacion.ANONIMO);

        final String urlRedirect = securityService.iniciarSesionAutenticacion(
                SistragesMock.crearEntidad().getIdentificador(), IDIOMA,
                authList, QAA, "http://localhost:8080", true);

        Assert.isTrue(urlRedirect != null,
                "No se ha devuelto url redireccion login");

        // Autenticar usuario (en modo mock, autentica segun primera letra del
        // ticket)
        final SesionInfo sesionInfo = new SesionInfo();
        sesionInfo.setIdioma(IDIOMA);
        sesionInfo.setUserAgent("");
        final UsuarioAutenticadoInfo usuarioAutenticadoInfo = securityService
                .validarTicketAutenticacion(sesionInfo,
                        auth.toString() + "123");
        return usuarioAutenticadoInfo;
    }

}

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
import es.caib.sistramit.core.api.model.flujo.DetallePasos;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.api.model.security.SesionInfo;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.service.FlujoTramitacionService;
import es.caib.sistramit.core.api.service.SecurityService;
import es.caib.sistramit.core.service.component.integracion.SistragesComponent;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;

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
        confEntidad = sistragesComponent.obtenerConfiguracionEntidad("E1");
        timestamp1 = confEntidad.getTimestamp();
        confEntidad = sistragesComponent.obtenerConfiguracionEntidad("E1");
        timestamp2 = confEntidad.getTimestamp();
        Assert.isTrue(timestamp1.equals(timestamp2), "No coincide timestamp");
        sistragesComponent.evictConfiguracionEntidad("E1");
        confEntidad = sistragesComponent.obtenerConfiguracionEntidad("E1");
        timestamp3 = confEntidad.getTimestamp();
        Assert.isTrue(!timestamp2.equals(timestamp3),
                "No ha variado timestamp");

        // Avisos entidad
        RAvisosEntidad avisosEntidad = null;
        avisosEntidad = sistragesComponent.obtenerAvisosEntidad("E1");
        timestamp1 = avisosEntidad.getTimestamp();
        avisosEntidad = sistragesComponent.obtenerAvisosEntidad("E1");
        timestamp2 = avisosEntidad.getTimestamp();
        Assert.isTrue(timestamp1.equals(timestamp2), "No coincide timestamp");
        sistragesComponent.evictAvisosEntidad("E1");
        avisosEntidad = sistragesComponent.obtenerAvisosEntidad("E1");
        timestamp3 = avisosEntidad.getTimestamp();
        Assert.isTrue(!timestamp2.equals(timestamp3),
                "No ha variado timestamp");

        // Definicion tramite
        DefinicionTramiteSTG defTramite = null;
        defTramite = sistragesComponent.recuperarDefinicionTramite("T1", 1,
                "es");
        timestamp1 = defTramite.getDefinicionVersion().getTimestamp();
        defTramite = sistragesComponent.recuperarDefinicionTramite("T1", 1,
                "es");
        timestamp2 = defTramite.getDefinicionVersion().getTimestamp();
        Assert.isTrue(timestamp1.equals(timestamp2), "No coincide timestamp");
        sistragesComponent.evictDefinicionTramite("T1", 1, "es");
        defTramite = sistragesComponent.recuperarDefinicionTramite("T1", 1,
                "es");
        timestamp3 = defTramite.getDefinicionVersion().getTimestamp();
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

    /** Verificación flujo tramitacion. */
    @Test
    public void test4_cargaTramite() {

        DetalleTramite dt = null;

        final UsuarioAutenticadoInfo usuarioAutenticadoInfo = loginSimulado(
                TypeAutenticacion.AUTENTICADO);

        // Iniciar trámite
        final String idTramite = "x";
        final int version = 1;
        final Map<String, String> parametrosInicio = new HashMap<>();
        final String idTramiteCatalogo = "y";
        final String idioma = "es";
        final String urlInicio = "localhost:8080/sistramitfront/asistente/iniciarTramite.html?tramite=x&version=1&idioma=es&idTramiteCatalogo=y";

        final String idSesionTramitacion = flujoTramitacionService
                .iniciarTramite(idTramite, version, idioma, idTramiteCatalogo,
                        urlInicio, parametrosInicio, usuarioAutenticadoInfo);
        Assert.isTrue(idSesionTramitacion != null,
                "No se devuelve id sesion tramitacion");

        // Recargar trámite (dentro sesion)
        flujoTramitacionService.recargarTramite(idSesionTramitacion,
                usuarioAutenticadoInfo);
        dt = flujoTramitacionService.obtenerDetalleTramite(idSesionTramitacion);
        Assert.isTrue(idSesionTramitacion.equals(dt.getIdSesionTramitacion()),
                "No se devuelve id sesion tramitacion");

        // Cargar trámite (fuera sesion)
        flujoTramitacionService.cargarTramite(idSesionTramitacion,
                usuarioAutenticadoInfo);
        dt = flujoTramitacionService.obtenerDetalleTramite(idSesionTramitacion);
        Assert.isTrue(idSesionTramitacion.equals(dt.getIdSesionTramitacion()),
                "No se devuelve id sesion tramitacion");

    }

    /** Verificación flujo tramitacion: recorre pasos de tramitación. */
    @Test
    public void test5_flujoTramitacion() {

        final UsuarioAutenticadoInfo usuarioAutenticadoInfo = loginSimulado(
                TypeAutenticacion.AUTENTICADO);

        // Iniciar trámite
        final String idTramite = "x";
        final int version = 1;
        final Map<String, String> parametrosInicio = new HashMap<>();
        final String idTramiteCatalogo = "y";
        final String idioma = "es";
        final String urlInicio = "localhost:8080/sistramitfront/asistente/iniciarTramite.html?tramite=x&version=1&idioma=es&idTramiteCatalogo=y";

        final String idSesionTramitacion = flujoTramitacionService
                .iniciarTramite(idTramite, version, idioma, idTramiteCatalogo,
                        urlInicio, parametrosInicio, usuarioAutenticadoInfo);
        Assert.isTrue(idSesionTramitacion != null,
                "No se devuelve id sesion tramitacion");
        this.logger.info("Tramite iniciado: " + idSesionTramitacion);

        // Detalle tramite
        final DetalleTramite dt = flujoTramitacionService
                .obtenerDetalleTramite(idSesionTramitacion);
        Assert.isTrue(idSesionTramitacion.equals(dt.getIdSesionTramitacion()),
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

        // Inicio sesion hacia redireccion
        final List<TypeAutenticacion> authList = new ArrayList<>();
        authList.add(TypeAutenticacion.AUTENTICADO);
        authList.add(TypeAutenticacion.ANONIMO);

        final String urlRedirect = securityService.iniciarSesionAutenticacion(
                "es", authList, "3", "http://localhost:8080");

        Assert.isTrue(urlRedirect != null,
                "No se ha devuelto url redireccion login");

        // Autenticar usuario (en modo mock, autentica segun primera letra del
        // ticket)
        final SesionInfo sesionInfo = new SesionInfo();
        sesionInfo.setIdioma("es");
        sesionInfo.setUserAgent("");
        final UsuarioAutenticadoInfo usuarioAutenticadoInfo = securityService
                .validarTicketAutenticacion(sesionInfo,
                        auth.toString() + "123");
        return usuarioAutenticadoInfo;
    }

}

package es.caib.sistramit.core.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.exception.NoExisteFlujoTramitacionException;
import es.caib.sistramit.core.api.model.flujo.DetallePasos;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.FlujoTramitacionInfo;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.service.FlujoTramitacionService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.flujo.FlujoTramitacionCacheComponent;
import es.caib.sistramit.core.service.component.flujo.FlujoTramitacionComponent;

@Service
@Transactional
public class FlujoTramitacionServiceImpl implements FlujoTramitacionService {

    /** Caché con con los flujos de tramitacion. */
    @Autowired
    private FlujoTramitacionCacheComponent flujoTramitacionCache;

    @Override
    @NegocioInterceptor
    public String crearSesionTramitacion(
            UsuarioAutenticadoInfo usuarioAutenticado) {
        // Generamos flujo de tramitacion y almacenamos en map
        final FlujoTramitacionComponent ft = (FlujoTramitacionComponent) ApplicationContextProvider
                .getApplicationContext().getBean("flujoTramitacionComponent");
        final String idSesionTramitacion = ft
                .crearSesionTramitacion(usuarioAutenticado);
        flujoTramitacionCache.put(idSesionTramitacion, ft);
        return idSesionTramitacion;
    }

    @Override
    @NegocioInterceptor
    public void iniciarTramite(String idSesionTramitacion,
            final String idTramite, final int version, final String idioma,
            final String idTramiteCatalogo, final String urlInicio,
            final Map<String, String> parametrosInicio) {
        final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(
                idSesionTramitacion);
        ft.iniciarTramite(idTramite, version, idioma, idTramiteCatalogo,
                urlInicio, parametrosInicio);
    }

    @Override
    @NegocioInterceptor
    public DetalleTramite obtenerDetalleTramite(
            final String idSesionTramitacion) {
        final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(
                idSesionTramitacion);
        return ft.obtenerDetalleTramite();
    }

    @Override
    @NegocioInterceptor
    public DetallePasos obtenerDetallePasos(final String idSesionTramitacion) {
        final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(
                idSesionTramitacion);
        return ft.obtenerDetallePasos();
    }

    @Override
    @NegocioInterceptor
    public void cargarTramite(String idSesionTramitacion,
            UsuarioAutenticadoInfo usuarioAutenticado) {
        // Generamos flujo de tramitacion, almacenamos en map y cargamos trámite
        final FlujoTramitacionComponent ft = (FlujoTramitacionComponent) ApplicationContextProvider
                .getApplicationContext().getBean("flujoTramitacionComponent");
        flujoTramitacionCache.put(idSesionTramitacion, ft);
        ft.cargarTramite(idSesionTramitacion, usuarioAutenticado);
    }

    @Override
    @NegocioInterceptor
    public void recargarTramite(String idSesionTramitacion,
            UsuarioAutenticadoInfo userInfo) {
        // Generamos flujo de tramitacion, almacenamos en map y cargamos trámite
        final FlujoTramitacionComponent ft = (FlujoTramitacionComponent) ApplicationContextProvider
                .getApplicationContext().getBean("flujoTramitacionComponent");
        flujoTramitacionCache.put(idSesionTramitacion, ft);
        ft.recargarTramite(idSesionTramitacion, userInfo);
    }

    @Override
    @NegocioInterceptor
    public ResultadoIrAPaso irAPaso(String idSesionTramitacion, String idPaso) {
        final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(
                idSesionTramitacion);
        return ft.irAPaso(idPaso);
    }

    @Override
    @NegocioInterceptor
    public ResultadoIrAPaso irAPasoActual(String idSesionTramitacion) {
        final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(
                idSesionTramitacion);
        return ft.irAPasoActual();
    }

    @Override
    @NegocioInterceptor
    public ResultadoAccionPaso accionPaso(String idSesionTramitacion,
            String idPaso, TypeAccionPaso accionPaso,
            ParametrosAccionPaso parametros) {
        final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(
                idSesionTramitacion);
        return ft.accionPaso(idPaso, accionPaso, parametros);
    }

    @Override
    @NegocioInterceptor
    public void cancelarTramite(String idSesionTramitacion) {
        final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(
                idSesionTramitacion);
        ft.cancelarTramite();
    }

    @Override
    @NegocioInterceptor
    public void purgar() {
        flujoTramitacionCache.purgar();
    }

    // -------------------------------------------------------------------------------------------
    // - Métodos especiales invocados desde el interceptor. No pasan por
    // interceptor
    // de auditoria.
    // -------------------------------------------------------------------------------------------
    @Override
    public FlujoTramitacionInfo obtenerFlujoTramitacionInfo(
            final String idSesionTramitacion) {
        // ATENCION: NO DEBE PASAR POR INTERCEPTOR. SE USA DESDE EL PROPIO
        // INTERCEPTOR.
        FlujoTramitacionInfo dt = null;
        final FlujoTramitacionComponent ft = flujoTramitacionCache
                .get(idSesionTramitacion);
        if (ft != null) {
            try {
                dt = ft.obtenerFlujoTramitacionInfo();
            } catch (final Exception ex) {
                // No hacemos nada
                dt = null;
            }
        }
        return dt;
    }

    @Override
    public void invalidarFlujoTramitacion(final String idSesionTramitacion) {
        final FlujoTramitacionComponent ft = flujoTramitacionCache
                .get(idSesionTramitacion);
        if (ft != null) {
            try {
                ft.invalidarFlujoTramicacion();
            } catch (final Exception ex) {
                // No hacemos nada
            }
        }
    }

    // --------------------------------------------------------------
    // ---- FUNCIONES INTERNAS
    // --------------------------------------------------------------

    /**
     * Obtiene flujo de tramitación.
     *
     * @param idSesionTramitacion
     *            id sesión tramitación
     * @return flujo tramitación
     */
    private FlujoTramitacionComponent obtenerFlujoTramitacion(
            final String idSesionTramitacion) {
        final FlujoTramitacionComponent ft = flujoTramitacionCache
                .get(idSesionTramitacion);
        if (ft == null) {
            throw new NoExisteFlujoTramitacionException(idSesionTramitacion);
        }
        return ft;
    }

}

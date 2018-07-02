package es.caib.sistramit.core.service.component.flujo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.GeneradorId;
import es.caib.sistramit.core.api.model.flujo.DetallePasos;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.integracion.SistragesComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;

@Component("flujoTramitacionComponent")
@Scope(value = "prototype")
public class FlujoTramitacionComponentImpl
        implements FlujoTramitacionComponent {

    /** Log. */
    private final Logger log = LoggerFactory.getLogger(getClass());

    /** Configuracion. */
    @Autowired
    private ConfiguracionComponent configuracionComponent;

    /** SistragesComponent. */
    @Autowired
    private SistragesComponent sistragesComponent;

    /**
     * Indica si el flujo es invalido. Se marcará como inválido al generarse una
     * excepción de tipo FATAL.
     */
    private boolean flujoInvalido;

    // TODO Ver donde almacenar info
    /** Id sesion tramitacion. */
    private String idSesionTramitacion;

    /** Usuario autenticado. */
    private UsuarioAutenticadoInfo usuarioAutenticado;

    /** Paso actual. */
    private String idPasoActual;

    @Override
    public String iniciarTramite(final String idTramite, final int version,
            final String idioma, final String idTramiteCatalogo,
            final String urlInicio, final Map<String, String> parametrosInicio,
            UsuarioAutenticadoInfo pUsuarioAutenticado) {

        // TODO PENDIENTE

        idSesionTramitacion = GeneradorId.generarId();
        usuarioAutenticado = pUsuarioAutenticado;
        idPasoActual = TypePaso.DEBESABER.toString();
        return idSesionTramitacion;
    }

    @Override
    public void cargarTramite(String idSesionTramitacion,
            UsuarioAutenticadoInfo pUsuarioAutenticado) {
        // TODO PENDIENTE
        idSesionTramitacion = idSesionTramitacion;
        usuarioAutenticado = pUsuarioAutenticado;

    }

    @Override
    public void recargarTramite(String idSesionTramitacion,
            UsuarioAutenticadoInfo pUsuarioAutenticado) {
        // TODO PENDIENTE
        idSesionTramitacion = idSesionTramitacion;
        usuarioAutenticado = pUsuarioAutenticado;
    }

    @Override
    public DetalleTramite obtenerDetalleTramite() {
        // TODO PENDIENTE. Mock para simular JSON
        final DetalleTramite dt = MockFlujo.generarDetalleTramite(
                idSesionTramitacion, usuarioAutenticado, idPasoActual);
        return dt;
    }

    @Override
    public DetallePasos obtenerDetallePasos() {
        // TODO PENDIENTE. Mock para simular JSON
        final DetallePasos dt = MockFlujo.generarDetallePasos(idPasoActual);
        return dt;
    }

    @Override
    public void invalidarFlujoTramicacion() {
        flujoInvalido = true;
    }

    @Override
    public ResultadoIrAPaso irAPaso(String idPaso) {
        // TODO PENDIENTE. Mock para simular JSON
        final ResultadoIrAPaso r = new ResultadoIrAPaso();
        r.setFinalizadoTrasIrAPaso(false);
        r.setIdPasoActual(idPaso);
        r.setIdSesionTramitacion(idSesionTramitacion);
        idPasoActual = idPaso;
        return r;
    }

    @Override
    public ResultadoIrAPaso irAPasoActual() {
        // TODO PENDIENTE. Mock para simular JSON
        final ResultadoIrAPaso r = new ResultadoIrAPaso();
        r.setFinalizadoTrasIrAPaso(false);
        r.setIdPasoActual(idPasoActual);
        r.setIdSesionTramitacion(idSesionTramitacion);
        return r;
    }

    @Override
    public ResultadoAccionPaso accionPaso(String idPaso,
            TypeAccionPaso accionPaso, ParametrosAccionPaso parametros) {

        // TODO PENDIENTE

        return null;
    }

    @Override
    public void cancelarTramite() {
        // TODO PENDIENTE
    }

    // TODO BORRAR
    @Override
    public void test(String param) {

        final String[] params = param.split("@");

        final String accion = params[0];

        if ("RCG".equals(accion)) {
            configuracionComponent.obtenerPropiedadConfiguracion(
                    TypePropiedadConfiguracion.URL_SISTRAMIT);
        }

        if ("ECG".equals(accion)) {
            sistragesComponent.evictConfiguracionGlobal();
        }

        if ("RCE".equals(accion)) {
            sistragesComponent.obtenerConfiguracionEntidad(params[1]);
        }

        if ("ECE".equals(accion)) {
            sistragesComponent.evictConfiguracionEntidad(params[1]);
        }

        if ("RDT".equals(accion)) {
            sistragesComponent.recuperarDefinicionTramite(params[1],
                    Integer.parseInt(params[2]), params[3]);
        }

        if ("EDT".equals(accion)) {
            sistragesComponent.evictDefinicionTramite(params[1],
                    Integer.parseInt(params[2]), params[3]);
        }
    }

}

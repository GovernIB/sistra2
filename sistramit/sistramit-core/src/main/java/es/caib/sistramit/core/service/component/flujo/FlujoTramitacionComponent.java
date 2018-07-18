package es.caib.sistramit.core.service.component.flujo;

import java.util.Map;

import es.caib.sistramit.core.api.model.flujo.DetallePasos;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.FlujoTramitacionInfo;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;

public interface FlujoTramitacionComponent {

    String crearSesionTramitacion(UsuarioAutenticadoInfo pUsuarioAutenticado);

    void iniciarTramite(String idTramite, int version, String idioma,
            String idTramiteCatalogo, String urlInicio,
            Map<String, String> parametrosInicio);

    void cargarTramite(String idSesionTramitacion,
            UsuarioAutenticadoInfo usuarioAutenticado);

    void recargarTramite(String idSesionTramitacion,
            UsuarioAutenticadoInfo userInfo);

    DetalleTramite obtenerDetalleTramite();

    DetallePasos obtenerDetallePasos();

    void invalidarFlujoTramicacion();

    ResultadoIrAPaso irAPaso(String idPaso);

    ResultadoIrAPaso irAPasoActual();

    ResultadoAccionPaso accionPaso(String idPaso, TypeAccionPaso accionPaso,
            ParametrosAccionPaso parametros);

    void cancelarTramite();

    FlujoTramitacionInfo obtenerFlujoTramitacionInfo();

}
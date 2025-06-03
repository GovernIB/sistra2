package es.caib.sistramit.core.service.component.integracion;

import es.caib.sistra2.commons.plugins.digitalizacion.api.*;
import es.caib.sistramit.core.api.exception.SesionDigitalizacionException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DatosUsuario;
import es.caib.sistramit.core.api.model.flujo.FuncionarioHabilitado;
import es.caib.sistramit.core.api.model.flujo.RedireccionDigitalizacion;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DigitalizacionRespuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("digitalizacionComponent")
public class DigitalizacionComponentImpl implements DigitalizacionComponent{

    /** Configuracion. */
    @Autowired
    private ConfiguracionComponent configuracionComponent;

    @Override
    public RedireccionDigitalizacion redireccionDigitalizacion(final String nombreDocumento, final String idEntidad, final String urlCallBack, final VariablesFlujo variablesFlujo) {
        String idSesionDigitalizacion = null;
        String urlRedireccion = null;
        boolean iframe = false;
        // Obtenemos plugin de digitalización
        final IDigitalizacionPlugin plugin = getPluginDigitalizacion(idEntidad);
        // Generamos sesión de digitalización
        try {
            // Establecemos parametros sesion
            FuncionarioHabilitado fh = variablesFlujo.getUsuarioAutenticado().getFuncionarioHabilitado();
            InfoPersonaDigitalizacion infoFHDigitalizacion = new InfoPersonaDigitalizacion(fh.getUserName(), fh.getNombre(), fh.getNombre(), fh.getApellido1(), fh.getApellido2(), fh.getNif());
            DatosUsuario usu = variablesFlujo.getUsuario();
            InfoPersonaDigitalizacion ciudadano = new InfoPersonaDigitalizacion(usu.getNif(), usu.getNombre(), usu.getNombre(), usu.getApellido1(), usu.getApellido2(), usu.getNif());
            InfoSesionDigitalizacion infoSesion = new InfoSesionDigitalizacion();
            infoSesion.setNombreDocumento(nombreDocumento);
            infoSesion.setEntidad(idEntidad);
            infoSesion.setIdioma(variablesFlujo.getIdioma());
            infoSesion.setCodigoDIR3(fh.getDir3());
            infoSesion.setFuncionarioHabilitado(infoFHDigitalizacion);
            infoSesion.setCiudadano(ciudadano);
            // Generamos id sesion
            idSesionDigitalizacion = plugin.generarSesionDigitalizacion(infoSesion);
        } catch (DigitalizacionPluginException e) {
            throw new SesionDigitalizacionException("Error al generar sesión digitalización: " + e.getMessage(), e);
        }
        // Iniciamos sesión de digitalización
        try {
            urlRedireccion = plugin.iniciarSesionDigitalizacion(idSesionDigitalizacion, urlCallBack);
            iframe = plugin.isIframe();
        } catch (DigitalizacionPluginException e) {
            throw new SesionDigitalizacionException("Error al iniciar sesión digitalización: " + e.getMessage(), e);
        }
        // Devolvemos resultado
        RedireccionDigitalizacion redireccion = new RedireccionDigitalizacion();
        redireccion.setIdSesionDigitalizacion(idSesionDigitalizacion);
        redireccion.setUrl(urlRedireccion);
        redireccion.setIframe(TypeSiNo.fromBoolean(iframe));
        return redireccion;
    }

    @Override
    public DigitalizacionRespuesta recuperarResultadoDigitalizacionExterna(String idEntidad, String idSesionDigitalizacion) {
            // Obtenemos plugin de digitalización
            final IDigitalizacionPlugin plugin = getPluginDigitalizacion(idEntidad);
            // Obtenemos resultado de la digitalización
            ResultadoDigitalizacion res = null;
            try {
                res = plugin.obtenerResultadoDigitalizacion(idSesionDigitalizacion);
            } catch (DigitalizacionPluginException e) {
                throw new SesionDigitalizacionException("Error al obtener resultado sesión digitalización: " + e.getMessage(), e);
            }
            // Devolvemos resultado
            DigitalizacionRespuesta respuesta = new DigitalizacionRespuesta();
            respuesta.setDigitalizado(res.isDigitalizado());
            respuesta.setFicheroNombre(res.getNombreFichero());
            respuesta.setFicheroContenido(res.getDocumento());
            respuesta.setDetalleError(res.getMensajeError());
            respuesta.setTrazaError(res.getTrazaError());
            return respuesta;
    }

    /**
     * Obtiene plugin digitalización.
     *
     * @return plugin
     */
    private IDigitalizacionPlugin getPluginDigitalizacion(final String entidad) {
        final IDigitalizacionPlugin plugin = (IDigitalizacionPlugin) configuracionComponent
                .obtenerPluginEntidad(TypePluginEntidad.DIGITALIZACION, entidad);
        return plugin;
    }

}
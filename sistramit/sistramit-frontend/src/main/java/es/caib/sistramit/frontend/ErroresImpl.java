package es.caib.sistramit.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.ServiceException;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.frontend.literales.LiteralesFront;
import es.caib.sistramit.frontend.model.MensajeUsuario;
import es.caib.sistramit.frontend.model.RespuestaJSON;
import es.caib.sistramit.frontend.model.types.TypeRespuestaJSON;

/**
 * Errores.
 *
 * @author Indra
 *
 */
@Component("errores")
public final class ErroresImpl implements Errores {

    /** Atributo literales. */
    @Autowired
    private LiteralesFront literales;

    /** Configuracion. */
    @Autowired
    private SystemService systemService;

    @Override
    public RespuestaJSON generarRespuestaJsonExcepcion(final Exception pEx,
            final String idioma) {

        // Evaluamos excepcion
        TypeRespuestaJSON tipoError;
        String tituloMensaje;
        String textoMensaje;
        String urlMensaje;

        // Establecemos nivel excepcion
        TypeNivelExcepcion nivel;
        if (pEx instanceof ServiceException) {
            final ServiceException ex = (ServiceException) pEx;
            nivel = ex.getNivel();
        } else {
            nivel = TypeNivelExcepcion.FATAL;
        }

        // En funcion del nivel establecemos tipo respuesta y url siguiente
        switch (nivel) {
        case WARNING:
            tipoError = TypeRespuestaJSON.WARNING;
            break;
        case ERROR:
            tipoError = TypeRespuestaJSON.ERROR;
            break;
        default: // FATAL
            tipoError = TypeRespuestaJSON.FATAL;
        }

        // Establecemos titulo / texto excepcion
        tituloMensaje = devolverTituloExcepcion(pEx, idioma, tipoError);
        textoMensaje = devolverMensajeError(pEx, idioma);
        urlMensaje = devolverUrlExcepcion(pEx, idioma, tipoError);

        // Creamos respuesta
        final RespuestaJSON res = new RespuestaJSON();
        res.setEstado(tipoError);
        res.setUrl(urlMensaje);
        res.setMensaje(new MensajeUsuario(tituloMensaje, textoMensaje));

        return res;
    }

    /**
     * Establece url tras excepción.
     *
     * @param pEx
     *            Excepción
     * @param pIdioma
     *            Idioma
     * @param pTipoError
     *            Tipo error
     * @return url tras excepción (nulo si no se establece ninguna)
     */
    private String devolverUrlExcepcion(final Exception pEx,
            final String pIdioma, final TypeRespuestaJSON pTipoError) {
        String url = null;

        // Url estandar para excepciones recargar tramite
        if (pTipoError == TypeRespuestaJSON.FATAL) {
            // TODO Revisar excepciones y urls.
            url = getUrlAsistente() + "/asistente/recargarTramite.html";
        }

        if (pEx instanceof ServiceException) {
            // Comprobamos si esta particularizado por excepcion
            final String keyLiteralExcepcion = getNombreExcepcion(pEx);
            url = literales.getLiteralFront(LiteralesFront.EXCEPCIONES,
                    "url." + keyLiteralExcepcion, pIdioma, url);
        }
        return url;
    }

    /**
     * Devuelte titulo excepcion.
     *
     * @param pEx
     *            Excepcion
     * @param idioma
     *            Idioma
     * @param pTipoError
     *            Tipo error
     * @return Titulo excepcion
     */
    private String devolverTituloExcepcion(final Exception pEx,
            final String idioma, final TypeRespuestaJSON pTipoError) {
        // Obtenemos titulo general
        String tituloMensaje = literales.getLiteralFront(
                LiteralesFront.EXCEPCIONES, "title." + pTipoError.toString(),
                idioma);

        // Comprobamos si esta particularizado por excepcion
        if (pEx instanceof ServiceException) {
            final String keyLiteralExcepcion = getNombreExcepcion(pEx);
            tituloMensaje = literales.getLiteralFront(
                    LiteralesFront.EXCEPCIONES, "title." + keyLiteralExcepcion,
                    idioma, tituloMensaje);
        }

        return tituloMensaje;
    }

    /**
     * Método para devolver mensaje error de la clase TramitacionController.
     *
     * @param ex
     *            Parámetro ex
     * @param idioma
     *            Parámetro idioma
     * @return el string
     */
    private String devolverMensajeError(final Exception ex,
            final String idioma) {

        String mensaje = null;

        final String nombreExcepcion = getNombreExcepcion(ex);

        // Obtenemos texto general
        mensaje = literales.getLiteralFront(LiteralesFront.EXCEPCIONES,
                "text.generica", idioma);

        // Buscamos si existe texto particularizado para la excepcion
        mensaje = literales.getLiteralFront(LiteralesFront.EXCEPCIONES,
                "text." + nombreExcepcion, idioma, mensaje);

        // Reemplazamos parametros especiales
        mensaje = StringUtils.replace(mensaje, "[#excepcion.nombre#]",
                nombreExcepcion);
        mensaje = StringUtils.replace(mensaje, "[#excepcion.mensaje#]",
                ex.getMessage());

        return mensaje;

    }

    /**
     * Obtiene nombre excepcion.
     *
     * @param ex
     *            Excepcion
     * @return nombre excepcion
     */
    private String getNombreExcepcion(final Exception ex) {
        String keyLiteralExcepcion;
        final String name = ex.getClass().getName();
        final int idx = name.lastIndexOf(".");
        keyLiteralExcepcion = name.substring(idx + ConstantesNumero.N1,
                name.length());
        return keyLiteralExcepcion;
    }

    /**
     * Obtiene url asistente.
     *
     * @return url asistente
     */
    private String getUrlAsistente() {
        // TODO Ver si cachear
        return systemService.obtenerPropiedadConfiguracion(
                TypePropiedadConfiguracion.SISTRAMIT_URL);
    }

}

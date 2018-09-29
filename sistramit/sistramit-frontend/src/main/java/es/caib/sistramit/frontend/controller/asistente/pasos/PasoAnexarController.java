package es.caib.sistramit.frontend.controller.asistente.pasos;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoAnexar;
import es.caib.sistramit.frontend.controller.TramitacionController;
import es.caib.sistramit.frontend.literales.LiteralesFront;
import es.caib.sistramit.frontend.model.MensajeUsuario;
import es.caib.sistramit.frontend.model.RespuestaJSON;
import es.caib.sistramit.frontend.model.types.TypeRespuestaJSON;

/**
 * Interacción con paso Anexar.
 *
 * @author Indra
 *
 */
@Controller
@RequestMapping(value = "/asistente/ad")
public final class PasoAnexarController extends TramitacionController {

    /** Constante parametro id paso. */
    private static final String PARAM_ID_PASO = "idPaso";
    /** Constante parametro id anexo. */
    private static final String PARAM_ID_ANEXO = "idAnexo";
    /** Constante parametro instancia. */
    private static final String PARAM_INSTANCIA = "instancia";

    /**
     * Anexa un documento. El upload del documento se realizará desde un iframe
     * de forma que como respuesta se generará un HTML que se cargará en el
     * iframe e invocará a la función fileUploaded del parent pasando el estado
     * actual del trámite.
     *
     * @param idPaso
     *            Identificador paso.
     * @param idAnexo
     *            Identificador anexo.
     * @param titulo
     *            Título del anexo (para genéricos).
     * @param request
     *            Request para extraer el fichero (busca fichero en el parámetro
     *            de la request "fichero").
     * @return Genera un HTML que invoca a la función fileUploaded del parent
     */
    @RequestMapping(value = "/anexarDocumento.json", method = RequestMethod.POST)
    public ModelAndView anexarDocumento(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_ANEXO) final String idAnexo,
            @RequestParam(value = "titulo", required = false) final String titulo,
            final HttpServletRequest request) {

        debug("Anexando documento: " + idAnexo);

        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        boolean errorLectura = false;

        // Recuperamos datos fichero de la request
        String fileName = null;
        byte[] fileContent = null;
        if (request instanceof MultipartHttpServletRequest) {
            final MultipartHttpServletRequest mp = (MultipartHttpServletRequest) request;
            final MultipartFile fic = mp.getFile("fichero");
            try {
                fileName = fic.getOriginalFilename();
                fileContent = fic.getBytes();
            } catch (final IOException e) {
                errorLectura = true;
            }
        } else {
            errorLectura = true;
        }

        RespuestaJSON resAnexar;
        resAnexar = new RespuestaJSON();

        if (!errorLectura) {

            // Invocamos al flujo para anexar
            final ParametrosAccionPaso params = new ParametrosAccionPaso();
            params.addParametroEntrada(PARAM_ID_ANEXO, idAnexo);
            params.addParametroEntrada("titulo", titulo);
            params.addParametroEntrada("nombreFichero", fileName);
            params.addParametroEntrada("datosFichero", fileContent);

            final ResultadoAccionPaso rp = getFlujoTramitacionService()
                    .accionPaso(idSesionTramitacion, idPaso,
                            TypeAccionPasoAnexar.ANEXAR_DOCUMENTO, params);

            // En caso de que se haya convertido a PDF mostramos mensaje
            // para que el ciudadano revise el documento
            if ("true".equalsIgnoreCase(
                    (String) rp.getParametroRetorno("conversionPDF"))) {
                resAnexar.setMensaje(new MensajeUsuario(
                        getLiteralesFront().getLiteralFront(
                                LiteralesFront.MENSAJES, "atencion",
                                getIdioma()),
                        getLiteralesFront().getLiteralFront(
                                LiteralesFront.MENSAJES, "anexoConvertidoPDF",
                                getIdioma())));
            }

        } else {
            resAnexar.setEstado(TypeRespuestaJSON.ERROR);
            resAnexar.setMensaje(new MensajeUsuario("Error",
                    "Error al leer fichero de la request"));
        }

        return generarJsonView(resAnexar);
    }

    /**
     * Borra un documento.
     *
     * @param idPaso
     *            Id paso
     * @param idAnexo
     *            Id anexo
     * @param instancia
     *            Instancia (en caso de ser multiinstancia, es decir, genérico)
     * @return Devuelve JSON con estado actual del paso
     */
    @RequestMapping(value = "/borrarDocumento.json", method = RequestMethod.POST)
    public ModelAndView borrarDocumento(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_ANEXO) final String idAnexo,
            @RequestParam(value = PARAM_INSTANCIA, required = false) final String instancia) {

        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        // Invocamos al flujo para borrar documento
        final ParametrosAccionPaso parametros = new ParametrosAccionPaso();
        parametros.addParametroEntrada(PARAM_ID_ANEXO, idAnexo);
        // TODO QUITAR CUANDO SE ARREGLE JS
        if (!"???".equals(instancia)) {
            parametros.addParametroEntrada(PARAM_INSTANCIA, instancia);
        }
        getFlujoTramitacionService().accionPaso(idSesionTramitacion, idPaso,
                TypeAccionPasoAnexar.BORRAR_ANEXO, parametros);

        // Retornamos respuesta
        final RespuestaJSON resBorrarAnexo = new RespuestaJSON();
        return generarJsonView(resBorrarAnexo);

    }

    /**
     * Devuelve contenido del anexo.
     *
     * @param idPaso
     *            Identificador paso.
     * @param idAnexo
     *            Identificador del anexo.
     * @param instancia
     *            Instancia (en caso de ser multiinstancia, es decir, genérico)
     * @return Genera descarga documento
     */
    @RequestMapping(value = "/descargarDocumento.html")
    public ModelAndView descargarDocumento(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_ANEXO) final String idAnexo,
            @RequestParam(value = PARAM_INSTANCIA, required = false) final String instancia) {

        debug("Obteniendo datos anexo: " + idAnexo);

        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        ParametrosAccionPaso pParametros;
        pParametros = new ParametrosAccionPaso();
        pParametros.addParametroEntrada(PARAM_ID_ANEXO, idAnexo);

        // TODO QUITAR CUANDO SE ARREGLE JS
        if (!"???".equals(instancia)) {
            pParametros.addParametroEntrada(PARAM_INSTANCIA, instancia);
        }

        final ResultadoAccionPaso rap = getFlujoTramitacionService().accionPaso(
                idSesionTramitacion, idPaso,
                TypeAccionPasoAnexar.DESCARGAR_ANEXO, pParametros);
        final byte[] datos = (byte[]) rap.getParametroRetorno("datosFichero");
        final String nombreFichero = (String) rap
                .getParametroRetorno("nombreFichero");

        return generarDownloadView(nombreFichero, datos);

    }

    /**
     * Realiza download de la plantilla de un anexo.
     *
     * @param idPaso
     *            Identificador paso.
     * @param idAnexo
     *            Identificador anexo.
     * @return Documento para descargar.
     */
    @RequestMapping("/descargarPlantilla.html")
    public ModelAndView descargarPlantilla(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_ANEXO) final String idAnexo) {
        debug("Descargar plantilla para anexo " + idAnexo);

        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        // Invocamos al flujo para descargar la plantilla
        final ParametrosAccionPaso params = new ParametrosAccionPaso();
        params.addParametroEntrada(PARAM_ID_ANEXO, idAnexo);
        final ResultadoAccionPaso rp = getFlujoTramitacionService().accionPaso(
                idSesionTramitacion, idPaso,
                TypeAccionPasoAnexar.DESCARGAR_PLANTILLA, params);
        final String nomFichero = (String) rp
                .getParametroRetorno("nombreFichero");
        final byte[] plantilla = (byte[]) rp
                .getParametroRetorno("datosFichero");

        // Descargamos plantilla a traves DownloadFileView
        return generarDownloadView(nomFichero, plantilla);
    }

    // TODO BORRAR
    @RequestMapping("/testFirma.html")
    public ModelAndView testFirma(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_ANEXO) final String idAnexo,
            @RequestParam(value = PARAM_INSTANCIA, required = false) final String instancia) {

        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        final ParametrosAccionPaso params = new ParametrosAccionPaso();
        params.addParametroEntrada(PARAM_ID_ANEXO, idAnexo);
        params.addParametroEntrada(PARAM_INSTANCIA, instancia);
        final ResultadoAccionPaso rp = getFlujoTramitacionService().accionPaso(
                idSesionTramitacion, idPaso, TypeAccionPasoAnexar.TEST_FIRMAR,
                params);
        final String url = (String) rp.getParametroRetorno("url");

        return new ModelAndView("redirect:" + url);
    }

    @CrossOrigin
    @RequestMapping("/testRetornoFirma.html")
    public ModelAndView retornoFirma(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_ANEXO) final String idAnexo,
            @RequestParam(value = PARAM_INSTANCIA, required = false) final String instancia) {

        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        final ParametrosAccionPaso params = new ParametrosAccionPaso();
        params.addParametroEntrada(PARAM_ID_ANEXO, idAnexo);
        params.addParametroEntrada(PARAM_INSTANCIA, instancia);
        final ResultadoAccionPaso rp = getFlujoTramitacionService().accionPaso(
                idSesionTramitacion, idPaso,
                TypeAccionPasoAnexar.TEST_RETORNO_FIRMA, params);

        return new ModelAndView("redirect:/asistente/recargarTramite.html");
    }

    @RequestMapping(value = "/testDescargarFirma.html")
    public ModelAndView descargarFirma(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_ANEXO) final String idAnexo,
            @RequestParam(value = PARAM_INSTANCIA, required = false) final String instancia) {

        debug("Obteniendo datos anexo: " + idAnexo);

        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        ParametrosAccionPaso pParametros;
        pParametros = new ParametrosAccionPaso();
        pParametros.addParametroEntrada(PARAM_ID_ANEXO, idAnexo);
        pParametros.addParametroEntrada(PARAM_INSTANCIA, instancia);

        final ResultadoAccionPaso rap = getFlujoTramitacionService().accionPaso(
                idSesionTramitacion, idPaso,
                TypeAccionPasoAnexar.TEST_DESCARGAR_FIRMA, pParametros);
        final byte[] datos = (byte[]) rap.getParametroRetorno("datosFichero");
        final String nombreFichero = (String) rap
                .getParametroRetorno("nombreFichero");

        return generarDownloadView(nombreFichero, datos);

    }

}

package es.caib.sistramit.frontend.controller.asistente;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistra2.commons.utils.CifradoUtil;
import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.ErrorFormularioSoporteException;
import es.caib.sistramit.core.api.exception.WarningFrontException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.AnexoFichero;
import es.caib.sistramit.core.api.model.flujo.DetallePasos;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.frontend.SesionHttp;
import es.caib.sistramit.frontend.controller.TramitacionController;
import es.caib.sistramit.frontend.literales.LiteralesFront;
import es.caib.sistramit.frontend.model.AsistenteConfig;
import es.caib.sistramit.frontend.model.AsistenteInfo;
import es.caib.sistramit.frontend.model.MensajeAsistente;
import es.caib.sistramit.frontend.model.MensajeUsuario;
import es.caib.sistramit.frontend.model.RespuestaJSON;
import es.caib.sistramit.frontend.model.types.TypeRespuestaJSON;
import es.caib.sistramit.frontend.security.SecurityUtils;
import es.caib.sistramit.frontend.security.UsuarioAutenticado;

@Controller
@RequestMapping(value = "/asistente")
public class AsistenteTramitacionController extends TramitacionController {

    @Autowired
    SesionHttp sesionHttp;

    /** Url redireccion asistente. */
    private static final String URL_REDIRIGIR_ASISTENTE = "asistente/redirigirAsistente";

    /**
     * Inicia trámite.
     *
     * @param tramite
     *            Trámite
     * @param version
     *            Versión
     * @param idioma
     *            Idioma
     * @param idTramiteCatalogo
     *            Id trámite en catálogo de servicios
     * @param parametros
     *            Parameros de inicio del trámite. Lista separada por -_- (p.e.:
     *            param1-_-valor1-_-param2-_-valor2)
     * @param request
     *            request
     * @return Redireccion a mostrar asistente
     */
    @RequestMapping(value = "/iniciarTramite.html")
    public ModelAndView iniciarTramite(
            @RequestParam("tramite") final String tramite,
            @RequestParam("version") final int version,
            @RequestParam("idioma") final String idioma,
            @RequestParam("idTramiteCatalogo") final String idTramiteCatalogo,
            @RequestParam(value = "parametros", required = false) final String parametros,
            final HttpServletRequest request) {

        // Url inicio
        final String urlInicio = getUrlAsistente()
                + "/asistente/iniciarTramite.html?" + request.getQueryString();

        // Parametros inicio (convertimos parametros a map)
        final Map<String, String> parametrosInicio = new HashMap<>();
        if (!StringUtils.isBlank(parametros)) {
            String key;
            String value;
            final String[] params = parametros.split("-_-");
            for (int i = 0; i < params.length; i = i + ConstantesNumero.N2) {
                key = params[i];
                if ((i + ConstantesNumero.N1) < params.length) {
                    value = params[i + ConstantesNumero.N1];
                } else {
                    value = "";
                }
                parametrosInicio.put(key, value);
            }
        }

        // Obtiene usuario autenticado
        final UsuarioAutenticado usuarioAutenticado = SecurityUtils
                .obtenerUsuarioAutenticado();

        // Crea sesion tramitacion
        final String idSesionTramitacion = getFlujoTramitacionService()
                .crearSesionTramitacion(usuarioAutenticado.getUsuario());

        // Inicia tramite
        getFlujoTramitacionService().iniciarTramite(idSesionTramitacion,
                tramite, version, idioma, idTramiteCatalogo, urlInicio,
                parametrosInicio);

        // Almacena en la sesion
        final DetalleTramite dt = getFlujoTramitacionService()
                .obtenerDetalleTramite(idSesionTramitacion);
        registraSesionTramitacion(idSesionTramitacion,
                dt.getDebug() == TypeSiNo.SI);

        final ModelAndView mav = new ModelAndView(URL_REDIRIGIR_ASISTENTE);
        return mav;
    }

    /**
     * Carga trámite existente y redirige a la página del asistente.
     *
     * @param idSesionCifrado
     *            Identificador sesión de tramitación (cifrado)
     * @return Vista que redirige al asistente
     */
    @RequestMapping("/cargarTramite.html")
    public ModelAndView cargarTramite(
            @RequestParam("idSesionTramitacion") final String idSesionCifrado) {

        // Decodificamos id sesion
        final String idSesion = CifradoUtil.decrypt(idSesionCifrado);

        // Cargamos tramite
        cargarTramiteImpl(idSesion, false);

        debug("Cargada instancia tramite");

        // Redirigimos a carga asistente
        return new ModelAndView(URL_REDIRIGIR_ASISTENTE);

    }

    /**
     * Recargar trámite.
     *
     * @return Vista que redirige al asistente
     */
    @RequestMapping("/recargarTramite.html")
    public ModelAndView recargarTramite() {

        final String idSesionTramitacion = getIdSesionTramitacion();
        debug("Recargando instancia tramite: " + idSesionTramitacion);

        cargarTramiteImpl(idSesionTramitacion, true);

        debug("Recargada instancia tramite: " + idSesionTramitacion);

        // Redirigimos a carga asistente
        return new ModelAndView(URL_REDIRIGIR_ASISTENTE);

    }

    /**
     * Muestra asistente de tramitación.
     *
     * @return Vista asistente
     */
    @RequestMapping(value = "/asistente.html")
    public ModelAndView asistente() {
        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        // Obtiene detalle tramite
        final DetalleTramite detalleTramite = getFlujoTramitacionService()
                .obtenerDetalleTramite(idSesionTramitacion);

        // Devolvemos informacion asistente
        final AsistenteInfo ai = new AsistenteInfo();
        ai.setIdSesionTramitacion(idSesionTramitacion);
        ai.setIdioma(detalleTramite.getTramite().getIdioma());
        // TODO Pendiente ver info a pasar
        return new ModelAndView("asistente/asistente", "datos", ai);
    }

    /**
     * Cancela un trámite.
     *
     * @return Devuelve JSON indicando que se ha cancelado.
     *
     */
    @RequestMapping(value = "/cancelarTramite.json", method = RequestMethod.POST)
    public ModelAndView cancelarTramite() {

        debug("Cancelar tramite");
        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        // Obtiene detalle trámite
        final DetalleTramite dt = getFlujoTramitacionService()
                .obtenerDetalleTramite(idSesionTramitacion);
        final String urlCarpeta = dt.getEntidad().getUrlCarpeta();

        // Cancela trámite
        getFlujoTramitacionService().cancelarTramite(idSesionTramitacion);

        final RespuestaJSON res = new RespuestaJSON();
        final MensajeUsuario mu = new MensajeUsuario(
                getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES,
                        "atencion", getIdioma()),
                getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES,
                        "tramite.cancelado", getIdioma()));
        res.setMensaje(mu);
        res.setUrl(urlCarpeta);

        return generarJsonView(res);
    }

    /**
     * Realiza logout.
     *
     * @return Realiza logout.
     *
     */
    @RequestMapping(value = "/logout.html")
    public ModelAndView logout() {
        // TODO PENDIENTE
        return null;
    }

    /**
     * Descarga clave tramitación.
     *
     * @return Descarga clave tramitación.
     *
     */
    @RequestMapping(value = "/descargarClave.html")
    public ModelAndView descargarClave() {
        // TODO PENDIENTE
        final String nombre = "clave.txt";
        final byte[] datosFichero = ("PENDIENTE GENERAR FICHERO PDF. CLAVE: "
                + getIdSesionTramitacionActiva()).getBytes();
        return generarDownloadView(nombre, datosFichero);
    }

    /**
     * Devuelve un JSON con la información inicial trámite.
     *
     * @return JSON con la información inicial trámite.
     *
     */
    @RequestMapping(value = "/informacionTramite.json", method = RequestMethod.POST)
    public ModelAndView informacionTramite() {
        debug("Obteniendo info tramite");

        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        // Obtiene detalle tramite
        final DetalleTramite detalleTramite = getFlujoTramitacionService()
                .obtenerDetalleTramite(idSesionTramitacion);

        final RespuestaJSON res = new RespuestaJSON();
        res.setDatos(detalleTramite);

        // Mensaje asistente en sesion (recuperamos si existe mensaje y lo
        // reseteamos)
        final MensajeAsistente mensajeAsistente = this.getMensajeAsistente();
        if (mensajeAsistente != null) {
            this.setMensajeAsistente(null);
            final MensajeUsuario mu = mensajeAsistente.getMensaje();
            res.setEstado(mensajeAsistente.getTipo());
            res.setMensaje(mu);
        }

        return generarJsonView(res);
    }

    /**
     * Devuelve JSON con el paso indicado.
     *
     * @param idPaso
     *            Identificador del formulario.
     * @return Devuelve JSON con estado actual del trámite.
     */
    @RequestMapping(value = "/irAPaso.json", method = RequestMethod.POST)
    public ModelAndView irAPaso(@RequestParam("paso") final String idPaso) {
        debug("Ir a paso " + idPaso);

        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        // Intenta ir a paso indicado
        final ResultadoIrAPaso resPaso = getFlujoTramitacionService()
                .irAPaso(idSesionTramitacion, idPaso);

        // Obtiene info paso actual
        debug("Paso actual: " + resPaso.getIdPasoActual());

        // Recupera info tramite
        final DetallePasos dt = getFlujoTramitacionService()
                .obtenerDetallePasos(idSesionTramitacion);
        final RespuestaJSON res = new RespuestaJSON();
        res.setDatos(dt);

        final MensajeUsuario mu = new MensajeUsuario("", "");
        res.setMensaje(mu);

        return generarJsonView(res);
    }

    /**
     * Devuelve JSON con el paso indicado.
     *
     * @return Devuelve JSON con estado actual del trámite.
     */
    @RequestMapping(value = "/irAPasoActual.json", method = RequestMethod.POST)
    public ModelAndView irAPasoActual() {
        debug("Ir a paso actual");

        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        // Intenta ir a paso indicado
        final ResultadoIrAPaso resPaso = getFlujoTramitacionService()
                .irAPasoActual(idSesionTramitacion);

        // Obtiene info paso actual
        debug("Paso actual: " + resPaso.getIdPasoActual());

        // Recupera info tramite
        final DetallePasos dt = getFlujoTramitacionService()
                .obtenerDetallePasos(idSesionTramitacion);
        final RespuestaJSON res = new RespuestaJSON();
        res.setDatos(dt);

        final MensajeUsuario mu = new MensajeUsuario("", "");
        res.setMensaje(mu);

        return generarJsonView(res);
    }

    /**
     * Devuelve todos los literales de la aplicación.
     *
     * @return literales de la aplicación
     */
    @RequestMapping("/js/literales.js")
    public ModelAndView obtenerLiteralesAplicacion() {

        final Properties props = getLiteralesFront()
                .getLiteralesSeccion(LiteralesFront.APLICACION, getIdioma());
        final Set<Map.Entry<Object, Object>> literales = props.entrySet();
        for (final Map.Entry<Object, Object> entry : literales) {
            final String key = (String) entry.getKey();
            /*
             * final String value = StringEscapeUtils .escapeEcmaScript((String)
             * entry.getValue());
             */
            final String value = (String) entry.getValue();
            props.setProperty(key, value);
        }

        return new ModelAndView("asistente/literales", "literales",
                props.entrySet());
    }

    /**
     * Devuelve configuración aplicación.
     *
     * @return configuración aplicación
     */
    @RequestMapping("/js/configuracion.js")
    public ModelAndView obtenerConfiguracionAplicacion() {
        final AsistenteConfig conf = new AsistenteConfig();
        conf.setUrl(getSystemService().obtenerPropiedadConfiguracion(
                TypePropiedadConfiguracion.SISTRAMIT_URL));
        conf.setIdioma(this.getIdioma());
        return new ModelAndView("asistente/configuracion", "configuracion",
                conf);
    }

    /**
     * Retorno gestor pago externo.
     *
     * @param ticket
     *            ticket
     * @return retorno de pago externo recargando el trámite
     */
    @RequestMapping(value = "/retornoPagoExterno.html")
    public ModelAndView retornoPagoExterno(
            @RequestParam("ticket") final String ticket) {
        // TODO PENDIENTE
        return null;
    }

    /**
     * Retorno gestor formulario externo.
     *
     * @param ticket
     *            ticket
     * @return retorno de formulario externo recargando el trámite
     */
    @RequestMapping(value = "/retornoFormularioExterno.html")
    public ModelAndView retornoFormularioExterno(
            @RequestParam("ticket") final String ticket) {

        // TODO PENDIENTE

        return null;
    }

    /**
     * Retorno componente de firma externo.
     *
     * @param ticket
     *            ticket
     * @return retorno de componente de firma externo recargando el trámite
     */
    @RequestMapping(value = "/retornoFirmaExterno.html")
    public ModelAndView retornoFirmaExterno(
            @RequestParam("ticket") final String ticket) {
        // TODO PENDIENTE
        return null;
    }

    /**
     * Petición ayuda mediante formulario soporte.
     *
     * @param nif
     *            Nif
     * @param nombre
     *            Nombre
     * @param telefono
     *            Teléfono
     * @param email
     *            Email
     * @param problemaTipo
     *            Tipo problema
     * @param problemaDesc
     *            Descripción problema
     * @param request
     *            request
     * @return
     */
    @RequestMapping(value = "/formularioSoporte.json", method = RequestMethod.POST)
    public ModelAndView formularioSoporte(
            @RequestParam(value = "nif", required = false) final String nif,
            @RequestParam(value = "nom", required = false) final String nombre,
            @RequestParam(value = "telefono", required = true) final String telefono,
            @RequestParam(value = "email", required = true) final String email,
            @RequestParam(value = "problemaTipo", required = true) final String problemaTipo,
            @RequestParam(value = "problemaDesc", required = true) final String problemaDesc,
            final HttpServletRequest request) {

        // TODO VER SI PONEMOS LIMITE FICHERO

        debug("Formulario soporte ");

        final RespuestaJSON res = new RespuestaJSON();

        if (request instanceof MultipartHttpServletRequest) {

            // Obtiene fichero
            final MultipartHttpServletRequest mp = (MultipartHttpServletRequest) request;
            final MultipartFile fic = mp.getFile("anexo");

            // Invocamos al flujo para envio a soporte
            final String idSesionTramitacion = getIdSesionTramitacionActiva();
            try {
                AnexoFichero anexo = null;
                if (fic != null) {
                    anexo = new AnexoFichero();
                    anexo.setFileName(fic.getOriginalFilename());
                    anexo.setFileContent(fic.getBytes());
                    anexo.setFileContentType(fic.getContentType());
                }

                getFlujoTramitacionService().envioFormularioSoporte(
                        idSesionTramitacion, nif, nombre, telefono, email,
                        problemaTipo, problemaDesc, anexo);

            } catch (final ErrorFormularioSoporteException | IOException efs) {
                res.setEstado(TypeRespuestaJSON.ERROR);
            }

        } else {
            res.setEstado(TypeRespuestaJSON.ERROR);
        }

        return generarJsonView(res);

    }

    // -------------------------------------------------------------------
    // FUNCIONES PRIVADAS
    // -------------------------------------------------------------------

    /**
     * Carga el tramite y lo registra en sesion.
     *
     * @param pIdSesion
     *            Id sesion
     * @param recarga
     *            Indica si es una recarga dentro del flujo (formularios,
     *            pagos,..) o una carga del trámite desde persistencia.
     */
    private void cargarTramiteImpl(final String pIdSesion,
            final boolean recarga) {

        // Obtenemos info usuario autenticado
        final UsuarioAutenticado user = SecurityUtils
                .obtenerUsuarioAutenticado();
        final UsuarioAutenticadoInfo userInfo = user.getUsuario();

        // Cargamos flujo
        if (recarga) {
            getFlujoTramitacionService().recargarTramite(pIdSesion, userInfo);
        } else {
            getFlujoTramitacionService().cargarTramite(pIdSesion, userInfo);
        }

        // Obtiene detalle tramite
        final DetalleTramite dt = getFlujoTramitacionService()
                .obtenerDetalleTramite(pIdSesion);

        // Registra en sesion
        registraSesionTramitacion(pIdSesion, dt.getDebug() == TypeSiNo.SI);

        // Comprobamos que sea el iniciador (en caso de autenticado)
        if (dt.getTramite().getAutenticacion() != userInfo.getAutenticacion()) {
            throw new WarningFrontException("No coincide nivel autenticacion");
        }
        if (dt.getTramite().getAutenticacion() != TypeAutenticacion.ANONIMO) {
            if (!StringUtils.equals(dt.getUsuario().getNif(),
                    userInfo.getNif())) {
                throw new WarningFrontException(
                        "No coincide usuario autenticado ("
                                + user.getUsuario().getNif()
                                + ") con usuario iniciador trámite ("
                                + dt.getUsuario().getNif() + ")");
            }
        }

    }

}

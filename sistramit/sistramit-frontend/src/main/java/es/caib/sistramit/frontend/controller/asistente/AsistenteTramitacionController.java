package es.caib.sistramit.frontend.controller.asistente;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.model.flujo.*;
import es.caib.sistramit.core.api.model.flujo.types.*;
import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.system.rest.externo.InfoTramiteFH;
import es.caib.sistramit.frontend.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.ErrorFormularioSoporteException;
import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.exception.WarningFrontException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.security.InfoLoginTramite;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.system.rest.externo.InfoTicketAcceso;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.api.service.SecurityService;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.frontend.ApplicationContextProvider;
import es.caib.sistramit.frontend.ModuleConfig;
import es.caib.sistramit.frontend.controller.TramitacionController;
import es.caib.sistramit.frontend.literales.LiteralesFront;
import es.caib.sistramit.frontend.model.types.TypeRespuestaJSON;
import es.caib.sistramit.frontend.security.SecurityUtils;
import es.caib.sistramit.frontend.security.UsuarioAutenticado;

@Controller
@RequestMapping(value = "/asistente")
public class AsistenteTramitacionController extends TramitacionController {

	/** Security service. */
	@Autowired
	private SecurityService securityService;

	/** System service. */
	@Autowired
	private SystemService systemService;

	/** Url redireccion asistente. */
	private static final String URL_REDIRIGIR_ASISTENTE = "asistente/redirigirAsistente";

	/** Url redireccion asistente. */
	private static final String URL_REDIRIGIR_PERSISTENCIA = "asistente/persistencia";

	/**
	 * Inicia trámite.
	 *
	 * @param tramite
	 *                              Trámite
	 * @param version
	 *                              Versión
	 * @param idioma
	 *                              Idioma
	 * @param idTramiteCatalogo
	 *                              Id trámite en catálogo de servicios
	 * @param parametros
	 *                              Parameros de inicio del trámite. Lista separada
	 *                              por -_- (p.e.:
	 *                              param1-_-valor1-_-param2-_-valor2)
	 * @param request
	 *                              request
	 * @return Redireccion a mostrar asistente
	 */
	@RequestMapping(value = "/iniciarTramite.html")
	public ModelAndView iniciarTramite(@RequestParam("tramite") final String tramite,
			@RequestParam("version") final int version,
			@RequestParam(value = "idioma", required = false) final String idioma,
			@RequestParam("idTramiteCatalogo") final String idTramiteCatalogo,
			@RequestParam(value = "servicioCatalogo", required = false, defaultValue = "false") final boolean servicioCatalogo,
			@RequestParam(value = "parametros", required = false) final String parametros,
			@RequestParam(value = "forzarNuevo", required = false, defaultValue = "false") final boolean forzarNuevo,
			final HttpServletRequest request) {
		DatosInicioTramite datosInicioTramite = new DatosInicioTramite(tramite, version, idioma, idTramiteCatalogo, servicioCatalogo, parametros, forzarNuevo);
		return iniciarTramiteImpl(datosInicioTramite, request);
	}

	@RequestMapping(value = "/iniciarTramiteDesdePersistencia.html")
	public ModelAndView iniciarTramiteDesdePersistencia(final HttpServletRequest request) {
		DatosInicioTramite datosInicioTramite = (DatosInicioTramite) request.getSession().getAttribute("datosInicioTramite");
		if (datosInicioTramite == null) {
			throw new ErrorFrontException("No s'han indicat dades per iniciar tràmit");
		}
		return iniciarTramiteImpl(datosInicioTramite, request);
	}

	/**
	 * Carga trámite existente y redirige a la página del asistente.
	 *
	 * @param idSesionCifrado
	 *                            Identificador sesión de tramitación (cifrado)
	 * @return Vista que redirige al asistente
	 */
	@RequestMapping("/cargarTramite.html")
	public ModelAndView cargarTramite(@RequestParam("idSesionTramitacion") final String idSesionCifrado) {

		// Decodificamos id sesion
		// final String idSesion = CifradoUtil.decrypt(idSesionCifrado);
		final String idSesion = idSesionCifrado;

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
		final DetalleTramite detalleTramite = getFlujoTramitacionService().obtenerDetalleTramite(idSesionTramitacion);

		// Devolvemos informacion asistente
		final AsistenteInfo ai = new AsistenteInfo();
		ai.setIdSesionTramitacion(idSesionTramitacion);
		ai.setIdioma(detalleTramite.getTramite().getIdioma());
		final ModuleConfig module = (ModuleConfig) ApplicationContextProvider.getApplicationContext()
				.getBean("frontModuleConfig");
		ai.setVersion(module.getVersion());
		if (module.getCommitSvn() == null || module.getCommitSvn().isEmpty()) {
			ai.setCommit(module.getCommitGit());
		} else {
			ai.setCommit(module.getCommitSvn());
		}

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

		// Cancela trámite
		final String url = getFlujoTramitacionService().cancelarTramite(idSesionTramitacion);

		final RespuestaJSON res = new RespuestaJSON();
		final MensajeUsuario mu = new MensajeUsuario(
				getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES, "atencion", getIdioma()),
				getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES, "tramite.cancelado", getIdioma()));
		res.setMensaje(mu);
		res.setUrl(url);

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
		debug("Logout tramite");
		final String idSesionTramitacion = getIdSesionTramitacionActiva();

		// Cancela trámite
		final String url = getFlujoTramitacionService().logoutTramite(idSesionTramitacion);

		return new ModelAndView("redirect:" + url);
	}

	/**
	 * Descarga clave tramitación.
	 *
	 * @return Descarga clave tramitación.
	 *
	 */
	@RequestMapping(value = "/descargarClave.html")
	public ModelAndView descargarClave() {
		final String idSesionTramitacion = getIdSesionTramitacionActiva();
		final byte[] datosFichero = getFlujoTramitacionService().obtenerClavePdf(idSesionTramitacion);
		final String nombre = idSesionTramitacion + ".pdf";
		return generarDownloadView(nombre, datosFichero);
	}

	/**
	 * Descarga archivo catálogo procedimientos.
	 *
	 * @return Descarga archivo catálogo procedimientos.
	 *
	 */
	@RequestMapping(value = "/descargarArchivoCP.html")
	public ModelAndView descargarArchivoCP(@RequestParam("referenciaArchivo") final String referenciaArchivo) {
		final String idSesionTramitacion = getIdSesionTramitacionActiva();
		final AnexoFichero fichero = getFlujoTramitacionService().descargarArchivoCP(idSesionTramitacion,
				referenciaArchivo);
		return generarDownloadView(fichero.getFileName(), fichero.getFileContent());
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
		final DetalleTramite detalleTramite = getFlujoTramitacionService().obtenerDetalleTramite(idSesionTramitacion);

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
	 *                   Identificador del formulario.
	 * @return Devuelve JSON con estado actual del trámite.
	 */
	@RequestMapping(value = "/irAPaso.json", method = RequestMethod.POST)
	public ModelAndView irAPaso(@RequestParam("paso") final String idPaso) {
		debug("Ir a paso " + idPaso);

		final String idSesionTramitacion = getIdSesionTramitacionActiva();

		// Intenta ir a paso indicado
		final ResultadoIrAPaso resPaso = getFlujoTramitacionService().irAPaso(idSesionTramitacion, idPaso);

		// Obtiene info paso actual
		debug("Paso actual: " + resPaso.getIdPasoActual());

		// Recupera info tramite
		final DetallePasos dt = getFlujoTramitacionService().obtenerDetallePasos(idSesionTramitacion);
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
		final ResultadoIrAPaso resPaso = getFlujoTramitacionService().irAPasoActual(idSesionTramitacion);

		// Obtiene info paso actual
		debug("Paso actual: " + resPaso.getIdPasoActual());

		// Recupera info tramite
		final DetallePasos dt = getFlujoTramitacionService().obtenerDetallePasos(idSesionTramitacion);
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

		final Properties props = getLiteralesFront().getLiteralesSeccion(LiteralesFront.APLICACION, getIdioma());
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

		return new ModelAndView("asistente/literales", "literales", props.entrySet());
	}

	/**
	 * Devuelve configuración aplicación.
	 *
	 * @return configuración aplicación
	 */
	@RequestMapping("/js/configuracion.js")
	public ModelAndView obtenerConfiguracionAplicacion() {

		// Detalle trámite
		DetalleTramite tramiteInfo = this.getFlujoTramitacionService().obtenerDetalleTramite(this.getIdSesionTramitacionActiva());

		// Metemos version sistra2 para cachear js/css por versión (si es SNAPSHOT
		// metemos timestamp para forzar recuperación)
		String version = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.VERSION);
		if (StringUtils.endsWith(version, "SNAPSHOT")) {
			version += "-" + System.currentTimeMillis();
		}

		// Iframe firma
		final String iframeFirmaWidth = StringUtils.defaultString(
				getSystemService().obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IFRAME_FIRMA_WIDTH), "200");
		final String iframeFirmaHeight = StringUtils.defaultString(
				getSystemService().obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IFRAME_FIRMA_HEIGHT),
				"200");

		// Iframe digitalizacion
		final String iframeDigitalizacionWidth = StringUtils.defaultString(
				getSystemService().obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IFRAME_DIGITALIZACION_WIDTH), "200");
		final String iframeDigitalizacionHeight = StringUtils.defaultString(
				getSystemService().obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IFRAME_DIGITALIZACION_HEIGHT),
				"200");

		// Formulario: carácteres búsqueda. */
		final String formularioNumCharsBusqueda = StringUtils.defaultString(
				getSystemService().obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.FORM_NUMCHARS_BUSQUEDA),
				"3");

		final AsistenteConfig conf = new AsistenteConfig();
		conf.setUrl(getSystemService().obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_URL));
		conf.setIdioma(this.getIdioma());
		conf.setVersion(version);
		conf.setIframeFirmaHeight(iframeFirmaHeight);
		conf.setIframeFirmaWidth(iframeFirmaWidth);
		conf.setIframeDigitalizacionHeight(iframeDigitalizacionHeight);
		conf.setIframeDigitalizacionWidth(iframeDigitalizacionWidth);
		conf.setFormularioNumCharsBusqueda(formularioNumCharsBusqueda);
		conf.setIdSesion(this.getIdSesionTramitacionActiva());
		conf.setFormularioAyudaActivada(tramiteInfo.getEntidad().getAyudaContextual() == TypeSiNo.SI ? "S" : "N");

		return new ModelAndView("asistente/configuracion", "configuracion", conf);
	}

	/**
	 * Retorno gestor pago externo.
	 *
	 * @param ticket
	 *                   ticket
	 * @return retorno de pago externo recargando el trámite
	 */
	@RequestMapping(value = "/retornoPagoExterno.html")
	public ModelAndView retornoPagoExterno(@RequestParam("ticket") final String ticket) {

		// Obtenemos datos retorno pago
		final RetornoPago retornoPago = securityService.obtenerTicketPago(ticket);

		// Cargamos tramite de persistencia
		final String idSesionTramitacion = retornoPago.getIdSesionTramitacion();
		this.cargarTramiteImpl(idSesionTramitacion, true);

		// Validamos pago
		final ParametrosAccionPaso params = new ParametrosAccionPaso();
		params.addParametroEntrada("idPago", retornoPago.getIdPago());
		final ResultadoAccionPaso ra = getFlujoTramitacionService().accionPaso(idSesionTramitacion,
				retornoPago.getIdPaso(), TypeAccionPasoPagar.VERIFICAR_PAGO_PASARELA, params);
		final PagoVerificacion verificacion = (PagoVerificacion) ra.getParametroRetorno("verificacion");

		debug("Estado pago: Verificado = " + verificacion.getVerificado() + " - Realizado = "
				+ verificacion.getRealizado());

		// En funcion del resultado, mostramos mensaje al usuario
		final MensajeAsistente ma = generarMensajeValidacionPago(verificacion);
		this.setMensajeAsistente(ma);

		// Redirigimos a carga asistente
		return new ModelAndView(URL_REDIRIGIR_ASISTENTE);
	}

	/**
	 * Retorno gestor formulario externo.
	 *
	 * @param ticket
	 *                   ticket
	 * @return retorno de formulario externo recargando el trámite
	 */
	@RequestMapping(value = "/retornoGestorFormularioExterno.html")
	public ModelAndView retornoGestorFormularioExterno(@RequestParam("ticket") final String ticket) {
		// Obtenemos info ticket
		final RetornoFormularioExterno infoTicket = securityService.obtenerTicketFormularioExterno(ticket);
		// Cargamos tramite de persistencia
		this.cargarTramiteImpl(infoTicket.getIdSesionTramitacion(), true);
		// Retornamos a formulario
		return retornoFormulario(infoTicket.getIdSesionTramitacion(), infoTicket.getIdPaso(),
				infoTicket.getIdFormulario(), infoTicket.getTicket());
	}

	protected ModelAndView retornoFormulario(final String idSesionTramitacion, final String idPaso,
			final String idFormulario, final String ticketSesionFormulario) {
		// Obtenemos detalle tramite
		final DetalleTramite dt = getFlujoTramitacionService().obtenerDetalleTramite(idSesionTramitacion);

		// Ejecutamos accion paso segun tipo paso
		ParametrosAccionPaso pParametros;
		pParametros = new ParametrosAccionPaso();
		pParametros.addParametroEntrada("idFormulario", idFormulario);
		pParametros.addParametroEntrada("ticket", ticketSesionFormulario);

		TypeAccionPaso accionPaso = null;
		if (dt.getTramite().getTipoPasoActual() == TypePaso.RELLENAR) {
			accionPaso = TypeAccionPasoRellenar.GUARDAR_FORMULARIO;
		} else if (dt.getTramite().getTipoPasoActual() == TypePaso.CAPTURAR) {
			throw new ErrorFrontException("PENDENT IMPLEMENTAR");
		} else {
			throw new ErrorFrontException(
					"No es permet guardar formulari per passa " + dt.getTramite().getTipoPasoActual());
		}

		final ResultadoAccionPaso respuestaGuardarFormulario = getFlujoTramitacionService()
				.accionPaso(idSesionTramitacion, idPaso, accionPaso, pParametros);

		// Redirigimos a carga asistente
		return new ModelAndView(URL_REDIRIGIR_ASISTENTE);
	}


	/**
	 * Retorno componente de digitalización externo (no se gestiona con ticket, se presupone
	 * dentro de la misma sesión).
	 *
	 * @param idPaso    id paso
	 * @param idDocumento id documento
	 * @param instancia  instancia
	 * @return retorno de componente de firma externo recargando el trámite
	 */
	@RequestMapping(value = "/retornoDigitalizacionExterno.html")
	public ModelAndView retornoDigitalizacionExterno(@RequestParam("idPaso") final String idPaso,
											@RequestParam("idDocumento") final String idDocumento) {


		debug("Retorno digitalización para documento: " + idDocumento);

		// Cargamos tramite de persistencia
		final String idSesionTramitacion = getIdSesionTramitacion();

		// Tratamos documento digitalizado
		ParametrosAccionPaso pParametros;
		pParametros = new ParametrosAccionPaso();
		pParametros.addParametroEntrada("idAnexo", idDocumento);
		final ResultadoAccionPaso rap = getFlujoTramitacionService().accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoAnexar.FINALIZAR_DIGITALIZACION_ANEXO, pParametros);

		// Revisamos respuesta digitalizacion
		final DigitalizacionResultado fv = (DigitalizacionResultado) rap.getParametroRetorno("resultado");
		debug("Digitalizacion: " + fv.getDigitalizado() + "  " + fv.getDetalleError());
		String mensaje = "digitalizacionRealizada";
		String detalleError = null;
		if (fv.getDigitalizado() == TypeSiNo.NO) {
			mensaje = "digitalizacionError";
			detalleError = fv.getDetalleError();
		}

		// En funcion del resultado, mostramos mensaje al usuario
		final MensajeAsistente ma = generarMensajeErrorAsistente("atencion", mensaje, detalleError, TypeRespuestaJSON.SUCCESS);
		this.setMensajeAsistente(ma);

		// Redirigimos a carga asistente
		return new ModelAndView(URL_REDIRIGIR_ASISTENTE);

	}


	/**
	 * Retorno componente de firma externo (no se gestiona con ticket, se presupone
	 * dentro de la misma sesión).
	 *
	 * @param idPaso    id paso
	 * @param idDocumento id documento
	 * @param instancia  instancia
	 * @param firmante   firmante
	 * @return retorno de componente de firma externo recargando el trámite
	 */
	@RequestMapping(value = "/retornoFirmaExterno.html")
	public ModelAndView retornoFirmaExterno(@RequestParam("idPaso") final String idPaso,
			@RequestParam("idDocumento") final String idDocumento, @RequestParam("instancia") final String instancia,
			@RequestParam(value = "firmante", required = false) final String firmante) {

		// TODO Solo se usa firma en registro, si se usara en otro paso deberia añadirse
		// logica para diferenciar el paso

		debug("Verificar firma externa documento: " + idDocumento + " - " + instancia + " para firmante " + firmante);

		// Cargamos tramite de persistencia
		final String idSesionTramitacion = getIdSesionTramitacion();
		// this.cargarTramiteImpl(idSesionTramitacion, true);

		// Verificamos firma
		ParametrosAccionPaso pParametros;
		pParametros = new ParametrosAccionPaso();
		pParametros.addParametroEntrada("idDocumento", idDocumento);
		pParametros.addParametroEntrada("instancia", instancia);
		pParametros.addParametroEntrada("firmante", StringUtils.isNotBlank(firmante) ? firmante : null);
		final ResultadoAccionPaso rap = getFlujoTramitacionService().accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoRegistrar.VERIFICAR_FIRMA_DOCUMENTO, pParametros);

		final FirmaVerificacion fv = (FirmaVerificacion) rap.getParametroRetorno("resultado");

		// TODO Revisar respuesta verificacion firma (si genera excepcion o retorna
		// datos verificacion)

		String mensaje = "firmaClienteVerificada";
		String detalleError = null;

		if (fv.getCancelada() == TypeSiNo.SI) {
			mensaje = "firmaClienteCancelada";
		}

		if (fv.getVerificada() == TypeSiNo.NO) {
			mensaje = "firmaClienteNoVerificada";
			detalleError = fv.getDetalleError();
		}

		// Cargamos asistente
		debug("Firma verificada");

		// En funcion del resultado, mostramos mensaje al usuario
		final MensajeAsistente ma = generarMensajeErrorAsistente("atencion", mensaje, detalleError,
				TypeRespuestaJSON.SUCCESS);
		this.setMensajeAsistente(ma);

		// Redirigimos a carga asistente
		return new ModelAndView(URL_REDIRIGIR_ASISTENTE);

	}

	/**
	 * Retorno desde gestor formularios interno.
	 *
	 * @param idPaso
	 *                         id paso
	 * @param idFormulario
	 *                         id formulario
	 * @param ticket
	 *                         id sesión formulario
	 * @return Actualiza datos formulario y recarga asistente
	 */
	@RequestMapping(value = "/retornoGestorFormularioInterno.html")
	public ModelAndView retornoGestorFormularioInterno(@RequestParam("idPaso") final String idPaso,
			@RequestParam("idFormulario") final String idFormulario, @RequestParam("ticket") final String ticket) {
		debug("Retorno gestor formulario interno: " + idFormulario + " - idSesionFormulario " + ticket);
		return retornoFormulario(getIdSesionTramitacion(), idPaso, idFormulario, ticket);
	}

	/**
	 * Retorno desde carpeta ciudadano.
	 *
	 * @param ticket
	 *                   ticket
	 * @return carga asistente
	 */
	@RequestMapping(value = "/retornoCarpetaCiudadano.html")
	public ModelAndView retornoCarpetaCiudadano(@RequestParam("ticket") final String ticket,
			final HttpServletRequest request) {
		// Obtenemos datos ticket
		final InfoTicketAcceso infoTicket = securityService.obtenerTicketAcceso(ticket);
		try {
			// Cargamos tramite de persistencia
			final String idSesionTramitacion = infoTicket.getIdSesionTramitacion();
			this.cargarTramiteImpl(idSesionTramitacion, false);
			// Redirigimos a carga asistente
			return new ModelAndView(URL_REDIRIGIR_ASISTENTE);
		} catch (final Exception ex) {
			// Capturamos error para poder redirigir a url callback
			return generarViewForException(ex, infoTicket.getUrlCallbackError(), request);
		}
	}

	/**
	 * Retorno inicio tramitación FH.
	 *
	 * @param ticket
	 *                   ticket
	 * @return carga asistente
	 */
	@RequestMapping(value = "/retornoFH.html")
	public ModelAndView retornoFH(@RequestParam("ticket") final String ticket,
												final HttpServletRequest request) {
		// Obtenemos datos ticket
		final InfoTicketAcceso infoTicket = securityService.obtenerTicketAcceso(ticket);
		try {
			// Iniciamos tramite
			InfoTramiteFH tramiteFH = infoTicket.getInfoAccesoFH().getTramiteFH();
			DatosInicioTramite datosInicioTramite = new DatosInicioTramite(tramiteFH.getTramite(), tramiteFH.getVersion(),
					tramiteFH.getIdioma(), tramiteFH.getIdTramiteCatalogo(), tramiteFH.isServicioCatalogo(),
					tramiteFH.getParametros(), false);
			return iniciarTramiteImpl(datosInicioTramite, request);
		} catch (final Exception ex) {
			// Capturamos error
			String urlCallback = null;
			return generarViewForException(ex, urlCallback, request);
		}
	}

	/**
	 * Petición ayuda mediante formulario soporte.
	 *
	 * @param nif
	 *                         Nif
	 * @param nombre
	 *                         Nombre
	 * @param telefono
	 *                         Teléfono
	 * @param email
	 *                         Email
	 * @param problemaTipo
	 *                         Tipo problema
	 * @param problemaDesc
	 *                         Descripción problema
	 * @param request
	 *                         request
	 * @return
	 */
	@RequestMapping(value = "/formularioSoporte.json", method = RequestMethod.POST)
	public ModelAndView formularioSoporte(@RequestParam(value = "nif", required = false) final String nif,
			@RequestParam(value = "nombre", required = false) final String nombre,
			@RequestParam(value = "telefono", required = true) final String telefono,
			@RequestParam(value = "email", required = true) final String email,
			@RequestParam(value = "problemaTipo", required = true) final String problemaTipo,
			@RequestParam(value = "problemaDesc", required = true) final String problemaDesc,
			@RequestParam(value = "horarioContacto", required = true) final String horarioContacto,
			final HttpServletRequest request) {

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
				if (fic != null && fic.getBytes() != null && fic.getBytes().length > 0) {
					anexo = new AnexoFichero();
					anexo.setFileName(fic.getOriginalFilename());
					anexo.setFileContent(fic.getBytes());
					anexo.setFileContentType(fic.getContentType());
				}

				// TODO METEMOS ÑAPA PARA LIMITAR A 3000 YA QUE EN CHROME LOS TEXTAREA NO CUENTAN LOS SALTOS DE LINEA
				getFlujoTramitacionService().envioFormularioSoporte(idSesionTramitacion, nif, nombre, telefono, email,
						problemaTipo, StringUtils.substring(problemaDesc,0, 4000), horarioContacto, anexo);
;
			} catch (final ErrorFormularioSoporteException | IOException efs) {
				res.setEstado(TypeRespuestaJSON.ERROR);
			}

		} else {
			res.setEstado(TypeRespuestaJSON.ERROR);
		}

		return generarJsonView(res);

	}

	/**
	 * Redirigimos peticiones al contexto raíz de asistente para que redirija a
	 * asistente.html.
	 */
	@RequestMapping(value = "/")
	public ModelAndView index() {
		return new ModelAndView(URL_REDIRIGIR_ASISTENTE);
	}

	/**
	 * Url para mantener sesión activa (pensada para timer en cliente).
	 * @return Jsonview
	 */
	@RequestMapping(value = "/mantenerSesion.json", method = RequestMethod.POST)
	public ModelAndView mantenerSesion() {
		// Generamos respuesta
		RespuestaJSON resAnexar = new RespuestaJSON();
		// - Retorna JSON
		return generarJsonView(resAnexar);
	}

	// -------------------------------------------------------------------
	// FUNCIONES PRIVADAS
	// -------------------------------------------------------------------

	/**
	 * Carga el tramite y lo registra en sesion.
	 *
	 * @param pIdSesion
	 *                      Id sesion
	 * @param recarga
	 *                      Indica si es una recarga dentro del flujo (formularios,
	 *                      pagos,..) o una carga del trámite desde persistencia.
	 */
	private void cargarTramiteImpl(final String pIdSesion, final boolean recarga) {

		// Verificamos id sesion no es nulo
		if (StringUtils.isBlank(pIdSesion)) {
			throw new ErrorFrontException("No s'ha indicat id sessió tramitació");
		}

		// Obtenemos info usuario autenticado
		final UsuarioAutenticado user = SecurityUtils.obtenerUsuarioAutenticado();
		final UsuarioAutenticadoInfo userInfo = user.getUsuario();

		// Cargamos flujo
		if (recarga) {
			getFlujoTramitacionService().recargarTramite(pIdSesion, userInfo);
		} else {
			getFlujoTramitacionService().cargarTramite(pIdSesion, userInfo);
		}

		// Obtiene detalle tramite
		final DetalleTramite dt = getFlujoTramitacionService().obtenerDetalleTramite(pIdSesion);

		// Registra en sesion
		registraSesionTramitacion(dt);

		// Comprobamos que sea el iniciador (en caso de autenticado)
		if (dt.getTramite().getAutenticacion() != userInfo.getAutenticacion()) {
			throw new WarningFrontException("No coincideix nivell autenticació");
		}
		if (dt.getTramite().getAutenticacion() != TypeAutenticacion.ANONIMO) {
			if (!StringUtils.equals(dt.getUsuario().getNif(), userInfo.getNif())) {
				throw new WarningFrontException("No coincideix usuari autenticat (" + user.getUsuario().getNif()
						+ ") amb usuario iniciador tràmit (" + dt.getUsuario().getNif() + ")");
			}
		}

	}

	/**
	 * Genera url inicio forzando nuevo trámite.
	 * 
	 * @param datosInicioTramite
	 *                      Datos inicio trámite
	 * @return url inicio forzando nuevo trámite.
	 */
	private String generarUrlIniciarTramiteNuevo(DatosInicioTramite datosInicioTramite) {
        try {
			URI uri = new URIBuilder(getUrlAsistente() + "/asistente/iniciarTramite.html")
                    .addParameter("tramite", datosInicioTramite.getTramite())
                    .addParameter("version", String.valueOf(datosInicioTramite.getVersion()))
                    .addParameter("idioma", datosInicioTramite.getIdioma())
                    .addParameter("idTramiteCatalogo", datosInicioTramite.getIdTramiteCatalogo())
                    .addParameter("servicioCatalogo", String.valueOf(datosInicioTramite.isServicioCatalogo()))
                    .addParameter("parametros", datosInicioTramite.getParametros())
                    .addParameter("forzarNuevo", String.valueOf(true))
                    .build();
			return uri.toString();
        } catch (URISyntaxException e) {
            throw new ErrorConfiguracionException("Error al generar url inicio trámite: " + e.getMessage());
        }
	}

	/**
	 * Inicia trámite.
	 *
	 * @param datosInicioTramite datos inicio trámite
	 * @param request request
	 * @return Redireccion a mostrar asistente
	 */
	private ModelAndView iniciarTramiteImpl(DatosInicioTramite datosInicioTramite, HttpServletRequest request) {
		ModelAndView mav = null;

		// Obtiene usuario autenticado
		final UsuarioAutenticado usuarioAutenticado = SecurityUtils.obtenerUsuarioAutenticado();

		// Acceso por FH
		String accesoFH = usuarioAutenticado.getUsuario().getFuncionarioHabilitado() != null ? usuarioAutenticado.getUsuario().getFuncionarioHabilitado().getNif() : null;

		// Verifica si tiene tramitaciones iniciadas
		List<TramiteIniciado> tramitacionesIniciadas = new ArrayList<>();
		if (!datosInicioTramite.isForzarNuevo()) {
			tramitacionesIniciadas = securityService.obtenerTramitacionesIniciadas(
					usuarioAutenticado.getUsuario().getNif(), datosInicioTramite.getTramite(), datosInicioTramite.getVersion(),
					datosInicioTramite.getIdTramiteCatalogo(), datosInicioTramite.isServicioCatalogo(),
					accesoFH);
		}

		// Si tiene tramitaciones iniciadas, mostramos listado
		if (!tramitacionesIniciadas.isEmpty()) {
			// Obtenemos info trámite
			final InfoLoginTramite tramiteInfo = securityService.obtenerInfoLoginTramite(datosInicioTramite.getTramite(), datosInicioTramite.getVersion(),
					datosInicioTramite.getIdTramiteCatalogo(), datosInicioTramite.isServicioCatalogo(), datosInicioTramite.getIdioma());
			// Literales pagina
			final Map<String, String> literales = new HashMap<>();
			final Properties literalesProps = getLiteralesFront().getLiteralesSeccion("persistencia", datosInicioTramite.getIdioma());
			final Set<String> keys = literalesProps.stringPropertyNames();
			for (final String key : keys) {
				literales.put(key, literalesProps.getProperty(key));
			}
			// Marcamos sesión para indicar que no se invalide al iniciar trámite
			request.getSession().setAttribute(ConstantesSeguridad.AUTOLOGOUT_NOINVALIDAR,
					ConstantesSeguridad.AUTOLOGOUT_NOINVALIDAR);
			// Guardamos en sesión datos incio trámite por si se inicia uno nuevo desde pantalla persistencia
			datosInicioTramite.setForzarNuevo(true);
			request.getSession().setAttribute("datosInicioTramite", datosInicioTramite);
			// Redirige a vista
			final Map<String, Object> model = new HashMap<>();
			model.put("idioma", datosInicioTramite.getIdioma());
			model.put("entidad", tramiteInfo.getEntidad());
			model.put("tramite", tramiteInfo.getTitulo());
			model.put("literales", literales);
			model.put("usuario", usuarioAutenticado.getUsuario());
			model.put("tramitacionesIniciadas", tramitacionesIniciadas);
			String urlInicioTramitePersistencia = getUrlAsistente() + "/asistente/iniciarTramiteDesdePersistencia.html";
			model.put("urlIniciarTramiteNuevo", urlInicioTramitePersistencia);
			model.put("urlReanudarTramiteNuevo", getUrlAsistente() + "/asistente/cargarTramite.html?idSesionTramitacion=");
			mav = new ModelAndView(URL_REDIRIGIR_PERSISTENCIA, model);
		}  else {
			// Si no tiene tramitaciones iniciadas, iniciamos trámite con el idioma será con el que se ha autenticado
			final String idiomaInicio = usuarioAutenticado.getUsuario().getSesionInfo().getIdioma();
			// Parametros inicio (convertimos parametros a map)
			final Map<String, String> parametrosInicio = parametrosInicioTramiteToMap(datosInicioTramite.getParametros());
			// Genera url inicio trámite forzando nuevo
			final String urlInicio = generarUrlIniciarTramiteNuevo(datosInicioTramite);
			// Inicia tramite
			final String idSesionTramitacion = getFlujoTramitacionService().iniciarTramite(
					usuarioAutenticado.getUsuario(), datosInicioTramite.getTramite(), datosInicioTramite.getVersion(),
					idiomaInicio, datosInicioTramite.getIdTramiteCatalogo(), datosInicioTramite.isServicioCatalogo(),
					urlInicio, parametrosInicio);
			// Almacena en la sesion (si no se puede iniciar con el idioma establecido, se
			// cambiará al del trámite)
			final DetalleTramite dt = getFlujoTramitacionService().obtenerDetalleTramite(idSesionTramitacion);
			registraSesionTramitacion(dt);
			// Redirigimos a asistente
			mav = new ModelAndView(URL_REDIRIGIR_ASISTENTE);
		}

		return mav;
	}

	/**
	 * Convierte los parametros de inicio del trámite a un mapa.
	 * @param parametros parametros de inicio del trámite
	 * @return
	 */
	private Map<String, String> parametrosInicioTramiteToMap(String parametros) {
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
		return parametrosInicio;
	}


}

<%@ page isELIgnored="false" %>
<%@page contentType="application/x-javascript" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

// HEADER SESION

var headerIdSessio = "sesion-id";
var tokenIdSessio = "${configuracion.idSesion}";

// CSRF

// var tokenCSRF = $("meta[name='_csrf']").attr("content");
// var headerCSRF = $("meta[name='_csrf_header']").attr("content");
var tokenCSRF = "${_csrf.token}";
var headerCSRF = "${_csrf.headerName}";

if (headerCSRF == "") {
	 headerCSRF = "X-CSRF-TOKEN";
}

// version

var APP_VERSIO = "version=${configuracion.version}";

// timeout

var APP_TIMEOUT = ${configuracion.timeoutAjaxGeneral};
var APP_TIMEOUT_RT = ${configuracion.timeoutAjaxRegistro};

// configuraci� de URLs

var	APP_ = "${configuracion.url}/asistente/"
	,APP_SERVIDOR = "${configuracion.url}/asistente/";

var APP_LITERALS = "${configuracion.url}/asistente/js/literales.js";


/* tramit */

var	APP_TRAMIT_INFO = APP_SERVIDOR + "informacionTramite.json"
	,APP_TRAMIT_PAS = APP_SERVIDOR + "irAPaso.json"
	,APP_TRAMIT_SUPORT = APP_SERVIDOR + "formularioSoporte.json"
	,APP_TRAMIT_CLAU_DESCARREGA = APP_SERVIDOR + "descargarClave.html"
	,APP_TRAMIT_DESCONECTA = APP_SERVIDOR + "logout.html"
	,APP_TRAMIT_CANCELA = APP_SERVIDOR + "cancelarTramite.json";


/* pas emplenar */

var APP_FORM_URL = APP_SERVIDOR + "rf/abrirFormulario.json"
	,APP_FORM_PDF = APP_SERVIDOR + "rf/descargarFormulario.html"
	,APP_FORM_XML = APP_SERVIDOR + "rf/descargarXmlFormulario.html"
	,APP_FORM_IMPRIMIR = APP_SERVIDOR + "<pendiente>";


/* pas annexar */

var APP_ANNEXE_ANNEXA = APP_SERVIDOR + "ad/anexarDocumento.json"
	,APP_ANNEXE_ESBORRA = APP_SERVIDOR + "ad/borrarDocumento.json"
	,APP_ANNEXE_DESCARREGA = APP_SERVIDOR + "ad/descargarDocumento.html"
	,APP_PLANTILLA_DESCARREGA = APP_SERVIDOR + "ad/descargarPlantilla.html";


/* pas taxa */

var APP_TAXA_URL = APP_SERVIDOR + "pt/iniciarPago.json"
	,APP_TAXA_VALIDAR = APP_SERVIDOR + "pt/verificarPagoIniciado.json"
	,APP_TAXA_DESCARTAR = APP_SERVIDOR + "pt/cancelarPagoIniciado.json"
	,APP_TAXA_JUSTIFICANT = APP_SERVIDOR + "pt/descargarJustificante.html"
	,APP_TAXA_PRESENCIAL = APP_SERVIDOR + "pt/cartaPago.json";


/* pas registrar */

var  APP_SIGNATURA_URL = APP_SERVIDOR + "rt/firmarDocumento.json"
	,APP_TRAMIT_REGISTRA = APP_SERVIDOR + "rt//registrarTramite.json"
	,APP_REGISTRE_DESCARREGA = APP_SERVIDOR + "rt/descargarDocumento.html"
	,APP_SIGNATURA_REGISTRAR_DESCARREGA = APP_SERVIDOR + "rt/descargarFirma.html"
	,APP_TRAMIT_REINTENTA = APP_SERVIDOR + "rt/reintentar.json";

/* pas guardar */

var APP_TRAMIT_JUSTIFICANT = APP_SERVIDOR + "gj/descargarJustificante.html",
	APP_TRAMIT_JUSTIFICANT_URL = APP_SERVIDOR + "gj/redirigirJustificante.json",
	APP_TRAMIT_JUSTIFICANT_CARPETA = APP_SERVIDOR + "gj/redirigirJustificante.json",
	APP_GUARDA_DESCARREGA = APP_SERVIDOR + "gj/descargarDocumento.html",
	APP_TRAMIT_SURT = APP_SERVIDOR + "gj/salirTramite.html",
	APP_SIGNATURA_GUARDAR_DESCARREGA = APP_SERVIDOR + "gj/descargarFirma.html",
	APP_TRAMIT_VALORA = APP_SERVIDOR + "gj/valorarTramite.json";


/* formulario */
var APP_FORMS_URL_DINAMICA = "s"
	,APP_FORM_DATA_FORMAT = "es"
	,APP_FORM_CARREGA = APP_SERVIDOR + "fm/cargarFormulario.json"
	,APP_FORM_GUARDA = APP_SERVIDOR + "fm/guardarPagina.json"
	,APP_FORM_CANCELAR = APP_SERVIDOR + "fm/cancelar.json"
	,APP_FORM_AVALUA_CAMP = APP_SERVIDOR + "fm/evaluarCambioCampo.json"
	,APP_FORMS_CAPTCHA_TXT_GENERA = APP_SERVIDOR + "fm/generarImagenCaptcha.html"
	,APP_FORMS_CAPTCHA_TXT_REPRODUIX = APP_SERVIDOR + "fm/generarSonidoCaptcha.html"
	,APP_FORMS_CAPTCHA_SEL_GENERA = ""
	,APP_FORMS_CAPTCHA_SEL_REPRODUEIX = ""
	,APP_FORMS_CAPTCHA_SEL_DESCARREGA = ""
	,APP_FORM_PAG_ACTUAL = APP_SERVIDOR + "fm/cargarPaginaActual.json"
	,APP_FORM_PAG_ANTERIOR = APP_SERVIDOR + "fm/cargarPaginaAnterior.json"
	,APP_FORM_DESAR_SORTIR = APP_SERVIDOR + "fm/guardarSalirPagina.json"
	,APP_FORM_SELECTOR_AJAX = APP_SERVIDOR + "fm/buscadorDinamico.json"
	,APP_FORM_SELECTOR_AJAX_LE = APP_SERVIDOR + "fm/buscadorDinamicoElemento.json"
	,APP_FORM_SELECTOR_DIN_NUM = ${configuracion.formularioNumCharsBusqueda}
	,APP_FORM_LE_AFEGIX = APP_SERVIDOR + "fm/anyadirElemento.json"
	,APP_FORM_LE_MODIFICA = APP_SERVIDOR + "fm/modificarElemento.json"
	,APP_FORM_LE_AVALUA = APP_SERVIDOR + "fm/evaluarCambioCampoElemento.json"
	,APP_FORM_LE_DESA = APP_SERVIDOR + "fm/guardarElemento.json"
	,APP_FORM_LE_CONSULTA = APP_SERVIDOR + "fm/consultarElemento.json";



/* configuracion iframe firma */
var APP_SIGNATURA_IFRAME_WIDTH = ${configuracion.iframeFirmaWidth}
	,APP_SIGNATURA_IFRAME_HEIGHT = ${configuracion.iframeFirmaHeight};

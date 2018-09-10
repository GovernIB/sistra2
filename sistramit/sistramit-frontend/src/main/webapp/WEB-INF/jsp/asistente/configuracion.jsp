<%@ page isELIgnored="false" %>
<%@page contentType="application/x-javascript" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

// CSRF

var tokenCSRF = $("meta[name='_csrf']").attr("content");
var headerCSRF = $("meta[name='_csrf_header']").attr("content");


// configuració de URLs


var	APP_ = "${configuracion.url}/asistente/"
	,APP_SERVIDOR = "${configuracion.url}/asistente/";

var APP_LITERALS = "${configuracion.url}/asistente/js/literales.js";


/* tramit */

var	APP_TRAMIT_INFO = APP_SERVIDOR + "informacionTramite.json"
	,APP_TRAMIT_PAS = APP_SERVIDOR + "irAPaso.json"
	,APP_TRAMIT_SUPORT = APP_SERVIDOR + "formularioSoporte.json"
	,APP_TRAMIT_CLAU_DESCARREGA = APP_SERVIDOR + "descargarClave.html"
	,APP_TRAMIT_DESCONECTA = APP_SERVIDOR + "logout.html"
	,APP_TRAMIT_CANCELA = APP_SERVIDOR + "cancelarTramite.json"
	,APP_TRAMIT_SURT = "http://www.google.es";


/* pas emplenar */

var APP_FORM_URL = APP_SERVIDOR + "bbdd/formulariURL.php"
	,APP_FORM_PDF = APP_SERVIDOR + "rf/descargarFormulario.html"
	,APP_FORM_XML = APP_SERVIDOR + "rf/descargarXmlFormulario.html";


/* pas annexar */

var APP_ANNEXE_ANNEXA = APP_SERVIDOR + "bbdd/annexeAnnexa.php"
	,APP_ANNEXE_ESBORRA = APP_SERVIDOR + "bbdd/annexeEsborra.php"
	,APP_ANNEXE_DESCARREGA = APP_SERVIDOR + "bbdd/annexeDescarrega.php"
	,APP_PLANTILLA_DESCARREGA = APP_SERVIDOR + "bbdd/plantillaDescarrega.php";


/* pas taxa */

var APP_TAXA_DESCARREGA = APP_SERVIDOR + "bbdd/taxaDescarrega.php";


/* pas registrar */

var APP_SIGNA = APP_SERVIDOR + "rt/firmarDocumento.json",
	APP_TRAMIT_REGISTRA = APP_SERVIDOR + "rt/registrarTramite.json";

/* pas guardar */

var APP_TRAMIT_JUSTIFICANT = APP_SERVIDOR + "gj/descargarJustificante.html"
<%@ page isELIgnored="false" %>
<%@page contentType="application/x-javascript" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

// CSRF
var tokenCSRF = $("meta[name='_csrf']").attr("content");
var headerCSRF = $("meta[name='_csrf_header']").attr("content");

// configuració de URLs

var	APP_SERVIDOR = "${configuracion.url}/asistente/",
	APP_SERVIDOR_JSON = "${configuracion.url}/asistente/",
	APP_SERVIDOR_EXT = ".json";

var	APP_URL_TRAMIT = "informacionTramite",
	APP_URL_PAS = "irAPaso";

var APP_LITERALS = "${configuracion.url}/asistente/js/literales.js";

var APP_URL_SUPORT_FORM = "${configuracion.url}/asistente/formularioSoporte.json";

var APP_URL_FORMS = "../sistra2form/index.html";
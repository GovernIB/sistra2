<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="${idioma}" lang="${idioma}">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>DEBUG STT</title>

	<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" media="screen" href="css/debug.css" />

	<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="js/jquery.imc.debug.js"></script>
	<script type="text/javascript">

		var textDeAcuerdo = "${datos.literales['sesiontramitacion.deacuerdo']}";
		var textCargando = "${datos.literales['sesiontramitacion.cargando']}";
		var textSinEventos = "${datos.literales['sesiontramitacion.sineventos']}";
		var textCabeceraIdTramite = "${datos.literales['sesiontramitacion.cabecera.idTramite']}";
		var textCabeceraEntorno = "${datos.literales['sesiontramitacion.cabecera.entorno']}";
		var textCabeceraIdSesion = "${datos.literales['sesiontramitacion.cabecera.idSesion']}";
		var textCabeceraIcono = "${datos.literales['sesiontramitacion.cabecera.icona']}";
		var textCabeceraLogInterno = "${datos.literales['sesiontramitacion.cabecera.fechaLogInterno']}";
		var textCabeceraTipoEvento = "${datos.literales['sesiontramitacion.cabecera.tipoEvento']}";
		var textCabeceraDescripcion = "${datos.literales['sesiontramitacion.cabecera.descripcion']}";
		var textCabeceraOpciones = "${datos.literales['sesiontramitacion.cabecera.opciones']}";
		var textTrazaError = "${datos.literales['sesiontramitacion.trazaError']}";
		var textPropiedades = "${datos.literales['sesiontramitacion.propiedades']}";


		var textoTipo = new Array();
		textoTipo['TR_INI'] =  "${datos.literales['typeEvento.TR_INI']}";
		textoTipo['TR_INK'] =  "${datos.literales['typeEvento.TR_INK']}";
		textoTipo['TR_CAR'] =  "${datos.literales['typeEvento.TR_CAR']}";
		textoTipo['TR_BOR'] =  "${datos.literales['typeEvento.TR_BOR']}";
		textoTipo['TR_REG'] =  "${datos.literales['typeEvento.TR_REG']}";
		textoTipo['TR_ENV'] =  "${datos.literales['typeEvento.TR_ENV']}";
		textoTipo['TR_FIN'] =  "${datos.literales['typeEvento.TR_FIN']}";
		textoTipo['TR_SCR'] =  "${datos.literales['typeEvento.TR_SCR']}";
		textoTipo['TR_SCR'] =  "${datos.literales['typeEvento.TR_SCR']}";

	<!--
		var pagControlador ="html";
		var dirDades = "debug";

		var tokenCSRF= "<c:out value="${tokenCSRF}"/>";

	-->
	</script>

</head>

<body>

	<!-- contenidor -->
	<div id="contenidor">

		<!-- cap -->
		<div id="cap">
			<div id="cap_contingut">

				<div class="aplicacio">
				  <div class="tituloDebug"><p>${datos.literales['sesiontramitacion.titulo']}</p></div>
				</div>

			</div>
		</div>
		<!-- /cap -->

		<!-- apartats -->
		<div id="apartats">
			<div id="apartats_contingut"></div>
		</div>
		<!-- /apartats -->

		<!-- continguts -->
		<div id="continguts">

			<!-- escriptori -->
			<div id="escriptori">
				<div id="escriptori_contingut">

					<div class="de-cercador">
						<label for="minutos">${datos.literales['sesiontramitacion.minutos']}</label> <input type="text" id="minutos" value="30" size="3" maxlength="4" />
						<a class="bt bt-recupera"><span class="i">&nbsp;</span><span class="t">${datos.literales['sesiontramitacion.recupera']}</span></a>
					</div>

					<div id="log"></div>

				</div>
			</div>
			<!-- /escriptori -->

		</div>
		<!-- /continguts -->


	</div>
	<!-- /contenidor -->

</body>

</html>
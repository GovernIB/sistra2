<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>DEBUG STT</title>

	<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" media="screen" href="css/debug.css" />

	<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="js/jquery.imc.comuns.js"></script>
	<script type="text/javascript" src="js/jquery.imc.debug.js"></script>
	<script type="text/javascript">
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
				  <div class="tituloDebug"><p>Debug Asistente de Tramitación</p></div>
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
						<label for="minutos">Minutos a recuperar antes de instante actual</label> <input type="text" id="minutos" value="30" size="3" maxlength="4" />
						<a class="bt bt-recupera"><span class="i">&nbsp;</span><span class="t">Recupera</span></a>
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
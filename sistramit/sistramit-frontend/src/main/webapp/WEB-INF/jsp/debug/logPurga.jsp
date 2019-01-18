<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="${datos.idioma}" lang="${datos.idioma}">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>DEBUG PURGA STT</title>

	<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" media="screen" href="css/debug.css" />

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
				  <div class="tituloDebug"><p>${datos.literales['purga.titulo']}</p></div>
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

					<div id="log">
						<p>
							${datos.literales['purga.finalizado.ok']}: <input type="text" name="resultado" value="${datos.resultado.finalizadoOk}" style="width:200px" />
						</p>
						<p>
							<label style="vertical-align: top;">${datos.literales['purga.detalles']}:</label>
							<textarea rows="40" cols="100">
								${datos}
							</textarea>
						</p>

					</div>

				</div>
			</div>
			<!-- /escriptori -->

		</div>
		<!-- /continguts -->


	</div>
	<!-- /contenidor -->

</body>

</html>
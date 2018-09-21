<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="${datos.idioma}">
<head>

	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>

	<title>GOIB</title>

	<!--[if IE]><link rel="shortcut icon" href="imgs/favicon/favicon.ico" type="image/x-icon" /><![endif]-->
	<link rel="apple-touch-icon-precomposed" href="imgs/favicon/favicon-apple.png" />
	<link rel="icon" href="imgs/favicon/favicon.png" />

	<link rel="stylesheet" media="screen" href="css/imc-sf.css" />

	<script src="js/utils/jquery-3.3.1.min.js"></script>

	<!-- inicia! -->
	<script src="js/configuracion.js"></script>
	<script src="js/imc-sf.js"></script>

</head>

<body>

	<div id="imc-carrega-inicial" class="imc-carrega-inicial">

		<!--<div class="imc--logo" title="Govern de les Illes Balears"></div>-->

		<h1><span>Assistent de tramitació</span></h1>

		<!-- gestió errors -->

		<div class="imc--errors" aria-hidden="true">

			<div>Error al carregar l'aplicació</div>

			<ul class="imc--botonera">
				<li>
					<button type="button" class="imc-bt imc--ico imc--torna imc--error-torna" data-tipus="torna"><span>Intentar-ho de nou</span></button>
				</li>
				<li>
					<a href="http://www.caib.es"><span>Anar al portal del GOIB</span></a>
				</li>
			</ul>

		</div>

	</div>

	<!-- contenidor -->
	<div id="imc-contenidor" class="imc-contenidor" aria-hidden="true">

		<div class="imc-contingut" id="imc-contingut">
			<div class="imc--c">

			</div>
		</div>

	</div>
	<!-- /contenidor -->

</body>
</html>
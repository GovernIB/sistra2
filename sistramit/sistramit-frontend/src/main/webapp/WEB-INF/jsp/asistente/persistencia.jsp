<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="${idioma}">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

	<meta charset="utf-8">
	<meta http-equiv="Expires" content="0">
	<meta http-equiv="Last-Modified" content="0">
	<meta http-equiv="Cache-Control" content="no-cache, mustrevalidate">
	<meta http-equiv="Pragma" content="no-cache">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>

	<title>${entidad.codigo}</title>

	<!--[if IE]><link rel="shortcut icon" href="imgs/favicon/favicon.ico" type="image/x-icon" /><![endif]-->
	<link rel="apple-touch-icon-precomposed" href="${entidad.favicon}">
	<link rel="icon" href="${entidad.favicon}">

	<link rel="stylesheet" media="screen" href="css/imc-sf.css">
	<link rel="stylesheet" media="screen" href="css/imc-sf-botonera.css">
	<link rel="stylesheet" media="screen" href="css/imc-sf-base.css">

	<script src="js/utils/jquery-1.12.4.min.js"></script>

	<!-- persistencia -->

	<link rel="stylesheet" media="screen" href="css/imc-sf-persistencia.css">

</head>

<body>

	<!-- contenidor -->

	<div id="imc-contenidor" class="imc-contenidor">


		<!-- persistencia -->

		<div class="imc--persistencia" id="imc--persistencia">
			<div class="imc--c">


				<h1><span>${literales["tramitacionesIniciadas"]}</span></h1>

				<div class="imc--info">
					<p>${literales["avisoTramitacionesIniciadasInicio"]} <em>${literales["avisoTramitacionesIniciadasBoton"]}</em>. ${literales["avisoTramitacionesIniciadasFin"]}</p>
					<p>${literales["avisoTramitacionNuevaInicio"]} <em>${literales["avisoTramitacionNuevaBoton"]}</em>.</p>
				</div>

				<div class="imc--taula">

					<table>
						<thead>
							<tr>
								<th><span>${literales["fechaUltimoAcceso"]}</span></th>
								<th><span>${literales["fechaInicio"]}</span></th>
								<th><span>${literales["fechaLimite"]}</span></th>
								<th><span>${literales["idioma"]}</span></th>
								<th><span>${literales["acciones"]}</span></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="tramite" items="${tramitacionesIniciadas}">
							<tr>
								<td data-etiqueta="Data últim accés"><span><fmt:formatDate pattern = "dd-MM-yyyy HH:mm:ss" value = "${tramite.fechaUltimoAcceso}" /></span></td>
								<td data-etiqueta="Data inici"><span><fmt:formatDate pattern = "dd-MM-yyyy HH:mm:ss" value = "${tramite.fechaInicio}" /></span></td>
								<td data-etiqueta="Data llimit"><span><fmt:formatDate pattern = "dd-MM-yyyy HH:mm:ss" value = "${tramite.fechaCaducidad}" /></span></td>
								<td data-etiqueta="Idioma"><em>${literales[tramite.idioma]}</em></td>
								<td data-etiqueta=""><a class="imc-bt" href="${urlReanudarTramiteNuevo}${tramite.idSesion}"><span>${literales["avisoTramitacionesIniciadasBoton"]}</span></a></td>
							</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>

				<div class="imc--opcions">

					<a class="imc-bt imc--ico imc--nova" href="${urlIniciarTramiteNuevo}"><span>${literales["avisoTramitacionNuevaBoton"]}</span></a>

				</div>


			</div>

		</div>


	</div>

	<!-- /contenidor -->


</body>
</html>
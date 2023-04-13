<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!doctype html>
<html lang="<c:out value="${error.idioma}"/>">
<head>

	<meta charset="utf-8" />
	<meta http-equiv="Expires" content="0">
	<meta http-equiv="Last-Modified" content="0">
	<meta http-equiv="Cache-Control" content="no-cache, mustrevalidate">
	<meta http-equiv="Pragma" content="no-cache">
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title><spring:message code="login.titulo"/></title>

	<!--[if IE]><link rel="shortcut icon" href="imgs/favicon/favicon.ico" type="image/x-icon" /><![endif]-->
	<link rel="apple-touch-icon-precomposed" href="<c:out value="${pageContext.request.contextPath}"/>/base/imgs/favicon/favicon-apple.png" />
	<link rel="icon" href="<c:out value="${pageContext.request.contextPath}"/>/base/imgs/favicon/favicon.png" />

	<link rel="stylesheet" media="screen" href="<c:out value="${pageContext.request.contextPath}"/>/base/css/imc-sf--general.css" />

	<c:if test="${not empty error.url}">
	<script type="text/javascript">

			function redireccion() {
				document.location="${error.url}";
			}
	</script>
	</c:if>
</head>

<body>

		<!-- missatge: error -->
		<div id="imc-missatge" class="imc-missatge" data-tipus="<c:out value="${error.mensaje.estiloError}"/>">
			<h1>
				<span><c:out value="${error.mensaje.titulo}"/></span>
			</h1>
			<div>
				<c:out value="${error.mensaje.texto}"/>
			</div>

			<c:if test="${not empty error.mensaje.debug}">
			<div class="imc--desenvolupadors">
				<h2><span><spring:message code="error.txtCodiDebugDesenv"/></span></h2>
				<p>${error.mensaje.debug}</p>
			</div>
			</c:if>

			<c:if test="${not empty error.url}">
			<div class="imc--botonera">
				<a href="javascript:redireccion();"><span><c:out value="${error.mensaje.textoBoton}"/></span></a>
			</div>
			</c:if>
		</div>

</body>
</html>




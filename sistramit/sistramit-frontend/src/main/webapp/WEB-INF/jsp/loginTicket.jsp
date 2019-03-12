<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!doctype html>
<html lang="<c:out value="${login.idioma}"/>">
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
	<link rel="apple-touch-icon-precomposed" href="base/imgs/favicon/favicon-apple.png" />
	<link rel="icon" href="base/imgs/favicon/favicon.png" />

	<link rel="stylesheet" media="screen" href="base/css/imc-sf--general.css" />

	<script type="text/javascript">
		function loginTicket() {
			document.getElementById("formLogin").submit();
		}
	</script>

</head>

<body onload="loginTicket()">


	<!-- càrrega -->
	<div id="imc-carrega-inicial" class="imc-carrega-inicial">
		<h1>
			<span><spring:message code="login.cargando"/></span>
		</h1>
	</div>

	<form name="formLogin" id="formLogin" method="post" action="login">
		<input type="hidden" name="username" id="username" value="<c:out value="${login.ticketName}"/>"/>
		<input type="hidden" name="password" id="password" value="<c:out value="${login.ticketValue}"/>"/>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>

</body>
</html>



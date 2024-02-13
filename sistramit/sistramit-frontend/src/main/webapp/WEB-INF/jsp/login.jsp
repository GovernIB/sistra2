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

			function redireccion() {

				<c:if test="${login.bloquear}">
					// Redireccion carpeta
					document.location="${login.entidad.urlCarpeta}";
				</c:if>

				<c:if test="${!login.bloquear}">
					<c:if test="${login.loginAnonimoAuto}">
						// Login anonimo automatico
						document.getElementById("username").value = "<%=es.caib.sistramit.core.api.model.security.ConstantesSeguridad.ANONIMO_USER%>";
						document.getElementById("password").value = "<%=es.caib.sistramit.core.api.model.security.ConstantesSeguridad.ANONIMO_USER%>";
						document.getElementById("formLogin").submit();
					</c:if>

					<c:if test="${not login.loginAnonimoAuto}">
						// Redireccion componente autenticacion
						document.location="redirigirAutenticacionLogin.html?entidad=${login.entidad.id}&nivelesAutenticacion=${login.nivelesAutenticacionToString()}&metodosAutenticado=${login.metodosAutenticadoToString()}&qaa=${login.valueQaaString()}&debug=${login.debug}";
					</c:if>
				</c:if>

			}

	</script>

</head>

<body
	<c:if test="${empty param.error}">
		<c:if test="${empty login.avisos}">
			onload="javascript:redireccion();"
		</c:if>
	</c:if>
>


	<!-- càrrega -->
	<c:if test="${ (empty param.error) and (empty login.avisos) }">
		<div id="imc-carrega-inicial" class="imc-carrega-inicial">
			<h1>
				<span><spring:message code="login.cargando"/></span>
			</h1>
		</div>
	</c:if>
	<!-- Revisar gestion error: ¿si sale error no hacer nada mas? -->
	<c:if test="${not empty param.error}">
			<!-- missatge: error -->
			<div id="imc-missatge" class="imc-missatge" data-tipus="error">
				<h1>
					<span><spring:message code="login.error"/></span>
				</h1>
				<div>
					<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
				</div>
				<!--  TODO PENDIENTE SI MOSTRAMOS BOTON Y VER A DONDE SE REDIRECCIONA
				<div class="imc--botonera">
					<a href="index.html"><span>PENDIENTE LITERAL</span></a>
				</div>
				-->
			</div>
	</c:if>

	<!--  Avisos entidad -->
	<c:if test="${empty param.error}">
		<c:if test="${not empty login.avisos}">

			<!-- missatge: info -->
			<div id="imc-missatge" class="imc-missatge" data-tipus="info">
				<h1>
					<span>${login.titulo}</span>
				</h1>
				<div>
					<c:forEach var="aviso" items="${login.avisos}">
						<p>
			    			<c:out value="${aviso.mensaje}"/>
			    		 </p>
					</c:forEach>
				</div>
				<div class="imc--botonera">
					<a href="javascript:redireccion()"><span><spring:message code="login.continuar"/></span></a>
				</div>
			</div>
		</c:if>
	</c:if>

	<!-- Form para envio parametros login -->
	<form name="formLogin" id="formLogin" method="post" action="login">
		<input type="hidden" name="username" id="username" />
		<input type="hidden" name="password" id="password" />
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>

</body>
</html>



<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="<c:out value="${login.idioma}"/>" lang="<c:out value="${login.idioma}"/>">

<head>

	<script type="text/javascript">

			function loginComponenteAutenticacion() {
				document.location="redirigirAutenticacionLogin.html?entidad=${entidad.id}&metodosAutenticacion=${login.nivelesAutenticacionToString()}&qaa=${login.qaa}&debug=${login.debug}";
			}

			function loginAnonimoAuto() {
				document.getElementById("username").value = "<%=es.caib.sistramit.core.api.model.security.ConstantesSeguridad.ANONIMO_USER%>";
				document.getElementById("password").value = "<%=es.caib.sistramit.core.api.model.security.ConstantesSeguridad.ANONIMO_USER%>";
				document.getElementById("formLogin").submit();
			}

	</script>

</head>

<body>

	<h1><c:out value="${login.titulo}"/></h1>

	<p>
		Avisos plataforma <br/>
		<c:if test="${empty login.avisos}">
	    	No hay avisos
	    </c:if>
		<ul>
		<c:forEach var="aviso" items="${login.avisos}">
			<li>
    			<c:out value="${aviso.mensaje}"/> - Bloquear: <c:out value="${aviso.bloquearAcceso}"/>
    		 </li>
		</c:forEach>
		</ul>
	</p>

	<!-- Revisar gestion error: ¿si sale error no hacer nada mas? -->
	<c:if test="${not empty param.error}">
		<fmt:message key="login.error.texto" />
		<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
	</c:if>


	<!-- Deberia lanzarse automaticamente tras revision avisos plataforma -->
	<c:if test="${not login.loginAnonimoAuto}">
	<p>
		<a href="javascript:loginComponenteAutenticacion()"> Redireccion componente autenticacion (deberia lanzarse automaticamente tras revision avisos plataforma) </a>
	</p>
	</c:if>
	<c:if test="${login.loginAnonimoAuto}">
	<p>
		<a href="javascript:loginAnonimoAuto()"> Autenticación anónima automática (deberia lanzarse automaticamente tras revision avisos plataforma) </a>
	</p>
	</c:if>

	<!-- Form para envio parametros login -->
	<form name="formLogin" id="formLogin" method="post" action="login">
		<input type="hidden" name="username" id="username" />
		<input type="hidden" name="password" id="password" />
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>


</body>
</html>
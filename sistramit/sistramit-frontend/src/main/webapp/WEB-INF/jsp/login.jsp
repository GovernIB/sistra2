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

			function loginAnonimo() {
				document.getElementById("username").value = "nobody";
				document.getElementById("password").value = "nobody";
				document.getElementById("formLogin").submit();
			}

			function loginClave() {
				document.location="redirigirClave.html";
			}

	</script>

</head>

<body>

	<h1><c:out value="${login.tituloTramite}"/></h1>

	<c:if test="${not empty param.error}">
		<fmt:message key="login.error.texto" />
		<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
	</c:if>


	<form name="formLogin" id="formLogin" method="post" action="login">
		<input type="hidden" name="username" id="username" />
		<input type="hidden" name="password" id="password" />
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>

	<c:if test="${login.loginAnonimo}">
		<p>
			<a href="javascript:loginAnonimo()"> Login anonimo </a>
		</p>
	</c:if>

	<c:if test="${login.loginAutenticado}">
		<p>
			<a href="javascript:loginClave()"> Login Clave </a>
		</p>
	</c:if>

</body>
</html>
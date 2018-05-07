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
		function loginTicket() {
			document.getElementById("formLogin").submit();
		}
	</script>

</head>

<body onload="loginTicket()">

	Autenticando...

	<form name="formLogin" id="formLogin" method="post" action="login">
		<input type="hidden" name="username" id="username" value="<c:out value="${login.ticketName}"/>"/>
		<input type="hidden" name="password" id="password" value="<c:out value="${login.ticketValue}"/>"/>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>

</body>
</html>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="es">
<head>

	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>

	<title>GOIB</title>

</head>

<body>

	<p>Simular rellenar:</p>

	<form action="simularGuardarFormulario.html" method="post" target="_top">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="hidden" name="idPaso" value="${datos.idPaso}"/>
		<input type="hidden" name="idFormulario" value="${datos.idFormulario}"/>

		<textarea name="xml" rows="40" cols="100"><c:out value="${datos.xml}" escapeXml="true"/></textarea>

		<br/>

		<input type="submit" value="guardar"/>

	</form>

</body>
</html>
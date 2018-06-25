<%@ page isELIgnored="false" %>
<%@page contentType="application/x-javascript" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

(function($) {
	<c:forEach var="entry" items="${literales}">
		<c:out value="${entry.key}"/> = "<c:out value="${entry.value}" escapeXml='false' />";
	</c:forEach>
})(jQuery);
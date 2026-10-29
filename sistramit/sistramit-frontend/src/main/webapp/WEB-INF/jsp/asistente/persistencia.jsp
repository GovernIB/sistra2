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

	<!-- persistencia -->

	<link rel="stylesheet" media="screen" href="css/imc-sf-persistencia.css">

		<!-- js -->

    	<script src="js/utils/jquery-3.7.0.min.js"></script>
    	<script src="js/imc-sf-funcions.js"></script>

    	<script src="js/imc-sf--pag-persistencia.js"></script>

    	<script>

    	    var tokenCSRF = "${_csrf.token}";
            var headerCSRF = "${_csrf.headerName}";

    		var esborra_url = "urlCancelarTramiteDesdePersistencia.json";

    		var txtPreguntaTitol = "${literales["txtPreguntaTitol"]}"
    			,txtPreguntaText = "${literales["txtPreguntaText"]}"
    			,txtEsborrantTitol = "${literales["txtEsborrantTitol"]}"
    			,txtErrorTitol = "${literales["txtErrorTitol"]}"
    			,txtErrorText = "${literales["txtErrorText"]}";

    		var txtCapTitol = "${literales["txtCapTitol"]}"
    			,txtCapInfo = "<p>${literales["txtCapInfo"]}<em>${literales["txtCapInfoBoton"]}</em>.</p>";

    	</script>


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
							<tr data-id="${tramite.idSesion}">
								<td data-etiqueta="Data últim accés"><span><fmt:formatDate pattern = "dd-MM-yyyy HH:mm:ss" value = "${tramite.fechaUltimoAcceso}" /></span></td>
								<td data-etiqueta="Data inici"><span><fmt:formatDate pattern = "dd-MM-yyyy HH:mm:ss" value = "${tramite.fechaInicio}" /></span></td>
								<td data-etiqueta="Data llimit"><span><fmt:formatDate pattern = "dd-MM-yyyy HH:mm:ss" value = "${tramite.fechaCaducidad}" /></span></td>
								<td data-etiqueta="Idioma"><em>${literales[tramite.idioma]}</em></td>
								<td data-etiqueta="">
								    <a class="imc-bt" href="${urlReanudarTramite}${tramite.idSesion}"><span>${literales["avisoTramitacionesIniciadasBoton"]}</span></a>
                                    <button type="button" data-accio="tramit-elimina"><span>${literales["botonElimina"]}</span></button>
                                </td>
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

		<!-- missatge -->

    	<div class="imc-missatge" id="imc-missatge" data-accio="esborra" aria-hidden="true" role="alertdialog" tabindex="0" data-tabpos="0">
    		<div class="imc--c">

    			<div class="imc--text">

    				<h2><span>${literales["txtPreguntaTitol"]}</span></h2>

    				<div class="imc--explicacio">${literales["txtPreguntaText"]}</div>

    				<div class="imc--desenvolupadors" aria-hidden="true">
    					<h3><span>Codi d'error per a desenvolupadors</span></h3>
    					<p></p>
    				</div>

    				<div class="imc--botonera">
    					<button type="button" class="imc-bt imc--cancela" data-tipus="cancela" data-tabula="si" data-tabpos="1"><span>${literales["botonCancela"]}</span></button>
    					<button type="button" class="imc-bt imc--ico imc--principal" data-tipus="accepta" data-tabula="si" data-tabpos="2"><span>${literales["botonAccepta"]}</span></button>
    					<button type="button" class="imc-bt imc--cancela" data-tipus="tanca" data-tabula="si"><span>${literales["botonTanca"]}</span></button>
    				</div>

    			</div>

    		</div>
    	</div>

    	<!-- /missatge -->




</body>
</html>
// Debug

// elements
var eventos,
	imc_minutos_elm,
	imc_recupera_elm,
	imc_log_elm,
	opcions_elm,
	opcions_contingut_elm,
	opcions_h2_elm,
	opcions_llistat_elm;

$(function(){

	// Añadimos header para control CSRF cada vez que se haga un ajax por POST
	$("body").bind("ajaxSend", function(elm, xhr, s){   if (s.type == "POST") {      xhr.setRequestHeader('X-CSRF-Token', tokenCSRF);   }});

	// elements
	imc_minutos_elm = $("#minutos");
	imc_recupera_elm = $("a.bt-recupera:first");
	imc_log_elm = $("#log");

	// pinta
	codi_opcions = "<div id=\"opcions\">";
	codi_opcions += "<div id=\"opcions_contingut\">";
	codi_opcions += "<p class=\"dacord\"><a class=\"bt bt-dacord\" ><span class=\"i\">&nbsp;</span><span class=\"t\">"+textDeAcuerdo+"</span></a></p>";
	codi_opcions += "<h2>&nbsp;</h2>";
	codi_opcions += "<div class=\"llistat\"></div>";
	codi_opcions += "</div>";
	codi_opcions += "</div>";
	$(codi_opcions).appendTo("body");
	opcions_elm = $("#opcions");
	opcions_contingut_elm = $("#opcions_contingut");
	opcions_h2_elm = opcions_elm.find("h2:first");
	opcions_llistat_elm = opcions_elm.find(".llistat:first");
	opcions_elm.on("click.dacord", ".bt-dacord", amagarOpcions);

	// Recuperamos automáticamente
	recuperarEventos();

	// funcions
	function recuperarEventos() {
		// vars
		var enviant = function() {
				imc_log_elm.fadeOut(200, function() {
					imc_log_elm.html("<div class=\"de-carregant\">"+textCargando+"</div>").fadeIn(200, function() {
				 		envia();
					});
				});
			},
			envia = function() {

				if (typeof agafaInfoAjax !== "undefined") {
					agafaInfoAjax.abort();
				}

				var dataVars = { minutos: imc_minutos_elm.val() };

				agafaInfoAjax = $.ajax({
					 type: "POST",
					 url: "recuperarEventosSesionTramitacion.json",
					 data: dataVars,
					 dataType: "json",
					 error: function(dades, tipus, errorThrown) {
						 if (tipus !== "abort") {
						 	 alert("Error ajax: " + dades.status + " - " + dades.statusText);
						 }
					 },
					 success: function(data) {
						if (data.estado !== 'SUCCESS') {
							// $.missatge({ tipus: "alerta", mode: "error", titol: "Error en la respuesta", text: data.mensaje.texto });
							alert(data.mensaje.texto);
						} else {
							imc_log_elm.fadeOut(200, function() {
								pintaLog(data);
							});
						}
					 }
				});
			}
		// enviant
		enviant();
	}

	function pintaLog(data) {
		eventos = data.datos.eventos;

		if (eventos.length === 0) {
			imc_log_elm.html(textSinEventos).fadeIn(200);
			return false;
		}

		var codi_taula = "<div class=\"de-id\">";
		codi_taula += "&nbsp; "+textCabeceraIdTramite+": <strong>" + data.datos.idTramite + "</strong>";
		codi_taula += "&nbsp; "+textCabeceraEntorno+": <strong>" + data.datos.entorno + "</strong>";
		codi_taula += "&nbsp; "+textCabeceraIdSesion+": <strong>" + data.datos.idSesionTramitacion + "</strong>";
		codi_taula += "</div>";


		// codi cap
		codi_taula += "<div class=\"table tOrdenat tPaginat tDebug\" role=\"grid\" aria-live=\"polite\" aria-atomic=\"true\" aria-relevant=\"text additions\">";
		codi_taula += "<div class=\"thead\">";
		codi_taula += "<div class=\"tr\" role=\"rowheader\">";
		codi_taula += "<div class=\"th icona\" role=\"columnheader\"><span class=\"th icona\">"+textCabeceraIcono+"</span></div>";
		codi_taula += "<div class=\"th data\" role=\"columnheader\"><span class=\"th data\">"+textCabeceraLogInterno+"</span></div>";
		codi_taula += "<div class=\"th tipus\" role=\"columnheader\"><span class=\"th tipus\">"+textCabeceraTipoEvento+"</span></div>";
		codi_taula += "<div class=\"th descripcio\" role=\"columnheader\"><span class=\"th descripcio\">"+textCabeceraDescripcion+"</span></div>";
		codi_taula += "<div class=\"th ops\" role=\"columnheader\"><span class=\"th ops\">"+textCabeceraOpciones+"</span></div>";
		codi_taula += "</div>";
		codi_taula += "</div>";
		codi_taula += "<div class=\"tbody\"></div>";
		codi_taula += "</div>";

		imc_log_elm.html(codi_taula);

		var taula_tbody_elm = imc_log_elm.find("div.tbody:first");

		$(eventos).each(function(i) {
			var es_node = this,
				es_descripcio = escapeHTML(es_node.descripcion),
				es_error = (es_node.trazaError !== null && es_node.trazaError !== "" && es_node.trazaError !== undefined) ? true : false,
				es_data = millisToDate(es_node.fecha),
				es_tipus = es_node.tipoEvento,
				class_fila_error = (es_error) ? " error" : "",
				codi_fila = "<div class=\"tr" + class_fila_error + "\" role=\"row\">",
				class_ico = (es_error) ? " ico-error" : (es_tipus.indexOf("TR_SCR") !== -1) ? " ico-debug" : " ico-evento";
				codi_opcions = "&nbsp;";

			var es_tipus_string;
			if (textoTipo[es_tipus] == undefined || textoTipo[es_tipus] == '') {
				es_tipus_string = es_tipus;
			} else {
				es_tipus_string = textoTipo[es_tipus];
			}
			es_descripcio = (es_error) ? "<span class=\"error\">Error: " + es_node.excepcionError + "</span>.<br />" + es_descripcio : es_descripcio;

			codi_opcions += (es_node.propiedadesEvento !== null) ? "<a class=\"bt bt-propietats\"  title=\"\"><span class=\"i\">&nbsp;</span></a>" : "";
			codi_opcions += (es_node.trazaError !== null && es_node.trazaError.length > 0) ? "<a class=\"bt bt-traza\"  title=\"\"><span class=\"i\">&nbsp;</span></a>" : "";

			codi_fila += "<div class=\"td icona" + class_ico + "\" role=\"gridcell\">&nbsp;</div>";
			codi_fila += "<div class=\"td data\" role=\"gridcell\"><p>" + es_data + "</p></div>";
			codi_fila += "<div class=\"td tipus\" role=\"gridcell\"><p>" + es_tipus_string + "</p></div>";
			codi_fila += "<div class=\"td descripcio\" role=\"gridcell\"><p>" + es_descripcio + "</p></div>";
			codi_fila += "<div class=\"td ops\" role=\"gridcell\"><p>" + codi_opcions + "</p></div>";

			codi_fila += "</div>";
			taula_tbody_elm.append(codi_fila);
			taula_tbody_elm.find("div.tr:last").data("fila", i);

		});

		imc_log_elm
			.find("*[title]").title().end()
			.fadeIn(200);

		imc_log_elm.on("click.opcions", ".bt-propietats, .bt-traza", function(e) {
			pintarOpcions({ boto: $(this) });
		});

	}

	function millisToDate(millis) {
		var fecha = new Date(millis);
		var str = pad(fecha.getHours(),2) + ":" + pad(fecha.getMinutes(),2) + ":" + pad(fecha.getSeconds(),2) + "," + fecha.getMilliseconds();
		return str;
	}

	function pad(number, length) {
		var str = '' + number;
		while (str.length < length) {
			 str = '0' + str;
		}
		return str;
	}

	function pintarOpcions(options) {

		var settings = $.extend({
				boto: "",
				fila: 0,
				contenidor: $("body"),
				tipus: "props" // props || traza
			}, options),
			boto = settings.boto,
			fila = parseInt(boto.closest("div.tr").data("fila"),10),
			contenidor = settings.contenidor,
			tipus = (boto.hasClass("bt-traza")) ? "traza" : settings.tipus,
			titol = (tipus === "traza") ? textTrazaError : textPropiedades,
			finestra_W,
			finestra_H,
			opcions_H,
			opcions_W;

		var llansa = function() {

			if (fila === opcions_elm.data("fila") && tipus === opcions_elm.data("tipus")) {
				return false;
			}

			if (opcions_elm.css("display") !== "none") {
				opcions_elm.fadeOut(200, function() { pinta(); });
			} else {
				pinta();
			}

		},
		pinta = function() {

			var html_llistat;

			if (tipus === "traza") {
				html_llistat = "<p>" + escapeHTML(eventos[fila].trazaError) + "</p>";
			} else {
				html_llistat = "<table><tbody>";
				$.each(eventos[fila].propiedadesEvento, function(i, n){
					$.each(n, function(key, valor){
						html_llistat += "<tr><th>" +  escapeHTML(key) + " =</th><td>" + escapeHTML(valor)  + "<td></tr>";
					});
				});
				html_llistat += "</tbody></table>";
			}

			opcions_h2_elm.attr("class",tipus).text(titol);
			opcions_llistat_elm.html(html_llistat)
			opcions_elm.data("fila", fila).data("tipus", tipus);

			coloca();

		},
		mides = function() {
			finestra_W = $(window).width();
			finestra_H = $(window).height();
			opcions_H = finestra_H - 100;
			opcions_W = opcions_elm.outerWidth();
		},
		coloca = function() {

			mides();
			opcions_elm.css({ left: (finestra_W-opcions_W)/2 + "px", height: opcions_H + "px" })
			mostra();

		},
		posiciona = function() {

			mides();
			opcions_elm.animate({ left: (finestra_W-opcions_W)/2 + "px", height: opcions_H + "px" }, 200);
			var h2_H = opcions_h2_elm.outerHeight(true);
			opcions_llistat_elm.css({ height: (opcions_H - h2_H - 50) + "px" });

		},
		mostra = function() {

			opcions_elm.fadeIn(200, function() {
				var opcions_H = opcions_elm.height(),
					h2_H = opcions_h2_elm.outerHeight(true);
				opcions_llistat_elm.css({ height: (opcions_H - h2_H - 50) + "px" });
				$(window).on("resize.debug", posiciona);
			});

		};

		llansa();

	}

	function amagarOpcions() {
		opcions_elm.fadeOut(200).data("fila", "");
		$(window).off("resize.debug");
	}

	function escapeHTML( string )
	{
	    return jQuery( '<pre>' ).text( string ).html();
	}


	// inicia
	imc_recupera_elm.bind("click", recuperarEventos);

});


//title
$.fn.title = function(options) {
	var settings = $.extend({
		temps: 100,
		contenidor: $("#contenidor")
	}, options);
	this.each(function(){
		var element = $(this),
		title_text = element.attr("title"),
		onEnter = function() {
			if ($("#title").size() == 1) {
				element.attr("title", $("#title").text());
				$("#title").remove();
			}
			var title_text = element.attr("title"),
				contenidor_H = settings.contenidor.outerHeight(),
				contenidor_W = settings.contenidor.outerWidth(),
				element_T = element.offset().top,
				element_L = element.offset().left,
				element_H = element.outerHeight(),
				element_T = element_T + element_H;
			$("body").append("<div id=\"title\" style=\"top: " + element_T + "px; left: " + element_L + "px;\">" + title_text + "</div>");
			element.attr("title", "");
			var title_H = $("#title").outerHeight(),
				title_W = $("#title").outerWidth();
			$("#title").data("element", element);
			if ((element_T + title_H) > (contenidor_H - 30)) {
				$("#title").css({ "top": (element_T - title_H - element_H) + "px" });
			}
			if ((element_L + title_W) > contenidor_W) {
				$("#title").css({ "left": (contenidor_W - title_W - 15) + "px" });
			}
		},
		onLeave = function() {
			var element_title = $("#title").data("element");
			element_title.attr("title", $("#title").text());
			$("#title").remove();
		};
		element.unbind('.title').bind('mouseenter.title', onEnter).bind('mouseleave.title', onLeave);
	});
	return this;
};
// /title
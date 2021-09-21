// JS


// constants

var APP_IDIOMA = "ca";

var APP_JSON_TRAMIT
	,APP_JSON_TRAMIT_D
	,APP_JSON_TRAMIT_T
	,APP_JSON_TRAMIT_U
	,APP_JSON_TRAMIT_E
	,APP_TRAMIT_INFO
	,APP_TRAMIT_PAS_ID
	,APP_TRAMIT_FLUIX
	,APP_USUARI_ID;

var APP_JSON_TRAMIT_MISSATGE = false;

var HTML_MOLLA_PA;

var imc_finestra
	,imc_html
	,imc_head
	,imc_body
	,imc_carrega_inicial
	,imc_contenidor
	,imc_cap_fixe
	,imc_cap
	,imc_cap_c
	,imc_tramitacio
	,imc_molla_pa
	,imc_contingut
	,imc_contingut_c
	,imc_contacte
	,imc_missatge;

var imc_bt_desconecta
	,imc_bt_desa
	,imc_bt_elimina
	,imc_bt_equip_suport;


// onReady

$(function(){

	APP_IDIOMA = $("html").attr("lang");

	imc_finestra = $(window);
	imc_html = $("html");
	imc_head = $("head");
	imc_body = $("body");
	imc_carrega_inicial = $("#imc-carrega-inicial");
	imc_contenidor = $("#imc-contenidor");
	imc_contingut = $("#imc-contingut");
	imc_contingut_c = imc_contingut.find(".imc--c:first");

	// revisa URL

	var url_string = document.location.toString(),
		hiHaAmpersan = (url_string.indexOf("#") === -1) ? false : true;

	if (hiHaAmpersan) {

		document.location = "";

	}

	// estils?

	var estils = (localStorage.getItem("goib_sistra2tramit_estils")) ? localStorage.getItem("goib_sistra2tramit_estils") : "pd";

	imc_html
		.attr("data-estil", estils);

	// carrega info tràmit

	imc_body
		.carregaInfoTramit();

});


// carrega infoTramit

$.fn.carregaInfoTramit = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			bt_intenta = element.find("button:first"),
			envia_ajax = false,
			inicia = function() {

				bt_intenta
					.off('.carregaInfoTramit')
					.on('click.carregaInfoTramit', intenta);

				envia();

			},
			envia = function() {

				var pag_url = APP_TRAMIT_INFO,
					pag_dades = { id: "" };
				
				envia_ajax =
					$.ajax({
						url: pag_url,
						data: pag_dades,
						method: "post",
						dataType: "json",
						timeout: APP_TIMEOUT,
						beforeSend: function(xhr) {
							xhr.setRequestHeader(headerCSRF, tokenCSRF);
						}
					})
					.done(function( data ) {
						
						APP_JSON_TRAMIT = data;
						APP_JSON_TRAMIT_D = APP_JSON_TRAMIT.datos;
						APP_JSON_TRAMIT_T = APP_JSON_TRAMIT_D.tramite;
						APP_JSON_TRAMIT_U = APP_JSON_TRAMIT_D.usuario;
						APP_JSON_TRAMIT_E = APP_JSON_TRAMIT_D.entidad;

						if (APP_JSON_TRAMIT.estado === "SUCCESS" || APP_JSON_TRAMIT.estado === "WARNING") {

							// missatge

							var json_missatge = APP_JSON_TRAMIT.mensaje
								,json_missatge_titol = json_missatge.titulo
								,json_missatge_text = json_missatge.texto;

							if (json_missatge_titol !== "") {

								APP_JSON_TRAMIT_MISSATGE = json_missatge;

							}

							// literals
							
							carregaLiterals();

						} else {

							error();

						}
						
					})
					.fail(function(fail_dades, fail_tipus, errorThrown) {
						
						error();
						
					});

			},
			intenta = function() {

				imc_carrega_inicial
					.removeClass("imc--error");

				envia();

			},
			error = function(missatge) {

				envia_ajax = false;

				imc_carrega_inicial
					.addClass("imc--error");

			};
		
		// inicia
		inicia();
		
	});
	return this;
}


// carrega literals

function carregaLiterals() {

	$.when(
		
		$.getScript(APP_LITERALS + "?idioma="+APP_IDIOMA)

	).then(

		function() {
			
			carregaHTML();

		}

	).fail(

		function() {

			var error_txt = "Error carrega literals";

			consola( error_txt );

			imc_carrega_inicial
				.addClass("imc--error");

		}

	);

}


// carrega HTML

function carregaHTML() {

	// html

	$.when(
		
		$.get(APP_ + "css/imc-sf-botonera.css?" + APP_VERSIO)
		,$.get(APP_ + "css/imc-sf-base.css?" + APP_VERSIO)
		,$.get(APP_ + "html/imc-cap.html")
		,$.get(APP_ + "html/imc-contacte.html")
		,$.get(APP_ + "html/imc-missatge.html")
		//,$.getScript(APP_ + "js/utils/modernizr-imc-0.3.js?" + APP_VERSIO)
		,$.getScript(APP_ + "js/utils/modernizr-custom.js?" + APP_VERSIO)
		,$.getScript(APP_ + "js/utils/markup.min.js?" + APP_VERSIO)
		,$.getScript(APP_ + "js/imc-sf-pas.js?" + APP_VERSIO)
		,$.getScript(APP_ + "js/imc-sf-funcions.js?" + APP_VERSIO)
		,$.getScript(APP_ + "js/imc-sf-inicia.js?" + APP_VERSIO)

	).then(

		function( cssBotonera, cssBase, htmlCap, htmlContacte, htmlMissatge) {

			// MarkUp retorna només text

			Mark.pipes.nomesText = function (str, n) {

				return ($(str).length) ? $(str).text() : str;

			};

			// estils

			$("<style>")
				.html( cssBotonera[0] )
					.appendTo( imc_head );

			$("<style>")
				.html( cssBase[0] )
					.appendTo( imc_head );

			// html

			APP_TRAMIT_FLUIX = APP_JSON_TRAMIT_T.tipoFlujo;

			imc_body
				.attr("data-fluix", APP_TRAMIT_FLUIX);

			// cap

			var jsonAutenticacio = APP_JSON_TRAMIT_T.autenticacion,
				jsonUsuari = txtSenseAutenticacio;

			if (jsonAutenticacio === "c") {

				var usuari_cognom1 = APP_JSON_TRAMIT_U.apellido1
					,usuari_cognom2 = APP_JSON_TRAMIT_U.apellido2
					,usuari_nom = APP_JSON_TRAMIT_U.nombre;

				if (!$([usuari_cognom1]).estaBuit() && !$([usuari_cognom2]).estaBuit() && !$([usuari_nom]).estaBuit()) {

					jsonUsuari = APP_JSON_TRAMIT_U.apellido1 + " " + APP_JSON_TRAMIT_U.apellido2 + ", " + APP_JSON_TRAMIT_U.nombre;

				} else if (!$([usuari_cognom1]).estaBuit() && $([usuari_cognom2]).estaBuit() && !$([usuari_nom]).estaBuit()) {

					jsonUsuari = APP_JSON_TRAMIT_U.apellido1 + ", " + APP_JSON_TRAMIT_U.nombre;

				} else if ($([usuari_cognom1]).estaBuit() && $([usuari_cognom2]).estaBuit() && !$([usuari_nom]).estaBuit()) {

					jsonUsuari = APP_JSON_TRAMIT_U.nombre;

				}

			}

			APP_USUARI_ID = ( jsonAutenticacio === "c") ? APP_JSON_TRAMIT_U.nif : false;

			var jsonTramitTitol = APP_JSON_TRAMIT_T.titulo;

			var txtHTML_Cap = {
					txtGovern: txtGovern
					,txtAplicacioTitol: txtAplicacioTitol
					,txtUsuari: txtUsuari
					,txtDesconecta: txtDesconecta
					,txtClauTramitacio: txtClauTramitacio
					,txtDesauClau: txtDesauClau
					,txtAccessibilitat: txtAccessibilitat
					,txtEliminau: txtEliminau
					,jsonAutenticacio: jsonAutenticacio
					,jsonUsuari: jsonUsuari
					,jsonClauTramitacio: APP_JSON_TRAMIT_T.idSesion
					,jsonTramitTitol: jsonTramitTitol
					,txtMollaPa: txtMollaPa
					,txtCalSaber: txtCalSaber
					,txtEmplenar: txtEmplenar
					,txtAnnexar: txtAnnexar
					,txtPagar: txtPagar
					,txtRegistrar: txtRegistrar
				};

			var html_cap = Mark.up(htmlCap[0], txtHTML_Cap);

			imc_contenidor
				.prepend( html_cap );

			// contacte

			//APP_JSON_TRAMIT_E

			var jsonMapaWeb = APP_JSON_TRAMIT_E.urlMapaWeb
				,jsonAvisLegal = APP_JSON_TRAMIT_E.urlAvisoLegal
				,jsonRSS = APP_JSON_TRAMIT_E.urlRss;

			var jsonXarxes = APP_JSON_TRAMIT_E.redes
				,jsonYouTube = APP_JSON_TRAMIT_E.redes.youtube
				,jsonInstagram = APP_JSON_TRAMIT_E.redes.instagram
				,jsonTwitter = APP_JSON_TRAMIT_E.redes.twiter
				,jsonFacebook = APP_JSON_TRAMIT_E.redes.facebook;

			var txtHTML_Contacte = {
					txtNecessitauAjuda: txtNecessitauAjuda
					,txtTelefon: txtTelefon
					,txtContactauAmb: txtContactauAmb
					,txtEquipSuport: txtEquipSuport
					,txtAjuda: txtAjuda
					,txtAjudaIntro: txtAjudaIntro
					,txtEmplenarSeguent: txtEmplenarSeguent
					,txtFormIncidencias: txtFormIncidencias
					,txtVisitePag: txtVisitePag
					,txtSuportTecnic: txtSuportTecnic
					,txtEnvieuCorreu: txtEnvieuCorreu
					,txtTrucarTelf: txtTrucarTelf
					,txtTanca: txtTanca
					,jsonTelefon: APP_JSON_TRAMIT_E.soporte.telefono
					,jsonSuportTecnic: APP_JSON_TRAMIT_E.soporte.url
					,jsonSuportCorreu: APP_JSON_TRAMIT_E.soporte.correo
					,txtEliminau: txtEliminau
					,txtFormContacte: txtFormContacte
					,txtNIF: txtNIF
					,txtNom: txtNom
					,txtTelefon: txtTelefon
					,txtCorreu: txtCorreu
					,txtHorariContacte: txtHorariContacte
					,txtTipusProblema: txtTipusProblema
					,txtDescripcioProblema: txtDescripcioProblema
					,txtDocumentAnnex: txtDocumentAnnex
					,txtSeleccionaFitxer: txtSeleccionaFitxer
					,txtEnviaConsulta: txtEnviaConsulta
					,txtTorna: txtTorna
					,jsonTipusProblema: APP_JSON_TRAMIT_E.soporte.problemas
					,txtEnviantDades: txtEnviantDades
					,txtCancelaEnviament: txtCancelaEnviament
					,txtTornaIntentar: txtTornaIntentar
					,txtReinicia: txtReinicia
					,jsonContacte: APP_JSON_TRAMIT_E.contacto
					,txtMapaWeb: txtMapaWeb
					,txtAvisLegal: txtAvisLegal
					,txtSegueixnos: txtSegueixnos
					,jsonMapaWeb: jsonMapaWeb
					,jsonAvisLegal: jsonAvisLegal
					,jsonRSS: jsonRSS
					,jsonXarxes: jsonXarxes
					,jsonYouTube: jsonYouTube
					,jsonInstagram: jsonInstagram
					,jsonTwitter: jsonTwitter
					,jsonFacebook: jsonFacebook
				};

			var html_contacte = Mark.up(htmlContacte[0], txtHTML_Contacte);

			imc_contenidor
				.append( html_contacte );

			// missatge

			var txtHTML_Missatge = {
					txtDesarSortir: txtDesarSortir
					,txtSortirSenseDesar: txtSortirSenseDesar
					,txtAccepta: txtAccepta
					,txtCancela: txtCancela
					,txtTanca: txtTanca
				};

			var html_missatge = Mark.up(htmlMissatge[0], txtHTML_Missatge);

			imc_body
				.append( html_missatge );

			// entitat - logo

			var app_logo = APP_JSON_TRAMIT_E.logo;

			if (app_logo !== "") {

				imc_contenidor
					.find(".imc--logo:first")
						.css({ "backgroundImage": "url("+app_logo+")" });

			}

			// entitat - css

			var app_css = APP_JSON_TRAMIT_E.css;

			if (app_css !== "") {

				$("<link>")
					.attr({ rel: "stylesheet", type: "text/css", href: APP_JSON_TRAMIT_E.css })
						.appendTo( imc_head );

			}

			// inicia html

			iniciaHTML();

		}

	).fail(

		function() {

			var error_txt = "Error carrega arxius HTML i CSS";

			consola( error_txt );

			imc_carrega_inicial
				.addClass("imc--error");

		}

	);

}
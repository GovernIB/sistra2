// JS


// constants

var APP_IDIOMA = "ca";

var APP_JSON_TRAMIT
	,APP_JSON_TRAMIT_D
	,APP_JSON_TRAMIT_T
	,APP_JSON_TRAMIT_U
	,APP_JSON_TRAMIT_E
	,APP_URL_TRAMIT
	,APP_URL_PAS;

var HTML_MOLLA_PA;

var imc_finestra
	,imc_head
	,imc_body
	,imc_carrega_inicial
	,imc_contenidor
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

				var pag_url = APP_SERVIDOR_JSON + APP_URL_TRAMIT + APP_SERVIDOR_EXT,
					pag_data = { id: "" };

				envia_ajax =
					$.ajax({
						url: pag_url,
						data: pag_data,
						beforeSend: function(xhr) {
				            xhr.setRequestHeader(headerCSRF, tokenCSRF);
				        },
						method: "post",
						dataType: "json"
					})
					.done(function( data ) {

						APP_JSON_TRAMIT = data;
						APP_JSON_TRAMIT_D = APP_JSON_TRAMIT.datos;
						APP_JSON_TRAMIT_T = APP_JSON_TRAMIT_D.tramite;
						APP_JSON_TRAMIT_U = APP_JSON_TRAMIT_D.usuario;
						APP_JSON_TRAMIT_E = APP_JSON_TRAMIT_D.entidad;

						if (APP_JSON_TRAMIT.estado === "SUCCESS" || APP_JSON_TRAMIT.estado === "WARNING") {

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

			imc_contenidor_c
				.html( error_txt );

		}

	);

}


// carrega HTML

function carregaHTML() {

	// html

	$.when(

		$.get(APP_SERVIDOR + "css/imc-sf-botonera.css")
		,$.get(APP_SERVIDOR + "css/imc-sf-base.css")
		,$.get(APP_SERVIDOR + "html/imc-cap.html")
		,$.get(APP_SERVIDOR + "html/imc-contacte.html")
		,$.get(APP_SERVIDOR + "html/imc-missatge.html")
		//,$.getScript(APP_LITERALS + "?idioma="+APP_IDIOMA)
		,$.getScript(APP_SERVIDOR + "js/utils/modernizr-imc-0.3.js")
		,$.getScript(APP_SERVIDOR + "js/utils/markup.min.js")
		,$.getScript(APP_SERVIDOR + "js/imc-sf-pas.js")
		,$.getScript(APP_SERVIDOR + "js/imc-sf-funcions.js")
		,$.getScript(APP_SERVIDOR + "js/imc-sf-inicia.js")

	).then(

		function( cssBotonera, cssBase, htmlCap, htmlContacte, htmlMissatge) {

			// estils

			$("<style>")
				.html( cssBotonera[0] )
					.appendTo( imc_head );

			$("<style>")
				.html( cssBase[0] )
					.appendTo( imc_head );

			// html

			// cap

			var txtHTML_Cap = {
				txtGovern: txtGovern
				,txtAplicacioTitol: txtAplicacioTitol
				,txtUsuari: txtUsuari
				,txtDesconecta: txtDesconecta
				,txtClauTramitacio : txtClauTramitacio
				,txtDesauClau : txtDesauClau
				,txtEliminau: txtEliminau
				,jsonUsuari: APP_JSON_TRAMIT_U.apellido1 + " " + APP_JSON_TRAMIT_U.apellido2 + ", " + APP_JSON_TRAMIT_U.nombre
				,jsonClauTramitacio: APP_JSON_TRAMIT_T.idSesion
				,jsonTramitTitol: APP_JSON_TRAMIT_T.titulo
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
				,jsonContacte: APP_JSON_TRAMIT_E.contacto
			};

			var html_contacte = Mark.up(htmlContacte[0], txtHTML_Contacte);

			imc_contenidor
				.append( html_contacte );

			// missatge

			var txtHTML_Missatge = {
				txtAccepta: txtAccepta
				,txtCancela: txtCancela
			};

			var html_missatge = Mark.up(htmlMissatge[0], txtHTML_Missatge);

			imc_body
				.append( html_missatge );

			// inicia html

			iniciaHTML();

		}

	).fail(

		function() {

			var error_txt = "Error carrega arxius HTML i CSS";

			imc_contenidor_c
				.html( error_txt );

		}

	);

}
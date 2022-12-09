// index

var imc_forms_head = $("head")
	,imc_forms_body = $("body");

var imc_forms_contenidor
	,imc_forms_formulari_c
	,imc_forms_finestra
	,imc_forms_contingut
	,imc_forms_ajuda;

var FORMS_JS = false;

var APP_FORMS_ = ""
	,FORMS_CONTENIDOR = false
	,APP_FORMS_VERSIO = "0.1";


// consola

function consola(text) {
	if (typeof console !== "undefined") {
		console.log(text);
	}
}


// errors generals

$.fn.appFormsErrorsGeneral = function(options) {

	var settings = $.extend({
			estat: false,
			titol: txtFormDinErrorGeneralTitol,
			text: txtFormDinErrorGeneralText,
			debug: false,
			url: APP_FORMS_
		}, options);

	this.each(function(){
		var element = $(this),
			estat = settings.estat,
			titol = settings.titol,
			text = settings.text,
			debug = settings.debug,
			url = settings.url,
			mostra = function() {

				if (estat === "fail" || estat === "FATAL") {

					imc_forms_contenidor
						.remove();

				}

				imc_forms_missatge
					.appFormsMissatge({ accio: "error", titol: titol, text: text, debug: debug, alTancar: function() { document.location = url; } });

			};
		
		// mostra
		mostra();
		
	});

	return this;
}



// forms inicia

function appFormsInicia() {

	// configuracio

	imc_forms_contenidor
		.appFormsConfiguracio({ forms_json: FORMS_JSON, desDe: "inicia" });

}



// appCarregaScripts

function appFormsCarregaScripts() {

	// elements

	imc_forms_contenidor = $("#imc-forms-contenidor");
	imc_forms_formulari_c = imc_forms_contenidor.find(".imc--c:first");
	imc_forms_finestra = imc_forms_contenidor.find(".imc--finestra:first");
	imc_forms_contingut = imc_forms_contenidor.find(".imc--contingut:first");

	imc_forms_ajuda = $("#imc-forms-ajuda");

	if (FORMS_JS) {

		return;

	}

	// carrega de JS

	$.when(

		$.get(APP_FORMS_ + "forms/css/imc-forms--destaca.css?" + APP_FORMS_VERSIO)
		,$.get(APP_FORMS_ + "forms/css/imc-forms--select.css?" + APP_FORMS_VERSIO)
		,$.get(APP_FORMS_ + "forms/css/imc-forms--taula-iframe.css?" + APP_FORMS_VERSIO)
		,$.get(APP_FORMS_ + "forms/css/imc-forms--missatge.css?" + APP_FORMS_VERSIO)
		,$.get(APP_FORMS_ + "forms/css/imc-forms.css?" + APP_FORMS_VERSIO)
		,$.getScript(APP_FORMS_ + "forms/js/numeral.min.js?" + APP_FORMS_VERSIO)
		,$.getScript(APP_FORMS_ + "forms/js/imc-forms--comuns.js?" + APP_FORMS_VERSIO)
		,$.getScript(APP_FORMS_ + "forms/js/imc-forms--funcions.js?" + APP_FORMS_VERSIO)
		,$.getScript(APP_FORMS_ + "forms/js/imc-forms--validacions.js?" + APP_FORMS_VERSIO)
		,$.getScript(APP_FORMS_ + "forms/js/imc-forms--captcha.js?" + APP_FORMS_VERSIO)
		,$.getScript(APP_FORMS_ + "forms/js/imc-forms--llistaElements.js?" + APP_FORMS_VERSIO)
		,$.getScript(APP_FORMS_ + "forms/js/imc-forms--serialitza.js?" + APP_FORMS_VERSIO)
		,$.getScript(APP_FORMS_ + "forms/js/imc-forms--missatge.js?" + APP_FORMS_VERSIO)
		,$.getScript(APP_FORMS_ + "forms/js/imc-forms--moduls.js?" + APP_FORMS_VERSIO)

	).then(

		function( cssFormsDestaca, cssFormsSelect, cssFormsTaulaIframe, cssFormsMissatge, cssForms) {

			$.getScript(APP_FORMS_ + "forms/js/numeral.min_es-es.js?" + APP_FORMS_VERSIO);

			// estils

			$("<style>")
				.html( cssFormsDestaca[0] )
					.appendTo( imc_forms_head );

			$("<style>")
				.html( cssFormsSelect[0] )
					.appendTo( imc_forms_head );

			$("<style>")
				.html( cssFormsTaulaIframe[0] )
					.appendTo( imc_forms_head );

			$("<style>")
				.html( cssFormsMissatge[0] )
					.appendTo( imc_forms_head );

			$("<style>")
				.html( cssForms[0] )
					.appendTo( imc_forms_head );

			FORMS_JS = true;

		}

	).fail(

		function() {

			consola("Error carrega arxius JS i CSS de FORMS");

			imc_forms_body
				.appFormsErrorsGeneral({ estat: "fail" });

		}

	);

}


// càrrega inicial

$.when(
	
	$.get(APP_FORMS_ + "forms/html/imc--forms-contenidor.html?" + APP_VERSIO)

).then(

	function( htmlForms ) {

		// pintem contenidor de FORMS

		if (!FORMS_CONTENIDOR) {

			imc_forms_body
				.append( htmlForms );

		} else {

			FORMS_CONTENIDOR
				.append( htmlForms );

			FORMS_CONTENIDOR
				.find(".imc-forms-contenidor:first")
					.addClass("imc-cd");

		}

		// literals

		var ajuda_marc = $("#imc-forms-contenidor .imc--ajuda:first");

		ajuda_marc
			.find("strong:first")
				.text( txtFormDinAjudaActivada )
				.end()
			.find("button:first span")
				.text( txtFormDinDesactivar );

		$("#imc--nav-seccions")
			.find("button:first span")
				.text( txtFormsDinSeccionsForm );

		// carrega CSS i JS

		appFormsCarregaScripts();
		
		// carrega formulari

		FORMS_ARXIUS = true;

	}

).fail(

	function() {

		consola("Formulari: error des de l'inicia de FORMS (FAIL)");

	}

);


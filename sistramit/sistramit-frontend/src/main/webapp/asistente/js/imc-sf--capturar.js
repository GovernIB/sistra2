// Capturar


// elements i globals

var imc_form_captura;

var FORMS_JSON = false
	,FORMS_ARXIUS = false;



// onReady

function appPasCapturarInicia() {
	
	imc_form_captura = imc_contingut.find(".imc--form-captura:first");

	var form_captura_id = JSON_PAS_ACTUAL.datos.actual.formulario.id;

	document
		.location = "#pas/"+APP_TRAMIT_PAS_ID+"/formulari/"+form_captura_id; // APP_FORM_XML + "?idFormulario=" + form_captura_id + "&idPaso=" + APP_TRAMIT_PAS_ID;

	if (FORMS_ARXIUS) {
		return;
	}

	// carrega JS inicial de FORMS

	$.when(
		
		$.getScript(APP_ + "forms/js/imc-forms--inicia.js?" + APP_VERSIO)

	).then(

		function() {

			FORMS_ARXIUS = true;

		}

	).fail(

		function() {

			consola("Formulari captura: error des de l'inicia de FORMS (FAIL)");

			imc_contenidor
				.errors({ estat: "fail" });

		}

	);

}



// appEmplenaFormulariCaptura

$.fn.appEmplenaFormulariCaptura = function(options) {
	var settings = $.extend({
		element: "",
		form_id: false
	}, options);
	this.each(function(){
		var element = $(this),
			form_id = settings.form_id,
			form_nom = false,
			envia_ajax = false,
			form_ticket = false,
			carregant = function() {

				// dades ajax

				var pag_url = APP_FORM_URL,
					pag_dades = { idFormulario: form_id, idPaso: APP_TRAMIT_PAS_ID };

				// ajax

				if (envia_ajax) {

					envia_ajax
						.abort();

				}
				
				envia_ajax =
					$.ajax({
						url: pag_url,
						data: pag_dades,
						method: "post",
						dataType: "json",
						timeout: APP_TIMEOUT
					})
					.done(function( data ) {

						if (data.estado === "SUCCESS" || data.estado === "WARNING") {

							var continua = function() {

									var form_tipus = data.datos.tipo;

									form_ticket = data.datos.ticket;

									if (form_tipus === "i") {

										carregaForm();

									} else {

										document.location = data.datos.url;

									}

								};

							if (data.estado === "WARNING") {

								imc_missatge
									.appMissatge({ accio: "warning", titol: data.mensaje.titulo, text: data.mensaje.texto, alAcceptar: function() { continua(); } });

								return;

							}

							continua();

						} else {

							envia_ajax = false;

							consola("Formulari captura (carrega ticket): error des de JSON");
							
							imc_contenidor
								.errors({ estat: data.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, debug: data.mensaje.debug, url: data.url });

						}
						
					})
					.fail(function(dades, tipus, errorThrown) {

						if (tipus === "abort") {
							return false;
						}
						
						consola("Formulari captura (carrega ticket): error fail");
						
						imc_contenidor
							.errors({ estat: "fail" });
						
					});

			},
			carregaForm = function() {

				$.when(
					
					$.getJSON( APP_FORM_CARREGA + "?ticket=" + form_ticket )

				).then(

					function( jsonForm ) {

						FORMS_JSON = jsonForm;

						if (FORMS_JSON.estado === "SUCCESS" || FORMS_JSON.estado === "WARNING") {
							
							// carregat

							carregaFormArxius();

							if (FORMS_JSON.estado === "WARNING") {

								imc_missatge
									.appMissatge({ accio: "warning", titol: FORMS_JSON.mensaje.titulo, text: FORMS_JSON.mensaje.texto });

							}

						} else {

							consola("Formulari captura (carrega dades): error des de JSON");
							
							imc_contenidor
								.errors({ estat: FORMS_JSON.estado, titol: FORMS_JSON.mensaje.titulo, text: FORMS_JSON.mensaje.texto, debug: FORMS_JSON.mensaje.debug, url: FORMS_JSON.url });

						}

					}

				).fail(

					function(jqXHR, textStatus, errorThrown) {

						consola("Formulari captura (carrega dades): error fail");

						//imc_contenidor
						//	.errors({ estat: "fail" });

					}

				);


			},
			carregaFormArxius = function() {

				if (FORMS_ARXIUS) {

					carregat();
					return;

				}

				// carrega JS inicial de FORMS

				$.when(
					
					$.get(APP_ + "forms/html/imc--forms-contenidor.html?" + APP_VERSIO)
					,$.getScript(APP_ + "forms/js/imc-forms--inicia.js?" + APP_VERSIO)

				).then(

					function( htmlForms ) {

						imc_form_captura
							.append( htmlForms[0] );
						
						// carrega formulari

						FORMS_ARXIUS = true;

						carregat();

					}

				).fail(

					function() {

						consola("Formulari: error des de l'inicia de carregaFormArxius (FAIL)");

						imc_contenidor
							.errors({ estat: "fail" });

					}

				);

			},
			carregat = function() {

				// FORMS afegim el html del form

				$("#imc-forms-contenidor")
					.find(".imc--contingut:first")
						.html( $(FORMS_JSON.datos.html).html() );

				// FORMS iniciem

				appFormsInicia();

				// continua

				if (!form_ticket) {
					return;
				}

				mostra();

			},
			mostra = function() {

				$("#imc-forms-contenidor")
					.attr("aria-hidden", "false");

				imc_missatge
					.appMissatge({ araAmaga: true });
				
				envia_ajax = false;

			},
			error = function(opcions) {

				// missatge error

				var settings_opcions = $.extend({
						titol: txtTramitEliminaErrorTitol,
						text: txtTramitEliminaErrorText
					}, opcions);

				var titol = settings_opcions.titol,
					text = settings_opcions.text;

				imc_missatge
					.appMissatge({ accio: "error", titol: titol, text: text });

				tanca();

				envia_ajax = false;

			};
		
		// obri
		carregant();
		
	});
	return this;
}

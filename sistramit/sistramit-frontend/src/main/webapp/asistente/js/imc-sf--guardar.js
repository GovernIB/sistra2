// guardar justificant

var imc_guarda,
	imc_bt_desa_justificant,
	imc_bt_tramit_surt;


// onReady

function appPasGuardarInicia() {
	
	imc_guarda = imc_contingut.find(".imc--guarda:first");
	imc_bt_desa_justificant = $("#imc-bt-justificant-desa");
	imc_bt_tramit_surt = $("#imc-bt-tramit-surt");

	imc_guarda
		.appGuarda()
		.appValora();

	imc_bt_desa_justificant
		.appJustificantDesa();

	imc_bt_tramit_surt
		.appTramitSurt();

};


// appGuarda

$.fn.appGuarda = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			inicia = function() {

				element
					.off('.appGuarda')
					.on('click.appGuarda', ".imc--descarrega", descarrega);

			},
			descarrega = function(e) {

				var bt = $(this)
					,bt_tipus = bt.attr("data-tipus")
					,elm_id = bt.closest(".imc--reg-doc").attr("data-id")
					,elm_instancia = bt.closest(".imc--reg-doc").attr("data-instancia")
					,url = APP_GUARDA_DESCARREGA
					,id = "idDocumento";

				document.location = url + "?" + id + "=" + elm_id + "&idPaso=" + APP_TRAMIT_PAS_ID + "&instancia=" + elm_instancia;

			};
		
		// inicia
		inicia();
		
	});
	return this;
}


// appJustificantDesa

$.fn.appJustificantDesa = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			envia_ajax = false,
			inicia = function() {

				element
					.off('.appJustificantDesa')
					.on('click.appJustificantDesa', desa);

			},
			desa = function() {

				document.location = APP_TRAMIT_JUSTIFICANT + "?idPaso=" + APP_TRAMIT_PAS_ID;

			};
		
		// inicia
		inicia();
		
	});
	return this;
}


// appTramitSurt

$.fn.appTramitSurt = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			envia_ajax = false,
			inicia = function() {

				element
					.off('.appTramitSurt')
					.on('click.appTramitSurt', surt);

			},
			surt = function(e) {

				var bt = $(this);

				imc_missatge
					.appMissatge({
						accio: "desconecta"
						,boto: bt
						,titol: txtSortirSeguretatTitol
						,alAcceptar: function() { document.location = APP_TRAMIT_SURT; }
					});

			};
		
		// inicia
		inicia();
		
	});
	return this;
}


// appValora

$.fn.appValora = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			valoracio_el = element.find(".imc--valoracio:first"),
			output_el = false,
			envia_ajax = false,
			valor_per_especificar = 3,
			especificar_el = element.find(".imc--valoracio-especifica:first"),
			inicia = function() {

				if (valoracio_el.length) {

					output_el = element.find("output:first");

					valoracio_el
						.off('.appValora')
						.on('click.appValora', ".imc--estrelles label", valora)
						.on('click.appValora', "button[data-accio='valora']", envia);

				}

			},
			valora = function(e) {

				var label_el = $(this)
					,label_el_txt = label_el.find("span:first").text()
					,label_el_for = label_el.attr("for")
					,label_el_val = $("#"+label_el_for).val();

				output_el
					.text( label_el_txt );

				if (label_el_val <= valor_per_especificar) {

					especificar_el
						.slideDown();

				} else {

					especificar_el
						.slideUp();

				}

			},
			envia = function() {

				// verifica

				var valoracio_val = parseInt( valoracio_el.find(".imc--estrelles:first input:checked").val(), 10);

				if (!valoracio_val) {

					imc_missatge
						.appMissatge({ accio: "error", titol: txtValoracioNoHiHa });

					return;

				}

				// missatge

				imc_missatge
					.appMissatge({ accio: "carregant", titol: txtValoracioEnviantTitol });


				// envia config

				var	pag_url = APP_TRAMIT_VALORA,
					pag_form = valoracio_el[0],
					formData = new FormData();

				// dades

				var problemes_val = [];

				valoracio_el
					.find("input[name='imc-f-problemes']:checked")
						.each(function() {
							problemes_val.push( $(this).val() );
						});

				var observacions_val = valoracio_el.find("textarea:first").val();

				formData
					.append("idPaso", APP_TRAMIT_PAS_ID);

				formData
					.append("valoracion", valoracio_val);

				formData
					.append("problemas", problemes_val);

				formData
					.append("observaciones", observacions_val);

				// envia ajax

				if (envia_ajax) {

					envia_ajax
						.abort();

				}

				envia_ajax = $.ajax({
								type: "post",
								url: pag_url,
								data: formData,
								processData: false,
								cache: false,
								contentType: false,
								beforeSend: function(xhr) {
									xhr.setRequestHeader(headerCSRF, tokenCSRF);
								}
							})
							.done(function( data ) {
								
								var json = data;

								if (json.estado === "SUCCESS" || json.estado === "WARNING") {

									valorat();

								} else {

									envia_ajax = false;

									consola("Guarda valora: error des de JSON");

									imc_contenidor
										.errors({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, url: json.url });

								}

							})
							.fail(function(dades, tipus, errorThrown) {

								if (tipus === "abort") {
									return false;
								}

								envia_ajax = false;
								
								consola("Guarda valora: error des de FAIL");

								imc_contenidor
									.errors({ estat: "fail" });
								
							});


			},
			valorat = function() {

				imc_missatge
					.appMissatge({ accio: "correcte", titol: txtValoratTitol, text: txtValoratText });

				valoracio_el
					.slideUp(function() {

						valoracio_el
							.remove();

					});

			};
		
		// inicia
		inicia();
		
	});
	return this;
}

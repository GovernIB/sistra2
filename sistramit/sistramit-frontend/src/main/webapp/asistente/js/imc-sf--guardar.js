// guardar justificant

var imc_guarda
	,imc_justificant
	,imc_bt_tramit_surt;


// onReady

function appPasGuardarInicia() {
	
	imc_guarda = imc_contingut.find(".imc--guarda:first");
	imc_justificant = imc_contingut.find(".imc--guarda-justificant:first");
	imc_bt_tramit_surt = $("#imc-bt-tramit-surt");

	imc_guarda
		.appDocumentacioMostra()
		.appGuarda()
		.appSiganuraDescarrega()
		.appValora();

	imc_justificant
		.appJustificantDesa()
		.appJustificantURL();

	imc_bt_tramit_surt
		.appTramitSurt();

};


// appDocumentacioMostra

$.fn.appDocumentacioMostra = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			documentacio_el = element.find(".imc--documentacio:first"),
			inicia = function() {

				element
					.off('.appDocumentacioMostra')
					.on('click.appDocumentacioMostra', "button[data-accio='documenta']" ,activa);

			},
			activa = function(e) {

				var bt = $(this)
					,hasDeObrir = (documentacio_el.hasClass("imc--on")) ? false : true;

				var bt_text = (hasDeObrir) ? txtDocumentacioAmaga : txtDocumentacioMostra
					,acciona = (hasDeObrir) ? obri() : tanca();

				bt
					.find("span")
						.text( bt_text );

			},
			obri = function() {

				documentacio_el
					.stop()
					.slideDown(200)
					.addClass("imc--on");

			},
			tanca = function() {

				documentacio_el
					.stop()
					.slideUp(200)
					.removeClass("imc--on");

			};
		
		// inicia
		inicia();
		
	});
	return this;
}


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
			inicia = function() {

				element
					.off('.appJustificantDesa')
					.on('click.appJustificantDesa', "button[data-accio='desa']", desa);

			},
			desa = function() {

				document.location = APP_TRAMIT_JUSTIFICANT + "?idPaso=" + APP_TRAMIT_PAS_ID;

			};
		
		// inicia
		inicia();
		
	});
	return this;
}


// appJustificantURL

$.fn.appJustificantURL = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			envia_ajax = false,
			inicia = function() {

				element
					.off('.appJustificantURL')
					.on('click.appJustificantURL', "button[data-accio='url'], button[data-accio='carpeta']", envia);

			},
			envia = function(e) {

				var bt = $(this)
					,bt_accio = bt.attr("data-accio");

				// missatge

				imc_missatge
					.appMissatge({ accio: "carregant", titol: txtDescarregantURL });

				// envia config

				var	pag_url = (bt_accio === "url") ? APP_TRAMIT_JUSTIFICANT_URL : APP_TRAMIT_JUSTIFICANT_CARPETA
					,formData = new FormData();

				// dades

				formData
					.append("idPaso", APP_TRAMIT_PAS_ID);

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

									descarregat(json);

								} else {

									envia_ajax = false;

									consola("Justificant URL: error des de JSON");

									imc_contenidor
										.errors({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, url: json.url });

								}

							})
							.fail(function(dades, tipus, errorThrown) {

								if (tipus === "abort") {
									return false;
								}

								envia_ajax = false;
								
								consola("Justificant URL: error des de FAIL");

								imc_contenidor
									.errors({ estat: "fail" });
								
							});


			},
			descarregat = function(json) {

				// missatge?

				var estat = (json.estado === "SUCCESS") ? "correcte" : "warning"
					,titol = json.mensaje.titulo
					,text = json.mensaje.texto;

				if (titol !== "") {

					imc_missatge
						.appMissatge({ accio: estat, titol: titol, text: text, alAcceptar: function() { obri(json); } });

					return;

				}

				obri(json);

			},
			obri = function(json) {

				var url = json.datos.url;

				var obri_url = function() {

						window
							.open(url, "_blank");

					};

				$("<a>")
					.attr({ href: "javascript:;", id: "imc--bt-justificant-url-dinamic", target: "_blank" })
					.on("click", obri_url)
					.appendTo( imc_body )
					.trigger("click");

				setTimeout(
					function() {

						$("#imc--bt-justificant-url-dinamic")
							.remove();

					}
					,100
				);

				imc_missatge
					.appMissatge({ araAmaga: true });

			};
		
		// inicia
		inicia();
		
	});
	return this;
}


// appSiganuraDescarrega

$.fn.appSiganuraDescarrega = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			inicia = function() {

				element
					.off('.appSiganuraDescarrega')
					.on('click.appSiganuraDescarrega', "button[data-es='signatura'].imc--descarrega", descarrega);

			},
			descarrega = function(e) {

				var bt = $(this)
					,bt_tipus = bt.attr("data-tipus")
					,elm_id = bt.closest(".imc--reg-doc").attr("data-id")
					,elm_instancia = bt.closest(".imc--reg-doc").attr("data-instancia")
					,elm_signant = bt.closest(".imc--signat").attr("data-nif")
					,url = APP_SIGNATURA_GUARDAR_DESCARREGA
					,id = "idDocumento";

				document.location = url + "?" + id + "=" + elm_id + "&idPaso=" + APP_TRAMIT_PAS_ID + "&instancia=" + elm_instancia + "&firmante=" + elm_signant;

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
			valor_seleccionat = 0,
			inicia = function() {

				if (valoracio_el.length) {

					output_el = element.find("output:first");

					valoracio_el
						.off('.appValora')
						.on('mouseenter.appValora', ".imc--estrelles label", entra)
						.on('mouseleave.appValora', ".imc--estrelles label", surt)
						.on('click.appValora', ".imc--estrelles label", valora)
						.on('click.appValora', "button[data-accio='valora']", envia);

				}

			},
			entra = function(e) {

				var label_el = $(this)
					,label_el_txt = label_el.find("span:first").text();

				output_el
					.text( label_el_txt );

			},
			surt = function() {

				var label_el_txt = (valor_seleccionat === 0) ? txtSenseValoracio : valoracio_el.find(".imc--estrelles label[for=imc-f-estrelles-"+valor_seleccionat+"]:first").text();

				output_el
					.text( label_el_txt );

			},
			valora = function(e) {

				var label_el = $(this)
					,label_el_txt = label_el.find("span:first").text()
					,label_el_for = label_el.attr("for")
					,label_el_val = $("#"+label_el_for).val();

				valor_seleccionat = label_el_val;

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

// Emplenar formularis

var imc_formularis;

var FORMS_JSON
	,FORMS_ARXIUS = false;



// onReady

function appPasEmplenarInicia() {
	
	imc_formularis = imc_contingut.find(".imc--formularis:first");

	imc_formularis
		.appFormulariDescarrega();

	if (FORMS_ARXIUS) {

		return;

	}

	// carrega JS inicial de FORMS

	$.when(
		
		$.get(APP_ + "forms/html/imc--forms-contenidor.html?" + APP_VERSIO)
		,$.getScript(APP_ + "forms/js/imc-forms--inicia.js?" + APP_VERSIO)

	).then(

		function( htmlForms ) {

			imc_body
				.append( htmlForms[0] );

			// literals

			$("#imc-forms-contenidor")
				.find("strong:first")
					.text( txtFormDinAjudaActivada )
					.end()
				.find("button:first span ")
					.text( txtFormDinDesactivar );

			// carrega CSS i JS

			appFormsCarregaScripts() 
			
			// carrega formulari

			FORMS_ARXIUS = true;

		}

	).fail(

		function() {

			consola("Formulari: error des de l'inicia de FORMS (FAIL)");

			imc_contenidor
				.errors({ estat: "fail" });

		}

	);

}


// appFormulariDescarrega

$.fn.appFormulariDescarrega = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			inicia = function() {

				element
					.off('.appFormulariDescarrega')
					.on('click.appFormulariDescarrega', "button", activa);

			},
			activa = function(e) {

				var bt = $(this)
					,form_id = bt.parent().find("a:first").attr("data-id")
					,esPDF = (bt.hasClass("imc--fo-pdf")) ? true : false
					,url = (esPDF) ? APP_FORM_PDF : APP_FORM_XML;

				document.location = url + "?idFormulario=" + form_id + "&idPaso=" + APP_TRAMIT_PAS_ID;

			};
		
		// inicia
		inicia();
		
	});
	return this;
}


// appEmplenaFormulari

$.fn.appEmplenaFormulari = function(options) {
	var settings = $.extend({
		element: "",
		form_id: false
	}, options);
	this.each(function(){
		var element = $(this),
			form_id = settings.form_id,
			form_nom = false,
			ico_fons = false,
			ico_anim = false,
			ico_anim_carrega = false,
			envia_ajax = false,
			form_ticket = false,
			obri = function(e) {

				var bt = element.find("a[data-id="+form_id+"]:first");

				if (!bt.length) {
					return;
				}

				var bt_ico = bt.find(".imc--formulari:first"),
					bt_ico_T = bt_ico.offset().top,
					bt_ico_L = bt_ico.offset().left,
					bt_ico_W = bt_ico.outerWidth(),
					bt_ico_H = bt_ico.outerHeight();

				if (bt.attr("data-obligatori") === "d" || element.attr("data-lectura") === "s") {
					return;
				}

				ico_fons = $("<div>").addClass("imc-ico-fons").attr("aria-hidden", "true").appendTo( imc_body );
				ico_anim = $("<div>").addClass("imc-ico-anim").attr("aria-hidden", "false").css({ top: bt_ico_T+"px", left: bt_ico_L+"px", width: bt_ico_W+"px", height: bt_ico_W+"px" }).appendTo( ico_fons );

				ico_fons
					.attr("aria-hidden", "false");

				ico_anim
					.animate(
						{ top: "50%", left: "50%", borderRadius: "50%" }
						,200
						,function() {

							carrega();

						})
					.addClass("imc--centra");

				form_nom = bt.find("strong:first").text();

			},
			carrega = function() {

				ico_anim_carrega = $("<div>").addClass("imc-ico-carrega").appendTo( ico_fons );

				ico_anim_carrega
					.animate(
						{ opacity: "1" }
						,500
						,function() {

							carregant();

						}
					);

			},
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
						timeout: APP_TIMEOUT,
						beforeSend: function(xhr) {
							xhr.setRequestHeader(headerCSRF, tokenCSRF);
						}
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

							consola("Formulari (carrega ticket): error des de JSON");
							
							imc_contenidor
								.errors({ estat: data.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, url: data.url });

						}
						
					})
					.fail(function(dades, tipus, errorThrown) {

						if (tipus === "abort") {
							return false;
						}
						
						consola("Formulari (carrega ticket): error fail");
						
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

							/*
							imc_forms_finestra
								.find(".imc--contingut:first")
									.html( FORMS_JSON.datos.html );
							*/
							
							// carregat

							carregaFormArxius();

							if (FORMS_JSON.estado === "WARNING") {

								imc_missatge
									.appMissatge({ accio: "warning", titol: FORMS_JSON.mensaje.titulo, text: FORMS_JSON.mensaje.texto });

							}

						} else {

							consola("Formulari (carrega dades): error des de JSON");
							
							imc_contenidor
								.errors({ estat: FORMS_JSON.estado, titol: FORMS_JSON.mensaje.titulo, text: FORMS_JSON.mensaje.texto, url: FORMS_JSON.url });

						}

					}

				).fail(

					function(jqXHR, textStatus, errorThrown) {

						consola("Formulari (carrega dades): error fail");

						imc_contenidor
							.errors({ estat: "fail" });

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

						imc_body
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
						.html( FORMS_JSON.datos.html );

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
					//.attr("data-id", form_id)
					.attr("aria-hidden", "false");

				ico_fons
					.attr("aria-hidden", "true");

				setTimeout(
					function() {

						ico_fons
							.remove();

						$("html, body")
							.addClass("imc--sense-scroll");


					}, 300);
				
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

				// capa carrega

				ico_fons
					.remove();

				tanca();

				envia_ajax = false;

			};
		
		// obri
		obri();
		
	});
	return this;
}

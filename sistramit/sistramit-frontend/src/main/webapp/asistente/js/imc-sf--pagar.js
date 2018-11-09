// Emplenar formularis

var imc_pagaments
	,imc_pagament
	,imc_pagament_c
	,imc_pagament_bt
	,imc_pagament_iframe;


// onReady

function appPasPagarInicia() {
	
	imc_pagaments = imc_contingut.find(".imc--pagaments:first");

	imc_pagament = $("#imc-pagament");
	imc_pagament_c = imc_pagament.find(".imc--c:first");
	imc_pagament_bt = imc_pagament.find("button:first");
	imc_pagament_iframe = imc_pagament.find("iframe:first");

	// funcions

	imc_pagaments
		.appPagament()
		.appPagamentPresencial()
		.appPagaments();

}


// appPagament

$.fn.appPagament = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			envia_ajax = false,
			elm_pagament_id = false,
			inicia = function() {

				element
					.off('.appPagament')
					.on('click.appPagament', ".imc--pagament-electronic", obri);

			},
			obri = function(e) {

				var bt = $(this)
					,bt_ico = bt.closest("li").find(".imc--pagament:first")
					,bt_ico_T = bt_ico.offset().top
					,bt_ico_L = bt_ico.offset().left
					,bt_ico_W = bt_ico.outerWidth()
					,bt_ico_H = bt_ico.outerHeight();

				elm_pagament_id = bt.closest("li").attr("data-id");

				if (bt.attr("data-obligatori") === "d") {
					return;
				}

				form_id = bt.attr("data-id");

				ico_anim = $("<div>").addClass("imc-ico-anim").css({ top: bt_ico_T+"px", left: bt_ico_L+"px", width: bt_ico_W+"px", height: bt_ico_W+"px" }).appendTo( imc_pagament_c );

				imc_pagament
					.addClass("imc--on");

				ico_anim
					.animate(
						{ top: "50%", left: "50%", borderRadius: "50%" }
						,200
						,function() {

							carrega();

						})
					.addClass("imc--centra");

			},
			carrega = function() {

				ico_anim_carrega = $("<div>").addClass("imc-ico-carrega").appendTo( imc_pagament_c );

				ico_anim_carrega
					.animate(
						{ opacity: "1" }
						,500
						,function() {

							carregant();

						});

			},
			carregant = function() {

				var pag_url = APP_TAXA_URL,
					pag_data = { idPago: elm_pagament_id, idPaso: APP_TRAMIT_PAS_ID };

				// ajax

				if (envia_ajax) {

					envia_ajax
						.abort();

				}
				
				envia_ajax =
					$.ajax({
						url: pag_url,
						data: pag_data,
						method: "post",
						dataType: "json",
						beforeSend: function(xhr) {
							xhr.setRequestHeader(headerCSRF, tokenCSRF);
						}
					})
					.done(function( data ) {

						if (data.estado === "SUCCESS" || data.estado === "WARNING") {

							var continua = function() {

									var form_tipus = data.datos.tipo
										,form_url = data.datos.url;

									if (form_tipus === "i") {

										imc_pagament_iframe
											.off("")
											.on("load", carregat);

										imc_pagament_iframe
											.attr("src", form_url);

									} else {

										document.location = form_url;

									}

								};

							if (data.mensaje.titulo !== "") {

								imc_missatge
									.appMissatge({ accio: "warning", titol: data.mensaje.titulo, text: data.mensaje.texto, alAcceptar: function() { continua(); } });

								return;

							}

							continua();

						} else {

							envia_ajax = false;

							consola("Pagament electrònic: error des de JSON");
							
							imc_contenidor
								.errors({ estat: data.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, url: data.url });

						}
						
					})
					.fail(function(dades, tipus, errorThrown) {

						envia_ajax = false;

						if (tipus === "abort") {
							return false;
						}
						
						consola("Pagament electrònic: error des de FAIL");
						
						imc_contenidor
							.errors({ estat: "fail" });
						
					});

			},
			carregat = function() {

				if (imc_pagament_iframe.attr("src") === "") {
					return;
				}

				var window_T = $(window).scrollTop(),
					ifr_T = imc_pagament_iframe.offset().top,
					ifr_L = imc_pagament_iframe.offset().left,
					ifr_W = imc_pagament_iframe.outerWidth(),
					ifr_H = imc_pagament_iframe.outerHeight();

				ico_anim
					.animate(
						{ top: (ifr_T-window_T)+"px", left: ifr_L+"px", width: ifr_W+"px", height: ifr_H+"px", borderRadius: "0" }
						,200
						,function() {

							mostra();

						})
					.removeClass("imc--centra");

			},
			mostra = function() {

				ico_anim_carrega
					.remove();

				imc_pagament_iframe
					.addClass("imc--on");

				ico_anim
					.addClass("imc--off");

				setTimeout(
					function() {

						ico_anim
							.remove();

						$("html, body")
							.addClass("imc--iframe-mostrat");


					}, 300);

				imc_pagament_c
					.off('.appFormulari')
					.on('click.appFormulari', tanca);

				envia_ajax = false;

			},
			tanca = function() {

				imc_pagament
					.addClass("imc--off");

				$("html, body")
					.removeClass("imc--iframe-mostrat");

				setTimeout(
					function() {

						imc_pagament_iframe
							.removeClass("imc--on");

						imc_pagament
							.find("iframe")
								.attr("src", "")
								.end()
							.removeClass("imc--on imc--off");


					}, 300);

			};
		
		// inicia
		inicia();
		
	});
	return this;
}



// appPagamentPresencial

$.fn.appPagamentPresencial = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			envia_ajax = false,
			elm_pagament_id = false,
			inicia = function() {

				element
					.off('.appPagamentPresencial')
					.on('click.appPagamentPresencial', ".imc--pagament-presencial", carrega);

			},
			carrega = function(e) {

				var bt = $(this);

				elm_pagament_id = bt.closest("li").attr("data-id");

				imc_missatge
					.appMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtPagamentPresencialSeleccionat, alMostrar: function() { carregant(); } });

			},
			carregant = function() {

				var pag_url = APP_TAXA_PRESENCIAL,
					pag_data = { idPago: elm_pagament_id, idPaso: APP_TRAMIT_PAS_ID };

				// ajax

				if (envia_ajax) {

					envia_ajax
						.abort();

				}
				
				envia_ajax =
					$.ajax({
						url: pag_url,
						data: pag_data,
						method: "post",
						dataType: "json",
						beforeSend: function(xhr) {
							xhr.setRequestHeader(headerCSRF, tokenCSRF);
						}
					})
					.done(function( data ) {

						if (data.estado === "SUCCESS" || data.estado === "WARNING") {

							var continua = function() {

									imc_contingut_c
										.appPas({ pas: APP_TRAMIT_PAS_ID, recarrega: true });

								};

							if (data.mensaje.titulo !== "") {

								imc_missatge
									.appMissatge({ accio: "warning", titol: data.mensaje.titulo, text: data.mensaje.texto, alAcceptar: function() { continua(); } });

								return;

							}

							continua();

						} else {

							envia_ajax = false;

							consola("Pagament presencial: error des de JSON");
							
							imc_contenidor
								.errors({ estat: data.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, url: data.url });

						}
						
					})
					.fail(function(dades, tipus, errorThrown) {

						envia_ajax = false;

						if (tipus === "abort") {
							return false;
						}
						
						consola("Pagament presencial: error des de FAIL");
						
						imc_contenidor
							.errors({ estat: "fail" });
						
					});

			};
		
		// inicia
		inicia();
		
	});
	return this;
}



// appPagaments

$.fn.appPagaments = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			envia_ajax = false,
			inicia = function() {

				element
					.off('.appPagaments')
					.on('click.appPagaments', ".imc--plantilla-pagament", descarregaJustificant)
					.on('click.appPagaments', ".imc--justificant-pagament", descarregaJustificant)
					.on('click.appPagaments', ".imc--descartar-pagament", executa)
					.on('click.appPagaments', ".imc--revisar-pagament", executa);

			},
			descarregaJustificant = function(e) {

				var bt = $(this)
					,elm_pagament_id = bt.closest("li").attr("data-id");

				document.location = APP_TAXA_JUSTIFICANT + "?idPago=" + elm_pagament_id + "&idPaso=" + APP_TRAMIT_PAS_ID;

			},
			executa = function(e) {

				var bt = $(this)
					,esDescartar = (bt.hasClass("imc--descartar-pagament")) ? true : false
					,elm_pagament_id = bt.closest("li").attr("data-id");

				// es descartar

				if (esDescartar) {

					var taxa_tipus = bt.closest("li").attr("data-presentacio")
						,text = (taxa_tipus === "p") ? txtDescartarTaxaText : txtDescartarTaxaElectronicaText;

					imc_missatge
						.appMissatge({ boto: bt, accio: "esborra", titol: txtDescartarTaxaTitol, text: text, alAcceptar: function() { enviant(esDescartar, elm_pagament_id); } });

					return;
				}

				// es validar

				enviant(esDescartar, elm_pagament_id);

			},
			enviant = function(esDescartar, elm_pagament_id) {

				var titol = (esDescartar) ? txtDescartantTaxaTitol : txtValidantTaxaTitol;

				imc_missatge
					.appMissatge({ accio: "carregant", amagaDesdeFons: false, titol: titol, alMostrar: function() { enviament(esDescartar, elm_pagament_id); } });

			},
			enviament = function(esDescartar, elm_pagament_id) {

				// ajax!!!

				var pag_url = (esDescartar) ? APP_TAXA_DESCARTAR : APP_TAXA_VALIDAR
					,pag_data = { idPago: elm_pagament_id, idPaso: APP_TRAMIT_PAS_ID }
					,errorConsola = (esDescartar) ? "descartar" : "validar";

				// ajax

				if (envia_ajax) {

					envia_ajax
						.abort();

				}
				
				envia_ajax =
					$.ajax({
						url: pag_url,
						data: pag_data,
						method: "post",
						dataType: "json",
						beforeSend: function(xhr) {
							xhr.setRequestHeader(headerCSRF, tokenCSRF);
						}
					})
					.done(function( data ) {

						if (data.estado === "SUCCESS" || data.estado === "WARNING") {

							var continua = function() {

									// recarrega si escull PRESENCIAL

									imc_contingut_c
										.appPas({ pas: APP_TRAMIT_PAS_ID, recarrega: true });

								};

							if (data.mensaje.titulo !== "") {

								imc_missatge
									.appMissatge({ accio: "warning", titol: data.mensaje.titulo, text: data.mensaje.texto, alAcceptar: function() { continua(); } });

								return;

							}

							continua();

						} else {

							envia_ajax = false;

							consola("Taxa "+errorConsola+": error des de JSON");
							
							imc_contenidor
								.errors({ estat: data.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, url: data.url });

						}
						
					})
					.fail(function(dades, tipus, errorThrown) {

						envia_ajax = false;

						if (tipus === "abort") {
							return false;
						}
						
						consola("Taxa "+errorConsola+": error des de FAIL");
						
						imc_contenidor
							.errors({ estat: "fail" });
						
					});

			};
		
		// inicia
		inicia();
		
	});
	return this;
}

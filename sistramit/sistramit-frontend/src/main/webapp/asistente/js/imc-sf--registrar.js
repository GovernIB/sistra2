// Emplenar formularis

var imc_registre
	,imc_bt_registra
	,imc_signatura
	,imc_signatura_c
	,imc_signatura_bt
	,imc_signatura_iframe;


// onReady

function appPasRegistrarInicia() {
	
	imc_registre = imc_contingut.find(".imc--registre:first");
	imc_bt_registra = $("#imc-bt-registra");
	imc_bt_reintenta = $("#imc-bt-reintenta")

	imc_signatura = $("#imc-signatura");
	imc_signatura_c = imc_signatura.find(".imc--c:first");
	imc_signatura_bt = imc_signatura.find("button:first");
	imc_signatura_iframe = imc_signatura.find("iframe:first");

	imc_registre
		.appRegistre()
		.appSigna();

	imc_bt_registra
		.appRegistra();

	imc_bt_reintenta
		.appRegistra();

};


// appSigna

$.fn.appSigna = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			envia_ajax = false,
			document_id = false,
			document_instancia = false,
			document_signant_nif = false,
			inicia = function() {

				element
					.off('.appSigna')
					.on('click.appSigna', ".imc--signa", obri);

			},
			obri = function(e) {

				var bt = $(this)
					,bt_ico = bt
					,bt_ico_T = bt_ico.offset().top
					,bt_ico_L = bt_ico.offset().left
					,bt_ico_W = bt_ico.outerWidth()
					,bt_ico_H = bt_ico.outerHeight();

				elm_signatura_id = bt.closest("li").attr("data-id");

				var elm_li_reg = bt.closest("li.imc--reg-doc");

				document_id = elm_li_reg.attr("data-id");
				document_instancia = elm_li_reg.attr("data-instancia");
				document_signant_nif = bt.closest("li.imc--per-signar").attr("data-nif");

				if (bt.attr("data-obligatori") === "d") {
					return;
				}

				form_id = bt.attr("data-id");

				ico_anim = $("<div>").addClass("imc-ico-anim").css({ top: bt_ico_T+"px", left: bt_ico_L+"px", width: bt_ico_W+"px", height: bt_ico_W+"px" }).appendTo( imc_signatura_c );

				imc_signatura
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

				ico_anim_carrega = $("<div>").addClass("imc-ico-carrega").appendTo( imc_signatura_c );

				ico_anim_carrega
					.animate(
						{ opacity: "1" }
						,500
						,function() {

							carregant();

						});

			},
			carregant = function() {

				var pag_url = APP_SIGNATURA_URL,
					pag_dades = { idDocumento: document_id, instancia: document_instancia, firmante: document_signant_nif, idPaso: APP_TRAMIT_PAS_ID };

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
						beforeSend: function(xhr) {
							xhr.setRequestHeader(headerCSRF, tokenCSRF);
						}
					})
					.done(function( data ) {

						if (data.estado === "SUCCESS" || data.estado === "WARNING") {

							var continua = function() {

									var form_tipus = data.datos.tipo
										,form_url = data.datos.url;

									imc_signatura_iframe
										.off("")
										.on("load", carregat);

									imc_signatura_iframe
										.attr("src", form_url);

								};

							if (data.estado === "WARNING") {

								imc_missatge
									.appMissatge({ accio: "warning", titol: data.mensaje.titulo, text: data.mensaje.texto, alAcceptar: function() { continua(); } });

								return;

							}

							continua();

						} else {

							envia_ajax = false;

							consola("Formulari: error des de JSON");
							
							imc_contenidor
								.errors({ estat: data.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, url: data.url });

						}
						
					})
					.fail(function(dades, tipus, errorThrown) {

						if (tipus === "abort") {
							return false;
						}
						
						consola("Formulari: error des de FAIL");
						
						imc_contenidor
							.errors({ estat: "fail" });
						
					});

			},
			carregat = function() {

				if (imc_signatura_iframe.attr("src") === "") {
					return;
				}

				var window_T = $(window).scrollTop(),
					ifr_T = imc_signatura_iframe.offset().top,
					ifr_L = imc_signatura_iframe.offset().left,
					ifr_W = imc_signatura_iframe.outerWidth(),
					ifr_H = imc_signatura_iframe.outerHeight();

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

				imc_signatura_iframe
					.addClass("imc--on");

				ico_anim
					.addClass("imc--off");

				setTimeout(
					function() {

						ico_anim
							.remove();

						$("html, body")
							.addClass("imc--sense-scroll");


					}, 300);

				imc_signatura_c
					.off('.appFormulari')
					.on('click.appFormulari', tanca);

				envia_ajax = false;

			},
			tanca = function() {

				imc_signatura
					.addClass("imc--off");

				$("html, body")
					.removeClass("imc--sense-scroll");

				setTimeout(
					function() {

						imc_signatura_iframe
							.removeClass("imc--on");

						imc_signatura
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


// appRegistre

$.fn.appRegistre = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			inicia = function() {

				element
					.off('.appRegistre')
					.on('click.appRegistre', ".imc--descarrega", descarrega);

			},
			descarrega = function(e) {

				var bt = $(this)
					,bt_tipus = bt.attr("data-tipus")
					,elm_id = bt.closest(".imc--reg-doc").attr("data-id")
					,elm_instancia = bt.closest(".imc--reg-doc").attr("data-instancia")
					,url = APP_REGISTRE_DESCARREGA
					,id = "idDocumento";

				document.location = url + "?" + id + "=" + elm_id + "&idPaso=" + APP_TRAMIT_PAS_ID + "&instancia=" + elm_instancia;

			};
		
		// inicia
		inicia();
		
	});
	return this;
}


// appRegistra

$.fn.appRegistra = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			accio = false,
			envia_ajax = false,
			inicia = function() {

				element
					.off('.appRegistra')
					.on('click.appRegistra', obri);

			},
			obri = function(e) {

				var bt = $(this)
					,accio = bt.attr("data-accio");

				var titol = (accio === "reintenta") ? txtReintentarAtencioTitol : txtRegistrarAtencioTitol
					,text = (accio === "reintenta") ? txtReintentarAtencioText : txtRegistrarAtencioText;

				imc_missatge
					.appMissatge({
						boto: element
						, accio: accio
						, titol: titol
						, text: text
						, alAcceptar: function() { registra(); }
					});

			},
			registra = function() {

				// missatge carregant
				
				imc_missatge
					.appMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtRegistrant });

				registrant();

			},
			registrant = function() {

				var pag_url = (accio === "reintenta") ? APP_TRAMIT_REINTENTA : APP_TRAMIT_REGISTRA,
					pag_dades = { idPaso: APP_TRAMIT_PAS_ID };

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
						beforeSend: function(xhr) {
							xhr.setRequestHeader(headerCSRF, tokenCSRF);
						}
					})
					.done(function( data ) {

						if (data.estado === "SUCCESS" || data.estado === "WARNING") {

							var accio = (data.estado === "SUCCESS") ? "tramitat" : "warning";

							imc_missatge
								.appMissatge({ accio: accio, amagaDesdeFons: false, titol: data.mensaje.titulo, text: data.mensaje.texto, alAcceptar: function() { document.location = data.url; } });

						} else {

							envia_ajax = false;

							consola("Registra: error des de JSON");
							
							imc_contenidor
								.errors({ estat: data.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, url: data.url });

						}
						
					})
					.fail(function(dades, tipus, errorThrown) {

						//consola(dades+" , "+ tipus +" , "+ errorThrown);

						if (tipus === "abort") {
							return false;
						}
						
						consola("Registra: error des de FAIL");
						
						imc_contenidor
							.errors({ estat: "fail" });
						
					});

			};
		
		// inicia
		inicia();
		
	});
	return this;
}

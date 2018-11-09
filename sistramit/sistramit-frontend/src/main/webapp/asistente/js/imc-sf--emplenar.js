// Emplenar formularis

var imc_formularis,
	imc_formulari,
	imc_formulari_c,
	imc_formulari_bt,
	imc_formulari_iframe;


// onReady

function appPasEmplenarInicia() {
	
	imc_formularis = imc_contingut.find(".imc--formularis:first");

	imc_formulari = $("#imc-formulari");
	imc_formulari_c = imc_formulari.find(".imc--c:first");
	imc_formulari_bt = imc_formulari.find("button:first");
	imc_formulari_iframe = imc_formulari.find("iframe:first");

	imc_formularis
		.appFormulari()
		.appFormulariDescarrega();

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


// appFormulari

$.fn.appFormulari = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			ico_anim = false,
			ico_anim_carrega = false,
			iframe_url = false,
			form_id = false,
			envia_ajax = false,
			inicia = function() {

				element
					.off('.appFormulari')
					.on('click.appFormulari', "li a", obri);

				imc_formulari_bt
					.off('.appFormulari')
					.on('click.appFormulari', tanca);

			},
			obri = function(e) {

				var bt = $(this),
					bt_ico = bt.find(".imc--formulari:first"),
					bt_ico_T = bt_ico.offset().top,
					bt_ico_L = bt_ico.offset().left,
					bt_ico_W = bt_ico.outerWidth(),
					bt_ico_H = bt_ico.outerHeight();

				if (bt.attr("data-obligatori") === "d" || element.attr("data-lectura") === "s") {
					return;
				}

				form_id = bt.attr("data-id");

				ico_anim = $("<div>").addClass("imc-ico-anim").css({ top: bt_ico_T+"px", left: bt_ico_L+"px", width: bt_ico_W+"px", height: bt_ico_W+"px" }).appendTo( imc_formulari_c );

				imc_formulari
					.addClass("imc--on");

				ico_anim
					.animate(
						{ top: "50%", left: "50%", borderRadius: "50%" }
						,200
						,function() {

							carrega();

						})
					.addClass("imc--centra");

				iframe_url = bt.attr("data-url");

			},
			carrega = function() {

				ico_anim_carrega = $("<div>").addClass("imc-ico-carrega").appendTo( imc_formulari_c );

				ico_anim_carrega
					.animate(
						{ opacity: "1" }
						,500
						,function() {

							carregant();

						});

			},
			carregant = function() {

				//var ico_anim_text = $("<div>").addClass("imc-ico-carrega-text").text( "Carregant formulari..." ).appendTo( imc_formulari_c );
				/*
				imc_formulari_iframe
					.off("")
					.on("load", carregat);

				imc_formulari_iframe
					.attr("src", iframe_url);
				*/

				var pag_url = APP_FORM_URL,
					pag_data = { idFormulario: form_id, idPaso: APP_TRAMIT_PAS_ID };

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

										imc_formulari_iframe
											.off("")
											.on("load", carregat);

										imc_formulari_iframe
											.attr("src", form_url);

									} else {

										document.location = form_url;

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

				if (imc_formulari_iframe.attr("src") === "") {
					return;
				}

				var window_T = $(window).scrollTop(),
					ifr_T = imc_formulari_iframe.offset().top,
					ifr_L = imc_formulari_iframe.offset().left,
					ifr_W = imc_formulari_iframe.outerWidth(),
					ifr_H = imc_formulari_iframe.outerHeight();

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

				imc_formulari_iframe
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

				imc_formulari_c
					.off('.appFormulari')
					.on('click.appFormulari', tanca);

				envia_ajax = false;

			},
			tanca = function() {

				imc_formulari
					.addClass("imc--off");

				$("html, body")
					.removeClass("imc--iframe-mostrat");

				setTimeout(
					function() {

						imc_formulari_iframe
							.removeClass("imc--on");

						imc_formulari
							.find("iframe")
								.attr("src", "")
								.end()
							.removeClass("imc--on imc--off");


					}, 300);

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

				// iframe

				ico_anim_carrega
					.remove();

				ico_anim
					.remove();

				tanca();

				envia_ajax = false;

			};
		
		// inicia
		inicia();
		
	});
	return this;
}

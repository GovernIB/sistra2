// Emplenar formularis

var imc_formularis,
	imc_formulari,
	imc_formulari_c,
	imc_formulari_bt,
	imc_formulari_finestra;

var JSON_FORMULARI;


// onReady

function appPasEmplenarInicia() {
	
	imc_formularis = imc_contingut.find(".imc--formularis:first");

	imc_formulari = $("#imc-formulari");
	imc_formulari_c = imc_formulari.find(".imc--c:first");
	imc_formulari_bt = imc_formulari.find("button:first");
	imc_formulari_finestra = imc_formulari.find(".imc--finestra:first");

	imc_formularis
		//.appFormulari()
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
			ico_anim = false,
			ico_anim_carrega = false,
			iframe_url = false,
			envia_ajax = false,
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
				form_nom = bt.find("strong:first").text();

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
						beforeSend: function(xhr) {
							xhr.setRequestHeader(headerCSRF, tokenCSRF);
						}
					})
					.done(function( data ) {

						if (data.estado === "SUCCESS" || data.estado === "WARNING") {

							var continua = function() {

									var form_tipus = data.datos.tipo
										,form_ticket = data.datos.ticket;

									if (form_tipus === "i") {

										imc_formulari_finestra
											.attr("data-ticket", form_ticket);

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
			carregaForm = function() {

				$.when(
					
					$.get( APP_FORM_CARREGA + "?ticket=" + imc_formulari_finestra.attr("data-ticket") )

				).then(

					function( jsonForm ) {

						var json_form = jsonForm;

						imc_formulari_finestra
							.find(".imc--contingut:first")
								.html( json_form.datos.html );

						JSON_FORMULARI = json_form;
						
						// carregat

						carregaFormArxius();

					}

				).fail(

					function() {

						consola("Formulari: error des de carregaFormArxius (FAIL)");

						imc_contenidor
							.errors({ estat: "fail" });

					}

				);


			},
			carregaFormArxius = function() {

				$.when(
					
					$.get(APP_ + "css/sf--emplenar/imc-destaca.css")
					,$.get(APP_ + "css/sf--emplenar/imc-forms-select.css")
					,$.get(APP_ + "css/sf--emplenar/imc-forms-taula-iframe.css")
					,$.get(APP_ + "css/sf--emplenar/imc-forms.css")
					,$.getScript(APP_ + "js/forms/imc-forms--inicia.js")

				).then(

					function( cssFormsDestaca, cssFormsSelect, cssFormsTaulaIframe, cssForms) {

						// estils

						$("<style>")
							.html( cssFormsDestaca[0] )
								.appendTo( imc_head );

						$("<style>")
							.html( cssFormsSelect[0] )
								.appendTo( imc_head );

						$("<style>")
							.html( cssFormsTaulaIframe[0] )
								.appendTo( imc_head );

						$("<style>")
							.html( cssForms[0] )
								.appendTo( imc_head );
						
						// carrega formulari

						carregat();

					}

				).fail(

					function() {

						consola("Formulari: error des de carregaFormArxius (FAIL)");

						imc_contenidor
							.errors({ estat: "fail" });

					}

				);

			},
			carregat = function() {

				if (imc_formulari_finestra.attr("data-ticket") === "") {
					return;
				}

				var window_T = $(window).scrollTop(),
					ifr_T = imc_formulari_finestra.offset().top,
					ifr_L = imc_formulari_finestra.offset().left,
					ifr_W = imc_formulari_finestra.outerWidth(),
					ifr_H = imc_formulari_finestra.outerHeight();

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

				var poderGuardar = JSON_FORMULARI.datos.permitirGuardar || "n";

				imc_formulari
					.attr("data-id", form_id)
					.attr("data-guardar", poderGuardar);

				// títol

				var titol = JSON_FORMULARI.datos.titulo
					,mostarTitol = (JSON_FORMULARI.datos.mostrarTitulo === "s") ? true : false;

				if (mostarTitol) {

					imc_formulari_finestra
						.find("h3:first span")
							.text( titol )
							.end()
						.find("h3:first")
							.show();

				} else {

					imc_formulari_finestra
						.find("h3:first")
							.hide();

				}

				// radio, checks i mostra

				imc_formulari_finestra
					.find(".imc-input-radio label, .imc-input-check label")
						.attr("tabindex", "0")
						.end()
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
				
				envia_ajax = false;

			},
			tancaForm = function() {

				imc_formulari
					.addClass("imc--off");

				$("html, body")
					.removeClass("imc--sense-scroll");

				setTimeout(
					function() {

						imc_formulari_finestra
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
		
		// obri
		obri();
		
	});
	return this;
}


// appMissatgeFormAccions

$.fn.appMissatgeFormAccions = function(options) {
	var settings = $.extend({
		accio: false
	}, options);
	this.each(function(){
		var element = $(this), // imc_missatge
			accio = settings.accio,
			inicia = function() {

				if (accio) {

					if (accio === "surt") {
						surt();
					}

					return;

				}

				element
					.off('.appMissatgeFormAccions')
					.on('click.appMissatgeFormAccions', "button[data-tipus=desa]", desaSurt)
					.on('click.appMissatgeFormAccions', "button[data-tipus=surt]", surt)
					.on('click.appMissatgeFormAccions', "button[data-tipus=tanca]", surt);

			},
			desaSurt = function() {

				imc_formulari_finestra
					.appFormsAccions({ accio: "envia" });

				element
					.off('.appMissatgeFormAccions');

			},
			surt = function() {

				element
					.off('.appMissatgeFormAccions');

				// amaga formulari

				imc_formulari
					.addClass("imc--off");

				$("html, body")
					.removeClass("imc--sense-scroll");

				setTimeout(
					function() {

						imc_formulari_finestra
							.removeClass("imc--on");

						imc_formulari
							.find("iframe")
								.attr("src", "")
								.end()
							.removeClass("imc--on imc--off");

						document.location = "#pas/" + APP_TRAMIT_PAS_ID;

					}, 300);

				// amaga missatge

				element
					.off('.appMissatgeFormAccions')
					.attr("aria-hidden", "true")
					.addClass("imc--off");

				setTimeout(
					function() {

						element
							.find(".imc--text:first")
								.removeClass("imc--on");

						element
							.removeClass("imc--on imc--off");

					}, 200);

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
							.addClass("imc--sense-scroll");


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
					.removeClass("imc--sense-scroll");

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

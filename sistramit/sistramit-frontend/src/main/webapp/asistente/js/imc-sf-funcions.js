// JS


// appCap

$.fn.appCap = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			dades_el = element.find(".imc--dades:first"),
			dades_W = false,
			dades_usuari_W = false,
			dades_usuari_H = false,
			dades_clau_H = false,
			dades_desa_H = false,
			altura_total = false,
			verifica = function() {

				dades_el
					.removeAttr("style")
					.removeClass("imc--obert");

				if (dades_el.css("opacity") === "0") {

					inicia();

				}

			},
			inicia = function() {

				dades_W = dades_el.css("width");
				dades_usuari_W = dades_el.find(".imc--usuari:first").css("width");
				dades_usuari_H = dades_el.find(".imc--usuari:first").outerHeight();
				dades_clau_H = dades_el.find(".imc--clau:first").outerHeight();
				dades_desa_H = dades_el.find(".imc--desa:first").outerHeight();

				altura_total = (dades_W === dades_usuari_W) ? dades_usuari_H + dades_clau_H + dades_desa_H : dades_desa_H;

				if (dades_el.css("opacity") === "0") {

					element
						.off('.appCap')
						.on('click.appCap', activa);

				}

			},
			activa = function(e) {

				var bt = $(e.target);

				if (dades_el.hasClass("imc--obert") && !bt.hasClass("imc--desa")) {

					dades_el
						.animate(
							{ height: "0px", opacity: 0 }
							,200
							,function() {

								dades_el
									.removeClass("imc--obert");

							});

				} else {

					dades_el
						.animate(
							{ height: altura_total+"px", opacity: 1 }
							,200
							,function() {

								dades_el
									.addClass("imc--obert");

							});

				}

			};

		// verifica
		verifica();

	});
	return this;
}


// appDesconecta

$.fn.appDesconecta = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			inicia = function() {

				element
					.off('.appDesconecta')
					.on('click.appDesconecta', avis);

			},
			avis = function() {

				imc_missatge
					.appMissatge({ boto: element, accio: "desconecta", titol: txtSortirTitol, text: txtSortirText });

			};

		// inicia
		inicia();

	});
	return this;
}


// appTramitacioElimina

$.fn.appTramitacioElimina = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			inicia = function() {

				element
					.off('.appTramitacioElimina')
					.on('click.appTramitacioElimina', obri);

			},
			obri = function() {

				imc_missatge
					.appMissatge({ boto: element, accio: "esborra", titol: txtTramitEliminaTitol, text: txtTramitEliminaText });

			};

		// inicia
		inicia();

	});
	return this;
}


// appMissatge

$.fn.appMissatge = function(options) {
	var settings = $.extend({
		boto: false,
		accio: "informa",
		titol: "",
		text: "",
		araAmaga: false,
		amagaDesdeFons: true,
		alMostrar: function() {},
		alAmagar: function() {},
		alAcceptar: function() {},
		alCancelar: function() {}
	}, options);
	this.each(function(){
		var element = $(this),
			boto = settings.boto,
			accio = settings.accio,
			element_c = element.find(".imc--c:first"),
			element_text = element.find(".imc--text:first"),
			titol_txt = settings.titol,
			text_txt = settings.text,
			araAmaga = settings.araAmaga,
			amagaDesdeFons = settings.amagaDesdeFons,
			alMostrar = settings.alMostrar,
			alAmagar = settings.alAmagar,
			alAcceptar = settings.alAcceptar,
			alCancelar = settings.alCancelar,
			botonera_elm = element.find(".imc--botonera:first"),
			accepta_bt = element.find("button[data-tipus='accepta']:first"),
			cancela_bt = element.find("button[data-tipus='cancela']:first"),
			ico_anim = false,
			inicia = function() {

				if (araAmaga) {

					amaga();
					return;

				}

				accepta_bt
					.off('.appMissatge')
					.on('click.appMissatge', accepta);

				cancela_bt
					.off('.appMissatge')
					.on('click.appMissatge', cancela);

				element
					.find("h2 span")
						.text( titol_txt )
						.end()
					.find(".imc--text:first div")
						.text( text_txt )
						.end()
					.attr("data-accio", accio);

				botonera_elm
					.attr("aria-hidden", "false")
					.show();

				cancela_bt
					.attr("aria-hidden", "false")
					.show();

				if (accio === "informa" || accio === "alerta") {

					cancela_bt
						.attr("aria-hidden", "true")
						.hide();

				} else if (accio === "carregant") {

					botonera_elm
						.attr("aria-hidden", "true")
						.hide();

				}

				anima();

			},
			anima = function() {

				element
					.attr("aria-hidden", "false")
					.addClass("imc--on");

				if (!boto) {

					mostra(false);
					return;

				}

				var bt_T = boto.offset().top,
					bt_L = boto.offset().left,
					bt_W = boto.outerWidth(),
					bt_H = boto.outerHeight();

				var mi_T = element_text.offset().top,
					mi_L = element_text.offset().left,
					mi_W = element_text.outerWidth(),
					mi_H = element_text.outerHeight();

				ico_anim = $("<div>").addClass("imc-missatge-anim").css({ top: bt_T+"px", left: bt_L+"px", width: bt_W+"px", height: bt_H+"px", opacity: 0 }).appendTo( element_c );

				ico_anim
					.animate(
						{ top: "50%", left: "50%", width: mi_W+"px", height: mi_H+"px", marginTop: "-"+(mi_H/2)+"px", marginLeft: "-"+(mi_W/2)+"px", opacity: 1 }
						,200
						,function() {

							mostra(true);

						});

			},
			mostra = function(desDeBoto) {

				alMostrar();

				element_text
					.addClass("imc--on");

				if (desDeBoto) {

					ico_anim
						.addClass("imc--off");

				}

				setTimeout(
					function() {

						if (desDeBoto) {

							ico_anim
								.remove();

						}

					}, 300);

				if (amagaDesdeFons) {

					element_c
						.off('.appMissatge')
						.on('click.appMissatge', amagaFons);

				}

			},
			accepta = function() {

				alAcceptar();

				if (accio === "informa" || accio === "alerta") {

					amaga();

				}

			},
			cancela = function() {

				alCancelar();

				amaga();

			},
			amagaFons = function(e) {

				var el_click = $(e.target),
					estaDins = el_click.closest(".imc--text").length;

				if (!estaDins) {
					amaga();
				}

			},
			amaga = function() {

				alAmagar();

				element
					.attr("aria-hidden", "true")
					.addClass("imc--off");

				setTimeout(
					function() {

						element_text
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


// appFitxerAdjunta

$.fn.appFitxerAdjunta = function(opcions){
	var settings = $.extend({
			contenidor: false
		}, opcions);
	this.each(function(){
		var	elm = $(this),
			adjunta_bt = elm.find(".imc-bt:first"),
			elimina_bt = elm.find(".imc-bt-elimina:first"),
			input_elm = elm.find("input:first"),
			prepara = function() {

				elm
					.off(".appFitxerAdjunta")
					.removeClass("imc-emplenat");

				adjunta_bt
					.off(".appFitxerAdjunta")
					.on("click.appFitxerAdjunta", activa);

				input_elm
					.val("")
					.off(".appFitxerAdjunta")
					.on("change.appFitxerAdjunta", pinta);

				elm
					.off(".appFitxerAdjunta")
					.on("click.appFitxerAdjunta", ".imc-bt-elimina", elimina);

			},
			activa = function() {

				input_elm
					.trigger("click");

			},
			pinta = function() {

				var arxiu_val = input_elm.val(),
					hiHaValor = (arxiu_val !== "") ? true : false;

				elm
					.removeClass("imc-emplenat");

				if (hiHaValor) {

					elm
						.addClass("imc-emplenat");

					if (arxiu_val.indexOf("fakepath") !== -1) {
						arxiu_val = arxiu_val.replace("C:\\fakepath\\", "");
					}



					elm
						.find("p")
							.text( arxiu_val );



				}

			},
			elimina = function(e) {

				elm
					.removeClass("imc-emplenat");

				// input original

				input_elm
					.val("");

				if (input_elm.val() !== "") {
					input_elm.replaceWith( input_elm = input_elm.clone( true ) );
				}

			};

		// prepara
		prepara()

	});
	return this;
}


// appSuport

$.fn.appSuport = function(options) {
	var settings = $.extend({
		contenidor: false
	}, options);
	this.each(function(){
		var element = $(this),
			bt_equip_suport = $("#imc-bt-equip-suport"),
			bt_obri_form = $("#imc-bt-suport--form"),
			el_suport = $("#imc-suport"),
			el_suport_c = el_suport.find(".imc--c:first"),
			el_suport_ajuda = el_suport.find(".imc-s--ajuda:first"),
			el_suport_form = el_suport.find(".imc-s--form:first"),
			el_suport_form_f = el_suport_form.find("form:first"),
			el_suport_missatge = el_suport.find(".imc-s--missatge:first"),
			bt_tanca = el_suport.find(".imc--tanca"),
			bt_torna = el_suport.find(".imc--torna:first"),
			bt_form_envia = $("#imc-bt-suport-form-envia"),
			ico_anim = false,
			envia_ajax = false,
			bt_error_torna = el_suport_missatge.find(".imc--error-torna:first"),
			bt_cancela_enviament = el_suport_missatge.find(".imc--cancela:first"),
			inicia = function() {

				el_suport_form_f
					.attr("action", APP_URL_SUPORT_FORM);

				bt_equip_suport
					.off('.appSuport')
					.on('click.appSuport', obri);

				bt_obri_form
					.off('.appSuport')
					.on('click.appSuport', formulari);

				bt_tanca
					.off('.appSuport')
					.on('click.appSuport', tanca);

				bt_torna
					.off('.appSuport')
					.on('click.appSuport', torna);

				el_suport_form_f
					.off('.appSuport')
					.on('submit.appSuport', envia);

				bt_error_torna
					.off('.appSuport')
					.on('click.appSuport', errorTorna);

				bt_cancela_enviament
					.off('.appSuport')
					.on('click.appSuport', aborta);

				problemes();

			},
			obri = function() {

				// esborra dades form

				el_suport_form_f[0]
					.reset();

				el_suport_form_f
					.find(".imc-bt-elimina:first")
						.trigger("click");

				// anima i mostra

				anima();

			},
			anima = function() {

				el_suport
					.attr("aria-hidden", "false")
					.addClass("imc--on");

				var bt_T = bt_equip_suport.offset().top,
					bt_L = bt_equip_suport.offset().left,
					bt_W = bt_equip_suport.outerWidth(),
					bt_H = bt_equip_suport.outerHeight();

				var mi_T = el_suport_ajuda.offset().top,
					mi_L = el_suport_ajuda.offset().left,
					mi_W = el_suport_ajuda.outerWidth(),
					mi_H = el_suport_ajuda.outerHeight();

				ico_anim = $("<div>").addClass("imc-suport-anim").css({ top: bt_T+"px", left: bt_L+"px", width: bt_W+"px", height: bt_H+"px", opacity: 0 }).appendTo( el_suport_c );

				ico_anim
					.animate(
						{ top: "50%", left: "50%", width: mi_W+"px", height: mi_H+"px", marginTop: "-"+(mi_H/2)+"px", marginLeft: "-"+(mi_W/2)+"px", opacity: 1 }
						,200
						,function() {

							mostra(true);

						});

			},
			mostra = function(desDeBoto) {

				el_suport_ajuda
					.addClass("imc--on");

				if (desDeBoto) {

					ico_anim
						.addClass("imc--off");

				}

				setTimeout(
					function() {

						if (desDeBoto) {

							ico_anim
								.remove();

						}

					}, 300);

				el_suport_c
					.off('.appSuport')
					.on('click.appSuport', amagaFons);

			},
			formulari = function() {

				el_suport_ajuda
					.removeClass("imc--on");

				el_suport_form
					.addClass("imc--on");

			},
			torna = function() {

				el_suport_ajuda
					.addClass("imc--on");

				el_suport_form
					.removeClass("imc--on");

			},
			amagaFons = function(e) {

				var el_click = $(e.target),
					estaDins = el_click.closest(".imc-s--contingut").length;

				if (!estaDins) {
					tanca();
				}

			},
			tanca = function() {

				el_suport
					.attr("aria-hidden", "true")
					.addClass("imc--off");

				setTimeout(
					function() {

						el_suport_ajuda
							.removeClass("imc--on");

						el_suport_form
							.removeClass("imc--on");

						el_suport_missatge
							.removeClass("imc--on imc--enviat-correcte");

						el_suport
							.removeClass("imc--on imc--off");

					}, 200);

			},
			envia = function(e) {

				e.preventDefault();

				// missatge

				el_suport_form
					.removeClass("imc--on");

				el_suport_missatge
					.find("h2 span")
						.text( txtEnviantDades )
						.end()
					.find("div")
						.text( "" )
						.end()
					.removeClass("imc--enviat-correcte imc--enviat-error")
					.addClass("imc--on");

				// envia config

				var	pag_url = APP_URL_SUPORT_FORM,
					formData = new FormData( el_suport_form.find("form:first")[0] );

				// arxius

				var form_annexes = el_suport_form.find("input[type=file]"),
					hiHaArxius = (form_annexes.length) ? true : false;

				if (hiHaArxius) {

					form_annexes
						.each(function(i) {

							var el_annex = $(this);

							if (el_annex.val() !== "") {

								formData
									.append('archivo-'+i, el_annex[0].files[0]);

							}

						});

				}

				// ajax

				if (envia_ajax) {

					envia_ajax
						.abort();

				}

				envia_ajax =
					$.ajax({
						type: "post",
						url: pag_url,
						data: formData,
						processData: false,
						beforeSend: function(xhr) {
				            xhr.setRequestHeader(headerCSRF, tokenCSRF);
				        },
						cache: false,
						contentType: false
					})
					.done(function( data ) {

						var json = data,
							estat = json.estado;

						if (estat === "ERROR") {
							finalitzat({ estat: "error", json: json });
						} else if (estat === "FATAL") {
							finalitzat({ estat: "fatal", json: json });
						} else {
							finalitzat({ estat: "SUCCESS", json: json });
						}

					})
					.fail(function(dades, tipus, errorThrown) {

						consola(dades+" , "+ tipus +" , "+ errorThrown);

						if (tipus === "abort") {
							return false;
						}

						finalitzat({ estat: "fail" });

					});

			},
			aborta = function() {

				envia_ajax
					.abort();

				errorTorna();

			},
			finalitzat = function(opcions) {

				var settings_opcions = $.extend({
					estat: false,
					json: false
				}, opcions);

				var estat = settings_opcions.estat,
					json = settings_opcions.json;

				var titol = (estat === "SUCCESS") ? txtDadesEnviadesCorrecteTitol : (estat === "fail") ? txtDadesEnviadesErrorTitol : json.mensaje.titulo,
					text = (estat === "SUCCESS") ? txtDadesEnviadesCorrecteText : (estat === "fail") ? txtDadesEnviadesErrorText : json.mensaje.texto,
					clase = (estat === "SUCCESS") ? "imc--enviat-correcte" : "imc--enviat-error";

				el_suport_missatge
					.find("h2 span")
						.text( titol )
						.end()
					.find("div")
						.text( text )
						.end()
					.addClass( clase );

				envia_ajax = false;

				consola("finalitzat");

			},
			errorTorna = function() {

				el_suport_form
					.addClass("imc--on");

				el_suport_missatge
					.removeClass("imc--on");

			},
			problemes = function() {

				var problema_el = el_suport_form.find(".imc-el--problema:first"),
					selector_el = problema_el.find("select:first"),
					explicacio_el = problema_el.find(".imc--problema-explicacio:first");

				var alSeleccionar = function() {

						var sel = $(this),
							sel_val = sel.val();

						explicacio_el
							.find("li.imc--visible")
								.removeClass("imc--visible")
								.attr("aria-hidden", "true")
								.end()
							.find("li[data-valor='"+sel_val+"']")
								.addClass("imc--visible")
								.attr("aria-hidden", "false");

					};

				selector_el
					.off('.appSuport')
					.on('change.appSuport', alSeleccionar);

			};

		// inicia
		inicia();

	});
	return this;
}


// consola

function consola(text) {
	if (typeof console !== "undefined") {
		console.log(text);
	}
}

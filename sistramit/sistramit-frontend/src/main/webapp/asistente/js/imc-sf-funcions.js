// JS

var APP_ACCESSIBILITAT_HTML = false;


// estaBuit

$.fn.estaBuit = function() {

	var valor = this[0],
		estaBuit = false;

	if (valor === "" || valor === null || valor === "null" || valor === false || valor === 0) {
		estaBuit = true;
	}

	return estaBuit;

}


// nomesText

$.fn.nomesText = function() {

	var valor = this[0];

	return ($(valor).length) ? $(valor).text() : valor;

}


// appCap

$.fn.appCap = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			inicia = function() {

				if (imc_cap_fixe.css("position") === "fixed") {

					var cap_fixe_ALTURA = imc_cap_fixe.height();

					imc_contingut
						.css("paddingTop", cap_fixe_ALTURA+"px");

				} else {

					imc_contingut
						.removeAttr("style");

				}

			};
		
		// inicia
		inicia();
		
	});
	return this;
}


// appClauDesa

$.fn.appClauDesa = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			inicia = function() {

				var autenticacio_val = imc_cap.attr("data-autenticacio");

				element
					.data("data-autenticacio", autenticacio_val);

				if (autenticacio_val === "a") {

					element
						.off('.appClauDesa')
						.on('click.appClauDesa', desa);

				}

			},
			desa = function() {

				if (element.data("data-autenticacio") === "a") {

					document.location = APP_TRAMIT_CLAU_DESCARREGA + "?id=" + APP_JSON_TRAMIT_T.idSesion;

				}

			};
		
		// inicia
		inicia();
		
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
			avis = function(e) {

				var bt = $(this);

				var esPersistent = (APP_JSON_TRAMIT_T.persistente === "s") ? true : false
					,esAnonim = (APP_JSON_TRAMIT_T.autenticacion === "a") ? true : false;

				var txt_text = (!esAnonim) ? txtSortirTextPotTornar : txtSortirText;

				if (!esPersistent) {

					txt_text = txtSortirTextNoPersistent;

				}

				imc_missatge
					.appMissatge({ boto: element, accio: "desconecta", titol: txtSortirTitol, text: txt_text, bt: bt, alAcceptar: function() { document.location = APP_TRAMIT_DESCONECTA; } });

			};
		
		// events

		element
			.off('.appDesconecta')
			.on('click.appDesconecta', avis);
		
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
			envia_ajax = false,
			inicia = function() {

				element
					.off('.appTramitacioElimina')
					.on('click.appTramitacioElimina', obri);

			},
			obri = function(e) {

				var bt = $(this);

				imc_missatge
					.appMissatge({ boto: element, accio: "esborra", titol: txtTramitEliminaTitol, text: txtTramitEliminaText, bt: bt, alAcceptar: function() { eliminant(); } });

			},
			eliminant = function() {

				// missatge carregant
				
				imc_missatge
					.appMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtTramitEliminant });
				
				// envia

				envia();

			},
			envia = function() {

				var pag_url = APP_TRAMIT_CANCELA,
					pag_dades = { id: APP_JSON_TRAMIT_T.idSesion };

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

							document.location = data.url;

						} else {

							consola("carregaJSON desde JSON");

							error({ titol: data.mensaje.titulo, text: data.mensaje.texto });

						}
						
					})
					.fail(function(dades, tipus, errorThrown) {

						consola(dades+" , "+ tipus +" , "+ errorThrown);

						if (tipus === "abort") {
							return false;
						}
						
						consola("carregaJSON desde FAIL");
						error();
						
					});

			},
			error = function(opcions) {

				var settings_opcions = $.extend({
						titol: txtTramitEliminaErrorTitol,
						text: txtTramitEliminaErrorText
					}, opcions);

				var titol = settings_opcions.titol,
					text = settings_opcions.text;

				imc_missatge
					.appMissatge({ accio: "error", titol: titol, text: text });

				envia_ajax = false;

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
			debug: false,
			bt: false,
			araAmaga: false,
			amagaDesdeFons: true,
			alMostrar: function() {},
			alAmagar: function() {},
			alAcceptar: function() {},
			alCancelar: function() {},
			alTancar: function() {}
		}, options);
	this.each(function(){
		var element = $(this),
			boto = settings.boto,
			accio = settings.accio,
			element_c = element.find(".imc--c:first"),
			element_text = element.find(".imc--text:first"),
			titol_txt = settings.titol,
			text_txt = settings.text,
			debug_txt = settings.debug,
			bt = settings.bt,
			araAmaga = settings.araAmaga,
			amagaDesdeFons = settings.amagaDesdeFons,
			alMostrar = settings.alMostrar,
			alAmagar = settings.alAmagar,
			alAcceptar = settings.alAcceptar,
			alCancelar = settings.alCancelar,
			alTancar = settings.alTancar,
			botonera_elm = element.find(".imc--botonera:first"),
			accepta_bt = element.find("button[data-tipus='accepta']:first"),
			cancela_bt = element.find("button[data-tipus='cancela']:first"),
			tanca_bt = element.find("button[data-tipus='tanca']:first"),
			ico_anim = false,
			inicia = function() {

				if (araAmaga) {

					amaga();
					return;

				}

				accepta_bt
					.off('.appMissatge')
					.one('click.appMissatge', accepta);

				cancela_bt
					.off('.appMissatge')
					.one('click.appMissatge', cancela);

				tanca_bt
					.off('.appMissatge')
					.one('click.appMissatge', tanca);

				element
					.find("h2 span")
						.text( titol_txt )
						.end()
					.attr("data-accio", accio);

				// text HTML

				if (text_txt !== "") {

					text_txt = text_txt.replace("script", "");

				}

				element
					.find(".imc--explicacio:first")
						.html( text_txt );

				// debug HTML

				element
					.find(".imc--desenvolupadors:first")
						.attr("aria-hidden", "true")
						.end()
					.find(".imc--desenvolupadors:first p")
						.text( "" );

				if (debug_txt) {

					element
						.find(".imc--desenvolupadors:first")
							.attr("aria-hidden", "false")
							.end()
						.find(".imc--desenvolupadors:first p")
							.text( debug_txt );

				}

				// mostrem

				element_c
					.off('.appMissatge');

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
						.on('click.appMissatge', amagaFons);

				}

				element
					.appPopupTabula();

			},
			accepta = function() {

				alAcceptar();

				if (accio === "informa" || accio === "alerta" || accio === "correcte") {
					
					amaga();

				}

			},
			cancela = function() {
				
				alCancelar();

				amaga();

			},
			tanca = function() {

				alTancar();

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
					.addClass("imc--off")
					.appPopupTabula({ accio: "finalitza" });

				setTimeout(
					function() {

						element_text
							.removeClass("imc--on");

						element
							.removeClass("imc--on imc--off");


					}, 200);

				// enfoquem al botó llançador

				if (bt) {

					/*var estaEnPopup = (bt.closest(".imc--popup").length) ? true : false;

					if (estaEnPopup) {

						bt
							.closest(".imc--popup")
								.appPopupTabula({ enfocaEn: bt });

					} else {*/

						bt
							.focus();

					//}


				}

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

					if (arxiu_val.indexOf("\\") !== -1) {
						arxiu_val = arxiu_val.split('\\')[arxiu_val.split('\\').length - 1];
					}

					elm
						.find("p")
							.text( arxiu_val );

				}
				
			},
			elimina = function(e) {
				
				elm
					.removeClass("imc-emplenat");

				elm
					.parent()
						.find(".imc--annex-error")
							.remove();

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
			input_file = el_suport.find(".imc-input-type:first input:first"),
			url_fatal = false,
			inicia = function() {

				// events

				el_suport_form_f
					.attr("action", APP_TRAMIT_SUPORT);

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

				el_suport_missatge
					.off(".appFatal");

				// tipus arxius

				var json_annexe = APP_JSON_TRAMIT_E.soporte.anexo || false;

				if (json_annexe) {

					input_file
						.attr({ "data-extensions": json_annexe.extensiones, "data-mida": json_annexe.tamanyo });

					var txt_extensions = ""
						,el_suport_ext = json_annexe.extensiones.split(",")
						,el_suport_ext_size = el_suport_ext.length;

					$(el_suport_ext)
						.each(function(i) {

							var ex = this;

							txt_extensions += (i < el_suport_ext_size-1) ? ex.toUpperCase() + ", " : ex.toUpperCase();

						});

					el_suport
						.find(".imc-el--annexe:first label:first")
							.append( ". " + txtExtensionsPermeses + " " + txt_extensions +". " + txtExtensionsMidaMaxima + " " + json_annexe.tamanyo );

				}

				// problemes?

				problemes();

			},
			obri = function() {

				// esborra dades form

				el_suport_form_f[0]
					.reset();

				el_suport_form_f
					.find(".imc-bt-elimina:first")
						.trigger("click");

				el_suport_missatge
					.off(".appFatal");

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

				el_suport_form
					.removeClass("imc--on")
					.attr("aria-hidden", "true");

				el_suport_ajuda
					.addClass("imc--on")
					.attr("aria-hidden", "false");

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

				// tabulador

				el_suport
					.appPopupTabula();

			},
			formulari = function() {

				$("#nif, #nombre")
					.val("");

				if (APP_JSON_TRAMIT_T.autenticacion === "c") {

					$("#nif")
						.val( APP_JSON_TRAMIT_U.nif )
						.attr("readonly", "readonly");

					$("#nombre")
						.val( APP_JSON_TRAMIT_U.nombre + " " + APP_JSON_TRAMIT_U.apellido1 + " " + APP_JSON_TRAMIT_U.apellido2 )
						.attr("readonly", "readonly");

				} else {

					$("#nif, #nombre")
						.removeAttr("readonly");

				}

				el_suport_ajuda
					.removeClass("imc--on")
					.attr("aria-hidden", "true");

				el_suport_form
					.addClass("imc--on")
					.attr("aria-hidden", "false");

				// tabulador

				el_suport
					.appPopupTabula();


				// provar HTML5 support

				el_suport_form
					.find("input[required]")
						.off(".suportValidacio")
						.on("invalid.suportValidacio", function(e) {

							var input_invalid = e.target;

							if (input_invalid.validity.valueMissing) {
								input_invalid.setCustomValidity( txtSuport_campObligatori );
							}

							$(input_invalid)
								.addClass("imc--f-suport-error");

						})
						.on("change.suportValidacio", function(e) {

							var input_invalid = e.target;

							input_invalid.setCustomValidity('');
							
						});

			},
			torna = function() {

				el_suport_ajuda
					.addClass("imc--on")
					.attr("aria-hidden", "false");

				el_suport_form
					.removeClass("imc--on")
					.attr("aria-hidden", "true");

				// tabulador

				el_suport
					.appPopupTabula();

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
							.removeClass("imc--on")
							.attr("aria-hidden", "true");

						el_suport_form
							.removeClass("imc--on")
							.attr("aria-hidden", "false");

						el_suport_missatge
							.removeClass("imc--on imc--enviant imc--enviat-correcte");

						el_suport
							.removeClass("imc--on imc--off")
							.appPopupTabula({ accio: "finalitza" });

						bt_equip_suport
							.focus();

					}, 200);

			},
			envia = function(e) {

				e.preventDefault();

				// revisa extensions

				if (input_file.val() !== "") {

					var arxiu = input_file[0].files[0]
						,arxiu_nom = arxiu.name
						,arxiu_extensio = arxiu_nom.split('.')[arxiu_nom.split('.').length - 1].toLowerCase()
						,arxiu_mida = arxiu.size
						,arxiu_mida_MB = (arxiu.size / 1048576).toFixed(2)
						,arxiu_mida_KB = (arxiu.size / 1024).toFixed(2);

					var tipus_posibles = input_file.attr("data-extensions")
						,tipus_posibles_arr = tipus_posibles.split(",")
						,esTipusPosible = false;

					var text_error = ""
						,revisa_error = false;

					$(tipus_posibles_arr)
						.each(function() {

							var ext_tipus = this;

							esTipusPosible = (arxiu_extensio.toUpperCase() == ext_tipus.toUpperCase()) ? true : false;

							if (esTipusPosible) {
								return false;
							}

						});
						
					if (!esTipusPosible && tipus_posibles !== "null") {

						text_error = txtErrorTipusNoPermes;
						revisa_error = true;

					}

					var mida_max = input_file.attr("data-mida")
						,esMB = (mida_max.indexOf("MB") !== -1) ? true : false
						,mida_max_num = (esMB) ? parseInt( mida_max.replace("MB", ""), 10 ) : parseInt( mida_max.replace("KB", ""), 10 )

					if (esMB) {

						esMidaPosible = (mida_max_num > arxiu_mida_MB) ? true : false;

					} else {

						esMidaPosible = (mida_max_num > arxiu_mida_KB) ? true : false;

					}

					if (!esMidaPosible && input_file.attr("data-mida") !== "null") {

						text_error = txtErrorMidaGran;
						revisa_error = true;

					}

					if (revisa_error && text_error) {

						el_suport
							.find(".imc--annex-error")
								.remove();

						$("<div>")
							.addClass("imc--annex-error")
							.text( text_error )
							.appendTo( el_suport.find(".imc-el--annexe:first") );

					 	return;
					}

				}


				// revisa NIF

				// dni, nie, nifOtros, nifPJ

				var nif_val = $("#nif").val();

				if (nif_val !== "") {

					var dni_valid = ( appValidaIdentificadorSuport.dni(nif_val) ) ? true : false
						,nie_valid = ( appValidaIdentificadorSuport.nie(nif_val) ) ? true : false
						,nifOtros_valid = ( appValidaIdentificadorSuport.nifOtros(nif_val) ) ? true : false
						,nifPJ_valid = ( appValidaIdentificadorSuport.nifPJ(nif_val) ) ? true : false;

					if (!dni_valid && !nie_valid && !nifOtros_valid && !nifPJ_valid) {

						var nif_error = function() {

								$("#nif")
									.addClass("imc--f-suport-error")
									.focus()
									.off(".nifError")
									.on("keyup.nifError", function() {

										$(this)
											.removeClass("imc--f-suport-error");
											
									});

							};

						imc_missatge
							.appMissatge({ accio: "error", titol: txtSuportNIF_errorTitol, text: txtSuportNIF_errorText, alTancar: function() { nif_error(); } });

						return;

					}

				}

				$("#nif")
					.removeClass("imc--f-suport-error");


				// revisa TELF

				var telf_val = $("#telefono").val();

				var regexp = new RegExp( '(6|7|8|9)([0-9]){8}' )
					,esTelfCorrecte = (regexp.test(telf_val)) ? true : false;

				if (!esTelfCorrecte) {

					var telf_error = function() {

							$("#telefono")
								.addClass("imc--f-suport-error")
								.focus()
								.off(".telfError")
								.on("keyup.telfError", function() {

									$(this)
										.removeClass("imc--f-suport-error");
										
								});

						};

					imc_missatge
						.appMissatge({ accio: "error", titol: txtSuportTelf_errorTitol, text: txtSuportTelf_errorText, alTancar: function() { telf_error(); } });

					return;

				}

				$("#telefono")
					.removeClass("imc--f-suport-error");


				// revisa correu

				var correu_val = $("#email").val();

				var esCorreuCorrecte = (/^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\.)+[a-zA-Z]{2,4}$/.test(correu_val)) ? true : false;;

				if (!esCorreuCorrecte) {

					var correu_error = function() {

							$("#email")
								.addClass("imc--f-suport-error")
								.focus()
								.off(".correuError")
								.on("keyup.correuError", function() {

									$(this)
										.removeClass("imc--f-suport-error");
										
								});

						};

					imc_missatge
						.appMissatge({ accio: "error", titol: txtSuportCorreu_errorTitol, text: txtSuportCorreu_errorText, alTancar: function() { correu_error(); } });

					return;

				}

				$("#email")
					.removeClass("imc--f-suport-error");


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
					.removeClass("imc--enviat-correcte imc--enviat-error imc--enviat-fatal")
					.addClass("imc--on imc--enviant");


				// envia config

				var	pag_url = APP_TRAMIT_SUPORT,
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
						cache: false,
						contentType: false,
						timeout: APP_TIMEOUT,
						beforeSend: function(xhr) {
							xhr.setRequestHeader(headerCSRF, tokenCSRF);
						}
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
					.removeClass("imc--enviant")
					.addClass( clase );

				envia_ajax = false;

				if (estat === "fatal") {

					url_fatal = json.url;

					el_suport_missatge
						.addClass("imc--enviat-fatal")
						.off(".appFatal")
						.on("click.appFatal", "button[data-tipus='reinicia']", reinicia);

				}

			},
			reinicia = function(e) {

				setTimeout(
					function() {

						imc_contenidor
							.html("");

					}
					,200
				);

				document.location = url_fatal;

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


// valida identificador SUPORT

var appValidaIdentificadorSuport = (function(){

	var LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";

	var calcularLetraDni = function(valor) {
		var dni = parseInt(valor, 10);
		var modulo = dni % 23;
		var letra = LETRAS_DNI.charAt(modulo);
		return letra;
	};

	var obtenerDigitos = function(valor) {
		var digitos = "";
		for (i = 0; i < valor.length; i++) {
			if (!isNaN(valor.charAt(i))) {
				digitos += valor.charAt(i);
			}
        }
		return digitos;
	}

	// dni, nie, nifOtros, nifPJ, nss  (abans: nif, cif, nie, nss)

	return {
		dni: function(valor) {
			valor = valor.toUpperCase();

			if (valor.length != 9) {
				return false;
			}

			var patronNif = "^[0-9]{0,8}[" + LETRAS_DNI + "]{1}$";
			var regExp = new RegExp(patronNif);
			if (!regExp.test(valor)) {
				return false;
			}
			var digitos = obtenerDigitos(valor);
			var letra = calcularLetraDni(digitos);

			return (valor.charAt(8) == letra);

		},
		nie: function(valor) {
			valor = valor.toUpperCase();

			if (valor.length != 9) {
				return false;
			}

			var patronNie = "^[X|Y|Z][0-9]{1,8}[A-Z]{1}$";
			var regExp=new RegExp(patronNie);
			if (!regExp.test(valor)) {
				return false;
			}

			var numero = "0";
			if (valor.charAt(0) == "Y") {
				numero = "1";
			} else if (valor.charAt(0) == "Z"){
				numero = "2";
			}

			var digitos = obtenerDigitos(valor);

			var letra = calcularLetraDni(numero + digitos);

			return (valor.charAt(8) ==  letra);

		},
		nifOtros: function(valor) {
			valor = valor.toUpperCase();

			if (valor.length != 9) {
				return false;
			}

			var patronNifOtros = "^[K|L|M][0-9]{1,8}[A-Z]{1}$";
			var regExp = new RegExp(patronNifOtros);
			if (!regExp.test(valor)) {
				return false;
			}
			var digitos = obtenerDigitos(valor);
			var letra = calcularLetraDni(digitos);

			return (valor.charAt(8) == letra);

		},
		nifPJ: function(valor) {
			valor = valor.toUpperCase();

			var patronCif = "^[ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{7}([0-9]||[ABCDEFGHIJ]){1}$";
			var regExp = new RegExp(patronCif);
			if (!regExp.test(valor)) {
				return false;
			}

			var codigoControl = valor.substring(valor.length - 1, valor.length);

			var v1 = [ 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 ];
			var v2 = [ "J", "A", "B", "C", "D", "E", "F", "G", "H", "I" ];

			var suma = 0;
            for (i = 2; i <= 6; i += 2) {
            	suma += v1[parseInt(valor.substring(i - 1, i), 10)];
                suma += parseInt(valor.substring(i, i + 1));
            }

            suma += v1[parseInt(valor.substring(7, 8))];
            suma = (10 - (suma % 10));
            if (suma == 10) {
                suma = 0;
            }
            var letraControl = v2[suma];
            res = (codigoControl == (suma + "") || codigoControl.toUpperCase() == letraControl);

            return res;

		},
		nif: function(valor) {

			// dni

			valor = valor.toUpperCase();

			if (valor.length != 9) {
				return false;
			}

			var esPatronNIF = false
				,esPatronNIFotros = false
				,esLletraNumero = false;

			var patronNif = "^[0-9]{0,8}[" + LETRAS_DNI + "]{1}$";

			var regExp = new RegExp(patronNif);

			if (regExp.test(valor)) {
				esPatronNIF = true;
			}

			var patronNifOtros = "^[K|L|M][0-9]{1,8}[A-Z]{1}$";

			var regExp = new RegExp(patronNifOtros);

			if (regExp.test(valor)) {
				esPatronNIFotros = true;
			}


			var digitos = obtenerDigitos(valor);
			var letra = calcularLetraDni(digitos);

			if (valor.charAt(8) == letra) {
				esLletraNumero = true;
			}

			// resultat

			var resultat = ((esPatronNIF || esPatronNIFotros) && esLletraNumero) ? true : false;

			return resultat;

		}
	}

})();


// accessibilitat

$.fn.appAccessibilitat = function(options) {
	var settings = $.extend({
		contenidor: false
	}, options);
	this.each(function(){
		var element = $(this),
			verifica = function() {


				if (APP_ACCESSIBILITAT_HTML) {

					mostra();

				} else {

					carrega();

				}

			},
			carrega = function() {

				// carrega

				$.when(
		
					$.get(APP_ + "css/imc-accessibilitat.css")
					,$.get(APP_ + "html/imc-accessibilitat.html")
					,$.getScript(APP_ + "js/imc-accessibilitat.js?" + APP_VERSIO)

				).then(

					function( cssAcc, htmlAcc) {

						// estils

						$("<style>")
							.html( cssAcc[0] )
								.appendTo( imc_head );

						// html

						var literals_acc = {
							txtAccessibilitat: txtAccessibilitat
							,txtAccCompromisTitol: txtAccCompromisTitol
							,txtAccCompromisText_1: txtAccCompromisText_1
							,txtAccCompromisText_2: txtAccCompromisText_2
							,txtEquipSuport: txtEquipSuport
							,txtAccCompromisContacte: txtAccCompromisContacte
							,txtAccIconaTitle: txtAccIconaTitle
							,txtAccIconaAlt: txtAccIconaAlt
							,txtAccCanviaTitol: txtAccCanviaTitol
							,txtAccCanviaText_1: txtAccCanviaText_1
							,txtEstilsDefecte: txtEstilsDefecte
							,txtEstilsAltContrast: txtEstilsAltContrast
							,txtCanvia: txtCanvia
							,txtAccTecnologiaTitol: txtAccTecnologiaTitol
							,txtAccTecnologiaText_1: txtAccTecnologiaText_1
							,txtAccTecnologiaText_2: txtAccTecnologiaText_2
							,txtAccEinesTitol: txtAccEinesTitol
							,txtAccEinesText_1: txtAccEinesText_1
							,txtAccTorna: txtAccTorna
							,pasActualId: JSON_PAS_ACTUAL.datos.actual.id
						};

						APP_ACCESSIBILITAT_HTML = Mark.up(htmlAcc[0], literals_acc);

						imc_contingut_c
							.append( APP_ACCESSIBILITAT_HTML );

						// mostra

						mostra();

					}

				).fail(

					function(dades, tipus, errorThrown) {

						if (tipus === "abort") {
							return false;
						}

						consola("Accessibilitat càrrega arxius: error des de FAIL");

						imc_contenidor
							.errors({ estat: "fail" });

					}

				);

			},
			mostra = function() {

				imc_contenidor
					.addClass("imc--mostra-acc");

				imc_cap_c
					.appCap();

				appAccessibilitatInicia();

			};
		
		// verifica
		verifica();
		
	});
	return this;
}


// errors

$.fn.errors = function(options) {
	var settings = $.extend({
			estat: false,
			titol: txtErrorGeneralTitol,
			text: txtErrorGeneralText,
			debug: false,
			url: APP_
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

					imc_contenidor
						.remove();

				}

				imc_missatge
					.appMissatge({ accio: "error", titol: titol, text: text, debug: debug, alTancar: function() { document.location = url; } });

			};
		
		// mostra
		mostra();
		
	});
	return this;
}


// consola

function consola(text) {
	if (typeof console !== "undefined") {
		console.log(text);
	}
}


// appPopupTabula

$.fn.appPopupTabula = function(options) {

	var settings = $.extend({
			element: ""
			,accio: false
			,enfocaEn: false
		}, options);

	this.each(function(){
		var element = $(this)
			,accio = settings.accio
			,enfocaEn = settings.enfocaEn
			,el_num = 0
			,elems_tab = []
			,elems_tab_size = 0
			,esMissatge = false
			,esPopupDocument = false
			,inicia = function() {

				// finalitzem?

				if (accio === "finalitza") {

					element
						.off(".appPopupTabula");

					return;

				}


				// iniciem vars

				el_num = 0;
				elems_tab = [];
				elems_tab_size = 0;


				// es missatge?

				esMissatge = (element.closest(".imc-missatge").length) ? true : false;


				// es formulari?

				esFormulari = (element.closest(".imc-forms-contenidor").length) ? true : false;


				// es popup de document?

				esPopupDocument = (element.closest(".imc-document").length) ? true : false;


				// es LOPD de registrar?

				esPopupLOPD = (element.closest(".imc-popup--lopd").length) ? true : false;


				// es suport?

				esSuport = (element.closest(".imc-suport").length) ? true : false;


				// pinta

				setTimeout(
					function() {

						pinta();

					}
					,100
				);

			},
			pinta = function() {


				// es un missatge

				if (esMissatge) {

					element
						.find("button")
							.attr("data-tabula", "si");

				}


				// es un formulari

				if (esFormulari) {

					// és un formulari

					f_elms = element.find(".imc-element");

					f_elms
						.each(function() {

							var f_el = $(this)
								,f_el_tipus = f_el.attr("data-tipus")
								,f_el_contingut = f_el.attr("data-contingut")
								,f_el_ocult = f_el.attr("data-ocult");

							if (f_el_ocult === "s") {
								return;
							}

							if (f_el_tipus === "texto") {

								// text

								f_el
									.find("input:first")
										.attr("data-tabula", "si")
										.end()
									.find("textarea:first")
										.attr("data-tabula", "si");

							} else if (f_el_tipus === "captcha") {

								// captcha

								f_el
									.find("input, button")
										.attr("data-tabula", "si");

							} else if (f_el_tipus === "check") {

								// check únic

								f_el
									.find("input:first")
										.attr("data-tabula", "si");

							} else if (f_el_tipus === "verificacion") {

								// check únic

								f_el
									.find("label:first")
										.removeAttr("tabindex")
										.end()
									.find("input:first")
										.attr("data-tabula", "si");

							} else if (f_el_tipus === "listaElementos") {

								// llista d'elements

								f_el
									.find("button, input[type=radio]")
										.attr("data-tabula", "si");

							} else if (f_el_contingut === "d") {

								// selector

								f_el
									.find("a.imc-select:first, button.imc--bt-reset")
										.attr("data-tabula", "si");

							} else if (f_el_contingut === "m") {

								// llista checks

								f_el
									.find("input[type=checkbox]")
										.attr("data-tabula", "si");

							} else if (f_el_contingut === "u") {

								// llista checks

								f_el
									.find("input[type=radio]")
										.attr("data-tabula", "si");

							} else if (f_el_contingut === "a") {

								// llista checks

								f_el
									.find("input[type=text]")
										.attr("data-tabula", "si");

							}

						});

					// i la botonera de navegació i ajuda

					element
						.find("button")
							.attr("data-tabula", "si");

				}


				// popup document?

				if (esPopupDocument) {

					element
						.find("input[type=text]:visible, input[type=checkbox]:visible, .imc-bt-anexa:visible, button:visible")
							.attr("data-tabula", "si");

				}


				// popup LOPD?

				if (esPopupLOPD) {

					element
						.find("a:visible, button:visible")
							.attr("data-tabula", "si");

				}


				// suport

				if (esSuport) {

					// capa amagada

					var capa_mostrada = element.find("div[aria-hidden=false]:first")
						,capa_amagada = element.find("div[aria-hidden=true]:first");

					capa_mostrada
						.find("a, input[type=text], input[type=email], select, textarea, button")
							.attr("data-tabula", "si");
					
					capa_amagada
						.find("a, input[type=text], input[type=email], select, textarea, button")
							.removeAttr("data-tabula")
							.removeAttr("data-tabpos");

				}

				// activem

				activa();

			},
			activa = function() {

				elems_tab = element.find("*[data-tabula=si]:visible:not(:disabled)");
				elems_tab_size = elems_tab.length;

				if (elems_tab_size) {

					elems_tab
						.each(function(i) {

							var el = $(this);

							el
								.attr("data-tabpos", i+1);
							
						});

					elems_tab
						.splice(0, 0, element);

					element
						.off(".appPopupTabula")
						.on("focus.appPopupTabula", "*[data-tabula]", reposiciona)
						.on("focus.appPopupTabula", reposiciona)
						.on("keydown.appPopupTabula", tabula)
						.attr("data-tabpos", 0);

				}

				// enfoquem en algun element?

				if (enfocaEn) {

					enfocaEn
						.focus();

				} else {

					element
						.focus();

				}

			},
			reposiciona = function(e) {

				var inp_el = $(this)
					,in_tabpos = parseInt( inp_el.attr("data-tabpos"), 10);

				el_num = in_tabpos;

			},
			tabula = function(e) {

				var tecla = e.keyCode
					,esShift = !!e.shiftKey;

				if ( esShift && tecla === 9) {

					e.preventDefault();

					el_num--;

					if (el_num < 0) {
						el_num = elems_tab_size;
					}

					elems_tab[el_num]
						.focus();

				} else if ( !esShift && tecla === 9){
				
					e.preventDefault();

					el_num++;

					if (el_num > elems_tab_size) {
						el_num = 0;
					}

					elems_tab[el_num]
						.focus();

				}

			};
		
		// inicia

		inicia();
		
	});

	return this;
}

// JS

var APP_ACCESSIBILITAT_HTML = false;


// appCap

$.fn.appCap = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			inicia = function() {

				var cap_fixe_ALTURA = imc_cap_fixe.height();

				imc_contingut
					.css("paddingTop", cap_fixe_ALTURA+"px");

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
			inicia = function() {

				element
					.off('.appDesconecta')
					.on('click.appDesconecta', avis);

			},
			avis = function() {

				var esPersistent = (APP_JSON_TRAMIT_T.persistente === "s") ? true : false
					,esAnonim = (APP_JSON_TRAMIT_T.autenticacion === "a") ? true : false;

				var txt_text = (!esAnonim) ? txtSortirTextPotTornar : txtSortirText;

				if (!esPersistent) {

					txt_text = txtSortirTextNoPersistent;

				}

				imc_missatge
					.appMissatge({ boto: element, accio: "desconecta", titol: txtSortirTitol, text: txt_text, alAcceptar: function() { document.location = APP_TRAMIT_DESCONECTA; } });

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
			envia_ajax = false,
			inicia = function() {

				element
					.off('.appTramitacioElimina')
					.on('click.appTramitacioElimina', obri);

			},
			obri = function() {

				imc_missatge
					.appMissatge({ boto: element, accio: "esborra", titol: txtTramitEliminaTitol, text: txtTramitEliminaText, alAcceptar: function() { eliminant(); } });

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
					.on('click.appMissatge', accepta);

				cancela_bt
					.off('.appMissatge')
					.on('click.appMissatge', cancela);

				tanca_bt
					.off('.appMissatge')
					.on('click.appMissatge', tanca);

				element
					.find("h2 span")
						.text( titol_txt )
						.end()
					.attr("data-accio", accio);

				// text HTML

				if (text_txt) {

					text_txt = text_txt.replace("script", "");

					element
						.find(".imc--text:first div")
							.html( text_txt );

				}

				element_c
					.off('.appMissatge');
				/*
				botonera_elm
					.attr("aria-hidden", "false")
					.show();

				cancela_bt
					.attr("aria-hidden", "false")
					.show();

				tanca_bt
					.attr("aria-hidden", "true")
					.hide();

				if (accio === "informa" || accio === "alerta") {

					cancela_bt
						.attr("aria-hidden", "true")
						.hide();

				} else if (accio === "carregant") {

					botonera_elm
						.attr("aria-hidden", "true")
						.hide();

				}*/

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

				$("#nif, #nombre")
					.val("");

				if (APP_JSON_TRAMIT_T.autenticacion === "c") {

					$("#nif")
						.val( APP_JSON_TRAMIT_U.nif );

					$("#nombre")
						.val( APP_JSON_TRAMIT_U.nombre + " " + APP_JSON_TRAMIT_U.apellido1 + " " + APP_JSON_TRAMIT_U.apellido2 );

				}

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
							.removeClass("imc--on imc--enviant imc--enviat-correcte");

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
					,$.getScript(APP_ + "js/imc-accessibilitat.js")

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
							,pasActualId: JSON_PAS_ACTUAL.datos.actual.id
						};

						APP_ACCESSIBILITAT_HTML = Mark.up(htmlAcc[0], literals_acc);

						imc_contingut_c
							.append( APP_ACCESSIBILITAT_HTML );

						// mostra

						mostra();

					}

				).fail(

					function() {

						alert("Accessibilitat: error caregant HTML, CSS i JS");

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
			url: APP_
	}, options);
	this.each(function(){
		var element = $(this),
			estat = settings.estat,
			titol = settings.titol,
			text = settings.text,
			url = settings.url,
			mostra = function() {

				if (estat === "fail" || estat === "FATAL") {

					imc_contenidor
						.remove();

				}

				imc_missatge
					.appMissatge({ accio: "error", titol: titol, text: text, alTancar: function() { document.location = url; } });

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

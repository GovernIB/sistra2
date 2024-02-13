// appFormsCampClicat

var FORMS_CAMP_CLICAT
	FORMS_CAMP_CLICAT_ID = false;

$.fn.appFormsCampClicat = function(options) {

	var settings = $.extend({
			referent: $(window)
		}, options);

	this.each(function(){

		var element = $(this)
			,clicat = function() {

				var elm = $(this);

				FORMS_CAMP_CLICAT = elm;
				FORMS_CAMP_CLICAT_ID = elm.attr("data-id");

				console.log("appFormsCampClicat: CLICK en " + FORMS_CAMP_CLICAT_ID);

			}
			,tabula = function(e) {

				var tecla = e.keyCode;

				if (tecla === 9) {

					FORMS_CAMP_CLICAT = false;
					FORMS_CAMP_CLICAT_ID = false;

					console.log("appFormsCampClicat: " + tecla + " - todo a FALSE");

				}

			};

		// clicat

		element
			.off('.appFormsCampClicat')
			.on('click.appFormsCampClicat', ".imc-element", clicat)
			.on('keydown.appFormsCampClicat', tabula);

	});

	return this;
}

// /appFormsCampClicat




// Avalua un camp

// appFormsAvalua

$.fn.appFormsAvalua = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			input = false,
			input_element = false,
			input_id = false,
			envia_ajax = false,
			desDeTaula = false,
			avalua_json = false,
			valor_inicial_txt = false,
			selector_valor_ = false,
			inicia = function() {

				element
					.off(".appFormsAvalua")
					.on("focus.appFormsAvalua", "div[data-tipus='texto'] input, div[data-tipus='texto'] textarea", preevalua)
					.on("blur.appFormsAvalua", "div[data-tipus='texto'] input, div[data-tipus='texto'] textarea", selecciona)
					.on("click.appFormsAvalua", "div[data-tipus='selector'][data-contingut='d'] .imc-select a.imc-select", preselecciona)
					.on("click.appFormsAvalua", "div[data-tipus='selector'][data-contingut='d'] .imc-select-submenu ul a", selecciona)
					.on("click.appFormsAvalua", "fieldset[data-tipus='selector'] label", selecciona)
					.on("click.appFormsAvalua", "div[data-type='check'] .imc-input-check", selecciona)
					.on("click.appFormsAvalua", "button[data-accio=seleccio-elimina], .imc--selector-opcions-ajax button", selecciona);

				// revisem si hi ha llista d'elements (taula) al form per aplicar l'observació de qualsevol canvi a la taula

				observa();

			},
			preevalua = function(e) {

				input = $(this);
				input_element = input.closest(".imc-element");

				var esAvaluable = (input_element.attr("data-avalua") === "s") ? true : false
					,esLectura = (input_element.attr("data-lectura") === "s") ? true : false;

				
				// si és lectura no fem res

				if (esLectura) {
					return;
				}


				// si és avaluable i no lectura

				if (esAvaluable && !esLectura) {

					valor_inicial_txt = input.val();

					element
						.attr("data-preevalua", "s");

				}

			},
			preselecciona = function(e) {

				var selector_ = $(this)
					,contenidor_el_ = selector_.closest(".imc-element");

				selector_valor_ = contenidor_el_.find("input[type=hidden]:first").val();

			},
			selecciona = function(e) {

				input = $(this);

				var contenidor_el_ = input.closest(".imc-element")
					,contenidor_el_tipus = contenidor_el_.attr("data-tipus");

				var esAvaluable = (contenidor_el_.attr("data-avalua") === "s") ? true : false
					,esLectura = (contenidor_el_.attr("data-lectura") === "s") ? true : false;

				var esCampDeText = (contenidor_el_tipus === "texto") ? true : false
					,esCampSelector = (contenidor_el_tipus === "selector") ? true : false;


				// es selector i el valor seleccionat es igual al marcat anteriorment

				if (esCampSelector && esAvaluable && !esLectura && selector_valor_ === contenidor_el_.find("li.imc-select-seleccionat:first a").attr("data-value")) {
					return;
				}


				// és avaluable, no es lectura i ha canviat el valor en camp de text (data-tipus=texto)

				if (esCampDeText && esAvaluable && !esLectura && valor_inicial_txt !== input.val()) {

					avalua();

				} else if (!esCampDeText && esAvaluable && !esLectura) {

					avalua();

				} else {

					element
						.removeAttr("data-preevalua");

				}

			},
			observa = function() {

				element
					.find("fieldset[data-tipus='listaElementos']")
						.each(function(i) {

							var llista_elms = $(this)
								,teObservador = (llista_elms.attr("data-observer") === "s") ? true : false;

							if (!teObservador) {

								const targetNode = llista_elms.find(".imc-el-taula-elements:first tbody:first")[0];

								//const config = { characterData: false, attributes: false, childList: true, subtree: true };
								const config = { childList: true };

								const callback = function(mutationsList, observer) {

									var mutationsList_size = mutationsList.length; // v.ie11

									//for (const mutation of mutationsList) {

									for (var i = 0; i < mutationsList_size; i++) {

										mutation = mutationsList[i];

										if (mutation.type === 'childList') {

											var esAvaluable = (llista_elms.attr("data-avalua") === "s") ? true : false;

											if (esAvaluable) {

												input = llista_elms.find(".imc-el-taula-elements:first tbody:first");

												setTimeout(
													function() {

														avalua();

													}
													,330
												);

											}

										}

									}

								};

								const observer = new MutationObserver(callback);

								observer
									.observe(targetNode, config);

								llista_elms
									.attr("data-observer", "s");

							}

						});

			},
			avalua = function(e) {

				input_element = input.closest(".imc-element");
				input_id = input_element.attr("data-id");
				input_tipus = input_element.attr("data-tipus");

				// preevalua?

				if (input_tipus !== "texto" && element.attr("data-preevalua") === "s") { // imc_forms_contenidor.attr("data-preevalua") === "s"
					return;
				}

				// hi ha error al camp?

				if (input_tipus === "texto" && input_element.hasClass("imc-el-error")) {
					return;
				}

				// missatge enviant

				imc_forms_missatge
					.attr("tabindex", "-1")
					.appFormsMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtFormDinAvaluantTitol, alMostrar: function() { enviaRetarda(); } })
					.focus();

			},
			enviaRetarda = function(e) {

				// el selector múltiple necessita un petit retard per agafar correctament els valors

				setTimeout(
					function() {

						envia();

					},50
				);

			},
			envia = function(e) {

				// l'avaluació pot vindre del form principal y del detall de la taula (llista d'elements)

				desDeTaula = (input_element.closest(".imc-forms--taula").length) ? true : false;

				// serialitza

				//var valorsSerialitzats = (desDeTaula) ? $("#imc-forms--taula").appSerialitza({ verifica: false }) : imc_forms_finestra.appSerialitza({ verifica: false });

				var valorsSerialitzats = element.appSerialitza({ verifica: false });

				valorsSerialitzats["idCampo"] = input_id;

				if (desDeTaula) {

					valorsSerialitzats["idCampoListaElementos"] = $("#imc-forms--taula").attr("data-id");

				}

				// dades ajax

				var pag_url = (desDeTaula) ? APP_FORM_LE_AVALUA : APP_FORM_AVALUA_CAMP
					,pag_dades = valorsSerialitzats;

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
						dataType: "json"
					})
					.done(function( data ) {

						envia_ajax = false;

						element // imc_forms_contenidor
							.removeAttr("data-preevalua");

						json = data;

						if (json.estado === "SUCCESS" || json.estado === "WARNING") {

							if (json.estado === "WARNING") {

								imc_forms_missatge
									.appFormsMissatge({ accio: "warning", titol: data.mensaje.titulo, text: data.mensaje.texto, alAcceptar: function() { mostra(); } });

								return;

							}

							avalua_json = json;

							setTimeout(
								function() {

									resultat();

								},
								100
							);
							

						} else {

							envia_ajax = false;

							element // imc_forms_contenidor
								.removeAttr("data-preevalua");

							consola("Avalua dada del formulari: error des de JSON");

							imc_forms_body
								.appFormsErrorsGeneral({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, debug: data.mensaje.debug, url: json.url });

						}

					})
					.fail(function(dades, tipus, errorThrown) {

						envia_ajax = false;

						element // imc_forms_contenidor
							.removeAttr("data-preevalua");

						if (tipus === "abort") {
							return false;
						}

						consola("Avalua dada del formulari: error des de FAIL");

						imc_forms_body
							.appFormsErrorsGeneral({ estat: "fail" });

					});

			},
			resultat = function() {

				var validacio = avalua_json.datos.validacion
					,validacio_estat = (validacio !== null && validacio.estado) ? validacio.estado : false
					,validacio_missatge = (validacio !== null && validacio.mensaje) ? validacio.mensaje : false
					,camp_id = (validacio !== null && validacio.campo !== null && validacio.campo !== "") ? validacio.campo : false;


				// llevem "error", per si estiguera correcte, el "camp_id" pot ser que no vinga, per tant usem "input_id"

				//if (camp_id && camp_id !== "" && camp_id !== null) {

					element
						.find("*[data-id='"+input_id+"']")
							.removeClass("imc-el-error");

				//}

				// si hi ha error, el marquem

				if (validacio_estat === "error") {

					if (camp_id && camp_id !== "" && camp_id !== null) {

						imc_forms_missatge
							.appFormsMissatge({ accio: "error", titol: validacio_missatge, text: txtFormDinErrorText, amagaDesdeFons: false, alTancar: function() { remarca(camp_id); enfocaAlSeguent(); } });

					} else {

						imc_forms_missatge
							.appFormsMissatge({ accio: "error", titol: validacio_missatge, text: "", amagaDesdeFons: false, alTancar: function() { enfocaAlSeguent(); } });

					}

					return;

				}

				// si hi ha missatge, el mostrem

				if (validacio_missatge && validacio_missatge !== null && validacio_missatge !== "") {

					var destacaCamp = function() {

							if (validacio_estat === "error") {

								input_element
									.appDestaca({ referent: element.find(".imc--form:first") });

							}

						};

					imc_forms_missatge
						.appFormsMissatge({ accio: validacio_estat, titol: validacio_missatge, text: "", alTancar: function() { destacaCamp(); }, alAcceptar: function() { destacaCamp(); imc_forms_missatge.appFormsMissatge({ araAmaga: true }); enfocaAlSeguent(); } });

				} else {

					imc_forms_missatge
						.appFormsMissatge({ araAmaga: true });

					// esperem una miqueta al enfocar

					setTimeout(
						function() {

							enfocaAlSeguent();

						}
						,400
					);
					
				}

				// configuració

				element
					.attr("data-repinta", "s")
					.appFormsConfiguracio({ forms_json: avalua_json, desDe: "avalua" });

			},
			remarca = function(camp_id) {

				element
					.find("*[data-id="+camp_id+"]:first")
						.addClass("imc-el-error")
						.appDestaca({ referent: element.find(".imc--form:first") });

			},
			enfocaAlSeguent = function() {

				// si hi ha click

				if (FORMS_CAMP_CLICAT && (input_id !== FORMS_CAMP_CLICAT_ID)) {

					var el_seguent = element.find("div[data-id="+FORMS_CAMP_CLICAT_ID+"]:first");

					el_seguent
						.find("input:first, textarea:first, a.imc-select:first")
							.focus();

					return;

				}

				// si hi ha taulador

				if (input_element.attr("data-tipus") === "selector" && input_element.attr("data-contingut") === "d") {

					// si es selector desplegable ens quedem al mateix element

					input_element
						.find("a.imc-select:first")
							.focus();

				} else if (input_element.attr("data-tipus") === "selector" && (input_element.attr("data-contingut") === "m" || input_element.attr("data-contingut") === "u")) {

					var el_div = input.closest("div");

					el_div
						.addClass("imc--focus")
						.find("label:first")
							.focus();

					el_div
						.removeClass("imc--focus");

				} else {

					// si no es selector passem a un altre

					var el_seguent = input_element.nextAll(".imc-element[data-id]:not([data-ocult=s]):not(.imc-separador):first");

					if (el_seguent.length) {

						el_seguent
							.find("input:first, textarea:first, a.imc-select:first")
								.focus();

					} else {

						imc_forms_finestra
							.find("li.imc--finalitza:first button")
								.focus();

					}

				}

			};

		// inicia
		inicia();

	});
	return this;
}

// appFormsAvalua
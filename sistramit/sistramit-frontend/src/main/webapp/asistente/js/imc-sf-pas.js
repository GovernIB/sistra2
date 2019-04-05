// JS


// elements

var JSON_PAS_ACTUAL = false;

var HTML_CAL_SABER
	,HTML_EMPLENAR
	,HTML_ANNEXAR
	,HTML_PAGAR
	,HTML_REGISTRAR
	,HTML_GUARDAR
	,HTML_SEGUENT_PAS = false;

var HTML_PAS_CARREGAT = [];

var HTML_PAS_LITERALS = {
		"ds": {
			txtCalSaber: txtCalSaber
			,txtCalSaberInfo: txtCalSaberInfo
			,txtVoleuInformacio: txtVoleuInformacio
			,txtExplicacioDetalladaOcultar: txtExplicacioDetalladaOcultar
			,txtExplicacioDetalladaMirau: txtExplicacioDetalladaMirau
			,txtCalSaberExplicacio: txtCalSaberExplicacio
			,txtEmplenarTitol: txtEmplenarTitol
			,txtEmplenarExplicacio: txtEmplenarExplicacio
			,txtAnnexarTitol: txtAnnexarTitol
			,txtAnnexarExplicacio: txtAnnexarExplicacio
			,txtPagarTitol: txtPagarTitol
			,txtPagarExplicacio: txtPagarExplicacio
			,txtRegistrarTitol: txtRegistrarTitol
			,txtRegistrarExplicacio: txtRegistrarExplicacio
			,txtTitolLOPD: txtTitolLOPD
			,txtSeguent: txtSeguent
		},
		"rf": {
			txtEmplenarTitol: txtEmplenarTitol
			,txtEmplenarInfo: txtEmplenarInfo
			,txtFormulari: txtFormulari
			,txtObligatori: txtObligatori
			,txtOpcional: txtOpcional
			,txtRevisa: txtRevisa
			,txtDepenent: txtDepenent
			,txtCompletat: txtCompletat
			,txtNoCompletat: txtNoCompletat
			,txtAnterior: txtAnterior
			,txtSeguent: txtSeguent
			,txtAjudaActivada: txtAjudaActivada
			,txtDesactivar: txtDesactivar
			,txtTancaFormulari: txtTancaFormulari
		},
		"ad": {
			txtAnnexarTitol: txtAnnexarTitol
			,txtAnnexarInfo: txtAnnexarInfo
			,txtFormulari: txtFormulari
			,txtObligatori: txtObligatori
			,txtOpcional: txtOpcional
			,txtCompletat: txtCompletat
			,txtNoCompletat: txtNoCompletat
			,txtAnterior: txtAnterior
			,txtSeguent: txtSeguent
			,txtTanca: txtTanca
			,txtElimina: txtElimina
			,txtEnviar: txtEnviar
			,txtCancela: txtCancela
			,txtSeleccionaFitxer: txtSeleccionaFitxer
			,txtEnviantDades: txtEnviantDades
			,txtCancelaEnviament: txtCancelaEnviament
			,txtTornaIntentar: txtTornaIntentar
			,txtElectronicament: txtElectronicament
			,txtElectronicamentInfo: txtElectronicamentInfo
			,txtPresencialment: txtPresencialment
			,txtPresencialmentInfo: txtPresencialmentInfo
			,txtDesa: txtDesa
			,txtSiAportare: txtSiAportare
			,txtEsborra: txtEsborra
		},
		"pt": {
			txtPagarTitol: txtPagarTitol
			,txtPagarInfo: txtPagarInfo
			,txtObligatori: txtObligatori
			,txtOpcional: txtOpcional
			,txtCompletat: txtCompletat
			,txtNoCompletat: txtNoCompletat
			,txtAnterior: txtAnterior
			,txtSeguent: txtSeguent
			,txtPagamentTanca: txtPagamentTanca
			,txtCapPagament: txtCapPagament
			,txtPagament: txtPagament
			,txtPagarPresencialInfo: txtPagarPresencialInfo
			,txtPagarElectronicInfo: txtPagarElectronicInfo
		},
		"rt": {
			txtRegistrarTitol: txtRegistrarTitol
			,txtRegistrarInfo: txtRegistrarInfo
			,txtInfoResum: txtInfoResum
			,txtFormularis: txtFormularis
			,txtAnnexes: txtAnnexes
			,txtPagaments: txtPagaments
			,txtAnterior: txtAnterior
			,txtRegistrar: txtRegistrar
			,txtReintentar: txtReintentar
		},
		"gj": {
			txtDesauJustificant: txtDesauJustificant
			,txtDesauInfo: txtDesauInfo
			,txtDesauJustificant: txtDesauJustificant
			,txtDesauDocumentacio: txtDesauDocumentacio
			,txtDesauDocumentacioInfo: txtDesauDocumentacioInfo
			,txtFormularis: txtFormularis
			,txtAnnexes: txtAnnexes
			,txtPagaments: txtPagaments
			,txtAnterior: txtAnterior
			,txtRegistrar: txtRegistrar
			,txtSotiuTramit: txtSotiuTramit
		}

		



	};


// appPas

$.fn.appPas = function(options) {
	var settings = $.extend({
			pas: false
			,recarrega: false
			,esFormulari: false
			,formulariId: false
	}, options);
	this.each(function(){
		var element = $(this),
			pas = settings.pas,
			recarrega = settings.recarrega,
			esFormulari = settings.esFormulari,
			formulariId = settings.formulariId,
			pas_json = false,
			pas_tipus = false,
			envia_ajax = false,
			inicia = function() {

				// hi ha pas?

				if (!pas) {

					error();
					return;

				}

				// llevem accessibilitat

				imc_contenidor
					.removeClass("imc--mostra-acc");

				// es un pas emplenar amb un formulari obert?

				if (typeof imc_formulari !== "undefined" && imc_formulari.length && imc_formulari.css("visibility") === "visible") {

					var potGuardar = imc_formulari.attr("data-guardar");

					var text_avis = (potGuardar === "s") ? txtFormEixirText : txtFormEixirNoGuardaText
						,potGuardar_class = (potGuardar === "s") ? "imc--si-pot-guardar" : "imc--no-pot-guardar";

					imc_missatge
						.removeClass("imc--si-pot-guardar imc--no-pot-guardar")
						.addClass( potGuardar_class )
						.appMissatge({ accio: "formSurt", titol: txtFormEixirTitol, text: text_avis });

					imc_missatge
						.appMissatgeFormAccions();

					document.location = "#pas/" + APP_TRAMIT_PAS_ID + "/formulari/" + imc_formulari.attr("data-id");

				}

				// es el mateix pas ja carregat?

				if (imc_contingut.attr("data-id") === pas && !recarrega) {

					imc_cap_c
						.appCap();

					return;

				}

				// missatge carregant
				
				imc_missatge
					.appMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtPasCarregant });
				
				// carrega

				carregaJSON();

			},
			carregaJSON = function() {

				var pag_url = APP_TRAMIT_PAS,
					pag_dades = { paso: pas };

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
						
						pas_json = data;
						JSON_PAS_ACTUAL = data;

						if (pas_json.estado === "SUCCESS" || pas_json.estado === "WARNING") {

							if (pas_json.estado === "WARNING") {

								imc_missatge
									.appMissatge({ accio: "warning", titol: data.mensaje.titulo, text: data.mensaje.texto, alAcceptar: function() { mollaPa(); carregaHTML(); } });

								return;

							}

							mollaPa();
							carregaHTML();

						} else {

							envia_ajax = false;

							consola("Pas: error des de JSON");

							imc_contenidor
								.errors({ estat: pas_json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, url: pas_json.url });

						}
						
					})
					.fail(function(dades, tipus, errorThrown) {

						if (tipus === "abort") {
							return false;
						}
						
						consola("Pas: error des de FAIL");

						imc_contenidor
							.errors({ estat: "fail" });
						
					});

			},
			carregaHTML = function() {

				pas_tipus = pas_json.datos.actual.tipo;

				var pas_arxius_nom = (pas_tipus === "ds") ? "calSaber"
									: (pas_tipus === "rf") ? "emplenar"
									: (pas_tipus === "ad") ? "annexar"
									: (pas_tipus === "pt") ? "pagar"
									: (pas_tipus === "rt") ? "registrar"
									: "guardar";

				// si està carregat

				if (typeof HTML_PAS_CARREGAT[pas_tipus] !== "undefined") {

					pinta();
					return;

				}

				// carrega

				$.when(
		
					$.get(APP_ + "css/imc-sf--"+pas_arxius_nom+".css?" + APP_VERSIO)
					,$.get(APP_ + "html/imc-sf--"+pas_arxius_nom+".html")
					,$.getScript(APP_ + "js/imc-sf--"+pas_arxius_nom+".js?" + APP_VERSIO)

				).then(

					function( cssPas, htmlPas) {

						// estils

						$("<style>")
							.html( cssPas[0] )
								.appendTo( imc_head );

						// html

						HTML_PAS_CARREGAT[pas_tipus] = htmlPas[0];

						// pinta

						pinta();

					}

				).fail(

					function() {

						envia_ajax = false;

						consola("Pas: error caregant HTML, CSS i JS");

						imc_contenidor
							.errors({ estat: "fail" });

					}

				);

			},
			mollaPa = function() {

				// es pas de guardar?

				if (JSON_PAS_ACTUAL.datos.actual.tipo === "gj") {

					imc_molla_pa
						.remove();

					imc_tramitacio
						.find(".imc--elimina:first")
							.remove();

					return;

				}

				// repintem html

				var imc_nav = imc_molla_pa.find("nav:first");

				imc_nav
					.html( $("<ol>") );

				var imc_nav_ol = imc_molla_pa.find("ol:first");

				// molla pa

				var pas_passes = pas_json.datos.pasos;

				// pintem HTML bàsic

				$(pas_passes)
					.each(function() {

						var el = this
							,el_id = el.id
							,el_tipus = el.tipo
							,el_completat = el.completado
							,el_accessible = el.accesible;

						if (el_tipus !== "gj") {

							// pintem HTML bàsic

							var num_span = $("<span>").addClass("imc--num"),
								nom_span = $("<span>").addClass("imc--nom"),
								pas_div = $("<div>").append( num_span ).append( nom_span ),
								pas_li = $("<li>").attr({ "data-id": el_id, "data-tipus": el_tipus }).append( pas_div ).appendTo( imc_nav_ol );

							// seleccionem per id

							var molla_pa_el = imc_molla_pa.find("li[data-id='"+el_id+"']");

							// text

							var nom_txt = (el_tipus === "ds") ? txtCalSaber : (el_tipus === "rf") ? txtEmplenar : (el_tipus === "ad") ? txtAnnexar : (el_tipus === "pt") ? txtPagar : (el_tipus === "rt") ? txtRegistrar : "";

							molla_pa_el
								.find(".imc--nom")
									.text( nom_txt )
									.end()
								.addClass("imc--mostra");

							// actual

							if (pas === el_id) {

								molla_pa_el
									.find("div:first")
										.attr({ "class": "imc--selec", "aria-current": "page" });

							} else if (el_completat === "s") {

								var html_completat = molla_pa_el.find("div:first").html();

								var html_a = $("<a>").attr("href", "#pas/"+el_id).append( html_completat );

								molla_pa_el
									.html( html_a );

							}

						}

					});

				// numera

				imc_molla_pa
					.find("li:not(.imc--mostra)")
						.remove();

				imc_molla_pa
					.find("li")
						.each(function(i) {

							var el = $(this);

							el
								.find(".imc--num")
									.text( i+1 );

						});

			},
			pinta = function() {

				// afegir llistat de elements

				var txtGlobals = {};

				if (pas_tipus === "ds") {

					HTML_PAS_LITERALS[pas_tipus]["txtCalSaberImportant"] = pas_json.datos.actual.instrucciones;
					HTML_PAS_LITERALS[pas_tipus]["txtInfoLOPD"] = pas_json.datos.actual.infoLOPD;

				} else if (pas_tipus === "rf") {

					HTML_PAS_LITERALS[pas_tipus]["jsonEntorn"] = APP_JSON_TRAMIT_D.entorno;
					HTML_PAS_LITERALS[pas_tipus]["jsonSolsLectura"] = pas_json.datos.actual.soloLectura;
					HTML_PAS_LITERALS[pas_tipus]["formularis"] = pas_json.datos.actual.formularios;

					var txtGlobals = {
							globals: {
								txtFormulariEnPDF: txtFormulariEnPDF
								,txtFormulariEnXML: txtFormulariEnXML
								,txtPDF: txtPDF
								,txtXML: txtXML
								,pasID: APP_TRAMIT_PAS_ID
							}
						};

				} else if (pas_tipus === "ad") {

					HTML_PAS_LITERALS[pas_tipus]["annexes"] = pas_json.datos.actual.anexos;
					HTML_PAS_LITERALS[pas_tipus]["annexesPresencials"] = pas_json.datos.actual.anexos;

					var txtGlobals = {
							globals: {
								txtDocumentsInstancies: txtDocumentsInstancies
								,txtDocumentsInstancies2: txtDocumentsInstancies2
								,txtDocumentsAccept: txtDocumentsAccept
								,txtMidaMaxima: txtMidaMaxima
								,txtDescarregaPlantilla: txtDescarregaPlantilla
								,txtDocAnnexat: txtDocAnnexat
								,txtDocsAnnexats: txtDocsAnnexats
								,txtTitol: txtTitol
								,txtObriFinestra: txtObriFinestra
								,txtPlantilla: txtPlantilla
								,txtDescarrega: txtDescarrega
								,txtEsborra: txtEsborra
							}
						};

				} else if (pas_tipus === "pt") {

					HTML_PAS_LITERALS[pas_tipus]["pagaments"] = pas_json.datos.actual.pagos;
					HTML_PAS_LITERALS[pas_tipus]["jsonSolsLectura"] = pas_json.datos.actual.soloLectura;

					var txtGlobals = {
							globals: {
								txtPresencial: txtPresencial
								,txtElectronic: txtElectronic
								,txtDescartarPagament: txtDescartarPagament
								,txtPlantillaPagament: txtPlantillaPagament
								,txtJustificantPagament: txtJustificantPagament
								,txtRevisarPagament: txtRevisarPagament
								,txtSolsLectura: pas_json.datos.actual.soloLectura
							}
						};

				} else if (pas_tipus === "rt") {

					HTML_PAS_LITERALS[pas_tipus]["formularis"] = pas_json.datos.actual.formularios;
					HTML_PAS_LITERALS[pas_tipus]["annexes"] = pas_json.datos.actual.anexos;
					HTML_PAS_LITERALS[pas_tipus]["pagaments"] = pas_json.datos.actual.pagos;
					HTML_PAS_LITERALS[pas_tipus]["representado"] = pas_json.datos.actual.representado;
					HTML_PAS_LITERALS[pas_tipus]["registrar"] = pas_json.datos.actual.registrar;
					HTML_PAS_LITERALS[pas_tipus]["reintentar"] = pas_json.datos.actual.reintentar;
					HTML_PAS_LITERALS[pas_tipus]["seguent"] = pas_json.datos.siguiente;

					var txtGlobals = {
							globals: {
								txtSignar: txtSignar
								,txtDescarrega: txtDescarrega
								,jsonUsuariId: APP_USUARI_ID
								,txtSignant: txtSignant
								,txtSignants: txtSignants
								,txtSignatEl: txtSignatEl
							}
						};

				} else if (pas_tipus === "gj") {

					HTML_PAS_LITERALS[pas_tipus]["formularis"] = pas_json.datos.actual.justificante.formularios;
					HTML_PAS_LITERALS[pas_tipus]["annexes"] = pas_json.datos.actual.justificante.anexos;
					HTML_PAS_LITERALS[pas_tipus]["pagaments"] = pas_json.datos.actual.justificante.pagos;

					var txtGlobals = {
							globals: {
								txtDescarrega: txtDescarrega
								,txtSignant: txtSignant
								,txtSignants: txtSignants
								,txtSignatEl: txtSignatEl
							}
						};

				}

				// tractar html

				var html_pas = Mark.up(HTML_PAS_CARREGAT[pas_tipus], HTML_PAS_LITERALS[pas_tipus], txtGlobals);

				imc_contingut_c
					.html( html_pas );

				// accions per tipus de pas

				if (pas_tipus === "ds") {

					pinta_ds();

				} else if (pas_tipus === "rf") {

					pinta_rf();

				} else if (pas_tipus === "ad") {

					pinta_ad();

				} else if (pas_tipus === "pt") {

					pinta_pt();

				} else if (pas_tipus === "rt") {

					pinta_rt();

				} else if (pas_tipus === "gj") {

					pinta_gj();

				}

				// passes anterior i seguent

				var pas_completat = pas_json.datos.actual.completado;

				if (pas_completat === "n") {

					var bt_seguent_pas = imc_contingut_c.find(".imc--navegacio:first .imc--seguent")
						,bt_seguent_pas_html = bt_seguent_pas.parent().html();

					HTML_SEGUENT_PAS = bt_seguent_pas_html;

					bt_seguent_pas
						.parent()
							.remove();

				}

				var pas_anterior = pas_json.datos.anterior,
					pas_seguent = pas_json.datos.siguiente;

				if (pas_anterior !== "" && pas_anterior !== null) {

					imc_contingut_c
						.find(".imc--navegacio:first .imc--anterior")
							.attr("href", "#pas/"+pas_anterior);

				}

				if (pas_seguent !== "" && pas_seguent !== null && pas_completat === "s") {

					imc_contingut_c
						.find(".imc--navegacio:first .imc--seguent")
							.attr("href", "#pas/"+pas_seguent);

				}

				// mostra

				mostra();

				// dalt

				window
					.scrollTo(0, 0);
				
			},
			pinta_ds = function() {

				// inicia js pas

				appPasCalSaberInicia();

				// cal saber pases

				var cs_passes = pas_json.datos.actual.pasos;

				$(cs_passes)
					.each(function() {
						
						var el = this
							,el_tipus = el.tipo;

						imc_cs_explicacio
							.find("li[data-tipus='"+el_tipus+"']")
								.addClass("imc--mostra");

					});
				
				imc_cs_explicacio
					.find("li:not(.imc--mostra)")
						.remove();

				imc_cs_explicacio
					.find("li")
						.each(function(i) {

							var el = $(this);

							el
								.find(".imc--num")
									.text( i+1 );

						});

			},
			arxius_rf = false,
			pinta_rf = function() {

				var inicia_rf = function() {

						imc_contingut_c
							.find(".imc--formularis:first a")
								.each(function(i) {

									var el = $(this)
										,el_id = el.attr("data-id");

									el
										.find(".imc--formulari:first span")
											.text( txtFormulari )
											.end()
										.find(".imc--ogligatori:first span")
											.text( txtObligatori )
											.end()
										.find(".imc--opcional:first span")
											.text( txtOpcional )
											.end()
										.find(".imc--depenent:first span")
											.text( txtDepenent )
											.end()
										.find(".imc--completat:first span")
											.text( txtCompletat )
											.end()
										.find(".imc--no-completat:first span")
											.text( txtNoCompletat );

								});

						appPasEmplenarInicia();

					};

				// IE11?

				if (!Modernizr.cssgrid && !arxius_rf) {

					$.get(
						APP_ + "css/imc-sf--emplenar-ie.css?" + APP_VERSIO
						,function(cssPas) {

							$("<style>")
								.html( cssPas )
									.appendTo( imc_head );

							inicia_rf();

							arxius_rf = true;

						}
					);

				} else {

					inicia_rf();

				}

			},
			arxius_ad = false,
			pinta_ad= function() {

				var inicia_ad = function() {

						imc_contingut_c
							.find(".imc--ann")
								.each(function(i) {

									var el = $(this)
										,hiHaDocuments = el.find("li").length;

									if (hiHaDocuments) {

										el
											.attr("aria-hidden", "false");

									} else {

										el
											.remove()

									}

								});

						imc_contingut_c
							.find(".imc--annexes:first a")
								.each(function(i) {

									var el = $(this),
										el_id = el.attr("data-id");

									var doc_txt = (el.closest(".imc--e").length) ? txtDocumentElectronic : txtDocumentPresencial;

									el
										.find(".imc--doc:first span")
											.text( doc_txt )
											.end()
										.find(".imc--ogligatori:first span")
											.text( txtObligatori )
											.end()
										.find(".imc--opcional:first span")
											.text( txtOpcional )
											.end()
										.find(".imc--completat:first span")
											.text( txtCompletat )
											.end()
										.find(".imc--no-completat:first span")
											.text( txtNoCompletat );

								});

						imc_contingut_c
							.find(".imc-document:first form")
								.attr("action", APP_ANNEXE_ANNEXA);


						imc_contingut_c
							.find(".imc--opcions")
								.each(function(i) {

									var el = $(this),
										el_data_extension = el.attr("data-extensions");

									if (el_data_extension !== "null") {

										var el_data_extension_arr = el_data_extension.split(","),
											el_data_extension_arr_size = el_data_extension_arr.length,
											el_codi = "";

										$(el_data_extension_arr)
											.each(function(j) {

												var d_e = this;

												el_codi += d_e.toUpperCase();
												el_codi += (j < el_data_extension_arr_size-1) ? ", " : "";

											});

										el
											.find(".imc--extensions strong")
												.text( el_codi );

									}

									// hi ha algo que mostrar a opcions?

									if ($.trim( el.html() ) === "") {

										el
											.remove();

									}

								});

						imc_contingut_c
							.find(".imc--doc-li")
								.each(function(i) {

									var el = $(this),
										num_max = parseInt( el.attr("data-num") ),
										num_annexats = el.find(".imc--annexats li").length;

									if (num_max === num_annexats) {

										el
											.addClass("imc--completat");

									}

								});

						appPasAnnexarInicia();

					};

				// IE11?

				if (!Modernizr.cssgrid && !arxius_ad) {

					$.get(
						APP_ + "css/imc-sf--annexar-ie.css?" + APP_VERSIO
						,function(cssPas) {

							$("<style>")
								.html( cssPas )
									.appendTo( imc_head );

							inicia_ad();

							arxius_ad = true;

						}
					);

				} else {

					inicia_ad();

				}

			},
			arxius_pt = false,
			pinta_pt = function() {

				// informació pagaments

				var inicia_pt = function() {

						var esPresencial = esElectronic = false;

						var items = imc_contingut_c.find(".imc--pagaments li")
							,items_size = items.length;

						if (items_size) {

							items
								.each(function() {

									var it = $(this)
										,presentacio = it.attr("data-presentacio");

									if (presentacio === "p") {
										esPresencial = true;
									}

									if (presentacio === "e") {
										esElectronic = true;
									}

									if (presentacio === "" || presentacio === "null") {
										
										if (it.find(".imc--pagament-presencial").length) {
											esPresencial = true;
										}
										if (it.find(".imc--pagament-electronic").length) {
											esElectronic = true;
										}

									}

								})

						}

						if (esPresencial) {

							imc_contingut_c
								.find(".imc--info-pagament:first")
									.addClass("imc--es-presencial");

						}

						if (esElectronic) {

							imc_contingut_c
								.find(".imc--info-pagament:first")
									.addClass("imc--es-electronic");

						}

						// inicia

						appPasPagarInicia();

					};

				// IE11?

				if (!Modernizr.cssgrid && !arxius_pt) {

					$.get(
						APP_ + "css/imc-sf--pagar-ie.css?" + APP_VERSIO
						,function(cssPas) {

							$("<style>")
								.html( cssPas )
									.appendTo( imc_head );

							inicia_pt();

							arxius_pt = true;

						}
					);

				} else {

					inicia_pt();

				}

			},
			arxius_rt = false,
			pinta_rt = function() {

				var inicia_rt = function() {

						appPasRegistrarInicia();

					};

				// IE11?

				if (!Modernizr.cssgrid && !arxius_rt) {

					$.get(
						APP_ + "css/imc-sf--registrar-ie.css?" + APP_VERSIO
						,function(cssPas) {

							$("<style>")
								.html( cssPas )
									.appendTo( imc_head );

							inicia_rt();

							arxius_rt = true;

						}
					);

				} else {

					inicia_rt();

				}

			},
			arxius_gj = false,
			pinta_gj = function() {

				var inicia_gj = function() {

						appPasGuardarInicia();

					};

				// IE11?

				if (!Modernizr.cssgrid && !arxius_gj) {

					$.get(
						APP_ + "css/imc-sf--guardar-ie.css?" + APP_VERSIO
						,function(cssPas) {

							$("<style>")
								.html( cssPas )
									.appendTo( imc_head );

							inicia_rt();

							arxius_gj = true;

						}
					);

				} else {

					inicia_gj();

				}

			},
			mostra = function(text) {

				imc_missatge
					.appMissatge({ araAmaga: true });

				envia_ajax = false;

				imc_contingut
					.attr("data-id", JSON_PAS_ACTUAL.datos.actual.id);

				imc_cap_c
					.appCap();

			},
			error = function(opcions) {

				var settings_opcions = $.extend({
						estat: "ERROR",
						titol: txtTramitPasErrorTitol,
						text: txtTramitPasErrorText
					}, opcions);

				var estat = settings_opcions.estat,
					titol = settings_opcions.titol,
					text = settings_opcions.text;

				imc_missatge
					.appMissatge({ accio: "error", titol: titol, text: text, alTancar: function() { reintenta({ titol: titol, text: text }); } });

				envia_ajax = false;

			},
			reintenta = function(opcions) {

				var esPrimeraCarrega = ($.trim(imc_contingut_c.html()) === "") ? true : false;

				if (esPrimeraCarrega) {

					var text_html = imc_missatge.find(".imc--text:first").html();

					var reintentant = function() {

							imc_contingut_c
								.html("")
								.appPas({ pas: APP_JSON_TRAMIT_D.idPasoActual });

						};

					var bt_text = $("<span>").text( txtReintenta ),
						bt = $("<button>").addClass("imc-bt imc--ico imc--reintenta").attr("type", "button").append( bt_text ).on("click", reintentant);

					$("<div>")
						.addClass("imc-pas--missatge-error")
						.html( text_html )
						.append( bt )
						.appendTo( imc_contingut_c );

					imc_contingut_c
						.find(".imc-pas--missatge-error:first .imc--botonera")
							.remove();

				} else {

					document.location = "#pas/" + APP_TRAMIT_PAS_ID;

				}

			};
		
		// inicia
		inicia();
		
	});
	return this;
}


// hashchange

var URL_HASH,
	URL_PARAMETRES;

function urlHash() {
	
	URL_HASH = location.hash.replace(/^.*#/, '');
	URL_PARAMETRES = URL_HASH.split("/");
		
	return URL_HASH, URL_PARAMETRES;
	
}

jQuery(window)
	.on("hashchange", function(){ 
		
		urlHash();
	
	if (URL_PARAMETRES[0] === "pas" && URL_PARAMETRES.length === 2) {
		
		var pas = URL_PARAMETRES[1];

		APP_TRAMIT_PAS_ID = URL_PARAMETRES[1];

		imc_contingut_c
			.appPas({ pas: pas });

	} else if (URL_PARAMETRES[0] === "pas" && URL_PARAMETRES[2] === "formulari" && URL_PARAMETRES.length === 4) {

		var pas = URL_PARAMETRES[1]
			,form_id = URL_PARAMETRES[3];

		APP_TRAMIT_PAS_ID = URL_PARAMETRES[1];

		// està obert el formulari

		if (typeof imc_formulari !== "undefined" && imc_formulari.length && imc_formulari.css("visibility") === "visible" && imc_formulari.attr("data-id") === form_id) {

			return;

		}

		// obri formulari

		imc_formularis
			.appEmplenaFormulari({ form_id: form_id });
		
	} else if (URL_PARAMETRES[0] === "accessibilitat") {

		imc_contingut_c
			.appAccessibilitat();

	}
	
});


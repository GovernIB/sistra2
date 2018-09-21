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
			,txtCompletat: txtCompletat
			,txtNoCompletat: txtNoCompletat
			,txtAnterior: txtAnterior
			,txtSeguent: txtSeguent
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
	}, options);
	this.each(function(){
		var element = $(this),
			pas = settings.pas,
			recarrega = settings.recarrega,
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
					pag_data = { paso: pas };

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
						
						pas_json = data;
						JSON_PAS_ACTUAL = data;

						if (pas_json.estado === "SUCCESS" || pas_json.estado === "WARNING") {

							mollaPa();
							carregaHTML();

						} else {

							envia_ajax = false;

							consola("Pas: error des de JSON");
							errors({ estat: pas_json.estado, titol: data.mensaje.titulo, text: data.mensaje.text, url: pas_json.url });

						}
						
					})
					.fail(function(dades, tipus, errorThrown) {

						consola(dades+" , "+ tipus +" , "+ errorThrown);

						if (tipus === "abort") {
							return false;
						}
						
						consola("Pas: error des de FAIL");
						errors({ estat: "fail" });
						
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
		
					$.get(APP_ + "css/imc-sf--"+pas_arxius_nom+".css")
					,$.get(APP_ + "html/imc-sf--"+pas_arxius_nom+".html")
					,$.getScript(APP_ + "js/imc-sf--"+pas_arxius_nom+".js")

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
						errors({ estat: "fail", titol: "Pas: error caregant HTML, CSS i JS" });

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
					HTML_PAS_LITERALS[pas_tipus]["formularis"] = pas_json.datos.actual.formularios;

					var txtGlobals = {
							globals: {
								txtFormulariEnPDF: txtFormulariEnPDF
								,txtFormulariEnXML: txtFormulariEnXML
								,txtPDF: txtPDF
								,txtXML: txtXML
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

					HTML_PAS_LITERALS[pas_tipus]["pagaments"] = pas_json.datos.actual.tasas;

				} else if (pas_tipus === "rt") {

					HTML_PAS_LITERALS[pas_tipus]["formularis"] = pas_json.datos.actual.formularios;
					HTML_PAS_LITERALS[pas_tipus]["annexes"] = pas_json.datos.actual.anexos;
					HTML_PAS_LITERALS[pas_tipus]["pagaments"] = pas_json.datos.actual.tasas;

					var txtGlobals = {
							globals: {
								txtSignar: txtSignar
								,txtDescarrega: txtDescarrega
							}
						};

				} else if (pas_tipus === "gj") {

					HTML_PAS_LITERALS[pas_tipus]["formularis"] = pas_json.datos.actual.formularios;
					HTML_PAS_LITERALS[pas_tipus]["annexes"] = pas_json.datos.actual.anexos;
					HTML_PAS_LITERALS[pas_tipus]["pagaments"] = pas_json.datos.actual.tasas;

					var txtGlobals = {
							globals: {
								txtDescarrega: txtDescarrega
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
			pinta_rf = function() {

				imc_contingut_c
					.find(".imc--formularis:first a")
						.each(function(i) {

							var el = $(this),
								el_id = el.attr("data-id");

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
								.find(".imc--completat:first span")
									.text( txtCompletat )
									.end()
								.find(".imc--no-completat:first span")
									.text( txtNoCompletat );
									//.end()
								//.attr("data-url", APP_URL_FORMS + "?id=" + el_id);

						});

				appPasEmplenarInicia();

			},
			pinta_ad= function() {

				imc_contingut_c
					.find(".imc--ann")
						.each(function(i) {

							var el = $(this),
								hiHaDocuments = el.find("li").length;

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

			},
			pinta_rt = function() {

				appPasRegistrarInicia();

			},
			pinta_gj = function() {

				appPasGuardarInicia();

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

					document.location = "#pas/" + APP_JSON_TRAMIT_D.idPasoActual;

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
		
	} else if (URL_PARAMETRES[0] === "accessibilitat") {

		imc_contingut_c
			.appAccessibilitat();

	}
	
});


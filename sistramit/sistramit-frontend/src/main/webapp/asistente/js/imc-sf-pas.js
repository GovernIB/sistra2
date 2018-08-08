// JS


// elements

var JSON_PAS_ACTUAL = false;

var HTML_CAL_SABER
	,HTML_EMPLENAR
	,HTML_ANNEXAR
	,HTML_PAGAR
	,HTML_REGISTRAR
	,HTML_GUARDAR = false;

var HTML_PAS_CARREGAT = [];

var HTML_PAS_LITERALS = {
		"ds": {
			txtCalSaber: txtCalSaber
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
			,txtSeguent: txtSeguent
		},
		"rf": {
			txtEmplenarTitol: txtEmplenarTitol
			,txtFormulari: txtFormulari
			,txtObligatori: txtObligatori
			,txtOpcional: txtOpcional
			,txtCompletat: txtCompletat
			,txtNoCompletat: txtNoCompletat
			,txtAnterior: txtAnterior
			,txtSeguent: txtSeguent
		}
		,
		"ad": {
			txtAnnexarTitol: txtAnnexarTitol
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

		}





	};


// appPas

$.fn.appPas = function(options) {
	var settings = $.extend({
		pas: false
	}, options);
	this.each(function(){
		var element = $(this),
			pas = settings.pas,
			pas_json = false,
			pas_tipus = false,
			inicia = function() {

				if (!pas) {

					error();
					return;

				}


				// missatge carregant

				imc_missatge
					.appMissatge({ accio: "carregant", amagaDesdeFons: false, titol:"Carregant dades del pas" });

				// carrega

				carregaJSON();

			},
			carregaJSON = function() {

				var pag_url = APP_SERVIDOR_JSON + APP_URL_PAS + APP_SERVIDOR_EXT,
					pag_data = { paso: pas };

				envia_ajax =
					$.ajax({
						url: pag_url,
						data: pag_data,
						method: "post",
						beforeSend: function(xhr) {
				            xhr.setRequestHeader(headerCSRF, tokenCSRF);
				        },
						dataType: "json"
					})
					.done(function( data ) {

						pas_json = data;
						JSON_PAS_ACTUAL = data;

						if (pas_json.estado === "SUCCESS" || pas_json.estado === "WARNING") {

							mollaPa();
							carregaHTML();

						} else {

							error("carregaJSON desde JSON");

						}

					})
					.fail(function(fail_dades, fail_tipus, errorThrown) {

						error("carregaJSON desde FAIL");

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

					$.get(APP_SERVIDOR + "css/imc-sf--"+pas_arxius_nom+".css")
					,$.get(APP_SERVIDOR + "html/imc-sf--"+pas_arxius_nom+".html")
					,$.getScript(APP_SERVIDOR + "js/imc-sf--"+pas_arxius_nom+".js")

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

						error("carregaHTML desde FAIL");

					}

				);

			},
			mollaPa = function() {

				// repentem html

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

				if (pas_tipus === "rf") {

					HTML_PAS_LITERALS[pas_tipus]["formularis"] = pas_json.datos.actual.formularios;
					HTML_PAS_LITERALS[pas_tipus]["txtInstruccions"] = pas_json.datos.actual.instrucciones;

				} else if (pas_tipus === "ad") {

					HTML_PAS_LITERALS[pas_tipus]["annexes"] = pas_json.datos.actual.anexos;
					HTML_PAS_LITERALS[pas_tipus]["annexesPresencials"] = pas_json.datos.actual.anexos;
					HTML_PAS_LITERALS[pas_tipus]["txtInstruccions"] = pas_json.datos.actual.instrucciones;

					var txtGlobals = {
							globals: {
								txtDocumentsAccept: txtDocumentsAccept
								,txtMidaMaxima: txtMidaMaxima
								,txtDescarregaPlantilla: txtDescarregaPlantilla
								,txtDocAnnexat: txtDocAnnexat
								,txtDocsAnnexats: txtDocsAnnexats
								,txtTitol: txtTitol
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

				}

				// passes anterior i seguent

				var pas_completat = pas_json.datos.actual.completado;

				if (pas_completat === "n") {

					imc_contingut_c
						.find(".imc--navegacio:first .imc--seguent")
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

				var info_txt = pas_json.datos.actual.instrucciones,
					lopd_txt = pas_json.datos.actual.infoLOPD;

				imc_contingut_c
					.find(".imc--info:first")
						.html( info_txt )
						.end()
					.find(".imc--lopd:first")
						.html( lopd_txt );

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
									.text( txtNoCompletat )
									.end()
								.attr("data-url", APP_URL_FORMS + "?id=" + el_id);

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
						.attr("action", APP_URL_ANNEXAR);

				appPasAnnexarInicia();

			},
			mostra = function(text) {

				imc_missatge
					.appMissatge({ araAmaga: true });


			},
			error = function(text) {

				alert("ERROR: "+text);



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

		imc_contingut_c
			.appPas({ pas: pas });

	}

});


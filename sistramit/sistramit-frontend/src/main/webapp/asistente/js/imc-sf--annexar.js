// Emplenar formularis

var imc_docs
	,imc_document
	,imc_doc_c
	,imc_document_contingut
	,imc_doc_elms;

var imc_doc_electronic
	,imc_doc_electronic_form
	,imc_doc_electronic_bt_envia
	,imc_doc_electronic_bt_cancela;

var imc_doc_presencial
	,imc_doc_presencial_form
	,imc_doc_presencial_bt_desa
	,imc_doc_presencial_bt_cancela;

var imc_doc_missatge
	,imc_bt_torna_intentar
	,imc_bt_tanca
	,imc_bt_cancela;

var imc_navegacio;

var HTML_FORM_ELECTRONIC;


// onReady

function appPasAnnexarInicia() {

	imc_docs = imc_contingut.find(".imc--annexes:first");
	imc_document = $("#imc-document");
	imc_doc_c = imc_document.find(".imc--c:first");
	imc_document_contingut = $("#imc-document-contingut");

	imc_doc_elms = imc_document.find("div.imc--doc");

	imc_doc_electronic = imc_document.find(".imc--doc-electronic:first");
	imc_doc_electronic_form = imc_doc_electronic.find("form:first");
	imc_doc_electronic_bt_envia = imc_doc_electronic.find("button[data-tipus='envia']:first");
	imc_doc_electronic_bt_cancela = imc_doc_electronic.find("button[data-tipus='cancela']:first");

	imc_doc_presencial = imc_document.find(".imc--doc-presencial:first");
	imc_doc_presencial_form = imc_doc_presencial.find("form:first");
	imc_doc_presencial_bt_desa = imc_doc_presencial.find("button[data-tipus='desa']:first");
	imc_doc_presencial_bt_cancela = imc_doc_presencial.find("button[data-tipus='cancela']:first");

	imc_doc_missatge = imc_document.find(".imc--doc-missatge:first");
	imc_bt_torna_intentar = imc_doc_missatge.find(".imc--error-torna:first");
	imc_bt_tanca = imc_doc_missatge.find(".imc--tanca:first");
	imc_bt_cancela = imc_doc_missatge.find(".imc--cancela:first");

	imc_navegacio = imc_contingut.find(".imc--navegacio:first");

	HTML_FORM_ELECTRONIC = imc_contingut.find(".imc-input-annex:first").html();

	imc_docs
		.appAnnexa()
		.appAnnexaLlistat();

	imc_document
		.appAnnexaArxiu();

};


// appAnnexa

$.fn.appAnnexa = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			ico_anim = false,
			ico_anim_carrega = false,
			envia_ajax = false,
			el_doc_info = imc_document.find(".imc--doc-info:first"),
			el_input_file = imc_doc_electronic.find(".imc--arxiu input[type=file]:first"),
			doc_tipus = false,
			el_afegits = imc_doc_electronic_form.find(".imc--afegits:first"),
			html_annexats = el_afegits.find("li:first").html(),
			inicia = function() {


				// botons electrònic enviament

				imc_doc_electronic_form
					.off('.appAnnexa')
					.on('submit.appAnnexa', envia);

				imc_doc_electronic_bt_cancela
					.off('.appAnnexa')
					.on('click.appAnnexa', tanca);

				// botons presencial enviament

				imc_doc_presencial_form
					.off('.appAnnexa')
					.on('submit.appAnnexa', envia);

				imc_doc_presencial_bt_cancela
					.off('.appAnnexa')
					.on('click.appAnnexa', tanca);

				// document missatge

				imc_bt_torna_intentar
					.off('.appSuport')
					.on('click.appSuport', errorTorna);

				imc_bt_tanca
					.off('.appAnnexa')
					.on('click.appAnnexa', tanca);

				imc_bt_cancela
					.off('.appSuport')
					.on('click.appSuport', aborta);

			},
			tanca = function() {

				setTimeout(
					function() {

						imc_document_contingut
							.removeClass("imc--on");

						imc_document
							.attr("aria-hidden", "true");

						imc_document
							.removeClass("imc--on imc--off")
							.appPopupTabula({ accio: "finalitza" });


					}, 100);

				// foco en l'enllaç

				var doc_obert_id = imc_document.attr("data-id");

				imc_docs
					.find("li[data-id="+doc_obert_id+"]:first a.imc--doc-obri")
						.focus();

			},
			envia = function(e) {

				e.preventDefault();

				doc_tipus = imc_document.attr("data-tipus");
				doc_id = imc_document.attr("data-id");

				// revisa

				imc_doc_electronic_form
					.find(".imc--annex-error")
						.remove();

				var revisa_error = false
					,text_error = false;

				if (doc_tipus === "e") {

					var num_maxim = parseInt(imc_document.attr("data-num"), 10)
						titol_val = imc_doc_electronic_form.find(".imc--titol:first input").val();

					var el_inputs_file = imc_doc_electronic_form.find("input[type=file]:first"),
						valor = el_inputs_file.val();

					if (!valor || valor === "") {

						text_error = txtErrorCapArxiu;
						revisa_error = true;

					} else {

						var arxiu = el_inputs_file[0].files[0]
							,arxiu_nom = arxiu.name
							,arxiu_extensio = arxiu_nom.split('.')[arxiu_nom.split('.').length - 1].toLowerCase()
							,arxiu_mida = arxiu.size
							,arxiu_mida_MB = (arxiu.size / 1048576).toFixed(2)
							,arxiu_mida_KB = (arxiu.size / 1024).toFixed(2);

						var tipus_posibles = imc_document.attr("data-extensions")
							,tipus_posibles_arr = tipus_posibles.split(",")
							,esTipusPosible = false;

						$(tipus_posibles_arr)
							.each(function() {

								var ext_tipus = this;

								esTipusPosible = (arxiu_extensio == ext_tipus) ? true : false;

								if (esTipusPosible) {
									return false;
								}

							});

						if (!esTipusPosible && tipus_posibles !== "null") {

							text_error = txtErrorTipusNoPermes;
							revisa_error = true;

						}

						var mida_max = imc_document.attr("data-mida")
							,esMB = (mida_max.indexOf("MB") !== -1) ? true : false
							,mida_max_num = (esMB) ? parseInt( mida_max.replace("MB", ""), 10 ) : parseInt( mida_max.replace("KB", ""), 10 )
							,mida_max_BT = (esMB) ? mida_max_num * 1048576 : mida_max_num * 1024;


						// comprovem la mida

						var esMidaPosible = (mida_max_BT > arxiu_mida) ? true : false;

						if (!esMidaPosible && imc_document.attr("data-mida") !== "null") {

							text_error = txtErrorMidaGran;
							revisa_error = true;

						}

					}



				}

				if (revisa_error && text_error) {

					var error_lloc = imc_doc_electronic_form.find(".imc-input-annex:first");

					$("<div>")
						.addClass("imc--annex-error")
						.text( text_error )
						.appendTo( error_lloc );

				 	return;
				}

				// envia

				// missatge

				var txt_enviant = (doc_tipus === "e") ? txtAnnexantDades : txtDesantDades;

				imc_doc_elms
					.attr("aria-hidden", "true")
					.removeClass("imc--on");

				imc_doc_missatge
					.find("h2 span")
						.text( txt_enviant )
						.end()
					.find("div")
						.text( "" )
						.end()
					.removeClass("imc--enviat-correcte imc--enviat-error")
					.addClass("imc--enviant")
					.attr("aria-hidden", "false")
					.addClass("imc--on");

				// envia config

				var	pag_url = APP_ANNEXE_ANNEXA,
					pag_form = (doc_tipus === "e") ? imc_doc_electronic_form[0] : imc_doc_presencial_form[0],
					formData = new FormData();

				// dades

				formData
					.append("idPaso", APP_TRAMIT_PAS_ID);

				formData
					.append("idAnexo", doc_id);

				formData
					.append("tipo", doc_tipus);

				var num_maxim = parseInt(imc_document.attr("data-num"), 10)
					titol_val = imc_doc_electronic_form.find(".imc--titol:first input").val();

				if (num_maxim > 1) {

					formData
						.append("titulo", titol_val);

				}

				// electronic: arxiu
				// presencial: checkbox

				if (doc_tipus === "e") {

					var form_annexes = imc_doc_electronic_form.find("input[type=file]:first"),
						hiHaArxius = (form_annexes.length) ? true : false;

					formData
						.append('fichero', form_annexes[0].files[0]);

				} else {

					formData
						.append("anexo_presencial", $("#anexo_presencial").prop("checked"));

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
						} else if (estat === "WARNING") {
							finalitzat({ estat: "WARNING", json: json });
						} else {
							finalitzat({ estat: "SUCCESS", json: json });
						}

					})
					.fail(function(dades, tipus, errorThrown) {

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
					json = settings_opcions.json
					url = json.url;

				var titol = (estat === "fail") ? txtDadesEnviadesErrorTitol : json.mensaje.titulo,
					text = (estat === "fail") ? txtDadesEnviadesErrorText : json.mensaje.texto,
					clase = (estat === "SUCCESS") ? "imc--enviat-correcte" : "imc--enviat-error";

				if (titol === "") {

					titol = txtDadesEnviadesCorrecteTitol;

				}

				imc_doc_missatge
					.find("h2 span")
						.text( titol )
						.end()
					.find("div")
						.text( text )
						.end()
					.find(".imc--tanca")
						.off(".recarrega")
						.end()
					.removeClass("imc--enviant")
					.addClass( clase );

				envia_ajax = false;

				consola("finalitzat");

				// al tancar, recarregar

				if (estat === "SUCCESS" || estat === "WARNING") {

					imc_doc_missatge
						.find(".imc--tanca")
							.on("click.recarrega", recarrega);

				}

				if (estat === "fail" || estat === "error" || estat === "fatal") {

					if (estat === "fail") {
						url = APP_;
					}

					imc_contenidor
						.remove();

					var debug = json.mensaje.debug;

					imc_missatge
						.appMissatge({ accio: "error", titol: titol, text: text, debug: debug, alTancar: function() { document.location = url; } });

				}

			},
			recarrega = function() {

				$("html, body")
					.removeClass("imc--sense-scroll");

				imc_doc_missatge
					.find(".imc--tanca")
						.off(".recarrega");

				imc_contingut_c
					.appPas({ pas: APP_TRAMIT_PAS_ID, recarrega: true });

			},
			errorTorna = function() {

				var capa_aMostrar = (doc_tipus === "e") ? imc_doc_electronic : imc_doc_presencial;

				capa_aMostrar
					.attr("aria-hidden", "false")
					.addClass("imc--on");

				imc_doc_missatge
					.attr("aria-hidden", "true")
					.removeClass("imc--on");

			};

		// inicia
		inicia();

	});
	return this;
}



// appAnnexaArxiu

$.fn.appAnnexaArxiu = function(opcions){
	var settings = $.extend({
			contenidor: false
		}, opcions);
	this.each(function(){
		var	elm = $(this),
			input_annex_el = elm.find(".imc-input-annex:first"),
			annexa_input = elm.find("input[type=file]:first"),
			arxiu_annexat_el = elm.find(".imc--arxiu-annexat:first"),
			activa = function(e) {

				var annexa_bt = $(this);

				annexa_input = annexa_bt.closest(".imc--arxiu").find("input[type=file]:first");
				arxiu_annexat_el = annexa_bt.closest(".imc--arxiu").find(".imc--arxiu-annexat:first"),

				annexa_input
					.trigger("click");

			},
			pinta = function(e) {

				annexa_input = $(this);

				var arxiu_val = annexa_input.val(),
					hiHaValor = (arxiu_val !== "") ? true : false;

				if (hiHaValor) {

					// revisem dades

					if (arxiu_val.indexOf("fakepath") !== -1) {
						arxiu_val = arxiu_val.replace("C:\\fakepath\\", "");
					}

					if (arxiu_val.indexOf("\\") !== -1) {
						arxiu_val = arxiu_val.split('\\')[arxiu_val.split('\\').length - 1];
					}


					// pintem dades

					arxiu_annexat_el
						.find("p")
							.text( arxiu_val );

					input_annex_el
						.attr("data-emplenat", "si");

					// popup tabula

					imc_document
						.appPopupTabula();

				}

			},
			elimina = function(e) {

				var bt_elimina = $(this);

				bt_elimina
					.closest(".imc-input-annex")
						.find(".imc--annex-error")
							.remove();

				bt_elimina
					.closest("li")
						.remove();

				// input original

				annexa_input
					.val("");

				arxiu_annexat_el
						.find("p")
							.text( "" );

				input_annex_el
					.removeAttr("data-emplenat");

			},
			resalta = function(e) {

				e.preventDefault();

				var file_el = $(this);

				file_el
					.addClass("imc--ann-resalta");

			},
			iguala = function(e) {

				e.preventDefault();

				var file_el = $(this);

				file_el
					.removeClass("imc--ann-resalta");

			},
			transferix = function(e) {

				e.preventDefault();

				var file_el = $(this)
					,file_input = file_el.find("input[type=file]:first");

				const nova_transferencia = new DataTransfer();

				nova_transferencia
					.items
						.add( e.originalEvent.dataTransfer.files[0] );

				file_input[0].files = nova_transferencia.files;

				file_input
					.trigger("change");

			};

		// events

		elm
			.off(".appAnnexaArxiu")
			.on("click.appAnnexaArxiu", ".imc-input-annex .imc-bt-anexa", activa)
			.on("click.appAnnexaArxiu", ".imc-input-annex .imc-bt-elimina", elimina)
			.on("change.appAnnexaArxiu", ".imc-input-annex input[type=file]:first", pinta)
			.on("dragenter.appAnnexaArxiu, dragover.appAnnexaArxiu", ".imc-document-contingut", resalta)
			.on("dragleave.appAnnexaArxiu, drop.appAnnexaArxiu", ".imc-document-contingut", iguala)
			.on("drop.appAnnexaArxiu", ".imc-document-contingut", transferix);

	});
	return this;
}


// appAnnexaLlistat

$.fn.appAnnexaLlistat = function(opcions){

	var settings = $.extend({
			contenidor: false
		}, opcions);

	this.each(function(){
		var	elm = $(this)
			,annex_id = false
			,arxiu_id = false
			,arxiu_text = false
			,el_doc_info = imc_document.find(".imc--doc-info:first")
			,el_input_file = imc_doc_electronic.find(".imc--arxiu input[type=file]:first")
			,doc_tipus = false
			,arxiuObri = function(e) {

				// dades annex

				var bt = $(this),
					bt_titol_text = bt.find("strong:first").text(),
					item_bt = bt.closest("li"),
					bt_id = item_bt.attr("data-id"),
					bt_omplit = item_bt.attr("data-omplit"),
					bt_num = parseInt( item_bt.attr("data-num"), 10),
					bt_extensions = item_bt.attr("data-extensions"),
					bt_mida = item_bt.attr("data-mida"),
					bt_ajuda = item_bt.attr("data-ajuda");

				doc_tipus = item_bt.attr("data-tipus"); // e (electrònic), p (presencial)

				var el_annexats = item_bt.find(".imc--annexats:first"),
					items_annexats = el_annexats.find("strong");

				// esta completat?

				if (item_bt.hasClass("imc--completat")) {

					return;

				}

				// afegits

				imc_document_contingut
					.removeClass("imc--hi-ha-afegits")
					.removeClass("imc--no-annexar");

				// elements a mostrar

				imc_doc_elms
					.attr("aria-hidden", "true")
					.removeClass("imc--on");

				imc_doc_electronic_form
					.find(".imc-bt-elimina")
						.trigger("click");

				if (doc_tipus === "e") {

					imc_doc_electronic
						.attr("aria-hidden", "false")
						.addClass("imc--on");

				} else {

					imc_doc_presencial
						.attr("aria-hidden", "false")
						.addClass("imc--on");

					var estaOmplit = (bt_omplit === "c") ? true : false;

					imc_doc_presencial
						.find("input:first")
							.prop("checked", estaOmplit);

				}



				// document nom

				imc_document
					.find("h2 span")
						.text( bt_titol_text )
						.end()
					.attr({ "data-id": bt_id, "data-omplit": bt_omplit, "data-num": bt_num, "data-extensions": bt_extensions, "data-mida": bt_mida, "data-tipus": doc_tipus });

				// extensions document

				el_doc_info
					.html("");

				var opcions_codi = item_bt.find(".imc--opcions").html();

				if (typeof opcions_codi !== "undefined") {

					el_doc_info
						.html( opcions_codi );

				}

				el_input_file
					.removeAttr("accept");

				if (bt_extensions !== "" && bt_extensions !== "null") {

					var el_ext = bt_extensions.split(","),
						el_ext_size = el_ext.length;

					var ext_accept = "";

					$(el_ext)
						.each(function(i) {

							var ext = this;

							ext_accept += (i === el_ext_size-1) ? "application/"+ext : "application/"+ext+",";

						});

				}

				// ajuda document

				if (bt_ajuda !== "" && bt_ajuda !== "null") {

					$("<div>")
						.addClass("imc--important")
						.html( bt_ajuda )
							.appendTo( el_doc_info );

				}


				// es pot signar?

				var signants_ = item_bt.find(".imc--signants")
					,hiHa_signants = (signants_.length) ? true : false;

				if (hiHa_signants) {

					var signants_clone = signants_.clone()
						,signatures_text = item_bt.find(".imc--signatures:first p:first").text() + " ";

					el_doc_info
						.append( txt_annexar_signar_info )
						.append( signants_clone );


					// signatures "r"?

					var signants_els = el_doc_info.find(".imc--signants");

					signants_els
						.each(function() {

							var signants_el = $(this)
								,signants_llistat = signants_el.find("ul:first")
								,hiHa_r = signants_el.find("li[data-obligatorietat=r]").length;

							if (hiHa_r) {

								var p_txt = $("<p>").text( txtSignarUnDelsSeguents )
									,llistat_r = $("<ul>");

								var data_completat = signants_el.find("li[data-obligatorietat=r][data-signat=s]").length ? "s" : "n";

								$("<li>")
									.addClass("imc--signants-r")
									.attr("data-completat", data_completat)
									.html( p_txt )
									.append( llistat_r )
									.appendTo( signants_llistat );

								signants_el
									.find("li[data-obligatorietat=r]")
										.each(function() {

											llistat_r
												.append( $(this).clone() );

										});

								signants_el
									.find("ul:first > li[data-obligatorietat=r]")
										.remove();

							}

						});

					imc_document
						.find(".imc--signans-info:first p:first span:first")
							.prepend( signatures_text );

				}


				// tabulador en el popup

				imc_document
					.appPopupTabula();


				//

				imc_document_contingut
					.addClass("imc--on");

				imc_document
					.addClass("imc--on")
					.attr("aria-hidden", "false");



			}
			,arxiuDescarrega = function(e) {

				var bt = $(this)
					,elm_annexe_id = bt.closest(".imc--doc-li").attr("data-id")
					,elm_id = bt.attr("data-id");

				document.location = APP_ANNEXE_DESCARREGA + "?idAnexo=" + elm_annexe_id + "&idPaso=" + APP_TRAMIT_PAS_ID + "&instancia=" + elm_id;

			}
			,plantillaDescarrega = function(e) {

				var bt = $(this)
					,elm_annexe_id = bt.closest(".imc--doc-li").attr("data-id");

				document.location = APP_PLANTILLA_DESCARREGA + "?idAnexo=" + elm_annexe_id + "&idPaso=" + APP_TRAMIT_PAS_ID;

			}
			,arxiuEsborraConfirma = function(e) {

				var bt = $(this)
					,annex = bt.closest(".imc--doc-li")
					,arxiu = bt.parent().find("strong");

				annex_id = annex.attr("data-id");
				arxiu_id = arxiu.attr("data-id");
				arxiu_text = arxiu.text();

				imc_missatge
					.appMissatge({
						boto: bt
						, bt: bt
						, accio: "esborra"
						, titol: txtVolEsborrarTitol
						, text: arxiu_text
						, alAcceptar: function() { arxiuEsborraEnvia(); }
					});

			}
			,arxiuEsborraEnvia = function(e) {

				imc_missatge
					.appMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtEsborrantAnnex, alMostrar: function() { arxiuEsborraEnviant(); } });

			}
			,arxiuEsborraEnviant = function(e) {

				// envia config

				var	pag_url = APP_ANNEXE_ESBORRA
					,formData = new FormData();

				// dades

				formData
					.append("idPaso", APP_TRAMIT_PAS_ID);

				formData
					.append("idAnexo", annex_id);

				formData
					.append("instancia", arxiu_id);

				formData
					.append("archivo", arxiu_text);

				// envia ajax

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

					var json = data;

					if (json.estado === "SUCCESS" || json.estado === "WARNING") {

						arxiuEsborrat();

					} else {

						envia_ajax = false;

						consola("Annexar esborra: error des de JSON");

						imc_contenidor
							.errors({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, debug: data.mensaje.debug, url: json.url });

					}

				})
				.fail(function(dades, tipus, errorThrown) {

					if (tipus === "abort") {
						return false;
					}

					consola("Annexar esborra: error des de FAIL");

					imc_contenidor
						.errors({ estat: "fail" });

				});

			}
			,arxiuEsborrat = function(opcions) {

				imc_missatge
					.appMissatge({ titol: txtEsborrantCorrecte, alTancar: function() { pasRecarrega(); } });

			}
			,pasRecarrega = function(opcions) {

				setTimeout(
					function() {

						imc_contingut_c
							.appPas({ pas: APP_TRAMIT_PAS_ID, recarrega: true });

					}
					,300
				);

			};

		// events

		elm
			.off(".appAnnexaLlistat")
			.on('click.appAnnexaLlistat', "li a", arxiuObri)
			.on('click.appAnnexaLlistat', ".imc--descarrega-plantilla", plantillaDescarrega)
			.on('click.appAnnexaLlistat', ".imc--descarrega", arxiuDescarrega)
			.on("click.appAnnexaLlistat", ".imc--esborra", arxiuEsborraConfirma)

	});
	return this;
}



var txt_annexar_signar_info =
		`<div class="imc--signans-info">
			<p><span></span> ${txtSignatRevise}</p>
			<ul>
				<li class="imc--ogligatori">
					<span>${txtSignantsInfo_obligatori}</span>
				</li>
				<li class="imc--opcional">
					<span>${txtSignantsInfo_opcional}</span>
				</li>
				<li class="imc--requerit">
					<span>${txtSignantsInfo_requerida}</span>
				</li>
			</ul>
		</div>`;

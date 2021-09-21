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

	imc_docs
		.appAnnexa();

	$(".imc-input-annexe")
		.appFitxerAnnexa();

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

				element
					.off('.appAnnexa')
					.on('click.appAnnexa', "li a", obri)
					.on('click.appAnnexa', ".imc--descarrega", descarrega)
					.on('click.appAnnexa', ".imc--descarrega-plantilla", descarregaPlantilla);

				element
					.appAnnexatEsborra();

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
			descarrega = function(e) {

				var bt = $(this)
					,elm_annexe_id = bt.closest(".imc--doc-li").attr("data-id")
					,elm_id = bt.attr("data-id");

				document.location = APP_ANNEXE_DESCARREGA + "?idAnexo=" + elm_annexe_id + "&idPaso=" + APP_TRAMIT_PAS_ID + "&instancia=" + elm_id;

			},
			descarregaPlantilla = function(e) {

				var bt = $(this)
					,elm_annexe_id = bt.closest(".imc--doc-li").attr("data-id");

				document.location = APP_PLANTILLA_DESCARREGA + "?idAnexo=" + elm_annexe_id + "&idPaso=" + APP_TRAMIT_PAS_ID;

			},
			obri = function(e) {

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

				doc_tipus = item_bt.attr("data-tipus"); // e, p

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

				el_afegits
					.html("");

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

						/*

					if (items_annexats.length) {

						var txt_titol = (items_annexats.length === 1) ? txtDocAnnexat : txtDocsAnnexats;

						$("<p>")
							.text( txt_titol )
								.appendTo( el_afegits );

						$("<ul>")
							.appendTo( el_afegits );

						$(items_annexats)
							.each(function() {

								var st = $(this),
									st_id = st.attr("data-id"),
									st_fitxer = st.attr("data-fitxer"),
									st_nom = st.text();

								$("<li>")
									.html( html_annexats )
										.appendTo( el_afegits.find("ul") );

								var ultim = el_afegits.find("li:last");

								ultim
									.find("p")
										.text( st_nom )
										.end()
									.find("button")
										.attr("data-id", st_id)
										.attr("data-fitxer", st_fitxer);


							});

						imc_document_contingut
							.addClass("imc--hi-ha-afegits");

						if (bt_num <= items_annexats.length) {

							imc_document_contingut
								.addClass("imc--no-annexar");

						}

						el_afegits
							.appAnnexatEsborra();

					}
					*/

				} else {

					imc_doc_presencial
						.attr("aria-hidden", "false")
						.addClass("imc--on");

					var estaOmplit = (bt_omplit === "c") ? true : false;

					imc_doc_presencial
						.find("input:first")
							.prop("checked", estaOmplit);

				}

				// obri animació

				var bt_ico = bt.find(".imc--doc:first"),
					bt_ico_T = bt_ico.offset().top,
					bt_ico_L = bt_ico.offset().left,
					bt_ico_W = bt_ico.outerWidth(),
					bt_ico_H = bt_ico.outerHeight();

				ico_anim = $("<div>").addClass("imc-ico-anim").css({ top: bt_ico_T+"px", left: bt_ico_L+"px", width: bt_ico_W+"px", height: bt_ico_W+"px" }).appendTo( imc_doc_c );

				imc_document
					.addClass("imc--on");

				ico_anim
					.animate(
						{ top: "50%", left: "50%", borderRadius: "50%" }
						,200
						,function() {

							carrega();

						})
					.addClass("imc--centra");

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

					//el_input_file
						//.attr("accept", ext_accept);

				}

				// ajuda document

				if (bt_ajuda !== "" && bt_ajuda !== "null") {

					$("<div>")
						.addClass("imc--important")
						.html( bt_ajuda )
							.appendTo( el_doc_info );

				}

				// tabulador en el popup

				imc_document
					.appPopupTabula();

			},
			carrega = function() {

				ico_anim_carrega = $("<div>").addClass("imc-ico-carrega").appendTo( imc_doc_c );

				ico_anim_carrega
					.animate(
						{ opacity: "1" }
						,500
						,function() {

							carregat();

						});

			},
			carregat = function() {

				var window_T = $(window).scrollTop(),
					ifr_T = imc_document_contingut.offset().top,
					ifr_L = imc_document_contingut.offset().left,
					ifr_W = imc_document_contingut.outerWidth(),
					ifr_H = imc_document_contingut.outerHeight();

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

				imc_document_contingut
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

				/*

				// fons cancela

				imc_doc_c
					.off('.appAnnexa')
					.on('click.appAnnexa', tanca);
				
				*/

			},
			tanca = function() {

				imc_document
					.addClass("imc--off");

				$("html, body")
					.removeClass("imc--sense-scroll");

				setTimeout(
					function() {

						imc_document_contingut
							.removeClass("imc--on");

						imc_document
							.removeClass("imc--on imc--off")
							.appPopupTabula({ accio: "finalitza" });


					}, 300);

			},
			envia = function(e) {

				e.preventDefault();

				doc_tipus = imc_document.attr("data-tipus");
				doc_id = imc_document.attr("data-id");

				// revisa

				imc_doc_electronic_form
					.find(".imc--annexe-error")
						.remove();

				var revisa_error = false
					,text_error = false;

				if (doc_tipus === "e") {

					var docs_annexats = imc_doc_electronic_form.find(".imc--per-afegir:first input[type=file]");

					var num_maxim = parseInt(imc_document.attr("data-num"), 10)
						titol_val = imc_doc_electronic_form.find(".imc--titol:first input").val();

					if (!docs_annexats.length) {

						text_error = txtErrorCapArxiu;
						revisa_error = true;

					} else if (num_maxim > 1 && titol_val === "") {

						text_error = "Falta incloure un títol al annex";
						revisa_error = true;

					} else {

						imc_doc_electronic_form
							.find(".imc--arxiu:first input[type=file]:first")
								.each(function() {

									var el_inputs_file = $(this),
										valor = el_inputs_file.val();

									if (!valor || valor === "") {

										text_error = txtErrorCapArxiu;
										revisa_error = true;

									}

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

									if (esMB) {

										esMidaPosible = (mida_max_num > arxiu_mida_MB) ? true : false;

									} else {

										esMidaPosible = (mida_max_num > arxiu_mida_KB) ? true : false;

									}
									
									if (!esMidaPosible && imc_document.attr("data-mida") !== "null") {

										text_error = txtErrorMidaGran;
										revisa_error = true;

									}

								});

					}

				}

				if (revisa_error && text_error) {

					var error_lloc = imc_doc_electronic_form.find(".imc-input-annexe:first");

					$("<div>")
						.addClass("imc--annexe-error")
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

				// electronic - arxius
				// presencial - checkbox

				if (doc_tipus === "e") {

					var form_annexes = imc_doc_electronic_form.find("input[type=file]"),
						hiHaArxius = (form_annexes.length) ? true : false;

					if (hiHaArxius) {

						form_annexes
							.each(function(i) {

								var el_annex = $(this);

								if (el_annex.val() !== "") {

									formData
										.append('fichero', el_annex[0].files[0]);

								}

							});

					}

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

				/*
				if (estat === "SUCCESS" && doc_tipus === "e") {

					finalitzatElectronic(json);

				} else if (estat === "SUCCESS" && doc_tipus === "p") {

					finalitzatPresencial(json);

				}*/

				// al tancar, recarregar

				if (estat === "SUCCESS" || estat === "WARNING") {

					imc_doc_missatge
						.find(".imc--tanca")
							.on("click.recarrega", recarrega)

				}

				if (estat === "fail" || estat === "error" || estat === "fatal") {

					if (estat === "fail") {
						url = APP_;
					}

					imc_contenidor
						.remove();

					imc_missatge
						.appMissatge({ accio: "error", titol: titol, text: text, alTancar: function() { document.location = url; } });

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
			finalitzatElectronic = function(json) {

				var json_annexes = json.datos.anexos
					,json_completat = json.datos.completado || "n"
					,json_seguent_id = json.datos.siguiente || false;

				if (json_annexes.length) {

					$(json_annexes)
						.each(function(i) {

							var el_annex = this;

							var id = el_annex.id,
								esObligatori = el_annex.obligatorio || false,
								fitxers = el_annex.ficheros || false;

							var doc_item = imc_docs.find("li[data-id="+id+"]:first");

							if (esObligatori) {
								
								if ( esObligatori === "s" ) {

									var doc_valor = doc_item.find(".imc--opcional:first");

									doc_valor
										.find("span")
											.text( txtObligatori )
											.end()
										.removeClass("imc--opcional")
										.addClass("imc--ogligatori");

									doc_item
										.attr("data-obligatori", "s");

								} else {
									
									var doc_valor = doc_item.find(".imc--ogligatori:first");

									doc_valor
										.find("span")
											.text( txtOpcional )
											.end()
										.removeClass("imc--ogligatori")
										.addClass("imc--opcional");

									doc_item
										.attr("data-obligatori", "n");

								}

							}

							if (fitxers) {

								$(fitxers)
									.each(function(j) {

										var fi = this,
											fi_id = fi.id,
											fi_fitxer = fi.fichero,
											fi_titol = fi.titulo;

										var doc_item_annexats = doc_item.find(".imc--annexats:first");

										var annexats_num = doc_item_annexats.find("li").length;

										if (annexats_num) {

											doc_item_annexats
												.find("p:first")
													.text( txtDocsAnnexats );

										} else {

											var html_ul = $("<ul>")
												,html_p = $("<p>").text( txtDocAnnexat );

											doc_item_annexats
												.append( html_p )
												.append( html_ul );

										}

										var annexats_llista = doc_item_annexats.find("ul:first");


										var html_strong = $("<strong>").attr({ "data-id": fi_id, "data-fitxer": fi_fitxer }).text( fi_fitxer )
											,html_button = $("<button>").addClass("imc-bt imc--ico imc--descarrega").attr({ "data-id": fi_id, "type": "button" }).html( $("<span>").text( txtDescarrega ) )
											,html_li = $("<li>").append( html_strong ).append( html_button );

										annexats_llista
											.append( html_li );

										var doc_valor = doc_item.find(".imc--no-completat:first");

										doc_valor
											.find("span")
												.text( txtCompletat )
												.end()
											.removeClass("imc--no-completat")
											.addClass("imc--completat");

										doc_item
											.attr("data-omplit", "c");

									});

							}

						});

				}

				// completat?

				estaCompletat(json_completat, json_seguent_id);

			},
			finalitzatPresencial = function(json) {

				var json_annexes = json.datos.anexos
					,json_completat = json.datos.completado || "n"
					,json_seguent_id = json.datos.siguiente || false;

				if (json_annexes.length) {

					$(json_annexes)
						.each(function(i) {

							var el_annex = this;

							var id = el_annex.id,
								esObligatori = el_annex.obligatorio || false,
								estaAnnexat = el_annex.anexado || false;

							var doc_item = imc_docs.find("li[data-id="+id+"]:first");

							if (esObligatori) {
								
								if ( esObligatori === "s" ) {

									var doc_valor = doc_item.find(".imc--opcional:first");

									doc_valor
										.find("span")
											.text( txtObligatori )
											.end()
										.removeClass("imc--opcional")
										.addClass("imc--ogligatori");

									doc_item
										.attr("data-obligatori", "s");

								} else {
									
									var doc_valor = doc_item.find(".imc--ogligatori:first");

									doc_valor
										.find("span")
											.text( txtOpcional )
											.end()
										.removeClass("imc--ogligatori")
										.addClass("imc--opcional");

									doc_item
										.attr("data-obligatori", "n");

								}

							}

							if (estaAnnexat) {

								if ( estaAnnexat === "s" ) {

									var doc_valor = doc_item.find(".imc--no-completat:first");

									doc_valor
										.find("span")
											.text( txtCompletat )
											.end()
										.removeClass("imc--no-completat")
										.addClass("imc--completat");

									doc_item
										.attr("data-omplit", "c");

								} else {
									
									var doc_valor = doc_item.find(".imc--completat:first");

									doc_valor
										.find("span")
											.text( txtNoCompletat )
											.end()
										.removeClass("imc--completat")
										.addClass("imc--no-completat");

									doc_item
										.attr("data-omplit", "v");

								}

							}

						});

				}

				// completat?

				estaCompletat(json_completat, json_seguent_id);

			},
			estaCompletat = function(json_completat, json_seguent_id) {

				imc_navegacio
					.find(".imc--seguent")
						.parent()
							.remove();

				if (json_completat === "s") {

					var li = $("<li>").html( HTML_SEGUENT_PAS );

					imc_navegacio
						.find("ul:first")
							.append( li );

					imc_navegacio
						.find(".imc--seguent:first")
							.attr("href", "#pas/"+json_seguent_id);

				}

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



// appFitxerAnnexa

$.fn.appFitxerAnnexa = function(opcions){
	var settings = $.extend({
			contenidor: false
		}, opcions);
	this.each(function(){
		var	elm = $(this),
			elm_arxiu = elm.find(".imc--arxiu:first"),
			annexa_bt = elm_arxiu.find(".imc--bt-anexa:first"),
			input_elm = elm_arxiu.find("input[type=file]:first"),
			elm_perAfegir = elm.find(".imc--per-afegir:first"),
			html_arxiu = false,
			prepara = function() {

				elm
					.off(".appFitxerAnnexa")
					.on("click.appFitxerAnnexa", ".imc-bt-anexa", activa)
					.on("click.appFitxerAnnexa", ".imc-bt-elimina", elimina)
					.removeClass("imc-emplenat");

				input_elm
					.val("")
					.off(".appFitxerAnnexa")
					.on("change.appFitxerAnnexa", pinta);

				imc_document_contingut
					.removeClass("imc--num-maxim");

				// HTML arxiu

				html_arxiu = elm_perAfegir.find("li:first").html();

				elm
					.attr("data-html", html_arxiu);

				elm_perAfegir
					.html("");

			},
			activa = function() {

				input_elm
					.trigger("click");
				
			},
			pinta = function(e) {
				
				input_elm = $(this);

				var arxiu_val = input_elm.val(),
					hiHaValor = (arxiu_val !== "") ? true : false;

				if (hiHaValor) {

					// hi ha llista?

					if ( !elm_perAfegir.find("ul").length ) {

						$("<ul>")
							.appendTo( elm_perAfegir );

					}

					var elm_perAfegir_ul = elm_perAfegir.find("ul:first");

					// pintem

					if (arxiu_val.indexOf("fakepath") !== -1) {
						arxiu_val = arxiu_val.replace("C:\\fakepath\\", "");
					}

					if (arxiu_val.indexOf("\\") !== -1) {
						arxiu_val = arxiu_val.split('\\')[arxiu_val.split('\\').length - 1];
					}

					var input_clonat = input_elm.clone(true);

					$("<li>")
						.html( html_arxiu )
							.prependTo( elm_perAfegir_ul );

					var ultim_afegit = elm_perAfegir_ul.find("li:first");

					ultim_afegit
						.find("p")
							.text( arxiu_val )
							.end()
						.append( input_clonat );

					// input original
					/*
					input_elm
						.val("");

					if (input_elm.val() !== "") {
						input_elm.replaceWith( input_elm = input_elm.clone( true ) );
					}*/

					// revisem número màxim

					imc_document_contingut
						.addClass("imc--num-maxim");

					imc_document
						.appPopupTabula();

				}
				
			},
			elimina = function(e) {
				
				var bt_elimina = $(this);

				bt_elimina
					.closest(".imc-input-annexe")
						.find(".imc--annexe-error")
							.remove();

				bt_elimina
					.closest("li")
						.remove();

				// input original
				
				input_elm
					.val("");

				if (input_elm.val() !== "") {
					input_elm.replaceWith( input_elm = input_elm.clone( true ) );
				}

				// revisem número màxim

				imc_document_contingut
					.removeClass("imc--num-maxim");

			},
			revisa = function(e) {

				// imc--no-annexar

				var num_maxim = parseInt(imc_document.attr("data-num"), 10),
					num_per_afegir = elm_perAfegir.find("li").length;

				if (num_maxim === num_per_afegir) {

					imc_document_contingut
						.addClass("imc--num-maxim");

				} else {

					imc_document_contingut
						.removeClass("imc--num-maxim");

					//elm_perAfegir
					//	.html("");

				}

			};
		
		// prepara
		prepara()

	});
	return this;
}


// appAnnexatEsborra

$.fn.appAnnexatEsborra = function(opcions){
	var settings = $.extend({
			contenidor: false
		}, opcions);
	this.each(function(){
		var	elm = $(this),
			bt_esborra = false,
			bt_esborra_id = false,
			bt_esborra_fitxer = false,
			bt_esborra_anexe_id = false,
			annexe_id = false,
			annexe_num = false,
			annexe_elm = false,
			annexe_elm_annexats = false,
			prepara = function() {
				
				elm
					.off(".appAnnexatEsborra")
					.on("click.appAnnexatEsborra", ".imc--esborra", verifica);

			},
			verifica = function(e) {

				bt = $(this);

				annex = bt.closest(".imc--doc-li");
				annex_id = annex.attr("data-id");
				annex_num_max = parseInt( annex.attr("data-num"), 10);

				arxiu = bt.parent().find("strong");
				arxiu_id = arxiu.attr("data-id");
				arxiu_text = arxiu.text();

				imc_missatge
					.appMissatge({
						boto: bt
						, accio: "esborra"
						, titol: txtVolEsborrarTitol
						, text: arxiu_text
						, alAcceptar: function() { esborra(); }
					});

			},
			esborra = function(e) {

				imc_missatge
					.appMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtEsborrantAnnex });

				// envia config

				var	pag_url = APP_ANNEXE_ESBORRA,
					formData = new FormData();

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

						esborrat();

					} else {

						envia_ajax = false;

						consola("Annexar esborra: error des de JSON");

						imc_contenidor
							.errors({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, url: json.url });

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
				
			},
			esborrat = function(opcions) {

				imc_missatge
					.appMissatge({ titol: txtEsborrantCorrecte, alTancar: function() { recarrega(); } });

			},
			recarrega = function(opcions) {
				/*
				var settings_opcions = $.extend({
					estat: false,
					json: false
				}, opcions);

				var estat = settings_opcions.estat,
					json = settings_opcions.json;
				*/

				setTimeout(
					function() {

						imc_contingut_c
							.appPas({ pas: APP_TRAMIT_PAS_ID, recarrega: true });

					}
					,300
				);

				

				/*
				if (estat === "error" || (estat === "fail")) {

					// missatge en el botó

					bt_esborra
						.find("span")
							.text( txtEsborra )
							.end()
						.addClass("imc-bt-esborra")
						.removeClass("imc-bt-esborrant");

				} else {

					bt_esborra
						.parent()
							.slideUp( 200, function() {
								
								$(this)
									.remove();

								annexe_elm = imc_docs.find("li[data-id="+annexe_id+"]:first");
								annexe_elm_annexats = annexe_elm.find(".imc--annexats:first");

								annexe_elm_annexats
									.find("strong[data-id="+bt_esborra_id+"]:first")
										.closest("li")
											.remove();

								repasa();

							});

				}
				*/

			},
			repasa = function() {

				var num_afegits_actual = imc_document_contingut.find(".imc--afegits:first li").length;

				if ( num_afegits_actual === 0 ) {

					imc_document_contingut
						.removeClass("imc--hi-ha-afegits imc--no-annexar")

					annexe_elm_annexats
						.html("");

					var doc_valor = annexe_elm.find(".imc--completat:first");

					doc_valor
						.find("span")
							.text( txtNoCompletat )
							.end()
						.removeClass("imc--completat")
						.addClass("imc--no-completat");

					annexe_elm
						.attr("data-omplit", "v");

				} else if (num_afegits_actual < annex_num_max) {

					imc_document_contingut
						.removeClass("imc--no-annexar");

				}

			};
		
		// prepara
		prepara()

	});
	return this;
}


/*

envia = function(e) {

				imc_missatge
					.appMissatge({
						accio: "envia",
						titol : txtTitolEnviant
					});

				var form_el = $("#imc-img-parteFotosNuxeoForm");

				var form_action = form_el.attr('action'),
					img_el = form_el.find("#foto");
				
				var form_dades = new FormData();

				form_dades
					.append('foto', form_el.find('#foto')[0].files[0]);
				
				form_dades
					.append('titulo', form_el.find('#tit').val());

				form_dades
					.append('operacion', $('#imc-img-operacion').val());

				form_dades
					.append('cod_actuacion', $('#imc-img-cod_actuacion').val());

				form_dades
					.append('relevo', $('#imc-img-relevo').val());

				form_dades
					.append('iddoc', "");

				var ajax  = new XMLHttpRequest();

				ajax
					.upload
						.addEventListener('progress',function(evt){

							var percentatge = (evt.loaded/evt.total)*100;
							console.log(percentatge);
							enviaProgres(Math.round(percentatge));

						},false);

				ajax
					.addEventListener('load',function(evt){

						if (evt.target.responseText.toLowerCase().indexOf('error')>=0) {
							
							error( txtImatgeEnviamentError );

						} else {

							window.location = "mmepartefotos.do?id="+ $('#imc-img-cod_actuacion').val() +"/"+ $('#imc-img-relevo').val();

						}
						
					},false);

				ajax
					.addEventListener('error',function(evt){

						error( txtImatgeEnviamentError );
						enviaProgres(0);

					},false);

				ajax
					.addEventListener('abort',function(evt){

						error( txtImatgeEnviamentAnulada );
						enviaProgres(0);

					},false);

				ajax.open('POST',form_action);

				ajax.send(form_dades);

				return false;
				

			},
			enviaProgres = function(percentatge) {

				imc_progres
					.html(percentatge+'%');

				if (percentatge === 100) {
					completatProgres();
				}

			},
			completatProgres = function() {

				var completat_txt = $("<p>").addClass("imc-enviament-completat").text( txtEnviamentCompletat );

				imc_missatge
					.find(".imc--info")
						.append( completat_txt );

			},


			*/
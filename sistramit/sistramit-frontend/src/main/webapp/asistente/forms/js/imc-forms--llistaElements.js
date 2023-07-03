// CAPTCHA

/*
APP_FORM_LE_AFEGIX
,APP_FORM_LE_MODIFICA
,APP_FORM_LE_AVALUA
,APP_FORM_LE_DESA
*/

var imc_forms_taula
	,imc_forms_taula_form;


// events annexats a la llista d'elements (taula)

$.fn.appFormsLlistaElements = function(options) {

	var settings = $.extend({
			columnes: 0
			,cerca : false
			,filesMax: 0
			,filesNum: 0
			,operacions: false
			,autoOrdre: false
			,desDe: false
			,dades: false
		}, options);

	this.each(function(){
		var element = $(this)
			,columnes = settings.columnes
			,cerca = settings.cerca
			,filesMax = parseInt( settings.filesMax, 10)
			,filesNum = parseInt( settings.filesNum, 10)
			,operacions = settings.operacions
			,autoOrdre = settings.autoOrdre
			,desDe = settings.desDe
			,filesActuals = 0
			,dades = settings.dades
			,elm_id = false
			,elm_label = false
			,taula_body = false
			,envia_url = false
			,envia_ajax = false
			,json = false
			,inicia = function() {

				elm_id = element.attr("data-id");
				elm_label = element.find("legend:first").text();

				element
					.find(".imc--sense-resultats")
						.remove();

				element
					.removeAttr("data-cercat");

				element
					.find(".imc-el-taula-elements:first")
						.attr("data-desde", desDe);

				// files màximes?

				if (filesMax && !isNaN(filesMax)) {

					element
						.attr("data-max", filesMax);

				}

				// files altura?

				if (filesNum) {

					element
						.attr("data-altura", filesNum);

					var altura_taula = (2*filesNum) + 2.1;

					element
						.find(".imc-el-taula-elements:first")
							.css("height", altura_taula+"em");

				}

				// operacions?

				if (operacions) {

					// IBMO

					element
						.attr("data-operacions", operacions);

				}

				// auto ordre?

				if (autoOrdre && autoOrdre === "s") {

					element
						.attr("data-autoordre", autoOrdre);

				} else if (autoOrdre && autoOrdre === "n") {

					element
						.removeAttr("data-autoordre");

				}

				// si passe columnes

				if (columnes) {

					element
						.off(".appFormsLlistaElements")
						.on("click.appFormsLlistaElements", "button.imc-bt-afegix", afegix)
						.on("click.appFormsLlistaElements", "button.imc-bt-elimina", elimina)
						.on("click.appFormsLlistaElements", "button.imc-bt-modifica", modifica)
						.on("click.appFormsLlistaElements", "button.imc-bt-consulta", consulta)
						.on("click.appFormsLlistaElements", "button.imc-bt-puja", puja)
						.on("click.appFormsLlistaElements", "button.imc-bt-baixa", baixa)
						.on("click.appFormsLlistaElements", ".imc-el-taula-elements tbody tr", selecciona);

					element
						.on("dblclick.appFormsLlistaElements", ".imc-el-taula-elements[data-desde=cerca] tbody tr", consulta)
						.on("click.appFormsLlistaElements", "button[data-accio=fila-consulta]", consulta)
						.on("dblclick.appFormsLlistaElements", ".imc-el-taula-elements[data-desde=inicia] tbody tr", modifica);

					element
						.find("button:not(.imc-bt-afegix)")
							.attr("disabled", "disabled");

					pintaEstructura();

				}

				// cerca?

				if (cerca) {

					element
						.attr("data-cerca", cerca)
						.on("click.appFormsLlistaElements", "button.imc-bt-cerca", cercaActiva);

					element
						.find("button.imc-bt-cerca")
							.removeAttr("disabled");

					var altura_minima = (2.6*filesNum) + 4.4;

					element
						.css("min-height", altura_minima+"em");

				}

				// si passe les dades

				if (dades) {

					pintaDades();

				}

			}
			,pintaEstructura = function() {

				// pintem element taula

				$("<table>")
					.append( $("<thead>").append("<tr>") )
					.append( $("<tbody>") )
					.appendTo( element.find(".imc-el-taula-elements:first") );

				var tr_elm = element.find(".imc-el-taula-elements:first thead tr:first");

				$("<th>")
					.addClass("imc--selector")
					.append( "" )
					.appendTo( tr_elm );

				$(columnes)
					.each(function() {

						var columna = this
							,col_id = columna.id
							,col_descripcio = columna.descripcion
							,col_ample = columna.ancho;

						$("<th>")
							.attr("data-id", col_id)
							.css({ width: col_ample+"%" })
							.append( $("<span>").text( col_descripcio ) )
							.appendTo( tr_elm );

					});

				$("<th>")
					.addClass("imc--consulta")
					.append( "" )
					.appendTo( tr_elm );

				taula_body = element.find("tbody:first");

				// pintem número màxim de files

				if (filesMax && !isNaN(filesMax)) {

					var element_txt = (filesMax === 1) ? txtFormDinTaulaElement : txtFormDinTaulaElements;

					element
						.find("legend:first")
							.append( " (" + txtFormDinTaulaMaxim + " " + filesMax + " " + element_txt + ")" );

				}

				// pintem popup fila detall

				$.when(
		
					$.get(APP_FORMS_ + "forms/html/imc--forms-taula.html?" + APP_VERSIO)

				).then(

					function( htmlFormsTaula ) {

						$("#imc-forms--taula")
							.remove();

						imc_forms_body
							.append( htmlFormsTaula );

						imc_forms_taula = $("#imc-forms--taula");
						imc_forms_taula_form = imc_forms_taula.find(".imc--form-contenidor:first");

						// literals

						imc_forms_taula
							.find("button[data-accio=llista-el-cancela] span")
								.text( txtFormDinCancela )
								.end()
							.find("button[data-accio=llista-el-desa] span")
								.text( txtFormDinDesa )
								.end()
							.find("button[data-accio=llista-el-tanca] span")
								.text( txtFormDinTanca )
								.end()
							.attr("data-id", elm_id);

					}

				).fail(

					function() {

						consola("Element llista: error des de carrega HTML (FAIL)");

						imc_contenidor
							.errors({ estat: "fail" });

					}

				);

			}
			,pintaDades = function() {

				var tbody_elm = element.find(".imc-el-taula-elements:first tbody:first");


				// si ve d'avalua, netejem les dades

				if (desDe === "avalua" || desDe === "cerca") {

					tbody_elm
						.html("");

				}


				// pintem dades

				$(dades)
					.each(function(i) {

						var fila = this.elemento;

						$("<tr>")
							.attr("data-id", i+1)
							.attr("data-dades", JSON.stringify(this))
							.appendTo( tbody_elm );

						var thead_taula_el = element.find(".imc-el-taula-elements:first thead:first")
							,tr_elm = element.find(".imc-el-taula-elements:first tbody:first tr:last");


						// pintem la fila amb <td>

						thead_taula_el
							.find("th")
								.each(function(j) {

									var th_el = $(this)
										,th_el_id = th_el.attr("data-id");

									if (j === 0) {

										$("<td>")
											.append( $("<input>").attr({ type: "radio", name: elm_id, id: elm_id + "_" + i }) )
											.appendTo( tr_elm );

									} else {

										$("<td>")
											.attr({ "data-id": th_el_id })
											.appendTo( tr_elm );

									}

							});

						// pintem la fila amb les dades

						$(fila)
							.each(function() {

								var columna = this
									,col_id = columna.id
									,col_tipus = columna.tipo
									,col_valor = columna.valor;

								if (col_valor !== "" && col_valor !== null && col_tipus === "i") {
									col_valor = columna.valor.descripcion || "";
								}

								if (col_valor !== "" && col_valor !== null && col_tipus === "l") {

									var col_llistat = col_valor
										,col_llistat_size = col_llistat.length
										,col_llistat_txt = "";

									$(col_llistat)
										.each(function(j) {

											var col_ll = this
												,col_desc = col_ll.descripcion;

											col_llistat_txt += col_desc;
											col_llistat_txt += (j === col_llistat_size-1) ? "" : ", ";

										});

									col_valor = col_llistat_txt;
									
								}

								// cerquem la cel·la i afegim

								tr_elm
									.find("td[data-id="+col_id+"]")
										.attr({ "data-tipus": col_tipus })
										.append( col_valor );

						});

						// pintem botó consulta

						$("<button>")
							.attr({ type: "button", "data-accio": "fila-consulta", title: txtFormDinTaulaConsultaFila })
							.appendTo( tr_elm.find("td:last") );

					});

				// revisem número de files

				element
					.appFormsLlistaElementsFilesRevisa();


				// mostrem resultat si es una cerca

				if (desDe === "cerca") {

					element
						.attr("data-cercat", "s");

					imc_forms_missatge
						.appFormsMissatge({ araAmaga: true });

				}

			}
			,selecciona = function(e) {

				var fila_el = $(this)
					,fila_id = fila_el.attr("data-id")
					,fila_class = fila_el.hasClass("imc--seleccionada");

				// llevem la selecció si hi haguera

				fila_el
					.closest("tbody")
						.find("tr.imc--seleccionada")
							.removeClass("imc--seleccionada");

				// es lectura?

				var esLectura = (fila_el.closest(".imc-element").attr("data-lectura") === "s") ? true : false
					,esConsulta = (fila_el.closest(".imc-element").attr("data-operacions").includes("C")) ? true : false;

				if (esLectura && !esConsulta) {
					return;
				}

				// si no es lectura, seleccionem

				fila_el
					.find("input:first")
						.prop("checked", true)
						.end()
					.addClass("imc--seleccionada");

				element
					.find("button:not(.imc-bt-afegix)")
						.removeAttr("disabled");

				// revisem tabulació

				$("#imc-forms-contenidor")
					.appFormsPopupTabula({ enfocaEn: fila_el.find("input:first") });

			},
			afegix = function() {

				imc_forms_missatge
					.appFormsMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtFormDinTaulaAfegixCarregant, alMostrar: function() { afegixCarrega(); } });

			},
			afegixCarrega = function() {

				// serialitza

				var valorsSerialitzats = imc_forms_finestra.appSerialitza({ verifica: false });

				valorsSerialitzats["idCampoListaElementos"] = elm_id;

				// dades ajax

				var pag_url = APP_FORM_LE_AFEGIX,
					pag_dades = valorsSerialitzats;

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

						envia_ajax = false;

						imc_forms_contenidor
							.removeAttr("data-preevalua");

						json = data;

						if (json.estado === "SUCCESS" || json.estado === "WARNING") {

							if (json.estado === "WARNING") {

								imc_forms_missatge
									.appFormsMissatge({ accio: "warning", titol: data.mensaje.titulo, text: data.mensaje.texto, alAcceptar: function() { mostra(); } });

								return;

							}

							afegixObri(json);

						} else {

							envia_ajax = false;

							imc_forms_contenidor
								.removeAttr("data-preevalua");

							consola("Element llista, carrega afegix: error des de JSON");

							imc_forms_body
								.appFormsErrorsGeneral({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, debug: data.mensaje.debug, url: json.url });

						}

					})
					.fail(function(dades, tipus, errorThrown) {

						envia_ajax = false;

						imc_forms_contenidor
							.removeAttr("data-preevalua");

						if (tipus === "abort") {
							return false;
						}

						consola("Element llista, carrega afegix: error des de FAIL");

						imc_forms_body
							.appFormsErrorsGeneral({ estat: "fail" });

					});

			},
			afegixObri = function(json) {

				var json_html = new DOMParser().parseFromString(json.datos.html, "text/html");

				imc_forms_taula_form
					.html( $(json_html).find(".imc-form-contingut:first").html() );

				imc_forms_missatge
					.appFormsMissatge({ araAmaga: true });

				imc_forms_taula
					.attr("aria-hidden", "false")
					.appFormsLlistaElementsForm({ element_id: elm_id, titol: elm_label, desDe: "afegix" });

				imc_forms_taula
					.appFormsConfiguracio({ forms_json: json, desDe: "taula" });

				imc_forms_taula
				.attr("data-mayuscules", imc_forms_finestra.attr("data-mayuscules"))
					.appFormsPopupTabula();
					//.focus();

			},
			elimina = function(e) {

				var bt = $(this);

				imc_forms_missatge
					.appFormsMissatge({ accio: "esborra", titol: txtFormDinTaulaEsborraElementTitol, text: txtFormDinTaulaEsborraElementText, bt: bt, alAcceptar: function() { eliminem(); } });

			},
			eliminem = function() {

				var fila_el = taula_body.find("input:checked").closest("tr");

				fila_el
					.remove();

				imc_forms_missatge
					.appFormsMissatge({ araAmaga: true });

				reordena();

				// revisem número de files

				element
					.appFormsLlistaElementsFilesRevisa();

			},
			modifica = function(e) {

				// es lectura?

				var bt_elm = $(this)
					,esLectura = (bt_elm.closest(".imc-element").attr("data-lectura") === "s") ? true : false;

				if (esLectura) {
					return;
				}

				// si no es lectura, carreguem

				imc_forms_missatge
					.appFormsMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtFormDinTaulaModificaCarregant, alMostrar: function() { modificaCarrega(); } });


			},
			modificaCarrega = function() {

				// serialitza

				var valorsSerialitzats = imc_forms_finestra.appSerialitza({ verifica: false });

				valorsSerialitzats["idCampoListaElementos"] = elm_id;

				// index element

				var indexElement = taula_body.find("input:checked").closest("tr").attr("data-id");

				valorsSerialitzats["indiceElemento"] = parseInt(indexElement, 10) - 1;

				// dades ajax

				var pag_url = APP_FORM_LE_MODIFICA,
					pag_dades = valorsSerialitzats;

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

						envia_ajax = false;

						imc_forms_contenidor
							.removeAttr("data-preevalua");

						json = data;

						if (json.estado === "SUCCESS" || json.estado === "WARNING") {

							if (json.estado === "WARNING") {

								imc_forms_missatge
									.appFormsMissatge({ accio: "warning", titol: data.mensaje.titulo, text: data.mensaje.texto, alAcceptar: function() { mostra(); } });

								return;

							}

							modificaObri(json);

						} else {

							envia_ajax = false;

							imc_forms_contenidor
								.removeAttr("data-preevalua");

							consola("Element llista, carrega modifica: error des de JSON");

							imc_forms_body
								.appFormsErrorsGeneral({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, debug: data.mensaje.debug, url: json.url });

						}

					})
					.fail(function(dades, tipus, errorThrown) {

						envia_ajax = false;

						imc_forms_contenidor
							.removeAttr("data-preevalua");

						if (tipus === "abort") {
							return false;
						}

						consola("Element llista, carrega modifica: error des de FAIL");

						imc_forms_body
							.appFormsErrorsGeneral({ estat: "fail" });

					});

			},
			modificaObri = function(json) {

				var json_html = new DOMParser().parseFromString(json.datos.html, "text/html");

				imc_forms_taula_form
					.html( $(json_html).find(".imc-form-contingut:first").html() );

				imc_forms_missatge
					.appFormsMissatge({ araAmaga: true });

				imc_forms_taula
					.attr("aria-hidden", "false")
					.appFormsLlistaElementsForm({ element_id: elm_id, titol: elm_label, desDe: "modifica" });

				imc_forms_taula
					.appFormsConfiguracio({ forms_json: json, desDe: "taula" });

				imc_forms_taula
					.appFormsPopupTabula();
					//.focus();

			},
			consulta = function(e) {

				var el_activat = $(this);

				// és un botó o es una fila?

				var indexElement = 	(el_activat.hasClass("imc-bt-consulta")) ? taula_body.find("input:checked").closest("tr").attr("data-id") :
									(el_activat.attr("data-accio") === "fila-consulta" ) ? el_activat.closest("tr").attr("data-id") :
									el_activat.attr("data-id");


				// carreguem

				imc_forms_missatge
					.appFormsMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtFormDinTaulaModificaCarregant, alMostrar: function() { consultaCarrega(indexElement); } });


			},
			consultaCarrega = function(indexElement) {

				// serialitza

				var valorsSerialitzats = imc_forms_finestra.appSerialitza({ verifica: false });

				valorsSerialitzats["idCampoListaElementos"] = elm_id;

				// index element

				valorsSerialitzats["indiceElemento"] = parseInt(indexElement, 10) - 1;

				// dades ajax

				var pag_url = APP_FORM_LE_CONSULTA,
					pag_dades = valorsSerialitzats;

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

						envia_ajax = false;

						imc_forms_contenidor
							.removeAttr("data-preevalua");

						json = data;

						if (json.estado === "SUCCESS" || json.estado === "WARNING") {

							if (json.estado === "WARNING") {

								imc_forms_missatge
									.appFormsMissatge({ accio: "warning", titol: data.mensaje.titulo, text: data.mensaje.texto, alAcceptar: function() { mostra(); } });

								return;

							}

							consultaObri(json);

						} else {

							envia_ajax = false;

							imc_forms_contenidor
								.removeAttr("data-preevalua");

							consola("Element llista, carrega modifica: error des de JSON");

							imc_forms_body
								.appFormsErrorsGeneral({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, debug: data.mensaje.debug, url: json.url });

						}

					})
					.fail(function(dades, tipus, errorThrown) {

						envia_ajax = false;

						imc_forms_contenidor
							.removeAttr("data-preevalua");

						if (tipus === "abort") {
							return false;
						}

						consola("Element llista, carrega modifica: error des de FAIL");

						imc_forms_body
							.appFormsErrorsGeneral({ estat: "fail" });

					});

			},
			consultaObri = function(json) {

				var json_html = new DOMParser().parseFromString(json.datos.html, "text/html");

				imc_forms_taula_form
					.html( $(json_html).find(".imc-form-contingut:first").html() );

				imc_forms_missatge
					.appFormsMissatge({ araAmaga: true });

				imc_forms_taula
					.attr("aria-hidden", "false")
					.appFormsLlistaElementsForm({ element_id: elm_id, titol: elm_label, desDe: "consulta" });

				imc_forms_taula
					.appFormsConfiguracio({ forms_json: json, desDe: "taula" });

				imc_forms_taula
					.appFormsPopupTabula();

			},
			puja = function() {

				var fila_el = taula_body.find("input:checked").closest("tr")
					,fila_id = fila_el.attr("data-id")
					,fila_id_num = parseInt(fila_id, 10);

				if (fila_id_num === 1) {
					return;
				}

				var fila_clone = fila_el.clone()
					,fila_anterior = fila_el.prev();

				fila_el
					.remove();

				fila_anterior
					.before( fila_clone );

				reordena();

			},
			baixa = function() {

				var fila_el = taula_body.find("input:checked").closest("tr")
					,fila_id = fila_el.attr("data-id")
					,fila_id_num = parseInt(fila_id, 10);

				if (fila_id_num === taula_body.find("tr").length) {
					return;
				}

				var fila_clone = fila_el.clone()
					,fila_anterior = fila_el.next();

				fila_el
					.remove();

				fila_anterior
					.after( fila_clone );

				reordena();

			},
			reordena = function() {

				taula_body
					.find("tr")
						.each(function(i) {

							var tr_el = $(this);

							tr_el
								.attr("data-id", i+1);

						});

			},
			cercaActiva = function() {

				imc_forms_missatge
					.appFormsMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtFormDinTaulaCercaCarregant, alMostrar: function() { cercaCarrega(); } });

			},
			cercaCarrega = function() {

				// serialitza

				var valorsSerialitzats = imc_forms_finestra.appSerialitza({ verifica: false });

				valorsSerialitzats["idCampoListaElementos"] = elm_id;

				// dades ajax

				var pag_url = APP_FORM_LE_CERCA,
					pag_dades = valorsSerialitzats;

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

						envia_ajax = false;

						imc_forms_contenidor
							.removeAttr("data-preevalua");

						json = data;

						if (json.estado === "SUCCESS" || json.estado === "WARNING") {

							if (json.estado === "WARNING") {

								imc_forms_missatge
									.appFormsMissatge({ accio: "warning", titol: data.mensaje.titulo, text: data.mensaje.texto, alAcceptar: function() { mostra(); } });

								return;

							}

							cercaPinta(json);

						} else {

							envia_ajax = false;

							imc_forms_contenidor
								.removeAttr("data-preevalua");

							consola("Element llista, carrega cerca: error des de JSON");

							imc_forms_body
								.appFormsErrorsGeneral({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, debug: data.mensaje.debug, url: json.url });

						}

					})
					.fail(function(dades, tipus, errorThrown) {

						envia_ajax = false;

						imc_forms_contenidor
							.removeAttr("data-preevalua");

						if (tipus === "abort") {
							return false;
						}

						consola("Element llista, carrega cerca: error des de FAIL");

						imc_forms_body
							.appFormsErrorsGeneral({ estat: "fail" });

					});

			},
			cercaPinta = function(json) {

				var json_valors = json.datos.valor
					,json_valors_size = json_valors.length
					,llista_valor = (json_valors_size) ? json.datos.valor : false
					,llista_valor_size = (json_valors_size) ? llista_valor.length : false;


				// NO hi ha valors

				if (!json_valors || !json_valors.length || !llista_valor.length) {

					element
						.find(".imc--sense-resultats")
							.remove()
							.end()
						.removeAttr("data-cercat");

					$("<p>")
						.addClass("imc--sense-resultats")
						.text("No hi ha resultats")
						.appendTo( element );

					imc_forms_missatge
						.appFormsMissatge({ araAmaga: true });

					return;

				}

				// SÍ hi ha valors

				element
					.find(".imc--sense-resultats")
						.remove();

				element
					.appFormsLlistaElements({ dades: llista_valor, desDe: "cerca" });

			};

		// inicia
		inicia();

	});
	return this;
}



// events i accion del popuop detall de la llista d'elements (taula)

$.fn.appFormsLlistaElementsForm = function(options) {
	var settings = $.extend({
		element_id: false
		,titol: false
		,desDe: false // afegix, modifica
		,fila_id: false
	}, options);
	this.each(function(){
		var element = $(this)
			,element_id = settings.element_id
			,titol = settings.titol
			,desDe = settings.desDe
			,envia_url = false
			,envia_ajax = false
			,json = false
			,inicia = function() {

				imc_forms_taula
					.attr("data-id", element_id)
					.attr("data-des-de", desDe);

				element
					.find(".imc--titol:first span")
						.text( titol );


				var sotatitol_txt = (desDe === "afegix") ? txtFormDinTaulaAfegixTitol
									: (desDe === "modifica") ? txtFormDinTaulaModificaTitol
									: txtFormDinTaulaConsultaTitol;

				element
					.find(".imc--titol:first strong")
						.text( sotatitol_txt );

				// ajuda al camp

				element
					.off('.appFormsAjudaCamp');

				if (imc_forms_ajuda.data("ajuda") === "on") {

					element
						.appFormsAjudaCamp({ referent: element.find(".imc--form:first") });

				}

				// events botonera

				element
					.off(".appFormsLlistaElementsForm")
					.on("click.appFormsLlistaElementsForm", "button[data-accio=llista-el-desa]", desa)
					.on("click.appFormsLlistaElementsForm", "button[data-accio=llista-el-cancela]", cancela)
					.on("click.appFormsLlistaElementsForm", "button[data-accio=llista-el-tanca]", cancela);

			}
			,cancela = function() {

				imc_forms_taula
					.attr("aria-hidden", "true");

				// enfoquem al botó

				var fieldset_pare = imc_forms_body.find("fieldset[data-id='" + element_id + "']:first")
					,boto_pare = (desDe === "afegix") ? fieldset_pare.find("button.imc-bt-afegix:first")
								: (desDe === "modifica") ? fieldset_pare.find("button.imc-bt-modifica:first")
								: fieldset_pare.find("button.imc-bt-consulta:first");

				boto_pare
					.focus();


			}
			,desa = function() {

				imc_forms_missatge
					.appFormsMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtFormDinTaulaDesant, alMostrar: function() { desaEnvia(); } });

			}
			,desaEnvia = function() {

				// serialitza

				var valorsSerialitzats = imc_forms_taula_form.appSerialitza({ verifica: true });

				if (!valorsSerialitzats) {

					var vesAlPrimerError = function() {

							imc_forms_taula
								.find(".imc-el-error:first")
									.appDestaca({ referent: imc_forms_taula.find(".imc--form:first") });

							imc_forms_taula
								.find(".imc-el-error:first input")
									.focus();

							return;

						};

					var error_missatge_text = (ERROR_TEXT) ? ERROR_TEXT : txtFormDinErrorText;

					imc_forms_missatge
						.appFormsMissatge({ accio: "error", titol: txtFormDinErrorTitol, text: error_missatge_text, alTancar: function() { vesAlPrimerError(); } });

					return;

				}

				valorsSerialitzats["idCampoListaElementos"] = element_id;

				// dades ajax

				var pag_url = APP_FORM_LE_DESA,
					pag_dades = valorsSerialitzats;

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

						envia_ajax = false;

						json = data;

						if (json.estado === "SUCCESS" || json.estado === "WARNING") {

							if (json.estado === "WARNING") {

								imc_forms_missatge
									.appFormsMissatge({ accio: "warning", titol: data.mensaje.titulo, text: data.mensaje.texto, alAcceptar: function() { mostra(); } });

								return;

							}

							desaAcaba(json);

						} else {

							envia_ajax = false;

							consola("Element llista, desa: error des de JSON");

							imc_forms_body
								.appFormsErrorsGeneral({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, debug: data.mensaje.debug, url: json.url });

						}

					})
					.fail(function(dades, tipus, errorThrown) {

						envia_ajax = false;

						if (tipus === "abort") {
							return false;
						}

						consola("Element llista, desa: error des de FAIL");

						imc_forms_body
							.appFormsErrorsGeneral({ estat: "fail" });

					});

			}
			,desaAcaba = function(json) {

				var validacio_estat = (json.datos.validacion && json.datos.validacion !== null) ? json.datos.validacion.estado : false
					,validacio_missatge = (json.datos.validacion && json.datos.validacion !== null) ? json.datos.validacion.mensaje : false
					,camp_id = (json.datos.validacion && json.datos.validacion !== null) ? json.datos.validacion.campo : false
					,fila_valors = json.datos.valor
					,fila_index = (json.datos.indice === null) ? false : parseInt(json.datos.indice, 10) // la fila on es troba (comença amb 0)
					,fila_ordre = (json.datos.orden === null) ? false : parseInt(json.datos.orden, 10); // la nova posició (comença amb 0)

				// hi ha error

				if (validacio_estat === "error") {

					if (camp_id && camp_id !== "" && camp_id !== null) {

						imc_forms_missatge
							.appFormsMissatge({ accio: "error", titol: validacio_missatge, text: txtFormDinErrorText, amagaDesdeFons: false, alAcceptar: function() { remarca(camp_id); } });

					} else {

						imc_forms_missatge
							.appFormsMissatge({ accio: "error", titol: validacio_missatge, text: "", amagaDesdeFons: false });

					}

					return;

				}

				// hi ha missatge

				if (validacio_missatge) {

					imc_forms_missatge
						.appFormsMissatge({ accio: validacio_estat, titol: validacio_missatge, text: "", amagaDesdeFons: false, alAcceptar: function() { finalitza(fila_valors, fila_index, fila_ordre); } })
						.appMissatgeFormAccions();

					return;

				}

				finalitza(fila_valors, fila_index, fila_ordre);

			},
			remarca = function(camp_id) {

				element
					.find("*[data-id='"+camp_id+"']")
						.addClass("imc-el-error")
						.appDestaca({ referent: element.find(".imc--contingut:first") }); // imc_forms_contenidor.find(".imc--contingut:first")

			},
			finalitza = function(fila_valors, fila_index, fila_ordre) {

				if (fila_index || fila_index === 0) {

					// és una modificació de fila pq hi ha índex

					var fila_taula_el = imc_forms_body.find("fieldset[data-id='" + element_id + "'] .imc-el-taula-elements tr[data-id='" + (fila_index+1) +"']");

					var fila_json = { "elemento": fila_valors };

					fila_taula_el
						.attr("data-dades", JSON.stringify(fila_json));

					$(fila_valors)
						.each(function() {

							var valor = this
								,valor_id = valor.id
								,valor_tipus = valor.tipo
								,valor_valor = valor.valor;

							if (valor_valor !== "" && valor_valor !== null && valor_tipus === "i") {
								valor_valor = valor.valor.descripcion || "";
							}

							if (valor_valor !== "" && valor_valor !== null && valor_tipus === "l") {

								var valors_llistat = valor_valor
									,valors_llistat_size = valors_llistat.length
									,valors_llistat_txt = "";

								$(valors_llistat)
									.each(function(j) {

										var val_ll = this
											,val_desc = val_ll.descripcion;

										valors_llistat_txt += val_desc;
										valors_llistat_txt += (j === valors_llistat_size-1) ? "" : ", ";

									});

								valor_valor = valors_llistat_txt;
								
							}

							fila_taula_el
								.find("td[data-id='"+valor_id+"']")
									.text( valor_valor );

						});
					

					// revisem si l'ordre (posició) és el mateix o un altre nou

					if (fila_index !== false && fila_ordre !== false && fila_index !== fila_ordre) {

						// si és diferent, canviem de posició

						var fila_clonada = fila_taula_el.clone(true);

						// esborrem fila

						fila_taula_el
							.remove();

						// agafem dades taula i nombre de files
						
						var taula_tbody_el = imc_forms_body.find("fieldset[data-id='" + element_id + "'] .imc-el-taula-elements tbody:first")
							,files_num = taula_tbody_el.find("tr").length;

						// coloquem la fila

						if (fila_ordre === 0 || files_num === 0) {

							taula_tbody_el
								.prepend( fila_clonada );

						} else if (fila_ordre > files_num-1) {

							taula_tbody_el
								.append( fila_clonada );

						} else {

							taula_tbody_el
								.find("tr:eq("+ (fila_ordre) +")")
									.before( fila_clonada );

						}

					} else {

						// clonem i reemplatzem, per a poder executar el Observer

						fila_taula_el
							.replaceWith( fila_taula_el.clone(true) );

					}


				} else {

					// fila_index = false

					// és una nova fila

					var fieldset_el = imc_forms_body.find("fieldset[data-id='" + element_id + "']:first")
						,thead_taula_el = fieldset_el.find(".imc-el-taula-elements thead:first");

					var taula_tbody_el = fieldset_el.find(".imc-el-taula-elements tbody:first")
						,files_num = taula_tbody_el.find("tr").length;

					var fina_nova = $("<tr>").attr("data-tipus", "nova");

					// si hi ha ordre, colocar on toca

					if (fila_ordre !== false) {

						// coloquem la fila

						if (fila_ordre === 0 || files_num === 0) {

							taula_tbody_el
								.prepend( fina_nova );

						} else if (fila_ordre > files_num-1) {

							taula_tbody_el
								.append( fina_nova );

						} else {

							taula_tbody_el
								.find("tr:eq("+ (fila_ordre) +")")
									.before( fina_nova );

						}

					} else {

						// sino hi ha ordre, fila al final

						taula_tbody_el
							.append( fina_nova );

					}


					// seleccionem la fina nova per treballar amb ella

					var fila_ultima_el = taula_tbody_el.find("tr[data-tipus=nova]:first");

					fila_ultima_el
						.attr("data-id", files_num+1);

					var fila_json = { "elemento": fila_valors };

					fila_ultima_el
						.attr("data-dades", JSON.stringify(fila_json));

					$("<td>")
						.append( $("<input>").attr({ type: "radio", name: element_id, id: element_id + "_" + (files_num+1) }) )
						.appendTo( fila_ultima_el );

					$(fila_valors)
						.each(function() {

							var valor = this
								,valor_id = valor.id
								,valor_tipus = valor.tipo
								,valor_valor = valor.valor;

							if (valor_valor !== "" && valor_valor !== null && valor_tipus === "i") {
								valor_valor = valor.valor.descripcion || "";
							}

							if (valor_valor !== "" && valor_valor !== null && valor_tipus === "l") {

								var valors_llistat = valor_valor
									,valors_llistat_size = valors_llistat.length
									,valors_llistat_txt = "";

								$(valors_llistat)
									.each(function(j) {

										var val_ll = this
											,val_desc = val_ll.descripcion;

										valors_llistat_txt += val_desc;
										valors_llistat_txt += (j === valors_llistat_size-1) ? "" : ", ";

									});

								valor_valor = valors_llistat_txt;
								
							}

							// si la dada està també en la capçalera, afegim

							if ( thead_taula_el.find("th[data-id="+valor_id+"]").length ) {

								$("<td>")
									.attr({ "data-id": valor_id, "data-tipus": valor_tipus })
									.append( valor_valor )
									.appendTo( fila_ultima_el );

							}

						});

					$("<td>")
						.appendTo( fila_ultima_el );

					// eliminem tipus de "nova"

					taula_tbody_el
						.find("tr[data-tipus=nova]:first")
							.removeAttr("data-tipus");

					// revisem número de files

					fieldset_el
						.appFormsLlistaElementsFilesRevisa();

				}

				// reordenem

				reordena();

				// amaguem missatges;

				imc_forms_taula
					.attr("aria-hidden", "true");

				imc_forms_missatge
					.appFormsMissatge({ araAmaga: true });

				// eliminem error (si hi haguera)

				var fieldset_pare = imc_forms_body.find("fieldset[data-id='" + element_id + "']:first")
					,boto_pare = (desDe === "afegix") ? fieldset_pare.find("button.imc-bt-afegix:first") : fieldset_pare.find("button.imc-bt-modifica:first");

				fieldset_pare
					.removeClass("imc-el-error");

				// enfoquem al botó

				boto_pare
					.focus();

			},
			reordena = function() {

				var taula_tbody_el = imc_forms_body.find("fieldset[data-id='" + element_id + "'] .imc-el-taula-elements tbody:first");

				taula_tbody_el
					.find("tr")
						.each(function(i) {

							var tr_el = $(this);

							tr_el
								.attr("data-id", i+1);

						});

			};

		// inicia
		inicia();

	});
	return this;
}



// revisem el número de files per si hem arribat al nombre màxim, aleshores desactivem el botó d'afegir

$.fn.appFormsLlistaElementsFilesRevisa = function(options) {

	var settings = $.extend({
			filesMax: false
		}, options);

	this.each(function(){
		var element = $(this)
			,filesMax = settings.filesMax
			,element_id = element.attr("data-id")
			,inicia = function() {

				// botó afegir

				element
					.find(".imc-el-taula-botonera button.imc-bt-afegix")
						.removeAttr("disabled");

				filesMax = parseInt( element.attr("data-max"), 10);

				if (filesMax && !isNaN(filesMax) && filesMax > 0) {

					var filesActuals = element.find(".imc-el-taula-elements:first tbody:first tr").length;

					if (filesActuals >= filesMax) {

						element
							.find("button.imc-bt-afegix:first")
								.attr("disabled", "disabled");

					}

				}

				// botons modificar i esborrar, i botons puja i baixa

				element
					.find(".imc-el-taula-botonera button:not(.imc-bt-afegix)")
						.attr("disabled", "disabled");

				if (element.find("input[type=radio]:checked").val()) {

					element
						.find(".imc-el-taula-botonera button:not(.imc-bt-afegix)")
							.removeAttr("disabled");

				}

				// revisem tabulació

				$("#imc-forms-contenidor")
					.appFormsPopupTabula({ enfocaEn: false });

			};

		// inicia
		inicia();

	});
	return this;
}


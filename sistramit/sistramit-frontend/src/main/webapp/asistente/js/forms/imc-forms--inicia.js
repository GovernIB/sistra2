// index

var imc_forms_contenidor = imc_formulari.find(".imc--contingut:first")
	,imc_forms_ajuda = $("#imc-forms-ajuda");

var FORMS_JS = false;



// appFormsAjuda

$.fn.appFormsAjuda = function(opcions) {
	var settings = $.extend({
		element: ""
	}, opcions);
	this.each(function(){
		var element = $(this),
			ajuda_inicial = imc_forms_ajuda.hasClass("imc--desactivada") ? "off" : "on",
			bt_ajuda = element.find("button:first"),
			txtAjudaInfo = false,
			txtAjudaBoto = false,
			ajuda_data = false,
			inicia = function() {

				// verifica
				if (!imc_forms_ajuda.hasClass("imc--desactivada")) {

					imc_forms_contenidor
						.appFormsAjudaCamp();

					imc_forms_ajuda
						.data("ajuda", "on");

				}

				// inicia
				imc_forms_ajuda
					.data("ajuda", ajuda_inicial);

				bt_ajuda
					.off('.appFormsAjuda')
					.on('click.appFormsAjuda', activa);

			},
			activa = function() {

				if (imc_forms_ajuda.data("ajuda") === "off") {

					imc_forms_ajuda
						.removeClass("imc--desactivada");

					imc_forms_contenidor
						.appFormsAjudaCamp({ referent: imc_forms_contenidor });

					txtAjudaInfo = txtAjuda + " " + txtActivada;
					txtAjudaBoto = txtDesactiva;
					ajuda_data = "on";

				} else {

					imc_forms_ajuda
						.addClass("imc--desactivada");

					imc_forms_contenidor
						.off('.appFormsAjudaCamp');

					txtAjudaInfo = txtAjuda + " " + txtDesctivada;
					txtAjudaBoto = txtActiva;
					ajuda_data = "off";

				}


				imc_forms_ajuda
					.fadeOut(200, function() {

						canvia();

						imc_forms_ajuda
							.fadeIn(200);

					});

				$.ajax({
					  type: "POST",
					  url: "activarAyuda.do",
					  data: { activar: ("on" == ajuda_data)}
					});

			},
			canvia = function() {

				imc_forms_ajuda
					.find("strong")
						.text(txtAjudaInfo)
						.end()
					.find("button")
						.text(txtAjudaBoto)
						.end()
					.data("ajuda", ajuda_data);

			};

		// inicia
		inicia();

	});
	return this;
}
// /ajuda


// appFormsAjudaCamp

$.fn.appFormsAjudaCamp = function(options) {
	var settings = $.extend({
		referent: $(window)
	}, options);
	this.each(function(){
		var element = $(this),
			referent = settings.referent,
			ajuda_elm = false,
			ajuda_anterior_elm = false,
			onMouseEnter = function() {
				var elm = $(this);
				ajuda_elm = elm.find(".imc-el-ajuda:first");
				if (ajuda_elm.length && ajuda_elm.html() !== "") {

					onMouseLeave();

					ajuda_anterior_elm = ajuda_elm;

					var window_W = element.width(),
						window_H = element.height(),
						window_scroll_T = element.scrollTop(),
						elm_T = elm.position().top,
						elm_L = elm.position().left,
						elm_H = elm.outerHeight(),
						elm_label = elm.find(".imc-el-etiqueta:first, legend:first"),
						elm_label_W = elm_label.outerWidth(),
						elm_label_H = elm_label.outerHeight(),
						elm_control = elm.find(".imc-el-control:first, ul:first"),
						elm_control_W = elm_control.outerWidth(),
						elm_control_H = elm_control.outerHeight(),
						ajuda_W = ajuda_elm.outerWidth(),
						ajuda_H = ajuda_elm.outerHeight();

					var ajuda_T = elm_T-ajuda_H-5+window_scroll_T,
						ajuda_L = elm_L;

					if ((elm_L+ajuda_W) > window_W) {

						ajuda_L = elm_L - ajuda_W + elm_control_W;

						ajuda_elm
							.addClass("imc--dreta");

					} else {

						ajuda_elm
							.removeClass("imc--dreta");

					}

					var ajuda_T_inici = ajuda_T+5;

					if (window_scroll_T > ajuda_T) {

						ajuda_T = elm_T + elm_H;
						ajuda_T_inici = ajuda_T-5;

						ajuda_elm
							.addClass("imc--dalt");

					} else {

						ajuda_elm
							.removeClass("imc--dalt");

					}

					ajuda_elm
						.css({ top: ajuda_T_inici+"px", left: ajuda_L+"px", opacity: 0 })
						.addClass("imc-el-ajuda-on")
						.animate({ top: ajuda_T+"px", opacity:1 }, 200)
						.off(".appFormsAjudaCamp")
						.on("mouseenter.appFormsAjudaCamp", onMouseLeave);

				}
			},
			onMouseLeave = function() {

				if (ajuda_elm) {

					ajuda_elm
						.stop();

					if (ajuda_anterior_elm) {

						ajuda_anterior_elm
							.removeClass("imc-el-ajuda-on")
							.removeAttr("style");

					}
				}

			};

		// inicia
		element
			.off('.appFormsAjudaCamp')
			.on('mouseenter.appFormsAjudaCamp', ".imc-element", onMouseEnter)
			.on('focus.appFormsAjudaCamp', ".imc-element", onMouseEnter)
			.on('mouseleave.appFormsAjudaCamp', ".imc-element", onMouseLeave)
			.on('blur.appFormsAjudaCamp', ".imc-element", onMouseLeave);
	});
	return this;
}
// /appFormsAjudaCamp


// appFormsErrors
$.fn.appFormsErrors = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			inicia = function() {

				element
					.off('.appFormsErrors')
					.on('click.appFormsErrors', ".imc-forms-errors a", marca);

			},
			marca = function(e) {

				var bt = $(this),
					camp_id = bt.attr("data-id"),
					camp_el = $("#"+camp_id);

				camp_el
					.appDestaca({ referent: imc_forms_contenidor });

				setTimeout(
					function() {

						camp_el
							.focus();

					}, 1500
				);

			};

		// inicia
		inicia();

	});
	return this;
}
// /appFormsErrors


// appDestaca
$.fn.appDestaca = function(options) {
	var settings = $.extend({
		referent: $(window),
		com: "enmarca" // "resalta", "enmarca"
	}, options);
	this.each(function(){
		var element = $(this),
			referent = settings.referent,
			com = settings.com,
			imc_finestra_H = false,
			imc_finestra_scroll_T = false,
			imc_document_H = false,
			imc_document_W = false,
			el = element.closest(".imc-element"),
			el_T = false,
			el_L = false,
			el_W = false,
			el_H = false,
			destacat_el = false,
			inicia = function() {

				imc_finestra_H = referent.height();
				imc_finestra_scroll_T = referent.scrollTop();
				imc_document_H = referent[0].scrollHeight;
				imc_document_W = referent.outerWidth(true);

				el_T = el.position().top + imc_finestra_scroll_T;
				el_L = el.position().left;
				el_W = el.outerWidth(true);
				el_H = el.outerHeight(true);

				if (com === "resalta") {

					resalta();

				} else {

					enmarca();

				}

			},
			resalta = function() {

				if ($("#imc-destacat").length) {

					$("#imc-destacat")
						.remove();

				}

				//consola("offset()TOP: " + el.offset().top + " - offset()LEFT: " + el.offset().left);
				//consola("position()TOP: " + el.position().top + " - position()LEFT: " + el.position().left);

				var destacat_el_T = $("<div>").addClass("imc--dalt").css({ height: el_T+"px" }),
					destacat_el_B = $("<div>").addClass("imc--baix").css({ top: (el_T+el_H)+"px", height: (imc_document_H - el_T - el_H)+"px" }),
					destacat_el_L = $("<div>").addClass("imc--esquerre").css({ top: el_T+"px", width: el_L+"px", height: el_H+"px" }),
					destacat_el_R = $("<div>").addClass("imc--dreta").css({ top: el_T+"px", left: (el_L+el_W)+"px", width: (imc_document_W - (el_L + el_W))+"px", height: el_H+"px" }); //

				destacat_el = $("<div>").addClass("imc-destacat").attr("id", "imc-destacat").append( destacat_el_T ).append( destacat_el_B ).append( destacat_el_L ).append( destacat_el_R ).appendTo( referent );

				// escrolletja

				escrolletja();

				// parpadeja

				parpadeja();

			},
			escrolletja = function() {

				var anarScroll = false,
					pos_respecte_finestra = (imc_finestra_H / 2) + imc_finestra_scroll_T;

				if (el_T > pos_respecte_finestra) {

					anarScroll = el_T - (imc_finestra_H / 2);

				} else if (el_T < imc_finestra_scroll_T) {

					anarScroll = el_T - 20;

				}

				if (anarScroll) {

					referent
						.animate(
							{ scrollTop: anarScroll+"px" }
							,500
						);

				}

			},
			parpadeja = function() {

				destacat_el
					.addClass("imc--parpadeja");

				var escoltaEsborra = function(event){
					if (event.animationName == "parpadeja") {
						elimina();
					}
				}

				document
					.addEventListener("animationend", escoltaEsborra, false);

				document
					.addEventListener("MSAnimationEnd", escoltaEsborra, false);

				document
					.addEventListener("webkitAnimationEnd", escoltaEsborra, false);

			},
			elimina = function() {

				$("#imc-destacat")
					.remove();

			},
			enmarca = function() {

				if ($("#imc-destacat--enmarca").length) {

					$("#imc-destacat--enmarca")
						.remove();

				}

				var enmarcat_el = $("<div>")
					.addClass("imc-destaca--enmarca")
					.attr("id", "imc-destacat--enmarca")
					.css({ height: imc_finestra_H+"px" })
					.appendTo( referent );

				// escrolletja

				escrolletja();

				// enmarca

				enmarcat_el
					.animate(
						{ top: el_T+"px", left: el_L+"px", height: el_H+"px", width: el_W+"px", opacity: .3 }
						,700
					).animate(
						{ opacity: 0 }
						,200
						,function() {
							$(this).remove();
						}
					);

			};

		// inicia
		inicia();

	});
	return this;
}
// /appDestaca


// appFormsConfiguracio

$.fn.appFormsConfiguracio = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			json_config = JSON_FORMULARI.datos.configuracion,
			json_valors = JSON_FORMULARI.datos.valores,
			json_valors_possibles = JSON_FORMULARI.datos.valoresPosibles,
			json_accions = JSON_FORMULARI.datos.acciones,
			json_recursos = JSON_FORMULARI.datos.recursos,
			inicia = function() {

				imc_formulari_finestra
					.off(".appFormsAvalua");

				/*
				imc_formulari_finestra
					.find(".imc-el-error")
						.removeClass("imc-el-error");
				*/

				// configuració (sense lectura i modificable)

				if (json_config && json_config.length) {

					$(json_config)
						.each(function() {

							var conf = this
								,conf_id = conf.id
								,conf_tipus = conf.tipo || false
								,conf_obligatori = conf.obligatorio || false
								,conf_ajuda = conf.ayuda || false
								,conf_avalua = conf.evaluar || false
								,conf_contingut = conf.contenido || false
								,conf_opcions = conf.opciones || false
								,conf_valors = conf.valores || false;

							var elm = imc_forms_contenidor.find("*[data-id="+conf_id+"]")
								,elm_input = elm.find("input:first");

							elm
								.removeClass("imc-el-error");

							// no hi ha elements amb eixe ID

							if (!elm.length){
								return;
							}

							// sí que hi ha ID

							if (conf_tipus) {

								elm
									.attr({ "data-tipus": conf_tipus });

							}

							if (conf_contingut) {

								elm
									.attr({ "data-contingut": conf_contingut });

							}

							if (conf_obligatori) {

								elm
									.attr({ "data-obligatori": conf_obligatori });

								if (conf_obligatori === "s") {

									elm_input
										.attr("required", "required");

								} else if (conf_obligatori === "n") {

									elm_input
										.removeAttr("required");

								}

							}

							if (conf_ajuda && conf_ajuda !== null && conf_ajuda !== "") {

								$("<div>")
									.addClass("imc-el-ajuda")
									.text( conf_ajuda )
									.appendTo( elm );

							}

							if (conf_avalua && conf_avalua === "s") {

								elm
									.attr("data-avalua", "s");

								elm_input
									.attr("data-avalua", "s");

							}

							if (conf_contingut) {

								var contingut = (conf_contingut === "an") ? "alfanumeric"
											: (conf_contingut === "cp") ? "codipostal"
											: (conf_contingut === "em") ? "correuelectronic"
											: (conf_contingut === "ex") ? "expresioregular"
											: (conf_contingut === "fe") ? "data"
											: (conf_contingut === "ho") ? "hora"
											: (conf_contingut === "id") ? "identificador"
											: (conf_contingut === "nu") ? "numero"
											: (conf_contingut === "pw") ? "contrasenya"
											: (conf_contingut === "te") ? "telefon"
											: (conf_contingut === "d") ? "desplegable"
											: (conf_contingut === "m") ? "multiple"
											: (conf_contingut === "u") ? "unic"
											: "";

								var type = (conf_contingut === "an") ? "text"
											: (conf_contingut === "cp") ? "text"
											: (conf_contingut === "em") ? "email"
											: (conf_contingut === "ex") ? "text"
											: (conf_contingut === "fe") ? "date"
											: (conf_contingut === "ho") ? "time"
											: (conf_contingut === "id") ? "text"
											: (conf_contingut === "nu") ? "text"
											: (conf_contingut === "pw") ? "password"
											: (conf_contingut === "te") ? "number"
											: (conf_contingut === "d") ? "hidden"
											: "";

								elm_input
									.attr({ "type": type, "data-contingut": contingut });

								if (conf_contingut === "cp") {

									elm_input
										.attr({ "pattern": "((0[1-9]|5[0-2])|[1-4][0-9])[0-9]{3}", "maxlength": 5 });

								}

							}

							if (conf_opcions) {

								// alfanumèric

								if (conf_opcions.tamanyo && conf_opcions.tamanyo !== null) {

									elm_input
										.attr({ "maxlength": conf_opcions.tamanyo, "data-amplaria": conf_opcions.tamanyo });

								}

								// expresió regular

								if (conf_opcions.regexp && conf_opcions.regexp !== null) {

									elm_input
										.attr({ "pattern": conf_opcions.regexp, "data-regexp": conf_opcions.regexp });

								}

								// identificador

								if (conf_opcions.nif && conf_opcions.nif === "s") {

									elm_input
										.attr("data-nif", conf_opcions.nif);

								}

								if (conf_opcions.cif && conf_opcions.cif === "s") {

									elm_input
										.attr("data-cif", conf_opcions.cif);

								}

								if (conf_opcions.nie && conf_opcions.nie === "s") {

									elm_input
										.attr("data-nie", conf_opcions.nie);

								}

								if (conf_opcions.nss && conf_opcions.nss === "s") {

									elm_input
										.attr("data-nss", conf_opcions.nss);

								}

								// numero

								if (conf_opcions.enteros && conf_opcions.enteros !== null) {

									elm_input
										.attr("data-enters", conf_opcions.enteros);

								}

								if (conf_opcions.decimales && conf_opcions.decimales !== null) {

									elm_input
										.attr("data-decimals", conf_opcions.decimales);

								}

								if (conf_opcions.separador && conf_opcions.separador !== null) {

									elm_input
										.attr("data-separador", conf_opcions.separador);

								}

								if (conf_opcions.negativo && conf_opcions.negativo !== null) {

									elm_input
										.attr("data-negatiu", conf_opcions.negativo);

								}

								if (conf_opcions.rangoMin && conf_opcions.rangoMin !== null) {

									elm_input
										.attr({ "min": conf_opcions.rangoMin, "data-rangMin": conf_opcions.rangoMin });

								}

								if (conf_opcions.rangoMax && conf_opcions.rangoMax !== null) {

									elm_input
										.attr({ "max": conf_opcions.rangoMax, "data-rangMax": conf_opcions.rangoMax });

								}

								// telèfon

								if (conf_opcions.fijo) {

									elm_input
										.attr("data-fixe", conf_opcions.fijo);

								}

								if (conf_opcions.movil) {

									elm_input
										.attr("data-mobil", conf_opcions.movil);

								}

								// checkbutton

								if (conf_opcions.checked && conf_opcions.checked !== null) {

									elm_input
										.attr("data-checked", conf_opcions.checked);

								}

								if (conf_opcions.noChecked && conf_opcions.noChecked !== null) {

									elm_input
										.attr("data-noChecked", conf_opcions.noChecked);

								}

								// selector amb index s/n

								if (conf_opcions.indice && conf_opcions.indice !== null) {

									elm_input
										.attr("data-index", conf_opcions.indice);

									if (conf_opcions.indice === "s") {

										elm
											.addClass("imc-el-index");

									}

								}

							}

							// verificacion

							if (conf_tipus === "verificacion") {

								elm_input
									.attr({ "data-marcat": conf_valors.checked, "data-desmarcat": conf_valors.noChecked });

							}

						});

				}

				// valors posibles

				if (json_valors_possibles && json_valors_possibles.length) {

					$(json_valors_possibles)
						.each(function(i) {

							var va = this
								,va_id = va.id
								,va_valors = va.valores;

							var elm = imc_forms_contenidor.find("*[data-id="+va_id+"]")
								,elm_input = elm.find("input:first")
								,elm_input_tipus = elm.attr("data-tipus")
								,elm_input_contingut = elm.attr("data-contingut");

							if (elm_input_tipus === "selector") {

								// desplegable

								if (elm_input_contingut === "d") {

									var pare_el = elm.find(".imc-opcions")
										,ul_el = $("<ul>");

									pare_el
										.find(".imc-select-submenu")
											.remove()
											.end()
										.find("a span")
											.html("")
											.end()
										.find("input")
											.val("");

									$("<div>")
										.addClass("imc-select-submenu")
										.append( ul_el )
										.appendTo( pare_el );

									var llistat_el = pare_el.find("ul:first");

									$(va_valors)
										.each(function() {

											var opcio = this
												,opcio_valor = opcio.valor
												,opcio_text = opcio.descripcion;

											if (opcio_text === "") {
												opcio_text = "&nbsp;";
											}

											var opcio_a = $("<a>").attr("data-value", opcio_valor).html( opcio_text );

											$("<li>")
												.append( opcio_a )
												.appendTo( llistat_el );

										});

								}

								// multiple

								if (elm_input_contingut === "m" || elm_input_contingut === "u") {

									var llistat_el = elm.find("ul:first")
										,tipus_class = (elm_input_contingut === "m") ? "imc-input-check" : "imc-input-radio"
										,tipus_type = (elm_input_contingut === "m") ? "checkbox" : "radio";

									llistat_el
										.html("");

									$(va_valors)
										.each(function(j) {

											var opcio = this
												,opcio_valor = opcio.valor
												,opcio_text = opcio.descripcion;

											var opcio_input = $("<input>").attr({ id: va_id+"__"+i+"_"+j, name: va_id, type: tipus_type, value: opcio_valor })
												,opcio_text = $("<label>").attr("for", va_id+"__"+i+"_"+j).text( opcio_text )
												,opcio_div = $("<div>").addClass( tipus_class ).append( opcio_input ).append( opcio_text );

											$("<li>")
												.append( opcio_div )
												.appendTo( llistat_el );

										});

								}

							}

						});

				}

				// valors

				if (json_valors && json_valors.length) {

					$(json_valors)
						.each(function() {

							var v = this
								,val_id = v.id
								,val_tipus = v.tipo || false
								,val_valor = v.valor || false;

							var elm = imc_forms_contenidor.find("*[data-id="+val_id+"]")
								,elm_input = elm.find("input:first, textarea:first")
								,elm_input_tipus = elm.attr("data-tipus")
								,elm_input_contingut = elm.attr("data-contingut")
								,elm_esLectura = (elm.attr("data-lectura") === "s") ? true : false;

							elm
								.attr( "data-valortipus", val_tipus );

							elm_input
								.attr( "data-tipus", val_tipus );

							if (val_valor && val_valor !== "" && val_valor !== null) {

								if (elm_input_tipus === "texto") {

									elm_input
										.val( val_valor );

								} else if (elm_input_tipus === "selector" && elm_input_contingut === "d") {

									var opcio_valor = val_valor.valor || false;

									if (opcio_valor && opcio_valor !== "") {

										setTimeout(
											function() {
												/*
												elm
													.find("a[data-value='"+opcio_valor+"']:first")
														.trigger("click");
												*/

												var elm_txt = elm.find("a[data-value='"+opcio_valor+"']:first").text();

												elm_input
													.val( opcio_valor );

												elm
													.find("a.imc-select:first")
														.html( $("<span>").text(elm_txt) );

											},50
										);

									}

								} else if (elm_input_tipus === "selector" && elm_input_contingut === "m") {

									$(val_valor)
										.each(function() {

											var selec = this
												,selec_val = selec.valor;

											setTimeout(
												function() {
													/*
													elm
														.find("input[value='"+selec_val+"']:first")
															.parent()
																.find("label")
																	.trigger("click");
													*/

													elm
														.find("input[value='"+selec_val+"']:first")
															.prop("checked", true);


													if (elm_esLectura) {

														elm
															.find("input")
																.attr("disabled", "disabled");

													}

												},50
											);

										});

								} else if (elm_input_tipus === "selector" && elm_input_contingut === "u") {

									var opcio_valor = val_valor.valor || false;

									if (opcio_valor) {

										setTimeout(
											function() {
												/*
												elm
													.find("input[value='"+opcio_valor+"']:first")
														.parent()
															.find("label")
																.trigger("click");
												*/

												elm
													.find("input[value='"+opcio_valor+"']:first")
														.prop("checked", true);

											},50
										);

									}

								} else if (elm_input_tipus === "verificacion") {

									var estaMarcat = elm.find("input[data-marcat='"+val_valor+"']:first").length;

									if (estaMarcat) {

										elm
											.find("label")
												.trigger("click");

									}

								}

							}

						});

				}

				// accions

				if (json_accions && json_accions.length) {

					imc_formulari_finestra
						.find(".imc-forms-navegacio")
							.remove();

					var acc_html = "<nav id=\"imc-forms-navegacio\" class=\"imc-forms-navegacio imc--forms-nav\"><ul></ul></nav>";

					imc_formulari_finestra
						.find(".imc--contingut:first")
							.append( acc_html );

					var acc_llistat = imc_formulari_finestra.find(".imc-forms-navegacio:first ul:first");

					$(json_accions)
						.each(function() {

							var acc = this
								,acc_tipus = acc.tipo || false
								,acc_valor = acc.valor || false
								,acc_validar = acc.validar || false
								,acc_text = acc.titulo || false
								,acc_estil = acc.estilo || false;

							var bt_class = (acc_tipus === "anterior") ? "imc--anterior"
											: (acc_tipus === "siguiente") ? "imc--seguent"
											: (acc_tipus === "finalizar") ? "imc--finalitza"
											: (acc_tipus === "salir") ? "imc--eixir"
											: acc_estil;

							var acc_validar = (acc_tipus === "anterior") ? "n"
											: (acc_tipus === "siguiente") ? "s"
											: (acc_tipus === "finalizar") ? "s"
											: (acc_tipus === "salir") ? "n"
											: acc_validar;

							var bt_text = (acc_tipus === "anterior") ? txtAnterior
											: (acc_tipus === "siguiente") ? txtSeguent
											: (acc_tipus === "finalizar") ? txtFinalitza
											: (acc_tipus === "salir") ? txtEixir
											: acc_text;

							var bt = $("<button>").attr("type", "button").html( $("<span>").text( bt_text ) );

							$("<li>")
								.addClass( bt_class )
								.append( bt )
								.appendTo( acc_llistat );

						});

					/*
					<button type="button" id="imc-bt-iframe-tanca" class="imc-bt imc--ico imc--form-tanca" data-accio="tanca"><span>Tanca formulari</span></button>
					*/

					var bt_finalitza_span = $("<span>").text( txtTancaFormulari )
						,bt_finalitza_button = $("<button>").addClass("imc--form-tanca").attr({ type: "button", id: "imc-bt-iframe-tanca", "data-accio": "tanca" }).html( bt_finalitza_span )
						,bt_li = $("<li>").addClass("imc--tanca").html( bt_finalitza_button );

					acc_llistat
						.prepend( bt_li );

				}

				// configuració (només lectura i modificable)

				if (json_config && json_config.length) {

					$(json_config)
						.each(function() {

							var conf = this
								,conf_id = conf.id
								,conf_obligatori = conf.obligatorio || false
								,conf_lectura = conf.soloLectura || false
								,conf_modificable = conf.modificable || false;

							var elm = imc_forms_contenidor.find("*[data-id="+conf_id+"]")
								,elm_input = elm.find("input:first");

							if (conf_lectura) {

								var conf_tipus = elm.attr("data-tipus")
									,conf_contingut = elm.attr("data-contingut");

								elm
									.attr({ "data-lectura": conf_lectura });

								if (conf_lectura === "s") {

									if (conf_tipus === "texto") {

										elm_input
											.attr("readonly", "readonly");

									} else if (conf_tipus === "selector" && conf_contingut === "d") {

										elm
											.find("a.imc-select")
												.addClass("imc-select-lectura");

									} else if (conf_tipus === "selector" && (conf_contingut === "m" || conf_contingut === "u")) {

										elm
											.find("input")
												.attr("disabled", "disabled");

									} else if (conf_tipus === "check") {

										elm_input
											.attr("disabled", "disabled");

									}

								} else if (conf_lectura === "n") {

									elm_input
										.removeAttr("readonly");

									if (conf_tipus === "texto") {

										elm_input
											.removeAttr("readonly");

									} else if (conf_tipus === "selector" && conf_contingut === "d") {

										elm
											.find("a.imc-select")
												.removeClass("imc-select-lectura");

									} else if (conf_tipus === "selector" && (conf_contingut === "m" || conf_contingut === "u")) {

										elm
											.find("input")
												.removeAttr("disabled");

									} else if (conf_tipus === "check") {

										elm_input
											.removeAttr("disabled");

									}

								}

							}

						});

				}

				// events

				setTimeout(
					function() {

						imc_formulari_finestra
							.appFormsAccions()
							.appFormsValida()
							.appFormsAvalua();

						// ve de avaluar i vol repintar?

						if (element.attr("data-repinta") === "s") {

							element
								.removeAttr("data-repinta");

							return;

						}

						// primera vegada

						var primer_element = imc_formulari_finestra.find("div.imc-element[data-id]:first")
							,primer_element_camp = primer_element.find("input:first, textarea:first, a.imc-select:first");

						imc_formulari_finestra
							.find(".imc--contingut:first")
								.animate(
									{
										scrollTop: "0px"
									}
									,0
									, function() {
										
										primer_element_camp
											.focus();

									}
								);

					},300
				);

			};

		// inicia
		inicia();

	});
	return this;
}


// appFormsValida
$.fn.appFormsValida = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			valor_inicial = false,
			inicia = function() {

				element
					.off(".appFormsValida")
					.on("focus.appFormsValida", "input", valora)
					.on("blur.appFormsValida", "input", valida)
					.on("blur.appFormsValida", "div[data-contingut='nu'] input", formateja);

			},
			formateja = function(e) {

				var input = $(this)
					,input_val = input.val()
					,input_pare = input.closest(".imc-element");

				if (input_val !== "") {

					var elm_negatiu = input.attr("data-negatiu") || false
						,elm_enters = parseInt( input.attr("data-enters"), 10) || false
						,elm_decimals = parseInt( input.attr("data-decimals"), 10) || false
						,elm_separador = input.attr("data-separador") || false;

					if (elm_separador) {

						var format_val = '0,0';

						if (elm_decimals) {

							var str_decimales = "";

							for(i=0; i<elm_decimals; i++) {
								str_decimales += "0";
							}

							var format_val = '0,0.' + str_decimales;

						}

						if (elm_separador === "cp") {

							numeral
								.locale('en');

    					} else {

							numeral
								.locale('es-es');

    					}

						var input_val_format = numeral( input_val ).format( format_val );

						input
							.val( input_val_format );

					}

				}

			},
			valora = function(e) {

				var input = $(this)
					,input_val = input.val();

				valor_inicial = input_val;

			},
			valida = function(e) {

				var input = $(this)
					,input_val = input.val()
					,input_pare = input.closest(".imc-element")
					,esError = false;

				// si no canvia el valor, no fem res

				if (input_val === valor_inicial) {
					return;
				}

				// obligatori

				esError = (input.is(":required") && input_val === "") ? true : false;

				// codi postal

				if (input.attr("data-contingut") === "codipostal" && input_val !== "") {

					esError = ( !input.appValida({ format: "codipostal", valor: input_val }) ) ? true : false;

				}

				// correu electrònic

				if (input.attr("data-contingut") === "correuelectronic" && input_val !== "") {

					esError = ( !input.appValida({ format: "correuelectronic", valor: input_val }) ) ? true : false;

				}

				// expresió regular

				if (input.attr("data-contingut") === "expresioregular" && input_val !== "") {

					esError = ( !input.appValida({ format: "expresioregular", valor: input_val }) ) ? true : false;

				}

				// identificador

				if (input.attr("data-contingut") === "identificador" && input_val !== "") {

					var idValid = false;

					if (input.attr("data-nif") === "s") {

						idValid = ( appValidaIdentificador.nif(input_val) ) ? true : false;

					}

					if (!idValid && input.attr("data-cif") === "s") {

						idValid = ( appValidaIdentificador.cif(input_val) ) ? true : false;

					}

					if (!idValid && input.attr("data-nie") === "s") {

						idValid = ( appValidaIdentificador.nie(input_val) ) ? true : false;

					}

					if (!idValid && input.attr("data-nss") === "s") {

						idValid = ( appValidaIdentificador.nss(input_val) ) ? true : false;

					}

					esError = !idValid;

				}

				// numero

				if (input.attr("data-contingut") === "numero" && input_val !== "") {

					esError = ( !input.appValida({ format: "numero", valor: input_val }) ) ? true : false;

				}

				// telèfon

				if (input.attr("data-contingut") === "telefon" && input_val !== "") {

					esError = ( !input.appValida({ format: "telefon", valor: input_val }) ) ? true : false;

				}

				// data

				if (input.attr("data-contingut") === "data" && input_val !== "") {

					esError = ( !input.appValida({ format: "data", valor: input_val }) ) ? true : false;

				}

				// marquem l'error

				if (esError) {

					input_pare
						.addClass("imc-el-error");

				} else {

					input_pare
						.removeClass("imc-el-error");

				}

			};

		// inicia
		inicia();

	});
	return this;
}
// appFormsValida


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
			inicia = function() {

				element
					.off(".appFormsAvalua")
					.on("focus.appFormsAvalua", "div[data-tipus='texto'] input, div[data-tipus='texto'] textarea", preevalua)
					.on("blur.appFormsAvalua", "div[data-tipus='texto'] input, div[data-tipus='texto'] textarea", selecciona)
					.on("click.appFormsAvalua", "div[data-tipus='selector'][data-contingut='d'] .imc-select-submenu a", selecciona)
					.on("click.appFormsAvalua", "fieldset[data-tipus='selector'] label", selecciona)
					.on("click.appFormsAvalua", "div[data-type='check'] .imc-input-check", selecciona);

			},
			preevalua = function(e) {

				input = $(this);
				input_element = input.closest(".imc-element");

				var esAvaluable = (input_element.attr("data-avalua") === "s") ? true : false;

				if (esAvaluable) {

					imc_forms_contenidor
						.attr("data-preevalua", "s");

					// posar capa invisible

					var imc_finestra_H = imc_forms_contenidor.height()
						,imc_finestra_scroll_T = imc_forms_contenidor.scrollTop()
						,imc_document_H = imc_forms_contenidor[0].scrollHeight
						,imc_document_W = imc_forms_contenidor.outerWidth(true);

					var el_T = input_element.position().top + imc_finestra_scroll_T
						,el_L = input_element.position().left
						,el_W = input_element.outerWidth(true)
						,el_H = input_element.outerHeight(true);

					var preevaluaAmaga = function() {

							$("#imc-preevalua")
								.remove();

						};

					if ($("#imc-preevalua").length) {

						preevaluaAmaga();

					}

					var preevalua_el_T = $("<div>").addClass("imc--dalt").css({ height: el_T+"px" })
						,preevalua_el_B = $("<div>").addClass("imc--baix").css({ top: (el_T+el_H)+"px", height: (imc_document_H - el_T - el_H)+"px" })
						,preevalua_el_L = $("<div>").addClass("imc--esquerre").css({ top: el_T+"px", width: el_L+"px", height: el_H+"px" })
						,preevalua_el_R = $("<div>").addClass("imc--dreta").css({ top: el_T+"px", left: (el_L+el_W)+"px", width: (imc_document_W - (el_L + el_W))+"px", height: el_H+"px" });

					$("<div>")
						.addClass("imc-preevalua")
						.attr("id", "imc-preevalua")
						.append( preevalua_el_T )
						.append( preevalua_el_B )
						.append( preevalua_el_L )
						.append( preevalua_el_R )
						.on("click", preevaluaAmaga)
						.appendTo( imc_forms_contenidor );

				}

			},
			selecciona = function(e) {

				input = $(this);

				var esAvaluable = (input.closest(".imc-element").attr("data-avalua") === "s") ? true : false;

				if (esAvaluable) {

					avalua();

				}

			},
			avalua = function(e) {

				input_element = input.closest(".imc-element");
				input_id = input_element.attr("data-id");
				input_tipus = input_element.attr("data-tipus");

				// preevalua?

				if (input_tipus !== "texto" && imc_forms_contenidor.attr("data-preevalua") === "s") {
					return;
				}

				// missatge enviant

				imc_missatge
					.attr("tabindex", "-1")
					.appMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtAvaluantTitol, alMostrar: function() { enviaRetarda(); } })
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

				// serialitza

				var valorsSerialitzats = imc_formulari_finestra.appSerialitza({ verifica: false });

				valorsSerialitzats["idCampo"] = input_id;

				// dades ajax

				var pag_url = APP_FORM_AVALUA_CAMP,
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

								imc_missatge
									.appMissatge({ accio: "warning", titol: data.mensaje.titulo, text: data.mensaje.texto, alAcceptar: function() { mostra(); } });

								return;

							}

							JSON_FORMULARI = json;

							resultat();

						} else {

							envia_ajax = false;

							imc_forms_contenidor
								.removeAttr("data-preevalua");

							consola("Avalua dada del formulari: error des de JSON");

							imc_contenidor
								.errors({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, url: json.url });

						}

					})
					.fail(function(dades, tipus, errorThrown) {

						envia_ajax = false;

						imc_forms_contenidor
							.removeAttr("data-preevalua");

						if (tipus === "abort") {
							return false;
						}

						consola("Avalua dada del formulari: error des de FAIL");

						imc_contenidor
							.errors({ estat: "fail" });

					});

			},
			resultat = function() {

				var validacio = JSON_FORMULARI.datos.validacion
					,validacio_estat = (validacio !== null && validacio.estado) ? validacio.estado : false
					,validacio_missatge = (validacio !== null && validacio.mensaje) ? validacio.mensaje : false
					,camp_id = (validacio !== null && validacio.campo !== null && validacio.campo !== "") ? validacio.campo : false;

				if (validacio_estat === "error") {

					if (camp_id && camp_id !== "" && camp_id !== null) {

						imc_missatge
							.appMissatge({ accio: "error", titol: validacio_missatge, text: txtFormErrorText, amagaDesdeFons: false, alTancar: function() { remarca(camp_id); enfocaAlSeguent(); } });

					} else {

						imc_missatge
							.appMissatge({ accio: "error", titol: validacio_missatge, text: "", amagaDesdeFons: false, alTancar: function() { enfocaAlSeguent(); } });

					}

					return;

				}

				if (validacio_missatge && validacio_missatge !== null && validacio_missatge !== "") {

					var destacaCamp = function() {

							if (validacio_estat === "error") {

								input_element
									.appDestaca({ referent: imc_forms_contenidor });

							}

						};

					imc_missatge
						.appMissatge({ accio: validacio_estat, titol: validacio_missatge, text: "", alTancar: function() { destacaCamp(); }, alAcceptar: function() { destacaCamp(); imc_missatge.appMissatge({ araAmaga: true }); enfocaAlSeguent(); } });

				} else {

					imc_missatge
						.appMissatge({ araAmaga: true });

					enfocaAlSeguent();

				}

				// configuracio

				imc_forms_contenidor
					.attr("data-repinta", "s")
					.appFormsConfiguracio();


				// formateig de dades

				imc_forms_contenidor
					.appFormsFormateig();

			},
			remarca = function(camp_id) {

				imc_forms_contenidor
					.find("div[data-id="+camp_id+"]:first")
						.addClass("imc-el-error")
						.appDestaca({ referent: imc_forms_contenidor });

			},
			enfocaAlSeguent = function() {

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

					var el_seguent = input_element.nextAll(".imc-element[data-id]:first");

					if (el_seguent.length) {

						el_seguent
							.find("input:first, textarea:first")
								.focus();

					} else {

						imc_formulari_finestra
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


// appFormsFormateig
$.fn.appFormsFormateig = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			arxius_data = false,
			arxius_hora = false,
			inicia = function() {

				// data

				if (!Modernizr.inputtypes.date) {

					var configura_data = function() {

							element
								.find(".imc-data")
									.addClass("imc-no-type-date")
									.dataCompletar()
									.datepicker({ dateFormat: "dd/mm/yy", changeMonth: true, changeYear: true });

						};

					if (!arxius_data) {

						$.when(
			
							//$.get(APP_ + "css/ui-lightness/jquery-ui-1.10.3.custom.min.css?" + APP_VERSIO)
							$.getScript(APP_ + "js/utils/jquery-maskedinput.min.js?" + APP_VERSIO)
							,$.getScript(APP_ + "js/utils/jquery-ui-1.10.3.custom.min.js?" + APP_VERSIO)

						).then(

							function( cssPas ) {

								// estils

								//<link rel="stylesheet" media="screen" href="css/imc-sf.css" />

								$("<link>")
									.attr({ rel: "stylesheet", media: "screen", href: APP_ + "css/ui-lightness/jquery-ui-1.10.3.custom.min.css?" + APP_VERSIO })
										.appendTo( imc_head );

								arxius_data = true;

								// configura

								configura_data();

							}

						).fail(

							function() {

								envia_ajax = false;

								consola("Forms inicia (data): error caregant CSS i JS");

								imc_contenidor
									.errors({ estat: "fail" });

							}

						);

					} else {

						configura_data();

					}

				}

				// hora

				if (!Modernizr.inputtypes.time) {

					var configura_hora = function() {

							element
								.find("input[type=time]")
									.addClass("imc-no-type-time")
									.horaCompletar();


						};

					if (!arxius_hora) {

						$.when(
			
							$.getScript(APP_ + "js/utils/jquery-maskedinput.min.js?" + APP_VERSIO)

						).then(

							function() {

								arxius_hora = true;

								// configura

								configura_hora();
								
							}

						).fail(

							function() {

								envia_ajax = false;

								consola("Forms inicia (hora): error caregant CSS i JS");

								imc_contenidor
									.errors({ estat: "fail" });

							}

						);

					} else {

						configura_hora();

					}

				}

				// generatedcontent

				if (!Modernizr.generatedcontent) {

					element
						.find(".imc-el-obligatori label")
							.prepend("<span class=\"imc-obligatori\">*</span>");

				}

				// per a tothom

				if (element.hasClass("imc-el-selector") && !element.hasClass("imc-el-selector-events-enlinia")) {

					element
						.selectorIMC();

				}

				element
					.find(".imc-el-selector:not(.imc-el-selector-events-enlinia)")
						.selectorIMC()
						.end()
					.find("*[title]")
						.title()
						.end()
					.find("input[type=number]")
						.inputNumero()
						.end()
					.find("a:not([href])")
						.attr("tabindex", "0")
						.attr("href", "javascript:;")
						.end()
					.find(".imc-el-import")
						.immport()
						.end()
					.find(".imc-el-index")
						.indexa();

				if (element.hasClass("imc-el-index")) {

					element
						.indexa();

				}

			};

		// inicia
		inicia();

	});
	return this;
}
// /appFormsFormateig


// appFormsAccions
$.fn.appFormsAccions = function(options) {
	var settings = $.extend({
		element: "",
		accio: false
	}, options);
	this.each(function(){
		var element = $(this),
			accio = settings.accio,
			envia_ajax = false,
			inicia = function() {

				element
					.off(".appFormsAccions")
					.on("click.appFormsAccions", "button[data-accio=tanca]", tanca)
					.on("click.appFormsAccions", ".imc--finalitza button", envia)
					.on("click.appFormsAccions", "fieldset[data-tipus='selector'] label, div[data-tipus='verificacion'] label", llevaError);

			},
			llevaError = function(e) {

				var el = $(this)
					,el_element = el.closest(".imc-element");

				el_element
					.removeClass("imc-el-error");

			},
			tanca = function() {

				var potGuardar = imc_formulari.attr("data-guardar");

				var text_avis = (potGuardar === "s") ? txtFormEixirText : txtFormEixirNoGuardaText
					,potGuardar_class = (potGuardar === "s") ? "imc--si-pot-guardar" : "imc--no-pot-guardar";

				imc_missatge
					.removeClass("imc--si-pot-guardar imc--no-pot-guardar")
					.addClass( potGuardar_class )
					.appMissatge({ accio: "formSurt", titol: txtFormEixirTitol, text: text_avis });

				imc_missatge
					.appMissatgeFormAccions();

			},
			envia = function() {

				var valorsSerialitzats = imc_formulari_finestra.appSerialitza();

				if (!valorsSerialitzats) {

					var vesAlPrimerError = function() {

							imc_formulari_finestra
								.find(".imc-el-error:first")
									.appDestaca({ referent: imc_forms_contenidor });

							imc_formulari_finestra
								.find(".imc-el-error:first input")
									.focus();

							return;

						};

					imc_missatge
						.appMissatge({ accio: "error", titol: txtFormErrorTitol, text: txtFormErrorText, alTancar: function() { vesAlPrimerError(); } });

					return;

				}

				imc_missatge
					.appMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtFormDesant });

				var pag_url = APP_FORM_GUARDA,
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

								imc_missatge
									.appMissatge({ accio: "warning", titol: data.mensaje.titulo, text: data.mensaje.texto, alAcceptar: function() { mostra(); } });

								return;

							}

							mostra(json);

						} else {

							envia_ajax = false;

							consola("Pas: error des de JSON");

							imc_contenidor
								.errors({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, url: json.url });

						}

					})
					.fail(function(dades, tipus, errorThrown) {

						envia_ajax = false;

						if (tipus === "abort") {
							return false;
						}

						consola("Formuari GUARDA: error des de FAIL");

						imc_contenidor
							.errors({ estat: "fail" });

					});

			},
			mostra = function(json) {

				var validacio_estat = (json.datos.validacion && json.datos.validacion !== null) ? json.datos.validacion.estado : false
					,validacio_missatge = (json.datos.validacion && json.datos.validacion !== null) ? json.datos.validacion.mensaje : false
					,estaFinalitzat = (json.datos.finalizado === "s") ? true : false
					,camp_id = (json.datos.validacion && json.datos.validacion !== null) ? json.datos.validacion.campo : false
					,url = json.datos.url;

				if (validacio_estat === "error") {

					if (camp_id && camp_id !== "" && camp_id !== null) {

						imc_missatge
							.appMissatge({ accio: "error", titol: validacio_missatge, text: txtFormErrorText, amagaDesdeFons: false, alTancar: function() { remarca(camp_id); } });

					} else {

						imc_missatge
							.appMissatge({ accio: "error", titol: validacio_missatge, text: "", amagaDesdeFons: false });

					}

					return;

				}

				var accio = (estaFinalitzat) ? function() { finalitza(url) } : function() { carrega() };

				if (validacio_missatge) {

					imc_missatge
						.appMissatge({ accio: validacio_estat, titol: validacio_missatge, text: "", amagaDesdeFons: false, alTancar: function() { accio(); } })
						.appMissatgeFormAccions();

					return;

				}

				accio();

			},
			remarca = function(camp_id) {

				imc_formulari_finestra
					.find("*[data-id='"+camp_id+"']")
						.appDestaca({ referent: imc_forms_contenidor });

			},
			carrega = function(url) {

				alert("Nova pàgina del form!");

			},
			finalitza = function(url) {

				document.location = url;

			};

		// envia o inicia

		if (accio === "envia") {

			envia();

		} else {

			inicia();

		}

	});
	return this;
}


// appCarregaScripts

function appFormsCarregaScripts() {

	var completat = function() {

			// configuracio

			imc_forms_contenidor
				.appFormsConfiguracio();

			// ajuda

			imc_forms_ajuda
				.appFormsAjuda();

			// errors

			imc_forms_contenidor
				.appFormsErrors();

			// arbre

			imc_forms_contenidor
				.find(".imc-el-arbre")
					.arbre();

			// taula

			imc_forms_contenidor
				.find(".imc-el-taula")
					.taula();

			// text selector

			imc_forms_contenidor
				.find(".imc-el-text-selector")
					.inputSelectAjax();

			// formateig de dades

			imc_forms_contenidor
				.appFormsFormateig();

		};

	if (FORMS_JS) {

		completat();
		return;

	}


	// carrega de JS

	$.when(

		$.getScript(APP_ + "js/forms/imc-forms--comuns.js?" + APP_VERSIO)
		,$.getScript(APP_ + "js/forms/imc-forms--funcions.js?" + APP_VERSIO)
		,$.getScript(APP_ + "js/forms/imc-forms--validacions.js?" + APP_VERSIO)
		,$.getScript(APP_ + "js/forms/imc-forms--serialitza.js?" + APP_VERSIO)
		//,$.getScript(APP_ + "js/forms/literals/jquery-imc-literals-calendari-" + APP_IDIOMA + ".js")

	).then(

		function() {

			FORMS_JS = true;

			completat();

		}

	).fail(

		function() {

			consola("Error carrega JS FORMS");

			imc_contenidor
				.errors({ estat: "fail" });

		}

	);

}


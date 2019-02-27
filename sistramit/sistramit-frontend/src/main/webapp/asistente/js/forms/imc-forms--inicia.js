// index

var imc_forms_contenidor = imc_formulari.find(".imc--contingut:first")
	,imc_forms_ajuda = $("#imc-forms-ajuda");


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
						elm_label = elm.find(".imc-el-etiqueta:first"),
						elm_label_W = elm_label.outerWidth(),
						elm_label_H = elm_label.outerHeight(),
						elm_control = elm.find(".imc-el-control:first"),
						elm_control_W = elm_control.outerWidth(),
						elm_control_H = elm_control.outerHeight(),
						ajuda_W = ajuda_elm.outerWidth(),
						ajuda_H = ajuda_elm.outerHeight();

					consola(element.attr("class") + " - " + window_scroll_T)

					var ajuda_T = elm_T-ajuda_H-5+window_scroll_T,
						ajuda_L = elm_L,
						cueta_html = "<div class=\"imc-el-aj-pestanya\"><span></span></div>";

					if (!ajuda_elm.find(".imc-el-aj-pestanya").length) {
						//ajuda_elm.append(cueta_html);
					}

					if ((elm_L+ajuda_W) > window_W) {

						ajuda_L = elm_L - ajuda_W + elm_control_W;
						//ajuda_elm.find(".imc-el-aj-pestanya").css({ left: (ajuda_W-elm_label_W+5)+"px" });

						ajuda_elm
							.addClass("imc--dreta");



					} else {

						//ajuda_elm.find(".imc-el-aj-pestanya").removeAttr("style");

						ajuda_elm
							.removeClass("imc--dreta");


					}

					var ajuda_T_inici = ajuda_T+5;

					if (window_scroll_T > ajuda_T) {
						ajuda_T = elm_T + elm_H;
						ajuda_T_inici = ajuda_T-5;

						//ajuda_elm.find(".imc-el-aj-pestanya").addClass("imc-el-aj-pestanya-dalt");

						ajuda_elm
							.addClass("imc--dalt");

					} else {

						//ajuda_elm.find(".imc-el-aj-pestanya").removeClass("imc-el-aj-pestanya-dalt");

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
					ajuda_elm.stop();
					if (ajuda_anterior_elm) {
						ajuda_anterior_elm.removeClass("imc-el-ajuda-on").removeAttr("style");
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
		referent: $(window)
	}, options);
	this.each(function(){
		var element = $(this),
			referent = settings.referent,
			el = element.closest(".imc-element"),
			imc_finestra = $(window),
			imc_document = $(document),
			destacat_el = false,
			inicia = function() {

				if ($("#imc-destacat").length) {

					$("#imc-destacat")
						.remove();

				}

				var imc_finestra_H = referent.height(),
					imc_finestra_scroll_T = referent.scrollTop(),
					imc_document_H = referent[0].scrollHeight,
					imc_document_W = referent.outerWidth(true);

				var el_T = el.position().top + imc_finestra_scroll_T;// + 20,
					el_L = el.position().left,
					el_W = el.outerWidth(true),
					el_H = el.outerHeight(true);

				consola("offset()TOP: " + el.offset().top + " - offset()LEFT: " + el.offset().left);
				consola("position()TOP: " + el.position().top + " - position()LEFT: " + el.position().left);

				var destacat_el_T = $("<div>").addClass("imc--dalt").css({ height: el_T+"px" }),
					destacat_el_B = $("<div>").addClass("imc--baix").css({ top: (el_T+el_H)+"px", height: (imc_document_H - el_T - el_H)+"px" }),
					destacat_el_L = $("<div>").addClass("imc--esquerre").css({ top: el_T+"px", width: el_L+"px", height: el_H+"px" }),
					destacat_el_R = $("<div>").addClass("imc--dreta").css({ top: el_T+"px", left: (el_L+el_W)+"px", width: (imc_document_W - (el_L + el_W))+"px", height: el_H+"px" }); //

				destacat_el = $("<div>").addClass("imc-destacat").attr("id", "imc-destacat").append( destacat_el_T ).append( destacat_el_B ).append( destacat_el_L ).append( destacat_el_R ).appendTo( referent );

				// escrolletja

				var pos_respecte_finestra = (imc_finestra_H / 2) + imc_finestra_scroll_T;

				consola( "id: " + el.attr("data-id") );
				consola( el_T + " > "+ imc_finestra_scroll_T );

				if (el_T > pos_respecte_finestra) {

					var anarScroll = el_T - (imc_finestra_H / 2);

					escrolletja(anarScroll);

				} else if (el_T < imc_finestra_scroll_T) {

					var anarScroll = el_T - 20;

					escrolletja(anarScroll);

				} else {

					parpadeja();

				}

			},
			escrolletja = function(anarScroll) {

				referent
					.animate(
						{ scrollTop: anarScroll+"px" }
						,500
						,function() {

							parpadeja();

						});

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
								,elm_input = elm.find("input:first")
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

									if (opcio_valor) {

										setTimeout(
											function() {

												elm
													.find("a[data-value='"+opcio_valor+"']:first")
														.trigger("click");

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

													elm
														.find("input[value='"+selec_val+"']:first")
															.parent()
																.find("label")
																	.trigger("click");

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

												elm
													.find("input[value='"+opcio_valor+"']:first")
														.parent()
															.find("label")
																.trigger("click");

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

					},100
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
					.on("blur.appFormsValida", "input", valida);

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

					var identificadorValido = false;

					if (input.attr("data-nif") === "s") {

						identificadorValido = ( appValidaIdentificador.nif(input_val) ) ? true : false;

					}

					if (!identificadorValido && input.attr("data-cif") === "s") {

						identificadorValido = ( appValidaIdentificador.cif(input_val) ) ? true : false;

					}

					if (!identificadorValido && input.attr("data-nie") === "s") {

						identificadorValido = ( appValidaIdentificador.nie(input_val) ) ? true : false;

					}

					if (!identificadorValido && input.attr("data-nss") === "s") {

						identificadorValido = ( appValidaIdentificador.nss(input_val) ) ? true : false;

					}

					esError = !identificadorValido;

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
					.on("blur.appFormsAvalua", "div[data-tipus='texto'] input", selecciona)
					.on("click.appFormsAvalua", "div[data-tipus='selector'][data-contingut='d'] .imc-select-submenu a", selecciona)
					.on("click.appFormsAvalua", "fieldset[data-tipus='selector'] label", selecciona)
					.on("click.appFormsAvalua", "div[data-type='check'] .imc-input-check", selecciona);

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

				// missatge enviant

				imc_missatge
					.appMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtAvaluantTitol, alMostrar: function() { envia(); } });

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

							consola("Avalua dada del formulari: error des de JSON");

							imc_contenidor
								.errors({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, url: json.url });

						}

					})
					.fail(function(dades, tipus, errorThrown) {

						envia_ajax = false;

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
					,validacio_missatge = (validacio !== null && validacio.mensaje) ? validacio.mensaje : false;

				if (validacio_estat === "error") {

					imc_forms_contenidor
						.find("div[data-id="+input_id+"]:first")
							.addClass("imc-el-error");

				}

				if (validacio_missatge && validacio_missatge !== null && validacio_missatge !== "") {

					var destacaCamp = function() {

							input_element
								.appDestaca({ referent: imc_forms_contenidor });

						};

					imc_missatge
						.appMissatge({ accio: validacio_estat, titol: validacio_missatge, alTancar: function() { destacaCamp(); }, alAcceptar: function() { destacaCamp(); imc_missatge.appMissatge({ araAmaga: true }); } });

				} else {

					imc_missatge
						.appMissatge({ araAmaga: true });

				}

				// configuracio

				imc_forms_contenidor
					.appFormsConfiguracio();


				// formateig de dades

				imc_forms_contenidor
					.appFormsFormateig();

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
			inicia = function() {

				// data

				if (!Modernizr.inputtypes.date) {

					element
						.find(".imc-data")
							.addClass("imc-no-type-date")
							.dataCompletar()
							.datepicker({ dateFormat: "dd/mm/yy", changeMonth: true, changeYear: true });

				}

				// hora

				if (!Modernizr.inputtypes.time) {

					element
						.find("input[type=time]")
							.addClass("imc-no-type-time")
							.horaCompletar();

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
					.appMissatge({ accio: "formSurt", titol: txtFormEixirTitol, text: text_avis });

				imc_missatge
					.removeClass("imc--si-pot-guardar imc--no-pot-guardar")
					.addClass( potGuardar_class )
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
					,url = json.datos.url;

				if (validacio_estat === "error") {

					imc_missatge
						.appMissatge({ accio: "error", titol: validacio_missatge, amagaDesdeFons: false });

					return;

				}

				var accio = (estaFinalitzat) ? function() { finalitza(url) } : function() { carrega() };

				if (validacio_missatge) {

					imc_missatge
						.appMissatge({ accio: validacio_estat, titol: validacio_missatge, amagaDesdeFons: false, alTancar: function() { accio(); } })
						.appMissatgeFormAccions();

					return;

				}

				accio();

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


	$.when(

		$.getScript(APP_ + "js/forms/imc-forms--comuns.js?" + APP_VERSIO)
		,$.getScript(APP_ + "js/forms/imc-forms--funcions.js?" + APP_VERSIO)
		,$.getScript(APP_ + "js/forms/imc-forms--validacions.js?" + APP_VERSIO)
		,$.getScript(APP_ + "js/forms/imc-forms--serialitza.js?" + APP_VERSIO)
		//,$.getScript(APP_ + "js/forms/literals/jquery-imc-literals-calendari-" + APP_IDIOMA + ".js")
		,$.getScript(APP_ + "js/forms/literals/vars-imc-literals-" + APP_IDIOMA + ".js?" + APP_VERSIO)

	).then(

		function() {

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

		}

	).fail(

		function() {

			consola("Error carrega JS FORMS");

			imc_contenidor
				.errors({ estat: "fail" });

		}

	);

}


// carrega

appFormsCarregaScripts();










// appEventExemple

function appEventExemple() {

	// events

	// input text

	$("#imc-text-selecciona").on("click", function() {
		control_select("input_1", "B");
	});

	$("#imc-text-deshabilita").on("click", function() {
		control_disabled("input_1", true);
	});

	$("#imc-text-habilita").on("click", function() {
		control_disabled("input_1", false);
	});

	$("#imc-text-lectura").on("click", function() {
		control_readOnly("input_1", true);
	});

	$("#imc-text-escritura").on("click", function() {
		control_readOnly("input_1", false);
	});

	$("#imc-text-valor").on("click", function() {
		var vals = control_values("input_1");
		alert("Etiqueta: " + vals.etiqueta + "- Valor: " + vals.valor);
	});

	// llista radios

	$("#imc-seleccionaRadio").on("click", function() {
		control_select("input_17", "B");
	});

	$("#imc-deshabilitaRadios").on("click", function() {
		control_disabled("input_17", true);
	});

	$("#imc-habilitaRadios").on("click", function() {
		control_disabled("input_17", false);
	});

	$("#imc-comLectura-17a").on("click", function() {
		control_readOnly("input_17", true);
	});

	$("#imc-comLectura-17b").on("click", function() {
		control_readOnly("input_17", false);
	});

	$("#imc-radio-seleccionat").on("click", function() {
		var vals = control_values("input_17");
		alert("Etiqueta: " + vals.etiqueta + "- Valor: " + vals.valor);
	});

	var radios_nou = [
										{ "etiqueta": "Opción AAAA", "valor": "AAAA", "defecto": false }
										,{ "etiqueta": "Opción BBBB", "valor": "BBBB", "defecto": false }
										,{ "etiqueta": "Opción CCCC", "valor": "CCCC", "defecto": false }
										,{ "etiqueta": "Opción DDDD", "valor": "DDDD", "defecto": true }
										,{ "etiqueta": "Opción EEEE", "valor": "EEEE", "defecto": false }
									];

	$("#imc-radio-canvia").on("click", function() {
		control_refill("input_17", radios_nou);
	});

	//

	$("#imc-seleccionaCheck").on("click", function() {
		control_select("input_18", "1");
	});

	$("#imc-seleccionaChecks").on("click", function() {
		control_select("input_18", ["4", "5"], true);
	});

	$("#imc-deshabilitaChecks").on("click", function() {
		control_disabled("input_18", true);
	});

	$("#imc-habilitaChecks").on("click", function() {
		control_disabled("input_18", false);
	});

	$("#imc-comLectura-18a").on("click", function() {
		control_readOnly("input_18", true);
	});

	$("#imc-comLectura-18b").on("click", function() {
		control_readOnly("input_18", false);
	});

	$("#imc-select-alguno").on("click", function() {
		alert(
			imc_forms_formulari.find("input[name=input_18]:checkbox:checked").length
		);
	});

	$("#imc-check-seleccionat").on("click", function() {
		var vals = control_values("input_18"),
			selecccionats = "";
		for (var i=0; i<vals.length; i++) {
			selecccionats += " (" + vals[i].etiqueta + ", " + vals[i].valor + ")";
		}
		alert("Total: " + vals.length + " - Seleccionats: " + selecccionats);
	});

	var checks_nou = [
										{ "etiqueta": "Opción AAAA", "valor": "AAAA", "defecto": false }
										,{ "etiqueta": "Opción BBBB", "valor": "BBBB", "defecto": false }
										,{ "etiqueta": "Opción CCCC", "valor": "CCCC", "defecto": true }
										,{ "etiqueta": "Opción DDDD", "valor": "DDDD", "defecto": true }
										,{ "etiqueta": "Opción EEEE", "valor": "EEEE", "defecto": false }
									];

	$("#imc-check-canvia").on("click", function() {
		control_refill("input_18", checks_nou);
	});

	//

	$("#imc-select-op3").on("click", function() {
		control_select("input_19", "CC");
	});

	$("#imc-select-deshabilita").on("click", function() {
		control_disabled("input_19", true);
	});

	$("#imc-select-habilita").on("click", function() {
		control_disabled("input_19", false);
	});

	$("#imc-select-lectura").on("click", function() {
		control_readOnly("input_19", true);
	});

	$("#imc-select-escritura").on("click", function() {
		control_readOnly("input_19", false);
	});

	var select_nou = [
											{ "etiqueta": "Opción AAAA", "valor": "AAAA", "defecto": false }
											,{ "etiqueta": "Opción BBBB", "valor": "BBBB", "defecto": false }
											,{ "etiqueta": "Opción CCCC", "valor": "CCCC", "defecto": false }
											,{ "etiqueta": "Opción DDDD", "valor": "DDDD", "defecto": true }
											,{ "etiqueta": "Opción EEEE", "valor": "EEEE", "defecto": false }
										];

	$("#imc-select-canvia").on("click", function() {
		control_refill("input_19", select_nou);
	});

	$("#imc-select-seleccionat").on("click", function() {
		var vals = control_values("input_19");
		alert("Etiqueta: " + vals.etiqueta + "- Valor: " + vals.valor);
	});

	$("#imc-select-canvia-i-selecciona").on("click", function() {
		control_refill("input_19", select_nou);
		control_select("input_19", "CCCC");
	});

	//

	$("#imc-multipleRadio-opA").on("click", function() {
		control_select("input_20", "A");
	});

	$("#imc-multipleRadio-deshabilita").on("click", function() {
		control_disabled("input_20", true);
	});

	$("#imc-multipleRadio-habilita").on("click", function() {
		control_disabled("input_20", false);
	});

	$("#imc-multipleRadio-lectura").on("click", function() {
		control_readOnly("input_20", true);
	});

	$("#imc-multipleRadio-escritura").on("click", function() {
		control_readOnly("input_20", false);
	});

	$("#imc-multipleRadio-seleccionat").on("click", function() {
		var vals = control_values("input_20");
		alert("Etiqueta: " + vals.etiqueta + "- Valor: " + vals.valor);
	});

	var multipleRadio_nou = [
														{ "etiqueta": "Opción AAAA", "valor": "AAAA", "defecto": false }
														,{ "etiqueta": "Opción BBBB", "valor": "BBBB", "defecto": false }
														,{ "etiqueta": "Opción CCCC", "valor": "CCCC", "defecto": false }
														,{ "etiqueta": "Opción DDDD", "valor": "DDDD", "defecto": true }
														,{ "etiqueta": "Opción EEEE", "valor": "EEEE", "defecto": false }
													];

	$("#imc-multipleRadio-canvia").on("click", function() {
		control_refill("input_20", multipleRadio_nou);
	});

	//

	$("#imc-multipleCheck").on("click", function() {
		control_select("input_21", "A");
	});

	$("#imc-multipleChecks").on("click", function() {
		control_select("input_21", ["C", "D"], true);
	});

	$("#imc-multipleCheck-deshabilita").on("click", function() {
		control_disabled("input_21", true);
	});

	$("#imc-multipleCheck-habilita").on("click", function() {
		control_disabled("input_21", false);
	});

	$("#imc-multipleCheck-lectura").on("click", function() {
		control_readOnly("input_21", true);
	});

	$("#imc-multipleCheck-escritura").on("click", function() {
		control_readOnly("input_21", false);
	});

	$("#imc-multipleCheck-seleccionat").on("click", function() {
		var vals = control_values("input_21"),
			selecccionats = "";
		for (var i=0; i<vals.length; i++) {
			selecccionats += " (" + vals[i].etiqueta + ", " + vals[i].valor + ")";
		}
		alert("Total: " + vals.length + " - Seleccionats: " + selecccionats);
	});

	var multipleCheck_nou = [
														{ "etiqueta": "Opción AAAA", "valor": "AAAA", "defecto": true }
														,{ "etiqueta": "Opción BBBB", "valor": "BBBB", "defecto": false }
														,{ "etiqueta": "Opción CCCC", "valor": "CCCC", "defecto": false }
														,{ "etiqueta": "Opción DDDD", "valor": "DDDD", "defecto": true }
														,{ "etiqueta": "Opción EEEE", "valor": "EEEE", "defecto": false }
													];

	$("#imc-multipleCheck-canvia").on("click", function() {
		control_refill("input_21", multipleCheck_nou);
	});

	// arbre

	$("#imc-arbre-selecciona").on("click", function() {
		control_select("input_checkbox1", "2");
	});

	$("#imc-arbre-selecciona-n").on("click", function() {
		control_select("input_checkbox1", ["2","3","4"]);
	});

	$("#imc-arbre-deshabilita").on("click", function() {
		control_disabled("input_checkbox1", true);
	});

	$("#imc-arbre-habilita").on("click", function() {
		control_disabled("input_checkbox1", false);
	});

	$("#imc-arbre-lectura").on("click", function() {
		control_readOnly("input_checkbox1", true);
	});

	$("#imc-arbre-escritura").on("click", function() {
		control_readOnly("input_checkbox1", false);
	});

	var arbre_nou = [
										{"valor":"e", "etiqueta":"España"},
										{"parentValor":"e", "valor":"ca", "etiqueta":"Catalunya"},
										{"parentValor":"ca", "valor":"b", "etiqueta":"Barcelona"},
										{"parentValor":"e", "valor":"cv", "etiqueta":"País Valencià"},
										{"parentValor":"cv", "valor":"a", "etiqueta":"Alicante"},
										{"parentValor":"cv", "valor":"c", "etiqueta":"Castellon"},
										{"parentValor":"cv", "valor":"v", "etiqueta":"Valencia"},
										{"parentValor":"v", "valor":"xa", "etiqueta":"Xàtiva"},
										{"parentValor":"v", "valor":"ma", "etiqueta":"Massamagrell"}
									];

	$("#imc-arbre-nou").on("click", function() {
		control_refill("input_checkbox1", arbre_nou);
	});

	$("#imc-arbre-seleccionat").on("click", function() {
		var vals = control_values("input_checkbox1"),
			selecccionats = "";
		for (var i=0; i<vals.length; i++) {
			selecccionats += " (" + vals[i].etiqueta + ", " + vals[i].valor + ")";
		}
		alert("Total: " + vals.length + " - Seleccionats: " + selecccionats);
	});

	$("#imc-arbre-canvia-i-selecciona").on("click", function() {
		control_refill("input_checkbox1");
		control_select("input_checkbox1", "xa");
	});

	$("#imc-arbre-expandix").on("click", function() {
		control_expandAll("input_checkbox1", true);
	});

	$("#imc-arbre-contrau").on("click", function() {
		control_expandAll("input_checkbox1", false);
	});

	//

	$("#imc-taula-deshabilita").on("click", function() {
		control_disabled("input_30", true);
	});

	$("#imc-taula-habilita").on("click", function() {
		control_disabled("input_30", false);
	});

	$("#imc-taula-lectura").on("click", function() {
		control_readOnly("input_30", true);
	});

	$("#imc-taula-escritura").on("click", function() {
		control_readOnly("input_30", false);
	});

	$("#imc-bt-afegix-input_30").on("click", function() {
		//document.location = "index_pag_7_taula.html";
		var url_page = "index_pag_7_taula_iframe.html",
			url_vars = "?listaelementos@accion=insertar&listaelementos@campo=cListaElementos&listaelementos@indice=0&ID_INSTANCIA=72181210c1cad5a1555e6ff9982bc975 <http://rsanz.indra.es:8080/formfront/ver.do?listaelementos%40accion=insertar&listaelementos%40campo=cListaElementos&listaelementos%40indice=0&ID_INSTANCIA=72181210c1cad5a1555e6ff9982bc975";
		control_tableDetall("input_30", url_page+url_vars);
	});

	//

	$("#imc-taula-deshabilita_2").on("click", function() {
		control_disabled("input_40", true);
	});

	$("#imc-taula-habilita_2").on("click", function() {
		control_disabled("input_40", false);
	});

	$("#imc-taula-lectura_2").on("click", function() {
		control_readOnly("input_40", true);
	});

	$("#imc-taula-escritura_2").on("click", function() {
		control_readOnly("input_40", false);
	});

	$("#imc-bt-afegix-input_40").on("click", function() {
		//document.location = "index_pag_7_taula.html";
		var url_page = "index_pag_7_taula_iframe.html",
			url_vars = "?listaelementos@accion=insertar&listaelementos@campo=cListaElementos&listaelementos@indice=0&ID_INSTANCIA=72181210c1cad5a1555e6ff9982bc975 <http://rsanz.indra.es:8080/formfront/ver.do?listaelementos%40accion=insertar&listaelementos%40campo=cListaElementos&listaelementos%40indice=0&ID_INSTANCIA=72181210c1cad5a1555e6ff9982bc975";
		control_tableDetall("input_40", url_page+url_vars);
	});

	// taula pag detall

	$("#imc-bt-ta-guarda").on("click", function() {
		document.location = "index_pag_7.html";
	});

	$("#imc-bt-ta-cancela").on("click", function() {
		document.location = "index_pag_7.html";
	});

	$("#imc-bt-ta-guarda-iframe").on("click", function() {
		control_tableDetall_accio("guarda");
	});

	$("#imc-bt-ta-cancela-iframe").on("click", function() {
		control_tableDetall_accio("cancela");
	});



	// textarea

	$("#imc-textarea-selecciona").on("click", function() {
		control_select("input_Textarea", "Un text per al textarea");
	});

	$("#imc-textarea-deshabilita").on("click", function() {
		control_disabled("input_Textarea", true);
	});

	$("#imc-textarea-habilita").on("click", function() {
		control_disabled("input_Textarea", false);
	});

	$("#imc-textarea-lectura").on("click", function() {
		control_readOnly("input_Textarea", true);
	});

	$("#imc-textarea-escritura").on("click", function() {
		control_readOnly("input_Textarea", false);
	});

	$("#imc-textarea-valor").on("click", function() {
		var vals = control_values("input_Textarea");
		alert("Etiqueta: " + vals.etiqueta + "- Valor: " + vals.valor);
	});

	// data

	$("#imc-data-selecciona").on("click", function() {
		control_select("input_15");
	});

	$("#imc-data-deshabilita").on("click", function() {
		control_disabled("input_15", true);
	});

	$("#imc-data-habilita").on("click", function() {
		control_disabled("input_15", false);
	});

	$("#imc-data-lectura").on("click", function() {
		control_readOnly("input_15", true);
	});

	$("#imc-data-escritura").on("click", function() {
		control_readOnly("input_15", false);
	});

	$("#imc-data-valor").on("click", function() {
		var vals = control_values("input_15");
		alert("Etiqueta: " + vals.etiqueta + "- Valor: " + vals.valor);
	});

	// hora

	$("#imc-time-selecciona").on("click", function() {
		control_select("input_16");
	});

	$("#imc-time-deshabilita").on("click", function() {
		control_disabled("input_16", true);
	});

	$("#imc-time-habilita").on("click", function() {
		control_disabled("input_16", false);
	});

	$("#imc-time-lectura").on("click", function() {
		control_readOnly("input_16", true);
	});

	$("#imc-time-escritura").on("click", function() {
		control_readOnly("input_16", false);
	});

	$("#imc-time-valor").on("click", function() {
		var vals = control_values("input_16");
		alert("Etiqueta: " + vals.etiqueta + "- Valor: " + vals.valor);
	});

	// data i hora

	$("#imc-dateTime-selecciona").on("click", function() {
		control_select("input_17");
	});

	$("#imc-dateTime-deshabilita").on("click", function() {
		control_disabled("input_17", true);
	});

	$("#imc-dateTime-habilita").on("click", function() {
		control_disabled("input_17", false);
	});

	$("#imc-dateTime-lectura").on("click", function() {
		control_readOnly("input_17", true);
	});

	$("#imc-dateTime-escritura").on("click", function() {
		control_readOnly("input_17", false);
	});

	$("#imc-dateTime-valor").on("click", function() {
		var vals = control_values("input_17");
		alert("Etiqueta: " + vals.etiqueta + "- Valor: " + vals.valor);
	});

	// numero

	$("#imc-number-selecciona").on("click", function() {
		control_select("input_19");
	});

	$("#imc-number-deshabilita").on("click", function() {
		control_disabled("input_19", true);
	});

	$("#imc-number-habilita").on("click", function() {
		control_disabled("input_19", false);
	});

	$("#imc-number-lectura").on("click", function() {
		control_readOnly("input_19", true);
	});

	$("#imc-number-escritura").on("click", function() {
		control_readOnly("input_19", false);
	});

	$("#imc-number-valor").on("click", function() {
		var vals = control_values("input_19");
		alert("Etiqueta: " + vals.etiqueta + "- Valor: " + vals.valor);
	});

	// check unitario

	$("#imc-check1-selecciona").on("click", function() {
		control_select("input_check_1", true);
	});

	$("#imc-check1-deselecciona").on("click", function() {
		control_select("input_check_1", false);
	});

	$("#imc-check1-deshabilita").on("click", function() {
		control_disabled("input_check_1", true);
	});

	$("#imc-check1-habilita").on("click", function() {
		control_disabled("input_check_1", false);
	});

	$("#imc-check1-lectura").on("click", function() {
		control_readOnly("input_check_1", true);
	});

	$("#imc-check1-escritura").on("click", function() {
		control_readOnly("input_check_1", false);
	});

	$("#imc-check1-valor").on("click", function() {
		var vals = control_values("input_check_1");
		alert("Etiqueta: " + vals.etiqueta + "- Valor: " + vals.valor);
	});

	// enviant dades
	$("#imc-capa-enviant-dades").on("click", function() {
		mostrarCapaEnviando();

		setTimeout(
			function() {
				ocultarCapaEnviando();
			}, 2000
		);

	});

}
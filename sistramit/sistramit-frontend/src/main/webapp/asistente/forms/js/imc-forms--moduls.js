// mòduls


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
						.appFormsAjudaCamp({ referent: imc_forms_finestra.find(".imc--contingut:first") });

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
						.appFormsAjudaCamp({ referent: imc_forms_finestra.find(".imc--contingut:first") });

					txtAjudaInfo = txtFormDinAjuda + " " + txtFormDinActivada;
					txtAjudaBoto = txtFormDinDesactiva;
					ajuda_data = "on";

				} else {

					imc_forms_ajuda
						.addClass("imc--desactivada");

					imc_forms_contenidor
						.off('.appFormsAjudaCamp');

					txtAjudaInfo = txtFormDinAjuda + " " + txtFormDinDesctivada;
					txtAjudaBoto = txtFormDinActiva;
					ajuda_data = "off";

				}


				imc_forms_ajuda
					.fadeOut(200, function() {

						canvia();

						imc_forms_ajuda
							.fadeIn(200);

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

					var window_W = referent.width(),
						window_H = referent.height(),
						window_scroll_T = referent.scrollTop(),
						elm_T = elm.position().top,
						elm_L = elm.position().left,
						elm_H = elm.outerHeight(true),
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

						ajuda_T = elm_T + window_scroll_T + elm_H;
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
					.appDestaca({ referent: imc_forms_contenidor.find(".imc--contingut :first") });

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
				imc_document_H = referent[0].scrollHeight || 0;
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

							$(this)
								.remove();

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
		forms_json: false
		,desDe: false // inicia, avalua, taula
	}, options);

	this.each(function(){
		var element = $(this),
			forms_json = settings.forms_json,
			desDe = settings.desDe,
			json_config = forms_json.datos.configuracion,
			json_valors = forms_json.datos.valores,
			json_valors_possibles = forms_json.datos.valoresPosibles,
			json_accions = forms_json.datos.acciones,
			json_recursos = forms_json.datos.recursos,
			json_mayuscules = forms_json.datos.forzarMayusculas || false,
			inicia = function() {

				if (desDe === "inicia") {

					// poder desar

					var poderGuardar = forms_json.datos.permitirGuardar || "n";

					element
						.attr("data-guardar", poderGuardar);

					// índex a les seccions

					var indexSeccions = forms_json.datos.indiceSecciones || "n";

					element
						.attr("data-index-seccions", indexSeccions);

					if (indexSeccions === "s" && element.find("#imc--nav-seccions").length) {

						$.getScript(
							APP_FORMS_ + "forms/js/imc-forms--seccions-nav.js?" + APP_FORMS_VERSIO
							,function() {

								element
									.appFormsSeccionsNav();

							}
						);

					}

					// títol

					var titol = forms_json.datos.titulo
						,mostarTitol = (forms_json.datos.mostrarTitulo === "s") ? true : false;

					if (mostarTitol) {

						imc_forms_finestra
							.find("h3:first span")
								.text( titol )
								.end()
							.find("h3:first")
								.show();

					} else {

						imc_forms_finestra
							.find("h3:first")
								.hide();

					}

					// esborrem recursos (css) anteriors

					imc_forms_finestra
						.find("link")
							.remove();

				}

				if (desDe === "inicia") {

					// mayuscules

					imc_forms_finestra
						.removeAttr("data-mayuscules")
						.off(".appFormsAvalua");

				}

				if (desDe === "avalua") {

					imc_forms_finestra
						.off(".appFormsAvalua");

				}

				// radio, checks

				element
					.find(".imc-input-radio label, .imc-input-check label")
						.attr("tabindex", "0");

				// recursos CSS

				if (json_recursos && json_recursos.css) {

					$("<link>")
						.attr({ rel: "stylesheet", href: json_recursos.css + "?ts=" + new Date().getTime() })
						.addClass("css_especial")
						.appendTo( imc_forms_finestra );

				}

				// recursos IMGs

				if (json_recursos && json_recursos.imagenes) {

					$(json_recursos.imagenes)
						.each(function() {

							re_img = this;

							if (re_img.captcha) {

								var img_elm = $("#" + re_img.id)
									,img_html = $("<img>").attr({ id: re_img.id, alt: "", title: txtRegenerarCaptcha, "data-src": re_img.src }).addClass("captcha")
									,img_a = $("<a>").attr({ "onclick": "javascript:$.regenerarCaptcha('" + re_img.id + "' );" }).append( img_html );

								img_elm
									.replaceWith( img_a );

							} else {

								var img_elm = $("#" + re_img.id)
									,img_html = $("<img>").attr({ id: re_img.id, alt: "", src: re_img.src });

								img_elm
									.replaceWith( img_html );

							}

						});

				}

				// mayúscules tot

				if (json_mayuscules && json_mayuscules === "s") {

					imc_forms_finestra
						.attr("data-mayuscules", "s");

				}

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

							var elm = element.find("*[data-id="+conf_id+"]") // imc_forms_contenidor.find("*[data-id="+conf_id+"]")
								,elm_input = elm.find("input:first, textarea:first");

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
										.attr("required", "required")
										.attr("aria-required", "true");

								} else if (conf_obligatori === "n") {

									elm_input
										.removeAttr("required")
										.removeAttr("aria-required");

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
											: (conf_contingut === "a") ? "ajax"
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
											: (conf_contingut === "te") ? "text" // number?
											: (conf_contingut === "d") ? "hidden"
											: (conf_contingut === "a") ? "text"
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

								// línies

								if (conf_opcions.lineas && conf_opcions.lineas !== null) {

									elm_input
										.attr({ "data-linies": conf_opcions.lineas });

									elm
										.addClass("imc-el-files-"+conf_opcions.lineas);

								}

								// mayúscules

								if (conf_opcions.mayusculas && conf_opcions.mayusculas === "s") {

									elm_input
										.attr({ "data-mayuscules": conf_opcions.mayusculas });

								}

								// expresió regular

								if (conf_opcions.regexp && conf_opcions.regexp !== null) {

									elm_input
										.attr({ "pattern": conf_opcions.regexp, "data-regexp": conf_opcions.regexp });

								}

								// identificador

								if (conf_opcions.dni && conf_opcions.dni === "s") {

									elm_input
										.attr("data-dni", conf_opcions.dni);

								}

								if (conf_opcions.nifOtros && conf_opcions.nifOtros === "s") {

									elm_input
										.attr("data-nifOtros", conf_opcions.nifOtros);

								}

								if (conf_opcions.nie && conf_opcions.nie === "s") {

									elm_input
										.attr("data-nie", conf_opcions.nie);

								}

								if (conf_opcions.nifPJ && conf_opcions.nifPJ === "s") {

									elm_input
										.attr("data-nifPJ", conf_opcions.nifPJ);

								}

								if (conf_opcions.nss && conf_opcions.nss === "s") {

									elm_input
										.attr("data-nss", conf_opcions.nss);

								}

								if (conf_opcions.nif && conf_opcions.nif === "s") {

									elm_input
										.attr("data-nif", conf_opcions.nif);

								}

								if (conf_opcions.cif && conf_opcions.cif === "s") {

									elm_input
										.attr("data-cif", conf_opcions.cif);

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

								if ((conf_opcions.rangoMin && conf_opcions.rangoMin !== null) || conf_opcions.rangoMin === 0) {

									elm_input
										.attr({ "min": conf_opcions.rangoMin, "data-rangMin": conf_opcions.rangoMin });

								}

								if ((conf_opcions.rangoMax && conf_opcions.rangoMax !== null) || conf_opcions.rangoMax === 0) {

									elm_input
										.attr({ "max": conf_opcions.rangoMax, "data-rangMax": conf_opcions.rangoMax });

								}

								// telèfon

								if (conf_opcions.fijo) {

									elm_input
										.attr({ "data-fixe": conf_opcions.fijo, maxlength: "9" });

								}

								if (conf_opcions.movil) {

									elm_input
										.attr({ "data-mobil": conf_opcions.movil, maxlength: "9" });

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

							// captcha

							if (conf_tipus === "captcha") {

								elm
									.appFormsCaptcha();

							}

							// llista elements

							if (conf_tipus === "listaElementos") {

								elm
									.appFormsLlistaElements({ columnes: conf_opcions.columnas, cerca: conf.busqueda, filesMax: conf_opcions.maxElementos, filesNum: conf_opcions.numElementos, operacions: conf_opcions.operaciones, autoOrdre: conf_opcions.autoorden, desDe: desDe });

							}

							// selector ajax

							if (conf_tipus === "selector" && conf_contingut === "a") {

								var place_holder_text = (APP_FORM_SELECTOR_DIN_NUM > 1) ? txtFormDinSelectDinamicNum.replace('{{num}}', APP_FORM_SELECTOR_DIN_NUM) : txtFormDinSelectDinamic;

								elm
									.find("textarea:first")
										.attr("placeholder", place_holder_text)
										.end()
									.appFormsSelectorAjax();

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

							var elm = element.find("*[data-id="+va_id+"]") // imc_forms_contenidor.find("*[data-id="+va_id+"]")
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

							var elm = element.find("*[data-id="+val_id+"]") //  imc_forms_contenidor.find("*[data-id="+val_id+"]")
								,elm_input = elm.find("input:first, textarea:first")
								,elm_input_tipus = elm.attr("data-tipus")
								,elm_input_contingut = elm.attr("data-contingut")
								,elm_esLectura = (elm.attr("data-lectura") === "s") ? true : false;

							elm
								.attr( "data-valortipus", val_tipus );

							elm_input
								.attr( "data-tipus", val_tipus );


							// revisem si hi ha valor if() o no else{}

							if (val_valor && val_valor !== "" && val_valor !== null && val_valor !== "null") {

								// AMB VALOR!

								if (elm_input_tipus === "texto" || elm_input_tipus === "oculto") {


									// revisem si es data

									if (elm_input_contingut === "fe") {

										val_valor = appFormsDataFormat(val_valor);

									}

									/*

									ANTIC

									var esData = (elm_input_contingut === "fe") ? true : false
										,data_format = (typeof APP_FORM_DATA_FORMAT !== "undefined" && APP_FORM_DATA_FORMAT === "es") ? "es" : "in";

									if (esData && data_format === "es") {

										var data_internacional = val_valor.split("/");

										val_valor = data_internacional[2] + "-" + data_internacional[1] + "-" + data_internacional[0];

									}*/


									// apliquem valor

									elm_input
										.val( val_valor );

								} else if (elm_input_tipus === "selector" && elm_input_contingut === "d") {

									var opcio_valor = val_valor.valor || false;

									if (opcio_valor && opcio_valor !== "") {

										setTimeout(
											function() {

												var elm_txt = elm.find("a[data-value='"+opcio_valor+"']:first").text();

												elm_input
													.val( opcio_valor );

												elm
													.find("a.imc-select:first")
														.html( $("<span>").text(elm_txt) );

												elm
													.find("ul li.imc-select-seleccionat")
														.removeClass("imc-select-seleccionat");

												elm
													.find("ul a[data-value='" + opcio_valor + "']:first")
														.parent()
															.addClass("imc-select-seleccionat");


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
															.prop("checked", true);

													if (elm.attr("data-lectura") === "s") {

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
														.prop("checked", true);

												if (elm.attr("data-lectura") === "s") {

													elm
														.find("input")
															.attr("disabled", "disabled");

												}

											},50
										);

									}

								} else if (elm_input_tipus === "selector" && elm_input_contingut === "a") {

									// apliquem valor

									var bt_span = $("<span>").text( txtFormDinEliminaSeleccio )
										,bt_ = $("<button>").attr({ type: "button", "data-accio": "seleccio-elimina", title: txtFormDinEliminaSeleccio, "data-tabula": "si" }).append( bt_span );

									elm
										.find("textarea:first")
											.prop("readonly", true)
											.val( val_valor.descripcion )
											.end()
										.find("input:first")
											.val( val_valor.valor )
											.end()
										.find(".imc-el-control:first")
											.append( bt_ );

									var capa_altura = appRevisaAlturaCamp(val_valor.descripcion, elm.find(".imc-el-control:first"));

								} else if (elm_input_tipus === "verificacion") {

									var estaMarcat = elm.find("input[data-marcat='"+val_valor+"']:first").length;

									// marcat

									if (estaMarcat) {

										elm
											.find("input:first")
												.prop("checked", true);

									} else if (!estaMarcat) {

										elm
											.find("input:first")
												.prop("checked", false);

									}

									// lectura

									if (elm_esLectura) {

										elm
											.find("input")
												.attr("disabled", "disabled");

									} else if (!elm_esLectura) {

										elm
											.find("input")
												.removeAttr("disabled");

									}

								} else if (elm_input_tipus === "listaElementos") {

									elm
										.appFormsLlistaElements({ dades: val_valor, desDe: desDe });

								}

							} else {

								// SENSE VALOR (null)

								if (elm_input_tipus === "texto") {

									elm_input
										.val( "" );

								} else if (elm_input_tipus === "selector" && elm_input_contingut === "d") {

									setTimeout(
										function() {

											elm_input
												.val( "" );

											elm
												.find("a.imc-select:first")
													.html( $("<span>").text(txtFormDinSelecciona + "...") );

											elm
												.find("ul li.imc-select-seleccionat")
													.removeClass("imc-select-seleccionat");

										}
										,50
									);

								} else if (elm_input_tipus === "selector" && elm_input_contingut === "m") {

									setTimeout(
										function() {

											elm
												.find("input")
													.prop("checked", false);

											if (elm.attr("data-lectura") === "s") {

												elm
													.find("input")
														.attr("disabled", "disabled");

											} else if (elm.attr("data-lectura") !== "s") {

												elm
													.find("input")
														.removeAttr("disabled");

											}

										}
										,50
									);

								} else if (elm_input_tipus === "selector" && elm_input_contingut === "u") {

									setTimeout(
										function() {

											elm
												.find("input")
													.prop("checked", false);

											if (elm.attr("data-lectura") === "s") {

												elm
													.find("input")
														.attr("disabled", "disabled");

											} else if (elm.attr("data-lectura") !== "s") {

												elm
													.find("input")
														.removeAttr("disabled");

											}

										},50
									);

								} else if (elm_input_tipus === "selector" && elm_input_contingut === "a") {

									elm
										.find("input, textarea")
											.prop("readonly", false)
											.val( "" );

									elm
										.find("button[data-accio=seleccio-elimina]")
											.remove();

								} else if (elm_input_tipus === "verificacion") {

									elm
										.find("input")
											.prop("checked", false);

									if (elm_esLectura) {

										elm
											.find("input")
												.attr("disabled", "disabled");

									} else if (!elm_esLectura) {

										elm
											.find("input")
												.removeAttr("disabled");

									}

								}

							}

						});

				}

				// accions

				if (json_accions && json_accions.length) {

					imc_forms_finestra
						.find(".imc-forms-navegacio")
							.remove();

					var acc_html = "<nav id=\"imc-forms-navegacio\" class=\"imc-forms-navegacio imc--forms-nav\"><ul></ul></nav>";

					imc_forms_finestra
						//.find(".imc--contingut:first")
							.append( acc_html );

					var acc_llistat = imc_forms_finestra.find(".imc-forms-navegacio:first ul:first");

					$(json_accions)
						.each(function() {

							var acc = this
								,acc_tipus = acc.tipo || false
								,acc_valor = acc.valor || false
								,acc_validar = acc.validar || false
								,acc_text = acc.titulo || false
								,acc_estil = acc.estilo || false
								,acc_accio = acc.accion || false;

							var bt_class = (acc_tipus === "anterior") ? "imc--anterior"
											: (acc_tipus === "siguiente") ? "imc--seguent"
											: (acc_tipus === "finalizar") ? "imc--finalitza"
											: (acc_tipus === "imprimir") ? "imc--imprimir"
											: (acc_tipus === "cancelar") ? "imc--cancelar"
											: (acc_tipus === "cerrar") ? "imc--tancar"
											: (acc_tipus === "editar") ? "imc--editar"
											: acc_estil;

							var acc_validar = (acc_tipus === "anterior") ? "n"
											: (acc_tipus === "siguiente") ? "s"
											: (acc_tipus === "finalizar") ? "s"
											: (acc_tipus === "imprimir") ? "n"
											: (acc_tipus === "cancelar") ? "n"
											: (acc_tipus === "cerrar") ? "n"
											: (acc_tipus === "editar") ? "n"
											: acc_validar;

							var bt_text = (acc_tipus === "anterior") ? txtFormDinAnterior
											: (acc_tipus === "siguiente") ? txtFormDinSeguent
											: (acc_tipus === "finalizar") ? txtFormDinFinalitza
											: (acc_tipus === "imprimir") ? txtFormDinImprimir
											: (acc_tipus === "cancelar") ? txtFormDinCancela
											: (acc_tipus === "cerrar") ? txtFormDinTanca
											: (acc_tipus === "editar") ? txtFormDinEdita
											: (acc_text && acc_text !== "" && acc_text !== null) ? acc_text
											: "";

							var acc_accio = (acc_tipus === "anterior") ? "anterior"
											: (acc_tipus === "siguiente") ? "seguent"
											: (acc_tipus === "finalizar") ? "finaliza"
											: (acc_tipus === "imprimir") ? "imprimix"
											: (acc_tipus === "cancelar") ? "cancela"
											: (acc_tipus === "cerrar") ? "tanca"
											: (acc_tipus === "editar") ? "edita"
											: "";

							// afegim html botó

							var bt = $("<button>").attr({ type: "button", "data-tipus": acc_tipus, "data-validar": acc_validar, "data-accio": acc_accio }).html( $("<span>").text( bt_text ) );

							$("<li>")
								.addClass( bt_class )
								.append( bt )
								.appendTo( acc_llistat );

						});

					/*
					var bt_finalitza_span = $("<span>").text( txtFormDinTancaFormulari )
						,bt_finalitza_button = $("<button>").addClass("imc--form-tanca").attr({ type: "button", id: "imc-bt-iframe-tanca", "data-accio": "tanca" }).html( bt_finalitza_span )
						,bt_li = $("<li>").addClass("imc--tanca").html( bt_finalitza_button );

					acc_llistat
						.prepend( bt_li );*/

				}

				// configuració (només lectura i modificable)

				if (json_config && json_config.length) {

					$(json_config)
						.each(function() {

							var conf = this
								,conf_id = conf.id
								,conf_obligatori = conf.obligatorio || false
								,conf_lectura = conf.soloLectura || false
								,conf_tipus = conf.tipo || false
								,conf_ocult = conf.oculto || false
								,conf_modificable = conf.modificable || false;

							var elm = element.find("*[data-id="+conf_id+"]")
								,elm_input = elm.find("input:first")
								,elm_textarea = elm.find("textarea:first");


							// separador i ocult

							if (conf_tipus === "separador" && conf_ocult) {

								elm
									.attr({ "data-ocult": conf_ocult });

								var esSeparador = elm.hasClass("imc-sep-salt-carro");

								if (esSeparador && conf_ocult === "s") {

									elm
										.prevUntil(".imc-sep-salt-carro")
											.attr({ "data-ocult-bloc": conf_ocult });

								} else if (esSeparador && conf_ocult === "n") {

									elm
										.prevUntil(".imc-sep-salt-carro")
											.removeAttr("data-ocult-bloc");

								}

							}


							// bloc i ocult

							if (conf_tipus === "bloque" && conf_ocult) {

								//alert(conf_id)

								var bloc_ultim = element.find("*[data-bloc="+conf_id+"]:last")

								elm
									.attr({ "data-ocult": conf_ocult });

								var esSeparador = bloc_ultim.hasClass("imc-sep-bloc-fi");

								if (esSeparador && conf_ocult === "s") {

									bloc_ultim
										.prevUntil(".imc-sep-bloc-ini")
											.attr({ "data-ocult-bloc": conf_ocult });

								} else if (esSeparador && conf_ocult === "n") {

									bloc_ultim
										.prevUntil(".imc-sep-bloc-ini")
											.removeAttr("data-ocult-bloc");

								}

							}


							// element ocult

							if (conf_tipus !== "separador" && conf_tipus !== "bloque" && conf_ocult) {

								elm
									.attr({ "data-ocult": conf_ocult });

							}


							// només lectura

							if (conf_lectura) {

								var conf_tipus = elm.attr("data-tipus")
									,conf_contingut = elm.attr("data-contingut");

								elm
									.attr({ "data-lectura": conf_lectura });

								if (conf_lectura === "s") {

									if (conf_tipus === "texto") {

										elm_input
											.attr("readonly", "readonly");

										elm_textarea
											.attr("readonly", "readonly");

									} else if (conf_tipus === "selector" && conf_contingut === "d") {

										elm
											.find("a.imc-select")
												.addClass("imc-select-lectura");

									} else if (conf_tipus === "selector" && (conf_contingut === "m" || conf_contingut === "u")) {

										elm
											.find("input")
												.attr("disabled", "disabled");

									} else if (conf_tipus === "selector" && conf_contingut === "a") {

										elm_textarea
											.attr("readonly", "readonly");

									} else if (conf_tipus === "check" || conf_tipus === "verificacion") {

										elm_input
											.attr("disabled", "disabled");

									}

								} else if (conf_lectura === "n") {

									if (conf_tipus === "texto") {

										elm_input
											.removeAttr("readonly");

										elm_textarea
											.removeAttr("readonly");

									} else if (conf_tipus === "selector" && conf_contingut === "d") {

										elm
											.find("a.imc-select")
												.removeClass("imc-select-lectura");

									} else if (conf_tipus === "selector" && (conf_contingut === "m" || conf_contingut === "u")) {

										elm
											.find("input")
												.removeAttr("disabled");

									} else if (conf_tipus === "selector" && conf_contingut === "a") {

										var estaMarcat = (elm.find("input[type=hidden]:first").val() !== "") ? true : false;

										if (!estaMarcat) {

											elm_textarea
												.removeAttr("readonly");

										}

									} else if (conf_tipus === "check" || conf_tipus === "verificacion") {

										elm_input
											.removeAttr("disabled");

									}

								}

							}

						});

				}

				// selectors, opció inicial

				var elements_selector = element.find("div.imc-el-selector:not(.imc-el-index)"); // imc_forms_finestra.find("div.imc-el-selector:not(.imc-el-index)");

				if (elements_selector.length) {

					elements_selector
						.each(function() {

							var el_sel = $(this)
								,el_sel_submenu = el_sel.find(".imc-select-submenu:first ul");

							el_sel_submenu
								.find(".imc--dada-selecciona")
									.remove();

							$("<li>")
								.addClass("imc--dada-selecciona")
								.html(
									$("<a>")
										.attr({ "data-value": "", "tabindex": "0", "href": "javascript:;", "data-selecciona": "" })
											.text(txtFormDinSelecciona + "...")
								)
								.prependTo( el_sel_submenu );

						});

				}

				// events

				setTimeout(
					function() {

						element // imc_forms_finestra
							.appFormsAccions()
							.appFormsValida()
							.appFormsAvalua();

						// ajuda

						if (element.find("#imc-forms-ajuda").length) {

							imc_forms_ajuda
								.appFormsAjuda();

						}

						// errors

						element
							.appFormsErrors();

						// arbre

						element
							.find(".imc-el-arbre")
								.arbre();

						// taula

						element
							.find(".imc-el-taula")
								.taula();

						// text selector

						element
							.find(".imc-el-text-selector")
								.inputSelectAjax();

						// formateig de dades

						element
							.appFormsFormateig();

						// ve de avaluar i vol repintar?

						if (element.attr("data-repinta") === "s") {

							element
								.removeAttr("data-repinta");

							return;

						}

						// primera vegada

						var primer_element = element.find("div.imc-element[data-id]:first") // imc_forms_finestra.find("div.imc-element[data-id]:first")
							,primer_element_camp = primer_element.find("input:first, textarea:first, a.imc-select:first");

						element // imc_forms_finestra
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
					.on("focus.appFormsValida", "input, textarea", valora)
					.on("blur.appFormsValida", "input, textarea", valida)
					.on("blur.appFormsValida", "div[data-contingut='nu'] input, div[data-contingut='nu'] textarea", formateja);

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
				ERROR_TEXT = (esError) ? txtFormDinCampError_buit : false;

				// textarea amb un màxim de línies

				if (input.is("TEXTAREA") && input.attr("data-linies") && input_val !== "") {

					esError = ( !input.appValida({ format: "textarea", valor: input_val }) ) ? true : false;
					ERROR_TEXT = (esError) ? txtFormDinCampError_linies + " " + input.attr("data-linies") +"." : false;

				}

				// codi postal

				if (input.attr("data-contingut") === "codipostal" && input_val !== "") {

					esError = ( !input.appValida({ format: "codipostal", valor: input_val }) ) ? true : false;
					ERROR_TEXT = (esError) ? txtFormDinCampError_cp : false;

				}

				// correu electrònic

				if (input.attr("data-contingut") === "correuelectronic" && input_val !== "") {

					esError = ( !input.appValida({ format: "correuelectronic", valor: input_val }) ) ? true : false;
					ERROR_TEXT = (esError) ? txtFormDinCampError_correu : false;

				}

				// expresió regular

				if (input.attr("data-contingut") === "expresioregular" && input_val !== "") {

					esError = ( !input.appValida({ format: "expresioregular", valor: input_val }) ) ? true : false;

				}

				// identificador

				if (input.attr("data-contingut") === "identificador" && input_val !== "") {

					var idValid = false;

					if (input.attr("data-dni") === "s") {

						idValid = ( appValidaIdentificador.dni(input_val) ) ? true : false;

					}

					if (!idValid && input.attr("data-nie") === "s") {

						idValid = ( appValidaIdentificador.nie(input_val) ) ? true : false;

					}

					if (!idValid && input.attr("data-nifOtros") === "s") {

						idValid = ( appValidaIdentificador.nifOtros(input_val) ) ? true : false;

					}

					if (!idValid && (input.attr("data-nifPJ") === "s" || input.attr("data-cif") === "s")) {

						idValid = ( appValidaIdentificador.nifPJ(input_val) ) ? true : false;

					}

					if (!idValid && input.attr("data-nss") === "s") {

						idValid = ( appValidaIdentificador.nss(input_val) ) ? true : false;

					}

					if (!idValid && input.attr("data-nif") === "s") {

						idValid = ( appValidaIdentificador.nif(input_val) ) ? true : false;

					}

					esError = !idValid;
					ERROR_TEXT = (esError) ? txtFormDinCampError_id : false;

				}

				// numero

				if (input.attr("data-contingut") === "numero" && input_val !== "") {

					esError = ( !input.appValida({ format: "numero", valor: input_val }) ) ? true : false;
					ERROR_TEXT = (esError) ? txtFormDinCampError_numero : false;

				}

				// telèfon

				if (input.attr("data-contingut") === "telefon" && input_val !== "") {

					esError = ( !input.appValida({ format: "telefon", valor: input_val }) ) ? true : false;
					ERROR_TEXT = (esError) ? txtFormDinCampError_telf : false;

				}

				// data

				if (input.attr("data-contingut") === "data") {

					esError = ( !input.appValida({ format: "data", valor: input_val }) ) ? true : false;
					ERROR_TEXT = (esError) ? txtFormDinCampError_data : false;

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
			desDeTaula = false,
			avalua_json = false,
			inicia = function() {

				element
					.off(".appFormsAvalua")
					.on("focus.appFormsAvalua", "div[data-tipus='texto'] input, div[data-tipus='texto'] textarea", preevalua)
					.on("blur.appFormsAvalua", "div[data-tipus='texto'] input, div[data-tipus='texto'] textarea", selecciona)
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

				//alert( "input_element_HTML: " + input_element.html() );

				//alert( "input_element: " + input_element.attr("data-id") + " -- " + "esLectura: " + esLectura)

				if (esLectura) {
					return;
				}

				if (esAvaluable && !esLectura) {

					element
						.attr("data-preevalua", "s");

					// posar capa invisible

					var imc_finestra_H = element.height() // imc_forms_contenidor.height()
						,imc_finestra_scroll_T = element.scrollTop() // imc_forms_contenidor.scrollTop()
						,imc_document_H = element[0].scrollHeight // imc_forms_contenidor[0].scrollHeight
						,imc_document_W = element.outerWidth(true); // imc_forms_contenidor.outerWidth(true);

					var el_T = input_element.position().top + imc_finestra_scroll_T
						,el_L = input_element.position().left
						,el_W = input_element.outerWidth(true)
						,el_H = input_element.outerHeight(true);

					var preevaluaAmaga = function() {

							$("#imc-preevalua")
								.remove();

							$(window)
								.off(".preevalua");

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
						.appendTo( element ); // imc_forms_contenidor

					$(window)
						.on("click.preevalua", preevaluaAmaga);

				}

			},
			selecciona = function(e) {

				input = $(this);

				var esAvaluable = (input.closest(".imc-element").attr("data-avalua") === "s") ? true : false
					,esLectura = (input.closest(".imc-element").attr("data-lectura") === "s") ? true : false;

				if (esAvaluable && !esLectura) {

					avalua();

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
						dataType: "json",
						beforeSend: function(xhr) {
							xhr.setRequestHeader(headerCSRF, tokenCSRF);
						}
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

							resultat();

						} else {

							envia_ajax = false;

							element // imc_forms_contenidor
								.removeAttr("data-preevalua");

							consola("Avalua dada del formulari: error des de JSON");

							/*
							imc_contenidor
								.errors({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, url: json.url });
							*/

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

				// llevem "error", per si estiguera correcte

				if (camp_id && camp_id !== "" && camp_id !== null) {

					element
						.find("*[data-id='"+camp_id+"']")
							.removeClass("imc-el-error");

				}

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
						,100
					);

				}

				// configuració

				element
					.attr("data-repinta", "s")
					.appFormsConfiguracio({ forms_json: avalua_json, desDe: "avalua" });


				// formateig de dades

				element
					.appFormsFormateig();

			},
			remarca = function(camp_id) {

				element
					.find("*[data-id="+camp_id+"]:first") // .find("div[data-id="+camp_id+"]:first")
						.addClass("imc-el-error")
						.appDestaca({ referent: element.find(".imc--form:first") });

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
								.find("input[type=date]")
									.addClass("imc-no-type-date")
									.dataCompletar()
									.datepicker({ dateFormat: "dd/mm/yy", changeMonth: true, changeYear: true });

							$.datepicker.setDefaults($.datepicker.regional[APP_IDIOMA]);

						};

					if (!arxius_data) {

						$.when(

							$.getScript(APP_FORMS_ + "forms/js/utils/jquery-maskedinput.min.js?" + APP_VERSIO)
							,$.getScript(APP_FORMS_ + "forms/js/utils/jquery-ui-1.10.3.custom.min.js?" + APP_VERSIO)

						).then(

							function( cssPas ) {

								// estils

								$("<link>")
									.attr({ rel: "stylesheet", media: "screen", href: APP_FORMS_ + "forms/css/ui-lightness/jquery-ui-1.10.3.custom.min.css?" + APP_VERSIO })
										.appendTo( imc_forms_head );

								arxius_data = true;

								// configura

								$.getScript(
									APP_FORMS_ + "forms/js/literals/jquery-imc-literals-calendari-"+APP_IDIOMA+".js?" + APP_VERSIO
									,function() {

										configura_data();

									}
								);

							}

						).fail(

							function() {

								envia_ajax = false;

								consola("Forms inicia (data): error caregant CSS i JS");

								imc_forms_body
									.appFormsErrorsGeneral({ estat: "fail" });

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

							$.getScript(APP_FORMS_ + "forms/js/utils/jquery-maskedinput.min.js?" + APP_VERSIO)

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

								imc_forms_body
									.appFormsErrorsGeneral({ estat: "fail" });

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
					//.find("*[title]")
					//	.title()
					//	.end()
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
					.on("click.appFormsAccions", "button[data-accio=edita]", edita)
					.on("click.appFormsAccions", "button[data-accio=cancela]", cancela)
					.on("click.appFormsAccions", "button[data-accio=tanca]", tanca)
					.on("click.appFormsAccions", "button[data-accio=anterior]", anterior)
					.on("click.appFormsAccions", "button[data-accio=seguent]", envia)
					.on("click.appFormsAccions", "button[data-tipus=personalizada]", personalitzada)
					.on("click.appFormsAccions", "button[data-tipus=imprimir]", imprimir)
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

				// amaga formulari

				imc_forms_contenidor
					.attr("aria-hidden", "true")
					.appFormsPopupTabula({ accio: "finalitza" });

				$("html, body")
					.removeClass("imc--sense-scroll");

				setTimeout(
					function() {

						imc_forms_finestra
							.removeClass("imc--on");

						if (APP_FORMS_URL_DINAMICA === "s") {

							document.location = "#pas/" + APP_TRAMIT_PAS_ID;

						}

					}, 300);

				var form_id = imc_forms_contenidor.attr("data-id")
					,enllas_el = $("body").find("a[data-id="+form_id+"]");

				if (enllas_el.length) {

					enllas_el
						.focus();

				}

			},
			edita = function() {

				imc_forms_missatge
					.appFormsMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtFormDinEditantTitol, alMostrar: function() { editant(); } });

			},
			editant = function() {

				// serialitza

				var valorsSerialitzats = imc_forms_finestra.appSerialitza({ verifica: false });

				// dades ajax

				var pag_url = APP_FORM_EDITAR,
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
									.appFormsMissatge({ accio: "warning", titol: data.mensaje.titulo, text: data.mensaje.texto, alAcceptar: function() { editat(json); } });

								return;

							}

							editat(json);

						} else {

							envia_ajax = false;

							consola("Formulari EDITA: error des de JSON");

							imc_forms_body
								.appFormsErrorsGeneral({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, debug: data.mensaje.debug, url: json.url });

						}

					})
					.fail(function(dades, tipus, errorThrown) {

						envia_ajax = false;

						if (tipus === "abort") {
							return false;
						}

						consola("Formulari EDITA: error des de FAIL");

						imc_forms_body
							.appFormsErrorsGeneral({ estat: "fail" });

					});

			},
			editat = function(json) {

				document
					.location = json.datos.url;

			},
			cancela = function() {

				var potGuardar = imc_forms_contenidor.attr("data-guardar");

				var text_avis = (potGuardar === "s") ? txtFormDinEixirText : txtFormDinEixirNoGuardaText
					,potGuardar_class = (potGuardar === "s") ? "imc--si-pot-guardar" : "imc--no-pot-guardar";

				imc_forms_missatge
					.removeClass("imc--si-pot-guardar imc--no-pot-guardar")
					.addClass( potGuardar_class )
					.appFormsMissatge({ accio: "formSurt", titol: txtFormDinEixirTitol, text: text_avis });

				imc_forms_missatge
					.appMissatgeFormAccions();

			},
			imprimir = function() {

				document.location = APP_FORM_IMPRIMIR;

			},
			personalitzada = function(bt) {

				var bt = $(this)
					,bt_validar = bt.attr("data-validar")
					,bt_accio = bt.attr("data-accio");

				// serialitcem

				var validem = (bt_validar === "s") ? true : false;

				var valorsSerialitzats = imc_forms_finestra.appSerialitza({ verifica: validem });

				if (!valorsSerialitzats) {

					var vesAlPrimerError = function() {

							imc_forms_finestra
								.find(".imc-el-error:first")
									.appDestaca({ referent: imc_forms_contenidor.find(".imc--contingut:first") });

							var elm_amb_error = imc_forms_finestra.find(".imc-el-error:first");

							if (elm_amb_error.attr("data-contingut") === "d") {

								elm_amb_error
									.find("a.imc-select:first")
										.focus();

							} else {

								elm_amb_error
									.find("input:first")
										.focus();

							}

							return;

						};

					var error_missatge_text = (ERROR_TEXT) ? ERROR_TEXT : txtFormDinErrorText;

					imc_forms_missatge
						.appFormsMissatge({ accio: "error", titol: txtFormDinErrorTitol, text: error_missatge_text, alTancar: function() { vesAlPrimerError(); } });

					return;

				}

				valorsSerialitzats["accion"] = bt_accio;

				imc_forms_missatge
					.appFormsMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtFormDinDesant, alMostrar: function() { enviament(valorsSerialitzats); } });


			},
			envia = function(e) {

				// desDe?

				var bt = $(this);

				var desDe = (bt.attr("data-accio") === "seguent") ? "seguent" : false;

				// serialitzem i revisem errors al form

				var valorsSerialitzats = imc_forms_finestra.appSerialitza();

				if (!valorsSerialitzats) {

					var vesAlPrimerError = function() {

							imc_forms_finestra
								.find(".imc-el-error:first")
									.appDestaca({ referent: imc_forms_contenidor.find(".imc--contingut:first") });

							var elm_amb_error = imc_forms_finestra.find(".imc-el-error:first");

							if (elm_amb_error.attr("data-contingut") === "d") {

								elm_amb_error
									.find("a.imc-select:first")
										.focus();

							} else {

								elm_amb_error
									.find("input:first")
										.focus();

							}

							return;

						};

					var error_missatge_text = (ERROR_TEXT) ? ERROR_TEXT : txtFormDinErrorText;

					imc_forms_missatge
						.appFormsMissatge({ accio: "error", titol: txtFormDinErrorTitol, text: error_missatge_text, alTancar: function() { vesAlPrimerError(); } });

					return;

				}

				var titol_txt = txtFormDinDesant;

				if (typeof desDe && desDe === "seguent") {

					titol_txt = txtFormDinPagSeguent;

				}

				imc_forms_missatge
					.appFormsMissatge({ accio: "carregant", amagaDesdeFons: false, titol: titol_txt, alMostrar: function() { enviament(valorsSerialitzats); } });

			},
			anterior = function() {

				//$("#imc-forms-contenidor")
				//	.attr("aria-hidden", "true");

				var valorsSerialitzats = imc_forms_finestra.appSerialitza();

				imc_forms_missatge
					.appFormsMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtFormDinPagAnterior, alMostrar: function() { enviament(valorsSerialitzats, "anterior"); } });

			},
			seguent = function() {

				// açò no serveix

				$("#imc-forms-contenidor")
					.attr("aria-hidden", "true");

				envia("seguent");

			},
			enviament = function(valorsSerialitzats, desDe) {

				var pag_url = (typeof desDe !== "undefined" && desDe === "anterior") ? APP_FORM_PAG_ANTERIOR : APP_FORM_GUARDA
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

							mostra(json, desDe);

						} else {

							envia_ajax = false;

							consola("FORMS formulari envia: error des de JSON");

							imc_forms_body
								.appFormsErrorsGeneral({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, debug: data.mensaje.debug, url: json.url });

						}

					})
					.fail(function(dades, tipus, errorThrown) {

						envia_ajax = false;

						if (tipus === "abort") {
							return false;
						}

						consola("FORMS formulari envia: error des de FAIL");

						imc_forms_body
							.appFormsErrorsGeneral({ estat: "fail" });

					});

			},
			mostra = function(json, desDe) {

				var validacio_estat = (json.datos.validacion && json.datos.validacion !== null) ? json.datos.validacion.estado : false
					,validacio_missatge = (json.datos.validacion && json.datos.validacion !== null) ? json.datos.validacion.mensaje : false
					,estaFinalitzat = (json.datos.finalizado === "s") ? true : false
					,hiHaPagSeguent = (json.datos.recargar === "s") ? true : false
					,camp_id = (json.datos.validacion && json.datos.validacion !== null) ? json.datos.validacion.campo : false
					,url = json.datos.url;

				// llevem "error", per si estiguera correcte

				if (camp_id && camp_id !== "" && camp_id !== null) {

					element
						.find("*[data-id='"+camp_id+"']")
							.removeClass("imc-el-error");

				}

				// si hi ha error

				if (validacio_estat === "error") {

					if (camp_id && camp_id !== "" && camp_id !== null) {

						imc_forms_missatge
							.appFormsMissatge({ accio: "error", titol: validacio_missatge, text: txtFormDinErrorText, amagaDesdeFons: false, alTancar: function() { remarca(camp_id); } });

					} else {

						imc_forms_missatge
							.appFormsMissatge({ accio: "error", titol: validacio_missatge, text: "", amagaDesdeFons: false });

					}

					// repintem tots els captcha

					element // imc_forms_finestra
						.find(".imc-element[data-tipus=captcha]")
							.appFormsCaptcha({ regenera: true });

					return;

				}

				// tot correcte!

				// es anterior???

				if (typeof desDe !== "undefined" && desDe === "anterior") {

					FORMS_JSON = json;

					actualCarregat();

					return;

				}

				// es següent o finalitzat

				/*if (hiHaPagSeguent) {

					$("#imc-forms-contenidor")
						.attr("aria-hidden", "true");

				}*/

				var accio = (estaFinalitzat) ? function() { finalitza(url) } : function() { actualCarrega() };

				if (validacio_missatge) {

					imc_forms_missatge
						.appFormsMissatge({ accio: validacio_estat, titol: validacio_missatge, text: "", amagaDesdeFons: false, alTancar: function() { accio(); } })
						.appMissatgeFormAccions();

					return;

				}

				accio();

			},
			remarca = function(camp_id) {

				element // imc_forms_finestra
					.find("*[data-id='"+camp_id+"']")
						.addClass("imc-el-error")
						.appDestaca({ referent: imc_forms_contenidor.find(".imc--contingut:first") });

			},
			finalitza = function(url) {

				document.location = url;

			},
			actualCarrega = function() {

				$.when(

					$.getJSON( APP_FORM_PAG_ACTUAL )

				).then(

					function( jsonForm ) {

						FORMS_JSON = jsonForm;

						if (FORMS_JSON.estado === "SUCCESS" || FORMS_JSON.estado === "WARNING") {

							// carregat

							actualCarregat();

							if (FORMS_JSON.estado === "WARNING") {

								imc_forms_missatge
									.appMissatge({ accio: "warning", titol: FORMS_JSON.mensaje.titulo, text: FORMS_JSON.mensaje.texto });

							}

						} else {

							consola("Formulari (carrega pàg. actual): error des de JSON");

							imc_contenidor
								.errors({ estat: FORMS_JSON.estado, titol: FORMS_JSON.mensaje.titulo, text: FORMS_JSON.mensaje.texto, url: FORMS_JSON.url });

						}

					}

				).fail(

					function(jqXHR, textStatus, errorThrown) {

						consola("Formulari (carrega pàg. actual): error fail");

						imc_contenidor
							.errors({ estat: "fail" });

					}

				);

			},
			actualCarregat = function() {

				$("#imc-forms-contenidor")
					.attr("aria-hidden", "true");

				setTimeout(
					function() {

						// FORMS afegim el html del form

						$("#imc-forms-contenidor")
							.find(".imc--contingut:first")
								.html( $(FORMS_JSON.datos.html).html() );

						// FORMS iniciem

						appFormsInicia();

						// continua

						actualMostra();

					}
					,200
				);



			},
			actualMostra = function() {

				// amaguem missatge carregant

				imc_forms_missatge
					.appFormsMissatge({ araAmaga: true });

				// mostrem formulari

				$("#imc-forms-contenidor")
					.attr("aria-hidden", "false");

				setTimeout(
					function() {

						$("html, body")
							.addClass("imc--sense-scroll");

						$("#imc-forms-contenidor")
							.appFormsPopupTabula();

					}, 300);

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


// appMissatgeFormAccions

$.fn.appMissatgeFormAccions = function(options) {
	var settings = $.extend({
		accio: false
	}, options);
	this.each(function(){
		var element = $(this), // imc_missatge
			accio = settings.accio,
			envia_ajax = false,
			inicia = function() {

				if (accio) {

					if (accio === "surt") {
						surt();
					}

					return;

				}

				element
					.off('.appMissatgeFormAccions')
					.on('click.appMissatgeFormAccions', "button[data-tipus=desa]", desaSurt)
					.on('click.appMissatgeFormAccions', "button[data-tipus=surt]", surt);

			},
			surt = function() {

				imc_forms_missatge
					.appFormsMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtFormDinCancelantTitol, alMostrar: function() { sortint(); } });

			},
			sortint = function() {

				// serialitza

				var valorsSerialitzats = imc_forms_finestra.appSerialitza({ verifica: false });

				// dades ajax

				var pag_url = APP_FORM_CANCELAR,
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
									.appFormsMissatge({ accio: "warning", titol: data.mensaje.titulo, text: data.mensaje.texto, alAcceptar: function() { editat(json); } });

								return;

							}

							sortit(json);

						} else {

							envia_ajax = false;

							consola("Formulari CANCEL·LA: error des de JSON");

							imc_forms_body
								.appFormsErrorsGeneral({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, debug: data.mensaje.debug, url: json.url });

						}

					})
					.fail(function(dades, tipus, errorThrown) {

						envia_ajax = false;

						if (tipus === "abort") {
							return false;
						}

						consola("Formulari CANCEL·LA: error des de FAIL");

						imc_forms_body
							.appFormsErrorsGeneral({ estat: "fail" });

					});

			},
			sortit = function(json) {


				document
					.location = json.datos.url;

			},
			/*surt = function() {

				element
					.off('.appMissatgeFormAccions');

				// amaga formulari

				imc_forms_contenidor
					.attr("aria-hidden", "true")
					.appFormsPopupTabula({ accio: "finalitza" });

				$("html, body")
					.removeClass("imc--sense-scroll");

				setTimeout(
					function() {

						imc_forms_finestra
							.removeClass("imc--on");

						if (APP_FORMS_URL_DINAMICA === "s") {

							document.location = "#pas/" + APP_TRAMIT_PAS_ID;

						}

					}, 300);

				// amaga missatge

				element
					.off('.appMissatgeFormAccions')
					.attr("aria-hidden", "true")
					.addClass("imc--off");

				setTimeout(
					function() {

						element
							.find(".imc--text:first")
								.removeClass("imc--on");

						element
							.removeClass("imc--on imc--off");

					}, 200);

				var form_id = imc_forms_contenidor.attr("data-id")
					,enllas_el = $("body").find("a[data-id="+form_id+"]");

				if (enllas_el.length) {

					enllas_el
						.focus();

				}

			},*/
			desaSurt = function() {

				element
					.off('.appMissatgeFormAccions');

				// serialitzem i revisem errors al form

				var valorsSerialitzats = imc_forms_finestra.appSerialitza({ verifica: false });

				imc_forms_missatge
					.appFormsMissatge({ araAmaga: true });

				setTimeout(
					function() {

						imc_forms_missatge
							.appFormsMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtFormDinDesant, alMostrar: function() { enviament(valorsSerialitzats); } });

					},
					200
				);

			},
			enviament = function(valorsSerialitzats) {

				var pag_url = APP_FORM_DESAR_SORTIR
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

							mostra(json);

						} else {

							envia_ajax = false;

							consola("FORMS desa i surt: error des de JSON");

							imc_forms_body
								.appFormsErrorsGeneral({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, debug: data.mensaje.debug, url: json.url });

						}

					})
					.fail(function(dades, tipus, errorThrown) {

						envia_ajax = false;

						if (tipus === "abort") {
							return false;
						}

						consola("FORMS desa i surt: error des de FAIL");

						imc_forms_body
							.appFormsErrorsGeneral({ estat: "fail" });

					});

			},
			mostra = function(json) {


				var url = json.datos.url;

				finalitza(url);

			},
			finalitza = function(url) {

				document.location = url;

			};

		// inicia
		inicia();

	});
	return this;
}



// appDataEspanyola

$.fn.appDataEspanyola = function(options) {

	var settings = $.extend({
		data: false
	}, options);

	var data = settings.data
		,data_esp = data;

	if (data && data.indexOf("-") !== -1) {

		var data_ = data.split("-");

		data_esp = data_[2] + "/" + data_[1] + "/" + data_[0];

	}

	return data_esp;

}



// appFormsPopupTabula

$.fn.appFormsPopupTabula = function(options) {

	var settings = $.extend({
			element: ""
			,accio: false
			,enfocaEn: false
		}, options);

	this.each(function(){
		var element = $(this)
			,accio = settings.accio
			,enfocaEn = settings.enfocaEn
			,el_num = 0
			,elems_tab = []
			,elems_tab_size = 0
			,esMissatge = false
			,inicia = function() {

				// finalitzem?

				if (accio === "finalitza") {

					element
						.off(".appFormsPopupTabula");

					return;

				}


				// iniciem vars

				el_num = 0;
				elems_tab = [];
				elems_tab_size = 0;


				// es missatge?

				esMissatge = (element.closest(".imc-forms--missatge").length) ? true : false;


				// pinta

				setTimeout(
					function() {

						pinta();

					}
					,100
				);

			},
			pinta = function() {

				// revisem si és una capa amb formulari o una capa missatge

				if (!esMissatge) {

					// és un formulari

					f_elms = element.find(".imc-element");

					f_elms
						.each(function() {


							var f_el = $(this)
								,f_el_tipus = f_el.attr("data-tipus")
								,f_el_contingut = f_el.attr("data-contingut")
								,f_el_ocult = f_el.attr("data-ocult");

							if (f_el_ocult === "s") {
								return;
							}

							if (f_el_tipus === "texto") {

								// text

								f_el
									.find("input:first")
										.attr("data-tabula", "si")
										.end()
									.find("textarea:first")
										.attr("data-tabula", "si");

							} else if (f_el_tipus === "captcha") {

								// captcha

								f_el
									.find("input, button")
										.attr("data-tabula", "si");

							} else if (f_el_tipus === "check") {

								// check únic

								f_el
									.find("input:first")
										.attr("data-tabula", "si");

							} else if (f_el_tipus === "verificacion") {

								// check únic

								f_el
									.find("label:first")
										.removeAttr("tabindex")
										.end()
									.find("input:first")
										.attr("data-tabula", "si");

							} else if (f_el_tipus === "listaElementos") {

								// llista d'elements

								f_el
									.find("button")
										.attr("data-tabula", "si")
										.end()
									.find("input[type=radio]")
										.attr("data-tabula", "si");

							} else if (f_el_contingut === "d") {

								// selector

								f_el
									.find("a.imc-select:first")
										.attr("data-tabula", "si")
										.end()
									.find("button.imc--bt-reset")
										.attr("data-tabula", "si");

							} else if (f_el_contingut === "m") {

								// llista checks

								f_el
									.find("input[type=checkbox]")
										.attr("data-tabula", "si");

							} else if (f_el_contingut === "u") {

								// llista checks

								f_el
									.find("input[type=radio]")
										.attr("data-tabula", "si");

							} else if (f_el_contingut === "a") {



								f_el
									.find("textarea:first")
										.attr("data-tabula", "si");

							}

						});

				}


				// els botons finals, tant se val que siga un missatge o un formulari

				element
					.find("button")
						.attr("data-tabula", "si");


				// activem

				activa();

			},
			activa = function() {

				elems_tab = element.find("*[data-tabula=si]:visible:not(:disabled)");
				elems_tab_size = elems_tab.length;

				if (elems_tab_size) {

					elems_tab
						.each(function(i) {

							var el = $(this);

							el
								.attr("data-tabpos", i+1);

						});

					elems_tab
						.splice(0, 0, element);

					element
						.off(".appFormsPopupTabula")
						.on("focus.appFormsPopupTabula", "*[data-tabula]", reposiciona)
						.on("focus.appFormsPopupTabula", reposiciona)
						.on("keydown.appFormsPopupTabula", tabula)
						.attr("data-tabpos", 0);

				}

				// enfoquem en algun element?

				if (enfocaEn) {

					enfocaEn
						.focus();

				} else {

					element
						.focus();

				}

			},
			reposiciona = function(e) {

				var inp_el = $(this)
					,in_tabpos = parseInt( inp_el.attr("data-tabpos"), 10);

				el_num = in_tabpos;

				consola("reposiciona: " + el_num);

			},
			tabula = function(e) {

				//consola(el_num);

				var tecla = e.keyCode
					,esShift = !!e.shiftKey;

				if ( esShift && tecla === 9) {

					e.preventDefault();

					el_num--;

					if (el_num < 0) {
						el_num = elems_tab_size;
					}

					elems_tab[el_num]
						.focus();

				} else if ( !esShift && tecla === 9){

					e.preventDefault();

					el_num++;

					if (el_num > elems_tab_size) {
						el_num = 0;
					}

					elems_tab[el_num]
						.focus();

				}

			};

		// inicia
		inicia();

	});

	return this;
}



// appFormsSelectorAjax

$.fn.appFormsSelectorAjax = function(options) {

	var settings = $.extend({
			element: ""
		}, options);

	this.each(function(){
		var element = $(this)
			,envia_ajax = false
			,valorsSerialitzats = false
			,llistat_pare = false
			,prepara = function() {

				var control_el = element.find(".imc-el-control:first");

				$("<input>")
					.attr({ type: "hidden" })
					.appendTo( control_el );

				$("<div>")
					.addClass("imc--selector-opcions-ajax")
					.attr({ tabindex: "-1", "aria-hidden": "true" })
					.appendTo( control_el );

			}
			,inicia = function(e) {

				var input_el = $(this)
					,input_val = input_el.val();

				if (input_val.length < APP_FORM_SELECTOR_DIN_NUM || input_el.prop("readonly")) {
					return;
				}

				// més de 3 caracters, numéric, lletres, o intro

				var tecla = String.fromCharCode(e.keyCode);

				var regex = new RegExp("^[a-zA-Z0-9]+$");

				if (regex.test(tecla) || e.keyCode === 13 || e.keyCode === 8 || e.keyCode === 32 ) {
					crida(input_el);
				}

			}
			,serialitze = function(e) {

				// activem nav per teclat

				var input_ = $(this)
					,control_el = input_.closest(".imc-el-control");

				control_el
					.off(".appFormsSelectorAjax")
					.on("keyup.appFormsSelectorAjax", navega);

				// serialitzem

				valorsSerialitzats = imc_forms_finestra.appSerialitza({ verifica: false });

			}
			,crida = function(input_el) {

				// carregant dades

				llistat_pare = input_el.parent().find(".imc--selector-opcions-ajax:first");

				var carregant_codi = $("<div>").addClass("imc--carregant").text( txtFormDinCercantDades );

				llistat_pare
					.html( carregant_codi )
					.attr("aria-hidden", "false");

				// serialitza, afegim dades camp

				valorsSerialitzats["idCampo"] = input_el.attr("id");
				valorsSerialitzats["textoCampo"] = input_el.val();

				// dades ajax

				var pag_url = APP_FORM_SELECTOR_AJAX
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
						dataType: "json",
						beforeSend: function(xhr) {
							xhr.setRequestHeader(headerCSRF, tokenCSRF);
						}
					})
					.done(function( data ) {

						envia_ajax = false;

						var json = data;

						if (json.estado === "SUCCESS" || json.estado === "WARNING") {

							if (json.estado === "WARNING") {

								imc_forms_missatge
									.appFormsMissatge({ accio: "warning", titol: data.mensaje.titulo, text: data.mensaje.texto, alAcceptar: function() { mostra(); } });

								return;

							}

							pinta(json);

						} else {

							envia_ajax = false;

							consola("Forms, selector dinàmic ajax: error des de JSON");

							imc_forms_body
								.appFormsErrorsGeneral({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, debug: data.mensaje.debug, url: json.url });

						}

					})
					.fail(function(dades, tipus, errorThrown) {

						envia_ajax = false;

						if (tipus === "abort") {
							return false;
						}

						consola("Forms, selector dinàmic ajax: error des de FAIL");

						imc_forms_body
							.appFormsErrorsGeneral({ estat: "fail" });

					});

			}
			,pinta = function(json) {

				// llistat_pare

				var valors_json = json.datos.valores
					,valors_size = (valors_json) ? valors_json.length : 0;


				// no hi ha resultats

				if (!valors_size) {

					var cap_resultat_codi = $("<div>").addClass("imc--sense-resultats").text( txtFormDinSenseResultats );

					llistat_pare
						.html( cap_resultat_codi );

					return;

				}


				// hi ha resultats

				var valors_ll = $("<ul>");

				llistat_pare
					.html( valors_ll );

				$(valors_json)
					.each(function() {

						var valor = this
							,valor_id = valor.valor
							,valor_text = valor.descripcion;

						var sp_ = $("<span>").text( valor_text )
							,bt_ = $("<button>").attr({ "type": "button", "data-id": valor_id, "data-text": valor_text }).html( sp_ );

						$("<li>")
							.html( bt_ )
							.appendTo( valors_ll );

					});

			}
			,selecciona = function(e) {

				var bt_sel = $(this)
					,bt_val = bt_sel.attr("data-id")
					,bt_text = bt_sel.attr("data-text");

				var control_el = bt_sel.closest(".imc-el-control");


				// revisa altura

				var capa_altura = appRevisaAlturaCamp(bt_text, control_el);


				// pintem

				control_el
					.find("input[type=hidden]:first")
						.val( bt_val )
						.end()
					.find("input[type=text]:first, textarea")
						.val( bt_text )
						.prop("readonly", true);

				$("<button>")
					.attr({ type: "button", "data-accio": "seleccio-elimina", title: txtFormDinEliminaSeleccio, "data-tabula": "si" })
						.html( $("<span>").text( txtFormDinEliminaSeleccio ) )
						.appendTo( control_el );

				llistat_pare
					.attr("aria-hidden", "true");

				setTimeout(
					function() {

						llistat_pare
							.html( "" );

					},50
				);

				//capa__
				//	.remove();


				// tabulem de nou

				$("#imc-forms-contenidor")
					.appFormsPopupTabula();

				// enfoquem al control dinàmic

				setTimeout(
					function() {

						control_el
							.find("input[type=text]:first, textarea")
								.focus();

					},100
				);

			}
			,navega = function(e) {

				e.preventDefault();

				if (e.keyCode === 40) { // avall

					var item_selec = llistat_pare.find("button.imc--seleccionada")
						,llistat_seg = item_selec.parent().next();

					if (item_selec.length && llistat_seg.length) {

						llistat_pare
							.find("button.imc--seleccionada:first")
								.removeClass("imc--seleccionada");

						llistat_seg
							.find("button:first")
								.addClass("imc--seleccionada")
								.focus();

					} else if (!item_selec.length || (item_selec.length && !llistat_seg.length)) {

						llistat_pare
							.find("button.imc--seleccionada:first")
								.removeClass("imc--seleccionada");

						llistat_pare
							.find("button:first")
								.addClass("imc--seleccionada")
								.focus();

					}

					e.preventDefault();
					return false;

				} else if (e.keyCode === 38) { // amunt

					var item_selec = llistat_pare.find("button.imc--seleccionada")
						,llistat_seg = item_selec.parent().prev();

					if (item_selec.length && llistat_seg.length) {

						llistat_pare
							.find("button.imc--seleccionada:first")
								.removeClass("imc--seleccionada");

						llistat_seg
							.find("button:first")
								.addClass("imc--seleccionada")
								.focus();

					} else if (!item_selec.length || (item_selec.length && !llistat_seg.length)) {

						llistat_pare
							.find("button.imc--seleccionada:first")
								.removeClass("imc--seleccionada");

						llistat_pare
							.find("button:last")
								.addClass("imc--seleccionada")
								.focus();

					}

					e.preventDefault();
					return false;

				} else if (e.keyCode === 27) { // esc

					reseteja();

				}

			}
			,revisa = function() {

				setTimeout(
					function() {

						var obj_focusat = $(document.activeElement);

						if (!obj_focusat.closest("div[data-contingut=a]").length) {

							setTimeout(
								function() {

									reseteja();

								}, 500
							);

						}

					}, 100

				);

			}
			,reseteja = function(e) {

				if (typeof llistat_pare === "boolean") {
					return;
				}

				llistat_pare
					.html( "" )
					.attr("aria-hidden", "true");

			}
			,elimina = function(e) {

				var bt = $(this)
					,control_el = bt.parent()
					,esLectura = (bt.closest(".imc-element").attr("data-lectura") === "s") ? true : false;

				if (esLectura) {
					return;
				}

				control_el
					.find("input[type=hidden]:first")
						.val( "" )
						.end()
					.find("input[type=text]:first, textarea")
						.removeAttr("style")
						.val( "" )
						.prop("readonly", false)
						.focus();

				setTimeout(
					function() {

						control_el
							.find("button[data-accio=seleccio-elimina]")
								.remove();

					}, 50

				);

			};

		// prepara

		prepara();

		// events

		element
			.off(".appFormsSelectorAjax")
			.on("keyup.appFormsSelectorAjax", "input[type=text], textarea", inicia)
			.on("focus.appFormsSelectorAjax", "input[type=text], textarea", serialitze)
			.on("blur.appFormsSelectorAjax", "input[type=text], textarea, .imc--selector-opcions-ajax li button", revisa)
			.on("click.appFormsSelectorAjax", ".imc--selector-opcions-ajax li button", selecciona)
			.on("click.appFormsSelectorAjax", "button[data-accio=seleccio-elimina]", elimina);

	});

	return this;
}

function appRevisaAlturaCamp(bt_text, control_el) {

	var capa__ = $("<div>")
					.addClass("imc--selector-ajax-altura")
					.text( bt_text )
					.appendTo( control_el );

	var capa_altura = capa__.outerHeight(true);

	control_el
		.find("textarea")
			.css("height", capa_altura+"px");

	control_el
		.find(".imc--selector-ajax-altura")
			.remove();

	return capa_altura;

}


// COMUNS


// title
$.fn.title = function(options) {
	var settings = $.extend({
		temps: 100,
		posicio: "esquerre", // esquerre/dreta
		contenidor: $("body")
	}, options);
	this.each(function(){
		var element = $(this),
		title_text = element.attr("title") || "",
		onEnter = function() {
			if ($("#title").length) {
				$("#title").remove();
			}
			$("body").append("<div id=\"title\"><p>" + element.data("title") + "</p><div class=\"cueta-baix\">&nbsp;</div></div>");
			var contenidor_H = settings.contenidor.outerHeight(),
				contenidor_W = settings.contenidor.outerWidth(),
				element_T = element.offset().top,
				element_L = element.offset().left,
				element_H = element.outerHeight(),
				element_W = element.outerWidth(),
				title_elm = $("#title"),
				title_H = title_elm.outerHeight(),
				title_W = title_elm.outerWidth(),
				title_Padding = parseInt(title_elm.find("p").css("paddingLeft"), 10);
				
			if (settings.posicio === "dreta") {
				element_L = element_L + element_W - 15;
			}
			if (element_L+title_W+10 > contenidor_W) {
				element_L = element_L -title_W+element_W;
				title_elm.find("div").css("width", title_W - ((title_Padding*2) - (title_Padding/2)));
			} else {
				title_elm.find("div").removeAttr("style");
			}
			title_elm.css({ top: element_T + "px", left: element_L + "px" });
			element.attr("title", "");
			element_T = element_T - title_H;
			if (element_T < 5) {
				title_elm.html("<div class=\"cueta-dalt\">&nbsp;</div><p>" + title_text + "</p>");
				element_T = element.offset().top;
				element_T = element_T + element_H + 5;
			}
			title_elm.css({ "top": (element_T-2) + "px" });
		},
		onLeave = function() {
			$("#title").hide().remove();
			element.attr("title", element.data("title"));
		};
		if (title_text !== "") {
			element.data("title", title_text).off('.title').on('mouseenter.title', onEnter).on('mouseleave.title', onLeave).on('focus.title', onEnter).on('blur.title', onLeave);
		}
	});
	return this;
};
// /title


// dataCompletar
var AVUI_DIA,
	AVUI_MES,
	AVUI_ANY;

$.fn.dataCompletar = function(options) {
	var settings = $.extend({
		contenidor: $("body"),
		separador: "/"
	}, options);
	this.each(function(){
		var element = $(this),
		separador = settings.separador,
		onBlur = function() {
			var data_val = element.val();
			// completar
			if (data_val.indexOf("__" + separador + "____") !== -1) {
				var data_val_dia = data_val.substr(0,2);
				element.val(data_val_dia + separador + AVUI_MES + separador + AVUI_ANY);
			} else if (data_val.indexOf("____") !== -1) {
				var data_val_dia_i_mes = data_val.substr(0,5);
				element.val(data_val_dia_i_mes + separador + AVUI_ANY);
			}
			// verificar
			var data_val = element.val(),
				data = data_val.split(separador),
				dataDia = parseInt(data[0], 10),
				dataMes = parseInt(data[1], 10),
				dataAnyo = parseInt(data[2], 10),
				data_error = false;
			
			if (dataAnyo == 0) {
				data_error = true;
			}
			if (dataMes > 12 || dataMes == 0) {
				data_error = true;
			}
			if (dataDia == 0 || (dataMes == 4 || dataMes == 6 || dataMes == 9 || dataMes == 11) && dataDia > 30) {
				data_error = true;
			} else if ((dataMes == 1 || dataMes == 3 || dataMes == 5 || dataMes == 7 || dataMes == 8 || dataMes == 10 || dataMes == 12) && dataDia > 31) {
				data_error = true;
			} else if (dataMes == 2) {
				var febreroDias = (dataAnyo % 4 != 0) ? 28 : 29;
				if (dataDia > febreroDias) {
					data_error = true;
				}
			}
			if (data_error) {
				element.closest("div").find(".imc-input-error").slideDown(200);
				element.val(AVUI_DIA + separador + AVUI_MES + separador + AVUI_ANY);
			} else {
				element.closest("div").find(".imc-input-error").slideUp(200);
			}
		};
		element.off('.dataCompletar').on('blur.dataCompletar', onBlur).mask("99" + separador + "99" + separador + "9999");
	});
	return this;
}
// /dataCompletar


// horaCompletar
$.fn.horaCompletar = function(options) {
	var settings = $.extend({
			contenidor: $("body"),
			separador: ":"
	}, options);
	this.each(function(){
		var element = $(this),
				separador = settings.separador,
				onBlur = function() {
					var hora_val = element.val(),
							hora = hora_val.split(separador),
							horaHora = parseInt(hora[0], 10),
							horaMinuts = parseInt(hora[1], 10);
					// verificar
					horaHora = (horaHora > 23) ? "23" : (isNaN(horaHora) || horaHora === 0) ? "00" : (horaHora <= 9) ? "0"+horaHora : horaHora;
					horaMinuts = (horaMinuts > 59) ? "59" : (isNaN(horaMinuts) || horaMinuts === 0) ? "00" : (horaMinuts <= 9) ? "0"+horaMinuts : horaMinuts;
					// pintar
					element.val(horaHora + separador + horaMinuts);
				};
		// events
		element.off('.horaCompletar').on('blur.horaCompletar', onBlur).mask("99"+separador+"99");
	});
	return this;
}
// /horaCompletar


// toEm
$.fn.toEm = function(options) {
	var settings = $.extend({
		etiqueta: 'body'
	}, options);
	var elmValor = parseInt(this[0],10);
	var capaTest = $('<div id="imc-toEm" style="display: none; font-size: 1.2em; font-family: arial, helvetica; margin: 0; padding:0; height: auto; line-height: 1; border:0;"> </div>').appendTo(settings.etiqueta);
	var capaValor = parseInt($("#imc-toEm").innerHeight(),10); 
	capaTest.remove();
	return (elmValor / capaValor);// + 'em';
};
// /toEm


// submenu
$.fn.submenu = function(options) {
	this.each(function(){
		var settings = $.extend({
			submenu: $(this).find(".imc-select-submenu:first"),
			aliniacio: "esquerre",
			posicio : "position",
			amplariaIgual: true,
			tancarAlClicarDins: false
		}, options);
		var element = $(this),
		submenu_a,
		submenu_elm,
		tancarAlClicarDins = settings.tancarAlClicarDins,
		ultima_opcio = false,
		onClick = function() {

			if (element.find("a.imc-select:first").is(".imc-select-deshabilitat, .imc-select-lectura")) {
				return;
			}

			// mostrem?

			if (!element.hasClass("imc-select-on") && !element.find("a.imc-select:first").is(".imc-select-deshabilitat, .imc-select-lectura")) {

				submenu_a = element.find("a.imc-select:first");
				submenu_elm = element.find(".imc-select-submenu:first");

				// on està el submenu?

				var esLlistaElements = element.closest(".imc-forms--taula").length ? true : false;

				if (esLlistaElements) {

					var form_llista_contenidor = element.closest(".imc--form")
						,form_llista_contenidor_H = form_llista_contenidor.height();

					var form_submenu_a = element.find("a.imc-select:first")
						,form_submenu_pos_T = form_submenu_a.parent().position().top
						,form_submenu_H = form_submenu_a.outerHeight();

					var maxHeight = form_llista_contenidor_H - (form_submenu_pos_T + form_submenu_H) - 10;

					submenu_elm
						.find("ul:first")
							.css({ "max-height": maxHeight+"px" });

				}

				// continuem i mostrem

				element
					.addClass("imc-select-on imc-select-obrint");

				submenu_elm
					.addClass("imc-submenu-on");

				var window_H = imc_forms_contingut.height(),
					window_T = imc_forms_contingut.scrollTop(),
					element_T = (settings.posicio == "position") ? submenu_a.position().top : submenu_a.offset().top,
					element_L = (settings.posicio == "position") ? submenu_a.position().left : submenu_a.offset().left,
					element_H = submenu_a.outerHeight(),
					element_W = submenu_a.outerWidth(),
					submenu_W = submenu_elm.outerWidth(),
					submenu_H = submenu_elm.outerHeight(),
					submenu_L = (settings.aliniacio == "esquerre") ? element_L : element_L-submenu_W+element_W,
					submenu_T = element_H;

				if (settings.amplariaIgual) {

					submenu_elm
						.css({ width: element_W+"px" });

				}
				
				if ((element_T+element_H+submenu_H) > (window_H+window_T)) {

					submenu_T = element_T-submenu_H+window_T;

					submenu_elm
						.addClass("imc-opcions-superior");

				} else {

					submenu_elm
						.removeClass("imc-opcions-superior");

				}

				// està en la llista d'elements?

				if (esLlistaElements) {

					

				}

				// mostrem
					
				submenu_elm
					.css({ top: submenu_T+"px", left: submenu_L+"px" })
					.attr("tabindex", "-1")
					.show(function() {

						$(document)
							.on("click.submenu", onWindow)
							.on("keydown.submenu", onKeyDown);

						submenu_elm
							.focus();

						element
							.removeClass("imc-select-obrint");

						if (esLlistaElements) {

							var sub_H = submenu_elm.outerHeight();

							if (form_submenu_pos_T+sub_H > form_llista_contenidor_H) {

								var top_sub = (form_submenu_pos_T+form_submenu_H+sub_H) - form_llista_contenidor_H;

								submenu_elm
									.css({ top: (form_submenu_H-top_sub-10)+"px" });

							}
								
						}

					});

			} else {

				if (!element.hasClass("imc-select-obrint")) {
					onWindow();
				}

			}
		},
		onWindow = function(e) {
			var tanca = true;
			if (!tancarAlClicarDins) {
				var elm = (typeof e === "undefined") ? element : $(e.target);
				tanca = (elm.closest(".imc-submenu-on").length === 1) ? false : true;
			} else {
				var elm = (typeof e === "undefined") ? element : $(e.target);
				if (elm.closest(".imc-index-alfabet").length) {
					tanca = false;
				}
			}
			if (tanca) {
				element.removeClass("imc-select-on");
				submenu_elm.removeClass("imc-submenu-on");
				$(document).off("click.submenu").off("keydown.submenu");
				
				submenu_elm.find("a").removeClass("hover");
				
				if (!Modernizr.boxshadow) {
					submenu_elm.hide();
				} else {
					submenu_elm.slideUp(100);
				}
			}
		},
		onKeyDown = function(e) {
			
			if (e.keyCode === 38 || e.keyCode === 40) { // amunt

				var submenu_items = submenu_elm.find("li:not(.imc-select-seleccionat) a"),
					submenu_item_marcat = submenu_elm.find("li:not(.imc-select-seleccionat) a.hover").length;
				
			}

			if (e.keyCode === 38) { // amunt

				if (!submenu_item_marcat) {
					submenu_items.eq( submenu_items.length - 1 ).addClass("hover").focus();
				} else {
					var submenu_item_marcat = submenu_elm.find("li:not(.imc-select-seleccionat) a.hover:first"),
						marcat_index_p = submenu_items.index(submenu_item_marcat),
						marcat_index = (marcat_index < 0) ? submenu_items.length - 1 : marcat_index_p - 1;
					submenu_items.removeClass("hover").eq( marcat_index ).addClass("hover").focus();
				}
				
				e.preventDefault();
				return false;

			} else if (e.keyCode === 40) { // avall

				if (!submenu_item_marcat) {
					submenu_items.eq( 0 ).addClass("hover").focus();
				} else {
					var submenu_item_marcat = submenu_elm.find("li:not(.imc-select-seleccionat) a.hover:first"),
						marcat_index = submenu_items.index(submenu_item_marcat),
						marcat_index = (marcat_index >= submenu_items.length-1) ? 0 : marcat_index + 1;
					submenu_items.removeClass("hover").eq( marcat_index ).addClass("hover").focus();
				}
				e.preventDefault();
				return false;

			}

		},
		onFocus = function() {
			
			$(document)
				.off(".onSelectFletxa")
				.on("keyup.onSelectFletxa", onSelectFletxa);
				
			element
				.off(".onSelectLletra")
				.on("keyup.onSelectLletra", onSelectLletra);

		},
		onSelectFletxa = function(e) {

			if (e.keyCode === 40) { // amunt
			
				var elm = $(e.target);
				
				if (elm.is("A") && elm.hasClass("imc-select") && elm.parent().hasClass("imc-opcions")) {
					onClick();
					e.preventDefault();
					return false;
				}
			
			}
			
		},
		onSelectLletra = function(e) {

			console.log("va: ")
			
			if (element.find(".imc-select-submenu:first").hasClass("imc-submenu-on")) {
				if (!element.hasClass("imc-el-index-marcant")) {
					
					element.addClass("imc-el-index-marcant");
					
					//console.log( String.fromCharCode(e.keyCode) );
					situa( String.fromCharCode(e.keyCode) );

				}
			}
			
		},
		situa = function(elm_text) {
		
			var opcio_llista_trobat = false,
				opcio_llista_posicio = 0,
				elm_trobat = false,
				elm_trobat_mateixa_lletra = 0,
				elm_trobats = [];

			var el_opcions_llista = element.find(".imc-select-submenu ul:first")
				,el_opcions = el_opcions_llista.find("li");
			
			el_opcions
				.each(function() {

					var opcio = $(this).find("a"),
						opcio_text = opcio.text().toUpperCase(),
						opcio_inicial = normalize( opcio_text.charAt(0) );

					if (opcio.attr("data-value") !== "") {

						if (elm_text === opcio_inicial) {
							elm_trobat_mateixa_lletra++;
							elm_trobats.push( opcio );
						}
						if (!opcio_llista_trobat && elm_text === opcio_inicial) {
							opcio_llista_trobat = true;
							elm_trobat = opcio;
						}
						if (!opcio_llista_trobat) {
							opcio_llista_posicio += opcio.outerHeight() + 2;
						}

					}

				});

			if (elm_trobat) {
				
				if (elm_trobats.length > 1) {
					
					if (elm_text != ultima_opcio) {
						el_opcions_llista.find(".imc-alfabet-focus:first").removeClass("imc-alfabet-focus");
					}
					
					var elm_trobats_size = elm_trobats.length,
						elm_tro_posicio = 0,
						elm_tro_trobat = false,
						opcio_llista_posicio_suma = 0;
					
					for (var i=0; i<elm_trobats_size; i++) {
						if (!elm_tro_trobat) {
							opcio_llista_posicio_suma += $(elm_trobats[i]).outerHeight() + 2;
						}
						if ($(elm_trobats[i]).hasClass("imc-alfabet-focus")) {
							elm_tro_posicio = i+1;
							$(elm_trobats[i]).removeClass("imc-alfabet-focus");
							elm_tro_trobat = true;
							elm_tro_trobat_enCap = true;
						}
					}
					
					if (elm_tro_posicio >= elm_trobats_size) {
						elm_tro_posicio = 0;
					}
					
					if (elm_tro_trobat && elm_tro_posicio !== 0) {
						opcio_llista_posicio += opcio_llista_posicio_suma;
					}
					
					elm_trobat = $(elm_trobats[elm_tro_posicio]);
											
				}
				
				
				el_opcions_llista
					.animate(
						{
							scrollTop: opcio_llista_posicio+"px"
						}
						,200
						, function() {
							elm_trobat.addClass("imc-alfabet-marca");
							setTimeout(
								function() {
									elm_trobat.removeClass("imc-alfabet-marca").addClass("imc-alfabet-focus").focus();
									ultima_opcio = elm_text;
									element.removeClass("imc-el-index-marcant");
								},400
							);
						}
					);
					
			} else {
				
				element.removeClass("imc-el-index-marcant");
				
			}
			
		};

		// events

		element
			.off('.submenu')
			.on('click.submenu', 'a.imc-select', onClick)
			.on('focus.submenu', 'a.imc-select', onFocus);

	});
	return this;
};
// /submenu


// selector
$.fn.selectorIMC = function(options) {
	var settings = $.extend({
		tipus: "unic", // unic, multiple
		alAcabar: function() { }
	}, options);
	this.each(function(){
		var element = $(this),
			element_selector,
			element_a,
			seleccionat_input,
			seleccionat_span,
			opcions_elm,
			onClick = function(e) {
				
				var elm = $(this);
				
				if (elm.closest("ul").length && !elm.parent().hasClass("imc-select-seleccionat")) {
					
					element_selector = element.find("div.imc-select:first");
					element_a = element_selector.find("a:first");
					seleccionat_input = element_selector.find("input:first");
					seleccionat_span = element_selector.find("span:first");
					opcions_elm = element_selector.find("ul:first");
					
					seleccionat_input.val(elm.attr("data-value"));
					element_a.html( $("<span>").text( elm.text() ) ).focus();
					
					opcions_elm.find("li").removeClass("imc-select-seleccionat");
					elm.parent().addClass("imc-select-seleccionat");
					
					settings.alAcabar();
					
				}
				
			},
			onMouseEnter = function() {
				$(this).addClass("hover");
			},
			onMouseLeave = function() {
				$(this).removeClass("hover");
			},
			seleccionat = function() {
				element_selector = element.find("div.imc-select:first");
				seleccionat_input = element_selector.find("input:first");
				opcions_elm = element_selector.find("ul:first");
				opcions_elm.find("a[data-value=\"" +seleccionat_input.val()+ "\"]").parent().addClass("imc-select-seleccionat");
			};
			
		seleccionat();
		
		element
			.submenu({ posicio: "position", amplariaIgual: true, tancarAlClicarDins: true });
		
		if(typeof Modernizr !== "undefined" && !Modernizr.cssanimations) {
			element
				.off('.selector')
				.on('click.selector', '.imc-select-submenu a', onClick)
				.on('mouseenter.selector', '.imc-select-submenu a', onMouseEnter)
				.on('mouseleave.selector', '.imc-select-submenu a', onMouseLeave);
		} else {
			element.off('.selector')
				.on('click.selector', '.imc-select-submenu a', onClick);
		}
		
	});
	return this;
}
// /selector


function inputSelectSeleccionaValor(options) {
	var settings = $.extend({
			element: "",
			valor: ""
		}, options),
		element = settings.element,
		valor = settings.valor;
		
	if (element !== "" && valor !== "") {
		var text_nou, valor_nou;
		element
			.find(".imc-select-seleccionat:first").removeClass("imc-select-seleccionat").end()
			.find("ul a").each(function() {
				var elm_a = $(this),
					elm_a_valor = elm_a.attr("data-value");
					
				if (elm_a_valor === valor) {
					text_nou = elm_a.text();
					valor_nou = elm_a_valor;
					elm_a.parent().addClass("imc-select-seleccionat")
				}
			}).end()
			.find("a:first span").text(text_nou).end()
			.find("input:first").val(valor_nou);
	}
}


// inputSelectForm
$.fn.inputSelectForm = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			radio_elms = element.find("input[type=radio]"),
			button_elm = element.find("button"),
			onClick = function() {
				
				var radio_val = element.find("input.imc-dias").val(),
					select_elm = element.closest(".imc-select-form");
				
				select_elm
					.find("span:first").text( radio_val + " days" ).end()
					.find("input:first").val( radio_val ).end()
					.find("ul").removeAttr("class");
				
			},
			onChange = function() {
				
				var radio_elm = element.find("input[type=radio]:checked"),
					radio_val = radio_elm.val(),
					select_elm = element.closest(".imc-select-form");
				
				if (radio_val === "o") {
					
					element
						.find(".imc-set-duration").removeClass("imc-set-duration-on");
					select_elm
						.find("span:first").text( radio_elm.parent().text() ).end()
						.find("input:first").val( radio_val );
						
				} else {
					element.find(".imc-set-duration").addClass("imc-set-duration-on");
				}
				
			};
		radio_elms.off('.inputSelectForm').on('change.inputSelectForm', onChange);
		button_elm.off('.inputSelectForm').on('click.inputSelectForm', onClick);
		
	});
	return this;
}
// /inputSelectForm


// inputSelectBuscador
$.fn.inputSelectBuscador = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			seleccionat_input = element.find("input:first"),
			seleccionat_span = element.find("span:first"),
			opcions_elm = element.find("div:first"),
			cercador_elm = element.find(".imc-select-busca:first"),
			onClick = function(e) {
				var elm = $(e.target);
				
				if (elm.is("A") || (elm.is("SPAN") && elm.parent().is("A")) || (elm.is("STRONG") && elm.parent().is("A"))) {
					
					var elm_a = (elm.is("A")) ? elm : elm.parent();
					
					seleccionat_input.val(elm_a.attr("data-value"));
					seleccionat_span.text(elm_a.attr("data-text") + " " + elm_a.find("span:last").text());
					
					var att_class = (elm_a.attr("class")) ? elm_a.attr("class") : "";
					seleccionat_span.attr("class", att_class);

					opcions_elm.find("li").removeClass("imc-select-seleccionat");
					elm_a.parent().addClass("imc-select-seleccionat");
					
					elm_a
						.closest(".imc-submenu-on").removeClass("imc-submenu-on")
						.parent().find(".on:first").removeClass("on");
						
					var td_cantidad = elm_a.closest(".imc-tr").find(".imc-cantidad:first");
					
					td_cantidad
						.find("input:first").val( elm_a.attr("data-quantity") ).end()
						.removeClass("imc-cantidad-off");
					
					inputSelectSeleccionaValor({ element: td_cantidad.find(".imc-select:first"), valor: elm_a.attr("data-type") });
					
					elm_a.closest(".imc-tr").find(".imc-opcions-off").removeClass("imc-opcions-off");
				}
				
			},
			onMouseEnter = function() {
				$(this).addClass("hover");
			},
			onMouseLeave = function() {
				$(this).removeClass("hover");
			},
			onKeyUp = function() {
				
				var input_elm = $(this),
					input_val = input_elm.val();
				
				if (input_val.length >= 3) {
					filtra(input_val);	
				} else {
					mostraTot();	
				}
				
			},
			filtra = function(input_val) {
				
				opcions_elm.find("a").each(function(i) {
					var elm = $(this),
						elm_data_text = elm.attr("data-text"),
						elm_strong = elm.find("strong");
					
					if (elm_data_text.indexOf(input_val) !== -1) {
						elm_data_text = elm_data_text.replace(input_val, "<span class=\"imc-resaltado\">" + input_val + "</span>");
						elm_strong.html(elm_data_text);
					} else {
						elm.parent().addClass("imc-invisible");	
					}
					
				});
				
			},
			mostraTot = function() {
				opcions_elm.find("li").each(function() {
					var elm = $(this),
						elm_a = elm.find("a"),
						elm_strong = elm_a.find("strong");
					
					elm.removeClass("imc-invisible");
					elm_strong.text( elm_a.attr("data-text") );
					
				});
			};
		
		opcions_elm.off('.inputSelectBuscador').on('click.inputSelectBuscador', onClick);
		cercador_elm.off('.inputSelectBuscador').on('keyup.inputSelectBuscador', onKeyUp);
		if(typeof Modernizr !== "undefined" && !Modernizr.cssanimations) {
			opcions_elm.find("a").off('.inputSelectBuscador').on('mouseenter.inputSelectBuscador', onMouseEnter).on('mouseleave.inputSelectBuscador', onMouseLeave);
		}
		
	});
	return this;
}
// /inputSelectBuscador






// inputSelectAjax
$.fn.inputSelectAjax = function(options) {
	var settings = $.extend({
		element: "",
		aliniacio: "esquerre",
		amplariaIgual: true
	}, options);
	this.each(function(){
		var element = $(this),
			aliniacio = settings.aliniacio,
			amplariaIgual = settings.amplariaIgual,
			control_el = element.find(".imc-el-control:first"),
			input_el = element.find("input:first"),
			idioma = $("html").attr("lang"),
			cercant = false,
			resultats_el = false,
			seleccionant = false,
			pag_url = false,
			inicia = function() {

				input_el
					.off('.inputSelectAjax')
					.on('keyup.inputSelectAjax', onKeyUp)
					.on('click.inputSelectAjax', revisa)
					.on('blur.inputSelectAjax', amaga);

				pag_url = element.attr("data-url");

			},
			onKeyUp = function(e) {

				// verifica

				if (e.keyCode === 38 || e.keyCode === 40 || e.keyCode === 37 || e.keyCode === 39 || e.keyCode === 13 || e.keyCode === 9) {

					if (e.keyCode === 38 || e.keyCode === 40) {

						navega( e.keyCode );

					}

					if (e.keyCode === 13) {

						if ( resultats_el.is(":hidden") ) {

							revisa();

						} else {

							seleccionaAmbIntro();

						}

					}

					return;
				}
				
				var input_el_val = input_el.val();
				
				if (input_el_val.length >= 3) {
					cerca(input_el_val);	
				} else {
					elimina();
				}
				
			},
			elimina = function() {

				control_el
					.find(".imc-text-selector")
						.remove();

			},
			amaga = function() {

				setTimeout(
					function() {

						if (!seleccionant) {

							control_el
								.find(".imc-text-selector")
									.hide();

						}

						seleccionant = false;

					},100
				);

			},
			revisa = function() {

				var llistat_selector = control_el.find(".imc-text-selector:first");

				if (llistat_selector) {

					posiciona();

					llistat_selector
						.show();

				}

			},
			cerca = function( input_el_val ) {

				// prepara

				var llistat_selector = control_el.find(".imc-text-selector:first"),
					creaResultats = false;

				if (llistat_selector) {

					var data_cerca = llistat_selector.attr("data-cerca");

					if (data_cerca !== input_el_val) {

						elimina();

						creaResultats = true;

					} else {

						return;

					}


				} else {

					creaResultats = true;

				}

				if (creaResultats) {

					var cercant_el = $("<span>").addClass("imc-carregant").text( txtFormDinCercantDades );

					resultats_el = $("<div>").attr("data-cerca", input_el_val).addClass("imc-text-selector").html( cercant_el ).appendTo( control_el );

				}

				// posiciona

				posiciona();

				// cerca
				
				var pag_data = { idioma: idioma, valor: input_el_val };

				if (cercant) {

					cercant
						.abort();

				}

				cercant = $.ajax({
						type: "POST",
						url: pag_url,
						data: pag_data
					})
					.done(function( json ) {
						
						if (json.e === "error") {

							error( json );

						} else {

							pinta( json );

						}

					})
					.fail(function(dades, tipus, errorThrown) {
						
						if (tipus !== "abort") {
							
							error(dades, tipus, errorThrown);

						}

					});
			
			},
			pinta = function( json ) {

				var json_dades = json.d.i,
					json_dades_length = json_dades.length;

				if (json_dades_length === 0) {

					var sense_resultats = $("<strong>").addClass("imc-sense-resultats").text( txtFormDinSenseResultats );

					resultats_el
						.html( sense_resultats );

					resultats_el
						.closest(".imc-element")
							.addClass("imc-el-error");

				} else {

					var resultats_ul = $("<ul>").addClass("imc--resultats");

					$(json_dades)
						.each(function() {

							var el = this,
								el_span = $("<span>").text( el[1] ),
								el_a = $("<a>").attr("data-id", el[0]).html( el_span ).on("click.inputSelectAjax", selecciona),
								el_item = $("<li>").html( el_a ).appendTo( resultats_ul );

						});

					resultats_el
						.html( resultats_ul );

					resultats_el
						.find("a:first")
							.addClass("imc--seleccionat");

					posiciona();

				}

			},
			posiciona = function() {

				if (!resultats_el) {
					return;
				}

				var dinsIframe = window.top !== window.self;

				var window_H = (!dinsIframe) ? $(window).height() : $("#imc-formulari-iframe", top.document).height(),
					window_T = (!dinsIframe) ? $(window).scrollTop() : $(document).scrollTop(),
					control_T = input_el.offset().top,
					control_L = input_el.offset().left,
					control_H = input_el.outerHeight(),
					control_W = input_el.outerWidth(),
					opcions_W = resultats_el.outerWidth(),
					opcions_H = resultats_el.outerHeight(),
					opcions_L = (settings.aliniacio === "esquerre") ? control_L : control_L-opcions_W+control_W,
					opcions_T = control_T+control_H;

				if (settings.amplariaIgual) {
					resultats_el.css({ width: control_W+"px" });
				}
				
				console.log((control_T+ " + " +control_H+ " + " +opcions_H) + " > " + (window_H+window_T));

				if ((control_T+control_H+opcions_H) > (window_H+window_T)) {

					opcions_T = control_T-opcions_H;

					resultats_el
						.addClass("imc--superior");

				} else {

					resultats_el
						.removeClass("imc--superior");

				}

				resultats_el
					.css({ top: opcions_T+"px", left: opcions_L+"px" });

				if (amplariaIgual) {

					resultats_el
						.css({ width: control_W+"px" });

				}

			},
			selecciona = function() {

				seleccionant = true;

				var bt = $(this),
					bt_text = bt.find("span").text();

				resultats_el
					.find(".imc--seleccionat:first")
						.removeClass("imc--seleccionat")
						.end()
					.attr("data-cerca", bt_text);

				input_el
					.val( bt_text )
					.focus();

				bt
					.addClass("imc--seleccionat");

				resultats_el
					.closest(".imc-element")
						.removeClass("imc-el-error");

			},
			seleccionaAmbIntro = function() {

				var bt = resultats_el.find(".imc--seleccionat:first");

				if (bt.length) {

					var bt_text = bt.find("span").text();

					resultats_el
						.find(".imc--seleccionat:first")
							.removeClass("imc--seleccionat")
							.end()
						.attr("data-cerca", bt_text);

					input_el
						.val( bt_text )
						.focus();

					bt
						.addClass("imc--seleccionat");

					resultats_el
						.closest(".imc-element")
							.removeClass("imc-el-error");

				}

			},
			navega = function( codi ) {

				var opcions_elm = resultats_el.find("a"),
					opcions_elm_length = opcions_elm.length,
					opcio_selec = resultats_el.find(".imc--seleccionat:first"),
					opcio_selec_index = opcions_elm.index( opcio_selec );

				opcio_selec
					.removeClass("imc--seleccionat");

				if (codi === 38) {

					var posicio_eq = (opcio_selec_index === 0) ? opcions_elm_length - 1 : opcio_selec_index - 1;

				}

				if (codi === 40) {

					var posicio_eq = (opcio_selec_index === opcions_elm_length - 1) ? 0 : opcio_selec_index + 1;

				}

				opcions_elm
					.eq( posicio_eq )
						.addClass("imc--seleccionat");

				// scroll

				navegaScroll( posicio_eq, opcions_elm_length - 1 );

			},
			navegaScroll = function( posicio_eq, opcions_elm_length ) {

				var opcio_selec = resultats_el.find(".imc--seleccionat:first"),
					opcio_selec_T = opcio_selec.position().top,
					opcio_selec_H = opcio_selec.outerHeight(),
					resultats_el_H = resultats_el.outerHeight(),
					scroll_T = resultats_el.scrollTop();

				if (posicio_eq === 0) {

					resultats_el
						.scrollTop( 0 );

					return;

				}

				if (posicio_eq === opcions_elm_length) {

					resultats_el
						.scrollTop( resultats_el_H );

					return;

				}

				if (opcio_selec_T + opcio_selec_H - scroll_T > resultats_el_H) {

					resultats_el
						.scrollTop( scroll_T + opcio_selec_H );

				}

				if (opcio_selec_T + opcio_selec_H < scroll_T) {

					resultats_el
						.scrollTop( scroll_T + opcio_selec_T );

				}

			},
			error = function(dades, tipus, errorThrown) {

				var error_txt = (dades.e === "error") ? $("<span>").text( dades.m[0] ) : $("<span>").text( txtFormDinErrorAjax );

				resultats_el
					.html( error_txt );

				resultats_el
					.closest(".imc-element")
						.addClass("imc-el-error");

			};
		
		// inicia
		inicia();
		
	});
	return this;
}
// /inputSelectAjax









// aTip
$.fn.aTip = function(options) {
	var settings = $.extend({
		element: "",
		posicio: "position"
	}, options);
	this.each(function(){
		var element = $(this),
			tip_elm = element.find("p"),
			onMouseEnter = function() {
				var finestra_H = $(window).height(),
					element_T = (settings.posicio == "position") ? element.position().top : element.offset().top,
					element_L = (settings.posicio == "position") ? element.position().left : element.offset().left,
					element_H = element.outerHeight(),
					tip_H = tip_elm.outerHeight(),
					tip_T = element_T + element_H,
					tip_T = (tip_T + tip_H > finestra_H) ? element_T - tip_H : tip_T;

				tip_elm.css({ top: tip_T + "px", left: element_L + "px" }).fadeIn(200);
			},
			onMouseLeave = function() {
				tip_elm.fadeOut(200);
			};
		element.off('.aTip').on('focus.aTip', onMouseEnter).on('mouseenter.aTip', onMouseEnter).on('blur.aTip', onMouseLeave).on('mouseleave.aTip', onMouseLeave);
	});
	return this;
}
// /aTip


// inputNumero
$.fn.inputNumero = function(options) {
	var settings = $.extend({
		element: "",
		posicio: "position"
	}, options);
	this.each(function(){
		var element = $(this),
			element_maxlength = element.attr("maxlength") || "",
			onKeydown = function(event) {
				
				if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 || event.keyCode == 13 || 
					 	event.keyCode == 190 || // decimal (.)
						 
		             // Allow: Ctrl+A
		            (event.keyCode == 65 && event.ctrlKey === true) || 
		             // Allow: home, end, left, right
		            (event.keyCode >= 35 && event.keyCode <= 39)) {
		                 // let it happen, don't do anything
		                 return;
		        }
		        else {
		            // Ensure that it is a number and stop the keypress
		            if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) {
		                event.preventDefault(); 
		            }   
		        }
			};

		if (Modernizr.inputtypes.number && element_maxlength !== "") {
			element.addClass("no-type-number-"+element_maxlength);
		} else {
			var html = "<div class=\"imc-f-number-botones\">"
						+ "<p>"
							+ "<a href=\"#\" class=\"imc-suma\" title=\"Suma\">Suma</a>"
							+ "<a href=\"#\" class=\"imc-resta\" title=\"Resta\"><span>Resta</span></a>"
						+ "</p>";
					+ "</div>";
			//element.before(html);
		}

		element.off('.inputNumero').on('keydown.inputNumero', onKeydown);
	});
	return this;
}
// /inputNumero


// input cursor al final del text
function inputCursor(el) {
    if (typeof el.selectionStart == "number") {
        el.selectionStart = el.selectionEnd = el.value.length;
    } else if (typeof el.createTextRange != "undefined") {
        el.focus();
        var range = el.createTextRange();
        range.collapse(false);
        range.select();
    }
}
// /input cursor al final del text


// mostrarCapaEnviando
function mostrarCapaEnviando() {
	
	var missatge_titol_elm = $("<p>").text(txtFormDinEnviantDades).addClass("imc-mi-titol"),
		missatge_text_elm = $("<p>").text(txtFormDinEspere).addClass("imc-mi-text"),
		missatge_elm = $("<div>").html( $("<div>").addClass("imc-missatge-contingut").append(missatge_titol_elm).append(missatge_text_elm) ).attr("id", "imc-missatge").addClass("imc-missatge imc-mi imc-mi-enviant"),
		missatge_fons_elm = $("<div>").attr("id", "imc-missatge-fons").addClass("imc-missatge-fons");
	
	$("body").append(missatge_fons_elm).append(missatge_elm);
	
	$("#imc-missatge").fadeIn(200);
	
	var window_H = $(window).height(),
		contenidor_H = imc_forms_contenidor.outerHeight();
	
	if (contenidor_H > window_H) {
		$("#imc-missatge-fons").css("height", contenidor_H+"px");
	}
	
}
// /mostrarCapaEnviando


// ocultarCapaEnviando
function ocultarCapaEnviando() {
	
	$("#imc-missatge").stop().fadeOut(200, function() {
		$(this).remove();
	});
	$("#imc-missatge-fons").remove();
	
}
// /ocultarCapaEnviando


// normalize
var normalize = (function() {
	
  var from = "ÃÀÁÄÂÈÉËÊÌÍÏÎÒÓÖÔÙÚÜÛãàáäâèéëêìíïîòóöôùúüûÑñÇç",
      to   = "AAAAAEEEEIIIIOOOOUUUUaaaaaeeeeiiiioooouuuunncc",
      mapping = {};
 
  for(var i = 0, j = from.length; i < j; i++ )
      mapping[ from.charAt( i ) ] = to.charAt( i );
 
  return function( str ) {
      var ret = [];
      for( var i = 0, j = str.length; i < j; i++ ) {
          var c = str.charAt( i );
          if( mapping.hasOwnProperty( str.charAt( i ) ) )
              ret.push( mapping[ c ] );
          else
              ret.push( c );
      }
      return ret.join( '' );
  }
 
})();
// /normalize


// appTitle

$.fn.appTitle = function(options) {

	var settings = $.extend({
			element: ""
		}, options);

	this.each(function(){
		var element = $(this)
			,id_el = false
			,onAplica = "button"
			,inicia = function() {

				element
					.off(".appTooltip")
					.on('mouseenter.appTooltip, focus.appTooltip', onAplica, pinta)
					.on('mouseleave.appTooltip, blur.appTooltip', onAplica, amaga);

			}
			,pinta = function(e) {

				// pinta

				var elm_t = $(this);

				if (elm_t.find("span").css("position") !== "absolute") {
					return;
				}

				var elm_t_text = elm_t.text();

				imc_forms_body
					.find("div[role=tooltip]")
						.remove();

				var title_el = $("<div>")
									.attr({ "role": "tooltip", "aria-hidden": "true" })
									.text( elm_t_text )
									.insertBefore( imc_contenidor );

				// posiciona

				var finestra_W = imc_finestra.outerWidth();

				var bt_T = elm_t.offset().top
					,bt_L = elm_t.offset().left
					,bt_H = elm_t.outerHeight()
					,bt_W = elm_t.outerWidth(true)
					,bt_marge_L = parseInt(elm_t.css("marginLeft"), 10) || 0;

				var id_el_R = title_el.outerWidth() + bt_L;

				console.log(title_el.outerWidth() +" + "+ bt_L +" > "+ finestra_W);

				if (id_el_R > finestra_W) {

					var pos_R = finestra_W - (bt_L + bt_W);

					title_el
						.removeAttr("style")
						.css({ top: (bt_T + bt_H) + "px", right: (pos_R+10) + "px" })
						.addClass("imc--dreta");

				} else {

					title_el
						.removeAttr("style")
						.css({ top: (bt_T + bt_H) + "px", left: (bt_L+bt_marge_L-10) + "px" });

				}

				// mostra

				title_el
					.attr("aria-hidden", "false");

			}
			,amaga = function(e) {

				// pinta

				var elm_t = $(this);

				imc_forms_body
					.find("div[role=tooltip]:first")
						.attr("aria-hidden", "true");

			};
		
		// inicia
		inicia();
		
	});

	return this;
}



// appFormsDataFormat

var appFormsDataFormat = function(data_val) {

	var format = (data_val.indexOf("/") === -1) ? "in" : "es"
		,acceptaInputDate = (Modernizr.inputtypes.date) ? true : false;

	// revisem casos extraordinaris

	if (format === "es" && acceptaInputDate) {

		var data_in = data_val.split("/");

		data_val = data_in[2] + "-" + data_in[1] + "-" + data_in[0];

	} else if (format === "in" && !acceptaInputDate) {

		var data_es = data_val.split("-");

		data_val = data_es[2] + "/" + data_es[1] + "/" + data_es[0];

	}

	// retornem

	return data_val;

}
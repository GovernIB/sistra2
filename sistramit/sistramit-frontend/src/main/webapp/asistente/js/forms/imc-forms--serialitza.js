// SERIALITZA


$.fn.appSerialitza = function(opcions) {

	var settings = $.extend({
		verifica: true
	}, opcions);

	var element = $(this[0]),
		verifica = settings.verifica,
		form_id_i_valors = {},
		esError = false;

	// text

	var input_textual = "input[type=text], input[type=password], input[type=email], input[type=date], input[type=number], input[type=time], textarea";

	element
		.find( input_textual )
			.each(function() {
				
				var input_el = $(this)
					,input_id = input_el.attr("id")
					,input_val = input_el.val()
					,input_tipus = input_el.attr("data-tipus")
					,input_contingut = input_el.attr("data-contingut");	
				
				if (verifica) {

					var input_pare = input_el.closest(".imc-element");

					// obligatori

					esError = (input_el.is(":required") && input_val === "") ? true : false;

					// codi postal

					if (input_el.attr("data-contingut") === "codipostal" && input_val !== "") {

						esError = ( !input_el.appValida({ format: "codipostal", valor: input_val }) ) ? true : false;

					}

					// correu electrònic

					if (input_el.attr("data-contingut") === "correuelectronic" && input_val !== "") {

						esError = ( !input_el.appValida({ format: "correuelectronic", valor: input_val }) ) ? true : false;

					}

					// expresió regular

					if (input_el.attr("data-contingut") === "expresioregular" && input_val !== "") {

						esError = ( !input_el.appValida({ format: "expresioregular", valor: input_val }) ) ? true : false;

					}

					// identificador

					if (input_el.attr("data-contingut") === "identificador" && input_val !== "") {

						if (input_el.attr("data-nif") === "s") {

							esError = ( !appValidaIdentificador.nif(input_val) ) ? true : false;

						}

						if (input_el.attr("data-cif") === "s") {

							esError = ( !appValidaIdentificador.cif(input_val) ) ? true : false;

						}

						if (input_el.attr("data-nie") === "s") {

							esError = ( !appValidaIdentificador.nie(input_val) ) ? true : false;

						}

						if (input_el.attr("data-nss") === "s") {

							esError = ( !appValidaIdentificador.nss(input_val) ) ? true : false;

						}

					}

					// numero

					if (input_el.attr("data-contingut") === "numero" && input_val !== "") {

						esError = ( !input_el.appValida({ format: "numero", valor: input_val }) ) ? true : false;

					}

					// telèfon

					if (input_el.attr("data-contingut") === "telefon" && input_val !== "") {

						esError = ( !input_el.appValida({ format: "telefon", valor: input_val }) ) ? true : false;

					}

					// data

					if (input_el.attr("data-contingut") === "data" && input_val !== "") {

						esError = ( !input_el.appValida({ format: "data", valor: input_val }) ) ? true : false;

					}
					
					// hi ha error?

					if (esError) {

						input_pare
							.addClass("imc-el-error");

						return false;

					} else {

						input_pare
							.removeClass("imc-el-error");

					}

				}

				input_val = input_val.replace(/#-@/g, "").replace(/</g, "").replace(/>/g, "");

				form_id_i_valors[input_id] = input_tipus + "#-@" + input_val;
				
			});

	// selectors

	var input_selectors = "div[data-type='select'], fieldset[data-type='check-list'], fieldset[data-type='radio-list']";

	element
		.find( input_selectors )
			.each(function() {

				var el = $(this)
					,el_id = el.attr("data-id")
					,el_tipus = el.attr("data-tipus")
					,el_contingut = el.attr("data-contingut")
					,el_valortipus = el.attr("data-valortipus");

				if (el_contingut === "d") {

					var input_el = el.find("input:first")
						,input_val = input_el.val()
						,input_text = el.find(".imc-select:first span").text();

					form_id_i_valors[el_id] = el_valortipus + "#-@" + input_val + "#-@" + input_text;

				} else if (el_contingut === "m") {

					var valor = ""
						,check_elms = el.find(".imc-input-check")
						,check_elms_size = check_elms.length;

					check_elms
						.each(function(j) {

							var ckeck_val = $(this).find("input").val()
								,ckeck_text = $(this).find("label").text();

							valor += ckeck_val + "#-@" + ckeck_text;
							valor += (j < check_elms_size-1) ? "#-@" : "";

						});

					form_id_i_valors[el_id] = el_valortipus + "#-@" + valor;

				} else if (el_contingut === "u") {

					var input_el = el.find("input:checked")
						,input_val = input_el.val()
						,input_text = input_el.parent().find("label").text();

					form_id_i_valors[el_id] = el_valortipus + "#-@" + input_val + "#-@" + input_text;

				}


			});

	// check unitari

	var input_selectors = "div[data-type='check']";

	element
		.find( input_selectors )
			.each(function() {

				var el = $(this)
					,el_id = el.attr("data-id")
					,el_tipus = el.attr("data-tipus")
					,el_contingut = el.attr("data-contingut")
					,el_valortipus = el.attr("data-valortipus");

				var input_el = el.find("input:first")
					,input_val = (input_el.is(":checked")) ? input_el.attr("data-marcat") : input_el.attr("data-desmarcat");

				form_id_i_valors[el_id] = el_valortipus + "#-@" + input_val;

			});


	// errors?

	if (esError) {

		form_id_i_valors = false;

	}

	// retorna id/valors

	return form_id_i_valors;

}





/*



$.formulariSerialitzar = function(options) {
		var settings = $.extend({
			verifica: "si",
			validat: true,
			accio: ""
		}, options);
		
		var formulari_elm = $("div.formulari:first"),
			form_validat = settings.validat,
			mapVars = {},
			valida;
		
		// input text, textarea
		formulari_elm.find("input[type=text], input[type=password], textarea").each(function() {
			var input_elm = $(this)
				input_val = input_elm.val();
				
			input_val = input_val.replace(/#-@/g, "").replace(/</g, "").replace(/>/g, "");
			mapVars[input_elm.attr("id")] = "s#-@" +  input_val;
			
			if (settings.verifica == "si") {
				valida = $.formulariValida({ element: input_elm, conjunt: "formulari"});
				if (valida.resultat == "error") { form_validat = false; return false; }
			}
			
		});
		
		if (settings.verifica == "si") {
			if (!form_validat) { return false; }
		}
		
		// select
		formulari_elm.find("select").each(function() {
			var select_elm = $(this),
			option_selected = select_elm.find("option:selected");
			mapVars[select_elm.attr("id")] = "i#-@" + option_selected.val() + "#-@" + option_selected.text();
			
			if (settings.verifica == "si") {
				valida = $.formulariValida({ element: select_elm, conjunt: "formulari"});
				if (valida.resultat == "error") { form_validat = false; return false; }
			}
			
		});
		
		if (settings.verifica == "si") {
			if (!form_validat) { return false; }
		}
		
		// selector finestra
		formulari_elm.find("input.selector-finestra").each(function() {
			var selector_elm = $(this),
			selector_valor = formulari_elm.find("#" + selector_elm.attr("id") + "_VAL");
			mapVars[selector_elm.attr("id")] = "i#-@" + selector_valor.val() + "#-@" + selector_elm.val();
			
			if (settings.verifica == "si") {
				valida = $.formulariValida({ element: selector_elm, conjunt: "formulari"});
				if (valida.resultat == "error") { form_validat = false; return false; }
			}
			
		});
		
		if (settings.verifica == "si") {
			if (!form_validat) { return false; }
		}
		
		// selector llista checks
		formulari_elm.find("ul.check-list").each(function() {
			var ul_elm = $(this),
			check_elms = ul_elm.find("input[type=checkbox]:checked"),
			mapVarsIn = "";
			check_elms_size = check_elms.size();
			check_elms.each(function(i) {
				check_elm = $(this),
				check_id = check_elm.attr("id");
				
				mapVarsIn += check_elm.val() + "#-@" + formulari_elm.find("label[for=" + check_id + "]").text();
				mapVarsIn += (i < check_elms_size-1) ? "#-@" : "";
				
			});
			
			
			
			
			//LUISMI
			var idCampo =ul_elm.parent().attr("id").replace("ILC_","");
						
			mapVars[idCampo] = "l#-@" + mapVarsIn;
			
			if (settings.verifica == "si") {
				valida = $.formulariValida({ element: ul_elm.closest("div.input-list-container"), mode: "checkbox", conjunt: "formulari"});
				if (valida.resultat == "error") { form_validat = false; return false; }
			}
			
		});
		
		if (settings.verifica == "si") {
			if (!form_validat) { return false; }
		}
		
		// radio
		formulari_elm.find("ul.radio-list").each(function() {
			var ul_elm = $(this),
			input_checked_elm = ul_elm.find("input[type=radio]:checked"),
			input_checked_text = (input_checked_elm.size() === 0) ? "" : formulari_elm.find("label[for=" + input_checked_elm.attr("id") + "]").text(),
			input_checked_val = (input_checked_elm.size() === 0) ? "" : input_checked_elm.val(),
			input_checked_name = (input_checked_elm.size() === 0) ? ul_elm.find("input:first").attr("name") : input_checked_elm.attr("name");
			
			if (input_checked_val == "") {
				// Opcion sin seleccion
				input_checked_text = "";
			}
			
			mapVars[input_checked_name] = "i#-@" + input_checked_val + "#-@" + input_checked_text;
			
			if (settings.verifica == "si") {
				valida = $.formulariValida({ element: ul_elm.closest("div.input-list-container"), mode: "radio", conjunt: "formulari"});
				if (valida.resultat == "error") { form_validat = false; return false; }
			}
			
		});
		
		if (settings.verifica == "si") {
			if (!form_validat) { return false; }
		}
		
		// check unitari
		formulari_elm.find("input.check-unitario").each(function() {
			
			var check_elm = $(this);
			mapVars[check_elm.attr("id")] = "s#-@" + check_elm.val();
			
			if (settings.verifica == "si") {
				valida = $.formulariValida({ element: check_elm, mode: "check-unitario", conjunt: "formulari"});
				if (valida.resultat == "error") { form_validat = false; return false; }
			}
			
		});
		
		if (settings.verifica == "si") {
			if (!form_validat) { return false; }
		}
		
		if (settings.accio != "") {
			mapVars["accion"] = settings.accio;
		}
		
		return mapVars;
	
	};

	*/
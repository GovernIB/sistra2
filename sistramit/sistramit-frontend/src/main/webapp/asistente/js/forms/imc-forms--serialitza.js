// SERIALITZA


$.fn.appSerialitza = function(opcions) {

	var settings = $.extend({
		verifica: true
	}, opcions);

	var element = $(this[0]),
		verifica = settings.verifica,
		form_id_i_valors = {},
		esError = false;

	// element = imc_formulari_finestra;

	element
		.find(".imc-element")
			.each(function() {

				var el = $(this)
					,el_id = el.attr("data-id")
					,el_tipus = el.attr("data-tipus")
					,el_contingut = el.attr("data-contingut")
					,el_valortipus = el.attr("data-valortipus")
					,esObligatori = (el.attr("data-obligatori") === "s") ? true : false;

				// serialitza

				if (el_tipus === "texto") {

					var input_el = el.find("input:first")
						,input_val = input_el.val();

					input_val = input_val.replace(/#-@/g, "").replace(/</g, "").replace(/>/g, "");

					form_id_i_valors[el_id] = el_valortipus + "#-@" + input_val;

				} else if (el_tipus === "selector" && el_contingut === "d") {

					var input_el = el.find("input:first")
						,input_val = input_el.val()
						,input_text = el.find(".imc-select:first span").text();

					form_id_i_valors[el_id] = el_valortipus + "#-@" + input_val + "#-@" + input_text;

				} else if (el_tipus === "selector" && el_contingut === "m") {

					var valor = ""
						,check_elms = el.find(".imc-input-check")
						,check_elms_size = check_elms.length
						,checks_seleccionats = el.find("input:checked").length;

					check_elms
						.each(function(j) {

							var ckeck_el = $(this).find("input")
								,ckeck_val = ckeck_el.val()
								,ckeck_text = $(this).find("label").text();

							if (ckeck_el.is(":checked")) {

								valor += ckeck_val + "#-@" + ckeck_text;
								valor += (j < checks_seleccionats-1) ? "#-@" : "";

							}

						});

					form_id_i_valors[el_id] = el_valortipus + "#-@" + valor;

				} else if (el_tipus === "selector" && el_contingut === "u") {

					var input_el = el.find("input:checked")
						,input_val = input_el.val()
						,input_text = input_el.parent().find("label").text();

					if (input_el.length) {

						form_id_i_valors[el_id] = el_valortipus + "#-@" + input_val + "#-@" + input_text;

					} else {

						form_id_i_valors[el_id] = el_valortipus + "#-@#-@";

					}

				} else if (el_tipus === "verificacion") {

					var input_el = el.find("input:first")
						,input_val = (input_el.is(":checked")) ? input_el.attr("data-marcat") : input_el.attr("data-desmarcat");

					form_id_i_valors[el_id] = el_valortipus + "#-@" + input_val;

				}

				// verifica?

				if (verifica) {

					if (el_tipus === "texto") {

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

					} else if (el_tipus === "selector" && el_contingut === "d") {

						esError = (input_val === "") ? true : false;

					} else if (el_tipus === "selector" && el_contingut === "m") {

						esError = (!checks_seleccionats) ? true : false;

					} else if (el_tipus === "selector" && el_contingut === "u") {

						esError = (!input_el.length) ? true : false;

					} else if (el_tipus === "verificacion") {

						esError = (esObligatori && !input_el.is(":checked")) ? true : false;

					}
					
					
					// hi ha error?

					if (esError) {

						el
							.addClass("imc-el-error");

						return false;

					} else {

						el
							.removeClass("imc-el-error");

					}

				}

			});

	// errors?

	if (esError) {

		form_id_i_valors = false;

	}

	// retorna id/valors

	return form_id_i_valors;

}

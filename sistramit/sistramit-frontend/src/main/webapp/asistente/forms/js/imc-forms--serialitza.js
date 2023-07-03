// SERIALITZA

var ERROR_TEXT = false;


$.fn.appSerialitza = function(opcions) {

	var settings = $.extend({
		verifica: true
	}, opcions);

	var element = $(this[0])
		,verifica = settings.verifica
		,form_id_i_valors = {}
		,esError = false;

	var form_el_json = {};

	var formEnMayuscules = (imc_forms_finestra.attr("data-mayuscules") === "s") ? true : false;

	// element = imc_forms_finestra;

	element
		.find(".imc-element")
			.each(function() {

				var el = $(this)
					,el_id = el.attr("data-id")
					,el_tipus = el.attr("data-tipus")
					,el_contingut = el.attr("data-contingut")
					,el_valortipus = el.attr("data-valortipus")
					,esObligatori = (el.attr("data-obligatori") === "s") ? true : false
					,enMayuscules = (el.attr("data-mayuscules") === "s") ? true : false
					,estaOcult = (el.attr("data-ocult") === "s") ? true : false
					,estaBlocOcult = (el.attr("data-ocult-bloc") === "s") ? true : false;

				// serialitza

				if (el_tipus === "texto" || el_tipus === "oculto") {

					var input_el = el.find("input:first, textarea:first")
						,esData = (input_el.attr("type") === "date") ? true : false
						,input_val = input_el.val();

					var data_format = (typeof APP_FORM_DATA_FORMAT !== "undefined" && APP_FORM_DATA_FORMAT === "es") ? "es" : "in";

					if (esData && data_format === "es") {

						input_val = element.appFormsDataEspanyola({ data: input_val });

					}

					input_val = (el_contingut === "nu") ? input_val : input_val.replace(/#-@/g, "").replace(/</g, "").replace(/>/g, "");

					if (el_tipus === "texto" && (el_contingut === "id" || formEnMayuscules || enMayuscules)) {

						input_val = input_val.toUpperCase();

					}

					// iban

					if (el_contingut === "ib" && input_val !== "") {

						input_val = $.trim( input_val.toUpperCase().replace(/\s/g, "") );

					}

					form_el_json[el_id] = { id: el_id, tipo: el_valortipus, valor: input_val };

					form_id_i_valors[el_id] = JSON.stringify( form_el_json[el_id] );


				} else if (el_tipus === "selector" && el_contingut === "d") {

					var input_el = el.find("input:first")
						,input_val = input_el.val()
						,input_text = el.find(".imc-select:first span").text();

					if (input_val === "") {

						form_el_json[el_id] = { id: el_id, tipo: el_valortipus, valor: null };

					} else {

						form_el_json[el_id] = { id: el_id, tipo: el_valortipus, valor: { descripcion: input_text, valor: input_val } };

					}

					form_id_i_valors[el_id] = JSON.stringify( form_el_json[el_id] );


				} else if (el_tipus === "selector" && el_contingut === "m") {

					var valor = ""
						,check_elms = el.find(".imc-input-check")
						,check_elms_size = check_elms.length
						,checks_seleccionats = el.find("input:checked").length;

					form_el_json[el_id] = { id: el_id, tipo: el_valortipus, valor: null };

					var hiHaArray = false;

					check_elms
						.each(function(j) {

							var check_el = $(this).find("input")
								,check_val = check_el.val()
								,check_text = $(this).find("label").text();

							if (!hiHaArray && check_el.is(":checked")) {

								form_el_json[el_id]["valor"] = [];

								hiHaArray = true;

							}

							if (check_el.is(":checked")) {

								form_el_json[el_id]["valor"]
									.push({ descripcion: check_text, valor: check_val });

							}

						});

					form_id_i_valors[el_id] = JSON.stringify( form_el_json[el_id] );

				} else if (el_tipus === "selector" && el_contingut === "u") {

					var input_el = el.find("input:checked")
						,input_val = input_el.val()
						,input_text = input_el.parent().find("label").text();

					if (input_el.length) {

						form_el_json[el_id] = { id: el_id, tipo: el_valortipus, valor: { descripcion: input_text, valor: input_val } };

					} else {

						form_el_json[el_id] = { id: el_id, tipo: el_valortipus, valor: null };

					}

					form_id_i_valors[el_id] = JSON.stringify( form_el_json[el_id] );

				} else if (el_tipus === "selector" && el_contingut === "a") {

					var input_el = el.find("input[type=hidden]:first")
						,input_val = input_el.val()
						,input_text = el.find("textarea:first").val();

					if (input_el.length) {

						form_el_json[el_id] = { id: el_id, tipo: el_valortipus, valor: { descripcion: input_text, valor: input_val } };

					} else {

						form_el_json[el_id] = { id: el_id, tipo: el_valortipus, valor: null };

					}

					form_id_i_valors[el_id] = JSON.stringify( form_el_json[el_id] );



				} else if (el_tipus === "verificacion") {

					var input_el = el.find("input:first")
						,input_val = (input_el.is(":checked")) ? input_el.attr("data-marcat") : input_el.attr("data-desmarcat");

					form_el_json[el_id] = { id: el_id, tipo: el_valortipus, valor: input_val };

					form_id_i_valors[el_id] = JSON.stringify( form_el_json[el_id] );

				} else if (el_tipus === "captcha") {

					var input_el = el.find("input:first")
						,input_val = input_el.val();

					form_el_json[el_id] = { id: el_id, tipo: "s", valor: input_val };

					form_id_i_valors[el_id] = JSON.stringify( form_el_json[el_id] );

				} else if (el_tipus === "listaElementos") {

					var fila_elms = el.find("table tbody tr")
						,fila_elms_size = fila_elms.length;

					form_el_json[el_id] = { id: el_id, tipo: el_valortipus, valor: null };

					if (fila_elms_size) {

						form_el_json[el_id]["valor"] = [];

						fila_elms
							.each(function(j) {

								var tr_el = $(this)
									,tr_dades = tr_el.attr("data-dades")
									,td_elms = tr_el.find("td");

								var element_json = JSON.parse(tr_dades);

								form_el_json[el_id]["valor"]
									.push( element_json );

							});

					}

					form_id_i_valors[el_id] = JSON.stringify( form_el_json[el_id] );
					
				}

				// verifica?

				if (verifica && !estaOcult && !estaBlocOcult) {

					if (el_tipus === "texto" || el_tipus === "oculto") {

						// obligatori

						esError = (input_el.is(":required") && (!input_val || input_val.trim() === "")) ? true : false;
						ERROR_TEXT = (esError) ? txtFormDinCampError_buit : false;

						// textarea amb un màxim de línies

						if (input_el.is("TEXTAREA") && input_el.attr("data-linies") && input_val !== "") {

							esError = ( !input_el.appValida({ format: "textarea", valor: input_val }) ) ? true : false;
							ERROR_TEXT = (esError) ? txtFormDinCampError_linies + " " + input_el.attr("data-linies") +"." : false;

						}

						// codi postal

						if (input_el.attr("data-contingut") === "codipostal" && input_val !== "") {

							esError = ( !input_el.appValida({ format: "codipostal", valor: input_val }) ) ? true : false;
							ERROR_TEXT = (esError) ? txtFormDinCampError_cp : false;

						}

						// correu electrònic

						if (input_el.attr("data-contingut") === "correuelectronic" && input_val !== "") {

							esError = ( !input_el.appValida({ format: "correuelectronic", valor: input_val }) ) ? true : false;
							ERROR_TEXT = (esError) ? txtFormDinCampError_correu : false;

						}

						// expresió regular

						if (input_el.attr("data-contingut") === "expresioregular" && input_val !== "") {

							esError = ( !input_el.appValida({ format: "expresioregular", valor: input_val }) ) ? true : false;

						}

						// identificador

						if (input_el.attr("data-contingut") === "identificador" && input_val !== "") {

							var idValid = false;

							if (input_el.attr("data-dni") === "s") {

								idValid = ( appValidaIdentificador.dni(input_val) ) ? true : false;

							}

							if (!idValid && input_el.attr("data-nie") === "s") {

								idValid = ( appValidaIdentificador.nie(input_val) ) ? true : false;

							}

							if (!idValid && input_el.attr("data-nifOtros") === "s") {

								idValid = ( appValidaIdentificador.nifOtros(input_val) ) ? true : false;

							}

							if (!idValid && (input_el.attr("data-nifPJ") === "s" || input_el.attr("data-cif") === "s")) {

								idValid = ( appValidaIdentificador.nifPJ(input_val) ) ? true : false;

							}

							if (!idValid && input_el.attr("data-nss") === "s") {

								idValid = ( appValidaIdentificador.nss(input_val) ) ? true : false;

							}

							if (!idValid && input_el.attr("data-nif") === "s") {

								idValid = ( appValidaIdentificador.nif(input_val) ) ? true : false;

							}

							esError = !idValid;
							ERROR_TEXT = (esError) ? txtFormDinCampError_id : false;

						}

						// numero

						if (input_el.attr("data-contingut") === "numero" && input_val !== "") {

							esError = ( !input_el.appValida({ format: "numero", valor: input_val }) ) ? true : false;
							ERROR_TEXT = (esError) ? txtFormDinCampError_numero : false;

						}

						// telèfon

						if (input_el.attr("data-contingut") === "telefon" && input_val !== "") {

							esError = ( !input_el.appValida({ format: "telefon", valor: input_val }) ) ? true : false;
							ERROR_TEXT = (esError) ? txtFormDinCampError_telf : false;

						}

						// data
						
						if (input_el.attr("data-contingut") === "data") {

							esError = ( !input_el.appValida({ format: "data", valor: input_val }) ) ? true : false;
							ERROR_TEXT = (esError) ? txtFormDinCampError_data : false;

						}

						// iban

						if (input_el.attr("data-contingut") === "iban" && input_val !== "") {

							var valor_iban = $.trim( input_val.toUpperCase().replace(/\s/g, "") );

							esError = ( !IBAN.isValid( valor_iban ) && !validaCCC(valor_iban) ) ? true : false;
							ERROR_TEXT = (esError) ? txtFormDinCampError_iban : false;

						}

					} else if (el_tipus === "selector" && el_contingut === "d") {

						esError = (esObligatori && input_val === "") ? true : false;
						ERROR_TEXT = (esError) ? txtFormDinCampError_selector : false;

					} else if (el_tipus === "selector" && el_contingut === "m") {

						esError = (esObligatori && !checks_seleccionats) ? true : false;
						ERROR_TEXT = (esError) ? txtFormDinCampError_selector : false;

					} else if (el_tipus === "selector" && el_contingut === "u") {

						esError = (esObligatori && !input_el.length) ? true : false;
						ERROR_TEXT = (esError) ? txtFormDinCampError_selector : false;

					} else if (el_tipus === "selector" && el_contingut === "a") {

						esError = (input_el.is(":required") && input_val === "") ? true : false;
						ERROR_TEXT = (esError) ? txtFormDinCampError_selector : false;

					} else if (el_tipus === "verificacion") {

						esError = (esObligatori && !input_el.is(":checked")) ? true : false;
						ERROR_TEXT = (esError) ? txtFormDinCampError_verificacio : false;

					} else if (el_tipus === "captcha") {

						esError = (input_el.is(":required") && input_val === "") ? true : false;
						ERROR_TEXT = (esError) ? txtFormDinCampError_captcha : false;

					} else if (el_tipus === "listaElementos") {

						var th_num = el.find("tbody:first tr").length;

						esError = (esObligatori && !th_num) ? true : false;
						ERROR_TEXT = (esError) ? txtFormDinCampError_llistaElms : false;

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

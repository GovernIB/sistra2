// index

var idioma,
	imc_forms_contenidor,
	imc_forms_ajuda,
	imc_bt_ajuda,
	imc_forms_formulari,
	imc_forms_cap,
	imc_bt_sortiu;

// onReady

$(function(){
	
	idioma = $("html").attr("lang");
	imc_forms_contenidor = $("#imc-forms-contenidor");
	imc_forms_ajuda = $("#imc-forms-ajuda");
	imc_bt_ajuda = $("#imc-bt-ajuda");
	imc_forms_formulari = $("#imc-forms-formulari");

	imc_forms_cap = $("#imc-forms-cap");
	imc_bt_sortiu = imc_forms_cap.find(".imc--sortiu:first");

	// sortir

	imc_bt_sortiu
		.off(".sortiuForm")
		.on("click.sortiuForm", function() {
			
			$("#imc-bt-iframe-tanca", top.document)
				.trigger("click");

		});

	// carregant
	
	jQuery.getScript("js/jquery-imc-comuns.js", function() {
		
		jQuery.imcF_getMultipleScript(
			[
				"js/utils/jquery-ui-1.10.3.custom.min.js"
				,"js/utils/jquery-maskedinput.min.js"
				,"js/utils/jshashtable-3.0.js"
				,"js/utils/jquery-numberformatter-1.2.4.min.js"
				,"js/jquery-imc-forms-funcions.js"
				,"js/literals/jquery-imc-literals-calendari-" + idioma + ".js"
				,"js/literals/vars-imc-literals-" + idioma + ".js"
			],
			function() {
				
				// titol formulari
				var imc_form_titol_elm = imc_forms_contenidor.find("div.imc-fo-ti-text:first"),
					imc_form_titol_H3_elm = imc_form_titol_elm.find("h3 span");
				
				if (imc_form_titol_elm.width() < imc_form_titol_H3_elm.width()) {
					imc_form_titol_H3_elm.attr("title", imc_form_titol_H3_elm.text());
				}
				
				// arbre
				imc_forms_formulari
					.find(".imc-el-arbre")
						.arbre();
				
				// taula
				imc_forms_formulari
					.find(".imc-el-taula")
						.taula();

				// text selector
				imc_forms_formulari
					.find(".imc-el-text-selector")
						.inputSelectAjax();
				
				// formateig de dades
				dadesFormateig();
				
				// ajuda
				imc_bt_ajuda
					.ajuda();

				// errors
				imc_forms_contenidor
					.appErrors();
				
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
					document.location = "index_pag_7_taula.html";
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
		);
	
	});

});
// /onReady

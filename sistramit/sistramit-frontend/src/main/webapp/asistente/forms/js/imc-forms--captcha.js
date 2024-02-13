// CAPTCHA


/* columnas: 3, 4, 5, 6 */

/* HTML

<div class=\"imc--img\"><img src=\"\" alt=\"\"></div>
<input id=\"CAPTCHA_1\" name=\"CAPTCHA_1\" type=\"text\">
<button type=\"button\"><span>Refresca imatge</span></button>

*/


$.fn.appFormsCaptcha = function(options) {

	var settings = $.extend({
			element: ""
			,genera: false
			,tipo: false
			,columnes: false
			,titol: false
			,so: false
		}, options);

	this.each(function(){
		var element = $(this)
			,genera_acc = settings.genera
			,tipo_acc = settings.tipo
			,columnes_acc = settings.columnes
			,titol_acc = settings.titol
			,so_acc = settings.so
			,esSelecc = (tipo_acc === "s") ? true : false
			,app_json_captcha_genera = (esSelecc) ? APP_FORMS_CAPTCHA_SEL_GENERA : APP_FORMS_CAPTCHA_TXT_GENERA
			,app_json_captcha_so = (esSelecc) ? APP_FORMS_CAPTCHA_SEL_REPRODUEIX : APP_FORMS_CAPTCHA_TXT_REPRODUIX
			,app_json_captcha_sel_ids = APP_FORMS_CAPTCHA_SEL_DESCARREGA
			,camp_id = false
			,envia_url = false
			,envia_ajax = false
			,json = false
			,audio = false
			,inicia = function() {

				var camp_id = element.attr("data-id");

				// pinta HTML

				if (!esSelecc) {

					element
						.find("label:first")
							.text( txtFormDinIntroduiuText_captcha );

					var captcha_html = "<div class=\"imc--img\"><img src=\"\" alt=\"\"></div>"
										+ "<input id=\"" + camp_id + "\" name=\"" + camp_id + "\" type=\"text\">"
										+ "<button type=\"button\"><span>" + txtFormDinCampRefresca_captcha + "</span></button>";

				} else {

					var captcha_html = "<div class=\"imc--img\"></div>"
										+ "<button type=\"button\"><span>" + txtFormDinCampRefresca_captcha + "</span></button>";

				}

				element
					.find(".imc-el-control:first")
						.html( captcha_html );

				// botó sona

				btSo_crea();

				// events

				element
					.off(".appFormsCaptcha")
					.on("click.appFormsCaptcha", "button[data-accio=reproduix]", btSo_sona)
					.on("click.appFormsCaptcha", "button[data-accio=prepara-reproduccio]", reproduccioPrepara)
					.on("click.appFormsCaptcha", "button[data-accio=tanca-reproduccio]", reproduccioTanca)
					.on("keydown.appFormsCaptcha", "input[data-tipus=so]", revisaInputSo)
					.on("click.appFormsCaptcha", "button:last", genera);

				// genera img captcha

				genera();

			}
			,reproduccioPrepara = function() {

				element
					.find(".imc--grid-inputs:first input:first")
						.focus()
						.end()
					.find("label")
						.text( txtFormDinIntroduiuNum_captcha )
						.end()
					.attr("data-reproduccio", "prepara");

			}
			,reproduccioTanca = function() {

				var titol_original = element.find("label:first").attr("data-text");

				element
					.find("label")
						.text( titol_original )
						.end()
					.removeAttr("data-reproduccio");

				if (audio) {

					audio
						.pause();

					audio = false;

				}

			}
			,btSo_crea = function() {

				bt_sona = "";

				if (esSelecc && so_acc === "s" ) {

					var bt_prepara = $("<button>").attr({ type: "button", "data-accio": "prepara-reproduccio", "title": txtFormDinCampPreparaRepr_captcha })
						,bt_tanca = $("<button>").attr({ type: "button", "data-accio": "tanca-reproduccio", "title": txtFormDinCampTanca_captcha })
						,bt_sona = $("<button>").attr({ type: "button", "data-accio": "reproduix", "title": txtFormDinCampReproduix_captcha });

					element
						.find(".imc--img:first")
							.after( bt_sona )
							.after( bt_prepara )
							.after( bt_tanca );

				}

				if (!esSelecc) {

					var bt_sona = $("<button>").attr({ type: "button", "data-accio": "reproduix", "title": txtFormDinCampReproduix_captcha });

					element
						.find("input:first")
							.before( bt_sona );

				}

			}
			,btSo_sona = function() {

				if (!audio) {

					audio = new Audio( app_json_captcha_so + '?id=' + element.attr("data-id") + "&ts=" + new Date().getTime() + "&" + headerIdSessio + "=" + tokenIdSessio );
        		
					audio
						.play();

				} else if (audio && audio.paused ) {

					audio
						.play();

				} else {

					audio
						.pause();

				}

			}
			,genera = function() {
				
				var timestamp = Date.now()
					,camp_id = element.attr("data-id");

				element
					.find("input, button")
						.attr("disabled", "disabled");

				if (genera_acc) {

					esSelecc = (element.attr("data-tipo") === "s") ? true : false;
					app_json_captcha_genera = (esSelecc) ? APP_FORMS_CAPTCHA_SEL_GENERA : APP_FORMS_CAPTCHA_TXT_GENERA;
					app_json_captcha_so = (esSelecc) ? APP_FORMS_CAPTCHA_SEL_REPRODUEIX : APP_FORMS_CAPTCHA_TXT_REPRODUIX;

				}

				refresca(timestamp, camp_id);

			}
			,refresca = function(timestamp, camp_id) {

				// captcha

				if (!esSelecc) {

					// imatge txt

					var img_src = app_json_captcha_genera + "?id=" + camp_id + "&ts=" + timestamp + "&" + headerIdSessio + "=" + tokenIdSessio;

					element
						.find(".imc--img:first img")
							.attr("src", img_src)
							.end()
						.find("input:first")
							.val("");

					// activem

					activa();

				} else {

					// selector imatges

					var pag_dades = { id: camp_id }

					$.ajax({
						url: app_json_captcha_genera,
						data: pag_dades,
						method: "post",
						dataType: "json"
					})
					.done(function( json ) {

						var estat_ = json.estado
							,ids_ = json.datos.imagenes
							,figura_ = json.datos.figura;

						if (estat_ === "SUCCESS" || estat_ === "WARNING") {

							// JSON -> èxit o atenció

							var avant = function() {

									element
										.attr("data-ids", ids_)
										.removeAttr("data-reproduccio");

									// figura

									if (figura_ && figura_ !== "") {

										element
											.find("label:first")
												.attr("data-text", txtFormDinSeleccionaAmb_captcha + " " + figura_)
												.text( txtFormDinSeleccionaAmb_captcha + " " + figura_ );

									}

									// pinta imatges

									selecPinta(timestamp, camp_id);

								};

							if (json.estado === "WARNING") {

								imc_forms_missatge
									.appFormsMissatge({ accio: "warning", titol: data.mensaje.titulo, text: data.mensaje.texto, alAcceptar: function() { avant(); } });

								return;

							}

							// avant

							avant();
							

						} else {

							// JSON -> error!

							consola("Captcha genera SELECTOR IMGS: error des de JSON");

							imc_forms_body
								.appFormsErrorsGeneral({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, debug: data.mensaje.debug, url: json.url });

						}

					})
					.fail(function(dades, tipus, errorThrown) {

						envia_ajax = false;

						if (tipus === "abort") {
							return false;
						}

						consola("Captcha genera SELECTOR IMGS: error des de FAIL. " + dades + ", "+ tipus +", "+ errorThrown);

						imc_forms_body
							.appFormsErrorsGeneral({ estat: "fail" });

					});

				}

			}
			,selecPinta = function(timestamp, camp_id) {

				// columnes

				var imc_div = element.find(".imc--img:first");

				imc_div
					.find(".imc--grid, .imc--grid-inputs")
						.remove();

				var imc_grid = $("<div>").addClass("imc--grid").attr({ role: "listbox", "aria-busy": "false" }).off(".seSe").on("click.seSe", "button", selecSelecciona).appendTo( imc_div )
					,imc_grid_inputs = $("<div>").addClass("imc--grid-inputs").appendTo( imc_div );
				
				var ids_ = element.attr("data-ids").split(",")
					,ids_num = ids_.length;

				for (var i = 0; i <= ids_num-1; i++) {

					$("<button>")
						.attr({ "type": "button", "role": "option", "aria-checked": "false", "data-id": ids_[i], "style": "background-image: url(" + app_json_captcha_sel_ids + "?id=" + camp_id + "&imagen_id=" + ids_[i] + "&ts=" + timestamp + "&" + headerIdSessio + "=" + tokenIdSessio + ");" })
							.appendTo( imc_grid );

				}

				// so accessible

				for (var i = 0; i <= 3; i++) {

					$("<input>")
						.attr({ "type": "text", "maxlength": "2", "data-id": "id_" + i, "data-tipus": "so" })
							.appendTo( imc_grid_inputs );

				}

				// activem

				activa();

			}
			,selecSelecciona = function(e) {

				var bt_ = $(this)
					,nouValor = (bt_.attr("aria-checked") === "true") ? "false" : "true";

				bt_
					.attr("aria-checked", nouValor)

			}
			,revisaInputSo = function(e) {

				var tecla_ = e.keyCode
					,esCorrecte = true;


				// números

				if ( (tecla_ < 48 || tecla_ > 57) && (tecla_ < 96 || tecla_ > 105) && tecla_ !== 8 && tecla_ !== 9 && tecla_ !== 39 && tecla_ !== 37 && tecla_ !== 46) {

					esCorrecte = false;

				}

				// es correcte?

				if ( !esCorrecte ) {

					e.preventDefault();
					return;

				}

			}
			,activa = function() {

				// activem input i bottons

				element
					.find("input, button")
						.removeAttr("disabled");

			};

		// inicia

		if (genera_acc) {

			genera();

		} else {

			inicia();

		}
		

	});

	return this;
}


// Maia loves Ismaeleteeee
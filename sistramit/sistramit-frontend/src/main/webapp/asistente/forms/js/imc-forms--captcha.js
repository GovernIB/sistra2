// CAPTCHA


$.fn.appFormsCaptcha = function(options) {
	var settings = $.extend({
		element: ""
		,regenera: false
	}, options);
	this.each(function(){
		var element = $(this)
			,regenera_acc = settings.regenera
			,camp_id = false
			,envia_url = false
			,envia_ajax = false
			,json = false
			,audio = false
			,inicia = function() {

				// botó sona

				var bt_sona = $("<button>").attr({ type: "button", "data-accio": "reproduix", "title": txtFormDinCampReproduix_captcha });

				element
					.find("input:first")
						.before( bt_sona );

				// events

				element
					.off(".appFormsCaptcha")
					.on("click.appFormsCaptcha", "button:first", sona)
					.on("click.appFormsCaptcha", "button:last", regenera);

				element
					.find("input, button")
						.attr("disabled", "disabled");

				genera();

			}
			,sona = function() {

				if (audio) {

					audio
						.pause();

				}

				audio = new Audio(APP_FORMS_CAPTCHA_REPRODUIX+'?id=' + element.attr("data-id") + "&ts=" + new Date().getTime());
        		
        		audio
        			.play();

			}
			,genera = function() {
				
				var timestamp = Date.now()
					,camp_id = element.find("input").attr("id");

				var img_src = APP_FORMS_CAPTCHA + "?id=" + camp_id + "&ts=" + timestamp;

				element
					.find(".imc--img:first img")
						.attr("src", img_src)
						.end()
					.find("input:first")
						.val("");

				element
					.find("input, button")
						.removeAttr("disabled");

			}
			,regenera = function() {

				// serialitza

				var timestamp = Date.now()
					,camp_id = element.find("input").attr("id");

				var pag_dades = { id: camp_id, timestamp: timestamp};

				$.ajax({
					url: APP_FORMS_CAPTCHA_REGENERA,
					data: pag_dades,
					method: "post",
					dataType: "json",
					beforeSend: function(xhr) {
						xhr.setRequestHeader(headerCSRF, tokenCSRF);
					}
				})
				.done(function( data ) {

					// Refresca imatge

					var img_src = APP_FORMS_CAPTCHA +"?id="+ camp_id +"&timestamp="+ timestamp;

					element
						.find(".imc--img:first img")
							.attr("src", img_src);

					element
						.find("input, button")
							.removeAttr("disabled");

				})
				.fail(function(dades, tipus, errorThrown) {

					envia_ajax = false;

					if (tipus === "abort") {
						return false;
					}

					consola("Captcha regenera: error des de FAIL. " + dades + ", "+ tipus +", "+ errorThrown);

					imc_forms_body
						.appFormsErrorsGeneral({ estat: "fail" });

				});

			}
			,envia = function(e) {

				// dades ajax

				var pag_url = envia_url,
					pag_dades = { id: camp_id, timestamp: timestamp};

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
									.appFormsMissatge({ accio: "warning", titol: data.mensaje.titulo, text: data.mensaje.texto });

								return;

							}

							resultat();

						} else {

							envia_ajax = false;

							consola("Captcha genera: error des de JSON");

							imc_forms_body
								.appFormsErrorsGeneral({ estat: json.estado, titol: data.mensaje.titulo, text: data.mensaje.texto, debug: data.mensaje.debug, url: json.url });

						}

					})
					.fail(function(dades, tipus, errorThrown) {

						envia_ajax = false;

						if (tipus === "abort") {
							return false;
						}

						consola("Captcha genera: error des de FAIL");

						imc_forms_body
							.appFormsErrorsGeneral({ estat: "fail" });

					});

			}
			,resultat = function() {

				var img_url = json.datos.url;

				element
					.find("img")
						.attr("src", img_url);

				element
					.find("input, button")
						.removeAttr("disabled");

			};

		// inicia

		if (regenera_acc) {

			regenera();

		} else {

			inicia();

		}
		

	});
	return this;
}



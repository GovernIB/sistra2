// NÚMERO



var FORMS_TEL_INTERNACIONAL = false;


// configuració telf. internacional

$.fn.appFormsTelefonInternacional = function(options) {

	var settings = $.extend({
			el: false
		}, options);

	this.each(function(){
		var element = $(this)
			,camp_ = element.find("input:first")
			,iti = false
			,prepara = function(e) {


				// revisem si està carregat el plugin

				if (FORMS_TEL_INTERNACIONAL) {

					inicia();
					return;

				}


				// carreguem el plugin

				var tel_int_idioma = (APP_IDIOMA === "ca") ? APP_FORMS_ + "forms/js/utils/intlTelInput_ca_ES__.js?" + APP_FORMS_VERSIO : APP_FORMS_ + "forms/js/utils/intlTelInput_es_ES__.js?" + APP_FORMS_VERSIO;

				$.when(
					
					$.getScript(APP_FORMS_ + "forms/js/utils/intlTelInput.min.js?" + APP_FORMS_VERSIO)
					,$.getScript(tel_int_idioma)

				).then(

					function( tiJS, tiJS_idioma ) {

						$("<link>")
							.attr({ rel: "stylesheet", media: "screen", href: APP_FORMS_ + "forms/css/utils/intlTelInput.css?" + APP_FORMS_VERSIO })
								.appendTo( imc_forms_head )
								.on("load", function() { inicia(); });

						FORMS_TEL_INTERNACIONAL = true;

					}

				).fail(

					function() {

						consola("JS telèfon internacional: error des de càrrega inicial (FAIL)");

						// no fem res, i el deixem com a telèfon normal

					}

				);


			}
			,inicia = function() {

				// iniciem

				var elements_telf_int = element.find("div[data-telefon-internacional]");

				if (!elements_telf_int.length) {
					return;
				}

				elements_telf_int
					.each(function() {

						var tel_int_ = $(this)
							,camp_ = tel_int_.find("input:first");

						if( typeof tel_int_.data("iti") === "undefined" ) {

							iti = window
								.intlTelInput(
									camp_[0]
									,{
										i18n: intlTelInput_idioma,
										initialCountry: "es",
										separateDialCode: true,
										utilsScript: APP_FORMS_ + "forms/js/utils/intlTelInput.utils.js?" + APP_FORMS_VERSIO
									}
								);

							// guardem event d'inici a la dada del element

							tel_int_
								.data("iti", iti);

						}


						// es lectura?

						revisemLectura(tel_int_);


						// event només números i tecla '+'?

						revisaTeclat();


					});

			}
			,revisemLectura = function(tel_int_) {

				if (tel_int_.attr("data-lectura") === "n") {
					return;
				}

				tel_int_
					.find("button:first")
						.attr("disabled", "disabled")
						.end()
					.find(".iti__tel-input")
						.attr("readonly", "readonly");

			}
			,revisaTeclat = function() {

				// events

				element
					.off(".appFormsTelefonInternacional")
					.on("keydown.appFormsTelefonInternacional", "input.iti__tel-input", revisemTeclat);


			}
			,revisemTeclat = function(e) {

				var tecla_ = e.keyCode;

				var input = $(this)
					,input_size = input.val().length
					,esCorrecte = true;

				//console.log("revisemTeclat: " + e.keyCode + " -- Llargada: " + input_size);


				// números

				if ( (tecla_ < 48 || tecla_ > 57) && (tecla_ < 96 || tecla_ > 105) && tecla_ !== 8 && tecla_ !== 9 && tecla_ !== 39 && tecla_ !== 37 && tecla_ !== 46 && tecla_ !== 171) {

					esCorrecte = false;

				}

				// amplaria número

				if (input_size > 15) {

					esCorrecte = false;

				}

				// esborrar i suprimir

				if (tecla_ === 8 || tecla_ === 46 || tecla_ === 37 || tecla_ === 39) {

					esCorrecte = true;

				}

				// es correcte?

				if ( !esCorrecte ) {

					e.preventDefault();
					return;

				}

			};

		// prepara

		prepara();

	});

	return this;
}

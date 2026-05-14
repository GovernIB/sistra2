// appFormsAjuda

$.fn.appFormsAjuda = function(opcions) {
	var settings = $.extend({
		element: ""
	}, opcions);
	this.each(function(){
		var element = $(this),
			ajuda_inicial = (APP_FORMS_AJUDA_ACTIVADA === "S") ? "off" : "on",
			bt_ajuda = element.find("button:first"),
			txtAjudaInfo = false,
			txtAjudaBoto = false,
			ajuda_data = false,
			inicia = function() {

				imc_forms_ajuda
					.data("ajuda", ajuda_inicial);

				activa();

				bt_ajuda
					.off('.appFormsAjuda')
					.on('click.appFormsAjuda', activa);

			},
			activa = function() {

				if (imc_forms_ajuda.data("ajuda") === "off") {

					imc_forms_ajuda
						.removeClass("imc--desactivada");

					imc_forms_contenidor
						.appFormsAjudaCamp({ referent: imc_forms_finestra.find(".imc--contingut:first") })
						.attr("ajuda-activada", "si");

					txtAjudaInfo = txtFormDinAjuda + " " + txtFormDinActivada;
					txtAjudaBoto = txtFormDinDesactiva;
					ajuda_data = "on";

					APP_FORMS_AJUDA_ACTIVADA = "S"

				} else {

					imc_forms_ajuda
						.addClass("imc--desactivada");

					imc_forms_contenidor
						.off('.appFormsAjudaCamp')
						.attr("ajuda-activada", "no");

					txtAjudaInfo = txtFormDinAjuda + " " + txtFormDinDesctivada;
					txtAjudaBoto = txtFormDinActiva;
					ajuda_data = "off";

					APP_FORMS_AJUDA_ACTIVADA = "N"

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

		var element = $(this)
			,referent = settings.referent
			,elementEntra = function(e) {

				if (element.attr("ajuda-activada") !== "si") {
					return;
				}

				var elm = $(this)
					,ajuda_elm = elm.find(".imc-el-ajuda:first");

				if (!ajuda_elm.length || ajuda_elm.html() === "") {
					return;
				}

				// acció!

				var window_W = referent.width(),
					window_H = referent.height(),
					window_scroll_T = referent.scrollTop(),
					elm_T = elm.position().top,
					elm_L = elm.position().left,
					elm_H = elm.outerHeight(true),
					ajuda_W = ajuda_elm.outerWidth(),
					ajuda_H = ajuda_elm.outerHeight();

				var ajuda_T = elm_T-ajuda_H-5+window_scroll_T;

				ajuda_elm
					.removeClass("imc--dalt");

				var ajuda_T_inici = ajuda_T + 5;

				if (window_scroll_T > ajuda_T) {

					ajuda_elm
						.addClass("imc--dalt");

				}

				console
					.log("elementEntra");
				
				ajuda_elm
					.addClass("imc-el-ajuda-on")
					.off(".appFormsAjudaCamp")
					.on("mouseover.appFormsAjudaCamp, mouseenter.appFormsAjudaCamp", ajudaEntra)
					.on("mouseleave.appFormsAjudaCamp, mouseout.appFormsAjudaCamp", ajudaIx);

			}
			,elementSurt = function(e) {

				if (element.attr("ajuda-activada") !== "si") {
					return;
				}

				var elm = $(this)
					,ajuda_elm = elm.find(".imc-el-ajuda:first");

				console
					.log("elementSurt");

				ajuda_elm
					.removeClass("imc-el-ajuda-on")
					.off(".appFormsAjudaCamp");

			}
			,ajudaEntra = function(e) {

				var ajuda_ = $(this);

				console
					.log("ajudaEntra");

				ajuda_
					.addClass("imc-ajuda-over")
					.removeClass("imc-el-ajuda-on");

			}
			,ajudaIx = function(e) {

				var ajuda_ = $(this);

				ajuda_
					.removeClass("imc-ajuda-over");

			}
			,inicia = function() {

				// revisa posició dreta

				var window_W = referent.width();

				element
					.find(".imc-el-ajuda")
						.each(function() {

							var el_ajuda_ = $(this)
								,el_L = el_ajuda_.closest(".imc-element").position().left
								,el_ajuda_W = el_ajuda_.outerWidth();

							if ((el_L + el_ajuda_W) > window_W) {

								el_ajuda_
									.addClass("imc--dreta");

							}

						});

				// events

				element
					.off('.appFormsAjudaCamp')
					.on('mouseenter.appFormsAjudaCamp, focus.appFormsAjudaCamp', ".imc-element", elementEntra)
					.on('mouseleave.appFormsAjudaCamp, blur.appFormsAjudaCamp', ".imc-element", elementSurt);

			};

		// inicia

		inicia();

	});

	return this;
}

// /appFormsAjudaCamp

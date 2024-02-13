// appFormsAjudaCamp

$.fn.appFormsAjudaCamp = function(options) {

	var settings = $.extend({
			referent: $(window)
		}, options);

	this.each(function(){

		var element = $(this)
			,referent = settings.referent
			,ajuda_elm = false
			,ajuda_anterior_elm = false
			,onMouseEnter = function() {

				if (element.attr("ajuda-activada") !== "si") {
					return;
				}

				var elm = $(this);

				revisa(elm);

				ajuda_elm = elm.find(".imc-el-ajuda:first");

				if (!ajuda_elm.length || ajuda_elm.html() === "") {
					return;
				}

				// acció!

				ajuda_anterior_elm = ajuda_elm;

				var window_W = referent.width(),
					window_H = referent.height(),
					window_scroll_T = referent.scrollTop(),
					elm_T = elm.position().top,
					elm_L = elm.position().left,
					elm_H = elm.outerHeight(true),
					elm_control = elm.find(".imc-el-control:first, ul:first"),
					elm_control_W = elm_control.outerWidth(),
					elm_control_H = elm_control.outerHeight(),
					ajuda_W = ajuda_elm.outerWidth(),
					ajuda_H = ajuda_elm.outerHeight();

				var ajuda_T = elm_T-ajuda_H-5+window_scroll_T;

				ajuda_elm
					.removeClass("imc--dreta")
					.removeClass("imc--dalt");

				if ((elm_L+ajuda_W) > window_W) {

					ajuda_elm
						.addClass("imc--dreta");

				}

				var ajuda_T_inici = ajuda_T+5;

				if (window_scroll_T > ajuda_T) {

					ajuda_elm
						.addClass("imc--dalt");

				}

				ajuda_elm
					.addClass("imc-el-ajuda-on")
					.off(".appFormsAjudaCamp")
					.on("mouseenter.appFormsAjudaCamp", onMouseLeave);

			}
			,onMouseLeave = function() {

				if (element.attr("ajuda-activada") !== "si") {
					return;
				}

				var elm = $(this);

				ajuda_elm = elm.find(".imc-el-ajuda:first");

				if (!ajuda_elm.length || ajuda_elm.html() === "") {
					return;
				}

				ajuda_elm
					.removeClass("imc-el-ajuda-on");

			}
			,enfocat = function(elm) {

				return elm.find("input:first, textarea:first, a.imc-select:first").is(":focus");

			}
			,revisa = function(elm) {

				elm
					.closest(".imc-form-contingut")
						.find(".imc-el-ajuda-on")
							.removeClass("imc-el-ajuda-on");

			}
			,inicia = function() {

				// revisa posició dreta

				var window_W = referent.width();

				element
					.find(".imc-el-ajuda")
						.each(function() {

							var el_ajuda_ = $(this)
								,el_ajuda_L = el_ajuda_.closest(".imc-element").position().left
								,el_ajuda_W = el_ajuda_.outerWidth();

							if ((el_ajuda_L + el_ajuda_W) > window_W) {

								el_ajuda_
									.addClass("imc--dreta");

							}

						});

				// events

				element
					.off('.appFormsAjudaCamp')
					.on('mouseenter.appFormsAjudaCamp, focus.appFormsAjudaCamp', ".imc-element", onMouseEnter)
					.on('mouseleave.appFormsAjudaCamp, blur.appFormsAjudaCamp', ".imc-element", onMouseLeave);


			};

		// inicia

		inicia();

	});

	return this;
}
// /appFormsAjudaCamp
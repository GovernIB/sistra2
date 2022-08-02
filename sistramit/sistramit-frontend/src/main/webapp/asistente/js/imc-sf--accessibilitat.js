// Accessibilitat -> cap, submenú

// appAccessibilitatNav

$.fn.appAccessibilitatNav = function(opcions) {
	var settings = $.extend({
		element: ""
	}, opcions);
	this.each(function(){
		var element = $(this)
			,acc_llista = false
			,activa = function(e) {

				acc_llista = $(this).parent().find("ul:first");

				var estaAmagat = (acc_llista.attr("aria-hidden") === "true") ? true : false;

				if (estaAmagat) {

					mostra();

				} else {

					amaga();

				}

			}
			,mostra = function() {

				acc_llista
					.stop()
					.slideDown(function() {

						acc_llista
							.attr("aria-hidden", "false");

						imc_body
							.on("click.appAccessibilitatNavRevisa", revisaMenu);

					});

			}
			,amaga = function() {

				acc_llista
					.stop()
					.slideUp(function() {

						acc_llista
							.attr("aria-hidden", "true");

					});

				imc_body
					.off(".appAccessibilitatNavRevisa");

			}
			,revisaMenu = function(e) {

				var el_ = $(e.target);

				if (!el_.closest(".imc--accessibilitat-llistat").length) {

					amaga();

				}
 
			}
			,popup = function() {

				// revisa

				revisa();

				// mostrem popup

				$("#imc-accessibilitat")
					.attr("aria-hidden", "false")
					.addClass("imc--on");

				amaga();

			}
			,tanca = function() {

				$("#imc-accessibilitat")
					.attr("aria-hidden", "true")
					.removeClass("imc--on");

			}
			,revisa = function() {

				var estils = (localStorage.getItem("goib_sistra2tramit_estils")) ? localStorage.getItem("goib_sistra2tramit_estils") : "pd";
				
				$("#imc-accessibilitat")
					.find("input[value='"+estils+"']")
						.prop("checked", true);

			},
			canvia = function() {

				var input_sel_val = $("#imc-accessibilitat").find("input:checked").val();

				imc_html
					.attr("data-estil", input_sel_val);

				localStorage
					.setItem("goib_sistra2tramit_estils", input_sel_val);

				// info

				var text = (input_sel_val === "pd") ? txtAccCanviaEstilsPerDefecte : txtAccCanviaEstilsAltContrast;

				imc_missatge
					.appMissatge({ accio: "informa", titol: txtAccCanviaEstilsTitol, text: text });

				// tanquem popup

				tanca();

			};

		// events
		
		element
			.off(".appAccessibilitatNav")
			.on("click.appAccessibilitatNav", "button[data-accio=acc-menu]", activa)
			.on("click.appAccessibilitatNav", "button[data-accio=acc-popup]", popup)
			.on("click.appAccessibilitatNav", "button[data-accio=acc-tanca]", tanca)
			.on("click.appAccessibilitatNav", "button[data-accio=acc-canvia]", canvia);

	});
	return this;
}


imc_body
	.appAccessibilitatNav();


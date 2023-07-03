// Seccions navegador

// appNavSeccions

$.fn.appFormsSeccionsNav = function(opcions) {
	var settings = $.extend({
		element: ""
	}, opcions);
	this.each(function(){
		var element = $(this)
			,seccions_contenidor = $("#imc--nav-seccions")
			,seccions_llista = seccions_contenidor.find("ul:first")
			,form_contenidor = element.find(".imc--contingut.imc--form:first")
			,activa = function() {

				var estaAmagat = (seccions_llista.attr("aria-hidden") === "true") ? true : false;

				if (estaAmagat) {

					mostra();

				} else {

					amaga();

				}

			}
			,mostra = function() {

				seccions_llista
					.stop()
					.slideDown(function() {

						seccions_llista
							.attr("aria-hidden", "false");

						imc_forms_body
							.on("click.appFormsSeccionsNav", revisa);

					});

			}
			,amaga = function() {

				seccions_llista
					.stop()
					.slideUp(function() {

						seccions_llista
							.attr("aria-hidden", "true");

					});

				imc_forms_body
					.off(".appFormsSeccionsNav");

			}
			,ves = function(e) {

				e.preventDefault();

				var bt_ = $(this)
					,seccio_id = bt_.attr("href").substr(1)
					,form_contenidor_T = form_contenidor.scrollTop();

				//consola( $("#"+seccio_id).position().top + " - " + form_contenidor_T )

				document
					.getElementById(seccio_id)
						.scrollIntoView({ behavior: "smooth" });

				$("#"+seccio_id)
					.addClass("imc--secc-destacat");

				setTimeout(
					function() {

						$("#"+seccio_id)
							.removeClass("imc--secc-destacat");

					}
					,2000
				);

				/*
				form_contenidor
					.stop()
					.animate(
						{
							scrollTop: $("#"+seccio_id).position().top + form_contenidor_T
						}
						,200
					);*/

			}
			,revisa = function(e) {

				var el_ = $(e.target);

				if (!el_.closest(".imc--secc").length) {

					amaga();

				}
 
			};

		// inicia

		var seccio_tit = element.find("h4")
			,seccio_nav = element.find(".imc--nav-seccions:first ul");

		if (seccio_tit.length) {

			seccio_nav
				.html("");

			seccio_tit
				.each(function(i) {

					var tit_el = $(this)
						,tit_text = tit_el.find(".imc-se-titol").text();

					var html_svg_secc = '<svg height="100%" width="100%" xmlns="http://www.w3.org/2000/svg">'
									    +'<rect class="shape" height="100%" width="100%" />'
									  +'</svg>';

					tit_el
						.addClass("imc--secc-destaca")
						.attr("id", "secc"+i)
						.prepend( html_svg_secc );

					var tit_a = $("<a>").attr("href", "#secc"+i).html( $("<span>").text( tit_text ) );

					$("<li>")
						.html( tit_a )
						.appendTo( seccio_nav );

				});

		}

		// events
		
		element
			.off(".appFormsSeccionsNav")
			.on("click.appFormsSeccionsNav", "button[data-accio=seccio-obri]", activa)
			.on("click.appFormsSeccionsNav", ".imc--nav-seccions ul li a", ves);

	});
	return this;
}

// index

var APP_IDIOMA;

var imc_finestra,
	imc_contenidor,
	imc_cap,
	imc_molla_pa,
	imc_contingut,
	imc_missatge;

var imc_bt_desconecta,
	imc_bt_desa,
	imc_bt_elimina;


// onReady

$(function(){
	
	APP_IDIOMA = $("html").attr("lang");

	imc_finestra = $(window);
	imc_contenidor = $("#imc-contenidor");
	imc_cap = $("#imc-cap");
	imc_cap_c = imc_cap.find(".imc--c:first");
	imc_molla_pa = $("#imc-molla-pa");
	imc_contingut = $("#imc-contingut");
	imc_missatge = $("#imc-missatge");

	imc_bt_desconecta = $("#imc-bt-desconecta");
	imc_bt_desa = $("#imc-bt-desa");
	imc_bt_elimina = $("#imc-bt-elimina");

	// botó cap

	imc_cap_c
		.appCap();

	var resize_appCap;
	imc_finestra
		.on('resize', function(e) {
			clearTimeout(resize_appCap);
			resize_appCap = setTimeout(function() {
				
				imc_cap_c
					.appCap();

			}, 300);
		});

	// desconecta

	imc_bt_desconecta
		.appDesconecta();

	// botó elimina

	imc_bt_elimina
		.appTramitacioElimina();

	// pas?

	var pas = imc_contingut.attr("data-tipus");
	
	// Emplenar formularis

	if (pas === "C") {

		$.getScript("js/literals/imc-sf--calSaber-"+APP_IDIOMA+".js");
		$.getScript("js/imc-sf--calSaber.js");

	} else if (pas === "E") {

		$.getScript("js/imc-sf--emplenar.js");

	} else if (pas === "A") {

		$.getScript("js/imc-sf--annexar.js");

	} else if (pas === "P") {

		$.getScript("js/imc-sf--pagar.js");

	} else if (pas === "R") {

		$.getScript("js/imc-sf--registrar.js");

	} else if (pas === "F") {

		$.getScript("js/imc-sf--finalitzar.js");

	}

	// missatge informatiu
	/*
	imc_missatge
		.appMissatge({ titol:"Benvingut", text: "Pàgina example de tramitació telemàtica" });
	*/
});


// appCap

$.fn.appCap = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			dades_el = element.find(".imc--dades:first"),
			dades_W = false,
			dades_usuari_W = false,
			dades_usuari_H = false,
			dades_clau_H = false,
			dades_desa_H = false,
			altura_total = false,
			verifica = function() {

				dades_el
					.removeAttr("style")
					.removeClass("imc--obert");

				if (dades_el.css("opacity") === "0") {

					inicia();

				}

			},
			inicia = function() {

				dades_W = dades_el.css("width");
				dades_usuari_W = dades_el.find(".imc--usuari:first").css("width");
				dades_usuari_H = dades_el.find(".imc--usuari:first").outerHeight();
				dades_clau_H = dades_el.find(".imc--clau:first").outerHeight();
				dades_desa_H = dades_el.find(".imc--desa:first").outerHeight();

				altura_total = (dades_W === dades_usuari_W) ? dades_usuari_H + dades_clau_H + dades_desa_H : dades_desa_H;

				if (dades_el.css("opacity") === "0") {

					element
						.off('.appCap')
						.on('click.appCap', activa);

				}

			},
			activa = function(e) {

				var bt = $(e.target);

				if (dades_el.hasClass("imc--obert") && !bt.hasClass("imc--desa")) {

					dades_el
						.animate(
							{ height: "0px", opacity: 0 }
							,200
							,function() {

								dades_el
									.removeClass("imc--obert");

							});

				} else {

					dades_el
						.animate(
							{ height: altura_total+"px", opacity: 1 }
							,200
							,function() {

								dades_el
									.addClass("imc--obert");

							});

				}

			};
		
		// verifica
		verifica();
		
	});
	return this;
}


// appDesconecta

$.fn.appDesconecta = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			inicia = function() {

				element
					.off('.appDesconecta')
					.on('click.appDesconecta', avis);

			},
			avis = function() {

				imc_missatge
					.appMissatge({ boto: element, accio: "desconecta", titol:"Està segur de voler sortir?", text: "Recorde desar el número de clau abans de sortir si vol recuperar la tramitació en un futur." });

			};
		
		// inicia
		inicia();
		
	});
	return this;
}


// appTramitacioElimina

$.fn.appTramitacioElimina = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			inicia = function() {

				element
					.off('.appTramitacioElimina')
					.on('click.appTramitacioElimina', obri);

			},
			obri = function() {

				imc_missatge
					.appMissatge({ boto: element, accio: "esborra", titol:"Està segur?", text: "Va a esborrar la tramitació completament" });

			};
		
		// inicia
		inicia();
		
	});
	return this;
}


// appMissatge

$.fn.appMissatge = function(options) {
	var settings = $.extend({
		boto: false,
		accio: "informa",
		titol: "",
		text: "",
		araAmaga: false,
		alMostrar: function() {},
		alAmagar: function() {},
		alAcceptar: function() {},
		alCancelar: function() {}
	}, options);
	this.each(function(){
		var element = $(this),
			boto = settings.boto,
			accio = settings.accio,
			element_c = element.find(".imc--c:first"),
			element_text = element.find(".imc--text:first"),
			titol_txt = settings.titol,
			text_txt = settings.text,
			araAmaga = settings.araAmaga,
			alMostrar = settings.alMostrar,
			alAmagar = settings.alAmagar,
			alAcceptar = settings.alAcceptar,
			alCancelar = settings.alCancelar,
			accepta_bt = element.find("button[data-tipus='accepta']:first"),
			cancela_bt = element.find("button[data-tipus='cancela']:first"),
			ico_anim = false;
			inicia = function() {

				if (araAmaga) {

					amaga();
					return;

				}

				accepta_bt
					.off('.appTramitacioElimina')
					.on('click.appTramitacioElimina', accepta);

				cancela_bt
					.off('.appTramitacioElimina')
					.on('click.appTramitacioElimina', cancela);

				element
					.find("h2 span")
						.text( titol_txt )
						.end()
					.find("p")
						.text( text_txt )
						.end()
					.attr("data-accio", accio);

				cancela_bt
					.show();

				if (accio === "informa" || accio === "alerta") {

					cancela_bt
						.hide();

				}

				anima();

			},
			anima = function() {

				element
					.addClass("imc--on");

				if (!boto) {

					mostra(false);
					return;

				}

				var bt_T = boto.offset().top,
					bt_L = boto.offset().left,
					bt_W = boto.outerWidth(),
					bt_H = boto.outerHeight();

				var mi_T = element_text.offset().top,
					mi_L = element_text.offset().left,
					mi_W = element_text.outerWidth(),
					mi_H = element_text.outerHeight();

				ico_anim = $("<div>").addClass("imc-missatge-anim").css({ top: bt_T+"px", left: bt_L+"px", width: bt_W+"px", height: bt_H+"px", opacity: 0 }).appendTo( element_c );

				ico_anim
					.animate(
						{ top: "50%", left: "50%", width: mi_W+"px", height: mi_H+"px", marginTop: "-"+(mi_H/2)+"px", marginLeft: "-"+(mi_W/2)+"px", opacity: 1 }
						,200
						,function() {

							mostra(true);

						});

			},
			mostra = function(desDeBoto) {

				alMostrar();

				element_text
					.addClass("imc--on");

				if (desDeBoto) {

					ico_anim
						.addClass("imc--off");

				}

				setTimeout(
					function() {

						if (desDeBoto) {

							ico_anim
								.remove();

						}

					}, 300);

				element_c
					.off('.appTramitacioElimina')
					.on('click.appTramitacioElimina', amagaFons);

			},
			accepta = function() {

				alAcceptar();

				if (accio === "informa" || accio === "alerta") {
					
					amaga();

				}

			},
			cancela = function() {

				alCancelar();

				amaga();

			},
			amagaFons = function(e) {

				var el_click = $(e.target),
					estaDins = el_click.closest(".imc--text").length;

				if (!estaDins) {
					amaga();
				}

			},
			amaga = function() {

				alAmagar();

				element
					.addClass("imc--off");

				setTimeout(
					function() {

						element_text
							.removeClass("imc--on");

						element
							.removeClass("imc--on imc--off");


					}, 200);

			};
		
		// inicia
		inicia();
		
	});
	return this;
}
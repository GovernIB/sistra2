// Emplenar formularis

var imc_cs_explicacio,
	imc_bt_cs_explicacio;


// inicia

function appPasCalSaberInicia() {
	
	imc_cs_explicacio = $("#imc--explicacio-detallada");
	imc_bt_cs_explicacio = $("#imc-bt-explicacio-detallada");

	imc_cs_explicacio
		.appExplicacioDetallada();

}


// appExplicacioDetallada

$.fn.appExplicacioDetallada = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			llista = imc_cs_explicacio.find("ul:first"),
			inicia = function() {

				imc_bt_cs_explicacio
					.off('.appExplicacioDetallada')
					.on('click.appExplicacioDetallada', activa);

			},
			activa = function(e) {

				var hasDeObrir = (llista.hasClass("imc--on")) ? false : true;

				if (hasDeObrir) {
					obri();
				} else {
					tanca();
				}

			},
			obri = function() {

				llista
					.stop()
					.slideDown(200)
					.addClass("imc--on");

				imc_bt_cs_explicacio
					.find("span")
						.text( txtExplicacioDetalladaOcultar )
						.end()
					.attr("aria-expanded", "true");

			},
			tanca = function() {

				llista
					.stop()
					.slideUp(200)
					.removeClass("imc--on");

				imc_bt_cs_explicacio
					.find("span")
						.text( txtExplicacioDetalladaMirau )
						.end()
					.attr("aria-expanded", "false");

			};
		
		// inicia
		inicia();
		
	});
	return this;
}

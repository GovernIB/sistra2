// Emplenar formularis

var imc_cs_explicacio,
	imc_bt_cs_explicacio;


// inicia

function appPasCalSaberInicia() {
	
	imc_cs_explicacio = $("#imc--explicacio-detallada");
	imc_bt_cs_explicacio = $("#imc-bt-explicacio-detallada");

	imc_cs_explicacio
		.appExplicacioDetallada();

	/*

	Si no acabau el tràmit podeu utilitzar la clau QMP2TVK4-NTT9GDT8-T8NPIJQL per continuar amb la tramitació. Recordau que si no accediu al tràmit durant 60 dies s'esborrarà del nostre sistema.

	Una vegada finalitzat el tràmit, aquesta clau també us permetrà seguir l'estat de la vostra sol·licitud a través de "Les meves gestions".

	Pitjau el botó per desar la clau. Tot seguit es generarà un fitxer denominat Clau_data_hora.html que contindrà un enllaç per accedir al tràmit.
	
	"Desau la clau i continuau"

	*/

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
					.off('.appFormulari')
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
						.text( txtExplicacioDetalladaOcultar );

			},
			tanca = function() {

				llista
					.stop()
					.slideUp(200)
					.removeClass("imc--on");

				imc_bt_cs_explicacio
					.find("span")
						.text( txtExplicacioDetalladaMirau );

			};
		
		// inicia
		inicia();
		
	});
	return this;
}

// Emplenar formularis

var imc_pagaments;


// onReady

$(function(){
	
	imc_pagaments = imc_contingut.find(".imc--pagaments:first");

	imc_pagaments
		.find("li a")
			.appPagament();

});


// appPagament

$.fn.appPagament = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			inicia = function() {

				element
					.off('.appPagament')
					.on('click.appPagament', obri);

			},
			obri = function(e) {

				imc_missatge
					.appMissatge({ boto: element, titol: "Atenció", text: "Anem a una aplicació externa per finalitzar el pagament." });

			};
		
		// inicia
		inicia();
		
	});
	return this;
}

// Emplenar formularis

var imc_registre,
	imc_bt_registra;


// onReady

$(function(){
	
	imc_registre = imc_contingut.find(".imc--registre:first");
	imc_bt_registra = $("#imc-bt-registra")

	imc_registre
		.appRegistre();

	imc_bt_registra
		.appRegistra();

});


// appRegistre

$.fn.appRegistre = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			inicia = function() {

				element
					.off('.appRegistre')
					.on('click.appRegistre', ".imc--descarrega", descarrega)
					.on('click.appRegistre', ".imc--signa", signa)

			},
			descarrega = function(e) {

				var bt = $(e.target);

				imc_missatge
					.appMissatge({
						boto: bt
						, titol: "Descàrrega"
						, text: "Aquest botó inicia una descàrrega del document."
					});

			},
			signa = function(e) {

				var bt = $(e.target);

				imc_missatge
					.appMissatge({
						boto: bt
						, titol: "Signatura"
						, text: "Anem a signar aquest document."
					});

			};
		
		// inicia
		inicia();
		
	});
	return this;
}


// appRegistra

$.fn.appRegistra = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			inicia = function() {

				element
					.off('.appRegistra')
					.on('click.appRegistra', obri);

			},
			obri = function() {

				imc_missatge
					.appMissatge({
						boto: element
						, accio: "registra"
						, titol:"Atenció. Va a registrar la tramitació"
						, text: "A partir d'aquí es quedará desada la sol·licitud i ja no podrà modificar-la"
						, alAcceptar: function() { document.location="06_pas_finalitzar.html"; }
					});

			};
		
		// inicia
		inicia();
		
	});
	return this;
}

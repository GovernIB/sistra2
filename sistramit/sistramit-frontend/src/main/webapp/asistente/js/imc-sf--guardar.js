// guardar justificant

var imc_guarda,
	imc_bt_desa_justificant,
	imc_bt_tramit_surt;


// onReady

function appPasGuardarInicia() {
	
	imc_guarda = imc_contingut.find(".imc--guarda:first");
	imc_bt_desa_justificant = $("#imc-bt-justificant-desa");
	imc_bt_tramit_surt = $("#imc-bt-tramit-surt");

	imc_guarda
		.appGuarda();

	imc_bt_desa_justificant
		.appJustificantDesa();

	imc_bt_tramit_surt
		.appTramitSurt();

};


// appGuarda

$.fn.appGuarda = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			inicia = function() {

				element
					.off('.appGuarda')
					.on('click.appGuarda', ".imc--descarrega", descarrega);

			},
			descarrega = function(e) {

				var bt = $(this)
					,bt_tipus = bt.attr("data-tipus")
					,elm_id = bt.closest(".imc--reg-doc").attr("data-id")
					,elm_instancia = bt.closest(".imc--reg-doc").attr("data-instancia")
					,url = APP_GUARDA_DESCARREGA
					,id = "idDocumento";

				document.location = url + "?" + id + "=" + elm_id + "&idPaso=" + APP_TRAMIT_PAS_ID + "&instancia=" + elm_instancia;

			};
		
		// inicia
		inicia();
		
	});
	return this;
}


// appJustificantDesa

$.fn.appJustificantDesa = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			envia_ajax = false,
			inicia = function() {

				element
					.off('.appJustificantDesa')
					.on('click.appJustificantDesa', desa);

			},
			desa = function() {

				document.location = APP_TRAMIT_JUSTIFICANT + "?idPaso=" + APP_TRAMIT_PAS_ID;

			};
		
		// inicia
		inicia();
		
	});
	return this;
}


// appTramitSurt

$.fn.appTramitSurt = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			envia_ajax = false,
			inicia = function() {

				element
					.off('.appTramitSurt')
					.on('click.appTramitSurt', surt);

			},
			surt = function(e) {

				var bt = $(this);

				imc_missatge
					.appMissatge({
						accio: "desconecta"
						,boto: bt
						,titol: txtSortirSeguretatTitol
						,alAcceptar: function() { document.location = APP_TRAMIT_SURT; }
					});

			};
		
		// inicia
		inicia();
		
	});
	return this;
}

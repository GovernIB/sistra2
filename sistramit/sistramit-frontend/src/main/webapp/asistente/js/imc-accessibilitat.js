// accessibilitat


var imc_accessibilitat
	,imc_bt_torna_tramit
	,imc_bt_acc_suport
	,imc_form_acc_canvia;


// inicia

function appAccessibilitatInicia() {
	
	imc_accessibilitat = $("#imc-accessibilitat");
	imc_bt_torna_tramit = $("#imc-bt-torna-pas-tramitacio");
	imc_bt_acc_suport = $("#imc-bt-equip-suport-acc");
	imc_form_acc_canvia = $("#imc-acc-form-estils");

	imc_accessibilitat
		.appAcc();

	imc_form_acc_canvia
		.appAccCanvia();

}


// appAcc

$.fn.appAcc = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			inicia = function() {

				imc_bt_torna_tramit
					.attr("href", "#pas/"+JSON_PAS_ACTUAL.datos.actual.id);

				imc_bt_acc_suport
					.off(".appAcc")
					.on("click.appAcc", suport);

			},
			suport = function(e) {

				imc_bt_equip_suport
					.trigger("click");

			};
		
		// inicia
		inicia();
		
	});
	return this;
}


// appAccCanvia

$.fn.appAccCanvia = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			inicia = function() {

				var estils = (localStorage.getItem("goib_sistra2tramit_estils")) ? localStorage.getItem("goib_sistra2tramit_estils") : "pd";
				
				element
					.find("input[value='"+estils+"']")
						.prop("checked", true);

				element
					.off('.appAccCanvia')
					.on('click.appAccCanvia', "button", canvia);

			},
			canvia = function() {

				var input_sel_val = element.find("input:checked").val();

				imc_html
					.attr("data-estil", input_sel_val);

				localStorage
					.setItem("goib_sistra2tramit_estils", input_sel_val);

				// info

				var text = (input_sel_val === "pd") ? txtAccCanviaEstilsPerDefecte : txtAccCanviaEstilsAltContrast;

				imc_missatge
					.appMissatge({ accio: "informa", titol: txtAccCanviaEstilsTitol, text: text });

			};
		
		// inicia
		inicia();
		
	});
	return this;
}

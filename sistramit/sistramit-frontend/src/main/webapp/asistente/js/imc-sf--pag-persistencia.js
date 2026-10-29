// JS


// elements

var imc_body
	,imc_missatge;

var imc_info
	,imc_taula;


// persistència tràmits

$.fn.persistenciaTramits = function() {

	let tramitID
		,envia_ajax = false;

	var esborraHTML = function() {

			imc_taula
				.find("tr[data-id='" + tramitID + "']")
					.remove();

			var tramits_num = imc_taula.find("tbody tr").length;

			if (!tramits_num) {

				imc_body
					.find("h1:first span")
						.text( txtCapTitol );

				imc_info
					.html( txtCapInfo );

				imc_taula
					.remove();

			}
		
		}
		,eliminem = function() {

			var pag_url = esborra_url
				,pag_dades = { id: tramitID };
			
			if (envia_ajax) {

				envia_ajax
					.abort();

			}
				
			envia_ajax =
				$.ajax({
					url: pag_url
					,data: pag_dades
					,method: "post"
					,dataType: "json"
					,headers: {
						[headerCSRF]: tokenCSRF
					}
					,timeout: 30000
				})
				.done(function( data ) {

					var dades_json = data;

					var json_estat = dades_json.estado
						,json_missatge = dades_json.mensaje || false
						,json_missatge_titol = (json_missatge) ? json_missatge.titulo : false;

					if (json_missatge_titol) {

						var json_missatge_text = json_missatge.texto || "";

					}

					var accio_val = (json_estat === "SUCCESS") ? "correcte" : (json_estat === "WARNING") ? "warning" : "error";

					var funcioAlAcceptar = function() {};

					if (json_estat === "SUCCESS" || json_estat === "WARNING") {

						var funcioAlAcceptar = function() { esborraHTML(); };

					}

					envia_ajax = false;

					imc_missatge
						.appMissatge({ accio: accio_val, amagaDesdeFons: false, titol: json_missatge_titol, text: json_missatge_text, alAcceptar: function() { funcioAlAcceptar(); } });
					
				})
				.fail(function(fail_dades, tipus, errorThrown) {

					envia_ajax = false;

					if (tipus === "abort") {
							return false;
						}
					
					imc_missatge
						.appMissatge({ accio: "error", amagaDesdeFons: false, titol: txtErrorTitol, text: txtErrorText });
					
				});

		}
		,eliminant = function() {

			imc_missatge
				.appMissatge({ accio: "carregant", amagaDesdeFons: false, titol: txtEsborrantTitol, text: "", alMostrar: function() { eliminem(); } });

		}
		,elimina = function(e) {

			var bt_ = $(this);

			tramitID = bt_.closest("tr").attr("data-id");

			imc_missatge
				.appMissatge({ accio: "esborra", amagaDesdeFons: false, titol: txtPreguntaTitol, text: txtPreguntaText, alAcceptar: function() { eliminant(); } });

		};

	// events

	imc_body
		.off('.persistenciaTramits')
		.on('click.persistenciaTramits', "button[data-accio=tramit-elimina]", elimina);

}


// onReady

$(function(){

	imc_body = $("body");
	imc_missatge = $("#imc-missatge");

	imc_info = imc_body.find(".imc--info:first");
	imc_taula = imc_body.find(".imc--taula:first");


	// inicia

	imc_body
		.persistenciaTramits()

});

// NÚMERO


// validacions de camps numèrics


$.fn.appFormsValidaNumero = function(options) {

	var settings = $.extend({
			el: false
		}, options);

	this.each(function(){
		var element = $(this)
			,inicia = function() {

				// placeholder

				var camp_ = element.find("input, textarea");

				var negatiu_ = camp_.attr("data-negatiu") || false
					,enters_ = parseInt( camp_.attr("data-enters"), 10 ) || false
					,decimals_ = parseInt( camp_.attr("data-decimals"), 10 ) || false
					,separador_ = camp_.attr("data-separador") || false
					,placeholder_ = camp_.attr("data-placeholder") || false;


				// si no hi ha placeholder, acabem

				if (!placeholder_) {
					return;
				}


				// vars separadors

				var camp_placeholder = ""
					,separador_enters = ""
					,separador_decimals = ""
					,enters_txt = ""
					,decimals_txt = "";


				// separador tipus

				if (separador_ === "pc") {

					separador_enters = ".";
					separador_decimals = ",";

				} else if (separador_ === "cp") {

					separador_enters = ",";
					separador_decimals = ".";

				}


				// decimals

				if (decimals_) {

					for (let i = 0; i < decimals_; i++) {
						decimals_txt += "0";
					}

				}

				if (!decimals_) {
					separador_decimals = "";
				}


				// enterns

				if (enters_) {

					for (let i = 0; i < enters_; i++) {

						if (i%3 === 0 && i !== 0) {
							enters_txt = separador_enters + enters_txt;		
						}

						enters_txt = "0" + enters_txt;
					}

				}

				if (!enters_ || enters_ === 1) {
					enters_txt = "0";
				}


				// placeholder per a tots els camps

				camp_placeholder = enters_txt + separador_decimals + decimals_txt;


				// pintem placeholder

				camp_
					.attr("placeholder", camp_placeholder);

			}
			,revisa = function(e) {

				var tecla_ = e.keyCode;

				var input = $(this)
					,enters_ = parseInt( input.attr("data-enters"), 10 ) || false
					,decimals_ = parseInt( input.attr("data-decimals"), 10 ) || false
					,separador_ = input.attr("data-separador") || false
					,negatiu_ = (input.attr("data-negatiu") === "s") ? true : false
					,esCorrecte = true;


				// números

				if ( (tecla_ < 48 || tecla_ > 57) && (tecla_ < 96 || tecla_ > 105) && tecla_ !== 8 && tecla_ !== 9 && tecla_ !== 39 && tecla_ !== 37 && tecla_ !== 46) {

					esCorrecte = false;

				}


				// punts i coma

				if (decimals_ && (tecla_ == 110 || tecla_ == 188 || tecla_ == 190)) {

					esCorrecte = true;

				}

				// enters > 3 sense decimals

				if (enters_ > 3 && !decimals_ && separador_ === "pc" && (tecla_ == 110 || tecla_ == 190)) {

					esCorrecte = true;

				}

				if (enters_ > 3 && !decimals_ && separador_ === "cp" && tecla_ == 188) {

					esCorrecte = true;

				}


				// negatius

				if (negatiu_ && (tecla_ == 109 || tecla_ == 173)) {

					esCorrecte = true;

				}

				//console.log(e.keyCode + " // " + esCorrecte)


				// es correcte?

				if ( !esCorrecte ) {

					e.preventDefault();
					return;

				}

			};

		// inicia

		inicia();

		// events

		element
			.off(".appFormsValidaNumero")
			.on("keydown.appFormsValidaNumero", "input, textarea", revisa);

	});

	return this;
}

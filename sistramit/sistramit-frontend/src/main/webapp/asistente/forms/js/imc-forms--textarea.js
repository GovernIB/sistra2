// appTextareaAmplaria

$.fn.appTextareaAmplaria = function(options) {

	var settings = $.extend({
			amplaria: false
		}, options);

	this.each(function(){

		var element = $(this)
			,amplaria_max = settings.amplaria
			,revisa = function(e) {

				var textarea_valor_ = element.val()
                    ,salt_linia = textarea_valor_.match(/(\r\n|\n|\r)/g)
                    ,salt_linia_size = (salt_linia !== null) ? salt_linia.length : 0
                    ,textarea_amplaria_ = textarea_valor_.length + salt_linia_size;

				if (textarea_amplaria_ >= amplaria_max) {

					var el_valor = element.val()
						,el_valor_str = el_valor.substr(0, amplaria_max);

					element
						.val( el_valor_str );
					
					return;
				}

			}
			,inicia = function() {

				// revisa posició dreta

				amplaria_max = parseInt( element.attr("data-amplaria"), 10 );

				// events

				element
					.off('.appTextareaAmplaria')
					.on('keyup.appTextareaAmplaria,', revisa);

			};

		// inicia

		inicia();

	});

	return this;
}

// /appTextareaAmplaria
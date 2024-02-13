// IMATGE


// validacions de camps numèrics


$.fn.appFormsElementImatge = function(options) {

	var settings = $.extend({
			el: false
		}, options);

	this.each(function(){
		var element = $(this)
			,inicia = function() {

				var imatges_ = imc_forms_finestra.find("div[data-tipus=imagen]");

				imatges_
					.each(function() {

						var el_ = $(this)
							,cont_ = el_.find("imc--img-cont:first")
							,img_ = el_.find("img:first");

						var el_altura_ = el_.attr("data-altura")
							,el_amplaria = el_.attr("data-amplaria")
							,el_posicio = el_.attr("data-posicio")

						if (el_altura_ && el_altura_ !== "" && el_altura_ !== null) {

							img_
								.css("height", el_altura_);

						}

						if (el_amplaria && el_amplaria !== "" && el_amplaria !== null) {

							img_
								.css("height", el_amplaria);

						}

						if (el_posicio && el_posicio !== "" && el_posicio !== null) {

							img_
								.css("position", "relative");

							var posicions_ = el_posicio.split(",");

							if (posicions_[0] && posicions_[0] !== "") {

								img_
									.css("top", posicions_[0]);

							}

							if (posicions_[1] && posicions_[1] !== "") {

								img_
									.css("left", posicions_[1]);

							}

						}

					});



			};

		// inicia

		inicia();

	});

	return this;
}

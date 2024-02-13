// appFormsCampClicat

var FORMS_CAMP_CLICAT
	FORMS_CAMP_CLICAT_ID = false;

$.fn.appFormsCampClicat = function(options) {

	var settings = $.extend({
			referent: $(window)
		}, options);

	this.each(function(){

		var element = $(this)
			,clicat = function() {

				var elm = $(this);

				FORMS_CAMP_CLICAT = elm;
				FORMS_CAMP_CLICAT_ID = elm.attr("data-id");

			}
			,tabula = function(e) {

				var tecla = e.keyCode;

				if (tecla === 9) {

					FORMS_CAMP_CLICAT = false;
					FORMS_CAMP_CLICAT_ID = false;

				}

			};

		// clicat

		element
			.off('.appFormsCampClicat')
			.on('click.appFormsCampClicat', ".imc-element", clicat)
			.on('keyup.appFormsCampClicat', tabula);

	});

	return this;
}

// /appFormsCampClicat
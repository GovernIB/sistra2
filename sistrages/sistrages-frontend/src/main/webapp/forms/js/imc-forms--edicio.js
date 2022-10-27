// inicia

// elements

var imc_finestra,
	imc_contenidor,
	imc_forms_formulari;

var APP_CONTEXT = "";

function inicio(){
	imc_finestra = $(window);
	imc_contenidor = $("#imc-contenidor");
	imc_forms_formulari = $("#imc-forms-formulari");
	imc_forms_formulari.appElement();
	imc_forms_formulari.appScript();

	/*
	imc_forms_formulari
		.appOnLoad();
	*/
	imc_forms_formulari.appSetFocus({ id: idComponente });
}

// onReady

$(function(){
	inicio();
});

// Script

$.fn.appScript = function(options) {
	var settings = $.extend({
		id: false
	}, options);
	this.each(function(){
		var element = $(this),
			form_elms = element.find(".imc-element"),
			inicia = function() {

				form_elms
					.each(function() {

						var el = $(this)
							,el_script = el.attr("data-script")
							,ico_script = $("<span>").addClass("imc-el--script");

						if (el_script && el_script === "s") {

							var elm_onAplica = (el.hasClass("imc-el-hidden")) ? el : el.find(".imc-el-control");

							elm_onAplica
								.append( ico_script )
								.addClass("imc-el--relatiu");

						}

					});

			};

		// inicia
		inicia();

	});
	return this;
}




// appOnLoad

$.fn.appOnLoad = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			inicia = function() {

				alert("OnLoad!");

			};

		// inicia
		inicia();

	});
	return this;
}


//appSetFocus

$.fn.appSetFocus = function(options) {

    var settings = $.extend({
            id: false
            ,referent: $("#imc-contenidor")
        }, options);

    this.each(function(){

        var element = $(this)
            ,id = settings.id
            ,referent = settings.referent
            ,inicia = function() {
                if (!id) {
                    return;
                }

        var el_selec = element.find(".imc--el-seleccionat:first");

        if (el_selec.length) {
            el_selec.removeClass("imc--el-seleccionat");
        }



                var el = referent.find("[data-id='"+id+"']:first");

                if (el.length) {

                    var referent_scroll_T = referent.scrollTop()
                        ,el_T = el.position().top + referent_scroll_T;

                    referent
                        .animate(
                            {
                                scrollTop: el_T+"0px"
                            }
                            ,500
                            , function() {

                                el
                                    .addClass("imc--el-seleccionat")
                                    .focus();

                            }
                        );

                }

            };

        // inicia
        inicia();

    });
    return this;
}




// appElement

$.fn.appElement = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			elements_f = element.find(".imc-element"),
			inicia = function() {

				elements_f
					.off('.appElement')
					.on('mouseenter.appElement', mouseEnter)
					.on('mouseleave.appElement', mouseLeave)
					.on('click.appElement', selecciona);

				imc_finestra
					.on("keyup.appElement", mou);

			},
			mouseEnter = function(e) {

				var element_f = $(this);

				element_f
					.addClass("imc--el-remarca");

			},
			mouseLeave = function(e) {

				var element_f = $(this);

				element_f
					.removeClass("imc--el-remarca");

			},
			selecciona = function(e) {

				if (element.hasClass("imc-el-hidden") && element.attr("data-hidden") !== "visible") {
					return;
				}

				element
					.find(".imc--el-seleccionat:first")
						.removeClass("imc--el-seleccionat");

				var element_f = $(this);

				element_f
					.addClass("imc--el-seleccionat");

				//rcEditarComponente(element_f.attr(APP_CAMPO_ID));
				rcEditarComponente2(element_f.attr(APP_CAMPO_ID), element_f.attr(APP_CAMPO_TIPO_SECCION), element_f.attr(APP_CAMPO_SECCION_ID),element_f.attr(APP_CAMPO_SECCION_FORM_ID) );

			},
			mou = function(e) {

				// 38 amunt, 40 avall, 39 dreta, 37 esquerre

				if (e.keyCode === 39 || e.keyCode === 37) {

					var el_selec = element.find(".imc--el-seleccionat:first"),
						el_selec_nou = (e.keyCode === 39) ? el_selec.nextAll(".imc-element:first") : el_selec.prevAll(".imc-element:first");

					if (el_selec_nou.length) {

						el_selec
							.removeClass("imc--el-seleccionat");

						el_selec_nou
							.addClass("imc--el-seleccionat");

						revisaVisibilitat(el_selec_nou);
						//rcEditarComponente(el_selec_nou.attr(APP_CAMPO_ID));
						rcEditarComponente2(el_selec_nou.attr(APP_CAMPO_ID), el_selec_nou.attr(APP_CAMPO_TIPO_SECCION), el_selec_nou.attr(APP_CAMPO_SECCION_ID),el_selec_nou.attr(APP_CAMPO_SECCION_FORM_ID));
					}

				} else if (e.keyCode === 40 || e.keyCode === 38) {

					var el_selec = element.find(".imc--el-seleccionat:first"),
						el_selec_T = el_selec.offset().top,
						el_selec_L = el_selec.offset().left;

					var el_selec_nou = false,
						e_keyCode = e.keyCode;

					elements_f
						.each(function(e) {

							var el = $(this),
								el_T = el.offset().top,
								el_L = el.offset().left;

							if (e_keyCode === 40 && el_selec_T < el_T) {

								el_selec_nou = el;
								return false;

							} else if (e_keyCode === 38 && el_selec_T === el_T) {

								el_selec_nou = el.prevAll(".imc-element:first");
								return false;

							}

						});

					if (el_selec_nou.length) {

						el_selec
							.removeClass("imc--el-seleccionat");

						el_selec_nou
							.addClass("imc--el-seleccionat");

						revisaVisibilitat(el_selec_nou, e_keyCode);
						//rcEditarComponente(el_selec_nou.attr(APP_CAMPO_ID));
						rcEditarComponente2(el_selec_nou.attr(APP_CAMPO_ID), el_selec_nou.attr(APP_CAMPO_TIPO_SECCION), el_selec_nou.attr(APP_CAMPO_SECCION_ID),el_selec_nou.attr(APP_CAMPO_SECCION_FORM_ID));
					}

					e.preventDefault();

				}

			},
			revisaVisibilitat = function(elem, e_keyCode) {

				var viewport_T = imc_contenidor.scrollTop(),
					viewport_B = viewport_T + imc_contenidor.height() - 100,
					elem_T = viewport_T + elem.position().top,
					elem_B = elem_T + elem.outerHeight();

				var imc_contenidor_scroll = (elem_B > viewport_B && e_keyCode === 40) ? viewport_T + elem.outerHeight() + 50 : (elem_B < viewport_T + 50 && e_keyCode === 38) ? viewport_T - elem.outerHeight() - 50 : false;

				if (imc_contenidor_scroll) {
					visibilitza(imc_contenidor_scroll);
				}

			},
			visibilitza = function(imc_contenidor_scroll) {

				imc_contenidor
					.stop()
					.animate({
						scrollTop: imc_contenidor_scroll
					})

			};

		// inicia
		inicia();

	});
	return this;
}







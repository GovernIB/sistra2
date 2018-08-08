// Emplenar formularis

var imc_formularis,
	imc_formulari,
	imc_formulari_c,
	imc_formulari_bt,
	imc_formulari_iframe;


// onReady

function appPasEmplenarInicia() {
	
	imc_formularis = imc_contingut.find(".imc--formularis:first");
	imc_formulari = $("#imc-formulari");
	imc_formulari_c = imc_formulari.find(".imc--c:first");
	imc_formulari_bt = imc_formulari.find("button:first");
	imc_formulari_iframe = imc_formulari.find("iframe:first");

	imc_formularis
		.find("li a")
			.appFormulari();

}


// appFormulari

$.fn.appFormulari = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			ico_anim = false,
			ico_anim_carrega = false,
			iframe_url = false,
			inicia = function() {

				element
					.off('.appFormulari')
					.on('click.appFormulari', obri);

				imc_formulari_bt
					.off('.appFormulari')
					.on('click.appFormulari', tanca);

			},
			obri = function(e) {

				var bt = $(this),
					bt_ico = bt.find(".imc--formulari:first"),
					bt_ico_T = bt_ico.offset().top,
					bt_ico_L = bt_ico.offset().left,
					bt_ico_W = bt_ico.outerWidth(),
					bt_ico_H = bt_ico.outerHeight();

				ico_anim = $("<div>").addClass("imc-ico-anim").css({ top: bt_ico_T+"px", left: bt_ico_L+"px", width: bt_ico_W+"px", height: bt_ico_W+"px" }).appendTo( imc_formulari_c );

				imc_formulari
					.addClass("imc--on");

				ico_anim
					.animate(
						{ top: "50%", left: "50%", borderRadius: "50%" }
						,200
						,function() {

							carrega();

						})
					.addClass("imc--centra");

				iframe_url = bt.attr("data-url");

			},
			carrega = function() {

				ico_anim_carrega = $("<div>").addClass("imc-ico-carrega").appendTo( imc_formulari_c );

				ico_anim_carrega
					.animate(
						{ opacity: "1" }
						,500
						,function() {

							carregant();

						});

			},
			carregant = function() {

				//var ico_anim_text = $("<div>").addClass("imc-ico-carrega-text").text( "Carregant formulari..." ).appendTo( imc_formulari_c );

				imc_formulari_iframe
					.off("")
					.on("load", carregat);

				imc_formulari_iframe
					.attr("src", iframe_url);

			},
			carregat = function() {

				if (imc_formulari_iframe.attr("src") === "") {
					return;
				}

				var window_T = $(window).scrollTop(),
					ifr_T = imc_formulari_iframe.offset().top,
					ifr_L = imc_formulari_iframe.offset().left,
					ifr_W = imc_formulari_iframe.outerWidth(),
					ifr_H = imc_formulari_iframe.outerHeight();

				ico_anim
					.animate(
						{ top: (ifr_T-window_T)+"px", left: ifr_L+"px", width: ifr_W+"px", height: ifr_H+"px", borderRadius: "0" }
						,200
						,function() {

							mostra();

						})
					.removeClass("imc--centra");

			},
			mostra = function() {

				ico_anim_carrega
					.remove();

				imc_formulari_iframe
					.addClass("imc--on");

				ico_anim
					.addClass("imc--off");

				setTimeout(
					function() {

						ico_anim
							.remove();

						$("html, body")
							.addClass("imc--iframe-mostrat");


					}, 300);

				imc_formulari_c
					.off('.appFormulari')
					.on('click.appFormulari', tanca);

			},
			tanca = function() {

				imc_formulari
					.addClass("imc--off");

				$("html, body")
					.removeClass("imc--iframe-mostrat");

				setTimeout(
					function() {

						imc_formulari_iframe
							.removeClass("imc--on");

						imc_formulari
							.find("iframe")
								.attr("src", "")
								.end()
							.removeClass("imc--on imc--off");


					}, 300);

			};
		
		// inicia
		inicia();
		
	});
	return this;
}

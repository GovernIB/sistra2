// MISSATGE


// capa missatge

let forms_missatge_html = ''
		+ '<div class="imc--c">'
			+ '<div class="imc--text">'
				+ '<h2><span></span></h2>'
				+ '<div class="imc--explicacio"></div>'
				+ '<div class="imc--botonera">'
					+ '<button type="button" class="imc-bt imc--cancela" data-tipus="cancela"><span>{{txtFormDinCancela}}</span></button>'
					+ '<button type="button" class="imc-bt imc--ico imc--principal" data-tipus="desa"><span>{{txtFormDinDesarSortir}}</span></button>'
					+ '<button type="button" class="imc-bt imc--secundari" data-tipus="surt"><span>{{txtFormDinSortirSenseDesar}}</span></button>'
					+ '<button type="button" class="imc-bt imc--ico imc--principal" data-tipus="accepta"><span>{{txtFormDinAccepta}}</span></button>'
					+ '<button type="button" class="imc-bt imc--cancela" data-tipus="tanca"><span>{{txtFormDinTanca}}</span></button>'
				+ '</div>'
			+ '</div>'
		+ '</div>';

$("<div>")
	.attr({ "id": "imc-forms--missatge", "data-accio": "informa", "aria-hidden": "true", "role": "alertdialog", "tabindex": "0" })
	.addClass("imc-forms--missatge")
	.html( forms_missatge_html )
	.appendTo("body")

var imc_forms_missatge = $("#imc-forms--missatge");


// literals

imc_forms_missatge
	.find("button[data-tipus=cancela] span")
		.text( txtFormDinCancela )
		.end()
	.find("button[data-tipus=desa] span")
		.text( txtFormDinDesarSortir )
		.end()
	.find("button[data-tipus=surt] span")
		.text( txtFormDinSortirSenseDesar )
		.end()
	.find("button[data-tipus=accepta] span")
		.text( txtFormDinAccepta )
		.end()
	.find("button[data-tipus=tanca] span")
		.text( txtFormDinTanca );


// appFormsMissatge

$.fn.appFormsMissatge = function(options) {

	var settings = $.extend({
			boto: false
			,accio: "informa"
			,titol: ""
			,text: ""
			,bt: false
			,araAmaga: false
			,amagaDesdeFons: true
			,alMostrar: function() {}
			,alAmagar: function() {}
			,alAcceptar: function() {}
			,alCancelar: function() {}
			,alTancar: function() {}
		}, options);

	this.each(function(){
		var element = $(this)
			,boto = settings.boto
			,accio = settings.accio
			,element_c = element.find(".imc--c:first")
			,element_text = element.find(".imc--text:first")
			,titol_txt = settings.titol
			,text_txt = settings.text
			,bt = settings.bt
			,araAmaga = settings.araAmaga
			,amagaDesdeFons = settings.amagaDesdeFons
			,alMostrar = settings.alMostrar
			,alAmagar = settings.alAmagar
			,alAcceptar = settings.alAcceptar
			,alCancelar = settings.alCancelar
			,alTancar = settings.alTancar
			,botonera_elm = element.find(".imc--botonera:first")
			,accepta_bt = element.find("button[data-tipus='accepta']:first")
			,cancela_bt = element.find("button[data-tipus='cancela']:first")
			,tanca_bt = element.find("button[data-tipus='tanca']:first")
			,ico_anim = false
			,inicia = function() {

				if (araAmaga) {

					amaga();
					return;

				}

				accepta_bt
					.off('.appFormsMissatge')
					.on('click.appFormsMissatge', accepta);

				cancela_bt
					.off('.appFormsMissatge')
					.on('click.appFormsMissatge', cancela);

				tanca_bt
					.off('.appFormsMissatge')
					.on('click.appFormsMissatge', tanca);

				element
					.find("h2 span")
						.text( titol_txt )
						.end()
					.attr("data-accio", accio);

				// text HTML

				if (text_txt !== "") {

					text_txt = text_txt.replace("script", "");

				}

				element
					.find(".imc--explicacio:first")
						.html( text_txt );

				element_c
					.off('.appFormsMissatge');

				anima();

			}
			,anima = function() {

				element
					.attr("aria-hidden", "false")
					.addClass("imc--on");

				if (!boto) {

					mostra(false);
					return;

				}

				var bt_T = boto.offset().top,
					bt_L = boto.offset().left,
					bt_W = boto.outerWidth(),
					bt_H = boto.outerHeight();

				var mi_T = element_text.offset().top,
					mi_L = element_text.offset().left,
					mi_W = element_text.outerWidth(),
					mi_H = element_text.outerHeight();

				ico_anim = $("<div>").addClass("imc-missatge-anim").css({ top: bt_T+"px", left: bt_L+"px", width: bt_W+"px", height: bt_H+"px", opacity: 0 }).appendTo( element_c );

				ico_anim
					.animate(
						{ top: "50%", left: "50%", width: mi_W+"px", height: mi_H+"px", marginTop: "-"+(mi_H/2)+"px", marginLeft: "-"+(mi_W/2)+"px", opacity: 1 }
						,200
						,function() {

							mostra(true);

						});

			}
			,mostra = function(desDeBoto) {

				alMostrar();

				element_text
					.addClass("imc--on");

				if (desDeBoto) {

					ico_anim
						.addClass("imc--off");

				}

				setTimeout(
					function() {

						if (desDeBoto) {

							ico_anim
								.remove();

						}

					}, 300);

				if (amagaDesdeFons) {

					element_c
						.on('click.appFormsMissatge', amagaFons);

				}

				element
					.appFormsPopupTabula();

			}
			,accepta = function() {

				alAcceptar();

				if (accio === "informa" || accio === "alerta" || accio === "correcte") {
					
					amaga();

				}

			}
			,cancela = function() {
				
				alCancelar();

				amaga();

			}
			,tanca = function() {

				alTancar();

				amaga();

			}
			,amagaFons = function(e) {

				var el_click = $(e.target),
					estaDins = el_click.closest(".imc--text").length;

				if (!estaDins) {
					amaga();
				}

			}
			,amaga = function() {

				alAmagar();

				element
					.attr("aria-hidden", "true")
					.addClass("imc--off")
					.appFormsPopupTabula({ accio: "finalitza" });

				setTimeout(
					function() {

						element_text
							.removeClass("imc--on");

						element
							.removeClass("imc--on imc--off");


					}, 200);

				// enfoquem al botó llançador

				if (bt) {

					bt
						.focus();

				}

			};
		
		// inicia
		inicia();
		
	});
	return this;
}

// Te vull papi
// JS


// inicia HTML

function iniciaHTML() {
	
	imc_cap_fixe = $("#imc-cap--fixe");
	imc_cap = $("#imc-cap");
	imc_cap_c = imc_cap.find(".imc--c:first");
	imc_tramitacio = $("#imc-tramitacio");
	imc_molla_pa = $("#imc-molla-pa");
	imc_contacte = $("#imc-contacte");
	imc_missatge = $("#imc-missatge");

	imc_bt_desconecta = $("#imc-bt-desconecta");
	imc_bt_desa = $("#imc-bt-desa");
	imc_bt_elimina = $("#imc-bt-elimina");
	imc_bt_equip_suport = $("#imc-bt-equip-suport");

	
	// desa HTML molla pa

	HTML_MOLLA_PA = imc_molla_pa.find("nav:first").html();


	// cap padding

	imc_cap_c
		.appCap();

	var resize_appCap;
	imc_finestra
		.on('resize', function(e) {
			clearTimeout(resize_appCap);
			resize_appCap = setTimeout(function() {
				
				imc_cap_c
					.appCap();

			}, 100);
		});

	// desa clau

	imc_bt_desa
		.appClauDesa();

	// desconecta

	imc_bt_desconecta
		.appDesconecta();

	// botó elimina

	imc_bt_elimina
		.appTramitacioElimina();


	// suport

	imc_contacte
		.find(".imc-input-type")
			.appFitxerAdjunta()
			.end()
		.appSuport();


	// mostra

	imc_carrega_inicial
		.attr("aria-hidden", "true")
		.addClass("imc-amaga");

	setTimeout(
		function() {

			// mostra!!!

			imc_contenidor
				.attr("aria-hidden", "false")
				.addClass("imc-mostra");

			// anar a pas

			document.location = "#pas/" + APP_JSON_TRAMIT_D.idPasoActual;

		},
		200
	);

}

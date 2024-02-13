// VALIDACIONS


$.fn.appValida = function(opcions) {

	var settings = $.extend({
		format: false,
		valor: false
	}, opcions);

	// variables

	var element = $(this[0])
		,format = settings.format
		,valor = settings.valor
		,esCorrecte = false;
		

	// validacions

	if (format === "textarea") {

		esCorrecte = (parseInt( element.attr("data-linies"), 10) >= element.val().split("\n").length) ? true : false;

	}

	if (format === "codipostal") {

		esCorrecte = (/^(?:0[1-9]\d{3}|[1-4]\d{4}|5[0-2]\d{3})$/.test(valor)) ? true : false;

	}

	if (format === "correuelectronic") {

		esCorrecte = (/^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\.)+[a-zA-Z]{2,4}$/.test(valor)) ? true : false;

	}

	if (format === "expresioregular") {

		var regexp = new RegExp( element.attr("data-regexp") );

		esCorrecte = (regexp.test(valor)) ? true : false;

	}

	if (format === "numero") {

		esCorrecte =  true;

		var el_enters = parseInt(element.attr("data-enters"), 10) || false
			,el_decimals = parseInt(element.attr("data-decimals"), 10) || false
			,el_separador = element.attr("data-separador") || false
			,el_negatiu = element.attr("data-negatiu") || false
			,el_rangMin = element.attr("data-rangMin") || false
			,el_rangMax = element.attr("data-rangMax") || false;

		var validacions_arr = []
			,validacions_txt = [];


		// llevem els ceros de l'esquerre

		if (!el_separador) {
		
			valor = valor.replace(/^0+/, '');

			valor = (!valor.length) ? "0" : valor;

			element
				.val( valor );

			if ( valor.charAt(0) === "." || valor.charAt(0) === "," ) {

				valor = "0" + valor;

				element
					.val( valor );

			}

		}

		var valor_natiu = valor;


		// si no té separador, revisem si hi ha . o ,

		if (!el_separador && (valor.indexOf(".") !== -1 || valor.indexOf(",") !== -1)  ) {

			validacions_arr
				.push("e");

			validacions_txt
				.push("separador ERR");

		}


		// si tens separador, transformem a número natiu js (decimals amb punt)

		if (el_separador) {

			var separadorsCorrecte = true;

			var hihaPunt = (valor.indexOf(".") !== -1) ? true : false
				,hihaComa = (valor.indexOf(",") !== -1) ? true : false
				,nomPunts = valor.split(/\./).length -1
				,nomComes = valor.split(/,/).length -1
				,posPunt = valor.lastIndexOf(".")
				,posComa = valor.lastIndexOf(",");
			

			// separador decimals, només 1 separador o cap

			if (el_separador === "pc" && nomComes > 1) {

				separadorsCorrecte = false;

			}

			if (el_separador === "cp" && nomPunts > 1) {

				separadorsCorrecte = false;

			}


			// última posició

			if (el_separador === "pc" && hihaPunt && hihaComa && posComa < posPunt) {

				separadorsCorrecte = false;

			}

			if (el_separador === "cp" && hihaPunt && hihaComa && posComa > posPunt) {

				separadorsCorrecte = false;

			}


			// darrere de separador té que haber 3 cifres

			var darrere_deparador_ = valor_natiu.split(".").pop();
			
			if (el_separador === "pc" && hihaPunt && !hihaComa && (darrere_deparador_.length > 3 || darrere_deparador_.length < 3)) {

				separadorsCorrecte = false;

			} else if (el_separador === "pc" && hihaPunt && darrere_deparador_.length === 3 && darrere_deparador_.indexOf(",") !== -1) {

				separadorsCorrecte = false;

			} else if (el_separador === "pc" && hihaPunt && hihaComa && (darrere_deparador_.split(",")[0].length > 3 || darrere_deparador_.split(",")[0].length < 3)) {

				separadorsCorrecte = false;

			}


			var darrere_deparador_ = valor_natiu.split(",").pop();

			if (el_separador === "cp" && hihaComa && !hihaPunt && (darrere_deparador_.length > 3 || darrere_deparador_.length < 3)) {

				separadorsCorrecte = false;

			} else if (el_separador === "cp" && hihaComa && darrere_deparador_.length === 3 && darrere_deparador_.indexOf(".") !== -1) {

				separadorsCorrecte = false;

			} else if (el_separador === "cp" && hihaPunt && hihaComa && (darrere_deparador_.split(".")[0].length > 3 || darrere_deparador_.split(".")[0].length < 3)) {

				separadorsCorrecte = false;

			}


			// separadors correcte?

			if (!separadorsCorrecte) {

				validacions_arr
					.push("e");

				//console.log("separadorsCorrecte ERR");

			}


			// convertim a natiu JS

			if (el_separador === "pc") {

				valor_natiu = valor_natiu.replace(/\./g, '');
				valor_natiu = valor_natiu.replace(/,/g, ".");

			} else if (el_separador === "cp") {

				valor_natiu = valor.replace(/,/g, '');

			}

		}

		//console.log("Transformem a número natiu js: " + valor_natiu);


		// verifiquem que s'usen caracters numèrics

		var numberExpReg = /^\s*(\+|-)?((\d+(\.\d+)?)|(\.\d+))\s*$/;

		if ( !numberExpReg.test(valor_natiu) ) {

			validacions_arr
				.push("e");

			//console.log("numberExpReg ERR");

		}


		// que és un número correcte

		if ( isNaN(parseFloat(valor_natiu)) || !isFinite(valor_natiu) ) {

			validacions_arr
				.push("e");

			//console.log("que és un número correcte ERR");

		}
		

		// permitix negatius?

		if (el_negatiu === "n" && valor.substr(0,1) === "-") {

			validacions_arr
				.push("e");

			//console.log("permitix negatius? ERR");

		}


		// validem enters

		var valor_enter = valor_natiu.replace("-", "").split(".")[0];

		if ( valor_enter.length > el_enters ) {

			validacions_arr
				.push("e");

			//console.log("validem enters ERR");

		}


		// validem decimals

		if (el_separador) {

			var valor_decimal = (el_separador === "pc") ? valor_natiu.replace("-", "").split(",")[1] : valor_natiu.replace("-", "").split(".")[1];

			if (valor_decimal && valor_decimal.length > el_decimals) {

				validacions_arr
					.push("e");

				//console.log("validem decimals ERR");

			}

			if (el_decimals === 0 && valor.indexOf(".") !== -1) {

				validacions_arr
					.push("e");

				//console.log("validem decimals ERR");

			}

		}


		// rang mínim

		if (el_rangMin && parseFloat(valor_natiu) < parseFloat(el_rangMin)) {

			validacions_arr
				.push("e");

			//console.log("rang mínim ERR");

		}


		// rang màxim

		if (el_rangMax && parseFloat(valor_natiu) > parseFloat(el_rangMax)) {

			validacions_arr
				.push("e");

			//console.log("rang màxim ERR");

		}


		// revisem que tot estiga correcte

		$(validacions_arr)
			.each(function() {

				var el = this;

				if (el == "e") {
					esCorrecte = false;
				}

			});


		// formategem si està tot correcte i hi ha separador

		if (esCorrecte && el_separador) {

			var format_val = '0,0';

			if (el_decimals) {

				var str_decimales = "";

				for(i=0; i<el_decimals; i++) {
					str_decimales += "0";
				}

				var format_val = '0,0.' + str_decimales;

			}

			if (el_separador === "cp") {

				numeral
					.locale('en');

			} else {

				numeral
					.locale('es-es');

			}

			var input_val_format = numeral( valor ).format( format_val );

			element
				.val( input_val_format );

		}

	}

	if (format === "telefon") {

		esCorrecte =  true;

		var validacions_arr = [];

		if (isNaN(valor)) {

			validacions_arr
				.push("e");

		}

		var valor_primerDigit = parseInt( valor.substr(0,1), 10),
			valor_string = valor.toString();

		if (valor_primerDigit !== 9 && valor_primerDigit !== 8 && valor_primerDigit !== 6 && valor_primerDigit !== 7) {

			validacions_arr
				.push("e");

		}

		if (valor_string.length !== 9) {

			validacions_arr
				.push("e");

		}

		var el_fixe = element.attr("data-fixe") || false,
			el_mobil = element.attr("data-mobil") || false;

		if (el_fixe === "n" && (valor_primerDigit === 9 || valor_primerDigit === 8)) {

			validacions_arr
				.push("e");

		}

		if (el_mobil === "n" && (valor_primerDigit === 6 || valor_primerDigit === 7)) {

			validacions_arr
				.push("e");

		}

		$(validacions_arr)
			.each(function() {

				var el = this;

				if (el == "e") {
					esCorrecte = false;
				}

			});

	}

	if (format === "data" && valor !== "") {

		var revisaData = true
			,esCorrecte = true;

		if (valor.indexOf("-") === -1)  {

			var data_sp = valor.split("/")
				,data_sp_size = data_sp.length
				,dataDia = parseInt(data_sp[data_sp_size-3], 10)
				,dataMes = parseInt(data_sp[data_sp_size-2], 10)
				,dataAny = parseInt(data_sp[data_sp_size-1], 10);

			revisaData = (data_sp[data_sp_size-3].indexOf("_") !== -1 || data_sp[data_sp_size-2].indexOf("_") !== -1 || data_sp[data_sp_size-1].indexOf("_") !== -1) ? false : true;

		} else {

			var data_sp = valor.split("-")
				,dataDia = parseInt(data_sp[2], 10)
				,dataMes = parseInt(data_sp[1], 10)
				,dataAny = parseInt(data_sp[0], 10);

		}

		if (revisaData) {

			var d = new Date(dataAny, dataMes-1, dataDia);

			esCorrecte = (d.getFullYear() == dataAny && d.getMonth() == dataMes-1 && d.getDate() == dataDia && dataAny < 9999) ? true : false;

		}

	}

	if (format === "data" && valor === "") {

		esCorrecte = (element.is(":invalid")) ? false : true;

	}

	if (format === "iban" && valor !== "") {

		var valor_iban = $.trim( valor.toUpperCase().replace(/\s/g, "") );

		esCorrecte = IBAN.isValid( valor_iban ) && validaCCC(valor_iban);

	}

	// retorna si és correcte

	return esCorrecte;

}


// valida identificador

var appValidaIdentificador = (function(){

	var LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";

	var calcularLetraDni = function(valor) {
		var dni = parseInt(valor, 10);
		var modulo = dni % 23;
		var letra = LETRAS_DNI.charAt(modulo);
		return letra;
	};

	var obtenerDigitos = function(valor) {
		var digitos = "";
		for (i = 0; i < valor.length; i++) {
			if (!isNaN(valor.charAt(i))) {
				digitos += valor.charAt(i);
			}
        }
		return digitos;
	}

	// dni, nie, nifOtros, nifPJ, nss  (abans: nif, cif, nie, nss)

	return {
		dni: function(valor) {
			valor = valor.toUpperCase();

			if (valor.length != 9) {
				return false;
			}

			var patronNif = "^[0-9]{0,8}[" + LETRAS_DNI + "]{1}$";
			var regExp = new RegExp(patronNif);
			if (!regExp.test(valor)) {
				return false;
			}
			var digitos = obtenerDigitos(valor);
			var letra = calcularLetraDni(digitos);

			return (valor.charAt(8) == letra);

		},
		nie: function(valor) {
			valor = valor.toUpperCase();

			if (valor.length != 9) {
				return false;
			}

			var patronNie = "^[X|Y|Z][0-9]{1,8}[A-Z]{1}$";
			var regExp=new RegExp(patronNie);
			if (!regExp.test(valor)) {
				return false;
			}

			var numero = "0";
			if (valor.charAt(0) == "Y") {
				numero = "1";
			} else if (valor.charAt(0) == "Z"){
				numero = "2";
			}

			var digitos = obtenerDigitos(valor);

			var letra = calcularLetraDni(numero + digitos);

			return (valor.charAt(8) ==  letra);

		},
		nifOtros: function(valor) {
			valor = valor.toUpperCase();

			if (valor.length != 9) {
				return false;
			}

			var patronNifOtros = "^[K|L|M][0-9]{1,8}[A-Z]{1}$";
			var regExp = new RegExp(patronNifOtros);
			if (!regExp.test(valor)) {
				return false;
			}
			var digitos = obtenerDigitos(valor);
			var letra = calcularLetraDni(digitos);

			return (valor.charAt(8) == letra);

		},
		nifPJ: function(valor) {
			valor = valor.toUpperCase();

			var patronCif = "^[ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{7}([0-9]||[ABCDEFGHIJ]){1}$";
			var regExp = new RegExp(patronCif);
			if (!regExp.test(valor)) {
				return false;
			}

			var codigoControl = valor.substring(valor.length - 1, valor.length);

			var v1 = [ 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 ];
			var v2 = [ "J", "A", "B", "C", "D", "E", "F", "G", "H", "I" ];

			var suma = 0;
            for (i = 2; i <= 6; i += 2) {
            	suma += v1[parseInt(valor.substring(i - 1, i), 10)];
                suma += parseInt(valor.substring(i, i + 1));
            }

            suma += v1[parseInt(valor.substring(7, 8))];
            suma = (10 - (suma % 10));
            if (suma == 10) {
                suma = 0;
            }
            var letraControl = v2[suma];
            res = (codigoControl == (suma + "") || codigoControl.toUpperCase() == letraControl);


            // VERIFICAR PRIMERA LLETRA I SI AL FINAL ÉS LLETRA O NÚMERO

            if (res) {

				var finalAmbLletra = "NPQRSW"
					,finalAmbNumero = "ABCDEFGHJUV";

	            var lletraInicial = valor.charAt(0)
	            	codiControlFinal = valor.substring(valor.length - 1, valor.length);

	            if (finalAmbLletra.indexOf(lletraInicial) !== -1 && !isNaN( parseInt(codiControlFinal, 10) ) ) {
	            	res = false;
	            }

	            if (finalAmbNumero.indexOf(lletraInicial) !== -1 && isNaN( parseInt(codiControlFinal, 10) ) ) {
	            	res = false;
	            }

	        }


            return res;

		},
		nss: function(valor) {
			if (!valor) { return false; }
			if (valor.length != 11 && valor.length != 12) { return false; }
			if (valor.substr(2, 1) == 0) { valor = "" + valor.substr(0, 2) + valor.substr(3, valor.length-1); }

			return (valor.substr(0, valor.length-2)%97 == valor.substr(valor.length-2, 2)) ? true : false;

		},
		nif: function(valor) {

			// dni

			valor = valor.toUpperCase();

			if (valor.length != 9) {
				return false;
			}

			var esPatronNIF = false
				,esPatronNIFotros = false
				,esLletraNumero = false;

			var patronNif = "^[0-9]{0,8}[" + LETRAS_DNI + "]{1}$";

			var regExp = new RegExp(patronNif);

			if (regExp.test(valor)) {
				esPatronNIF = true;
			}

			var patronNifOtros = "^[K|L|M][0-9]{1,8}[A-Z]{1}$";

			var regExp = new RegExp(patronNifOtros);

			if (regExp.test(valor)) {
				esPatronNIFotros = true;
			}


			var digitos = obtenerDigitos(valor);
			var letra = calcularLetraDni(digitos);

			if (valor.charAt(8) == letra) {
				esLletraNumero = true;
			}

			// resultat

			var resultat = ((esPatronNIF || esPatronNIFotros) && esLletraNumero) ? true : false;

			return resultat;

		}
	}

})();

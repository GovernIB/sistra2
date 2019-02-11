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

		/*
		"enteros" : 5,
		"decimales" : 2,
		"separador" : "pc",
		"negativo" : "n",
		"rangoMin" : null,
		"rangoMax" : null
		*/

		var el_enters = parseInt(element.attr("data-enters"), 10) || false
			,el_decimals = parseInt(element.attr("data-decimals"), 10) || false
			,el_separador = element.attr("data-separador") || false
			,el_negatiu = element.attr("data-negatiu") || false
			,el_rangMin = element.attr("data-rangMin") || false
			,el_rangMax = element.attr("data-rangMax") || false;

		// si no té separador, revisem si hi ha . o ,

		if (!el_separador) {

			esCorrecte = (valor.indexOf(".") === -1 && valor.indexOf(",") === -1) ? true : false;

		}

		// si tens separador, transformem a número natiu js (decimals amb punt)

		if (el_separador) {

			if (el_separador === "pc") {

				valor = valor.replace(/\./g, '');
				valor = valor.replace(",", ".");

			} else {

				valor = valor.replace(/,/g, '');	

			}

		}

		// verifiquem que s'usen caracters numèrics

		var numberExpReg = /^\s*(\+|-)?((\d+(\.\d+)?)|(\.\d+))\s*$/;  

		esCorrecte = ( numberExpReg.test(valor) ) ? true : false;

		// que és un número correcte

		esCorrecte = ( !isNaN(parseFloat(valor)) && isFinite(valor) ) ? true : false;

		// validem decimals

		if (el_decimals === 0) {

			esCorrecte = ( valor.indexOf(".") !== -1 ) ? false : true;

		}

		// permitix negatius?

		if (el_negatiu === "n") {

			esCorrecte = ( valor.substr(0,1) === "-" ) ? false : true;

		}

		// si no té separador, llevem els ceros de l'esquerre

		if (!el_separador) {

			element
				.val(parseInt(valor, 10));

			valor = element.val();

		}

		// validem enters

		var valor_enter = valor.replace("-", "").split(".")[0];

		esCorrecte = ( valor_enter.length <= el_enters ) ? true : false;

		// validem decimals

		var valor_decimal = valor.replace("-", "").split(".")[1];

		esCorrecte = ( valor_decimal.length <= el_decimals ) ? true : false;

		// rang mínim

		if (el_rangMin) {

			esCorrecte = ( parseFloat(valor) >= parseFloat(el_rangMin) ) ? true : false;


		}

		// rang màxim

		if (el_rangMax) {

			esCorrecte = ( parseFloat(valor) <= parseFloat(el_rangMax) ) ? true : false;

		}

	}

	if (format === "telefon") {

		esCorrecte = (isNaN(valor)) ? false : true;

		var valor_primerDigit = parseInt( valor.substr(0,1), 10),
			valor_string = valor.toString();

		esCorrecte = (valor_primerDigit !== 9 && valor_primerDigit !== 8 && valor_primerDigit !== 6 && valor_primerDigit !== 7) ? false : true;

		esCorrecte = (valor_string.length !== 9) ? false : true;

		var el_fixe = element.attr("data-fixe") || false,
			el_mobil = element.attr("data-mobil") || false;

		esCorrecte = (el_fixe === "n" && (valor_primerDigit === 9 || valor_primerDigit === 8)) ? false : esCorrecte;

		esCorrecte = (el_mobil === "n" && (valor_primerDigit === 6 || valor_primerDigit === 7)) ? false : esCorrecte;

	}

	if (format === "data") {

		esCorrecte = (valor.length !== 10) ? false : true;

		var data = valor.split("/")
			,dataDia = parseInt(data[2], 10)
			,dataMes = parseInt(data[1], 10)
			,dataAny = parseInt(data[1], 10);

		esCorrecte = (dataAny === 0 || dataMes > 12 || dataMes === 0 || dataDia === 0 || dataDia > 31) ? false : true;

		esCorrecte = (dataDia === 31 && (dataMes === 4 || dataMes === 6 || dataMes === 9 || dataMes === 11)) ? false : true;

		esCorrecte = (dataDia === 30 && (dataMes === 1 || dataMes === 3 || dataMes === 5 || dataMes === 7 || dataMes === 8 || dataMes === 10 || dataMes === 12)) ? false : true;

		var dies_febrer = (dataAny % 4 != 0) ? 28 : 29;

		esCorrecte = (dataMes === 2 && dataDia !== dies_febrer) ? false : true;

	}

	// retorna si és correcte

	return esCorrecte;

}


// valida identificador

var appValidaIdentificador = (function(){
 
	var LETRAS_DNI="TRWAGMYFPDXBNJZSQVHLCKE";
	
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
 
	return {
		nss: function(valor) {
			if (!valor) { return false; }
			if (valor.length != 11 && valor.length != 12) { return false; }
			if (valor.substr(2, 1) == 0) { valor = "" + valor.substr(0, 2) + valor.substr(3, valor.length-1); }
			return (valor.substr(0, valor.length-2)%97 == valor.substr(valor.length-2, 2)) ? true : false;
		},
		nif: function(valor) {
			
			valor = valor.toUpperCase();
			
			if (valor.length != 9) { 
				return false;
			}
			
			var patronNif = "^[0-9]{0,8}[" + LETRAS_DNI + "]{1}$";
			var regExp=new RegExp(patronNif);
			if (!regExp.test(valor)) {
				return false;
			}
			var digitos = obtenerDigitos(valor);
			var letra = calcularLetraDni(digitos);
			return (valor.charAt(8) ==  letra);
	        				
		},
		cif: function(valor) {
			valor = valor.toUpperCase();
			
			var patronCif = "^[ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{7}([0-9]||[ABCDEFGHIJ]){1}$";
			var regExp=new RegExp(patronCif);
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
            return res;                
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
							
		}
	}
	
})();

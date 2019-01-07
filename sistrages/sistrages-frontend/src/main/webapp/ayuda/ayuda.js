
	/**
	 * Vuelve hacia atrás en el history
	 * @returns
	 */
    function volver () {
    	window.history.back();
    }

	/**
	 * Devuelve los parametros de la url.
	 * @param parameterName El parametro que se busca, por ejemplo, si url es: <br />
	 * 	URL?param1=valor1&param2=valor2  <br />
	 * findGetParameter('param2') --> valor2
	 *
	 * @returns valor segun el parameterName, en caso de no haber, es null
	 */
	function findGetParameter(parameterName) {
	    var result = null,
	        tmp = [];
	    location.search
	        .substr(1)
	        .split("&")
	        .forEach(function (item) {
	          tmp = item.split("=");
	          if (tmp[0] === parameterName) result = decodeURIComponent(tmp[1]);
	        });
	    return result;
	}

	/**
	 * Prototipo para cargar el breadcrumb con mas parametros.
	 * @returns
	 */
	function cargarUL () {

		alert('ini');

		var elementUL = document.getElementById("luBreadCrumb");
		for(var i = 1 ; i < 10; i++) {
			var texto = findGetParameter("param" + i);
			if (texto == null || texto == '' || texto == undefined) {
				break;
			}
			var url = findGetParameter("paramURL" + i);

			var elementA  = document.createElement("a");
			var elementLI  = document.createElement("li");
			elementA.setAttribute('href',url);
	        var createAText = document.createTextNode(texto);
	        elementA.appendChild(createAText);
	     	elementLI.appendChild(elementA);
			elementUL.appendChild(elementLI);
		}


		alert('sale');
	}


	/**
	 * Hola desde iframe.
	 * @returns
	 */
	function hola() {
		alert("hola desde iframe");
	}

	/**
	 * Hola desde el padre
	 * @returns
	 */
	function holaPadre() {
		window.parent.hola("padre");
	}

	/**
	 * Ocultar ayuda.
	 * @returns
	 */
	function ocultarAyuda() {
		window.parent.ocultar_ayuda();
	}

	/**
	 * Copiar al portapapeles y enviar mensaje.
	 * @param texto
	 * @returns
	 */
	function copiarPortapeles(texto) {

		var textarea = document.createElement('textarea');
		textarea.textContent = texto;
	  	document.body.appendChild(textarea);

	  	var selection = document.getSelection();
	  	var range = document.createRange();
		//  range.selectNodeContents(textarea);
	  	range.selectNode(textarea);
	  	selection.removeAllRanges();
	  	selection.addRange(range);

	  	console.log('copy success', document.execCommand('copy'));
	  	selection.removeAllRanges();

	  	document.body.removeChild(textarea);

	  	window.parent.mensaje_copy();
	}

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
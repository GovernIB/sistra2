// form a un iFrame


// per a rebre i enviar info amb iframes en servidors diferents

/*
window
	.addEventListener("message", function(event) {

		if (event.origin != 'https://localhost') {
			return;
		}

		alert( "He rebut un 'message' de la capa pare: " + event.data );

		// promesa missatge

		if (event.data === "obri") {

			document
				.querySelector("a[data-id='F2']")
					.click();
								
		}

	});
*/
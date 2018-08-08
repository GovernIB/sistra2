// JS


// hashchange

var URL_HASH,
	URL_PARAMETRES;

function urlHash() {
	
	URL_HASH = location.hash.replace(/^.*#/, '');
	URL_PARAMETRES = URL_HASH.split("/");
		
	return URL_HASH, URL_PARAMETRES;
	
}

jQuery(window)
	.on("hashchange", function(){ 
		
		urlHash();
	
	if (URL_PARAMETRES[0] === "pas" && URL_PARAMETRES.length === 2) {
		
		var pas = URL_PARAMETRES[1];

		imc_contingut_c
			.appPas({ pas: pas });
		
	}
	
});

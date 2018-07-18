package es.caib.sistrages.rest.adapter;

import es.caib.sistrages.core.api.model.ValoresDominio;
import es.caib.sistrages.rest.api.interna.RValoresDominio;

public class RValoresDominioAdapter {
	
	private RValoresDominio rValoresDominio;

	public RValoresDominioAdapter(ValoresDominio ori) {
		rValoresDominio = new RValoresDominio();
		rValoresDominio.setCodigoError(ori.getCodigoError());
		rValoresDominio.setDescripcionError(ori.getDescripcionError());
		rValoresDominio.setError(ori.isError());
		for (int i = 0; i < ori.getNumeroFilas(); i++) {
			rValoresDominio.addFila();
			for (String col :  ori.getNombreColumnas()) {			
				rValoresDominio.setValor(i+1, col, ori.getValor(i+1, col));	
			}
		}		
	}

	public RValoresDominio getrValoresDominio() {
		return rValoresDominio;
	}

	public void setrValoresDominio(RValoresDominio rValoresDominio) {
		this.rValoresDominio = rValoresDominio;
	}

}

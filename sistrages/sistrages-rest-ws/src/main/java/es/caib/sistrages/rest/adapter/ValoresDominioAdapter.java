package es.caib.sistrages.rest.adapter;

import org.springframework.stereotype.Component;


import es.caib.sistrages.core.api.model.ValoresDominio;
import es.caib.sistrages.rest.api.interna.RValoresDominio;

/**
 * Adapter para convertir a modelo rest.
 * @author Indra
 */
@Component
public class ValoresDominioAdapter {
	
	/**
	 * Convertir a modelo rest ValoresDominio
	 * @param ori ValoresDominio
	 * @return RValoresDominio
	 */
	public RValoresDominio convertir (ValoresDominio ori) {
		RValoresDominio rValoresDominio = new RValoresDominio();
		rValoresDominio.setCodigoError(ori.getCodigoError());
		rValoresDominio.setDescripcionError(ori.getDescripcionError());
		rValoresDominio.setError(ori.isError());
		for (int i = 0; i < ori.getNumeroFilas(); i++) {
			rValoresDominio.addFila();
			for (String col :  ori.getNombreColumnas()) {			
				rValoresDominio.setValor(i+1, col, ori.getValor(i+1, col));	
			}
		}
		return rValoresDominio;		
	}

}

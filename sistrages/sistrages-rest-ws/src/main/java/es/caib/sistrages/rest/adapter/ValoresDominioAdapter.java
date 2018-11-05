package es.caib.sistrages.rest.adapter;

import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.rest.api.interna.RValoresDominio;

/**
 * Adapter para convertir a modelo rest.
 * 
 * @author Indra
 */
@Component
public class ValoresDominioAdapter {

	/**
	 * Convertir a modelo rest ValoresDominio
	 * 
	 * @param ori
	 *            ValoresDominio
	 * @return RValoresDominio
	 */
	public RValoresDominio convertir(final ValoresDominio ori) {
		final RValoresDominio rValoresDominio = new RValoresDominio();
		rValoresDominio.setCodigoError(ori.getCodigoError());
		rValoresDominio.setDescripcionError(ori.getDescripcionError());
		rValoresDominio.setError(ori.isError());
		for (int i = 0; i < ori.getNumeroFilas(); i++) {
			rValoresDominio.addFila();
			for (final String col : ori.getNombreColumnas()) {
				rValoresDominio.setValor(i + 1, col, ori.getValor(i + 1, col));
			}
		}
		return rValoresDominio;
	}

}

package es.caib.sistrages.rest.adapter;

import org.springframework.stereotype.Component;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.rest.api.interna.RDominio;

/**
 * Adapter para convertir a modelo rest.
 *
 * @author Indra
 *
 */

@Component
public class DominioAdapter {

	/**
	 * Conversion a modelo rest.
	 *
	 * @param dominio
	 */
	public RDominio convertir(final Dominio dominio) {

		final RDominio rDominio = new RDominio();
		rDominio.setCachear(dominio.isCacheable());
		rDominio.setIdentificador(dominio.getIdentificador());
		rDominio.setSql(dominio.getSql());
		rDominio.setTipo(dominio.getTipo().toString());
		rDominio.setUri(dominio.getUrl());
		return rDominio;
	}

}
package es.caib.sistrages.rest.adapter;

import org.springframework.stereotype.Component;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.rest.api.interna.RConfiguracionAutenticacion;
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
		RDominio rDominio = null;
		if (dominio != null) {
			rDominio = new RDominio();
			rDominio.setTipoCache(dominio.getCache().toString());
			rDominio.setIdentificador(dominio.getIdentificadorCompuesto());
			rDominio.setSql(dominio.getSql());
			rDominio.setTipo(dominio.getTipo().toString());
			if (RDominio.TIPO_CONSULTA_BD.equals(rDominio.getTipo())) {
				rDominio.setUri(dominio.getJndi());
			} else {
				rDominio.setUri(dominio.getUrl());
			}
			if (dominio.getConfiguracionAutenticacion() != null) {
				final RConfiguracionAutenticacion rConfAutenticacion = new RConfiguracionAutenticacion();
				rConfAutenticacion.setIdentificador(dominio.getConfiguracionAutenticacion().getIdentificadorCompuesto());
				rConfAutenticacion.setUsuario(dominio.getConfiguracionAutenticacion().getUsuario());
				rConfAutenticacion.setPassword(dominio.getConfiguracionAutenticacion().getPassword());
				rDominio.setConfiguracionAutenticacion(rConfAutenticacion);
			}
			rDominio.setTimeout(dominio.getTimeout());
		}
		return rDominio;
	}

}
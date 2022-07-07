package es.caib.sistrages.rest.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.comun.Propiedad;
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
	public RDominio convertir(final Dominio dominio, final String idEntidad) {
		RDominio rDominio = null;
		if (dominio != null) {
			rDominio = new RDominio();
			rDominio.setIdentificadorEntidad(idEntidad);
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
				rDominio.setIdentificadorConfAutenticacion(
						dominio.getConfiguracionAutenticacion().getIdentificadorCompuesto());
			}
			rDominio.setTimeout(dominio.getTimeout());

			if (dominio.getParametros() != null) {
				final List<String> parametros = new ArrayList<String>();
				for (final Propiedad p : dominio.getParametros()) {
					parametros.add(p.getCodigo());
				}
				rDominio.setParametros(parametros);
			}

		}
		return rDominio;
	}

}
package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistra2.commons.plugins.dominio.api.ParametroDominio;
import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;

/**
 * Dominio Resolucion service.
 *
 * @author Indra.
 *
 */
public interface DominioResolucionService {

	/**
	 * Realiza una consulta a la bbdd.
	 *
	 * @param idFuenteDatos
	 * @param parametros
	 * @return valoresDominio
	 */
	public ValoresDominio realizarConsultaFuenteDatos(String idDominio, List<ValorParametroDominio> parametros);

	/**
	 * Realiza consulta a la bbdd
	 *
	 * @param datasource
	 * @param sql
	 * @param parametros
	 * @return
	 */
	public ValoresDominio realizarConsultaBD(String datasource, String sql, List<ValorParametroDominio> parametros);

	/**
	 * Realiza una consulta remota.
	 *
	 * @param ambito
	 * @param idDominio
	 * @param url
	 * @param parametros
	 * @return
	 */
	public ValoresDominio realizarConsultaRemota(TypeAmbito ambito, Long idEntidad, String idDominio, String url,
			List<ParametroDominio> parametros);

}

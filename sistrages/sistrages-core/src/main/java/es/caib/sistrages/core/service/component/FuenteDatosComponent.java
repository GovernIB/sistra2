package es.caib.sistrages.core.service.component;

import java.util.List;

import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.model.ValorParametroDominio;

public interface FuenteDatosComponent {

	/**
	 * realizarConsultaFuenteDatos
	 *
	 * @param idFuenteDatos
	 * @param parametros
	 * @return valoresDominio
	 */
	ValoresDominio realizarConsultaFuenteDatos(String idDominio, List<ValorParametroDominio> parametros);

	/**
	 * Realiza consulta BD
	 *
	 * @param datasource
	 * @param sql
	 * @param parametros
	 * @return
	 */
	ValoresDominio realizarConsultaBD(String datasource, String sql, List<ValorParametroDominio> parametros);

}

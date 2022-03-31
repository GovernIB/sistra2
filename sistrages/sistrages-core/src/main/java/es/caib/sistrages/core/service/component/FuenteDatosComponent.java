package es.caib.sistrages.core.service.component;

import java.util.List;

import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;

public interface FuenteDatosComponent {

	/**
	 * realizarConsultaFuenteDatos
	 * @param ambito
	 * @param idEntidad
	 * @param idArea
	 * @param idDominio
	 * @param parametros
	 * @return
	 */
	ValoresDominio realizarConsultaFuenteDatos(final TypeAmbito ambito, final String idEntidad, final String idArea, final String idDominio, final List<ValorParametroDominio> parametros);

	/**
	 * Realiza consulta BD
	 *
	 * @param datasource
	 * @param sql
	 * @param parametros
	 * @return
	 */
	ValoresDominio realizarConsultaBD(String datasource, String sql, List<ValorParametroDominio> parametros);

	/**
	 * Realiza consulta lista fija
	 * @param ambito
	 * @param codigoEntidad
	 * @param codigoArea
	 * @param identificador
	 * @return
	 */

	ValoresDominio realizarConsultaListaFija(TypeAmbito ambito, Long codigoEntidad, Long codigoArea,
			String identificador, String identificadorEntidad, String identificadorArea);

}

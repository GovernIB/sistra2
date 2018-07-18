package es.caib.sistrages.core.service.component;

import java.util.List;

import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.ValoresDominio;

public interface FuenteDatosComponent {
	
	/**
	 * realizarConsultaFuenteDatos
	 * @param idFuenteDatos
	 * @param parametros
	 * @return valoresDominio
	 */
	ValoresDominio realizarConsultaFuenteDatos(String idDominio, List<ValorParametroDominio> parametros);

}

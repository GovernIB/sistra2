package es.caib.sistrages.core.service.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.service.repository.dao.DominioDao;
import es.caib.sistrages.core.service.repository.dao.FuenteDatoDao;

@Component
public class FuenteDatosComponentImpl implements FuenteDatosComponent {

	@Autowired
	FuenteDatoDao fuenteDatosDao;

	@Autowired
	DominioDao dominioDao;

	@Override
	public ValoresDominio realizarConsultaFuenteDatos(final TypeAmbito ambito, final String idEntidad, final String idArea, final String idDominio, final List<ValorParametroDominio> parametros) {
		return fuenteDatosDao.realizarConsultaFuenteDatos(ambito, idEntidad, idArea, idDominio, parametros);
	}

	@Override
	public ValoresDominio realizarConsultaBD(final String datasource, final String sql,
			final List<ValorParametroDominio> parametros) {
		return fuenteDatosDao.realizarConsultaBD(datasource, sql, parametros);
	}

	@Override
	public ValoresDominio realizarConsultaListaFija(final TypeAmbito ambito, final Long codigoEntidad, final Long codigoArea, final String identificador, String identificadorEntidad, String identificadorArea) {
		return dominioDao.realizarConsultaListaFija(ambito, codigoEntidad, codigoArea, identificador, identificadorEntidad, identificadorArea);
	}

}

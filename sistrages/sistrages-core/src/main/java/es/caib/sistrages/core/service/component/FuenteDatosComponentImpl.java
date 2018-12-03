package es.caib.sistrages.core.service.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.service.repository.dao.DominioDao;
import es.caib.sistrages.core.service.repository.dao.FuenteDatoDao;

@Component
public class FuenteDatosComponentImpl implements FuenteDatosComponent {

	@Autowired
	FuenteDatoDao fuenteDatosDao;

	@Autowired
	DominioDao dominioDao;

	@Override
	public ValoresDominio realizarConsultaFuenteDatos(final String idDominio,
			final List<ValorParametroDominio> parametros) {
		return fuenteDatosDao.realizarConsultaFuenteDatos(idDominio, parametros);
	}

	@Override
	public ValoresDominio realizarConsultaBD(final String datasource, final String sql,
			final List<ValorParametroDominio> parametros) {
		return fuenteDatosDao.realizarConsultaBD(datasource, sql, parametros);
	}

	@Override
	public ValoresDominio realizarConsultaListaFija(final String identificador) {
		return dominioDao.realizarConsultaListaFija(identificador);
	}

}

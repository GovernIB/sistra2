package es.caib.sistrages.core.service.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.ValoresDominio;
import es.caib.sistrages.core.service.repository.dao.FuenteDatoDao;

@Component
@Transactional
public class FuenteDatosComponentImpl implements FuenteDatosComponent {

	@Autowired
	FuenteDatoDao fuenteDatosDao;

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

}

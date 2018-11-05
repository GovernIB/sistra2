package es.caib.sistrages.core.service;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistra2.commons.plugins.dominio.api.DominioPluginException;
import es.caib.sistra2.commons.plugins.dominio.api.IDominioPlugin;
import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.service.DominioResolucionService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.component.ConfiguracionComponent;
import es.caib.sistrages.core.service.component.FuenteDatosComponent;
import es.caib.sistrages.core.service.repository.dao.DominioDao;
import es.caib.sistrages.core.service.repository.dao.FuenteDatoDao;

/**
 * La clase DominioServiceImpl.
 */
@Service
@Transactional
public class DominioResolucionServiceImpl implements DominioResolucionService {

	/**
	 * log.
	 */
	private final Logger log = LoggerFactory.getLogger(DominioResolucionServiceImpl.class);

	/**
	 * dominio dao.
	 */
	@Autowired
	DominioDao dominioDao;

	/**
	 * fuente dato dao.
	 */
	@Autowired
	FuenteDatoDao fuenteDatoDao;

	/**
	 * dominio dao.
	 */
	@Autowired
	FuenteDatosComponent fuenteDatosComponent;

	/**
	 * Configuracion Component.
	 */
	@Autowired
	ConfiguracionComponent configuracionComponent;

	@Override
	@NegocioInterceptor
	public ValoresDominio realizarConsultaFuenteDatos(final String idDominio,
			final List<ValorParametroDominio> parametros) {
		return fuenteDatosComponent.realizarConsultaFuenteDatos(idDominio, parametros);
	}

	@Override
	@NegocioInterceptor
	public ValoresDominio realizarConsultaBD(final String datasource, final String sql,
			final List<ValorParametroDominio> parametros) {
		return fuenteDatosComponent.realizarConsultaBD(datasource, sql, parametros);
	}

	@Override
	public ValoresDominio realizarConsultaRemota(final TypeAmbito ambito, final Long idEntidad, final String idDominio,
			final String url, final List<es.caib.sistra2.commons.plugins.dominio.api.ParametroDominio> parametros) {
		IDominioPlugin iplugin;
		if (ambito == TypeAmbito.GLOBAL) {
			iplugin = (IDominioPlugin) configuracionComponent.obtenerPluginGlobal(TypePlugin.DOMINIO_REMOTO);
		} else {
			iplugin = (IDominioPlugin) configuracionComponent.obtenerPluginEntidad(TypePlugin.DOMINIO_REMOTO,
					idEntidad);
		}

		ValoresDominio valoresDominio;
		if (iplugin == null) {
			valoresDominio = new ValoresDominio();
			valoresDominio.setError(true);
			valoresDominio.setCodigoError("PLG");
			valoresDominio.setDescripcionError("El plugin es nulo");
		} else {
			try {
				valoresDominio = iplugin.invocarDominio(idDominio, url, parametros);
				valoresDominio.setError(false);
			} catch (final DominioPluginException e) {
				valoresDominio = new ValoresDominio();
				valoresDominio.setError(true);
				valoresDominio.setDescripcionError(ExceptionUtils.getMessage(e));
				valoresDominio.setCodigoError("BD");
			}
		}
		return valoresDominio;
	}

}

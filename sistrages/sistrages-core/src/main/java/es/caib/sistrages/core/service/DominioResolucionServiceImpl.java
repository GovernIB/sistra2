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
import es.caib.sistrages.core.api.exception.ValorIdentificadorIncorrectoException;
import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.comun.ValorIdentificadorCompuesto;
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
		ValorIdentificadorCompuesto valor = new ValorIdentificadorCompuesto(idDominio);
		if (valor.isError()) {
			throw new ValorIdentificadorIncorrectoException("Error obteniendo el identificador compuesto de :" + idDominio);
		}
		return fuenteDatosComponent.realizarConsultaFuenteDatos(valor.getAmbito(), valor.getIdentificadorEntidad(), valor.getIdentificadorArea(), valor.getIdentificador(), parametros);
	}

	@Override
	@NegocioInterceptor
	public ValoresDominio realizarConsultaBD(final String datasource, final String sql,
			final List<ValorParametroDominio> parametros) {
		return fuenteDatosComponent.realizarConsultaBD(datasource, sql, parametros);
	}

	@Override
	public ValoresDominio realizarConsultaRemota(final TypeAmbito ambito, final Long idEntidad, final String idDominio,
			final String url, final String usr, final String pwd,
			final List<es.caib.sistra2.commons.plugins.dominio.api.ParametroDominio> parametros) {
		final IDominioPlugin iplugin = (IDominioPlugin) configuracionComponent.obtenerPlugin(TypePlugin.DOMINIO_REMOTO,
				idEntidad);
		ValoresDominio valoresDominio;
		if (iplugin == null) {
			valoresDominio = new ValoresDominio();
			valoresDominio.setError(true);
			valoresDominio.setCodigoError("PLG");
			valoresDominio.setDescripcionError("El plugin es nulo");
		} else {
			// Reemplaza placeholders
			String usrRev = usr;
			if (usr != null) {
				usrRev = configuracionComponent.replacePlaceholders(usr);
			}
			String pwdRev = pwd;
			if (pwd != null) {
				pwdRev = configuracionComponent.replacePlaceholders(pwd);
			}
			// Consulta dominio
			try {
				valoresDominio = iplugin.invocarDominio(idDominio, url, parametros, usrRev, pwdRev, 60L);
			} catch (final DominioPluginException e) {
				valoresDominio = new ValoresDominio();
				valoresDominio.setError(true);
				valoresDominio.setDescripcionError(ExceptionUtils.getMessage(e));
				valoresDominio.setCodigoError("BD");
			}
		}
		return valoresDominio;
	}

	@Override
	public ValoresDominio realizarConsultaListaFija(final TypeAmbito ambito, final Long idEntidad, final Long idArea,
			final String identificador, final String url, final List<Propiedad> parametros) {
		return fuenteDatosComponent.realizarConsultaListaFija(ambito, idEntidad, idArea, identificador, null, null);
	}

}

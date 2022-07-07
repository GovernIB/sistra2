package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.EnvioRemoto;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosValores;
import es.caib.sistrages.core.api.model.FuenteFila;
import es.caib.sistrages.core.api.model.comun.CsvDocumento;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.comun.ValorIdentificadorCompuesto;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.EnvioRemotoService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.component.FuenteDatosComponent;
import es.caib.sistrages.core.service.repository.dao.AreaDao;
import es.caib.sistrages.core.service.repository.dao.DominioDao;
import es.caib.sistrages.core.service.repository.dao.EntidadDao;
import es.caib.sistrages.core.service.repository.dao.EnvioRemotoDao;
import es.caib.sistrages.core.service.repository.dao.FuenteDatoDao;
import es.caib.sistrages.core.service.repository.model.JFuenteDatos;

/**
 * La clase DominioServiceImpl.
 */
@Service
@Transactional
public class EnvioRemotoServiceImpl implements EnvioRemotoService {

	/**
	 * log.
	 */
	private final Logger log = LoggerFactory.getLogger(EnvioRemotoServiceImpl.class);

	@Autowired
	EntidadDao entidadDao;

	@Autowired
	AreaDao areaDao;

	@Autowired
	EnvioRemotoDao envioRemotoDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#loadDominio(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public EnvioRemoto loadEnvio(final Long codDominio) {
		EnvioRemoto result = null;
		result = envioRemotoDao.getByCodigo(codDominio);
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#addDominio(es.caib.
	 * sistrages.core.api.model.Dominio, java.lang.Long, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public Long addEnvio(final EnvioRemoto envio, final Long idEntidad, final Long idArea) {
		return envioRemotoDao.add(envio, idEntidad, idArea);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#removeDominio(java.lang.
	 * Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean removeEnvio(final Long idEnvio) {
		return envioRemotoDao.remove(idEnvio);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#listDominio(es.caib.
	 * sistrages.core.api.model.types.TypeAmbito, java.lang.Long, java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public List<EnvioRemoto> listEnvio(final TypeAmbito ambito, final Long id, final String filtro) {
		return envioRemotoDao.getAllByFiltro(ambito, id, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#updateDominio(es.caib.
	 * sistrages.core.api.model.Dominio)
	 */
	@Override
	@NegocioInterceptor
	public void updateEnvio(final EnvioRemoto envio) {
		envioRemotoDao.updateEnvio(envio);
	}

	@Override
	@NegocioInterceptor
	public void clonar(final String envioID, final String nuevoIdentificador, final Long areaID, final Long fdID,
			final Long idEntidad) {
		envioRemotoDao.clonar(envioID, nuevoIdentificador, areaID, fdID, idEntidad);
	}

	@Override
	@NegocioInterceptor
	public List<EnvioRemoto> getEnviosByConfAut(Long idConfiguracion, Long idArea) {
		return envioRemotoDao.getEnviosByConfAut(idConfiguracion, idArea);
	}

	@Override
	@NegocioInterceptor
	public List<EnvioRemoto> getEnviosByIdentificador(List<String> identificadoresDominio, final Long idEntidad,
			final Long idArea) {
		return envioRemotoDao.getEnviosByIdentificador(identificadoresDominio, idEntidad, idArea);
	}

	@Override
	@NegocioInterceptor
	public boolean existeEnvioByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad,
			Long codigoArea, Long codigoDominio) {
		return envioRemotoDao.existeEnvioByIdentificador(ambito, identificador, codigoEntidad, codigoArea,
				codigoDominio);
	}

	@Override
	@NegocioInterceptor
	public EnvioRemoto getEnvioByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad,
			Long codigoArea, Long codigoDominio) {
		String idEntidad = null;
		String idArea = null;
		return envioRemotoDao.getEnvioByIdentificador(ambito, identificador, idEntidad, idArea, codigoEntidad, codigoArea,
				codigoDominio);
	}

	@Override
	@NegocioInterceptor
	public EnvioRemoto loadEnvioByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad,
			Long codigoArea, Long codigoDominio) {
		return envioRemotoDao.getEnvioByIdentificador(ambito, identificador, null, null, codigoEntidad, codigoArea,
				codigoDominio);
	}

	@Override
	@NegocioInterceptor
	public EnvioRemoto loadEnvioByIdentificadorCompuesto(String identificadorCompuesto) {

		ValorIdentificadorCompuesto valor = new ValorIdentificadorCompuesto(identificadorCompuesto);
		return envioRemotoDao.getEnvioByIdentificador(valor.getAmbito(), valor.getIdentificador(),
				valor.getIdentificadorEntidad(), valor.getIdentificadorArea(), null, null, null);
	}

}

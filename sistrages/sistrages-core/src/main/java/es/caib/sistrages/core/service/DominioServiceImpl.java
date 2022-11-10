package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosValores;
import es.caib.sistrages.core.api.model.FuenteFila;
import es.caib.sistrages.core.api.model.comun.CsvDocumento;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.comun.ValorIdentificadorCompuesto;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeClonarAccion;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.component.FuenteDatosComponent;
import es.caib.sistrages.core.service.repository.dao.AreaDao;
import es.caib.sistrages.core.service.repository.dao.ConfiguracionAutenticacionDao;
import es.caib.sistrages.core.service.repository.dao.DominioDao;
import es.caib.sistrages.core.service.repository.dao.EntidadDao;
import es.caib.sistrages.core.service.repository.dao.FuenteDatoDao;
import es.caib.sistrages.core.service.repository.model.JFuenteDatos;

/**
 * La clase DominioServiceImpl.
 */
@Service
@Transactional
public class DominioServiceImpl implements DominioService {

	/**
	 * log.
	 */
	private final Logger log = LoggerFactory.getLogger(DominioServiceImpl.class);

	@Autowired
	EntidadDao entidadDao;

	@Autowired
	AreaDao areaDao;

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
	 * config aute dao.
	 */
	@Autowired
	ConfiguracionAutenticacionDao configAutDao;

	/**
	 * dominio dao.
	 */
	@Autowired
	FuenteDatosComponent fuenteDatosComponent;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#loadDominio(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public Dominio loadDominio(final Long codDominio) {
		Dominio result = null;
		result = dominioDao.getByCodigo(codDominio);
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
	public Long addDominio(final Dominio entidad, final Long idEntidad, final Long idArea) {
		return dominioDao.add(entidad, idEntidad, idArea);
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
	public boolean removeDominio(final Long idDominio) {
		return dominioDao.remove(idDominio);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#listDominio(es.caib.
	 * sistrages.core.api.model.types.TypeAmbito, java.lang.Long, java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public List<Dominio> listDominio(final TypeAmbito ambito, final Long id, final String filtro) {
		return dominioDao.getAllByFiltro(ambito, id, filtro);
	}

	@Override
	public List<Dominio> listDominio(List<TypeAmbito> ambitos, Long idTramite, String filtro) {
		return dominioDao.getAllByFiltro(ambitos, idTramite, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#updateDominio(es.caib.
	 * sistrages.core.api.model.Dominio)
	 */
	@Override
	@NegocioInterceptor
	public void updateDominio(final Dominio dominio) {
		dominioDao.updateDominio(dominio);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#loadFuenteDato(java.lang.
	 * Long)
	 */
	@Override
	@NegocioInterceptor
	public FuenteDatos loadFuenteDato(final Long idFuenteDato) {
		return fuenteDatoDao.getById(idFuenteDato);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#loadFuenteDato(java.lang.
	 * Long)
	 */
	@Override
	@NegocioInterceptor
	public FuenteDatosValores loadFuenteDatoValores(final Long idFuenteDato) {
		return fuenteDatoDao.getValoresById(idFuenteDato);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#addFuenteDato(es.caib.
	 * sistrages.core.api.model.FuenteDatos, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public void addFuenteDato(final FuenteDatos fuenteDato, final Long id) {
		fuenteDatoDao.add(fuenteDato, id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#updateFuenteDato(es.caib.
	 * sistrages.core.api.model.FuenteDatos)
	 */
	@Override
	@NegocioInterceptor
	public void updateFuenteDato(final FuenteDatos fuenteDato) {
		fuenteDatoDao.updateFuenteDato(fuenteDato);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#removeFuenteDato(java.lang.
	 * Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean removeFuenteDato(final Long idFuenteDato) {
		boolean borrar;
		if (!dominioDao.getAllByFuenteDatos(idFuenteDato).isEmpty()) {
			borrar = false;
		} else {
			borrar = true;
			fuenteDatoDao.remove(idFuenteDato);
		}
		return borrar;
	}

	@Override
	@NegocioInterceptor
	public List<String> listDominiosByFD(final Long idFuenteDatos) {
		return dominioDao.getAllByFuenteDatos(idFuenteDatos);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#listFuenteDato(es.caib.
	 * sistrages.core.api.model.types.TypeAmbito, java.lang.Long, java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public List<FuenteDatos> listFuenteDato(final TypeAmbito ambito, final Long id, final String filtro) {
		return fuenteDatoDao.getAllByFiltro(ambito, id, filtro);
	}

	@Override
	@NegocioInterceptor
	public FuenteFila loadFuenteDatoFila(final Long idFuenteDatoFila) {
		return fuenteDatoDao.loadFuenteDatoFila(idFuenteDatoFila);
	}

	@Override
	@NegocioInterceptor
	public void addFuenteDatoFila(final FuenteFila fila, final Long idFuente) {
		fuenteDatoDao.addFuenteDatoFila(fila, idFuente);
	}

	@Override
	@NegocioInterceptor
	public void updateFuenteDatoFila(final FuenteFila fila) {
		fuenteDatoDao.updateFuenteDatoFila(fila);
	}

	@Override
	@NegocioInterceptor
	public void removeFuenteFila(final Long idFila) {
		fuenteDatoDao.removeFuenteFila(idFila);
	}

	@Override
	@NegocioInterceptor
	public boolean isCorrectoPK(final FuenteFila fuenteFila, final Long idFuenteDato) {
		return fuenteDatoDao.isCorrectoPK(fuenteFila, idFuenteDato);
	}

	@Override
	@NegocioInterceptor
	public void importarCSV(final Long idFuenteDatos, final CsvDocumento csv) {
		fuenteDatoDao.importarCSV(idFuenteDatos, csv);
	}

	@Override
	@NegocioInterceptor
	public FuenteDatos loadFuenteDato(final TypeAmbito ambito, final String identificador, final Long codigoEntidad,
			final Long codigoArea, final Long codigoFD) {
		return fuenteDatoDao.getByIdentificador(ambito, identificador, codigoEntidad, codigoArea, codigoFD);
	}

	@Override
	@NegocioInterceptor
	public boolean existeFuenteDato(final TypeAmbito ambito, final String identificador, final Long codigoEntidad,
			final Long codigoArea, final Long codigoFD) {
		return fuenteDatoDao.existeFDByIdentificador(ambito, identificador, codigoEntidad, codigoArea, codigoFD);
	}

	@Override
	@NegocioInterceptor
	public boolean tieneTramiteVersion(final Long idDominio, final Long idTramiteVersion) {
		return dominioDao.tieneTramiteVersion(idDominio, idTramiteVersion);
	}

	@Override
	@NegocioInterceptor
	public void removeTramiteVersion(final Long idDominio, final Long idTramiteVersion) {
		dominioDao.removeTramiteVersion(idDominio, idTramiteVersion);
	}

	@Override
	@NegocioInterceptor
	public void addTramiteVersion(final Long idDominio, final Long idTramiteVersion) {
		dominioDao.addTramiteVersion(idDominio, idTramiteVersion);
	}

	@Override
	@NegocioInterceptor
	public List<Dominio> listDominio(final Long idTramite, final String filtro) {
		return dominioDao.getAllByFiltro(idTramite, filtro);
	}

	@Override
	@NegocioInterceptor
	public void importarDominio(final FilaImportarDominio filaDominio, final Long idEntidad, final Long idArea)
			throws Exception {
		// Si es de tipo FD , vemos de obtenerlo antes
		JFuenteDatos jfuenteDatos;
		if (filaDominio.getDominio().getTipo() == TypeDominio.FUENTE_DATOS) {
			jfuenteDatos = fuenteDatoDao.importarFD(filaDominio, filaDominio.getDominio().getAmbito(), idEntidad,
					idArea);
		} else {
			jfuenteDatos = null;
		}

		dominioDao.importar(filaDominio, idEntidad, idArea, jfuenteDatos);
	}

	@Override
	@NegocioInterceptor
	public void clonar(final String dominioID, final String nuevoIdentificador, final Long areaID, final Long idEntidad, final TypeClonarAccion accionFD, final FuenteDatos fd, final TypeClonarAccion accionCA, final ConfiguracionAutenticacion confAut) {
		log.debug("CLONAR");
		FuenteDatos fuenteDatos = null;
		ConfiguracionAutenticacion conf = null;
		if (accionFD != TypeClonarAccion.NADA) {
			if (accionFD == TypeClonarAccion.MANTENER) {
				fuenteDatos = fd;
			} else {
				//Reemplaza/Crea los datos del fd del dominio con el fd objetivo
				fuenteDatos = fuenteDatoDao.clonar(dominioID, accionFD, fd, idEntidad, areaID);
			}
		}

		if (accionCA != TypeClonarAccion.NADA) {
			if (accionCA == TypeClonarAccion.MANTENER) {
				conf = confAut;
			} else {
				//Reemplaza/Crea los datos del fd del dominio con el fd objetivo
				conf = configAutDao.clonar(dominioID, accionCA, confAut, idEntidad, areaID);
			}
		}

		dominioDao.clonar(dominioID, nuevoIdentificador, areaID,  idEntidad, fuenteDatos, conf);
	}

	@Override
	@NegocioInterceptor
	public List<Dominio> getDominiosByConfAut(TypeAmbito ambito, Long idConfiguracion, Long idArea) {
		return dominioDao.getDominiosByConfAut(ambito, idConfiguracion, idArea);
	}

	@Override
	@NegocioInterceptor
	public List<Dominio> getDominiosByIdentificador(List<String> identificadoresDominio, final Long idEntidad,
			final Long idArea) {
		return dominioDao.getDominiosByIdentificador(identificadoresDominio, idEntidad, idArea);
	}

	@Override
	@NegocioInterceptor
	public boolean existeDominioByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad,
			Long codigoArea, Long codigoDominio) {
		return dominioDao.existeDominioByIdentificador(ambito, identificador, codigoEntidad, codigoArea, codigoDominio);
	}

	@Override
	@NegocioInterceptor
	public Dominio loadDominioByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad,
			Long codigoArea, Long codigoDominio) {
		return dominioDao.getDominioByIdentificador(ambito, identificador, null, null, codigoEntidad, codigoArea,
				codigoDominio);
	}

	@Override
	@NegocioInterceptor
	public Dominio loadDominioByIdentificadorCompuesto(String identificadorCompuesto) {

		ValorIdentificadorCompuesto valor = new ValorIdentificadorCompuesto(identificadorCompuesto);
		return dominioDao.getDominioByIdentificador(valor.getAmbito(), valor.getIdentificador(),
				valor.getIdentificadorEntidad(), valor.getIdentificadorArea(), null, null, null);
	}



}

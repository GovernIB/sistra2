package es.caib.sistrages.core.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.ConsultaGeneral;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.service.ConsultaGeneralService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.ConfiguracionAutenticacionDao;
import es.caib.sistrages.core.service.repository.dao.DominioDao;
import es.caib.sistrages.core.service.repository.dao.FormularioExternoDao;

/**
 * La clase TramiteServiceImpl.
 */
@Service
@Transactional
public class ConsultaGeneralServiceImpl implements ConsultaGeneralService {

	/** LOG */
	private final Logger log = LoggerFactory.getLogger(TramiteServiceImpl.class);

	/** DAO Dominios. */
	@Autowired
	DominioDao dominiosDao;

	/** Configuración component. */
	@Autowired
	ConfiguracionAutenticacionDao configuracionAutenticacionDao;

	/** Configuración component. */
	@Autowired
	FormularioExternoDao formularioExtenroDao;


	@Override
	@NegocioInterceptor
	public List<ConsultaGeneral> listar(String filtro, TypeIdioma idioma, Long idEntidad, Long idArea,boolean checkAmbitoGlobal, boolean checkAmbitoEntidad,
			boolean checkAmbitoArea, boolean checkDominios, boolean checkConfAut, boolean checkGFE) {
		List<ConsultaGeneral> datos = new ArrayList<>();
		if (checkDominios) {
			datos.addAll(dominiosDao.listar(filtro, idioma, idEntidad, idArea, checkAmbitoGlobal, checkAmbitoEntidad, checkAmbitoArea));
		}

		if (checkConfAut) {
			datos.addAll(configuracionAutenticacionDao.listar(filtro, idioma, idEntidad, idArea, checkAmbitoGlobal, checkAmbitoEntidad, checkAmbitoArea));
		}

		if (checkGFE) {
			if (checkAmbitoArea) {
				datos.addAll(formularioExtenroDao.listar(filtro, idioma, idEntidad, idArea, checkAmbitoGlobal, checkAmbitoEntidad, checkAmbitoArea));
			}
		}
		return datos;
	}

}
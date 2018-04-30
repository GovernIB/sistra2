package es.caib.sistrages.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.FicheroExternoDao;
import es.caib.sistrages.core.service.repository.dao.ProcesoDao;

@Service
@Transactional
public class SystemServiceImpl implements SystemService {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(SystemServiceImpl.class);

	/** DAO Fichero externo. */
	@Autowired
	FicheroExternoDao ficheroExternoDAO;

	/** DAO Procesos. */
	@Autowired
	ProcesoDao procesosDAO;

	@Override
	@NegocioInterceptor
	public void purgarFicheros(final String appId) {

		// Control maestro/esclavo procesos
		if (procesosDAO.verificarMaestro(appId)) {
			log.debug("Es maestro. Lanza purga ficheros");
			ficheroExternoDAO.purgarFicheros();
		} else {
			log.debug("No es maestro. No lanza purga ficheros");
		}

	}

}

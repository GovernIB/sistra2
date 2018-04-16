package es.caib.sistrages.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.FicheroExternoDao;

@Service
@Transactional
public class SystemServiceImpl implements SystemService {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(SystemServiceImpl.class);

	@Autowired
	FicheroExternoDao dao;

	@Override
	@NegocioInterceptor
	public void purgarFicheros(final String appId) {

		// TODO Control maestro/esclavo procesos

		log.debug("Purga ficheros");
		dao.purgarFicheros();
	}

}

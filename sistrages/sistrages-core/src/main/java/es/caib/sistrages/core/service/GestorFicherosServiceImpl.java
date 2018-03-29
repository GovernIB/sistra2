package es.caib.sistrages.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.ContenidoFichero;
import es.caib.sistrages.core.api.service.GestorFicherosService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.FicheroExternoDao;

/**
 * Implementación gestor ficheros.
 */
@Service
@Transactional
public class GestorFicherosServiceImpl implements GestorFicherosService {

	/** Constante LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(GestorFicherosServiceImpl.class);

	/** FicheroExterno dao. */
	@Autowired
	FicheroExternoDao ficheroExternoDao;

	@Override
	@NegocioInterceptor
	public void purgarFicheros() {
		ficheroExternoDao.purgarFicheros();
	}

	@Override
	@NegocioInterceptor
	public ContenidoFichero obtenerContenidoFichero(final Long id) {
		return ficheroExternoDao.getContentById(id);
	}

}

package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.DominioDao;

@Service
@Transactional
public class DominioServiceImpl implements DominioService {

	private final Logger log = LoggerFactory.getLogger(DominioServiceImpl.class);

	@Autowired
	DominioDao dominioDao;

	@Override
	@NegocioInterceptor
	public Dominio loadDominio(final Long idDominio) {
		Dominio result = null;
		result = dominioDao.getById(idDominio);
		return result;
	}

	@Override
	@NegocioInterceptor
	public void addDominio(final Dominio entidad, final Long idEntidad, final Long idArea) {
		dominioDao.add(entidad, idEntidad, idArea);
	}

	@Override
	@NegocioInterceptor
	public void removeDominio(final Long idDominio) {
		dominioDao.remove(idDominio);
	}

	@Override
	@NegocioInterceptor
	public List<Dominio> listDominio(final TypeAmbito ambito, final Long id, final String filtro) {
		return dominioDao.getAllByFiltro(ambito, id, filtro);
	}

	@Override
	public void updateDominio(final Dominio dominio) {
		dominioDao.updateDominio(dominio);
	}

}

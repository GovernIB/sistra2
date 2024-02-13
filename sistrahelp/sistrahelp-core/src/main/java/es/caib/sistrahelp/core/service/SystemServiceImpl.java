package es.caib.sistrahelp.core.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrahelp.core.api.exception.CargaConfiguracionException;
import es.caib.sistrahelp.core.api.model.Sesion;
import es.caib.sistrahelp.core.api.service.SystemService;
import es.caib.sistrahelp.core.interceptor.NegocioInterceptor;
import es.caib.sistrahelp.core.service.repository.dao.SesionDao;

@Service
@Transactional
public class SystemServiceImpl implements SystemService {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(SystemServiceImpl.class);

	/** Sesion DAO. */
	@Autowired
	SesionDao sesionDao;

	@PostConstruct
	public void init() {

	}

	@Override
	@NegocioInterceptor
	public Sesion getSesion(final String pUserName) {
		return sesionDao.getByUser(pUserName);
	}

	@Override
	@NegocioInterceptor
	public void updateSesionPropiedades(final String pUserName, final String pPropiedades) {
		sesionDao.updatePropiedades(pUserName, pPropiedades);
	}

}

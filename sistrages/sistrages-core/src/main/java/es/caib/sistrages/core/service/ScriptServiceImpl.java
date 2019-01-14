package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.LiteralScript;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.service.ScriptService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.ScriptDao;

@Service
@Transactional
public class ScriptServiceImpl implements ScriptService {

	private final Logger log = LoggerFactory.getLogger(ScriptServiceImpl.class);

	@Autowired
	ScriptDao scriptDao;

	@Override
	@NegocioInterceptor
	public Script getScript(final Long idScript) {
		return scriptDao.getScript(idScript);
	}

	@Override
	@NegocioInterceptor
	public List<LiteralScript> getLiterales(final Long idScript) {
		return scriptDao.getLiterales(idScript);
	}

	@Override
	@NegocioInterceptor
	public LiteralScript getLiteralScript(final Long idLiteralScript) {
		return scriptDao.getLiteralScript(idLiteralScript);
	}

	@Override
	@NegocioInterceptor
	public LiteralScript addLiteralScript(final LiteralScript literalScript, final Long idScript) {
		return scriptDao.addLiteralScript(literalScript, idScript);
	}

	@Override
	@NegocioInterceptor
	public void updateLiteralScript(final LiteralScript literalScript) {
		scriptDao.updateLiteralScript(literalScript);
	}

	@Override
	@NegocioInterceptor
	public void removeLiteralScript(final Long literalScript) {
		scriptDao.removeLiteralScript(literalScript);
	}

	@Override
	@NegocioInterceptor
	public boolean checkIdentificadorRepetido(final String identificador, final Long codigo, final Long idScript) {
		return scriptDao.checkIdentificadorRepetido(identificador, codigo, idScript);
	}

}

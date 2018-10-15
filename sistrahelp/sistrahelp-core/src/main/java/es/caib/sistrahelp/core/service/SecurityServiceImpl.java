package es.caib.sistrahelp.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrahelp.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrahelp.core.api.service.ContextService;
import es.caib.sistrahelp.core.api.service.SecurityService;
import es.caib.sistrahelp.core.interceptor.NegocioInterceptor;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private ContextService contextService;

	@Override
	@NegocioInterceptor
	public List<TypeRoleAcceso> getRoles() {
		return contextService.getRoles();
	}

	@Override
	@NegocioInterceptor
	public String getUsername() {
		return contextService.getUsername();
	}

}

package es.caib.sistrages.core.ejb;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.service.FormularioInternoService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class FormularoInternoServiceBean implements FormularioInternoService {

	/**
	 * formulario interno service.
	 */
	@Autowired
	FormularioInternoService formIntService;

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioInternoService#
	 * getFormularioInterno(java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public FormularioInterno getFormularioInterno(final Long pId) {
		return formIntService.getFormularioInterno(pId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioInternoService#
	 * updateFormularioInterno(es.caib.sistrages.core.api.model.FormularioInterno)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateFormularioInterno(final FormularioInterno pFormInt) {
		formIntService.updateFormularioInterno(pFormInt);

	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public FormularioInterno getFormularioInternoPaginas(final Long pId) {
		return formIntService.getFormularioInternoPaginas(pId);
	}

}

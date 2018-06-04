package es.caib.sistrages.core.ejb;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.ObjetoFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
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
	public FormularioInterno getFormularioInternoCompleto(final Long pId) {
		return formIntService.getFormularioInternoCompleto(pId);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public FormularioInterno getFormularioInternoPaginas(final Long pId) {
		return formIntService.getFormularioInternoPaginas(pId);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public PaginaFormulario getPaginaFormulario(final Long pId) {
		return formIntService.getPaginaFormulario(pId);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public PaginaFormulario getContenidoPaginaFormulario(final Long pId) {
		return formIntService.getContenidoPaginaFormulario(pId);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public ObjetoFormulario addComponenteFormulario(final TypeObjetoFormulario pTipoObjeto, final Long pIdPagina,
			final Long pIdLinea, final Integer pOrden, final String pPosicion) {
		return formIntService.addComponenteFormulario(pTipoObjeto, pIdPagina, pIdLinea, pOrden, pPosicion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public ObjetoFormulario updateComponenteFormulario(final ComponenteFormulario pComponente) {
		return formIntService.updateComponenteFormulario(pComponente);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public ComponenteFormulario getComponenteFormulario(final Long pId) {
		return formIntService.getComponenteFormulario(pId);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void removeComponenteFormulario(final Long pId) {
		formIntService.removeComponenteFormulario(pId);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void removeLineaFormulario(final Long pId) {
		formIntService.removeLineaFormulario(pId);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateOrdenComponenteFormulario(final Long pId, final Integer pOrden) {
		formIntService.updateOrdenComponenteFormulario(pId, pOrden);

	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateOrdenLineaFormulario(final Long pId, final Integer pOrden) {
		formIntService.updateOrdenLineaFormulario(pId, pOrden);

	}

}

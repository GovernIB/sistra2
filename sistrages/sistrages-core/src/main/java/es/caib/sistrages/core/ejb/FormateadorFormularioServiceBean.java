package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.service.FormateadorFormularioService;

/**
 * La clase FormateadorFormularioServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class FormateadorFormularioServiceBean implements FormateadorFormularioService {

	/**
	 * FormateadorFormularioService.
	 */
	@Autowired
	FormateadorFormularioService fmtService;

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormateadorFormularioService#
	 * getFormateadorFormulario(java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public FormateadorFormulario getFormateadorFormulario(final Long idFmt) {
		return fmtService.getFormateadorFormulario(idFmt);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public FormateadorFormulario getFormateadorFormulario(final String codigo) {
		return fmtService.getFormateadorFormulario(codigo);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormateadorFormularioService#
	 * addFormateadorFormulario(es.caib.sistrages.core.api.model.
	 * FormateadorFormulario)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void addFormateadorFormulario(final Long idEntidad, final FormateadorFormulario fmt) {
		fmtService.addFormateadorFormulario(idEntidad, fmt);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormateadorFormularioService#
	 * removeFormateadorFormulario(java.lang.Long)
	 */
	@Override
	@RolesAllowed(ConstantesRolesAcceso.ADMIN_ENT)
	public boolean removeFormateadorFormulario(final Long idFmt) {
		return fmtService.removeFormateadorFormulario(idFmt);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormateadorFormularioService#
	 * updateFormateadorFormulario(es.caib.sistrages.core.api.model.
	 * FormateadorFormulario)
	 */
	@Override
	@RolesAllowed(ConstantesRolesAcceso.ADMIN_ENT)
	public void updateFormateadorFormulario(final FormateadorFormulario fmt) {
		fmtService.updateFormateadorFormulario(fmt);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormateadorFormularioService#
	 * listFormateadorFormulario(java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<FormateadorFormulario> listFormateadorFormulario(final Long idEntidad, final String filtro) {
		return fmtService.listFormateadorFormulario(idEntidad, filtro);
	}

}

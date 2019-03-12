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
import es.caib.sistrages.core.api.model.PlantillaFormateador;
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
	public void removeFormateadorFormulario(final Long idFmt) {
		fmtService.removeFormateadorFormulario(idFmt);

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
	public void updateFormateadorFormulario(final FormateadorFormulario fmt, final Long idEntidad) {
		fmtService.updateFormateadorFormulario(fmt, idEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormateadorFormularioService#
	 * listFormateadorFormulario(java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<FormateadorFormulario> listFormateadorFormulario(final Long idEntidad, final String filtro,
			final Boolean bloqueado) {
		return fmtService.listFormateadorFormulario(idEntidad, filtro, bloqueado);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean tieneRelacionesFormateadorFormulario(final Long idFmt) {
		return fmtService.tieneRelacionesFormateadorFormulario(idFmt);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<PlantillaFormateador> getListaPlantillasFormateador(final Long idFormateador) {
		return fmtService.getListaPlantillasFormateador(idFormateador);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public FormateadorFormulario getFormateadorPorDefecto(final Long idEntidad) {
		return fmtService.getFormateadorPorDefecto(idEntidad);
	}

}

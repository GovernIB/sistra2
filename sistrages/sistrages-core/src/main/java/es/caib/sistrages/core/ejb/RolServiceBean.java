package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.Rol;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.service.RolService;

/**
 * La clase RolServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class RolServiceBean implements RolService {

	/**
	 * aviso entidad service.
	 */
	@Autowired
	RolService rolService;

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.RolService#getRol(java. lang.Long)
	 */
	@Override
	@RolesAllowed(ConstantesRolesAcceso.ADMIN_ENT)
	public Rol getRol(final Long idRol) {
		return rolService.getRol(idRol);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.RolService#addRol(es.caib.sistrages.core.
	 * api.model.Rol)
	 */
	@Override
	@RolesAllowed(ConstantesRolesAcceso.ADMIN_ENT)
	public void addRol(final Rol rol) {
		rolService.addRol(rol);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.RolService#removeRol(java.lang.Long)
	 */
	@Override
	@RolesAllowed(ConstantesRolesAcceso.ADMIN_ENT)
	public boolean removeRol(final Long idRol) {
		return rolService.removeRol(idRol);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.RolService#updateRol(es.caib.sistrages.
	 * core.api.model.Rol)
	 */
	@Override
	@RolesAllowed(ConstantesRolesAcceso.ADMIN_ENT)
	public void updateRol(final Rol rol) {
		rolService.updateRol(rol);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.caib.sistrages.core.api.service.RolService#listRol(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	@RolesAllowed(ConstantesRolesAcceso.ADMIN_ENT)
	public List<Rol> listRol(final Long idEntidad, final String filtro) {
		return rolService.listRol(idEntidad, filtro);
	}

}

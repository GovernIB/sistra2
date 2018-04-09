package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.service.EntidadService;

/**
 * La clase EntidadServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EntidadServiceBean implements EntidadService {

	/**
	 * entidad service.
	 */
	@Autowired
	EntidadService entidadService;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#loadEntidad(java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public Entidad loadEntidad(final Long idEntidad) {
		return entidadService.loadEntidad(idEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#listEntidad(es.caib.
	 * sistrages.core.api.model.types.TypeIdioma, java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public List<Entidad> listEntidad(final TypeIdioma idioma, final String filtro) {
		return entidadService.listEntidad(idioma, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#addEntidad(es.caib.
	 * sistrages.core.api.model.Entidad)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public void addEntidad(final Entidad entidad) {
		entidadService.addEntidad(entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#removeEntidad(java.lang.
	 * Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public boolean removeEntidad(final Long idEntidad) {
		return entidadService.removeEntidad(idEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#
	 * updateEntidadSuperAdministrador(es.caib.sistrages.core.api.model.Entidad)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public void updateEntidadSuperAdministrador(final Entidad entidad) {
		entidadService.updateEntidadSuperAdministrador(entidad);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.caib.sistrages.core.api.service.EntidadService#
	 * updateEntidadAdministradorEntidad(es.caib.sistrages.core.api.model.Entidad)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public void updateEntidadAdministradorEntidad(final Entidad entidad) {
		entidadService.updateEntidadAdministradorEntidad(entidad);
	}

}

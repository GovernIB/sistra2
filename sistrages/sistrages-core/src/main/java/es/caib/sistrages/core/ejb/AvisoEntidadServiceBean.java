package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.service.AvisoEntidadService;

/**
 * La clase AvisoEntidadServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class AvisoEntidadServiceBean implements AvisoEntidadService {

	/**
	 * aviso entidad service.
	 */
	@Autowired
	AvisoEntidadService avisoEntidadService;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.AvisoEntidadService#getAvisoEntidad(java.
	 * lang.Long)
	 */
	@Override
	@RolesAllowed(ConstantesRolesAcceso.ADMIN_ENT)
	public AvisoEntidad getAvisoEntidad(final Long id) {
		return avisoEntidadService.getAvisoEntidad(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.AvisoEntidadService#addAvisoEntidad(java.
	 * lang.Long, es.caib.sistrages.core.api.model.AvisoEntidad)
	 */
	@Override
	@RolesAllowed(ConstantesRolesAcceso.ADMIN_ENT)
	public void addAvisoEntidad(final Long idEntidad, final AvisoEntidad avisoEntidad) {
		avisoEntidadService.addAvisoEntidad(idEntidad, avisoEntidad);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.AvisoEntidadService#removeAvisoEntidad(
	 * java.lang.Long)
	 */
	@Override
	@RolesAllowed(ConstantesRolesAcceso.ADMIN_ENT)
	public boolean removeAvisoEntidad(final Long id) {
		return avisoEntidadService.removeAvisoEntidad(id);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.AvisoEntidadService#updateAvisoEntidad(es.
	 * caib.sistrages.core.api.model.AvisoEntidad)
	 */
	@Override
	@RolesAllowed(ConstantesRolesAcceso.ADMIN_ENT)
	public void updateAvisoEntidad(final AvisoEntidad avisoEntidad) {
		avisoEntidadService.updateAvisoEntidad(avisoEntidad);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.AvisoEntidadService#listAvisoEntidad(java.
	 * lang.Long, es.caib.sistrages.core.api.model.types.TypeIdioma,
	 * java.lang.String)
	 */
	@Override
	@RolesAllowed(ConstantesRolesAcceso.ADMIN_ENT)
	public List<AvisoEntidad> listAvisoEntidad(final Long idEntidad, final TypeIdioma idioma, final String filtro) {
		return avisoEntidadService.listAvisoEntidad(idEntidad, idioma, filtro);
	}

}

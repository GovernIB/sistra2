package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.service.AreaService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class AreaServiceBean implements AreaService {

	@Autowired
	AreaService areaService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.caib.sistrages.core.api.service.AreaService#listArea(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public List<Area> listArea(final Long idEntidad, final String filtro) {
		return areaService.listArea(idEntidad, filtro);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.caib.sistrages.core.api.service.AreaService#getArea(java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public Area getArea(final Long id) {
		return areaService.getArea(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.caib.sistrages.core.api.service.AreaService#addArea(java.lang.Long,
	 * es.caib.sistrages.core.api.model.Area)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public void addArea(final Long idEntidad, final Area area) {
		areaService.addArea(idEntidad, area);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.caib.sistrages.core.api.service.AreaService#removeArea(java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public boolean removeArea(final Long idArea) {
		return areaService.removeArea(idArea);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.caib.sistrages.core.api.service.AreaService#updateArea(es.caib.sistrages.
	 * core.api.model.Area)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public void updateArea(final Area area) {
		areaService.updateArea(area);
	}

}

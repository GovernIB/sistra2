package es.caib.sistrages.core.ejb.migracion;

import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.comun.migracion.ErrorMigracion;
import es.caib.sistrages.core.api.service.migracion.MigracionService;

/**
 * La clase AvisoEntidadServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class MigracionServiceBean implements MigracionService {

	/**
	 * aviso entidad service.
	 */
	@Autowired
	MigracionService migracionService;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.AvisoEntidadService#getAvisoEntidad(java.
	 * lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public List<Tramite> getTramiteSistra() {
		return migracionService.getTramiteSistra();
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public List<TramiteVersion> getTramiteVersionSistra(final Long pIdTramite) {
		return migracionService.getTramiteVersionSistra(pIdTramite);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public List<ErrorMigracion> migrarTramiteVersion(final Long pIdTramiteSistra, final int pNumVersionSistra,
			final Long pIdTramite, final int pNumVersion, final Map<String, Object> pParams) {
		return migracionService.migrarTramiteVersion(pIdTramiteSistra, pNumVersionSistra, pIdTramite, pNumVersion,
				pParams);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public boolean isDestinoCorrecto(final Long pIdTramiteSistra, final int pNumVersionSistra) {
		return migracionService.isDestinoCorrecto(pIdTramiteSistra, pNumVersionSistra);
	}

}

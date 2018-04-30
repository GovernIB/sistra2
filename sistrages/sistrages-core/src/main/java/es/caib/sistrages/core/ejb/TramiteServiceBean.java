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
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramiteTipo;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.service.TramiteService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TramiteServiceBean implements TramiteService {

	@Autowired
	TramiteService tramiteService;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#listArea(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<Area> listArea(final Long idEntidad, final String filtro) {
		return tramiteService.listArea(idEntidad, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getArea(java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Area getArea(final Long id) {
		return tramiteService.getArea(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#addArea(java.lang.Long,
	 * es.caib.sistrages.core.api.model.Area)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void addArea(final Long idEntidad, final Area area) {
		tramiteService.addArea(idEntidad, area);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#removeArea(java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean removeArea(final Long idArea) {
		return tramiteService.removeArea(idArea);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.TramiteService#updateArea(es.caib.
	 * sistrages. core.api.model.Area)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public void updateArea(final Area area) {
		tramiteService.updateArea(area);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#listTramite(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<Tramite> listTramite(final Long idArea, final String pFiltro) {
		return tramiteService.listTramite(idArea, pFiltro);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Tramite getTramite(final Long id) {
		return tramiteService.getTramite(id);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void addTramite(final Long idArea, final Tramite pTramite) {
		tramiteService.addTramite(idArea, pTramite);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean removeTramite(final Long id) {
		return tramiteService.removeTramite(id);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateTramite(final Tramite pTramite) {
		tramiteService.updateTramite(pTramite);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<TramiteVersion> listTramiteVersion(final Long idTramite, final String filtro) {
		return tramiteService.listTramiteVersion(idTramite, filtro);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void addTramiteVersion(final TramiteVersion tramiteVersion, final String idTramite) {
		tramiteService.addTramiteVersion(tramiteVersion, idTramite);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateTramiteVersion(final TramiteVersion tramiteVersion, final boolean borrarScriptPI,
			final Script scriptParamsIniciales, final boolean borrarScriptPersonalizacion,
			final Script scriptPersonalizacion) {
		tramiteService.updateTramiteVersion(tramiteVersion, borrarScriptPI, scriptParamsIniciales,
				borrarScriptPersonalizacion, scriptPersonalizacion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void removeTramiteVersion(final Long idTramiteVersion) {
		tramiteService.removeTramiteVersion(idTramiteVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public TramiteVersion getTramiteVersion(final Long idTramiteVersion) {
		return tramiteService.getTramiteVersion(idTramiteVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public List<TramiteTipo> listTipoTramitePaso() {
		return tramiteService.listTipoTramitePaso();
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Area getAreaTramite(final Long idTramite) {
		return tramiteService.getAreaTramite(idTramite);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public List<TramitePaso> getTramitePasos(final Long idTramiteVersion) {
		return tramiteService.getTramitePasos(idTramiteVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public void changeAreaTramite(final Long idArea, final Long idTramite) {
		tramiteService.changeAreaTramite(idArea, idTramite);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public List<Dominio> getTramiteDominios(final Long idTramiteVersion) {
		return tramiteService.getTramiteDominios(idTramiteVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public void updateTramitePaso(final TramitePaso tramitePasoRellenar) {
		tramiteService.updateTramitePaso(tramitePasoRellenar);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public void removeFormulario(final Long idTramitePaso, final Long idFormulario) {
		tramiteService.removeFormulario(idTramitePaso, idFormulario);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public TramitePaso getTramitePaso(final Long id) {
		return tramiteService.getTramitePaso(id);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public FormularioTramite getFormulario(final Long idFormularioTramite) {
		return tramiteService.getFormulario(idFormularioTramite);
	}

}

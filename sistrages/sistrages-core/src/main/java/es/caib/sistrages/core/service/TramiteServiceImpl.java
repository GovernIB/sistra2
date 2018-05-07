package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramiteTipo;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.component.AreaComponent;
import es.caib.sistrages.core.service.repository.dao.AreaDao;
import es.caib.sistrages.core.service.repository.dao.DominioDao;
import es.caib.sistrages.core.service.repository.dao.FuenteDatoDao;
import es.caib.sistrages.core.service.repository.dao.TramiteDao;
import es.caib.sistrages.core.service.repository.dao.TramitePasoDao;

@Service
@Transactional
public class TramiteServiceImpl implements TramiteService {

	private final Logger log = LoggerFactory.getLogger(TramiteServiceImpl.class);

	@Autowired
	AreaDao areaDataDao;

	@Autowired
	TramiteDao tramiteDao;

	@Autowired
	TramitePasoDao tramitePasoDao;

	/** DAO Dominios. */
	@Autowired
	DominioDao dominiosDao;

	/** DAO Fuente Datos. */
	@Autowired
	FuenteDatoDao fuenteDatoDao;

	@Autowired
	AreaComponent areaComponent;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#listArea(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public List<Area> listArea(final Long idEntidad, final String filtro) {
		return areaDataDao.getAllByFiltro(idEntidad, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getArea(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public Area getArea(final Long id) {
		return areaDataDao.getById(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#addArea(java.lang.Long,
	 * es.caib.sistrages.core.api.model.Area)
	 */
	@Override
	@NegocioInterceptor
	public void addArea(final Long idEntidad, final Area area) {
		areaDataDao.add(idEntidad, area);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#removeArea(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean removeArea(final Long id) {

		// Verifica dependencias
		if (!tramiteDao.getAll(id).isEmpty()) {
			return false;
		}

		// Borrado en cascada: dominios y formateadores
		dominiosDao.removeByArea(id);
		fuenteDatoDao.removeByArea(id);

		// Borramos area
		areaDataDao.remove(id);
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.TramiteService#updateArea(es.caib.
	 * sistrages. core.api.model.Area)
	 */
	@Override
	@NegocioInterceptor
	public void updateArea(final Area area) {
		areaDataDao.update(area);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#listTramite(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public List<Tramite> listTramite(final Long idArea, final String pFiltro) {
		return tramiteDao.getAllByFiltro(idArea, pFiltro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getTramite(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public Tramite getTramite(final Long id) {
		return tramiteDao.getById(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#addTramite(java.lang.Long,
	 * es.caib.sistrages.core.api.model.Tramite)
	 */
	@Override
	@NegocioInterceptor
	public void addTramite(final Long idArea, final Tramite pTramite) {
		tramiteDao.add(idArea, pTramite);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#removeTramite(java.lang.
	 * Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean removeTramite(final Long id) {
		tramiteDao.remove(id);
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.TramiteService#updateTramite(es.caib.
	 * sistrages.core.api.model.Tramite)
	 */
	@Override
	@NegocioInterceptor
	public void updateTramite(final Tramite pTramite) {
		tramiteDao.update(pTramite);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getTramitesVersion(Long
	 * idTramite)
	 */
	@Override
	@NegocioInterceptor
	public List<TramiteVersion> listTramiteVersion(final Long idTramite, final String filtro) {
		return tramiteDao.getTramitesVersion(idTramite, filtro);
	}

	@Override
	@NegocioInterceptor
	public void addTramiteVersion(final TramiteVersion tramiteVersion, final String idTramite) {
		tramiteDao.addTramiteVersion(tramiteVersion, idTramite);
	}

	@Override
	@NegocioInterceptor
	public void updateTramiteVersion(final TramiteVersion tramiteVersion, final boolean borrarScriptPI,
			final Script scriptParamsIniciales, final boolean borrarScriptPersonalizacion,
			final Script scriptPersonalizacion) {
		tramiteDao.updateTramiteVersion(tramiteVersion, borrarScriptPI, scriptParamsIniciales,
				borrarScriptPersonalizacion, scriptPersonalizacion);
	}

	@Override
	@NegocioInterceptor
	public void removeTramiteVersion(final Long idTramiteVersion) {
		tramiteDao.removeTramiteVersion(idTramiteVersion);
	}

	@Override
	@NegocioInterceptor
	public TramiteVersion getTramiteVersion(final Long idTramiteVersion) {
		return tramiteDao.getTramiteVersion(idTramiteVersion);
	}

	@Override
	@NegocioInterceptor
	public List<TramiteTipo> listTipoTramitePaso() {
		return tramiteDao.listTipoTramitePaso();
	}

	@Override
	@NegocioInterceptor
	public Area getAreaTramite(final Long idTramite) {
		return tramiteDao.getAreaTramite(idTramite);
	}

	@Override
	@NegocioInterceptor
	public List<TramitePaso> getTramitePasos(final Long idTramiteVersion) {
		return tramitePasoDao.getTramitePasos(idTramiteVersion);
	}

	@Override
	@NegocioInterceptor
	public void changeAreaTramite(final Long idArea, final Long idTramite) {
		tramiteDao.changeAreaTramite(idArea, idTramite);
	}

	@Override
	@NegocioInterceptor
	public List<Dominio> getTramiteDominios(final Long idTramiteVersion) {
		return tramiteDao.getTramiteDominios(idTramiteVersion);
	}

	@Override
	@NegocioInterceptor
	public void removeFormulario(final Long idTramitePaso, final Long idFormulario) {
		tramitePasoDao.removeFormulario(idTramitePaso, idFormulario);
	}

	@Override
	@NegocioInterceptor
	public void updateTramitePaso(final TramitePaso tramitePasoRellenar) {
		tramitePasoDao.updateTramitePaso(tramitePasoRellenar);
	}

	@Override
	@NegocioInterceptor
	public TramitePaso getTramitePaso(final Long idTramitePaso) {
		return tramitePasoDao.getTramitePaso(idTramitePaso);
	}

	@Override
	@NegocioInterceptor
	public FormularioTramite getFormulario(final Long idFormularioTramite) {
		return tramitePasoDao.getFormulario(idFormularioTramite);
	}

	@Override
	@NegocioInterceptor
	public void addFormularioTramite(final FormularioTramite formularioTramite, final Long idTramitePaso) {
		tramitePasoDao.addFormularioTramite(formularioTramite, idTramitePaso);
	}

	@Override
	@NegocioInterceptor
	public void updateFormularioTramite(final FormularioTramite formularioTramite) {
		tramitePasoDao.updateFormularioTramite(formularioTramite);
	}

	@Override
	@NegocioInterceptor
	public void addDocumentoTramite(final Documento documento, final Long idTramitePaso) {
		tramitePasoDao.addDocumentoTramite(documento, idTramitePaso);
	}

	@Override
	@NegocioInterceptor
	public void updateDocumentoTramite(final Documento documento) {
		tramitePasoDao.updateDocumentoTramite(documento);
	}

	@Override
	@NegocioInterceptor
	public Documento getDocumento(final Long idDocumento) {
		return tramitePasoDao.getDocumento(idDocumento);
	}

	@Override
	@NegocioInterceptor
	public void removeDocumento(final Long idTramitePaso, final Long idDocumento) {
		tramitePasoDao.removeDocumento(idTramitePaso, idDocumento);
	}

	@Override
	@NegocioInterceptor
	public Tasa getTasa(final Long idTasa) {
		return tramitePasoDao.getTasa(idTasa);
	}

	@Override
	@NegocioInterceptor
	public void addTasaTramite(final Tasa tasa, final Long idTramitePaso) {
		tramitePasoDao.addTasaTramite(tasa, idTramitePaso);
	}

	@Override
	@NegocioInterceptor
	public void updateTasaTramite(final Tasa tasa) {
		tramitePasoDao.updateTasaTramite(tasa);
	}

	@Override
	@NegocioInterceptor
	public void removeTasa(final Long idTramitePaso, final Long idTasa) {
		tramitePasoDao.removeTasa(idTramitePaso, idTasa);
	}

}
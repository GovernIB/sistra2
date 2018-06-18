package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.HistorialVersion;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramiteTipo;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeAccionHistorial;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.component.AreaComponent;
import es.caib.sistrages.core.service.repository.dao.AreaDao;
import es.caib.sistrages.core.service.repository.dao.DominioDao;
import es.caib.sistrages.core.service.repository.dao.FicheroExternoDao;
import es.caib.sistrages.core.service.repository.dao.FormularioInternoDao;
import es.caib.sistrages.core.service.repository.dao.FuenteDatoDao;
import es.caib.sistrages.core.service.repository.dao.HistorialVersionDao;
import es.caib.sistrages.core.service.repository.dao.TramiteDao;
import es.caib.sistrages.core.service.repository.dao.TramitePasoDao;

@Service
@Transactional
public class TramiteServiceImpl implements TramiteService {

	private final Logger log = LoggerFactory.getLogger(TramiteServiceImpl.class);

	/** DAO de historial version. **/
	@Autowired
	HistorialVersionDao historialVersionDao;

	/** DAO de area. **/
	@Autowired
	AreaDao areaDao;

	/** DAO Fichero Externo. */
	@Autowired
	FicheroExternoDao ficheroExternoDao;

	/** DAO Formulario interno. */
	@Autowired
	FormularioInternoDao formularioInternoDao;

	/** DAO Tramite. */
	@Autowired
	TramiteDao tramiteDao;

	/** DAO Tramite Paso. **/
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
		return areaDao.getAllByFiltro(idEntidad, filtro);
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
		return areaDao.getById(id);
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
		areaDao.add(idEntidad, area);
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
		areaDao.remove(id);
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
		areaDao.update(area);
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
	public void addTramiteVersion(final TramiteVersion tramiteVersion, final String idTramite, final String usuario) {
		final Long idTramiteVersion = tramiteDao.addTramiteVersion(tramiteVersion, idTramite);
		historialVersionDao.add(idTramiteVersion, usuario, TypeAccionHistorial.CREACION, "");
	}

	@Override
	@NegocioInterceptor
	public void updateTramiteVersion(final TramiteVersion tramiteVersion) {
		tramiteDao.updateTramiteVersion(tramiteVersion);
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
	public List<Long> getTramiteDominiosId(final Long idTramiteVersion) {
		return tramiteDao.getTramiteDominiosId(idTramiteVersion);
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
		// Primero creamos el formulario interno y luego el formulario tramite.
		final Long idFormularioInterno = formularioInternoDao.addFormulario(formularioTramite);
		tramitePasoDao.addFormularioTramite(formularioTramite, idTramitePaso, idFormularioInterno);
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

	@Override
	@NegocioInterceptor
	public void uploadDocAnexo(final Long idDocumento, final Fichero fichero, final byte[] contents,
			final Long idEntidad) {
		final Fichero newFichero = tramitePasoDao.uploadDocAnexo(idDocumento, fichero);
		ficheroExternoDao.guardarFichero(idEntidad, newFichero, contents);
	}

	@Override
	@NegocioInterceptor
	public void removeDocAnexo(final Long idDocumento) {
		tramitePasoDao.removeDocAnexo(idDocumento);
	}

	@Override
	@NegocioInterceptor
	public void bloquearTramiteVersion(final Long idTramiteVersion, final String username) {
		tramiteDao.bloquearTramiteVersion(idTramiteVersion, username);
		historialVersionDao.add(idTramiteVersion, username, TypeAccionHistorial.BLOQUEAR, "");
	}

	@Override
	@NegocioInterceptor
	public void desbloquearTramiteVersion(final Long idTramiteVersion, final String username, final String detalle) {
		tramiteDao.desbloquearTramiteVersion(idTramiteVersion);
		historialVersionDao.add(idTramiteVersion, username, TypeAccionHistorial.DESBLOQUEAR, detalle);
	}

	@Override
	@NegocioInterceptor
	public List<HistorialVersion> listHistorialVersion(final Long idTramiteVersion, final String filtro) {
		return historialVersionDao.getAllByFiltro(idTramiteVersion, filtro);
	}

	@Override
	@NegocioInterceptor
	public HistorialVersion getHistorialVersion(final Long idHistorialVersion) {
		return historialVersionDao.getById(idHistorialVersion);
	}

	@Override
	@NegocioInterceptor
	public boolean tieneTramiteVersion(final Long idTramite) {
		return tramiteDao.tieneTramiteVersion(idTramite);
	}

	@Override
	@NegocioInterceptor
	public boolean tieneTramiteNumVersionRepetida(final Long idTramite, final int release) {
		return tramiteDao.tieneTramiteNumVersionRepetido(idTramite, release);
	}

	@Override
	@NegocioInterceptor
	public int getTramiteNumVersionMaximo(final Long idTramite) {
		return tramiteDao.getTramiteNumVersionMaximo(idTramite);
	}

	@Override
	@NegocioInterceptor
	public void clonadoTramiteVersion(final Long idTramiteVersion, final String usuario) {
		log.debug("Entrando al clonar idTramiteVersion: {} por el usuario {} ", idTramiteVersion, usuario);
		final Long idTramiteVersionNuevo = tramiteDao.clonarTramiteVersion(idTramiteVersion);
		historialVersionDao.add(idTramiteVersionNuevo, usuario, TypeAccionHistorial.CREACION, "");
	}

	@Override
	@NegocioInterceptor
	public List<Long> listTramiteVersionActiva(final Long idArea) {
		return tramiteDao.listTramiteVersionActiva(idArea);
	}

	@Override
	@NegocioInterceptor
	public List<FormateadorFormulario> getFormateadoresTramiteVersion(final Long idTramiteVersion) {
		return tramitePasoDao.getFormateadoresTramiteVersion(idTramiteVersion);
	}

	@Override
	@NegocioInterceptor
	public List<FormularioInterno> getFormulariosTramiteVersion(final Long idTramiteVersion) {
		return tramitePasoDao.getFormulariosTramiteVersion(idTramiteVersion);
	}

	@Override
	@NegocioInterceptor
	public List<Fichero> getFicherosTramiteVersion(final Long idTramiteVersion) {
		return tramitePasoDao.getFicherosTramiteVersion(idTramiteVersion);
	}

	@Override
	@NegocioInterceptor
	public Area getAreaByIdentificador(final String identificador, final Long idEntidad) {
		return areaDao.getAreaByIdentificador(identificador, idEntidad);
	}

	@Override
	@NegocioInterceptor
	public Tramite getTramiteByIdentificador(final String identificador, final Long idArea) {
		return tramiteDao.getTramiteByIdentificador(identificador, idArea);
	}

	@Override
	@NegocioInterceptor
	public TramiteVersion getTramiteVersionByNumVersion(final int identificador, final Long idTramite) {
		return tramiteDao.getTramiteVersionByNumVersion(identificador, idTramite);
	}

	@Override
	@NegocioInterceptor
	public TramiteVersion getTramiteVersionMaxNumVersion(final Long idTramite) {
		return tramiteDao.getTramiteVersionMaxNumVersion(idTramite);
	}

	@Override
	@NegocioInterceptor
	public void intercambiarFormularios(final Long idFormulario1, final Long idFormulario2) {
		tramitePasoDao.intercambiarFormularios(idFormulario1, idFormulario2);
	}

}
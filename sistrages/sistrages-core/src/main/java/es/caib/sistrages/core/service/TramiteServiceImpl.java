package es.caib.sistrages.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.DominioTramite;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.HistorialVersion;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramiteTipo;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.FilaImportarArea;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.comun.FilaImportarFormateador;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramite;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeAccionHistorial;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.component.AreaComponent;
import es.caib.sistrages.core.service.repository.dao.AreaDao;
import es.caib.sistrages.core.service.repository.dao.AvisoEntidadDao;
import es.caib.sistrages.core.service.repository.dao.DominioDao;
import es.caib.sistrages.core.service.repository.dao.FicheroExternoDao;
import es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao;
import es.caib.sistrages.core.service.repository.dao.FormularioInternoDao;
import es.caib.sistrages.core.service.repository.dao.FuenteDatoDao;
import es.caib.sistrages.core.service.repository.dao.HistorialVersionDao;
import es.caib.sistrages.core.service.repository.dao.TramiteDao;
import es.caib.sistrages.core.service.repository.dao.TramitePasoDao;

/**
 * La clase TramiteServiceImpl.
 */
@Service
@Transactional
public class TramiteServiceImpl implements TramiteService {

	/**
	 * log.
	 */
	private final Logger log = LoggerFactory.getLogger(TramiteServiceImpl.class);

	/**
	 * DAO de historial version.
	 */
	@Autowired
	HistorialVersionDao historialVersionDao;

	/**
	 * DAO de area.
	 */
	@Autowired
	AreaDao areaDao;

	/**
	 * DAO Fichero Externo.
	 */
	@Autowired
	FicheroExternoDao ficheroExternoDao;

	/**
	 * DAO Formateador formulario
	 */
	@Autowired
	FormateadorFormularioDao formateadorFormularioDao;

	/**
	 * DAO Formulario interno.
	 */
	@Autowired
	FormularioInternoDao formularioInternoDao;

	/**
	 * DAO Tramite.
	 */
	@Autowired
	TramiteDao tramiteDao;

	/** DAO Aviso Entidad. */
	@Autowired
	AvisoEntidadDao avisoEntidadDao;

	/**
	 * DAO Tramite Paso.
	 */
	@Autowired
	TramitePasoDao tramitePasoDao;

	/**
	 * DAO Dominios.
	 */
	@Autowired
	DominioDao dominiosDao;

	/**
	 * DAO Fuente Datos.
	 */
	@Autowired
	FuenteDatoDao fuenteDatoDao;

	/**
	 * area component.
	 */
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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#addTramiteVersion(es.caib.
	 * sistrages.core.api.model.TramiteVersion, java.lang.String, java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public void addTramiteVersion(final TramiteVersion tramiteVersion, final String idTramite, final String usuario) {
		final Long idTramiteVersion = tramiteDao.addTramiteVersion(tramiteVersion, idTramite);
		historialVersionDao.add(idTramiteVersion, usuario, TypeAccionHistorial.CREACION, "");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#updateTramiteVersion(es.
	 * caib.sistrages.core.api.model.TramiteVersion)
	 */
	@Override
	@NegocioInterceptor
	public void updateTramiteVersion(final TramiteVersion tramiteVersion) {
		tramiteDao.updateTramiteVersion(tramiteVersion);
	}

	@Override
	@NegocioInterceptor
	public void updateTramiteVersionControlAcceso(final TramiteVersion tramiteVersion, final AvisoEntidad avisoEntidad,
			final Long idEntidad) {
		if (avisoEntidad.getCodigo() == null) {
			if (avisoEntidad.getMensaje() != null && avisoEntidad.getMensaje().getTraducciones() != null
					&& !avisoEntidad.getMensaje().getTraducciones().isEmpty()) {
				avisoEntidadDao.add(idEntidad, avisoEntidad);
			}
		} else {
			avisoEntidadDao.update(avisoEntidad);
		}

		tramiteDao.updateTramiteVersion(tramiteVersion);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#removeTramiteVersion(java.
	 * lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public void removeTramiteVersion(final Long idTramiteVersion) {
		tramiteDao.removeTramiteVersion(idTramiteVersion);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getTramiteVersion(java.lang
	 * .Long)
	 */
	@Override
	@NegocioInterceptor
	public TramiteVersion getTramiteVersion(final Long idTramiteVersion) {
		return tramiteDao.getTramiteVersion(idTramiteVersion);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.TramiteService#listTipoTramitePaso()
	 */
	@Override
	@NegocioInterceptor
	public List<TramiteTipo> listTipoTramitePaso() {
		return tramiteDao.listTipoTramitePaso();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getAreaTramite(java.lang.
	 * Long)
	 */
	@Override
	@NegocioInterceptor
	public Area getAreaTramite(final Long idTramite) {
		return tramiteDao.getAreaTramite(idTramite);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getTramitePasos(java.lang.
	 * Long)
	 */
	@Override
	@NegocioInterceptor
	public List<TramitePaso> getTramitePasos(final Long idTramiteVersion) {
		return tramitePasoDao.getTramitePasos(idTramiteVersion);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#changeAreaTramite(java.lang
	 * .Long, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public void changeAreaTramite(final Long idArea, final Long idTramite) {
		tramiteDao.changeAreaTramite(idArea, idTramite);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getTramiteDominiosId(java.
	 * lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public List<Long> getTramiteDominiosId(final Long idTramiteVersion) {
		return tramiteDao.getTramiteDominiosId(idTramiteVersion);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#removeFormulario(java.lang.
	 * Long, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public void removeFormulario(final Long idTramitePaso, final Long idFormulario) {
		tramitePasoDao.removeFormulario(idTramitePaso, idFormulario);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#updateTramitePaso(es.caib.
	 * sistrages.core.api.model.TramitePaso)
	 */
	@Override
	@NegocioInterceptor
	public void updateTramitePaso(final TramitePaso tramitePasoRellenar) {
		tramitePasoDao.updateTramitePaso(tramitePasoRellenar);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getTramitePaso(java.lang.
	 * Long)
	 */
	@Override
	@NegocioInterceptor
	public TramitePaso getTramitePaso(final Long idTramitePaso) {
		return tramitePasoDao.getTramitePaso(idTramitePaso);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getFormulario(java.lang.
	 * Long)
	 */
	@Override
	@NegocioInterceptor
	public FormularioTramite getFormulario(final Long idFormularioTramite) {
		return tramitePasoDao.getFormulario(idFormularioTramite);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#addFormularioTramite(es.
	 * caib.sistrages.core.api.model.FormularioTramite, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public FormularioTramite addFormularioTramite(final FormularioTramite formularioTramite, final Long idTramitePaso) {
		// Primero creamos el formulario interno y luego el formulario tramite.
		final Long idFormularioInterno = formularioInternoDao.addFormulario(formularioTramite);
		return tramitePasoDao.addFormularioTramite(formularioTramite, idTramitePaso, idFormularioInterno);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#updateFormularioTramite(es.
	 * caib.sistrages.core.api.model.FormularioTramite)
	 */
	@Override
	@NegocioInterceptor
	public void updateFormularioTramite(final FormularioTramite formularioTramite) {
		tramitePasoDao.updateFormularioTramite(formularioTramite);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#addDocumentoTramite(es.caib
	 * .sistrages.core.api.model.Documento, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public Documento addDocumentoTramite(final Documento documento, final Long idTramitePaso) {
		return tramitePasoDao.addDocumentoTramite(documento, idTramitePaso);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#updateDocumentoTramite(es.
	 * caib.sistrages.core.api.model.Documento)
	 */
	@Override
	@NegocioInterceptor
	public void updateDocumentoTramite(final Documento documento) {
		tramitePasoDao.updateDocumentoTramite(documento);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getDocumento(java.lang.
	 * Long)
	 */
	@Override
	@NegocioInterceptor
	public Documento getDocumento(final Long idDocumento) {
		return tramitePasoDao.getDocumento(idDocumento);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#removeDocumento(java.lang.
	 * Long, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public void removeDocumento(final Long idTramitePaso, final Long idDocumento) {
		tramitePasoDao.removeDocumento(idTramitePaso, idDocumento);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getTasa(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public Tasa getTasa(final Long idTasa) {
		return tramitePasoDao.getTasa(idTasa);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#addTasaTramite(es.caib.
	 * sistrages.core.api.model.Tasa, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public Tasa addTasaTramite(final Tasa tasa, final Long idTramitePaso) {
		return tramitePasoDao.addTasaTramite(tasa, idTramitePaso);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#updateTasaTramite(es.caib.
	 * sistrages.core.api.model.Tasa)
	 */
	@Override
	@NegocioInterceptor
	public void updateTasaTramite(final Tasa tasa) {
		tramitePasoDao.updateTasaTramite(tasa);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#removeTasa(java.lang.Long,
	 * java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public void removeTasa(final Long idTramitePaso, final Long idTasa) {
		tramitePasoDao.removeTasa(idTramitePaso, idTasa);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#uploadDocAnexo(java.lang.
	 * Long, es.caib.sistrages.core.api.model.Fichero, byte[], java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public void uploadDocAnexo(final Long idDocumento, final Fichero fichero, final byte[] contents,
			final Long idEntidad) {
		final Fichero newFichero = tramitePasoDao.uploadDocAnexo(idDocumento, fichero);
		ficheroExternoDao.guardarFichero(idEntidad, newFichero, contents);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#removeDocAnexo(java.lang.
	 * Long)
	 */
	@Override
	@NegocioInterceptor
	public void removeDocAnexo(final Long idDocumento) {
		tramitePasoDao.removeDocAnexo(idDocumento);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#bloquearTramiteVersion(java
	 * .lang.Long, java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public void bloquearTramiteVersion(final Long idTramiteVersion, final String username) {
		tramiteDao.bloquearTramiteVersion(idTramiteVersion, username);
		historialVersionDao.add(idTramiteVersion, username, TypeAccionHistorial.BLOQUEAR, "");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#desbloquearTramiteVersion(
	 * java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public void desbloquearTramiteVersion(final Long idTramiteVersion, final String username, final String detalle) {
		tramiteDao.desbloquearTramiteVersion(idTramiteVersion);
		historialVersionDao.add(idTramiteVersion, username, TypeAccionHistorial.DESBLOQUEAR, detalle);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#listHistorialVersion(java.
	 * lang.Long, java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public List<HistorialVersion> listHistorialVersion(final Long idTramiteVersion, final String filtro) {
		return historialVersionDao.getAllByFiltro(idTramiteVersion, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getHistorialVersion(java.
	 * lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public HistorialVersion getHistorialVersion(final Long idHistorialVersion) {
		return historialVersionDao.getById(idHistorialVersion);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#tieneTramiteVersion(java.
	 * lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean tieneTramiteVersion(final Long idTramite) {
		return tramiteDao.tieneTramiteVersion(idTramite);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.TramiteService#
	 * tieneTramiteNumVersionRepetida(java.lang.Long, int)
	 */
	@Override
	@NegocioInterceptor
	public boolean tieneTramiteNumVersionRepetida(final Long idTramite, final int release) {
		return tramiteDao.tieneTramiteNumVersionRepetido(idTramite, release);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getTramiteNumVersionMaximo(
	 * java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public int getTramiteNumVersionMaximo(final Long idTramite) {
		return tramiteDao.getTramiteNumVersionMaximo(idTramite);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#clonadoTramiteVersion(java.
	 * lang.Long, java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public void clonadoTramiteVersion(final Long idTramiteVersion, final String usuario) {
		log.debug("Entrando al clonar idTramiteVersion: {} por el usuario {} ", idTramiteVersion, usuario);
		final Long idTramiteVersionNuevo = tramiteDao.clonarTramiteVersion(idTramiteVersion);
		historialVersionDao.add(idTramiteVersionNuevo, usuario, TypeAccionHistorial.CREACION, "");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#listTramiteVersionActiva(
	 * java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public List<Long> listTramiteVersionActiva(final Long idArea) {
		return tramiteDao.listTramiteVersionActiva(idArea);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.TramiteService#
	 * getFormateadoresTramiteVersion(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public List<FormateadorFormulario> getFormateadoresTramiteVersion(final Long idTramiteVersion) {
		return tramitePasoDao.getFormateadoresTramiteVersion(idTramiteVersion);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.TramiteService#
	 * getFormulariosTramiteVersion(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public List<DisenyoFormulario> getFormulariosTramiteVersion(final Long idTramiteVersion) {
		return tramitePasoDao.getFormulariosTramiteVersion(idTramiteVersion);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getFicherosTramiteVersion(
	 * java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public List<Fichero> getFicherosTramiteVersion(final Long idTramiteVersion) {
		return tramitePasoDao.getFicherosTramiteVersion(idTramiteVersion);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getAreaByIdentificador(java
	 * .lang.String, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public Area getAreaByIdentificador(final String identificador, final Long idEntidad) {
		return areaDao.getAreaByIdentificador(identificador, idEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getTramiteByIdentificador(
	 * java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public Tramite getTramiteByIdentificador(final String identificador) {
		return tramiteDao.getTramiteByIdentificador(identificador);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.TramiteService#
	 * getTramiteVersionByNumVersion(int, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public TramiteVersion getTramiteVersionByNumVersion(final int identificador, final Long idTramite) {
		return tramiteDao.getTramiteVersionByNumVersion(identificador, idTramite);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.TramiteService#
	 * getTramiteVersionMaxNumVersion(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public TramiteVersion getTramiteVersionMaxNumVersion(final Long idTramite) {
		return tramiteDao.getTramiteVersionMaxNumVersion(idTramite);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#intercambiarFormularios(
	 * java.lang.Long, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public void intercambiarFormularios(final Long idFormulario1, final Long idFormulario2) {
		tramitePasoDao.intercambiarFormularios(idFormulario1, idFormulario2);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getTramiteVersionByDominio(
	 * java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public List<DominioTramite> getTramiteVersionByDominio(final Long idDominio) {
		return tramiteDao.getTramiteVersionByDominio(idDominio);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#checkTasaRepetida(java.lang
	 * .Long, java.lang.String, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean checkTasaRepetida(final Long idTramiteVersion, final String identificador, final Long idTasa) {
		return tramitePasoDao.checkTasaRepetida(idTramiteVersion, identificador, idTasa);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#checkAnexoRepetido(java.
	 * lang.Long, java.lang.String, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean checkAnexoRepetido(final Long idTramiteVersion, final String identificador, final Long idAnexo) {
		return tramitePasoDao.checkAnexoRepetido(idTramiteVersion, identificador, idAnexo);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#checkIdentificadorRepetido(
	 * java.lang.String, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean checkIdentificadorRepetido(final String identificador, final Long codigo) {
		return tramiteDao.checkIdentificadorRepetido(identificador, codigo);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.TramiteService#
	 * checkIdentificadorAreaRepetido(java.lang.String, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean checkIdentificadorAreaRepetido(final String identificador, final Long codigo) {
		return areaDao.checkIdentificadorRepetido(identificador, codigo);
	}

	@Override
	@NegocioInterceptor
	public void importar(final FilaImportarArea filaArea, final FilaImportarTramite filaTramite,
			final FilaImportarTramiteVersion filaTramiteVersion, final List<FilaImportarDominio> filasDominios,
			final List<FilaImportarFormateador> filasFormateador, final Long idEntidad,
			final Map<Long, DisenyoFormulario> formularios, final Map<Long, Fichero> ficheros,
			final Map<Long, byte[]> ficherosContent) throws Exception {
		final Long idArea = areaDao.importar(filaArea);
		final Long idTramite = tramiteDao.importar(filaTramite, idArea);

		/**
		 * IdDominios son los dominios que se relacionan con el tramite mientras que
		 * idDominiosEquivalencia es de donde viene, la id que equivale con el
		 * importado.
		 **/
		final Map<Long, Long> idDominiosEquivalencia = new HashMap<Long, Long>();
		final List<Long> idDominios = new ArrayList<>();
		for (final FilaImportarDominio filaDominio : filasDominios) {
			final Long idDominio = dominiosDao.importar(filaDominio);
			idDominios.add(idDominio);
			idDominiosEquivalencia.put(idDominio, filaDominio.getDominio().getCodigo());
		}

		final Map<Long, FormateadorFormulario> formateadores = new HashMap<>();
		for (final FilaImportarFormateador filaFormateador : filasFormateador) {

			final Long idFormateador = formateadorFormularioDao.importar(filaFormateador, idEntidad);
			final FormateadorFormulario ff = formateadorFormularioDao.getById(idFormateador);
			formateadores.put(idFormateador, ff);
		}

		final Long idTramiteVersion = tramiteDao.importar(filaTramiteVersion, idTramite, idDominios);
		for (final TramitePaso tramitePaso : filaTramiteVersion.getTramiteVersion().getListaPasos()) {
			tramitePasoDao.importar(filaTramiteVersion, tramitePaso, idTramiteVersion, idEntidad, formularios, ficheros,
					ficherosContent, formateadores, idDominiosEquivalencia);
		}
	}

}
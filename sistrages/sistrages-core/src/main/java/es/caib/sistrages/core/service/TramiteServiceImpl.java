package es.caib.sistrages.core.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampo;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSelector;
import es.caib.sistrages.core.api.model.ComponenteFormularioListaElementos;
import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.DominioTramite;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.HistorialVersion;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.SeccionReutilizableTramite;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoAnexar;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramitePasoRegistrar;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.TramitePasoTasa;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.ErrorValidacion;
import es.caib.sistrages.core.api.model.comun.FilaImportar;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.comun.FilaImportarFormateador;
import es.caib.sistrages.core.api.model.comun.FilaImportarGestor;
import es.caib.sistrages.core.api.model.comun.FilaImportarResultado;
import es.caib.sistrages.core.api.model.comun.FilaImportarSeccion;
import es.caib.sistrages.core.api.model.comun.ScriptInfo;
import es.caib.sistrages.core.api.model.comun.TramiteSimple;
import es.caib.sistrages.core.api.model.comun.ValorIdentificadorCompuesto;
import es.caib.sistrages.core.api.model.types.TypeAccionHistorial;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeClonarAccion;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.model.types.TypeListaValores;
import es.caib.sistrages.core.api.model.types.TypePaso;
import es.caib.sistrages.core.api.model.types.TypeScriptFlujo;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.component.AreaComponent;
import es.caib.sistrages.core.service.component.TramiteComponent;
import es.caib.sistrages.core.service.component.ValidadorComponent;
import es.caib.sistrages.core.service.repository.dao.AreaDao;
import es.caib.sistrages.core.service.repository.dao.AvisoEntidadDao;
import es.caib.sistrages.core.service.repository.dao.ConfiguracionAutenticacionDao;
import es.caib.sistrages.core.service.repository.dao.DominioDao;
import es.caib.sistrages.core.service.repository.dao.EnvioRemotoDao;
import es.caib.sistrages.core.service.repository.dao.FicheroExternoDao;
import es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao;
import es.caib.sistrages.core.service.repository.dao.FormularioExternoDao;
import es.caib.sistrages.core.service.repository.dao.FormularioInternoDao;
import es.caib.sistrages.core.service.repository.dao.FuenteDatoDao;
import es.caib.sistrages.core.service.repository.dao.HistorialVersionDao;
import es.caib.sistrages.core.service.repository.dao.RolDao;
import es.caib.sistrages.core.service.repository.dao.ScriptDao;
import es.caib.sistrages.core.service.repository.dao.SeccionReutilizableDao;
import es.caib.sistrages.core.service.repository.dao.TramiteDao;
import es.caib.sistrages.core.service.repository.dao.TramitePasoDao;
import es.caib.sistrages.core.service.repository.model.JFormularioTramite;
import es.caib.sistrages.core.service.repository.model.JFuenteDatos;

/**
 * La clase TramiteServiceImpl.
 */
@Service
@Transactional
public class TramiteServiceImpl implements TramiteService {

	/** LOG */
	private final Logger log = LoggerFactory.getLogger(TramiteServiceImpl.class);

	/** DAO de historial version. */
	@Autowired
	HistorialVersionDao historialVersionDao;

	/** DAO de area. */
	@Autowired
	AreaDao areaDao;

	/** DAO Fichero Externo. */
	@Autowired
	FicheroExternoDao ficheroExternoDao;

	/** DAO Formateador formulario */
	@Autowired
	FormateadorFormularioDao formateadorFormularioDao;

	/** DAO Externo formulario */
	@Autowired
	FormularioExternoDao gestorExternoDao;

	/** DAO Seccion reutilizable */
	@Autowired
	SeccionReutilizableDao seccionReutilizableDao;

	/** DAO Externo formulario */
	@Autowired
	RolDao rolDao;

	/** DAO Formulario interno. */
	@Autowired
	FormularioInternoDao formularioInternoDao;

	/** DAO Tramite. */
	@Autowired
	TramiteDao tramiteDao;

	/** DAO Aviso Entidad. */
	@Autowired
	AvisoEntidadDao avisoEntidadDao;

	/** DAO Tramite Paso. */
	@Autowired
	TramitePasoDao tramitePasoDao;

	/** DAO Dominios. */
	@Autowired
	DominioDao dominiosDao;

	/** DAO Fuente Datos. */
	@Autowired
	FuenteDatoDao fuenteDatoDao;

	/** Area component. */
	@Autowired
	AreaComponent areaComponent;

	/** Validador component. */
	@Autowired
	ValidadorComponent validadorComponent;

	/** DAO Script. */
	@Autowired
	ScriptDao scriptDao;

	/** Tramite component. */
	@Autowired
	TramiteComponent tramiteComponent;

	/** Configuración component. */
	@Autowired
	ConfiguracionAutenticacionDao configuracionAutenticacionDao;

	/** Configuración component. */
	@Autowired
	EnvioRemotoDao erDao;

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

//		// Verifica dependencias
//		if (!tramiteDao.getAll(id).isEmpty()) {
//			return false;
//		}
//
//		// Borrado en cascada: dominios y formateadores
//		// dominiosDao.removeByArea(id); fuenteDatoDao.removeByArea(id);
//
//		// Si tiene dominio/FD, entonces no se permite borrar
//		// porque se puede borrar sin querer
//		final List<Dominio> doms = dominiosDao.getAllByFiltro(TypeAmbito.AREA, id, null);
//		if (!doms.isEmpty()) {
//			return false;
//		}
//
//		final List<FuenteDatos> fuentes = fuenteDatoDao.getAllByFiltro(TypeAmbito.AREA, id, null);
//		if (!fuentes.isEmpty()) {
//			return false;
//		}

		// Borramos roles area
		rolDao.removeByArea(id);
		// Borramos versiones tramites area
		tramiteDao.removeTramiteVersionByArea(id);
		// Borramos tramites area
		tramiteDao.removeByArea(id);
		// Borramos envios remotos
		erDao.removeByArea(id);
		// Borramos dominio area
		dominiosDao.removeByArea(id);
		// Borramos fuente datos area
		fuenteDatoDao.removeByArea(id);
		// Borramos gestor externo area
		gestorExternoDao.removeByArea(id);
		// Borramos configuracion autenticacion area
		configuracionAutenticacionDao.removeByArea(id);
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

	@Override
	public List<Tramite> listTramite(Long idEntidad, List<Long> areas, String filtro) {
		return tramiteDao.getAllByFiltro(idEntidad, areas, filtro);
	}

	@Override
	@NegocioInterceptor
	public List<Tramite> listTramiteByEntidad(final Long idEntidad) {
		return tramiteDao.getAllByEntidad(idEntidad);
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
	public Long addTramiteVersion(final TramiteVersion tramiteVersion, final String idTramite, final String usuario) {
		return tramiteComponent.addTramiteVersion(tramiteVersion, idTramite, usuario);
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
	public void removeTramiteVersion(final Long idTramiteVersion, final int numVersion) {
		// Borrar un disenyoFormulario asociado a la Lista Elementos
		List<Long> idFormulariosLE = tramiteDao.getDisenyosLEByTramite(idTramiteVersion);
		if (idFormulariosLE != null && !idFormulariosLE.isEmpty()) {
			for(Long idLE : idFormulariosLE) {
				formularioInternoDao.removeFormulario(idLE);
			}
		}

		tramiteDao.removeTramiteVersion(idTramiteVersion);
		avisoEntidadDao.removeMensajes(idTramiteVersion, numVersion);
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
	 * .Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public void changeAreaTramite(final Long idArea, final Long idTramite, final Long idAreaAntigua,
			final String usuario) {
		tramiteDao.changeAreaTramite(idArea, idTramite);
		final Area areaAntigua = areaDao.getById(idAreaAntigua);
		dominiosDao.borrarReferencias(idTramite, idAreaAntigua);
		formularioInternoDao.borrarReferencias(idTramite, idAreaAntigua);

		final List<TramiteVersion> versiones = tramiteDao.getTramitesVersion(idTramite, null);
		if (versiones != null) {
			for (final TramiteVersion version : versiones) {
				tramiteDao.incrementarRelease(version.getCodigo());
				historialVersionDao.add(version.getCodigo(), usuario, TypeAccionHistorial.MOVER_TRAMITE,
						"Tràmit mogut des de l'àrea " + areaAntigua.getCodigoDIR3Entidad());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getDominioSimpleByTramiteId
	 * (java. lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public List<Dominio> getDominioSimpleByTramiteId(final Long idTramiteVersion) {
		return tramiteDao.getDominioSimpleByTramiteId(idTramiteVersion);
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
		return tramiteComponent.addFormularioTramite(formularioTramite, idTramitePaso);
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
		return tramiteComponent.addDocumentoTramite(documento, idTramitePaso);
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
		return tramiteComponent.addTasaTramite(tasa, idTramitePaso);
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

	@Override
	@NegocioInterceptor
	public void actualizarFechaTramiteVersion(final Long idTramiteVersion, final String username,
			final String detalle) {
		historialVersionDao.add(idTramiteVersion, username, TypeAccionHistorial.MODIFICAR_TRAMITE, detalle);
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
	public boolean tieneTramiteNumVersionRepetida(final Long idTramite, final int numVersion) {
		return tramiteDao.tieneTramiteNumVersionRepetido(idTramite, numVersion);
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
	public Long clonadoTramiteVersion(final Long idTramiteVersion, final String usuario, final Long idArea,
			final Long idTramite) {
		log.debug("Entrando al clonar idTramiteVersion: {} por el usuario {} ", idTramiteVersion, usuario);

		// Primero vemos si hay que clonar dominios de tipo área al nuevo área.
		// Recorremos todos los dominios asociados, si hay alguna de tipo área,
		// entonces tendremos que ver si existe en la nueva área (si no existe, la
		// creamos)
		List<Dominio> doms = tramiteDao.getDominioSimpleByTramiteId(idTramiteVersion);
		for (Dominio dom : doms) {
			if (dom.getAmbito() == TypeAmbito.AREA) {
				Dominio dominioNuevaArea = dominiosDao.getDominioByIdentificador(TypeAmbito.AREA,
						dom.getIdentificador(), null, null, null, idArea, null);

				// Si no existe el dominio, lo clonamos
				if (dominioNuevaArea == null) {

					FuenteDatos fuenteDatosOriginal = null;
					// Si es de tipo Fuente Datos, se obtiene la fuente de datos original
					if (dom.getTipo() == TypeDominio.FUENTE_DATOS && dom.getIdFuenteDatos() != null) {
						fuenteDatosOriginal = fuenteDatoDao.getById(dom.getIdFuenteDatos());
					}

					// Obtenemos la conf autenticacion si tiene asociada.
					ConfiguracionAutenticacion confAutOriginal = null;
					if (dom.getConfiguracionAutenticacion() != null) {
						confAutOriginal = dom.getConfiguracionAutenticacion();
					}

					FuenteDatos fdClonado = null;
					ConfiguracionAutenticacion confAutClonado = null;

					if (fuenteDatosOriginal != null) {
						// Clonamos la fuente de datos
						fdClonado = fuenteDatoDao.clonar(dom.getCodigo().toString(), TypeClonarAccion.CREAR,
								fuenteDatosOriginal, null, idArea);
					}
					if (confAutOriginal != null) {
						// Clonamos la conf autenticacion
						confAutClonado = configuracionAutenticacionDao.clonar(dom.getCodigo().toString(),
								TypeClonarAccion.CREAR, confAutOriginal, null, idArea);
					}

					// Clonamos el dominios y asociamos con las FD/ConfAut clonadas
					dominiosDao.clonar(dom.getCodigo().toString(), dom.getIdentificador(), idArea, null, fdClonado,
							confAutClonado);
				}
			}

		}

		// Luego clonar el trámite
		final Long idTramiteVersionNuevo = tramiteDao.clonarTramiteVersion(idTramiteVersion, idArea, idTramite);

		// Sustituimos referencias a los viejos scripts
		sustituirReferenciasScript(idTramiteVersionNuevo, idTramiteVersion);

		// Actualizamos el historial
		historialVersionDao.add(idTramiteVersionNuevo, usuario, TypeAccionHistorial.CREACION, "");

		return idTramiteVersionNuevo;
	}

	private void sustituirReferenciasScript(Long idNuevo, Long idViejo) {
		TramiteVersion tv = tramiteDao.getTramiteVersion(idNuevo);
		tv.setListaPasos(tramitePasoDao.getTramitePasos(idNuevo));
		TramiteVersion tvV = tramiteDao.getTramiteVersion(idViejo);
		String idN = tramiteDao.getById(tv.getIdTramite()).getIdentificadorEntidad() + "."
				+ tramiteDao.getById(tv.getIdTramite()).getIdentificadorArea();
		String idV = tramiteDao.getById(tvV.getIdTramite()).getIdentificadorEntidad() + "."
				+ tramiteDao.getById(tvV.getIdTramite()).getIdentificadorArea();
		List<TramitePaso> tp = tv.getListaPasos();
		if (tv.getScriptInicializacionTramite() != null) {
			sustituirString(scriptDao.getScript(tv.getScriptInicializacionTramite().getCodigo()), idN, idV);
		}
		if (tv.getScriptPersonalizacion() != null) {
			sustituirString(scriptDao.getScript(tv.getScriptPersonalizacion().getCodigo()), idN, idV);
		}
		if (tp != null && !tp.isEmpty()) {
			for (final TramitePaso paso : tp) {
				if (paso instanceof TramitePasoDebeSaber) {
					sustituirString(((TramitePasoDebeSaber) paso).getScriptDebeSaber(), idN, idV);
				} else if (paso instanceof TramitePasoRellenar) {
					sustituirString(((TramitePasoRellenar) paso).getScriptNavegacion(), idN, idV);
					sustituirString(((TramitePasoRellenar) paso).getScriptVariables(), idN, idV);
					List<FormularioTramite> forms = ((TramitePasoRellenar) paso).getFormulariosTramite();
					if (forms != null && !forms.isEmpty()) {
						for (final FormularioTramite formulario : forms) {
							sustituirString(formulario.getScriptDatosIniciales(), idN, idV);
							sustituirString(formulario.getScriptFirma(), idN, idV);
							sustituirString(formulario.getScriptObligatoriedad(), idN, idV);
							sustituirString(formulario.getScriptParametros(), idN, idV);
							sustituirString(formulario.getScriptRetorno(), idN, idV);
							final DisenyoFormulario disenyoFormulario = formularioInternoDao
									.getFormularioCompletoById(formulario.getIdFormularioInterno(), false);
							if (disenyoFormulario != null) {
								formulario.setDisenyoFormulario(disenyoFormulario);
							}
							if (formulario.getDisenyoFormulario() != null) {
								sustituirString(formulario.getDisenyoFormulario().getScriptPlantilla(), idN, idV);
								List<PaginaFormulario> pags = formulario.getDisenyoFormulario().getPaginas();
								if (pags != null && !pags.isEmpty()) {
									for (final PaginaFormulario paginaFormulario : pags) {
										sustituirString(paginaFormulario.getScriptValidacion(), idN, idV);
										sustituirString(paginaFormulario.getScriptNavegacion(), idN, idV);
										if (paginaFormulario.getLineas() != null) {
											for (final LineaComponentesFormulario linea : paginaFormulario
													.getLineas()) {
												if (linea.getComponentes() != null) {
													for (final ComponenteFormulario componente : linea
															.getComponentes()) {
														if (componente instanceof ComponenteFormularioCampo) {
															sustituirString(((ComponenteFormularioCampo) componente)
																	.getScriptAutorrellenable(), idN, idV);
															sustituirString(((ComponenteFormularioCampo) componente)
																	.getScriptSoloLectura(), idN, idV);
															sustituirString(((ComponenteFormularioCampo) componente)
																	.getScriptValidacion(), idN, idV);
															if (componente instanceof ComponenteFormularioCampoSelector) {
																final ComponenteFormularioCampoSelector selector = (ComponenteFormularioCampoSelector) componente;
																if (TypeListaValores.SCRIPT
																		.equals(selector.getTipoListaValores())) {
																	sustituirString(selector.getScriptValoresPosibles(),
																			idN, idV);
																}
															}
														}
													}
												}
											}
										}
									}

								}
							}
						}
					}
				} else if (paso instanceof TramitePasoAnexar) {
					((TramitePasoAnexar) paso).getScriptAnexosDinamicos();
					List<Documento> anexos = ((TramitePasoAnexar) paso).getDocumentos();
					if (anexos != null && !anexos.isEmpty()) {
						for (final Documento anexo : anexos) {
							sustituirString(anexo.getScriptFirmarDigitalmente(), idN, idV);
							sustituirString(anexo.getScriptObligatoriedad(), idN, idV);
							sustituirString(anexo.getScriptValidacion(), idN, idV);
						}
					}
				} else if (paso instanceof TramitePasoTasa) {
					List<Tasa> tasas = ((TramitePasoTasa) paso).getTasas();
					if (tasas != null && !tasas.isEmpty()) {
						for (final Tasa tasa : tasas) {
							sustituirString(tasa.getScriptObligatoriedad(), idN, idV);
							sustituirString(tasa.getScriptPago(), idN, idV);
						}
					}
				} else if (paso instanceof TramitePasoRegistrar) {
					sustituirString(((TramitePasoRegistrar) paso).getScriptAlFinalizar(), idN, idV);
					sustituirString(((TramitePasoRegistrar) paso).getScriptDestinoRegistro(), idN, idV);
					sustituirString(((TramitePasoRegistrar) paso).getScriptPresentador(), idN, idV);
					sustituirString(((TramitePasoRegistrar) paso).getScriptRepresentante(), idN, idV);
					sustituirString(((TramitePasoRegistrar) paso).getScriptValidarRegistrar(), idN, idV);
				}
				sustituirString(paso.getScriptNavegacion(), idN, idV);
				sustituirString(paso.getScriptVariables(), idN, idV);
			}
		}
	}

	private void sustituirString(Script script, String idN, String idV) {
		if (script != null && !script.getContenido().isEmpty()) {
			String contenido = script.getContenido();
			if (contenido.contains(idV)) {
				script.setContenido(contenido.replaceAll(idV, idN));
				scriptDao.updateScript(script);
			}
		}
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
	 * .lang.String)
	 */
	@Override
	@NegocioInterceptor
	public Area getAreaByIdentificador(final String identificadorEntidad, final String identificador) {
		return areaDao.getAreaByIdentificador(identificadorEntidad, identificador);
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
	public Tramite getTramiteByIdentificador(final String identificador, final Long idArea, String identificadorArea,
			Long codigoTramite) {
		return tramiteDao.getTramiteByIdentificador(identificador, idArea, identificadorArea, codigoTramite);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.TramiteService#
	 * getTramiteVersionByNumVersion(int, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public TramiteVersion getTramiteVersionByNumVersion(final int numeroVersion, final Long idTramite) {
		return tramiteDao.getTramiteVersionByNumVersion(numeroVersion, idTramite);
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

	@Override
	@NegocioInterceptor
	public List<DominioTramite> getTramiteVersionByDominio(final Long idDominio, final String filtro) {
		return tramiteDao.getTramiteVersionByDominio(idDominio, filtro);
	}

	@Override
	@NegocioInterceptor
	public List<SeccionReutilizableTramite> getTramiteVersionBySeccionReutilizable(Long idSeccionReutilizable) {
		return tramiteDao.getTramiteVersionBySeccionReutilizable(idSeccionReutilizable);
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
	public List<DominioTramite> getTramiteVersionByEnvioRemoto(final Long idEnvioRemoto) {
		return tramiteDao.getTramiteVersionByEnvioRemoto(idEnvioRemoto);
	}

	@Override
	@NegocioInterceptor
	public List<DominioTramite> getTramiteVersionByEnvioRemoto(final Long idEnvioRemoto, final String filtro) {
		return tramiteDao.getTramiteVersionByEnvioRemoto(idEnvioRemoto, filtro);
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
	public List<DominioTramite> getTramiteVersionByGfe(final Long idGfe) {
		return tramiteDao.getTramiteVersionByGfe(idGfe);
	}

	@Override
	@NegocioInterceptor
	public List<DominioTramite> getTramiteVersionByGfe(final Long idGfe, final String filtro) {
		return tramiteDao.getTramiteVersionByGfe(idGfe, filtro);
	}

	@Override
	@NegocioInterceptor
	public boolean getCountTramiteVersionByGfe(Long id) {
		return tramiteDao.getCountTramiteVersionByGfe(id);
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
	public boolean checkIdentificadorRepetido(final String identificador, final Long codigo, final Long idArea) {
		return tramiteDao.checkIdentificadorRepetido(identificador, codigo, idArea);
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
	public FilaImportarResultado importar(final FilaImportar filaImportar) throws Exception {
		final FilaImportarResultado resultado = new FilaImportarResultado();

		// Paso 1. Importamos el area
		final Long idArea = areaDao.importar(filaImportar.getFilaArea(), filaImportar.getIdEntidad());
		resultado.setIdArea(idArea);

		// Paso 2. Importamos el trámite.
		final Long idTramite = tramiteDao.importar(filaImportar.getFilaTramite(), idArea);
		resultado.setIdTramite(idTramite);

		/**
		 * IdDominios son los dominios que se relacionan con el tramite mientras que
		 * idDominiosEquivalencia es de donde viene, la id que equivale con el
		 * importado.
		 **/
		// Paso 3. Importamos los dominios y Fuentes de datos.
		final Map<Long, Long> idDominiosEquivalencia = new HashMap<>();
		final List<Long> idDominios = new ArrayList<>();
		for (final FilaImportarDominio filaDominio : filaImportar.getFilaDominios()) {

			// Si es de tipo FD , vemos de obtenerlo antes
			JFuenteDatos jfuenteDatos;
			if (filaDominio.getDominio().getTipo() == TypeDominio.FUENTE_DATOS) {
				jfuenteDatos = fuenteDatoDao.importarFD(filaDominio, filaDominio.getDominio().getAmbito(),
						filaImportar.getIdEntidad(), idArea);
			} else {
				jfuenteDatos = null;
			}

			final Long idDominio = dominiosDao.importar(filaDominio, filaImportar.getIdEntidad(), idArea, jfuenteDatos);
			idDominios.add(idDominio);
			idDominiosEquivalencia.put(idDominio, filaDominio.getDominio().getCodigo());
		}

		// Paso 4. Importamos los formateadores.
		final Map<Long, FormateadorFormulario> formateadores = new HashMap<>();
		final Map<Long, Long> mapFormateadores = new HashMap<>();
		for (final FilaImportarFormateador filaFormateador : filaImportar.getFilaFormateador()) {

			final Long idFormateador = formateadorFormularioDao.importar(filaFormateador, filaImportar.getIdEntidad());
			final FormateadorFormulario ff = formateadorFormularioDao.getById(idFormateador);
			formateadores.put(idFormateador, ff);
			mapFormateadores.put(filaFormateador.getFormateadorFormulario().getCodigo(), idFormateador);
		}

		// Paso 5. Importamos los gestores.
		final Map<Long, GestorExternoFormularios> gestores = new HashMap<>();
		final Map<Long, Long> mapGestores = new HashMap<>();
		if (filaImportar.getFilaGestor() != null) {
			for (final FilaImportarGestor filaGestor : filaImportar.getFilaGestor()) {

				final Long idGestor = gestorExternoDao.importar(filaGestor, idArea);
				final GestorExternoFormularios ff = gestorExternoDao.getById(idGestor);
				gestores.put(idGestor, ff);
				mapGestores.put(filaGestor.getGestor().getCodigo(), idGestor);
			}
		}

		// Paso 6. Importamos las secciones.
		final Map<Long, SeccionReutilizable> secciones = new HashMap<>();
		final Map<Long, Long> mapSecciones = new HashMap<>();
		if (filaImportar.getFilaSecciones() != null) {
			for (final FilaImportarSeccion filaSeccion : filaImportar.getFilaSecciones()) {

				final Long idFormularioAsociado = formularioInternoDao.importarFormularioSeccion(filaSeccion);
				final Long idSeccion = seccionReutilizableDao.importar(filaSeccion, filaImportar.getIdEntidad(),
						idFormularioAsociado);
				final SeccionReutilizable ff = seccionReutilizableDao.getSeccionReutilizable(idSeccion);
				secciones.put(idSeccion, ff);
				mapSecciones.put(filaSeccion.getSeccion().getCodigo(), idSeccion);
			}
		}


		// Paso 6. Importamos los diseños de la LE.
		final Map<Long, Long> mapDisenyosLE = new HashMap<>();
		if (filaImportar.getDisenyosLE() != null && !filaImportar.getDisenyosLE().isEmpty()) {
			for(Entry<Long, DisenyoFormulario> dato : filaImportar.getDisenyosLE().entrySet()) {
				ComponenteFormularioListaElementos componente = new ComponenteFormularioListaElementos();
				//Introducimos el diseño de la LE: dato.getValue()
				Long idNuevo = formularioInternoDao.addFormulario(componente );
				Map<String,Long> formulariosId = null;
				Long idJFormulario = idNuevo;
				JFormularioTramite formulario = null;
				tramitePasoDao.importarDisenyoFormulario(formulariosId, formulario, idJFormulario, filaImportar.getFormularios(), filaImportar.getFicherosContent(),
						idDominiosEquivalencia, filaImportar.getIdEntidad(), secciones, mapSecciones, formateadores,
						mapFormateadores, mapDisenyosLE, false, dato.getValue());
				mapDisenyosLE.put(dato.getKey(), idNuevo);
			}
		}

		// Importamos la version de trámite
		final Long idTramiteVersion = tramiteDao.importar(filaImportar.getFilaTramiteVersion(), idTramite, idDominios,
				filaImportar.getUsuario(), filaImportar.isModoIM());
		resultado.setIdTramiteVersion(idTramiteVersion);

		// Importamos los pasos
		int ordenPaso = 1;
		final List<TramitePaso> pasos = filaImportar.getFilaTramiteVersion().getTramiteVersion().getListaPasos();
		Collections.sort(pasos, new Comparator<TramitePaso>() {
			@Override
			public int compare(final TramitePaso p1, final TramitePaso p2) {
				return Integer.compare(p1.getOrden(), p2.getOrden());
			}
		});
		for (final TramitePaso tramitePaso : pasos) {
			tramitePaso.setOrden(ordenPaso);
			reordenar(tramitePaso);
			if (tramitePaso.getTipo().equals(TypePaso.FINALIZAR)) {
				if (((TramitePasoRegistrar) tramitePaso).getDestino() == null) {
					((TramitePasoRegistrar) tramitePaso).setDestino("R");
				}
			}
			tramitePasoDao.importar(filaImportar.getFilaTramiteRegistro(), tramitePaso, idTramiteVersion,
					filaImportar.getIdEntidad(), filaImportar.getFormularios(), filaImportar.getFicheros(),
					filaImportar.getFicherosContent(), formateadores, mapFormateadores, gestores, mapGestores,
					idDominiosEquivalencia, idArea, mapSecciones, secciones, mapDisenyosLE);
			ordenPaso++;
		}

		return resultado;
	}

	/**
	 * Fuerza un orden de 1 en ascendente de tasas/anexos/formularios.
	 *
	 * @param tramitePaso
	 */
	private void reordenar(final TramitePaso tramitePaso) {
		if (tramitePaso instanceof TramitePasoTasa) {
			((TramitePasoTasa) tramitePaso).reordenar();
		}
		if (tramitePaso instanceof TramitePasoAnexar) {
			((TramitePasoAnexar) tramitePaso).reordenar();
		}
		if (tramitePaso instanceof TramitePasoRellenar) {
			((TramitePasoRellenar) tramitePaso).reordenar();
		}
	}

	@Override
	@NegocioInterceptor
	public boolean checkFormularioRepetido(final Long idTramiteVersion, final String identificador,
			final Long idFormulario) {
		return tramitePasoDao.checkFormularioRepetido(idTramiteVersion, identificador, idFormulario);
	}

	@Override
	@NegocioInterceptor
	public TramiteSimple getTramiteSimple(final String idTramiteVersion) {
		return tramiteDao.getTramiteSimple(idTramiteVersion);
	}

	@Override
	@NegocioInterceptor
	public String getIdiomasDisponibles(final String idTramiteVersion) {
		return tramiteDao.getIdiomasDisponibles(idTramiteVersion);
	}

	@Override
	@NegocioInterceptor
	public List<ErrorValidacion> validarVersionTramite(final Long id, final String idioma) {
		return validadorComponent.comprobarVersionTramite(id, idioma);
	}

	@Override
	@NegocioInterceptor
	public List<ErrorValidacion> validarVersionTramite(final TramiteVersion pTramiteVersion, final String pIdioma) {
		return validadorComponent.comprobarVersionTramite(pTramiteVersion, pIdioma);
	}

	@Override
	@NegocioInterceptor
	public List<ErrorValidacion> validarScript(final Script pScript, final List<Dominio> pListaDominios,
			final List<String> pIdiomasTramiteVersion, final String pIdioma) {
		return validadorComponent.comprobarScript(pScript, pListaDominios, pIdiomasTramiteVersion, pIdioma);
	}

	@Override
	@NegocioInterceptor
	public String getIdentificadorByCodigoVersion(final Long codigo) {
		return tramiteDao.getIdentificadorByCodigoVersion(codigo);
	}

	@Override
	@NegocioInterceptor
	public void permiteSubsanacion(final Long idPaso, final boolean activarSubsanacion) {
		tramitePasoDao.permiteSubsanacion(idPaso, activarSubsanacion);
	}

	@Override
	@NegocioInterceptor
	public void updateLiteral(final Literal pLiteral) {
		tramiteDao.updateLiteral(pLiteral);
	}

	@Override
	@NegocioInterceptor
	public void updateScript(final Script pScript) {
		scriptDao.updateScript(pScript);
	}

	@Override
	@NegocioInterceptor
	public TramiteVersion createTramiteVersionDefault(final Integer pNumVersion, final String pIdiomasSoportados,
			final String pDatosUsuarioBloqueo) {
		return tramiteComponent.createTramiteVersionDefault(pNumVersion, pIdiomasSoportados, pDatosUsuarioBloqueo);
	}

	@Override
	@NegocioInterceptor
	public List<TramitePaso> createListaPasosNormalizado() {
		return tramiteComponent.createNormalizado();
	}

	@Override
	@NegocioInterceptor
	public Documento createDocumentoDefault() {
		return tramiteComponent.createDocumentoDefault();
	}

	@Override
	@NegocioInterceptor
	public Tasa createTasaDefault() {
		return tramiteComponent.createTasaDefault();
	}

	@Override
	@NegocioInterceptor
	public FormularioTramite createFormularioTramiteDefault() {
		return tramiteComponent.createFormularioTramiteDefault();
	}

	@Override
	@NegocioInterceptor
	public void borradoHistorial(final Long idTramiteVersion, final String username) {
		historialVersionDao.borradoHistorial(idTramiteVersion, username);
	}

	@Override
	@NegocioInterceptor
	public List<ScriptInfo> listScriptsInfo(final Long idTramiteVersion) {
		final List<ScriptInfo> scriptsInfo = new ArrayList<>();

		final TramiteVersion tv = tramiteDao.getTramiteVersion(idTramiteVersion);
		if (tv.getScriptInicializacionTramite() != null) {
			scriptsInfo.add(new ScriptInfo(tv.getScriptInicializacionTramite().getCodigo(), null,
					TypeScriptFlujo.SCRIPT_PARAMETROS_INICIALES));
		}
		if (tv.getScriptPersonalizacion() != null) {
			scriptsInfo.add(new ScriptInfo(tv.getScriptPersonalizacion().getCodigo(), null,
					TypeScriptFlujo.SCRIPT_PERSONALIZACION_TRAMITE));
		}

		// Primero recorremos los pasos
		for (final TramitePaso tramitePaso : tramitePasoDao.getTramitePasos(idTramiteVersion)) {

			if (tramitePaso.getScriptNavegacion() != null) {
				scriptsInfo.add(new ScriptInfo(tramitePaso.getScriptNavegacion().getCodigo(), getTypePaso(tramitePaso),
						TypeScriptFlujo.SCRIPT_NAVEGACION_PASO));
			}

			if (tramitePaso.getScriptVariables() != null) {
				scriptsInfo.add(new ScriptInfo(tramitePaso.getScriptVariables().getCodigo(), getTypePaso(tramitePaso),
						TypeScriptFlujo.SCRIPT_VARIABLE_FLUJO));
			}

			if (tramitePaso instanceof TramitePasoAnexar) {

				final List<ScriptInfo> scripts = listScriptsInfo((TramitePasoAnexar) tramitePaso);
				scriptsInfo.addAll(scripts);

			} else if (tramitePaso instanceof TramitePasoRellenar) {

				final List<ScriptInfo> scripts = listScriptsInfo((TramitePasoRellenar) tramitePaso);
				scriptsInfo.addAll(scripts);

			} else if (tramitePaso instanceof TramitePasoRegistrar) {

				final List<ScriptInfo> scripts = listScriptsInfo((TramitePasoRegistrar) tramitePaso);
				scriptsInfo.addAll(scripts);

			} else if (tramitePaso instanceof TramitePasoTasa) {

				final List<ScriptInfo> scripts = listScriptsInfo((TramitePasoTasa) tramitePaso);
				scriptsInfo.addAll(scripts);

			} else if (tramitePaso instanceof TramitePasoDebeSaber) {
				final List<ScriptInfo> scripts = listScriptsInfo((TramitePasoDebeSaber) tramitePaso);
				scriptsInfo.addAll(scripts);
			}

		}
		return scriptsInfo;
	}

	/**
	 * Listscript de un paso de tipo anexar.
	 *
	 * @param paso
	 * @return
	 */
	private List<ScriptInfo> listScriptsInfo(final TramitePasoAnexar paso) {
		final List<ScriptInfo> scriptsInfo = new ArrayList<>();
		if (paso.getScriptAnexosDinamicos() != null) {
			scriptsInfo.add(new ScriptInfo(paso.getScriptAnexosDinamicos().getCodigo(), TypePaso.ANEXAR,
					TypeScriptFlujo.SCRIPT_LISTA_DINAMICA_ANEXOS));
		}

		if (paso.getDocumentos() != null) {
			for (final Documento doc : paso.getDocumentos()) {
				if (doc.getScriptFirmarDigitalmente() != null) {
					scriptsInfo.add(new ScriptInfo(doc.getScriptFirmarDigitalmente().getCodigo(), TypePaso.ANEXAR,
							TypeScriptFlujo.SCRIPT_FIRMANTES, doc));
				}
				if (doc.getScriptObligatoriedad() != null) {
					scriptsInfo.add(new ScriptInfo(doc.getScriptObligatoriedad().getCodigo(), TypePaso.ANEXAR,
							TypeScriptFlujo.SCRIPT_DEPENDENCIA_DOCUMENTO, doc));
				}
				if (doc.getScriptValidacion() != null) {
					scriptsInfo.add(new ScriptInfo(doc.getScriptValidacion().getCodigo(), TypePaso.ANEXAR,
							TypeScriptFlujo.SCRIPT_VALIDAR_ANEXO, doc));
				}
			}
		}
		return scriptsInfo;
	}

	/**
	 * Listscript de un paso de tipo rellenar.
	 *
	 * @param paso
	 * @return
	 */
	private List<ScriptInfo> listScriptsInfo(final TramitePasoRellenar paso) {
		final List<ScriptInfo> scriptsInfo = new ArrayList<>();
		if (paso.getFormulariosTramite() != null) {
			for (final FormularioTramite formulario : paso.getFormulariosTramite()) {
				if (formulario.getScriptDatosIniciales() != null) {
					scriptsInfo.add(new ScriptInfo(formulario.getScriptDatosIniciales().getCodigo(), TypePaso.RELLENAR,
							TypeScriptFlujo.SCRIPT_DATOS_INICIALES_FORMULARIO, formulario));
				}
				if (formulario.getScriptFirma() != null) {
					scriptsInfo.add(new ScriptInfo(formulario.getScriptFirma().getCodigo(), TypePaso.RELLENAR,
							TypeScriptFlujo.SCRIPT_FIRMANTES, formulario));
				}
				if (formulario.getScriptObligatoriedad() != null) {
					scriptsInfo.add(new ScriptInfo(formulario.getScriptObligatoriedad().getCodigo(), TypePaso.RELLENAR,
							TypeScriptFlujo.SCRIPT_DEPENDENCIA_DOCUMENTO, formulario));
				}
				if (formulario.getScriptParametros() != null) {
					scriptsInfo.add(new ScriptInfo(formulario.getScriptParametros().getCodigo(), TypePaso.RELLENAR,
							TypeScriptFlujo.SCRIPT_PARAMETROS_FORMULARIO, formulario));
				}
				if (formulario.getScriptRetorno() != null) {
					scriptsInfo.add(new ScriptInfo(formulario.getScriptRetorno().getCodigo(), TypePaso.RELLENAR,
							TypeScriptFlujo.SCRIPT_POSTGUARDAR_FORMULARIO, formulario));
				}

				final List<ScriptInfo> scriptsComponent = formularioInternoDao
						.getScriptsInfo(formulario.getIdFormularioInterno(), formulario);
				scriptsInfo.addAll(scriptsComponent);
			}
		}

		return scriptsInfo;
	}

	/**
	 * Listscript de un paso de tipo registrar.
	 *
	 * @param paso
	 * @return
	 */
	private List<ScriptInfo> listScriptsInfo(final TramitePasoRegistrar paso) {
		final List<ScriptInfo> scriptsInfo = new ArrayList<>();
		if (paso.getScriptDestinoRegistro() != null) {
			scriptsInfo.add(new ScriptInfo(paso.getScriptDestinoRegistro().getCodigo(), TypePaso.FINALIZAR,
					TypeScriptFlujo.SCRIPT_PARAMETROS_REGISTRO));
		}
		if (paso.getScriptAlFinalizar() != null) {
			scriptsInfo.add(new ScriptInfo(paso.getScriptAlFinalizar().getCodigo(), TypePaso.FINALIZAR,
					TypeScriptFlujo.SCRIPT_AVISO));
		}
		if (paso.getScriptPresentador() != null) {
			scriptsInfo.add(new ScriptInfo(paso.getScriptPresentador().getCodigo(), TypePaso.FINALIZAR,
					TypeScriptFlujo.SCRIPT_PRESENTADOR_REGISTRO));
		}
		if (paso.getScriptRepresentante() != null) {
			scriptsInfo.add(new ScriptInfo(paso.getScriptRepresentante().getCodigo(), TypePaso.FINALIZAR,
					TypeScriptFlujo.SCRIPT_REPRESENTACION_REGISTRO));
		}
		if (paso.getScriptValidarRegistrar() != null) {
			scriptsInfo.add(new ScriptInfo(paso.getScriptValidarRegistrar().getCodigo(), TypePaso.FINALIZAR,
					TypeScriptFlujo.SCRIPT_PERMITIR_REGISTRO));
		}
		if (paso.getScriptVariables() != null) {
			scriptsInfo.add(new ScriptInfo(paso.getScriptVariables().getCodigo(), TypePaso.FINALIZAR,
					TypeScriptFlujo.SCRIPT_VARIABLE_FLUJO));
		}
		return scriptsInfo;
	}

	/**
	 * Listscript de un paso de tipo tasa.
	 *
	 * @param paso
	 * @return
	 */
	private List<ScriptInfo> listScriptsInfo(final TramitePasoTasa paso) {
		final List<ScriptInfo> scriptsInfo = new ArrayList<>();
		if (paso.getTasas() != null) {
			for (final Tasa tasa : paso.getTasas()) {
				if (tasa.getScriptObligatoriedad() != null) {
					scriptsInfo.add(new ScriptInfo(tasa.getScriptObligatoriedad().getCodigo(), TypePaso.PAGAR,
							TypeScriptFlujo.SCRIPT_DEPENDENCIA_DOCUMENTO, tasa));
				}
				if (tasa.getScriptPago() != null) {
					scriptsInfo.add(new ScriptInfo(tasa.getScriptPago().getCodigo(), TypePaso.PAGAR,
							TypeScriptFlujo.SCRIPT_DATOS_PAGO, tasa));
				}
			}
		}
		return scriptsInfo;
	}

	/**
	 * Listscript de un paso de tipo debe saber.
	 *
	 * @param paso
	 * @return
	 */
	private List<ScriptInfo> listScriptsInfo(final TramitePasoDebeSaber paso) {
		final List<ScriptInfo> scriptsInfo = new ArrayList<>();
		if (paso.getScriptDebeSaber() != null) {
			scriptsInfo.add(new ScriptInfo(paso.getScriptDebeSaber().getCodigo(), TypePaso.DEBESABER,
					TypeScriptFlujo.SCRIPT_DEBE_SABER));
		}
		return scriptsInfo;
	}

	/**
	 * Obtiene el literal del paso.
	 */
	private final TypePaso getTypePaso(final TramitePaso tramitePaso) {
		TypePaso paso;
		if (tramitePaso instanceof TramitePasoRellenar) {
			paso = TypePaso.RELLENAR;
		} else if (tramitePaso instanceof TramitePasoAnexar) {
			paso = TypePaso.ANEXAR;
		} else if (tramitePaso instanceof TramitePasoTasa) {
			paso = TypePaso.PAGAR;
		} else if (tramitePaso instanceof TramitePasoRegistrar) {
			paso = TypePaso.FINALIZAR;
		} else if (tramitePaso instanceof TramitePasoDebeSaber) {
			paso = TypePaso.DEBESABER;
		} else {
			paso = null;
		}

		return paso;
	}

	@Override
	@NegocioInterceptor
	public List<GestorExternoFormularios> getGFEByTramiteVersion(final Long idTramiteVersion) {
		return tramiteDao.getGFEByTramiteVersion(idTramiteVersion);
	}

	@Override
	@NegocioInterceptor
	public void borrarScriptsVersion(final Long idTramiteVersion, final boolean propiedades, final boolean rellenar,
			final boolean anexo, final boolean tasas, final boolean registrar, final boolean propcaptura) {

		if (propiedades) {
			tramitePasoDao.borrarScriptsPropiedades(idTramiteVersion);
		}

		for (TramitePaso tramitePaso : tramitePasoDao.getTramitePasos(idTramiteVersion)) {

			if (tramitePaso instanceof TramitePasoRegistrar && registrar) {
				tramitePasoDao.borrarScriptsRegistro(tramitePaso.getCodigo());
			} else if (tramitePaso instanceof TramitePasoTasa && tasas) {
				tramitePasoDao.borrarScriptsPago(tramitePaso.getCodigo());
			} else if (tramitePaso instanceof TramitePasoAnexar && anexo) {
				tramitePasoDao.borrarScriptsAnexo(tramitePaso.getCodigo());
			} else if (tramitePaso instanceof TramitePasoRellenar && rellenar) {
				tramitePasoDao.borrarScriptsRellenar(tramitePaso.getCodigo(), idTramiteVersion);
			}
			// TODO Aun no está creado el tramitePasoCaptura
//				else if (tramitePaso instanceof TramitePasoCaptura && propcaptura) {
//						tramitePasoDao.borrarScriptsCaptura(tramitePaso.getCodigo());
//				}
		}

	}

	@Override
	@NegocioInterceptor
	public int listTramiteTotal(Long idEntidad, List<Long> areas, String filtro) {

		return tramiteDao.getTotalByFiltro(idEntidad, areas, filtro);
	}

	@Override
	@NegocioInterceptor
	public List<Tramite> listTramite(int first, int pageSize, String sortField, boolean asc, Long idEntidad,
			List<Long> areas, String filtro) {

		return tramiteDao.getAllByFiltro(first, pageSize, sortField, asc, idEntidad, areas, filtro);
	}

	@Override
	@NegocioInterceptor
	public void actualizarDominios(TramiteVersion tramiteVersion, final List<ValorIdentificadorCompuesto> dominios) {
		tramiteDao.actualizarDominios(tramiteVersion, dominios);
	}

	@Override
	@NegocioInterceptor
	public List<ErrorValidacion> checkDominioNoUtilizado(Long idDominio, Long idTramiteVersion, final String idioma) {
		return validadorComponent.checkDominioNoUtilizado(idDominio, idTramiteVersion, idioma);
	}

	@Override
	@NegocioInterceptor
	public List<SeccionReutilizable> getSeccionesTramite(Long idTramiteVersion) {
		return tramiteDao.getSeccionesReutilizableByTramite(idTramiteVersion);
	}

	@Override
	@NegocioInterceptor
	public boolean existenTramitesBySeccionReutilizable(Long idSeccionReutilizable) {
		return tramiteDao.existenTramiteVersionBySeccionReutilizable(idSeccionReutilizable);
	}

	@Override
	@NegocioInterceptor
	public List<Long> getDisenyosLEByTramite(Long codigo) {
		return tramiteDao.getDisenyosLEByTramite(codigo);
	}

}
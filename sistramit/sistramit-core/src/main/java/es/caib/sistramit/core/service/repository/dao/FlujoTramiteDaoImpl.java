package es.caib.sistramit.core.service.repository.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.GeneradorId;
import es.caib.sistramit.core.api.exception.RepositoryException;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoTramite;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroTramiteFinalizado;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroTramitePersistencia;
import es.caib.sistramit.core.api.model.system.rest.externo.TramiteFinalizado;
import es.caib.sistramit.core.api.model.system.rest.externo.TramitePersistencia;
import es.caib.sistramit.core.api.model.system.rest.interno.FicheroPersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPaginacion;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPagoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPerdidaClave;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.PagoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.PersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.types.TypeTramitePersistencia;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaTramite;
import es.caib.sistramit.core.service.model.flujo.EstadoPersistenciaPasoTramite;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.types.TypeDocumentoPersistencia;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.system.PerdidaClaveFichero;
import es.caib.sistramit.core.service.repository.model.HDocumento;
import es.caib.sistramit.core.service.repository.model.HFichero;
import es.caib.sistramit.core.service.repository.model.HFirma;
import es.caib.sistramit.core.service.repository.model.HPaso;
import es.caib.sistramit.core.service.repository.model.HSesionTramitacion;
import es.caib.sistramit.core.service.repository.model.HTramite;
import es.caib.sistramit.core.service.repository.model.HTramiteFinalizado;

/**
 * Implementación DAO Flujo Tramite.
 *
 * @author Indra
 */
@Repository("flujoTramiteDao")
public final class FlujoTramiteDaoImpl implements FlujoTramiteDao {

	private static final String ASCENDING = "ASCENDING";
	/**
	 * Entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public String crearSesionTramitacion() {
		// Generamos id sesion
		final String id = GeneradorId.generarId();
		// Creamos sesion
		final HSesionTramitacion hSesionTramitacion = new HSesionTramitacion();
		hSesionTramitacion.setIdSesionTramitacion(id);
		hSesionTramitacion.setFecha(new Date());
		entityManager.persist(hSesionTramitacion);
		return id;
	}

	@Override
	public void crearTramitePersistencia(final DatosPersistenciaTramite dpt) {
		// Mapeamos propiedades
		final HTramite hTramite = HTramite.fromModel(dpt);
		// Establecemos sesion tramitacion
		final HSesionTramitacion hSesionTramitacion = findHSesionTramitacion(dpt.getIdSesionTramitacion());
		if (hSesionTramitacion == null) {
			throw new RepositoryException("No existeix tràmit: " + dpt.getIdSesionTramitacion());
		}
		hTramite.setSesionTramitacion(hSesionTramitacion);
		// Establecemos fecha ultimo acceso
		dpt.setFechaUltimoAcceso(new Date());
		// Actualizamos bbdd
		entityManager.persist(hTramite);
	}

	@Override
	public DatosPersistenciaTramite obtenerTramitePersistencia(final String idSesionTramitacion) {
		final HTramite hTramite = getHTramite(idSesionTramitacion);
		return HTramite.toModel(hTramite);
	}

	@Override
	public Date registraAccesoTramite(final String pIdSesionTramitacion, final Date pFechaCaducidad) {
		// Buscamos tramite y actualizamos propiedades
		final HTramite hTramite = getHTramite(pIdSesionTramitacion);
		final Timestamp timestampFlujo = new Timestamp(System.currentTimeMillis());
		final Date fecha = new Date();
		hTramite.setFechaUltimoAcceso(fecha);
		hTramite.setFechaCaducidad(pFechaCaducidad);
		hTramite.setTimestamp(timestampFlujo);

		// Actualizamos bbdd
		entityManager.merge(hTramite);

		// Devolvamos timestamp flujo
		return timestampFlujo;

	}

	@Override
	public boolean verificaTimestampFlujo(final String pIdSesionTramitacion, final Date pTimestampFlujo) {
		// Buscamos tramite
		final HTramite hTramite = getHTramite(pIdSesionTramitacion);
		// Verificamos que coincida el timestamp
		return (hTramite.getTimestamp().compareTo(pTimestampFlujo) == 0);
	}

	@Override
	public void cambiaEstadoTramite(final String pIdSesionTramitacion, final TypeEstadoTramite pEstado) {
		final HTramite hTramite = getHTramite(pIdSesionTramitacion);
		// Actualizamos estado
		if (pEstado == TypeEstadoTramite.FINALIZADO) {
			hTramite.setFechaFin(new Date());
		}
		hTramite.setEstado(pEstado.toString());
		entityManager.merge(hTramite);

		// Si finalizado, persistimos en tabla de finalizados
		if (pEstado == TypeEstadoTramite.FINALIZADO && !hTramite.isCancelado()) {
			final HTramiteFinalizado hTramiteFin = new HTramiteFinalizado();
			hTramiteFin.setIdSesionTramitacion(hTramite.getSesionTramitacion().getIdSesionTramitacion());
			hTramiteFin.setFechaFinalizacion(hTramite.getFechaFin());
			hTramiteFin.setIdTramite(hTramite.getIdTramite());
			hTramiteFin.setVersionTramite(hTramite.getVersionTramite());
			hTramiteFin.setDescripcionTramite(hTramite.getDescripcionTramite());
			hTramiteFin.setIdProcedimientoSIA(hTramite.getIdProcedimientoSIA());
			hTramiteFin.setIdioma(hTramite.getIdioma());
			hTramiteFin.setAutenticacion(hTramite.getAutenticacion());
			hTramiteFin.setMetodoAutenticacion(hTramite.getMetodoAutenticacion());
			hTramiteFin.setNifIniciador(hTramite.getNifIniciador());
			if (StringUtils.isNotBlank(hTramite.getNifIniciador())) {
				hTramiteFin.setNombreIniciador(hTramite.getNombreIniciador()
						+ StringUtils.defaultIfBlank(" " + hTramite.getApellido1Iniciador(), "")
						+ StringUtils.defaultIfBlank(" " + hTramite.getApellido2Iniciador(), ""));
			}
			// Si existe paso registro, anotamos numero de registro y presentador
			final List<HPaso> lstPasos = findHPasos(pIdSesionTramitacion);
			for (final HPaso p : lstPasos) {
				if (TypePaso.fromString(p.getTipoPaso()) == TypePaso.REGISTRAR) {
					for (final HDocumento hdoc : p.getDocumentos()) {
						if (TypeDocumentoPersistencia
								.fromString(hdoc.getTipo()) == TypeDocumentoPersistencia.REGISTRO) {
							hTramiteFin.setNumeroRegistro(hdoc.getRegistroNumeroRegistro());
							hTramiteFin.setNifPresentador(hdoc.getRegistroNifPresentador());
							hTramiteFin.setNombrePresentador(hdoc.getRegistroNombrePresentador());
							break;
						}
					}
					break;
				}
			}
			// Si no existe paso registro, presentador será el iniciador
			if (hTramiteFin.getNumeroRegistro() == null) {
				hTramiteFin.setNumeroRegistro(hTramiteFin.getNifIniciador());
				hTramiteFin.setNifPresentador(hTramiteFin.getNombreIniciador());
			}

			entityManager.persist(hTramiteFin);

		}

	}

	@Override
	public void crearPaso(final String pIdSesionTramitacion, final String pIdPaso, final TypePaso pTipoPaso,
			final int orden) {

		// Buscamos tramite
		final HTramite hTramite = getHTramite(pIdSesionTramitacion);

		// Creamos paso
		final HPaso hPaso = new HPaso();
		hPaso.setIdentificadorPaso(pIdPaso);
		hPaso.setEstado(TypeEstadoPaso.NO_INICIALIZADO.toString());
		hPaso.setTipoPaso(pTipoPaso.toString());
		hPaso.setTramitePersistencia(hTramite);
		hPaso.setOrden(orden);

		entityManager.persist(hPaso);
		entityManager.flush();

	}

	@Override
	public void eliminarPaso(final String pIdSesionTramitacion, final String pIdPaso) {

		// Obtenemos paso
		final HPaso paso = getHPaso(pIdSesionTramitacion, pIdPaso);

		// Buscamos ficheros a eliminar
		final List<ReferenciaFichero> ficherosEliminar = new ArrayList<>();

		// Recorremos documentos para ver los ficheros a eliminar
		for (final HDocumento hdoc : paso.getDocumentos()) {
			// Buscamos ficheros del documento
			ficherosEliminar.addAll(obtenerFicherosDocumento(hdoc));
			// Buscamos firmas documento
			ficherosEliminar.addAll(obtenerFirmasDocumento(hdoc));
		}

		// Eliminamos paso
		entityManager.remove(paso);

		// Hacemos fluhs antes de eliminar los ficheros
		entityManager.flush();

		// Eliminamos ficheros (firmas y ficheros)
		eliminarFicheros(ficherosEliminar);

	}

	@Override
	public void eliminarPasos(final String pIdSesionTramitacion) {
		final List<HPaso> listaPasos = findHPasos(pIdSesionTramitacion);
		for (final HPaso paso : listaPasos) {
			eliminarPaso(pIdSesionTramitacion, paso.getIdentificadorPaso());
		}
	}

	@Override
	public List<EstadoPersistenciaPasoTramite> obtenerListaPasos(final String pIdSesionTramitacion) {

		final List<HPaso> lstPasos = findHPasos(pIdSesionTramitacion);
		final List<EstadoPersistenciaPasoTramite> lista = new ArrayList<>();
		for (final HPaso paso : lstPasos) {
			final EstadoPersistenciaPasoTramite ept = EstadoPersistenciaPasoTramite
					.createNewEstadoPersistenciaPasoTramite();
			ept.setEstado(TypeEstadoPaso.fromString(paso.getEstado()));
			ept.setId(paso.getIdentificadorPaso());
			ept.setTipo(TypePaso.fromString(paso.getTipoPaso()));
			ept.setOrden(paso.getOrden());
			lista.add(ept);
		}

		return lista;
	}

	@Override
	public void purgarTramite(final String pIdSesionTramitacion) {
		// Eliminamos pasos de tramitacion
		eliminarPasos(pIdSesionTramitacion);
		// Marca el tramite como purgado
		final HTramite hTramite = getHTramite(pIdSesionTramitacion);
		hTramite.setPurgado(true);
		hTramite.setFechaPurgado(new Date());
		entityManager.merge(hTramite);
	}

	@Override
	public void cancelarTramite(final String pIdSesionTramitacion) {
		// Marca el tramite como cancelado
		final HTramite hTramite = getHTramite(pIdSesionTramitacion);
		hTramite.setEstado(TypeEstadoTramite.FINALIZADO.toString());
		hTramite.setFechaFin(new Date());
		hTramite.setCancelado(true);
		entityManager.merge(hTramite);
	}

	@Override
	public Long contadorLimiteTramitacion(final String idTramite, final int version, final int pLimiteIntervalo,
			final Date finIntervalo) {

		Long res = null;

		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(finIntervalo);
		calendar.add(Calendar.MINUTE, pLimiteIntervalo * ConstantesNumero.N_1);
		final Date inicioIntervalo = calendar.getTime();

		final String hql = "SELECT COUNT(*) FROM HTramite WHERE idTramite = :idTramite AND versionTramite = :versionTramite "
				+ " AND fechaInicio >= :fechaInicio AND fechaInicio <= :fechaFin";
		final Query query = entityManager.createQuery(hql);
		query.setParameter("idTramite", idTramite);
		query.setParameter("versionTramite", version);
		query.setParameter("fechaInicio", inicioIntervalo);
		query.setParameter("fechaFin", finIntervalo);
		res = (Long) query.getSingleResult();

		return res;
	}

	@Override
	public List<PerdidaClaveFichero> obtenerTramitesPerdidaClave(final FiltroPerdidaClave pFiltroBusqueda) {
		List<PerdidaClaveFichero> result = null;

		final CriteriaQuery<PerdidaClaveFichero> query = obtenerTramitesPerdidaClaveCriteria(pFiltroBusqueda,
				PerdidaClaveFichero.class, false);

		result = entityManager.createQuery(query).getResultList();

		return result;

	}

	@Override
	public Long countTramitesPerdidaClave(final FiltroPerdidaClave pFiltroBusqueda) {
		final CriteriaQuery<Long> queryCount = obtenerTramitesPerdidaClaveCriteria(pFiltroBusqueda, Long.class, true);
		return entityManager.createQuery(queryCount).getSingleResult();
	}

	@Override
	public Long countPagos(final FiltroPagoAuditoria pFiltroBusqueda) {
		final CriteriaQuery<Long> queryCount = obtenerPagosCriteria(pFiltroBusqueda, Long.class, true);
		return entityManager.createQuery(queryCount).getSingleResult();
	}

	@Override
	public List<PagoAuditoria> obtenerPagos(final FiltroPagoAuditoria pFiltroBusqueda,
			final FiltroPaginacion filtroPaginacion) {
		List<PagoAuditoria> result = null;

		final CriteriaQuery<PagoAuditoria> query = obtenerPagosCriteria(pFiltroBusqueda, PagoAuditoria.class, false);

		if (filtroPaginacion == null) {
			result = entityManager.createQuery(query).getResultList();
		} else {
			result = entityManager.createQuery(query).setFirstResult(filtroPaginacion.getFirst())
					.setMaxResults(filtroPaginacion.getPageSize()).getResultList();
		}

		return result;

	}

	@Override
	public Long countTramitesPersistencia(final FiltroPersistenciaAuditoria pFiltroBusqueda) {
		final CriteriaQuery<Long> queryCount = obtenerTramitesPersistenciaCriteria(pFiltroBusqueda, Long.class, true);
		return entityManager.createQuery(queryCount).getSingleResult();
	}

	@Override
	public List<PersistenciaAuditoria> obtenerTramitesPersistencia(final FiltroPersistenciaAuditoria pFiltroBusqueda,
			final FiltroPaginacion filtroPaginacion) {
		List<PersistenciaAuditoria> result = null;

		final CriteriaQuery<PersistenciaAuditoria> query = obtenerTramitesPersistenciaCriteria(pFiltroBusqueda,
				PersistenciaAuditoria.class, false);

		if (filtroPaginacion == null) {
			result = entityManager.createQuery(query).getResultList();
		} else {
			result = entityManager.createQuery(query).setFirstResult(filtroPaginacion.getFirst())
					.setMaxResults(filtroPaginacion.getPageSize()).getResultList();
		}

		return result;

	}

	@Override
	public List<FicheroPersistenciaAuditoria> recuperarPersistenciaFicheros(final Long pIdTramite) {

		final List<FicheroPersistenciaAuditoria> result = recuperarPersistenciaFicherosCriteria(pIdTramite, "fichero");

		result.addAll(recuperarPersistenciaFicherosCriteria(pIdTramite, "formularioPdf"));

		result.addAll(recuperarPersistenciaFicherosCriteria(pIdTramite, "pagoJustificantePdf"));

		Collections.sort(result, (a, b) -> a.getCodigo().compareTo(b.getCodigo()));

		result.addAll(recuperarPersistenciaFicherosFirmaCriteria(pIdTramite));

		return result;
	}

	@Override
	public List<TramitePersistencia> recuperarTramitesPersistencia(final FiltroTramitePersistencia pFiltro) {
		return recuperarTramitesPersistenciaCriteria(pFiltro);
	}

	/**
	 * Añade filtro a la where.
	 *
	 * @param hql
	 *                   hql
	 * @param filtro
	 *                   filtro
	 * @param        boolean
	 *                   si ya existen parametros
	 * @return hql
	 */
	private String addFiltroToWhere(String hql, final String filtro, final boolean existeParams) {
		if (existeParams) {
			hql += " and ";
		}
		hql += " " + filtro + " ";
		return hql;
	}

	@Override
	public List<TramiteFinalizado> recuperarTramitesFinalizados(final FiltroTramiteFinalizado pFiltro) {

		final Map<String, Object> params = new LinkedHashMap<String, Object>();

		String hql = "SELECT t FROM HTramiteFinalizado t WHERE ";
		boolean existeParams = false;
		if (pFiltro.getNif() != null) {
			hql = addFiltroToWhere(hql, "t.nifPresentador = :nif", existeParams);
			params.put("nif", pFiltro.getNif());
			existeParams = true;
		}
		if (pFiltro.getIdSesionTramitacion() != null) {
			hql = addFiltroToWhere(hql, "t.idSesionTramitacion = :idSesionTramitacion", existeParams);
			params.put("idSesionTramitacion", pFiltro.getIdSesionTramitacion());
			existeParams = true;
		}
		if (pFiltro.getFechaDesde() != null) {
			hql = addFiltroToWhere(hql, "t.fechaFinalizacion >= :fechaDesde", existeParams);
			params.put("fechaDesde", pFiltro.getFechaDesde());
			existeParams = true;
		}
		if (pFiltro.getFechaHasta() != null) {
			hql = addFiltroToWhere(hql, "t.fechaFinalizacion <= :fechaHasta", existeParams);
			params.put("fechaHasta", pFiltro.getFechaHasta());
			existeParams = true;
		}
		final Query query = entityManager.createQuery(hql);
		for (final String paramName : params.keySet()) {
			query.setParameter(paramName, params.get(paramName));
		}

		final List<HTramiteFinalizado> queryResult = query.getResultList();

		final List<TramiteFinalizado> result = new ArrayList<>();
		for (final HTramiteFinalizado h : queryResult) {
			result.add(HTramiteFinalizado.toModel(h));
		}
		return result;
	}

	// ------------ FUNCIONES PRIVADAS --------------------------------------

	public List<TramitePersistencia> recuperarTramitesPersistenciaCriteria(final FiltroTramitePersistencia pFiltro) {

		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<TramitePersistencia> query = builder.createQuery(TramitePersistencia.class);

		final Root<HTramite> tableT = query.from(HTramite.class);
		final Root<HSesionTramitacion> tableS = query.from(HSesionTramitacion.class);

		Predicate predicate = builder.equal(tableT.get("sesionTramitacion"), tableS);

		// Filtro por idSesionTramitacion o por (nif/fecha inicio/fecha fin)
		if (StringUtils.isNotBlank(pFiltro.getIdSesionTramitacion())) {
			predicate = builder.and(predicate, builder.equal(tableS.get("idSesionTramitacion"),
					pFiltro.getIdSesionTramitacion().trim().toUpperCase()));
		} else {
			predicate = builder.and(predicate,
					builder.equal(tableT.get("nifIniciador"), pFiltro.getNif().trim().toUpperCase()));

			if (pFiltro.getFechaDesde() != null) {
				predicate = builder.and(predicate,
						builder.greaterThanOrEqualTo(tableT.get("fechaInicio"), pFiltro.getFechaDesde()));
			}

			if (pFiltro.getFechaHasta() != null) {
				predicate = builder.and(predicate,
						builder.lessThanOrEqualTo(tableT.get("fechaHasta"), pFiltro.getFechaHasta()));
			}
		}

		// Filtro de tramite persistente no finalizado
		// - Estado rellenando
		predicate = builder.and(predicate,
				builder.equal(tableT.get("estado"), TypeEstadoTramite.RELLENANDO.toString()));
		// - Persistente, no cancelado y no purgado
		predicate = builder.and(predicate, builder.isTrue(tableT.get("persistente")));
		predicate = builder.and(predicate, builder.isFalse(tableT.get("cancelado")));
		predicate = builder.and(predicate, builder.isFalse(tableT.get("purgado")));
		predicate = builder.and(predicate, builder.isFalse(tableT.get("purgaPendientePorPagoRealizado")));

		// - Sin que se haya cumplido fecha caducidad
		Predicate predicateFechaCaducidad = builder.lessThan(builder.currentTimestamp(), tableT.get("fechaCaducidad"));
		predicateFechaCaducidad = builder.or(predicateFechaCaducidad, builder.isNull(tableT.get("fechaCaducidad")));
		predicate = builder.and(predicate, predicateFechaCaducidad);

		query.where(predicate);
		query.orderBy(builder.desc(tableT.get("fechaInicio")));

		query.multiselect(tableS.get("idSesionTramitacion"), tableT.get("idioma"), tableT.get("descripcionTramite"),
				tableT.get("idTramite"), tableT.get("versionTramite"), tableT.get("fechaInicio"),
				tableT.get("fechaUltimoAcceso"));

		final List<TramitePersistencia> result = entityManager.createQuery(query).getResultList();

		return result;
	}

	public List<FicheroPersistenciaAuditoria> recuperarPersistenciaFicherosFirmaCriteria(final Long pIdTramite) {

		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<FicheroPersistenciaAuditoria> query = builder
				.createQuery(FicheroPersistenciaAuditoria.class);

		final Root<HTramite> tableT = query.from(HTramite.class);
		final Root<HPaso> tableP = query.from(HPaso.class);
		final Root<HDocumento> tableD = query.from(HDocumento.class);
		final Root<HFichero> tableFic = query.from(HFichero.class);
		final Root<HFirma> tableFirma = query.from(HFirma.class);

		Predicate predicate = builder.equal(tableP.get("tramitePersistencia"), tableT);
		predicate = builder.and(predicate, builder.equal(tableD.get("paso"), tableP));
		predicate = builder.and(predicate, builder.equal(tableFirma.get("documentoPersistente"), tableD));
		predicate = builder.and(predicate, builder.equal(tableFirma.get("firma"), tableFic));

		predicate = builder.and(predicate, builder.equal(tableT.get("codigo"), pIdTramite));
		predicate = builder.and(predicate,
				builder.equal(tableD.get("estado"), TypeEstadoDocumento.RELLENADO_CORRECTAMENTE.toString()));

		query.where(predicate);

		query.multiselect(tableP.get("identificadorPaso"), tableP.get("tipoPaso"), tableFic.get("nombre"),
				tableFic.get("codigo"), tableFic.get("clave"), tableD.get("tipo"));

		final List<FicheroPersistenciaAuditoria> result = entityManager.createQuery(query).getResultList();

		return result;
	}

	public List<FicheroPersistenciaAuditoria> recuperarPersistenciaFicherosCriteria(final Long pIdTramite,
			final String pReferencia) {

		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<FicheroPersistenciaAuditoria> query = builder
				.createQuery(FicheroPersistenciaAuditoria.class);

		final Root<HTramite> tableT = query.from(HTramite.class);
		final Root<HPaso> tableP = query.from(HPaso.class);
		final Root<HDocumento> tableD = query.from(HDocumento.class);
		final Root<HFichero> tableFic = query.from(HFichero.class);

		Predicate predicate = builder.equal(tableP.get("tramitePersistencia"), tableT);
		predicate = builder.and(predicate, builder.equal(tableD.get("paso"), tableP));
		predicate = builder.and(predicate, builder.equal(tableD.get(pReferencia), tableFic));

		predicate = builder.and(predicate, builder.equal(tableT.get("codigo"), pIdTramite));
		predicate = builder.and(predicate,
				builder.equal(tableD.get("estado"), TypeEstadoDocumento.RELLENADO_CORRECTAMENTE.toString()));

		query.where(predicate);

		query.multiselect(tableP.get("identificadorPaso"), tableP.get("tipoPaso"), tableFic.get("nombre"),
				tableFic.get("codigo"), tableFic.get("clave"), tableD.get("tipo"));

		final List<FicheroPersistenciaAuditoria> result = entityManager.createQuery(query).getResultList();

		return result;
	}

	private <T> CriteriaQuery<T> obtenerTramitesPersistenciaCriteria(final FiltroPersistenciaAuditoria pFiltroBusqueda,
			final Class<T> pTipo, final boolean pCount) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<T> query = builder.createQuery(pTipo);

		final Root<HSesionTramitacion> tableS = query.from(HSesionTramitacion.class);
		final Root<HTramite> tableT = query.from(HTramite.class);
		final Root<HPaso> tableP = query.from(HPaso.class);
		// final Root<HDocumento> tableD = query.from(HDocumento.class);

		final Join<HPaso, HDocumento> tableD = tableP.join("documentos", JoinType.LEFT);

		Predicate predicate = builder.equal(tableT.get("sesionTramitacion"), tableS);
		predicate = builder.and(predicate, builder.equal(tableP.get("tramitePersistencia"), tableT));
		// predicate = builder.and(predicate, builder.equal(tableD.get("paso"),
		// tableP));

		if (pFiltroBusqueda.getListaAreas() != null) {
			predicate = builder.and(predicate, tableT.get("idArea").in(pFiltroBusqueda.getListaAreas()));
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getIdSesionTramitacion())) {
			predicate = builder.and(predicate,
					builder.equal(tableS.get("idSesionTramitacion"), pFiltroBusqueda.getIdSesionTramitacion()));
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getNif())) {
			predicate = builder.and(predicate,
					builder.like(tableT.get("nifIniciador"), "%" + pFiltroBusqueda.getNif() + "%"));
		}

		if (pFiltroBusqueda.getFechaDesde() != null) {
			predicate = builder.and(predicate,
					builder.greaterThanOrEqualTo(tableT.get("fechaInicio"), pFiltroBusqueda.getFechaDesde()));
		}

		if (pFiltroBusqueda.getFechaHasta() != null) {
			predicate = builder.and(predicate,
					builder.lessThanOrEqualTo(tableT.get("fechaInicio"), pFiltroBusqueda.getFechaHasta()));
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getIdTramite())) {
			predicate = builder.and(predicate, builder.equal(tableT.get("idTramite"), pFiltroBusqueda.getIdTramite()));
		}

		if (pFiltroBusqueda.getVersionTramite() != null) {
			predicate = builder.and(predicate,
					builder.equal(tableT.get("versionTramite"), pFiltroBusqueda.getVersionTramite()));
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getIdProcedimientoCP())) {
			predicate = builder.and(predicate,
					builder.equal(tableT.get("idProcedimientoCP"), pFiltroBusqueda.getIdProcedimientoCP()));
		}

		if (TypeTramitePersistencia.PAGO_REALIZADO_TRAMITE_SIN_FINALIZAR
				.equals(pFiltroBusqueda.getTipoTramitePersistencia())) {
			predicate = builder.and(predicate,
					builder.equal(tableT.get("estado"), TypeEstadoTramite.FINALIZADO.toString()));

			predicate = builder.and(predicate,
					builder.equal(tableD.get("tipo"), TypeDocumentoPersistencia.PAGO.toString()));
			predicate = builder.and(predicate,
					builder.equal(tableD.get("estado"), TypeEstadoDocumento.RELLENADO_CORRECTAMENTE.toString()));
		}

		query.where(predicate);

		if (pCount) {
			query.multiselect(builder.countDistinct(tableT));
		} else {
			if (StringUtils.isEmpty(pFiltroBusqueda.getSortField())) {
				query.orderBy(builder.desc(tableT.get("fechaInicio")));
			} else {
				if ("fechaInicio".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableT.get("fechaInicio")));
					} else {
						query.orderBy(builder.desc(tableT.get("fechaInicio")));
					}
				} else if ("idTramite".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableT.get("idTramite")));
					} else {
						query.orderBy(builder.desc(tableT.get("idTramite")));
					}
				} else if ("versionTramite".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableT.get("versionTramite")));
					} else {
						query.orderBy(builder.desc(tableT.get("versionTramite")));
					}
				} else if ("descripcionTramite".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableT.get("descripcionTramite")));
					} else {
						query.orderBy(builder.desc(tableT.get("descripcionTramite")));
					}
				} else if ("idSesionTramitacion".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableS.get("idSesionTramitacion")));
					} else {
						query.orderBy(builder.desc(tableS.get("idSesionTramitacion")));
					}
				} else if ("nif".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableT.get("nifIniciador")));
					} else {
						query.orderBy(builder.desc(tableT.get("nifIniciador")));
					}
				} else if ("nombre".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableT.get("nombreIniciador")),
								builder.asc(tableT.get("apellido1Iniciador")),
								builder.asc(tableT.get("apellido2Iniciador")));
					} else {
						query.orderBy(builder.desc(tableT.get("nombreIniciador")),
								builder.desc(tableT.get("apellido1Iniciador")),
								builder.desc(tableT.get("apellido2Iniciador")));
					}
				} else if ("fechaUltimoAcceso".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableT.get("fechaUltimoAcceso")));
					} else {
						query.orderBy(builder.desc(tableT.get("fechaUltimoAcceso")));
					}
				} else if ("estado".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableT.get("estado")));
					} else {
						query.orderBy(builder.desc(tableT.get("estado")));
					}
				}

			}

			query.distinct(true);
			query.multiselect(tableT.get("codigo"), tableS.get("idSesionTramitacion"), tableT.get("idTramite"),
					tableT.get("versionTramite"), tableT.get("idProcedimientoCP"), tableT.get("nifIniciador"),
					tableT.get("nombreIniciador"), tableT.get("apellido1Iniciador"), tableT.get("apellido2Iniciador"),
					tableT.get("fechaInicio"), tableT.get("estado"), tableT.get("cancelado"),
					tableT.get("fechaCaducidad"), tableT.get("purgar"), tableT.get("fechaPurgado"),
					tableT.get("purgado"), tableT.get("descripcionTramite"), tableT.get("fechaUltimoAcceso"),
					tableT.get("fechaFin"), tableT.get("persistente"), tableT.get("urlInicio"));
		}

		return query;
	}

	private <T> CriteriaQuery<T> obtenerPagosCriteria(final FiltroPagoAuditoria pFiltroBusqueda, final Class<T> pTipo,
			final boolean pCount) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<T> query = builder.createQuery(pTipo);

		final Root<HTramite> tableT = query.from(HTramite.class);
		final Root<HPaso> tableP = query.from(HPaso.class);
		final Root<HDocumento> tableD = query.from(HDocumento.class);
		final Root<HSesionTramitacion> tableS = query.from(HSesionTramitacion.class);

		Predicate predicate = builder.notEqual(tableD.get("estado"), TypeEstadoDocumento.SIN_RELLENAR.toString());
		predicate = builder.and(predicate,
				builder.equal(tableD.get("tipo"), TypeDocumentoPersistencia.PAGO.toString()));
		predicate = builder.and(predicate, builder.equal(tableT.get("sesionTramitacion"), tableS));
		predicate = builder.and(predicate, builder.equal(tableP.get("tramitePersistencia"), tableT));
		predicate = builder.and(predicate, builder.equal(tableD.get("paso"), tableP));

		if (pFiltroBusqueda.getListaAreas() != null) {
			predicate = builder.and(predicate, tableT.get("idArea").in(pFiltroBusqueda.getListaAreas()));
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getIdSesionTramitacion())) {
			predicate = builder.and(predicate,
					builder.equal(tableS.get("idSesionTramitacion"), pFiltroBusqueda.getIdSesionTramitacion()));
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getNif())) {
			predicate = builder.and(predicate,
					builder.like(tableD.get("pagoNifSujetoPasivo"), "%" + pFiltroBusqueda.getNif() + "%"));
		}

		if (pFiltroBusqueda.getFechaDesde() != null) {
			predicate = builder.and(predicate,
					builder.greaterThanOrEqualTo(tableT.get("fechaInicio"), pFiltroBusqueda.getFechaDesde()));
		}

		if (pFiltroBusqueda.getFechaHasta() != null) {
			predicate = builder.and(predicate,
					builder.lessThanOrEqualTo(tableT.get("fechaInicio"), pFiltroBusqueda.getFechaHasta()));
		}

		if (pFiltroBusqueda.getAcceso() != null) {
			predicate = builder.and(predicate,
					builder.equal(tableT.get("autenticacion"), pFiltroBusqueda.getAcceso().toString()));
		}

		query.where(predicate);

		if (pCount) {
			query.multiselect(builder.count(tableD));
		} else {
			if (StringUtils.isEmpty(pFiltroBusqueda.getSortField())) {
				query.orderBy(builder.desc(tableT.get("fechaInicio")));
			} else {
				if ("fecha".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableT.get("fechaInicio")));
					} else {
						query.orderBy(builder.desc(tableT.get("fechaInicio")));
					}
				} else if ("idTramite".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableT.get("idTramite")));
					} else {
						query.orderBy(builder.desc(tableT.get("idTramite")));
					}
				} else if ("versionTramite".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableT.get("versionTramite")));
					} else {
						query.orderBy(builder.desc(tableT.get("versionTramite")));
					}
				} else if ("idSesionTramitacion".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableS.get("idSesionTramitacion")));
					} else {
						query.orderBy(builder.desc(tableS.get("idSesionTramitacion")));
					}
				} else if ("estadoPago".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableD.get("estado")));
					} else {
						query.orderBy(builder.desc(tableD.get("estado")));
					}
				}
			}

			query.multiselect(tableS.get("idSesionTramitacion"), tableT.get("fechaInicio"), tableT.get("idTramite"),
					tableT.get("versionTramite"), tableT.get("idProcedimientoCP"), tableD.get("fichero"),
					tableD.get("ficheroClave"), tableD.get("codigo"), tableD.get("estado"),
					tableD.get("pagoEstadoIncorrecto"));
		}

		return query;
	}

	private <T> CriteriaQuery<T> obtenerTramitesPerdidaClaveCriteria(final FiltroPerdidaClave pFiltroBusqueda,
			final Class<T> pTipo, final boolean pCount) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<T> query = builder.createQuery(pTipo);

		final Root<HTramite> tableT = query.from(HTramite.class);
		final Root<HPaso> tableP = query.from(HPaso.class);
		final Root<HDocumento> tableD = query.from(HDocumento.class);
		final Root<HSesionTramitacion> tableS = query.from(HSesionTramitacion.class);

		Predicate predicate = builder.equal(tableT.get("autenticacion"), TypeAutenticacion.ANONIMO.toString());
		predicate = builder.and(predicate,
				builder.equal(tableD.get("tipo"), TypeDocumentoPersistencia.FORMULARIO.toString()));
		predicate = builder.and(predicate,
				builder.equal(tableD.get("estado"), TypeEstadoDocumento.RELLENADO_CORRECTAMENTE.toString()));

		predicate = builder.and(predicate,
				builder.notEqual(tableT.get("estado"), TypeEstadoTramite.FINALIZADO.toString()));
		predicate = builder.and(predicate, builder.isFalse(tableT.get("cancelado")));
		predicate = builder.and(predicate, builder.isFalse(tableT.get("purgado")));
		predicate = builder.and(predicate, builder.isFalse(tableT.get("purgar")));

		predicate = builder.and(predicate, builder.equal(tableT.get("sesionTramitacion"), tableS));
		predicate = builder.and(predicate, builder.equal(tableP.get("tramitePersistencia"), tableT));
		predicate = builder.and(predicate, builder.equal(tableD.get("paso"), tableP));

		if (pFiltroBusqueda.getListaAreas() != null) {
			predicate = builder.and(predicate, tableT.get("idArea").in(pFiltroBusqueda.getListaAreas()));
		}

		if (pFiltroBusqueda.getFechaDesde() != null) {
			predicate = builder.and(predicate,
					builder.greaterThanOrEqualTo(tableT.get("fechaInicio"), pFiltroBusqueda.getFechaDesde()));
		}

		if (pFiltroBusqueda.getFechaHasta() != null) {
			predicate = builder.and(predicate,
					builder.lessThanOrEqualTo(tableT.get("fechaInicio"), pFiltroBusqueda.getFechaHasta()));
		}
		if (StringUtils.isNoneBlank(pFiltroBusqueda.getIdTramite())) {
			predicate = builder.and(predicate, builder.equal(tableT.get("idTramite"), pFiltroBusqueda.getIdTramite()));
		}

		if (pFiltroBusqueda.getVersionTramite() != null) {
			predicate = builder.and(predicate,
					builder.equal(tableT.get("versionTramite"), pFiltroBusqueda.getVersionTramite()));
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getIdProcedimientoCP())) {
			predicate = builder.and(predicate,
					builder.equal(tableT.get("idProcedimientoCP"), pFiltroBusqueda.getIdProcedimientoCP()));
		}

		query.where(predicate);

		if (pCount) {
			query.multiselect(builder.count(tableD));
		} else {
			query.orderBy(builder.desc(tableT.get("fechaInicio")));
			query.multiselect(tableS.get("idSesionTramitacion"), tableT.get("fechaInicio"), tableT.get("idTramite"),
					tableT.get("versionTramite"), tableT.get("idProcedimientoCP"), tableD.get("fichero"),
					tableD.get("ficheroClave"));
		}

		return query;
	}

	/**
	 * Busca sesion tramitacion.
	 *
	 * @param idSesionTramitacion
	 *                                id sesion tramitacion
	 * @return sesion tramitacion
	 */
	private HSesionTramitacion findHSesionTramitacion(final String idSesionTramitacion) {
		HSesionTramitacion hSesion = null;
		final String sql = "SELECT t FROM HSesionTramitacion t WHERE t.idSesionTramitacion = :idSesionTramitacion";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idSesionTramitacion", idSesionTramitacion);
		final List<?> results = query.getResultList();
		if (!results.isEmpty()) {
			hSesion = (HSesionTramitacion) results.get(0);
		}
		return hSesion;
	}

	/**
	 * Busca tramite.
	 *
	 * @param idSesionTramitacion
	 *                                id sesion tramitacion
	 * @return sesion tramitacion
	 */
	private HTramite findHTramite(final String idSesionTramitacion) {
		HTramite hTramite = null;
		final String sql = "SELECT t from HTramite t where t.sesionTramitacion.idSesionTramitacion = :idSesionTramitacion";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idSesionTramitacion", idSesionTramitacion);
		final List<?> results = query.getResultList();
		if (!results.isEmpty()) {
			hTramite = (HTramite) results.get(0);
		}
		return hTramite;
	}

	/**
	 * Busca tramite y genera excepcion si no lo encuentra.
	 *
	 * @param idSesionTramitacion
	 *                                id sesion tramitacion
	 * @return sesion tramitacion
	 */
	private HTramite getHTramite(final String pIdSesionTramitacion) {
		final HTramite hTramite = findHTramite(pIdSesionTramitacion);
		if (hTramite == null) {
			throw new RepositoryException("No existeix tràmit: " + pIdSesionTramitacion);
		}
		return hTramite;
	}

	/**
	 * Busca pasos tramite.
	 *
	 * @param idSesionTramitacion
	 *                                id sesion tramitacion
	 * @return pasos tramite
	 */
	@SuppressWarnings("unchecked")
	private List<HPaso> findHPasos(final String idSesionTramitacion) {
		final String sql = "select p from HPaso p inner join p.tramitePersistencia t where t.sesionTramitacion.idSesionTramitacion=:idSesionTramitacion order by p.codigo";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idSesionTramitacion", idSesionTramitacion);
		return query.getResultList();
	}

	/**
	 * Busca paso tramite.
	 *
	 * @param idSesionTramitacion
	 *                                id sesion tramitacion
	 * @return paso tramite
	 */
	@SuppressWarnings("unchecked")
	private HPaso findHPaso(final String idSesionTramitacion, final String idPaso) {
		final String sql = "select p from HPaso p inner join p.tramitePersistencia t where t.sesionTramitacion.idSesionTramitacion=:idSesionTramitacion and p.identificadorPaso = :idPaso order by p.codigo";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idSesionTramitacion", idSesionTramitacion);
		query.setParameter("idPaso", idPaso);
		final List<HPaso> results = query.getResultList();
		HPaso res = null;
		if (!results.isEmpty()) {
			if (results.size() > ConstantesNumero.N1) {
				throw new RepositoryException("S'ha trobat " + results.size() + " amb id passa " + idPaso
						+ " per id sessió tramitació " + idSesionTramitacion);
			}
			res = results.get(0);
		}
		return res;
	}

	/**
	 * Busca paso tramite y genera excepción si no lo encuentra.
	 *
	 * @param idSesionTramitacion
	 *                                id sesion tramitacion
	 * @return paso tramite
	 */
	private HPaso getHPaso(final String idSesionTramitacion, final String idPaso) {
		final HPaso paso = findHPaso(idSesionTramitacion, idPaso);
		if (paso == null) {
			throw new RepositoryException(
					"No es troba passa " + idPaso + " per id sessió tramitació " + idSesionTramitacion);
		}
		return paso;
	}

	/**
	 * Obtiene ficheros de las firmas de un documento.
	 *
	 * @param hdoc
	 *                 HDocumento
	 * @return ficheros de las firmas de un documento.
	 */
	private List<ReferenciaFichero> obtenerFirmasDocumento(final HDocumento hdoc) {
		ReferenciaFichero ref;
		final List<ReferenciaFichero> ficherosDoc = new ArrayList<>();
		for (final HFirma hFirma : hdoc.getFirmas()) {
			if (hFirma.getFirma() != null) {
				ref = ReferenciaFichero.createNewReferenciaFichero(hFirma.getFirma(), hFirma.getFirmaClave());
				ficherosDoc.add(ref);
			}
		}
		return ficherosDoc;
	}

	/**
	 * Obtiene ficheros deun documento.
	 *
	 * @param hdoc
	 *                 HDocumento
	 * @return ficheros de un documento.
	 */
	private List<ReferenciaFichero> obtenerFicherosDocumento(final HDocumento hdoc) {
		ReferenciaFichero ref;
		final List<ReferenciaFichero> ficherosDoc = new ArrayList<>();
		if (hdoc.getFichero() != null) {
			ref = ReferenciaFichero.createNewReferenciaFichero(hdoc.getFichero(), hdoc.getFicheroClave());
			ficherosDoc.add(ref);
		}
		if (hdoc.getFormularioPdf() != null) {
			ref = ReferenciaFichero.createNewReferenciaFichero(hdoc.getFormularioPdf(), hdoc.getFormularioPdfClave());
			ficherosDoc.add(ref);
		}
		if (hdoc.getPagoJustificantePdf() != null) {
			ref = ReferenciaFichero.createNewReferenciaFichero(hdoc.getPagoJustificantePdf(),
					hdoc.getPagoJustificantePdfClave());
			ficherosDoc.add(ref);
		}
		return ficherosDoc;
	}

	/**
	 * Método para Eliminar ficheros de la clase FlujoTramiteDaoImpl.
	 *
	 * @param ficheros
	 *                     Parámetro ficheros
	 */
	private void eliminarFicheros(final List<ReferenciaFichero> ficheros) {
		if (!ficheros.isEmpty()) {
			final StringBuffer sb = new StringBuffer(ConstantesNumero.N40 * ficheros.size());
			sb.append("delete from HFichero where ");
			boolean primer = true;
			for (final ReferenciaFichero ref : ficheros) {
				if (primer) {
					primer = false;
				} else {
					sb.append(" or ");
				}
				sb.append("codigo = " + ref.getId());
			}
			final String sql = sb.toString();
			final Query query = entityManager.createQuery(sql);
			query.executeUpdate();
		}
	}

}

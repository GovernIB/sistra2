package es.caib.sistramit.core.service.repository.dao;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoTramite;
import es.caib.sistramit.core.api.model.flujo.types.TypeSoporteEstado;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.system.rest.externo.Evento;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroEvento;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroTramiteFinalizado;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroTramitePersistencia;
import es.caib.sistramit.core.api.model.system.rest.externo.TramiteFinalizado;
import es.caib.sistramit.core.api.model.system.rest.externo.TramitePersistencia;
import es.caib.sistramit.core.api.model.system.rest.interno.ErroresPorTramiteCM;
import es.caib.sistramit.core.api.model.system.rest.interno.ErroresPorTramiteCMRe;
import es.caib.sistramit.core.api.model.system.rest.interno.EventoAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.rest.interno.EventoCM;
import es.caib.sistramit.core.api.model.system.rest.interno.FicheroPersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroEventoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPaginacion;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPagoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPerdidaClave;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FormularioSoporte;
import es.caib.sistramit.core.api.model.system.rest.interno.PagoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.PersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.types.TypeEvento;
import es.caib.sistramit.core.api.model.system.types.TypeTramitePersistencia;
import es.caib.sistramit.core.service.model.flujo.DatosFicheroPersistencia;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.types.TypeDocumentoPersistencia;
import es.caib.sistramit.core.service.model.system.PerdidaClaveFichero;
import es.caib.sistramit.core.service.repository.model.HDocumento;
import es.caib.sistramit.core.service.repository.model.HEventoAuditoria;
import es.caib.sistramit.core.service.repository.model.HFichero;
import es.caib.sistramit.core.service.repository.model.HFirma;
import es.caib.sistramit.core.service.repository.model.HPaso;
import es.caib.sistramit.core.service.repository.model.HSesionTramitacion;
import es.caib.sistramit.core.service.repository.model.HSoporte;
import es.caib.sistramit.core.service.repository.model.HTramite;
import es.caib.sistramit.core.service.repository.model.HTramiteFinalizado;

/**
 * Implementación DAO Rest Api.
 *
 * @author Indra
 */
@Repository("restApiDao")
public final class RestApiDaoImpl implements RestApiDao {

	private static final String ASCENDING = "ASCENDING";
	/**
	 * Entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

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
	 * @param hql    hql
	 * @param filtro filtro
	 * @param        boolean si ya existen parametros
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

	@Override
	public DatosFicheroPersistencia recuperarFicheroPersistencia(final ReferenciaFichero pRefFic) {
		final HFichero hFichero = findHFichero(pRefFic);
		DatosFicheroPersistencia dfp = null;
		if (hFichero != null) {
			dfp = new DatosFicheroPersistencia();
			dfp.setNombre(hFichero.getNombre());
			dfp.setContenido(hFichero.getContenido());
		}
		return dfp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DatosFicheroPersistencia recuperarFicheroPersistenciaNoBorrado(final ReferenciaFichero pRefFic) {

		HFichero hFichero = null;
		DatosFicheroPersistencia dfp = null;

		final String sql = "SELECT f from HFichero f where f.codigo=:codigo and f.clave=:clave and f.borrar=0";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("codigo", pRefFic.getId());
		query.setParameter("clave", pRefFic.getClave());
		final List<HFichero> results = query.getResultList();

		if (!results.isEmpty()) {
			hFichero = results.get(0);

			if (hFichero != null) {
				dfp = new DatosFicheroPersistencia();
				dfp.setNombre(hFichero.getNombre());
				dfp.setContenido(hFichero.getContenido());
			}
		}

		return dfp;
	}

	@Override
	public DocumentoPasoPersistencia obtenerDocumento(final Long pIdDoc) {
		DocumentoPasoPersistencia documento = null;

		final HDocumento hDoc = entityManager.find(HDocumento.class, pIdDoc);

		if (hDoc != null) {
			documento = HDocumento.toModel(hDoc, 0);
		}

		return documento;
	}

	@Override
	public Long countByAreas(final FiltroEventoAuditoria pFiltros) {

		CriteriaQuery<Long> queryCount = null;

		if (pFiltros.isErrorPlataforma()) {
			queryCount = retrieveByAreasAndNoIdSesionCriteria(pFiltros, Long.class, true);
		} else {
			queryCount = retrieveByAreasCriteria(pFiltros, Long.class, true);
		}
		return entityManager.createQuery(queryCount).getSingleResult();

	}

	@Override
	public List<Evento> recuperarEventos(final FiltroEvento pFiltro) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Evento> query = builder.createQuery(Evento.class);

		final Root<HEventoAuditoria> tableE = query.from(HEventoAuditoria.class);
		final Root<HTramite> tableT = query.from(HTramite.class);

		final Join<HEventoAuditoria, HSesionTramitacion> p = tableE.join("sesionTramitacion", JoinType.LEFT);

		Predicate predicate = builder.equal(tableE.get("sesionTramitacion"), tableT.get("sesionTramitacion"));

		/*
		 * final Date minDate = DateUtils.truncate(pFiltro.getFecha(), Calendar.DATE);
		 * final Date maxDate = new Date(minDate.getTime() + TimeUnit.DAYS.toMillis(1) -
		 * 1); predicate = builder.and(predicate, builder.between(tableE.get("fecha"),
		 * minDate, maxDate));
		 */

		predicate = builder.and(predicate, builder.greaterThanOrEqualTo(tableE.get("fecha"), pFiltro.getFecha()));

		if (pFiltro.getListaEventos() != null && !pFiltro.getListaEventos().isEmpty()) {
			predicate = builder.and(predicate,
					tableE.get("tipo").in(getListaEventosToString(pFiltro.getListaEventos())));
		} else {
			predicate = builder.and(predicate,
					tableE.get("tipo").in(getListaEventosToString(TypeEvento.getEventosExternos())));
		}

		query.where(predicate);
		query.orderBy(builder.asc(tableE.get("fecha")));
		query.multiselect(p.get("idSesionTramitacion"), tableE.get("tipo"), tableE.get("fecha"),
				tableT.get("nifIniciador"), tableT.get("idTramite"), tableT.get("versionTramite"),
				tableT.get("idProcedimientoCP"), tableT.get("idProcedimientoSIA"), tableE.get("codigoError"),
				tableE.get("descripcion"), tableE.get("resultado"), tableE.get("trazaError"), tableE.get("detalle"));
		final List<Evento> resultado = entityManager.createQuery(query).getResultList();

		return resultado;
	}

	@Override
	public List<EventoAuditoriaTramitacion> retrieveByAreas(final FiltroEventoAuditoria pFiltroBusqueda) {
		return retrieveByAreasImpl(pFiltroBusqueda, null);
	}

	@Override
	public List<EventoAuditoriaTramitacion> retrieveByAreas(final FiltroEventoAuditoria pFiltroBusqueda,
			final FiltroPaginacion filtroPaginacion) {
		return retrieveByAreasImpl(pFiltroBusqueda, filtroPaginacion);
	}

	// ------------ FUNCIONES PRIVADAS --------------------------------------

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
			predicate = builder.and(predicate, builder.like(tableS.get("idSesionTramitacion"),
					"%" + pFiltroBusqueda.getIdSesionTramitacion() + "%"));
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
			predicate = builder.and(predicate,
					builder.like(tableT.get("idTramite"), "%" + pFiltroBusqueda.getIdTramite() + "%"));
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
					builder.notEqual(tableT.get("estado"), TypeEstadoTramite.FINALIZADO.toString()));

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
			predicate = builder.and(predicate, builder.like(tableS.get("idSesionTramitacion"),
					"%" + pFiltroBusqueda.getIdSesionTramitacion() + "%"));
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
	 * Busca fichero.
	 *
	 * @param referenciaFichero referenciaFichero
	 * @return HFichero
	 */
	private HFichero findHFichero(final ReferenciaFichero referenciaFichero) {
		HFichero hFichero = null;
		final String sql = "SELECT f from HFichero f where f.codigo=:codigo and f.clave=:clave";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("codigo", referenciaFichero.getId());
		query.setParameter("clave", referenciaFichero.getClave());
		final List<HFichero> results = query.getResultList();
		if (!results.isEmpty()) {
			hFichero = results.get(0);
		}
		return hFichero;
	}

	private List<EventoAuditoriaTramitacion> retrieveByAreasImpl(final FiltroEventoAuditoria pFiltroBusqueda,
			final FiltroPaginacion filtroPaginacion) {
		List<EventoAuditoriaTramitacion> result = null;

		CriteriaQuery<EventoAuditoriaTramitacion> query = null;

		if (pFiltroBusqueda.isErrorPlataforma()) {
			query = retrieveByAreasAndNoIdSesionCriteria(pFiltroBusqueda, EventoAuditoriaTramitacion.class, false);
		} else {
			query = retrieveByAreasCriteria(pFiltroBusqueda, EventoAuditoriaTramitacion.class, false);
		}

		if (filtroPaginacion == null) {
			result = entityManager.createQuery(query).getResultList();
		} else {
			result = entityManager.createQuery(query).setFirstResult(filtroPaginacion.getFirst())
					.setMaxResults(filtroPaginacion.getPageSize()).getResultList();
		}

		return result;
	}

	private <T> CriteriaQuery<T> retrieveByAreasCriteria(final FiltroEventoAuditoria pFiltroBusqueda,
			final Class<T> pTipo, final boolean pCount) {

		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<T> query = builder.createQuery(pTipo);

		final Root<HEventoAuditoria> tableE = query.from(HEventoAuditoria.class);
		final Root<HTramite> tableT = query.from(HTramite.class);

		final Join<HEventoAuditoria, HSesionTramitacion> p = tableE.join("sesionTramitacion");

		Predicate predicate = builder.equal(tableE.get("sesionTramitacion"), tableT.get("sesionTramitacion"));

		if (pFiltroBusqueda.getListaAreas() != null) {
			predicate = builder.and(predicate, tableT.get("idArea").in(pFiltroBusqueda.getListaAreas()));
		}

		if (pFiltroBusqueda.getFechaDesde() != null) {
			predicate = builder.and(predicate,
					builder.greaterThanOrEqualTo(tableE.get("fecha"), pFiltroBusqueda.getFechaDesde()));
		}

		if (pFiltroBusqueda.getFechaHasta() != null) {
			predicate = builder.and(predicate,
					builder.lessThanOrEqualTo(tableE.get("fecha"), pFiltroBusqueda.getFechaHasta()));
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getCodSia())) {
			predicate = builder.and(predicate,
					builder.like(tableT.get("idProcedimientoSIA"), "%" + pFiltroBusqueda.getCodSia() + "%"));
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getIdSesionTramitacion())) {
			predicate = builder.and(predicate, builder.like(tableE.get("sesionTramitacion").get("idSesionTramitacion"),
					"%" + pFiltroBusqueda.getIdSesionTramitacion() + "%"));
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getNif())) {
			predicate = builder.and(predicate,
					builder.like(tableT.get("nifIniciador"), "%" + pFiltroBusqueda.getNif() + "%"));
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getNombre())) {
			predicate = builder.and(predicate,
					builder.like(tableT.get("nombreIniciador"), "%" + pFiltroBusqueda.getNombre() + "%"));
		}

		if (pFiltroBusqueda.getEvento() != null) {
			predicate = builder.and(predicate,
					builder.equal(tableE.get("tipo"), pFiltroBusqueda.getEvento().toString()));
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getExcepcion())) {
			predicate = builder.and(predicate,
					builder.like(tableE.get("codigoError"), "%" + pFiltroBusqueda.getExcepcion() + "%"));
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getIdTramite())) {
			predicate = builder.and(predicate,
					builder.like(tableT.get("idTramite"), "%" + pFiltroBusqueda.getIdTramite() + "%"));
		}

		if (pFiltroBusqueda.getVersionTramite() != null) {
			predicate = builder.and(predicate,
					builder.equal(tableT.get("versionTramite"), pFiltroBusqueda.getVersionTramite()));
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getIdProcedimientoCP())) {
			predicate = builder.and(predicate,
					builder.like(tableT.get("idProcedimientoCP"), "%" + pFiltroBusqueda.getIdProcedimientoCP() + "%"));
		}

		query.where(predicate);

		if (pCount) {
			query.multiselect(builder.count(tableE));
		} else {
			if (StringUtils.isEmpty(pFiltroBusqueda.getSortField())) {
				query.orderBy(builder.desc(tableE.get("fecha")));
			} else {
				if ("tipoEvento".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableE.get("tipo")));
					} else {
						query.orderBy(builder.desc(tableE.get("tipo")));
					}
				} else if ("fecha".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableE.get("fecha")));
					} else {
						query.orderBy(builder.desc(tableE.get("fecha")));
					}
				} else if ("idSesionTramitacion".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(p.get("idSesionTramitacion")));
					} else {
						query.orderBy(builder.desc(p.get("idSesionTramitacion")));
					}
				} else if ("nif".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableT.get("nifIniciador")));
					} else {
						query.orderBy(builder.desc(tableT.get("nifIniciador")));
					}
				} else if ("nombre".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableT.get("nombreIniciador")));
					} else {
						query.orderBy(builder.desc(tableT.get("nombreIniciador")));
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
				} else if ("idProcedimientoCP".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableT.get("idProcedimientoCP")));
					} else {
						query.orderBy(builder.desc(tableT.get("idProcedimientoCP")));
					}
				} else if ("idProcedimientoSIA".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableT.get("idProcedimientoSIA")));
					} else {
						query.orderBy(builder.desc(tableT.get("idProcedimientoSIA")));
					}
				} else if ("codigoError".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableE.get("codigoError")));
					} else {
						query.orderBy(builder.desc(tableE.get("codigoError")));
					}
				}

			}

			query.multiselect(tableE.get("id"), p.get("idSesionTramitacion"), tableE.get("tipo"), tableE.get("fecha"),
					tableT.get("nifIniciador"), tableT.get("nombreIniciador"), tableT.get("apellido1Iniciador"),
					tableT.get("apellido2Iniciador"), tableT.get("idTramite"), tableT.get("versionTramite"),
					tableT.get("idProcedimientoCP"), tableT.get("idProcedimientoSIA"), tableE.get("codigoError"),
					tableE.get("descripcion"), tableE.get("resultado"), tableE.get("trazaError"),
					tableT.get("descripcionTramite"), tableE.get("detalle"));

		}
		return query;
	}

	private <T> CriteriaQuery<T> retrieveByAreasAndNoIdSesionCriteria(final FiltroEventoAuditoria pFiltroBusqueda,
			final Class<T> pTipo, final boolean pCount) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<T> query = builder.createQuery(pTipo);

		final Root<HEventoAuditoria> tableE = query.from(HEventoAuditoria.class);

		Predicate predicate = builder.isNull(tableE.get("sesionTramitacion"));

		if (pFiltroBusqueda.getFechaDesde() != null) {
			predicate = builder.and(predicate,
					builder.greaterThanOrEqualTo(tableE.get("fecha"), pFiltroBusqueda.getFechaDesde()));
		}

		if (pFiltroBusqueda.getFechaHasta() != null) {
			predicate = builder.and(predicate,
					builder.lessThanOrEqualTo(tableE.get("fecha"), pFiltroBusqueda.getFechaHasta()));
		}

		if (pFiltroBusqueda.getEvento() != null) {
			predicate = builder.and(predicate,
					builder.equal(tableE.get("tipo"), pFiltroBusqueda.getEvento().toString()));
		}

		query.where(predicate);

		if (pCount) {
			query.multiselect(builder.count(tableE));
		} else {
			if (StringUtils.isEmpty(pFiltroBusqueda.getSortField())) {
				query.orderBy(builder.desc(tableE.get("fecha")));
			} else {
				if ("tipoEvento".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableE.get("tipo")));
					} else {
						query.orderBy(builder.desc(tableE.get("tipo")));
					}
				} else if ("fecha".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableE.get("fecha")));
					} else {
						query.orderBy(builder.desc(tableE.get("fecha")));
					}
				} else if ("codigoError".equals(pFiltroBusqueda.getSortField())) {
					if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
						query.orderBy(builder.asc(tableE.get("codigoError")));
					} else {
						query.orderBy(builder.desc(tableE.get("codigoError")));
					}
				}
			}

			query.multiselect(tableE.get("id"), tableE.get("tipo"), tableE.get("fecha"), tableE.get("codigoError"),
					tableE.get("descripcion"), tableE.get("resultado"), tableE.get("trazaError"),
					tableE.get("detalle"));

		}

		return query;
	}

	private List<TramitePersistencia> recuperarTramitesPersistenciaCriteria(final FiltroTramitePersistencia pFiltro) {

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
						builder.lessThanOrEqualTo(tableT.get("fechaInicio"), pFiltro.getFechaHasta()));
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

	private List<FicheroPersistenciaAuditoria> recuperarPersistenciaFicherosFirmaCriteria(final Long pIdTramite) {

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

	private List<FicheroPersistenciaAuditoria> recuperarPersistenciaFicherosCriteria(final Long pIdTramite,
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

	/**
	 * Convierte lista eventos a lista string.
	 *
	 * @param listaEventos lista eventos
	 * @return lista string
	 */
	private List<String> getListaEventosToString(final List<TypeEvento> listaEventos) {
		List<String> resultado = null;
		if (listaEventos != null) {
			resultado = new ArrayList<>();
			for (final TypeEvento evento : listaEventos) {
				resultado.add(evento.toString());
			}
		}
		return resultado;
	}

	@Override
	public List<EventoCM> recuperarEventosCM(FiltroEventoAuditoria pFiltroBusqueda) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EventoCM> query = builder.createQuery(EventoCM.class);
		List<TypeEvento> eventos = new ArrayList<>();
		eventos.add(TypeEvento.FORMULARIO_INICIO);
		eventos.add(TypeEvento.FORMULARIO_FIN);
		eventos.add(TypeEvento.PAGO_ELECTRONICO_INICIO);
		eventos.add(TypeEvento.PAGO_ELECTRONICO_VERIFICADO);
		eventos.add(TypeEvento.PAGO_PRESENCIAL);
		eventos.add(TypeEvento.FIRMA_INICIO);
		eventos.add(TypeEvento.REGISTRAR_TRAMITE_INICIO);
		eventos.add(TypeEvento.REGISTRAR_TRAMITE);
		eventos.add(TypeEvento.INICIAR_TRAMITE);
		eventos.add(TypeEvento.FIN_TRAMITE);
		eventos.add(TypeEvento.ERROR);

		Root<HEventoAuditoria> tableE = query.from(HEventoAuditoria.class);
		query.multiselect(tableE.get("tipo").alias("tipoEvento"), builder.count(tableE).alias("concurrencias"));

		Predicate predicate = tableE.get("tipo").in(getListaEventosToString(eventos));
		Predicate predicateFirma = builder.equal(tableE.get("tipo"), TypeEvento.FIRMA_FIN.toString());
		predicateFirma = builder.and(predicateFirma, builder.equal(tableE.get("resultado"), "OK"));
		predicate = builder.or(predicate, predicateFirma);

		if (pFiltroBusqueda.getFechaDesde() != null) {
			predicate = builder.and(predicate,
					builder.greaterThanOrEqualTo(tableE.get("fecha"), pFiltroBusqueda.getFechaDesde()));
		}

		if (pFiltroBusqueda.getFechaHasta() != null) {
			predicate = builder.and(predicate,
					builder.lessThanOrEqualTo(tableE.get("fecha"), pFiltroBusqueda.getFechaHasta()));
		}

		query.where(predicate);
		query.groupBy(tableE.get("tipo"));

		List<EventoCM> resultList = entityManager.createQuery(query).getResultList();

		return resultList;
	}

	@Override
	public List<ErroresPorTramiteCM> recuperarErroresPorTramiteCM(final FiltroEventoAuditoria pFiltroBusqueda,
			final FiltroPaginacion filtroPaginacion) {

		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		Boolean acotarEventos = true; // si queremos buscar por todos los eventos del trámite (evntos anexar, guardar
										// formulario, etc) o solo los
		// de inicio fin y error
		final StringBuilder sql = new StringBuilder("select");
		sql.append(" e.TRP_IDETRA, ");
		sql.append(" e.TRP_VERTRA, ");
		sql.append("  sum( CASE e.log_evetip WHEN 'TR_INI' THEN e.num ELSE 0 END) evetoini, ");
		sql.append(" sum(CASE e.log_evetip WHEN 'TR_FIN' THEN e.num ELSE 0 END) evetofin, ");
		sql.append(" sum( CASE e.log_evetip WHEN 'ERROR' THEN e.num ELSE 0 END) eventoerror, ");
		sql.append(" sum( CASE e.log_evetip WHEN 'TR_INI' THEN e.num WHEN 'TR_FIN' THEN -e.num ELSE 0 END) resta, ");
		sql.append(" CASE sum( CASE e.log_evetip WHEN 'TR_INI' THEN e.num ELSE 0 END) WHEN 0 THEN 0 ELSE ");
		sql.append(
				" ((100.0 - sum(CASE e.log_evetip WHEN 'TR_FIN' THEN e.num ELSE 0 END) / sum( CASE e.log_evetip WHEN 'TR_INI' THEN e.num ELSE 0 END) * 100)) ");
		sql.append(" END ");
		sql.append(" div ");
		sql.append(" from ( ");
		sql.append(" select p.TRP_IDETRA, p.TRP_VERTRA, f.log_evetip, count(f.log_evetip) as num ");
		sql.append(" from STT_LOGINT f ");
		sql.append(" JOIN STT_TRAPER p ON f.LOG_CODSES = p.TRP_CODSTR ");
		sql.append(" and p.trp_idetra is not NULL ");
		sql.append(" where ");
		sql.append(" f.log_evetip in ('ERROR', 'TR_INI','TR_FIN') ");

		if (pFiltroBusqueda.getIdTramite() != null) {
			sql.append(" and p.TRP_IDETRA like '%").append(pFiltroBusqueda.getIdTramite()).append("%'");
		}

		if (pFiltroBusqueda.getFechaDesde() != null) {
			sql.append(" and f.LOG_EVEFEC >= TO_TIMESTAMP('")
					.append(Timestamp.from(pFiltroBusqueda.getFechaDesde().toInstant()))
					.append("', 'YYYY-MM-DD HH24:MI:SS.FF') ");
		}
		if (pFiltroBusqueda.getFechaHasta() != null) {
			sql.append(" and f.LOG_EVEFEC <= TO_TIMESTAMP('")
					.append(Timestamp.from(pFiltroBusqueda.getFechaHasta().toInstant()))
					.append("', 'YYYY-MM-DD HH24:MI:SS.FF') ");
		}

		if (pFiltroBusqueda.getListaAreas() != null) {
			StringBuilder areasString = new StringBuilder("");
			for (String area : pFiltroBusqueda.getListaAreas()) {
				if (areasString.toString().isEmpty()) {
					areasString.append("('").append(area).append("'");
				} else {
					areasString.append(", '").append(area).append("'");
				}
			}
			areasString.append(")");
			sql.append(" and p.TRP_IDEARE in ").append(areasString.toString());
		}

		sql.append(" group by p.TRP_IDETRA, p.TRP_VERTRA, f.log_evetip ");
		sql.append(" ) e ");
		sql.append(" group by e.TRP_IDETRA, e.TRP_VERTRA ");
		if (pFiltroBusqueda.isErrorPlataforma()) {
			sql.append(" having sum( CASE e.log_evetip WHEN 'ERROR' THEN e.num ELSE 0 END) > 0 ");
		} else {
			sql.append(" having (sum(CASE e.log_evetip WHEN 'TR_INI' THEN e.num ELSE 0 END)>0 ");
			sql.append(" and ");
			sql.append(" sum(CASE e.log_evetip WHEN 'TR_FIN' THEN e.num ELSE 0 END) = 0 ");
			sql.append(" and ");
			sql.append(" sum(CASE e.log_evetip WHEN 'ERROR' THEN e.num ELSE 0 END) = 0) ");
		}

		if (StringUtils.isEmpty(pFiltroBusqueda.getSortField())) {
			sql.append(" order by e.TRP_IDETRA DESC ");
		} else {
			if ("idTramite".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					sql.append(" order by e.TRP_IDETRA ASC ");
				} else {
					sql.append(" order by e.TRP_IDETRA DESC ");
				}
			} else if ("version".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					sql.append(" order by e.TRP_VERTRA ASC ");
				} else {
					sql.append(" order by e.TRP_VERTRA DESC ");
				}
			} else if ("sesionesInacabadas".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					sql.append(" order by resta ASC ");

				} else {
					sql.append(" order by resta DESC ");

				}
			} else if ("sesionesFinalizadas".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					sql.append(" order by evetofin ASC ");
				} else {
					sql.append(" order by evetofin DESC ");
				}
			} else if ("numeroErrores".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					sql.append(" order by eventoerror ASC ");

				} else {
					sql.append(" order by eventoerror DESC ");

				}
			} else if ("porcentage".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					sql.append(" order by div ASC ");

				} else {
					sql.append(" order by div DESC ");

				}
			}
		}
		Query query = entityManager.createNativeQuery(sql.toString());
		List<Object[]> lista;
		if (filtroPaginacion == null) {
			lista = query.getResultList();
		} else {
			lista = query.setFirstResult(filtroPaginacion.getFirst()).setMaxResults(filtroPaginacion.getPageSize())
					.getResultList();
		}

		List<ErroresPorTramiteCM> listCast = new ArrayList<ErroresPorTramiteCM>();
		for (Object[] obj : lista) {
			ErroresPorTramiteCM temp = new ErroresPorTramiteCM();
			temp.setIdTramite((String) obj[0]);
			temp.setVersion(((BigDecimal) obj[1]).intValue());
			temp.setSesionesIniciadas(((BigDecimal) obj[2]).longValue());
			temp.setSesionesFinalizadas(((BigDecimal) obj[3]).longValue());
			temp.setNumeroErrores(((BigDecimal) obj[4]).longValue());
			temp.setSesionesInacabadas(((BigDecimal) obj[5]).longValue());
			temp.setPorcentage(((BigDecimal) obj[6]).doubleValue());
			listCast.add(temp);
		}
		// final CriteriaQuery<ErroresPorTramiteCM> query =
		// entityManager.createNativeQuery(sqlString, resultClass);

		/*
		 * final CriteriaBuilder builder = entityManager.getCriteriaBuilder(); final
		 * CriteriaQuery<ErroresPorTramiteCM> query =
		 * builder.createQuery(ErroresPorTramiteCM.class);
		 *
		 *
		 * Root<HEventoAuditoria> tableE = query.from(HEventoAuditoria.class);
		 * Root<HTramite> tableT = query.from(HTramite.class);
		 *
		 * Subquery<Long> subSesIni = query.subquery(Long.class); Root<HEventoAuditoria>
		 * subSesIniRootE = subSesIni.from(HEventoAuditoria.class); Root<HTramite>
		 * subSesIniRootT = subSesIni.from(HTramite.class); final Join<HEventoAuditoria,
		 * HSesionTramitacion> subSesIniJoin = subSesIniRootE.join("sesionTramitacion",
		 * JoinType.LEFT); subSesIni.select(builder.count(subSesIniRootE.get("id")));
		 * Predicate subSesIniPred =
		 * builder.equal(subSesIniRootE.get("sesionTramitacion"),
		 * subSesIniRootT.get("sesionTramitacion")); subSesIniPred =
		 * builder.and(subSesIniPred, builder.equal(subSesIniRootE.get("tipo"),
		 * "TR_INI")); subSesIniPred = builder.and(subSesIniPred,
		 * builder.equal(subSesIniRootT.get("idTramite"), tableT.get("idTramite")));
		 * subSesIniPred = builder.and(subSesIniPred,
		 * builder.equal(subSesIniRootT.get("versionTramite"),
		 * tableT.get("versionTramite"))); if (pFiltroBusqueda.getFechaDesde() != null)
		 * { subSesIniPred = builder.and(subSesIniPred,
		 * builder.greaterThanOrEqualTo(subSesIniRootE.get("fecha"),
		 * pFiltroBusqueda.getFechaDesde())); } if (pFiltroBusqueda.getFechaHasta() !=
		 * null) { subSesIniPred = builder.and(subSesIniPred,
		 * builder.lessThanOrEqualTo(subSesIniRootE.get("fecha"),
		 * pFiltroBusqueda.getFechaHasta())); } subSesIni.where(subSesIniPred);
		 * subSesIni.groupBy(subSesIniRootT.get("idTramite"),
		 * subSesIniRootT.get("versionTramite"));
		 *
		 * Subquery<Long> subSesKo = query.subquery(Long.class); Root<HEventoAuditoria>
		 * subSesKoRootE = subSesKo.from(HEventoAuditoria.class); Root<HTramite>
		 * subSesKoRootT = subSesKo.from(HTramite.class); final Join<HEventoAuditoria,
		 * HSesionTramitacion> subSesKoJoin = subSesKoRootE.join("sesionTramitacion",
		 * JoinType.LEFT);
		 * subSesKo.select(builder.count(subSesKoRootE.get("sesionTramitacion"))).
		 * distinct(true); Predicate subSesKoPred =
		 * builder.equal(subSesKoRootE.get("sesionTramitacion"),
		 * subSesKoRootT.get("sesionTramitacion")); subSesKoPred =
		 * builder.and(subSesKoPred, builder.equal(subSesKoRootE.get("tipo"),
		 * "TR_INI")); subSesKoPred = builder.and(subSesKoPred,
		 * builder.equal(subSesKoRootT.get("idTramite"), tableT.get("idTramite")));
		 * subSesKoPred = builder.and(subSesKoPred,
		 * builder.equal(subSesKoRootT.get("versionTramite"),
		 * tableT.get("versionTramite")));
		 *
		 * Subquery<String> subSesKo2 = query.subquery(String.class);
		 * Root<HEventoAuditoria> subSesKo2RootE =
		 * subSesKo2.from(HEventoAuditoria.class);
		 * subSesKo2.select(subSesKo2RootE.get("sesionTramitacion")); Predicate
		 * subSesKo2Pred = builder.equal(subSesKo2RootE.get("tipo"), "ERROR"); if
		 * (pFiltroBusqueda.getFechaDesde() != null) { subSesKo2Pred =
		 * builder.and(subSesKo2Pred,
		 * builder.greaterThanOrEqualTo(subSesKo2RootE.get("fecha"),
		 * pFiltroBusqueda.getFechaDesde())); } if (pFiltroBusqueda.getFechaHasta() !=
		 * null) { subSesKo2Pred = builder.and(subSesKo2Pred,
		 * builder.lessThanOrEqualTo(subSesKo2RootE.get("fecha"),
		 * pFiltroBusqueda.getFechaHasta())); } subSesKo2.where(subSesKo2Pred);
		 *
		 * Subquery<String> subSesKo3 = query.subquery(String.class);
		 * Root<HEventoAuditoria> subSesKo3RootE =
		 * subSesKo3.from(HEventoAuditoria.class);
		 * subSesKo3.select(subSesKo3RootE.get("sesionTramitacion")); Predicate
		 * subSesKo3Pred = builder.equal(subSesKo3RootE.get("tipo"), "TR_FIN"); if
		 * (pFiltroBusqueda.getFechaDesde() != null) { subSesKo3Pred =
		 * builder.and(subSesKo3Pred,
		 * builder.greaterThanOrEqualTo(subSesKo3RootE.get("fecha"),
		 * pFiltroBusqueda.getFechaDesde())); } if (pFiltroBusqueda.getFechaHasta() !=
		 * null) { subSesKo3Pred = builder.and(subSesKo3Pred,
		 * builder.lessThanOrEqualTo(subSesKo3RootE.get("fecha"),
		 * pFiltroBusqueda.getFechaHasta())); } subSesKo3.where(subSesKo3Pred);
		 *
		 * subSesKoPred = builder.and(subSesKoPred,
		 * builder.in(subSesKoRootE.get("sesionTramitacion")).value(subSesKo2));
		 *
		 * subSesKoPred = builder.and(subSesKoPred,
		 * builder.not(builder.in(subSesKoRootE.get("sesionTramitacion")).value(
		 * subSesKo3)));
		 *
		 * subSesKo.where(subSesKoPred);
		 * subSesKo.groupBy(subSesKoRootT.get("idTramite"),
		 * subSesKoRootT.get("versionTramite"));
		 *
		 * Subquery<Long> subSesFin = query.subquery(Long.class); Root<HEventoAuditoria>
		 * subSesFinRootE = subSesFin.from(HEventoAuditoria.class); Root<HTramite>
		 * subSesFinRootT = subSesFin.from(HTramite.class); final Join<HEventoAuditoria,
		 * HSesionTramitacion> subSesFinEJoin = subSesFinRootE.join("sesionTramitacion",
		 * JoinType.LEFT); subSesFin.select(builder.count(subSesFinRootE.get("id")));
		 * Predicate subSesFinPred =
		 * builder.equal(subSesFinRootE.get("sesionTramitacion"),
		 * subSesFinRootT.get("sesionTramitacion")); subSesFinPred =
		 * builder.and(subSesFinPred, builder.equal(subSesFinRootE.get("tipo"),
		 * "TR_FIN")); subSesFinPred = builder.and(subSesFinPred,
		 * builder.equal(subSesFinRootT.get("idTramite"), tableT.get("idTramite")));
		 * subSesFinPred = builder.and(subSesFinPred,
		 * builder.equal(subSesFinRootT.get("versionTramite"),
		 * tableT.get("versionTramite"))); if (pFiltroBusqueda.getFechaDesde() != null)
		 * { subSesFinPred = builder.and(subSesFinPred,
		 * builder.greaterThanOrEqualTo(subSesFinRootE.get("fecha"),
		 * pFiltroBusqueda.getFechaDesde())); } if (pFiltroBusqueda.getFechaHasta() !=
		 * null) { subSesFinPred = builder.and(subSesFinPred,
		 * builder.lessThanOrEqualTo(subSesFinRootE.get("fecha"),
		 * pFiltroBusqueda.getFechaHasta())); } subSesFin.where(subSesFinPred);
		 * subSesFin.groupBy(subSesFinRootT.get("idTramite"),
		 * subSesFinRootT.get("versionTramite"));
		 *
		 * Subquery<Long> subSesResta = query.subquery(Long.class);
		 * subSesResta.select(builder.diff(subSesIni, subSesKo));
		 *
		 * final Join<HEventoAuditoria, HSesionTramitacion> p =
		 * tableE.join("sesionTramitacion", JoinType.LEFT); Predicate predicate =
		 * builder.equal(tableE.get("sesionTramitacion"),
		 * tableT.get("sesionTramitacion")); predicate = builder.and(predicate,
		 * builder.equal(tableE.get("tipo"), "ERROR")); if
		 * (pFiltroBusqueda.getFechaDesde() != null) { predicate =
		 * builder.and(predicate, builder.greaterThanOrEqualTo(tableE.get("fecha"),
		 * pFiltroBusqueda.getFechaDesde())); } if (pFiltroBusqueda.getFechaHasta() !=
		 * null) { predicate = builder.and(predicate,
		 * builder.lessThanOrEqualTo(tableE.get("fecha"),
		 * pFiltroBusqueda.getFechaHasta())); } if (pFiltroBusqueda.getListaAreas() !=
		 * null) { predicate = builder.and(predicate,
		 * tableT.get("idArea").in(pFiltroBusqueda.getListaAreas())); }
		 * query.where(predicate);
		 *
		 * Expression<Double> diff =
		 * builder.quot(builder.coalesce(subSesKo.getSelection(), 0),
		 * subSesIni.getSelection()) .as(Double.class); Expression<Double> prod =
		 * builder.prod(100.0, diff);
		 *
		 * if (StringUtils.isEmpty(pFiltroBusqueda.getSortField())) {
		 * query.orderBy(builder.asc(tableT.get("idTramite"))); } else { if
		 * ("idTramite".equals(pFiltroBusqueda.getSortField())) { if
		 * (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
		 * query.orderBy(builder.asc(tableT.get("idTramite"))); } else {
		 * query.orderBy(builder.desc(tableT.get("idTramite"))); } } else if
		 * ("version".equals(pFiltroBusqueda.getSortField())) { if
		 * (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
		 * query.orderBy(builder.asc(tableT.get("versionTramite"))); } else {
		 * query.orderBy(builder.desc(tableT.get("versionTramite"))); } } else if
		 * ("sesionesInacabadas".equals(pFiltroBusqueda.getSortField())) { if
		 * (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
		 * query.orderBy(builder.asc(builder.diff(subSesIni.getSelection(),
		 * builder.sum(builder.coalesce(subSesFin.getSelection(), 0),
		 * builder.coalesce(subSesKo.getSelection(), 0))))); } else {
		 * query.orderBy(builder.desc(builder.diff(subSesIni.getSelection(),
		 * builder.sum(builder.coalesce(subSesFin.getSelection(), 0),
		 * builder.coalesce(subSesKo.getSelection(), 0))))); } } else if
		 * ("sesionesKo".equals(pFiltroBusqueda.getSortField())) { if
		 * (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
		 * query.orderBy(builder.asc(builder.coalesce(subSesKo.getSelection(), 0))); }
		 * else { query.orderBy(builder.desc(builder.coalesce(subSesKo.getSelection(),
		 * 0))); } } else if
		 * ("sesionesFinalizadas".equals(pFiltroBusqueda.getSortField())) { if
		 * (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
		 * query.orderBy(builder.asc(builder.coalesce(subSesFin.getSelection(), 0))); }
		 * else { query.orderBy(builder.desc(builder.coalesce(subSesFin.getSelection(),
		 * 0))); } } else if ("numeroErrores".equals(pFiltroBusqueda.getSortField())) {
		 * if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
		 * query.orderBy(builder.asc(builder.count(tableE.get("id")))); } else {
		 * query.orderBy(builder.desc(builder.count(tableE.get("id")))); } } else if
		 * ("porcentage".equals(pFiltroBusqueda.getSortField())) { if
		 * (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
		 * query.orderBy(builder.asc(prod)); } else { query.orderBy(builder.desc(prod));
		 * } }
		 *
		 * }
		 *
		 * query.multiselect(tableT.get("idTramite").alias("idTramite"),
		 * tableT.get("versionTramite").alias("version"),
		 * subSesIni.getSelection().alias("sesionesIniciadas"),
		 * builder.coalesce(subSesKo.getSelection(), 0).alias("sesionesKo"),
		 * builder.coalesce(subSesFin.getSelection(), 0).alias("sesionesFinalizadas"),
		 * builder.count(tableE.get("id")).alias("numeroErrores"),
		 * builder.diff(subSesIni.getSelection(),
		 * builder.sum(builder.coalesce(subSesFin.getSelection(), 0),
		 * builder.coalesce(subSesKo.getSelection(), 0))) .alias("sesionesInacabadas"),
		 * prod.alias("porcentage")); query.groupBy(tableT.get("idTramite"),
		 * tableT.get("versionTramite"));
		 *
		 * List<ErroresPorTramiteCM> resultList = null;
		 *
		 * if (filtroPaginacion == null) { resultList =
		 * entityManager.createQuery(query).getResultList(); } else { resultList =
		 * entityManager.createQuery(query).setFirstResult(filtroPaginacion.getFirst())
		 * .setMaxResults(filtroPaginacion.getPageSize()).getResultList(); }
		 */

		return listCast;
	}

	@Override
	public Long contarErroresPorTramiteCM(FiltroEventoAuditoria pFiltroBusqueda) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		Boolean acotarEventos = true; // si queremos buscar por todos los eventos del trámite (evntos anexar, guardar
										// formulario, etc) o solo los
		// de inicio fin y error
		final StringBuilder sql = new StringBuilder("select");

		sql.append(" e.TRP_IDETRA, ");
		sql.append(" e.TRP_VERTRA ");
		sql.append(" from ( ");
		sql.append(" select p.TRP_IDETRA, p.TRP_VERTRA, f.log_evetip, count(f.log_evetip) as num ");
		sql.append(" from STT_LOGINT f ");
		sql.append(" JOIN STT_TRAPER p ON f.LOG_CODSES = p.TRP_CODSTR ");
		sql.append(" and p.trp_idetra is not NULL ");
		sql.append(" where ");
		sql.append(" f.log_evetip in ('ERROR', 'TR_INI','TR_FIN') ");
		if (pFiltroBusqueda.getIdTramite() != null) {
			sql.append(" and p.TRP_IDETRA like '%").append(pFiltroBusqueda.getIdTramite()).append("%'");
		}

		if (pFiltroBusqueda.getFechaDesde() != null) {
			sql.append(" and f.LOG_EVEFEC >= TO_TIMESTAMP('")
					.append(Timestamp.from(pFiltroBusqueda.getFechaDesde().toInstant()))
					.append("', 'YYYY-MM-DD HH24:MI:SS.FF') ");
		}
		if (pFiltroBusqueda.getFechaHasta() != null) {
			sql.append(" and f.LOG_EVEFEC <= TO_TIMESTAMP('")
					.append(Timestamp.from(pFiltroBusqueda.getFechaHasta().toInstant()))
					.append("', 'YYYY-MM-DD HH24:MI:SS.FF') ");
		}

		if (pFiltroBusqueda.getListaAreas() != null) {
			StringBuilder areasString = new StringBuilder("");
			for (String area : pFiltroBusqueda.getListaAreas()) {
				if (areasString.toString().isEmpty()) {
					areasString.append("('").append(area).append("'");
				} else {
					areasString.append(", '").append(area).append("'");
				}
			}
			areasString.append(")");
			sql.append(" and p.TRP_IDEARE in ").append(areasString.toString());
		}

		sql.append(" group by p.TRP_IDETRA, p.TRP_VERTRA, f.log_evetip ");
		sql.append(" ) e ");
		sql.append(" group by e.TRP_IDETRA, e.TRP_VERTRA ");
		if (pFiltroBusqueda.isErrorPlataforma()) {
			sql.append(" having sum( CASE e.log_evetip WHEN 'ERROR' THEN e.num ELSE 0 END) > 0 ");
		} else {
			sql.append(" having (sum(CASE e.log_evetip WHEN 'TR_INI' THEN e.num ELSE 0 END)>0 ");
			sql.append(" and ");
			sql.append(" sum(CASE e.log_evetip WHEN 'TR_FIN' THEN e.num ELSE 0 END) = 0 ");
			sql.append(" and ");
			sql.append(" sum(CASE e.log_evetip WHEN 'ERROR' THEN e.num ELSE 0 END) = 0) ");
		}
		Query query = entityManager.createNativeQuery(sql.toString());
		List<Object[]> lista = query.getResultList();

		return Long.valueOf(lista.size());
	}

	@Override
	public List<EventoCM> recuperarErroresPorTramiteCMExpansion(final FiltroEventoAuditoria pFiltroBusqueda,
			final FiltroPaginacion filtroPaginacion) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EventoCM> query = builder.createQuery(EventoCM.class);

		Root<HEventoAuditoria> tableE = query.from(HEventoAuditoria.class);
		Predicate predicate;
		Root<HTramite> tableT = query.from(HTramite.class);

		final Join<HEventoAuditoria, HSesionTramitacion> p = tableE.join("sesionTramitacion");

		predicate = builder.equal(tableE.get("sesionTramitacion"), tableT.get("sesionTramitacion"));
		predicate = builder.and(predicate, builder.equal(tableE.get("tipo"), "ERROR"));
		predicate = builder.and(predicate, builder.like(tableT.get("idTramite"), pFiltroBusqueda.getIdTramite()));
		predicate = builder.and(predicate,
				builder.equal(tableT.get("versionTramite"), pFiltroBusqueda.getVersionTramite()));

		if (pFiltroBusqueda.getFechaDesde() != null) {
			predicate = builder.and(predicate,
					builder.greaterThanOrEqualTo(tableE.get("fecha"), pFiltroBusqueda.getFechaDesde()));
		}

		if (pFiltroBusqueda.getFechaHasta() != null) {
			predicate = builder.and(predicate,
					builder.lessThanOrEqualTo(tableE.get("fecha"), pFiltroBusqueda.getFechaHasta()));
		}

		query.where(predicate);

		query.groupBy(tableE.get("codigoError"));

		Subquery<Long> subPorc = query.subquery(Long.class);
		Root<HEventoAuditoria> subPorcE = subPorc.from(HEventoAuditoria.class);
		Predicate subPorcPred;
		Root<HTramite> subPorcT = subPorc.from(HTramite.class);

		final Join<HEventoAuditoria, HSesionTramitacion> subPorcJoin = subPorcE.join("sesionTramitacion");
		subPorcPred = builder.equal(subPorcE.get("sesionTramitacion"), subPorcT.get("sesionTramitacion"));
		subPorcPred = builder.and(subPorcPred, builder.equal(subPorcE.get("tipo"), "ERROR"));
		subPorcPred = builder.and(subPorcPred, builder.like(subPorcT.get("idTramite"), pFiltroBusqueda.getIdTramite()));
		subPorcPred = builder.and(subPorcPred,
				builder.equal(subPorcT.get("versionTramite"), pFiltroBusqueda.getVersionTramite()));

		if (pFiltroBusqueda.getFechaDesde() != null) {
			subPorcPred = builder.and(subPorcPred,
					builder.greaterThanOrEqualTo(subPorcE.get("fecha"), pFiltroBusqueda.getFechaDesde()));
		}

		if (pFiltroBusqueda.getFechaHasta() != null) {
			subPorcPred = builder.and(subPorcPred,
					builder.lessThanOrEqualTo(subPorcE.get("fecha"), pFiltroBusqueda.getFechaHasta()));
		}

		subPorc.where(subPorcPred);
		subPorc.select(builder.count(subPorcE.get("id")));

		Expression<Double> diff = builder.quot(builder.count(tableE.get("id")), subPorc.getSelection())
				.as(Double.class);
		Expression<Double> prod = builder.prod(100.0, diff);

		query.multiselect(tableE.get("codigoError"), builder.count(tableE.get("id")), prod);

		if (StringUtils.isEmpty(pFiltroBusqueda.getSortField())) {
			query.orderBy(builder.asc(tableE.get("codigoError")));
		} else {
			if ("tipoEvento".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					query.orderBy(builder.asc(tableE.get("codigoError")));
				} else {
					query.orderBy(builder.desc(tableE.get("codigoError")));
				}
			} else if ("concurrencias".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					query.orderBy(builder.asc(builder.count(tableE.get("id"))));
				} else {
					query.orderBy(builder.desc(builder.count(tableE.get("id"))));
				}
			} else if ("porcentage".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					query.orderBy(builder.asc(prod));
				} else {
					query.orderBy(builder.desc(prod));
				}
			}
		}
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public Long contarErroresPorTramiteExpansionCM(FiltroEventoAuditoria pFiltroBusqueda) {

		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EventoCM> query = builder.createQuery(EventoCM.class);

		final Root<HEventoAuditoria> tableE = query.from(HEventoAuditoria.class);

		Predicate predicate = builder.equal(tableE.get("tipo"), "ERROR");

		final Root<HTramite> tableT = query.from(HTramite.class);

		final Join<HEventoAuditoria, HSesionTramitacion> p = tableE.join("sesionTramitacion");
		predicate = builder.and(predicate,
				builder.equal(tableE.get("sesionTramitacion"), tableT.get("sesionTramitacion")));

		if (pFiltroBusqueda.getListaAreas() != null) {
			predicate = builder.and(predicate, tableT.get("idArea").in(pFiltroBusqueda.getListaAreas()));
		}

		predicate = builder.and(predicate, builder.like(tableT.get("idTramite"), pFiltroBusqueda.getIdTramite()));

		predicate = builder.and(predicate,
				builder.equal(tableT.get("versionTramite"), pFiltroBusqueda.getVersionTramite()));

		if (pFiltroBusqueda.getFechaDesde() != null) {
			predicate = builder.and(predicate,
					builder.greaterThanOrEqualTo(tableE.get("fecha"), pFiltroBusqueda.getFechaDesde()));
		}

		if (pFiltroBusqueda.getFechaHasta() != null) {
			predicate = builder.and(predicate,
					builder.lessThanOrEqualTo(tableE.get("fecha"), pFiltroBusqueda.getFechaHasta()));
		}

		query.where(predicate);

		query.groupBy(tableE.get("codigoError"));

		query.multiselect(tableE.get("codigoError"), builder.count(tableE.get("id")));

		return Long.valueOf(entityManager.createQuery(query).getResultList().size());
	}

	@Override
	public List<EventoCM> recuperarErroresPlataformaCM(final FiltroEventoAuditoria pFiltroBusqueda,
			final FiltroPaginacion filtroPaginacion) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EventoCM> query = builder.createQuery(EventoCM.class);

		Root<HEventoAuditoria> tableE = query.from(HEventoAuditoria.class);
		Predicate predicate;

		predicate = builder.equal(tableE.get("tipo"), "ERROR");
		predicate = builder.and(predicate, builder.isNull(tableE.get("sesionTramitacion")));

		if (pFiltroBusqueda.getFechaDesde() != null) {
			predicate = builder.and(predicate,
					builder.greaterThanOrEqualTo(tableE.get("fecha"), pFiltroBusqueda.getFechaDesde()));
		}

		if (pFiltroBusqueda.getFechaHasta() != null) {
			predicate = builder.and(predicate,
					builder.lessThanOrEqualTo(tableE.get("fecha"), pFiltroBusqueda.getFechaHasta()));
		}

		query.where(predicate);

		query.groupBy(tableE.get("codigoError"));

		Subquery<Long> subPorc = query.subquery(Long.class);
		Root<HEventoAuditoria> subPorcE = subPorc.from(HEventoAuditoria.class);
		Predicate subPorcPred;

		subPorcPred = builder.equal(subPorcE.get("tipo"), "ERROR");
		subPorcPred = builder.and(subPorcPred, builder.isNull(subPorcE.get("sesionTramitacion")));

		if (pFiltroBusqueda.getFechaDesde() != null) {
			subPorcPred = builder.and(subPorcPred,
					builder.greaterThanOrEqualTo(subPorcE.get("fecha"), pFiltroBusqueda.getFechaDesde()));
		}

		if (pFiltroBusqueda.getFechaHasta() != null) {
			subPorcPred = builder.and(subPorcPred,
					builder.lessThanOrEqualTo(subPorcE.get("fecha"), pFiltroBusqueda.getFechaHasta()));
		}

		subPorc.where(subPorcPred);
		subPorc.select(builder.count(subPorcE.get("id")));

		Expression<Double> diff = builder.quot(builder.count(tableE.get("id")), subPorc.getSelection())
				.as(Double.class);
		Expression<Double> prod = builder.prod(100.0, diff);

		query.multiselect(tableE.get("codigoError"), builder.count(tableE.get("id")), prod);

		if (StringUtils.isEmpty(pFiltroBusqueda.getSortField())) {
			query.orderBy(builder.asc(tableE.get("codigoError")));
		} else {
			if ("tipoEvento".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					query.orderBy(builder.asc(tableE.get("codigoError")));
				} else {
					query.orderBy(builder.desc(tableE.get("codigoError")));
				}
			} else if ("concurrencias".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					query.orderBy(builder.asc(builder.count(tableE.get("id"))));
				} else {
					query.orderBy(builder.desc(builder.count(tableE.get("id"))));
				}
			} else if ("porcentage".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					query.orderBy(builder.asc(prod));
				} else {
					query.orderBy(builder.desc(prod));
				}
			}
		}
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public Long contarErroresPlataformaCM(FiltroEventoAuditoria pFiltroBusqueda) {

		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EventoCM> query = builder.createQuery(EventoCM.class);

		final Root<HEventoAuditoria> tableE = query.from(HEventoAuditoria.class);

		Predicate predicate = builder.equal(tableE.get("tipo"), "ERROR");

		predicate = builder.and(predicate, builder.isNull(tableE.get("sesionTramitacion")));

		if (pFiltroBusqueda.getFechaDesde() != null) {
			predicate = builder.and(predicate,
					builder.greaterThanOrEqualTo(tableE.get("fecha"), pFiltroBusqueda.getFechaDesde()));
		}

		if (pFiltroBusqueda.getFechaHasta() != null) {
			predicate = builder.and(predicate,
					builder.lessThanOrEqualTo(tableE.get("fecha"), pFiltroBusqueda.getFechaHasta()));
		}

		query.where(predicate);

		query.groupBy(tableE.get("codigoError"));

		query.multiselect(tableE.get("codigoError"), builder.count(tableE.get("id")));

		return Long.valueOf(entityManager.createQuery(query).getResultList().size());
	}

	@Override
	public List<FormularioSoporte> recuperarFormularioSoporte(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion) {
		String sql = "select e.SOP_CODIGO, e.SOP_FCCREA, s.SES_IDESTR, e.SOP_NIF, e.SOP_NOMBRE, e.SOP_TELEFO, e.SOP_EMAIL, e.SOP_HORARI, e.SOP_PRODES, e.SOP_PROTIP, e.SOP_PROTID, t.TRP_IDETRA, t.TRP_VERTRA, e.SOP_FICNOM, e.SOP_FICDAT, e.SOP_ESTADO, e.SOP_COMENT from STT_SOPORT e left join STT_SESION s on e.SOP_CODSTR = s.SES_CODIGO left join STT_TRAPER t on e.SOP_CODSTR = t.TRP_CODSTR where 1 = 1 ";

		if (pFiltroBusqueda.getFechaDesde() != null) {
			sql += " and e.SOP_FCCREA >= TO_TIMESTAMP('" + Timestamp.from(pFiltroBusqueda.getFechaDesde().toInstant())
					+ "', 'YYYY-MM-DD HH24:MI:SS.FF')";
		}

		if (pFiltroBusqueda.getFechaHasta() != null) {
			sql += " and e.SOP_FCCREA <= TO_TIMESTAMP('" + Timestamp.from(pFiltroBusqueda.getFechaHasta().toInstant())
					+ "', 'YYYY-MM-DD HH24:MI:SS.FF')";
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getIdSesionTramitacion())) {
			sql += " and s.SES_IDESTR like '%" + pFiltroBusqueda.getIdSesionTramitacion() + "%'";
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getNif())) {
			sql += " and e.SOP_NIF like '%" + pFiltroBusqueda.getNif() + "%'";
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getNombre())) {
			sql += " and e.SOP_NOMBRE like '%" + pFiltroBusqueda.getNombre() + "%'";
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getTlf())) {
			sql += " and e.SOP_TELEFO like '%" + pFiltroBusqueda.getTlf() + "%'";
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getEmail())) {
			sql += " and e.SOP_EMAIL like '%" + pFiltroBusqueda.getEmail() + "%'";
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getIdTramite())) {
			sql += " and t.TRP_IDETRA like '%" + pFiltroBusqueda.getIdTramite() + "%'";
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getEmail())) {
			sql += " and e.SOP_EMAIL like '%" + pFiltroBusqueda.getEmail() + "%'";
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getTlf())) {
			sql += " and e.SOP_TELEFO like '%" + pFiltroBusqueda.getTlf() + "%'";
		}

		if (pFiltroBusqueda.getVersionTramite() != null) {
			sql += " and t.TRP_VERTRA = " + pFiltroBusqueda.getVersionTramite();
		}

		if (pFiltroBusqueda.getEstado() != null) {
			sql += " and e.SOP_ESTADO = '" + pFiltroBusqueda.getEstado() + "'";
		}

		if (pFiltroBusqueda.getListaAreas() != null) {
			StringBuilder areasString = new StringBuilder("");
			for (String area : pFiltroBusqueda.getListaAreas()) {
				if (areasString.toString().isEmpty()) {
					areasString.append("('").append(area).append("'");
				} else {
					areasString.append(", '").append(area).append("'");
				}
			}
			areasString.append(")");
			sql += (" and t.TRP_IDEARE in " + areasString.toString());
		}

		if (StringUtils.isEmpty(pFiltroBusqueda.getSortField())) {
			sql += " order by e.SOP_CODIGO DESC";
		} else {
			if ("idTramite".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					sql += " order by t.TRP_IDETRA ASC";
				} else {
					sql += " order by t.TRP_IDETRA DESC";
				}
			} else if ("version".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					sql += " order by t.TRP_VERTRA ASC";
				} else {
					sql += " order by t.TRP_VERTRA DESC";
				}
			} else if ("fechaCreacion".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					sql += " order by e.SOP_FCCREA ASC";
				} else {
					sql += " order by e.SOP_FCCREA DESC";
				}
			} else if ("sesion".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					sql += " order by s.SES_IDESTR ASC";
				} else {
					sql += " order by s.SES_IDESTR DESC";
				}
			} else if ("nif".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					sql += " order by e.SOP_NIF ASC";
				} else {
					sql += " order by e.SOP_NIF DESC";
				}
			} else if ("nombre".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					sql += " order by e.SOP_NOMBRE ASC";
				} else {
					sql += " order by e.SOP_NOMBRE DESC";
				}
			} else if ("telefono".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					sql += " order by e.SOP_TELEFO ASC";

				} else {
					sql += " order by e.SOP_TELEFO DESC";
				}
			} else if ("email".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					sql += " order by e.SOP_EMAIL ASC";
				} else {
					sql += " order by e.SOP_EMAIL DESC";
				}
			} else if ("horario".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					sql += " order by e.SOP_HORARI ASC";
				} else {
					sql += " order by e.SOP_HORARI DESC";
				}
			} else if ("tipoProblema".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					sql += " order by e.SOP_PROTIP ASC";
				} else {
					sql += " order by e.SOP_PROTIP DESC";
				}
			} else if ("descripcionProblema".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					sql += " order by e.SOP_PRODES ASC";
				} else {
					sql += " order by e.SOP_PRODES DESC";
				}
			} else if ("estado".equals(pFiltroBusqueda.getSortField())) {
				if (ASCENDING.equals(pFiltroBusqueda.getSortOrder())) {
					sql += " order by e.SOP_ESTADO ASC";
				} else {
					sql += " order by e.SOP_ESTADO DESC";
				}
			}
		}
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> lista;
		if (filtroPaginacion == null) {
			lista = query.getResultList();
		} else {
			lista = query.setFirstResult(filtroPaginacion.getFirst()).setMaxResults(filtroPaginacion.getPageSize())
					.getResultList();
		}

		List<FormularioSoporte> listCast = new ArrayList<FormularioSoporte>();
		for (Object[] obj : lista) {
			FormularioSoporte temp = new FormularioSoporte();
			temp.setCodigo(((BigDecimal) obj[0]).longValue());
			temp.setFechaCreacion((Date) obj[1]);
			temp.setSesionTramitacion((String) obj[2]);
			temp.setNif((String) obj[3]);
			temp.setNombre((String) obj[4]);
			temp.setTelefono((String) obj[5]);
			temp.setEmail((String) obj[6]);
			temp.setHorario((String) obj[7]);
			temp.setDescripcionProblema((String) obj[8]);
			temp.setTipoProblema((String) obj[9]);
			temp.setTipoProblemaDesc((String) obj[10]);
			temp.setIdTramite((String) obj[11]);
			if (obj[12] != null) {
				temp.setVersion(((BigDecimal) obj[12]).intValue());
			}
			temp.setNombreFichero((String) obj[13]);
			if (obj[14] != null) {
				try {
					temp.setDatosFichero(((Blob) obj[14]).getBytes(1, ((int) ((Blob) obj[14]).length())));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			temp.setEstado((String) obj[15]);
			temp.setComentario((String) obj[16]);
			listCast.add(temp);
		}
		return listCast;
	}

	@Override
	public Long contarFormularioSoporte(FiltroEventoAuditoria pFiltroBusqueda) {
		String sql = "select e.SOP_CODIGO, e.SOP_FCCREA, s.SES_IDESTR, e.SOP_NIF, e.SOP_NOMBRE, e.SOP_TELEFO, e.SOP_EMAIL, e.SOP_HORARI, e.SOP_PRODES, e.SOP_PROTIP, e.SOP_PROTID, t.TRP_IDETRA, t.TRP_VERTRA, e.SOP_FICNOM, e.SOP_FICDAT, e.SOP_ESTADO, e.SOP_COMENT from STT_SOPORT e left join STT_SESION s on e.SOP_CODSTR = s.SES_CODIGO left join STT_TRAPER t on e.SOP_CODSTR = t.TRP_CODSTR where 1=1 ";

		if (pFiltroBusqueda.getFechaDesde() != null) {
			sql += " and e.SOP_FCCREA >= TO_TIMESTAMP('" + Timestamp.from(pFiltroBusqueda.getFechaDesde().toInstant())
					+ "', 'YYYY-MM-DD HH24:MI:SS.FF')";
		}

		if (pFiltroBusqueda.getFechaHasta() != null) {
			sql += " and e.SOP_FCCREA <= TO_TIMESTAMP('" + Timestamp.from(pFiltroBusqueda.getFechaHasta().toInstant())
					+ "', 'YYYY-MM-DD HH24:MI:SS.FF')";
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getIdSesionTramitacion())) {
			sql += " and s.SES_IDESTR like '%" + pFiltroBusqueda.getIdSesionTramitacion() + "%'";
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getNif())) {
			sql += " and e.SOP_NIF like '%" + pFiltroBusqueda.getNif() + "%'";
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getNombre())) {
			sql += " and e.SOP_NOMBRE like '%" + pFiltroBusqueda.getNombre() + "%'";
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getIdTramite())) {
			sql += " and t.TRP_IDETRA like '%" + pFiltroBusqueda.getIdTramite() + "%'";
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getEmail())) {
			sql += " and e.SOP_EMAIL like '%" + pFiltroBusqueda.getEmail() + "%'";
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getTlf())) {
			sql += " and e.SOP_TELEFO like '%" + pFiltroBusqueda.getTlf() + "%'";
		}

		if (pFiltroBusqueda.getVersionTramite() != null) {
			sql += " and t.TRP_VERTRA = " + pFiltroBusqueda.getVersionTramite();
		}

		if (pFiltroBusqueda.getEstado() != null) {
			sql += " and e.SOP_ESTADO = '" + pFiltroBusqueda.getEstado() + "'";
		}

		if (pFiltroBusqueda.getListaAreas() != null) {
			StringBuilder areasString = new StringBuilder("");
			for (String area : pFiltroBusqueda.getListaAreas()) {
				if (areasString.toString().isEmpty()) {
					areasString.append("('").append(area).append("'");
				} else {
					areasString.append(", '").append(area).append("'");
				}
			}
			areasString.append(")");
			sql += (" and t.TRP_IDEARE in " + areasString.toString());
		}

		Query query = entityManager.createNativeQuery(sql);

		return Long.valueOf(query.getResultList().size());
	}

	@Override
	public void updateEstadoIncidencia(Long idSoporte, TypeSoporteEstado estado, String comentarios) {
		final HSoporte hsoporte = entityManager.find(HSoporte.class, idSoporte);
		hsoporte.setEstado(estado.toString());
		hsoporte.setComentarios(comentarios);
		entityManager.merge(hsoporte);
	}
}

package es.caib.sistramit.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.externo.Evento;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroEvento;
import es.caib.sistramit.core.api.model.system.rest.interno.EventoAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroEventoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPaginacion;
import es.caib.sistramit.core.api.model.system.types.TypeEvento;
import es.caib.sistramit.core.service.repository.model.HEventoAuditoria;
import es.caib.sistramit.core.service.repository.model.HSesionTramitacion;
import es.caib.sistramit.core.service.repository.model.HTramite;

/**
 * Implementación DAO Auditoria.
 *
 * @author Indra
 */
@Repository("auditoriaDao")
public final class AuditoriaDaoImpl implements AuditoriaDao {

	private static final String ASCENDING = "ASCENDING";
	/**
	 * Entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void add(final EventoAuditoria evento) {
		HSesionTramitacion hSesion = null;
		if (evento.getIdSesionTramitacion() != null) {
			hSesion = findHSesionTramitacion(evento.getIdSesionTramitacion());
		}
		final HEventoAuditoria hEvento = HEventoAuditoria.fromModel(evento);
		hEvento.setSesionTramitacion(hSesion);
		entityManager.persist(hEvento);

	}

	@Override
	public int purgarEventosSinSesion(final Date fechaHasta) {
		final String sql = "DELETE FROM HEventoAuditoria t WHERE t.fecha < :fecha AND t.sesionTramitacion is null";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("fecha", fechaHasta);
		return query.executeUpdate();
	}

	@Override
	public int purgarEventosSesion(final String idSesionTramitacion) {
		final String sql = "DELETE FROM HEventoAuditoria t WHERE t.sesionTramitacion.idSesionTramitacion = :idSesionTramitacion";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idSesionTramitacion", idSesionTramitacion);
		return query.executeUpdate();
	}

	@Override
	public List<EventoAuditoria> retrieve(final String idSesionTramitacion, final Date fechaDesde,
			final Date fechaHasta, final boolean ordenAsc) {
		final List<EventoAuditoria> listaEvento = new ArrayList<>();

		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<HEventoAuditoria> query = builder.createQuery(HEventoAuditoria.class);
		final Root<HEventoAuditoria> root = query.from(HEventoAuditoria.class);

		Predicate predicate = builder.conjunction();

		if (fechaDesde != null) {
			predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get("fecha"), fechaDesde));
		}

		if (fechaHasta != null) {
			predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get("fecha"), fechaHasta));
		}

		final Fetch<HEventoAuditoria, HSesionTramitacion> p = root.fetch("sesionTramitacion", JoinType.LEFT);

		if (idSesionTramitacion != null) {
			predicate = builder.and(predicate,
					builder.equal(root.get("sesionTramitacion").get("idSesionTramitacion"), idSesionTramitacion));
		}

		query.where(predicate);

		if (ordenAsc) {
			query.orderBy(builder.asc(root.get("fecha")));
		} else {
			query.orderBy(builder.desc(root.get("fecha")));
		}

		final List<HEventoAuditoria> result = entityManager.createQuery(query).getResultList();

		for (final HEventoAuditoria hEventoAuditoria : result) {
			listaEvento.add(HEventoAuditoria.toModel(hEventoAuditoria));
		}

		return listaEvento;
	}

	@Override
	public List<EventoAuditoriaTramitacion> retrieveByAreas(final FiltroEventoAuditoria pFiltroBusqueda) {
		return retrieveByAreas(pFiltroBusqueda, null);
	}

	@Override
	public List<EventoAuditoriaTramitacion> retrieveByAreas(final FiltroEventoAuditoria pFiltroBusqueda,
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

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getIdSesionTramitacion())) {
			predicate = builder.and(predicate, builder.equal(tableE.get("sesionTramitacion").get("idSesionTramitacion"),
					pFiltroBusqueda.getIdSesionTramitacion()));
		}

		if (StringUtils.isNoneBlank(pFiltroBusqueda.getNif())) {
			predicate = builder.and(predicate,
					builder.like(tableT.get("nifIniciador"), "%" + pFiltroBusqueda.getNif() + "%"));
		}

		if (pFiltroBusqueda.getEvento() != null) {
			predicate = builder.and(predicate,
					builder.equal(tableE.get("tipo"), pFiltroBusqueda.getEvento().toString()));
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
					tableE.get("detalle"));

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

	private HSesionTramitacion findHSesionTramitacion(final String idSesionTramitacion) {
		HSesionTramitacion hSesion = null;
		final String sql = "SELECT t FROM HSesionTramitacion t WHERE t.idSesionTramitacion = :idSesionTramitacion";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idSesionTramitacion", idSesionTramitacion);
		final List<HSesionTramitacion> results = query.getResultList();
		if (!results.isEmpty()) {
			hSesion = results.get(0);
		}
		return hSesion;
	}

	@Override
	public int deleteLogInterno(final Date fecha) {
		// Borramos logs no asociados a tramites (errores o eventos)
		// Realizamos delete con sql nativo para optimizar y limitar num de regs
		// a borrar

		final String delete = "DELETE FROM STT_LOGINT WHERE LOG_EVEFEC < :fecha AND LOG_CODSES NOT IN ( SELECT SES_CODIGO FROM STT_SESION )";
		final Query sqlQuery = entityManager.createNativeQuery(delete);
		sqlQuery.setParameter("fecha", fecha);
		return sqlQuery.executeUpdate();
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

	/**
	 * Convierte lista eventos a lista string.
	 *
	 * @param listaEventos
	 *                         lista eventos
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

}

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
import es.caib.sistramit.core.api.model.system.EventoAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.FiltroAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.FiltroPaginacion;
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

	/**
	 * Entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void add(final EventoAuditoria evento, final String idSesionTramitacion) {
		HSesionTramitacion hSesion = null;
		if (idSesionTramitacion != null) {
			hSesion = findHSesionTramitacion(idSesionTramitacion);
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
	public List<EventoAuditoriaTramitacion> retrieveByAreas(final FiltroAuditoriaTramitacion pFiltroBusqueda) {
		return retrieveByAreas(pFiltroBusqueda, null);
	}

	@Override
	public List<EventoAuditoriaTramitacion> retrieveByAreas(final FiltroAuditoriaTramitacion pFiltroBusqueda,
			final FiltroPaginacion filtroPaginacion) {
		List<EventoAuditoriaTramitacion> result = null;
		final CriteriaQuery<EventoAuditoriaTramitacion> query = retrieveByAreasCriteria(pFiltroBusqueda,
				EventoAuditoriaTramitacion.class, false);

		if (filtroPaginacion == null) {
			result = entityManager.createQuery(query).getResultList();
		} else {
			result = entityManager.createQuery(query).setFirstResult(filtroPaginacion.getFirst())
					.setMaxResults(filtroPaginacion.getPageSize()).getResultList();
		}

		return result;
	}

	@Override
	public Long retrieveByAreasCount(final FiltroAuditoriaTramitacion pFiltros) {
		final CriteriaQuery<Long> queryCount = retrieveByAreasCriteria(pFiltros, Long.class, true);
		final Long countResult = entityManager.createQuery(queryCount).getSingleResult();

		return countResult;
	}

	private <T> CriteriaQuery<T> retrieveByAreasCriteria(final FiltroAuditoriaTramitacion pFiltroBusqueda,
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
			query.orderBy(builder.asc(tableE.get("fecha")));

			query.multiselect(tableE.get("id"), p.get("idSesionTramitacion"), tableE.get("tipo"), tableE.get("fecha"),
					tableT.get("nifIniciador"), tableT.get("idTramite"), tableT.get("versionTramite"),
					tableT.get("idProcedimientoCP"), tableT.get("idProcedimientoSIA"), tableE.get("codigoError"),
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
		final List results = query.getResultList();
		if (!results.isEmpty()) {
			hSesion = (HSesionTramitacion) results.get(0);
		}
		return hSesion;
	}

}

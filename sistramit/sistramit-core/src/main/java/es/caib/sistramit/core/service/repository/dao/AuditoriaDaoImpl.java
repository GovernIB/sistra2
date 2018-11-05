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
import es.caib.sistramit.core.api.model.system.FiltrosAuditoriaTramitacion;
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
	public List<EventoAuditoriaTramitacion> retrieveByAreas(final FiltrosAuditoriaTramitacion pFiltros) {

		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EventoAuditoriaTramitacion> query = builder.createQuery(EventoAuditoriaTramitacion.class);

		final Root<HEventoAuditoria> tableE = query.from(HEventoAuditoria.class);
		final Root<HTramite> tableT = query.from(HTramite.class);

		final Join<HEventoAuditoria, HSesionTramitacion> p = tableE.join("sesionTramitacion");

		Predicate predicate = builder.equal(tableE.get("sesionTramitacion"), tableT.get("sesionTramitacion"));

		if (pFiltros.getListaAreas() != null) {
			predicate = builder.and(predicate, tableT.get("idArea").in(pFiltros.getListaAreas()));
		}

		if (pFiltros.getFechaDesde() != null) {
			predicate = builder.and(predicate,
					builder.greaterThanOrEqualTo(tableE.get("fecha"), pFiltros.getFechaDesde()));
		}

		if (pFiltros.getFechaHasta() != null) {
			predicate = builder.and(predicate,
					builder.lessThanOrEqualTo(tableE.get("fecha"), pFiltros.getFechaHasta()));
		}

		if (StringUtils.isNoneBlank(pFiltros.getIdSesionTramitacion())) {
			predicate = builder.and(predicate, builder.equal(tableE.get("sesionTramitacion").get("idSesionTramitacion"),
					pFiltros.getIdSesionTramitacion()));
		}

		if (StringUtils.isNoneBlank(pFiltros.getNif())) {
			predicate = builder.and(predicate, builder.like(tableT.get("nifIniciador"), "%" + pFiltros.getNif() + "%"));
		}

		if (pFiltros.getEvento() != null) {
			predicate = builder.and(predicate, builder.equal(tableE.get("tipo"), pFiltros.getEvento().toString()));
		}

		if (StringUtils.isNoneBlank(pFiltros.getIdTramite())) {
			predicate = builder.and(predicate, builder.equal(tableT.get("idTramite"), pFiltros.getIdTramite()));
		}

		if (pFiltros.getVersionTramite() != null) {
			predicate = builder.and(predicate,
					builder.equal(tableT.get("versionTramite"), pFiltros.getVersionTramite()));
		}

		if (StringUtils.isNoneBlank(pFiltros.getIdProcedimientoCP())) {
			predicate = builder.and(predicate,
					builder.equal(tableT.get("idProcedimientoCP"), pFiltros.getIdProcedimientoCP()));
		}

		query.where(predicate);

		query.orderBy(builder.asc(tableE.get("fecha")));

		final List<EventoAuditoriaTramitacion> result = entityManager.createQuery(query.multiselect(tableE.get("id"),
				p.get("idSesionTramitacion"), tableE.get("tipo"), tableE.get("fecha"), tableT.get("nifIniciador"),
				tableT.get("idTramite"), tableT.get("versionTramite"), tableT.get("idProcedimientoCP"),
				tableT.get("idProcedimientoSIA"), tableE.get("codigoError"), tableE.get("descripcion"),
				tableE.get("resultado"), tableE.get("trazaError"), tableE.get("detalle"))).getResultList();

		return result;
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

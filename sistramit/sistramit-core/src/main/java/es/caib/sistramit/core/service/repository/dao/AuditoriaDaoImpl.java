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
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.service.repository.model.HEventoAuditoria;
import es.caib.sistramit.core.service.repository.model.HSesionTramitacion;

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
	public int deleteLogInterno(final Date fecha) {
		// Borramos logs no asociados a tramites (errores o eventos)
		// Realizamos delete con sql nativo para optimizar y limitar num de regs
		// a borrar

		final String delete = "DELETE FROM STT_LOGINT WHERE LOG_EVEFEC < :fecha AND LOG_CODSES NOT IN ( SELECT SES_CODIGO FROM STT_SESION )";
		final Query sqlQuery = entityManager.createNativeQuery(delete);
		sqlQuery.setParameter("fecha", fecha);
		return sqlQuery.executeUpdate();
	}

	// --------------------- FUNCIONES PRIVADAS
	// ------------------------------------------------------------

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

}

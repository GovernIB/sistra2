package es.caib.sistramit.core.service.repository.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

    /**
     * Entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(EventoAuditoria evento, String idSesionTramitacion) {
        HSesionTramitacion hSesion = null;
        if (idSesionTramitacion != null) {
            hSesion = findHSesionTramitacion(idSesionTramitacion);
        }
        final HEventoAuditoria hEvento = HEventoAuditoria.fromModel(evento);
        hEvento.setSesionTramitacion(hSesion);
        entityManager.persist(hEvento);

    }

    @Override
    public int purgarEventosSinSesion(Date fechaHasta) {
        final String sql = "DELETE FROM HEventoAuditoria t WHERE t.fecha < :fecha AND t.sesionTramitacion is null";
        final Query query = entityManager.createQuery(sql);
        query.setParameter("fecha", fechaHasta);
        return query.executeUpdate();
    }

    @Override
    public int purgarEventosSesion(String idSesionTramitacion) {
        final String sql = "DELETE FROM HEventoAuditoria t WHERE t.sesionTramitacion.idSesionTramitacion = :idSesionTramitacion";
        final Query query = entityManager.createQuery(sql);
        query.setParameter("idSesionTramitacion", idSesionTramitacion);
        return query.executeUpdate();
    }

    @Override
    public List<EventoAuditoria> retrieve(String idSesionTramitacion,
            Date fechaDesde, Date fechaHasta, boolean ordenAsc) {
        // TODO Auto-generated method stub
        return null;
    }

    private HSesionTramitacion findHSesionTramitacion(
            String idSesionTramitacion) {
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

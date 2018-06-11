package es.caib.sistramit.core.service.repository.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
            hSesion = entityManager.find(HSesionTramitacion.class,
                    idSesionTramitacion);
        }
        final HEventoAuditoria hEvento = HEventoAuditoria.fromModel(evento);
        hEvento.setSesionTramitacion(hSesion);
        entityManager.persist(hEvento);

    }

    @Override
    public int purgar(Date toDate) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<EventoAuditoria> retrieve(String idSesionTramitacion,
            Date fechaDesde, Date fechaHasta, boolean ordenAsc) {
        // TODO Auto-generated method stub
        return null;
    }

}

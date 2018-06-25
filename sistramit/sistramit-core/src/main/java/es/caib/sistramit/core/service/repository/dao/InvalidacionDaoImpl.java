package es.caib.sistramit.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import es.caib.sistramit.core.api.model.system.Invalidacion;
import es.caib.sistramit.core.service.repository.model.HInvalidacion;

/**
 * Implementación DAO Invalidacion.
 *
 * @author Indra
 */
@Repository("invalidacionDao")
public final class InvalidacionDaoImpl implements InvalidacionDao {

    /**
     * Entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addInvalidacion(Invalidacion invalidacion) {
        final HInvalidacion h = HInvalidacion.fromModel(invalidacion);
        entityManager.persist(h);
        invalidacion.setFecha(h.getFecha());
    }

    @Override
    public List<Invalidacion> obtenerInvalidaciones(Date fecha) {
        final String sql = "SELECT t FROM HInvalidacion t WHERE t.fecha > :fecha ORDER BY t.fecha desc";
        final Query query = entityManager.createQuery(sql);
        query.setParameter("fecha", fecha);
        final List<HInvalidacion> results = query.getResultList();

        final List<Invalidacion> res = new ArrayList<>();
        for (final HInvalidacion h : results) {
            res.add(h.toModel());
        }
        return res;
    }

    @Override
    public void purgarInvalidaciones(Date fechaHasta) {

        // TODO PENDIENTE. PURGAR A LAS 24h.

    }

}

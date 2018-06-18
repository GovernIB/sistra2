package es.caib.sistramit.core.service.repository.dao;

import java.util.Date;
import java.util.List;

import es.caib.sistramit.core.api.model.system.Invalidacion;
import es.caib.sistramit.core.api.model.system.types.TypeInvalidacion;

/**
 * DAO para invalidaciones y refresco caché: definicion tramite, datos dominio,
 * configuracion,...
 */
public interface InvalidacionDao {

    /**
     * Guarda invalidacion.
     *
     * @param invalidacion
     *            invalidacion
     */
    void addInvalidacion(Invalidacion invalidacion);

    /**
     * Obtiene lista invalidaciones a partir de una fecha.
     *
     * @param tipo
     *            tipo
     * @param fecha
     *            fecha
     * @return Lista de invalidaciones
     */
    List<Invalidacion> obtenerInvalidaciones(TypeInvalidacion tipo, Date fecha);

    /**
     * Purga invalidaciones.
     *
     * @param fechaHasta
     *            Borra invalidaciones hasta la fecha indicada
     */
    void purgarInvalidaciones(Date fechaHasta);

}

package es.caib.sistramit.core.service.repository.dao;

import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistra2.commons.utils.JSONUtil;
import es.caib.sistra2.commons.utils.JSONUtilException;
import es.caib.sistramit.core.api.exception.RepositoryException;
import es.caib.sistramit.core.api.model.system.rest.externo.TramiteFinalizado;
import es.caib.sistramit.core.service.model.flujo.EntregaTramite;
import es.caib.sistramit.core.service.model.flujo.types.TypeEntregaEstado;
import es.caib.sistramit.core.service.repository.model.HTramiteEntrega;
import es.caib.sistramit.core.service.repository.model.HTramiteFinalizado;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

/**
 * Implementación DAO Entrega trámite.
 *
 * @author Indra
 */
@Repository("entregaDao")
public final class EntregaTramiteDaoImpl implements EntregaTramiteDao {

    /**
     * Entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void crearEntrega(String idSesionTramitacion, Date fechaRegistro, String idEntidad, AsientoRegistral asiento, boolean inmediato) {
        HTramiteEntrega hTramiteEntrega = new HTramiteEntrega();
        hTramiteEntrega.setIdSesionTramitacion(idSesionTramitacion);
        hTramiteEntrega.setFechaFinalizacion(fechaRegistro);
        hTramiteEntrega.setIdEntidad(idEntidad);
        hTramiteEntrega.setInmediato(inmediato);
        hTramiteEntrega.setEstado(TypeEntregaEstado.PENDIENTE_ENTREGAR.toString());
        try {
            hTramiteEntrega.setAsiento(JSONUtil.toJSON(asiento));
        } catch (Exception e) {
            new RepositoryException("Error al serializar el asiento", e);
        }
        entityManager.persist(hTramiteEntrega);

        entityManager.flush();
        System.out.println("Entrega creada: " + hTramiteEntrega.getCodigo());

    }

    @Override
    public AsientoRegistral recuperarAsiento(String idSesionTramitacion) {
        HTramiteEntrega he = getHTramiteEntrega(idSesionTramitacion);
        AsientoRegistral asientoRegistral = null;
        try {
            asientoRegistral = (AsientoRegistral) JSONUtil.fromJSON(he.getAsiento(), AsientoRegistral.class);
        } catch (JSONUtilException e) {
            new RepositoryException("Error al deserializar el asiento", e);
        }
        return asientoRegistral;
    }

    @Override
    public List<EntregaTramite> recuperarEntregaInmediatosPendientes() {
        // Recupera pendientes inmediatas que no se han procesado todavía y no están bloqueadas
        return recuperarPendientes(Arrays.asList(TypeEntregaEstado.PENDIENTE_ENTREGAR.toString()), true);
    }


    @Override
    public List<EntregaTramite> recuperarEntregaPeriodicosPendientes() {
        // Recupera pendientes periódicas que no están bloqueadas
        return recuperarPendientes(Arrays.asList(
                        TypeEntregaEstado.PENDIENTE_ENTREGAR.toString(),
                        TypeEntregaEstado.REINTENTAR_ENTREGA.toString(),
                        TypeEntregaEstado.ERROR_ENTREGA.toString()),
                null);
    }

    @Override
    public void actualizarEstadoEntrega(String idSesionTramitacion, String idSesionEnvio, TypeEntregaEstado estado, String mensajeError) {
        // Actualiza entrega (desbloqueamos entrada)
        String sql = "UPDATE HTramiteEntrega t SET t.estado = :estado, t.idSesionEnvio = :idSesionEnvio, t.mensajeError = :mensajeError, t.fechaEntrega = :fechaEntrega, t.fechaBloqueo = null WHERE t.idSesionTramitacion = :idSesionTramitacion";
        Query query = entityManager.createQuery(sql);
        query.setParameter("estado", estado.toString());
        query.setParameter("idSesionEnvio", idSesionEnvio);
        query.setParameter("mensajeError", mensajeError);
        query.setParameter("idSesionTramitacion", idSesionTramitacion);
        query.setParameter("fechaEntrega", new Date());
        query.executeUpdate();

        // Si es correcto, actualizamos idenvio en tabla tramites finalizados
        if (estado == TypeEntregaEstado.ENTREGADO) {
            sql = "UPDATE HTramiteFinalizado t SET t.numeroEntrega = :idSesionEnvio WHERE t.idSesionTramitacion = :idSesionTramitacion";
            query = entityManager.createQuery(sql);
            query.setParameter("idSesionEnvio", idSesionEnvio);
            query.setParameter("idSesionTramitacion", idSesionTramitacion);
            query.executeUpdate();
        }
    }

    @Override
    public boolean bloquearEntrega(String idSesionTramitacion) {
        String sql = "UPDATE HTramiteEntrega t SET t.fechaBloqueo = :fecha WHERE t.idSesionTramitacion = :idSesionTramitacion AND t.fechaBloqueo is null AND t.estado <> :estadoEntregado";
        Query query = entityManager.createQuery(sql);
        query.setParameter("idSesionTramitacion", idSesionTramitacion);
        query.setParameter("fecha", new Date());
        query.setParameter("estadoEntregado", TypeEntregaEstado.ENTREGADO.toString());
        return (query.executeUpdate() == 1);
    }

    @Override
    public void desbloquearEntregas() {
        // Desbloquea las que no se han procesado y hayan pasado el limite de tiempo
        Date fechaLimiteBloqueo = DateUtils.addMinutes(new Date(), -15);
        String sql = "UPDATE HTramiteEntrega t SET t.fechaBloqueo = null WHERE t.fechaBloqueo < :fechaLimite";
        Query query = entityManager.createQuery(sql);
        query.setParameter("fechaLimite", fechaLimiteBloqueo);
        query.executeUpdate();
    }

    @Override
    public int purgarEntregasTramites() {
        String sql = "DElETE HTramiteEntrega t WHERE t.estado = :estado";
        Query query = entityManager.createQuery(sql);
        query.setParameter("estado", TypeEntregaEstado.ENTREGADO.toString());
        return query.executeUpdate();
    }

    @Override
    public TramiteFinalizado recuperarTramiteFinalizado(String idSesionTramitacion) {
        TramiteFinalizado res = null;
        final String sql = "SELECT t from HTramiteFinalizado t where t.idSesionTramitacion = :idSesionTramitacion";
        final Query query = entityManager.createQuery(sql);
        query.setParameter("idSesionTramitacion", idSesionTramitacion);
        final List<?> results = query.getResultList();
        if (!results.isEmpty()) {
            HTramiteFinalizado hTramiteFinalizado = (HTramiteFinalizado) results.get(0);
            res = HTramiteFinalizado.toModel(hTramiteFinalizado);
        }
        return res;
    }

    @Override
    public List<TramiteFinalizado> recuperarFinalizadosFHPendientes() {
        List<TramiteFinalizado> res = new ArrayList<>();
        final String sql = "SELECT t from HTramiteFinalizado t where t.funcionarioHabilitadoIdActuacion is not null and t.funcionarioHabilitadoAvisoFecha is null";
        final Query query = entityManager.createQuery(sql);
        final List<?> results = query.getResultList();
        for (Object obj : results) {
            HTramiteFinalizado hTramiteFinalizado = (HTramiteFinalizado) obj;
            res.add(HTramiteFinalizado.toModel(hTramiteFinalizado));
        }
        return res;
    }

    @Override
    public void actualizarAvisoCorrectoFuncionarioHabilitado(String idSesionTramitacion) {
        String sql = "UPDATE HTramiteFinalizado t SET t.funcionarioHabilitadoAvisoFecha = :fechaAviso, t.funcionarioHabilitadoAvisoError = null WHERE t.idSesionTramitacion = :idSesionTramitacion";
        Query query = entityManager.createQuery(sql);
        query.setParameter("fechaAviso", new Date());
        query.setParameter("idSesionTramitacion", idSesionTramitacion);
        query.executeUpdate();
    }

    @Override
    public void actualizarAvisoErrorFuncionarioHabilitado(String idSesionTramitacion, String msgError) {
        String sql = "UPDATE HTramiteFinalizado t SET t.funcionarioHabilitadoAvisoFecha = null, t.funcionarioHabilitadoAvisoError = :msgError WHERE t.idSesionTramitacion = :idSesionTramitacion";
        Query query = entityManager.createQuery(sql);
        query.setParameter("msgError", StringUtils.substring(msgError, 0, 4000));
        query.setParameter("idSesionTramitacion", idSesionTramitacion);
        query.executeUpdate();
    }

    /**
     * Recupera entrega trámite.
     *
     * @param idSesionTramitacion id sesion tramitacion
     * @return entrega trámite
     */
    private HTramiteEntrega findHTramiteEntrega(final String idSesionTramitacion) {
        HTramiteEntrega hTramite = null;
        final String sql = "SELECT t from HTramiteEntrega t where t.idSesionTramitacion = :idSesionTramitacion";
        final Query query = entityManager.createQuery(sql);
        query.setParameter("idSesionTramitacion", idSesionTramitacion);
        final List<?> results = query.getResultList();
        if (!results.isEmpty()) {
            hTramite = (HTramiteEntrega) results.get(0);
        }
        return hTramite;
    }

    /**
     * Busca entrega trámite y genera excepcion si no lo encuentra.
     *
     * @param pIdSesionTramitacion id sesion tramitacion
     * @return entrega trámite
     */
    private HTramiteEntrega getHTramiteEntrega(final String pIdSesionTramitacion) {
        final HTramiteEntrega hTramite = findHTramiteEntrega(pIdSesionTramitacion);
        if (hTramite == null) {
            throw new RepositoryException("No existeix tràmit: " + pIdSesionTramitacion);
        }
        return hTramite;
    }

    /**
     * Recupera pendientes.
     *
     * @param estados   Estados a recuperar.
     * @param inmediato Si es inmediato (si nulo no se controla)
     * @return lista de entregas
     */
    private List<EntregaTramite> recuperarPendientes(List<String> estados, Boolean inmediato) {

        List<EntregaTramite> entregas;
        entregas = new ArrayList<>();

        // Recuperamos solo campos necesarios para no penalizar recuperar el CLOB
        String sql = "SELECT t.idSesionTramitacion, t.idEntidad, t.estado, t.idSesionEnvio FROM HTramiteEntrega t WHERE t.estado IN :estados AND t.fechaBloqueo is null ";
        if (inmediato != null) {
            sql += " AND t.inmediato = :inmediato";
        }

        final Query query = entityManager.createQuery(sql);
        query.setParameter("estados", estados);
        if (inmediato != null) {
            query.setParameter("inmediato", inmediato);
        }

        final List<Object[]> resultados = query.getResultList();
        for (Object[] resultado : resultados) {
            EntregaTramite et = new EntregaTramite();
            et.setIdSesionTramitacion((String) resultado[0]);
            et.setIdEntidad((String) resultado[1]);
            et.setEstado(TypeEntregaEstado.fromString((String) resultado[2]));
            et.setIdSesionEnvio((String) resultado[3]);
            entregas.add(et);
        }
        return entregas;
    }


}

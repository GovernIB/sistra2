package es.caib.sistramit.core.service.repository.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.GeneradorId;
import es.caib.sistramit.core.api.exception.RepositoryException;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoTramite;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaTramite;
import es.caib.sistramit.core.service.model.flujo.EstadoPersistenciaPasoTramite;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.repository.model.HDocumento;
import es.caib.sistramit.core.service.repository.model.HFirma;
import es.caib.sistramit.core.service.repository.model.HPaso;
import es.caib.sistramit.core.service.repository.model.HSesionTramitacion;
import es.caib.sistramit.core.service.repository.model.HTramite;

/**
 * Implementación DAO Flujo Tramite.
 *
 * @author Indra
 */
@Repository("flujoTramiteDao")
public final class FlujoTramiteDaoImpl implements FlujoTramiteDao {

    /**
     * Entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String crearSesionTramitacion() {
        // Generamos id sesion
        final String id = GeneradorId.generarId();
        // Creamos sesion
        final HSesionTramitacion hSesionTramitacion = new HSesionTramitacion();
        hSesionTramitacion.setIdSesionTramitacion(id);
        hSesionTramitacion.setFecha(new Date());
        entityManager.persist(hSesionTramitacion);
        return id;
    }

    @Override
    public void crearTramitePersistencia(DatosPersistenciaTramite dpt) {
        // Mapeamos propiedades
        final HTramite hTramite = HTramite.fromModel(dpt);
        // Establecemos sesion tramitacion
        final HSesionTramitacion hSesionTramitacion = findHSesionTramitacion(
                dpt.getIdSesionTramitacion());
        if (hSesionTramitacion == null) {
            throw new RepositoryException(
                    "No existe tramite: " + dpt.getIdSesionTramitacion());
        }
        hTramite.setSesionTramitacion(hSesionTramitacion);
        // Establecemos fecha ultimo acceso
        dpt.setFechaUltimoAcceso(new Date());
        // Actualizamos bbdd
        entityManager.persist(hTramite);
    }

    @Override
    public DatosPersistenciaTramite obtenerTramitePersistencia(
            String idSesionTramitacion) {
        final HTramite hTramite = getHTramite(idSesionTramitacion);
        return HTramite.toModel(hTramite);
    }

    @Override
    public Date registraAccesoTramite(final String pIdSesionTramitacion,
            final Date pFechaCaducidad) {
        // Buscamos tramite y actualizamos propiedades
        final HTramite hTramite = getHTramite(pIdSesionTramitacion);
        final Timestamp timestampFlujo = new Timestamp(
                System.currentTimeMillis());
        final Date fecha = new Date();
        hTramite.setFechaUltimoAcceso(fecha);
        hTramite.setFechaCaducidad(pFechaCaducidad);
        hTramite.setTimestamp(timestampFlujo);

        // Actualizamos bbdd
        entityManager.merge(hTramite);

        // Devolvamos timestamp flujo
        return timestampFlujo;

    }

    @Override
    public boolean verificaTimestampFlujo(final String pIdSesionTramitacion,
            final Date pTimestampFlujo) {
        // Buscamos tramite
        final HTramite hTramite = getHTramite(pIdSesionTramitacion);
        // Verificamos que coincida el timestamp
        return (hTramite.getTimestamp().compareTo(pTimestampFlujo) == 0);
    }

    @Override
    public void cambiaEstadoTramite(final String pIdSesionTramitacion,
            final TypeEstadoTramite pEstado) {
        final HTramite hTramite = getHTramite(pIdSesionTramitacion);
        if (pEstado == TypeEstadoTramite.FINALIZADO) {
            hTramite.setFechaFin(new Date());
        }
        hTramite.setEstado(pEstado.toString());
        // Actualizamos bbdd
        entityManager.merge(hTramite);
    }

    @Override
    public void crearPaso(final String pIdSesionTramitacion,
            final String pIdPaso, final TypePaso pTipoPaso, final int orden) {

        // Buscamos tramite
        final HTramite hTramite = getHTramite(pIdSesionTramitacion);

        // Creamos paso
        final HPaso hPaso = new HPaso();
        hPaso.setIdentificadorPaso(pIdPaso);
        hPaso.setEstado(TypeEstadoPaso.NO_INICIALIZADO.toString());
        hPaso.setTipoPaso(pTipoPaso.toString());
        hPaso.setTramitePersistencia(hTramite);
        hPaso.setOrden(orden);

        entityManager.persist(hPaso);

    }

    @Override
    public void eliminarPaso(final String pIdSesionTramitacion,
            final String pIdPaso) {

        // Obtenemos paso
        final HPaso paso = getHPaso(pIdSesionTramitacion, pIdPaso);

        // Buscamos ficheros a eliminar
        final List<ReferenciaFichero> ficherosEliminar = new ArrayList<ReferenciaFichero>();

        // Recorremos documentos para ver los ficheros a eliminar
        for (final HDocumento hdoc : paso.getDocumentos()) {
            // Buscamos ficheros del documento
            ficherosEliminar.addAll(obtenerFicherosDocumento(hdoc));
            // Buscamos firmas documento
            ficherosEliminar.addAll(obtenerFirmasDocumento(hdoc));
        }

        // Eliminamos paso
        entityManager.remove(paso);

        // Hacemos fluhs antes de eliminar los ficheros
        entityManager.flush();

        // Eliminamos ficheros (firmas y ficheros)
        eliminarFicheros(ficherosEliminar);

    }

    @Override
    public void eliminarPasos(final String pIdSesionTramitacion) {
        final List<HPaso> listaPasos = findHPasos(pIdSesionTramitacion);
        for (final HPaso paso : listaPasos) {
            eliminarPaso(pIdSesionTramitacion, paso.getIdentificadorPaso());
        }
    }

    @Override
    public List<EstadoPersistenciaPasoTramite> obtenerListaPasos(
            final String pIdSesionTramitacion) {

        final List<HPaso> lstPasos = findHPasos(pIdSesionTramitacion);
        final List<EstadoPersistenciaPasoTramite> lista = new ArrayList<>();
        for (final HPaso paso : lstPasos) {
            final EstadoPersistenciaPasoTramite ept = EstadoPersistenciaPasoTramite
                    .createNewEstadoPersistenciaPasoTramite();
            ept.setEstado(TypeEstadoPaso.fromString(paso.getEstado()));
            ept.setId(paso.getIdentificadorPaso());
            ept.setTipo(TypePaso.fromString(paso.getTipoPaso()));
            ept.setOrden(paso.getOrden());
            lista.add(ept);
        }

        return lista;
    }

    @Override
    public void purgarTramite(final String pIdSesionTramitacion) {
        // Eliminamos pasos de tramitacion
        eliminarPasos(pIdSesionTramitacion);
        // Marca el tramite como purgado
        final HTramite hTramite = getHTramite(pIdSesionTramitacion);
        hTramite.setPurgado(true);
        hTramite.setFechaPurgado(new Date());
        entityManager.merge(hTramite);
    }

    @Override
    public void cancelarTramite(final String pIdSesionTramitacion) {
        // Marca el tramite como cancelado
        final HTramite hTramite = getHTramite(pIdSesionTramitacion);
        hTramite.setEstado(TypeEstadoTramite.FINALIZADO.toString());
        hTramite.setFechaFin(new Date());
        hTramite.setCancelado(true);
        entityManager.merge(hTramite);
    }

    @Override
    public Long contadorLimiteTramitacion(final String idTramite,
            final int version, final int pLimiteIntervalo,
            final Date finIntervalo) {

        Long res = null;

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(finIntervalo);
        calendar.add(Calendar.MINUTE, pLimiteIntervalo * ConstantesNumero.N_1);
        final Date inicioIntervalo = calendar.getTime();

        final String hql = "SELECT COUNT(*) FROM HTramite WHERE idTramite = :idTramite AND versionTramite = :versionTramite "
                + " AND fechaInicio >= :fechaInicio AND fechaInicio <= :fechaFin";
        final Query query = entityManager.createQuery(hql);
        query.setParameter("idTramite", idTramite);
        query.setParameter("versionTramite", version);
        query.setParameter("fechaInicio", inicioIntervalo);
        query.setParameter("fechaFin", finIntervalo);
        res = (Long) query.getSingleResult();

        return res;
    }

    // ------------ FUNCIONES PRIVADAS --------------------------------------
    /**
     * Busca sesion tramitacion.
     *
     * @param idSesionTramitacion
     *            id sesion tramitacion
     * @return sesion tramitacion
     */
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

    /**
     * Busca tramite.
     *
     * @param idSesionTramitacion
     *            id sesion tramitacion
     * @return sesion tramitacion
     */
    private HTramite findHTramite(String idSesionTramitacion) {
        HTramite hTramite = null;
        final String sql = "SELECT t from HTramite t where t.sesionTramitacion.idSesionTramitacion = :idSesionTramitacion";
        final Query query = entityManager.createQuery(sql);
        query.setParameter("idSesionTramitacion", idSesionTramitacion);
        final List results = query.getResultList();
        if (!results.isEmpty()) {
            hTramite = (HTramite) results.get(0);
        }
        return hTramite;
    }

    /**
     * Busca tramite y genera excepcion si no lo encuentra.
     *
     * @param idSesionTramitacion
     *            id sesion tramitacion
     * @return sesion tramitacion
     */
    private HTramite getHTramite(String pIdSesionTramitacion) {
        final HTramite hTramite = findHTramite(pIdSesionTramitacion);
        if (hTramite == null) {
            throw new RepositoryException(
                    "No existe tramite: " + pIdSesionTramitacion);
        }
        return hTramite;
    }

    /**
     * Busca pasos tramite.
     *
     * @param idSesionTramitacion
     *            id sesion tramitacion
     * @return pasos tramite
     */
    private List<HPaso> findHPasos(String idSesionTramitacion) {
        final String sql = "select p from HPaso p inner join p.tramitePersistencia t where t.sesionTramitacion.idSesionTramitacion=:idSesionTramitacion order by p.codigo";
        final Query query = entityManager.createQuery(sql);
        query.setParameter("idSesionTramitacion", idSesionTramitacion);
        final List<HPaso> results = query.getResultList();
        return results;
    }

    /**
     * Busca paso tramite.
     *
     * @param idSesionTramitacion
     *            id sesion tramitacion
     * @return paso tramite
     */
    private HPaso findHPaso(String idSesionTramitacion, String idPaso) {
        final String sql = "select p from HPaso p inner join p.tramitePersistencia t where t.sesionTramitacion.idSesionTramitacion=:idSesionTramitacion and p.identificadorPaso = :idPaso order by p.codigo";
        final Query query = entityManager.createQuery(sql);
        query.setParameter("idSesionTramitacion", idSesionTramitacion);
        query.setParameter("idPaso", idPaso);
        final List<HPaso> results = query.getResultList();
        HPaso res = null;
        if (!results.isEmpty()) {
            if (results.size() > ConstantesNumero.N1) {
                throw new RepositoryException("Se han encontrado "
                        + results.size() + " con id paso " + idPaso
                        + " para id sesion tramitacion " + idSesionTramitacion);
            }
            res = results.get(0);
        }
        return res;
    }

    /**
     * Busca paso tramite y genera excepción si no lo encuentra.
     *
     * @param idSesionTramitacion
     *            id sesion tramitacion
     * @return paso tramite
     */
    private HPaso getHPaso(String idSesionTramitacion, String idPaso) {
        final HPaso paso = findHPaso(idSesionTramitacion, idPaso);
        if (paso == null) {
            throw new RepositoryException("No se encuentra paso " + idPaso
                    + " para id sesion tramitacion " + idSesionTramitacion);
        }
        return paso;
    }

    /**
     * Obtiene ficheros de las firmas de un documento.
     *
     * @param hdoc
     *            HDocumento
     * @return ficheros de las firmas de un documento.
     */
    private List<ReferenciaFichero> obtenerFirmasDocumento(
            final HDocumento hdoc) {
        ReferenciaFichero ref;
        final List<ReferenciaFichero> ficherosDoc = new ArrayList<>();
        for (final HFirma hFirma : hdoc.getFirmas()) {
            if (hFirma.getFirma() != null) {
                ref = ReferenciaFichero.createNewReferenciaFichero(
                        hFirma.getFirma(), hFirma.getFirmaClave());
                ficherosDoc.add(ref);
            }
        }
        return ficherosDoc;
    }

    /**
     * Obtiene ficheros deun documento.
     *
     * @param hdoc
     *            HDocumento
     * @return ficheros de un documento.
     */
    private List<ReferenciaFichero> obtenerFicherosDocumento(
            final HDocumento hdoc) {
        ReferenciaFichero ref;
        final List<ReferenciaFichero> ficherosDoc = new ArrayList<>();
        if (hdoc.getFichero() != null) {
            ref = ReferenciaFichero.createNewReferenciaFichero(
                    hdoc.getFichero(), hdoc.getFicheroClave());
            ficherosDoc.add(ref);
        }
        if (hdoc.getFormularioPdf() != null) {
            ref = ReferenciaFichero.createNewReferenciaFichero(
                    hdoc.getFormularioPdf(), hdoc.getFormularioPdfClave());
            ficherosDoc.add(ref);
        }
        if (hdoc.getPagoJustificantePdf() != null) {
            ref = ReferenciaFichero.createNewReferenciaFichero(
                    hdoc.getPagoJustificantePdf(),
                    hdoc.getPagoJustificantePdfClave());
            ficherosDoc.add(ref);
        }
        return ficherosDoc;
    }

    /**
     * Método para Eliminar ficheros de la clase FlujoTramiteDaoImpl.
     *
     * @param ficheros
     *            Parámetro ficheros
     */
    private void eliminarFicheros(final List<ReferenciaFichero> ficheros) {
        if (!ficheros.isEmpty()) {
            final StringBuffer sb = new StringBuffer(
                    ConstantesNumero.N40 * ficheros.size());
            sb.append("delete from HFichero where ");
            boolean primer = true;
            for (final ReferenciaFichero ref : ficheros) {
                if (primer) {
                    primer = false;
                } else {
                    sb.append(" or ");
                }
                sb.append("codigo = " + ref.getId());
            }
            final String sql = sb.toString();
            final Query query = entityManager.createQuery(sql);
            query.executeUpdate();
        }
    }

}

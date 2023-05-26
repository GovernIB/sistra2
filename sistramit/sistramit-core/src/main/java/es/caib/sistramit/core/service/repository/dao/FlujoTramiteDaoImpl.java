package es.caib.sistramit.core.service.repository.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.GeneradorId;
import es.caib.sistramit.core.api.exception.RepositoryException;
import es.caib.sistramit.core.api.model.flujo.TramiteIniciado;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoTramite;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.model.flujo.DatosFormularioSoporte;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaTramite;
import es.caib.sistramit.core.service.model.flujo.EstadoPersistenciaPasoTramite;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.types.TypeDocumentoPersistencia;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.repository.model.HDocumento;
import es.caib.sistramit.core.service.repository.model.HFirma;
import es.caib.sistramit.core.service.repository.model.HPaso;
import es.caib.sistramit.core.service.repository.model.HSesionTramitacion;
import es.caib.sistramit.core.service.repository.model.HSoporte;
import es.caib.sistramit.core.service.repository.model.HTramite;
import es.caib.sistramit.core.service.repository.model.HTramiteFinalizado;

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
	public void crearTramitePersistencia(final DatosPersistenciaTramite dpt) {
		// Mapeamos propiedades
		final HTramite hTramite = HTramite.fromModel(dpt);
		// Establecemos sesion tramitacion
		final HSesionTramitacion hSesionTramitacion = findHSesionTramitacion(dpt.getIdSesionTramitacion());
		if (hSesionTramitacion == null) {
			throw new RepositoryException("No existeix tràmit: " + dpt.getIdSesionTramitacion());
		}
		hTramite.setSesionTramitacion(hSesionTramitacion);
		// Establecemos fecha ultimo acceso
		dpt.setFechaUltimoAcceso(new Date());
		// Actualizamos bbdd
		entityManager.persist(hTramite);
	}

	@Override
	public DatosPersistenciaTramite obtenerTramitePersistencia(final String idSesionTramitacion) {
		final HTramite hTramite = getHTramite(idSesionTramitacion);
		return HTramite.toModel(hTramite);
	}

	@Override
	public Date registraAccesoTramite(final String pIdSesionTramitacion, final Date pFechaCaducidad) {
		// Buscamos tramite y actualizamos propiedades
		final HTramite hTramite = getHTramite(pIdSesionTramitacion);
		final Timestamp timestampFlujo = new Timestamp(System.currentTimeMillis());
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
	public boolean verificaTimestampFlujo(final String pIdSesionTramitacion, final Date pTimestampFlujo) {
		// Buscamos tramite
		final HTramite hTramite = getHTramite(pIdSesionTramitacion);
		// Verificamos que coincida el timestamp
		return (hTramite.getTimestamp().compareTo(pTimestampFlujo) == 0);
	}

	@Override
	public void cambiaEstadoTramite(final String pIdSesionTramitacion, final TypeEstadoTramite pEstado) {
		final HTramite hTramite = getHTramite(pIdSesionTramitacion);
		// Actualizamos estado
		if (pEstado == TypeEstadoTramite.FINALIZADO) {
			hTramite.setFechaFin(new Date());
		}
		hTramite.setEstado(pEstado.toString());
		entityManager.merge(hTramite);

		// Si finalizado, persistimos en tabla de finalizados
		if (pEstado == TypeEstadoTramite.FINALIZADO && !hTramite.isCancelado()) {
			final HTramiteFinalizado hTramiteFin = new HTramiteFinalizado();
			hTramiteFin.setIdSesionTramitacion(hTramite.getSesionTramitacion().getIdSesionTramitacion());
			hTramiteFin.setFechaFinalizacion(hTramite.getFechaFin());
			hTramiteFin.setIdTramite(hTramite.getIdTramite());
			hTramiteFin.setVersionTramite(hTramite.getVersionTramite());
			hTramiteFin.setDescripcionTramite(hTramite.getDescripcionTramite());
			hTramiteFin.setIdProcedimientoSIA(hTramite.getIdProcedimientoSIA());
			hTramiteFin.setIdioma(hTramite.getIdioma());
			hTramiteFin.setAutenticacion(hTramite.getAutenticacion());
			hTramiteFin.setMetodoAutenticacion(hTramite.getMetodoAutenticacion());
			hTramiteFin.setNifIniciador(hTramite.getNifIniciador());
			if (StringUtils.isNotBlank(hTramite.getNifIniciador())) {
				hTramiteFin.setNombreIniciador(hTramite.getNombreIniciador()
						+ StringUtils.defaultIfBlank(" " + hTramite.getApellido1Iniciador(), "")
						+ StringUtils.defaultIfBlank(" " + hTramite.getApellido2Iniciador(), ""));
			}
			// Si existe paso registro, anotamos numero de registro y presentador
			final List<HPaso> lstPasos = findHPasos(pIdSesionTramitacion);
			for (final HPaso p : lstPasos) {
				if (TypePaso.fromString(p.getTipoPaso()) == TypePaso.REGISTRAR) {
					for (final HDocumento hdoc : p.getDocumentos()) {
						if (TypeDocumentoPersistencia
								.fromString(hdoc.getTipo()) == TypeDocumentoPersistencia.REGISTRO) {
							hTramiteFin.setNumeroRegistro(hdoc.getRegistroNumeroRegistro());
							hTramiteFin.setNifPresentador(hdoc.getRegistroNifPresentador());
							hTramiteFin.setNombrePresentador(hdoc.getRegistroNombrePresentador());
							break;
						}
					}
					break;
				}
			}
			// Si no existe paso registro, presentador será el iniciador
			if (hTramiteFin.getNumeroRegistro() == null) {
				hTramiteFin.setNumeroRegistro(hTramiteFin.getNumeroRegistro());
				hTramiteFin.setNifPresentador(hTramiteFin.getNifIniciador());
			}

			entityManager.persist(hTramiteFin);

		}

	}

	@Override
	public void crearPaso(final String pIdSesionTramitacion, final String pIdPaso, final TypePaso pTipoPaso,
			final int orden) {

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
		entityManager.flush();

	}

	@Override
	public void eliminarPaso(final String pIdSesionTramitacion, final String pIdPaso) {

		// Obtenemos paso
		final HPaso paso = getHPaso(pIdSesionTramitacion, pIdPaso);

		// Buscamos ficheros a eliminar
		final List<ReferenciaFichero> ficherosEliminar = new ArrayList<>();

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
	public List<EstadoPersistenciaPasoTramite> obtenerListaPasos(final String pIdSesionTramitacion) {

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
	public Long contadorLimiteTramitacion(final String idTramite, final int version, final int pLimiteIntervalo,
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

	@Override
	public void registraFormularioSoporte(final String idSesionTramitacion,
			final DatosFormularioSoporte datosFormularioSoporte) {

		final HSoporte hSoporte = HSoporte.fromModel(datosFormularioSoporte);

		// Establecemos sesion tramitacion
		final HSesionTramitacion hSesionTramitacion = findHSesionTramitacion(idSesionTramitacion);
		if (hSesionTramitacion == null) {
			throw new RepositoryException("No existeix tràmit: " + idSesionTramitacion);
		}
		hSoporte.setSesionTramitacion(hSesionTramitacion);

		// Persistimos
		entityManager.persist(hSoporte);

	}

	@Override
	public List<TramiteIniciado> obtenerTramitacionesIniciadas(final String nif, final String idTramite,
			final int versionTramite, final String idTramiteCatalogo, final boolean servicioCatalogo) {
		final String hql = "SELECT t FROM HTramite t "
		 		+ " WHERE t.nifIniciador = :nif AND t.autenticacion = '" + TypeAutenticacion.AUTENTICADO + "' "
				+ " AND t.idTramite = :idTramite AND t.versionTramite = :versionTramite "
				+ " AND t.idProcedimientoCP = :idTramiteCatalogo AND t.servicioCP = :servicioCatalogo"
				+ " AND t.cancelado is false AND t.estado <> '" + TypeEstadoTramite.FINALIZADO.toString() + "' "
				+ " AND (t.fechaCaducidad is null OR t.fechaCaducidad > CURRENT_DATE) "
				+ " AND t.purgar is false AND t.purgaPendientePorPagoRealizado is false AND t.purgado is false "
				+ " ORDER BY t.fechaUltimoAcceso DESC ";
		final Query query = entityManager.createQuery(hql);
		query.setParameter("nif", nif);
		query.setParameter("idTramite", idTramite);
		query.setParameter("versionTramite", versionTramite);
		query.setParameter("idTramiteCatalogo", idTramiteCatalogo);
		query.setParameter("servicioCatalogo", servicioCatalogo);
		final List<HTramite> tramitesIniciados = query.getResultList();

		final List<TramiteIniciado> res = new ArrayList<>();
		if (tramitesIniciados != null && !tramitesIniciados.isEmpty()) {
			for (final HTramite t : tramitesIniciados) {
				final TramiteIniciado ti = new TramiteIniciado();
				ti.setIdSesion(t.getSesionTramitacion().getIdSesionTramitacion());
				ti.setIdioma(t.getIdioma());
				ti.setFechaInicio(t.getFechaInicio());
				ti.setFechaUltimoAcceso(t.getFechaUltimoAcceso());
				ti.setFechaCaducidad(t.getFechaCaducidad());
				res.add(ti);
			}
		}
		return res;
	}

	// ------------ FUNCIONES PRIVADAS --------------------------------------

	/**
	 * Busca sesion tramitacion.
	 *
	 * @param idSesionTramitacion
	 *                                id sesion tramitacion
	 * @return sesion tramitacion
	 */
	private HSesionTramitacion findHSesionTramitacion(final String idSesionTramitacion) {
		HSesionTramitacion hSesion = null;
		final String sql = "SELECT t FROM HSesionTramitacion t WHERE t.idSesionTramitacion = :idSesionTramitacion";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idSesionTramitacion", idSesionTramitacion);
		final List<?> results = query.getResultList();
		if (!results.isEmpty()) {
			hSesion = (HSesionTramitacion) results.get(0);
		}
		return hSesion;
	}

	/**
	 * Busca tramite.
	 *
	 * @param idSesionTramitacion
	 *                                id sesion tramitacion
	 * @return sesion tramitacion
	 */
	private HTramite findHTramite(final String idSesionTramitacion) {
		HTramite hTramite = null;
		final String sql = "SELECT t from HTramite t where t.sesionTramitacion.idSesionTramitacion = :idSesionTramitacion";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idSesionTramitacion", idSesionTramitacion);
		final List<?> results = query.getResultList();
		if (!results.isEmpty()) {
			hTramite = (HTramite) results.get(0);
		}
		return hTramite;
	}

	/**
	 * Busca tramite y genera excepcion si no lo encuentra.
	 *
	 * @param pIdSesionTramitacion
	 *                                 id sesion tramitacion
	 * @return sesion tramitacion
	 */
	private HTramite getHTramite(final String pIdSesionTramitacion) {
		final HTramite hTramite = findHTramite(pIdSesionTramitacion);
		if (hTramite == null) {
			throw new RepositoryException("No existeix tràmit: " + pIdSesionTramitacion);
		}
		return hTramite;
	}

	/**
	 * Busca pasos tramite.
	 *
	 * @param idSesionTramitacion
	 *                                id sesion tramitacion
	 * @return pasos tramite
	 */
	@SuppressWarnings("unchecked")
	private List<HPaso> findHPasos(final String idSesionTramitacion) {
		final String sql = "select p from HPaso p inner join p.tramitePersistencia t where t.sesionTramitacion.idSesionTramitacion=:idSesionTramitacion order by p.codigo";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idSesionTramitacion", idSesionTramitacion);
		return query.getResultList();
	}

	/**
	 * Busca paso tramite.
	 *
	 * @param idSesionTramitacion
	 *                                id sesion tramitacion
	 * @return paso tramite
	 */
	@SuppressWarnings("unchecked")
	private HPaso findHPaso(final String idSesionTramitacion, final String idPaso) {
		final String sql = "select p from HPaso p inner join p.tramitePersistencia t where t.sesionTramitacion.idSesionTramitacion=:idSesionTramitacion and p.identificadorPaso = :idPaso order by p.codigo";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idSesionTramitacion", idSesionTramitacion);
		query.setParameter("idPaso", idPaso);
		final List<HPaso> results = query.getResultList();
		HPaso res = null;
		if (!results.isEmpty()) {
			if (results.size() > ConstantesNumero.N1) {
				throw new RepositoryException("S'ha trobat " + results.size() + " amb id passa " + idPaso
						+ " per id sessió tramitació " + idSesionTramitacion);
			}
			res = results.get(0);
		}
		return res;
	}

	/**
	 * Busca paso tramite y genera excepción si no lo encuentra.
	 *
	 * @param idSesionTramitacion
	 *                                id sesion tramitacion
	 * @return paso tramite
	 */
	private HPaso getHPaso(final String idSesionTramitacion, final String idPaso) {
		final HPaso paso = findHPaso(idSesionTramitacion, idPaso);
		if (paso == null) {
			throw new RepositoryException(
					"No es troba passa " + idPaso + " per id sessió tramitació " + idSesionTramitacion);
		}
		return paso;
	}

	/**
	 * Obtiene ficheros de las firmas de un documento.
	 *
	 * @param hdoc
	 *                 HDocumento
	 * @return ficheros de las firmas de un documento.
	 */
	private List<ReferenciaFichero> obtenerFirmasDocumento(final HDocumento hdoc) {
		ReferenciaFichero ref;
		final List<ReferenciaFichero> ficherosDoc = new ArrayList<>();
		for (final HFirma hFirma : hdoc.getFirmas()) {
			if (hFirma.getFirma() != null) {
				ref = ReferenciaFichero.createNewReferenciaFichero(hFirma.getFirma(), hFirma.getFirmaClave());
				ficherosDoc.add(ref);
			}
		}
		return ficherosDoc;
	}

	/**
	 * Obtiene ficheros deun documento.
	 *
	 * @param hdoc
	 *                 HDocumento
	 * @return ficheros de un documento.
	 */
	private List<ReferenciaFichero> obtenerFicherosDocumento(final HDocumento hdoc) {
		ReferenciaFichero ref;
		final List<ReferenciaFichero> ficherosDoc = new ArrayList<>();
		if (hdoc.getFichero() != null) {
			ref = ReferenciaFichero.createNewReferenciaFichero(hdoc.getFichero(), hdoc.getFicheroClave());
			ficherosDoc.add(ref);
		}
		if (hdoc.getFormularioPdf() != null) {
			ref = ReferenciaFichero.createNewReferenciaFichero(hdoc.getFormularioPdf(), hdoc.getFormularioPdfClave());
			ficherosDoc.add(ref);
		}
		if (hdoc.getPagoJustificantePdf() != null) {
			ref = ReferenciaFichero.createNewReferenciaFichero(hdoc.getPagoJustificantePdf(),
					hdoc.getPagoJustificantePdfClave());
			ficherosDoc.add(ref);
		}
		return ficherosDoc;
	}

	/**
	 * Método para Eliminar ficheros de la clase FlujoTramiteDaoImpl.
	 *
	 * @param ficheros
	 *                     Parámetro ficheros
	 */
	private void eliminarFicheros(final List<ReferenciaFichero> ficheros) {
		if (!ficheros.isEmpty()) {
			final StringBuffer sb = new StringBuffer(ConstantesNumero.N40 * ficheros.size());
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

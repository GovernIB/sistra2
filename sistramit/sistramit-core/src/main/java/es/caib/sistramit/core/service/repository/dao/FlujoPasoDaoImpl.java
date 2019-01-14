package es.caib.sistramit.core.service.repository.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.RepositoryException;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.model.flujo.DatosFicheroPersistencia;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.repository.model.HDocumento;
import es.caib.sistramit.core.service.repository.model.HFichero;
import es.caib.sistramit.core.service.repository.model.HPaso;
import es.caib.sistramit.core.service.repository.model.HTramite;

/**
 * Implementación DAO Flujo Paso.
 *
 * @author Indra
 */
@Repository("flujoPasoDao")
public final class FlujoPasoDaoImpl implements FlujoPasoDao {

	/**
	 * Entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public DatosPersistenciaPaso obtenerPasoPersistencia(final String pIdSesionTramitacion, final String pIdPaso) {
		final HPaso hPaso = getHPaso(pIdSesionTramitacion, pIdPaso);

		DatosPersistenciaPaso dpp = null;

		dpp = new DatosPersistenciaPaso();
		dpp.setId(hPaso.getIdentificadorPaso());
		dpp.setTipo(TypePaso.fromString(hPaso.getTipoPaso()));
		dpp.setEstado(TypeEstadoPaso.fromString(hPaso.getEstado()));

		String id = "";
		String idNueva = "";
		int contador = ConstantesNumero.N1;
		for (final HDocumento hDoc : hPaso.getDocumentos()) {
			idNueva = hDoc.getId();
			if (!idNueva.equals(id)) {
				id = idNueva;
				contador = ConstantesNumero.N1;
			}
			final DocumentoPasoPersistencia documento = HDocumento.toModel(hDoc, contador++);
			dpp.getDocumentos().add(documento);
		}

		return dpp;
	}

	@Override
	public void cambiarEstadoPaso(final String pIdSesionTramitacion, final String pIdPaso,
			final TypeEstadoPaso pEstado) {
		final HPaso hPaso = getHPaso(pIdSesionTramitacion, pIdPaso);
		hPaso.setEstado(pEstado.toString());
		entityManager.merge(hPaso);
	}

	@Override
	public void establecerDatosDocumento(final String pIdSesionTramitacion, final String pIdPaso,
			final DocumentoPasoPersistencia pDocPaso) {
		// Recuperamos paso
		final HPaso hPaso = getHPaso(pIdSesionTramitacion, pIdPaso);

		// Comprobamos si existe documento
		HDocumento hdoc = findHDocumento(hPaso.getCodigo(), pDocPaso.getId(), pDocPaso.getInstancia());
		if (hdoc != null) {
			// - Si existe lo modificamos
			HDocumento.mergeFromModel(hdoc, pDocPaso);
			// - Actualizamos BD
			entityManager.merge(hdoc);
		} else {
			// - Si no existe lo creamos
			hdoc = HDocumento.fromModel(pDocPaso);
			hdoc.setPaso(hPaso);
			hdoc.setInstanciaTimeStamp(new Timestamp(System.currentTimeMillis()));
			// - Añadimos a lista documentos paso (tenemos en cuenta el orden de
			// instancia)
			hPaso.addDocumento(hdoc);
			// - Actualizamos BD
			entityManager.persist(hdoc);
		}
	}

	@Override
	public void eliminarDocumento(final String pIdSesionTramitacion, final String pIdPaso, final String pIdDocumento,
			final int pInstancia) {
		final HPaso hPaso = getHPaso(pIdSesionTramitacion, pIdPaso);
		final HDocumento hdoc = findHDocumento(hPaso.getCodigo(), pIdDocumento, pInstancia);
		// Borramos documento
		entityManager.remove(hdoc);
		// Lo quitamos de la lista de documentos
		hPaso.removeDocumento(hdoc);
	}

	@Override
	public ReferenciaFichero insertarFicheroPersistencia(final String pNombre, final byte[] pContenido,
			final String pIdSesionTramitacion) {

		// Buscamos codigo tramite asociado a sesion de tramitacion
		final HTramite hTramite = getHTramite(pIdSesionTramitacion);

		if (StringUtils.isBlank(pNombre)) {
			throw new RepositoryException("No se ha indicado nombre del fichero");
		}

		if (pContenido == null || pContenido.length <= 0) {
			throw new RepositoryException("No se puede insertar fichero vacio");
		}

		final HFichero hFichero = new HFichero();
		hFichero.setNombre(pNombre);
		hFichero.setContenido(pContenido);
		hFichero.setTamanyo(new Long(pContenido.length));
		hFichero.setClave(generarClave());
		hFichero.setFechaCreacion(new Date());
		hFichero.setCodigoTramite(hTramite.getCodigo());

		entityManager.persist(hFichero);

		return new ReferenciaFichero(hFichero.getCodigo(), hFichero.getClave());

	}

	@Override
	public DatosFicheroPersistencia recuperarFicheroPersistencia(final ReferenciaFichero pRefFic) {
		final HFichero hFichero = findHFichero(pRefFic);
		DatosFicheroPersistencia dfp = null;
		if (hFichero != null) {
			dfp = new DatosFicheroPersistencia();
			dfp.setNombre(hFichero.getNombre());
			dfp.setContenido(hFichero.getContenido());
		}
		return dfp;
	}

	@Override
	public void actualizarFicheroPersistencia(final ReferenciaFichero pRefFic, final String pNombre,
			final byte[] pContenido) {
		final HFichero hFichero = getHFichero(pRefFic);
		hFichero.setNombre(pNombre);
		hFichero.setContenido(pContenido);
		hFichero.setTamanyo(new Long(pContenido.length));
		entityManager.merge(hFichero);

	}

	@Override
	public void eliminarFicheroPersistencia(final ReferenciaFichero pRefFic) {
		// No borramos fichero, lo marcamos xa borrar x proceso purga
		final HFichero hFichero = getHFichero(pRefFic);
		hFichero.setBorrar(true);
		entityManager.merge(hFichero);
	}

	@Override
	public DocumentoPasoPersistencia obtenerDocumentoPersistencia(final String pIdSesionTramitacion,
			final String pIdPaso, final String pIdAnexo, final int pInstancia) {
		final HPaso hPaso = getHPaso(pIdSesionTramitacion, pIdPaso);
		final HDocumento hDocumento = getHDocumento(hPaso.getCodigo(), pIdAnexo, pInstancia);
		final DocumentoPasoPersistencia dpp = HDocumento.toModel(hDocumento, pInstancia);
		return dpp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DatosFicheroPersistencia recuperarFicheroPersistenciaNoBorrado(final ReferenciaFichero pRefFic) {

		HFichero hFichero = null;
		DatosFicheroPersistencia dfp = null;

		final String sql = "SELECT f from HFichero f where f.codigo=:codigo and f.clave=:clave and f.borrar=0";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("codigo", pRefFic.getId());
		query.setParameter("clave", pRefFic.getClave());
		final List<HFichero> results = query.getResultList();

		if (!results.isEmpty()) {
			hFichero = results.get(0);

			if (hFichero != null) {
				dfp = new DatosFicheroPersistencia();
				dfp.setNombre(hFichero.getNombre());
				dfp.setContenido(hFichero.getContenido());
			}
		}

		return dfp;
	}

	@Override
	public DocumentoPasoPersistencia obtenerDocumento(final Long pIdDoc) {
		DocumentoPasoPersistencia documento = null;

		final HDocumento hDoc = entityManager.find(HDocumento.class, pIdDoc);

		if (hDoc != null) {
			documento = HDocumento.toModel(hDoc, 0);
		}

		return documento;
	}

	// ------------ FUNCIONES PRIVADAS --------------------------------------

	/**
	 * Busca paso tramite.
	 *
	 * @param idSesionTramitacion
	 *            id sesion tramitacion
	 * @param idPaso
	 *            id paso
	 * @return paso tramite
	 */
	private HPaso findHPaso(final String idSesionTramitacion, final String idPaso) {
		final String sql = "select p from HPaso p inner join p.tramitePersistencia t where t.sesionTramitacion.idSesionTramitacion=:idSesionTramitacion and p.identificadorPaso = :idPaso order by p.codigo";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idSesionTramitacion", idSesionTramitacion);
		query.setParameter("idPaso", idPaso);
		final List<HPaso> results = query.getResultList();
		HPaso res = null;
		if (!results.isEmpty()) {
			if (results.size() > ConstantesNumero.N1) {
				throw new RepositoryException("Se han encontrado " + results.size() + " con id paso " + idPaso
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
	 * @param idPaso
	 *            id paso
	 * @return paso tramite
	 */
	private HPaso getHPaso(final String idSesionTramitacion, final String idPaso) {
		final HPaso paso = findHPaso(idSesionTramitacion, idPaso);
		if (paso == null) {
			throw new RepositoryException(
					"No se encuentra paso " + idPaso + " para id sesion tramitacion " + idSesionTramitacion);
		}
		return paso;
	}

	/**
	 * Busca documento de un paso tramite.
	 *
	 * @param codPaso
	 *            codigo paso
	 * @param idDocumento
	 *            id documento
	 * @param instancia
	 *            instancia
	 * @return documento
	 */
	private HDocumento findHDocumento(final Long codPaso, final String idDocumento, final int instancia) {
		final String sql = "select d from HDocumento d where d.paso.codigo=:codPaso and d.id=:idDocumento order by d.instanciaTimeStamp asc";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("codPaso", codPaso);
		query.setParameter("idDocumento", idDocumento);
		final List<HDocumento> listaHdoc = query.getResultList();
		HDocumento hDoc = null;
		if (listaHdoc.size() >= instancia) {
			hDoc = listaHdoc.get(instancia - ConstantesNumero.N1);
		}
		return hDoc;
	}

	/**
	 * Busca documento de un paso tramite y da error si no lo encuentra.
	 *
	 * @param codPaso
	 *            codigo paso
	 * @param idDocumento
	 *            id documento
	 * @param instancia
	 *            instancia
	 * @return documento
	 */
	private HDocumento getHDocumento(final Long codPaso, final String idDocumento, final int instancia) {
		final HDocumento hDoc = findHDocumento(codPaso, idDocumento, instancia);
		if (hDoc == null) {
			throw new RepositoryException(
					"No se encuentra documento " + idDocumento + " - " + instancia + " en el paso " + codPaso);
		}
		return hDoc;
	}

	/**
	 * Busca tramite.
	 *
	 * @param idSesionTramitacion
	 *            id sesion tramitacion
	 * @return sesion tramitacion
	 */
	private HTramite findHTramite(final String idSesionTramitacion) {
		HTramite hTramite = null;
		final String sql = "SELECT t from HTramite t where t.sesionTramitacion.idSesionTramitacion = :idSesionTramitacion";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idSesionTramitacion", idSesionTramitacion);
		final List<HTramite> results = query.getResultList();
		if (!results.isEmpty()) {
			hTramite = results.get(0);
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
	private HTramite getHTramite(final String pIdSesionTramitacion) {
		final HTramite hTramite = findHTramite(pIdSesionTramitacion);
		if (hTramite == null) {
			throw new RepositoryException("No existe tramite: " + pIdSesionTramitacion);
		}
		return hTramite;
	}

	/**
	 * Busca fichero.
	 *
	 * @param referenciaFichero
	 *            referenciaFichero
	 * @return HFichero
	 */
	private HFichero findHFichero(final ReferenciaFichero referenciaFichero) {
		HFichero hFichero = null;
		final String sql = "SELECT f from HFichero f where f.codigo=:codigo and f.clave=:clave";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("codigo", referenciaFichero.getId());
		query.setParameter("clave", referenciaFichero.getClave());
		final List<HFichero> results = query.getResultList();
		if (!results.isEmpty()) {
			hFichero = results.get(0);
		}
		return hFichero;
	}

	/**
	 * Busca fichero y da error si no encuentra.
	 *
	 * @param referenciaFichero
	 *            referenciaFichero
	 * @return HFichero
	 */
	private HFichero getHFichero(final ReferenciaFichero referenciaFichero) {
		final HFichero hFichero = findHFichero(referenciaFichero);
		if (hFichero == null) {
			throw new RepositoryException(
					"No se encuentra fichero " + referenciaFichero.getId() + " - " + referenciaFichero.getClave());
		}
		return hFichero;
	}

	/**
	 * Método para Generar clave de la clase FlujoPasoDaoImpl.
	 *
	 * @return el string
	 */
	private String generarClave() {
		final Random r = new Random();
		final StringBuffer clave = new StringBuffer("");
		final int ca = Character.getNumericValue('a');
		for (int i = 0; i < ConstantesNumero.N10; i++) {
			clave.append(Character.forDigit(ca + r.nextInt(ConstantesNumero.N26), Character.MAX_RADIX));
		}
		return clave.toString();
	}

}

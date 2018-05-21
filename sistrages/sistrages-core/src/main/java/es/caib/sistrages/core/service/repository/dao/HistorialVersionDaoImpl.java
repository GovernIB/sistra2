package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.HistorialVersion;
import es.caib.sistrages.core.api.model.types.TypeAccion;
import es.caib.sistrages.core.service.repository.model.JHistorialVersion;
import es.caib.sistrages.core.service.repository.model.JVersionTramite;

/**
 * La clase TramiteDaoImpl.
 */
@Repository("historialVersionDao")
public class HistorialVersionDaoImpl implements HistorialVersionDao {

	/** Literal. no existe el tramite. **/
	private static final String STRING_NO_EXISTE_HISTORIAL_VERSION = "No existe el tramite version: ";
	/** Literal. falta el tramite. **/
	private static final String STRING_FALTA_HISTORIAL_VERSION = "Falta el historial version";

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de TramiteDaoImpl.
	 */
	public HistorialVersionDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.HistorialVersionDao#getById(
	 * java.lang. Long)
	 */
	@Override
	public HistorialVersion getById(final Long idHistorialVersion) {
		HistorialVersion historialVersion = null;

		if (idHistorialVersion == null) {
			throw new FaltanDatosException("Falta el identificador");
		}

		final JHistorialVersion jHistorialVersion = entityManager.find(JHistorialVersion.class, idHistorialVersion);

		if (jHistorialVersion == null) {
			throw new NoExisteDato(STRING_NO_EXISTE_HISTORIAL_VERSION + idHistorialVersion);
		} else {
			historialVersion = jHistorialVersion.toModel();
		}

		return historialVersion;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.HistorialVersionDao#
	 * getAllByFiltro(java. lang.Long, java.lang.String)
	 */
	@Override
	public List<HistorialVersion> getAllByFiltro(final Long idTramiteVersion, final String pFiltro) {
		return listarHistoriales(idTramiteVersion, pFiltro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.HistorialVersionDao#getAll(java
	 * .lang. Long)
	 */
	@Override
	public List<HistorialVersion> getAll(final Long idTramiteVersion) {
		return listarHistoriales(idTramiteVersion, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.HistorialVersionDao#add(java.
	 * lang.Long, es.caib.sistrages.core.api.model.Tramite)
	 */
	@Override
	public void add(final Long idTramiteVersion, final String username, final TypeAccion accion,
			final String detalleCambio) {
		if (idTramiteVersion == null) {
			throw new FaltanDatosException(STRING_FALTA_HISTORIAL_VERSION);
		}

		final JVersionTramite jTramiteVersion = entityManager.find(JVersionTramite.class, idTramiteVersion);
		final JHistorialVersion historial = new JHistorialVersion();
		historial.setFecha(new java.util.Date());
		historial.setUsuario(username);
		historial.setTipoAccion(accion.toString());
		if (detalleCambio == null || detalleCambio.isEmpty()) {
			historial.setDetalleCambio(" ");
		} else {
			historial.setDetalleCambio(detalleCambio);
		}
		historial.setVersionTramite(jTramiteVersion);
		historial.setRelease(jTramiteVersion.getRelease());
		entityManager.persist(historial);
	}

	/**
	 * Listar historiales.
	 *
	 * @param idTramiteVersion
	 *            Id area
	 * @param pFiltro
	 *            the filtro
	 * @return lista de tramites
	 */
	@SuppressWarnings("unchecked")
	private List<HistorialVersion> listarHistoriales(final Long idTramiteVersion, final String pFiltro) {
		final List<HistorialVersion> resultado = new ArrayList<>();

		String sql = "Select t From JHistorialVersion t where t.versionTramite.id = :idTramiteVersion";

		if (StringUtils.isNotBlank(pFiltro)) {
			sql += " AND upper(t.detalleCambio) like :filtro";
		}
		sql += " ORDER BY t.codigo desc";

		final Query query = entityManager.createQuery(sql);

		query.setParameter("idTramiteVersion", idTramiteVersion);
		if (StringUtils.isNotBlank(pFiltro)) {
			query.setParameter("filtro", "%" + pFiltro.toUpperCase() + "%");
		}

		final List<JHistorialVersion> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final Iterator<JHistorialVersion> iterator = results.iterator(); iterator.hasNext();) {
				final JHistorialVersion jHistorialVersion = iterator.next();
				resultado.add(jHistorialVersion.toModel());
			}
		}

		return resultado;
	}

}

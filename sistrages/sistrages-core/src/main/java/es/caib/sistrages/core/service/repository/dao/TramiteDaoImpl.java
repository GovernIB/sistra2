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
import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramiteTipo;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeFlujo;
import es.caib.sistrages.core.service.repository.model.JArea;
import es.caib.sistrages.core.service.repository.model.JDominio;
import es.caib.sistrages.core.service.repository.model.JPasoTramitacion;
import es.caib.sistrages.core.service.repository.model.JTipoPasoTramitacion;
import es.caib.sistrages.core.service.repository.model.JTramite;
import es.caib.sistrages.core.service.repository.model.JVersionTramite;

/**
 * La clase TramiteDaoImpl.
 */
@Repository("tramiteDao")
public class TramiteDaoImpl implements TramiteDao {

	/** Literal. no existe el tramite. **/
	private static final String STRING_NO_EXISTE_TRAMITE = "No existe el tramite: ";
	/** Literal. falta el tramite. **/
	private static final String STRING_FALTA_TRAMITE = "Falta el tramite";

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de TramiteDaoImpl.
	 */
	public TramiteDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.TramiteDao#getById(java.lang.
	 * Long)
	 */
	@Override
	public Tramite getById(final Long id) {
		Tramite tramite = null;

		if (id == null) {
			throw new FaltanDatosException("Falta el identificador");
		}

		final JTramite jTramite = entityManager.find(JTramite.class, id);

		if (jTramite == null) {
			throw new NoExisteDato(STRING_NO_EXISTE_TRAMITE + id);
		} else {
			tramite = jTramite.toModel();
		}

		return tramite;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.TramiteDao#getAllByFiltro(java.
	 * lang.Long, java.lang.String)
	 */
	@Override
	public List<Tramite> getAllByFiltro(final Long idArea, final String pFiltro) {
		return listarTramites(idArea, pFiltro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.TramiteDao#getAll(java.lang.
	 * Long)
	 */
	@Override
	public List<Tramite> getAll(final Long idArea) {
		return listarTramites(idArea, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.TramiteDao#add(java.lang.Long,
	 * es.caib.sistrages.core.api.model.Tramite)
	 */
	@Override
	public void add(final Long idArea, final Tramite pTramite) {
		if (idArea == null) {
			throw new FaltanDatosException("Falta el area");
		}

		if (pTramite == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}

		final JArea jArea = entityManager.find(JArea.class, idArea);
		final JTramite jTramite = JTramite.fromModel(pTramite);
		jTramite.setArea(jArea);
		entityManager.persist(jTramite);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.TramiteDao#remove(java.lang.
	 * Long)
	 */
	@Override
	public void remove(final Long id) {
		if (id == null) {
			throw new FaltanDatosException("Falta el identificador");
		}

		final JTramite jTramite = entityManager.find(JTramite.class, id);
		if (jTramite == null) {
			throw new NoExisteDato(STRING_NO_EXISTE_TRAMITE + id);
		}
		entityManager.remove(jTramite);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.TramiteDao#update(es.caib.
	 * sistrages.core.api.model.Tramite)
	 */
	@Override
	public void update(final Tramite pTramite) {
		if (pTramite == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}
		final JTramite jTramite = entityManager.find(JTramite.class, pTramite.getId());
		if (jTramite == null) {
			throw new NoExisteDato(STRING_NO_EXISTE_TRAMITE + pTramite.getId());
		}

		// Mergeamos datos
		final JTramite jTramiteNew = JTramite.fromModel(pTramite);
		jTramiteNew.setCodigo(jTramite.getCodigo());
		jTramiteNew.setArea(jTramite.getArea());
		jTramiteNew.setVersionTramite(jTramite.getVersionTramite());
		entityManager.merge(jTramiteNew);
	}

	/**
	 * Listar tramite.
	 *
	 * @param idArea
	 *            Id area
	 * @param pFiltro
	 *            the filtro
	 * @return lista de tramites
	 */
	@SuppressWarnings("unchecked")
	private List<Tramite> listarTramites(final Long idArea, final String pFiltro) {
		final List<Tramite> resultado = new ArrayList<>();

		String sql = "Select t From JTramite t where t.area.codigo = :idArea";

		if (StringUtils.isNotBlank(pFiltro)) {
			sql += " AND (upper(t.descripcion) like :filtro OR upper(t.identificador) like :filtro)";
		}
		sql += " ORDER BY t.codigo";

		final Query query = entityManager.createQuery(sql);

		query.setParameter("idArea", idArea);
		if (StringUtils.isNotBlank(pFiltro)) {
			query.setParameter("filtro", "%" + pFiltro.toUpperCase() + "%");
		}

		final List<JTramite> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final Iterator<JTramite> iterator = results.iterator(); iterator.hasNext();) {
				final JTramite jTramite = iterator.next();
				resultado.add(jTramite.toModel());
			}
		}

		return resultado;
	}

	@Override
	public List<TramiteVersion> getTramitesVersion(final Long idTramite, final String filtro) {
		final List<TramiteVersion> resultado = new ArrayList<>();

		String sql = "Select t From JVersionTramite t where t.tramite.codigo = :idTramite ";
		if (filtro != null && !filtro.isEmpty()) {
			sql += " and lower(t.usuarioDatosBloqueo) like '%" + filtro.toLowerCase() + "%' ";
		}
		sql += "order by t.numeroVersion desc";

		final Query query = entityManager.createQuery(sql);
		query.setParameter("idTramite", idTramite);

		@SuppressWarnings("unchecked")
		final List<JVersionTramite> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final Iterator<JVersionTramite> iterator = results.iterator(); iterator.hasNext();) {
				final JVersionTramite jTramiteVersion = iterator.next();
				final TramiteVersion tramiteVersion = new TramiteVersion();
				tramiteVersion.setCodigo(jTramiteVersion.getCodigo());
				tramiteVersion.setNumeroVersion(jTramiteVersion.getNumeroVersion());
				if (jTramiteVersion.getBloqueada()) {
					tramiteVersion.setBloqueada(1);
				} else {
					tramiteVersion.setBloqueada(0);
				}
				tramiteVersion.setCodigoUsuarioBloqueo(jTramiteVersion.getUsuarioIdBloqueo());
				tramiteVersion.setDatosUsuarioBloqueo(jTramiteVersion.getUsuarioDatosBloqueo());
				tramiteVersion.setActiva(jTramiteVersion.isActiva());
				tramiteVersion.setTipoFlujo(TypeFlujo.fromString(jTramiteVersion.getTipoflujo()));
				tramiteVersion.setRelease(jTramiteVersion.getRelease());

				resultado.add(tramiteVersion);
			}
		}

		return resultado;
	}

	@Override
	public void addTramiteVersion(final TramiteVersion tramiteVersion, final String idTramite) {

		if (idTramite == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}

		final JTramite jTramite = entityManager.find(JTramite.class, Long.valueOf(idTramite));
		final JVersionTramite jTramiteVersion = JVersionTramite.fromModel(tramiteVersion);
		jTramiteVersion.setTramite(jTramite);
		entityManager.persist(jTramiteVersion);
		entityManager.flush();

		if (tramiteVersion.getListaPasos() != null) {
			for (final TramitePaso paso : tramiteVersion.getListaPasos()) {
				final JPasoTramitacion jpaso = JPasoTramitacion.fromModel(paso);
				jpaso.setVersionTramite(jTramiteVersion);
				entityManager.persist(jpaso);
			}
		}

	}

	@Override
	public void updateTramiteVersion(final TramiteVersion tramiteVersion) {
		final JVersionTramite jTramiteVersionOld = entityManager.find(JVersionTramite.class,
				tramiteVersion.getCodigo());
		final JVersionTramite jTramiteVersion = JVersionTramite.fromModel(tramiteVersion);
		jTramiteVersion.setTramite(jTramiteVersionOld.getTramite());
		jTramiteVersion.setHistorialVersion(jTramiteVersion.getHistorialVersion());
		entityManager.merge(jTramiteVersion);
	}

	@Override
	public void removeTramiteVersion(final Long idTramiteVersion) {

		// Paso 0. Obtenemos la versión del trámite.
		final JVersionTramite jTramiteVersion = entityManager.find(JVersionTramite.class, idTramiteVersion);

		// Paso1. Obtenemos los pasos y los borramos
		final String sql = "Select t From JPasoTramitacion t where t.versionTramite.id = :idTramiteVersion";

		final Query query = entityManager.createQuery(sql);
		query.setParameter("idTramiteVersion", idTramiteVersion);

		@SuppressWarnings("unchecked")
		final List<JPasoTramitacion> pasos = query.getResultList();
		for (final JPasoTramitacion paso : pasos) {
			entityManager.remove(paso);
		}

		// Paso 2. Buscamos los dominios que tengan la versión trámite y borramos la
		// relacion
		final String sqlDominio = "Select d From JDominio d join d.versionesTramite t where t.id = :idTramiteVersion";

		final Query queryDominio = entityManager.createQuery(sqlDominio);
		queryDominio.setParameter("idTramiteVersion", idTramiteVersion);

		@SuppressWarnings("unchecked")
		final List<JDominio> dominios = queryDominio.getResultList();
		for (final JDominio dominio : dominios) {
			dominio.getVersionesTramite().remove(jTramiteVersion);
			entityManager.merge(dominio);
		}

		// Paso 3. Borramos el tramite versión
		entityManager.remove(jTramiteVersion);

	}

	@Override
	public TramiteVersion getTramiteVersion(final Long idTramiteVersion) {
		final JVersionTramite jTramiteVersion = entityManager.find(JVersionTramite.class, idTramiteVersion);
		return jTramiteVersion.toModel();
	}

	@Override
	public List<TramiteTipo> listTipoTramitePaso() {
		final String sql = "Select t From JTipoPasoTramitacion t order by t.orden asc";

		final Query query = entityManager.createQuery(sql);

		@SuppressWarnings("unchecked")
		final List<JTipoPasoTramitacion> results = query.getResultList();

		final List<TramiteTipo> resultado = new ArrayList<>();
		if (results != null && !results.isEmpty()) {
			for (final Iterator<JTipoPasoTramitacion> iterator = results.iterator(); iterator.hasNext();) {
				final JTipoPasoTramitacion jTramiteVersion = iterator.next();
				resultado.add(jTramiteVersion.toModel());
			}
		}

		return resultado;
	}

	@Override
	public Area getAreaTramite(final Long idTramite) {
		Area area = null;
		final JTramite jTramite = entityManager.find(JTramite.class, Long.valueOf(idTramite));
		if (jTramite != null) {
			area = jTramite.getArea().toModel();
		}
		return area;
	}

	@Override
	public void changeAreaTramite(final Long idArea, final Long idTramite) {
		final JArea jArea = entityManager.find(JArea.class, idArea);
		final JTramite jTramite = entityManager.find(JTramite.class, idTramite);
		jTramite.setArea(jArea);
		entityManager.merge(jTramite);
	}

	@Override
	public List<Dominio> getTramiteDominios(final Long idTramiteVersion) {
		final String sql = "Select d From JDominio d JOIN d.versionesTramite t where t.id = :idTramiteVersion order by d.identificador asc";

		final Query query = entityManager.createQuery(sql);
		query.setParameter("idTramiteVersion", idTramiteVersion);

		@SuppressWarnings("unchecked")
		final List<JDominio> results = query.getResultList();

		final List<Dominio> resultado = new ArrayList<>();
		if (results != null && !results.isEmpty()) {
			for (final Iterator<JDominio> iterator = results.iterator(); iterator.hasNext();) {
				final JDominio jdominio = iterator.next();
				resultado.add(jdominio.toModel());
			}
		}

		return resultado;
	}

}

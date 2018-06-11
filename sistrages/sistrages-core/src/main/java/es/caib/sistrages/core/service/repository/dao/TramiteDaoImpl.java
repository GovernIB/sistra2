package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.exception.TramiteVersionException;
import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramiteTipo;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeFlujo;
import es.caib.sistrages.core.service.repository.model.JAnexoTramite;
import es.caib.sistrages.core.service.repository.model.JArea;
import es.caib.sistrages.core.service.repository.model.JDominio;
import es.caib.sistrages.core.service.repository.model.JFichero;
import es.caib.sistrages.core.service.repository.model.JFormularioTramite;
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
	/** Id tramite version. **/
	private static final String STRING_ID_TRAMITE_VERSION = "idTramiteVersion";

	@Autowired
	private FicheroExternoDao ficheroExternoDao;

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
		final JTramite jTramite = entityManager.find(JTramite.class, pTramite.getCodigo());
		if (jTramite == null) {
			throw new NoExisteDato(STRING_NO_EXISTE_TRAMITE + pTramite.getCodigo());
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
				tramiteVersion.setBloqueada(jTramiteVersion.getBloqueada());
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
	public Long addTramiteVersion(final TramiteVersion tramiteVersion, final String idTramite) {

		if (idTramite == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}

		final JTramite jTramite = entityManager.find(JTramite.class, Long.valueOf(idTramite));
		if (jTramite == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}

		final StringBuilder sqlCount = new StringBuilder(
				"Select count(*) From JVersionTramite v where  v.tramite.id = ");
		sqlCount.append(idTramite);
		sqlCount.append(" and v.numeroVersion = ");
		sqlCount.append(tramiteVersion.getRelease());
		final Query query = entityManager.createQuery(sqlCount.toString());
		final Long cuantos = (Long) query.getSingleResult();
		if (cuantos > 0) {
			throw new TramiteVersionException("Num. versio repetit");
		}

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

		return jTramiteVersion.getCodigo();
	}

	@Override
	public Long clonarTramiteVersion(final Long idTramiteVersion) {

		// Paso 0. Preparamos los datos.
		if (idTramiteVersion == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}

		// Paso 1. Clonamos el tramiteVersion
		final JVersionTramite jVersionTramiteOriginal = entityManager.find(JVersionTramite.class, idTramiteVersion);
		if (jVersionTramiteOriginal == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}
		final int numVersion = this.getTramiteNumVersionMaximo(jVersionTramiteOriginal.getTramite().getCodigo()) + 1;
		final JVersionTramite jVersionTramite = JVersionTramite.clonar(jVersionTramiteOriginal, numVersion);

		entityManager.persist(jVersionTramite);
		entityManager.flush();

		// Paso 2. Buscamos las relaciones de dominios y las añadimos
		final String sqlDominios = "Select d From JDominio d JOIN d.versionesTramite t where t.id = :idTramiteVersion order by d.identificador asc";

		final Query queryDominios = entityManager.createQuery(sqlDominios);
		queryDominios.setParameter(STRING_ID_TRAMITE_VERSION, idTramiteVersion);

		@SuppressWarnings("unchecked")
		final List<JDominio> jdominios = queryDominios.getResultList();
		for (final JDominio jdominio : jdominios) {
			jdominio.getVersionesTramite().add(jVersionTramite);
			entityManager.merge(jdominio);
		}

		// Paso 3. Buscamos los pasos y los clonamos y añadimos.
		final String sqlPasos = "Select p From JPasoTramitacion p where p.versionTramite.id = :idTramiteVersion order by p.orden asc";
		final Long idEntidad = jVersionTramite.getTramite().getArea().getEntidad().getCodigo();
		final Query queryPasos = entityManager.createQuery(sqlPasos);
		queryPasos.setParameter(STRING_ID_TRAMITE_VERSION, idTramiteVersion);
		@SuppressWarnings("unchecked")
		final List<JPasoTramitacion> jpasos = queryPasos.getResultList();
		for (final JPasoTramitacion origPaso : jpasos) {
			final JPasoTramitacion jpaso = JPasoTramitacion.clonar(origPaso, jVersionTramite);
			entityManager.persist(jpaso);
			checkFicherosPaso(jpaso, origPaso, idEntidad);
		}

		return jVersionTramite.getCodigo();
	}

	/**
	 * Este método privado se encarga de clonar un fichero hacia ficheroexterno.
	 *
	 * @param jpaso
	 * @param origPaso
	 * @param idEntidad
	 */
	private void checkFicherosPaso(final JPasoTramitacion jpaso, final JPasoTramitacion origPaso,
			final Long idEntidad) {

		if (origPaso.getPasoAnexar() != null) {
			// Buscamos en el original, si tiene un fichero y lo guardamos en ficheros
			// externos.
			for (final JAnexoTramite anexo : origPaso.getPasoAnexar().getAnexosTramite()) {
				if (anexo.getFicheroPlantilla() != null) {
					final byte[] content = ficheroExternoDao.getContentById(anexo.getFicheroPlantilla().getCodigo())
							.getContent();
					final JFichero fichero = getAnexoTramite(jpaso, anexo.getCodigo());
					if (fichero != null) {
						ficheroExternoDao.guardarFichero(idEntidad, fichero.toModel(), content);
					}
				}
			}
		}

		if (origPaso.getPasoInformacion() != null && origPaso.getPasoInformacion().getFicheroPlantilla() != null) {
			// Pendiente de hacerlo
		}

		// Los formularios.
		if (origPaso.getPasoCaptura() != null && origPaso.getPasoCaptura().getFormularioTramite() != null
				&& origPaso.getPasoCaptura().getFormularioTramite().getFormulario() != null) {
			// Pendiente de hacerlo
		}

		// Los formularios.
		if (origPaso.getPasoRellenar() != null && origPaso.getPasoRellenar().getFormulariosTramite() != null) {
			for (final JFormularioTramite formulario : origPaso.getPasoRellenar().getFormulariosTramite()) {
				// Pendiente de hacerlo
			}
		}
	}

	/**
	 * Devuelve el anexo.
	 *
	 * @param jpaso
	 * @param ficheroPlantilla
	 * @return
	 */
	private JFichero getAnexoTramite(final JPasoTramitacion jpaso, final Long codigo) {
		JFichero jfichero = null;
		for (final JAnexoTramite anexo : jpaso.getPasoAnexar().getAnexosTramite()) {
			if (anexo.getFicheroPlantilla() != null) {
				if (anexo.getCodigoClonado().compareTo(codigo) == 0) {
					jfichero = anexo.getFicheroPlantilla();
				}
			}
		}
		return jfichero;
	}

	@Override
	public void updateTramiteVersion(final TramiteVersion tramiteVersion) {
		final JVersionTramite jTramiteVersionOld = entityManager.find(JVersionTramite.class,
				tramiteVersion.getCodigo());
		final JVersionTramite jTramiteVersion = JVersionTramite.fromModel(tramiteVersion);
		jTramiteVersion.setTramite(jTramiteVersionOld.getTramite());
		entityManager.merge(jTramiteVersion);
	}

	@Override
	public void removeTramiteVersion(final Long idTramiteVersion) {

		// Paso 0. Obtenemos la versión del trámite.
		final JVersionTramite jTramiteVersion = entityManager.find(JVersionTramite.class, idTramiteVersion);

		// Paso1. Obtenemos los pasos y los borramos
		final String sql = "Select t From JPasoTramitacion t where t.versionTramite.id = :idTramiteVersion";

		final Query query = entityManager.createQuery(sql);
		query.setParameter(STRING_ID_TRAMITE_VERSION, idTramiteVersion);

		@SuppressWarnings("unchecked")
		final List<JPasoTramitacion> pasos = query.getResultList();
		for (final JPasoTramitacion paso : pasos) {
			entityManager.remove(paso);
		}

		// Paso 2. Buscamos los dominios que tengan la versión trámite y borramos la
		// relacion
		final String sqlDominio = "Select d From JDominio d join d.versionesTramite t where t.id = :idTramiteVersion";

		final Query queryDominio = entityManager.createQuery(sqlDominio);
		queryDominio.setParameter(STRING_ID_TRAMITE_VERSION, idTramiteVersion);

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
		final JTramite jTramite = entityManager.find(JTramite.class, idTramite);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getTramiteDominiosId(final Long idTramiteVersion) {
		final String sql = "Select d.codigo From JDominio d JOIN d.versionesTramite t where t.id = :idTramiteVersion order by d.identificador asc";

		final Query query = entityManager.createQuery(sql);
		query.setParameter(STRING_ID_TRAMITE_VERSION, idTramiteVersion);

		return query.getResultList();

	}

	@Override
	public void bloquearTramiteVersion(final Long idTramiteVersion, final String username) {
		final JVersionTramite jTramiteVersion = entityManager.find(JVersionTramite.class, idTramiteVersion);
		if (!jTramiteVersion.getBloqueada()) {
			jTramiteVersion.setBloqueada(true);
			jTramiteVersion.setUsuarioDatosBloqueo(username);
			entityManager.merge(jTramiteVersion);
		}

	}

	@Override
	public void desbloquearTramiteVersion(final Long idTramiteVersion) {
		final JVersionTramite jTramiteVersion = entityManager.find(JVersionTramite.class, idTramiteVersion);
		if (jTramiteVersion.getBloqueada()) {
			jTramiteVersion.setBloqueada(false);
			jTramiteVersion.setUsuarioDatosBloqueo("");
			jTramiteVersion.setRelease(jTramiteVersion.getRelease() + 1);
			entityManager.merge(jTramiteVersion);
		}
	}

	@Override
	public boolean tieneTramiteVersion(final Long idTramite) {
		final StringBuilder sqlCount = new StringBuilder(
				"Select count(*) From JVersionTramite v where v.tramite.id = ");
		sqlCount.append(idTramite);
		final Query query = entityManager.createQuery(sqlCount.toString());
		final Long cuantos = (Long) query.getSingleResult();
		boolean tiene;
		if (cuantos == 0) {
			tiene = false;
		} else {
			tiene = true;
		}
		return tiene;
	}

	@Override
	public boolean tieneTramiteNumVersionRepetido(final Long idTramite, final int release) {
		final StringBuilder sqlCount = new StringBuilder(
				"Select count(*) From JVersionTramite v where v.tramite.id = ");
		sqlCount.append(idTramite);
		sqlCount.append(" and v.numeroVersion = ");
		sqlCount.append(release);
		final Query query = entityManager.createQuery(sqlCount.toString());
		final Long cuantos = (Long) query.getSingleResult();

		boolean tiene;
		if (cuantos == 0) {
			tiene = false;
		} else {
			tiene = true;
		}
		return tiene;
	}

	@Override
	public int getTramiteNumVersionMaximo(final Long idTramite) {

		// Primero comprobamos si tiene, si no tiene, se devuelve el valor por defecto
		// (0) sino el máximo valor +1
		final StringBuilder sqlCount = new StringBuilder(
				"Select count(v) From JVersionTramite v where v.tramite.id = ");
		sqlCount.append(idTramite);
		final Query query = entityManager.createQuery(sqlCount.toString());
		final Long cuantos = (Long) query.getSingleResult();

		int realeaseMax;
		if (cuantos == 0) {
			realeaseMax = 0;
		} else {

			final StringBuilder sqlMax = new StringBuilder(
					"Select max(v.numeroVersion) From JVersionTramite v where v.tramite.id = ");
			sqlMax.append(idTramite);
			final Query queryMax = entityManager.createQuery(sqlMax.toString());
			realeaseMax = (Integer) queryMax.getSingleResult();
		}
		return realeaseMax;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> listTramiteVersionActiva(final Long idArea) {

		final String sql = "Select distinct tv.tramite.codigo From JVersionTramite tv where tv.tramite.area.codigo = :idArea and tv.activa = true order by tv.tramite.codigo ASC";
		final Query query = entityManager.createQuery(sql);

		query.setParameter("idArea", idArea);
		return query.getResultList();

	}

	@Override
	public Tramite getTramiteByIdentificador(final String identificador, final Long idArea) {

		final String sql = "Select t From JTramite t where t.area.codigo = :idArea and t.identificador = :identificador";
		final Query query = entityManager.createQuery(sql);

		query.setParameter("idArea", idArea);
		query.setParameter("identificador", identificador);

		final JTramite jtramite = (JTramite) query.getSingleResult();

		if (jtramite == null) {
			return null;
		} else {
			return jtramite.toModel();
		}
	}

	@Override
	public TramiteVersion getTramiteVersionByNumVersion(final int numeroVersion, final Long idTramite) {
		final String sql = "Select t From JVersionTramite t where t.tramite.codigo = :idTramite and t.numeroVersion = :numeroVersion";
		final Query query = entityManager.createQuery(sql);

		query.setParameter("idTramite", idTramite);
		query.setParameter("numeroVersion", numeroVersion);

		final JVersionTramite jVersionTramite = (JVersionTramite) query.getSingleResult();

		if (jVersionTramite == null) {
			return null;
		} else {
			return jVersionTramite.toModel();
		}
	}

	@Override
	public TramiteVersion getTramiteVersionMaxNumVersion(final Long idTramite) {

		final int numVersion = this.getTramiteNumVersionMaximo(idTramite);
		if (numVersion == 0) {
			return null;
		} else {
			return this.getTramiteVersionByNumVersion(numVersion, idTramite);
		}
	}

}

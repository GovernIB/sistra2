package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.service.repository.model.JArea;
import es.caib.sistrages.core.service.repository.model.JConfiguracionAutenticacion;
import es.caib.sistrages.core.service.repository.model.JDominio;
import es.caib.sistrages.core.service.repository.model.JGestorExternoFormularios;

/**
 * La clase ConfiguracionAutenticacionDaoImpl.
 */
@Repository("ConfiguracionAutenticacionDao")
public class ConfiguracionAutenticacionDaoImpl implements ConfiguracionAutenticacionDao {

	private static final String FALTA_IDENTIFICADOR = "Falta el identificador";
	private static final String NO_EXISTE_EL_CONF_AUT = "No existe la configuracion autenticacion: ";
	private static final String FALTA_AREA = "Falta el are";
	private static final String FALTA_CONF_AUTENTICACION = "Falta la configuracion autenticacion";
	private static final String NO_EXISTE_EL_AREA = "No existe el area: ";

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de ConfiguracionAutenticacionDaoImpl.
	 */
	public ConfiguracionAutenticacionDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.ConfiguracionAutenticacionDao#getById(
	 * java. lang.Long)
	 */
	@Override
	public ConfiguracionAutenticacion getById(final Long pId) {
		ConfiguracionAutenticacion confAutenticacion = null;

		if (pId == null) {
			throw new FaltanDatosException(FALTA_IDENTIFICADOR);
		}

		final JConfiguracionAutenticacion jConfiguracionAutenticacion = entityManager.find(JConfiguracionAutenticacion.class,
				pId);

		if (jConfiguracionAutenticacion == null) {
			throw new NoExisteDato(NO_EXISTE_EL_CONF_AUT + pId);
		} else {
			confAutenticacion = jConfiguracionAutenticacion.toModel();
		}

		return confAutenticacion;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.ConfiguracionAutenticacionDao#add(long,
	 * es.caib.sistrages.core.api.model.ConfiguracionAutenticacion)
	 */
	@Override
	public Long add(final Long pIdArea, final ConfiguracionAutenticacion pConfiguracionAutenticacion) {
		if (pConfiguracionAutenticacion == null) {
			throw new FaltanDatosException(FALTA_CONF_AUTENTICACION);
		}

		if (pIdArea == null) {
			throw new FaltanDatosException(FALTA_AREA);
		}

		final JArea jArea = entityManager.find(JArea.class, pIdArea);
		if (jArea == null) {
			throw new FaltanDatosException(NO_EXISTE_EL_AREA + pIdArea);
		}

		final JConfiguracionAutenticacion jConfiguracionAutenticacion = JConfiguracionAutenticacion
				.fromModel(pConfiguracionAutenticacion);
		jConfiguracionAutenticacion.setArea(jArea);
		entityManager.persist(jConfiguracionAutenticacion);
		return jConfiguracionAutenticacion.getCodigo();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.ConfiguracionAutenticacionDao#remove(
	 * java. lang.Long)
	 */
	@Override
	public void remove(final Long pId) {

		String sqlGestor = "select a from JGestorExternoFormularios as a where a.configuracionAutenticacion.codigo = :codigoAut";
		final Query queryGestor = entityManager.createQuery(sqlGestor);

		queryGestor.setParameter("codigoAut", pId);
		final List<JGestorExternoFormularios> gestores = queryGestor.getResultList();
		if (gestores != null && !gestores.isEmpty()) {
			for(JGestorExternoFormularios gestor : gestores) {
				gestor.setConfiguracionAutenticacion(null);
				entityManager.merge(gestor);
			}
		}

		String sqlDom = "select a from JDominio as a where a.configuracionAutenticacion.codigo = :codigoAut";
		final Query queryDom = entityManager.createQuery(sqlDom);

		queryDom.setParameter("codigoAut", pId);
		final List<JDominio> dominios = queryDom.getResultList();
		if (dominios != null && !dominios.isEmpty()) {
			for(JDominio dominio : dominios) {
				dominio.setConfiguracionAutenticacion(null);
				entityManager.merge(dominio);
			}
		}


		final JConfiguracionAutenticacion jConfiguracionAutenticacion = entityManager.find(JConfiguracionAutenticacion.class,
				pId);
		if (jConfiguracionAutenticacion == null) {
			throw new NoExisteDato(NO_EXISTE_EL_CONF_AUT + pId);
		}
		entityManager.remove(jConfiguracionAutenticacion);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.ConfiguracionAutenticacionDao#update(es.
	 * caib. sistrages.core.api.model.ConfiguracionAutenticacion)
	 */
	@Override
	public void update(final ConfiguracionAutenticacion pConfiguracionAutenticacion) {
		if (pConfiguracionAutenticacion == null) {
			throw new FaltanDatosException(FALTA_CONF_AUTENTICACION);
		}

		final JConfiguracionAutenticacion jConfiguracionAutenticacion = entityManager.find(JConfiguracionAutenticacion.class,
				pConfiguracionAutenticacion.getCodigo());
		if (jConfiguracionAutenticacion == null) {
			throw new NoExisteDato(NO_EXISTE_EL_CONF_AUT + pConfiguracionAutenticacion.getCodigo());
		}
		// Mergeamos datos
		jConfiguracionAutenticacion.merge(pConfiguracionAutenticacion);
		entityManager.merge(jConfiguracionAutenticacion);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.ConfiguracionAutenticacionDao#
	 * getAllByFiltro( java.lang.Long,
	 * es.caib.sistrages.core.api.model.types.TypeIdioma, java.lang.String)
	 */
	@Override
	public List<ConfiguracionAutenticacion> getAllByFiltro(final Long pIdArea, final TypeIdioma pIdioma,
			final String pFiltro) {
		if (pIdArea == null) {
			throw new FaltanDatosException(FALTA_AREA);
		}
		return listarConfiguracionAutenticacion(pIdArea, pIdioma, pFiltro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.ConfiguracionAutenticacionDao#getAll(
	 * java. lang.Long)
	 */
	@Override
	public List<ConfiguracionAutenticacion> getAll(final Long pIdArea) {
		if (pIdArea == null) {
			throw new FaltanDatosException(FALTA_AREA);
		}
		return listarConfiguracionAutenticacion(pIdArea, null, null);
	}

	/**
	 * Listar avisos.
	 *
	 * @param pIdArea idArea
	 * @param pIdioma    idioma
	 * @param pFiltro    filtro
	 * @return Listado de avisos
	 */
	@SuppressWarnings("unchecked")
	private List<ConfiguracionAutenticacion> listarConfiguracionAutenticacion(final Long pIdArea, final TypeIdioma pIdioma,
			final String pFiltro) {
		final List<ConfiguracionAutenticacion> listaConfiguraciones = new ArrayList<>();
		String sql = "select a from JConfiguracionAutenticacion as a";

		sql += " where a.area.codigo = :idArea ";
		if (StringUtils.isNotBlank(pFiltro)) {
			sql += "  AND ( a.identificador like :filtro OR a.descripcion like :filtro OR a.url like :filtro) ";
		}
		sql += "  order by a.identificador, a.codigo";

		final Query query = entityManager.createQuery(sql);

		query.setParameter("idArea", pIdArea);

		if (StringUtils.isNotBlank(pFiltro)) {
			query.setParameter("filtro", "%" + pFiltro.toLowerCase() + "%");
		}

		final List<JConfiguracionAutenticacion> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			for (final JConfiguracionAutenticacion jConfiguracionAutenticacion : results) {
				listaConfiguraciones.add(jConfiguracionAutenticacion.toModel());
			}
		}
		return listaConfiguraciones;
	}

	@Override
	public void removeByArea(final Long pIdArea) {

		if (pIdArea == null) {
			throw new FaltanDatosException(FALTA_AREA);
		}

		final String sql = "delete from JConfiguracionAutenticacion a where a.area.codigo = :idArea";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idArea", pIdArea);
		query.executeUpdate();
	}

	@Override
	public boolean existeConfiguracionAutenticacion(final String identificador, final Long idCodigo) {
		final StringBuffer sql = new StringBuffer(
				"select count(a) from JConfiguracionAutenticacion as a where a.identificador like :identificador");
		if (idCodigo != null) {
			sql.append(" and a.codigo != :codigo");
		}
		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("identificador", identificador);
		if (idCodigo != null) {
			query.setParameter("codigo", idCodigo);
		}
		final Long cuantos = (Long) query.getSingleResult();
		return cuantos != 0l;
	}

	@Override
	public ConfiguracionAutenticacion getConfiguracionAutenticacion(String identificador, Long codArea) {
		ConfiguracionAutenticacion config = null;
		final StringBuffer sql = new StringBuffer(
				"select a from JConfiguracionAutenticacion as a where a.identificador like :identificador and a.area.codigo = :codArea");

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("identificador", identificador);
		query.setParameter("codArea", codArea);

		final List<JConfiguracionAutenticacion> configs =  query.getResultList();
		if (configs != null && !configs.isEmpty()) {
			config = configs.get(0).toModel();
		}
		return config;
	}

}

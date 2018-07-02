package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.service.repository.model.JConfiguracionGlobal;

/**
 * La clase ConfiguracionGlobalDaoImpl.
 */
@Repository("configuracionGlobalDao")
public class ConfiguracionGlobalDaoImpl implements ConfiguracionGlobalDao {

	/**
	 * Crea una nueva instancia de ConfiguracionGlobalDaoImpl.
	 */
	public ConfiguracionGlobalDaoImpl() {
		super();
	}

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.ConfiguracionGlobalDao#getById(
	 * java.lang.Long)
	 */
	@Override
	public ConfiguracionGlobal getById(final Long id) {
		ConfiguracionGlobal configuracionGlobal = null;
		final JConfiguracionGlobal jConfiguracionGlobal = entityManager.find(JConfiguracionGlobal.class, id);
		if (jConfiguracionGlobal != null) {
			configuracionGlobal = jConfiguracionGlobal.toModel();
		}
		return configuracionGlobal;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.ConfiguracionGlobalDao#
	 * getByPropiedad(java.lang.String)
	 */
	@Override
	public ConfiguracionGlobal getByPropiedad(final String propiedad) {
		ConfiguracionGlobal configuracionGlobal = null;

		final JConfiguracionGlobal jConfiguracionGlobal = getJByPropiedad(propiedad);

		if (jConfiguracionGlobal != null) {
			configuracionGlobal = jConfiguracionGlobal.toModel();
		}

		return configuracionGlobal;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.ConfiguracionGlobalDao#add(es.
	 * caib.sistrages.core.api.model.ConfiguracionGlobal)
	 */
	@Override
	public void add(final ConfiguracionGlobal configuracionGlobal) {
		final JConfiguracionGlobal jConfiguracionGlobal = JConfiguracionGlobal.fromModel(configuracionGlobal);
		;
		entityManager.persist(jConfiguracionGlobal);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.ConfiguracionGlobalDao#remove(
	 * java.lang.Long)
	 */
	@Override
	public void remove(final Long id) {
		final JConfiguracionGlobal jConfiguracionGlobal = entityManager.find(JConfiguracionGlobal.class, id);
		if (jConfiguracionGlobal == null) {
			throw new NoExisteDato("No existe configuracion global: " + id);
		}
		entityManager.remove(jConfiguracionGlobal);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.ConfiguracionGlobalDao#update(
	 * es.caib.sistrages.core.api.model.ConfiguracionGlobal)
	 */
	@Override
	public void update(final ConfiguracionGlobal configuracionGlobal) {
		final JConfiguracionGlobal jConfiguracionGlobal = entityManager.find(JConfiguracionGlobal.class,
				configuracionGlobal.getCodigo());
		if (jConfiguracionGlobal == null) {
			throw new NoExisteDato("No existe configuracion global: " + configuracionGlobal.getCodigo());
		}
		final JConfiguracionGlobal jConfiguracionGlobalNew = JConfiguracionGlobal.fromModel(configuracionGlobal);
		entityManager.merge(jConfiguracionGlobalNew);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.ConfiguracionGlobalDao#getAll()
	 */
	@Override
	public List<ConfiguracionGlobal> getAll() {
		return listarPropiedades(null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.ConfiguracionGlobalDao#
	 * getAllByFiltro(java.lang.String)
	 */
	@Override
	public List<ConfiguracionGlobal> getAllByFiltro(final String filtro) {
		return listarPropiedades(filtro);
	}

	/**
	 * Lista de valores de configuracion global.
	 *
	 * @param filtro
	 *            filtro
	 * @return lista de configuracion global
	 */
	@SuppressWarnings("unchecked")
	private List<ConfiguracionGlobal> listarPropiedades(final String filtro) {
		final List<ConfiguracionGlobal> listaConfiguracionGlobal = new ArrayList<>();

		String sql = "select t from JConfiguracionGlobal as t";
		if (StringUtils.isNotBlank(filtro)) {
			sql += " where lower(t.propiedad) like :pFiltro or lower(t.valor) like :pFiltro or lower(t.descripcion) like :pFiltro";
		}
		sql += " order by t.propiedad";
		final Query query = entityManager.createQuery(sql);

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("pFiltro", "%".concat(filtro.toLowerCase()).concat("%"));
		}

		final List<JConfiguracionGlobal> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final JConfiguracionGlobal jConfiguracionGlobal : results) {
				listaConfiguracionGlobal.add(jConfiguracionGlobal.toModel());
			}
		}

		return listaConfiguracionGlobal;
	}

	/**
	 * Obtiene el valor de configuracion global.
	 *
	 * @param propiedad
	 *            propiedad
	 * @return el valor de configuracion global
	 */
	@SuppressWarnings("unchecked")
	private JConfiguracionGlobal getJByPropiedad(final String propiedad) {
		JConfiguracionGlobal jConfiguracionGlobal = null;

		if (propiedad != null) {
			final Query query = entityManager
					.createQuery("Select t From JConfiguracionGlobal t Where t.propiedad = :propiedad");
			query.setParameter("propiedad", propiedad);

			final List<JConfiguracionGlobal> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				jConfiguracionGlobal = results.get(0);
			}
		}

		return jConfiguracionGlobal;
	}

}

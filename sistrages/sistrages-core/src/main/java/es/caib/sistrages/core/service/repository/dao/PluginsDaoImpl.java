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
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.service.repository.model.JEntidad;
import es.caib.sistrages.core.service.repository.model.JPlugin;

/**
 * La clase PluginsDaoImpl.
 */
@Repository("pluginsDao")
public class PluginsDaoImpl implements PluginsDao {

	/**
	 * Crea una nueva instancia de PluginsDaoImpl.
	 */
	public PluginsDaoImpl() {
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
	 * es.caib.sistrages.core.service.repository.dao.PluginsDao#getById(java.lang.
	 * Long)
	 */
	@Override
	public Plugin getById(final Long id) {
		Plugin plugin = null;
		final JPlugin jPlugin = entityManager.find(JPlugin.class, id);
		if (jPlugin != null) {
			plugin = jPlugin.toModel();
		}
		return plugin;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.PluginsDao#getAll(es.caib.
	 * sistrages.core.api.model.types.TypeAmbito, java.lang.Long)
	 */
	@Override
	public List<Plugin> getAll(final TypeAmbito ambito, final Long idEntidad) {
		return listarPlugins(ambito, idEntidad, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.PluginsDao#getAllByFiltro(es.
	 * caib.sistrages.core.api.model.types.TypeAmbito, java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	public List<Plugin> getAllByFiltro(final TypeAmbito ambito, final Long idEntidad, final String filtro) {
		return listarPlugins(ambito, idEntidad, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.PluginsDao#add(es.caib.
	 * sistrages.core.api.model.Plugin, java.lang.Long)
	 */
	@Override
	public void add(final Plugin plugin, final Long idEntidad) {
		JEntidad jEntidad = null;
		if (plugin.getAmbito() == TypeAmbito.ENTIDAD) {
			jEntidad = entityManager.find(JEntidad.class, idEntidad);
			if (jEntidad == null) {
				throw new FaltanDatosException("No se ha especificado una entidad valida. Id Entidad: " + idEntidad);
			}
		}

		final JPlugin jPlugin = JPlugin.fromModel(plugin);
		jPlugin.setEntidad(jEntidad);
		entityManager.persist(jPlugin);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.PluginsDao#update(es.caib.
	 * sistrages.core.api.model.Plugin)
	 */
	@Override
	public void update(final Plugin plugin) {
		final JPlugin jPlugin = entityManager.find(JPlugin.class, plugin.getId());
		if (jPlugin == null) {
			throw new NoExisteDato("No existe plugin " + plugin.getId());
		}
		final JPlugin jPluginNew = JPlugin.fromModel(plugin);
		jPluginNew.setEntidad(jPlugin.getEntidad());
		entityManager.merge(jPluginNew);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.PluginsDao#remove(java.lang.
	 * Long)
	 */
	@Override
	public void remove(final Long id) {
		final JPlugin jPlugin = entityManager.find(JPlugin.class, id);
		if (jPlugin == null) {
			throw new NoExisteDato("No existe plugin " + id);
		}
		entityManager.remove(jPlugin);
	}

	/**
	 * Listado de plugins.
	 *
	 * @param ambito
	 *            ambito
	 * @param idEntidad
	 *            idEntidad
	 * @param filtro
	 *            filtro
	 * @return lista de plugins
	 */
	@SuppressWarnings("unchecked")
	private List<Plugin> listarPlugins(final TypeAmbito ambito, final Long idEntidad, final String filtro) {
		final List<Plugin> listaPlugin = new ArrayList<>();

		String sql = "select t from JPlugin as t where t.ambito = :ambito ";
		if (ambito == TypeAmbito.ENTIDAD) {
			sql += " AND t.entidad.codigo = :idEntidad";
		}
		if (StringUtils.isNotBlank(filtro)) {
			sql += " AND ( lower(t.claseImplementadora) like :pFiltro or lower(t.descripcion) like :pFiltro  or lower(t.instancia) like :pFiltro)";
		}
		sql += " order by t.tipo";
		final Query query = entityManager.createQuery(sql);

		query.setParameter("ambito", ambito.toString());
		if (ambito == TypeAmbito.ENTIDAD) {
			query.setParameter("idEntidad", idEntidad);
		}
		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("pFiltro", "%".concat(filtro.toLowerCase()).concat("%"));
		}

		final List<JPlugin> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final JPlugin jPlugin : results) {
				listaPlugin.add(jPlugin.toModel());
			}
		}

		return listaPlugin;
	}

	@Override
	public void removeByEntidad(final Long idEntidad) {
		final String sql = "delete from JPlugin as t where t.entidad.codigo = :idEntidad";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idEntidad", idEntidad);
		query.executeUpdate();
	}

}

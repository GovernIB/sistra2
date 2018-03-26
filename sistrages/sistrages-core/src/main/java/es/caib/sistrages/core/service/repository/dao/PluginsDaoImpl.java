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
@Repository("plugin")
public class PluginsDaoImpl implements PluginsDao {

	public PluginsDaoImpl() {
		super();
	}

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Plugin getById(final Long id) {
		Plugin plugin = null;
		final JPlugin jPlugin = entityManager.find(JPlugin.class, id);
		if (jPlugin != null) {
			plugin = jPlugin.toModel();
		}
		return plugin;
	}

	@Override
	public List<Plugin> getAll(final TypeAmbito ambito, final Long idEntidad) {
		return listarPlugins(ambito, idEntidad, null);
	}

	@Override
	public List<Plugin> getAllByFiltro(final TypeAmbito ambito, final Long idEntidad, final String filtro) {
		return listarPlugins(ambito, idEntidad, filtro);
	}

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

	@Override
	public void remove(final Long id) {
		final JPlugin jPlugin = entityManager.find(JPlugin.class, id);
		if (jPlugin == null) {
			throw new NoExisteDato("No existe plugin " + id);
		}
		entityManager.remove(jPlugin);
	}

	private List<Plugin> listarPlugins(final TypeAmbito ambito, final Long idEntidad, final String filtro) {
		final List<Plugin> listaPlugin = new ArrayList<>();

		String sql = "select t from JPlugin as t where t.ambito = :ambito ";
		if (ambito == TypeAmbito.ENTIDAD) {
			sql += " AND t.entidad.codigo = :idEntidad";
		}
		if (StringUtils.isNotBlank(filtro)) {
			sql += " AND lower(t.claseImplementadora) like :pFiltro";
		}
		sql += " order by t.tipo";
		final Query query = entityManager.createQuery(sql);

		query.setParameter("ambito", ambito.toString());
		if (ambito == TypeAmbito.ENTIDAD) {
			query.setParameter("idEntidad", idEntidad);
		}
		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("pFiltro", "%".concat(filtro).concat("%"));
		}

		final List<JPlugin> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final JPlugin jPlugin : results) {
				listaPlugin.add(jPlugin.toModel());
			}
		}

		return listaPlugin;
	}

}

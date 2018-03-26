package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.service.repository.model.JAvisoEntidad;
import es.caib.sistrages.core.service.repository.model.JEntidad;
import es.caib.sistrages.core.service.repository.model.JLiteral;

/**
 * La clase AvisoEntidadDaoImpl.
 */
@Repository("avisoEntidadDao")
public class AvisoEntidadDaoImpl implements AvisoEntidadDao {

	/** Entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public AvisoEntidad getById(final Long id) {
		AvisoEntidad avisoEntidad = null;
		final JAvisoEntidad jAvisoEntidad = entityManager.find(JAvisoEntidad.class, id);
		if (jAvisoEntidad != null) {
			avisoEntidad = jAvisoEntidad.toModel();
		}
		return avisoEntidad;
	}

	@Override
	public void add(final long idEntidad, final AvisoEntidad avisoEntidad) {
		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		final JAvisoEntidad jAvisoEntidad = JAvisoEntidad.fromModel(avisoEntidad);
		jAvisoEntidad.setEntidad(jEntidad);
		entityManager.persist(jAvisoEntidad);
	}

	@Override
	public void remove(final Long id) {
		final JAvisoEntidad jAvisoEntidad = entityManager.find(JAvisoEntidad.class, id);
		if (jAvisoEntidad == null) {
			throw new NoExisteDato("No existe aviso entidad: " + id);
		}
		entityManager.remove(jAvisoEntidad);
	}

	@Override
	public void update(final AvisoEntidad avisoEntidad) {
		final JAvisoEntidad jAvisoEntidad = entityManager.find(JAvisoEntidad.class, avisoEntidad.getId());
		if (jAvisoEntidad == null) {
			throw new NoExisteDato("No existe aviso entidad: " + avisoEntidad.getId());
		}
		// Mergeamos datos
		final JAvisoEntidad jAvisoEntidadNew = JAvisoEntidad.fromModel(avisoEntidad);
		jAvisoEntidadNew.setEntidad(jAvisoEntidad.getEntidad());
		jAvisoEntidadNew.setCodigo(avisoEntidad.getId());
		jAvisoEntidadNew.setMensaje(JLiteral.mergeModel(jAvisoEntidad.getMensaje(), avisoEntidad.getMensaje()));
		entityManager.merge(jAvisoEntidadNew);
	}

	@Override
	public List<AvisoEntidad> getAllByFiltro(final Long idEntidad, final TypeIdioma idioma, final String filtro) {
		return listarAvisos(idEntidad, idioma, filtro);
	}

	@Override
	public List<AvisoEntidad> getAll(final Long idEntidad) {
		return listarAvisos(idEntidad, null, null);
	}

	private List<AvisoEntidad> listarAvisos(final Long idEntidad, final TypeIdioma idioma, final String filtro) {
		final List<AvisoEntidad> listaAvisoEntidad = new ArrayList<>();
		String sql = "select t from JAvisoEntidad as a where a.entidad.codigo = :idEntidad";
		if (StringUtils.isNotBlank(filtro)) {
			sql += " LEFT JOIN a.mensaje.traduccionLiterales t WHERE (t.idioma.identificador = :idioma AND LOWER(t.literal) LIKE :filtro)";
		}
		sql += "  order by a.fechaInicio";

		final Query query = entityManager.createQuery(sql);

		query.setParameter("idEntidad", idEntidad);

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("idioma", idioma.toString());
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		final List<JAvisoEntidad> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			for (final JAvisoEntidad jAvisoEntidad : results) {
				listaAvisoEntidad.add(jAvisoEntidad.toModel());
			}
		}
		return listaAvisoEntidad;
	}

}

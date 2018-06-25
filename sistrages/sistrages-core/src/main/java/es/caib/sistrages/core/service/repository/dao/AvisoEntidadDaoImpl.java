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
import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.types.TypeAvisoEntidad;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.service.repository.model.JAvisoEntidad;
import es.caib.sistrages.core.service.repository.model.JEntidad;
import es.caib.sistrages.core.service.repository.model.JLiteral;

/**
 * La clase AvisoEntidadDaoImpl.
 */
@Repository("avisoEntidadDao")
public class AvisoEntidadDaoImpl implements AvisoEntidadDao {

	private static final String FALTA_IDENTIFICADOR = "Falta el identificador";
	private static final String NO_EXISTE_EL_AVISO = "No existe el aviso de entidad: ";
	private static final String FALTA_ENTIDAD = "Falta la entidad";
	private static final String FALTA_AVISO = "Falta el aviso de entidad";
	private static final String NO_EXISTE_LA_ENTIDAD = "No existe la entidad: ";

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de AvisoEntidadDaoImpl.
	 */
	public AvisoEntidadDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.AvisoEntidadDao#getById(
	 * java. lang.Long)
	 */
	@Override
	public AvisoEntidad getById(final Long pId) {
		AvisoEntidad avisoEntidad = null;

		if (pId == null) {
			throw new FaltanDatosException(FALTA_IDENTIFICADOR);
		}

		final JAvisoEntidad jAvisoEntidad = entityManager.find(JAvisoEntidad.class, pId);

		if (jAvisoEntidad == null) {
			throw new NoExisteDato(NO_EXISTE_EL_AVISO + pId);
		} else {
			avisoEntidad = jAvisoEntidad.toModel();
		}

		return avisoEntidad;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.AvisoEntidadDao#add(long,
	 * es.caib.sistrages.core.api.model.AvisoEntidad)
	 */
	@Override
	public void add(final Long pIdEntidad, final AvisoEntidad pAvisoEntidad) {
		if (pAvisoEntidad == null) {
			throw new FaltanDatosException(FALTA_AVISO);
		}

		if (pIdEntidad == null) {
			throw new FaltanDatosException(FALTA_ENTIDAD);
		}

		final JEntidad jEntidad = entityManager.find(JEntidad.class, pIdEntidad);
		if (jEntidad == null) {
			throw new FaltanDatosException(NO_EXISTE_LA_ENTIDAD + pIdEntidad);
		}

		final JAvisoEntidad jAvisoEntidad = JAvisoEntidad.fromModel(pAvisoEntidad);
		jAvisoEntidad.setEntidad(jEntidad);
		entityManager.persist(jAvisoEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.AvisoEntidadDao#remove(
	 * java. lang.Long)
	 */
	@Override
	public void remove(final Long pId) {
		final JAvisoEntidad jAvisoEntidad = entityManager.find(JAvisoEntidad.class, pId);
		if (jAvisoEntidad == null) {
			throw new NoExisteDato(NO_EXISTE_EL_AVISO + pId);
		}
		entityManager.remove(jAvisoEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.AvisoEntidadDao#update(es.
	 * caib. sistrages.core.api.model.AvisoEntidad)
	 */
	@Override
	public void update(final AvisoEntidad pAvisoEntidad) {
		if (pAvisoEntidad == null) {
			throw new FaltanDatosException(FALTA_AVISO);
		}

		final JAvisoEntidad jAvisoEntidad = entityManager.find(JAvisoEntidad.class, pAvisoEntidad.getId());
		if (jAvisoEntidad == null) {
			throw new NoExisteDato(NO_EXISTE_EL_AVISO + pAvisoEntidad.getId());
		}
		// Mergeamos datos
		final JAvisoEntidad jAvisoEntidadNew = JAvisoEntidad.fromModel(pAvisoEntidad);
		jAvisoEntidadNew.setEntidad(jAvisoEntidad.getEntidad());
		jAvisoEntidadNew.setCodigo(pAvisoEntidad.getId());
		jAvisoEntidadNew.setMensaje(JLiteral.mergeModel(jAvisoEntidad.getMensaje(), pAvisoEntidad.getMensaje()));
		entityManager.merge(jAvisoEntidadNew);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.AvisoEntidadDao#
	 * getAllByFiltro( java.lang.Long,
	 * es.caib.sistrages.core.api.model.types.TypeIdioma, java.lang.String)
	 */
	@Override
	public List<AvisoEntidad> getAllByFiltro(final Long pIdEntidad, final TypeIdioma pIdioma, final String pFiltro) {
		if (pIdEntidad == null) {
			throw new FaltanDatosException(FALTA_ENTIDAD);
		}
		return listarAvisos(pIdEntidad, pIdioma, pFiltro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.AvisoEntidadDao#getAll(
	 * java. lang.Long)
	 */
	@Override
	public List<AvisoEntidad> getAll(final Long pIdEntidad) {
		if (pIdEntidad == null) {
			throw new FaltanDatosException(FALTA_ENTIDAD);
		}
		return listarAvisos(pIdEntidad, null, null);
	}

	/**
	 * Listar avisos.
	 *
	 * @param pIdEntidad
	 *            idEntidad
	 * @param pIdioma
	 *            idioma
	 * @param pFiltro
	 *            filtro
	 * @return Listado de avisos
	 */
	@SuppressWarnings("unchecked")
	private List<AvisoEntidad> listarAvisos(final Long pIdEntidad, final TypeIdioma pIdioma, final String pFiltro) {
		final List<AvisoEntidad> listaAvisoEntidad = new ArrayList<>();
		String sql = "select a from JAvisoEntidad as a";
		if (StringUtils.isNotBlank(pFiltro)) {
			sql += " LEFT JOIN a.mensaje.traduccionLiterales t ";
		}
		sql += " where a.entidad.codigo = :idEntidad ";
		if (StringUtils.isNotBlank(pFiltro)) {
			sql += "  AND (t.idioma.identificador = :idioma AND LOWER(t.literal) LIKE :filtro) ";
		}
		sql += "  order by a.fechaInicio, a.codigo";

		final Query query = entityManager.createQuery(sql);

		query.setParameter("idEntidad", pIdEntidad);

		if (StringUtils.isNotBlank(pFiltro)) {
			query.setParameter("idioma", pIdioma.toString());
			query.setParameter("filtro", "%" + pFiltro.toLowerCase() + "%");
		}

		final List<JAvisoEntidad> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			for (final JAvisoEntidad jAvisoEntidad : results) {
				listaAvisoEntidad.add(jAvisoEntidad.toModel());
			}
		}
		return listaAvisoEntidad;
	}

	@Override
	public void removeByEntidad(final Long pIdEntidad) {

		if (pIdEntidad == null) {
			throw new FaltanDatosException(FALTA_ENTIDAD);
		}

		final String sql = "delete from JAvisoEntidad a where a.entidad.codigo = :idEntidad";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idEntidad", pIdEntidad);
		query.executeUpdate();
	}

	@Override
	public AvisoEntidad getAvisoEntidadByTramite(final String identificadorTramite) {

		if (identificadorTramite == null || identificadorTramite.isEmpty()) {
			throw new FaltanDatosException(FALTA_IDENTIFICADOR);
		}

		final String sql = "select a from JAvisoEntidad as a  where a.tipo = :tipoAviso and a.listaSerializadaTramites like :identificadorTramite ";

		final Query query = entityManager.createQuery(sql);
		query.setParameter("tipoAviso", TypeAvisoEntidad.TRAMITE_VERSION.toString());
		query.setParameter("identificadorTramite", identificadorTramite);
		AvisoEntidad avisoEntidad = null;

		final List<JAvisoEntidad> results = query.getResultList();
		if (!results.isEmpty()) {
			final JAvisoEntidad javisoEntidad = results.get(0);
			avisoEntidad = javisoEntidad.toModel();
		}

		return avisoEntidad;
	}

}

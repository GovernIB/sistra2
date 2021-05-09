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
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.comun.FilaImportarGestor;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.service.repository.model.JArea;
import es.caib.sistrages.core.service.repository.model.JConfiguracionAutenticacion;
import es.caib.sistrages.core.service.repository.model.JGestorExternoFormularios;

/**
 * La clase FormularioExternoDaoImpl.
 */
@Repository("FormularioExternoDao")
public class FormularioExternoDaoImpl implements FormularioExternoDao {

	private static final String FALTA_IDENTIFICADOR = "Falta el identificador";
	private static final String NO_EXISTE_EL_FORM_EXTERNO = "No existe el Formulario Externo: ";
	private static final String FALTA_AREA = "Falta el are";
	private static final String FALTA_AVISO = "Falta el formulario Externo";
	private static final String NO_EXISTE_EL_AREA = "No existe el area: ";

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de FormularioExternoDaoImpl.
	 */
	public FormularioExternoDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormularioExternoDao#getById(
	 * java. lang.Long)
	 */
	@Override
	public GestorExternoFormularios getById(final Long pId) {
		GestorExternoFormularios FormularioExterno = null;

		if (pId == null) {
			throw new FaltanDatosException(FALTA_IDENTIFICADOR);
		}

		final JGestorExternoFormularios jGestorExternoFormularios = entityManager.find(JGestorExternoFormularios.class,
				pId);

		if (jGestorExternoFormularios == null) {
			throw new NoExisteDato(NO_EXISTE_EL_FORM_EXTERNO + pId);
		} else {
			FormularioExterno = jGestorExternoFormularios.toModel();
		}

		return FormularioExterno;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormularioExternoDao#add(long,
	 * es.caib.sistrages.core.api.model.FormularioExterno)
	 */
	@Override
	public void add(final Long pIdArea, final GestorExternoFormularios pFormularioExterno) {
		if (pFormularioExterno == null) {
			throw new FaltanDatosException(FALTA_AVISO);
		}

		if (pIdArea == null) {
			throw new FaltanDatosException(FALTA_AREA);
		}

		final JArea jArea = entityManager.find(JArea.class, pIdArea);
		if (jArea == null) {
			throw new FaltanDatosException(NO_EXISTE_EL_AREA + pIdArea);
		}

		final JGestorExternoFormularios jGestorExternoFormularios = JGestorExternoFormularios
				.fromModel(pFormularioExterno);
		jGestorExternoFormularios.setArea(jArea);
		entityManager.persist(jGestorExternoFormularios);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormularioExternoDao#remove(
	 * java. lang.Long)
	 */
	@Override
	public void remove(final Long pId) {
		final JGestorExternoFormularios jGestorExternoFormularios = entityManager.find(JGestorExternoFormularios.class,
				pId);
		if (jGestorExternoFormularios == null) {
			throw new NoExisteDato(NO_EXISTE_EL_FORM_EXTERNO + pId);
		}
		entityManager.remove(jGestorExternoFormularios);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormularioExternoDao#update(es.
	 * caib. sistrages.core.api.model.FormularioExterno)
	 */
	@Override
	public void update(final GestorExternoFormularios pFormularioExterno) {
		if (pFormularioExterno == null) {
			throw new FaltanDatosException(FALTA_AVISO);
		}

		final JGestorExternoFormularios jGestorExternoFormularios = entityManager.find(JGestorExternoFormularios.class,
				pFormularioExterno.getCodigo());
		if (jGestorExternoFormularios == null) {
			throw new NoExisteDato(NO_EXISTE_EL_FORM_EXTERNO + pFormularioExterno.getCodigo());
		}
		// Mergeamos datos
		jGestorExternoFormularios.merge(pFormularioExterno);
		entityManager.merge(jGestorExternoFormularios);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioExternoDao#
	 * getAllByFiltro( java.lang.Long,
	 * es.caib.sistrages.core.api.model.types.TypeIdioma, java.lang.String)
	 */
	@Override
	public List<GestorExternoFormularios> getAllByFiltro(final Long pIdArea, final TypeIdioma pIdioma,
			final String pFiltro) {
		if (pIdArea == null) {
			throw new FaltanDatosException(FALTA_AREA);
		}
		return listarGestorExternoFormularios(pIdArea, pIdioma, pFiltro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormularioExternoDao#getAll(
	 * java. lang.Long)
	 */
	@Override
	public List<GestorExternoFormularios> getAll(final Long pIdArea) {
		if (pIdArea == null) {
			throw new FaltanDatosException(FALTA_AREA);
		}
		return listarGestorExternoFormularios(pIdArea, null, null);
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
	private List<GestorExternoFormularios> listarGestorExternoFormularios(final Long pIdArea, final TypeIdioma pIdioma,
			final String pFiltro) {
		final List<GestorExternoFormularios> listaFormularioExterno = new ArrayList<>();
		String sql = "select a from JGestorExternoFormularios as a";

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

		final List<JGestorExternoFormularios> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			for (final JGestorExternoFormularios jGestorExternoFormularios : results) {
				listaFormularioExterno.add(jGestorExternoFormularios.toModel());
			}
		}
		return listaFormularioExterno;
	}

	@Override
	public void removeByArea(final Long pIdArea) {

		if (pIdArea == null) {
			throw new FaltanDatosException(FALTA_AREA);
		}

		final String sql = "delete from JGestorExternoFormularios a where a.area.codigo = :idArea";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idArea", pIdArea);
		query.executeUpdate();
	}

	@Override
	public boolean existeFormulario(final String identificador, final Long idCodigo) {
		final StringBuffer sql = new StringBuffer(
				"select count(a) from JGestorExternoFormularios as a where a.identificador like :identificador");
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
	public List<GestorExternoFormularios> getGestorExternoByAutenticacion(Long id, Long idArea) {
		final StringBuffer sql = new StringBuffer(
				"select a from JGestorExternoFormularios as a where a.area.id = :idArea and a.configuracionAutenticacion.id = :id");
		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("idArea", idArea);
		query.setParameter("id", id);
		List<GestorExternoFormularios> gestores = new ArrayList<>();
		List<JGestorExternoFormularios> jgestores = query.getResultList();
		if (jgestores != null) {
			for (JGestorExternoFormularios jgestor : jgestores) {
				gestores.add(jgestor.toModel());
			}
		}
		return gestores;
	}

	@Override
	public GestorExternoFormularios getFormularioExternoByIdentificador(String identificador) {
	    GestorExternoFormularios gestorExternoFormularios = null;
		String sql = "select a from JGestorExternoFormularios as a where a.identificador like :identificador ";
		final Query query = entityManager.createQuery(sql);

		query.setParameter("identificador", identificador);


		final List<JGestorExternoFormularios> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			gestorExternoFormularios  = results.get(0).toModel();
		}
		return gestorExternoFormularios;
	}

	@Override
	public Long importar(FilaImportarGestor filaGestor, Long idArea) {
		JConfiguracionAutenticacion config = null;
		if (filaGestor.getConfiguracionAutenticacionActual() != null) {
			config = entityManager.find(JConfiguracionAutenticacion.class, filaGestor.getConfiguracionAutenticacionActual().getCodigo());
		}

		if (filaGestor.getGestorActual() == null) { // Si no existe, se crea.
			final JArea jarea = entityManager.find(JArea.class, idArea);
			final JGestorExternoFormularios jGFE = JGestorExternoFormularios
					.fromModel(filaGestor.getGestor());
			jGFE.setCodigo(null);
			jGFE.setArea(jarea);
			jGFE.setConfiguracionAutenticacion(config);
			entityManager.persist(jGFE);
			return jGFE.getCodigo();
		} else {
			final JGestorExternoFormularios jGFE = entityManager.find(JGestorExternoFormularios.class,
					filaGestor.getGestorActual().getCodigo());
			if (filaGestor.getAccion() == TypeImportarAccion.REEMPLAZAR) {
				jGFE.setDescripcion(filaGestor.getGestor().getDescripcion());
				jGFE.setConfiguracionAutenticacion(config);
				entityManager.merge(jGFE);
			}
			return jGFE.getCodigo();

		}
 	}

}

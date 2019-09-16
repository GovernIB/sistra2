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
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.PlantillaFormateador;
import es.caib.sistrages.core.api.model.comun.FilaImportarFormateador;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.service.repository.model.JEntidad;
import es.caib.sistrages.core.service.repository.model.JFichero;
import es.caib.sistrages.core.service.repository.model.JFormateadorFormulario;
import es.caib.sistrages.core.service.repository.model.JIdioma;
import es.caib.sistrages.core.service.repository.model.JPlantillaFormateador;

/**
 * La clase FormateadorFormularioDaoImpl.
 */
@Repository("formateadorFormularioDao")
public class FormateadorFormularioDaoImpl implements FormateadorFormularioDao {

	private static final String NO_EXISTE_FMT = "No existe formateador de formularios";
	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de FormateadorFormularioDaoImpl.
	 */
	public FormateadorFormularioDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao#
	 * getById(java.lang.Long)
	 */
	@Override
	public FormateadorFormulario getById(final Long id) {
		FormateadorFormulario fmt = null;

		if (id == null) {
			throw new FaltanDatosException("Falta el identificador");
		}

		final JFormateadorFormulario jFmt = entityManager.find(JFormateadorFormulario.class, id);

		if (jFmt == null) {
			throw new NoExisteDato(NO_EXISTE_FMT + ": " + id);
		} else {
			fmt = jFmt.toModel();
		}

		return fmt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FormateadorFormulario getByCodigo(final String codigo) {
		FormateadorFormulario result = null;
		final String sql = "select f from JFormateadorFormulario f where f.identificador = :codigo";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("codigo", codigo);
		final List<JFormateadorFormulario> list = query.getResultList();
		if (!list.isEmpty()) {
			result = list.get(0).toModel();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao#add(es
	 * .caib.sistrages.core.api.model.FormateadorFormulario)
	 */
	@Override
	public void add(final Long idEntidad, final FormateadorFormulario fmt) {
		if (fmt == null) {
			throw new FaltanDatosException("Falta el formateador de formulario");
		}

		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad con id " + idEntidad);
		}

		if (fmt.isGenerico()) {
			quitarPorDefecto(idEntidad, null);
		}

		final JFormateadorFormulario jFmt = JFormateadorFormulario.fromModel(fmt);
		jFmt.setEntidad(jEntidad);

		entityManager.persist(jFmt);
	}

	/**
	 * Método que se encarga de quitar el formateador por defecto porque se va a
	 * añadir otro distinto.
	 *
	 * @param idEntidad
	 */
	private void quitarPorDefecto(final Long idEntidad, final Long idFmt) {
		final StringBuilder sql = new StringBuilder();
		sql.append(
				"UPDATE JFormateadorFormulario FORM SET FORM.generico = 0 WHERE FORM.generico = 1 AND FORM.entidad.codigo = :idEntidad");
		if (idFmt != null) {
			sql.append(" and FORM.codigo != :idFmt");
		}
		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("idEntidad", idEntidad);
		if (idFmt != null) {
			query.setParameter("idFmt", idFmt);
		}
		query.executeUpdate();
		entityManager.flush();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao#remove
	 * (java.lang.Long)
	 */
	@Override
	public void remove(final Long id) {

		if (id == null) {
			throw new FaltanDatosException("Falta el identificador");
		}

		final JFormateadorFormulario jFmt = entityManager.find(JFormateadorFormulario.class, id);
		if (jFmt == null) {
			throw new NoExisteDato(NO_EXISTE_FMT + ": " + id);
		}
		entityManager.remove(jFmt);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao#update
	 * (es.caib.sistrages.core.api.model.FormateadorFormulario)
	 */
	@Override
	public void update(final FormateadorFormulario fmt, final Long idEntidad) {
		if (fmt == null) {
			throw new FaltanDatosException("Falta el formateador de formulario");
		}

		if (fmt.isGenerico()) {
			quitarPorDefecto(idEntidad, fmt.getCodigo());
		}

		final JFormateadorFormulario jFmt = entityManager.find(JFormateadorFormulario.class, fmt.getCodigo());

		if (jFmt == null) {
			throw new NoExisteDato(NO_EXISTE_FMT + ": " + fmt.getCodigo());
		}

		// Mergeamos datos
		final JFormateadorFormulario jFmtNew = JFormateadorFormulario.fromModel(fmt);
		jFmtNew.setCodigo(jFmt.getCodigo());
		jFmtNew.setEntidad(jFmt.getEntidad());

		entityManager.merge(jFmtNew);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao#
	 * getAllByFiltro(java.lang.String)
	 */
	@Override
	public List<FormateadorFormulario> getAllByFiltro(final Long idEntidad, final String filtro,
			final Boolean bloqueado) {
		return listarFmt(idEntidad, filtro, bloqueado);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao#getAll
	 * ()
	 */
	@Override
	public List<FormateadorFormulario> getAll(final Long idEntidad) {
		return listarFmt(idEntidad, null, null);
	}

	/**
	 * Listar formateador de Formulario.
	 *
	 * @param filtro filtro
	 * @return Listado de formateadores de Formulario
	 */
	@SuppressWarnings("unchecked")
	private List<FormateadorFormulario> listarFmt(final long idEntidad, final String filtro,
			final Boolean desactivarPersonalizacion) {
		final List<FormateadorFormulario> listaFmt = new ArrayList<>();
		String sql = "select f from JFormateadorFormulario f where f.entidad.codigo = :idEntidad";

		if (StringUtils.isNotBlank(filtro)) {
			sql += " AND (LOWER(f.identificador) LIKE :filtro OR LOWER(f.descripcion) LIKE :filtro OR LOWER(f.classname) LIKE :filtro)";
		}
		if (desactivarPersonalizacion != null) {
			sql += " AND f.desactivarPersonalizacion = :desactivarPersonalizacion";
		}
		sql += " order by f.classname";

		final Query query = entityManager.createQuery(sql);

		query.setParameter("idEntidad", idEntidad);
		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("filtro", "%".concat(filtro.toLowerCase()).concat("%"));
		}
		if (desactivarPersonalizacion != null) {
			query.setParameter("desactivarPersonalizacion", desactivarPersonalizacion);
		}

		final List<JFormateadorFormulario> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			for (final JFormateadorFormulario jRol : results) {
				final FormateadorFormulario fmt = jRol.toModel();
				listaFmt.add(fmt);
			}
		}
		return listaFmt;
	}

	@Override
	public void removeByEntidad(final Long idEntidad) {
		final String sql = "delete from JFormateadorFormulario as a where a.entidad.codigo = :idEntidad";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idEntidad", idEntidad);
		query.executeUpdate();
	}

	@Override
	public Long importar(final FilaImportarFormateador filaFormateador, final Long idEntidad) {
		if (filaFormateador.getFormateadorFormularioActual() == null) { // Si no existe, se crea.
			final JEntidad jentidad = entityManager.find(JEntidad.class, idEntidad);
			final JFormateadorFormulario jFmt = JFormateadorFormulario
					.fromModel(filaFormateador.getFormateadorFormulario());
			jFmt.setCodigo(null);
			jFmt.setEntidad(jentidad);
			entityManager.persist(jFmt);
			return jFmt.getCodigo();
		} else {
			final JFormateadorFormulario jFmt = entityManager.find(JFormateadorFormulario.class,
					filaFormateador.getFormateadorFormularioActual().getCodigo());
			if (filaFormateador.getAccion() == TypeImportarAccion.REEMPLAZAR) {
				jFmt.setClassname(filaFormateador.getFormateadorFormulario().getClassname());
				jFmt.setDescripcion(filaFormateador.getFormateadorFormulario().getDescripcion());
				entityManager.merge(jFmt);
			}
			return jFmt.getCodigo();

		}
	}

	@Override
	public boolean tieneRelacionesFormateadorFormulario(final Long idFmt) {
		final String sql = "select count(plantilla) from JPlantillaFormulario plantilla where plantilla.formateadorFormulario.codigo = :idFmt";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idFmt", idFmt);

		final Long resultado = (Long) query.getSingleResult();

		return resultado.compareTo(0l) > 0;

	}

	@Override
	public List<PlantillaFormateador> getListaPlantillasFormateador(final Long idFormateador) {
		final List<PlantillaFormateador> listaPlantillas = new ArrayList<>();
		final String sql = "select f from JPlantillaFormateador f where f.formateador.codigo = :idFormateador ";

		final Query query = entityManager.createQuery(sql);

		query.setParameter("idFormateador", idFormateador);

		final List<JPlantillaFormateador> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			for (final JPlantillaFormateador jPlantilla : results) {
				listaPlantillas.add(jPlantilla.toModel());
			}
		}
		return listaPlantillas;
	}

	@Override
	public PlantillaFormateador uploadPlantillaFormateador(final Long idPlantillaFormateador,
			final PlantillaFormateador plantillaFormateador) {
		JPlantillaFormateador jPlantillaFormateador;
		if (plantillaFormateador.getCodigo() != null) {
			jPlantillaFormateador = entityManager.find(JPlantillaFormateador.class, plantillaFormateador.getCodigo());
			if (jPlantillaFormateador.getFichero() != null) {
				jPlantillaFormateador.getFichero().setNombre(plantillaFormateador.getFichero().getNombre());
				jPlantillaFormateador.getFichero().setPublico(plantillaFormateador.getFichero().isPublico());
			} else {
				jPlantillaFormateador.setFichero(JFichero.fromModel(plantillaFormateador.getFichero()));
			}
			entityManager.merge(jPlantillaFormateador);
		} else {
			jPlantillaFormateador = JPlantillaFormateador.fromModel(plantillaFormateador);
			jPlantillaFormateador
					.setFormateador(entityManager.find(JFormateadorFormulario.class, idPlantillaFormateador));

			jPlantillaFormateador.setIdioma(entityManager.find(JIdioma.class, plantillaFormateador.getIdioma()));
			entityManager.persist(jPlantillaFormateador);
		}
		return jPlantillaFormateador.toModel();
	}

	@Override
	public FormateadorFormulario getFormateadorPorDefecto(final Long idEntidad, final String codigoDir3) {
		FormateadorFormulario formateador = null;
		final StringBuilder sql = new StringBuilder();

		sql.append("select f from JFormateadorFormulario f where ");

		if (idEntidad != null) {
			sql.append(" f.entidad.codigo = :idEntidad and ");
		}
		if (codigoDir3 != null && !codigoDir3.isEmpty()) {
			sql.append(" f.entidad.codigoDir3 = :codigoDir3 and ");
		}

		sql.append(" f.generico = true ");

		final Query query = entityManager.createQuery(sql.toString());

		if (idEntidad != null) {
			query.setParameter("idEntidad", idEntidad);
		}
		if (codigoDir3 != null) {
			query.setParameter("codigoDir3", codigoDir3);
		}

		final List<JFormateadorFormulario> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			formateador = results.get(0).toModel();
		}

		return formateador;
	}

	@Override
	public void removePlantillaFormateador(final Long idPlantillaFormateador) {
		final JPlantillaFormateador jPlantillaFormateador = entityManager.find(JPlantillaFormateador.class,
				idPlantillaFormateador);
		if (jPlantillaFormateador == null) {
			throw new NoExisteDato("No existe plantilla formateador " + idPlantillaFormateador);
		}

		entityManager.remove(jPlantillaFormateador);

	}
}

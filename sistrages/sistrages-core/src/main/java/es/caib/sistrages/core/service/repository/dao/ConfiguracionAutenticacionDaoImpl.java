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
import es.caib.sistrages.core.api.model.ConsultaGeneral;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.service.repository.model.JArea;
import es.caib.sistrages.core.service.repository.model.JConfiguracionAutenticacion;
import es.caib.sistrages.core.service.repository.model.JDominio;
import es.caib.sistrages.core.service.repository.model.JEntidad;
import es.caib.sistrages.core.service.repository.model.JGestorExternoFormularios;

/**
 * La clase ConfiguracionAutenticacionDaoImpl.
 */
@Repository("ConfiguracionAutenticacionDao")
public class ConfiguracionAutenticacionDaoImpl implements ConfiguracionAutenticacionDao {

	private static final String FALTA_IDENTIFICADOR = "Falta el identificador";
	private static final String NO_EXISTE_EL_CONF_AUT = "No existe la configuracion autenticacion: ";
	private static final String FALTA_AMBITO = "Falta el ambito";
	private static final String FALTA_AREA = "Falta el area";
	private static final String FALTA_ENTIDAD = "Falta la entidad";
	private static final String FALTA_CONF_AUTENTICACION = "Falta la configuracion autenticacion";
	private static final String NO_EXISTE_EL_AREA = "No existe el area: ";
	private static final String NO_EXISTE_LA_ENTIDAD = "No existe la entidad:";

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
	public Long add(final Long pIdArea, final Long idEntidad, final ConfiguracionAutenticacion data) {

		if (data == null) {
			throw new FaltanDatosException(FALTA_CONF_AUTENTICACION);
		}

		JArea jArea = null;
		JEntidad jEntidad = null;
		if (data.getAmbito() == TypeAmbito.AREA) {

			if (pIdArea == null) {
				throw new FaltanDatosException(FALTA_AREA);
			} else {
				jArea = entityManager.find(JArea.class, pIdArea);
				if (jArea == null) {
					throw new FaltanDatosException(NO_EXISTE_EL_AREA + pIdArea);
				}
			}
		}

		if (data.getAmbito() == TypeAmbito.ENTIDAD) {

			if (idEntidad == null) {
				throw new FaltanDatosException(FALTA_ENTIDAD);
			} else {
				jEntidad = entityManager.find(JEntidad.class, idEntidad);
				if (jEntidad == null) {
					throw new FaltanDatosException(NO_EXISTE_LA_ENTIDAD + idEntidad);
				}
			}
		}



		final JConfiguracionAutenticacion jConfiguracionAutenticacion = JConfiguracionAutenticacion
				.fromModel(data);
		jConfiguracionAutenticacion.setArea(jArea);
		jConfiguracionAutenticacion.setEntidad(jEntidad);
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
	public List<ConfiguracionAutenticacion> getAllByFiltro(final TypeAmbito ambito, final Long idArea, final Long idEntidad, final TypeIdioma pIdioma,
			final String pFiltro) {
		if (ambito == null) {
			throw new FaltanDatosException(FALTA_AMBITO);
		}

		if (idArea == null && ambito == TypeAmbito.AREA) {
			throw new FaltanDatosException(FALTA_AREA);
		}

		if (idEntidad == null && ambito == TypeAmbito.ENTIDAD) {
			throw new FaltanDatosException(FALTA_ENTIDAD);
		}

		return listarConfiguracionAutenticacion(ambito, idArea, idEntidad, pIdioma, pFiltro);
	}


	/**
	 * Listar avisos.
	 *
	 * @param idArea idArea
	 * @param pIdioma    idioma
	 * @param pFiltro    filtro
	 * @return Listado de avisos
	 */
	@SuppressWarnings("unchecked")
	private List<ConfiguracionAutenticacion> listarConfiguracionAutenticacion(final TypeAmbito ambito, final Long idArea, final Long idEntidad, final TypeIdioma pIdioma,
			final String pFiltro) {
		final List<ConfiguracionAutenticacion> listaConfiguraciones = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select a from JConfiguracionAutenticacion as a");

		sql.append( " where a.ambito = :ambito ");
		if (ambito == TypeAmbito.AREA) {
			sql.append(" AND a.area.codigo = :idArea ");
		}
		if (ambito == TypeAmbito.ENTIDAD) {
			sql.append(" AND a.entidad.codigo = :idEntidad ");
		}
		if (StringUtils.isNotBlank(pFiltro)) {
			sql .append( "  AND (LOWER(a.descripcion) LIKE :filtro OR LOWER(a.identificador) LIKE :filtro) ");
		}
		sql.append("  order by a.identificador, a.codigo");

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("ambito", ambito.toString());

		if (ambito == TypeAmbito.AREA) {
			query.setParameter("idArea", idArea);
		}
		if (ambito == TypeAmbito.ENTIDAD) {
			query.setParameter("idEntidad", idEntidad);
		}

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
	public boolean existeConfiguracionAutenticacion(TypeAmbito ambito, String identificador, Long codigoEntidad, Long codigoArea, Long codigoConfAut) {
		Query query = getQuery(true, ambito, identificador, codigoEntidad, codigoArea, codigoConfAut);
		final Long cuantos = (Long) query.getSingleResult();
		return cuantos != 0l;
	}

	@Override
	public ConfiguracionAutenticacion getConfiguracionAutenticacion(TypeAmbito ambito, String identificador, Long codigoEntidad, Long codigoArea, Long codigoConfAut) {
		Query query = getQuery(false, ambito, identificador, codigoEntidad, codigoArea, codigoConfAut);
		final List<JConfiguracionAutenticacion> confAuts = query.getResultList();
		final ConfiguracionAutenticacion confAut;
		if (confAuts == null || confAuts.isEmpty()) {
			confAut = null;
		} else {
			confAut = confAuts.get(0).toModel();
		}
		return confAut;
	}

	private Query getQuery (boolean isTotal, TypeAmbito ambito, String identificador, Long codigoEntidad, Long codigoArea, Long codigoConfAut) {
		final StringBuilder sql = new StringBuilder("select ");
		if (isTotal) {
			sql.append(" count(d) ");
		} else {
			sql.append(" d ");
		}
		sql.append(" from JConfiguracionAutenticacion d where d.ambito like :ambito ");
		if (ambito == TypeAmbito.AREA) {
			sql.append(" AND d.area.codigo = :codigoArea");
		}
		if (ambito == TypeAmbito.ENTIDAD) {
			sql.append(" AND d.entidad.codigo = :codigoEntidad");
		}
		if (identificador != null && !identificador.isEmpty()) {
			sql.append(" AND d.identificador = :identificador");
		}
		if (codigoConfAut != null) {
			sql.append(" AND d.codigo != :codigoConfAut");
		}

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("ambito", ambito.toString());
		if (ambito == TypeAmbito.AREA) {
			query.setParameter("codigoArea", codigoArea);
		}
		if (ambito == TypeAmbito.ENTIDAD) {
			query.setParameter("codigoEntidad", codigoEntidad);
		}
		if (identificador != null && !identificador.isEmpty()) {
			query.setParameter("identificador", identificador);
		}
		if (codigoConfAut != null) {
			query.setParameter("codigoConfAut", codigoConfAut);
		}


		return query;
	}

	@Override
	public List<ConsultaGeneral> listar(String filtro, TypeIdioma idioma, Long idEntidad, Long idArea,
			boolean checkAmbitoGlobal, boolean checkAmbitoEntidad, boolean checkAmbitoArea) {
		final List<ConsultaGeneral> listaConfiguraciones = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select a from JConfiguracionAutenticacion as a");

		sql.append(" where a.ambito in (:ambitos)");
		if (StringUtils.isNotBlank(filtro)) {
			sql .append( "  AND (LOWER(a.descripcion) LIKE :filtro OR LOWER(a.identificador) LIKE :filtro) ");
		}
		sql .append( "  order by a.identificador, a.codigo");

		List<String> ambitos = new ArrayList<>();
		if (checkAmbitoGlobal) { ambitos.add(TypeAmbito.GLOBAL.toString()); }
		if (checkAmbitoEntidad) { ambitos.add(TypeAmbito.ENTIDAD.toString()); }
		if (checkAmbitoArea) { ambitos.add(TypeAmbito.AREA.toString()); }

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("ambitos", ambitos);

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		final List<JConfiguracionAutenticacion> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			for (final JConfiguracionAutenticacion jConfiguracionAutenticacion : results) {
				listaConfiguraciones.add(jConfiguracionAutenticacion.toModelConsultaGeneral());
			}
		}
		return listaConfiguraciones;
	}

}

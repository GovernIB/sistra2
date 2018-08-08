package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.core.service.repository.model.JArea;
import es.caib.sistrages.core.service.repository.model.JDominio;
import es.caib.sistrages.core.service.repository.model.JEntidad;
import es.caib.sistrages.core.service.repository.model.JFuenteDatos;
import es.caib.sistrages.core.service.repository.model.JVersionTramite;

/**
 * La clase DominioDaoImpl.
 */
@Repository("dominioDao")
public class DominioDaoImpl implements DominioDao {

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private FuenteDatoDao fuenteDatoDao;

	/**
	 * Crea una nueva instancia de DominioDaoImpl.
	 */
	public DominioDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.DominioDao#getById(java.lang.
	 * Long)
	 */
	@Override
	public Dominio getByCodigo(final Long codDominio) {
		Dominio dominio = null;
		final JDominio hdominio = entityManager.find(JDominio.class, codDominio);
		if (hdominio != null) {
			// Establecemos datos
			dominio = hdominio.toModel();
		}
		return dominio;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Dominio getByIdentificador(final String identificadorDominio) {
		Dominio result = null;
		final String sql = "SELECT d FROM JDominio d where d.identificador = :codigoDominio";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("codigoDominio", identificadorDominio);
		final List<JDominio> list = query.getResultList();
		if (!list.isEmpty()) {
			result = list.get(0).toModel();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.DominioDao#add(es.caib.
	 * sistrages.core.api.model.Dominio, java.lang.Long, java.lang.Long)
	 */
	@Override
	public void add(final Dominio dominio, final Long idEntidad, final Long idArea) {
		// Añade dominio por superadministrador estableciendo datos minimos
		final JDominio jDominio = new JDominio();
		jDominio.fromModel(dominio);
		if (dominio.getIdFuenteDatos() != null) {
			final JFuenteDatos jFuenteDatos = entityManager.find(JFuenteDatos.class, dominio.getIdFuenteDatos());
			jDominio.setFuenteDatos(jFuenteDatos);
		}

		if (idEntidad != null) {
			final JEntidad jentidad = entityManager.find(JEntidad.class, idEntidad);
			if (jDominio.getEntidades() == null) {
				jDominio.setEntidades(new HashSet<>());
			}
			jDominio.getEntidades().add(jentidad);
		}
		if (idArea != null) {
			final JArea jarea = entityManager.find(JArea.class, idArea);
			if (jDominio.getAreas() == null) {
				jDominio.setAreas(new HashSet<>());
			}
			jDominio.getAreas().add(jarea);
		}
		entityManager.persist(jDominio);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.DominioDao#remove(java.lang.
	 * Long)
	 */
	@Override
	public void remove(final Long idDominio) {
		final JDominio hdominio = entityManager.find(JDominio.class, idDominio);
		if (hdominio != null) {
			entityManager.remove(hdominio);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.DominioDao#getAllByFiltro(es.
	 * caib.sistrages.core.api.model.types.TypeAmbito, java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	public List<Dominio> getAllByFiltro(final TypeAmbito ambito, final Long id, final String filtro) {
		return listarDominios(ambito, id, filtro);
	}

	@Override
	public List<Dominio> getAllByFiltro(final Long idTramite, final String filtro) {
		return listarDominios(idTramite, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.DominioDao#getAll(es.caib.
	 * sistrages.core.api.model.types.TypeAmbito, java.lang.Long)
	 */
	@Override
	public List<Dominio> getAll(final TypeAmbito ambito, final Long id) {
		return listarDominios(ambito, id, null);
	}

	/**
	 * Listar dominios.
	 *
	 * @param ambito
	 *            ambito
	 * @param id
	 *            id
	 * @param filtro
	 *            filtro
	 * @return lista de dominios
	 */
	private List<Dominio> listarDominios(final TypeAmbito ambito, final Long id, final String filtro) {
		final List<Dominio> dominioes = new ArrayList<>();

		final List<JDominio> results = listarJDominios(ambito, id, filtro);

		if (results != null && !results.isEmpty()) {
			for (final JDominio jdominio : results) {
				final Dominio dominio = jdominio.toModel();
				dominioes.add(dominio);
			}
		}

		return dominioes;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.DominioDao#updateDominio(es.
	 * caib.sistrages.core.api.model.Dominio)
	 */
	@Override
	public void updateDominio(final Dominio dominio) {
		final JDominio jdominio = entityManager.find(JDominio.class, dominio.getCodigo());
		jdominio.fromModel(dominio);
		if (dominio.getIdFuenteDatos() != null) {
			final JFuenteDatos jFuenteDatos = entityManager.find(JFuenteDatos.class, dominio.getIdFuenteDatos());
			jdominio.setFuenteDatos(jFuenteDatos);
		}
		entityManager.merge(jdominio);
	}

	@Override
	public void removeByEntidad(final Long idEntidad) {
		final List<JDominio> dominios = listarJDominios(TypeAmbito.ENTIDAD, idEntidad, null);
		for (final JDominio d : dominios) {
			remove(d.getCodigo());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dominio> getAllByFuenteDatos(final Long idFuenteDatos) {
		final List<Dominio> result = new ArrayList<>();
		final String sql = "SELECT d FROM JDominio d where d.fuenteDatos.codigo = :idFuenteDatos";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idFuenteDatos", idFuenteDatos);
		final List<JDominio> list = query.getResultList();
		for (final JDominio d : list) {
			result.add(d.toModel());
		}
		return result;
	}

	@Override
	public void removeByArea(final Long idArea) {
		final List<JDominio> dominios = listarJDominios(TypeAmbito.AREA, idArea, null);
		for (final JDominio d : dominios) {
			remove(d.getCodigo());
		}
	}

	/**
	 * Lista dominios.
	 *
	 * @param ambito
	 *            Ambito
	 * @param id
	 *            id
	 * @param filtro
	 *            filtro
	 * @return dominios
	 */
	@SuppressWarnings("unchecked")
	private List<JDominio> listarJDominios(final TypeAmbito ambito, final Long id, final String filtro) {
		String sql = "SELECT DISTINCT d FROM JDominio d ";

		if (ambito == TypeAmbito.AREA) {
			sql += " JOIN d.areas area WHERE area.id = :id AND d.ambito = '" + TypeAmbito.AREA + "'";
		} else if (ambito == TypeAmbito.ENTIDAD) {
			sql += " JOIN d.entidades ent WHERE ent.id = :id AND d.ambito = '" + TypeAmbito.ENTIDAD + "'";
		} else if (ambito == TypeAmbito.GLOBAL) {
			sql += " WHERE d.ambito = '" + TypeAmbito.GLOBAL + "'";
		}

		if (StringUtils.isNotBlank(filtro)) {
			sql += " AND (LOWER(d.descripcion) LIKE :filtro OR LOWER(d.identificador) LIKE :filtro)";
		}

		sql += " ORDER BY d.identificador";

		final Query query = entityManager.createQuery(sql);

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		if (id != null) {
			query.setParameter("id", id);
		}

		return query.getResultList();
	}

	@Override
	public void addTramiteVersion(final Long idDominio, final Long idTramiteVersion) {
		gestionTramiteVersion(idDominio, idTramiteVersion, true);
	}

	@Override
	public void removeTramiteVersion(final Long idDominio, final Long idTramiteVersion) {
		gestionTramiteVersion(idDominio, idTramiteVersion, false);
	}

	/**
	 * Gestiona (anyade/borra) la relación entre un trámite versión y un dominio.
	 *
	 * @param idDominio
	 * @param idTramiteVersion
	 * @param anyadir
	 */
	private void gestionTramiteVersion(final Long idDominio, final Long idTramiteVersion, final boolean anyadir) {
		final JDominio jdominio = entityManager.find(JDominio.class, idDominio);
		final JVersionTramite jversionTramite = entityManager.find(JVersionTramite.class, idTramiteVersion);

		if (jdominio == null) {
			throw new FaltanDatosException("Falta el dominio");
		}

		if (jversionTramite == null) {
			throw new FaltanDatosException("Falta el version tramite");
		}

		if (anyadir) {

			jdominio.getVersionesTramite().add(jversionTramite);

		} else {

			jdominio.getVersionesTramite().remove(jversionTramite);

		}

		entityManager.merge(jdominio);

	}

	@Override
	public boolean tieneTramiteVersion(final Long idDominio, final Long idTramiteVersion) {
		final JDominio jdominio = entityManager.find(JDominio.class, idDominio);
		final JVersionTramite jversionTramite = entityManager.find(JVersionTramite.class, idTramiteVersion);

		if (jdominio == null) {
			throw new FaltanDatosException("Falta el dominio");
		}

		if (jversionTramite == null) {
			throw new FaltanDatosException("Falta el version tramite");
		}

		return jdominio.getVersionesTramite().contains(jversionTramite);
	}

	private List<Dominio> listarDominios(final Long idTramite, final String filtro) {
		final List<Dominio> listaDominios = new ArrayList<>();

		final List<JDominio> results = listarJDominios(idTramite, filtro);

		if (results != null && !results.isEmpty()) {
			for (final JDominio jdominio : results) {
				final Dominio dominio = jdominio.toModel();
				listaDominios.add(dominio);
			}
		}

		return listaDominios;
	}

	/**
	 * Listar dominios.
	 *
	 * @param idTramite
	 *            idtramite
	 * @param filtro
	 *            filtro
	 * @return lista de dominios
	 */
	@SuppressWarnings("unchecked")
	private List<JDominio> listarJDominios(final Long idTramite, final String filtro) {
		String sql = "select distinct d"
				+ " from JDominio d LEFT JOIN d.areas area on d.ambito = 'A' left join d.entidades entidad on d.ambito = 'E', JTramite t "
				+ " where t.codigo = :idTramite and (d.ambito = 'G' or" + " (d.ambito = 'A' and area = t.area ) or"
				+ " (d.ambito = 'E' and entidad = t.area.entidad) )";

		if (StringUtils.isNotBlank(filtro)) {
			sql += " AND (LOWER(d.descripcion) LIKE :filtro OR LOWER(d.identificador) LIKE :filtro)";
		}

		sql += " ORDER BY d.identificador";

		final Query query = entityManager.createQuery(sql);

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		if (idTramite != null) {
			query.setParameter("idTramite", idTramite);
		}

		return query.getResultList();
	}

	/***
	 * Funcionamiento del método: <br />
	 * - Si la acción tomada es mantener, no realizamos nada. - Si la acción tomada
	 * es reemplazar: <br />
	 * - Si existe, aprovechamos el mismo dominio y actualizamos los parámetros.
	 * <br />
	 * - Si no existe, lo creamos y actualizamos los params.<br />
	 *
	 * @throws Exception
	 */
	@Override
	public Long importar(final FilaImportarDominio filaDominio) throws Exception {
		// Si es reemplazar, hacemos la acción.
		if (filaDominio.getAccion() == TypeImportarAccion.REEMPLAZAR) {
			JDominio dominioAlmacenar;
			if (filaDominio.getDominioActual() == null) { // No existe Dominio

				dominioAlmacenar = JDominio.fromModelStatic(filaDominio.getDominio());
				dominioAlmacenar.setCodigo(null);

				if (filaDominio.getDominio().getAmbito() == TypeAmbito.AREA) {

					final JArea jArea = entityManager.find(JArea.class, filaDominio.getIdArea());
					final Set<JArea> areas = new HashSet<>(0);
					areas.add(jArea);
					dominioAlmacenar.setAreas(areas);
				}

				if (filaDominio.getDominio().getAmbito() == TypeAmbito.ENTIDAD) {
					final JEntidad jEntidad = entityManager.find(JEntidad.class, filaDominio.getIdEntidad());
					final Set<JEntidad> entidades = new HashSet<>(0);
					entidades.add(jEntidad);
					dominioAlmacenar.setEntidades(entidades);
				}

			} else { // Existe el dominio.

				dominioAlmacenar = entityManager.find(JDominio.class, filaDominio.getDominioActual().getCodigo());

			}

			// Tratamos la fuente de datos
			if (filaDominio.getDominio().getTipo() == TypeDominio.FUENTE_DATOS) {
				final Long idFuenteDatos = fuenteDatoDao.importarFD(filaDominio);
				final JFuenteDatos jfuenteDatos = entityManager.find(JFuenteDatos.class, idFuenteDatos);
				dominioAlmacenar.setFuenteDatos(jfuenteDatos);
			} else {
				dominioAlmacenar.setFuenteDatos(null);
			}

			// Actualizamos los tipos
			switch (filaDominio.getDominio().getTipo()) {
			case CONSULTA_BD:
				dominioAlmacenar.setDatasourceJndi(filaDominio.getResultadoJndi());
				dominioAlmacenar.setSql(JDominio.decodeSql(filaDominio.getResultadoSQL()));
				break;
			case CONSULTA_REMOTA:
				dominioAlmacenar.setServicioRemotoUrl(filaDominio.getResultadoURL());
				break;
			case FUENTE_DATOS:
				dominioAlmacenar.setSql(JDominio.decodeSql(filaDominio.getResultadoSQL()));
				break;
			case LISTA_FIJA:
				dominioAlmacenar.setListaFijaValores(filaDominio.getResultadoLista());
				break;
			}
			// Actualizamos los params
			dominioAlmacenar.setParametros(UtilJSON.toJSON(filaDominio.getDominio().getParametros()));

			entityManager.merge(dominioAlmacenar);
			return dominioAlmacenar.getCodigo();
		} else {
			return filaDominio.getDominioActual().getCodigo();
		}
	}

}

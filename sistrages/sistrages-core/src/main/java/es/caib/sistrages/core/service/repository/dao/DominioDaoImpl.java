package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.ConsultaGeneral;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.comun.ConstantesDominio;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.comun.ValorIdentificadorCompuesto;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.core.service.repository.model.JArea;
import es.caib.sistrages.core.service.repository.model.JConfiguracionAutenticacion;
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

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.DominioDao#add(es.caib.
	 * sistrages.core.api.model.Dominio, java.lang.Long, java.lang.Long)
	 */
	@Override
	public Long add(final Dominio dominio, final Long idEntidad, final Long idArea) {
		// Añade dominio por superadministrador estableciendo datos minimos
		final JDominio jDominio = new JDominio();
		jDominio.fromModel(dominio);
		if (dominio.getIdFuenteDatos() != null) {
			final JFuenteDatos jFuenteDatos = entityManager.find(JFuenteDatos.class, dominio.getIdFuenteDatos());
			jDominio.setFuenteDatos(jFuenteDatos);
		}

		if (idEntidad != null) {
			final JEntidad jentidad = entityManager.find(JEntidad.class, idEntidad);
			jDominio.setEntidad(jentidad);
		}
		if (idArea != null) {
			final JArea jarea = entityManager.find(JArea.class, idArea);
			jDominio.setArea(jarea);
		}
		entityManager.persist(jDominio);
		return jDominio.getCodigo();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.DominioDao#remove(java.lang.
	 * Long)
	 */
	@Override
	public boolean remove(final Long idDominio) {
		boolean retorno;
		final boolean existeComp = isComponenteConDominio(idDominio);
		if (existeComp) {
			retorno = false;
		} else {

			final JDominio hdominio = entityManager.find(JDominio.class, idDominio);
			if (hdominio == null) {
				// No existe
				retorno = false;
			} else {
				if (hdominio.getVersionesTramite() != null && !hdominio.getVersionesTramite().isEmpty()) {
					// Si tiene versiones tramites asociadas, no se borra
					retorno = false;
				} else {
					// Si no tiene relaciones, se borra correctamente
					entityManager.remove(hdominio);
					retorno = true;
				}
			}

		}
		return retorno;
	}

	/**
	 *
	 * @param idDominio
	 * @return
	 */
	private boolean isComponenteConDominio(final Long idDominio) {
		final String sql = "SELECT DISTINCT c FROM JCampoFormularioIndexado c where c.dominio.codigo = " + idDominio;
		final Query query = entityManager.createQuery(sql);
		final List<JDominio> lista = query.getResultList();
		return !lista.isEmpty();
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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.DominioDao#getAllByFiltro(es.
	 * caib.sistrages.core.api.model.types.TypeAmbito, java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	public List<Dominio> getAllByFiltro(final List<TypeAmbito> ambitos, final Long id, final String filtro) {
		return listarDominios(ambitos, id, filtro);
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
	 * @param ambito ambito
	 * @param id     id
	 * @param filtro filtro
	 * @return lista de dominios
	 */
	private List<Dominio> listarDominios(final TypeAmbito ambito, final Long id, final String filtro) {
		final List<Dominio> dominioes = new ArrayList<>();

		List<TypeAmbito> ambitos = new ArrayList<>();
		ambitos.add(ambito);
		final List<JDominio> results = listarJDominios(ambitos, id, filtro);

		if (results != null && !results.isEmpty()) {
			for (final JDominio jdominio : results) {
				final Dominio dominio = jdominio.toModel();
				dominioes.add(dominio);
			}
		}

		return dominioes;
	}


	/**
	 * Listar dominios.
	 *
	 * @param ambito ambito
	 * @param id     id
	 * @param filtro filtro
	 * @return lista de dominios
	 */
	private List<Dominio> listarDominios(final List<TypeAmbito> ambitos, final Long id, final String filtro) {
		final List<Dominio> dominioes = new ArrayList<>();
		final List<JDominio> results = listarJDominios(ambitos, id, filtro);

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
		List<TypeAmbito> ambitos = new ArrayList<>();
		ambitos.add(TypeAmbito.ENTIDAD);
		final List<JDominio> dominios = listarJDominios(ambitos, idEntidad, null);
		for (final JDominio d : dominios) {
			remove(d.getCodigo());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllByFuenteDatos(final Long idFuenteDatos) {
		final List<String> result = new ArrayList<>();
		final String sql = "SELECT d FROM JDominio d where d.fuenteDatos.codigo = :idFuenteDatos";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idFuenteDatos", idFuenteDatos);
		final List<JDominio> list = query.getResultList();
		for (final JDominio d : list) {
			result.add(d.toModel().getIdentificadorCompuesto());
		}
		return result;
	}

	@Override
	public void removeByArea(final Long idArea) {
		List<TypeAmbito> ambitos = new ArrayList<>();
		ambitos.add(TypeAmbito.AREA);
		final List<JDominio> dominios = listarJDominios(ambitos, idArea, null);
		for (final JDominio d : dominios) {
			remove(d.getCodigo());
		}
	}

	/**
	 * Lista dominios.
	 *
	 * @param ambito Ambito
	 * @param id     id
	 * @param filtro filtro
	 * @return dominios
	 */
	@SuppressWarnings("unchecked")
	private List<JDominio> listarJDominios(final List<TypeAmbito> ambitos, final Long id, final String filtro) {
		StringBuilder sql = new StringBuilder("SELECT DISTINCT d FROM JDominio d WHERE 1 = 1 ");
		if (StringUtils.isNotBlank(filtro)) {
			sql.append(" AND (LOWER(d.descripcion) LIKE :filtro OR LOWER(d.identificador) LIKE :filtro)");
		}

		if (ambitos != null && !ambitos.isEmpty()) {

			sql.append(" AND ");
			if (ambitos.size() >= 2) {
				sql.append(" ( ");
			}

			if (ambitos.contains(TypeAmbito.AREA)) {

				sql.append( " ( d.area.id = :id AND d.ambito = '" + TypeAmbito.AREA + "' ) ");

				if (ambitos.contains(TypeAmbito.ENTIDAD) || ambitos.contains(TypeAmbito.GLOBAL)) {
					sql.append( " OR ");
				}
			}
			if (ambitos.contains(TypeAmbito.ENTIDAD)) {

				sql.append( " ( d.entidad.id = :id AND d.ambito = '" + TypeAmbito.ENTIDAD + "' ) ");

				if (ambitos.contains(TypeAmbito.GLOBAL)) {
					sql.append( " OR ");
				}
			}
			if (ambitos.contains(TypeAmbito.GLOBAL)) {

				sql.append( " ( d.ambito = '" + TypeAmbito.GLOBAL + "' ) ");
			}

			if (ambitos.size() >= 2) {
				sql.append(" ) ");
			}
		}


		sql.append( " ORDER BY d.identificador");

		final Query query = entityManager.createQuery(sql.toString());

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		if (id != null) {
			query.setParameter("id", id);
		}

		return query.getResultList();
	}

	@Override
	public List<ConsultaGeneral> listar(String filtro, TypeIdioma idioma, Long idEntidad, Long idArea,
			boolean checkAmbitoGlobal, boolean checkAmbitoEntidad, boolean checkAmbitoArea) {
		StringBuilder sql = new StringBuilder("SELECT DISTINCT d FROM JDominio d  ");

		sql.append(" WHERE d.ambito IN (:ambitos) ");
		if (StringUtils.isNotBlank(filtro)) {
			sql.append(" AND (LOWER(d.descripcion) LIKE :filtro OR LOWER(d.identificador) LIKE :filtro)");
		}
		List<String> ambitos = new ArrayList<>();
		if (checkAmbitoGlobal) {
			ambitos.add(TypeAmbito.GLOBAL.toString());
		}
		if (checkAmbitoEntidad) {
			ambitos.add(TypeAmbito.ENTIDAD.toString());
		}
		if (checkAmbitoArea) {
			ambitos.add(TypeAmbito.AREA.toString());
		}
		final Query query = entityManager.createQuery(sql.toString());

		query.setParameter("ambitos", ambitos);
		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		List<JDominio> jdominios = query.getResultList();
		List<ConsultaGeneral> datos = new ArrayList<>();
		if (jdominios != null) {
			for (JDominio jdominio : jdominios) {
				datos.add(jdominio.toModelConsultaGeneral());
			}
		}

		return datos;
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
	 * @param idTramite idtramite
	 * @param filtro    filtro
	 * @return lista de dominios
	 */
	@SuppressWarnings("unchecked")
	private List<JDominio> listarJDominios(final Long idTramite, final String filtro) {
		String sql = "select distinct d" + " from JDominio d, JTramite t "
				+ " where t.codigo = :idTramite and (d.ambito = 'G' or" + " (d.ambito = 'A' and d.area = t.area ) or"
				+ " (d.ambito = 'E' and d.entidad = t.area.entidad) )";

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
	public Long importar(final FilaImportarDominio filaDominio, final Long idEntidad, final Long idArea,
			final JFuenteDatos jfuenteDatos) {

		// Si es reemplazar, hacemos la acción.
		if (filaDominio.getAccion() == TypeImportarAccion.CREAR) {

			JDominio dominioAlmacenar = JDominio.fromModelStatic(filaDominio.getDominio());
			dominioAlmacenar.setCodigo(null);
			dominioAlmacenar.setConfiguracionAutenticacion(null);
			dominioAlmacenar.setArea(null);
			dominioAlmacenar.setEntidad(null);

			if (filaDominio.getDominio().getAmbito() == TypeAmbito.AREA) {

					final JArea jArea = entityManager.find(JArea.class, idArea);
					dominioAlmacenar.setArea(jArea);
			}

			if (filaDominio.getDominio().getAmbito() == TypeAmbito.ENTIDAD) {
					final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
					dominioAlmacenar.setEntidad(jEntidad);
			}

			// Seteamos la fuente de datos (estara a null si no es de tipo Fuente datos)
			dominioAlmacenar.setFuenteDatos(jfuenteDatos);

			// Actualizamos los tipos
			switch (filaDominio.getDominio().getTipo()) {
			case CONSULTA_BD:
				dominioAlmacenar.setDatasourceJndi(filaDominio.getResultadoJndi());
				dominioAlmacenar.setSql(JDominio.decodeSql(filaDominio.getResultadoSQL()));
				break;
			case CONSULTA_REMOTA:
				dominioAlmacenar.setServicioRemotoUrl(filaDominio.getResultadoURL());
				if (filaDominio.getConfiguracionAutenticacionActual() == null
						|| filaDominio.getConfiguracionAutenticacionActual().getIdentificador()
								.equals("Sense autenticació")
						|| filaDominio.getConfiguracionAutenticacionActual().getIdentificador()
								.equals("Sin autenticación")) {
					dominioAlmacenar.setConfiguracionAutenticacion(null);
				} else {
					if (filaDominio.getConfiguracionAutenticacionActual().getCodigo() == null) {
						JConfiguracionAutenticacion config = JConfiguracionAutenticacion
								.fromModel(filaDominio.getConfiguracionAutenticacionActual());
						if (TypeAmbito.ENTIDAD.toString().equals(dominioAlmacenar.getAmbito())) {
							JEntidad jentidad = entityManager.find(JEntidad.class, idEntidad);
							config.setEntidad(jentidad);
							config.setAmbito(TypeAmbito.ENTIDAD.toString());
						}
						if (TypeAmbito.AREA.toString().equals(dominioAlmacenar.getAmbito())) {
							JArea jarea = entityManager.find(JArea.class, idArea);
							config.setArea(jarea);
							config.setAmbito(TypeAmbito.AREA.toString());
						} else {
							config.setAmbito(TypeAmbito.GLOBAL.toString());
						}
						entityManager.persist(config);
						entityManager.flush();
						dominioAlmacenar.setConfiguracionAutenticacion(config);
					} else {
						JConfiguracionAutenticacion config = entityManager.find(JConfiguracionAutenticacion.class,
								filaDominio.getConfiguracionAutenticacionActual().getCodigo());
						dominioAlmacenar.setConfiguracionAutenticacion(config);
					}
				}
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
			// Actualizamos el tipo de cache
			dominioAlmacenar.setCacheo(filaDominio.getDominio().getCache().toString());
			entityManager.persist(dominioAlmacenar);

			return dominioAlmacenar.getCodigo();


		} else if (filaDominio.getAccion() == TypeImportarAccion.REEMPLAZAR) {

			JDominio dominioAlmacenar;
			if (filaDominio.getDominioActual() == null) { // No existe Dominio

				dominioAlmacenar = JDominio.fromModelStatic(filaDominio.getDominio());
				dominioAlmacenar.setCodigo(null);
				dominioAlmacenar.setConfiguracionAutenticacion(null);
				dominioAlmacenar.setArea(null);
				dominioAlmacenar.setEntidad(null);

				if (filaDominio.getDominio().getAmbito() == TypeAmbito.AREA) {

					final JArea jArea = entityManager.find(JArea.class, idArea);
					dominioAlmacenar.setArea(jArea);
				}

				if (filaDominio.getDominio().getAmbito() == TypeAmbito.ENTIDAD) {
					final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
					dominioAlmacenar.setEntidad(jEntidad);
				}

			} else { // Existe el dominio.

				dominioAlmacenar = entityManager.find(JDominio.class, filaDominio.getDominioActual().getCodigo());

			}

			// Seteamos la fuente de datos (estara a null si no es de tipo Fuente datos)
			dominioAlmacenar.setFuenteDatos(jfuenteDatos);

			// Actualizamos los tipos
			switch (filaDominio.getDominio().getTipo()) {
			case CONSULTA_BD:
				dominioAlmacenar.setDatasourceJndi(filaDominio.getResultadoJndi());
				dominioAlmacenar.setSql(JDominio.decodeSql(filaDominio.getResultadoSQL()));
				break;
			case CONSULTA_REMOTA:
				dominioAlmacenar.setServicioRemotoUrl(filaDominio.getResultadoURL());
				if (filaDominio.getConfiguracionAutenticacionActual() == null
						|| filaDominio.getConfiguracionAutenticacionActual().getIdentificador()
								.equals("Sense autenticació")
						|| filaDominio.getConfiguracionAutenticacionActual().getIdentificador()
								.equals("Sin autenticación")) {
					dominioAlmacenar.setConfiguracionAutenticacion(null);
				} else {
					if (filaDominio.getConfiguracionAutenticacionActual().getCodigo() == null) {
						JConfiguracionAutenticacion config = JConfiguracionAutenticacion
								.fromModel(filaDominio.getConfiguracionAutenticacionActual());
						if (TypeAmbito.ENTIDAD.toString().equals(dominioAlmacenar.getAmbito())) {
							JEntidad jentidad = entityManager.find(JEntidad.class, idEntidad);
							config.setEntidad(jentidad);
							config.setAmbito(TypeAmbito.ENTIDAD.toString());
						}
						if (TypeAmbito.AREA.toString().equals(dominioAlmacenar.getAmbito())) {
							JArea jarea = entityManager.find(JArea.class, idArea);
							config.setArea(jarea);
							config.setAmbito(TypeAmbito.AREA.toString());
						} else {
							config.setAmbito(TypeAmbito.GLOBAL.toString());
						}
						entityManager.persist(config);
						entityManager.flush();
						dominioAlmacenar.setConfiguracionAutenticacion(config);
					} else {
						JConfiguracionAutenticacion config = entityManager.find(JConfiguracionAutenticacion.class,
								filaDominio.getConfiguracionAutenticacionActual().getCodigo());
						dominioAlmacenar.setConfiguracionAutenticacion(config);
					}
				}
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
			// Actualizamos el tipo de cache
			dominioAlmacenar.setCacheo(filaDominio.getDominio().getCache().toString());
			if (dominioAlmacenar.getCodigo() == null) {
				entityManager.persist(dominioAlmacenar);
			} else {
				entityManager.merge(dominioAlmacenar);
			}

			return dominioAlmacenar.getCodigo();
		} else { // MANTENER
			return filaDominio.getDominioActual().getCodigo();
		}
	}

	/**
	 * Limpiamos los campos id del dominio y, en caso de pasarse area y fd, se
	 * asocian.
	 */
	@Override
	public void clonar(final String dominioID, final String nuevoIdentificador, final Long areaID,
			final Long idEntidad, final FuenteDatos fd, final ConfiguracionAutenticacion confAut) {

		JArea jareas = null;
		JFuenteDatos jfuenteDatos = null;
		JConfiguracionAutenticacion jconfAut = null;
		if (areaID != null) {
			jareas = entityManager.find(JArea.class, areaID);
		}
		if (fd != null) {
			jfuenteDatos = entityManager.find(JFuenteDatos.class, fd.getCodigo());

		}
		if (confAut != null) {
			jconfAut =  entityManager.find(JConfiguracionAutenticacion.class, confAut.getCodigo());
		}

		JEntidad jentidad = null;
		if (idEntidad != null) {
			jentidad = entityManager.find(JEntidad.class, idEntidad);
		}

		final JDominio hdominio = JDominio.clonar(entityManager.find(JDominio.class, Long.valueOf(dominioID)) ,
				nuevoIdentificador, jareas, jfuenteDatos, jentidad, jconfAut);
		entityManager.persist(hdominio);
	}

	/**
	 * Obtiene el valores dominio.
	 */
	@Override
	public ValoresDominio realizarConsultaListaFija(final TypeAmbito ambito, final Long codigoEntidad,
			final Long codigoArea, final String identificador, String identificadorEntidad, String identificadorArea) {
		final ValoresDominio valoresDominio = new ValoresDominio();
		final Dominio dominio = this.getDominioByIdentificadorPrivado(ambito, identificador, identificadorEntidad,
				identificadorArea, codigoEntidad, codigoArea, null);
		for (final Propiedad prop : dominio.getListaFija()) {
			final int fila = valoresDominio.addFila();
			valoresDominio.setValor(fila, ConstantesDominio.LISTAFIJA_COLUMNA_CODIGO, prop.getCodigo());
			valoresDominio.setValor(fila, ConstantesDominio.LISTAFIJA_COLUMNA_DESCRIPCION, prop.getValor());
		}
		return valoresDominio;
	}

	/**
	 * Recorre todos los dominios asociados a un trámite, sólo nos interesa los de
	 * tipo área. <br />
	 * Posteriormente, recorre todas las versiones que tenga asociadas e introduce
	 * en una lista las versiones que pertezcan al trámite (idTramite).<br />
	 * Luego con la lista (que al menos debería haber uno), borramos las versiones
	 * asociadas al dominio y mergeamos.
	 */
	@Override
	public void borrarReferencias(final Long idTramite, final Long idArea) {

		// Todos los dominios asociados a un tramite
		final List<JDominio> jDominios = getDominioAreaByTramite(idTramite);
		for (final JDominio jdominio : jDominios) {

			// Buscamos las versiones que pertenezcan al trámite
			final List<JVersionTramite> versionesBorrarReferencias = new ArrayList<>();
			for (final JVersionTramite version : jdominio.getVersionesTramite()) {
				if (version.getTramite().getCodigo().compareTo(idTramite) == 0) {
					versionesBorrarReferencias.add(version);
				}
			}

			// Borramos las referencias y mergeamos
			if (!versionesBorrarReferencias.isEmpty()) {
				jdominio.getVersionesTramite().removeAll(versionesBorrarReferencias);
				entityManager.merge(jdominio);
			}

		}
	}

	private List<JDominio> getDominioAreaByTramite(final Long idTramite) {
		final String sql = "select distinct d from JDominio d LEFT JOIN d.versionesTramite versiones where d.ambito = 'A' and versiones.tramite.codigo = :idTramite ORDER BY d.identificador ";

		final Query query = entityManager.createQuery(sql);
		query.setParameter("idTramite", idTramite);

		return query.getResultList();
	}

	@Override
	public List<Dominio> getDominiosByConfAut(TypeAmbito ambito, Long idConfiguracion, Long idArea) {
		final String sql;
		if (ambito.equals(TypeAmbito.AREA)) {
			sql = "select d from JDominio d where d.ambito = :Ambito and d.area.codigo = :idArea and d.configuracionAutenticacion.codigo = :idConf ORDER BY d.identificador ";
		} else if (ambito.equals(TypeAmbito.ENTIDAD)) {
			sql = "select d from JDominio d where d.ambito = :Ambito and d.entidad.codigo = :idArea and d.configuracionAutenticacion.codigo = :idConf ORDER BY d.identificador ";
		} else {
			sql = "select d from JDominio d where d.ambito = :Ambito and d.configuracionAutenticacion.codigo = :idConf ORDER BY d.identificador ";
		}

		final Query query = entityManager.createQuery(sql);
		query.setParameter("idConf", idConfiguracion);
		if (!ambito.equals(TypeAmbito.GLOBAL)) {
			query.setParameter("idArea", idArea);
		}
		query.setParameter("Ambito", ambito.toString());

		final List<JDominio> jDominios = query.getResultList();
		final List<Dominio> dominios = new ArrayList<>();
		for (final JDominio jdominio : jDominios) {
			dominios.add(jdominio.toModel());
		}
		return dominios;
	}

	@Override
	public List<Dominio> getDominiosByIdentificador(List<String> identificadoresDominio, final Long idEntidad,
			final Long idArea) {

		final List<Dominio> dominios = new ArrayList<>();
		for (String identificadorDominio : identificadoresDominio) {
			ValorIdentificadorCompuesto valor = new ValorIdentificadorCompuesto(identificadorDominio);
			Dominio dom = getDominioByIdentificador(valor.getIdentificador(), valor.getAmbito(),
					valor.getIdentificadorEntidad(), valor.getIdentificadorArea());
			if (dom != null) {
				if (dom.getAmbito() == TypeAmbito.GLOBAL) {
					dominios.add(dom);
				} else if (dom.getAmbito() == TypeAmbito.ENTIDAD) {
					// Solo se agregan dominios de la misma entidad
					if (dom.getEntidad().compareTo(idEntidad) == 0) {
						dominios.add(dom);
					}
				} else if (dom.getAmbito() == TypeAmbito.AREA) {
					// Solo se agregan dominios de la misma area
					if (dom.getArea().getCodigo().compareTo(idArea) == 0) {
						dominios.add(dom);
						break;
					}
				}
			}
		}
		return dominios;
	}

	private Dominio getDominioByIdentificador(String identificadorDominio, final TypeAmbito ambito,
			final String idEntidad, final String idArea) {

		final StringBuilder sql = new StringBuilder(
				"select d from JDominio d where d.ambito = :ambito and d.identificador = :identificador ");
		if (ambito == TypeAmbito.ENTIDAD) {
			sql.append(" AND d.entidad.identificador = :idEntidad");
		}
		if (ambito == TypeAmbito.AREA) {
			sql.append(" AND d.area.identificador = :idArea");
			sql.append(" AND d.area.entidad.identificador = :idEntidad");
		}

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("identificador", identificadorDominio);
		query.setParameter("ambito", ambito.toString());
		if (ambito == TypeAmbito.ENTIDAD) {
			query.setParameter("idEntidad", idEntidad);
		}
		if (ambito == TypeAmbito.AREA) {
			query.setParameter("idArea", idArea);
			query.setParameter("idEntidad", idEntidad);
		}

		final List<JDominio> jDominios = query.getResultList();
		Dominio resultado = null;
		if (jDominios != null && !jDominios.isEmpty()) {
			resultado = jDominios.get(0).toModel();

		}
		return resultado;
	}

	@Override
	public boolean existeDominioByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad,
			Long codigoArea, Long codigoDominio) {
		Query query = getQuery(true, ambito, identificador, null, null, codigoEntidad, codigoArea, codigoDominio);
		final Long cuantos = (Long) query.getSingleResult();
		return cuantos != 0l;
	}

	private Query getQuery(boolean isTotal, TypeAmbito ambito, String identificador, String identificadorEntidad,
			String identificadorArea, Long codigoEntidad, Long codigoArea, Long codigoDominio) {
		final StringBuilder sql = new StringBuilder("select ");
		if (isTotal) {
			sql.append(" count(d) ");
		} else {
			sql.append(" d ");
		}
		sql.append(" from JDominio d where d.ambito like :ambito ");
		if (ambito == TypeAmbito.AREA) {
			if (codigoArea != null) {
				sql.append(" AND d.area.codigo = :codigoArea");
			}
			if (identificadorArea != null && !identificadorArea.isEmpty()) {
				sql.append(" AND d.area.identificador = :identificadorArea");
			}
		}
		if (ambito == TypeAmbito.ENTIDAD) {
			if (codigoEntidad != null) {
				sql.append(" AND d.entidad.codigo = :codigoEntidad");
			}
			if (identificadorEntidad != null && !identificadorEntidad.isEmpty()) {
				sql.append(" AND d.entidad.identificador = :identificadorEntidad");
			}

		}
		if (identificador != null && !identificador.isEmpty()) {
			sql.append(" AND d.identificador = :identificador");
		}
		if (codigoDominio != null) {
			sql.append(" AND d.codigo != :codigoDominio");
		}

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("ambito", ambito.toString());
		if (ambito == TypeAmbito.AREA) {

			if (codigoArea != null) {
				query.setParameter("codigoArea", codigoArea);
			}
			if (identificadorArea != null && !identificadorArea.isEmpty()) {
				query.setParameter("identificadorArea", identificadorArea);
			}
		}
		if (ambito == TypeAmbito.ENTIDAD) {
			if (codigoEntidad != null) {
				query.setParameter("codigoEntidad", codigoEntidad);
			}
			if (identificadorEntidad != null && !identificadorEntidad.isEmpty()) {
				query.setParameter("identificadorEntidad", identificadorEntidad);
			}

		}
		if (identificador != null && !identificador.isEmpty()) {
			query.setParameter("identificador", identificador);
		}
		if (codigoDominio != null) {
			query.setParameter("codigoDominio", codigoDominio);
		}

		return query;
	}

	@Override
	public Dominio getDominioByIdentificador(TypeAmbito ambito, String identificador, String identificadorEntidad,
			String identificadorArea, Long codigoEntidad, Long codigoArea, Long codigoDominio) {
		return getDominioByIdentificadorPrivado(ambito, identificador, identificadorEntidad, identificadorArea,
				codigoEntidad, codigoArea, codigoDominio);
	}

	private Dominio getDominioByIdentificadorPrivado(TypeAmbito ambito, String identificador,
			String identificadorEntidad, String identificadorArea, Long codigoEntidad, Long codigoArea,
			Long codigoDominio) {
		Query query = getQuery(false, ambito, identificador, identificadorEntidad, identificadorArea, codigoEntidad,
				codigoArea, codigoDominio);
		final List<JDominio> dominios = query.getResultList();
		final Dominio dominio;
		if (dominios == null || dominios.isEmpty()) {
			dominio = null;
		} else {
			dominio = dominios.get(0).toModel();
		}

		return dominio;
	}

}

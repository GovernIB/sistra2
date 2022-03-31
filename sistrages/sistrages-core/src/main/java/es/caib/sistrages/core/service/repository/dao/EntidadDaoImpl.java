package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.PlantillaEntidad;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.service.repository.model.JArea;
import es.caib.sistrages.core.service.repository.model.JEntidad;
import es.caib.sistrages.core.service.repository.model.JFichero;
import es.caib.sistrages.core.service.repository.model.JIdioma;
import es.caib.sistrages.core.service.repository.model.JLiteral;
import es.caib.sistrages.core.service.repository.model.JPlantillaEntidad;

@Repository("entidadDao")
public class EntidadDaoImpl implements EntidadDao {

	/** EntityManager. */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de EntidadDaoImpl.
	 */
	public EntidadDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#getById(java.lang.
	 * Long)
	 */
	@Override
	public Entidad getById(final Long idEntidad) {
		Entidad entidad = null;
		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		if (jEntidad != null) {
			// Establecemos datos
			entidad = jEntidad.toModel();
		}
		return entidad;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#getById(java.lang.
	 * Long)
	 */

	@Override
	public Entidad getByCodigo(final String codigoDir3) {
		Entidad entidad = null;

		final String sql = "select entidad from JEntidad entidad where entidad.codigoDir3 like '" + codigoDir3 + "'";
		final Query query = entityManager.createQuery(sql);

		final List<JEntidad> results = query.getResultList();

		if (!results.isEmpty()) {
			entidad = results.get(0).toModel();
		}
		return entidad;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#getByArea(java.lang.
	 * Long)
	 */
	@Override
	public Entidad getByArea(final Long idArea) {
		Entidad entidad = null;
		final JArea jarea = entityManager.find(JArea.class, idArea);
		if (jarea != null) {
			// Establecemos datos
			entidad = jarea.getEntidad().toModel();
		}
		return entidad;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.EntidadDao#add(es.caib.
	 * sistrages.core.api.model.Entidad)
	 */
	@Override
	public void add(final Entidad entidad) {
		// Añade entidad por superadministrador estableciendo datos minimos
		final JEntidad jEntidad = new JEntidad();
		jEntidad.setCodigoDir3(entidad.getCodigoDIR3());
		jEntidad.setNombre(JLiteral.fromModel(entidad.getNombre()));
		jEntidad.setActiva(entidad.isActivo());
		jEntidad.setRoleAdministrador(entidad.getRol());
		jEntidad.setRoleSup(entidad.getRolSup());
		jEntidad.setDiasPreregistro(entidad.getDiasPreregistro());
		jEntidad.setIdentificador(entidad.getIdentificador());
		entityManager.persist(jEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#remove(java.lang.
	 * Long)
	 */
	@Override
	public void remove(final Long idEntidad) {
		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad " + idEntidad);
		}
		entityManager.remove(jEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.EntidadDao#
	 * updateSuperAdministrador(es.caib.sistrages.core.api.model.Entidad)
	 */
	@Override
	public void updateSuperAdministrador(final Entidad entidad) {
		// Update entidad por superadministrador estableciendo datos minimos
		final JEntidad jEntidad = entityManager.find(JEntidad.class, entidad.getCodigo());
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad " + entidad.getCodigo());
		}
		jEntidad.setCodigoDir3(entidad.getCodigoDIR3());
		jEntidad.setNombre(JLiteral.mergeModel(jEntidad.getNombre(), entidad.getNombre()));
		jEntidad.setActiva(entidad.isActivo());
		jEntidad.setRoleAdministrador(entidad.getRol());
		jEntidad.setRoleSup(entidad.getRolSup());
		jEntidad.setIdentificador(entidad.getIdentificador());
		entityManager.merge(jEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.EntidadDao#
	 * updateAdministradorEntidad(es.caib.sistrages.core.api.model.Entidad)
	 */
	@Override
	public void updateAdministradorEntidad(final Entidad entidad) {

		final JEntidad jEntidad = entityManager.find(JEntidad.class, entidad.getCodigo());
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad " + entidad.getCodigo());
		}

		jEntidad.setPiePaginaAsistenteTramitacion(
				JLiteral.mergeModel(jEntidad.getPiePaginaAsistenteTramitacion(), entidad.getPie()));
		jEntidad.setLopd(JLiteral.mergeModel(jEntidad.getLopd(), entidad.getLopd()));

		jEntidad.setEmail(entidad.getEmail());
		jEntidad.setContactoEmail(entidad.isEmailHabilitado());
		jEntidad.setContactoTelefono(entidad.isTelefonoHabilitado());
		jEntidad.setTelefono(entidad.getTelefono());
		jEntidad.setContactoUrl(entidad.isUrlSoporteHabilitado());
		jEntidad.setUrlSoporte(entidad.getUrlSoporte());
		jEntidad.setContactoFormularioIncidencias(entidad.isFormularioIncidenciasHabilitado());
		jEntidad.setUrlSede(JLiteral.mergeModel(jEntidad.getUrlSede(), entidad.getUrlSede()));
		jEntidad.setUrlCarpetaCiudadana(
				JLiteral.mergeModel(jEntidad.getUrlCarpetaCiudadana(), entidad.getUrlCarpetaCiudadana()));
		jEntidad.setDiasPreregistro(entidad.getDiasPreregistro());

		jEntidad.setMapaWeb(JLiteral.mergeModel(jEntidad.getMapaWeb(), entidad.getMapaWeb()));
		jEntidad.setAvisoLegal(JLiteral.mergeModel(jEntidad.getAvisoLegal(), entidad.getAvisoLegal()));
		jEntidad.setRss(JLiteral.mergeModel(jEntidad.getRss(), entidad.getRss()));
		jEntidad.setUrlYoutube(entidad.getUrlYoutube());
		jEntidad.setUrlInstagram(entidad.getUrlInstagram());
		jEntidad.setUrlTwitter(entidad.getUrlTwitter());
		jEntidad.setUrlFacebook(entidad.getUrlFacebook());
		jEntidad.setPermiteSubsanarAnexar(entidad.isPermiteSubsanarAnexar());
		jEntidad.setPermiteSubsanarPagar(entidad.isPermiteSubsanarPagar());
		jEntidad.setPermiteSubsanarRegistrar(entidad.isPermiteSubsanarRegistrar());
		jEntidad.setInstruccionesSubsanacion(
				JLiteral.mergeModel(jEntidad.getInstruccionesSubsanacion(), entidad.getInstruccionesSubsanacion()));
		jEntidad.setDiasTramitesPresenciales(entidad.getDiasTramitesPresenciales());
		jEntidad.setInstruccionesPresencial(
				JLiteral.mergeModel(jEntidad.getInstruccionesPresencial(), entidad.getInstruccionesPresencial()));
		jEntidad.setRegistroCentralizado(entidad.isRegistroCentralizado());
		jEntidad.setOficinaRegistroCentralizado(entidad.getOficinaRegistroCentralizado());
		jEntidad.setValorarTramite(entidad.isValorarTramite());
		jEntidad.setRegistroOcultarDescargaDocumentos(entidad.isRegistroOcultarDescargaDocumentos());
		entityManager.merge(jEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#getAllByFiltro(es.
	 * caib.sistrages.core.api.model.types.TypeIdioma, java.lang.String)
	 */
	@Override
	public List<Entidad> getAllByFiltro(final TypeIdioma idioma, final String filtro) {
		return listarEntidades(idioma, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.EntidadDao#getAll()
	 */
	@Override
	public List<Entidad> getAll() {
		return listarEntidades(null, null);
	}

	/**
	 * Listar entidades.
	 *
	 * @param idioma idioma
	 * @param filtro filtro
	 * @return lista de entidades
	 */
	@SuppressWarnings("unchecked")
	private List<Entidad> listarEntidades(final TypeIdioma idioma, final String filtro) {
		final List<Entidad> entidades = new ArrayList<>();

		String sql = "SELECT DISTINCT e FROM JEntidad e ";
		if (StringUtils.isNotBlank(filtro)) {
			sql += " LEFT JOIN e.nombre.traduccionLiterales t WHERE LOWER(e.codigoDir3) LIKE :filtro OR (t.idioma.identificador = :idioma AND LOWER(t.literal) LIKE :filtro) OR LOWER(e.roleAdministrador) LIKE :filtro";
		}
		sql += " ORDER BY e.nombre";

		final Query query = entityManager.createQuery(sql);

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("idioma", idioma.toString());
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		final List<JEntidad> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final JEntidad jentidad : results) {
				final Entidad entidad = jentidad.toModel();
				entidades.add(entidad);
			}
		}

		return entidades;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#removeLogoGestor(
	 * java.lang.Long)
	 */
	@Override
	public void removeLogoGestor(final Long idEntidad) {

		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad " + idEntidad);
		}

		final JFichero jFichero = jEntidad.getLogoGestorTramites();
		if (jFichero != null) {
			jEntidad.setLogoGestorTramites(null);
			entityManager.merge(jEntidad);
			entityManager.remove(jFichero);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#removeLogoAsistente(
	 * java.lang.Long)
	 */
	@Override
	public void removeLogoAsistente(final Long idEntidad) {
		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad " + idEntidad);
		}

		final JFichero jFichero = jEntidad.getLogoAsistenteTramitacion();
		if (jFichero != null) {
			jEntidad.setLogoAsistenteTramitacion(null);
			entityManager.merge(jEntidad);
			entityManager.remove(jFichero);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#removeCssAsistente(
	 * java.lang.Long)
	 */
	@Override
	public void removeCssAsistente(final Long idEntidad) {
		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad " + idEntidad);
		}

		final JFichero jFichero = jEntidad.getCssAsistenteTramitacion();
		if (jFichero != null) {
			jEntidad.setCssAsistenteTramitacion(null);
			entityManager.merge(jEntidad);
			entityManager.remove(jFichero);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#uploadLogoGestor(
	 * java.lang.Long, es.caib.sistrages.core.api.model.Fichero)
	 */
	@Override
	public Fichero uploadLogoGestor(final Long idEntidad, final Fichero fichero) {
		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad " + idEntidad);
		}

		jEntidad.setLogoGestorTramites(JFichero.fromModel(fichero));
		entityManager.merge(jEntidad);

		return jEntidad.getLogoGestorTramites().toModel();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#uploadLogoAsistente(
	 * java.lang.Long, es.caib.sistrages.core.api.model.Fichero)
	 */
	@Override
	public Fichero uploadLogoAsistente(final Long idEntidad, final Fichero fichero) {
		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad " + idEntidad);
		}

		jEntidad.setLogoAsistenteTramitacion(JFichero.fromModel(fichero));
		entityManager.merge(jEntidad);

		return jEntidad.getLogoAsistenteTramitacion().toModel();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#uploadCssAsistente(
	 * java.lang.Long, es.caib.sistrages.core.api.model.Fichero)
	 */
	@Override
	public Fichero uploadCssAsistente(final Long idEntidad, final Fichero fichero) {
		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad " + idEntidad);
		}

		jEntidad.setCssAsistenteTramitacion(JFichero.fromModel(fichero));
		entityManager.merge(jEntidad);

		return jEntidad.getCssAsistenteTramitacion().toModel();
	}

	@Override
	public boolean existeCodigoDIR3(final String codigoDIR3, final Long idEntidad) {
		String sql = "Select count(t) From JEntidad t where t.codigoDir3 = :codigoDir3";
		if (idEntidad != null) {
			sql += " and t.codigo != :idEntidad";
		}
		final Query query = entityManager.createQuery(sql);

		query.setParameter("codigoDir3", codigoDIR3);
		if (idEntidad != null) {
			query.setParameter("idEntidad", idEntidad);
		}

		final Long cuantos = (Long) query.getSingleResult();

		boolean repetido;
		if (cuantos == 0) {
			repetido = false;
		} else {
			repetido = true;
		}
		return repetido;
	}

	@Override
	public List<PlantillaEntidad> getListaPlantillasEmailFin(Long codEntidad) {
		final List<PlantillaEntidad> plantillasEntidad = new ArrayList<>();

		String sql = "SELECT DISTINCT e FROM JPlantillaEntidad e where e.entidad.codigo = :codigo ";

		final Query query = entityManager.createQuery(sql);

		query.setParameter("codigo", codEntidad );

		final List<JPlantillaEntidad> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final JPlantillaEntidad jplantillaEntidad : results) {
				final PlantillaEntidad entidad = jplantillaEntidad.toModel();
				plantillasEntidad.add(entidad);
			}
		}

		return plantillasEntidad;
	}

	@Override
	public PlantillaEntidad uploadPlantillasEmailFin(Long idPlantillaEntidad, PlantillaEntidad plantilla, final Long idEntidad) {
		JPlantillaEntidad jPlantillaEntidad;

		if (plantilla.getCodigo() != null) {
			jPlantillaEntidad = entityManager.find(JPlantillaEntidad.class, plantilla.getCodigo());
			if (jPlantillaEntidad.getFichero() != null) {
				jPlantillaEntidad.getFichero().setNombre(plantilla.getFichero().getNombre());
			} else {
				jPlantillaEntidad.setFichero(JFichero.fromModel(plantilla.getFichero()));
			}
			jPlantillaEntidad.getFichero().setPublico(false); //En plantilla de email fin es privado, NO publico
			entityManager.merge(jPlantillaEntidad);
		} else {
			jPlantillaEntidad = JPlantillaEntidad.fromModel(plantilla);
			jPlantillaEntidad.setEntidad(entityManager.find(JEntidad.class, idEntidad));
			jPlantillaEntidad.setIdioma(entityManager.find(JIdioma.class, plantilla.getIdioma()));
			entityManager.persist(jPlantillaEntidad);
		}

		return jPlantillaEntidad.toModel();
	}

	@Override
	public void removePlantillaEmailFin(Long plantillaEntidad) {
		final JPlantillaEntidad jPlantillaEntidad = entityManager.find(JPlantillaEntidad.class, plantillaEntidad);
		if (jPlantillaEntidad == null) {
			throw new NoExisteDato("No existe plantilla " + plantillaEntidad);
		}

		entityManager.remove(jPlantillaEntidad);
	}

	@Override
	public boolean existeFormulario(String identificador, Long codigo) {
		final StringBuffer sql = new StringBuffer(
				"select count(a) from JEntidad as a where a.identificador like :identificador");
		if (codigo != null) {
			sql.append(" and a.codigo != :codigo");
		}
		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("identificador", identificador);
		if (codigo != null) {
			query.setParameter("codigo", codigo);
		}
		final Long cuantos = (Long) query.getSingleResult();
		return cuantos != 0l;
	}

	@Override
	public Entidad getByIdentificador(String identificador) {
		final String sql = "select d from JDominio d where d.identificador = :identificador ";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("identificador", identificador);

		List<JEntidad> jentidad = query.getResultList();
		Entidad entidad = null;
		if (jentidad != null && !jentidad.isEmpty()) {
			entidad = jentidad.get(0).toModel();
		}

		return entidad;
	}
}

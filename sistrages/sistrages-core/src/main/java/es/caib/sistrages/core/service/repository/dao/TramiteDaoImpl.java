package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.exception.ImportacionError;
import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.exception.TramiteVersionException;
import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.DominioTramite;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramite;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramiteVersion;
import es.caib.sistrages.core.api.model.comun.TramiteSimple;
import es.caib.sistrages.core.api.model.comun.TramiteSimpleFormulario;
import es.caib.sistrages.core.api.model.comun.TramiteSimplePaso;
import es.caib.sistrages.core.api.model.types.TypeAccionHistorial;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeFlujo;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypePaso;
import es.caib.sistrages.core.service.repository.model.JAnexoTramite;
import es.caib.sistrages.core.service.repository.model.JArea;
import es.caib.sistrages.core.service.repository.model.JDominio;
import es.caib.sistrages.core.service.repository.model.JFichero;
import es.caib.sistrages.core.service.repository.model.JFormateadorFormulario;
import es.caib.sistrages.core.service.repository.model.JFormulario;
import es.caib.sistrages.core.service.repository.model.JFormularioTramite;
import es.caib.sistrages.core.service.repository.model.JHistorialVersion;
import es.caib.sistrages.core.service.repository.model.JLiteral;
import es.caib.sistrages.core.service.repository.model.JPasoRellenar;
import es.caib.sistrages.core.service.repository.model.JPasoTramitacion;
import es.caib.sistrages.core.service.repository.model.JPlantillaFormulario;
import es.caib.sistrages.core.service.repository.model.JPlantillaIdiomaFormulario;
import es.caib.sistrages.core.service.repository.model.JScript;
import es.caib.sistrages.core.service.repository.model.JTramite;
import es.caib.sistrages.core.service.repository.model.JVersionTramite;

/**
 * La clase TramiteDaoImpl.
 */
@Repository("tramiteDao")
public class TramiteDaoImpl implements TramiteDao {

	/** Literal. no existe el tramite. **/
	private static final String STRING_NO_EXISTE_TRAMITE = "No existe el tramite: ";
	/** Literal. falta el tramite. **/
	private static final String STRING_FALTA_TRAMITE = "Falta el tramite";
	/** Id tramite version. **/
	private static final String STRING_ID_TRAMITE_VERSION = "idTramiteVersion";

	@Autowired
	private FicheroExternoDao ficheroExternoDao;

	@Autowired
	private DominioDao dominioDao;

	@Autowired
	private FormularioInternoDao formularioInternoDao;

	@Autowired
	private HistorialVersionDao historialVersionDao;

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de TramiteDaoImpl.
	 */
	public TramiteDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.TramiteDao#getById(java.lang.
	 * Long)
	 */
	@Override
	public Tramite getById(final Long id) {
		Tramite tramite = null;

		if (id == null) {
			throw new FaltanDatosException("Falta el identificador");
		}

		final JTramite jTramite = entityManager.find(JTramite.class, id);

		if (jTramite == null) {
			throw new NoExisteDato(STRING_NO_EXISTE_TRAMITE + id);
		} else {
			tramite = jTramite.toModel();
		}

		return tramite;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.TramiteDao#getAllByFiltro(java.
	 * lang.Long, java.lang.String)
	 */
	@Override
	public List<Tramite> getAllByFiltro(final Long idArea, final String pFiltro) {
		return listarTramites(idArea, pFiltro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.TramiteDao#getAll(java.lang.
	 * Long)
	 */
	@Override
	public List<Tramite> getAll(final Long idArea) {
		return listarTramites(idArea, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.TramiteDao#add(java.lang.Long,
	 * es.caib.sistrages.core.api.model.Tramite)
	 */
	@Override
	public Long add(final Long idArea, final Tramite pTramite) {
		if (idArea == null) {
			throw new FaltanDatosException("Falta el area");
		}

		if (pTramite == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}

		final JArea jArea = entityManager.find(JArea.class, idArea);
		final JTramite jTramite = JTramite.fromModel(pTramite);
		jTramite.setArea(jArea);
		entityManager.persist(jTramite);
		return jTramite.getCodigo();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.TramiteDao#remove(java.lang.
	 * Long)
	 */
	@Override
	public void remove(final Long id) {
		if (id == null) {
			throw new FaltanDatosException("Falta el identificador");
		}

		final JTramite jTramite = entityManager.find(JTramite.class, id);
		if (jTramite == null) {
			throw new NoExisteDato(STRING_NO_EXISTE_TRAMITE + id);
		}
		entityManager.remove(jTramite);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.TramiteDao#update(es.caib.
	 * sistrages.core.api.model.Tramite)
	 */
	@Override
	public void update(final Tramite pTramite) {
		if (pTramite == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}
		final JTramite jTramite = entityManager.find(JTramite.class, pTramite.getCodigo());
		if (jTramite == null) {
			throw new NoExisteDato(STRING_NO_EXISTE_TRAMITE + pTramite.getCodigo());
		}

		// Mergeamos datos
		final JTramite jTramiteNew = JTramite.fromModel(pTramite);
		jTramiteNew.setCodigo(jTramite.getCodigo());
		jTramiteNew.setArea(jTramite.getArea());
		jTramiteNew.setVersionTramite(jTramite.getVersionTramite());
		entityManager.merge(jTramiteNew);
	}

	/**
	 * Listar tramite.
	 *
	 * @param idArea  Id area
	 * @param pFiltro the filtro
	 * @return lista de tramites
	 */
	@SuppressWarnings("unchecked")
	private List<Tramite> listarTramites(final Long idArea, final String pFiltro) {
		final List<Tramite> resultado = new ArrayList<>();

		String sql = "Select t From JTramite t where t.area.codigo = :idArea";

		if (StringUtils.isNotBlank(pFiltro)) {
			sql += " AND (upper(t.descripcion) like :filtro OR upper(t.identificador) like :filtro)";
		}
		sql += " ORDER BY t.codigo";

		final Query query = entityManager.createQuery(sql);

		query.setParameter("idArea", idArea);
		if (StringUtils.isNotBlank(pFiltro)) {
			query.setParameter("filtro", "%" + pFiltro.toUpperCase() + "%");
		}

		final List<JTramite> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final Iterator<JTramite> iterator = results.iterator(); iterator.hasNext();) {
				final JTramite jTramite = iterator.next();
				resultado.add(jTramite.toModel());
			}
		}

		return resultado;
	}

	@Override
	public List<TramiteVersion> getTramitesVersion(final Long idTramite, final String filtro) {
		final List<TramiteVersion> resultado = new ArrayList<>();

		String sql = "Select t From JVersionTramite t where t.tramite.codigo = :idTramite ";
		if (filtro != null && !filtro.isEmpty()) {
			sql += " and lower(t.usuarioDatosBloqueo) like '%" + filtro.toLowerCase() + "%' ";
		}
		sql += "order by t.numeroVersion desc";

		final Query query = entityManager.createQuery(sql);
		query.setParameter("idTramite", idTramite);

		@SuppressWarnings("unchecked")
		final List<JVersionTramite> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final Iterator<JVersionTramite> iterator = results.iterator(); iterator.hasNext();) {
				final JVersionTramite jTramiteVersion = iterator.next();
				final TramiteVersion tramiteVersion = new TramiteVersion();
				tramiteVersion.setCodigo(jTramiteVersion.getCodigo());
				tramiteVersion.setNumeroVersion(jTramiteVersion.getNumeroVersion());
				tramiteVersion.setBloqueada(jTramiteVersion.getBloqueada());
				tramiteVersion.setCodigoUsuarioBloqueo(jTramiteVersion.getUsuarioIdBloqueo());
				tramiteVersion.setDatosUsuarioBloqueo(jTramiteVersion.getUsuarioDatosBloqueo());
				tramiteVersion.setActiva(jTramiteVersion.isActiva());
				tramiteVersion.setTipoFlujo(TypeFlujo.fromString(jTramiteVersion.getTipoflujo()));
				tramiteVersion.setRelease(jTramiteVersion.getRelease());
				tramiteVersion.setIdTramite(jTramiteVersion.getTramite().getCodigo());
				tramiteVersion.setFechaUltima(getFechaUltima(jTramiteVersion.getCodigo()));
				tramiteVersion.setHuella(jTramiteVersion.getHuella());
				resultado.add(tramiteVersion);
			}
		}

		return resultado;
	}

	/**
	 * Calcula de una versión de trámite la fecha más actual de su historial.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	private Date getFechaUltima(final Long idTramiteVersion) {
		Date fecha = null;
		final String sql = "Select max(t.fecha) From JHistorialVersion t where t.versionTramite.codigo = :idTramiteVersion ";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idTramiteVersion", idTramiteVersion);
		final List<Object> resultado = query.getResultList();
		if (resultado != null && !resultado.isEmpty()) {
			fecha = (Date) resultado.get(0);
		}
		return fecha;
	}

	@Override
	public Long addTramiteVersion(final TramiteVersion tramiteVersion, final String idTramite) {

		if (idTramite == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}

		final JTramite jTramite = entityManager.find(JTramite.class, Long.valueOf(idTramite));
		if (jTramite == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}

		final StringBuilder sqlCount = new StringBuilder(
				"Select count(*) From JVersionTramite v where  v.tramite.codigo = ");
		sqlCount.append(idTramite);
		sqlCount.append(" and v.numeroVersion = ");
		sqlCount.append(tramiteVersion.getRelease());
		final Query query = entityManager.createQuery(sqlCount.toString());
		final Long cuantos = (Long) query.getSingleResult();
		if (cuantos > 0) {
			throw new TramiteVersionException("Num. versio repetit");
		}

		final JVersionTramite jTramiteVersion = JVersionTramite.fromModel(tramiteVersion);
		jTramiteVersion.setTramite(jTramite);
		entityManager.persist(jTramiteVersion);
		entityManager.flush();

		if (tramiteVersion.getListaPasos() != null) {
			for (final TramitePaso paso : tramiteVersion.getListaPasos()) {
				final JPasoTramitacion jpaso = JPasoTramitacion.fromModel(paso);
				jpaso.setVersionTramite(jTramiteVersion);
				entityManager.persist(jpaso);
			}
		}

		return jTramiteVersion.getCodigo();
	}

	@Override
	public Long clonarTramiteVersion(final Long idTramiteVersion, final Long idArea, final Long idTramite) {

		boolean cambioArea = false;

		// Paso 0. Preparamos los datos.
		if (idTramiteVersion == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}

		// Paso 1. Clonamos el tramiteVersion
		final JVersionTramite jVersionTramiteOriginal = entityManager.find(JVersionTramite.class, idTramiteVersion);
		final JTramite jtramite = entityManager.find(JTramite.class, idTramite);
		if (jVersionTramiteOriginal == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}

		if (jVersionTramiteOriginal.getTramite().getArea().getCodigo().compareTo(idArea) != 0) {
			cambioArea = true;
		}

		final int numVersion = this.getTramiteNumVersionMaximo(idTramite) + 1;
		final JVersionTramite jVersionTramite = JVersionTramite.clonar(jVersionTramiteOriginal, numVersion);
		jVersionTramite.setTramite(jtramite);

		entityManager.persist(jVersionTramite);
		entityManager.flush();

		// Paso 2. Buscamos las relaciones de dominios y las añadimos
		final String sqlDominios = "Select d From JDominio d JOIN d.versionesTramite t where t.codigo = :idTramiteVersion order by d.identificador asc";

		final Query queryDominios = entityManager.createQuery(sqlDominios);
		queryDominios.setParameter(STRING_ID_TRAMITE_VERSION, idTramiteVersion);

		@SuppressWarnings("unchecked")
		final List<JDominio> jdominios = queryDominios.getResultList();
		for (final JDominio jdominio : jdominios) {
			if (cambioArea && jdominio.getAmbito().equals("A")) {
				// Si hay cambio de area y el ambito es de area (A), se ignora
				continue;
			}
			jdominio.getVersionesTramite().add(jVersionTramite);
			entityManager.merge(jdominio);
		}

		// Paso 3.0 Obtenemos formateadores
		final Map<String, JFormateadorFormulario> formateadores = getFormateadoresTramiteVersion(idTramiteVersion);

		// Paso 3. Buscamos los pasos y los clonamos y añadimos.
		final String sqlPasos = "Select p From JPasoTramitacion p where p.versionTramite.codigo = :idTramiteVersion order by p.orden asc";
		final Long idEntidad = jVersionTramite.getTramite().getArea().getEntidad().getCodigo();
		final Query queryPasos = entityManager.createQuery(sqlPasos);
		queryPasos.setParameter(STRING_ID_TRAMITE_VERSION, idTramiteVersion);
		@SuppressWarnings("unchecked")
		final List<JPasoTramitacion> jpasos = queryPasos.getResultList();
		int ordenPaso = 1;
		for (final JPasoTramitacion origPaso : jpasos) {

			final Map<String, JFormulario> forms = new HashMap<>();
			final JPasoTramitacion jpaso = JPasoTramitacion.clonar(origPaso, jVersionTramite);
			jpaso.setOrden(ordenPaso);

			// Si el paso es de tipo rellenar
			if (origPaso.getPasoRellenar() != null && origPaso.getPasoRellenar().getFormulariosTramite() != null
					&& !origPaso.getPasoRellenar().getFormulariosTramite().isEmpty()) {
				// Habria que guardarse los diseños.
				for (final JFormularioTramite formulario : origPaso.getPasoRellenar().getFormulariosTramite()) {
					forms.put(formulario.getIdentificador(), formulario.getFormulario());
				}
			}

			entityManager.persist(jpaso);
			entityManager.flush();

			// Después de guardar los pasos se encarga de clonar los formularios
			if (!forms.isEmpty()) {
				for (final Map.Entry<String, JFormulario> entry : forms.entrySet()) {
					final JFormulario jform = JFormulario.clonar(entry.getValue(), formateadores, cambioArea);
					entityManager.persist(jform);
					entityManager.flush();
					for (final JFormularioTramite formTramite : jpaso.getPasoRellenar().getFormulariosTramite()) {
						if (formTramite.getIdentificador().equals(entry.getKey())) {
							formTramite.setFormulario(jform);
							entityManager.merge(jform);
							break;
						}
					}
				}
			}
			guardarContenidoFicheros(jpaso, origPaso, idEntidad);
			ordenPaso++;
		}

		return jVersionTramite.getCodigo();
	}

	/**
	 * Método privado para obtener los formateadores de una version.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	private Map<String, JFormateadorFormulario> getFormateadoresTramiteVersion(final Long idTramiteVersion) {

		final String sql = "Select plan.formateadorFormulario from  JPlantillaFormulario plan inner join plan.formulario fr where fr in (select forms.formulario from JPasoTramitacion pasot inner join pasot.pasoRellenar pasor inner join pasor.formulariosTramite forms  where pasot.versionTramite.codigo = :idTramiteVersion) ";

		final Query query = entityManager.createQuery(sql);
		query.setParameter("idTramiteVersion", idTramiteVersion);

		@SuppressWarnings("unchecked")
		final List<JFormateadorFormulario> results = query.getResultList();
		final Map<String, JFormateadorFormulario> resultado = new HashMap<>();

		if (results != null && !results.isEmpty()) {
			for (final Iterator<JFormateadorFormulario> iterator = results.iterator(); iterator.hasNext();) {
				final JFormateadorFormulario jformateadorFormulario = iterator.next();
				resultado.put(jformateadorFormulario.getIdentificador(), jformateadorFormulario);
			}
		}

		return resultado;
	}

	/**
	 * Este método privado se encarga de clonar un fichero hacia ficheroexterno.
	 * <br />
	 * Se basa en coger el paso original,
	 *
	 * @param jpaso
	 * @param origPaso
	 * @param idEntidad
	 */
	private void guardarContenidoFicheros(final JPasoTramitacion jpaso, final JPasoTramitacion origPaso,
			final Long idEntidad) {

		if (origPaso.getPasoAnexar() != null) {
			// Buscamos en el original, si tiene un fichero y lo guardamos en ficheros
			// externos.
			for (final JAnexoTramite anexo : origPaso.getPasoAnexar().getAnexosTramite()) {
				if (anexo.getFicheroPlantilla() != null) {
					final byte[] content = ficheroExternoDao.getContentById(anexo.getFicheroPlantilla().getCodigo())
							.getContent();
					final JFichero fichero = getFicheroAnexoTramite(jpaso, anexo.getCodigo());
					if (fichero != null) {
						ficheroExternoDao.guardarFichero(idEntidad, fichero.toModel(), content);
					}
				}
			}
		}

		if (origPaso.getPasoInformacion() != null && origPaso.getPasoInformacion().getFicheroPlantilla() != null) {
			// Pendiente de hacerlo
		}

		// Los formularios.
		if (origPaso.getPasoCaptura() != null && origPaso.getPasoCaptura().getFormularioTramite() != null
				&& origPaso.getPasoCaptura().getFormularioTramite().getFormulario() != null) {
			// Pendiente de hacerlo
		}

		// Los formularios.
		if (origPaso.getPasoRellenar() != null && origPaso.getPasoRellenar().getFormulariosTramite() != null) {

			final List<JPlantillaIdiomaFormulario> plantillas = getPlantillasIdiomas(jpaso.getPasoRellenar());
			final List<JPlantillaIdiomaFormulario> plantillasOriginales = getPlantillasIdiomas(
					origPaso.getPasoRellenar());

			for (final JPlantillaIdiomaFormulario plantilla : plantillas) {
				for (final JPlantillaIdiomaFormulario plantillaOriginal : plantillasOriginales) {
					if (plantilla.getFichero() != null
							&& plantilla.getCodigoClonado().compareTo(plantillaOriginal.getCodigo()) == 0
							&& plantillaOriginal.getFichero() != null) {
						final byte[] content = ficheroExternoDao
								.getContentById(plantillaOriginal.getFichero().getCodigo()).getContent();
						final JFichero fichero = plantilla.getFichero();
						if (fichero != null) {
							ficheroExternoDao.guardarFichero(idEntidad, fichero.toModel(), content);
						}
					}
				}
			}
		}
	}

	/**
	 * Busca las JPlantillasIdioma a partir del paso de tipo rellenar
	 * (JPasoRellenar)
	 *
	 * @param paso
	 * @return Lista de plantillas idiomas formulario.
	 */
	private List<JPlantillaIdiomaFormulario> getPlantillasIdiomas(final JPasoRellenar paso) {
		final List<JPlantillaIdiomaFormulario> plantillas = new ArrayList<>();
		if (paso.getFormulariosTramite() != null) {
			for (final JFormularioTramite formulario : paso.getFormulariosTramite()) {
				if (formulario.getFormulario() != null && formulario.getFormulario().getPlantillas() != null) {
					for (final JPlantillaFormulario plantilla : formulario.getFormulario().getPlantillas()) {
						if (plantilla.getPlantillaIdiomaFormulario() != null) {
							for (final JPlantillaIdiomaFormulario plantillaIdioma : plantilla
									.getPlantillaIdiomaFormulario()) {
								plantillas.add(plantillaIdioma);
							}
						}
					}
				}
			}
		}
		return plantillas;
	}

	/**
	 * Devuelve el anexo.
	 *
	 * @param jpaso
	 * @param ficheroPlantilla
	 * @return
	 */
	private JFichero getFicheroAnexoTramite(final JPasoTramitacion jpaso, final Long codigo) {
		JFichero jfichero = null;
		for (final JAnexoTramite anexo : jpaso.getPasoAnexar().getAnexosTramite()) {
			if (anexo.getFicheroPlantilla() != null && anexo.getCodigoClonado().compareTo(codigo) == 0) {
				jfichero = anexo.getFicheroPlantilla();
			}
		}
		return jfichero;
	}

	@Override
	public void updateTramiteVersion(final TramiteVersion tramiteVersion) {
		final JVersionTramite jTramiteVersionOld = entityManager.find(JVersionTramite.class,
				tramiteVersion.getCodigo());
		final JVersionTramite jTramiteVersion = JVersionTramite.fromModel(tramiteVersion);
		jTramiteVersion.setTramite(jTramiteVersionOld.getTramite());
		entityManager.merge(jTramiteVersion);
	}

	@Override
	public void removeTramiteVersion(final Long idTramiteVersion) {

		// Paso 0. Obtenemos la versión del trámite.
		final JVersionTramite jTramiteVersion = entityManager.find(JVersionTramite.class, idTramiteVersion);

		// Paso1. Obtenemos los pasos y los borramos
		final String sql = "Select t From JPasoTramitacion t where t.versionTramite.codigo = :idTramiteVersion";

		final Query query = entityManager.createQuery(sql);
		query.setParameter(STRING_ID_TRAMITE_VERSION, idTramiteVersion);

		@SuppressWarnings("unchecked")
		final List<JPasoTramitacion> pasos = query.getResultList();
		for (final JPasoTramitacion paso : pasos) {
			// Borramos el diseño.
			if (paso.getPasoRellenar() != null && paso.getPasoRellenar().getFormulariosTramite() != null) {
				for (final JFormularioTramite formulario : paso.getPasoRellenar().getFormulariosTramite()) {
					if (formulario.getFormulario() != null) {
						formularioInternoDao.removeFormulario(formulario.getFormulario().getCodigo());
					}
				}
			}
			entityManager.remove(paso);
		}

		// Paso2. Obtenemos los pasos y los borramos
		final String sqlHistorico = "Select t From JHistorialVersion t where t.versionTramite.codigo = :idTramiteVersion";

		final Query queryHistorico = entityManager.createQuery(sqlHistorico);
		queryHistorico.setParameter(STRING_ID_TRAMITE_VERSION, idTramiteVersion);

		@SuppressWarnings("unchecked")
		final List<JHistorialVersion> historiales = queryHistorico.getResultList();
		for (final JHistorialVersion historial : historiales) {
			entityManager.remove(historial);
		}

		// Paso 3. Buscamos los dominios que tengan la versión trámite y borramos la
		// relacion
		final String sqlDominio = "Select d From JDominio d join d.versionesTramite t where t.codigo = :idTramiteVersion";

		final Query queryDominio = entityManager.createQuery(sqlDominio);
		queryDominio.setParameter(STRING_ID_TRAMITE_VERSION, idTramiteVersion);

		@SuppressWarnings("unchecked")
		final List<JDominio> dominios = queryDominio.getResultList();
		for (final JDominio dominio : dominios) {
			dominio.getVersionesTramite().remove(jTramiteVersion);
			entityManager.merge(dominio);
		}

		// Paso 4. Borramos el tramite versión
		entityManager.remove(jTramiteVersion);

	}

	@Override
	public TramiteVersion getTramiteVersion(final Long idTramiteVersion) {
		final JVersionTramite jTramiteVersion = entityManager.find(JVersionTramite.class, idTramiteVersion);
		return jTramiteVersion.toModel();
	}

	@Override
	public Area getAreaTramite(final Long idTramite) {
		Area area = null;
		final JTramite jTramite = entityManager.find(JTramite.class, idTramite);
		if (jTramite != null) {
			area = jTramite.getArea().toModel();
		}
		return area;
	}

	@Override
	public void changeAreaTramite(final Long idArea, final Long idTramite) {
		final JArea jArea = entityManager.find(JArea.class, idArea);
		final JTramite jTramite = entityManager.find(JTramite.class, idTramite);
		jTramite.setArea(jArea);
		entityManager.merge(jTramite);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dominio> getDominioSimpleByTramiteId(final Long idTramiteVersion) {
		final String sql = "Select d.codigo, d.identificador, d.descripcion, d.ambito From JDominio d JOIN d.versionesTramite t where t.codigo = :idTramiteVersion order by d.identificador asc";

		final Query query = entityManager.createQuery(sql);
		query.setParameter(STRING_ID_TRAMITE_VERSION, idTramiteVersion);

		final List<Object[]> resultados = query.getResultList();
		final List<Dominio> dominios = new ArrayList<>();
		if (resultados != null && !resultados.isEmpty()) {
			for (final Object[] resultado : resultados) {

				final Dominio dominio = new Dominio();
				if (resultado[0] != null) {
					dominio.setCodigo(Long.valueOf(resultado[0].toString()));
				}
				if (resultado[1] != null) {
					dominio.setIdentificador(resultado[1].toString());
				}
				if (resultado[2] != null) {
					dominio.setDescripcion(resultado[2].toString());
				}
				if (resultado[3] != null) {
					dominio.setAmbito(TypeAmbito.fromString(resultado[3].toString()));
				}
				dominios.add(dominio);
			}
		}

		return dominios;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTramiteDominiosIdentificador(final Long idTramiteVersion) {
		final String sql = "Select d.identificador From JDominio d JOIN d.versionesTramite t where t.codigo = :idTramiteVersion order by d.identificador asc";

		final Query query = entityManager.createQuery(sql);
		query.setParameter(STRING_ID_TRAMITE_VERSION, idTramiteVersion);

		final List<String> resultado = query.getResultList();

		return resultado;
	}

	@Override
	public void bloquearTramiteVersion(final Long idTramiteVersion, final String username) {
		final JVersionTramite jTramiteVersion = entityManager.find(JVersionTramite.class, idTramiteVersion);
		if (!jTramiteVersion.getBloqueada()) {
			jTramiteVersion.setBloqueada(true);
			jTramiteVersion.setUsuarioDatosBloqueo(username);
			entityManager.merge(jTramiteVersion);
		}

	}

	@Override
	public void desbloquearTramiteVersion(final Long idTramiteVersion) {
		final JVersionTramite jTramiteVersion = entityManager.find(JVersionTramite.class, idTramiteVersion);
		if (jTramiteVersion.getBloqueada()) {
			jTramiteVersion.setBloqueada(false);
			jTramiteVersion.setUsuarioDatosBloqueo("");
			jTramiteVersion.setRelease(jTramiteVersion.getRelease() + 1);
			final String huella = getHuella();
			jTramiteVersion.setHuella(huella);
			entityManager.merge(jTramiteVersion);
		}
	}

	/**
	 * Genera una huella de 15 caracteres aleatorios y 5 numeros
	 *
	 * @return
	 */
	private String getHuella() {
		final char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		final StringBuilder sb = new StringBuilder(15);
		final Random random = new Random();
		for (int i = 0; i < 15; i++) {
			final char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		final String output = sb.toString();
		final int numero = random.nextInt(99999);

		return output + numero;
	}

	@Override
	public boolean tieneTramiteVersion(final Long idTramite) {
		final StringBuilder sqlCount = new StringBuilder(
				"Select count(*) From JVersionTramite v where v.tramite.codigo = ");
		sqlCount.append(idTramite);
		final Query query = entityManager.createQuery(sqlCount.toString());
		final Long cuantos = (Long) query.getSingleResult();
		boolean tiene;
		if (cuantos == 0) {
			tiene = false;
		} else {
			tiene = true;
		}
		return tiene;
	}

	@Override
	public boolean tieneTramiteNumVersionRepetido(final Long idTramite, final int numVersion) {
		final StringBuilder sqlCount = new StringBuilder(
				"Select count(*) From JVersionTramite v where v.tramite.codigo = ");
		sqlCount.append(idTramite);
		sqlCount.append(" and v.numeroVersion = ");
		sqlCount.append(numVersion);
		final Query query = entityManager.createQuery(sqlCount.toString());
		final Long cuantos = (Long) query.getSingleResult();

		boolean tiene;
		if (cuantos == 0) {
			tiene = false;
		} else {
			tiene = true;
		}
		return tiene;
	}

	@Override
	public int getTramiteNumVersionMaximo(final Long idTramite) {

		// Primero comprobamos si tiene, si no tiene, se devuelve el valor por defecto
		// (0) sino el máximo valor +1
		final StringBuilder sqlCount = new StringBuilder(
				"Select count(v) From JVersionTramite v where v.tramite.codigo = ");
		sqlCount.append(idTramite);
		final Query query = entityManager.createQuery(sqlCount.toString());
		final Long cuantos = (Long) query.getSingleResult();

		int realeaseMax;
		if (cuantos == 0) {
			realeaseMax = 0;
		} else {

			final StringBuilder sqlMax = new StringBuilder(
					"Select max(v.numeroVersion) From JVersionTramite v where v.tramite.codigo = ");
			sqlMax.append(idTramite);
			final Query queryMax = entityManager.createQuery(sqlMax.toString());
			realeaseMax = (Integer) queryMax.getSingleResult();
		}
		return realeaseMax;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> listTramiteVersionActiva(final Long idArea) {

		final String sql = "Select distinct tv.tramite.codigo From JVersionTramite tv where tv.tramite.area.codigo = :idArea and tv.activa = true order by tv.tramite.codigo ASC";
		final Query query = entityManager.createQuery(sql);

		query.setParameter("idArea", idArea);
		return query.getResultList();

	}

	@Override
	public Tramite getTramiteByIdentificador(final String identificador) {

		final String sql = "Select t From JTramite t where t.identificador = :identificador";
		final Query query = entityManager.createQuery(sql);

		query.setParameter("identificador", identificador);

		Tramite tramite = null;

		@SuppressWarnings("unchecked")
		final List<JTramite> tramites = query.getResultList();

		if (tramites != null && tramites.size() > 0) {
			tramite = tramites.get(0).toModel();
		}

		return tramite;
	}

	@Override
	public TramiteVersion getTramiteVersionByNumVersion(final int numeroVersion, final Long idTramite) {
		final String sql = "Select t From JVersionTramite t where t.tramite.codigo = :idTramite and t.numeroVersion = :numeroVersion";
		final Query query = entityManager.createQuery(sql);

		query.setParameter("idTramite", idTramite);
		query.setParameter("numeroVersion", numeroVersion);

		TramiteVersion tramiteVersion = null;
		final List<JVersionTramite> resultado = query.getResultList();

		if (!resultado.isEmpty()) {
			tramiteVersion = resultado.get(0).toModel();
		}

		return tramiteVersion;
	}

	@Override
	public TramiteVersion getTramiteVersionByNumVersion(final String idTramite, final int numeroVersion) {
		final String sql = "Select t From JVersionTramite t where t.tramite.identificador = :idTramite and t.numeroVersion = :numeroVersion";
		final Query query = entityManager.createQuery(sql);

		query.setParameter("idTramite", idTramite);
		query.setParameter("numeroVersion", numeroVersion);

		TramiteVersion tramiteVersion = null;
		final List<JVersionTramite> resultado = query.getResultList();

		if (!resultado.isEmpty()) {
			tramiteVersion = resultado.get(0).toModel();
		}

		return tramiteVersion;
	}

	@Override
	public TramiteVersion getTramiteVersionMaxNumVersion(final Long idTramite) {

		final int numVersion = this.getTramiteNumVersionMaximo(idTramite);
		if (numVersion == 0) {
			return null;
		} else {
			return this.getTramiteVersionByNumVersion(numVersion, idTramite);
		}
	}

	@Override
	public List<DominioTramite> getTramiteVersionByDominio(final Long idDominio) {
		final List<DominioTramite> resultado = new ArrayList<>();

		final String sql = "Select t From JDominio d JOIN d.versionesTramite t where d.codigo = :idDominio  order by t.numeroVersion desc";

		final Query query = entityManager.createQuery(sql);
		query.setParameter("idDominio", idDominio);

		@SuppressWarnings("unchecked")
		final List<JVersionTramite> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final Iterator<JVersionTramite> iterator = results.iterator(); iterator.hasNext();) {
				final JVersionTramite jTramiteVersion = iterator.next();
				final DominioTramite dominioTramite = new DominioTramite();
				dominioTramite.setArea(jTramiteVersion.getTramite().getArea().getIdentificador());
				dominioTramite.setEntidad(jTramiteVersion.getTramite().getArea().getEntidad().getNombre().toModel());
				dominioTramite.setIdTramiteVersion(jTramiteVersion.getCodigo());
				dominioTramite.setNumVersion(jTramiteVersion.getNumeroVersion());
				dominioTramite.setRelease(jTramiteVersion.getRelease());
				dominioTramite.setTramite(jTramiteVersion.getTramite().getDescripcion());
				resultado.add(dominioTramite);
			}
		}

		return resultado;
	}

	@Override
	public boolean checkIdentificadorRepetido(final String identificador, final Long codigo) {
		String sql = "Select count(t) From JTramite t where t.identificador = :identificador";
		if (codigo != null) {
			sql += " and t.codigo != :codigo";
		}
		final Query query = entityManager.createQuery(sql);

		query.setParameter("identificador", identificador);
		if (codigo != null) {
			query.setParameter("codigo", codigo);
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
	public Long importar(final FilaImportarTramite filaTramite, final Long idArea) {

		Long idTramite;
		switch (filaTramite.getAccion()) {
		case CREAR:
			final Tramite tramite = filaTramite.getTramite();
			tramite.setCodigo(null);
			tramite.setIdentificador(filaTramite.getIdentificador());
			tramite.setDescripcion(filaTramite.getDescripcion());
			idTramite = add(idArea, tramite);
			break;
		case SELECCIONAR:
			idTramite = filaTramite.getTramiteActual().getCodigo();
			break;
		case REEMPLAZAR:
			final JTramite jTramite = entityManager.find(JTramite.class, filaTramite.getTramiteActual().getCodigo());
			jTramite.setDescripcion(filaTramite.getTramiteResultado());
			entityManager.merge(jTramite);
			idTramite = jTramite.getCodigo();
			break;
		default:
			throw new ImportacionError("Acció en tràmit dao no implementada.");
		}

		return idTramite;
	}

	@Override
	public Long importar(final FilaImportarTramiteVersion filaTramiteVersion, final Long idTramite,
			final List<Long> idDominios, final String usuario, final boolean isModoIM) {

		JVersionTramite jTramiteVersion = null;
		switch (filaTramiteVersion.getAccion()) {
		case INCREMENTAR:
		case CREAR:
			jTramiteVersion = limpiar(JVersionTramite.fromModel(filaTramiteVersion.getTramiteVersion()));
			if (filaTramiteVersion.getAccion() == TypeImportarAccion.INCREMENTAR) {
				final int numeroVersion = this.getTramiteNumVersionMaximo(idTramite);
				jTramiteVersion.setNumeroVersion(numeroVersion + 1);
				jTramiteVersion.setRelease(1);
			}
			if (filaTramiteVersion.getAccion() == TypeImportarAccion.CREAR) {
				jTramiteVersion.setNumeroVersion(Integer.parseInt(filaTramiteVersion.getNumVersion()));
				jTramiteVersion.setRelease(1);
			}

			final JTramite jTramite = entityManager.find(JTramite.class, idTramite);
			jTramiteVersion.setTramite(jTramite);
			entityManager.persist(jTramiteVersion);
			break;
		case REEMPLAZAR:
			// Obtenemos los pasos y los borramos
			final String sql = "Select t From JPasoTramitacion t where t.versionTramite.codigo = :idTramiteVersion";

			final Query query = entityManager.createQuery(sql);
			query.setParameter(STRING_ID_TRAMITE_VERSION, filaTramiteVersion.getTramiteVersionActual().getCodigo());

			@SuppressWarnings("unchecked")
			final List<JPasoTramitacion> pasos = query.getResultList();
			for (final JPasoTramitacion paso : pasos) {

				// Borramos el diseño.
				if (paso.getPasoRellenar() != null && paso.getPasoRellenar().getFormulariosTramite() != null) {
					for (final JFormularioTramite formulario : paso.getPasoRellenar().getFormulariosTramite()) {
						if (formulario.getFormulario() != null) {
							formularioInternoDao.removeFormulario(formulario.getFormulario().getCodigo());
						}
					}
				}
				entityManager.remove(paso);
			}
			entityManager.flush();

			jTramiteVersion = entityManager.find(JVersionTramite.class,
					filaTramiteVersion.getTramiteVersionActual().getCodigo());

			// Actualizamos la version y release respecto a lo que nos pasan
			jTramiteVersion.setActiva(filaTramiteVersion.getTramiteVersion().isActiva());
			jTramiteVersion.setAdmitePersistencia(filaTramiteVersion.getTramiteVersion().isPersistencia());
			jTramiteVersion.setAutenticado(filaTramiteVersion.getTramiteVersion().isAutenticado());
			jTramiteVersion.setBloqueada(false);
			jTramiteVersion.setDebug(filaTramiteVersion.getTramiteVersion().isDebug());
			jTramiteVersion.setDesactivacionTemporal(filaTramiteVersion.getTramiteVersion().isDesactivacion());
			jTramiteVersion.setIdiomasSoportados(filaTramiteVersion.getTramiteVersion().getIdiomasSoportados());
			if (filaTramiteVersion.getTramiteVersion().isLimiteTramitacion()) {
				jTramiteVersion.setLimiteTramitacion("S");
			} else {
				jTramiteVersion.setLimiteTramitacion("N");
			}
			jTramiteVersion
					.setLimiteTramitacionIntervalo(filaTramiteVersion.getTramiteVersion().getIntLimiteTramitacion());
			jTramiteVersion
					.setLimiteTramitacionNumero(filaTramiteVersion.getTramiteVersion().getNumLimiteTramitacion());
			jTramiteVersion.setMensajeDesactivacion(JLiteral
					.clonar(JLiteral.fromModel(filaTramiteVersion.getTramiteVersion().getMensajeDesactivacion())));
			jTramiteVersion.setNivelQAA(filaTramiteVersion.getTramiteVersion().getNivelQAA());
			jTramiteVersion.setNoAutenticado(filaTramiteVersion.getTramiteVersion().isNoAutenticado());
			jTramiteVersion.setNumeroVersion(filaTramiteVersion.getTramiteVersion().getNumeroVersion());
			jTramiteVersion.setPersistenciaDias(filaTramiteVersion.getTramiteVersion().getPersistenciaDias());
			jTramiteVersion.setPersistenciaInfinita(filaTramiteVersion.getTramiteVersion().isPersistenciaInfinita());
			jTramiteVersion.setPlazoFinDesactivacion(filaTramiteVersion.getTramiteVersion().getPlazoFinDesactivacion());
			jTramiteVersion
					.setPlazoInicioDesactivacion(filaTramiteVersion.getTramiteVersion().getPlazoInicioDesactivacion());
			if (isModoIM) {
				// En modo importar, se incrementa la release
				jTramiteVersion.setRelease(jTramiteVersion.getRelease() + 1);
			} else {
				// En modo Cuaderno Carga, se sustituye
				jTramiteVersion.setRelease(filaTramiteVersion.getTramiteVersion().getRelease());
			}
			jTramiteVersion.setScriptInicializacionTramite(JScript
					.fromModel(Script.clonar(filaTramiteVersion.getTramiteVersion().getScriptInicializacionTramite())));
			jTramiteVersion.setScriptPersonalizacion(JScript
					.fromModel(Script.clonar(filaTramiteVersion.getTramiteVersion().getScriptPersonalizacion())));
			jTramiteVersion.setUsuarioDatosBloqueo("");
			jTramiteVersion.setUsuarioIdBloqueo("");
			jTramiteVersion.setTipoflujo(filaTramiteVersion.getTramiteVersion().getTipoFlujo().toString());
			entityManager.merge(jTramiteVersion);

			// Solo en el modo Cuaderno de Carga se puede reemplazar y eliiminar versiones
			// antiguas (en IMP, siempre se incrementa)
			if (!isModoIM) {
				// Actualizamos el historial
				final String sqlHistorial = "Select t From JHistorialVersion t where t.versionTramite.codigo = :idTramiteVersion and t.release >= :release";

				final Query queryHistorial = entityManager.createQuery(sqlHistorial);
				queryHistorial.setParameter(STRING_ID_TRAMITE_VERSION,
						filaTramiteVersion.getTramiteVersionActual().getCodigo());
				queryHistorial.setParameter("release", filaTramiteVersion.getTramiteVersion().getRelease());

				@SuppressWarnings("unchecked")
				final List<JHistorialVersion> historiales = queryHistorial.getResultList();
				for (final JHistorialVersion historialFuturo : historiales) {
					entityManager.remove(historialFuturo);
				}
			}
			break;
		default:
			throw new ImportacionError("Acció en tràmit versió dao no implementada.");
		}

		entityManager.flush();

		// Creamos una nueva entrada en el historial
		historialVersionDao.add(jTramiteVersion.getCodigo(), usuario, TypeAccionHistorial.IMPORTACION, null);

		/** Primero borramos las asociaciones y luego las volvemos a asociar. **/
		final List<Dominio> mdominios = this.getDominioSimpleByTramiteId(jTramiteVersion.getCodigo());
		for (final Dominio mdominio : mdominios) {
			dominioDao.removeTramiteVersion(mdominio.getCodigo(), jTramiteVersion.getCodigo());
		}
		entityManager.flush();

		/** Id dominio. **/
		for (final Long idDominio : idDominios) {
			dominioDao.addTramiteVersion(idDominio, jTramiteVersion.getCodigo());
		}

		return jTramiteVersion.getCodigo();
	}

	/**
	 * Método que coge una version de tramite y quita los codigo.
	 *
	 * @param fromModel
	 * @return
	 */
	private JVersionTramite limpiar(final JVersionTramite version) {

		version.setCodigo(null);
		version.setScriptInicializacionTramite(JScript.clonar(version.getScriptInicializacionTramite()));
		version.setScriptPersonalizacion(JScript.clonar(version.getScriptPersonalizacion()));
		version.setMensajeDesactivacion(JLiteral.clonar(version.getMensajeDesactivacion()));
		return version;
	}

	@Override
	public TramiteSimple getTramiteSimple(final String idTramiteVersion) {

		final TramiteSimple tramite = new TramiteSimple();
		tramite.setCodigo(idTramiteVersion);
		final String sqlTramite = "Select v.idiomasSoportados From JVersionTramite v where v.codigo = :idTramiteVersion ";
		final Query queryTramite = entityManager.createQuery(sqlTramite);
		queryTramite.setParameter("idTramiteVersion", Long.valueOf(idTramiteVersion));
		final List<Object> objectIdiomas = queryTramite.getResultList();
		if (objectIdiomas.size() == 1) {
			final String oIdiomas = objectIdiomas.get(0).toString();
			tramite.setIdiomasSoportados(oIdiomas);
		}

		final List<TramiteSimplePaso> pasos = new ArrayList<>();

		final String sql = "Select p.codigo, p.tipoPaso From JPasoTramitacion p where p.versionTramite.codigo = :idTramiteVersion order by p.orden ";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idTramiteVersion", Long.valueOf(idTramiteVersion));

		@SuppressWarnings("unchecked")
		final List<Object[]> objectPasos = query.getResultList();

		if (objectPasos != null) {
			for (final Object[] objetPaso : objectPasos) {
				final TramiteSimplePaso paso = new TramiteSimplePaso();
				paso.setCodigo(Long.valueOf(objetPaso[0].toString()));
				paso.setTipo(TypePaso.fromString(objetPaso[1].toString()));
				if (paso.isTipoPasoRellenar()) {

					final List<TramiteSimpleFormulario> formularios = new ArrayList<>();
					final String sqlForm = "Select f.codigo, f.formulario.codigo From JPasoRellenar p JOIN  p.formulariosTramite f where p.codigo = :idPaso order by f.orden ";
					final Query queryForm = entityManager.createQuery(sqlForm);
					queryForm.setParameter("idPaso", paso.getCodigo());

					@SuppressWarnings("unchecked")
					final List<Object[]> objectForms = queryForm.getResultList();
					if (objectForms != null) {
						for (final Object[] objectForm : objectForms) {
							final TramiteSimpleFormulario formulario = new TramiteSimpleFormulario();
							formulario.setCodigo(Long.valueOf(objectForm[0].toString()));
							formulario.setIdFormularioInterno(Long.valueOf(objectForm[1].toString()));
							formularios.add(formulario);
						}
					}

					paso.setFormularios(formularios);
				}
				pasos.add(paso);
			}
		}

		tramite.setPasos(pasos);
		return tramite;

	}

	@Override
	public String getIdiomasDisponibles(final String idTramiteVersion) {
		final String sqlTramite = "Select v.idiomasSoportados From JVersionTramite v where v.codigo = :idTramiteVersion ";
		final Query queryTramite = entityManager.createQuery(sqlTramite);
		queryTramite.setParameter("idTramiteVersion", Long.valueOf(idTramiteVersion));
		final List<Object> objectIdiomas = queryTramite.getResultList();

		String idiomas = null;
		if (objectIdiomas.size() == 1) {
			idiomas = objectIdiomas.get(0).toString();
		}
		return idiomas;
	}

	@Override
	public List<Tramite> getAllByEntidad(final Long idEntidad) {
		return listarTramitesByEntidad(idEntidad, null);
	}

	@Override
	public List<Tramite> getAllByEntidad(final Long idEntidad, final String filtro) {
		return listarTramitesByEntidad(idEntidad, null);
	}

	private List<Tramite> listarTramitesByEntidad(final Long idEntidad, final String pFiltro) {
		final List<Tramite> resultado = new ArrayList<>();

		String sql = "Select t From JTramite t where t.area.entidad.codigo = :idEntidad";

		if (StringUtils.isNotBlank(pFiltro)) {
			sql += " AND (upper(t.descripcion) like :filtro OR upper(t.identificador) like :filtro)";
		}
		sql += " ORDER BY t.codigo";

		final Query query = entityManager.createQuery(sql);

		query.setParameter("idEntidad", idEntidad);
		if (StringUtils.isNotBlank(pFiltro)) {
			query.setParameter("filtro", "%" + pFiltro.toUpperCase() + "%");
		}

		final List<JTramite> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final Iterator<JTramite> iterator = results.iterator(); iterator.hasNext();) {
				final JTramite jTramite = iterator.next();
				resultado.add(jTramite.toModel());
			}
		}

		return resultado;
	}

	@Override
	public String getIdentificadorByCodigoVersion(final Long codigo) {
		final String sql = "Select t.tramite.identificador From JVersionTramite t where t.codigo = :codigo";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("codigo", codigo);
		String identificador = null;

		final List<String> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			identificador = results.get(0);
		}

		return identificador;
	}

	@Override
	public void updateLiteral(final Literal pLiteral) {
		if (pLiteral == null) {
			throw new FaltanDatosException("Falta el literal");
		}

		final JLiteral jLiteral = entityManager.find(JLiteral.class, pLiteral.getCodigo());
		if (jLiteral == null) {
			throw new NoExisteDato("No existe el literal " + pLiteral.getCodigo());
		}

		final JLiteral jLiteralNew = JLiteral.fromModel(pLiteral);
		entityManager.merge(jLiteralNew);
	}

	@Override
	public void incrementarRelease(final Long idTramiteVersion) {
		final String sql = "Select t From JVersionTramite t where t.codigo = :idTramite ";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idTramite", idTramiteVersion);

		final JVersionTramite jversion = (JVersionTramite) query.getSingleResult();
		jversion.setRelease(jversion.getRelease() + 1);
		entityManager.merge(jversion);

	}
}

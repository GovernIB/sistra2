package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.ObjetoFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.PlantillaFormulario;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoAnexar;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
import es.caib.sistrages.core.service.repository.model.JAnexoTramite;
import es.caib.sistrages.core.service.repository.model.JFichero;
import es.caib.sistrages.core.service.repository.model.JFormateadorFormulario;
import es.caib.sistrages.core.service.repository.model.JFormulario;
import es.caib.sistrages.core.service.repository.model.JFormularioTramite;
import es.caib.sistrages.core.service.repository.model.JLiteral;
import es.caib.sistrages.core.service.repository.model.JPagoTramite;
import es.caib.sistrages.core.service.repository.model.JPasoTramitacion;
import es.caib.sistrages.core.service.repository.model.JScript;
import es.caib.sistrages.core.service.repository.model.JVersionTramite;

/**
 * La clase TramitePasoDaoImpl.
 */
@Repository("tramitePasoDao")
public class TramitePasoDaoImpl implements TramitePasoDao {

	/** Literal. no existe el tramite paso. **/
	private static final String STRING_NO_EXISTE_TRAMITE_PASO = "No existe el tramite paso: ";
	/** Literal. no existe el formulario. **/
	private static final String STRING_NO_EXISTE_FORMULARIO = "No existe el formulario: ";
	/** Literal. falta el tramite. **/
	private static final String STRING_FALTA_TRAMITE = "Falta el tramite";
	/** Literal. falta el formulario. **/
	private static final String STRING_FALTA_FORMULARIO = "Falta el formulario";

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private FicheroExternoDao ficheroExternoDao;

	@Autowired
	private FormularioInternoDao formularioInternoDao;

	@Autowired
	private FormateadorFormularioDao formateadorFormularioDao;

	/**
	 * Crea una nueva instancia de TramiteDaoImpl.
	 */
	public TramitePasoDaoImpl() {
		super();
	}

	@Override
	public List<TramitePaso> getTramitePasos(final Long idTramiteVersion) {
		final String sql = "Select t From JPasoTramitacion t where t.versionTramite.id = :idTramiteVersion order by t.orden asc";

		final Query query = entityManager.createQuery(sql);
		query.setParameter("idTramiteVersion", idTramiteVersion);

		@SuppressWarnings("unchecked")
		final List<JPasoTramitacion> results = query.getResultList();

		final List<TramitePaso> resultado = new ArrayList<>();
		if (results != null && !results.isEmpty()) {
			for (final Iterator<JPasoTramitacion> iterator = results.iterator(); iterator.hasNext();) {
				final JPasoTramitacion jpasoTramitacion = iterator.next();
				resultado.add(jpasoTramitacion.toModel());
			}
		}

		return resultado;
	}

	@Override
	public void updateTramitePaso(final TramitePaso tramitePaso) {

		if (tramitePaso == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}

		final JPasoTramitacion jTramitePaso = entityManager.find(JPasoTramitacion.class, tramitePaso.getCodigo());
		if (jTramitePaso == null) {
			throw new NoExisteDato(STRING_NO_EXISTE_TRAMITE_PASO + tramitePaso.getIdPasoTramitacion());
		}

		// Mergeamos datos
		final JPasoTramitacion jTramitePasoNew = JPasoTramitacion.fromModel(tramitePaso);
		jTramitePasoNew.setCodigo(jTramitePaso.getCodigo());
		jTramitePasoNew.setVersionTramite(jTramitePaso.getVersionTramite());
		jTramitePasoNew.setTipoPasoTramitacion(jTramitePaso.getTipoPasoTramitacion());
		entityManager.merge(jTramitePasoNew);

	}

	@Override
	public void removeFormulario(final Long idTramitePaso, final Long idFormulario) {
		if (idTramitePaso == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}
		if (idFormulario == null) {
			throw new FaltanDatosException(STRING_FALTA_FORMULARIO);
		}

		final JPasoTramitacion jTramitePaso = entityManager.find(JPasoTramitacion.class, idTramitePaso);
		if (jTramitePaso == null) {
			throw new NoExisteDato(STRING_NO_EXISTE_TRAMITE_PASO + idTramitePaso);
		}
		final JFormularioTramite jFormulario = entityManager.find(JFormularioTramite.class, idFormulario);
		if (jFormulario == null) {
			throw new NoExisteDato(STRING_NO_EXISTE_FORMULARIO + idFormulario);
		}

		if (jFormulario.getFormulario() != null) {
			formularioInternoDao.removeFormulario(jFormulario.getFormulario().getCodigo());
		}

		jTramitePaso.getPasoRellenar().getFormulariosTramite().remove(jFormulario);
		entityManager.remove(jFormulario);
	}

	@Override
	public TramitePaso getTramitePaso(final Long idTramitePaso) {

		if (idTramitePaso == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}

		final JPasoTramitacion jTramitePaso = entityManager.find(JPasoTramitacion.class, idTramitePaso);
		if (jTramitePaso == null) {
			throw new NoExisteDato(STRING_NO_EXISTE_TRAMITE_PASO + idTramitePaso);
		}

		return jTramitePaso.toModel();
	}

	@Override
	public FormularioTramite getFormulario(final Long idFormularioTramite) {
		final JFormularioTramite jFormularioTramite = entityManager.find(JFormularioTramite.class, idFormularioTramite);
		return jFormularioTramite.toModel();
	}

	@Override
	public FormularioTramite addFormularioTramite(final FormularioTramite formularioTramite, final Long idTramitePaso,
			final Long idFormularioInterno) {
		final JFormularioTramite jFormulariotramite = JFormularioTramite.fromModel(formularioTramite);
		final JFormulario jFormularioInterno = entityManager.find(JFormulario.class, idFormularioInterno);
		final JPasoTramitacion jpasoRellenar = entityManager.find(JPasoTramitacion.class, idTramitePaso);
		jFormulariotramite.setOrden(jpasoRellenar.getPasoRellenar().getFormulariosTramite().size());
		jFormulariotramite.setFormulario(jFormularioInterno);
		entityManager.persist(jFormulariotramite);
		jpasoRellenar.getPasoRellenar().getFormulariosTramite().add(jFormulariotramite);
		// entityManager.merge(jpasoRellenar);
		return jFormulariotramite.toModel();
	}

	@Override
	public void updateFormularioTramite(final FormularioTramite formularioTramite) {
		final JFormularioTramite jFormulariotramiteOriginal = entityManager.find(JFormularioTramite.class,
				formularioTramite.getCodigo());
		final JFormularioTramite jFormulariotramite = JFormularioTramite.fromModel(formularioTramite);
		jFormulariotramite.setPasosRellenar(jFormulariotramiteOriginal.getPasosRellenar());
		jFormulariotramite.setFormulario(jFormulariotramiteOriginal.getFormulario());
		entityManager.merge(jFormulariotramite);
	}

	@Override
	public Documento getDocumento(final Long idDocumento) {
		final JAnexoTramite jAnexo = entityManager.find(JAnexoTramite.class, idDocumento);
		return jAnexo.toModel();
	}

	@Override
	public Documento addDocumentoTramite(final Documento documento, final Long idTramitePaso) {
		final JAnexoTramite janexoTramite = JAnexoTramite.fromModel(documento);
		final JPasoTramitacion jpasoTramitacion = entityManager.find(JPasoTramitacion.class, idTramitePaso);
		janexoTramite.setOrden(jpasoTramitacion.getPasoAnexar().getAnexosTramite().size());
		janexoTramite.setPasoAnexar(jpasoTramitacion.getPasoAnexar());
		entityManager.persist(janexoTramite);
		jpasoTramitacion.getPasoAnexar().getAnexosTramite().add(janexoTramite);
		return janexoTramite.toModel();
	}

	@Override
	public void updateDocumentoTramite(final Documento documento) {
		final JAnexoTramite jAnexoTramiteOriginal = entityManager.find(JAnexoTramite.class, documento.getCodigo());
		final JAnexoTramite jAnexoTramite = JAnexoTramite.fromModel(documento);
		jAnexoTramite.setPasoAnexar(jAnexoTramiteOriginal.getPasoAnexar());
		entityManager.merge(jAnexoTramite);
	}

	@Override
	public void removeDocumento(final Long idTramitePaso, final Long idDocumento) {
		final JPasoTramitacion jpasoRellenar = entityManager.find(JPasoTramitacion.class, idTramitePaso);
		final JAnexoTramite janexoTramite = entityManager.find(JAnexoTramite.class, idDocumento);
		jpasoRellenar.getPasoAnexar().getAnexosTramite().remove(janexoTramite);
		entityManager.remove(janexoTramite);
	}

	@Override
	public Tasa getTasa(final Long idTasa) {
		final JPagoTramite jPagotramite = entityManager.find(JPagoTramite.class, idTasa);
		return jPagotramite.toModel();
	}

	@Override
	public Tasa addTasaTramite(final Tasa tasa, final Long idTramitePaso) {
		final JPasoTramitacion jpasoRellenar = entityManager.find(JPasoTramitacion.class, idTramitePaso);
		final JPagoTramite jpagoTramite = JPagoTramite.fromModel(tasa);
		jpagoTramite.setOrden(jpasoRellenar.getPasoPagos().getPagosTramite().size());
		jpagoTramite.setPasoPagos(jpasoRellenar.getPasoPagos());
		entityManager.persist(jpagoTramite);
		jpasoRellenar.getPasoPagos().getPagosTramite().add(jpagoTramite);
		return jpagoTramite.toModel();
	}

	@Override
	public void updateTasaTramite(final Tasa tasa) {
		final JPagoTramite jpagoTramiteOld = entityManager.find(JPagoTramite.class, tasa.getCodigo());
		final JPagoTramite jpagoTramite = JPagoTramite.fromModel(tasa);
		jpagoTramite.setPasoPagos(jpagoTramiteOld.getPasoPagos());
		if (jpagoTramite.getScriptObligatoriedad() != null) {
			final JScript script = entityManager.merge(jpagoTramite.getScriptObligatoriedad());
			jpagoTramite.setScriptObligatoriedad(script);
		}
		if (jpagoTramite.getScriptDatosPago() != null) {
			final JScript script = entityManager.merge(jpagoTramite.getScriptDatosPago());
			jpagoTramite.setScriptDatosPago(script);
		}
		entityManager.merge(jpagoTramite);
	}

	@Override
	public void removeTasa(final Long idTramitePaso, final Long idTasa) {
		final JPasoTramitacion jpasoRellenar = entityManager.find(JPasoTramitacion.class, idTramitePaso);
		final JPagoTramite jpagoTramite = entityManager.find(JPagoTramite.class, idTasa);
		jpasoRellenar.getPasoPagos().getPagosTramite().remove(jpagoTramite);
		entityManager.remove(jpagoTramite);
	}

	@Override
	public Fichero uploadDocAnexo(final Long idDocumento, final Fichero fichero) {
		final JAnexoTramite jAnexoTramite = entityManager.find(JAnexoTramite.class, idDocumento);
		if (jAnexoTramite == null) {
			throw new NoExisteDato("No existe anexo " + idDocumento);
		}

		jAnexoTramite.setFicheroPlantilla(JFichero.fromModel(fichero));
		entityManager.merge(jAnexoTramite);

		return jAnexoTramite.getFicheroPlantilla().toModel();
	}

	@Override
	public void removeDocAnexo(final Long idDocumento) {
		final JAnexoTramite janexoTramite = entityManager.find(JAnexoTramite.class, idDocumento);
		final JFichero fic = janexoTramite.getFicheroPlantilla();
		if (fic != null) {
			janexoTramite.setFicheroPlantilla(null);
			entityManager.merge(janexoTramite);
			entityManager.remove(fic);
		}
	}

	@Override
	public List<FormateadorFormulario> getFormateadoresTramiteVersion(final Long idTramiteVersion) {

		final String sql = "Select plan.formateadorFormulario from  JPlantillaFormulario plan inner join plan.formulario fr where fr in (select forms.formulario from JPasoTramitacion pasot inner join pasot.pasoRellenar pasor inner join pasor.formulariosTramite forms  where pasot.versionTramite.codigo = :idTramiteVersion) ";

		final Query query = entityManager.createQuery(sql);
		query.setParameter("idTramiteVersion", idTramiteVersion);

		@SuppressWarnings("unchecked")
		final List<JFormateadorFormulario> results = query.getResultList();

		final List<FormateadorFormulario> resultado = new ArrayList<>();
		if (results != null && !results.isEmpty()) {
			for (final Iterator<JFormateadorFormulario> iterator = results.iterator(); iterator.hasNext();) {
				final JFormateadorFormulario jformateadorFormulario = iterator.next();
				resultado.add(jformateadorFormulario.toModel());
			}
		}

		return resultado;
	}

	@Override
	public List<DisenyoFormulario> getFormulariosTramiteVersion(final Long idTramiteVersion) {
		final String sql = "select forms.formulario from JPasoTramitacion pasot inner join pasot.pasoRellenar pasor inner join pasor.formulariosTramite forms  where pasot.versionTramite.codigo = :idTramiteVersion ";

		final Query query = entityManager.createQuery(sql);
		query.setParameter("idTramiteVersion", idTramiteVersion);

		@SuppressWarnings("unchecked")
		final List<JFormulario> results = query.getResultList();

		final List<DisenyoFormulario> resultado = new ArrayList<>();
		if (results != null && !results.isEmpty()) {
			for (final Iterator<JFormulario> iterator = results.iterator(); iterator.hasNext();) {
				final JFormulario jformulario = iterator.next();
				resultado.add(jformulario.toModelCompleto());
			}
		}

		return resultado;
	}

	@Override
	public List<Fichero> getFicherosTramiteVersion(final Long idTramiteVersion) {
		final List<Fichero> resultado = new ArrayList<>();

		// Ficheros en los anexos de tramite.
		final String sqlAnexoTramite = "select anexo.ficheroPlantilla from JPasoTramitacion pt inner join pt.pasoAnexar ptAnexo inner join ptAnexo.anexosTramite anexo where pt.versionTramite.codigo = :idTramiteVersion and anexo.ficheroPlantilla is not null ";

		final Query queryAnexoTramite = entityManager.createQuery(sqlAnexoTramite);
		queryAnexoTramite.setParameter("idTramiteVersion", idTramiteVersion);

		@SuppressWarnings("unchecked")
		final List<JFichero> resultsAnexoTramite = queryAnexoTramite.getResultList();

		if (resultsAnexoTramite != null && !resultsAnexoTramite.isEmpty()) {
			for (final Iterator<JFichero> iterator = resultsAnexoTramite.iterator(); iterator.hasNext();) {
				final JFichero jfichero = iterator.next();
				resultado.add(jfichero.toModel());
			}
		}

		// Ficheros en las plantilla de formularios.
		final String sqlPlantillasIdiomas = "select plaIdi.fichero from JPlantillaIdiomaFormulario plaIdi where plaIdi.plantillaFormulario.formulario in (select forms.formulario from JPasoTramitacion pasot inner join pasot.pasoRellenar pasor inner join pasor.formulariosTramite forms  where pasot.versionTramite.codigo = :idTramiteVersion ) )";

		final Query queryPlantillasIdiomas = entityManager.createQuery(sqlPlantillasIdiomas);
		queryPlantillasIdiomas.setParameter("idTramiteVersion", idTramiteVersion);

		@SuppressWarnings("unchecked")
		final List<JFichero> resultsPlantillasIdiomas = queryPlantillasIdiomas.getResultList();

		if (resultsPlantillasIdiomas != null && !resultsPlantillasIdiomas.isEmpty()) {
			for (final Iterator<JFichero> iterator = resultsPlantillasIdiomas.iterator(); iterator.hasNext();) {
				final JFichero jfichero = iterator.next();
				resultado.add(jfichero.toModel());
			}
		}

		// Ficheros en las imagen de formularios.
		final String sqlImagenFormularios = "select imagForm.fichero from JImagenFormulario imagForm where imagForm.elementoFormulario.listaElementosFormulario.paginaFormulario.formulario in (select forms.formulario from JPasoTramitacion pasot inner join pasot.pasoRellenar pasor inner join pasor.formulariosTramite forms  where pasot.versionTramite.codigo = :idTramiteVersion ) )";

		final Query queryImagenFormularios = entityManager.createQuery(sqlImagenFormularios);
		queryImagenFormularios.setParameter("idTramiteVersion", idTramiteVersion);

		@SuppressWarnings("unchecked")
		final List<JFichero> resultsImagenFormularios = queryImagenFormularios.getResultList();

		if (resultsImagenFormularios != null && !resultsImagenFormularios.isEmpty()) {
			for (final Iterator<JFichero> iterator = resultsImagenFormularios.iterator(); iterator.hasNext();) {
				final JFichero jfichero = iterator.next();
				resultado.add(jfichero.toModel());
			}
		}

		return resultado;
	}

	@Override
	public void intercambiarFormularios(final Long idFormulario1, final Long idFormulario2) {
		final JFormularioTramite jformulario1 = entityManager.find(JFormularioTramite.class, idFormulario1);
		final JFormularioTramite jformulario2 = entityManager.find(JFormularioTramite.class, idFormulario2);
		final int orden = jformulario1.getOrden();
		jformulario1.setOrden(jformulario2.getOrden());
		jformulario2.setOrden(orden);
		entityManager.merge(jformulario1);
		entityManager.merge(jformulario2);
	}

	@Override
	public boolean checkTasaRepetida(final Long idTramiteVersion, final String identificador, final Long idTasa) {

		if (idTramiteVersion == null || identificador == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}

		final StringBuilder sqlCount = new StringBuilder(
				"Select count(*) From JPagoTramite p where  p.pasoPagos.pasoTramitacion.versionTramite.codigo = ");
		sqlCount.append(idTramiteVersion);
		sqlCount.append(" and p.identificador = '");
		sqlCount.append(identificador);
		sqlCount.append("' ");
		if (idTasa != null) {
			sqlCount.append(" and p.codigo != " + idTasa);
		}

		final Query query = entityManager.createQuery(sqlCount.toString());
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
	public boolean checkAnexoRepetido(final Long idTramiteVersion, final String identificador, final Long idAnexo) {

		if (idTramiteVersion == null || identificador == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}

		final StringBuilder sqlCount = new StringBuilder(
				"Select count(*) From JAnexoTramite p where p.pasoAnexar.pasoTramitacion.versionTramite.codigo = ");
		sqlCount.append(idTramiteVersion);
		sqlCount.append(" and p.identificadorDocumento = '");
		sqlCount.append(identificador);
		sqlCount.append("' ");
		if (idAnexo != null) {
			sqlCount.append(" and p.codigo != " + idAnexo);
		}

		final Query query = entityManager.createQuery(sqlCount.toString());
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
	public Long importar(final FilaImportarTramiteVersion filaTramiteVersion, final TramitePaso tramitePaso,
			final Long idTramiteVersion, final Long idEntidad, final Map<Long, DisenyoFormulario> formularios,
			final Map<Long, Fichero> ficheros, final Map<Long, byte[]> ficherosContent,
			final Map<Long, FormateadorFormulario> formateadores) {

		final JVersionTramite jVersionTramite = entityManager.find(JVersionTramite.class, idTramiteVersion);

		// Checkeamos si es de tipo rellenar, y hacemos un prepaso y relacionamos el
		// idformulario con el identificador del formulario.
		/// y seteamos el idformulariointerno a nulo (ya seteareamos luego
		final Map<String, Long> formulariosId = new HashMap<>();
		if (tramitePaso instanceof TramitePasoRellenar) {
			for (final FormularioTramite formulario : ((TramitePasoRellenar) tramitePaso).getFormulariosTramite()) {
				if (formulario.getIdFormularioInterno() != null) {
					formulariosId.put(formulario.getIdentificador(), formulario.getIdFormularioInterno());
					formulario.setIdFormularioInterno(null);
				}
			}
		}
		JPasoTramitacion jpaso = JPasoTramitacion.clonar(JPasoTramitacion.fromModel(tramitePaso), jVersionTramite);

		// Checkeamos si es de tipo anexar, tenía anexado un fichero.
		if (tramitePaso instanceof TramitePasoAnexar) {
			for (final Documento documento : ((TramitePasoAnexar) tramitePaso).getDocumentos()) {
				if (documento.getAyudaFichero() != null && documento.getAyudaFichero().getCodigo() != null
						&& ficheros.containsKey(documento.getAyudaFichero().getCodigo())) {

					// Obtenemos el fichero del zip y su contenido.
					final Fichero fichero = ficheros.get(documento.getAyudaFichero().getCodigo());
					final byte[] ficheroContent = ficherosContent.get(documento.getAyudaFichero().getCodigo());

					// Generamos un nuevo fichero y guardamos su contenido.
					fichero.setCodigo(null);
					final JFichero jfichero = JFichero.fromModel(fichero);
					entityManager.persist(jfichero);
					fichero.setCodigo(jfichero.getCodigo());
					ficheroExternoDao.guardarFichero(idEntidad, fichero, ficheroContent);

					// Seteamos el jfichero que toque.
					for (final JAnexoTramite janexotramite : jpaso.getPasoAnexar().getAnexosTramite()) {
						if (janexotramite.getIdentificadorDocumento().equals(documento.getIdentificador())) {
							janexotramite.setFicheroPlantilla(jfichero);
							break;
						}
					}
				}
			}
		}

		if (jpaso.getPasoRegistrar() != null) {
			jpaso.getPasoRegistrar().setCodigoLibroRegistro(filaTramiteVersion.getTramiteVersionResultadoLibro());
			jpaso.getPasoRegistrar().setCodigoOficinaRegistro(filaTramiteVersion.getTramiteVersionResultadoOficina());
			jpaso.getPasoRegistrar().setCodigoTipoAsunto(filaTramiteVersion.getTramiteVersionResultadoTipo());
		}
		entityManager.persist(jpaso);
		entityManager.flush();

		jpaso = entityManager.find(JPasoTramitacion.class, jpaso.getCodigo());

		// Checkeamos si es de tipo rellenar, entonces buscamos el formulario, lo
		// creamos y lo asociamos
		if (jpaso.getPasoRellenar() != null && jpaso.getPasoRellenar().getFormulariosTramite() != null
				&& !jpaso.getPasoRellenar().getFormulariosTramite().isEmpty()) {
			for (final JFormularioTramite formulario : jpaso.getPasoRellenar().getFormulariosTramite()) {
				// Si contiene el identificador, es que tiene un diseño ya hecho
				if (formulariosId.containsKey(formulario.getIdentificador())) {

					// Creamos la id.
					formulario.setDescripcion(JLiteral.clonar(formulario.getDescripcion()));
					final Long idJFormulario = formularioInternoDao.addFormulario(formulario.toModel(), false);

					// Actualizamos el jformulario
					final Long idFormularioInterno = formulariosId.get(formulario.getIdentificador());
					final DisenyoFormulario formularioInterno = formularios.get(idFormularioInterno);

					final DisenyoFormulario disenyoFormularioAlmacenado = formularioInternoDao
							.getFormularioById(idJFormulario);
					disenyoFormularioAlmacenado.setMostrarCabecera(formularioInterno.isMostrarCabecera());
					disenyoFormularioAlmacenado
							.setPermitirAccionesPersonalizadas(formularioInterno.isPermitirAccionesPersonalizadas());
					if (formularioInterno.getScriptPlantilla() != null) {
						formularioInterno.getScriptPlantilla().setCodigo(null);
					}

					if (formularioInterno.getTextoCabecera() != null) {
						formularioInterno.getTextoCabecera().setCodigo(null);
						if (formularioInterno.getTextoCabecera().getTraducciones() != null) {
							for (final Traduccion trad : formularioInterno.getTextoCabecera().getTraducciones()) {
								trad.setCodigo(null);
							}
						}
					}

					formularioInternoDao.updateFormulario(disenyoFormularioAlmacenado);

					this.anyadirPaginas(formularioInterno.getPaginas(), idJFormulario);
					this.anyadirPlantillas(formularioInterno.getPlantillas(), idJFormulario, formateadores,
							ficherosContent, idEntidad);

					// Refrescamos la relacion entre el formulario del paso y el formulario de
					// diseño.
					final JFormulario jform = entityManager.find(JFormulario.class, idJFormulario);
					final JFormularioTramite jformNew = entityManager.find(JFormularioTramite.class,
							formulario.getCodigo());
					jformNew.setFormulario(jform);
					entityManager.merge(jformNew);

				}
			}
		}

		return jpaso.getCodigo();
	}

	/**
	 * Añaden plantilla y su lista de plantillas idiomas.
	 *
	 * @param mplantillas
	 * @param jformulario
	 * @param formateadores
	 */
	private void anyadirPlantillas(final List<PlantillaFormulario> mplantillas, final Long idFormularioInterno,
			final Map<Long, FormateadorFormulario> formateadores, final Map<Long, byte[]> ficherosContent,
			final Long idEntidad) {
		for (final PlantillaFormulario mplantilla : mplantillas) {

			final List<PlantillaIdiomaFormulario> plantillasIdioma = mplantilla.getPlantillasIdiomaFormulario();

			// Preparamos la plantilla
			if (mplantilla.getIdFormateadorFormulario() != null) {
				final String identificadorFormateador = formateadores.get(mplantilla.getIdFormateadorFormulario())
						.getIdentificador();
				final FormateadorFormulario formateador = formateadorFormularioDao
						.getByCodigo(identificadorFormateador);
				mplantilla.setIdFormateadorFormulario(formateador.getCodigo());
			}
			mplantilla.setCodigo(null);

			final Long idPlantilla = formularioInternoDao.addPlantilla(idFormularioInterno, mplantilla);

			for (final PlantillaIdiomaFormulario plantilla : plantillasIdioma) {
				plantilla.setCodigo(null);
				final byte[] contenido = ficherosContent.get(plantilla.getFichero().getCodigo());
				plantilla.getFichero().setCodigo(null);
				final PlantillaIdiomaFormulario mplantillaIdioma = formularioInternoDao
						.uploadPlantillaIdiomaFormulario(idPlantilla, plantilla);
				entityManager.flush();
				ficheroExternoDao.guardarFichero(idEntidad, mplantillaIdioma.getFichero(), contenido);

			}

		}
	}

	/**
	 * Añaden paginas y sus componentes.
	 *
	 * @param paginas
	 * @param jformulario
	 */
	private void anyadirPaginas(final List<PaginaFormulario> paginas, final Long idFormulario) {

		for (final PaginaFormulario paginaFormulario : paginas) {

			final List<LineaComponentesFormulario> lineas = paginaFormulario.getLineas();
			paginaFormulario.setLineas(null);
			final DisenyoFormulario formulario = formularioInternoDao.getFormularioPaginasById(idFormulario);
			if (paginaFormulario.getScriptValidacion() != null) {
				paginaFormulario.getScriptValidacion().setCodigo(null);
			}
			paginaFormulario.setCodigo(null);

			final Long idPagina = formularioInternoDao.addPagina(formulario.getCodigo(), paginaFormulario);

			// Recorremos las lineas anteriormente
			if (lineas != null) {
				for (final LineaComponentesFormulario mlinea : lineas) {
					final ObjetoFormulario objetoFormularioLine = formularioInternoDao
							.addComponente(TypeObjetoFormulario.LINEA, idPagina, null, mlinea.getOrden(), null);
					final Long idLinea = objetoFormularioLine.getCodigo();
					mlinea.setCodigo(idLinea);
					final List<ComponenteFormulario> componentes = mlinea.getComponentes();
					mlinea.setComponentes(null);
					formularioInternoDao.updateFormulario(formulario);

					// Añadir los componentes de la linea
					for (final ComponenteFormulario componente : componentes) {
						final ObjetoFormulario retorno = formularioInternoDao.addComponente(componente.getTipo(),
								idPagina, idLinea, componente.getOrden(), null);
						entityManager.flush();
						componente.setCodigo(retorno.getCodigo());
						if (componente.getAyuda() != null) {
							componente.getAyuda().setCodigo(null);
							if (componente.getAyuda().getTraducciones() != null) {
								for (final Traduccion traduccion : componente.getAyuda().getTraducciones()) {
									traduccion.setCodigo(null);
								}
							}
							componente.setAyuda(componente.getAyuda());
						}
						if (componente.getTexto() != null) {
							componente.getTexto().setCodigo(null);
							if (componente.getTexto().getTraducciones() != null) {
								for (final Traduccion traduccion : componente.getTexto().getTraducciones()) {
									traduccion.setCodigo(null);
								}
							}
							componente.setTexto(componente.getTexto());
						}
						// formularioInternoDao.updateComponente(componente);

					}

				}
			}

			// Recorremos las lineas anteriormente
			// for (final JListaElementosFormulario lista :
			// paginaFormulario.getListaElementos()) {
			// final ObjetoFormulario objetoFormularioListaElemento = formularioInternoDao
			// .addComponente(TypeObjetoFormulario.LISTA_ELEMENTOS, idPagina, null, null,
			// null);
			// lista.setCodigo(objetoFormularioListaElemento.getCodigo());
			// lista.setPaginaFormulario(jpag);
			// jpag.getListasElementosFormulario().add(lista);

			// final DisenyoFormulario form = jformulario.toModel();
			// form.setPagina(jpag.toModel(), i);

			// Habria que actualizar el formulario para que se actualice la linea
			// formularioInternoDao.updateFormulario(form);

			// lista.getElementoFormulario().setListaElementosFormulario(lista);
			// formularioInternoDao.updateComponente(lista.getElementoFormulario().toModel(JElementoFormulario.class));

			// Reactualizamos el formulario por si acaso
			// jformulario = entityManager.find(JFormulario.class, jformulario.getCodigo());
			// }

		}
	}

}
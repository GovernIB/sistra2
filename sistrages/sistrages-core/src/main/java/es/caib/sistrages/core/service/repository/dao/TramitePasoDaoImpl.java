package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampo;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoCheckbox;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoOculto;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSelector;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoTexto;
import es.caib.sistrages.core.api.model.ComponenteFormularioEtiqueta;
import es.caib.sistrages.core.api.model.ComponenteFormularioImagen;
import es.caib.sistrages.core.api.model.ComponenteFormularioSeccion;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.ObjetoFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.ParametroDominio;
import es.caib.sistrages.core.api.model.PlantillaFormulario;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoAnexar;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.ValorListaFija;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramiteRegistro;
import es.caib.sistrages.core.api.model.types.TypeFormularioGestor;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
import es.caib.sistrages.core.service.repository.model.JAnexoTramite;
import es.caib.sistrages.core.service.repository.model.JElementoFormulario;
import es.caib.sistrages.core.service.repository.model.JFichero;
import es.caib.sistrages.core.service.repository.model.JFormateadorFormulario;
import es.caib.sistrages.core.service.repository.model.JFormulario;
import es.caib.sistrages.core.service.repository.model.JFormularioTramite;
import es.caib.sistrages.core.service.repository.model.JGestorExternoFormularios;
import es.caib.sistrages.core.service.repository.model.JLineaFormulario;
import es.caib.sistrages.core.service.repository.model.JLiteral;
import es.caib.sistrages.core.service.repository.model.JPaginaFormulario;
import es.caib.sistrages.core.service.repository.model.JPagoTramite;
import es.caib.sistrages.core.service.repository.model.JPasoAnexar;
import es.caib.sistrages.core.service.repository.model.JPasoCaptura;
import es.caib.sistrages.core.service.repository.model.JPasoPagos;
import es.caib.sistrages.core.service.repository.model.JPasoRegistrar;
import es.caib.sistrages.core.service.repository.model.JPasoRellenar;
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
		jTramitePasoNew.setTipoPaso(jTramitePaso.getTipoPaso());

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

		// reordenamos formularios
		if (!jTramitePaso.getPasoRellenar().getFormulariosTramite().isEmpty()) {
			final List<JFormularioTramite> listaFormularioTramite = new ArrayList<>();
			listaFormularioTramite.addAll(jTramitePaso.getPasoRellenar().getFormulariosTramite());
			Collections.sort(listaFormularioTramite,
					(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));
			int orden = 1;
			for (final JFormularioTramite jFormularioTramite : listaFormularioTramite) {
				jFormularioTramite.setOrden(orden);
				orden++;
				entityManager.merge(jFormularioTramite);
			}
		}

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
		jFormulariotramite.setOrden((jpasoRellenar.getPasoRellenar().getFormulariosTramite().size()) + 1);
		jFormulariotramite.setFormulario(jFormularioInterno);
		entityManager.persist(jFormulariotramite);
		jpasoRellenar.getPasoRellenar().getFormulariosTramite().add(jFormulariotramite);
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
		janexoTramite.setOrden((jpasoTramitacion.getPasoAnexar().getAnexosTramite().size()) + 1);
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

		// reordenamos documentos
		if (!jpasoRellenar.getPasoAnexar().getAnexosTramite().isEmpty()) {
			final List<JAnexoTramite> listaAnexosTramite = new ArrayList<>();
			listaAnexosTramite.addAll(jpasoRellenar.getPasoAnexar().getAnexosTramite());
			Collections.sort(listaAnexosTramite,
					(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));
			int orden = 1;
			for (final JAnexoTramite jAnexoTramite : listaAnexosTramite) {
				jAnexoTramite.setOrden(orden);
				orden++;
				entityManager.merge(jAnexoTramite);
			}
		}
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
		jpagoTramite.setOrden((jpasoRellenar.getPasoPagos().getPagosTramite().size()) + 1);
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

		// reordenamos tasas
		if (!jpasoRellenar.getPasoPagos().getPagosTramite().isEmpty()) {
			final List<JPagoTramite> listaTasasTramite = new ArrayList<>();
			listaTasasTramite.addAll(jpasoRellenar.getPasoPagos().getPagosTramite());
			Collections.sort(listaTasasTramite,
					(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));
			int orden = 1;
			for (final JPagoTramite jPagoTramite : listaTasasTramite) {
				jPagoTramite.setOrden(orden);
				orden++;
				entityManager.merge(jPagoTramite);
			}
		}

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
	public void updateFormulario(Long idFormulario) {
		final JFormulario jFormulario = entityManager.find(JFormulario.class, idFormulario);
		if (jFormulario != null) {
			jFormulario.setScriptPlantilla(null);
			entityManager.merge(jFormulario);
		}

	}

	@Override
	public List<FormateadorFormulario> getFormateadoresTramiteVersion(final Long idTramiteVersion) {

		final String sql = "Select distinct plan.formateadorFormulario from  JPlantillaFormulario plan inner join plan.formulario fr where fr in (select forms.formulario from JPasoTramitacion pasot inner join pasot.pasoRellenar pasor inner join pasor.formulariosTramite forms  where pasot.versionTramite.codigo = :idTramiteVersion) ";

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
		final String sqlPlantillasIdiomas = "select plaIdi.fichero from JPlantillaIdiomaFormulario plaIdi where plaIdi.plantillaFormulario.formulario in (select forms.formulario from JPasoTramitacion pasot inner join pasot.pasoRellenar pasor inner join pasor.formulariosTramite forms  where pasot.versionTramite.codigo = :idTramiteVersion ) ";

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
		final String sqlImagenFormularios = "select imagForm.fichero from JImagenFormulario imagForm where imagForm.elementoFormulario.listaElementosFormulario.paginaFormulario.formulario in (select forms.formulario from JPasoTramitacion pasot inner join pasot.pasoRellenar pasor inner join pasor.formulariosTramite forms  where pasot.versionTramite.codigo = :idTramiteVersion ) ";

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

	private GestorExternoFormularios getGEF(String identificadorGFE, final Long idArea) {
		GestorExternoFormularios gfe = null;
		final String sql = "Select t From JGestorExternoFormularios t where t.identificador = :identificador and t.area.codigo = :idArea";

		final Query query = entityManager.createQuery(sql);
		query.setParameter("identificador", identificadorGFE);
		query.setParameter("idArea", idArea);

		@SuppressWarnings("unchecked")
		final List<JGestorExternoFormularios> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final Iterator<JGestorExternoFormularios> iterator = results.iterator(); iterator.hasNext()
					&& gfe == null;) {
				final JGestorExternoFormularios jgestor = iterator.next();
				gfe = jgestor.toModel();
			}
		}

		return gfe;

	}

	@Override
	public Long importar(final FilaImportarTramiteRegistro filaTramiteVersion, final TramitePaso tramitePaso,
			final Long idTramiteVersion, final Long idEntidad, final Map<Long, DisenyoFormulario> formularios,
			final Map<Long, Fichero> ficheros, final Map<Long, byte[]> ficherosContent,
			final Map<Long, FormateadorFormulario> formateadores, final Map<Long, Long> mapFormateadores,
			final Map<Long, GestorExternoFormularios> gestores, final Map<Long, Long> mapGestores,
			final Map<Long, Long> idDominiosEquivalencia, final Long idArea) {

		final JVersionTramite jVersionTramite = entityManager.find(JVersionTramite.class, idTramiteVersion);

		// Checkeamos si es de tipo rellenar, y hacemos un prepaso y relacionamos el
		// idformulario con el identificador del formulario.
		/// y seteamos el idformulariointerno a nulo (ya seteareamos luego
		final Map<String, Long> formulariosId = new HashMap<>();
		if (tramitePaso instanceof TramitePasoRellenar) {
			for (final FormularioTramite formulario : ((TramitePasoRellenar) tramitePaso).getFormulariosTramite()) {
				if (formulario.getTipoFormulario() == TypeFormularioGestor.INTERNO
						&& formulario.getIdFormularioInterno() != null) {
					formulariosId.put(formulario.getIdentificador(), formulario.getIdFormularioInterno());
					formulario.setIdFormularioInterno(null);
				}
				if (formulario.getTipoFormulario() == TypeFormularioGestor.EXTERNO) {
					Long id = formulario.getFormularioGestorExterno().getCodigo();
					Long idGestor = mapGestores.get(id);
					GestorExternoFormularios form = gestores.get(idGestor);
					formulario.setFormularioGestorExterno(form);
					if (formulario.getIdFormularioInterno() != null) {
						formulariosId.put(formulario.getIdentificador(), formulario.getIdFormularioInterno());
						formulario.setIdFormularioInterno(null);
					}
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
			jpaso.getPasoRegistrar().setCodigoLibroRegistro(filaTramiteVersion.getLibro());
			jpaso.getPasoRegistrar().setCodigoOficinaRegistro(filaTramiteVersion.getOficina());
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
					FormularioTramite formT = formulario.toModel();

					// Si es tipo externo, habra que asociarlo al formulario gestor externo
//					if (formulario.getTipoFormulario().equals("E")) {
//						Long id = formT.getFormularioGestorExterno().getCodigo();
//						Long idGestor = mapGestores.get(id);
//						GestorExternoFormularios form = gestores.get(idGestor);
//						formT.setFormularioGestorExterno(form);
//					} else {
//						formT.setFormularioGestorExterno(null);
//					}
					final Long idJFormulario = formularioInternoDao.addFormulario(formT, false);

					if (formulario.getTipoFormulario().equals("I") || formulario.getTipoFormulario().equals("E")) {

						// Actualizamos el jformulario
						final Long idFormularioInterno = formulariosId.get(formulario.getIdentificador());
						final DisenyoFormulario formularioInterno = formularios.get(idFormularioInterno);

						final DisenyoFormulario disenyoFormularioAlmacenado = formularioInternoDao
								.getFormularioById(idJFormulario);
						disenyoFormularioAlmacenado.setMostrarCabecera(formularioInterno.isMostrarCabecera());
						disenyoFormularioAlmacenado.setPermitirAccionesPersonalizadas(
								formularioInterno.isPermitirAccionesPersonalizadas());
						if (formularioInterno.getScriptPlantilla() != null) {
							formularioInterno.getScriptPlantilla().setCodigo(null);
							disenyoFormularioAlmacenado
									.setScriptPlantilla(Script.clonar(formularioInterno.getScriptPlantilla()));
						}

						if (formularioInterno.getTextoCabecera() != null) {
							formularioInterno.getTextoCabecera().setCodigo(null);
							if (formularioInterno.getTextoCabecera().getTraducciones() != null) {
								for (final Traduccion trad : formularioInterno.getTextoCabecera().getTraducciones()) {
									trad.setCodigo(null);
								}
							}
							disenyoFormularioAlmacenado.setTextoCabecera(formularioInterno.getTextoCabecera());
						}
						formularioInternoDao.updateFormulario(disenyoFormularioAlmacenado);

						this.anyadirPaginas(formularioInterno.getPaginas(), idJFormulario, ficherosContent,
								idDominiosEquivalencia, idEntidad);
						this.anyadirPlantillas(formularioInterno.getPlantillas(), idJFormulario, formateadores,
								mapFormateadores, ficherosContent, idEntidad);

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
			final Map<Long, FormateadorFormulario> formateadores, final Map<Long, Long> mapFormateadores,
			final Map<Long, byte[]> ficherosContent, final Long idEntidad) {
		for (final PlantillaFormulario mplantilla : mplantillas) {

			final List<PlantillaIdiomaFormulario> plantillasIdioma = mplantilla.getPlantillasIdiomaFormulario();

			// Preparamos la plantilla
			if (mplantilla.getIdFormateadorFormulario() != null) {
				final Long idFormateador = mapFormateadores.get(mplantilla.getIdFormateadorFormulario());
				mplantilla.setIdFormateadorFormulario(idFormateador);
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
	 * @param idFormulario
	 * @param ficherosContent
	 * @param idDominiosEquivalencia
	 * @param idEntidad
	 */
	private void anyadirPaginas(final List<PaginaFormulario> paginas, final Long idFormulario,
			final Map<Long, byte[]> ficherosContent, final Map<Long, Long> idDominiosEquivalencia,
			final Long idEntidad) {

		int ordenPagina = 1;
		Collections.sort(paginas, new Comparator<PaginaFormulario>() {
			@Override
			public int compare(final PaginaFormulario p1, final PaginaFormulario p2) {
				return Integer.compare(p1.getOrden(), p2.getOrden());
			}

		});
		for (final PaginaFormulario paginaFormulario : paginas) {
			paginaFormulario.setOrden(ordenPagina);
			final List<LineaComponentesFormulario> lineas = paginaFormulario.getLineas();
			paginaFormulario.setIdentificador(paginaFormulario.getIdentificador());
			paginaFormulario.setLineas(null);
			final DisenyoFormulario formulario = formularioInternoDao.getFormularioPaginasById(idFormulario);
			if (paginaFormulario.getScriptValidacion() != null) {
				paginaFormulario.getScriptValidacion().setCodigo(null);
			}
			if (paginaFormulario.getScriptNavegacion() != null) {
				paginaFormulario.getScriptNavegacion().setCodigo(null);
			}
			paginaFormulario.setCodigo(null);

			final Long idPagina = formularioInternoDao.addPagina(formulario.getCodigo(), paginaFormulario);
			// Recorremos las lineas anteriormente
			if (lineas != null) {
				Collections.sort(lineas);
				int ordenLinea = 1;
				for (final LineaComponentesFormulario mlinea : lineas) {
					mlinea.setOrden(ordenLinea);
					if (isTipoEtiqueta(mlinea)) {
						anyadirComponenteEtiqueta((ComponenteFormularioEtiqueta) mlinea.getComponentes().get(0),
								mlinea.getOrden(), idPagina);
						ordenLinea++;
						continue;
					}

					if (isTipoSeccion(mlinea)) {
						anyadirComponenteSeccion((ComponenteFormularioSeccion) mlinea.getComponentes().get(0),
								mlinea.getOrden(), idPagina);
						ordenLinea++;
						continue;
					}

					final ObjetoFormulario objetoFormularioLine = formularioInternoDao
							.addComponente(TypeObjetoFormulario.LINEA, idPagina, null, mlinea.getOrden(), null);
					final Long idLinea = objetoFormularioLine.getCodigo();
					final List<ComponenteFormulario> componentes = mlinea.getComponentes();

					if (componentes != null && !componentes.isEmpty()) {
						Collections.sort(componentes);
						int ordenComponente = 1;

						// Añadir los componentes de la linea
						for (final ComponenteFormulario componente : componentes) {
							componente.setOrden(ordenComponente);
							if (componente instanceof ComponenteFormularioCampoCheckbox) {
								anyadirComponenteCheckbox((ComponenteFormularioCampoCheckbox) componente, idLinea,
										idPagina);
							} else if (componente instanceof ComponenteFormularioCampoSelector) {
								anyadirComponenteSelector((ComponenteFormularioCampoSelector) componente, idLinea,
										idPagina, idDominiosEquivalencia);
							} else if (componente instanceof ComponenteFormularioCampoOculto) {
								anyadirComponenteCampoOculto((ComponenteFormularioCampoOculto) componente, idLinea,
										idPagina);
							} else if (componente instanceof ComponenteFormularioCampoTexto) {
								anyadirComponenteCampoTexto((ComponenteFormularioCampoTexto) componente, idLinea,
										idPagina);
							} else if (componente instanceof ComponenteFormularioImagen) {
								anyadirComponenteImagen((ComponenteFormularioImagen) componente, idLinea, idPagina,
										ficherosContent, idEntidad);
							} else if (componente instanceof ComponenteFormularioCampo) {
								anyadirComponenteCampo((ComponenteFormularioCampo) componente, idLinea, idPagina);
							}
							ordenComponente++;
						}
					}
					ordenLinea++;
				}
			}
			ordenPagina++;
		}

	}

	/**
	 * Los tipos etiqueta se crean de tirón, se pide añadir una etiqueta y
	 * directamente crea una linea con su etiqueta.
	 *
	 * @param mlinea
	 * @return
	 */
	private boolean isTipoEtiqueta(final LineaComponentesFormulario mlinea) {
		boolean tipoEtiqueta = false;
		if (mlinea != null && mlinea.getComponentes() != null && mlinea.getComponentes().size() == 1
				&& (mlinea.getComponentes().get(0) instanceof ComponenteFormularioEtiqueta)) {
			tipoEtiqueta = true;
		}

		return tipoEtiqueta;

	}

	/**
	 * Los tipos etiqueta se crean de tirón, se pide añadir una etiqueta y
	 * directamente crea una linea con su sección.
	 *
	 * @param mlinea
	 * @return
	 */
	private boolean isTipoSeccion(final LineaComponentesFormulario mlinea) {
		boolean tipoEtiqueta = false;
		if (mlinea != null && mlinea.getComponentes() != null && mlinea.getComponentes().size() == 1
				&& (mlinea.getComponentes().get(0) instanceof ComponenteFormularioSeccion)) {
			tipoEtiqueta = true;
		}

		return tipoEtiqueta;

	}

	/**
	 * Añade componente de tipo sección.
	 *
	 * @param componente
	 * @param idLinea
	 * @param idPagina
	 */
	private void anyadirComponenteSeccion(final ComponenteFormularioSeccion componente, final int orden,
			final Long idPagina) {
		final ObjetoFormulario retorno = formularioInternoDao.addComponente(componente.getTipo(), idPagina, null, orden,
				null);
		entityManager.flush();
		final Long id = ((LineaComponentesFormulario) retorno).getComponentes()
				.get(((LineaComponentesFormulario) retorno).getComponentes().size() - 1).getCodigo();

		final ComponenteFormularioSeccion comp = (ComponenteFormularioSeccion) formularioInternoDao
				.getComponenteById(id);
		comp.setAlineacionTexto(componente.getAlineacionTexto());
		if (componente.getAyuda() != null) {
			componente.getAyuda().setCodigo(null);
			if (componente.getAyuda().getTraducciones() != null) {
				for (final Traduccion traduccion : componente.getAyuda().getTraducciones()) {
					traduccion.setCodigo(null);
				}
			}
			comp.setAyuda(componente.getAyuda());
		}
		comp.setIdComponente(componente.getIdComponente());
		comp.setLetra(componente.getLetra());
		comp.setNoMostrarTexto(componente.isNoMostrarTexto());
		comp.setNumColumnas(componente.getNumColumnas());
		comp.setOrden(componente.getOrden());
		if (componente.getTexto() != null) {
			componente.getTexto().setCodigo(null);
			if (componente.getTexto().getTraducciones() != null) {
				for (final Traduccion traduccion : componente.getTexto().getTraducciones()) {
					traduccion.setCodigo(null);
				}
			}
			comp.setTexto(componente.getTexto());
		}
		comp.setTipo(componente.getTipo());
		formularioInternoDao.updateComponente(comp);
	}

	/**
	 * Añade componente imagen.
	 *
	 * @param componente
	 * @param idLinea
	 * @param idPagina
	 * @param ficherosContent
	 */
	private void anyadirComponenteImagen(final ComponenteFormularioImagen componente, final Long idLinea,
			final Long idPagina, final Map<Long, byte[]> ficherosContent, final Long idEntidad) {
		final ObjetoFormulario retorno = formularioInternoDao.addComponente(componente.getTipo(), idPagina, idLinea,
				componente.getOrden(), null);
		entityManager.flush();
		final Long id = ((ComponenteFormularioCampoTexto) retorno).getCodigo();

		final ComponenteFormularioImagen comp = (ComponenteFormularioImagen) formularioInternoDao.getComponenteById(id);
		comp.setAlineacionTexto(componente.getAlineacionTexto());
		if (componente.getAyuda() != null) {
			componente.getAyuda().setCodigo(null);
			if (componente.getAyuda().getTraducciones() != null) {
				for (final Traduccion traduccion : componente.getAyuda().getTraducciones()) {
					traduccion.setCodigo(null);
				}
			}
			comp.setAyuda(componente.getAyuda());
		}
		if (componente.getFichero() != null) {
			final byte[] content = ficherosContent.get(componente.getFichero().getCodigo());
			Fichero fichero = componente.getFichero();
			fichero.setCodigo(null);
			final JFichero jfichero = JFichero.fromModel(fichero);
			entityManager.persist(jfichero);
			fichero = jfichero.toModel();
			ficheroExternoDao.guardarFichero(idEntidad, fichero, content);
			comp.setFichero(componente.getFichero());
		}
		comp.setIdComponente(componente.getIdComponente());
		comp.setNoMostrarTexto(componente.isNoMostrarTexto());
		comp.setNumColumnas(componente.getNumColumnas());
		comp.setOrden(componente.getOrden());
		if (componente.getTexto() != null) {
			componente.getTexto().setCodigo(null);
			if (componente.getTexto().getTraducciones() != null) {
				for (final Traduccion traduccion : componente.getTexto().getTraducciones()) {
					traduccion.setCodigo(null);
				}
			}
			comp.setTexto(componente.getTexto());
		}
		comp.setTipo(componente.getTipo());
		formularioInternoDao.updateComponente(comp);
	}

	/**
	 * Añade componente etiqueta.
	 *
	 * @param componente
	 * @param idLinea
	 * @param idPagina
	 */
	private void anyadirComponenteEtiqueta(final ComponenteFormularioEtiqueta componente, final int ordenLinea,
			final Long idPagina) {
		final ObjetoFormulario retorno = formularioInternoDao.addComponente(componente.getTipo(), idPagina, null,
				ordenLinea, null);
		entityManager.flush();
		final Long id = ((LineaComponentesFormulario) retorno).getComponentes()
				.get(((LineaComponentesFormulario) retorno).getComponentes().size() - 1).getCodigo();

		final ComponenteFormularioEtiqueta comp = (ComponenteFormularioEtiqueta) formularioInternoDao
				.getComponenteById(id);
		if (componente.getAyuda() != null) {
			componente.getAyuda().setCodigo(null);
			if (componente.getAyuda().getTraducciones() != null) {
				for (final Traduccion traduccion : componente.getAyuda().getTraducciones()) {
					traduccion.setCodigo(null);
				}
			}
			comp.setAyuda(componente.getAyuda());
		}
		comp.setAlineacionTexto(componente.getAlineacionTexto());
		comp.setIdComponente(componente.getIdComponente());
		comp.setNoMostrarTexto(componente.isNoMostrarTexto());
		comp.setNumColumnas(componente.getNumColumnas());
		comp.setOrden(componente.getOrden());
		if (componente.getTexto() != null) {
			componente.getTexto().setCodigo(null);
			if (componente.getTexto().getTraducciones() != null) {
				for (final Traduccion traduccion : componente.getTexto().getTraducciones()) {
					traduccion.setCodigo(null);
				}
			}
			comp.setTexto(componente.getTexto());
		}
		comp.setTipo(componente.getTipo());
		comp.setTipoEtiqueta(componente.getTipoEtiqueta());
		formularioInternoDao.updateComponente(comp);
	}

	/**
	 * Añade componente campo de texto oculto.
	 *
	 * @param componente
	 * @param idLinea
	 * @param idPagina
	 */
	private void anyadirComponenteCampoOculto(final ComponenteFormularioCampoOculto componente, final Long idLinea,
			final Long idPagina) {
		final ObjetoFormulario retorno = formularioInternoDao.addComponente(componente.getTipo(), idPagina, idLinea,
				componente.getOrden(), null);
		entityManager.flush();

		final Long id = ((ComponenteFormularioCampoOculto) retorno).getCodigo();

		final ComponenteFormularioCampoOculto comp = (ComponenteFormularioCampoOculto) formularioInternoDao
				.getComponenteById(id);
		comp.setIdComponente(componente.getIdComponente());
		comp.setScriptAutorrellenable(Script.clonar(componente.getScriptAutorrellenable()));

		formularioInternoDao.updateComponente(comp);
	}

	/**
	 * Añade componente campo de texto.
	 *
	 * @param componente
	 * @param idLinea
	 * @param idPagina
	 */
	private void anyadirComponenteCampoTexto(final ComponenteFormularioCampoTexto componente, final Long idLinea,
			final Long idPagina) {
		final ObjetoFormulario retorno = formularioInternoDao.addComponente(componente.getTipo(), idPagina, idLinea,
				componente.getOrden(), null);
		entityManager.flush();
		final Long id = ((ComponenteFormularioCampoTexto) retorno).getCodigo();

		final ComponenteFormularioCampoTexto comp = (ComponenteFormularioCampoTexto) formularioInternoDao
				.getComponenteById(id);
		if (componente.getAyuda() != null) {
			componente.getAyuda().setCodigo(null);
			if (componente.getAyuda().getTraducciones() != null) {
				for (final Traduccion traduccion : componente.getAyuda().getTraducciones()) {
					traduccion.setCodigo(null);
				}
			}
			comp.setAyuda(componente.getAyuda());
		}
		comp.setAlineacionTexto(componente.getAlineacionTexto());
		comp.setExpresionRegular(componente.getExpresionRegular());
		comp.setIdComponente(componente.getIdComponente());
		comp.setIdentDni(componente.isIdentDni());
		comp.setIdentNie(componente.isIdentNie());
		comp.setIdentNifOtros(componente.isIdentNifOtros());
		comp.setIdentNif(componente.isIdentNif());
		comp.setIdentNss(componente.isIdentNss());
		comp.setNoModificable(componente.isNoModificable());
		comp.setNoMostrarTexto(componente.isNoMostrarTexto());
		comp.setNormalMultilinea(componente.isNormalMultilinea());
		comp.setNormalNumeroLineas(componente.getNormalNumeroLineas());
		comp.setNormalTamanyo(componente.getNormalTamanyo());
		comp.setNumColumnas(componente.getNumColumnas());
		comp.setNumeroConSigno(componente.isNumeroConSigno());
		comp.setNumeroDigitosDecimales(componente.getNumeroDigitosDecimales());
		comp.setNumeroDigitosEnteros(componente.getNumeroDigitosEnteros());
		comp.setNumeroRangoMaximo(componente.getNumeroRangoMaximo());
		comp.setNumeroRangoMinimo(componente.getNumeroRangoMinimo());
		comp.setNumeroSeparador(componente.getNumeroSeparador());
		comp.setObligatorio(componente.isObligatorio());
		comp.setOrden(componente.getOrden());
		comp.setOculto(componente.isOculto());
		comp.setPermiteRango(componente.isPermiteRango());
		comp.setScriptAutorrellenable(Script.clonar(componente.getScriptAutorrellenable()));
		comp.setScriptSoloLectura(Script.clonar(componente.getScriptSoloLectura()));
		comp.setScriptValidacion(Script.clonar(componente.getScriptValidacion()));
		comp.setSoloLectura(componente.isSoloLectura());
		comp.setTelefonoFijo(componente.isTelefonoFijo());
		comp.setTelefonoMovil(componente.isTelefonoMovil());
		if (componente.getTexto() != null) {
			componente.getTexto().setCodigo(null);
			if (componente.getTexto().getTraducciones() != null) {
				for (final Traduccion traduccion : componente.getTexto().getTraducciones()) {
					traduccion.setCodigo(null);
				}
			}
			comp.setTexto(componente.getTexto());
		}
		comp.setTipo(componente.getTipo());
		comp.setTipoCampoTexto(componente.getTipoCampoTexto());

		comp.setForzarMayusculas(componente.isForzarMayusculas());
		formularioInternoDao.updateComponente(comp);
	}

	/**
	 * Añade componente selector.
	 *
	 * @param componente
	 * @param idLinea
	 * @param idPagina
	 */
	private void anyadirComponenteSelector(final ComponenteFormularioCampoSelector componente, final Long idLinea,
			final Long idPagina, final Map<Long, Long> idDominiosEquivalencia) {
		final ObjetoFormulario retorno = formularioInternoDao.addComponente(componente.getTipo(), idPagina, idLinea,
				componente.getOrden(), null);
		entityManager.flush();
		final Long id = ((ComponenteFormularioCampo) retorno).getCodigo();

		final ComponenteFormularioCampoSelector comp = (ComponenteFormularioCampoSelector) formularioInternoDao
				.getComponenteById(id);
		comp.setAlineacionTexto(componente.getAlineacionTexto());
		comp.setAltura(componente.getAltura());
		if (componente.getAyuda() != null) {
			componente.getAyuda().setCodigo(null);
			if (componente.getAyuda().getTraducciones() != null) {
				for (final Traduccion traduccion : componente.getAyuda().getTraducciones()) {
					traduccion.setCodigo(null);
				}
			}
			comp.setAyuda(componente.getAyuda());
		}
		comp.setTipoListaValores(componente.getTipoListaValores());

		comp.setCampoDominioCodigo(componente.getCampoDominioCodigo());
		comp.setCampoDominioDescripcion(componente.getCampoDominioDescripcion());
		if (componente.getCodDominio() != null) {
			for (final Entry<Long, Long> ids : idDominiosEquivalencia.entrySet()) {
				if (ids.getValue().compareTo(componente.getCodDominio()) == 0) {
					comp.setCodDominio(ids.getKey());
					break;
				}
			}
		}

		comp.setIdComponente(componente.getIdComponente());
		comp.setIndiceAlfabetico(componente.isIndiceAlfabetico());
		if (componente.getListaParametrosDominio() != null) {
			for (final ParametroDominio parametro : componente.getListaParametrosDominio()) {
				parametro.setCodigo(null);
			}
			comp.setListaParametrosDominio(componente.getListaParametrosDominio());
		}
		if (componente.getListaValorListaFija() != null) {
			for (final ValorListaFija valor : componente.getListaValorListaFija()) {
				valor.setCodigo(null);
				if (valor.getDescripcion() != null) {
					valor.getDescripcion().setCodigo(null);
					for (final Traduccion trad : valor.getDescripcion().getTraducciones()) {
						trad.setCodigo(null);
					}
				}
			}
			comp.setListaValorListaFija(componente.getListaValorListaFija());
		}
		comp.setNoModificable(componente.isNoModificable());
		comp.setNoMostrarTexto(componente.isNoMostrarTexto());
		comp.setNumColumnas(componente.getNumColumnas());
		comp.setObligatorio(componente.isObligatorio());
		comp.setOrden(componente.getOrden());
		comp.setScriptAutorrellenable(Script.clonar(componente.getScriptAutorrellenable()));
		comp.setScriptSoloLectura(Script.clonar(componente.getScriptSoloLectura()));
		comp.setScriptValidacion(Script.clonar(componente.getScriptValidacion()));
		comp.setScriptValoresPosibles(Script.clonar(componente.getScriptValoresPosibles()));
		comp.setSoloLectura(componente.isSoloLectura());
		comp.setTipo(componente.getTipo());
		comp.setTipoCampoIndexado(componente.getTipoCampoIndexado());

		if (componente.getTexto() != null) {
			componente.getTexto().setCodigo(null);
			if (componente.getTexto().getTraducciones() != null) {
				for (final Traduccion traduccion : componente.getTexto().getTraducciones()) {
					traduccion.setCodigo(null);
				}
			}
			comp.setTexto(componente.getTexto());
		}
		comp.setTipo(componente.getTipo());
		comp.setOrientacion(componente.getOrientacion());
		formularioInternoDao.updateComponente(comp);
	}

	/**
	 * Añade componente campo.
	 *
	 * @param componente
	 * @param idLinea
	 * @param idPagina
	 */
	private void anyadirComponenteCampo(final ComponenteFormularioCampo componente, final Long idLinea,
			final Long idPagina) {
		final ObjetoFormulario retorno = formularioInternoDao.addComponente(componente.getTipo(), idPagina, idLinea,
				componente.getOrden(), null);
		entityManager.flush();
		final Long id = ((ComponenteFormularioCampo) retorno).getCodigo();

		final ComponenteFormularioCampo comp = (ComponenteFormularioCampo) formularioInternoDao.getComponenteById(id);
		comp.setAlineacionTexto(componente.getAlineacionTexto());
		if (componente.getAyuda() != null) {
			componente.getAyuda().setCodigo(null);
			if (componente.getAyuda().getTraducciones() != null) {
				for (final Traduccion traduccion : componente.getAyuda().getTraducciones()) {
					traduccion.setCodigo(null);
				}
			}
			comp.setAyuda(componente.getAyuda());
		}
		comp.setIdComponente(componente.getIdComponente());
		comp.setNoModificable(componente.isNoModificable());
		comp.setNoMostrarTexto(componente.isNoMostrarTexto());
		comp.setNumColumnas(componente.getNumColumnas());
		comp.setObligatorio(componente.isObligatorio());
		comp.setOrden(componente.getOrden());
		comp.setScriptAutorrellenable(Script.clonar(componente.getScriptAutorrellenable()));
		comp.setScriptSoloLectura(Script.clonar(componente.getScriptSoloLectura()));
		comp.setScriptValidacion(Script.clonar(componente.getScriptValidacion()));
		comp.setSoloLectura(componente.isSoloLectura());
		if (componente.getTexto() != null) {
			componente.getTexto().setCodigo(null);
			if (componente.getTexto().getTraducciones() != null) {
				for (final Traduccion traduccion : componente.getTexto().getTraducciones()) {
					traduccion.setCodigo(null);
				}
			}
			comp.setTexto(componente.getTexto());
		}
		comp.setTipo(componente.getTipo());
		formularioInternoDao.updateComponente(comp);
	}

	/**
	 * Anyade un componente de tipo checkbox.
	 *
	 * @param componente
	 * @param idLinea
	 * @param idPagina
	 */
	private void anyadirComponenteCheckbox(final ComponenteFormularioCampoCheckbox componente, final Long idLinea,
			final Long idPagina) {
		final ObjetoFormulario retorno = formularioInternoDao.addComponente(componente.getTipo(), idPagina, idLinea,
				componente.getOrden(), null);
		entityManager.flush();
		final Long id = ((ComponenteFormularioCampoCheckbox) retorno).getCodigo();

		final ComponenteFormularioCampoCheckbox comp = (ComponenteFormularioCampoCheckbox) formularioInternoDao
				.getComponenteById(id);
		comp.setAlineacionTexto(componente.getAlineacionTexto());
		if (componente.getAyuda() != null) {
			componente.getAyuda().setCodigo(null);
			if (componente.getAyuda().getTraducciones() != null) {
				for (final Traduccion traduccion : componente.getAyuda().getTraducciones()) {
					traduccion.setCodigo(null);
				}
			}
			comp.setAyuda(componente.getAyuda());
		}
		comp.setIdComponente(componente.getIdComponente());
		comp.setNoModificable(componente.isNoModificable());
		comp.setNoMostrarTexto(componente.isNoMostrarTexto());
		comp.setNumColumnas(componente.getNumColumnas());
		comp.setObligatorio(componente.isObligatorio());
		comp.setOrden(componente.getOrden());
		comp.setScriptAutorrellenable(Script.clonar(componente.getScriptAutorrellenable()));
		comp.setScriptSoloLectura(Script.clonar(componente.getScriptSoloLectura()));
		comp.setScriptValidacion(Script.clonar(componente.getScriptValidacion()));
		comp.setSoloLectura(componente.isSoloLectura());
		if (componente.getTexto() != null) {
			componente.getTexto().setCodigo(null);
			if (componente.getTexto().getTraducciones() != null) {
				for (final Traduccion traduccion : componente.getTexto().getTraducciones()) {
					traduccion.setCodigo(null);
				}
			}
			comp.setTexto(componente.getTexto());
		}

		comp.setValorChecked(componente.getValorChecked());
		comp.setValorNoChecked(componente.getValorNoChecked());
		comp.setTipo(componente.getTipo());
		formularioInternoDao.updateComponente(comp);
	}

	@Override
	public boolean checkFormularioRepetido(final Long idTramiteVersion, final String identificador,
			final Long idFormulario) {

		if (idTramiteVersion == null || identificador == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}

		final StringBuilder sqlCount = new StringBuilder(
				"Select count(*) From JFormularioTramite p inner join p.pasosRellenar c where c.pasoTramitacion.versionTramite.codigo = ");
		sqlCount.append(idTramiteVersion);
		sqlCount.append(" and p.identificador = '");
		sqlCount.append(identificador);
		sqlCount.append("' ");

		if (idFormulario != null) {
			sqlCount.append(" and p.codigo != " + idFormulario);
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
	public void permiteSubsanacion(final Long idPaso, final boolean activarSubsanacion) {
		if (idPaso == null) {
			throw new FaltanDatosException(STRING_FALTA_TRAMITE);
		}

		final Query queryAnexar = entityManager.createQuery(
				"update JPasoAnexar paso set permiteSubsanar = :activarSubsanacion where paso.pasoTramitacion.codigo = :idPaso");
		queryAnexar.setParameter("activarSubsanacion", activarSubsanacion);
		queryAnexar.setParameter("idPaso", idPaso);
		queryAnexar.executeUpdate();

		final Query queryTasa = entityManager.createQuery(
				"update JPasoPagos paso set permiteSubsanar = :activarSubsanacion where paso.pasoTramitacion.codigo = :idPaso");
		queryTasa.setParameter("activarSubsanacion", activarSubsanacion);
		queryTasa.setParameter("idPaso", idPaso);
		queryTasa.executeUpdate();

		final Query queryRegistrar = entityManager.createQuery(
				"update JPasoRegistrar paso set permiteSubsanar = :activarSubsanacion where paso.pasoTramitacion.codigo = :idPaso");
		queryRegistrar.setParameter("activarSubsanacion", activarSubsanacion);
		queryRegistrar.setParameter("idPaso", idPaso);
		queryRegistrar.executeUpdate();

	}

	@Override
	public void borrarScriptsPropiedades(Long idTramiteVersion) {
		final JVersionTramite jpaso = entityManager.find(JVersionTramite.class, idTramiteVersion);
		if (jpaso != null) {
			jpaso.setScriptInicializacionTramite(null);
			jpaso.setScriptPersonalizacion(null);
			entityManager.merge(jpaso);
		}
	}

	@Override
	public void borrarScriptsRellenar(final Long idPaso, final Long idTramiteVersion) {

		final JPasoRellenar jpaso = entityManager.find(JPasoRellenar.class, idPaso);
		if (jpaso != null && jpaso.getFormulariosTramite() != null) {
			for (JFormularioTramite jform : jpaso.getFormulariosTramite()) {
				jform.setScriptDatosIniciales(null);
				jform.setScriptFirmar(null);
				jform.setScriptObligatoriedad(null);
				jform.setScriptParametros(null);
				jform.setScriptRetorno(null);
				entityManager.merge(jform);
			}
		}

		List<DisenyoFormulario> formularios = this.getFormulariosTramiteVersion(idTramiteVersion);
		if (formularios != null && !formularios.isEmpty()) {
			for (final DisenyoFormulario formulario : formularios) {
				final JFormulario jform = entityManager.find(JFormulario.class, formulario.getCodigo());
				jform.setScriptPlantilla(null);
				entityManager.merge(jform);

				// Paginas
				if (formulario.getPaginas() != null && !formulario.getPaginas().isEmpty()) {
					for (PaginaFormulario pagina : formulario.getPaginas()) {
						final JPaginaFormulario jpag = entityManager.find(JPaginaFormulario.class, pagina.getCodigo());
						jpag.setScriptNavegacion(null);
						jpag.setScriptValidacion(null);
						entityManager.merge(jpag);

						// Lineas de la pagina.
						if (jpag.getLineasFormulario() != null && !jpag.getLineasFormulario().isEmpty()) {
							for (JLineaFormulario jlinea : jpag.getLineasFormulario()) {
								// Componentes de la linea
								if (jlinea.getElementoFormulario() != null
										&& !jlinea.getElementoFormulario().isEmpty()) {
									for (JElementoFormulario componente : jlinea.getElementoFormulario()) {
										String tipo = componente.getTipo();
										if (componente.getCampoFormulario() != null) {
											componente.getCampoFormulario().setScriptAutocalculado(null);
											componente.getCampoFormulario().setScriptSoloLectura(null);
											componente.getCampoFormulario().setScriptValidaciones(null);
											entityManager.merge(componente.getCampoFormulario());
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void borrarScriptsAnexo(Long idTramiteVersion) {
		final JPasoAnexar jpaso = entityManager.find(JPasoAnexar.class, idTramiteVersion);
		if (jpaso != null) {
			jpaso.setScriptAnexosDinamicos(null);
			entityManager.merge(jpaso);
			if (jpaso.getAnexosTramite() != null) {
				for (JAnexoTramite anexo : jpaso.getAnexosTramite()) {
					anexo.setScriptFirmantes(null);
					anexo.setScriptObligatoriedad(null);
					anexo.setScriptValidacion(null);
					entityManager.merge(anexo);
				}
			}
		}
	}

	@Override
	public void borrarScriptsPago(Long idTramiteVersion) {
		final JPasoPagos jpaso = entityManager.find(JPasoPagos.class, idTramiteVersion);
		if (jpaso != null && jpaso.getPagosTramite() != null) {
			for (JPagoTramite pago : jpaso.getPagosTramite()) {
				pago.setScriptDatosPago(null);
				pago.setScriptObligatoriedad(null);
				entityManager.merge(pago);
			}
		}
	}

	@Override
	public void borrarScriptsRegistro(Long idTramiteVersion) {
		final JPasoRegistrar jpaso = entityManager.find(JPasoRegistrar.class, idTramiteVersion);
		if (jpaso != null) {
			jpaso.setScriptAlFinalizar(null);
			jpaso.setScriptDestinoRegistro(null);
			jpaso.setScriptPresentador(null);
			jpaso.setScriptRepresentante(null);
			jpaso.setScriptValidarRegistrar(null);
			entityManager.merge(jpaso);
		}
	}

	@Override
	public void borrarScriptsCaptura(Long idTramiteVersion) {
		final JPasoCaptura jpaso = entityManager.find(JPasoCaptura.class, idTramiteVersion);
		if (jpaso != null) {
			// entityManager.merge(jpaso);
		}
	}

}
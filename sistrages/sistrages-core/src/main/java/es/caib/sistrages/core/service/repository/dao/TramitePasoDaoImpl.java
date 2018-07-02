package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.service.repository.model.JAnexoTramite;
import es.caib.sistrages.core.service.repository.model.JFichero;
import es.caib.sistrages.core.service.repository.model.JFormateadorFormulario;
import es.caib.sistrages.core.service.repository.model.JFormulario;
import es.caib.sistrages.core.service.repository.model.JFormularioTramite;
import es.caib.sistrages.core.service.repository.model.JPagoTramite;
import es.caib.sistrages.core.service.repository.model.JPasoTramitacion;
import es.caib.sistrages.core.service.repository.model.JScript;

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
	public void addFormularioTramite(final FormularioTramite formularioTramite, final Long idTramitePaso,
			final Long idFormularioInterno) {
		final JFormularioTramite jFormulariotramite = JFormularioTramite.fromModel(formularioTramite);
		final JFormulario jFormularioInterno = entityManager.find(JFormulario.class, idFormularioInterno);
		final JPasoTramitacion jpasoRellenar = entityManager.find(JPasoTramitacion.class, idTramitePaso);
		jFormulariotramite.setOrden(jpasoRellenar.getPasoRellenar().getFormulariosTramite().size());
		jFormulariotramite.setFormulario(jFormularioInterno);
		jpasoRellenar.getPasoRellenar().getFormulariosTramite().add(jFormulariotramite);
		entityManager.merge(jpasoRellenar);
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
	public void addDocumentoTramite(final Documento documento, final Long idTramitePaso) {
		final JAnexoTramite janexoTramite = JAnexoTramite.fromModel(documento);
		final JPasoTramitacion jpasoTramitacion = entityManager.find(JPasoTramitacion.class, idTramitePaso);
		janexoTramite.setOrden(jpasoTramitacion.getPasoAnexar().getAnexosTramite().size());
		janexoTramite.setPasoAnexar(jpasoTramitacion.getPasoAnexar());
		jpasoTramitacion.getPasoAnexar().getAnexosTramite().add(janexoTramite);
		entityManager.persist(janexoTramite);
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
	public void addTasaTramite(final Tasa tasa, final Long idTramitePaso) {
		final JPasoTramitacion jpasoRellenar = entityManager.find(JPasoTramitacion.class, idTramitePaso);
		final JPagoTramite jpagoTramite = JPagoTramite.fromModel(tasa);
		jpagoTramite.setOrden(jpasoRellenar.getPasoPagos().getPagosTramite().size());
		jpagoTramite.setPasoPagos(jpasoRellenar.getPasoPagos());
		jpasoRellenar.getPasoPagos().getPagosTramite().add(jpagoTramite);
		entityManager.persist(jpagoTramite);
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
	public List<FormularioInterno> getFormulariosTramiteVersion(final Long idTramiteVersion) {
		final String sql = "select forms.formulario from JPasoTramitacion pasot inner join pasot.pasoRellenar pasor inner join pasor.formulariosTramite forms  where pasot.versionTramite.codigo = :idTramiteVersion ";

		final Query query = entityManager.createQuery(sql);
		query.setParameter("idTramiteVersion", idTramiteVersion);

		@SuppressWarnings("unchecked")
		final List<JFormulario> results = query.getResultList();

		final List<FormularioInterno> resultado = new ArrayList<>();
		if (results != null && !results.isEmpty()) {
			for (final Iterator<JFormulario> iterator = results.iterator(); iterator.hasNext();) {
				final JFormulario jformulario = iterator.next();
				resultado.add(jformulario.toModel());
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

}
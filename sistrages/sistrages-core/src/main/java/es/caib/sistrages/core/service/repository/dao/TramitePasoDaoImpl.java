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
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.service.repository.model.JAnexoTramite;
import es.caib.sistrages.core.service.repository.model.JFichero;
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
		entityManager.merge(jTramitePaso);
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
		jFormulariotramite.setFormulario(jFormularioInterno);
		jpasoRellenar.getPasoRellenar().getFormulariosTramite().add(jFormulariotramite);
		entityManager.merge(jpasoRellenar);
	}

	@Override
	public void updateFormularioTramite(final FormularioTramite formularioTramite) {
		final JFormularioTramite jFormulariotramiteOriginal = entityManager.find(JFormularioTramite.class,
				formularioTramite.getId());
		final JFormularioTramite jFormulariotramite = JFormularioTramite.fromModel(formularioTramite);
		jFormulariotramite.setPasosRellenar(jFormulariotramiteOriginal.getPasosRellenar());
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
		final JPasoTramitacion jpasoRellenar = entityManager.find(JPasoTramitacion.class, idTramitePaso);
		janexoTramite.setPasoAnexar(jpasoRellenar.getPasoAnexar());
		jpasoRellenar.getPasoAnexar().getAnexosTramite().add(janexoTramite);
		entityManager.persist(janexoTramite);
	}

	@Override
	public void updateDocumentoTramite(final Documento documento) {
		final JAnexoTramite jAnexoTramiteOriginal = entityManager.find(JAnexoTramite.class, documento.getId());
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
		jpasoRellenar.getPasoPagos().getPagosTramite().add(jpagoTramite);
		jpagoTramite.setPasoPagos(jpasoRellenar.getPasoPagos());
		entityManager.persist(jpagoTramite);
	}

	@Override
	public void updateTasaTramite(final Tasa tasa) {
		final JPagoTramite jpagoTramiteOld = entityManager.find(JPagoTramite.class, tasa.getId());
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

}
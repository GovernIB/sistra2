package es.caib.sistrages.core.service.component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoAnexar;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramitePasoRegistrar;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.TramitePasoTasa;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeAccionHistorial;
import es.caib.sistrages.core.api.model.types.TypeAutenticacion;
import es.caib.sistrages.core.api.model.types.TypeFlujo;
import es.caib.sistrages.core.api.model.types.TypeFormulario;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.model.types.TypePaso;
import es.caib.sistrages.core.api.model.types.TypePresentacion;
import es.caib.sistrages.core.api.model.types.TypeTamanyo;
import es.caib.sistrages.core.api.model.types.TypeTipoDocumental;
import es.caib.sistrages.core.service.component.literales.Literales;
import es.caib.sistrages.core.service.repository.dao.FormularioInternoDao;
import es.caib.sistrages.core.service.repository.dao.HistorialVersionDao;
import es.caib.sistrages.core.service.repository.dao.TramiteDao;
import es.caib.sistrages.core.service.repository.dao.TramitePasoDao;

@Component("tramiteComponent")
public class TramiteComponentImpl implements TramiteComponent {

	@Autowired
	Literales literales;

	@Autowired
	TramiteDao tramiteDao;

	@Autowired
	TramitePasoDao tramitePasoDao;

	@Autowired
	HistorialVersionDao historialVersionDao;

	@Autowired
	FormularioInternoDao formularioInternoDao;

	@Override
	public TramiteVersion createTramiteVersionDefault(final Integer pNumVersion, final String pIdiomasSoportados,
			final String pDatosUsuarioBloqueo) {
		final TramiteVersion tramiteVersion = new TramiteVersion();
		tramiteVersion.setNumeroVersion(pNumVersion);
		tramiteVersion.setTipoFlujo(TypeFlujo.NORMAL);
		tramiteVersion.setActiva(true);
		tramiteVersion.setDebug(true);
		tramiteVersion.setAutenticado(true);
		tramiteVersion.setNoAutenticado(true);
		tramiteVersion.setIdiomasSoportados(pIdiomasSoportados);
		tramiteVersion.setPersistencia(true);
		tramiteVersion.setPersistenciaInfinita(true);
		tramiteVersion.setLimiteTramitacion(false);
		tramiteVersion.setDesactivacion(false);
		tramiteVersion.setRelease(0);
		tramiteVersion.setNivelQAA(2);
		tramiteVersion.setBloqueada(true);
		tramiteVersion.setDatosUsuarioBloqueo(pDatosUsuarioBloqueo);
		List<TypeAutenticacion> tiposAutenticacion = new ArrayList<>();
		tiposAutenticacion.add(TypeAutenticacion.CERTIFICADO);
		tiposAutenticacion.add(TypeAutenticacion.CLAVE_PIN);
		tiposAutenticacion.add(TypeAutenticacion.CLAVE_PERMANENTE);
		tramiteVersion.setTiposAutenticacion(tiposAutenticacion );
		return tramiteVersion;
	}

	@Override
	public List<TramitePaso> createNormalizado() {
		final List<TramitePaso> listaPasos = new ArrayList<>();

		/* Creamos Paso Debe Saber */
		final TramitePasoDebeSaber pasoDS = new TramitePasoDebeSaber();
		pasoDS.setIdPasoTramitacion(TypePaso.DEBESABER.toString());
		final Literal descripcionDS = new Literal();
		descripcionDS.add(new Traduccion(TypeIdioma.CATALAN.toString(),
				literales.getLiteral("typePaso", "ds", TypeIdioma.CATALAN.toString())));
		descripcionDS.add(new Traduccion(TypeIdioma.CASTELLANO.toString(),
				literales.getLiteral("typePaso", "ds", TypeIdioma.CASTELLANO.toString())));
		pasoDS.setDescripcion(descripcionDS);
		pasoDS.setOrden(1);
		pasoDS.setTipo(TypePaso.DEBESABER);
		listaPasos.add(pasoDS);

		/* Creamos Rellenar fomulario */
		final TramitePasoRellenar pasoRF = new TramitePasoRellenar();
		pasoRF.setIdPasoTramitacion(TypePaso.RELLENAR.toString());
		final Literal descripcionRF = new Literal();
		descripcionRF.add(new Traduccion(TypeIdioma.CATALAN.toString(),
				literales.getLiteral("typePaso", "rf", TypeIdioma.CATALAN.toString())));
		descripcionRF.add(new Traduccion(TypeIdioma.CASTELLANO.toString(),
				literales.getLiteral("typePaso", "rf", TypeIdioma.CASTELLANO.toString())));
		pasoRF.setDescripcion(descripcionRF);
		pasoRF.setOrden(2);
		pasoRF.setTipo(TypePaso.RELLENAR);
		listaPasos.add(pasoRF);

		/* Creamos Anexar Documentacion */
		final TramitePasoAnexar pasoAD = new TramitePasoAnexar();
		pasoAD.setIdPasoTramitacion(TypePaso.ANEXAR.toString());
		final Literal descripcionAD = new Literal();
		descripcionAD.add(new Traduccion(TypeIdioma.CATALAN.toString(),
				literales.getLiteral("typePaso", "ad", TypeIdioma.CATALAN.toString())));
		descripcionAD.add(new Traduccion(TypeIdioma.CASTELLANO.toString(),
				literales.getLiteral("typePaso", "ad", TypeIdioma.CASTELLANO.toString())));
		pasoAD.setDescripcion(descripcionAD);
		pasoAD.setOrden(3);
		pasoAD.setTipo(TypePaso.ANEXAR);
		listaPasos.add(pasoAD);

		/* Creamos Pagar Tasas */
		final TramitePasoTasa pasoPT = new TramitePasoTasa();
		pasoPT.setIdPasoTramitacion(TypePaso.PAGAR.toString());
		final Literal descripcionPT = new Literal();
		descripcionPT.add(new Traduccion(TypeIdioma.CATALAN.toString(),
				literales.getLiteral("typePaso", "pt", TypeIdioma.CATALAN.toString())));
		descripcionPT.add(new Traduccion(TypeIdioma.CASTELLANO.toString(),
				literales.getLiteral("typePaso", "pt", TypeIdioma.CASTELLANO.toString())));
		pasoPT.setDescripcion(descripcionPT);
		pasoPT.setOrden(4);
		pasoPT.setTipo(TypePaso.PAGAR);
		listaPasos.add(pasoPT);

		/* Creamos Registrar */
		final TramitePasoRegistrar pasoRT = new TramitePasoRegistrar();
		pasoRT.setIdPasoTramitacion(TypePaso.REGISTRAR.toString());
		final Literal descripcionRT = new Literal();
		descripcionRT.add(new Traduccion(TypeIdioma.CATALAN.toString(),
				literales.getLiteral("typePaso", "rt", TypeIdioma.CATALAN.toString())));
		descripcionRT.add(new Traduccion(TypeIdioma.CASTELLANO.toString(),
				literales.getLiteral("typePaso", "rt", TypeIdioma.CASTELLANO.toString())));
		pasoRT.setDescripcion(descripcionRT);
		pasoRT.setOrden(5);
		pasoRT.setTipo(TypePaso.REGISTRAR);
		listaPasos.add(pasoRT);

		return listaPasos;
	}

	@Override
	public Long addTramiteVersion(final TramiteVersion tramiteVersion, final String idTramite, final String usuario) {
		final Long idTramiteVersion = tramiteDao.addTramiteVersion(tramiteVersion, idTramite);
		historialVersionDao.add(idTramiteVersion, usuario, TypeAccionHistorial.CREACION, "");
		historialVersionDao.add(idTramiteVersion, usuario, TypeAccionHistorial.BLOQUEAR, "");

		return idTramiteVersion;
	}

	@Override
	public Documento createDocumentoDefault() {
		final Documento documento = new Documento();
		documento.setObligatoriedad(TypeFormularioObligatoriedad.OBLIGATORIO);
		documento.setTipoTamanyo(TypeTamanyo.KILOBYTES);
		documento.setTipoPresentacion(TypePresentacion.ELECTRONICA);
		documento.setNumeroInstancia(1);
		documento.setExtensiones("pdf;odt");
		documento.setTamanyoMaximo(1024);
		documento.setTipoDocumental(TypeTipoDocumental.TD99_OTROS);
		return documento;
	}

	@Override
	public Documento addDocumentoTramite(final Documento documento, final Long idTramitePaso) {
		return tramitePasoDao.addDocumentoTramite(documento, idTramitePaso);
	}

	@Override
	public Tasa createTasaDefault() {
		final Tasa tasa = new Tasa();
		tasa.setObligatoriedad(TypeFormularioObligatoriedad.OBLIGATORIO);
		tasa.setTipoPlugin(null);
		tasa.setSimulado(false);
		tasa.setOrden(0);
		return tasa;
	}

	@Override
	public Tasa addTasaTramite(final Tasa tasa, final Long idTramitePaso) {
		return tramitePasoDao.addTasaTramite(tasa, idTramitePaso);
	}

	@Override
	public FormularioTramite createFormularioTramiteDefault() {
		final FormularioTramite formulario = new FormularioTramite();
		formulario.setObligatoriedad(TypeFormularioObligatoriedad.OBLIGATORIO);
		formulario.setTipo(TypeFormulario.TRAMITE);
		return formulario;
	}

	@Override
	public FormularioTramite addFormularioTramite(final FormularioTramite formularioTramite, final Long idTramitePaso) {
		// Primero creamos el formulario interno y luego el formulario tramite.
		final Long idFormularioInterno = formularioInternoDao.addFormulario(formularioTramite);
		return tramitePasoDao.addFormularioTramite(formularioTramite, idTramitePaso, idFormularioInterno);
	}

}

package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoAnexar;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramitePasoRegistrar;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.TramitePasoTasa;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeFlujo;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.model.types.TypePaso;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogTramiteVersion extends DialogControllerBase {

	/** Servicio. */
	@Inject
	private TramiteService tramiteService;

	/** Id elemento a tratar. */
	private String id;

	/** Datos elemento. */
	private Tramite data;

	/** Tramite version. **/
	private TramiteVersion dataVersion;

	/**
	 * Inicialización.
	 */
	public void init() {

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		UtilJSF.checkSecOpenDialog(modo, id);

		dataVersion = new TramiteVersion();
		final int numVersion = tramiteService.getTramiteNumVersionMaximo(Long.valueOf(id)) + 1;
		dataVersion.setNumeroVersion(numVersion);
		dataVersion.setTipoFlujo(TypeFlujo.NORMAL);
		dataVersion.setActiva(true);
		dataVersion.setDebug(true);
		dataVersion.setAutenticado(true);
		dataVersion.setNoAutenticado(true);
		dataVersion.setIdiomasSoportados(UtilTraducciones.getIdiomasPorDefectoTramite());
		dataVersion.setPersistencia(true);
		dataVersion.setPersistenciaInfinita(true);
		dataVersion.setLimiteTramitacion(false);
		dataVersion.setDesactivacion(false);
		dataVersion.setRelease(0);
		dataVersion.setNivelQAA(2);
		dataVersion.setBloqueada(true);
		dataVersion.setDatosUsuarioBloqueo(UtilJSF.getSessionBean().getUserName());
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		if (dataVersion.getTipoFlujo() == TypeFlujo.PERSONALIZADO) {
			addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("dialogTramiteVersion.tipoNoImplementado"));
			return;
		} else {

			// Comprobamos que la realease no esté repetida.
			if (tramiteService.tieneTramiteNumVersionRepetida(Long.valueOf(id), this.dataVersion.getNumeroVersion())) {
				addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("dialogTramiteVersion.numVersionRepetida"));
				return;
			}

			final List<TramitePaso> listaPasos = new ArrayList<>();

			/* Creamos Paso Debe Saber */
			final TramitePasoDebeSaber pasoDS = new TramitePasoDebeSaber();
			pasoDS.setIdPasoTramitacion(TypePaso.DEBESABER.toString());
			final Literal descripcionDS = new Literal();
			descripcionDS.add(new Traduccion(TypeIdioma.CATALAN.toString(),
					UtilJSF.getLiteral("typePaso.ds", TypeIdioma.CATALAN.toString())));
			descripcionDS.add(new Traduccion(TypeIdioma.CASTELLANO.toString(),
					UtilJSF.getLiteral("typePaso.ds", TypeIdioma.CASTELLANO.toString())));
			pasoDS.setDescripcion(descripcionDS);
			pasoDS.setOrden(1);
			pasoDS.setTipo(TypePaso.DEBESABER);
			listaPasos.add(pasoDS);

			/* Creamos Rellenar fomulario */
			final TramitePasoRellenar pasoRF = new TramitePasoRellenar();
			pasoRF.setIdPasoTramitacion(TypePaso.RELLENAR.toString());
			final Literal descripcionRF = new Literal();
			descripcionRF.add(new Traduccion(TypeIdioma.CATALAN.toString(),
					UtilJSF.getLiteral("typePaso.rf", TypeIdioma.CATALAN.toString())));
			descripcionRF.add(new Traduccion(TypeIdioma.CASTELLANO.toString(),
					UtilJSF.getLiteral("typePaso.rf", TypeIdioma.CASTELLANO.toString())));
			pasoRF.setDescripcion(descripcionRF);
			pasoRF.setOrden(2);
			pasoRF.setTipo(TypePaso.RELLENAR);
			listaPasos.add(pasoRF);

			/* Creamos Anexar Documentacion */
			final TramitePasoAnexar pasoAD = new TramitePasoAnexar();
			pasoAD.setIdPasoTramitacion(TypePaso.ANEXAR.toString());
			final Literal descripcionAD = new Literal();
			descripcionAD.add(new Traduccion(TypeIdioma.CATALAN.toString(),
					UtilJSF.getLiteral("typePaso.ad", TypeIdioma.CATALAN.toString())));
			descripcionAD.add(new Traduccion(TypeIdioma.CASTELLANO.toString(),
					UtilJSF.getLiteral("typePaso.ad", TypeIdioma.CASTELLANO.toString())));
			pasoAD.setDescripcion(descripcionAD);
			pasoAD.setOrden(3);
			pasoAD.setTipo(TypePaso.ANEXAR);
			listaPasos.add(pasoAD);

			/* Creamos Pagar Tasas */
			final TramitePasoTasa pasoPT = new TramitePasoTasa();
			pasoPT.setIdPasoTramitacion(TypePaso.PAGAR.toString());
			final Literal descripcionPT = new Literal();
			descripcionPT.add(new Traduccion(TypeIdioma.CATALAN.toString(),
					UtilJSF.getLiteral("typePaso.pt", TypeIdioma.CATALAN.toString())));
			descripcionPT.add(new Traduccion(TypeIdioma.CASTELLANO.toString(),
					UtilJSF.getLiteral("typePaso.pt", TypeIdioma.CASTELLANO.toString())));
			pasoPT.setDescripcion(descripcionPT);
			pasoPT.setOrden(4);
			pasoPT.setTipo(TypePaso.PAGAR);
			listaPasos.add(pasoPT);

			/* Creamos Registrar */
			final TramitePasoRegistrar pasoRT = new TramitePasoRegistrar();
			pasoRT.setIdPasoTramitacion(TypePaso.REGISTRAR.toString());
			final Literal descripcionRT = new Literal();
			descripcionRT.add(new Traduccion(TypeIdioma.CATALAN.toString(),
					UtilJSF.getLiteral("typePaso.rt", TypeIdioma.CATALAN.toString())));
			descripcionRT.add(new Traduccion(TypeIdioma.CASTELLANO.toString(),
					UtilJSF.getLiteral("typePaso.rt", TypeIdioma.CASTELLANO.toString())));
			pasoRT.setDescripcion(descripcionRT);
			pasoRT.setOrden(5);
			pasoRT.setTipo(TypePaso.REGISTRAR);
			listaPasos.add(pasoRT);

			this.dataVersion.setListaPasos(listaPasos);

		}
		this.tramiteService.addTramiteVersion(this.dataVersion, id, UtilJSF.getSessionBean().getUserName());

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		UtilJSF.closeDialog(result);
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the data
	 */
	public Tramite getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final Tramite data) {
		this.data = data;
	}

	/**
	 * @return the dataVersion
	 */
	public TramiteVersion getDataVersion() {
		return dataVersion;
	}

	/**
	 * @param dataVersion
	 *            the dataVersion to set
	 */
	public void setDataVersion(final TramiteVersion dataVersion) {
		this.dataVersion = dataVersion;
	}

}

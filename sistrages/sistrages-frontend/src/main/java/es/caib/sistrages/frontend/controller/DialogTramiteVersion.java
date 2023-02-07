package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeFlujo;
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

	/** Id area. **/
	private String idArea;

	/** Datos elemento. */
	private Tramite data;

	/** Tramite version. **/
	private TramiteVersion dataVersion;

	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicialización.
	 */
	public void init() {

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		UtilJSF.checkSecOpenDialog(modo, id);

		final int numVersion = tramiteService.getTramiteNumVersionMaximo(Long.valueOf(id)) + 1;
		dataVersion = tramiteService.createTramiteVersionDefault(numVersion,
				UtilTraducciones.getIdiomasPorDefectoTramite(), UtilJSF.getSessionBean().getUserName());

	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		if (dataVersion.getTipoFlujo() == TypeFlujo.PERSONALIZADO) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("dialogTramiteVersion.tipoNoImplementado"));
			return;
		} else {

			// Comprobamos que la realease no esté repetida.
			if (tramiteService.tieneTramiteNumVersionRepetida(Long.valueOf(id), this.dataVersion.getNumeroVersion())) {
				addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("dialogTramiteVersion.numVersionRepetida"));
				return;
			}

			final List<TramitePaso> listaPasos = tramiteService.createListaPasosNormalizado();
			this.dataVersion.setListaPasos(listaPasos);

		}
		final Long idVersion = this.tramiteService.addTramiteVersion(this.dataVersion, id,
				UtilJSF.getSessionBean().getUserName());
		this.dataVersion.setCodigo(idVersion);
		this.dataVersion.setIdTramite(Long.valueOf(id));
		this.dataVersion.setIdArea(tramiteService.getAreaTramite(Long.valueOf(id)).getCodigo());

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setResult(this.dataVersion);
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
	 * @param id the id to set
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
	 * @param data the data to set
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
	 * @param dataVersion the dataVersion to set
	 */
	public void setDataVersion(final TramiteVersion dataVersion) {
		this.dataVersion = dataVersion;
	}

	/**
	 * @return the idArea
	 */
	public String getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea the idArea to set
	 */
	public void setIdArea(final String idArea) {
		this.idArea = idArea;
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {

		if (portapapeles.equals("") || portapapeles.equals(null)) {
			copiadoErr();
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
		}
	}

	/**
	 * @return the errorCopiar
	 */
	public final String getErrorCopiar() {
		return errorCopiar;
	}

	/**
	 * @param errorCopiar the errorCopiar to set
	 */
	public final void setErrorCopiar(String errorCopiar) {
		this.errorCopiar = errorCopiar;
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("viewTramites.copiar"));
	}

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

}

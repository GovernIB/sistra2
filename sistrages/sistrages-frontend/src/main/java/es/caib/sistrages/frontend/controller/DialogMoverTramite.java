package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogMoverTramite extends DialogControllerBase {

	/**
	 * Servicio.
	 */
	@Inject
	private TramiteService tramiteService;

	/**
	 * Id elemento a tratar.
	 */
	private String id;

	/**
	 * Datos elemento.
	 */
	private Tramite data;

	/**
	 * Areas.
	 */
	private List<Area> areas;

	/**
	 * Id area destino.
	 */
	private Long idAreaDestino;

	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicialización.
	 */
	public void init() {
		final Long idTramite = Long.valueOf(id);
		data = tramiteService.getTramite(idTramite);
		final Area area = tramiteService.getAreaTramite(idTramite);
		areas = tramiteService.listArea(UtilJSF.getIdEntidad(), null);
		idAreaDestino = area.getCodigo();
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		if (data.getIdArea().compareTo(idAreaDestino) == 0) {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogMoverTramite.error.mismarea"));
			return;
		}

		// Realizamos update
		tramiteService.changeAreaTramite(idAreaDestino, data.getCodigo(), data.getIdArea(),
				UtilJSF.getSessionBean().getUserName());

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.EDICION);
		result.setResult(data.getIdentificador());
		UtilJSF.closeDialog(result);
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.EDICION);
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
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

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public Tramite getData() {
		return data;
	}

	public void setData(final Tramite data) {
		this.data = data;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(final List<Area> areas) {
		this.areas = areas;
	}

	public Long getIdAreaDestino() {
		return idAreaDestino;
	}

	public void setIdAreaDestino(final Long idAreaDestino) {
		this.idAreaDestino = idAreaDestino;
	}
}

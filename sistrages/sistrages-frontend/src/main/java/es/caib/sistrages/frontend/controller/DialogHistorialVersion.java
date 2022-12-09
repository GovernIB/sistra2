package es.caib.sistrages.frontend.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;

import es.caib.sistrages.core.api.model.HistorialVersion;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogHistorialVersion extends DialogControllerBase {

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Id de la version. */
	private String id;

	/** Historial de versiones. */
	private List<HistorialVersion> data;

	/** Historial de versiones seleccionado. */
	private HistorialVersion datoSeleccionado;

	private String portapapeles;

	/** Inicialización. */
	public void init() {
		data = tramiteService.listHistorialVersion(Long.valueOf(id), null);
	}

	/**
	 * Borra el historial de versiones dejándolo a sólo 1, el de creación a hoy.
	 **/
	public void borradoHistorial() {
		tramiteService.borradoHistorial(Long.valueOf(id), UtilJSF.getSessionBean().getUserName());
		data = tramiteService.listHistorialVersion(Long.valueOf(id), null);
	}

	/** Cancelar. */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("historialVersionDialog");
	}

	/** Genera texto a copiar **/
	public void generarTxt() {
		if (this.datoSeleccionado != null) {
			HistorialVersion hv = this.datoSeleccionado;
			String txt = "";

			txt += "Release: " + hv.getRelease();
			txt += "\nEmpremta: " + hv.getHuella();
			txt += "\nAcció: " + UtilJSF.getLiteral("typeAccionHistorial." + hv.getTipoAccion());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			txt += "\nData: " + sdf.format(hv.getFecha());
			txt += "\nUsuari: " + hv.getUsuario();
			txt = txt.replaceAll("null", "");

			addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
			PrimeFaces.current().executeScript("document.focus; navigator.clipboard.writeText(`" + txt + "`);");
		} else {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.copypaste.primerocopy"));
		}
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
				UtilJSF.getLiteral("viewAuditoriaTramites.headError") + ' ' + UtilJSF.getLiteral("botones.copiar"));
	}

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
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
	public List<HistorialVersion> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final List<HistorialVersion> data) {
		this.data = data;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public HistorialVersion getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final HistorialVersion datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

}

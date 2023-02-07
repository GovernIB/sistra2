package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.EnvioRemoto;
import es.caib.sistrages.core.api.model.VariableArea;

import es.caib.sistrages.core.api.service.VariablesAreaService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogSeleccionarVariableArea extends DialogControllerBase {

	/** Id elemento a tratar. */
	private String id;

	/** Enlace servicio. */
	@Inject
	private VariablesAreaService vaService;

	/** Campos. **/
	private List<VariableArea> campos = new ArrayList<VariableArea>();

	/** Dato seleccionado. **/
	private VariableArea datoSeleccionado;

	/** Campos en formato JSON. **/
	private String iCampos;

	/** Area **/
	private String area;

	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicialización.
	 */
	public void init() {
		campos = vaService.listVariableArea(Long.parseLong(id), null);
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		if (datoSeleccionado != null) {
			final DialogResult result = new DialogResult();
			result.setResult(datoSeleccionado.getIdentificador());
			result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
			result.setCanceled(false);
			UtilJSF.closeDialog(result);
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noSelecVarAre"));
		}
	}

	/**
	 * Aceptar.
	 */
	public void cancelar() {

		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
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
	 * @return the iCampos
	 */
	public String getiCampos() {
		return iCampos;
	}

	/**
	 * @param iCampos the iCampos to set
	 */
	public void setiCampos(final String iCampos) {
		this.iCampos = iCampos;
	}

	/**
	 * @return the campos
	 */
	public final List<VariableArea> getCampos() {
		return campos;
	}

	/**
	 * @param campos the campos to set
	 */
	public final void setCampos(List<VariableArea> campos) {
		this.campos = campos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public final VariableArea getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado the datoSeleccionado to set
	 */
	public final void setDatoSeleccionado(VariableArea datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(final String area) {
		this.area = area;
	}
}

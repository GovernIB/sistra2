package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.service.FormularioExternoService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogConfiguracionFormulario extends DialogControllerBase {

	/** Id elemento a tratar. */
	private String id;

	/** Id elemento a tratar. */
	private String idArea;

	/** Enlace servicio. */
	@Inject
	private FormularioExternoService tramiteService;

	/** Datos elemento. */
	private Dominio data;

	/** Campos. **/
	private List<GestorExternoFormularios> campos;

	/** Campos en formato JSON. **/
	private String iCampos;

	/** Fila seleccionada. **/
	private GestorExternoFormularios valorSeleccionado;

	/** Ambito. **/
	private String ambito;

	/** Area **/
	private String area;

	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicialización.
	 */
	public void init() {

		campos = tramiteService.getGestorExternoByAutenticacion(Long.valueOf(id), Long.valueOf(idArea));

	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

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
	 * @return the data
	 */
	public Dominio getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final Dominio data) {
		this.data = data;
	}

	/**
	 * @return the valorSeleccionado
	 */
	public GestorExternoFormularios getValorSeleccionado() {
		return valorSeleccionado;
	}

	/**
	 * @param valorSeleccionado the valorSeleccionado to set
	 */
	public void setValorSeleccionado(final GestorExternoFormularios valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
	}

	/**
	 * @return the campos
	 */
	public List<GestorExternoFormularios> getCampos() {
		return campos;
	}

	/**
	 * @param campos the campos to set
	 */
	public void setCampos(final List<GestorExternoFormularios> campos) {
		this.campos = campos;
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
	 * @return the ambito
	 */
	public String getAmbito() {
		return ambito;
	}

	/**
	 * @param ambito the ambito to set
	 */
	public void setAmbito(final String ambito) {
		this.ambito = ambito;
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

	/**
	 * @return the idArea
	 */
	public String getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea the idArea to set
	 */
	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}

}

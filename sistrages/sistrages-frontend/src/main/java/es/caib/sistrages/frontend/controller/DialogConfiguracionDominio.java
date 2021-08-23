package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionListener;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogConfiguracionDominio extends DialogControllerBase {

	/** Id elemento a tratar. */
	private String id;

	/** Id elemento a tratar. */
	private String idArea;

	/** Enlace servicio. */
	@Inject
	private DominioService dominioService;

	/** Campos. **/
	private List<Dominio> campos;

	/** Campos en formato JSON. **/
	private String iCampos;

	/** Fila seleccionada. **/
	private Dominio valorSeleccionado;

	/** Ambito. **/
	private String ambito;

	/** Area **/
	private String area;

	/**
	 * Inicialización.
	 */
	public void init() {
		campos = dominioService.getDominiosByConfAut(Long.valueOf(id), Long.valueOf(idArea));
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
	 * Realiza el ping.
	 *
	 */
	public void realizarPing() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.valorSeleccionado.getCodigo().toString());
		params.put(TypeParametroVentana.AMBITO.toString(), this.valorSeleccionado.getAmbito().toString());
		UtilJSF.openDialog(DialogDominioPing.class, TypeModoAcceso.CONSULTA, params, true, 770, 600);
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
	 * @return the valorSeleccionado
	 */
	public Dominio  getValorSeleccionado() {
		return valorSeleccionado;
	}

	/**
	 * @param valorSeleccionado
	 *            the valorSeleccionado to set
	 */
	public void setValorSeleccionado(final Dominio  valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
	}

	/**
	 * @return the campos
	 */
	public List<Dominio> getCampos() {
		return campos;
	}

	/**
	 * @param campos
	 *            the campos to set
	 */
	public void setCampos(final List<Dominio> campos) {
		this.campos = campos;
	}

	/**
	 * @return the iCampos
	 */
	public String getiCampos() {
		return iCampos;
	}

	/**
	 * @param iCampos
	 *            the iCampos to set
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
	 * @param ambito
	 *            the ambito to set
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
	 * @param area
	 *            the area to set
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

package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.core.api.model.FuenteCampo;
import es.caib.sistrages.core.api.model.FuenteDato;
import es.caib.sistrages.core.api.model.FuenteFila;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogFuenteFila extends DialogControllerBase {

	/** Id elemento a tratar. */
	private String id;

	/** Fuente de campos JSON. **/
	private String iCampos;

	/** Fuente de campos. **/
	private List<FuenteCampo> campos;

	/** Datos elemento en JSON. **/
	private String iData;

	/** Datos elemento. */
	private FuenteFila data;

	/**
	 * Los datos.
	 *
	 * @see Para hacer las columnas mas dinamicas:
	 *      https://www.primefaces.org/showcase/ui/data/datatable/columns.xhtml
	 **/
	private List<FuenteFila> datos;

	/** Fila seleccionada. **/
	private FuenteFila valorSeleccionado;

	/**
	 * Inicialización.
	 *
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new FuenteFila();
			campos = (List<FuenteCampo>) UtilJSON.fromListJSON(iCampos, FuenteCampo.class);
			final List<FuenteDato> fuenteDatos = new ArrayList<>();
			Long i = 0l;
			for (final FuenteCampo campo : campos) {
				fuenteDatos.add(new FuenteDato(i, campo, ""));
				i++;
			}
			data.setDatos(fuenteDatos);
		} else {
			data = (FuenteFila) UtilJSON.fromJSON(iData, FuenteFila.class);
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
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
	 * @return the campos
	 */
	public List<FuenteCampo> getCampos() {
		return campos;
	}

	/**
	 * @param campos
	 *            the campos to set
	 */
	public void setCampos(final List<FuenteCampo> campos) {
		this.campos = campos;
	}

	/**
	 * @return the iData
	 */
	public String getiData() {
		return iData;
	}

	/**
	 * @param iData
	 *            the iData to set
	 */
	public void setiData(final String iData) {
		this.iData = iData;
	}

	/**
	 * @return the data
	 */
	public FuenteFila getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final FuenteFila data) {
		this.data = data;
	}

	/**
	 * @return the datos
	 */
	public List<FuenteFila> getDatos() {
		return datos;
	}

	/**
	 * @param datos
	 *            the datos to set
	 */
	public void setDatos(final List<FuenteFila> datos) {
		this.datos = datos;
	}

	/**
	 * @return the valorSeleccionado
	 */
	public FuenteFila getValorSeleccionado() {
		return valorSeleccionado;
	}

	/**
	 * @param valorSeleccionado
	 *            the valorSeleccionado to set
	 */
	public void setValorSeleccionado(final FuenteFila valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
	}

}

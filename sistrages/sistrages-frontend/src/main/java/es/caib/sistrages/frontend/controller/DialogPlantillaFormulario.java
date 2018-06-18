package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.PlantillaFormulario;
import es.caib.sistrages.core.api.service.FormateadorFormularioService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogPlantillaFormulario extends DialogControllerBase {

	/**
	 * Dato elemento en formato JSON.
	 */
	private String iData;

	/**
	 * Datos elemento.
	 */
	private PlantillaFormulario data;

	@Inject
	private FormateadorFormularioService fmtService;

	private List<FormateadorFormulario> listaFormateadores;

	/**
	 * Inicialización.
	 */
	public void init() {

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new PlantillaFormulario();
			data.setCodigo(System.currentTimeMillis() * -1);
		} else {
			data = (PlantillaFormulario) UtilJSON.fromJSON(iData, PlantillaFormulario.class);
		}

		setListaFormateadores(fmtService.listFormateadorFormulario(UtilJSF.getIdEntidad(), null));
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
	public PlantillaFormulario getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final PlantillaFormulario data) {
		this.data = data;
	}

	public List<FormateadorFormulario> getListaFormateadores() {
		return listaFormateadores;
	}

	public void setListaFormateadores(final List<FormateadorFormulario> listaFormateadores) {
		this.listaFormateadores = listaFormateadores;
	}

}

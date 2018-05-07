package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n tasa1.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogDefinicionVersionTasa extends ViewControllerBase {

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Id. **/
	private String id;

	/** Tasa. */
	private Tasa data;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionTasa1.
	 */
	public void init() {

		data = tramiteService.getTasa(Long.valueOf(id));
	}

	/**
	 * Editar descripcion
	 */
	public void editarDescripcion() {
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, this.data.getDescripcion(), null, null);

	}

	/**
	 * Editar script
	 */
	public void editarScript() {
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, null, true, 950, 700);
	}

	/**
	 * Guarda los datos y cierra el dialog.
	 */
	public void aceptar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Cierra el dialog sin guardar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * @return the data
	 */
	public Tasa getData() {
		return data;
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
	 * @param data
	 *            the data to set
	 */
	public void setData(final Tasa data) {
		this.data = data;
	}

}

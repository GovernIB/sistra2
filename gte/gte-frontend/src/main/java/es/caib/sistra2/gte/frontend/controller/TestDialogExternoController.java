package es.caib.sistra2.gte.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistra2.gte.frontend.model.DialogResult;
import es.caib.sistra2.gte.frontend.model.types.TypeModoAcceso;
import es.caib.sistra2.gte.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class TestDialogExternoController {

	/**
	 * Nombre de la vista.
	 */
	public final static String VIEW_NAME = "dialogTestData2";
	
	/**
	 * Modo acceso ventana.
	 */
	private String modoAcceso;
	
	/**
	 * Inicialización.
	 */
	public void init() {
		// Init
	}
	
	/**
	 * Cancelar.
	 */
	public void cancelar() {
		DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	public String getModoAcceso() {
		return modoAcceso;
	}

	public void setModoAcceso(String modoAcceso) {
		this.modoAcceso = modoAcceso;
	}

	
}

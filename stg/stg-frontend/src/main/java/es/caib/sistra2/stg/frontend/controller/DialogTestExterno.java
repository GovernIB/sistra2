package es.caib.sistra2.stg.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistra2.stg.frontend.model.DialogResult;
import es.caib.sistra2.stg.frontend.model.types.TypeModoAcceso;
import es.caib.sistra2.stg.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTestExterno extends DialogControllerBase {

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



}

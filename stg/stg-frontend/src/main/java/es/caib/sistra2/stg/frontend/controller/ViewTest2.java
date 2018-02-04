package es.caib.sistra2.stg.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistra2.stg.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class ViewTest2 extends ViewControllerBase {

	/**
	 * Inicializacion.
	 */
	public void init() {
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));
		getLogger().debug("inicializado");
	}

}

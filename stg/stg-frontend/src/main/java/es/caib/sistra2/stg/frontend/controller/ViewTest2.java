package es.caib.sistra2.stg.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistra2.stg.frontend.model.types.TypeOpcionMenuSuperAdministrador;

@ManagedBean
@ViewScoped
public class ViewTest2 extends ViewControllerBase {

	/**
	 * Inicializacion.
	 */
	public void init() {
		setTituloPantalla("View Test 2");
		getLogger().debug("inicializado");
	}

}



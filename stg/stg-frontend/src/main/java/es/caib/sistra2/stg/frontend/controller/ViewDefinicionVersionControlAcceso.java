package es.caib.sistra2.stg.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistra2.stg.frontend.model.types.TypeNivelGravedad;
import es.caib.sistra2.stg.frontend.util.UtilJSF;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n control de acceso.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDefinicionVersionControlAcceso extends ViewControllerBase {

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionControlAcceso.
	 */
	public ViewDefinicionVersionControlAcceso() {
		super();
	}

	/**
	 * Abre un di&aacute;logo para editar los datos.
	 */
	public void editar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

}

package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n registrar tramite.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDefinicionVersionRegistrarTramite extends ViewControllerBase {

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionRegistrarTramite.
	 */
	public ViewDefinicionVersionRegistrarTramite() {
		super();
	}

	/**
	 * Abre un di&aacute;logo para editar los datos.
	 */
	public void editar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

}

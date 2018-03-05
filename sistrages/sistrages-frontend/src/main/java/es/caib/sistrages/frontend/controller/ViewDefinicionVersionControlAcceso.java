package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

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
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf("1"));
		UtilJSF.openDialog(DialogDefinicionVersionControlAcceso.class, TypeModoAcceso.EDICION, params, true, 950, 450);
	}

	/**
	 * Editar descripcion.
	 */
	public void editarMensajeDesactivacion() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

}

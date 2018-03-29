package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n propiedades.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDefinicionVersionPropiedades extends ViewControllerBase {

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionPropiedades.
	 */
	public ViewDefinicionVersionPropiedades() {
		super();
	}

	/**
	 * Abre un di&aacute;logo para editar los datos.
	 */
	public void editar(final Long id) {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), id.toString());
		UtilJSF.openDialog(DialogDefinicionVersionPropiedades.class, TypeModoAcceso.EDICION, params, true, 950, 520);
	}

	/**
	 * Editar descripcion.
	 *
	 * 
	 */
	public void editarDescripcion(final Literal traducciones) {
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.CONSULTA, traducciones, null, null);
	}

	/**
	 * Editar Script.
	 */
	public void editarScript() {
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.ALTA, null, true, 950, 700);
	}

}

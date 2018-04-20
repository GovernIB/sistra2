package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

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
		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf("1")); // this.data.getId()
		UtilJSF.openDialog(DialogDefinicionVersionRegistrarTramite.class, TypeModoAcceso.EDICION, params, true, 950,
				400);

	}

	/**
	 * Editar descripcion
	 */
	public void editarInstrucciones() {
		final Literal lit = new Literal();
		lit.add(new Traduccion("ca", "Instrucciones"));
		lit.add(new Traduccion("es", "Instrucciones"));
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, lit, null, null);

	}

	/**
	 * Editar script
	 */
	public void editarScript() {
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, null, true, 950, 700);
	}
}

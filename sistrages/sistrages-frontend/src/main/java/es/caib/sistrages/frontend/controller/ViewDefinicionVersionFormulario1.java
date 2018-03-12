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
 * Mantenimiento de definici&oacute;n de versi&oacute;n formulario1.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDefinicionVersionFormulario1 extends ViewControllerBase {

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionFormulario1.
	 */
	public ViewDefinicionVersionFormulario1() {
		super();
	}

	/**
	 * Abre un di&aacute;logo para editar los datos.
	 */
	public void editar() {
		final Map<String, String> params = new HashMap<String, String>();
		params.put(TypeParametroVentana.ID.toString(), "1");
		UtilJSF.openDialog(DialogDisenyoFormulario.class, TypeModoAcceso.CONSULTA, params, true, 1100, 800);
	}

	/**
	 * Sin implementar.
	 */
	public void sinImplementar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

}

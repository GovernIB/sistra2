package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.TramitePasoTasa;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n tasa1.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDefinicionVersionTasa extends ViewControllerBase {

	/** Tasa. */
	private final TramitePasoTasa data;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionTasa1.
	 */
	public ViewDefinicionVersionTasa() {
		super();

		data = new TramitePasoTasa();
		data.setId(4L);
		data.setCodigo("4");
		final Literal literal4 = new Literal();
		literal4.add(new Traduccion("ca", "pagar Tasas"));
		literal4.add(new Traduccion("es", "pagar Tasas"));
		data.setDescripcion(literal4);
		data.setOrden(4);

	}

	/**
	 * Editar script
	 */
	public void editarScript() {
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, null, true, 950, 700);
	}

	/**
	 * Abre un di&aacute;logo para editar los datos.
	 */
	public void editar() {
		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.data.getId()));
		UtilJSF.openDialog(DialogDefinicionVersionTasa.class, TypeModoAcceso.EDICION, params, true, 950, 400);

	}

	/**
	 * @return the data
	 */
	public TramitePasoTasa getData() {
		return data;
	}

}

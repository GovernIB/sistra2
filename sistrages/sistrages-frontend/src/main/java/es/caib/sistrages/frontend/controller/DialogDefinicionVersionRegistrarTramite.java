package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
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
public class DialogDefinicionVersionRegistrarTramite extends ViewControllerBase {

	/** Id. **/
	private String id;

	/**
	 * Init.
	 */
	public void init() {

	}

	/**
	 * Guarda los datos y cierra el dialog.
	 */
	public void aceptar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(null);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Cierra el dialog sin guardar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Abre un di&aacute;logo para editar los datos.
	 */
	public void editar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
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

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}
}

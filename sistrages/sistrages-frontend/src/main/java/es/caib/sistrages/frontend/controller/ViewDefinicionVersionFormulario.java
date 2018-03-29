package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.core.api.model.Formulario;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.types.TypeFormulario;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n formulario1.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDefinicionVersionFormulario extends ViewControllerBase {

	/** Data. **/
	private Formulario data;

	/** Id. **/
	private String id;

	@PostConstruct
	public void init() {
		// TODO tendría que obtener el tramitePasoRellenar a partir de la id.
		id = "1";
		data = new Formulario();
		data.setId(1L);
		data.setCodigo("Formulario1");
		final Literal traducciones = new Literal();
		traducciones.add(new Traduccion("ca", "Datos de la solicitud"));
		traducciones.add(new Traduccion("es", "Datos de la solicitud"));
		data.setDescripcion(traducciones);
		data.setObligatoriedad(TypeFormularioObligatoriedad.OPCIONAL);
		data.setTipo(TypeFormulario.TRAMITE);
		data.setDebeFirmarse(true);
		data.setDebePrerregistrarse(true);
	}

	/**
	 * Abre un di&aacute;logo para editar los datos.
	 *
	 * 
	 */
	public void editarDescripcion() {
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.CONSULTA, data.getDescripcion(), null, null);

	}

	/**
	 * Lo pasa a modo editar.
	 *
	 * 
	 */
	public void editar() {
		final Map<String, String> params = new HashMap<String, String>();
		params.put(TypeParametroVentana.ID.toString(), id);
		UtilJSF.openDialog(DialogDefinicionVersionFormulario.class, TypeModoAcceso.EDICION, null, true, 950, 500);
	}

	/**
	 * Abre un di&aacute;logo para editar los datos.
	 */
	public void editarDisenyo() {
		final Map<String, String> params = new HashMap<String, String>();
		params.put(TypeParametroVentana.ID.toString(), "1");
		UtilJSF.openDialog(DialogDisenyoFormulario.class, TypeModoAcceso.CONSULTA, params, true, 1100, 800);
	}

	/**
	 * Script.
	 */
	public void script() {
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.CONSULTA, null, true, 950, 700);
	}

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionFormulario1.
	 */
	public ViewDefinicionVersionFormulario() {
		super();
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

	/**
	 * @return the data
	 */
	public Formulario getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final Formulario data) {
		this.data = data;
	}

}

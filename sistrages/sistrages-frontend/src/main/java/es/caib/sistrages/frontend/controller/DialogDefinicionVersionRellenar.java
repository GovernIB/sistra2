package es.caib.sistrages.frontend.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeFormulario;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n de rellenar.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogDefinicionVersionRellenar extends DialogControllerBase {

	/** Data. **/
	private FormularioTramite data;

	/** Id. **/
	private String id;

	/** Json tramite version. **/
	private String jsonTramiteVersion;

	/** Tramite version. **/
	private TramiteVersion tramiteVersion;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionRellenar.
	 */
	public DialogDefinicionVersionRellenar() {
		super();

	}

	@PostConstruct
	public void init() {

		data = new FormularioTramite();
		data.setObligatoriedad(TypeFormularioObligatoriedad.OPCIONAL);
		data.setTipo(TypeFormulario.TRAMITE);
		if (jsonTramiteVersion != null) {
			tramiteVersion = (TramiteVersion) UtilJSON.fromJSON(jsonTramiteVersion, TramiteVersion.class);
		}
	}

	/**
	 * Retorno dialogo de los botones de propiedades.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoDescripcion(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:
			case EDICION:

				final Literal traducciones = (Literal) respuesta.getResult();
				data.setDescripcion(traducciones);

				break;

			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

	}

	/**
	 * Editar descripcion del dominio.
	 *
	 *
	 */
	public void editarDescripcion() {
		if (data.getDescripcion() == null) {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, UtilTraducciones.getTraduccionesPorDefecto(),
					tramiteVersion);
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getDescripcion(), tramiteVersion);
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		if (this.data.getDescripcion() == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.descripcion.norelleno"));
			return;
		}

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * @return the data
	 */
	public FormularioTramite getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final FormularioTramite data) {
		this.data = data;
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
	 * @return the jsonTramiteVersion
	 */
	public String getJsonTramiteVersion() {
		return jsonTramiteVersion;
	}

	/**
	 * @param jsonTramiteVersion
	 *            the jsonTramiteVersion to set
	 */
	public void setJsonTramiteVersion(final String jsonTramiteVersion) {
		this.jsonTramiteVersion = jsonTramiteVersion;
	}

}

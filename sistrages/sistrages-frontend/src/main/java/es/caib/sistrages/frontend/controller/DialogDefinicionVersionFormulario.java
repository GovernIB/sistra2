package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
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
public class DialogDefinicionVersionFormulario extends DialogControllerBase {

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Data. **/
	private FormularioTramite data;

	/** ID tramiteVersion version. **/
	private String idTramiteVersion;

	/** tramiteVersion version. **/
	private TramiteVersion tramiteVersion;

	/** Id. **/
	private String id;

	/** Init. **/
	public void init() {

		data = tramiteService.getFormulario(Long.valueOf(id));
		if (idTramiteVersion != null) {
			tramiteVersion = tramiteService.getTramiteVersion(Long.valueOf(idTramiteVersion));
		}
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
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
	 * Abre un di&aacute;logo para editar los datos.
	 *
	 *
	 */
	public void editarDescripcion() {
		if (data.getDescripcion() == null) {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.ALTA, UtilTraducciones.getTraduccionesPorDefecto(),
					tramiteVersion);
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getDescripcion(), tramiteVersion);
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

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
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoDatosIniciales(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				final Script script = (Script) respuesta.getResult();
				data.setScriptDatosIniciales(script);
				break;
			case CONSULTA:
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoFirma(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				final Script script = (Script) respuesta.getResult();
				data.setScriptFirma(script);
				break;
			case CONSULTA:
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoObligatoriedad(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				final Script script = (Script) respuesta.getResult();
				data.setScriptObligatoriedad(script);
				break;
			case CONSULTA:
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoPrerregistro(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				final Script script = (Script) respuesta.getResult();
				data.setScriptPrerregistro(script);
				break;
			case CONSULTA:
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoRetorno(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				final Script script = (Script) respuesta.getResult();
				data.setScriptRetorno(script);
				break;
			case CONSULTA:
			default:
				break;
			}
		}
	}

	/**
	 * Carga el script
	 *
	 * @param script
	 */
	public void script(final Script script) {
		if (id == null) {
			UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.ALTA, null, true, 950, 700);
		} else {
			final Map<String, String> params = new HashMap<>();
			if (data.getScriptDatosIniciales() == null) {
				params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(new Script()));
			} else {
				params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(script));
			}
			UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.valueOf(modoAcceso), params, true, 950, 700);
		}
	}

	/**
	 * Abre un di&aacute;logo para editar los datos.
	 */
	public void editarDisenyo() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.data.getFormulario().getId().toString());
		UtilJSF.openDialog(DialogDisenyoFormulario.class, TypeModoAcceso.EDICION, params, true, 1200, 720);
	}

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionFormulario1.
	 */
	public DialogDefinicionVersionFormulario() {
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
	 * Get idTramiteVersion
	 *
	 * @return
	 */
	public String getIdTramiteVersion() {
		return idTramiteVersion;
	}

	/**
	 * Set idTramiteVersion
	 *
	 * @param idTramiteVersion
	 */
	public void setIdTramiteVersion(final String idTramiteVersion) {
		this.idTramiteVersion = idTramiteVersion;
	}

}

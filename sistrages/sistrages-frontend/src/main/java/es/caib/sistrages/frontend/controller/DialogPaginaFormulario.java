package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.types.TypeScriptFormulario;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogPaginaFormulario extends DialogControllerBase {

	/**
	 * Dato elemento en formato JSON.
	 */
	private String iData;

	/**
	 * Datos elemento.
	 */
	private PaginaFormulario data;

	@Inject
	FormularioInternoService formIntService;

	/**
	 * Inicialización.
	 */
	public void init() {

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new PaginaFormulario();
			// data.setCodigo(System.currentTimeMillis() * -1);
		} else {
			data = (PaginaFormulario) UtilJSON.fromJSON(iData, PaginaFormulario.class);
		}

	}

	/**
	 * Editar script
	 */
	public void editarDialogScriptValidacion(final String tipoScript, final Script script) {
		final Map<String, String> maps = new HashMap<>();
		maps.put(TypeParametroVentana.TIPO_SCRIPT_FORMULARIO.toString(),
				UtilJSON.toJSON(TypeScriptFormulario.fromString(tipoScript)));
		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(script));
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, maps, true, 700);

	}

	/**
	 * Retorno dialogo del script de personalizacion.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoScriptValidacion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setScriptValidacion((Script) respuesta.getResult());
				break;
			default:
				break;
			}
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
	 * @return the iData
	 */
	public String getiData() {
		return iData;
	}

	/**
	 * @param iData
	 *            the iData to set
	 */
	public void setiData(final String iData) {
		this.iData = iData;
	}

	/**
	 * @return the data
	 */
	public PaginaFormulario getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final PaginaFormulario data) {
		this.data = data;
	}

}

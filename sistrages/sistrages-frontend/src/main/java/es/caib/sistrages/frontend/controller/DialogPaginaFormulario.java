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

	/** Id. formulario interno. **/
	private String idFormulario;

	/** Id. tramite version. **/
	private String idTramiteversion;

	/** Id. formulario interno. **/
	private String idFormularioInterno;

	/** cambios **/
	private boolean cambios = false;
	/**
	 * Datos elemento.
	 */
	private PaginaFormulario data;
	/**
	 * Datos elemento iniciales.
	 */
	private PaginaFormulario dataI;

	@Inject
	FormularioInternoService formIntService;

	/**
	 * Inicialización.
	 */
	public void init() {

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new PaginaFormulario();
		} else {
			final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
			final Object json = mochila.get(Constantes.CLAVE_MOCHILA_FORMULARIO);
			if (json == null) {
				data = new PaginaFormulario();
				dataI = new PaginaFormulario();
			} else {
				data = (PaginaFormulario) UtilJSON.fromJSON(json.toString(), PaginaFormulario.class);
				dataI = (PaginaFormulario) UtilJSON.fromJSON(json.toString(), PaginaFormulario.class);
			}
		}
	}

	/**
	 * Editar script
	 */
	public void editarDialogScript(final String tipoScript, final Script script) {
		final Map<String, String> maps = new HashMap<>();
		maps.put(TypeParametroVentana.TIPO_SCRIPT_FORMULARIO.toString(),
				UtilJSON.toJSON(TypeScriptFormulario.fromString(tipoScript)));
		maps.put(TypeParametroVentana.FORM_INTERNO_ACTUAL.toString(), idFormularioInterno);
		maps.put(TypeParametroVentana.PAGINA.toString(), this.data.getCodigo().toString());
		maps.put(TypeParametroVentana.TRAMITEVERSION.toString(), idTramiteversion);
		maps.put(TypeParametroVentana.FORMULARIO_ACTUAL.toString(), this.idFormulario);
		maps.put(TypeParametroVentana.PARAMETRO_DISENYO.toString(), TypeParametroVentana.PARAMETRO_DISENYO_TRAMITE.toString());

		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(script));
		TypeModoAcceso modo;
		if (modoAcceso.equals(TypeModoAcceso.CONSULTA.toString())) {
			modo = TypeModoAcceso.CONSULTA;
		} else {
			modo = TypeModoAcceso.EDICION;
		}
		UtilJSF.openDialog(DialogScript.class, modo, maps, true, 700);

	}

	/**
	 * Retorno dialogo del script de personalizacion.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoScriptValidacion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setScriptValidacion((Script) respuesta.getResult());
				if (dataI != null && data != null) {
					if (this.isCambioScripts(data.getScriptValidacion(), dataI.getScriptValidacion())) {
						cambios = true;
					}
				} else if (dataI == null) {
					if (data != null) {
						cambios = true;
					}
				} else {
					if (dataI != null) {
						cambios = true;
					}
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo del script de personalizacion.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoScriptNavegacion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setScriptNavegacion((Script) respuesta.getResult());
				if (dataI != null && data != null) {
					if (this.isCambioScripts(data.getScriptNavegacion(), dataI.getScriptNavegacion())) {
						cambios = true;
					}
				} else if (dataI == null) {
					if (data != null) {
						cambios = true;
					}
				} else {
					if (dataI != null) {
						cambios = true;
					}
				}
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
		// Si es página final, quitamos el script de navegación
		if (data.isPaginaFinal()) {
			data.setScriptNavegacion(null);
		}

		if (!data.getIdentificador().equals(dataI.getIdentificador())
				|| data.isPaginaFinal() != dataI.isPaginaFinal()) {
			cambios = true;
		}
		result.setResult(new Object[] { data, cambios });
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
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("paginaFormularioDialog");
	}

	/**
	 * @return the data
	 */
	public PaginaFormulario getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final PaginaFormulario data) {
		this.data = data;
	}

	/**
	 * @return the idFormulario
	 */
	public String getIdFormulario() {
		return idFormulario;
	}

	/**
	 * @param idFormulario the idFormulario to set
	 */
	public void setIdFormulario(final String idFormulario) {
		this.idFormulario = idFormulario;
	}

	/**
	 * @return the idTramiteversion
	 */
	public String getIdTramiteversion() {
		return idTramiteversion;
	}

	/**
	 * @param idTramiteversion the idTramiteversion to set
	 */
	public void setIdTramiteversion(final String idTramiteversion) {
		this.idTramiteversion = idTramiteversion;
	}

	/**
	 * @return the idFormularioInterno
	 */
	public String getIdFormularioInterno() {
		return idFormularioInterno;
	}

	/**
	 * @param idFormularioInterno the idFormularioInterno to set
	 */
	public void setIdFormularioInterno(final String idFormularioInterno) {
		this.idFormularioInterno = idFormularioInterno;
	}

}

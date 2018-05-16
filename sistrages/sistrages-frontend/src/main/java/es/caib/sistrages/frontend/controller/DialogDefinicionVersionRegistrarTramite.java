package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.TramitePasoRegistrar;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
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
public class DialogDefinicionVersionRegistrarTramite extends ViewControllerBase {

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Id. **/
	private String id;

	/** Tramite Paso Registrar. **/
	private TramitePasoRegistrar data;

	/** Tramite version. **/
	private TramiteVersion tramiteVersion;

	/** ID tramite version. **/
	private String idTramiteVersion;

	/**
	 * Init.
	 */
	public void init() {
		data = (TramitePasoRegistrar) tramiteService.getTramitePaso(Long.valueOf(id));
		if (idTramiteVersion != null) {
			tramiteVersion = tramiteService.getTramiteVersion(Long.valueOf(idTramiteVersion));
		}
	}

	/**
	 * Guarda los datos y cierra el dialog.
	 */
	public void aceptar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
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
	 * Editar descripcion
	 */
	public void editarInstPresentacion() {
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, this.data.getInstruccionesPresentacion(),
				tramiteVersion);

	}

	/**
	 * Editar descripcion
	 */
	public void editarInstTramitacion() {
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, this.data.getInstruccionesFinTramitacion(),
				tramiteVersion);

	}

	/**
	 * Retorno dialogo de instrucciones.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoInstrucciones(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setInstruccionesFinTramitacion((Literal) respuesta.getResult());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo de presentacion.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoPresentacion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setInstruccionesPresentacion((Literal) respuesta.getResult());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo del script de presentador
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoScriptPresentador(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setScriptPresentador((Script) respuesta.getResult());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo del script de registro
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoScriptValidarRegistro(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setScriptValidarRegistrar((Script) respuesta.getResult());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo del script de registro
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoScriptRegistro(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setScriptDestinoRegistro((Script) respuesta.getResult());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo del script de registro
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoScriptRepresentante(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setScriptRepresentante((Script) respuesta.getResult());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Editar script
	 */
	public void editarScript(final Script script) {
		final Map<String, String> maps = new HashMap<>();
		if (script != null) {
			maps.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(script));
		}
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, maps, true, 950, 700);
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
	public TramitePasoRegistrar getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final TramitePasoRegistrar data) {
		this.data = data;
	}

	public String getIdTramiteVersion() {
		return idTramiteVersion;
	}

	public void setIdTramiteVersion(final String idTramiteVersion) {
		this.idTramiteVersion = idTramiteVersion;
	}
}

package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.service.PluginService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n tasa1.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogDefinicionVersionTasa extends ViewControllerBase {

	/** Tramite service. */
	@Inject
	private PluginService pluginService;

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Id. **/
	private String id;

	/** Tasa. */
	private Tasa data;

	/** Tramite version. **/
	private TramiteVersion tramiteVersion;

	/** ID tramite version. **/
	private String idTramiteVersion;

	/** Lista con los plugins. **/
	private List<Plugin> plugins;

	/** Id plugin. **/
	private Long idPlugin;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionTasa1.
	 */
	public void init() {
		data = tramiteService.getTasa(Long.valueOf(id));
		if (idTramiteVersion != null) {
			tramiteVersion = tramiteService.getTramiteVersion(Long.valueOf(idTramiteVersion));
		}
		plugins = pluginService.listPlugin(TypeAmbito.ENTIDAD, UtilJSF.getIdEntidad(), TypePlugin.PAGOS);
		if (data.getTipoPlugin() != null) {
			idPlugin = data.getTipoPlugin().getId();
		}
	}

	/**
	 * Editar descripcion
	 */
	public void editarDescripcion() {
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, this.data.getDescripcion(), tramiteVersion);

	}

	/**
	 * Editar script
	 */
	public void editarScript(final String tipoScript, final Script script) {
		final Map<String, String> maps = new HashMap<>();
		maps.put(TypeParametroVentana.TIPO_SCRIPT.toString(), tipoScript);
		if (script != null) {
			maps.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(script));
		}
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, maps, true, 950, 700);
	}

	/**
	 * Retorno dialogo del script de obligatoriedad.
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
				data.setDescripcion((Literal) respuesta.getResult());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo del script de obligatoriedad.
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
				data.setScriptObligatoriedad((Script) respuesta.getResult());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo del script de pago.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoPago(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setScriptPago((Script) respuesta.getResult());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Guarda los datos y cierra el dialog.
	 */
	public void aceptar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		if (idPlugin != null) {
			final Plugin plugin = pluginService.getPlugin(idPlugin);
			data.setTipoPlugin(plugin);
		}
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
	 * @return the data
	 */
	public Tasa getData() {
		return data;
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
	 * @param data
	 *            the data to set
	 */
	public void setData(final Tasa data) {
		this.data = data;
	}

	/**
	 * @return the tramiteService
	 */
	public TramiteService getTramiteService() {
		return tramiteService;
	}

	/**
	 * @param tramiteService
	 *            the tramiteService to set
	 */
	public void setTramiteService(final TramiteService tramiteService) {
		this.tramiteService = tramiteService;
	}

	public String getIdTramiteVersion() {
		return idTramiteVersion;
	}

	public void setIdTramiteVersion(final String idTramiteVersion) {
		this.idTramiteVersion = idTramiteVersion;
	}

	public List<Plugin> getPlugins() {
		return plugins;
	}

	public void setPlugins(final List<Plugin> plugins) {
		this.plugins = plugins;
	}

	public Long getIdPlugin() {
		return idPlugin;
	}

	public void setIdPlugin(final Long idPlugin) {
		this.idPlugin = idPlugin;
	}

}

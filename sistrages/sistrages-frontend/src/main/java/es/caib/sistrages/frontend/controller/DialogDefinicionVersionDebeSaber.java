package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.model.types.TypeScriptFlujo;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n de debe saber.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogDefinicionVersionDebeSaber extends DialogControllerBase {

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Id. **/
	private String id;

	/** Data. **/
	private TramitePasoDebeSaber data;

	/** Data inicial **/
	private TramitePasoDebeSaber dataI;

	/** ID tramite version. **/
	private String idTramiteVersion;

	/** Tramite version. **/
	private TramiteVersion tramiteVersion;

	/** Idiomas. **/
	private List<String> idiomas;

	private boolean cambios = false;

	private String portapapeles;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionDebeSaber.
	 */
	public DialogDefinicionVersionDebeSaber() {
		super();
	}

	public void init() {
		data = (TramitePasoDebeSaber) tramiteService.getTramitePaso(Long.valueOf(id));
		dataI = (TramitePasoDebeSaber) tramiteService.getTramitePaso(Long.valueOf(id));
		tramiteVersion = tramiteService.getTramiteVersion(Long.valueOf(idTramiteVersion));
		idiomas = UtilTraducciones.getIdiomas(tramiteVersion.getIdiomasSoportados());
	}

	/**
	 * Retorno dialogo de los botones de propiedades.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:

				final Literal traduccionesMod = (Literal) respuesta.getResult();
				final Literal traduccionesI = dataI.getInstruccionesIniciales();
				if (this.isCambioLiterales(traduccionesI, traduccionesMod)) {
					cambios = true;
				}
				data.setInstruccionesIniciales(traduccionesMod);

				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 *
	 *
	 */
	public void editar() {

		if (data.getInstruccionesIniciales() == null) {
			UtilTraducciones.openDialogTraduccionHTML(TypeModoAcceso.ALTA, data.getInstruccionesIniciales(), idiomas,
					idiomas, true);
		} else {
			UtilTraducciones.openDialogTraduccionHTML(TypeModoAcceso.EDICION, data.getInstruccionesIniciales(), idiomas,
					idiomas, true);
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		if (TypeModoAcceso.valueOf(modoAcceso) == TypeModoAcceso.EDICION) {
			tramiteService.updateTramitePaso(data);
			if (cambios) {
				tramiteService.actualizarFechaTramiteVersion(Long.parseLong(idTramiteVersion),
						UtilJSF.getSessionBean().getUserName(), "Modificación debe saber");
			}
		}

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

	public void returnDialogoScriptDebeSaber(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setScriptDebeSaber((Script) respuesta.getResult());
				if (dataI != null && data != null) {
					if (this.isCambioScripts(dataI.getScriptDebeSaber(), data.getScriptDebeSaber())) {
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
	 * Editar script
	 */
	public void editarScript(final String tipoScript, final Script script) {
		final Map<String, String> maps = new HashMap<>();
		maps.put(TypeParametroVentana.TIPO_SCRIPT_FLUJO.toString(),
				UtilJSON.toJSON(TypeScriptFlujo.fromString(tipoScript)));
		if (script != null) {
			UtilJSF.getSessionBean().limpiaMochilaDatos();
			final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
			mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(script));
		}
		String literal = "true";
		maps.put(TypeParametroVentana.TRAMITEVERSION.toString(), idTramiteVersion);
		maps.put(TypeParametroVentana.TRAMITEPASO.toString(), id);
		maps.put(TypeParametroVentana.LITERAL_HTML.toString(), literal);

		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, maps, true, 700);
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */

	/**
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("definicionVersionDebeSaberDialog");
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
				UtilJSF.getLiteral("viewAuditoriaTramites.headError") + ' ' + UtilJSF.getLiteral("botones.copiar"));
	}

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the data
	 */
	public TramitePasoDebeSaber getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final TramitePasoDebeSaber data) {
		this.data = data;
	}

	public String getIdTramiteVersion() {
		return idTramiteVersion;
	}

	public void setIdTramiteVersion(final String idTramiteVersion) {
		this.idTramiteVersion = idTramiteVersion;
	}

	/**
	 * @return the idiomas
	 */
	public List<String> getIdiomas() {
		return idiomas;
	}

	/**
	 * @param idiomas the idiomas to set
	 */
	public void setIdiomas(final List<String> idiomas) {
		this.idiomas = idiomas;
	}

	public void setCambios() {
		this.cambios = true;
	}

}

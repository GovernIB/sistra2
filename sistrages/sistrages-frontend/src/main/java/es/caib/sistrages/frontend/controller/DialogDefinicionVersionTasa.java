package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
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
 * Mantenimiento de definici&oacute;n de versi&oacute;n tasa1.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogDefinicionVersionTasa extends DialogControllerBase {

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Id. **/
	private String id;

	/** Tasa. */
	private Tasa data;

	/** Tasa inicial. */
	private Tasa dataI;

	/** Tramite version. **/
	private TramiteVersion tramiteVersion;

	/** ID tramite version. **/
	private String idTramiteVersion;

	/** ID Paso. **/
	private String idPaso;

	/** Idiomas. **/
	private List<String> idiomas;

	private boolean cambios = false;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionTasa1.
	 */
	public void init() {
		data = tramiteService.getTasa(Long.valueOf(id));
		dataI = tramiteService.getTasa(Long.valueOf(id));
		tramiteVersion = tramiteService.getTramiteVersion(Long.valueOf(idTramiteVersion));
		setIdiomas(UtilTraducciones.getIdiomas(tramiteVersion.getIdiomasSoportados()));
	}

	/**
	 * Editar descripcion
	 */
	public void editarDescripcion() {
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, this.data.getDescripcion(), tramiteVersion,
				UtilTraducciones.CARACTERES_NOPERMIT_REGWEB3, UtilTraducciones.TAMANYO_MAXIMO_REGWEB3);

	}

	/**
	 * Editar script
	 */
	public void editarScript(final String tipoScript, final Script script) {
		final Map<String, String> maps = new HashMap<>();
		maps.put(TypeParametroVentana.TIPO_SCRIPT_FLUJO.toString(),
				UtilJSON.toJSON(TypeScriptFlujo.fromString(tipoScript)));
		if (script != null) {
			final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
			mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(script));
		}
		maps.put(TypeParametroVentana.TRAMITEVERSION.toString(), idTramiteVersion);
		maps.put(TypeParametroVentana.TRAMITEPASO.toString(), idPaso);
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, maps, true, 700);

	}

	/**
	 * Retorno dialogo del script de obligatoriedad.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoDescripcion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setDescripcion((Literal) respuesta.getResult());
				final Literal traduccionesMod = (Literal) respuesta.getResult();
				final Literal traduccionesI = dataI.getDescripcion();
				if (this.isCambioLiterales(traduccionesI, traduccionesMod)) {
					cambios = true;
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo del script de obligatoriedad.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoObligatoriedad(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setScriptObligatoriedad((Script) respuesta.getResult());
				if (dataI != null && data != null) {
					if (this.isCambioScripts(dataI.getScriptObligatoriedad(), data.getScriptObligatoriedad())) {
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
	 * Retorno dialogo del script de pago.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoPago(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setScriptPago((Script) respuesta.getResult());
				if (dataI != null && data != null) {
					if (this.isCambioScripts(dataI.getScriptPago(), data.getScriptPago())) {
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
	 * Guarda los datos y cierra el dialog.
	 */
	public void aceptar() {
		// verificamos precondiciones
		if (!verificarGuardar()) {
			return;
		}

		tramiteService.updateTasaTramite(data);
		if (cambios) {
			tramiteService.actualizarFechaTramiteVersion(Long.parseLong(idTramiteVersion),
					UtilJSF.getSessionBean().getUserName(), "Modificación tasa");
		}
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
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("definicionVersionTasaDialog");
	}

	/**
	 * Verificar precondiciones al guardar.
	 *
	 * @return true, si se cumplen las todas la condiciones
	 */
	private boolean verificarGuardar() {
		if (tramiteService.checkTasaRepetida(tramiteVersion.getCodigo(), this.data.getIdentificador(),
				this.data.getCodigo())) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.identificador.repetido"));
			return false;
		}

		if (TypeFormularioObligatoriedad.DEPENDIENTE.equals(data.getObligatoriedad())
				&& (data.getScriptObligatoriedad() == null
						|| StringUtils.isEmpty(data.getScriptObligatoriedad().getContenido()))) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.obligatorio.dependencia"));
			return false;
		}

		return true;
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
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @param data the data to set
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
	 * @param tramiteService the tramiteService to set
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

	/**
	 * @return the idPaso
	 */
	public String getIdPaso() {
		return idPaso;
	}

	/**
	 * @param idPaso the idPaso to set
	 */
	public void setIdPaso(final String idPaso) {
		this.idPaso = idPaso;
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

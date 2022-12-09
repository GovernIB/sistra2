package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.exception.FrontException;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.core.api.model.types.TypeScriptFlujo;
import es.caib.sistrages.core.api.service.FormularioExternoService;
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

	/** Formulario Externo service. */
	@Inject
	private FormularioExternoService gestorFormularioExternoService;

	/** Gestor Formularios externos. **/
	private List<GestorExternoFormularios> gestores;

	/** Data. **/
	private FormularioTramite data;

	/** Data inicial. **/
	private FormularioTramite dataI;

	/** ID tramiteVersion version. **/
	private String idTramiteVersion;

	/** tramiteVersion version. **/
	private TramiteVersion tramiteVersion;

	/** Id. **/
	private String id;

	/** Id del formulario al que pertenece. **/
	private String idPaso;

	/** Idiomas. **/
	private List<String> idiomas;

	/** Area **/
	private String area;

	/** Cambios **/
	private boolean cambios = false;

	private String portapapeles;

	/** Init. **/
	public void init() {

		data = tramiteService.getFormulario(Long.valueOf(id));
		dataI = tramiteService.getFormulario(Long.valueOf(id));
		tramiteVersion = tramiteService.getTramiteVersion(Long.valueOf(idTramiteVersion));
		gestores = gestorFormularioExternoService.listFormularioExterno(Long.valueOf(area), UtilJSF.getIdioma(), null);
		setIdiomas(UtilTraducciones.getIdiomas(tramiteVersion.getIdiomasSoportados()));
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:
			case EDICION:
				final Literal traducciones = (Literal) respuesta.getResult();
				Literal literalesI = dataI.getDescripcion();
				final Literal literales = (Literal) respuesta.getResult();
				if (this.isCambioLiterales(literalesI, literales)) {
					cambios = true;
				}
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
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.ALTA, null, tramiteVersion,
					UtilTraducciones.CARACTERES_NOPERMIT_REGWEB3, UtilTraducciones.TAMANYO_MAXIMO_REGWEB3);
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getDescripcion(), tramiteVersion,
					UtilTraducciones.CARACTERES_NOPERMIT_REGWEB3, UtilTraducciones.TAMANYO_MAXIMO_REGWEB3);
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		// verificamos precondiciones
		if (!verificarGuardar()) {
			return;
		}

		tramiteService.updateFormularioTramite(data);
		if (cambios) {
			tramiteService.actualizarFechaTramiteVersion(Long.parseLong(idTramiteVersion),
					UtilJSF.getSessionBean().getUserName(), "Modificación formulario");
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
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("definicionVersionFormularioDialog");
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoDatosIniciales(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				final Script script = (Script) respuesta.getResult();
				data.setScriptDatosIniciales(script);
				if (dataI != null && data != null) {
					if (this.isCambioScripts(data.getScriptDatosIniciales(), dataI.getScriptDatosIniciales())) {
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
			case CONSULTA:
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoFirma(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				final Script script = (Script) respuesta.getResult();
				data.setScriptFirma(script);
				if (dataI != null && data != null) {
					if (this.isCambioScripts(data.getScriptFirma(), dataI.getScriptFirma())) {
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
			case CONSULTA:
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoObligatoriedad(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				final Script script = (Script) respuesta.getResult();
				data.setScriptObligatoriedad(script);
				if (dataI != null && data != null) {
					if (this.isCambioScripts(data.getScriptObligatoriedad(), dataI.getScriptObligatoriedad())) {
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
			case CONSULTA:
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoParametros(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				final Script script = (Script) respuesta.getResult();
				data.setScriptParametros(script);
				if (dataI != null && data != null) {
					if (this.isCambioScripts(data.getScriptParametros(), dataI.getScriptParametros())) {
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
			case CONSULTA:
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoRetorno(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				final Script script = (Script) respuesta.getResult();
				data.setScriptRetorno(script);
				if (dataI != null && data != null) {
					if (this.isCambioScripts(data.getScriptRetorno(), dataI.getScriptRetorno())) {
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
	public void script(final String tipoScript, final Script script) {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.TIPO_SCRIPT_FLUJO.toString(),
				UtilJSON.toJSON(TypeScriptFlujo.fromString(tipoScript)));
		params.put(TypeParametroVentana.TRAMITEVERSION.toString(), idTramiteVersion);
		params.put(TypeParametroVentana.TRAMITEPASO.toString(), this.idPaso);
		if (TypeScriptFlujo.fromString(tipoScript) != TypeScriptFlujo.SCRIPT_POSTGUARDAR_FORMULARIO) {
			params.put(TypeParametroVentana.FORMULARIO_ACTUAL.toString(), this.data.getCodigo().toString());
			params.put(TypeParametroVentana.FORM_INTERNO_ACTUAL.toString(),
					this.data.getIdFormularioInterno().toString());
		}

		if (id == null || script == null) {

			UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, params, true, 700);

		} else {

			final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
			mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(script));
			UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, params, true, 700);

		}
	}

	/**
	 * Abre un di&aacute;logo para editar los datos.
	 */
	public void editarDisenyo() {
		final Map<String, String> params = new HashMap<>();
		if (this.data.getIdFormularioInterno() == null) {
			throw new FrontException("No existe diseño formulario");
		}
		params.put(TypeParametroVentana.ID.toString(), this.data.getIdFormularioInterno().toString());

		params.put(TypeParametroVentana.TRAMITEVERSION.toString(), String.valueOf(tramiteVersion.getCodigo()));
		params.put(TypeParametroVentana.FORMULARIO_ACTUAL.toString(), this.id);
		params.put(TypeParametroVentana.PARAMETRO_DISENYO.toString(),
				TypeParametroVentana.PARAMETRO_DISENYO_TRAMITE.toString());

		Integer width = UtilJSF.getSessionBean().getWidth();
		Integer height = UtilJSF.getSessionBean().getHeight() - 60;
		UtilJSF.openDialog(DialogDisenyoFormulario.class, TypeModoAcceso.EDICION, params, true, width, height);
	}

	/**
	 * Verificar precondiciones al guardar.
	 *
	 * @return true, si se cumplen las todas la condiciones
	 */
	private boolean verificarGuardar() {

		if (TypeFormularioObligatoriedad.DEPENDIENTE.equals(data.getObligatoriedad())
				&& (data.getScriptObligatoriedad() == null
						|| StringUtils.isEmpty(data.getScriptObligatoriedad().getContenido()))) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.obligatorio.dependencia"));
			return false;
		}

		if (tramiteService.checkFormularioRepetido(tramiteVersion.getCodigo(), this.data.getIdentificador(),
				this.data.getCodigo())) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.identificador.repetido"));
			return false;
		}

		return true;
	}

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionFormulario1.
	 */
	public DialogDefinicionVersionFormulario() {
		super();
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
	public FormularioTramite getData() {
		return data;
	}

	/**
	 * @param data the data to set
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
	 * @return the tramiteVersion
	 */
	public final TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * @param tramiteVersion the tramiteVersion to set
	 */
	public final void setTramiteVersion(final TramiteVersion tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
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

	/**
	 * @return the gestores
	 */
	public List<GestorExternoFormularios> getGestores() {
		return gestores;
	}

	/**
	 * @param gestores the gestores to set
	 */
	public void setGestores(final List<GestorExternoFormularios> gestores) {
		this.gestores = gestores;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	public void setCambios() {
		this.cambios = true;
	}

	public boolean getCambios() {
		return this.cambios;
	}

}

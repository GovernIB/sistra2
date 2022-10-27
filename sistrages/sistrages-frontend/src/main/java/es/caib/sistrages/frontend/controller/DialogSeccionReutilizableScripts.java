package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.ScriptSeccionReutilizable;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.types.TypeScriptSeccionReutilizable;
import es.caib.sistrages.core.api.service.SeccionReutilizableService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogSeccionReutilizableScripts extends DialogControllerBase {

	/** Id elemento a tratar. */
	private String id;

	/** Enlace servicio. */
	@Inject
	private SeccionReutilizableService seccionReutService;

	/** Datos elemento. */
	private SeccionReutilizable data;


	/** Campos. **/
	private List<ScriptSeccionReutilizable> campos;

	/** Campos en formato JSON. **/
	private String iCampos;

	/** Ambito. **/
	private String ambito;

	/** Area **/
	private String area;

	private ScriptSeccionReutilizable valorSeleccionado;

	/**
	 * Inicialización.
	 */
	public void init() {

		campos = (List<ScriptSeccionReutilizable>) UtilJSF.getSessionBean().getMochilaDatos().get(Constantes.CLAVE_MOCHILA_SCRIPT);
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		if (campos == null || campos.isEmpty()) {
			ScriptSeccionReutilizable scriptCargaInicial = new ScriptSeccionReutilizable();
			scriptCargaInicial.setIdSeccionReutilizable(Long.valueOf(id));
			scriptCargaInicial.setTipoScript(TypeScriptSeccionReutilizable.CARGA_DATOS_INICIAL);
			campos.add(scriptCargaInicial);
		}
	}

	/**
	 * Visualiza el script.
	 *
	 * @return
	 */
	public void verScript(ScriptSeccionReutilizable scriptSeleccionado) {

		valorSeleccionado = scriptSeleccionado;

		// Muestra dialogo
		final Map<String, String> maps = new HashMap<>();
		maps.put(TypeParametroVentana.TIPO_SCRIPT_SECCION_REUTILIZABLE.toString(),
				UtilJSON.toJSON(valorSeleccionado.getTipoScript()));
		SeccionReutilizable seccion = seccionReutService.getSeccionReutilizable(valorSeleccionado.getIdSeccionReutilizable());
		maps.put(TypeParametroVentana.FORMULARIO_ACTUAL.toString(), seccion.getIdFormularioAsociado().toString());
		maps.put(TypeParametroVentana.SECCION_REUTILIZABLE.toString(), seccion.getIdentificador());
		maps.put(TypeParametroVentana.PARAMETRO_DISENYO.toString(), TypeParametroVentana.PARAMETRO_DISENYO_SECCION.toString());
	/*	maps.put(TypeParametroVentana.FORM_INTERNO_ACTUAL.toString(), this.id);
		maps.put(TypeParametroVentana.TRAMITEVERSION.toString(), idTramiteVersion);
*/
		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(valorSeleccionado.getScript()));

		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.valueOf(modoAcceso), maps, true, 700);

	}

	/**
	 * Retorno dialogo del script.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoScript(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				if (campos != null) {
					for(ScriptSeccionReutilizable scriptReutilizable : campos) {
						if (scriptReutilizable.getTipoScript() == valorSeleccionado.getTipoScript()) {
							Script script = (Script) respuesta.getResult();
							scriptReutilizable.setScript(script);
							break;
						}
					}
				}
				break;
			default:
				break;
			}
		}
	}
	/** Falta el return **/


	/**
	 * Aceptar.
	 */
	public void aceptar() {

		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(false);
		result.setResult(campos);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Aceptar.
	 */
	public void cancelar() {

		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
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
	public SeccionReutilizable getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final SeccionReutilizable data) {
		this.data = data;
	}

	/**
	 * @return the valorSeleccionado
	 */
	public ScriptSeccionReutilizable getValorSeleccionado() {
		return valorSeleccionado;
	}

	/**
	 * @param valorSeleccionado the valorSeleccionado to set
	 */
	public void setValorSeleccionado(final ScriptSeccionReutilizable valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
	}

	/**
	 * @return the campos
	 */
	public List<ScriptSeccionReutilizable> getCampos() {
		return campos;
	}

	/**
	 * @param campos the campos to set
	 */
	public void setCampos(final List<ScriptSeccionReutilizable> campos) {
		this.campos = campos;
	}

	/**
	 * @return the iCampos
	 */
	public String getiCampos() {
		return iCampos;
	}

	/**
	 * @param iCampos the iCampos to set
	 */
	public void setiCampos(final String iCampos) {
		this.iCampos = iCampos;
	}

	/**
	 * @return the ambito
	 */
	public String getAmbito() {
		return ambito;
	}

	/**
	 * @param ambito the ambito to set
	 */
	public void setAmbito(final String ambito) {
		this.ambito = ambito;
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
	public void setArea(final String area) {
		this.area = area;
	}

}

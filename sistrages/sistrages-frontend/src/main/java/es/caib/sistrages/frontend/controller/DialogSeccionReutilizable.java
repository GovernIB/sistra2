package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.exception.FrontException;
import es.caib.sistrages.core.api.model.ScriptSeccionReutilizable;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.SeccionReutilizableTramite;
import es.caib.sistrages.core.api.model.types.TypeScriptSeccionReutilizable;
import es.caib.sistrages.core.api.service.SeccionReutilizableService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n formulario1.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogSeccionReutilizable extends DialogControllerBase {

	/** Secciones Reutilizables service. */
	@Inject
	private SeccionReutilizableService seccionService;

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Data. **/
	private SeccionReutilizable data;

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

	/** Campos. **/
	private List<ScriptSeccionReutilizable> scripts;

	/** Indica si antes estaba activo **/
	private boolean activoOld = false;

	/** Init. **/
	public void init() {

		if (this.getModoAcceso().equals(TypeModoAcceso.ALTA.toString())) {
			data = new SeccionReutilizable();
			data.setBloqueado(true);
			data.setBloqueadoUsuario(UtilJSF.getSessionBean().getUserName());
			data.setActivado(true);
			activoOld = false;
		} else {
			data = seccionService.getSeccionReutilizable(Long.valueOf(id));
			activoOld  = data.isActivado();
			scripts = seccionService.getScriptsByIdSeccionReutilizable(Long.valueOf(id));
		}

		if (scripts == null || scripts.isEmpty()) {
			inicializarScripts();
		}

	}

	private void inicializarScripts() {
		scripts = new ArrayList<>();
		ScriptSeccionReutilizable scriptCargaInicial = ScriptSeccionReutilizable.createInstance(data.getCodigo(), TypeScriptSeccionReutilizable.CARGA_DATOS_INICIAL);
		scripts.add(scriptCargaInicial);
	}

	/**
	 * Abrir script
	 */
	public void abrirScript() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.data.getCodigo()));
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_SCRIPT, this.scripts);

		UtilJSF.openDialog(DialogSeccionReutilizableScripts.class, TypeModoAcceso.valueOf(modoAcceso), params, true, 770, 400);

	}

	public String getCssScripts() {
		if (scripts == null || scripts.isEmpty()) {
			return "";
		}
		for(ScriptSeccionReutilizable script : scripts) {
			if (script.getScript() != null && script.getScript().getContenido() != null && !script.getScript().getContenido().isEmpty()) {
				return "scriptRelleno";
			}
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public void returnDialogoScript(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			scripts = (List<ScriptSeccionReutilizable>) respuesta.getResult();
		}
	}

	/**
	 * Abre dialogo de tramites.
	 *
	 */
	public void tramites() {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.data.getCodigo()));
		UtilJSF.openDialog(DialogSeccionReutilizableTramites.class, TypeModoAcceso.CONSULTA, params, true, 770, 400);
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		// verificamos precondiciones
		if (!verificarGuardar()) {
			return;
		}

		boolean isAlta;
		if (data.getCodigo() == null) {
			data = seccionService.addSeccion(UtilJSF.getIdEntidad(), data, UtilJSF.getSessionBean().getUserName());
			this.modoAcceso = TypeModoAcceso.EDICION.toString();
			isAlta = true;
		} else {
			isAlta = false;
			if (activoOld && !data.isActivado()) {
				List<SeccionReutilizableTramite> tramites = tramiteService.getTramiteVersionBySeccionReutilizable(Long.valueOf(id));
				if (tramites.isEmpty()) {
					seccionService.updateSeccionReutilizable(data, scripts);
				} else {
					final RequestContext contextReq = RequestContext.getCurrentInstance();
					contextReq.execute("PF('confirmationButton').jq.click();");
					UtilJSF.doValidationFailed();
					return;
				}
			} else {
				seccionService.updateSeccionReutilizable(data, scripts);
			}
		}

		//Solo se cierra la ventana si es alta.
		if (isAlta) {
			inicializarScripts();
			String message =  UtilJSF.getLiteral("info.alta.ok") ;
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
		} else {
			// 	Retornamos resultado
			final DialogResult result = new DialogResult();
			result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
			result.setResult(data);
			UtilJSF.closeDialog(result);

		}
	}

	public void confirmacionCambios() {

		//Guardamos cambios
		seccionService.updateSeccionReutilizable(data, scripts);

		// 	Retornamos resultado
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
		UtilJSF.openHelp("dialogSeccionReutilizable");
	}


	/**
	 * Abre un di&aacute;logo para editar los datos.
	 */
	public void editarDisenyo() {
		if (data.getCodigo() == null) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("dialogSeccionesReutilizables.editarDisenyoSinCrear"));
		} else {
			final Map<String, String> params = new HashMap<>();
			if (this.data.getIdFormularioAsociado() == null) {
				throw new FrontException("No existe diseño formulario");
			}
			params.put(TypeParametroVentana.ID.toString(), this.data.getIdFormularioAsociado().toString());
			params.put(TypeParametroVentana.SECCION.toString(), String.valueOf(data.getCodigo()));
			params.put(TypeParametroVentana.SECCION_IDENTIFICADOR.toString(), String.valueOf(data.getIdentificador()));
			params.put(TypeParametroVentana.PARAMETRO_DISENYO.toString(), TypeParametroVentana.PARAMETRO_DISENYO_SECCION.toString());

			Integer width = UtilJSF.getSessionBean().getWidth();
			Integer height = UtilJSF.getSessionBean().getHeight() - 60;
			UtilJSF.openDialog(DialogDisenyoFormulario.class, TypeModoAcceso.valueOf(this.modoAcceso), params, true, width, height);
		}
	}


	/**
	 * Abre un di&aacute;logo para editar los datos.
	 */
	public void consultarDisenyo() {
		if (data.getCodigo() == null) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("dialogSeccionesReutilizables.editarDisenyoSinCrear"));
		} else {
			final Map<String, String> params = new HashMap<>();
			if (this.data.getIdFormularioAsociado() == null) {
				throw new FrontException("No existe diseño formulario");
			}
			params.put(TypeParametroVentana.ID.toString(), this.data.getIdFormularioAsociado().toString());
			params.put(TypeParametroVentana.SECCION.toString(), String.valueOf(data.getCodigo()));
			params.put(TypeParametroVentana.PARAMETRO_DISENYO.toString(), TypeParametroVentana.PARAMETRO_DISENYO_SECCION.toString());

			Integer width = UtilJSF.getSessionBean().getWidth();
			Integer height = UtilJSF.getSessionBean().getHeight() - 60;
			UtilJSF.openDialog(DialogDisenyoFormulario.class, TypeModoAcceso.CONSULTA, params, true, width, height);
		}
	}

	/**
	 * Verificar precondiciones al guardar.
	 *
	 * @return true, si se cumplen las todas la condiciones
	 */
	private boolean verificarGuardar() {

		if (data.getIdentificador() == null || data.getIdentificador().isEmpty()) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.obligatorio.dependencia"));
			return false;
		}
		if (data.getDescripcion() == null || data.getDescripcion().isEmpty()) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.obligatorio.dependencia"));
			return false;
		}

		if (data.getCodigo() == null && seccionService.existeIdentificador(UtilJSF.getIdEntidad(), data.getIdentificador())) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("dialogSeccionesReutilizables.identificadorRepetido"));
			return false;
		}
		return true;
	}

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionFormulario1.
	 */
	public DialogSeccionReutilizable() {
		super();
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

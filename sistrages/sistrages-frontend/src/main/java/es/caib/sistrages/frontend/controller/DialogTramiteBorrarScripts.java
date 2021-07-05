package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.comun.ScriptInfo;
import es.caib.sistrages.core.api.service.ScriptService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.DialogResultMessage;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteBorrarScripts extends DialogControllerBase {

	private boolean propiedades;
	private boolean rellenar;
	private boolean formularios;
	private boolean anexo;
	private boolean tasas;
	private boolean registrar;
	private boolean captura;

	/** Script service. */
	@Inject
	private ScriptService scriptService;

	@Inject
	private TramiteService tramiteService;

	/** Scripts para borrar. **/
	private List<ScriptInfo> data;

	/** Id de la version. */
	private String id;

	/** Inicialización. */
	public void init() {
		setPropiedades(true);
		setRellenar(true);
		setAnexo(true);
		setCaptura(true);
		setTasas(true);
		setRegistrar(true);
		setFormularios(true);
	}

	/** Cancelar. */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/** Cancelar. */
	public void aceptar() {
		// eliminar scripts propiedades
		/** Tramite service. */

		tramiteService.borrarScriptsVersion(Long.valueOf(id), propiedades, rellenar, anexo, tasas, registrar, captura);

		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		final DialogResultMessage dm = new DialogResultMessage();
		dm.setNivel(TypeNivelGravedad.INFO);
		dm.setMensaje("Se han borrado los scripts seleccionados");
		result.setMensaje(dm);

		UtilJSF.closeDialog(result);
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));
	}

	public void addCheck() {

	}

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("tramiteScriptDialog");
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

	public boolean isPropiedades() {
		return propiedades;
	}

	public void setPropiedades(final boolean propiedades) {
		this.propiedades = propiedades;
	}

	public boolean isRellenar() {
		return rellenar;
	}

	public void setRellenar(final boolean rellenar) {
		this.rellenar = rellenar;
	}

	public boolean isFormularios() {
		return formularios;
	}

	public void setFormularios(final boolean formularios) {
		this.formularios = formularios;
	}

	public boolean isAnexo() {
		return anexo;
	}

	public void setAnexo(final boolean anexo) {
		this.anexo = anexo;
	}

	public boolean isTasas() {
		return tasas;
	}

	public void setTasas(final boolean tasas) {
		this.tasas = tasas;
	}

	public boolean isRegistrar() {
		return registrar;
	}

	public void setRegistrar(final boolean registrar) {
		this.registrar = registrar;
	}

	public boolean isCaptura() {
		return captura;
	}

	public void setCaptura(final boolean captura) {
		this.captura = captura;
	}

}

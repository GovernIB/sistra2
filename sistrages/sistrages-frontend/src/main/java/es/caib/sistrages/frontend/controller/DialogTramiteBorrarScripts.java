package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.DialogResultMessage;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteBorrarScripts extends DialogControllerBase {

	private String tramite;
	private String version;
	private boolean propiedades;
	private boolean rellenar;
	private boolean anexo;
	private boolean tasas;
	private boolean registrar;
	private boolean captura;

	@Inject
	private TramiteService tramiteService;

	/** Id de la version. */
	private String id;

	private String portapapeles;

	/** Inicialización. */
	public void init() {
		setPropiedades(true);
		setRellenar(true);
		setAnexo(true);
		setCaptura(true);
		setTasas(true);
		setRegistrar(true);
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

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("tramiteBorrarScriptDialog");
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

	/**
	 * @return the tramite
	 */
	public String getTramite() {
		return tramite;
	}

	/**
	 * @param tramite the tramite to set
	 */
	public void setTramite(String tramite) {
		this.tramite = tramite;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

}

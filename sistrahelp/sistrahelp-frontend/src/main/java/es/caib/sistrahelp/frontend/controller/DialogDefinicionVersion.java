package es.caib.sistrahelp.frontend.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.comun.Constantes;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrahelp.core.api.service.ConfiguracionService;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogDefinicionVersion extends DialogControllerBase {
	@Inject
	private ConfiguracionService systemService;

	/** url iframe */
	private String url;

	/** id trámite a buscar */
	private String tramite;

	/** version trámite a buscar */
	private String version;

	private String mensaje;

	/**
	 * Inicialización.
	 */
	public void init() {
		url = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAGES_VIEW_URL)
				+ "/viewDefinicionVersionParams.xhtml?TRAMITE=" + tramite + "&VERSION=" + version;
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Cancelar.
	 */
	public void closeDialog() {
		final DialogResult result = new DialogResult();
		result.setResult(mensaje);
		result.setCanceled(false);
		UtilJSF.closeDialog(result);
	}

	/**
	 * @return the url
	 */
	public final String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public final void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the tramite
	 */
	public final String getTramite() {
		return tramite;
	}

	/**
	 * @param tramite the tramite to set
	 */
	public final void setTramite(String tramite) {
		this.tramite = tramite;
	}

	/**
	 * @return the version
	 */
	public final String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public final void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the mensaje
	 */
	public final String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public final void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}

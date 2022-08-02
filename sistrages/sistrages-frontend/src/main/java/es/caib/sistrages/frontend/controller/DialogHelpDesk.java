package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Rol;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.RolService;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogHelpDesk extends DialogControllerBase {
	@Inject
	private SystemService systemService;

	/** url iframe */
	private String url;

	/** id trámite a buscar */
	private String tramite;

	/** version trámite a buscar */
	private String version;

	/**
	 * Inicialización.
	 */
	public void init() {

		url = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAHELP_VIEW_URL.toString())
				+ "/viewAuditoriaTramitesParams.xhtml?TRAMITE=" + tramite + "&VERSION=" + version;
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("rolesPermisosDialog");
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

}

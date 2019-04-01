package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.CatalogoPluginException;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.ICatalogoProcedimientosPlugin;
import es.caib.sistrages.core.api.model.DominioTramite;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.service.ComponenteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteProcedimientos extends DialogControllerBase {

	/** Id elemento a tratar. */
	private String id;

	/** Id elemento a tratar. */
	private String version;

	/** Componente service. */
	@Inject
	private ComponenteService componenteService;

	/** Procedimientos **/
	private List<DefinicionTramiteCP> tramites;

	/** Campos. **/
	private List<DominioTramite> campos;

	/** Campos en formato JSON. **/
	private String iCampos;

	/** Ambito. **/
	private String ambito;

	/** Area **/
	private String area;

	/** Plugin catalogo. **/
	private ICatalogoProcedimientosPlugin iplugin;

	/**
	 * Inicialización.
	 *
	 * @throws CatalogoPluginException
	 */
	public void init() throws CatalogoPluginException {

		iplugin = (ICatalogoProcedimientosPlugin) componenteService
				.obtenerPluginEntidad(TypePlugin.CATALOGO_PROCEDIMIENTOS, UtilJSF.getIdEntidad());
		Integer intVersion = null;
		if (this.version != null && !this.version.isEmpty()) {
			intVersion = Integer.parseInt(this.version);
		}
		setTramites(iplugin.obtenerTramites(id, intVersion, UtilJSF.getIdioma().toString()));
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

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
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the campos
	 */
	public List<DominioTramite> getCampos() {
		return campos;
	}

	/**
	 * @param campos
	 *            the campos to set
	 */
	public void setCampos(final List<DominioTramite> campos) {
		this.campos = campos;
	}

	/**
	 * @return the iCampos
	 */
	public String getiCampos() {
		return iCampos;
	}

	/**
	 * @param iCampos
	 *            the iCampos to set
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
	 * @param ambito
	 *            the ambito to set
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
	 * @param area
	 *            the area to set
	 */
	public void setArea(final String area) {
		this.area = area;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(final String version) {
		this.version = version;
	}

	/**
	 * @return the tramites
	 */
	public List<DefinicionTramiteCP> getTramites() {
		return tramites;
	}

	/**
	 * @param tramites
	 *            the tramites to set
	 */
	public void setTramites(final List<DefinicionTramiteCP> tramites) {
		this.tramites = tramites;
	}

}

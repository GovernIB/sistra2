package es.caib.sistrages.frontend.controller;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeImportarAccion;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteVersionImportarTR extends DialogControllerBase {

	/**
	 * Log.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogTramiteVersionImportarTR.class);

	/** Servicio. */
	@Inject
	private TramiteService tramiteService;

	/** Id elemento a tratar. */
	private String id;

	/** Id elemento a tratar. */
	private String idArea;

	/** Dato. **/
	private String dato;

	/** Tramite version. */
	private Tramite data;

	/** Tramite version. */
	private Tramite dataActual;

	/**
	 * Inicialización.
	 */
	public void init() {
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		final String json = (String) mochilaDatos.get(Constantes.CLAVE_MOCHILA_TRAMITE);
		data = (Tramite) UtilJSON.fromJSON(json, Tramite.class);

		dataActual = tramiteService.getTramiteByIdentificador(data.getIdentificador(), Long.valueOf(idArea));
	}

	/**
	 * Cancelar.
	 */
	public void mantener() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(false);
		result.setResult(TypeImportarAccion.MANTENER);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Cancelar.
	 */
	public void remplazar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(false);
		result.setResult(TypeImportarAccion.REEMPLAZAR);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		result.setResult(TypeImportarAccion.PENDIENTE);
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
	 * @return the data
	 */
	public Tramite getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final Tramite data) {
		this.data = data;
	}

	/**
	 * @return the dato
	 */
	public String getDato() {
		return dato;
	}

	/**
	 * @param dato
	 *            the dato to set
	 */
	public void setDato(final String dato) {
		this.dato = dato;
	}

	/**
	 * @return the idArea
	 */
	public String getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea
	 *            the idArea to set
	 */
	public void setIdArea(final String idArea) {
		this.idArea = idArea;
	}

	/**
	 * @return the dataActual
	 */
	public Tramite getDataActual() {
		return dataActual;
	}

	/**
	 * @param dataActual
	 *            the dataActual to set
	 */
	public void setDataActual(final Tramite dataActual) {
		this.dataActual = dataActual;
	}

}

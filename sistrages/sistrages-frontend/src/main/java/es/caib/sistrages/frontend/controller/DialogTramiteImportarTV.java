package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistrages.core.api.model.comun.FilaImportarTramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteImportarTV extends DialogControllerBase {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogTramiteImportarTV.class);

	/** FilaImportar. */
	private FilaImportarTramiteVersion data;

	/** Accion. **/
	private String accion;

	/**
	 * Inicialización.
	 */
	public void init() {
		setData((FilaImportarTramiteVersion) UtilJSF.getSessionBean().getMochilaDatos()
				.get(Constantes.CLAVE_MOCHILA_IMPORTAR));
		setAccion(data.getAccion().toString());

	}

	/**
	 * Guardar.
	 */
	public void guardar() {

		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(false);
		data.setAccion(TypeImportarAccion.fromString(accion));
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
	 * @return the data
	 */
	public FilaImportarTramiteVersion getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final FilaImportarTramiteVersion data) {
		this.data = data;
	}

	/**
	 * @return the accion
	 */
	public String getAccion() {
		return accion;
	}

	/**
	 * @param accion
	 *            the accion to set
	 */
	public void setAccion(final String accion) {
		this.accion = accion;
	}

}

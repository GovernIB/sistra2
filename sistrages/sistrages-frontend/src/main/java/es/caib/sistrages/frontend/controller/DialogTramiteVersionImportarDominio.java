package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.FilaImportar;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeImportarAccion;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteVersionImportarDominio extends DialogControllerBase {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogTramiteVersionImportarDominio.class);

	/** Servicio. */
	@Inject
	private DominioService dominioService;

	/** Dominio. */
	private FilaImportar data;

	/**
	 * Inicialización.
	 */
	public void init() {
		data = (FilaImportar) UtilJSF.getSessionBean().getMochilaDatos().get(Constantes.CLAVE_MOCHILA_IMPORTAR);

	}

	/**
	 * Guardar.
	 */
	public void guardar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(false);
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
		result.setResult(TypeImportarAccion.PENDIENTE);
		UtilJSF.closeDialog(result);
	}

	/**
	 * @return the data
	 */
	public FilaImportar getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final FilaImportar data) {
		this.data = data;
	}

}

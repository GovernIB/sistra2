package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistrages.core.api.model.comun.FilaImportarArea;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteImportarAR extends DialogControllerBase {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogTramiteImportarAR.class);

	/** Fila importar. */
	private FilaImportarArea data;

	/** Mensaje. **/
	private String mensaje;

	/**
	 * Inicialización.
	 */
	public void init() {
		data = (FilaImportarArea) UtilJSF.getSessionBean().getMochilaDatos().get(Constantes.CLAVE_MOCHILA_IMPORTAR);
		if (data.getEstado() == TypeImportarEstado.EXISTE) {
			setMensaje(UtilJSF.getLiteral("dialogTramiteImportarAR.estado.existedistinto"));
		} else {
			setMensaje(UtilJSF.getLiteral("dialogTramiteImportarAR.estado.noexiste"));
		}
	}

	/** Consultar. **/
	public void consultarArea() {

		if (this.data.getAreaActual() != null) {
			// Muestra dialogo
			final Map<String, String> params = new HashMap<>();
			params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.data.getAreaActual().getCodigo()));
			UtilJSF.openDialog(DialogArea.class, TypeModoAcceso.CONSULTA, params, true, 520, 160);
		}
	}

	/**
	 * Guardar resultado.
	 */
	public void guardar() {

		if (data.getAreaResultado().isEmpty()) {
			addMessageContext(TypeNivelGravedad.WARNING, "Rellena el valor");
			return;
		}

		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR, this.data);

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
		UtilJSF.closeDialog(result);
	}

	/**
	 * @return the data
	 */
	public FilaImportarArea getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final FilaImportarArea data) {
		this.data = data;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(final String mensaje) {
		this.mensaje = mensaje;
	}

}

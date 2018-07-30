package es.caib.sistrages.frontend.controller;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.FilaImportarTramite;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeImportarEstado;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteImportarTR extends DialogControllerBase {

	/**
	 * Log.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogTramiteImportarTR.class);

	/** Fila Importar. */
	private FilaImportarTramite data;

	/** Mensaje. **/
	private String mensaje;

	/**
	 * Inicialización.
	 */
	public void init() {
		data = (FilaImportarTramite) UtilJSF.getSessionBean().getMochilaDatos().get(Constantes.CLAVE_MOCHILA_IMPORTAR);
		if (data.getEstado() == TypeImportarEstado.EXISTE) {
			setMensaje(UtilJSF.getLiteral("dialogTramiteImportarTR.estado.existedistinto"));
		} else {
			setMensaje(UtilJSF.getLiteral("dialogTramiteImportarTR.estado.noexiste"));
		}
	}

	/**
	 * Guardar.
	 */
	public void guardar() {

		if (data.getTramiteResultado().isEmpty()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "Rellena el valor");
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
	 * @param data
	 *            the data to set
	 */
	public void setData(final FilaImportarTramite data) {
		this.data = data;
	}

	/**
	 * @return the data
	 */
	public FilaImportarTramite getData() {
		return data;
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

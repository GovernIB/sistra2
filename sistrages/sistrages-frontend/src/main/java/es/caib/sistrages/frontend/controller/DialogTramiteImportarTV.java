package es.caib.sistrages.frontend.controller;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.FilaImportarTramiteVersion;
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

	/** Mensaje. **/
	private String mensaje;

	/** Para el paso de registro. **/
	private String oficina;

	/** Para el paso de registro. **/
	private String libro;

	/** Para el paso de registro. **/
	private String tipo;

	/**
	 * Inicialización.
	 */
	public void init() {
		setData((FilaImportarTramiteVersion) UtilJSF.getSessionBean().getMochilaDatos()
				.get(Constantes.CLAVE_MOCHILA_IMPORTAR));

		if (data.getTramiteVersionActual() == null) {

			setMensaje(UtilJSF.getLiteral("dialogTramiteImportarTV.estado.noexiste"));

		} else if (data.getTramiteVersionActual().getNumeroVersion() < data.getTramiteVersion().getNumeroVersion()) {

			setMensaje(UtilJSF.getLiteral("dialogTramiteImportarTV.estado.existemayor"));

		} else if (data.getTramiteVersionActual().getNumeroVersion() == data.getTramiteVersion().getNumeroVersion()) {

			setMensaje(UtilJSF.getLiteral("dialogTramiteImportarTV.estado.existeigual"));

		} else {

			setMensaje(UtilJSF.getLiteral("dialogTramiteImportarTV.estado.existemenor"));
		}

	}

	/**
	 * Guardar.
	 */
	public void guardar() {

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

	/**
	 * @return the oficina
	 */
	public String getOficina() {
		return oficina;
	}

	/**
	 * @param oficina
	 *            the oficina to set
	 */
	public void setOficina(final String oficina) {
		this.oficina = oficina;
	}

	/**
	 * @return the libro
	 */
	public String getLibro() {
		return libro;
	}

	/**
	 * @param libro
	 *            the libro to set
	 */
	public void setLibro(final String libro) {
		this.libro = libro;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

}

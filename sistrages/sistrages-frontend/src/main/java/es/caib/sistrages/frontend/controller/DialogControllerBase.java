package es.caib.sistrages.frontend.controller;

import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Clase de las que heredan los Dialog.
 *
 * @author Indra
 *
 */
public abstract class DialogControllerBase {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogControllerBase.class);

	/** Modo acceso ventana. */
	protected String modoAcceso;

	/**
	 * Devuelve logger.
	 *
	 * @return logger
	 */
	protected Logger getLogger() {
		return LOGGER;
	}

	/**
	 * Indica si el dialogo se abre en modo alta.
	 *
	 * @return boolean
	 */
	public boolean isAlta() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		return (modo == TypeModoAcceso.ALTA);
	}

	/**
	 * Indica si el dialogo se abre en modo edicion.
	 *
	 * @return boolean
	 */
	public boolean isEdicion() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		return (modo == TypeModoAcceso.EDICION);
	}

	/**
	 * Indica si el dialogo se abre en modo consulta.
	 *
	 * @return boolean
	 */
	public boolean isConsulta() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		return (modo == TypeModoAcceso.CONSULTA);
	}

	/**
	 * Indica si el dialogo no se abre en modo consulta.
	 *
	 * @return boolean
	 */
	public boolean isNotConsulta() {
		return !isConsulta();
	}

	/**
	 * Obtiene modo acceso.
	 *
	 * @return modo acceso
	 */
	public String getModoAcceso() {
		return modoAcceso;
	}

	/**
	 * Establece modo acceso.
	 *
	 * @param modoAcceso
	 *            modo acceso
	 */
	public void setModoAcceso(final String modoAcceso) {
		this.modoAcceso = modoAcceso;
	}

	/**
	 * Ayudar.
	 */
	public void ayudar() {
		addMessageContext(TypeNivelGravedad.INFO, "Ayuda");
	}

	/**
	 * Sirve para actualizar el style del script, por si tiene que marcarse de
	 * colorp orque tiene valor.
	 *
	 * @param script
	 * @return
	 */
	public String getColorScript(final Script script) {
		String className = "";
		if (script != null) {
			className = "scriptRelleno";
		}
		return className;
	}

	/**
	 * Muestra el mensaje. Se encarga de ver si debe mostrarse por el growl del
	 * dialog o del padre.
	 *
	 * @param nivel
	 * @param title
	 * @param message
	 */
	public void addMessageContext(final TypeNivelGravedad nivel, final String title, final String message) {
		if (Constantes.GROWL_PADRE) {
			showMessagePadre(nivel, title, message);
		} else {
			UtilJSF.addMessageContext(nivel, title, message);
		}
	}

	/**
	 * Muestra el mensaje. Se encarga de ver si debe mostrarse por el growl del
	 * dialog o del padre.
	 *
	 * @param nivel
	 * @param title
	 * @param message
	 */
	public void addMessageContext(final TypeNivelGravedad nivel, final String message) {
		if (Constantes.GROWL_PADRE) {
			showMessagePadre(nivel, null, message);
		} else {
			UtilJSF.addMessageContext(nivel, message);
		}
	}

	/**
	 * Añade mensaje al contexto para que lo trate la aplicación (growl,
	 * messages,...).
	 *
	 * @param nivel
	 *            Nivel gravedad
	 * @param message
	 *            Mensaje
	 * @param validationFailed
	 *            añade la marca de error de validacion
	 */
	public void addMessageContext(final TypeNivelGravedad nivel, final String message, final boolean validationFailed) {
		addMessageContext(nivel, message, message, validationFailed);
	}

	/**
	 * Añade mensaje al contexto para que lo trate la aplicación (growl,
	 * messages,...).
	 *
	 * @param nivel
	 *            Nivel gravedad
	 * @param message
	 *            Mensaje
	 * @param detail
	 *            Detalle
	 * @param validationFailed
	 *            añade la marca de error de validacion
	 */
	public void addMessageContext(final TypeNivelGravedad nivel, final String message, final String detail,
			final boolean validationFailed) {

		if (Constantes.GROWL_PADRE) {
			showMessagePadre(nivel, message, detail);
			if (validationFailed) {
				FacesContext.getCurrentInstance().validationFailed();
			}

		} else {
			UtilJSF.addMessageContext(nivel, message, detail);

			if (validationFailed) {
				FacesContext.getCurrentInstance().validationFailed();
			}
		}
	}

	/**
	 * Método privado que envia el growl al growl padre donde la cabecera.
	 *
	 * @param nivel
	 * @param title
	 * @param message
	 */
	private void showMessagePadre(final TypeNivelGravedad nivel, final String title, final String message) {
		UtilJSF.getSessionBean().getMochilaDatos().put(Constantes.CLAVE_MOCHILA_ERRORES_MSG, message);
		UtilJSF.getSessionBean().getMochilaDatos().put(Constantes.CLAVE_MOCHILA_ERRORES_NVL, nivel);
		if (title != null) {
			UtilJSF.getSessionBean().getMochilaDatos().put(Constantes.CLAVE_MOCHILA_ERRORES_TLE, title);
		}
		final RequestContext context = RequestContext.getCurrentInstance();
		final String evento = "window.parent.document.getElementById('formHeader:hiddenGrowl').click()";
		context.execute(evento);
	}
}

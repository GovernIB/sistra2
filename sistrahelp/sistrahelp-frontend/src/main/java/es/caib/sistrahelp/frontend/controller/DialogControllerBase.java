package es.caib.sistrahelp.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;

/**
 * Clase de las que heredan los Dialog.
 *
 * @author Indra
 *
 */
public abstract class DialogControllerBase {

	/**
	 * Log.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogControllerBase.class);

	/**
	 * Modo acceso ventana.
	 */
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
	 * @param modoAcceso modo acceso
	 */
	public void setModoAcceso(final String modoAcceso) {
		this.modoAcceso = modoAcceso;
	}

	/**
	 * Ayudar.
	 */
	public void ayudar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Ayuda");
	}

}

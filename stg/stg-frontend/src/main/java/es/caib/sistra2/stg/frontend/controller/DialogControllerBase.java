package es.caib.sistra2.stg.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistra2.stg.frontend.model.types.TypeModoAcceso;

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
	 * @return logger
	 */
	protected Logger getLogger() {
		return LOGGER;
	}

	/**
	 * Indica si el dialogo se abre en modo alta.
	 * @return boolean
	 */
	public boolean isAlta() {
		TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		return (modo == TypeModoAcceso.ALTA);
	}

	/**
	 * Indica si el dialogo se abre en modo edicion.
	 * @return boolean
	 */
	public boolean isEdicion() {
		TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		return (modo == TypeModoAcceso.EDICION);
	}

	/**
	 * Indica si el dialogo se abre en modo consulta.
	 * @return boolean
	 */
	public boolean isConsulta() {
		TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		return (modo == TypeModoAcceso.CONSULTA);
	}

	/**
	 * Obtiene modo acceso.
	 * @return modo acceso
	 */
	public String getModoAcceso() {
		return modoAcceso;
	}

	/**
	 * Establece modo acceso.
	 * @param modoAcceso modo acceso
	 */
	public void setModoAcceso(String modoAcceso) {
		this.modoAcceso = modoAcceso;
	}
}

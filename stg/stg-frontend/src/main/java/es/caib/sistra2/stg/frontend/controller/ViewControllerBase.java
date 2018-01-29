package es.caib.sistra2.stg.frontend.controller;

import javax.faces.bean.ManagedProperty;

import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistra2.stg.frontend.model.types.TypeNivelGravedad;
import es.caib.sistra2.stg.frontend.util.UtilJSF;

/**
 * Clase de las que heredan los View.
 *
 * @author Indra
 *
 */
public abstract class ViewControllerBase {

	/**
	 * Log.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ViewControllerBase.class);

	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean sesion;

	public SessionBean getSesion() {
		return sesion;
	}

	public void setSesion(final SessionBean sesion) {
		this.sesion = sesion;
	}

	public void setTituloPantalla(final String titulo) {
		sesion.setTituloPantalla(titulo);
	}

	/**
	 * Devuelve logger.
	 *
	 * @return logger
	 */
	protected final Logger getLogger() {
		return LOGGER;
	}

	/**
	 * Ventana principal de ayudar.
	 * @param event resultado
	 */
	public void ayudar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Ayuda");
	}

}

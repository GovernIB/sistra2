package es.caib.sistrahelp.core.api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.caib.sistrahelp.core.api.model.comun.Propiedad;

/**
 * La clase Sesion.
 */

public class Sesion extends ModelApi {

	/**
	 * Constante serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * usuario.
	 */
	private String usuario;

	/**
	 * fecha.
	 */
	private Date fecha;

	/** Propiedades */
	private List<Propiedad> propiedades = new ArrayList<>();

	/**
	 * Obtiene el valor de usuario.
	 *
	 * @return el valor de usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Establece el valor de usuario.
	 *
	 * @param usuario
	 *            el nuevo valor de usuario
	 */
	public void setUsuario(final String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Obtiene el valor de fecha.
	 *
	 * @return el valor de fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Establece el valor de fecha.
	 *
	 * @param fecha
	 *            el nuevo valor de fecha
	 */
	public void setFecha(final Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the propiedades
	 */
	public List<Propiedad> getPropiedades() {
		return propiedades;
	}

	/**
	 * @param propiedades the propiedades to set
	 */
	public void setPropiedades(List<Propiedad> propiedades) {
		this.propiedades = propiedades;
	}

}

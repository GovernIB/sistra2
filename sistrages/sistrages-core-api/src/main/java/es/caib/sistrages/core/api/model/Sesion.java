package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.caib.sistrages.core.api.model.comun.Propiedad;

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
	/**
	 * perfil.
	 */
	private String perfil;

	/**
	 * idioma.
	 */
	private String idioma;

	/**
	 * codigo Entidad.
	 */
	private Long entidad;

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
	 * Obtiene el valor de perfil.
	 *
	 * @return el valor de perfil
	 */
	public String getPerfil() {
		return perfil;
	}

	/**
	 * Establece el valor de perfil.
	 *
	 * @param perfil
	 *            el nuevo valor de perfil
	 */
	public void setPerfil(final String perfil) {
		this.perfil = perfil;
	}

	/**
	 * Obtiene el valor de idioma.
	 *
	 * @return el valor de idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Establece el valor de idioma.
	 *
	 * @param idioma
	 *            el nuevo valor de idioma
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Obtiene el valor de entidad.
	 *
	 * @return el valor de entidad
	 */
	public Long getEntidad() {
		return entidad;
	}

	/**
	 * Establece el valor de entidad.
	 *
	 * @param entidad
	 *            el nuevo valor de entidad
	 */
	public void setEntidad(final Long entidad) {
		this.entidad = entidad;
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

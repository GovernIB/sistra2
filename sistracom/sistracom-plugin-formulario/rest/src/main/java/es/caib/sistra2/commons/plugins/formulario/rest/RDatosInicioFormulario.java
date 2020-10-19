package es.caib.sistra2.commons.plugins.formulario.rest;

import java.io.Serializable;
import java.util.List;

/**
 * Datos inicio formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class RDatosInicioFormulario implements Serializable {

	/** Entidad. */
	private String entidad;

	/** Id sesión formulario en Sistra2. */
	private String idSesionFormulario;

	/** Id formulario externo. */
	private String idFormularioExterno;

	/** Idioma. */
	private String idioma;

	/** Usuario. */
	private RUsuarioInfo usuario;

	/** Datos actuales formulario (B64). */
	private String xmlDatosActuales;

	/** Parametros apertura formulario. */
	private List<RParametroFormulario> parametrosApertura;

	/**
	 * Url callback (se deberá invocar a la url de callback pasando parámetro
	 * "ticket" por POST de un solo uso y con timeout).
	 */
	private String urlCallback;

	/**
	 * Método de acceso a idFormularioExterno.
	 *
	 * @return idFormularioExterno
	 */
	public String getIdFormularioExterno() {
		return idFormularioExterno;
	}

	/**
	 * Método para establecer idFormularioExterno.
	 *
	 * @param idFormularioExterno
	 *                                idFormularioExterno a establecer
	 */
	public void setIdFormularioExterno(final String idFormularioExterno) {
		this.idFormularioExterno = idFormularioExterno;
	}

	/**
	 * Método de acceso a idioma.
	 *
	 * @return idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Método para establecer idioma.
	 *
	 * @param idioma
	 *                   idioma a establecer
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Método de acceso a usuario.
	 *
	 * @return usuario
	 */
	public RUsuarioInfo getUsuario() {
		return usuario;
	}

	/**
	 * Método para establecer usuario.
	 *
	 * @param usuario
	 *                    usuario a establecer
	 */
	public void setUsuario(final RUsuarioInfo usuario) {
		this.usuario = usuario;
	}

	/**
	 * Método de acceso a parametrosApertura.
	 *
	 * @return parametrosApertura
	 */
	public List<RParametroFormulario> getParametrosApertura() {
		return parametrosApertura;
	}

	/**
	 * Método para establecer parametrosApertura.
	 *
	 * @param parametrosApertura
	 *                               parametrosApertura a establecer
	 */
	public void setParametrosApertura(final List<RParametroFormulario> parametrosApertura) {
		this.parametrosApertura = parametrosApertura;
	}

	/**
	 * Método de acceso a xmlDatosActuales.
	 *
	 * @return xmlDatosActuales
	 */
	public String getXmlDatosActuales() {
		return xmlDatosActuales;
	}

	/**
	 * Método para establecer xmlDatosActuales.
	 *
	 * @param xmlDatosActuales
	 *                             xmlDatosActuales a establecer
	 */
	public void setXmlDatosActuales(final String xmlDatosActuales) {
		this.xmlDatosActuales = xmlDatosActuales;
	}

	/**
	 * Método de acceso a idSesionFormulario.
	 *
	 * @return idSesionFormulario
	 */
	public String getIdSesionFormulario() {
		return idSesionFormulario;
	}

	/**
	 * Método para establecer idSesionFormulario.
	 *
	 * @param idSesionFormulario
	 *                               idSesionFormulario a establecer
	 */
	public void setIdSesionFormulario(final String idSesionFormulario) {
		this.idSesionFormulario = idSesionFormulario;
	}

	/**
	 * Método de acceso a urlCallback.
	 *
	 * @return urlCallback
	 */
	public String getUrlCallback() {
		return urlCallback;
	}

	/**
	 * Método para establecer urlCallback.
	 *
	 * @param urlCallback
	 *                        urlCallback a establecer
	 */
	public void setUrlCallback(final String urlCallback) {
		this.urlCallback = urlCallback;
	}

	/**
	 * Método de acceso a entidad.
	 *
	 * @return entidad
	 */
	public String getEntidad() {
		return entidad;
	}

	/**
	 * Método para establecer entidad.
	 *
	 * @param entidad
	 *                    entidad a establecer
	 */
	public void setEntidad(final String entidad) {
		this.entidad = entidad;
	}

}

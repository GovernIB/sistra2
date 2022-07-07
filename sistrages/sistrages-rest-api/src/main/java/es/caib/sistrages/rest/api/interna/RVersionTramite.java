package es.caib.sistrages.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "RVersionTramite", description = "Descripcion de RVersionTramite")
public class RVersionTramite {

	/** Timestamp recuperacion. */
	@ApiModelProperty(value = "Timestamp recuperacion")
	private String timestamp;

	/** Identificador tramite. */
	@ApiModelProperty(value = "Identificador tramite")
	private String identificador;

	/** Versión trámite. */
	@ApiModelProperty(value = "Versión trámite")
	private int version;

	/** Release trámite. */
	@ApiModelProperty(value = "Release trámite")
	private int release;

	/** Id entidad. */
	@ApiModelProperty(value = "Id entidad")
	private String idEntidad;

	/** Id área. */
	@ApiModelProperty(value = "Id área")
	private String idArea;

	/** Idioma. */
	@ApiModelProperty(value = "Idioma")
	private String idioma;

	/** Tipo trámite: Trámite (T) / Servicio (S). */
	@ApiModelProperty(value = "Tipo trámite: Trámite (T) / Servicio (S)")
	private String tipoTramite;

	/** Tipo flujo: Normalizado (N) / Personalizado (P). */
	@ApiModelProperty(value = "Tipo flujo: Normalizado (N) / Personalizado (P)")
	private String tipoFlujo;

	/** Lista dominios utilizados. */
	@ApiModelProperty(value = "Lista dominios utilizados")
	private List<String> dominios;

	/** Propiedades. */
	@ApiModelProperty(value = "Propiedades")
	private RVersionTramitePropiedades propiedades;

	/** Control acceso. */
	@ApiModelProperty(value = "Control de acceso")
	private RVersionTramiteControlAcceso controlAcceso;

	/** Pasos tramitación. */
	@ApiModelProperty(value = "Pasos tramitación")
	private List<RPasoTramitacion> pasos;

	/**
	 * Método de acceso a idTramite.
	 *
	 * @return idTramite
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Método para establecer idTramite.
	 *
	 * @param idTramite
	 *                      idTramite a establecer
	 */
	public void setIdentificador(final String idTramite) {
		this.identificador = idTramite;
	}

	/**
	 * Método de acceso a version.
	 *
	 * @return version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * Método para establecer version.
	 *
	 * @param version
	 *                    version a establecer
	 */
	public void setVersion(final int version) {
		this.version = version;
	}

	/**
	 * Método de acceso a pasos.
	 *
	 * @return pasos
	 */
	public List<RPasoTramitacion> getPasos() {
		return pasos;
	}

	/**
	 * Método para establecer pasos.
	 *
	 * @param pasos
	 *                  pasos a establecer
	 */
	public void setPasos(final List<RPasoTramitacion> pasos) {
		this.pasos = pasos;
	}

	/**
	 * Método de acceso a propiedades.
	 *
	 * @return propiedades
	 */
	public RVersionTramitePropiedades getPropiedades() {
		return propiedades;
	}

	/**
	 * Método para establecer propiedades.
	 *
	 * @param propiedades
	 *                        propiedades a establecer
	 */
	public void setPropiedades(final RVersionTramitePropiedades propiedades) {
		this.propiedades = propiedades;
	}

	/**
	 * Método de acceso a controlAcceso.
	 *
	 * @return controlAcceso
	 */
	public RVersionTramiteControlAcceso getControlAcceso() {
		return controlAcceso;
	}

	/**
	 * Método para establecer controlAcceso.
	 *
	 * @param controlAcceso
	 *                          controlAcceso a establecer
	 */
	public void setControlAcceso(final RVersionTramiteControlAcceso controlAcceso) {
		this.controlAcceso = controlAcceso;
	}

	/**
	 * Método de acceso a dominios.
	 *
	 * @return dominios
	 */
	public List<String> getDominios() {
		return dominios;
	}

	/**
	 * Método para establecer dominios.
	 *
	 * @param dominios
	 *                     dominios a establecer
	 */
	public void setDominios(final List<String> dominios) {
		this.dominios = dominios;
	}

	/**
	 * Método de acceso a idEntidad.
	 *
	 * @return idEntidad
	 */
	public String getIdEntidad() {
		return idEntidad;
	}

	/**
	 * Método para establecer idEntidad.
	 *
	 * @param idEntidad
	 *                      idEntidad a establecer
	 */
	public void setIdEntidad(final String idEntidad) {
		this.idEntidad = idEntidad;
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
	 * Método de acceso a tipoFlujo.
	 *
	 * @return tipoFlujo
	 */
	public String getTipoFlujo() {
		return tipoFlujo;
	}

	/**
	 * Método para establecer tipoFlujo.
	 *
	 * @param tipoFlujo
	 *                      tipoFlujo a establecer
	 */
	public void setTipoFlujo(final String tipoFlujo) {
		this.tipoFlujo = tipoFlujo;
	}

	/**
	 * Método de acceso a timestamp.
	 *
	 * @return timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * Método para establecer timestamp.
	 *
	 * @param timestamp
	 *                      timestamp a establecer
	 */
	public void setTimestamp(final String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Método de acceso a release.
	 *
	 * @return release
	 */
	public int getRelease() {
		return release;
	}

	/**
	 * Método para establecer release.
	 *
	 * @param release
	 *                    release a establecer
	 */
	public void setRelease(final int release) {
		this.release = release;
	}

	/**
	 * Método de acceso a idArea.
	 *
	 * @return idArea
	 */
	public String getIdArea() {
		return idArea;
	}

	/**
	 * Método para establecer idArea.
	 *
	 * @param idArea
	 *                   idArea a establecer
	 */
	public void setIdArea(final String idArea) {
		this.idArea = idArea;
	}

	/**
	 * Método de acceso a tipoTramite.
	 *
	 * @return tipoTramite
	 */
	public String getTipoTramite() {
		return tipoTramite;
	}

	/**
	 * Método para establecer tipoTramite.
	 *
	 * @param tipoTramite
	 *                        tipoTramite a establecer
	 */
	public void setTipoTramite(final String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

}

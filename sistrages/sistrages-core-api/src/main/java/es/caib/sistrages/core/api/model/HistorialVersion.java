package es.caib.sistrages.core.api.model;

import java.util.Date;

import es.caib.sistrages.core.api.model.types.TypeAccion;

/**
 * Historial Version.
 */
public class HistorialVersion extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. **/
	private Long codigo;

	/** Versión trámite */
	private TramiteVersion tramiteVersion;

	/** Fecha. **/
	private Date fecha;

	/** Tipo acción: C (Creación) / M (Modificación) / I (Importación) */
	private TypeAccion tipoAccion;

	/** Release version. **/
	private int release;

	/** Detalle cambio. **/
	private String detalleCambio;

	/** Usuario. **/
	private String usuario;

	/** Constructor vacio. **/
	public HistorialVersion() {
		// Constructor vacio.
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the tramiteVersion
	 */
	public TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * @param tramiteVersion
	 *            the tramiteVersion to set
	 */
	public void setTramiteVersion(final TramiteVersion tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(final Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the tipoAccion
	 */
	public TypeAccion getTipoAccion() {
		return tipoAccion;
	}

	/**
	 * @param tipoAccion
	 *            the tipoAccion to set
	 */
	public void setTipoAccion(final TypeAccion tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	/**
	 * @return the release
	 */
	public int getRelease() {
		return release;
	}

	/**
	 * @param release
	 *            the release to set
	 */
	public void setRelease(final int release) {
		this.release = release;
	}

	/**
	 * @return the detalleCambio
	 */
	public String getDetalleCambio() {
		return detalleCambio;
	}

	/**
	 * @param detalleCambio
	 *            the detalleCambio to set
	 */
	public void setDetalleCambio(final String detalleCambio) {
		this.detalleCambio = detalleCambio;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(final String usuario) {
		this.usuario = usuario;
	}

}

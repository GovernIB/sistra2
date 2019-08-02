package es.caib.sistrages.core.service.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.caib.sistrages.core.api.model.HistorialVersion;
import es.caib.sistrages.core.api.model.types.TypeAccionHistorial;

/**
 * HistorialVersion
 */
@Entity
@Table(name = "STG_HISVER")
public class JHistorialVersion implements IModelApi {

	private static final long serialVersionUID = 1L;

	/** Código. **/
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_HISVER_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_HISVER_SEQ", sequenceName = "STG_HISVER_SEQ")
	@Column(name = "HVE_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	/** Version tramite. **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HVE_CODVTR", nullable = false)
	private JVersionTramite versionTramite;

	/** Fecha. **/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "HVE_FECHA", nullable = false, length = 7)
	private Date fecha;

	/**
	 * Tipo acción: C (Creación) / B (Bloquear) / D (Desbloquear) / I (Importación)
	 * / E (Exportacion) / L (Creacion por clonado) / O (Objeto de clonado)
	 **/
	@Column(name = "HVE_ACCION", nullable = false, length = 1)
	private String tipoAccion;

	/** Release. **/
	@Column(name = "HVE_RELEAS", nullable = false, precision = 8, scale = 0)
	private int release;

	/** Comentario. **/
	@Column(name = "HVE_CAMBIO", nullable = false)
	private String detalleCambio;

	/** Usuario . **/
	@Column(name = "HVE_USER", nullable = false, length = 100)
	private String usuario;

	/** Huella. **/
	@Column(name = "HVE_HUELLA", length = 20)
	private String huella;

	/**
	 * Constructor.
	 */
	public JHistorialVersion() {
		// Constructor vacio.
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the versionTramite
	 */
	public JVersionTramite getVersionTramite() {
		return versionTramite;
	}

	/**
	 * @param versionTramite the versionTramite to set
	 */
	public void setVersionTramite(final JVersionTramite versionTramite) {
		this.versionTramite = versionTramite;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(final Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the tipoAccion
	 */
	public String getTipoAccion() {
		return tipoAccion;
	}

	/**
	 * @param tipoAccion the tipoAccion to set
	 */
	public void setTipoAccion(final String tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	/**
	 * @return the release
	 */
	public int getRelease() {
		return release;
	}

	/**
	 * @param release the release to set
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
	 * @param detalleCambio the detalleCambio to set
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
	 * @param usuario the usuario to set
	 */
	public void setUsuario(final String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the huella
	 */
	public String getHuella() {
		return huella;
	}

	/**
	 * @param huella the huella to set
	 */
	public final void setHuella(final String huella) {
		this.huella = huella;
	}

	/**
	 * Historial version.
	 *
	 * @return
	 */
	public HistorialVersion toModel() {
		final HistorialVersion historialVersion = new HistorialVersion();
		historialVersion.setCodigo(this.getCodigo());
		historialVersion.setDetalleCambio(this.getDetalleCambio());
		historialVersion.setFecha(this.getFecha());
		historialVersion.setRelease(this.getRelease());
		historialVersion.setTipoAccion(TypeAccionHistorial.fromString(this.getTipoAccion()));
		historialVersion.setUsuario(this.getUsuario());
		historialVersion.setHuella(this.getHuella());
		return historialVersion;
	}

	/**
	 * Return JHistorial version.
	 *
	 * @param historialVersion
	 * @return
	 */
	public static JHistorialVersion fromModel(final HistorialVersion historialVersion) {
		JHistorialVersion jhistorialVersion = null;
		if (historialVersion != null) {
			jhistorialVersion = new JHistorialVersion();
			jhistorialVersion.setCodigo(historialVersion.getCodigo());
			jhistorialVersion.setDetalleCambio(historialVersion.getDetalleCambio());
			jhistorialVersion.setFecha(historialVersion.getFecha());
			jhistorialVersion.setRelease(historialVersion.getRelease());
			jhistorialVersion.setTipoAccion(historialVersion.getTipoAccion().toString());
			jhistorialVersion.setUsuario(historialVersion.getUsuario());
			jhistorialVersion.setHuella(historialVersion.getHuella());
		}
		return jhistorialVersion;
	}
}

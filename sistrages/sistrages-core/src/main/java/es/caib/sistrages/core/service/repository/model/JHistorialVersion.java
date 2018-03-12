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

/**
 * JHistorialVersion
 */
@Entity
@Table(name = "STG_HISVER")
public class JHistorialVersion implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_HISVER_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_HISVER_SEQ", sequenceName = "STG_HISVER_SEQ")
	@Column(name = "HVE_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HVE_CODVTR", nullable = false)
	private JVersionTramite versionTramite;

	@Temporal(TemporalType.DATE)
	@Column(name = "HVE_FECHA", nullable = false, length = 7)
	private Date fecha;

	@Column(name = "HVE_ACCION", nullable = false, length = 1)
	private String tipoAccion;

	@Column(name = "HVE_RELEAS", nullable = false, precision = 8, scale = 0)
	private int release;

	@Column(name = "HVE_CAMBIO", nullable = false)
	private String detalleCambio;

	@Column(name = "HVE_USER", nullable = false, length = 100)
	private String usuario;

	public JHistorialVersion() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

	public JVersionTramite getVersionTramite() {
		return this.versionTramite;
	}

	public void setVersionTramite(final JVersionTramite versionTramite) {
		this.versionTramite = versionTramite;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(final Date fecha) {
		this.fecha = fecha;
	}

	public String getTipoAccion() {
		return this.tipoAccion;
	}

	public void setTipoAccion(final String tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	public int getRelease() {
		return this.release;
	}

	public void setRelease(final int release) {
		this.release = release;
	}

	public String getDetalleCambio() {
		return this.detalleCambio;
	}

	public void setDetalleCambio(final String detalleCambio) {
		this.detalleCambio = detalleCambio;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(final String usuario) {
		this.usuario = usuario;
	}

}

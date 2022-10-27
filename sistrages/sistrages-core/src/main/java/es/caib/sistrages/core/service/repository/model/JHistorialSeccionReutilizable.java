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

import es.caib.sistrages.core.api.model.HistorialSeccionReutilizable;
import es.caib.sistrages.core.api.model.types.TypeAccionHistorial;

/**
 * Historial Seccion reutilizable
 */
@Entity
@Table(name = "STG_HISSRU")
public class JHistorialSeccionReutilizable implements IModelApi {

	private static final long serialVersionUID = 1L;

	/** Código. **/
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_HISSRU_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_HISSRU_SEQ", sequenceName = "STG_HISSRU_SEQ")
	@Column(name = "HSR_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	/** Version tramite. **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HSR_CODSRU", nullable = false)
	private JSeccionReutilizable seccionReutilizable;

	/** Fecha. **/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "HSR_FECHA", nullable = false, length = 7)
	private Date fecha;

	/**
	 * Tipo acción: C (Creación) / B (Bloquear) / D (Desbloquear) / I (Importación)
	 * / E (Exportacion) / L (Creacion por clonado) / O (Objeto de clonado)
	 **/
	@Column(name = "HSR_ACCION", nullable = false, length = 1)
	private String tipoAccion;

	/** Release. **/
	@Column(name = "HSR_RELEAS", nullable = false, precision = 8, scale = 0)
	private int release;

	/** Comentario. **/
	@Column(name = "HSR_CAMBIO", nullable = false)
	private String detalleCambio;

	/** Usuario . **/
	@Column(name = "HSR_USER", nullable = false, length = 100)
	private String usuario;

	/** Huella. **/
	@Column(name = "HSR_HUELLA", length = 20)
	private String huella;

	/**
	 * Constructor.
	 */
	public JHistorialSeccionReutilizable() {
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
	 * @return the seccionReutilizable
	 */
	public JSeccionReutilizable getSeccionReutilizable() {
		return seccionReutilizable;
	}

	/**
	 * @param seccionReutilizable the seccionReutilizable to set
	 */
	public void setSeccionReutilizable(JSeccionReutilizable seccionReutilizable) {
		this.seccionReutilizable = seccionReutilizable;
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
	public void setHuella(final String huella) {
		this.huella = huella;
	}

	/**
	 * Historial seccion reutilizable.
	 *
	 * @return
	 */
	public HistorialSeccionReutilizable toModel() {
		final HistorialSeccionReutilizable historialSeccionReutilizable = new HistorialSeccionReutilizable();
		historialSeccionReutilizable.setCodigo(this.getCodigo());
		historialSeccionReutilizable.setDetalleCambio(this.getDetalleCambio());
		historialSeccionReutilizable.setFecha(this.getFecha());
		historialSeccionReutilizable.setRelease(this.getRelease());
		historialSeccionReutilizable.setTipoAccion(TypeAccionHistorial.fromString(this.getTipoAccion()));
		historialSeccionReutilizable.setUsuario(this.getUsuario());
		historialSeccionReutilizable.setHuella(this.getHuella());
		return historialSeccionReutilizable;
	}

	/**
	 * Return JHistorial seccion reutilizable.
	 *
	 * @param historialSeccion
	 * @return
	 */
	public static JHistorialSeccionReutilizable fromModel(final HistorialSeccionReutilizable historialSeccion) {
		JHistorialSeccionReutilizable jhistorialReutilizable = null;
		if (historialSeccion != null) {
			jhistorialReutilizable = new JHistorialSeccionReutilizable();
			jhistorialReutilizable.setCodigo(historialSeccion.getCodigo());
			jhistorialReutilizable.setDetalleCambio(historialSeccion.getDetalleCambio());
			jhistorialReutilizable.setFecha(historialSeccion.getFecha());
			jhistorialReutilizable.setRelease(historialSeccion.getRelease());
			jhistorialReutilizable.setTipoAccion(historialSeccion.getTipoAccion().toString());
			jhistorialReutilizable.setUsuario(historialSeccion.getUsuario());
			jhistorialReutilizable.setHuella(historialSeccion.getHuella());
		}
		return jhistorialReutilizable;
	}
}

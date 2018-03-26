package es.caib.sistrages.core.service.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * JFicheroExterno
 */
@Entity
@Table(name = "STG_FICEXT")
public class JFicheroExterno implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FIE_REFDOC", unique = true, nullable = false, length = 1000)
	private String referenciaExterna;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FIE_REFFEC", nullable = false)
	private Date fechaReferencia;

	@Column(name = "FIE_CODFIC", nullable = false, precision = 18, scale = 0)
	private Long codigoFichero;

	@Column(name = "FIE_BORRAR", nullable = false, precision = 1, scale = 0)
	private boolean borrar;

	public JFicheroExterno() {
	}

	public String getReferenciaExterna() {
		return this.referenciaExterna;
	}

	public void setReferenciaExterna(final String referenciaExterna) {
		this.referenciaExterna = referenciaExterna;
	}

	public Date getFechaReferencia() {
		return this.fechaReferencia;
	}

	public void setFechaReferencia(final Date fechaReferencia) {
		this.fechaReferencia = fechaReferencia;
	}

	public Long getCodigoFichero() {
		return this.codigoFichero;
	}

	public void setCodigoFichero(final Long codigoFichero) {
		this.codigoFichero = codigoFichero;
	}

	public boolean isBorrar() {
		return this.borrar;
	}

	public void setBorrar(final boolean borrar) {
		this.borrar = borrar;
	}

}

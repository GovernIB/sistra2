package es.caib.sistrages.core.service.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * JProceso
 */
@Entity
@Table(name = "STG_PROCES")
public class JProceso implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PROC_IDENT", unique = true, nullable = false)
	private String identificador;

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	public String getInstancia() {
		return instancia;
	}

	public void setInstancia(final String instancia) {
		this.instancia = instancia;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(final Date fecha) {
		this.fecha = fecha;
	}

	@Column(name = "PROC_INSTAN", length = 50)
	private String instancia;

	@Temporal(TemporalType.DATE)
	@Column(name = "PROC_FECHA", length = 7)
	private Date fecha;

	public JProceso() {
	}

}

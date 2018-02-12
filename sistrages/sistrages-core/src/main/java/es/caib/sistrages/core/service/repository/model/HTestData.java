package es.caib.sistrages.core.service.repository.model;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="TESTDATA")
public class HTestData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO", unique=true, nullable=false, length=40)
	private String codigo;

	@Column(name = "DESCRIPCION", length=100)
	private String descripcion;

	public HTestData() {
		super();
	}


	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
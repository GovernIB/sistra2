package es.caib.sistrages.core.service.repository.modeltest;

import java.io.Serializable;

//@Entity
//@Table(name="AREA")
public class HArea implements Serializable {
	private static final long serialVersionUID = 1L;

	// @Id
	// @Column(name = "CODIGO", unique=true, nullable=false, length=40)
	private String codigo;

	// @Column(name = "DESCRIPCION", length=100)
	private String descripcion;

	public HArea() {
		super();
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

}
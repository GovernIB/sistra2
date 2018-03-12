package es.caib.sistrages.core.service.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * JConfiguracionGlobal
 */
@Entity
@Table(name = "STG_CNFGLO")
public class JConfiguracionGlobal implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_CNFGLO_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_CNFGLO_SEQ", sequenceName = "STG_CNFGLO_SEQ")
	@Column(name = "CFG_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@Column(name = "CFG_PROP", nullable = false, length = 100)
	private String propiedad;

	@Column(name = "CFG_VALOR", length = 500)
	private String valor;

	@Column(name = "CFG_DESCR", nullable = false)
	private String descripcion;

	public JConfiguracionGlobal() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

	public String getPropiedad() {
		return this.propiedad;
	}

	public void setPropiedad(final String propiedad) {
		this.propiedad = propiedad;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(final String valor) {
		this.valor = valor;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

}

package es.caib.sistrages.core.service.repository.model;

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
import javax.persistence.UniqueConstraint;

/**
 * JListaFijaValoresCampoIndexado
 */
@Entity
@Table(name = "STG_LFVCIN", uniqueConstraints = @UniqueConstraint(columnNames = { "LFV_CODCIN", "LFV_VALOR" }))
public class JListaFijaValoresCampoIndexado implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_LFVCIN_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_LFVCIN_SEQ", sequenceName = "STG_LFVCIN_SEQ")
	@Column(name = "LFV_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LFV_CODCIN", nullable = false)
	private JCampoFormularioIndexado campoFormularioIndexado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LFV_DESCRIP", nullable = false)
	private JLiterales descripcion;

	@Column(name = "LFV_VALOR", nullable = false, length = 100)
	private String valor;

	@Column(name = "LFV_DEFECT", nullable = false, precision = 1, scale = 0)
	private boolean porDefecto;

	@Column(name = "LFV_ORDEN", nullable = false, precision = 2, scale = 0)
	private int orden;

	public JListaFijaValoresCampoIndexado() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long lfvCodigo) {
		this.codigo = lfvCodigo;
	}

	public JCampoFormularioIndexado getCampoFormularioIndexado() {
		return this.campoFormularioIndexado;
	}

	public void setCampoFormularioIndexado(final JCampoFormularioIndexado campoFormularioIndexado) {
		this.campoFormularioIndexado = campoFormularioIndexado;
	}

	public JLiterales getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final JLiterales descripcion) {
		this.descripcion = descripcion;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(final String valor) {
		this.valor = valor;
	}

	public boolean isPorDefecto() {
		return this.porDefecto;
	}

	public void setPorDefecto(final boolean porDefecto) {
		this.porDefecto = porDefecto;
	}

	public int getOrden() {
		return this.orden;
	}

	public void setOrden(final int orden) {
		this.orden = orden;
	}

}

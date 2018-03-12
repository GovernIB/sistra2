package es.caib.sistrages.core.service.repository.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * JLineaSeccionFormulario
 */
@Entity
@Table(name = "STG_FORLI")
public class JLineaSeccionFormulario implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FORLI_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FORLI_SEQ", sequenceName = "STG_FORLI_SEQ")
	@Column(name = "FLS_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FLS_CODPAF", nullable = false)
	private JPaginaFormulario paginaFormulario;

	@Column(name = "FLS_ORDEN", nullable = false, precision = 2, scale = 0)
	private int orden;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lineaSeccionFormulario")
	private Set<JElementoFormulario> elementoFormulario = new HashSet<JElementoFormulario>(0);

	public JLineaSeccionFormulario() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

	public JPaginaFormulario getPaginaFormulario() {
		return this.paginaFormulario;
	}

	public void setPaginaFormulario(final JPaginaFormulario paginaFormulario) {
		this.paginaFormulario = paginaFormulario;
	}

	public int getOrden() {
		return this.orden;
	}

	public void setOrden(final int orden) {
		this.orden = orden;
	}

	public Set<JElementoFormulario> getElementoFormulario() {
		return this.elementoFormulario;
	}

	public void setElementoFormulario(final Set<JElementoFormulario> elementoFormulario) {
		this.elementoFormulario = elementoFormulario;
	}

}

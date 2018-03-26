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
 * JPaginaFormulario
 */
@Entity
@Table(name = "STG_FORPAG")
public class JPaginaFormulario implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FORPAG_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FORPAG_SEQ", sequenceName = "STG_FORPAG_SEQ")
	@Column(name = "PAF_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAF_CODFOR", nullable = false)
	private JFormulario formulario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAF_SCRVAL")
	private JScript scriptValidacion;

	@Column(name = "PAF_ORDEN", nullable = false, precision = 2, scale = 0)
	private int orden;

	@Column(name = "PAF_PAGFIN", nullable = false, precision = 1, scale = 0)
	private boolean paginaFinal;

	@Column(name = "PAF_PAGLEL", nullable = false, precision = 1, scale = 0)
	private boolean paginaAsociadaListaElementos;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paginaFormulario")
	private Set<JLineaSeccionFormulario> lineasSeccionFormulario = new HashSet<JLineaSeccionFormulario>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paginaFormulario")
	private Set<JListaElementosFormulario> listasElementosFormulario = new HashSet<JListaElementosFormulario>(0);

	public JPaginaFormulario() {
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JFormulario getFormulario() {
		return this.formulario;
	}

	public void setFormulario(final JFormulario formulario) {
		this.formulario = formulario;
	}

	public JScript getScriptValidacion() {
		return this.scriptValidacion;
	}

	public void setScriptValidacion(final JScript scriptValidacion) {
		this.scriptValidacion = scriptValidacion;
	}

	public int getOrden() {
		return this.orden;
	}

	public void setOrden(final int orden) {
		this.orden = orden;
	}

	public boolean isPaginaFinal() {
		return this.paginaFinal;
	}

	public void setPaginaFinal(final boolean paginaFinal) {
		this.paginaFinal = paginaFinal;
	}

	public boolean isPaginaAsociadaListaElementos() {
		return this.paginaAsociadaListaElementos;
	}

	public void setPaginaAsociadaListaElementos(final boolean paginaAsociadaListaElementos) {
		this.paginaAsociadaListaElementos = paginaAsociadaListaElementos;
	}

	public Set<JLineaSeccionFormulario> getLineasSeccionFormulario() {
		return this.lineasSeccionFormulario;
	}

	public void setLineasSeccionFormulario(final Set<JLineaSeccionFormulario> lineasSeccionFormulario) {
		this.lineasSeccionFormulario = lineasSeccionFormulario;
	}

	public Set<JListaElementosFormulario> getListasElementosFormulario() {
		return this.listasElementosFormulario;
	}

	public void setListasElementosFormulario(final Set<JListaElementosFormulario> listasElementosFormulario) {
		this.listasElementosFormulario = listasElementosFormulario;
	}

}

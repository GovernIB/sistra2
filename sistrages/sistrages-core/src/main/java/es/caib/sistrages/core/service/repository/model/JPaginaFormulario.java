package es.caib.sistrages.core.service.repository.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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

import es.caib.sistrages.core.api.model.PaginaFormulario;

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

	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "PAF_SCRVAL")
	private JScript scriptValidacion;

	@Column(name = "PAF_ORDEN", nullable = false, precision = 2, scale = 0)
	private int orden;

	@Column(name = "PAF_PAGFIN", nullable = false, precision = 1, scale = 0)
	private boolean paginaFinal;

	@Column(name = "PAF_PAGLEL", nullable = false, precision = 1, scale = 0)
	private boolean paginaAsociadaListaElementos;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paginaFormulario", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<JLineaFormulario> lineasFormulario = new HashSet<>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paginaFormulario")
	private Set<JListaElementosFormulario> listasElementosFormulario = new HashSet<>(0);

	public JPaginaFormulario() {
		super();
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

	public Set<JListaElementosFormulario> getListasElementosFormulario() {
		return this.listasElementosFormulario;
	}

	public void setListasElementosFormulario(final Set<JListaElementosFormulario> listasElementosFormulario) {
		this.listasElementosFormulario = listasElementosFormulario;
	}

	public Set<JLineaFormulario> getLineasFormulario() {
		return lineasFormulario;
	}

	public void setLineasFormulario(final Set<JLineaFormulario> lineasFormulario) {
		this.lineasFormulario = lineasFormulario;
	}

	public void addLinea(final JLineaFormulario lf) {
		lf.setPaginaFormulario(this);
		this.getLineasFormulario().add(lf);
	}

	public void removeLinea(final JLineaFormulario lf) {
		this.getLineasFormulario().remove(lf);
		lf.setPaginaFormulario(null);
	}

	public PaginaFormulario toModel() {
		final PaginaFormulario pagina = new PaginaFormulario();
		pagina.setCodigo(codigo);
		if (scriptValidacion != null) {
			pagina.setScriptValidacion(scriptValidacion.toModel());
		}
		pagina.setOrden(orden);
		pagina.setPaginaFinal(paginaFinal);

		return pagina;
	}

	public static JPaginaFormulario fromModel(final PaginaFormulario model) {
		JPaginaFormulario jModel = null;
		if (model != null) {
			jModel = new JPaginaFormulario();
			if (model.getCodigo() != null) {
				jModel.setCodigo(model.getCodigo());
			}
			jModel.setScriptValidacion(JScript.fromModel(model.getScriptValidacion()));
			jModel.setOrden(model.getOrden());
			jModel.setPaginaFinal(model.isPaginaFinal());
		}
		return jModel;
	}

	public static JPaginaFormulario createDefault(final JFormulario pJFormulario) {
		final JPaginaFormulario jPagina = new JPaginaFormulario();
		jPagina.setOrden(1);
		jPagina.setPaginaFinal(true);
		jPagina.setPaginaAsociadaListaElementos(false);
		jPagina.setFormulario(pJFormulario);
		return jPagina;
	}
}

package es.caib.sistrages.core.service.repository.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoOculto;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

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

	@Column(name = "PAF_IDENTI")
	private String identificador;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAF_CODFOR", nullable = false)
	private JFormulario formulario;

	@ManyToOne(fetch = FetchType.LAZY,   cascade = CascadeType.ALL)
	@JoinColumn(name = "PAF_SCRVAL")
	private JScript scriptValidacion;

	@ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
	@JoinColumn(name = "PAF_SCRNAV")
	private JScript scriptNavegacion;

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

	/**
	 * @return the alias
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param alias
	 *                  the alias to set
	 */
	public void setIdentificador(final String alias) {
		this.identificador = alias;
	}

	/**
	 * @return the scriptNavegacion
	 */
	public JScript getScriptNavegacion() {
		return scriptNavegacion;
	}

	/**
	 * @param scriptNavegacion
	 *                             the scriptNavegacion to set
	 */
	public void setScriptNavegacion(final JScript scriptNavegacion) {
		this.scriptNavegacion = scriptNavegacion;
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
		if (scriptNavegacion != null) {
			pagina.setScriptNavegacion(scriptNavegacion.toModel());
		}
		pagina.setOrden(orden);
		pagina.setPaginaFinal(paginaFinal);
		pagina.setIdentificador(identificador);

		return pagina;
	}

	public PaginaFormulario toModelCompleto() {
		final PaginaFormulario pagina = new PaginaFormulario();
		pagina.setCodigo(codigo);
		if (scriptValidacion != null) {
			pagina.setScriptValidacion(scriptValidacion.toModel());
		}
		pagina.setOrden(orden);
		pagina.setIdentificador(identificador);
		pagina.setPaginaFinal(paginaFinal);
		if (scriptNavegacion != null) {
			pagina.setScriptNavegacion(scriptNavegacion.toModel());
		}
		pagina.setPaginaAsociadaListaElementos(paginaAsociadaListaElementos);
		final List<LineaComponentesFormulario> lineas = new ArrayList<>(0);
		if (this.lineasFormulario != null) {
			for (final JLineaFormulario linea : this.lineasFormulario) {
				final LineaComponentesFormulario comp = new LineaComponentesFormulario();
				comp.setOrden(linea.getOrden());
				comp.setCodigo(linea.getCodigo());
				final List<ComponenteFormulario> componentes = new ArrayList<>(0);
				if (linea.getElementoFormulario() != null) {
					for (final JElementoFormulario elemento : linea.getElementoFormulario()) {
						switch (TypeObjetoFormulario.fromString(elemento.getTipo())) {
						case CAMPO_TEXTO:
							componentes.add(elemento.getCampoFormulario().getCampoFormularioTexto().toModel());
							break;
						case CHECKBOX:
							componentes.add(
									elemento.getCampoFormulario().getCampoFormularioCasillaVerificacion().toModel());
							break;
						case ETIQUETA:
							componentes.add(elemento.getEtiquetaFormulario().toModel());
							break;
						case IMAGEN:
							componentes.add(elemento.getImagenFormulario().toModel());
							break;
						case LISTA_ELEMENTOS:
							componentes.add(elemento.getCampoFormulario().toModel(ComponenteFormulario.class));
							break;
						case SECCION:
							componentes.add(elemento.getSeccionFormulario().toModel());
							break;
						case SECCION_REUTILIZABLE:
							componentes.add(elemento.getSeccionReutilizableFormulario().toModel());
							break;
						case SELECTOR:
							componentes.add(elemento.getCampoFormulario().getCampoFormularioIndexado().toModel());
							break;
						case CAMPO_OCULTO:
							componentes
									.add(elemento.getCampoFormulario().toModel(ComponenteFormularioCampoOculto.class));
							break;
						case CAPTCHA:
							componentes
							.add(elemento.getCaptchaFormulario().toModel());
							break;
						case LINEA:
						case PAGINA:
							break;
						}
					}
				}
				comp.setComponentes(componentes);
				lineas.add(comp);
			}
		}

		if (this.listasElementosFormulario != null) {
			for (final JListaElementosFormulario elementoForm : listasElementosFormulario) {
				final LineaComponentesFormulario comp = new LineaComponentesFormulario();
				comp.setCodigo(elementoForm.getCodigo());
				final List<ComponenteFormulario> componentes = new ArrayList<>(0);
				if (elementoForm.getElementoFormulario() != null) {
					componentes.add(elementoForm.getElementoFormulario().toModel(JElementoFormulario.class));
				}
				comp.setComponentes(componentes);
				lineas.add(comp);
			}
		}
		pagina.setLineas(lineas);

		return pagina;
	}

	public static JPaginaFormulario fromModel(final PaginaFormulario model) {
		JPaginaFormulario jModel = null;
		if (model != null) {
			jModel = new JPaginaFormulario();
			if (model.getCodigo() != null) {
				jModel.setCodigo(model.getCodigo());
			}
			jModel.setIdentificador(model.getIdentificador());
			jModel.setScriptValidacion(JScript.fromModel(model.getScriptValidacion()));
			jModel.setScriptNavegacion(JScript.fromModel(model.getScriptNavegacion()));
			jModel.setOrden(model.getOrden());
			jModel.setPaginaFinal(model.isPaginaFinal());
		}
		return jModel;
	}

	public static JPaginaFormulario createDefault(final JFormulario pJFormulario) {
		final JPaginaFormulario jPagina = new JPaginaFormulario();
		jPagina.setOrden(1);
		jPagina.setIdentificador("P1");
		jPagina.setPaginaFinal(true);
		jPagina.setPaginaAsociadaListaElementos(false);
		jPagina.setFormulario(pJFormulario);
		return jPagina;
	}

	public static JPaginaFormulario clonar(final JPaginaFormulario pagina, final JFormulario jformulario,
			final boolean cambioArea) {
		JPaginaFormulario jpagina = null;
		if (pagina != null) {
			jpagina = new JPaginaFormulario();

			jpagina.setFormulario(jformulario);
			jpagina.setIdentificador(pagina.getIdentificador());
			jpagina.setOrden(pagina.getOrden());
			jpagina.setScriptValidacion(JScript.clonar(pagina.getScriptValidacion()));
			jpagina.setScriptNavegacion(JScript.clonar(pagina.getScriptNavegacion()));

			jpagina.setPaginaFinal(pagina.isPaginaFinal());
			jpagina.setPaginaAsociadaListaElementos(pagina.isPaginaAsociadaListaElementos());

			if (pagina.getLineasFormulario() != null) {
				final List<JLineaFormulario> jlineas = new ArrayList<>(pagina.getLineasFormulario());
				Collections.sort(jlineas, new Comparator<JLineaFormulario>() {
					@Override
					public int compare(final JLineaFormulario p1, final JLineaFormulario p2) {
						return Integer.compare(p1.getOrden(), p2.getOrden());
					}

				});
				final Set<JLineaFormulario> lineasFormulario = new HashSet<>(0);
				int ordenLinea = 1;
				for (final JLineaFormulario jlinea : jlineas) {
					jlinea.setOrden(ordenLinea);
					lineasFormulario.add(JLineaFormulario.clonar(jlinea, jpagina, cambioArea));
					ordenLinea++;
				}
				jpagina.setLineasFormulario(lineasFormulario);
			}

			if (pagina.getListasElementosFormulario() != null) {
				final Set<JListaElementosFormulario> listasElementosFormulario = new HashSet<>(0);
				for (final JListaElementosFormulario listaElementos : pagina.getListasElementosFormulario()) {
					listasElementosFormulario.add(JListaElementosFormulario.clonar(listaElementos, jpagina));
				}
				jpagina.setListasElementosFormulario(listasElementosFormulario);
			}
		}
		return jpagina;
	}

	public static JPaginaFormulario fromModelCompleto(PaginaFormulario model) {
		JPaginaFormulario jModel = null;
		if (model != null) {
			jModel = new JPaginaFormulario();
			jModel.setCodigo(model.getCodigo());
			if (model.getCodigo() != null) {
				jModel.setCodigo(model.getCodigo());
			}
			jModel.setIdentificador(model.getIdentificador());
			Set<JLineaFormulario> lineasFormulario = new HashSet<JLineaFormulario>();
			if (model.getLineas() != null && !model.getLineas().isEmpty()) {
				for( LineaComponentesFormulario linea : model.getLineas()) {
					JLineaFormulario jlinea = JLineaFormulario.fromModel(linea);
					lineasFormulario.add(jlinea);
				}
			}
			jModel.setLineasFormulario(lineasFormulario);
			jModel.setOrden(model.getOrden());
			jModel.setPaginaFinal(model.isPaginaFinal());
			jModel.setScriptValidacion(JScript.fromModel(model.getScriptValidacion()));
			jModel.setScriptNavegacion(JScript.fromModel(model.getScriptNavegacion()));


		}
		return jModel;
	}
}

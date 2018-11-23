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

import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.comun.ConstantesDisenyo;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * JLineaFormulario
 */
@Entity
@Table(name = "STG_FORLI")
public class JLineaFormulario implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FORLI_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FORLI_SEQ", sequenceName = "STG_FORLI_SEQ")
	@Column(name = "FLS_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FLS_CODPAF", nullable = false)
	private JPaginaFormulario paginaFormulario;

	@Column(name = "FLS_ORDEN", nullable = false, precision = 2, scale = 0)
	private int orden;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lineaFormulario", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<JElementoFormulario> elementoFormulario = new HashSet<>(0);

	public JLineaFormulario() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
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

	public void addElemento(final JElementoFormulario e) {
		e.setLineaFormulario(this);
		this.getElementoFormulario().add(e);
	}

	public void removeElemento(final JElementoFormulario e) {
		this.getElementoFormulario().remove(e);
		e.setLineaFormulario(null);
	}

	public LineaComponentesFormulario toModel() {
		final LineaComponentesFormulario pagina = new LineaComponentesFormulario();
		pagina.setCodigo(codigo);
		pagina.setOrden(orden);
		return pagina;
	}

	public static JLineaFormulario fromModel(final LineaComponentesFormulario model) {
		JLineaFormulario jModel = null;
		if (model != null) {
			jModel = new JLineaFormulario();
			jModel.setCodigo(model.getCodigo());
			jModel.setOrden(model.getOrden());
		}
		return jModel;
	}

	public static JLineaFormulario createDefault(final int pOrden, final JPaginaFormulario pJPagina) {
		final JLineaFormulario jModel = new JLineaFormulario();
		jModel.setOrden(pOrden);
		pJPagina.addLinea(jModel);
		return jModel;
	}

	public boolean completa() {
		boolean res = false;
		int ncolumnas = 0;
		if (!elementoFormulario.isEmpty()) {
			for (final JElementoFormulario jElementoFormulario : elementoFormulario) {
				switch (TypeObjetoFormulario.fromString(jElementoFormulario.getTipo())) {
				case SECCION:
					ncolumnas += jElementoFormulario.getNumeroColumnas();
					break;
				case ETIQUETA:
					ncolumnas += jElementoFormulario.getNumeroColumnas();
					break;
				case CAMPO_TEXTO:
					ncolumnas += jElementoFormulario.getNumeroColumnas();
					break;
				case SELECTOR:
					ncolumnas += jElementoFormulario.getNumeroColumnas();
					break;
				default:
					break;
				}

				if (ncolumnas >= ConstantesDisenyo.NUM_MAX_COMPONENTES_LINEA) {
					res = true;
					break;
				}
			}
		}
		return res;
	}

	public static JLineaFormulario clonar(final JLineaFormulario linea, final JPaginaFormulario jpagina,
			final boolean cambioArea) {
		JLineaFormulario jlineaFormulario = null;
		if (linea != null) {
			jlineaFormulario = new JLineaFormulario();
			jlineaFormulario.setOrden(linea.getOrden());
			jlineaFormulario.setPaginaFormulario(jpagina);
			if (linea.getElementoFormulario() != null) {
				final Set<JElementoFormulario> elementoFormulario = new HashSet<>(0);
				for (final JElementoFormulario elemento : linea.getElementoFormulario()) {
					elementoFormulario.add(JElementoFormulario.clonar(elemento, jlineaFormulario, jpagina, cambioArea));
				}
				jlineaFormulario.setElementoFormulario(elementoFormulario);
			}

		}
		return jlineaFormulario;
	}

}
